package com.example.bitbybit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class foodDetailsPage extends Fragment {

    public foodDetailsPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_details_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name=bundle.getString("username");
        bundle.putString("username", name);
        String food = bundle.getString("foodbundle");
        bundle.putString("foodbundle", food);

        System.out.println(food);


        TextView foodTitle = view.findViewById(R.id.foodTitle);
        TextView calories = view.findViewById(R.id.Calories);
        TextView carbohydrates = view.findViewById(R.id.Carbo);
        TextView protein = view.findViewById(R.id.Protein);
        TextView fat = view.findViewById(R.id.Fat);
        ImageView foodImage = view.findViewById(R.id.foodImage);

        Thread dataThread = new Thread(() -> {
            try {
                Connection connection = Line.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM recipe WHERE recipe_id = '" +food+ "'");
                ResultSet res = ps.executeQuery();

                if(res.next()){
                    foodTitle.setText(res.getString(1));
                    calories.setText(String.valueOf(res.getInt(6)) + " kcal");
                    carbohydrates.setText(String.valueOf(res.getInt(7)) + "g");
                    protein.setText(String.valueOf(res.getInt(8)) + "g");
                    fat.setText(String.valueOf(res.getInt(9)) + "g");

                }else {
                    foodTitle.setText("");
                    calories.setText("");
                    carbohydrates.setText("");
                    protein.setText("");
                    fat.setText("");

                }
                res.close();
                connection.close();


            }catch (SQLException e){
                e.printStackTrace();
            }
        });
        dataThread.start();
        while (dataThread.isAlive()){

        }

        Button btnBackFoodDetails = view.findViewById(R.id.returnToHomePageButton);
        View.OnClickListener OCLBackHP = v -> Navigation.findNavController(view).navigate(R.id.homePage, bundle);
        btnBackFoodDetails.setOnClickListener(OCLBackHP);

        Button btnPrepareIng = view.findViewById(R.id.frenchToastIngredientsPageButton);
        View.OnClickListener OCLListIngredient = v -> Navigation.findNavController(view).navigate(R.id.foodIngredientPage, bundle);
        btnPrepareIng.setOnClickListener(OCLListIngredient);

        ImageButton saveRecipe = view.findViewById(R.id.saveRecipeButton);
        View.OnClickListener OCLSaveRecipe = v -> {

            AtomicReference<Boolean> status = new AtomicReference<>(false);

            Thread dataThread2 = new Thread(() -> {

                try {
                    Connection connection = Line.getConnection();
                    //check if there is data saved
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM favorite_recipe WHERE recipe_id = '" + foodTitle.getText().toString().trim() + "' AND user_id = '" + name + "'");
                    ResultSet resultSet = preparedStatement.executeQuery();

                    //if the post has been saved before then remove saved
                    if (resultSet.next()){
                        PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM favorite_recipe WHERE recipe_id = '" + foodTitle.getText().toString().trim() + "' AND user_id = '" + name + "'");
                        preparedStatement1.executeUpdate();
                        preparedStatement1.close();
                        status.set(false);
                    }
                    //if recipe has not been saved then add to saved
                    else {
                        PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO favorite_recipe (recipe_id,user_id) VALUES ('" + foodTitle.getText().toString().trim() + "', '" + name + "')");
                        preparedStatement2.executeUpdate();
                        preparedStatement2.close();
                        status.set(true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            dataThread2.start();
            while (dataThread2.isAlive()){

            }

            if (status.get())
                Toast.makeText(getContext(), "Added to saved recipes", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Removed from saved recipes", Toast.LENGTH_SHORT).show();

        };
        saveRecipe.setOnClickListener(OCLSaveRecipe);

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

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton2);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage, bundle);
        floatButton.setOnClickListener(OCLFloatButton);

    }

}