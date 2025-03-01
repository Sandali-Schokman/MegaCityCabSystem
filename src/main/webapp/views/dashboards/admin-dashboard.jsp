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
</head>
<body>
<%@ include file="dashboard-header.jsp" %>
<div class="dashboard">
    <h2>Admin Dashboard</h2>
    <p>Manage users, view reports, and oversee the system.</p>

    <div class="dashboard-options">
        <a href="#">Manage Users</a>
        <a href="../admin/manual-driver-assignment.jsp">Assign Drivers</a>
        <a href="#">View Reports</a>
        <a href="#">System Logs</a>
    </div>
</div>
<%@ include file="dashboard-footer.jsp" %>

</body>
</html>
