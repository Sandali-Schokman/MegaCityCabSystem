<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/4/2025
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.PerformanceReportDTO" %>

<%
    List<PerformanceReportDTO> reports = (List<PerformanceReportDTO>) request.getAttribute("reports");
    String role = (String) session.getAttribute("role");
    String dashboardPage = "../dashboards/admin-dashboard.jsp"; // Default to Admin

    if ("MANAGER".equals(role)) {
        dashboardPage = "../dashboards/operator-dashboard.jsp"; // Change to Manager's dashboard
    }
%>

<html>
<head>
    <title>Driver Performance Report</title>
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>
<%-- Include the correct dashboard based on the user role --%>
<jsp:include page="<%= dashboardPage %>"/>

<div class="container">
    <h2>Driver Performance Report</h2>

    <% if (reports != null && !reports.isEmpty()) { %>
    <table border="1">
        <tr>
            <th>Driver ID</th>
            <th>Name</th>
            <th>Completed Rides</th>
            <th>Total Earnings ($)</th>
            <th>Average Rating</th>
        </tr>
        <% for (PerformanceReportDTO report : reports) { %>
        <tr>
            <td><%= report.getDriverId() %></td>
            <td><%= report.getDriverName() %></td>
            <td><%= report.getCompletedRides() %></td>
            <td>$<%= report.getTotalEarnings() %></td>
            <td><%= report.getAverageRating() != 0 ? report.getAverageRating() : "No Ratings" %></td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No performance data available.</p>
    <% } %>

    <a href="<%= request.getContextPath() %>/views/dashboards/admin-dashboard.jsp">Back to Dashboard</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
