<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/5/2025
  Time: 11:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.BookingDTO" %>

<%
  String role = (String) session.getAttribute("role");
  if (!"CUSTOMER".equals(role)) {
    response.sendRedirect("../unauthorized.jsp");
    return;
  }

  String error = request.getParameter("error");
  String message = request.getParameter("message");
  List<BookingDTO> completedBookings = (List<BookingDTO>) request.getAttribute("completedBookings");
%>

<html>
<head>
  <title>Submit Review</title>
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
  <script>
    // Auto-fill Driver ID when a booking is selected
    function populateDriverId() {
      let selectedBooking = document.getElementById("booking_id");
      let driverIdInput = document.getElementById("driver_id");
      let selectedOption = selectedBooking.options[selectedBooking.selectedIndex];

      if (selectedOption) {
        driverIdInput.value = selectedOption.dataset.driverId;
      }
    }
  </script>
</head>
<body>
<jsp:include page="../dashboards/customer-dashboard.jsp"/>
<div class="container">
  <h2>Rate Your Ride</h2>

  <% if (error != null) { %>
  <p class="error-msg"><%= error %></p>
  <% } else if (message != null) { %>
  <p class="success-msg"><%= message %></p>
  <% } %>

  <% if (completedBookings != null && !completedBookings.isEmpty()) { %>
  <form action="<%= request.getContextPath() %>/review" method="POST">
    <%-- Select Completed Booking --%>
      <label for="booking_id">Select Completed Ride:</label>
      <select name="booking_id" id="booking_id" required onchange="populateDriverId()">
        <option value="" disabled selected>Select a ride</option>
        <% for (BookingDTO booking : completedBookings) { %>
        <option value="<%= booking.getBookingId() %>" data-driver-id="<%= booking.getDriverId() %>">
          Ride: <%= booking.getPickupLocation() %> to <%= booking.getDropoffLocation() %> (Driver ID: <%= booking.getDriverId() %>)
        </option>
        <% } %>
      </select>

      <%-- Driver ID (Auto-filled) --%>
    <label for="driver_id">Driver ID:</label>
    <input type="text" name="driver_id" id="driver_id" required placeholder="Enter Driver ID">

      <%-- Rating (1-5 Stars) --%>
    <label for="rating">Rate Your Driver (1-5):</label>
    <select name="rating" id="rating" required>
      <option value="1">⭐</option>
      <option value="2">⭐⭐</option>
      <option value="3">⭐⭐⭐</option>
      <option value="4">⭐⭐⭐⭐</option>
      <option value="5">⭐⭐⭐⭐⭐</option>
    </select>

      <%-- Feedback Textbox --%>
    <label for="feedback">Feedback:</label>
    <textarea name="feedback" id="feedback" rows="4" required placeholder="Write about your experience..."></textarea>

      <%-- Submit Button --%>
    <button type="submit">Submit Review</button>
  </form>
  <% } else { %>
  <p>No completed rides found to review.</p>
  <% } %>

  <a href="<%= request.getContextPath() %>/views/dashboards/customer-dashboard.jsp">Back to Dashboard</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
