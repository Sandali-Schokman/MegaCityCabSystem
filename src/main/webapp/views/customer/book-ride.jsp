<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/6/2025
  Time: 9:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Book a Ride</title>
    <link rel="stylesheet" href="../../assets/css/booking.css">
    <link rel="stylesheet" href="../../assets/css/cabStyle.css">
    <link rel="stylesheet" href="../../assets/css/dashboard.css">
    <link rel="stylesheet" href="../../assets/css/dashboard-header.css">
</head>
<body>
<div>
<%@ include file="../dashboards/customer-dashboard.jsp" %>
</div>
<div class="container">
    <h2>Book a Ride</h2>

    <%-- Display success or error messages --%>
    <% if (request.getParameter("error") != null) { %>
    <p class="error-msg"><%= request.getParameter("error") %></p>
    <% } else if (request.getParameter("message") != null) { %>
    <p class="success-msg"><%= request.getParameter("message") %></p>
    <% } %>

    <%-- Fare Calculation Form --%>
    <form action="<%= request.getContextPath() %>/calculateFare" method="POST" id="one">
        <label>Pickup Location</label>
        <input type="text" name="pickup" value="<%= request.getAttribute("pickup") != null ? request.getAttribute("pickup") : "" %>" required>
        <label>Dropoff Location</label>
        <input type="text" name="dropoff" value="<%= request.getAttribute("dropoff") != null ? request.getAttribute("dropoff") : "" %>" required>

        <label>Distance (KM) <small>(Leave empty for fixed route pricing)</small></label>
        <input type="number" step="0.1" name="distance" value="<%= request.getAttribute("distance") != null ? request.getAttribute("distance") : "" %>">

        <label>Scheduled Time</label>
        <input type="datetime-local" id="scheduled_time" name="scheduled_time" required>

        <button type="submit">Calculate Fare</button>
    </form>

    <%-- Display the calculated fare if available --%>
    <% if (request.getAttribute("calculatedFare") != null) { %>
    <p><strong>Estimated Fare:</strong> LKR <%= request.getAttribute("calculatedFare") %></p>
    <% } %>

    <%-- Booking Confirmation Form --%>
    <form action="<%= request.getContextPath() %>/booking" method="POST">
        <input type="hidden" name="pickup_location" value="<%= request.getAttribute("pickup") != null ? request.getAttribute("pickup") : "" %>">
        <input type="hidden" name="dropoff_location" value="<%= request.getAttribute("dropoff") != null ? request.getAttribute("dropoff") : "" %>">
        <input type="hidden" name="distance_km" value="<%= request.getAttribute("distance") != null ? request.getAttribute("distance") : "" %>">
        <input type="hidden" name="fare" value="<%= request.getAttribute("calculatedFare") != null ? request.getAttribute("calculatedFare") : "" %>">
        <input type="hidden" name="scheduled_time" value="<%= request.getParameter("scheduled_time") %>">

        <button type="submit">Confirm Booking</button>
    </form>

    <a href="<%= request.getContextPath() %>/views/dashboards/customer-dashboard.jsp">Back to Dashboard</a>
</div>
<script>
    // Preserve scheduled_time value if user goes back to the page
    document.getElementById('scheduled_time').value = new Date().toISOString().slice(0, 16);
</script>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
