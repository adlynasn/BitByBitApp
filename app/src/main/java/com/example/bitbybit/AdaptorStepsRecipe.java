package com.example.bitbybit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptorStepsRecipe extends RecyclerView.Adapter<AdaptorStepsRecipe.MyViewHolder> {

    Context context;
    ArrayList<NewsStep> newsStepArrayList;

    public AdaptorStepsRecipe(Context context, ArrayList<NewsStep> newsStepArrayList){

        this.context = context;
        this.newsStepArrayList = newsStepArrayList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.cardviewfoodliststeps, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewsStep newsStep = newsStepArrayList.get(position);
        holder.foodStep.setText(newsStep.step);

    }

    @Override
    public int getItemCount() {
        return newsStepArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView foodStep;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodStep = itemView.findViewById(R.id.recipeStep);
        }
    }
}
