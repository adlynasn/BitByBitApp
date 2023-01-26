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

public class registerPage extends Fragment {


    public registerPage() {
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
        return inflater.inflate(R.layout.fragment_register_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO link to db
        super.onViewCreated(view, savedInstanceState);

        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);
        EditText email = view.findViewById(R.id.email);
        EditText emailConf = view.findViewById(R.id.emailconfirmation);
        Button BtnReg = view.findViewById(R.id.registerButton);
        View.OnClickListener OCLRegBtn = view1 -> {
            System.out.println("Entered on click");

            AtomicReference<Boolean> status = new AtomicReference<>(true);
            AtomicReference<Boolean> status1 = new AtomicReference<>(true);
            AtomicReference<Boolean> status2 = new AtomicReference<>(false);
            AtomicReference<Boolean> status3 = new AtomicReference<>(false);
            AtomicReference<Boolean> status4 = new AtomicReference<>(false);
            Thread dataThread = new Thread(() -> {
                try {
                    System.out.println("Entered");
                    Connection connection = Line.getConnection();
                    assert connection != null;
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM user where user_id = '" + username.getText().toString() + "'");
                    PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM user where email  = '" + email.getText().toString() + "'");
                    PreparedStatement ps2 = connection.prepareStatement("INSERT INTO user(user_id, password, email, status) VALUES('" + username.getText().toString() + "','" + password.getText().toString() + "','" + email.getText().toString() + "',1)");
                    ResultSet res = ps.executeQuery();

                    if (!res.next()) {
                        System.out.println("1");
                        status.set(false);
                    }
                    res.close();

                    ResultSet res1 = ps1.executeQuery();

                    if (!res1.next()) {
                        System.out.println("2");
                        status1.set(false);
                    }
                    res1.close();

                    if (!status.get() && !status1.get()) {
                        if (username.getText().toString().equals("") || email.getText().toString().equals("") || email.getText().toString().equals("") || emailConf.getText().toString().equals("")) {
                            status2.set(true);

                        }else if (!email.getText().toString().contains("@gmail.com")) {
                            status3.set(true);
                        } else if (!emailConf.getText().toString().equals(email.getText().toString())) {
                            status4.set(true);
                        } else {
                            System.out.println("Entered to update");
                            ps2.executeUpdate();

                        }
                    }
                    connection.close();
                    ps.close();
                    ps1.close();
                    ps2.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            });
            dataThread.start();
            while (dataThread.isAlive()) {

            }

            if (status.get()) {
                Toast.makeText(getContext(), "Username has been used. Please enter another username.", Toast.LENGTH_SHORT).show();

            } else if (status1.get()) {
                Toast.makeText(getContext(), "Email has been used. Please enter another email.", Toast.LENGTH_SHORT).show();

            } else if (status2.get()) {
                Toast.makeText(getContext(), "Please fill all the section", Toast.LENGTH_SHORT).show();

            } else if (status3.get()) {
                Toast.makeText(getContext(), "Please enter appropriate email", Toast.LENGTH_SHORT).show();

            } else if (status4.get()) {
                Toast.makeText(getContext(), "Please fill email confirmation same with email", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), "You have register.", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view1).navigate(R.id.loginPage);
            }

        };
        BtnReg.setOnClickListener(OCLRegBtn);

        Button BtnCancelRegister = view.findViewById(R.id.returnToLoginPageButton);
        View.OnClickListener OCLCancel = v -> Navigation.findNavController(view).navigate(R.id.loginPage);
        BtnCancelRegister.setOnClickListener(OCLCancel);

    }
}