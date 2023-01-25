package com.example.bitbybit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;



@SuppressWarnings("deprecation")
public class homePage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name = bundle.getString("username");
        bundle.putString("username", name);

        ImageView LatestRecipeImage = view.findViewById(R.id.LatestRecipeImage);
        TextView latestRecipeName = view.findViewById(R.id.latestRecipeName);
        TextView feedTitle = view.findViewById(R.id.postTitle);
        TextView feedDescription = view.findViewById(R.id.postDescription);


        Thread dataThread = new Thread(() -> {

            try {
                //get info from db
                System.out.println("Trying to access recipe db");
                Connection connection = Line.getConnection();
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("SELECT recipe_id FROM recipe ORDER BY recipe_id DESC LIMIT 1");
                ResultSet resultSet = ps.executeQuery();
                PreparedStatement ps1 = connection.prepareStatement("SELECT recipe_id FROM recipe LIMIT 2");
                ResultSet resultSet1 = ps1.executeQuery();
                PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM post ORDER BY post_id DESC LIMIT 1");
                ResultSet resultSet2 = ps2.executeQuery();

                if (resultSet.next()) {
                    System.out.println("Recipe db accessed");
                    //set name of latest recipe
                    latestRecipeName.setText(resultSet.getString(1));
                }
                resultSet.close();
                ps.close();

                //set name of the 2 recipes

                resultSet1.close();
                ps1.close();

                if (resultSet2.next()) {
                    System.out.println("Post db accessed");
                    feedTitle.setText(resultSet2.getString(2));
                    feedDescription.setText(resultSet2.getString(3));
                }

                resultSet2.close();
                ps2.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        dataThread.start();
        while (dataThread.isAlive()) {

        }


        Button BtnAllRecipe = view.findViewById(R.id.buttonAllReceipe);
        View.OnClickListener OCLAllRecipe = v -> {
            Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.action_homePage_to_allRecipesPage, bundle);
        };
        BtnAllRecipe.setOnClickListener(OCLAllRecipe);


        Button btnFeed = view.findViewById(R.id.buttonFeed);
        View.OnClickListener OCLFeed = v -> {
            Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.socialFeedPage, bundle);
        };
        btnFeed.setOnClickListener(OCLFeed);


        LinearLayout feed = view.findViewById(R.id.textFeedLayout);
        View.OnClickListener OCLFeed2 = v -> Navigation.findNavController(view).navigate(R.id.socialFeedPage, bundle);
        feed.setOnClickListener(OCLFeed2);

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
                    System.out.println(name);
                    Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
                    return true;
            }
            return false;
        });

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton2);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage, bundle);
        floatButton.setOnClickListener(OCLFloatButton);


        //Latest recipe pic
        View.OnClickListener OCLLatestRecipeImage = v -> {
            AtomicReference<String> recipe_id = new AtomicReference<>();
            Thread dataThread2 = new Thread(() -> {

                try {
                    //get info from db
                    System.out.println("Trying to access recipe db");
                    Connection connection = Line.getConnection();
                    assert connection != null;
                    PreparedStatement ps = connection.prepareStatement("SELECT recipe_id FROM recipe ORDER BY recipe_id DESC LIMIT 1");
                    ResultSet resultSet = ps.executeQuery();

                    if (resultSet.next()) {
                        System.out.println("Recipe db accessed");
                        //get recipe_id

                        recipe_id.set(resultSet.getString(1));
                        String food_name = recipe_id.get();
                        bundle.putString("food-bundle", food_name);

                    }
                    resultSet.close();
                    ps.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dataThread2.start();
            while (dataThread2.isAlive()) {

            }

            Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
            bundle.putString("FoodName", recipe_id.get());
            Navigation.findNavController(view).navigate(R.id.foodDetailsPage, bundle);
        };
        LatestRecipeImage.setOnClickListener(OCLLatestRecipeImage);


        //Recipe 1 pic
    }

}