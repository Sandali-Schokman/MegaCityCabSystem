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
  List<EarningsDTO> earningsReport = (List<EarningsDTO>) request.getAttribute("earningsReport");
%>

<html>
<head>
  <title>Admin - Earnings Report</title>
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>
<%@ include file="../dashboards/admin-dashboard.jsp" %>

<div class="container">
  <h2>Earnings Report</h2>

  <% if (earningsReport != null && !earningsReport.isEmpty()) { %>
  <table border="1">
    <tr>
      <th>Driver ID</th>
      <th>Total Earnings (LKR)</th>
      <th>Completed Rides</th>
    </tr>
    <% for (EarningsDTO earning : earningsReport) { %>
    <tr>
      <td><%= earning.getDriverId() %></td>
      <td>LKR <%= earning.getTotalEarnings() %></td>
      <td><%= earning.getCompletedRides() %></td>
    </tr>
    <% } %>
  </table>
  <% } else { %>
  <p>No earnings data available.</p>
  <% } %>


  <a href="<%= request.getContextPath() %>/views/dashboards/<%= "ADMIN".equals(role) ? "admin-dashboard.jsp" : "operator-dashboard.jsp" %>">Back to Dashboard</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
