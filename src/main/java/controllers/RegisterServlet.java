package controllers;

import dao.UserDAO;
import models.User;
import utils.HashUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String email = request.getParameter("email");
        String fullName = request.getParameter("full_name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        System.out.println("Password: " + password + " Confirm Password: " + confirmPassword);
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match!");
            request.getRequestDispatcher("webapp/views/register.jsp").forward(request, response);
            return;
        }

        String hashedPassword = HashUtil.hashPassword(password);
        User user = new User(username, hashedPassword, email, fullName, phone, address, "customer", null);

        UserDAO userDAO = new UserDAO();
        if (userDAO.registerUser(user)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error");
        } else {
            request.setAttribute("error", "Registration failed. Try again!");
            request.getRequestDispatcher("webapp/views/register.jsp").forward(request, response);
        }
    }
}

