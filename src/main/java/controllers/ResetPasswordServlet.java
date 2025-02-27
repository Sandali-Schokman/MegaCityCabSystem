package controllers;

import services.UserService;
import utils.HashUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (token == null || newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/views/reset-password.jsp?message=All fields are required.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/views/reset-password.jsp?token=" + token + "&message=Passwords do not match.");
            return;
        }

        // Validate token before resetting password
        if (!userService.isValidResetToken(token)) {
            response.sendRedirect(request.getContextPath() + "/views/reset-password.jsp?message=Invalid or expired token.");
            return;
        }

        boolean isReset = userService.resetPasswordWithToken(token, HashUtil.hashPassword(newPassword));

        if (isReset) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?message=Password reset successful. You can now log in.");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/reset-password.jsp?message=Invalid or expired token.");
        }
    }
}
