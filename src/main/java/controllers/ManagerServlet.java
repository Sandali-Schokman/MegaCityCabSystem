package controllers;

import dto.UserDTO;
import services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/ManagerServlet")
public class ManagerServlet extends HttpServlet {
    private final UserService userService = new UserService();

    // Handle Manager Registration (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password"); // Hash before storing
            String email = request.getParameter("email");
            String fullName = request.getParameter("full_name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            UserDTO manager = new UserDTO(0, username, password, email, fullName, phone, address, "MANAGER", null);

            boolean isRegistered = userService.registerManager(manager);
            if (isRegistered) {
                response.sendRedirect(request.getContextPath() + "/views/admin/register-manager.jsp?message=Manager Registered Successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/admin/register-manager.jsp?error=Registration Failed.");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/register-manager.jsp?error=Invalid Input.");
        }
    }

    // Handle Listing Managers (GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        List<UserDTO> managers = userService.getAllManagers();
        request.setAttribute("managers", managers);
        request.getRequestDispatcher("/views/admin/manage-managers.jsp").forward(request, response);
    }
}
