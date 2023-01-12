package com.example.bitbybit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptorAllRecipe extends RecyclerView.Adapter<AdaptorAllRecipe.MyViewHolder> {

    Context context;
    ArrayList<NewsAll> newsAllArrayList;
    Bundle bundle;

    public AdaptorAllRecipe(Context context, ArrayList<NewsAll> newsAllArrayList, Bundle bundle) {
        this.context = context;
        this.newsAllArrayList = newsAllArrayList;
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardviewallrecipes, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NewsAll newsAll = newsAllArrayList.get(position);
        holder.foodName.setText(newsAll.RecipeName);
        String name = bundle.getString("username");
        bundle.putString("username", name);
        holder.foodImage.setImageResource(newsAll.FoodImage);
        holder.foodImage.setOnClickListener(view -> {
            String foodName = newsAll.RecipeName.toString();
            bundle.putString("FoodName", foodName);
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.foodDetailsPage, bundle);

        });

    }


    @Override
    public int getItemCount() {

        return newsAllArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage;
        TextView foodName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.recipeImage);
            foodName = itemView.findViewById(R.id.recipeName);
        }


    }
}
