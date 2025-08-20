package com.darwinruiz.shoplite.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static DbConnection instance;
    private Connection connection;

    // Ajuste a contenedor Docker: puerto 5555, DB appdb, user postgres
    private static final String URL = "jdbc:postgresql://localhost:5555/appdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin123";

    private DbConnection() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static DbConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DbConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
