<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/26/2025
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="utils.SessionUtils" %>

<jsp:include page="../session-check.jsp"/>

<%
    String role = SessionUtils.getUserRole(request);
    if (!"MANAGER".equals(role)) {
        response.sendRedirect("../unauthorized.jsp");
        return;
    }
%>

<html>
<head>
    <title>operator-dashboard</title>
</head>
<body>
<%@ include file="dashboard-header.jsp" %>

<div class="dashboard">
    <h2>Operator Dashboard</h2>
    <p>Manage bookings, assign drivers, and resolve complaints.</p>

    <div class="dashboard-options">
        <a href="<%= request.getContextPath() %>/views/admin/manual-driver-assignment.jsp">Assign Drivers</a>
        <a href="#">Handle Complaints</a>
        <a href="#">Verify Payments</a>
    </div>
</div>
<%@ include file="dashboard-footer.jsp" %>

</body>
</html>
