<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/7/2025
  Time: 6:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.BookingDTO" %>

<%
    List<BookingDTO> allBookings = (List<BookingDTO>) request.getAttribute("allBookings");
    String role = (String) session.getAttribute("role");
    String dashboardPage = "../dashboards/admin-dashboard.jsp"; // Default to Admin

    if ("MANAGER".equals(role)) {
        dashboardPage = "../dashboards/operator-dashboard.jsp"; // Change for Managers
    }
%>

<html>
<head>
    <title>Manage Bookings</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/dashboard.css">
</head>
<body>
<jsp:include page="<%= dashboardPage %>"/>

<div class="container">
    <h2>Manage Bookings</h2>

    <% if (allBookings != null && !allBookings.isEmpty()) { %>
    <table border="1">
        <tr>
            <th>Booking ID</th>
            <th>Customer</th>
            <th>Pickup</th>
            <th>Dropoff</th>
            <th>Scheduled Time</th>
            <th>Status</th>
            <th>Assign Driver</th>
            <th>Actions</th>
        </tr>
        <% for (BookingDTO booking : allBookings) { %>
        <tr>
            <td><%= booking.getBookingId() %></td>
            <td><%= booking.getCustomerId() %></td>
            <td><%= booking.getPickupLocation() %></td>
            <td><%= booking.getDropoffLocation() %></td>
            <td><%= booking.getScheduledTime() %></td>
            <td><%= booking.getBookingStatus() %></td>
            <td>
                <form action="<%= request.getContextPath() %>/assignDriver" method="POST">
                    <input type="hidden" name="booking_id" value="<%= booking.getBookingId() %>">
                    <select name="driver_id" required>
                        <option value="">Select Driver</option>
                        <%-- Populate available drivers dynamically --%>
                        <option value="1">Driver 1</option>
                        <option value="2">Driver 2</option>
                    </select>
                    <button type="submit">Assign</button>
                </form>
            </td>
            <td>
                <% if ("PENDING".equals(booking.getBookingStatus())) { %>
                <form action="<%= request.getContextPath() %>/cancelBooking" method="POST">
                    <input type="hidden" name="booking_id" value="<%= booking.getBookingId() %>">
                    <button type="submit">Cancel</button>
                </form>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No bookings available.</p>
    <% } %>

    <a href="<%= request.getContextPath() %>/views/dashboards/admin-dashboard.jsp">Back to Dashboard</a>
</div>
</body>
</html>
