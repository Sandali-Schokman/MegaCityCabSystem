<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/2/2025
  Time: 1:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="dto.EarningsDTO" %>

<%
    EarningsDTO earnings = (EarningsDTO) request.getAttribute("earnings");
%>

<html>
<head>
    <title>Driver Earnings</title>
    <link rel="stylesheet" href="../../assets/css/dashboard.css">
</head>
<body>
<%@ include file="../dashboards/driver-dashboard.jsp" %>

<div class="dashboard">
    <h2>Driver Earnings</h2>

    <% if (earnings != null) { %>
    <p><strong>Total Earnings:</strong> $<%= earnings.getTotalEarnings() %></p>
    <p><strong>Completed Rides:</strong> <%= earnings.getCompletedRides() %></p>
    <p><strong>Last Payment Date:</strong> <%= earnings.getLastPaymentDate() %></p>
    <% } else { %>
    <p>No earnings data available.</p>
    <% } %>

    <a href="<%= request.getContextPath() %>/views/dashboards/driver-dashboard.jsp">Back to Dashboard</a>
</div>

</body>
</html>
