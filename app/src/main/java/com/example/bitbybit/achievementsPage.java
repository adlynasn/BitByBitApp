package com.example.bitbybit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link achievementsPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class achievementsPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public achievementsPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment achievementsPage.
     */
    // TODO: Rename and change types and number of parameters
    public static achievementsPage newInstance(String param1, String param2) {
        achievementsPage fragment = new achievementsPage();
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
        return inflater.inflate(R.layout.fragment_achievements_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name=bundle.getString("username");
        bundle.putString("username", name);



        Thread dataThread = new Thread(() -> {
        int exercise = 0;
        int self_of_love = 0;
        int cooking = 0;
        TextView exer = view.findViewById(R.id.exerciseAchievementProgress);
        TextView self = view.findViewById(R.id.cookingAchievementProgress);
        TextView cook = view.findViewById(R.id.selfLoveAchievementProgress);

            try {
                Connection connection = Line.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM mission WHERE user_id =  '" + name + "' and mission_id = 0");
                PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM mission WHERE user_id =  '" + name + "' and mission_id = 1");
                PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM mission WHERE user_id =  '" + name + "' and mission_id = 2");
                ResultSet res = ps.executeQuery();
                ResultSet res1 = ps1.executeQuery();
                ResultSet res2 = ps2.executeQuery();

                while(res.next()){
                    exercise++;

                }

                while(res1.next()){
                    self_of_love++;
                }

                while(res2.next()){
                    cooking++;
                }


                exer.setText(Integer.valueOf(exercise).toString());
                self.setText(Integer.valueOf(self_of_love).toString());
                cook.setText(Integer.valueOf(cooking).toString());

            }catch (SQLException e){
                e.printStackTrace();
            }

        });
        dataThread.start();
        while (dataThread.isAlive()) {

        }
        Button btnBackToProf = view.findViewById(R.id.backToLoginPageButton);
        View.OnClickListener OCLBackProfile = v -> Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
        btnBackToProf.setOnClickListener(OCLBackProfile);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    Navigation.findNavController(view).navigate(R.id.homePage, bundle);
                    return true;
                case R.id.savedRecipes:
                    Navigation.findNavController(view).navigate(R.id.savedRecipesPage, bundle);
                    return true;
                case R.id.missions:
                    Navigation.findNavController(view).navigate(R.id.missionsPage, bundle);
                    return true;
                case R.id.profile:
                    Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
                    return true;
            }
            return false;
        });

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage);
        floatButton.setOnClickListener(OCLFloatButton);

    }
}

// TODO take data of achievements from database and display