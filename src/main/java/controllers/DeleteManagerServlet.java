package controllers;

import services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/DeleteManagerServlet")
public class DeleteManagerServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Authorization check
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        try {
            int managerId = Integer.parseInt(request.getParameter("manager_id"));

            boolean deleted = userService.deleteUserById(managerId);

            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/ManagerServlet?message=Manager removed successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/ManagerServlet?error=Failed to remove manager.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ManagerServlet?error=Invalid Manager ID.");
        }
    }
}
