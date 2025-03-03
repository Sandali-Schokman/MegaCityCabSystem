<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/26/2025
  Time: 12:25 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="utils.SessionUtils, java.util.List, dto.DriverDTO" %>

<jsp:include page="../session-check.jsp"/>

<%
    String role = SessionUtils.getUserRole(request);
    if (!"ADMIN".equals(role)) {
        response.sendRedirect("../unauthorized.jsp");
        return;
    }

    List<DriverDTO> drivers = (List<DriverDTO>) request.getAttribute("drivers");
%>


<html>
<head>
    <title>admin-dashboard</title>
    <link rel="stylesheet" href="../../assets/css/dashboard.css">
</head>
<body>
<%@ include file="dashboard-header.jsp" %>
<div class="dashboard">
    <h1>Admin Dashboard</h1>
    <p>Manage users, view reports, and oversee the system.</p>

    <div class="dashboard-options">
        <a href="#">Manage Users</a>
        <a href="../admin/manual-driver-assignment.jsp">Assign Drivers</a>
        <a href="<%= request.getContextPath() %>/viewDriverAvailability">Track Driver Availability</a>
        <a href="../admin/earnings-report.jsp">Earnings Report</a>
        <a href="#">View Reports</a>
        <a href="#">System Logs</a>
    </div>

    <h2>Driver Availability</h2>
    <table border="1">
        <tr>
            <th>Driver ID</th>
            <th>User ID</th>
            <th>Car ID</th>
            <th>Availability</th>
            <th>Total Earnings</th>
        </tr>
        <% if (drivers != null && !drivers.isEmpty()) { %>
        <% for (DriverDTO driver : drivers) { %>
        <tr>
            <td><%= driver.getDriverId() %></td>
            <td><%= driver.getUserId() %></td>
            <td><%= driver.getCarId() %></td>
            <td><%= driver.getAvailability() %></td>
            <td>$<%= driver.getTotalEarnings() %></td>
        </tr>
        <% } %>
        <% } else { %>
        <tr><td colspan="5">No drivers found.</td></tr>
        <% } %>
    </table>
</div>
<%@ include file="dashboard-footer.jsp" %>

</body>
</html>
