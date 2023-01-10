package com.example.bitbybit;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;


public class missionsPage extends Fragment {

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_missions_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(calendar.getTime());

        Bundle bundle = getArguments();
        String name = bundle.getString("username");
        bundle.putString("username", name);

        Button BtnExMis = view.findViewById(R.id.exerciseMissionCompleteButton);
        View.OnClickListener OCLExMis = v -> {
            System.out.println(name);
            System.out.println(date);
            //add data to db
            AtomicReference<Boolean> status = new AtomicReference<>(false);
            AtomicReference<Boolean> status1 = new AtomicReference<>(false);
            Thread dataThread = new Thread(() -> {
                try {
                    System.out.println("RUNNN");
                    Connection connection = Line.getConnection();
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO mission(user_id, mission_id, date) VALUES('" + name + "', 0, '" + date + "')");
                    PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM mission WHERE user_id = '" + name + "' AND date = '" + date + "'");
                    ResultSet res = ps1.executeQuery();

                    if (!res.next()) {
                        ps.executeUpdate();
                        status.set(true);
                    } else {
                        status1.set(true);

                    }

                    ps.close();
                    ps1.close();
                    res.close();
                    connection.close();


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dataThread.start();
            while (dataThread.isAlive()) {

            }
            if (status.get()) {
                Toast.makeText(getContext(), "Well Done", Toast.LENGTH_SHORT).show();

            }
            if (status1.get()) {
                Toast.makeText(getContext(), "You can only click the button once per day", Toast.LENGTH_SHORT).show();

            }
        };
        BtnExMis.setOnClickListener(OCLExMis);

        Button BtnCook = view.findViewById(R.id.cookingMissionCompleteButton);
        View.OnClickListener OCLCook = v -> {
            System.out.println(name);
            //add data to db
            AtomicReference<Boolean> status = new AtomicReference<>(false);
            AtomicReference<Boolean> status1 = new AtomicReference<>(false);
            Thread dataThread = new Thread(() -> {
                try {
                    Connection connection = Line.getConnection();
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO mission(user_id, mission_id, date) VALUES('" + name + "', 2, '" + date + "')");
                    PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM mission WHERE user_id = '" + name + "' AND date = '" + date + "'");
                    ResultSet res = ps1.executeQuery();

                    if (!res.next()) {
                        ps.executeUpdate();
                        status.set(true);
                    } else {
                        status1.set(true);

                    }

                    ps.close();
                    ps1.close();
                    res.close();
                    connection.close();


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dataThread.start();
            while (dataThread.isAlive()) {

            }

            if (status.get()) {
                Toast.makeText(getContext(), "Well Done", Toast.LENGTH_SHORT).show();

            }
            if (status1.get()) {
                Toast.makeText(getContext(), "You can only click the button once per day", Toast.LENGTH_SHORT).show();

            }
        };
        BtnCook.setOnClickListener(OCLCook);

        Button BtnSelfLove = view.findViewById(R.id.selfLoveMissionCompleteButton);
        View.OnClickListener OCLSelfLove = v -> {
            System.out.println(name);
            //add data to db
            AtomicReference<Boolean> status = new AtomicReference<>(false);
            AtomicReference<Boolean> status1 = new AtomicReference<>(false);
            Thread dataThread = new Thread(() -> {
                try {
                    Connection connection = Line.getConnection();
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO mission(user_id, mission_id, date) VALUES('" + name + "', 1, '" + date + "')");
                    PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM mission WHERE user_id = '" + name + "' AND date = '" + date + "'");
                    ResultSet res = ps1.executeQuery();

                    if (!res.next()) {
                        ps.executeUpdate();
                        status.set(true);
                    } else {
                        status1.set(true);

                    }


                    ps.close();
                    ps1.close();
                    res.close();
                    connection.close();


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dataThread.start();
            while (dataThread.isAlive()) {

            }
            if (status.get()) {
                Toast.makeText(getContext(), "Well Done", Toast.LENGTH_SHORT).show();

            }
            if (status1.get()) {
                Toast.makeText(getContext(), "You can only click the button once per day", Toast.LENGTH_SHORT).show();

            }
        };
        BtnSelfLove.setOnClickListener(OCLSelfLove);

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
- button pressed will send data to database
    - so that can present how much has been done this week
 */