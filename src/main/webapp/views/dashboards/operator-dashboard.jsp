<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/26/2025
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="utils.SessionUtils, java.util.List, dto.DriverDTO" %>

<jsp:include page="../session-check.jsp"/>

<%
    String role = SessionUtils.getUserRole(request);
    if (!"MANAGER".equals(role)) {
        response.sendRedirect("../unauthorized.jsp");
        return;
    }
    List<DriverDTO> drivers = (List<DriverDTO>) request.getAttribute("drivers");
%>

<html>
<head>
    <title>operator-dashboard</title>
    <link rel="stylesheet" href="../../assets/css/dashboard.css">
</head>
<body>
<%@ include file="dashboard-header.jsp" %>

<div class="dashboard">
    <h1>Manager Dashboard</h1>
    <p>Manage bookings, assign drivers, and resolve complaints.</p>

    <div class="dashboard-options">
        <a href="<%= request.getContextPath()%>/booking">Manage Bookings</a>
        <a href="<%= request.getContextPath() %>/views/manager/register-driver.jsp">Register Driver</a>
        <a href="<%= request.getContextPath() %>/assignManualDriver">Assign Drivers</a>
        <a href="<%= request.getContextPath() %>/DriverAvailability">Track Driver Availability</a>
        <a href="<%= request.getContextPath() %>/earningsReport">Earnings Report</a>
        <a href="<%= request.getContextPath() %>/verifyOnlineTransfer">Verify Online Transfers</a>
        <a href="<%= request.getContextPath() %>/bookingTrends">Booking Trends Report</a>
        <a href="<%= request.getContextPath() %>/performanceReport">Driver Performance Report</a>
        <a href="<%= request.getContextPath() %>/complaints">Manage Complaints</a>
        <a href="<%= request.getContextPath() %>/review">Driver Reviews & Ratings</a>
    </div>
</div>
</body>
</html>
