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
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link allRecipesPage#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("deprecation")
public class allRecipesPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public allRecipesPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment allRecipesPage.
     */
    // TODO: Rename and change types and number of parameters
    public static allRecipesPage newInstance(String param1, String param2) {
        allRecipesPage fragment = new allRecipesPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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