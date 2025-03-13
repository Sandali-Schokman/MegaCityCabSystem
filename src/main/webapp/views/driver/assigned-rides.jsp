<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/12/2025
  Time: 3:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="dto.BookingDTO" %>
<%@ page import="java.util.List" %>
<%
    List<BookingDTO> assignedBookings = (List<BookingDTO>) request.getAttribute("assignedBookings");
%>

<html>
<head>
    <title>Assigned Rides</title>
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
    <style>
        body { font-family: Arial; background-color: #f2f2f2; padding: 20px; color: #222222 }
        h2{align-content: center}
        table { width: 100%; border-collapse: collapse; background: #fff; box-shadow: 0 0 10px rgba(0,0,0,0.1);}
        th, td { padding: 12px; border: 1px solid #ccc; text-align: left; }
        th { background-color: #ff9900; color: white; }
    </style>
</head>
<body>
<jsp:include page="../dashboards/driver-dashboard.jsp"/>

<div>
    <h2>Your Assigned Rides</h2>

    <% if (assignedBookings != null && !assignedBookings.isEmpty()) { %>
    <table>
        <tr>
            <th>Booking ID</th>
            <th>Customer ID</th>
            <th>Pickup Location</th>
            <th>Drop-off Location</th>
            <th>Scheduled Time</th>
            <th>Action</th>
        </tr>
        <% for (BookingDTO booking : assignedBookings) { %>
        <tr>
            <td><%= booking.getBookingId() %></td>
            <td><%= booking.getCustomerId() %></td>
            <td><%= booking.getPickupLocation() %></td>
            <td><%= booking.getDropoffLocation() %></td>
            <td><%= booking.getScheduledTime() %></td>
            <td>
                <form action="<%= request.getContextPath() %>/endBooking" method="post" style="display:inline;">
                    <input type="hidden" name="booking_id" value="<%= booking.getBookingId() %>">
                    <button type="submit">End Booking</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No rides assigned yet.</p>
    <% } %>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
