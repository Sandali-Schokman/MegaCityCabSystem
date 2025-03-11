<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/4/2025
  Time: 6:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.BookingTrendsDTO" %>

<%
  List<BookingTrendsDTO> trends = (List<BookingTrendsDTO>) request.getAttribute("trends");
  String role = (String) session.getAttribute("role");
  String dashboardPage = "../dashboards/admin-dashboard.jsp"; // Default to Admin

  if ("MANAGER".equals(role)) {
    dashboardPage = "../dashboards/operator-dashboard.jsp"; // Change to Manager's dashboard
  }
%>

<html>
<head>
  <title>Booking Trends Report</title>
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>

<%-- Include the correct dashboard based on the user role --%>
<jsp:include page="<%= dashboardPage %>"/>

<div class="container">
  <h2>Booking Trends Report</h2>

  <% if (trends != null && !trends.isEmpty()) { %>
  <table border="1">
    <tr>
      <th>Date</th>
      <th>Total Bookings</th>
      <th>Completed</th>
      <th>Cancelled</th>
    </tr>
    <% for (BookingTrendsDTO trend : trends) { %>
    <tr>
      <td><%= trend.getDate() %></td>
      <td><%= trend.getTotalBookings() %></td>
      <td><%= trend.getCompletedBookings() %></td>
      <td><%= trend.getCancelledBookings() %></td>
    </tr>
    <% } %>
  </table>
  <% } else { %>
  <p>No booking trends available.</p>
  <% } %>

  <a href="<%= request.getContextPath() %>/views/dashboards/admin-dashboard.jsp">Back to Dashboard</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
