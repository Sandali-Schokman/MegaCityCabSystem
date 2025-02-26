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
}
