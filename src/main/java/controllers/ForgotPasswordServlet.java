package controllers;

import services.UserService;
import utils.EmailUtility;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/views/forgot-password.jsp?message=Please enter your email.");
            return;
        }

        // Generate a temporary password
        String tempPassword = userService.resetPassword(email);

        if (tempPassword != null) {
            // Send email with temporary password
            EmailUtility.sendEmail(email, "Password Reset", "Your temporary password is: " + tempPassword);
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?message=Check your email for the new password.");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/forgot-password.jsp?message=Email not found.");
        }
    }
}
