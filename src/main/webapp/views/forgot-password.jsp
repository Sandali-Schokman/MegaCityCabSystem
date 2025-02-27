<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/27/2025
  Time: 1:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password - Mega City Cab</title>
    <link rel="stylesheet" href="../assets/css/login.css">
</head>
<body>
<div class="login-container">
    <div class="login-box">
        <h2>Reset Your Password</h2>
        <form action="<%= request.getContextPath() %>/forgotPassword" method="POST">
            <div class="input-group">
                <input type="email" name="email" placeholder="Enter your email" required>
            </div>
            <button type="submit">Submit</button>
            <p class="error-msg">
                <% if (request.getParameter("message") != null) { %>
                <%= request.getParameter("message") %>
                <% } %>
            </p>
            <p><a href="<%= request.getContextPath() %>/views/login.jsp">Back to Login</a></p>
        </form>
    </div>
</div>
</body>
</html>
