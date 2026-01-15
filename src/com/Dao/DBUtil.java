package com.Dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection getConnection() throws Exception {
    	Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://192.168.0.6:3306/paranoia_iris_tvmdb200?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            "root",
            "P$123"
        );
    }
}
