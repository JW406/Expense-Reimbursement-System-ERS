  <div class="overlay">Loading...</div>
  <script>
    if (getIsAdmin()) {
      $('#navbar-header .navbar-nav:first-child').append(`
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/view-all-employees">View All Employees</a>
        </li>
      `)
    }
  </script>
  <script src="<%=request.getContextPath()%>/resources/libraries/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath()%>/resources/scripts/index.js"></script>
</body>

</html>
