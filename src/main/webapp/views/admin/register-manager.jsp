<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/13/2025
  Time: 6:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.UserDTO" %>

<%
    String error = request.getParameter("error");
    String message = request.getParameter("message");
%>

<html>
<head>
    <title>Register Manager</title>
    <link rel="stylesheet" href="../../assets/css/cabStyle.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>
<jsp:include page="../dashboards/admin-dashboard.jsp"/>

<div class="container">
    <h2>Register New Manager</h2>

    <% if (error != null) { %>
    <p class="error-msg"><%= error %></p>
    <% } else if (message != null) { %>
    <p class="success-msg"><%= message %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/ManagerServlet"  method="POST" onsubmit="return validateForm()">
        <label>Username:</label>
        <input type="text" name="username" required><br>

        <label>Password:</label>
        <input type="password" name="password" required><br>

        <label>Email:</label>
        <input type="email" name="email" required><br>

        <label>Full Name:</label>
        <input type="text" name="full_name" required><br>

        <label>Phone:</label>
        <input type="text" name="phone" required><br>

        <label>Address:</label>
        <textarea name="address" required></textarea><br>

        <button type="submit">Register Manager</button><br>
    </form>

    <a href="<%= request.getContextPath() %>/views/admin/manage-managers.jsp">View Managers</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
