package com.example.bitbybit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class socialFeedPage extends Fragment {


    private ArrayList<NewsFeed> newsFeedArraylist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_feed_page, container, false);
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name = bundle.getString("username");
        bundle.putString("username", name);

        dataInitialize();

        RecyclerView recyclerview = view.findViewById(R.id.RecyclerFeedRecipe);
        System.out.println("kat sini");
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        AdaptorFeed myAdapter = new AdaptorFeed(getContext(), newsFeedArraylist);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        Button btnBackFeedHp = view.findViewById(R.id.backFeedToHomePageButton);
        View.OnClickListener OCLBackFeed = v -> Navigation.findNavController(view).navigate(R.id.homePage, bundle);
        btnBackFeedHp.setOnClickListener(OCLBackFeed);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
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
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage, bundle);
        floatButton.setOnClickListener(OCLFloatButton);

    }

    private void dataInitialize() {
        newsFeedArraylist = new ArrayList<>();
        System.out.println("dalam ni");


        Thread dataThread = new Thread(() -> {

            try {
                System.out.println("dalam connection");
                Connection connection = Line.getConnection();
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM post ");
                ResultSet res = ps.executeQuery();

                while (res.next()) {
                    String feedTitle = res.getString(2);
                    String feedContent = res.getString(3);
                    NewsFeed newsFeed = new NewsFeed(feedTitle, feedContent);
                    newsFeedArraylist.add(newsFeed);

                }
                res.close();
                connection.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        dataThread.start();
        while (dataThread.isAlive()) {

        }
    }
}


/*
TODO
- Display feed
    - Get info from db
- If feed is recipe
    - go to recipe details when clicked
 */