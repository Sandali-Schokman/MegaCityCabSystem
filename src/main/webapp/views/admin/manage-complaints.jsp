<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/5/2025
  Time: 1:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.ComplaintDTO" %>

<%


  // Fetch complaints from request
  List<ComplaintDTO> complaints = (List<ComplaintDTO>) request.getAttribute("complaints");
  String role = (String) session.getAttribute("role");
  String dashboardPage = "../dashboards/admin-dashboard.jsp"; // Default to Admin

  if ("MANAGER".equals(role)) {
    dashboardPage = "../dashboards/operator-dashboard.jsp"; // Change to Manager's dashboard
  }
%>

<html>
<head>
  <title>Manage Complaints</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/dashboard.css">
</head>
<body>
<%-- Include Admin or Manager Dashboard --%>
<jsp:include page="<%= dashboardPage %>"/>

<div class="container">
  <h2>Manage Complaints</h2>

  <%-- Display success or error messages --%>
  <% if (request.getParameter("message") != null) { %>
  <p class="success-msg"><%= request.getParameter("message") %></p>
  <% } else if (request.getParameter("error") != null) { %>
  <p class="error-msg"><%= request.getParameter("error") %></p>
  <% } %>

  <%-- Display Complaints Table --%>
  <% if (complaints != null && !complaints.isEmpty()) { %>
  <table border="1">
    <tr>
      <th>Complaint ID</th>
      <th>Customer Name</th>
      <th>Booking ID</th>
      <th>Complaint</th>
      <th>Status</th>
      <th>Action</th>
    </tr>
    <% for (ComplaintDTO complaint : complaints) { %>
    <tr>
      <td><%= complaint.getComplaintId() %></td>
      <td><%= complaint.getCustomerName() %></td>
      <td>#<%= complaint.getBookingId() %></td>
      <td><%= complaint.getComplaintText() %></td>
      <td><%= complaint.getStatus() %></td>
      <td>
        <% if ("PENDING".equals(complaint.getStatus())) { %>
        <form action="<%= request.getContextPath() %>/resolveComplaint" method="POST">
          <input type="hidden" name="complaint_id" value="<%= complaint.getComplaintId() %>">
          <button type="submit">Resolve</button>
        </form>
        <% } else { %>
        ✅ Resolved
        <% } %>
      </td>
    </tr>
    <% } %>
  </table>
  <% } else { %>
  <p>No pending complaints available.</p>
  <% } %>

  <a href="<%= request.getContextPath() %>/views/dashboards/admin-dashboard.jsp">Back to Dashboard</a>
</div>
</body>
</html>
