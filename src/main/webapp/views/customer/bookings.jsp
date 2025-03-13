<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/6/2025
  Time: 9:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.BookingDTO" %>

<%
  List<BookingDTO> bookings = (List<BookingDTO>) request.getAttribute("bookings");
%>

<html>
<head>
  <title>My Bookings</title>
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>
<jsp:include page="../dashboards/customer-dashboard.jsp"/>

<div class="container">
  <h2>My Bookings</h2>

  <% if (bookings != null && !bookings.isEmpty()) { %>
  <table border="1">
    <tr>
      <th>Booking ID</th>
      <th>Pickup</th>
      <th>Dropoff</th>
      <th>Scheduled Time</th>
      <th>Status</th>
      <th>Fare ($)</th>
      <th>Payment Status</th>
      <th>Action</th>
      <th>Action</th>
    </tr>
    <% for (BookingDTO booking : bookings) { %>
    <tr>
      <td><%= booking.getBookingId() %></td>
      <td><%= booking.getPickupLocation() %></td>
      <td><%= booking.getDropoffLocation() %></td>
      <td><%= booking.getScheduledTime() %></td>
      <td><%= booking.getBookingStatus() %></td>
      <td>$<%= booking.getFare() %></td>
      <td><%= booking.getPaymentStatus() %></td>
      <td>
        <form action="<%= request.getContextPath() %>/views/customer/payment.jsp" method="post">
          <input type="hidden" name="booking_id" value='<%=booking.getBookingId()%>'>
          <input type="hidden" name="fare" value='<%=booking.getFare()%>'>

          <input type="submit" value="Pay">
        </form>
      </td>
      <td>
        <form action="<%= request.getContextPath()%>/views/customer/review.jsp" method="post">
          <input type="hidden" name="booking_id" value='<%=booking.getBookingId()%>'>
          <input type="hidden" name="driver_id" value='<%=booking.getDriverId()%>'>
          <input type="submit" value="review">
        </form>
      </td>
    </tr>
    <% } %>
  </table>
  <% } else { %>
  <p>No bookings found.</p>
  <% } %>

  <a href="<%= request.getContextPath() %>/views/dashboards/customer-dashboard.jsp">Back to Dashboard</a>
</div>
</body>
</html>
