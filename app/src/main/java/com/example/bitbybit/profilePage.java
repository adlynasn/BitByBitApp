package com.example.bitbybit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class profilePage extends Fragment {


    public profilePage() {
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
        return inflater.inflate(R.layout.fragment_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //carry login info
        Bundle bundle = getArguments();
        String name = bundle.getString("username");
        bundle.putString("username", name);


        // create variables to edit text
        TextView username = view.findViewById(R.id.username);
        TextView bio = view.findViewById(R.id.userBio);
        TextView weight = view.findViewById(R.id.userWeight);
        TextView height = view.findViewById(R.id.userHeight);
        TextView BMI = view.findViewById(R.id.userBMI);

        //connect to database
        Thread dataThread = new Thread(() -> {
            try{
                Connection connection = Line.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE user_id = '" + name.trim() + "'");
                ResultSet res = preparedStatement.executeQuery();
                PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM progress WHERE user_id = '" + name.trim() + "'");
                ResultSet res2 = preparedStatement2.executeQuery();

                while (res.next()){
                    System.out.println("have data in userdb");
                    username.setText(res.getString(1));
                    bio.setText(res.getString(6));
                }

                res.close();
                preparedStatement.close();

                if (res2.next()){
                    System.out.println("Have data in progress db");
                    weight.setText(String.valueOf(res2.getDouble(2)));
                    height.setText(String.valueOf(res2.getDouble(3)));
                    BMI.setText(String.valueOf(res2.getDouble(4)));
                }

                else {
                    System.out.println("No data in progress db");
                    weight.setText("");
                    height.setText("");
                    BMI.setText("");
                }

                res2.close();
                preparedStatement2.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        dataThread.start();
        while(dataThread.isAlive()){

        }


        //Edit profile page button
        Button BtnEditProf = view.findViewById(R.id.editProfilePageButton);
        View.OnClickListener OCLEditProf = v -> {
            Navigation.findNavController(view).navigate(R.id.editProfilePage, bundle);
        };
        BtnEditProf.setOnClickListener(OCLEditProf);


        //Achievement button
        Button BtnAchievement = view.findViewById(R.id.gotoAchievementsPageButton);
        View.OnClickListener OCLAchievement = v -> Navigation.findNavController(view).navigate(R.id.achievementsPage, bundle);
        BtnAchievement.setOnClickListener(OCLAchievement);


        //View calories button
        Button BtnViewCal = view.findViewById(R.id.viewCalorieButton);
        View.OnClickListener OCLCalIntake = v -> Navigation.findNavController(view).navigate(R.id.caloriesIntakePage, bundle);
        BtnViewCal.setOnClickListener(OCLCalIntake);

        Button BtnReport = view.findViewById(R.id.reportButton);
        View.OnClickListener OCLReport = v -> {
            System.out.println("report button pressed");
            Navigation.findNavController(view).navigate(R.id.customerSupportPage, bundle);
        };
        BtnReport.setOnClickListener(OCLReport);


        //Logout button
        Button btnLogout = view.findViewById(R.id.LogoutButton);
        View.OnClickListener OCLLogOut = v -> {
            Navigation.findNavController(view).navigate(R.id.loginPage, bundle);
            Toast.makeText(getContext(), "You have log out", Toast.LENGTH_SHORT).show();
        };
        btnLogout.setOnClickListener(OCLLogOut);


        //Update progress button
        Button btnUpdateProgress = view.findViewById(R.id.editProgressButton);
        View.OnClickListener OCLUpdateProgress = v -> Navigation.findNavController(view).navigate(R.id.action_profilePage_to_editGoalProgressPage, bundle);
        btnUpdateProgress.setOnClickListener(OCLUpdateProgress);


        //Bottom nav bar
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
TODO
- take data from db
    - display profile pic, name, bio, weight
- link to pages
    - edit profile
    - achievement
    - view calories
 */