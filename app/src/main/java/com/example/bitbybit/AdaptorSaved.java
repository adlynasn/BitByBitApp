package com.example.bitbybit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptorSaved extends RecyclerView.Adapter<AdaptorSaved.MyViewHolder> {

    Context context;
    ArrayList<NewsSaved> newsSavedArrayList;
    Bundle bundle;

    public AdaptorSaved(Context context, ArrayList<NewsSaved> newsSavedArrayList, Bundle bundle) {
        this.context = context;
        this.newsSavedArrayList = newsSavedArrayList;
        this.bundle = bundle;

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
        holder.recipeName.setOnClickListener(view -> {
            String foodName = newsSaved.RecipeName;
            bundle.putString("FoodName", foodName);
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.foodDetailsPage, bundle);
        });


    }

    @Override
    public int getItemCount() {
        return newsSavedArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipeName);

        }
    }
}
