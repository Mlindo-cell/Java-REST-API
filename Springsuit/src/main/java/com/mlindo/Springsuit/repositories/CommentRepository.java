package com.mlindo.Springsuit.repositories;

import com.mlindo.Springsuit.models.Comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    // Creating Connection
    private static Connection connection;

    // Creating universal method to open connection with MySQL database
    public static Connection getConnection() {
        try {
            // Registering with MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
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

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setPostId(rs.getInt("postId"));
                comment.setEmail(rs.getString("email"));
                comment.setBody(rs.getString("body"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.err.println("SQL error during fetching comments: " + e.getMessage());
            e.printStackTrace();
        }
        return comments;
    }

    public Comment getCommentById(int id) {
        Comment comment = null;
        String sql = "SELECT * FROM comments WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setPostId(rs.getInt("postId"));
                    comment.setEmail(rs.getString("email"));
                    comment.setBody(rs.getString("body"));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error during fetching comment by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return comment;
    }

    public void createComment(Comment comment) {
        String sql = "INSERT INTO comments (postId, email, body) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, comment.getPostId());
            stmt.setString(2, comment.getEmail());
            stmt.setString(3, comment.getBody());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
            System.out.println("Comment created successfully.");
            } else {
                System.out.println("No rows inserted.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error during comment creation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateComment(Comment comment) {
        String sql = "UPDATE comments SET postId = ?, email = ?, body = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, comment.getPostId());
            stmt.setString(2, comment.getEmail());
            stmt.setString(3, comment.getBody());
            stmt.setInt(4, comment.getId());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected during update: " + rowsAffected);
            if (rowsAffected == 0) {
                System.out.println("No comment found with ID: " + comment.getId());
            } else {
                System.out.println("Comment updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error during comment update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteComment(int id) {
        String sql = "DELETE FROM comments WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Attempting to delete comment with ID: " + id);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected during delete: " + rowsAffected);
            if (rowsAffected == 0) {
                System.out.println("No comment found with ID: " + id);
            } else {
                System.out.println("Comment deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error during comment deletion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

