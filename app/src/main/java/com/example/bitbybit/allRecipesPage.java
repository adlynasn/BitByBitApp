package com.example.bitbybit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class allRecipesPage extends Fragment {


    private ArrayList<NewsAll> newsAllArraylist;
    private int[] imageResourceID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_recipes_page, container, false);
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name=bundle.getString("username");
        bundle.putString("username", name);

        dataInitialize();

        RecyclerView recyclerview = view.findViewById(R.id.RecyclerAllrecipe);
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
                assert connection != null;
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


}