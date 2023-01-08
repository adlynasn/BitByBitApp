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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editGoalProgressPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editGoalProgressPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public editGoalProgressPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editGoalProgressPage.
     */
    // TODO: Rename and change types and number of parameters
    public static editGoalProgressPage newInstance(String param1, String param2) {
        editGoalProgressPage fragment = new editGoalProgressPage();
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
        return inflater.inflate(R.layout.fragment_edit_goal_progress_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnBackToProf = view.findViewById(R.id.backToLoginPageButton);
        View.OnClickListener OCLBackProfile = v -> Navigation.findNavController(view).navigate(R.id.action_editGoalProgressPage_to_profilePage);
        btnBackToProf.setOnClickListener(OCLBackProfile);

        Button btnCancelToProf = view.findViewById(R.id.cancelToProfilePageButton);
        View.OnClickListener OCLCancelProfile = v -> Navigation.findNavController(view).navigate(R.id.profilePage);
        btnCancelToProf.setOnClickListener(OCLCancelProfile);

        Button btnUpdateGoal = view.findViewById(R.id.updateProgressButton);
        View.OnClickListener OCLProgress = v -> {
            Toast.makeText(getContext(), "Update Progress Successful!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.profilePage);
        };
        btnUpdateGoal.setOnClickListener(OCLProgress);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
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
            }
        });

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton2);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage);
        floatButton.setOnClickListener(OCLFloatButton);

    }
}

/*
TODO send weight data into database
 */