package com.example.bitbybit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homePage extends Fragment {

    RecyclerView recyclerView;
    //UserPlacedApplicationAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homePage.
     */
    // TODO: Rename and change types and number of parameters
    public static homePage newInstance(String param1, String param2) {
        homePage fragment = new homePage();
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
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name = bundle.getString("username");
        bundle.putString("username", name);

        ImageView recipeImage = view.findViewById(R.id.LatestRecipeImage);

        Thread dataThread = new Thread(() -> {

            try{
                Connection connection = Line.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT recipe_picture FROM recipe ORDER BY recipe_pitcure DESC LIMIT 1 ");
                ResultSet res = ps.executeQuery();





                res.close();

            }catch(SQLException e){
                e.printStackTrace();
            }
        });
        dataThread.start();
        while (dataThread.isAlive()){

        }



        Button BtnAllRecipe = view.findViewById(R.id.buttonAllReceipe);
        View.OnClickListener OCLAllRecipe = v -> Navigation.findNavController(view).navigate(R.id.action_homePage_to_allRecipesPage, bundle);
        BtnAllRecipe.setOnClickListener(OCLAllRecipe);

        Button btnFeed = view.findViewById(R.id.buttonFeed);
        View.OnClickListener OCLFeed = v -> Navigation.findNavController(view).navigate(R.id.socialFeedPage, bundle);
        btnFeed.setOnClickListener(OCLFeed);

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
                    System.out.println(name);
                    Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
                    return true;
            }
            return false;
        });

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton2);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage, bundle);
        floatButton.setOnClickListener(OCLFloatButton);

//        ImageView recipeImage = view.findViewById(R.id.LatestRecipeImage);
        View.OnClickListener OCLRecipeImage = v -> Navigation.findNavController(view).navigate(R.id.foodDetailsPage, bundle);
        recipeImage.setOnClickListener(OCLRecipeImage);
    }
}