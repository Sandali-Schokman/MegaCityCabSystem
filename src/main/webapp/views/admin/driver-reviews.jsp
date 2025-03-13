<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/5/2025
  Time: 11:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.ReviewDTO" %>

<%
  String error = request.getParameter("error");
  String message = request.getParameter("message");
  List<ReviewDTO> reviews = (List<ReviewDTO>) request.getAttribute("reviews");

  // Determine the dashboard based on user role
  String role = (String) session.getAttribute("role");
  String dashboardPage = "../dashboards/admin-dashboard.jsp"; // Default to Admin
  if ("MANAGER".equals(role)) {
    dashboardPage = "../dashboards/operator-dashboard.jsp"; // Change to Manager's dashboard
  }
%>

<html>
<head>
  <title>Driver Reviews</title>
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
  <script>
    // Function to filter reviews dynamically
    function filterReviews() {
      let input = document.getElementById("search").value.toLowerCase();
      let tableRows = document.querySelectorAll("#reviewsTable tbody tr");

      tableRows.forEach(row => {
        let driverId = row.cells[1].innerText.toLowerCase();
        let customerId = row.cells[2].innerText.toLowerCase();
        let feedback = row.cells[4].innerText.toLowerCase();

        if (driverId.includes(input) || customerId.includes(input) || feedback.includes(input)) {
          row.style.display = "";
        } else {
          row.style.display = "none";
        }
      });
    }
  </script>
</head>
<body>
<jsp:include page="<%= dashboardPage %>"/>

<div class="container">
  <h2>Driver Reviews & Ratings</h2>

  <% if (error != null) { %>
  <p class="error-msg"><%= error %></p>
  <% } else if (message != null) { %>
  <p class="success-msg"><%= message %></p>
  <% } %>

  <% if (reviews != null && !reviews.isEmpty()) { %>
  <%-- Search Filter --%>
  <label for="search">Filter by Driver ID, Customer ID, or Feedback:</label>
  <input type="text" id="search" onkeyup="filterReviews()" placeholder="Search reviews...">

  <%-- Reviews Table --%>
  <table id="reviewsTable" border="1">
    <thead>
    <tr>
      <th>Review ID</th>
      <th>Driver ID</th>
      <th>Customer ID</th>
      <th>Rating</th>
      <th>Feedback</th>
      <th>Review Date</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <% for (ReviewDTO review : reviews) { %>
    <tr>
      <td><%= review.getReviewId() %></td>
      <td><%= review.getDriverId() %></td>
      <td><%= review.getCustomerId() %></td>
      <td><%= review.getRating() %> ⭐</td>
      <td><%= review.getFeedback() %></td>
      <td><%= review.getReviewDate() %></td>
      <td>
        <form action="<%= request.getContextPath() %>/deleteReview" method="POST" style="display:inline;">
          <input type="hidden" name="review_id" value="<%= review.getReviewId() %>">
          <button type="submit" onclick="return confirm('Are you sure you want to delete this review?');">🗑 Remove</button>
        </form>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
  <% } else { %>
  <p>No reviews found.</p>
  <% } %>

  <a href="<%= request.getContextPath() %>/views/dashboards/<%= "ADMIN".equals(role) ? "admin-dashboard.jsp" : "operator-dashboard.jsp" %>">Back to Dashboard</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
