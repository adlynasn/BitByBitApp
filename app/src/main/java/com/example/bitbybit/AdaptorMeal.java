package com.example.bitbybit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptorMeal extends  RecyclerView.Adapter<AdaptorMeal.MyViewHolder>{

    Context context;
    ArrayList<NewsMeal> newsMealArrayList;

    public AdaptorMeal(Context context, ArrayList<NewsMeal> newsMealArrayList){
        this.context = context;
        this.newsMealArrayList = newsMealArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardviewcalorieintake, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NewsMeal newsMeal = newsMealArrayList.get(position);
        holder.date.setText(newsMeal.Date);
        holder.viewCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,calorieCounterPage.class);
                intent.putExtra("menu name",newsMeal.Date);
//                Navigation.findNavController(view).navigate(R.id.viewCaloriesPage, bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return newsMealArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        Button viewCalorie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.mealDate);
            viewCalorie = itemView.findViewById(R.id.viewCalorieButton);
        }
    }
}
