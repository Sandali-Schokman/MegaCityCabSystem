package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.UserService;
import dto.UserDTO;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDTO user = userService.authenticateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            switch (user.getRole()) {
                case "ADMIN":
                    response.sendRedirect(request.getContextPath() + "/views/dashboards/admin-dashboard.jsp");
                    break;
                case "MANAGER":
                    response.sendRedirect(request.getContextPath() + "/views/dashboards/manager-dashboard.jsp");
                    break;
                case "DRIVER":
                    response.sendRedirect(request.getContextPath() + "/views/dashboards/driver-dashboard.jsp");
                    break;
                case "CUSTOMER":
                    response.sendRedirect(request.getContextPath() + "/views/dashboards/customer-dashboard.jsp");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Invalid Role");
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Invalid Credentials");
        }

    }
}
