<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Mega City Cab - Home</title>
  <link rel="stylesheet" href="assets/css/styles.css">
  <script>
    function toggleMenu() {
      document.getElementById("nav-links").classList.toggle("active");
    }
  </script>
</head>
<body>
<header>
  <div class="logo">Mega City Cab</div>
  <button class="hamburger" onclick="toggleMenu()">☰</button>
  <nav>
    <ul id="nav-links">
      <li><a href="index.jsp">Home</a></li>
      <li><a href="views/login.jsp">Book a Ride</a></li>
      <li><a href="views/register.jsp">Sign up</a></li>
      <li><a href="#services">Our Services</a></li>
      <li><a href="#">Contact</a></li>
    </ul>
  </nav>
</header>

<section class="hero">
  <h1>Reliable Cab Service in Colombo</h1>
  <p>Book a ride instantly and travel with ease.</p>
  <a href="views/login.jsp" class="btn">Book Now</a>
</section>

<section class="features" id="services">
  <div class="feature">
    <img src="assets/images/fast.png" alt="Fast Service">
    <h2>Fast & Reliable</h2>
    <p>Get a ride within minutes with our smart booking system.</p>
  </div>
  <div class="feature">
    <img src="assets/images/affordable.png" alt="Affordable Rides">
    <h2>Affordable Rides</h2>
    <p>Enjoy budget-friendly fares with transparent pricing.</p>
  </div>
  <div class="feature">
    <img src="assets/images/safety.jpg" alt="Safety First">
    <h2>Safety First</h2>
    <p>Trained drivers and secure rides for your peace of mind.</p>
  </div>
</section>

<footer>
  <p>&copy; 2024 Mega City Cab. All rights reserved.</p>
</footer>
</body>
</html>