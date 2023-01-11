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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link foodDetailsPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class foodDetailsPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public foodDetailsPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment foodDetailsPage.
     */
    // TODO: Rename and change types and number of parameters
    public static foodDetailsPage newInstance(String param1, String param2) {
        foodDetailsPage fragment = new foodDetailsPage();
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
                    calories.setText(String.valueOf(res.getDouble(6)));
                    carbohydrates.setText(String.valueOf(res.getDouble(7)));
                    protein.setText(String.valueOf(res.getDouble(8)));
                    fat.setText(String.valueOf(res.getDouble(9)));

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

//        bundle.putString("foodbundle", foodTitle.getText().toString());

        Button btnBackFoodDetails = view.findViewById(R.id.returnToHomePageButton);
        View.OnClickListener OCLBackHP = v -> Navigation.findNavController(view).navigate(R.id.homePage, bundle);
        btnBackFoodDetails.setOnClickListener(OCLBackHP);

        Button btnPrepareIng = view.findViewById(R.id.frenchToastIngredientsPageButton);
        View.OnClickListener OCLListIngredient = v -> Navigation.findNavController(view).navigate(R.id.foodIngredientPage, bundle);
        btnPrepareIng.setOnClickListener(OCLListIngredient);

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