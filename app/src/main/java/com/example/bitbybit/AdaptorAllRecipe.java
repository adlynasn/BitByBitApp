package com.example.bitbybit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptorAllRecipe extends RecyclerView.Adapter<AdaptorAllRecipe.MyViewHolder> {

    Context context;
    ArrayList<NewsAll> newsAllArrayList;

    public AdaptorAllRecipe(Context context, ArrayList<NewsAll> newsAllArrayList){
        this.context = context;
        this.newsAllArrayList = newsAllArrayList;
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
        holder.foodImage.setImageResource(newsAll.FoodImage);
        holder.foodImage.setOnClickListener(view -> {
//                Navigation.findNavController(view).navigate(R.id.foodDetailsPage, bundle);

        });

    }


    @Override
    public int getItemCount() {

        return newsAllArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.recipeImage);
            foodName = itemView.findViewById(R.id.recipeName);
        }


    }
}
