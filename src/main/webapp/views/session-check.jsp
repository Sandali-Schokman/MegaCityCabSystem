<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 2/28/2025
  Time: 2:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.SessionUtils" %>

<%
    if (!SessionUtils.isUserLoggedIn(request)) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Please log in first.");
        return;
    }
%>