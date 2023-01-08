package com.example.bitbybit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Line {
    private static String host = "mysql-103782-0.cloudclusters.net";
    private static String port = "10001";
    private static String database = "bitbybit";
    private static String username = "danial";
    private static String password = "bitbybit123";

    public static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", host, port, database), username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Connection connection = Line.getConnection();
     */
}
