<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/25/2025
  Time: 3:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Mega City Cab</title>
    <link rel="stylesheet" href="../assets/css/register.css">
    <script defer src="../assets/js/register.js" ></script>
</head>
<body>
<div class="container">
    <form action="<%= request.getContextPath()%>/register" method="post" class="register-form" onsubmit="return validateForm()">
        <h2>Sign Up</h2>

        <div class="input-group">
            <label>Full Name</label>
            <input type="text" name="full_name" required>
        </div>

        <div class="input-group">
            <label>Username</label>
            <input type="text" name="username" required>
        </div>

        <div class="input-group">
            <label>Email</label>
            <input type="email" name="email" required pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$">
        </div>

        <div class="input-group">
            <label>Phone</label>
            <input type="text" name="phone" required pattern="^\+?[0-9]{10,15}$">
        </div>

        <div class="input-group">
            <label>Address</label>
            <input type="text" name="address" required>
        </div>

        <div class="input-group">
            <label>Password</label>
            <input type="password" name="password" id="password" required>
        </div>

        <div class="input-group">
            <label>Confirm Password</label>
            <input type="password" name="confirm_password" id="confirm_password" required>
        </div>

        <button type="submit" class="btn">Register</button>
        <p>Already have an account? <a href="<%= request.getContextPath() %>/views/login.jsp">Login here</a></p>
    </form>
</div>

</body>
</html>
