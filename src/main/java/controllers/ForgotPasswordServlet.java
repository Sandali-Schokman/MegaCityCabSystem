package controllers;

import services.UserService;
import utils.EmailUtility;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/views/forgot-password.jsp?message=Please enter your email.");
            return;
        }


        // Generate a reset token
        String token = UUID.randomUUID().toString();
        boolean tokenSaved = userService.savePasswordResetToken(email, token);

        if (tokenSaved) {
            // Send email with password reset link
            String resetLink = request.getRequestURL().toString().replace("forgotPassword", "views/reset-password.jsp?token=" + token);
            EmailUtility.sendEmail(email, "Password Reset", "Click the link to reset your password: " + resetLink);
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?message=Check your email for the reset link.");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/forgot-password.jsp?message=Email not found.");
        }
    }
}
