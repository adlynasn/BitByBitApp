package com.example.bitbybit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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


public class viewCaloriesPage extends Fragment {


    private ArrayList<NewsIntake> newsIntakeArraylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_calories_page, container, false);
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView calorieTotal = view.findViewById(R.id.totalMealCalories);
        TextView carbohydrateTotal = view.findViewById(R.id.totalMealCarbo);
        TextView proteinTotal = view.findViewById(R.id.totalMealProtein);
        TextView fatTotal = view.findViewById(R.id.totalMealFat);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name=bundle.getString("username");
        bundle.putString("username", name);
        String date = bundle.getString("dateMeal");
        bundle.putString("dateMeal", date);

        dataInitialize(calorieTotal, carbohydrateTotal, proteinTotal, fatTotal, date, name);

        RecyclerView recyclerview = view.findViewById(R.id.recyclerViewMeals);
        System.out.println("kat sini");
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        AdaptorIntake myAdapter = new AdaptorIntake(getContext(), newsIntakeArraylist);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        Button BtnBackToIntake = view.findViewById(R.id.backToCalorieIntakePageButton);
        View.OnClickListener OCLBtnBack = v -> Navigation.findNavController(view).navigate(R.id.action_viewCaloriesPage_to_caloriesIntakePage, bundle);
        BtnBackToIntake.setOnClickListener(OCLBtnBack);


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

    private void dataInitialize(TextView a, TextView b, TextView c, TextView d, String date, String username) {

        newsIntakeArraylist = new ArrayList<>();
        System.out.println("dalam ni");
        System.out.println(date);


        @SuppressLint("SetTextI18n") Thread dataThread = new Thread(() -> {

            try {
                System.out.println("dalam connection");
                Connection connection = Line.getConnection();
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM calorie_nutrition WHERE entry_date = '" +date+ "' AND user_id = '" +username+ "'");
                ResultSet res = ps.executeQuery();
                int total_calorie = 0;
                int total_carbohydrate = 0;
                int total_protein = 0;
                int total_fat = 0;

                while (res.next()) {
                    String timeIntake = res.getString(3);
                    String calorieIntake = res.getString(4);
                    String carbohydrateIntake = res.getString(5);
                    String proteinIntake = res.getString(6);
                    String fatIntake = res.getString(7);
                    String nameIntake = res.getString(8);

                    int calorie = res.getInt(4);
                    int carbohydrate = res.getInt(5);
                    int protein = res.getInt(6);
                    int fat = res.getInt(7);
                    total_calorie+=calorie;
                    total_carbohydrate+=carbohydrate;
                    total_protein+=protein;
                    total_fat+=fat;

                    NewsIntake newsIntake = new NewsIntake(timeIntake, calorieIntake, carbohydrateIntake, proteinIntake, fatIntake, nameIntake);
                    newsIntakeArraylist.add(newsIntake);

                }
                a.setText(Integer.toString(total_calorie));
                b.setText(Integer.toString(total_carbohydrate));
                c.setText(Integer.toString(total_protein));
                d.setText(Integer.toString(total_fat));
                System.out.println(date);

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
TODO
- list days where there are data regarding calorie
- Navigate to calories intake page when pressed
 */