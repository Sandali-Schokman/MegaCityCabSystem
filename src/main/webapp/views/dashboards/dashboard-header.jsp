<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/26/2025
  Time: 12:37 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<html>
<head>
    <title>dashboard-header</title>
    <link rel="stylesheet" href="../../assets/css/dashboard-header.css">
</head>
<body>
<div class="navbar">
    <% if (role.equals("ADMIN")) { %>
    <a href="admin-dashboard.jsp">Admin Panel</a>
    <% } else if (role.equals("MANAGER")) { %>
    <a href="operator-dashboard.jsp">Operator Panel</a>
    <% } else if (role.equals("DRIVER")) { %>
    <a href="driver-dashboard.jsp">Driver Panel</a>
    <% } else if (role.equals("CUSTOMER")) { %>
    <a href="../home.jsp">Back</a>
    <% } %>
    <a href="<%= request.getContextPath()%>/logout">Logout</a>
</div>

</body>
</html>
