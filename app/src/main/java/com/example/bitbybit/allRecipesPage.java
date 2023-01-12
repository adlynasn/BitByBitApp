package com.example.bitbybit;

import android.content.ContentResolver;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class allRecipesPage extends Fragment {


    private ArrayList<NewsAll> newsAllArraylist;
    private RecyclerView recyclerview;
    private int[] imageResourceID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_recipes_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name=bundle.getString("username");
        bundle.putString("username", name);

        Drawable drawable = getPicFromDB("French Toast", requireActivity().getContentResolver(), new Thread());

        dataInitialize();

        recyclerview = view.findViewById(R.id.RecyclerAllrecipe);
        System.out.println("kat sini");
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        AdaptorAllRecipe myAdapter = new AdaptorAllRecipe(getContext(), newsAllArraylist, bundle);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();



        Button btnBackToHP = view.findViewById(R.id.returnToHomePageButton);
        View.OnClickListener OCLBackHP = v -> Navigation.findNavController(view).navigate(R.id.homePage, bundle);
        btnBackToHP.setOnClickListener(OCLBackHP);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    Navigation.findNavController(view).navigate(R.id.homePage, bundle);
                    return true;
                case R.id.savedRecipes:
                    Navigation.findNavController(view).navigate(R.id.savedRecipesPage, bundle);
                    return true;
                case R.id.missions:
                    Navigation.findNavController(view).navigate(R.id.missionsPage, bundle);
                    return true;
                case R.id.profile:
                    Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
                    return true;
            }
            return false;
        });

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage, bundle);
        floatButton.setOnClickListener(OCLFloatButton);

    }

    private void dataInitialize() {
        newsAllArraylist = new ArrayList<>();
        System.out.println("dalam ni");


        Thread dataThread = new Thread(() -> {

            try {
                System.out.println("dalam connection");
                Connection connection = Line.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM recipe ");
                ResultSet res = ps.executeQuery();

                int i =0;

                imageResourceID = new int[]{
                        R.drawable.airfryersalmon,
                        R.drawable.asiancucumbersalad,
                        R.drawable.avocadosmoothie,
                        R.drawable.balsamicglazedsalmon,
                        R.drawable.blueberrysmoothie,
                        R.drawable.french_toast_image,
                        R.drawable.oatmealsmoothie,
                        R.drawable.peachsmoothie,
                        R.drawable.pretzel,
                        R.drawable.ramensalad,
                        R.drawable.smoothiebowl,
                        R.drawable.strawberrysalad,
                        R.drawable.tofustirfry,
                        R.drawable.veganburger,
                        R.drawable.veganpancakes

                };

                while (res.next()) {
                    String foodName = res.getString(1);
                    NewsAll newsAll = new NewsAll(foodName, imageResourceID[i]);
                    newsAllArraylist.add(newsAll);
                    i++;


                }
                res.close();
                connection.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        dataThread.start();
        while (dataThread.isAlive()) {

        }
    }


    public static Drawable insertPicIntoDB(Uri imageUri, ContentResolver contentResolver, Thread dataThread){
        AtomicReference<InputStream> inputStreamAtomicReference = new AtomicReference<>();
        dataThread = new Thread(() -> {
            InputStream inputStream = null;
            try{
                Connection connection = Line.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO recipe(recipe_picture) VALUES(?)");
                inputStream = contentResolver.openInputStream(imageUri);
                preparedStatement.setBlob(1, inputStream);
                preparedStatement.execute();
            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            }
            assert  inputStream != null;
            inputStreamAtomicReference.set(inputStream);
        });
        while(dataThread.isAlive()){

        }
        return Drawable.createFromStream(inputStreamAtomicReference.get(), imageUri.toString());
    }

    // Returns a Drawable object. Requires recipe_id
    public static Drawable getPicFromDB(String recipe_id, ContentResolver contentResolver, Thread dataThread){
        AtomicReference<Drawable> atomicReference = new AtomicReference<>();
        dataThread = new Thread(() -> {
            try(
                    Connection connection = Line.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT recipe_picture FROM recipe WHERE recipe_id = ?");
                    ){
                preparedStatement.setString(1, recipe_id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    Blob blob = resultSet.getBlob(1);
                    byte[] data = blob.getBytes(1, (int) blob.length());
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
                    Drawable drawable = new BitmapDrawable(inputStream);
                    atomicReference.set(drawable);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Drawable toReturn = atomicReference.get();
        return toReturn;
    }

}