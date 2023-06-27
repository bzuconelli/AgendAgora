package com.example.agendagora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSigleton {
    public static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection ==null){
            connection= DriverManager.getConnection( //
                    "jdbc:mysql://localhost:3306/agendagora", //
                    "root", //
                    "");
        }
        return connection;
    }
}
