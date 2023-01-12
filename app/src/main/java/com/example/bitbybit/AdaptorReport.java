package com.example.bitbybit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptorReport extends RecyclerView.Adapter<AdaptorReport.MyViewHolder>{

    Context context;
    ArrayList<NewsReport> newsReportArrayList;

    public AdaptorReport(Context context, ArrayList<NewsReport> newsReportArrayList){
        this.context = context;
        this.newsReportArrayList = newsReportArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardviewadminreport, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NewsReport newsReport = newsReportArrayList.get(position);
        holder.ReportName.setText(newsReport.reportName);
        holder.ReportDesc.setText(newsReport.reportDesc);

    }

    @Override
    public int getItemCount() {
        return newsReportArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ReportName;
        TextView ReportDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ReportName = itemView.findViewById(R.id.reportUsername);
            ReportDesc = itemView.findViewById(R.id.reportDesc);
        }
    }
}
