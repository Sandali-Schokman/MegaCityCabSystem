<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/2/2025
  Time: 1:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="dto.EarningsDTO" %>

<%
    EarningsDTO earnings = (EarningsDTO) request.getAttribute("earnings");
%>

<html>
<head>
    <title>Driver Earnings</title>
    <link rel="stylesheet" href="../../assets/css/dashboard.css">
</head>
<body>
<%@ include file="../dashboards/driver-dashboard.jsp" %>

<div class="container">
    <h2>Driver Earnings</h2>

    <% if (earnings != null) { %>
    <table border="1">
        <tr>
            <th>Total Earnings (LKR)</th>
            <td>LKR <%= earnings.getTotalEarnings() %></td>
        </tr>
        <tr>
            <th>Driver Earnings (LKR)</th>
            <td>LKR <%= earnings.getDriverEarnings() %></td>
        </tr>
        <tr>
            <th>Company Share (LKR)</th>
            <td>LKR <%= earnings.getCompanyShare() %></td>
        </tr>
        <tr>
            <th>Completed Rides</th>
            <td><%= earnings.getCompletedRides() %></td>
        </tr>
    </table>
    <% } else { %>
    <p>No earnings data available.</p>
    <% } %>

    <a href="<%= request.getContextPath() %>/views/dashboards/driver-dashboard.jsp">Back to Dashboard</a>
</div>

</body>
</html>
