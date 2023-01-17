package com.example.bitbybit;

import static com.example.bitbybit.AdaptorMeal.bundle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class adminDeleteRecipePage extends Fragment {


    private int[] imageResourceID;
    private ArrayList<NewsAdminRecipe> newsAdminRecipeArrayList;
    private RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_delete_recipe_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name=bundle.getString("username");
        bundle.putString("username", name);


        DataInitialize();

        recyclerview = view.findViewById(R.id.RecyclerAdminAllrecipe);
        System.out.println("kat sini");
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        AdaptorAdminRecipe myAdapter = new AdaptorAdminRecipe(getContext(), newsAdminRecipeArrayList, bundle);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        Button BtnBackAdminAll = view.findViewById(R.id.returnToAdminHomePageButton);
        View.OnClickListener OCLBackAdminAll = v -> Navigation.findNavController(view).navigate(R.id.adminHomePage);
        BtnBackAdminAll.setOnClickListener(OCLBackAdminAll);
    }

    private void DataInitialize() {
        newsAdminRecipeArrayList = new ArrayList<>();
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
                        R.drawable.veganpancakes,
                        R.drawable.veganpancakes

                };

                while (res.next()) {
                    String foodName = res.getString(1);
                    NewsAdminRecipe newsAdminRecipe = new NewsAdminRecipe(foodName, imageResourceID[i]);
                    newsAdminRecipeArrayList.add(newsAdminRecipe);
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
}