package dao;

import database.DatabaseConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** All database operations for the products table. */
public class ProductDAO {

    public void addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products (product_name, description, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setString(2, p.getDescription());
            ps.setInt(3, p.getQuantity());
            ps.setBigDecimal(4, p.getPrice());
            ps.executeUpdate();
        }
    }

    public void updateProduct(Product p) throws SQLException {
        String sql = "UPDATE products SET product_name = ?, description = ?, quantity = ?, price = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setString(2, p.getDescription());
            ps.setInt(3, p.getQuantity());
            ps.setBigDecimal(4, p.getPrice());
            ps.setInt(5, p.getId());
            ps.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id, product_name, description, quantity, price FROM products ORDER BY id";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("price")));
            }
        }
        return list;
    }
}
