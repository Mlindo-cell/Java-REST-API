package com.mlindo.Springsuit.repositories;

import com.mlindo.Springsuit.models.Post;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    // Creating Connection
    private static Connection connection;

    // Creating universal method to open connection with MySQL database
    public static Connection getConnection() {
        try {
            // Registering with MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // Updated to latest MySQL Connector driver class
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

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setUserId(rs.getInt("userId"));
                post.setTitle(rs.getString("title"));
                post.setBody(rs.getString("body"));
                posts.add(post);
            }
            System.out.println("Fetched all posts successfully.");
        } catch (SQLException e) {
            System.err.println("SQL error during fetching posts: " + e.getMessage());
            e.printStackTrace();
        }
        return posts;
    }

    public Post getPostById(int id) {
        Post post = null;
        String sql = "SELECT * FROM posts WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    post = new Post();
                    post.setId(rs.getInt("id"));
                    post.setUserId(rs.getInt("userId"));
                    post.setTitle(rs.getString("title"));
                    post.setBody(rs.getString("body"));
                    System.out.println("Post found with ID: " + id);
                } else {
                    System.out.println("No post found with ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error during fetching post by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return post;
    }

    public void createPost(Post post) {
        String sql = "INSERT INTO posts (userId, title, body) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, post.getUserId());
            stmt.setString(2, post.getTitle());
            stmt.setString(3, post.getBody());
            stmt.executeUpdate();
            System.out.println("Post created successfully.");
        } catch (SQLException e) {
            System.err.println("SQL error during post creation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updatePost(Post post) {
        String sql = "UPDATE posts SET userId = ?, title = ?, body = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, post.getUserId());
            stmt.setString(2, post.getTitle());
            stmt.setString(3, post.getBody());
            stmt.setInt(4, post.getId());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected during update: " + rowsAffected);
            if (rowsAffected == 0) {
                System.out.println("No post found with ID: " + post.getId());
            } else {
                System.out.println("Post updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error during post update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletePost(int id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Attempting to delete post with ID: " + id);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected during delete: " + rowsAffected);
            if (rowsAffected == 0) {
                System.out.println("No post found with ID: " + id);
            } else {
                System.out.println("Post deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error during post deletion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
