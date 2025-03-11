<%--
  Created by IntelliJ IDEA.
  User: Sandali Schokman
  Date: 3/9/2025
  Time: 12:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, dto.PaymentDTO" %>

<%
  String error = request.getParameter("error");
  String message = request.getParameter("message");
  List<PaymentDTO> payments = (List<PaymentDTO>) request.getAttribute("payments");
%>

<html>
<head>
  <title>Verify Online Transfers</title>
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/cabStyle.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard.css">
  <link rel="stylesheet" href="../../MegaCityCabSystem_war_exploded/assets/css/dashboard-header.css">
</head>
<body>
<jsp:include page="../dashboards/admin-dashboard.jsp"/>

<div class="container">
  <h2>Verify Online Bank Transfers</h2>

  <% if (error != null) { %>
  <p class="error-msg"><%= error %></p>
  <% } else if (message != null) { %>
  <p class="success-msg"><%= message %></p>
  <% } %>

  <% if (payments != null && !payments.isEmpty()) { %>
  <table border="1">
    <tr>
      <th>Payment ID</th>
      <th>Booking ID</th>
      <th>Amount</th>
      <th>Payment Method</th>
      <th>Status</th>
      <th>Action</th>
    </tr>
    <% for (PaymentDTO payment : payments) { %>
    <tr>
      <td><%= payment.getPaymentId() %></td>
      <td><%= payment.getBookingId() %></td>
      <td>LKR <%= payment.getAmount() %></td>
      <td><%= payment.getMethod() %></td>
      <td><%= payment.getVerificationStatus() %></td>
      <td>
        <% if ("BANK_TRANSFER".equals(payment.getMethod()) && "PENDING".equals(payment.getVerificationStatus())) { %>
        <form action="<%= request.getContextPath() %>/verifyOnlineTransfer" method="POST">
          <input type="hidden" name="payment_id" value="<%= payment.getPaymentId() %>">
          <select name="status">
            <option value="APPROVED">Approve</option>
            <option value="REJECTED">Reject</option>
          </select>
          <button type="submit">Update</button>
        </form>
        <% } else { %>
        <span>Verified</span>
        <% } %>
      </td>
    </tr>
    <% } %>
  </table>
  <% } else { %>
  <p>No pending online transfers.</p>
  <% } %>

  <a href="<%= request.getContextPath() %>/views/dashboards/admin-dashboard.jsp">Back to Dashboard</a>
</div>
<%@ include file="../dashboards/dashboard-footer.jsp" %>
</body>
</html>
