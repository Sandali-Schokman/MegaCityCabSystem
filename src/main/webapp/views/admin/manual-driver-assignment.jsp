<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/1/2025
  Time: 2:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.List, dto.BookingDTO, dto.DriverDTO" %>

<jsp:include page="../session-check.jsp"/>

<%
  List<BookingDTO> pendingBookings = (List<BookingDTO>) request.getAttribute("pendingBookings");
  List<DriverDTO> availableDrivers = (List<DriverDTO>) request.getAttribute("availableDrivers");
  String role = (String) session.getAttribute("role");
  String dashboardPage = "../dashboards/admin-dashboard.jsp"; // Default to Admin

  if ("MANAGER".equals(role)) {
    dashboardPage = "../dashboards/operator-dashboard.jsp"; // Change to Manager's dashboard
  }
%>

<html>
<head>
    <title>Manual Driver Assignment</title>
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>
<%-- Include the correct dashboard based on the user role --%>
<jsp:include page="<%= dashboardPage %>"/>

<div class="container">
  <h2>Manual Driver Assignment</h2>

  <%-- Display errors or success messages --%>
  <% if (request.getParameter("error") != null) { %>
  <p class="error-msg"><%= request.getParameter("error") %></p>
  <% } else if (request.getParameter("message") != null) { %>
  <p class="success-msg"><%= request.getParameter("message") %></p>
  <% } %>

  <form action="<%= request.getContextPath() %>/assignManualDriver" method="POST">
    <label>Select Booking</label>
    <select name="booking_id" required>
      <% if (pendingBookings != null && !pendingBookings.isEmpty()) { %>
      <% for (BookingDTO booking : pendingBookings) { %>
      <option value="<%= booking.getBookingId() %>">
        Booking #<%= booking.getBookingId() %> - <%= booking.getPickupLocation() %> to <%= booking.getDropoffLocation() %>
      </option>
      <% } %>
      <% } else { %>
      <option disabled>No pending bookings</option>
      <% } %>
    </select>

    <label>Select Driver</label>
    <select name="driver_id" required>
      <% if (availableDrivers != null && !availableDrivers.isEmpty()) { %>
      <% for (DriverDTO driver : availableDrivers) { %>
      <option value="<%= driver.getDriverId() %>">
        Driver #<%= driver.getDriverId() %> - Available
      </option>
      <% } %>
      <% } else { %>
      <option disabled>No available drivers</option>
      <% } %>
    </select>

    <button type="submit">Assign Driver</button>
  </form>
  <a href="<%= request.getContextPath() %>/views/dashboards/<%= "ADMIN".equals(role) ? "admin-dashboard.jsp" : "operator-dashboard.jsp" %>">Back to Dashboard</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
