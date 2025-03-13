<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/2/2025
  Time: 1:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Register Driver</title>
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/register.css">
  <link rel="stylesheet" href="../../assets/css/cabStyle.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>
<%@ include file="../dashboards/operator-dashboard.jsp" %>

<div class="container">
  <h2>Register New Driver</h2>

  <%-- Display Messages --%>
  <% if (request.getParameter("error") != null) { %>
  <p class="error-msg"><%= request.getParameter("error") %></p>
  <% } else if (request.getParameter("message") != null) { %>
  <p class="success-msg"><%= request.getParameter("message") %></p>
  <% } %>

  <form action="<%= request.getContextPath() %>/registerDriver" method="POST">
    <label>Full Name</label>
    <input type="text" name="full_name" required>

    <label>Username</label>
    <input type="text" name="username" required>

    <label>Email</label>
    <input type="email" name="email" required>

    <label>Phone</label>
    <input type="text" name="phone" required>

    <label>Address</label>
    <input type="text" name="address" required>

    <label>Password</label>
    <input type="password" name="password" required>

    <label>Car Model</label>
    <input type="text" name="car_model" required>

    <label>License Plate</label>
    <input type="text" name="license_plate" required>

    <button type="submit">Register Driver</button>
  </form>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
