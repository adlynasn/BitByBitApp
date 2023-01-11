package com.example.bitbybit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptorIntake extends RecyclerView.Adapter<AdaptorIntake.MyViewHolder> {

    Context context;
    ArrayList<NewsIntake> newsIntakeArrayList;

    public  AdaptorIntake(Context context, ArrayList<NewsIntake> newsIntakeArrayList){

        this.context = context;
        this.newsIntakeArrayList = newsIntakeArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.cardviewmealslist, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NewsIntake newsIntake = newsIntakeArrayList.get(position);
        holder.time.setText(newsIntake.Time);
        holder.calories.setText(newsIntake.Calories);
        holder.carbohydrates.setText(newsIntake.Carbohydrates);
        holder.protein.setText(newsIntake.Protein);
        holder.fat.setText(newsIntake.Fat);
        holder.name.setText(newsIntake.Name);

    }

    @Override
    public int getItemCount() {
        return newsIntakeArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView calories;
        TextView carbohydrates;
        TextView protein;
        TextView fat;
        TextView name;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            time = itemView.findViewById(R.id.mealTime);
            calories = itemView.findViewById(R.id.mealCalories);
            carbohydrates = itemView.findViewById(R.id.mealCarbohydrate);
            protein = itemView.findViewById(R.id.mealProtein);
            fat = itemView.findViewById(R.id.mealFat);
            name = itemView.findViewById(R.id.mealName);

        }
    }
}
