package com.example.bitbybit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

public class achievementsPage extends Fragment {


    public achievementsPage() {
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
        return inflater.inflate(R.layout.fragment_achievements_page, container, false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name=bundle.getString("username");
        bundle.putString("username", name);



        @SuppressLint("SetTextI18n") Thread dataThread = new Thread(() -> {
        int exercise = 0;
        int self_of_love = 0;
        int cooking = 0;
        TextView exercises = view.findViewById(R.id.exerciseAchievementProgress);
        TextView self = view.findViewById(R.id.cookingAchievementProgress);
        TextView cook = view.findViewById(R.id.selfLoveAchievementProgress);

            try {
                Connection connection = Line.getConnection();
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM mission WHERE user_id =  '" + name + "' and mission_id = 0");
                PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM mission WHERE user_id =  '" + name + "' and mission_id = 1");
                PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM mission WHERE user_id =  '" + name + "' and mission_id = 2");
                ResultSet res = ps.executeQuery();
                ResultSet res1 = ps1.executeQuery();
                ResultSet res2 = ps2.executeQuery();

                while(res.next()){
                    exercise++;

                }

                while(res1.next()){
                    self_of_love++;
                }

                while(res2.next()){
                    cooking++;
                }


                exercises.setText(Integer.valueOf(exercise).toString());
                self.setText(Integer.valueOf(self_of_love).toString());
                cook.setText(Integer.valueOf(cooking).toString());

            }catch (SQLException e){
                e.printStackTrace();
            }

        });
        dataThread.start();
        while (dataThread.isAlive()) {

        }
        Button btnBackToProf = view.findViewById(R.id.backToLoginPageButton);
        View.OnClickListener OCLBackProfile = v -> Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
        btnBackToProf.setOnClickListener(OCLBackProfile);

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

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage);
        floatButton.setOnClickListener(OCLFloatButton);

    }
}

// TODO take data of achievements from database and display