package com.example.p3project;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;
import java.sql.*;

public class ConnectDB {
    static Connection conn = null;
    public void connect() {
        //OutputDevice out = new OutputDevice();
        try {
            String url = "jdbc:sqlite:Tasks.db";
            SQLiteConfig config = new SQLiteConfig();
            config.resetOpenMode(SQLiteOpenMode.CREATE); // this disable creation
            conn = DriverManager.getConnection(url, config.toProperties());
            Statement stmt = conn.createStatement();
            System.out.println("Successfully connected to " + url + " database");
        } catch (SQLException e) {
            throw new Error("Problem connecting to database", e);
        }
    }
    public Connection getConn(){
        return conn;
    }

}