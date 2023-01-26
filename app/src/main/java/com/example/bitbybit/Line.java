package com.example.bitbybit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Line {
     static String host = "mysql-107734-0.cloudclusters.net";
     static String port = "10221";
     static String database = "bitbybit2";
     static String username = "admin";
     static String password = "nur26eVP";

    public static Connection getConnection(){
        try{
            System.out.println("dapat masuk");
            return DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?user=%s & password=%s & useSSL=false", host, port, database, username, password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
