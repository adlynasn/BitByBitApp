package com.example.bitbybit;

import android.content.ContentResolver;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;



@SuppressWarnings("deprecation")
public class homePage extends Fragment {

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

        AtomicInteger i = new AtomicInteger(1);
        ImageView LatestRecipeImage = view.findViewById(R.id.LatestRecipeImage);
//        ImageView RecipeImage1 = view.findViewById(R.id.recipe1);
//        ImageView RecipeImage2 = view.findViewById(R.id.recipe2);
        TextView latestRecipeName = view.findViewById(R.id.latestRecipeName);
//        TextView recipeName1 = view.findViewById(R.id.recipeName1);
//        TextView recipeName2 = view.findViewById(R.id.recipeName2);
        TextView feedTitle = view.findViewById(R.id.postTitle);
        TextView feedDescription = view.findViewById(R.id.postDescription);


        Thread dataThread = new Thread(() -> {

            try {
                //get info from db
                System.out.println("Trying to access recipe db");
                Connection connection = Line.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT recipe_id FROM recipe ORDER BY recipe_id DESC LIMIT 1");
                ResultSet resultSet = ps.executeQuery();
                PreparedStatement ps1 = connection.prepareStatement("SELECT recipe_id FROM recipe LIMIT 2");
                ResultSet resultSet1 = ps1.executeQuery();
                PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM post ORDER BY post_id DESC LIMIT 1");
                ResultSet resultSet2 = ps2.executeQuery();

                if (resultSet.next()) {
                    System.out.println("Recipe db accessed");
                    //set name of latest recipe
                    latestRecipeName.setText(resultSet.getString(1));
//                    Blob blob = resultSet.getBlob(1);
//                    byte[] data = blob.getBytes(1, (int) blob.length());
//                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
//                    Drawable drawable = new BitmapDrawable(byteArrayInputStream);
//                    requireActivity().runOnUiThread(() -> recipeImage.setImageDrawable(drawable));
                }
                resultSet.close();
                ps.close();

                //set name of the 2 recipes
//                while (resultSet1.next()) {
//                    System.out.println(i.get());
//                    System.out.println("Recipe db accessed again");
//                    if (i.get() == 1)
//                        recipeName1.setText(resultSet1.getString(1));
//                    else if (i.get() == 2)
//                        recipeName2.setText(resultSet1.getString(1));
//                    i.getAndIncrement();
//                }

                resultSet1.close();
                ps1.close();

                if (resultSet2.next()) {
                    System.out.println("Post db accessed");
                    feedTitle.setText(resultSet2.getString(2));
                    feedDescription.setText(resultSet2.getString(3));
                }

                resultSet2.close();
                ps2.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        dataThread.start();
        while (dataThread.isAlive()) {

        }


        Button BtnAllRecipe = view.findViewById(R.id.buttonAllReceipe);
        View.OnClickListener OCLAllRecipe = v -> Navigation.findNavController(view).navigate(R.id.action_homePage_to_allRecipesPage, bundle);
        BtnAllRecipe.setOnClickListener(OCLAllRecipe);

        Button btnFeed = view.findViewById(R.id.buttonFeed);
        View.OnClickListener OCLFeed = v -> Navigation.findNavController(view).navigate(R.id.socialFeedPage, bundle);
        btnFeed.setOnClickListener(OCLFeed);

        LinearLayout feed = view.findViewById(R.id.textFeedLayout);
        View.OnClickListener OCLfeed = v -> Navigation.findNavController(view).navigate(R.id.socialFeedPage, bundle);
        feed.setOnClickListener(OCLfeed);

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
                    System.out.println(name);
                    Navigation.findNavController(view).navigate(R.id.profilePage, bundle);
                    return true;
            }
            return false;
        });

        FloatingActionButton floatButton = view.findViewById(R.id.floatingActionButton2);
        View.OnClickListener OCLFloatButton = v -> Navigation.findNavController(view).navigate(R.id.calorieCounterPage, bundle);
        floatButton.setOnClickListener(OCLFloatButton);


        //Latest recipe pic
        View.OnClickListener OCLLatestRecipeImage = v -> {
            AtomicReference<String> recipe_id = new AtomicReference<>();
            Thread dataThread2 = new Thread(() -> {

                try {
                    //get info from db
                    System.out.println("Trying to access recipe db");
                    Connection connection = Line.getConnection();
                    PreparedStatement ps = connection.prepareStatement("SELECT recipe_id FROM recipe ORDER BY recipe_id DESC LIMIT 1");
                    ResultSet resultSet = ps.executeQuery();

                    if (resultSet.next()) {
                        System.out.println("Recipe db accessed");
                        //get recipe_id

                        recipe_id.set(resultSet.getString(1));
                        String foodname = recipe_id.get();
                        bundle.putString("foodbundle", foodname);

                    }
                    resultSet.close();
                    ps.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dataThread2.start();
            while (dataThread2.isAlive()) {

            }
            // TODO Carry over recipe_id
            Navigation.findNavController(view).navigate(R.id.allRecipesPage, bundle);
        };
        LatestRecipeImage.setOnClickListener(OCLLatestRecipeImage);


        //Recipe 1 pic
//        View.OnClickListener OCLRecipeImage1 = v -> {
//            AtomicReference<String> recipe_id = new AtomicReference<>();
//            Thread dataThread3 = new Thread(() -> {
//
//                try {
//                    //get info from db
//                    System.out.println("Trying to access recipe db");
//                    Connection connection = Line.getConnection();
//                    PreparedStatement ps1 = connection.prepareStatement("SELECT recipe_id FROM recipe LIMIT 2");
//                    ResultSet resultSet1 = ps1.executeQuery();
//                    i.set(1);
//                    //set name of the 2 recipes
//                    while (resultSet1.next()) {
//                        System.out.println("Recipe db accessed again");
//                        //get recipe_id
//                        if (i.get() == 1) {
//                            recipe_id.set(resultSet1.getString(1));
//                            String foodname = recipe_id.get();
//                            bundle.putString("foodbundle", foodname);
//
//                        }
//                        i.getAndIncrement();
//                    }
//
//                    resultSet1.close();
//                    ps1.close();
//                    connection.close();
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            });
//            dataThread3.start();
//            while (dataThread3.isAlive()) {
//
//            }
//            //TODO Carry over recipe_id
//            Navigation.findNavController(view).navigate(R.id.foodDetailsPage, bundle);
//        };
//        RecipeImage1.setOnClickListener(OCLRecipeImage1);
//
//        //Recipe 2 pic
//        View.OnClickListener OCLRecipeImage2 = v -> {
//            AtomicReference<String> recipe_id = new AtomicReference<>();
//            Thread dataThread3 = new Thread(() -> {
//
//                try {
//                    //get info from db
//                    System.out.println("Trying to access recipe db");
//                    Connection connection = Line.getConnection();
//                    PreparedStatement ps1 = connection.prepareStatement("SELECT recipe_id FROM recipe LIMIT 2");
//                    ResultSet resultSet1 = ps1.executeQuery();
//                    i.set(1);
//                    //set name of the 2 recipes
//                    while (resultSet1.next()) {
//                        System.out.println("Recipe db accessed again");
//                        //get recipe_id
//                        if (i.get() == 2) {
//                            recipe_id.set(resultSet1.getString(1));
//                            String foodname = recipe_id.get();
//                            bundle.putString("foodbundle", foodname);
//
//                        }
//                        i.getAndIncrement();
//                    }
//
//                    resultSet1.close();
//                    ps1.close();
//                    connection.close();
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            });
//            dataThread3.start();
//            while (dataThread3.isAlive()) {
//
//            }
//            //TODO Carry over recipe_id
//            Navigation.findNavController(view).navigate(R.id.foodDetailsPage, bundle);
//        };
//        RecipeImage2.setOnClickListener(OCLRecipeImage2);
    }

    // Returns a Drawable object. Requires recipe_id
    public static Drawable getPicFromDB(String recipe_id, ContentResolver contentResolver, Thread dataThread) {
        AtomicReference<Drawable> atomicReference = new AtomicReference<>();
        dataThread = new Thread(() -> {
            try (
                    Connection connection = Line.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT recipe_picture FROM recipe WHERE recipe_id = ?");
            ) {
                preparedStatement.setString(1, recipe_id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Blob blob = resultSet.getBlob(1);
                    byte[] data = blob.getBytes(1, (int) blob.length());
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
                    Drawable drawable = new BitmapDrawable(inputStream);
                    atomicReference.set(drawable);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        Drawable toReturn = atomicReference.get();
        return toReturn;
    }
}