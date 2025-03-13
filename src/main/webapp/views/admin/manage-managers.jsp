<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/13/2025
  Time: 6:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.UserDTO" %>

<%
    List<UserDTO> managers = (List<UserDTO>) request.getAttribute("managers");
%>

<html>
<head>
    <title>Manage Managers<</title>
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
    <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>
<jsp:include page="../dashboards/admin-dashboard.jsp"/>

<div class="container">
    <h2>Manage Managers</h2>

    <% if (managers != null && !managers.isEmpty()) { %>
    <table border="1">
        <tr>
            <th>Manager ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Full Name</th>
            <th>Phone</th>
            <th>Action</th>
        </tr>
        <% for (UserDTO manager : managers) { %>
        <tr>
            <td><%= manager.getUserId() %></td>
            <td><%= manager.getUsername() %></td>
            <td><%= manager.getEmail() %></td>
            <td><%= manager.getFullName() %></td>
            <td><%= manager.getPhone() %></td>
            <td>
                <form action="<%= request.getContextPath() %>/DeleteManagerServlet" method="POST">
                    <input type="hidden" name="manager_id" value="<%= manager.getUserId() %>">
                    <button type="submit">Remove</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>No managers found.</p>
    <% } %>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
