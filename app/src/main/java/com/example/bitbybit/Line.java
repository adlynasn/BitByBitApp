package com.example.bitbybit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Line {
     static String host = "mysql-103782-0.cloudclusters.net";
     static String port = "10001";
     static String database = "bitbybit";
     static String username = "danial";
     static String password = "bitbybit123";

    public static Connection getConnection(){
        try{
            Connection connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s&useSSL=false", host, port, database, username, password));
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Connection connection = Line.getConnection();
     */
}
