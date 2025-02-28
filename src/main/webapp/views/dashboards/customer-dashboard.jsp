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
    if (!"CUSTOMER".equals(role)) {
        response.sendRedirect("../unauthorized.jsp");
        return;
    }
%>

<html>
<head>
    <title>customer-dashboard</title>
</head>
<body>

<%@ include file="dashboard-header.jsp" %>

<div class="dashboard">
    <h2>Customer Dashboard</h2>
    <p>Book rides, view history, and manage payments.</p>

    <div class="dashboard-options">
        <a href="#">Book a Ride</a>
        <a href="#">Ride History</a>
        <a href="#">Payments</a>
    </div>
</div>
<%@ include file="dashboard-footer.jsp" %>

</body>
</html>
