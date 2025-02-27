<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/26/2025
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    String role = (String) sessionObj.getAttribute("role");

    if (role == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>

<html>
<head>
    <title>driver-dashboard</title>
</head>
<body>
<%@ include file="dashboard-header.jsp" %>
<%
    if (!role.equals("DRIVER")) {
        response.sendRedirect("../unauthorized.jsp");
        return;
    }
%>
<div class="dashboard">
    <h2>Driver Dashboard</h2>
    <p>Manage ride requests, update availability, and track earnings.</p>

    <div class="dashboard-options">
        <a href="#">View Assigned Rides</a>
        <a href="#">Update Availability</a>
        <a href="#">Check Earnings</a>
    </div>
</div>
<%@ include file="dashboard-footer.jsp" %>

</body>
</html>
