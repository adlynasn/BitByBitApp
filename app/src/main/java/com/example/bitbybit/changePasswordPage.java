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
 * Use the {@link changePasswordPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class changePasswordPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public changePasswordPage() {
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
    public static changePasswordPage newInstance(String param1, String param2) {
        changePasswordPage fragment = new changePasswordPage();
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
        return inflater.inflate(R.layout.fragment_change_password_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        String name=bundle.getString("username");
        bundle.putString("username", name);

        Button btnCancel = view.findViewById(R.id.returnToProfilePageButton);
        View.OnClickListener OCLCancel = v -> {
            Navigation.findNavController(view).navigate(R.id.editProfilePage);

        };
        btnCancel.setOnClickListener(OCLCancel);

        EditText previousPass = view.findViewById(R.id.previousPassword);
        EditText NewPass = view.findViewById(R.id.newPassword);
        EditText ReEntersPass = view.findViewById(R.id.reEnteredPassword);


        Button btnChangePass = view.findViewById(R.id.changePasswordBut);
        View.OnClickListener OCLChange = v -> {

            //Connect to database
            AtomicReference<Boolean> status = new AtomicReference<>();
            AtomicReference<Boolean> status1 = new AtomicReference<>(false);
            AtomicReference<Boolean> status2 = new AtomicReference<>(false);
            Thread dataThread = new Thread(() ->{

                try {
                    Connection connection = Line.getConnection();
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE password = '" + previousPass.getText().toString() + "'");
                    PreparedStatement ps1 = connection.prepareStatement("UPDATE user SET password = '" + NewPass.getText().toString() + "' WHERE password = '" + previousPass.getText().toString() + "'");
                    ResultSet res = ps.executeQuery();

                    if (res.next()) {
                        status.set(false);

                        if (NewPass.getText().toString().isEmpty() || ReEntersPass.getText().toString().isEmpty()) {
                            status1.set(true);

                        } else if (!NewPass.getText().toString().equals(ReEntersPass.getText().toString())) {
                            status2.set(true);

                        } else {
                            ps1.execute();

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
            while (dataThread.isAlive()){

            }

            if (status.get()) {
                Toast.makeText(getContext(), "WRONG PASSWORD!", Toast.LENGTH_SHORT).show();

            }else if(status1.get()){
                Toast.makeText(getContext(), "Please fill all section", Toast.LENGTH_SHORT).show();

            }else if(status2.get()){
                Toast.makeText(getContext(), "Please make sure that new password and reEntered password is same", Toast.LENGTH_SHORT).show();

            }
            else {
                Navigation.findNavController(view).navigate(R.id.editProfilePage);
                Toast.makeText(getContext(), "PASSWORD HAS BEEN SUCCESSFULLY CHANGED!!", Toast.LENGTH_SHORT).show();
            }


        };
        btnChangePass.setOnClickListener(OCLChange);
    }
}