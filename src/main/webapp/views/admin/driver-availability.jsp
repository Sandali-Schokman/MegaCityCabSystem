<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/11/2025
  Time: 1:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="utils.SessionUtils, java.util.List, dto.DriverDTO" %>

<jsp:include page="../session-check.jsp"/>

<%
    String role = (String) session.getAttribute("role");
    String dashboardPage = "../dashboards/admin-dashboard.jsp"; // Default to Admin

    if ("MANAGER".equals(role)) {
        dashboardPage = "../dashboards/operator-dashboard.jsp"; // Change to Manager's dashboard
    }

    List<DriverDTO> drivers = (List<DriverDTO>) request.getAttribute("drivers");
%>

<html>
<head>
    <title>Driver Availability</title>
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">

</head>
<body>
<jsp:include page="<%= dashboardPage %>"/>
<div class="container">
    <h2>Driver Availability</h2>
    <% if (drivers != null && !drivers.isEmpty()) { %>
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
    <% } %>
</div>

<a href="<%= request.getContextPath() %>/views/dashboards/<%= "ADMIN".equals(role) ? "admin-dashboard.jsp" : "operator-dashboard.jsp" %>">Back to Dashboard</a>
</body>
</html>