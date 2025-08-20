package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.darwinruiz.shoplite.models.User;

public class UserRepository {

    private final Connection conn;

    public UserRepository() throws SQLException {
        this.conn = DbConnection.getInstance().getConnection();
    }

    // Buscar usuario por username (solo para login)
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT username, password, role FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        }
        return null;
    }
}
