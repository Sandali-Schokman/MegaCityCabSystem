package controllers;

import dto.DriverDTO;
import services.DriverAssignmentService;
import services.NearestDriverStrategy;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/assignDriver")
public class DriverAssignmentServlet extends HttpServlet {
    private final DriverAssignmentService driverService = new DriverAssignmentService(new NearestDriverStrategy());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<DriverDTO> assignedDriver = driverService.assignDriver();

        if (assignedDriver.isPresent()) {
            request.setAttribute("assignedDriver", assignedDriver.get());
            request.getRequestDispatcher("/views/admin/assign-driver.jsp").forward(request, response);
        } else {
            response.sendRedirect("/views/admin/assign-driver.jsp?error=No available drivers.");
        }
    }
}
