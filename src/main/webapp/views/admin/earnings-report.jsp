<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/3/2025
  Time: 10:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.EarningsDTO" %>
<%
  List<EarningsDTO> earningsList = (List<EarningsDTO>) request.getAttribute("earningsList");
%>

<html>
<head>
  <title>Admin - Earnings Report</title>
  <link rel="stylesheet" href="../../assets/css/dashboard.css">
</head>
<body>
<%@ include file="../dashboards/admin-dashboard.jsp" %>

<div class="dashboard">
  <h2>Earnings Report</h2>

  <table border="1">
    <tr>
      <th>Driver ID</th>
      <th>Total Earnings</th>
      <th>Completed Rides</th>
      <th>Last Payment Date</th>
    </tr>

    <% if (earningsList != null && !earningsList.isEmpty()) { %>
    <% for (EarningsDTO earnings : earningsList) { %>
    <tr>
      <td><%= earnings.getDriverId() %></td>
      <td>$<%= earnings.getTotalEarnings() %></td>
      <td><%= earnings.getCompletedRides() %></td>
      <td><%= earnings.getLastPaymentDate() %></td>
    </tr>
    <% } %>
    <% } else { %>
    <tr><td colspan="4">No earnings data available.</td></tr>
    <% } %>
  </table>

  <a href="<%= request.getContextPath() %>/views/dashboards/admin-dashboard.jsp">Back to Dashboard</a>
</div>
</body>
</html>
