<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/26/2025
  Time: 12:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Mega City Cab</title>
    <link rel="stylesheet" href="../assets/css/login.css">
    <script defer src="../assets/js/login.js"></script>
</head>
<body>
<div class="login-container">
    <div class="login-box">
        <h2>Welcome Back</h2>
        <form id="loginForm" action="<%= request.getContextPath() %>/login" method="POST">
            <div class="input-group">
                <input type="text" id="username" name="username" placeholder="Username" required>
            </div>
            <div class="input-group">
                <input type="password" id="password" name="password" placeholder="Password" required>
                <span class="toggle-password" onclick="togglePassword()">👁️</span>
            </div>
            <button type="submit">Login</button>
            <p class="error-msg">
                <% if (request.getParameter("error") != null) { %>
                <%= request.getParameter("error") %>
                <% } %>
            </p>
            <p><a href="<%= request.getContextPath() %>/views/forgot-password.jsp">Forgot Password?</a></p>
        </form>
    </div>
</div>
</body>
</html>
