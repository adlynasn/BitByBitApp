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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registerPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registerPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public registerPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registerPage.
     */
    // TODO: Rename and change types and number of parameters
    public static registerPage newInstance(String param1, String param2) {
        registerPage fragment = new registerPage();
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
        View.OnClickListener OCLRegBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                        if (status.get() == false && status1.get() == false) {
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
                System.out.println("APAKAH");

                if (status.get()) {
                    Toast.makeText(getContext(), "Username has been used. Please enter another username.", Toast.LENGTH_SHORT).show();

                } else if (status1.get()) {
                    Toast.makeText(getContext(), "Email has been used. Please enter another email.", Toast.LENGTH_SHORT).show();

                } else if (status2.get()) {
                    Toast.makeText(getContext(), "Pleaswe fill all the section", Toast.LENGTH_SHORT).show();

                } else if (status3.get()) {
                    Toast.makeText(getContext(), "Please enter appropriate email", Toast.LENGTH_SHORT).show();

                } else if (status4.get()) {
                    Toast.makeText(getContext(), "Please fill email confirmation same with email", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "You have register.", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.loginPage);
                }


//
//                if(username.getText().toString().equals("")){
//                    Toast.makeText(getContext(), "Please fill the username",Toast.LENGTH_SHORT).show();
//
//                }else if(password.getText().toString().equals("")){
//                    Toast.makeText(getContext(), "Please fill the password",Toast.LENGTH_SHORT).show();
//
//                }else if(email.getText().toString().equals("")){
//                    Toast.makeText(getContext(), "Please fill the email",Toast.LENGTH_SHORT).show();
//
//                }else if(emailConf.getText().toString().equals("")){
//                    Toast.makeText(getContext(), "Please fill the confirmation email",Toast.LENGTH_SHORT).show();
//
//                }else if(!email.getText().toString().contains("@")){
//                    Toast.makeText(getContext(), "Please enter appropriate email",Toast.LENGTH_SHORT).show();
//
//                }else if(!emailConf.getText().toString().equals(email.getText().toString())) {
//                    Toast.makeText(getContext(), "Please fill email confirmation same with email", Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Navigation.findNavController(view).navigate(R.id.loginPage);
//                }
//
            }
        };
        BtnReg.setOnClickListener(OCLRegBtn);

        Button btncancelRegis = view.findViewById(R.id.returnToLoginPageButton);
        View.OnClickListener OCLCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.loginPage);
            }
        };
        btncancelRegis.setOnClickListener(OCLCancel);

    }
}