<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/26/2025
  Time: 12:25 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="utils.SessionUtils" %>

<jsp:include page="../session-check.jsp"/>

<%
    String role = SessionUtils.getUserRole(request);
    if (!"ADMIN".equals(role)) {
        response.sendRedirect("../unauthorized.jsp");
        return;
    }

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
        <a href="<%= request.getContextPath()%>/views/admin/register-manager.jsp">Add Managers</a>
        <a href="<%= request.getContextPath()%>/ManagerServlet">Manage Managers</a>
        <a href="<%= request.getContextPath()%>/assignManualDriver">Assign Drivers</a>
        <a href="<%= request.getContextPath()%>/booking">Manage Bookings</a>
        <a href="<%= request.getContextPath() %>/DriverAvailability">Track Driver Availability</a>
        <a href="<%= request.getContextPath() %>/earningsReport">Earnings Report</a>
        <a href="<%= request.getContextPath() %>/bookingTrends">Booking Trends Report</a>
        <a href="<%= request.getContextPath() %>/performanceReport">Driver Performance Report</a>
        <a href="<%= request.getContextPath() %>/complaints">Manage Complaints</a>
        <a href="<%= request.getContextPath() %>/review">Driver Reviews & Ratings</a>
    </div>

    <br>

    <div>
        <h2>Set Commission Percentage</h2>
        <form action="<%= request.getContextPath() %>/updateCommission" method="POST">
            <label>Commission %:</label>
            <input type="number" name="commission" step="0.01" min="0" max="100" required>
            <button type="submit">Update</button>
        </form>

    </div>
</div>


</body>
</html>
