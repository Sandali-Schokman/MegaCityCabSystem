package controllers;

import dao.UserDAO;
import dao.DriverDAO;
import dto.UserDTO;
import dto.DriverDTO;
import utils.HashUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registerDriver")
public class RegisterDriverServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private DriverDAO driverDAO = new DriverDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = HashUtil.hashPassword(request.getParameter("password"));
        String email = request.getParameter("email");
        String fullName = request.getParameter("full_name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String carModel = request.getParameter("car_model");
        String licensePlate = request.getParameter("license_plate");

        // Insert driver into users table
        UserDTO user = new UserDTO(0, username, password, email, fullName, phone, address, "DRIVER", null);
        int userId = userDAO.registerDriver(user);

        if (userId > 0) {
            // Insert driver & car details
            boolean success = driverDAO.registerDriver(userId, carModel, licensePlate);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/views/manager/register-driver.jsp?message=Driver registered successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/manager/register-driver.jsp?error=Error registering driver.");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/views/manager/register-driver.jsp?error=Username or email already exists.");
        }
    }
}
