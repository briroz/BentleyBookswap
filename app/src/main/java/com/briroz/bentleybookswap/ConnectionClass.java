package com.briroz.bentleybookswap;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Created to allow connections from multiple activities
public class ConnectionClass {
    String ip = "frodo.bentley.edu:3306/BrianR";
    String user = "harry";
    String pass = "harry";

    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String connURL = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connURL = "jdbc:mysql://" + ip + "";
            conn = DriverManager.getConnection(connURL, user, pass);
        } catch (SQLException se) {
            Log.e("TAG", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("TAG", e.getMessage());
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
        return conn;
    }


}
