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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profilePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profilePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profilePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profilePage.
     */
    // TODO: Rename and change types and number of parameters
    public static profilePage newInstance(String param1, String param2) {
        profilePage fragment = new profilePage();
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
        return inflater.inflate(R.layout.fragment_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button BtnEditProf = view.findViewById(R.id.editProfilePageButton);
        View.OnClickListener OCLEditProf = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.editProfilePage);
            }
        };
        BtnEditProf.setOnClickListener(OCLEditProf);

        Button BtnAchievement = view.findViewById(R.id.gotoAchievementsPageButton);
        View.OnClickListener OCLAcievement = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.achievementsPage);
            }
        };
        BtnAchievement.setOnClickListener(OCLAcievement);

        Button BtnViewCal = view.findViewById(R.id.viewCalorieButton);
        View.OnClickListener OCLViewCal = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.viewCaloriesPage);
            }
        };
        BtnViewCal.setOnClickListener(OCLViewCal);

        Button btnLogout = view.findViewById(R.id.LogoutButton);
        View.OnClickListener OCLLogOut = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.loginPage);
                Toast.makeText(getContext(),"You have log out",Toast.LENGTH_SHORT).show();
            }
        };
        btnLogout.setOnClickListener(OCLLogOut);
    }
}

/*
TODO
- take data from db
    - display profile pic, name, bio, weight
- link to pages
    - edit profile
    - achievement
    - view calories
 */