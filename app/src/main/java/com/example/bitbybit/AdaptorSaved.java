package com.example.bitbybit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptorSaved extends RecyclerView.Adapter<AdaptorSaved.MyViewHolder>{

    Context context;
    ArrayList<NewsSaved> newsSavedArrayList;

    public AdaptorSaved(Context context, ArrayList<NewsSaved> newsSavedArrayList){
        this.context = context;
        this.newsSavedArrayList = newsSavedArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardviewsavedrecipes, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NewsSaved newsSaved = newsSavedArrayList.get(position);
        holder.recipeName.setText(newsSaved.RecipeName);
        holder.recipeImage.setImageResource(newsSaved.FoodImage);
        holder.recipeImage.setOnClickListener(view -> {
//                Navigation.findNavController(view).navigate(R.id.foodDetailsPage, bundle);
        });
        holder.delete.setOnClickListener(view -> {

        });

    }

    @Override
    public int getItemCount() {
        return newsSavedArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        ImageView recipeImage;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipeName);
            delete = itemView.findViewById(R.id.ButtonDeleteRecipe);

        }
    }
}
