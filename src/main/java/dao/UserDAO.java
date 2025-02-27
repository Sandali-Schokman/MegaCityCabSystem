package dao;

import config.DatabaseConnection;
import dto.UserDTO;
import models.User;
import java.sql.*;

public class UserDAO {
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, password, email, full_name, phone, address, role, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Hash before passing
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getAddress());
            stmt.setString(7, user.getRole()); // Role will be assigned internally

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserDTO getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserDTO(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"), // Hashed password
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Check if an email exists in the database
    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update password for the user
    public boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Store password reset token in database
    public boolean storePasswordResetToken(String email, String token, Timestamp expiration) {
        String query = "UPDATE users SET reset_token = ?, token_expiry = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, token);
            stmt.setTimestamp(2, expiration);
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update password using reset token
    public boolean updatePasswordUsingToken(String token, String hashedPassword) {
        String query = "UPDATE users SET password = ?, reset_token = NULL, token_expiry = NULL WHERE reset_token = ? AND token_expiry > NOW()";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hashedPassword);
            stmt.setString(2, token);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validate reset token
    public boolean isValidResetToken(String token) {
        String query = "SELECT COUNT(*) FROM users WHERE reset_token = ? AND token_expiry > NOW()";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

