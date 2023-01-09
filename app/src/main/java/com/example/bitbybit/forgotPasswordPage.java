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
 * Use the {@link forgotPasswordPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class forgotPasswordPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public forgotPasswordPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment changePasswordPage.
     */
    // TODO: Rename and change types and number of parameters
    public static forgotPasswordPage newInstance(String param1, String param2) {
        forgotPasswordPage fragment = new forgotPasswordPage();
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
        View.OnClickListener OCLReset = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AtomicReference<Boolean> status = new AtomicReference<>();
                AtomicReference<Boolean> status1 = new AtomicReference<>(false);
                AtomicReference<Boolean> status2 = new AtomicReference<>(false);
                Thread dataThread = new Thread(() -> {

                    try {
                        Connection connection = Line.getConnection();
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
                    Navigation.findNavController(view).navigate(R.id.loginPage);
                    Toast.makeText(getContext(), "PASSWORD HAS BEEN SUCCESSFULLY CHANGED!!", Toast.LENGTH_SHORT).show();
                }
            }

        };
        BtnResetPass.setOnClickListener(OCLReset);
    }

}

