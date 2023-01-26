package com.example.bitbybit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdaptorAdminRecipe extends RecyclerView.Adapter<AdaptorAdminRecipe.MyViewHolder>{
    Context context;
    ArrayList<NewsAdminRecipe> newsAdminRecipeArrayList;
    Bundle bundle;

    public AdaptorAdminRecipe(Context context, ArrayList<NewsAdminRecipe> newsAdminRecipeArrayList, Bundle bundle ){
        this.context = context;
        this.newsAdminRecipeArrayList = newsAdminRecipeArrayList;
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardviewadminallrecipes, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewsAdminRecipe newsAdminRecipe = newsAdminRecipeArrayList.get(position);
        holder.foodName.setText(newsAdminRecipe.FoodName);
        String name = bundle.getString("username");
        bundle.putString("username", name);
        holder.foodImage.setImageResource(newsAdminRecipe.FoodImage);
        holder.foodDelete.setOnClickListener(view -> {
            System.out.println("dalam thread");
            System.out.println(newsAdminRecipe.FoodName);
            Thread dataThread = new Thread(() -> {
               try {
                   System.out.println("dalam adaptor");
                   Connection connection = Line.getConnection();
                   assert connection != null;
                   PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM favorite_recipe WHERE recipe_id = '" + newsAdminRecipe.FoodName + "'");
                   preparedStatement.executeUpdate();
                   preparedStatement.close();
                   PreparedStatement ps = connection.prepareStatement("DELETE FROM recipe WHERE recipe_id = '" + newsAdminRecipe.FoodName + "'");
                   ps.executeUpdate();

                   ps.close();
                   connection.close();


               }catch (SQLException e){
                   e.printStackTrace();
               }


            });
            dataThread.start();
            while (dataThread.isAlive()){

            }
            Toast.makeText(context, "Food has been deleted", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.adminHomePage, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return newsAdminRecipeArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodName;
        Button foodDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.recipeImage);
            foodName = itemView.findViewById(R.id.recipeName);
            foodDelete = itemView.findViewById(R.id.deleteRecipeButton);
        }
    }
}
