<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/26/2025
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="utils.SessionUtils" %>
<%@ page import="java.util.List, dto.BookingDTO" %>
<jsp:include page="../session-check.jsp"/>

<%
    String role = (String) session.getAttribute("role");
    if (!"CUSTOMER".equals(role)) {
        response.sendRedirect("../unauthorized.jsp");
        return;
    }
%>

<%
    List<BookingDTO> bookings = (List<BookingDTO>) request.getAttribute("bookings");
%>


<html>
<head>
    <title>customer-dashboard</title>
    <link rel="stylesheet" href="../../assets/css/dashboard.css">
</head>
<body>

<%@ include file="dashboard-header.jsp" %>

<div class="dashboard">
    <h1>Customer Dashboard</h1>
    <p>Book rides, view history, and manage payments.</p>

    <div class="dashboard-options">
        <a href="<%= request.getContextPath() %>/views/customer/book-ride.jsp">Book a Ride</a>
        <a href="<%= request.getContextPath() %>/views/customer/bookings.jsp">My Bookings</a>
        <a href="#">Ride History</a>
        <a href="#">Payments</a>
        <a href="<%= request.getContextPath() %>/views/customer/complaint.jsp">Complaints</a>
        <a href="<%= request.getContextPath() %>/views/customer/review.jsp">Review</a>
    </div>
</div>
<%@ include file="dashboard-footer.jsp" %>

</body>
</html>
