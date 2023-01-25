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

public class forgotPasswordPage extends Fragment {

    public forgotPasswordPage() {
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
        return inflater.inflate(R.layout.fragment_forgot_password_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button BtnCancel = view.findViewById(R.id.returnToLoginPageButtonPageButton);
        View.OnClickListener OCLChgPass = v -> Navigation.findNavController(view).navigate(R.id.loginPage);
        BtnCancel.setOnClickListener(OCLChgPass);

        EditText password = view.findViewById(R.id.newPassword);
        EditText RePassword = view.findViewById(R.id.reEnteredPassword);
        EditText email = view.findViewById(R.id.email);
        Button BtnResetPass = view.findViewById(R.id.resetPasswordButton);
        View.OnClickListener OCLReset = view1 -> {

            AtomicReference<Boolean> status = new AtomicReference<>();
            AtomicReference<Boolean> status1 = new AtomicReference<>(false);
            AtomicReference<Boolean> status2 = new AtomicReference<>(false);
            Thread dataThread = new Thread(() -> {

                try {
                    Connection connection = Line.getConnection();
                    assert connection != null;
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM user where email = '" + email.getText().toString() + "'");
                    PreparedStatement ps1 = connection.prepareStatement("UPDATE user SET password = '" + password.getText().toString()+ "' where email = '" + email.getText().toString() + "'");
                    ResultSet res = ps.executeQuery();

                    if (res.next()) {
                        status.set(false);

                        if (password.getText().toString().isEmpty() || RePassword.getText().toString().isEmpty()) {
                            status1.set(true);

                        }else if (!password.getText().toString().equals(RePassword.getText().toString())){
                            status2.set(true);

                        }else {
                            ps1.executeUpdate();

                        }

                    } else {
                        status.set(true);
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
                Toast.makeText(getContext(), "WRONG EMAIL!", Toast.LENGTH_SHORT).show();

            }else if(status1.get()){
                Toast.makeText(getContext(), "Please fill both Password and re-Entered Password section", Toast.LENGTH_SHORT).show();

            }else if(status2.get()){
                Toast.makeText(getContext(), "Please make sure that password and reEntered password is same", Toast.LENGTH_SHORT).show();

            }
            else {
                Navigation.findNavController(view1).navigate(R.id.loginPage);
                Toast.makeText(getContext(), "PASSWORD HAS BEEN SUCCESSFULLY CHANGED!!", Toast.LENGTH_SHORT).show();
            }
        };
        BtnResetPass.setOnClickListener(OCLReset);
    }

}

