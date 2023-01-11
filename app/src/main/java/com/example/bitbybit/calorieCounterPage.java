package com.example.bitbybit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class calorieCounterPage extends Fragment {


    public calorieCounterPage() {
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
        return inflater.inflate(R.layout.fragment_calorie_counter_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState );

        Bundle bundle = getArguments();
        assert bundle != null;
        String name=bundle.getString("username");
        bundle.putString("username", name);



        Button BtnCancel = view.findViewById(R.id.cancelButton);
        View.OnClickListener OCLCancelBut = v -> Navigation.findNavController(view).navigate(R.id.homePage, bundle);
        BtnCancel.setOnClickListener(OCLCancelBut);


        Button BtnAddMeal = view.findViewById(R.id.addMealsButton);
        View.OnClickListener OCLAddMeal = v -> {

            //Initialise variables
            EditText entryDate = view.findViewById(R.id.entryDate);
            EditText entryTime = view.findViewById(R.id.entryTime);
            EditText recipeName = view.findViewById(R.id.recipeName);
            EditText calories = view.findViewById(R.id.calories);
            EditText recipeCarbohydrate = view.findViewById(R.id.recipeCarbohydrate);
            EditText recipeFat = view.findViewById(R.id.recipeFat);
            EditText recipeProtein = view.findViewById(R.id.recipeProtein);


            AtomicReference<Boolean> status = new AtomicReference<>(false);

            Thread dataThread = new Thread(() -> {

                try {
                    //init variables
                    String DateString = entryDate.getText().toString();
                    String TimeString = entryTime.getText().toString();
                    String recipeString = recipeName.getText().toString();

                    if (DateString.equals("") || TimeString.equals("") || recipeString.equals("") || calories.getText().toString().equals("") || recipeCarbohydrate.getText().toString().equals("") || recipeProtein.getText().toString().equals("") || recipeFat.getText().toString().equals(""))
                        status.set(true);

                    if (status.get() == false){
                        int IntCal = Integer.parseInt(calories.getText().toString().trim());
                        int IntCarb = Integer.parseInt(recipeCarbohydrate.getText().toString().trim());
                        int IntFat = Integer.parseInt(recipeFat.getText().toString().trim());
                        int IntProtein = Integer.parseInt(recipeProtein.getText().toString().trim());
                        System.out.println("Trying to access calorie_nutrition db");
                        Connection connection = Line.getConnection();
                        assert connection != null;
                        PreparedStatement ps = connection.prepareStatement("INSERT INTO calorie_nutrition (entry_date, entry_time, mealCalorie, mealCarbohydrate, mealProtein, mealFat, recipe_id, user_id) VALUES ('"
                                + DateString + "', '"
                                + TimeString + "', "
                                + IntCal + ", "
                                + IntCarb + ", "
                                + IntProtein + ", "
                                + IntFat + ", '"
                                + recipeString + "', '"
                                + name +"')");
                        ps.executeUpdate();
                        ps.close();
                        connection.close();
                        System.out.println("button pressed");
                        requireActivity().runOnUiThread(() -> Navigation.findNavController(view).navigate(R.id.homePage, bundle));
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            });
            dataThread.start();
            while (dataThread.isAlive()) {

            }

            if (status.get()) {
                Toast.makeText(getContext(), "Please fill in all the details!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), "MEAL ADDED", Toast.LENGTH_SHORT).show();

        };
        BtnAddMeal.setOnClickListener(OCLAddMeal);
    }

}
