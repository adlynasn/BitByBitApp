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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link adminUploadRecipePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class adminUploadRecipePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public adminUploadRecipePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment adminUploadRecipePage.
     */
    // TODO: Rename and change types and number of parameters
    public static adminUploadRecipePage newInstance(String param1, String param2) {
        adminUploadRecipePage fragment = new adminUploadRecipePage();
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
        return inflater.inflate(R.layout.fragment_admin_upload_recipe_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name = bundle.getString("username");
        bundle.putString("username", name);

        Button BtnAddRecipe = view.findViewById(R.id.addRecipeButton);
        View.OnClickListener OCLAddRecipe = v -> {

            EditText recipeName = view.findViewById(R.id.recipeName);
            EditText calories = view.findViewById(R.id.calories);
            EditText carbohydrates = view.findViewById(R.id.recipeCarbohydrate);
            EditText fat = view.findViewById(R.id.recipeFat);
            EditText protein = view.findViewById(R.id.recipeProtein);
            EditText Ingredients = view.findViewById(R.id.recipeIngredient);
            EditText Steps = view.findViewById(R.id.recipeSteps);

            AtomicReference<Boolean> status = new AtomicReference<>(false);
            AtomicReference<Boolean> status1 = new AtomicReference<>(false);

            Thread dataThread = new Thread(() -> {

                try {
                    Connection connection = Line.getConnection();
                    System.out.println(recipeName.getText().toString());
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM recipe WHERE recipe_id = '" + recipeName.getText().toString() + "'");
                    PreparedStatement ps1 = connection.prepareStatement("INSERT INTO recipe(recipe_id, recipe_ingredient, recipe_instruction, recipe_calorie, recipe_carbohydrate, recipe_protein, recipe_fat) VALUES('" + recipeName.getText().toString() + "','" + Ingredients.getText().toString() + "','" + Steps.getText().toString() + "','" + calories.getText().toString() + "','" + carbohydrates.getText().toString() + "','" + protein.getText().toString() + "','" + fat.getText().toString() + "')");
                    ResultSet res = ps.executeQuery();

                    if (res.next()) {
                        status.set(true);

                    }
                    if (!status.get()) {
                        if (recipeName.getText().toString().equals("") || Ingredients.getText().toString().equals("") || Steps.getText().toString().equals("") || calories.getText().toString().equals("") || fat.getText().toString().equals("") || carbohydrates.getText().toString().equals("") || protein.getText().toString().equals("")) {
                            status1.set(true);

                        } else {
                            ps1.executeUpdate();
                        }
                    }

                    res.close();
                    ps.close();
                    ps1.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dataThread.start();
            while (dataThread.isAlive()) {

            }
            if (status.get()) {
                Toast.makeText(getContext(), "The recipe or recipe name has already been used or uploaded ", Toast.LENGTH_SHORT).show();

            } else if (status1.get()) {
                Toast.makeText(getContext(), "Please fill all the section", Toast.LENGTH_SHORT).show();

            } else {
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