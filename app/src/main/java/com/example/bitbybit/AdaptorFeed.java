package com.example.bitbybit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptorFeed extends RecyclerView.Adapter<AdaptorFeed.MyViewHolder> {

    Context context;
    ArrayList<NewsFeed> newsFeedArrayList;

    public AdaptorFeed(Context context, ArrayList<NewsFeed> newsFeedArrayList){

        this.context =context;
        this.newsFeedArrayList = newsFeedArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.cardviewfeedcomment, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewsFeed newsFeed = newsFeedArrayList.get(position);
        holder.titleFeed.setText(newsFeed.feedTitle);
        holder.feedContent.setText(newsFeed.feedContents);

    }

    @Override
    public int getItemCount() {
        return newsFeedArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleFeed;
        TextView feedContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleFeed = itemView.findViewById(R.id.titleFeed);
            feedContent = itemView.findViewById(R.id.feedContent);
        }
    }
}
