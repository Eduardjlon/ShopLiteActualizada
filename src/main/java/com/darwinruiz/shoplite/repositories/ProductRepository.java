package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.db.DbConnection;
import com.darwinruiz.shoplite.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final Connection conn;

    public ProductRepository() throws SQLException {
        this.conn = DbConnection.getInstance().getConnection();
    }

    // Traer todos los productos con paginación
    public List<Product> findAll(int limit, int offset) throws SQLException {
        String sql = "SELECT * FROM products ORDER BY id LIMIT ? OFFSET ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
                products.add(p);
            }
        }
        return products;
    }

    // Nuevo método: traer todos los productos sin paginación
    public List<Product> listAll() throws SQLException {
        return findAll(1000, 0); // Ajusta el límite según necesites
    }

    // Contar todos los productos
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM products";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    // Agregar producto nuevo
    public void add(Product p) throws SQLException {
        String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setDouble(2, p.getPrice());
            stmt.setInt(3, p.getStock());
            stmt.executeUpdate();
        }
    }

    // Buscar producto por id
    public Product findById(long id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            }
        }
        return null;
    }

    // Actualizar producto
    public void update(Product p) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setDouble(2, p.getPrice());
            stmt.setInt(3, p.getStock());
            stmt.setLong(4, p.getId());
            stmt.executeUpdate();
        }
    }

    // Eliminar producto
    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
