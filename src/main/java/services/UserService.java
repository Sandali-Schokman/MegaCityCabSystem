package services;

import dao.UserDAO;
import dto.UserDTO;
import utils.HashUtil;

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
}
