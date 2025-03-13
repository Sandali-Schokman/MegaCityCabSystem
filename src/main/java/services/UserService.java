package services;

import dao.UserDAO;
import dto.UserDTO;
import mappers.UserMapper;
import models.User;
import utils.HashUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public UserDTO authenticateUser(String username, String password) {
        UserDTO user = userDAO.getUserByUsername(username);
        if (user != null && HashUtil.verifyPassword(password, user.getPassword())) {
            return user; // Valid user
        }
        return null; // Invalid credentials
    }

    public String resetPassword(String email) {
        // Check if the email exists
        if (!userDAO.emailExists(email)) {
            return null;
        }

        // Generate a temporary password
        String tempPassword = HashUtil.generateTemporaryPassword();
        String hashedPassword = HashUtil.hashPassword(tempPassword);

        // Update the password in the database
        boolean success = userDAO.updatePassword(email, hashedPassword);
        return success ? tempPassword : null;
    }

    // Save reset token in database
    public boolean savePasswordResetToken(String email, String token) {
        Timestamp expirationTime = new Timestamp(System.currentTimeMillis() + (15 * 60 * 1000)); // 15 minutes expiration
        return userDAO.storePasswordResetToken(email, token, expirationTime);
    }

    // Reset password using token
    public boolean resetPasswordWithToken(String token, String hashedPassword) {
        return userDAO.updatePasswordUsingToken(token, hashedPassword);
    }

    public boolean isValidResetToken(String token){
        return userDAO.isValidResetToken(token);
    }

    public boolean registerManager(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        return userDAO.registerManager(user);
    }

    public List<UserDTO> getAllManagers() {
        return userDAO.getAllManagers();
    }

    public boolean deleteUserById(int userId) {
        return userDAO.deleteUserById(userId);
    }

}
