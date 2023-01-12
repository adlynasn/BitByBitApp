package com.example.bitbybit;

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
import java.util.Arrays;
import java.util.List;


public class caloriesIntakePage extends Fragment {

    private ArrayList<NewsMeal> newsMealArraylist;
    private RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calories_intake_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name = bundle.getString("username");
        bundle.putString("username", name);

        dataInitialized(name);

        recyclerview = view.findViewById(R.id.recyclerViewCalorieIntake);
        System.out.println("kat sini");
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        AdaptorMeal myAdapter = new AdaptorMeal(getContext(), newsMealArraylist, bundle);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        Button BtnBackToIntake = view.findViewById(R.id.backToLoginPageButton);
        View.OnClickListener OCLBtnBack = v -> Navigation.findNavController(view).navigate(R.id.action_caloriesIntakePage_to_profilePage, bundle);
        BtnBackToIntake.setOnClickListener(OCLBtnBack);


        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
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

    private void dataInitialized(String name) {
        newsMealArraylist = new ArrayList<>();
        System.out.println("dalam ni");


        Thread dataThread = new Thread(() -> {

            try {
                System.out.println("dalam connection");
                Connection connection = Line.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM calorie_nutrition WHERE user_id = '" + name + "'");
                ResultSet res = ps.executeQuery();


                while (res.next()) {

                    String Date = res.getString(2);
                    if (newsMealArraylist.contains(Date)) {
                        System.out.println("Date already in arraylist");
                    } else {
                        System.out.println(Date + " added");
                        NewsMeal newsMeal = new NewsMeal(Date);
                        newsMealArraylist.add(newsMeal);
                    }
                }
//                res.close();
//                connection.close();

                if (res.next()) {
                    String ingredient = res.getString(1);
                    System.out.println(ingredient);


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

/*
TODO display calorie and nutrients consumed
- check for entries from db related to user and date
- display each food consumed on that day with calorie and nutritional value
- show sum of calorie and nutrient at the bottom

 */