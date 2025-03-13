<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/12/2025
  Time: 10:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.BookingDTO" %>

<%
    String message = request.getParameter("message");
    String error = request.getParameter("error");
    List<BookingDTO> bookings = (List<BookingDTO>) request.getAttribute("bookings");

    String role = (String) session.getAttribute("role");

    String dashboardPage = "../dashboards/driver-dashboard.jsp";
%>
<html>
<head>
    <title>Driver Bookings</title>
    <link rel="stylesheet" href="../../assets/css/cabStyle.css">
    <link rel="stylesheet" href="../../assets/css/dashboard.css">
    <link rel="stylesheet" href="../../assets/css/dashboard-header.css">

</head>
<body>
<jsp:include page="<%= dashboardPage %>"/>
<div class="container">
    <h2>Your Assigned Bookings</h2>

    <% if (message != null) { %>
    <p class="success-msg"><%= message %></p>
    <% } %>
    <% if (error != null) { %>
    <p class="error-msg"><%= error %></p>
    <% } %>


    <br>
    <a href="<%= request.getContextPath() %>/views/dashboards/driver-dashboard.jsp">Back to Dashboard</a>
</div>

<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
