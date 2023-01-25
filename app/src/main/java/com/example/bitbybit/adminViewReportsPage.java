package com.example.bitbybit;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class adminViewReportsPage extends Fragment {

    private ArrayList<NewsReport> newsReportArraylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_view_reports_page, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name=bundle.getString("username");
        bundle.putString("username", name);

        dataInitialize();

        RecyclerView recyclerview = view.findViewById(R.id.recyclerViewReports);
        System.out.println("kat sini");
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        AdaptorReport myAdapter = new AdaptorReport(getContext(), newsReportArraylist);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        Button BtnBack = view.findViewById(R.id.returnToAdminHomePageButton);
        View.OnClickListener OCLBtnBack = v -> Navigation.findNavController(view).navigate(R.id.adminHomePage, bundle);
        BtnBack.setOnClickListener(OCLBtnBack);
    }

    private void dataInitialize() {
        newsReportArraylist = new ArrayList<>();
        System.out.println("dalam ni");


        Thread dataThread = new Thread(() -> {

            try {
                System.out.println("dalam connection");
                Connection connection = Line.getConnection();
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM report ");
                ResultSet res = ps.executeQuery();

                while(res.next()){
                    String username = res.getString(3);
                    String description = res.getString(2);

                    NewsReport newsReport = new NewsReport(username, description);
                    newsReportArraylist.add(newsReport);



                }
                res.close();
                connection.close();



            }catch (SQLException e){
                e.printStackTrace();
            }
        });
        dataThread.start();
        while (dataThread.isAlive()){

        }
    }
}