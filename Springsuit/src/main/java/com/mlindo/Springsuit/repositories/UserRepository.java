package com.mlindo.Springsuit.repositories;

import com.mlindo.Springsuit.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    // Creating Connection
    private static Connection connection;

    // Creating universal method to open connection with MySQL database
    public static Connection getConnection() {
        try {
            // Registering with MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // Updated driver class
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/my_restful_api", "root", "MYdb@123");
                System.out.println("Connection established successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error establishing connection: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // Creating universal method to close connection with MySQL database
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                users.add(user);
            }
            System.out.println("Fetched all users successfully.");
        } catch (SQLException e) {
            System.err.println("SQL error during fetching users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public User getUserById(int id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getString("phone"));
                    System.out.println("User found with ID: " + id);
                } else {
                    System.out.println("No user found with ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error during fetching user by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    public void createUser(User user) {
        String sql = "INSERT INTO users (name, username, email, address, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPhone());
            stmt.executeUpdate();
            System.out.println("User created successfully.");
        } catch (SQLException e) {
            System.err.println("SQL error during user creation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, username = ?, email = ?, address = ?, phone = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPhone());
            stmt.setInt(6, user.getId());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected during update: " + rowsAffected);
            if (rowsAffected == 0) {
                System.out.println("No user found with ID: " + user.getId());
            } else {
                System.out.println("User updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error during user update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Attempting to delete user with ID: " + id);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected during delete: " + rowsAffected);
            if (rowsAffected == 0) {
                System.out.println("No user found with ID: " + id);
            } else {
                System.out.println("User deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error during user deletion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

