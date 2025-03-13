<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/5/2025
  Time: 12:50 AM
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

  List<BookingDTO> completedBookings = (List<BookingDTO>) request.getAttribute("completedBookings");
  System.out.println("JSP: " + completedBookings.size());
%>

<html>
<head>
  <title>Submit a Complaint</title>
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>
<jsp:include page="../dashboards/customer-dashboard.jsp"/>

<div class="container">
  <h2>Submit a Complaint</h2>

  <%-- Display success or error messages --%>
  <% if (request.getParameter("message") != null) { %>
  <p class="success-msg"><%= request.getParameter("message") %></p>
  <% } else if (request.getParameter("error") != null) { %>
  <p class="error-msg"><%= request.getParameter("error") %></p>
  <% } %>

  <%-- Complaint Submission Form --%>
  <form action="<%= request.getContextPath() %>/complaints" method="POST">
    <label for="booking_id">Select Booking:</label>
    <select name="booking_id" id="booking_id" required>
      <% if (completedBookings != null && !completedBookings.isEmpty()) { %>
      <% for (BookingDTO booking : completedBookings) { %>
      <option value="<%= booking.getBookingId() %>">
        Booking #<%= booking.getBookingId() %> - <%= booking.getPickupLocation() %> to <%= booking.getDropoffLocation() %>
      </option>
      <% } %>
      <% } else { %>
      <option disabled>No completed bookings available</option>
      <% } %>
    </select>

    <label for="complaint_text">Complaint Details:</label>
    <textarea name="complaint_text" id="complaint_text" required placeholder="Describe your complaint..."></textarea>

    <button type="submit">Submit Complaint</button>
  </form>

  <a href="<%= request.getContextPath() %>/views/dashboards/customer-dashboard.jsp">Back to Dashboard</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>

</body>
</html>
