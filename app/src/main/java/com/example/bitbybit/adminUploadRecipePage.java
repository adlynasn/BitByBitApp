package com.example.bitbybit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;


public class adminUploadRecipePage extends Fragment {

    public adminUploadRecipePage() {
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
        return inflater.inflate(R.layout.fragment_admin_upload_recipe_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name = bundle.getString("username");
        bundle.putString("username", name);

        Button BtnAddRecipe = view.findViewById(R.id.addRecipeButton);
        View.OnClickListener OCLAddRecipe = v -> {

            EditText recipeName = view.findViewById(R.id.recipeName);
            EditText calories = view.findViewById(R.id.calories);
            EditText carbohydrates = view.findViewById(R.id.recipeCarbohydrate);
            EditText fat = view.findViewById(R.id.recipeFat);
            EditText protein = view.findViewById(R.id.recipeProtein);
            EditText Ingredients = view.findViewById(R.id.recipeIngredients);
            EditText Steps = view.findViewById(R.id.recipeSteps);

            AtomicReference<Boolean> status = new AtomicReference<>(false);
            AtomicReference<Boolean> status1 = new AtomicReference<>(false);

            Thread dataThread = new Thread(() -> {

                try {
                    Connection connection = Line.getConnection();
                    System.out.println(recipeName.getText().toString());
//                    assert connection != null;
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM recipe WHERE recipe_id = '" + recipeName.getText().toString() + "'");
                    ResultSet res = ps.executeQuery();

                    if (res.next()) {
                        System.out.println("have existing recipe with same name");
                        status.set(true);

                    }
                    if (!status.get()) {
                        System.out.println("no recipe with that name");
                        if (recipeName.getText().toString().equals("") || Ingredients.getText().toString().equals("") || Steps.getText().toString().equals("") || calories.getText().toString().equals("") || fat.getText().toString().equals("") || carbohydrates.getText().toString().equals("") || protein.getText().toString().equals("") || Ingredients.getText().toString().equals("") || Steps.getText().toString().equals("")) {
                            status1.set(true);
                            System.out.println("Empty fields detected");
                        }
                        System.out.println(status1);
                        if (!status1.get()) {
                            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO recipe(recipe_id, recipe_ingredient, recipe_instruction, recipe_calorie, recipe_carbohydrate, recipe_protein, recipe_fat) VALUES('"
                                    + recipeName.getText().toString() + "','"
                                    + Ingredients.getText().toString() + "','"
                                    + Steps.getText().toString() + "','"
                                    + calories.getText().toString() + "','"
                                    + carbohydrates.getText().toString() + "','"
                                    + protein.getText().toString() + "','"
                                    + fat.getText().toString() + "')");
                            ps1.executeUpdate();
                            ps1.close();
                            System.out.println("data pushed`");
                        }
                    }

                    res.close();
                    ps.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dataThread.start();
            while (dataThread.isAlive()){

            }

            if (status.get()) {
                Toast.makeText(getContext(), "The recipe name has already been used", Toast.LENGTH_SHORT).show();

            }
            if (status1.get()) {
                Toast.makeText(getContext(), "Please fill all the section", Toast.LENGTH_SHORT).show();

            }
            if (!status.get() && !status1.get()) {
                System.out.println(status.get().toString() + status1.get().toString());
                Toast.makeText(getContext(), "The recipe has been uploaded", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.adminHomePage, bundle);

            }
        };
        BtnAddRecipe.setOnClickListener(OCLAddRecipe);


        Button btnBackToAdminHP = view.findViewById(R.id.returnToAdminHomePageButton);
        View.OnClickListener OCLBackAdminHP = v -> Navigation.findNavController(view).navigate(R.id.adminHomePage, bundle);
        btnBackToAdminHP.setOnClickListener(OCLBackAdminHP);
    }
}