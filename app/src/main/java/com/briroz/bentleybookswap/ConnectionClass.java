package com.briroz.bentleybookswap;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Created to allow connections from multiple activities
public class ConnectionClass {
    String ip = "frodo.bentley.edu";
    String schema = "BrianR";
    String user = "harry";
    String pass = "harry";

    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String connURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connURL = "jdbc:jtds:sqlserver://" + ip + ";" + "databaseName=" + schema + ";user=" + user + ";password=" + pass + ";";
            conn = DriverManager.getConnection(connURL);
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
