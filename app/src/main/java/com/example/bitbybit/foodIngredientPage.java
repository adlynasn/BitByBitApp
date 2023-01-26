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
import java.util.Arrays;
import java.util.List;


public class foodIngredientPage extends Fragment {

    private ArrayList<News> newsArraylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_ingredient_page, container, false);
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name=bundle.getString("username");
        String food = bundle.getString("food-bundle");
        bundle.putString("food-bundle", food);
        bundle.putString("username", name);
        String foodName = bundle.getString("FoodName");
        bundle.putString("FoodName", foodName);
        System.out.println(foodName);
        System.out.println(name);

        dataInitialize(foodName);

        RecyclerView recyclerview = view.findViewById(R.id.recyclerViewListIngredient);
        System.out.println("kat sini");
        recyclerview.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerview.setHasFixedSize(true);
        MyAdapter myAdapter = new MyAdapter(getContext(), newsArraylist);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        Button btnBackIngredient = view.findViewById(R.id.foodDetailsPageButton);
        View.OnClickListener OCLBackIngredient = v -> Navigation.findNavController(view).navigate(R.id.foodDetailsPage, bundle);
        btnBackIngredient.setOnClickListener(OCLBackIngredient);

        Button BtnListIngredientToStep = view.findViewById(R.id.recipeStepsPageButton);
        View.OnClickListener OCLListIngredient = v -> Navigation.findNavController(view).navigate(R.id.foodStepsPage, bundle);
        BtnListIngredientToStep.setOnClickListener(OCLListIngredient);

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

    private void dataInitialize(String obj) {
        newsArraylist = new ArrayList<>();
        System.out.println("dalam ni");


        Thread dataThread = new Thread(() -> {

           try {
               System.out.println("dalam connection");
               Connection connection = Line.getConnection();
               assert connection != null;
               PreparedStatement ps = connection.prepareStatement("SELECT recipe_ingredient FROM recipe WHERE recipe_id = '" +obj+ "'");
               ResultSet res = ps.executeQuery();

               if(res.next()){
                   String ingredient = res.getString(1);
                   System.out.println(ingredient);
                   List<String> list = new ArrayList<>(Arrays.asList(ingredient.split(">")));

                   for (int i = 0; i < list.size(); i++) {
                       News news = new News(list.get(i));
                       newsArraylist.add(news);
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