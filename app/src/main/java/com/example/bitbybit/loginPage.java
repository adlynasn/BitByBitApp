package com.example.bitbybit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link onboardingPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class loginPage extends Fragment {



    Button buttonLogin, buttonRegister, buttonChgPass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Button BtnLogin = view.findViewById(R.id.LoginButton);
        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);
        View.OnClickListener OVLLogin = v -> {

            //bruh
            //connect to database
            AtomicReference<Boolean> status = new AtomicReference<>();
            AtomicReference<Boolean> status2 = new AtomicReference<>();
            Thread dataThread = new Thread(() -> {
                try{
                    Connection connection = Line.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id FROM user WHERE user_id = '" + username.getText().toString().trim() + "' AND password = '" + password.getText().toString().trim() + "' AND status = 1");
                    ResultSet res = preparedStatement.executeQuery();
                    PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT user_id FROM user WHERE user_id = '" + username.getText().toString().trim() + "' AND password = '" + password.getText().toString().trim() + "' AND status = 0");
                    ResultSet res2 = preparedStatement2.executeQuery();

                    if(res.next()){
                        status.set(true);
                        status2.set(false);
                    }
                    else if (res2.next()){
                        status.set(false);
                        status2.set(true);
                    }
                    else{
                        status.set(false);
                        status2.set(false);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dataThread.start();
            while(dataThread.isAlive()){

            }

            Bundle bundle = new Bundle();
            bundle.putString("username", username.getText().toString());
            ListFragment newFragment =  new ListFragment();
            newFragment.setArguments(bundle);


            if (status.get()) {
                Toast.makeText(getContext(), "SUCCESSFULLY LOGIN", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.homePage, bundle);
            }
            else if (status2.get()){
                Toast.makeText(getContext(), "SUCCESSFULLY LOGIN", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.adminHomePage, bundle);
            }
            else {
                Toast.makeText(getContext(), "LOGIN FAILED!!PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show();
            }
        };
                BtnLogin.setOnClickListener(OVLLogin);

                Button BtnReg = view.findViewById(R.id.registerPageButton);
                View.OnClickListener OVLRegister = v -> Navigation.findNavController(view).navigate(R.id.registerPage);
                BtnReg.setOnClickListener(OVLRegister);

                Button BtnChgPass = view.findViewById(R.id.forgotPasswordPageButton);
                View.OnClickListener OVLChPass = v -> Navigation.findNavController(view).navigate(R.id.forgotPasswordPage);
                BtnChgPass.setOnClickListener(OVLChPass);

            }
        }
