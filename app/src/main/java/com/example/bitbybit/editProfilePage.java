package com.example.bitbybit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editProfilePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editProfilePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public editProfilePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editProfilePage.
     */
    // TODO: Rename and change types and number of parameters
    public static editProfilePage newInstance(String param1, String param2) {
        editProfilePage fragment = new editProfilePage();
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
        return inflater.inflate(R.layout.fragment_edit_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        String name=bundle.getString("username");
        bundle.putString("username", name);

        Button btnCancelToProf = view.findViewById(R.id.CancelToProfilePage);
        View.OnClickListener OCLCancelProfile = v -> Navigation.findNavController(view).navigate(R.id.profilePage);
        btnCancelToProf.setOnClickListener(OCLCancelProfile);


        Button BtnChangePass = view.findViewById(R.id.ChangePassBut);
        View.OnClickListener OCLCngPAss = v -> {
            Navigation.findNavController(view).navigate(R.id.changePasswordPage);
        };
        BtnChangePass.setOnClickListener(OCLCngPAss);


        EditText username = view.findViewById(R.id.username);
        EditText biodata = view.findViewById(R.id.userBio);

        Button btnUpdateProf = view.findViewById(R.id.updateProfileButton);
        View.OnClickListener OCLUpdate = v -> {

            AtomicReference<Boolean> status = new AtomicReference<>();
            AtomicReference<Boolean> status1 = new AtomicReference<>(false);

            try {
                Connection connection = Line.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM user where user_id = '" + username.getText().toString() + "'");
//                PreparedStatement ps1 = connection.prepareStatement("UPDATE user SET user_id = '" + username.getText().toString() + "' WHERE user = '" + this.username + "'");
//                PreparedStatement ps2 = connection.prepareStatement("UPDATE user SET profile_bio = '" + biodata.getText().toString() + "' WHERE user = '" + this.username + "'");
                ResultSet res = ps.executeQuery();

                if (!res.next()) {
                    status.set(false);
//                    ps1.executeUpdate();
//                    ps2.executeUpdate();

                } else {
                    status.set(true);
                }
                res.close();
                ps.close();
//                ps1.close();
//                ps2.close();
                connection.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (status.get()) {
                Toast.makeText(getContext(), "username has already been used. Please enter another username", Toast.LENGTH_SHORT).show();


            } else {
                Navigation.findNavController(view).navigate(R.id.profilePage);

            }

        };
        btnUpdateProf.setOnClickListener(OCLUpdate);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Navigation.findNavController(view).navigate(R.id.homePage);
                    return true;
                case R.id.savedRecipes:
                    Navigation.findNavController(view).navigate(R.id.savedRecipesPage);
                    return true;
                case R.id.missions:
                    Navigation.findNavController(view).navigate(R.id.missionsPage);
                    return true;
                case R.id.profile:
                    Navigation.findNavController(view).navigate(R.id.profilePage);
                    return true;
            }
            return false;
        });

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton2);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage);
        floatButton.setOnClickListener(OCLFloatButton);

    }
}

/*
TODO send updated data to database
 */