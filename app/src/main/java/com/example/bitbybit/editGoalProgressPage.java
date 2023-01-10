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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editGoalProgressPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editGoalProgressPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public editGoalProgressPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editGoalProgressPage.
     */
    // TODO: Rename and change types and number of parameters
    public static editGoalProgressPage newInstance(String param1, String param2) {
        editGoalProgressPage fragment = new editGoalProgressPage();
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
        return inflater.inflate(R.layout.fragment_edit_goal_progress_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
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
            Double newWeight = Double.parseDouble(currentWeight.getText().toString());
            Double newHeight = Double.parseDouble(currentHeight.getText().toString());
            Double BMI = newWeight/(Math.pow((newHeight/100.00),2));
            Double formattedBMI = Double.parseDouble(String.format("%.2f", BMI));


            //connect to database
            Thread dataThread = new Thread(() -> {
                try{
                    Connection connection = Line.getConnection();
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

                    requireActivity().runOnUiThread(() -> {
                        Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
                    });

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