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
    String role = SessionUtils.getUserRole(request);
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
</head>
<body>

<%@ include file="dashboard-header.jsp" %>

<div class="dashboard">
    <h2>Customer Dashboard</h2>
    <p>Book rides, view history, and manage payments.</p>

    <div class="dashboard-options">
        <a href="<%= request.getContextPath() %>/views/booking.jsp">Book a Ride</a>
        <a href="#">Ride History</a>
        <a href="#">Payments</a>
    </div>
    <h3>Your Booking History</h3>
    <table border="1">
        <tr>
            <th>Pickup</th>
            <th>Dropoff</th>
            <th>Scheduled Time</th>
            <th>Status</th>
        </tr>
        <% if (bookings != null) {
            for (BookingDTO booking : bookings) { %>
        <tr>
            <td><%= booking.getPickupLocation() %></td>
            <td><%= booking.getDropoffLocation() %></td>
            <td><%= booking.getScheduledTime() %></td>
            <td><%= booking.getBookingStatus() %></td>
        </tr>
        <% }} %>
    </table>
</div>
<%@ include file="dashboard-footer.jsp" %>

</body>
</html>
