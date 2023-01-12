package com.example.bitbybit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class foodStepsPage extends Fragment {


    private ArrayList<NewsStep> newsStepArraylist;
    private RecyclerView recyclerview;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_steps_page, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name=bundle.getString("username");
        String food = bundle.getString("foodbundle");
        bundle.putString("foodbundle", food);
        bundle.putString("username", name);
        System.out.println(food);
        System.out.println(name);
        String foodName = bundle.getString("FoodName");
        bundle.putString("FoodName", foodName);

        dataInitialize(foodName);

        recyclerview = view.findViewById(R.id.recyclerViewSteps);
        System.out.println("kat sini");
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        AdaptorStepsRecipe myAdapter = new AdaptorStepsRecipe(getContext(), newsStepArraylist);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        Button BtnBackFoodIng = view.findViewById(R.id.foodIngredientsPageButton);
        View.OnClickListener OCLBackIngredient = v -> Navigation.findNavController(view).navigate(R.id.action_foodStepsPage_to_foodIngredientPage, bundle);
        BtnBackFoodIng.setOnClickListener(OCLBackIngredient);

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

    private void dataInitialize(String foodName) {

        newsStepArraylist = new ArrayList<>();
        System.out.println("dalam ni");


        Thread dataThread = new Thread(() -> {

            try {
                System.out.println("dalam connection");
                Connection connection = Line.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT recipe_instruction FROM recipe WHERE recipe_id = '" +foodName+ "'");
                ResultSet res = ps.executeQuery();

                if(res.next()){
                    String ingredient = res.getString(1);
                    System.out.println(ingredient);
                    List<String> list = new ArrayList<String>(Arrays.asList(ingredient.split(">")));

                    for (int i = 0; i < list.size(); i++) {
                        NewsStep news = new NewsStep(list.get(i));
                        newsStepArraylist.add(news);
                    }

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
    }

}

