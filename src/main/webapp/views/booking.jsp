<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/28/2025
  Time: 8:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.SessionUtils" %>

<%
    if (!SessionUtils.isUserLoggedIn(request) || !"CUSTOMER".equals(session.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
        return;
    }
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Book a Ride</title>
    <link rel="stylesheet" href="../assets/css/booking.css">
</head>
<body>

<div class="container">
    <h2>Book a Ride</h2>

    <%-- Display Errors or Success Messages --%>
    <% if (request.getParameter("error") != null) { %>
    <p class="error-msg"><%= request.getParameter("error") %></p>
    <% } else if (request.getParameter("message") != null) { %>
    <p class="success-msg"><%= request.getParameter("message") %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/calculateFare" method="POST">
        <label>Pickup Location</label>
        <input type="text" name="pickup" required>

        <label>Dropoff Location</label>
        <input type="text" name="dropoff" required>

        <label>Distance (KM) <small>(Leave empty for fixed route pricing)</small></label>
        <input type="number" step="0.1" name="distance" required>

        <button type="submit">Calculate Fare</button>
    </form>

    <%-- Display the calculated fare if available --%>
    <% if (request.getAttribute("calculatedFare") != null) { %>
    <p><strong>Estimated Fare:</strong> LKR <%= request.getAttribute("calculatedFare") %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/booking" method="POST">
        <input type="hidden" name="pickup_location" value="<%= request.getParameter("pickup") %>">
        <input type="hidden" name="dropoff_location" value="<%= request.getParameter("dropoff") %>">
        <input type="hidden" name="distance_km" value="<%= request.getParameter("distance") %>">
        <input type="hidden" name="fare" value="<%= request.getAttribute("calculatedFare") %>">

        <button type="submit">Confirm Booking</button>
    </form>

    <p><a href="<%= request.getContextPath() %>/views/dashboards/customer-dashboard.jsp">Back to Dashboard</a></p>
</div>

</body>
</html>