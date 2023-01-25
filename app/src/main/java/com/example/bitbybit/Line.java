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
            return DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s&useSSL=false", host, port, database, username, password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnectionLocal(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bitbybit", "root", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
