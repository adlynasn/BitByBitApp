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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class customerSupportPage extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_support_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name=bundle.getString("username");
        bundle.putString("username", name);

        Button BtnCancelNew = view.findViewById(R.id.cancelReportButton);
        View.OnClickListener OCLCancel = v -> Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
        BtnCancelNew.setOnClickListener(OCLCancel);

        EditText report = view.findViewById(R.id.problemDescription);
        Button BtnSubmit = view.findViewById(R.id.submitReportButton);
        View.OnClickListener OCLSubmit = v -> {

            AtomicReference<Boolean> status = new AtomicReference<>(false);
            Thread dataThread = new Thread(() -> {

                try {
                    Connection connection = Line.getConnection();
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO report(report_description, user_id) VALUES('" + report.getText().toString() + "','" + name + "')");

                    if (report.getText().toString().equals("")){
                        status.set(true);

                    }else {
                        ps.executeUpdate();
                        status.set(false);
                    }

                    ps.close();
                    connection.close();

                }catch (SQLException e){
                    e.printStackTrace();
                }
            });
            dataThread.start();
            while (dataThread.isAlive()){

            }

            if (status.get()){
                Toast.makeText(getContext(), "Please fill the report description section", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getContext(), "Report submitted", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
            }
        };
        BtnSubmit.setOnClickListener(OCLSubmit);

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
TODO when submit button is pressed send the data to the database
 */