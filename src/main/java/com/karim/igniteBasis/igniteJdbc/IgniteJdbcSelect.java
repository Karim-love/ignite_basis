package com.karim.igniteBasis.igniteJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sblim
 * Date : 2021-11-25
 * Time : 오후 4:47
 */
public class IgniteJdbcSelect {
    public static void main(String[] args) {
        connect();
    }
    public static void connect() {
        String JDBC_DRIVER = "org.apache.ignite.IgniteJdbcDriver";
        String DB_URL = "jdbc:ignite:thin://192.168.124.250:10800/PUBLIC";

        System.out.println("zz");

        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);

            ResultSet rs = conn.createStatement().executeQuery("select USERID from MAPPING_DATA WHERE USERID= '1'");

            while (rs.next()) {
                String name = rs.getString(1);
                System.out.println(name);
            }
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
        }
    }
}
