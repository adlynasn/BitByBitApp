package com.example.bitbybit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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


public class editGoalProgressPage extends Fragment {

    public editGoalProgressPage() {
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
        return inflater.inflate(R.layout.fragment_edit_goal_progress_page, container, false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name=bundle.getString("username");
        bundle.putString("username", name);

        Button btnCancelToProf = view.findViewById(R.id.cancelToProfilePageButton);
        View.OnClickListener OCLCancelProfile = v -> Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
        btnCancelToProf.setOnClickListener(OCLCancelProfile);

        Button btnUpdateGoal = view.findViewById(R.id.updateProgressButton);
        View.OnClickListener OCLProgress = v -> {

            //create variables
            EditText currentWeight = view.findViewById(R.id.currentWeight);
            EditText currentHeight = view.findViewById(R.id.currentHeight);
            double newWeight = Double.parseDouble(currentWeight.getText().toString());
            double newHeight = Double.parseDouble(currentHeight.getText().toString());
            Double BMI = newWeight/(Math.pow((newHeight/100.00),2));
            @SuppressLint("DefaultLocale") double formattedBMI = Double.parseDouble(String.format("%.2f", BMI));


            //connect to database
            Thread dataThread = new Thread(() -> {
                try{
                    Connection connection = Line.getConnection();
                    assert connection != null;
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM progress WHERE user_id = '" + name.trim() + "'");
                    ResultSet res = preparedStatement.executeQuery();
                    PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE progress SET weight = "
                            + newWeight
                            + ", height = " + newHeight
                            + ", body_mass_index = " + formattedBMI);
                    PreparedStatement preparedStatement3 = connection.prepareStatement("INSERT INTO progress (user_id, weight, height, body_mass_index) VALUES ('"
                            + name + "',"
                            + newWeight + ","
                            + newHeight + ","
                            + formattedBMI + ")");

                    //if have past values
                    if (res.next()){
                        System.out.println("there is previous data");
                        //update to new values
                        preparedStatement2.executeUpdate();

                    }

                    //if no past values
                    else {
                        System.out.println("there is no past data");
                        //insert values

                        preparedStatement3.executeUpdate();

                    }
                    preparedStatement2.close();
                    preparedStatement3.close();

                    res.close();
                    preparedStatement.close();
                    connection.close();

                    requireActivity().runOnUiThread(() -> Navigation.findNavController(view).navigate(R.id.profilePage, bundle));

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dataThread.start();

            while(dataThread.isAlive()){

            }
        };
        btnUpdateGoal.setOnClickListener(OCLProgress);


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

/*
TODO send weight data into database
 */