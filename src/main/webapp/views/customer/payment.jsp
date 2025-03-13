<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/12/2025
  Time: 6:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%
    String role = (String) session.getAttribute("role");
    if (!"CUSTOMER".equals(role)) {
        response.sendRedirect("../unauthorized.jsp");
        return;
    }

    String error = request.getParameter("error");
    String message = request.getParameter("message");

    int bookingId = Integer.parseInt(request.getParameter("booking_id"));
    double fare = Double.parseDouble(request.getParameter("fare"));
%>

<html>
<head>
    <title>Make Payment</title>
    <link rel="stylesheet" href="../../assets/css/cabStyle.css">
    <link rel="stylesheet" href="../../assets/css/dashboard.css">
    <link rel="stylesheet" href="../../assets/css/dashboard-header.css">
</head>
<body>
<jsp:include page="../dashboards/customer-dashboard.jsp" />

<div class="container">
    <h2>Payment for Booking #<%= bookingId %></h2>

    <% if (error != null) { %>
    <p class="error-msg"><%= error %></p>
    <% } else if (message != null) { %>
    <p class="success-msg"><%= message %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/payment" method="post">
        <input type="hidden" name="booking_id" value="<%= bookingId %>">
        <input type="hidden" name="amount" value="<%= fare %>">

        <label for="method">Select Payment Method:</label>
        <select name="method" id="method" required>
            <option value="CASH">Cash (Pay to Driver)</option>
            <option value="BANK_TRANSFER">Online Bank Transfer</option>
        </select>

        <p><strong>Total Amount:</strong> LKR <%= fare %></p>

        <button type="submit">Make Payment</button>
    </form>

    <a href="<%= request.getContextPath() %>/views/dashboards/customer-dashboard.jsp">Back to Dashboard</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
