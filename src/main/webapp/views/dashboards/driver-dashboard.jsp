<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/26/2025
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="utils.SessionUtils" %>

<jsp:include page="../session-check.jsp"/>

<%
    String role = SessionUtils.getUserRole(request);
    if (!"DRIVER".equals(role)) {
        response.sendRedirect("../unauthorized.jsp");
        return;
    }
%>

<html>
<head>
    <title>driver-dashboard</title>
    <link rel="stylesheet" href="../../assets/css/dashboard.css">
</head>
<body>
<%@ include file="dashboard-header.jsp" %>

<div class="dashboard">
    <h1>Driver Dashboard</h1>
    <p>Manage ride requests, update availability, and track earnings.</p>

    <%-- Display success or error messages --%>
    <% if (request.getParameter("message") != null) { %>
    <p class="success-msg"><%= request.getParameter("message") %></p>
    <% } else if (request.getParameter("error") != null) { %>
    <p class="error-msg"><%= request.getParameter("error") %></p>
    <% } %>

    <div class="dashboard-options">
        <a href="<%= request.getContextPath() %>/driver/assignedRides">View Assigned Rides</a>
        <a href="<%= request.getContextPath() %>/earnings">View Earnings</a>
        <a href="<%= request.getContextPath() %>/verifyCash">Verify Cash</a>
    </div>

    <%-- Availability Update Form --%>
    <div class="availability-form">
        <h2>Update Availability</h2>
        <form action="<%= request.getContextPath() %>/updateAvailability" method="POST">
            <label for="status">Select Status:</label>
            <select name="status" id="status" required>
                <option value="AVAILABLE">Available</option>
                <option value="IN_A_HIRE">In a Hire</option>
                <option value="OFF">Off Duty</option>
            </select>
            <button type="submit">Update</button>
        </form>
    </div>

</div>
<%@ include file="dashboard-footer.jsp" %>

</body>
</html>
