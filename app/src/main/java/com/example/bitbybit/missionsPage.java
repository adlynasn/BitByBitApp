package com.example.bitbybit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link missionsPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class missionsPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public missionsPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment missionsPage.
     */
    // TODO: Rename and change types and number of parameters
    public static missionsPage newInstance(String param1, String param2) {
        missionsPage fragment = new missionsPage();
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
        return inflater.inflate(R.layout.fragment_missions_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        Button BtnExMis = view.findViewById(R.id.exerciseMissionCompleteButton);
        View.OnClickListener OCLExMis = v -> {
            //add data to db
        };
        BtnExMis.setOnClickListener(OCLExMis);

        Button BtnCook = view.findViewById(R.id.cookingMissionCompleteButton);
        View.OnClickListener OCLCook = v -> {
            //add data to db
        };
        BtnCook.setOnClickListener(OCLCook);

        Button BtnSelfLove = view.findViewById(R.id.selfLoveMissionCompleteButton);
        View.OnClickListener OCLSelfLove = v -> {
            //add data to db
        };
        BtnSelfLove.setOnClickListener(OCLSelfLove);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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
        });

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton2);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage);
        floatButton.setOnClickListener(OCLFloatButton);

    }

}

/*
TODO
- button pressed will send data to database
    - so that can present how much has been done this week
 */