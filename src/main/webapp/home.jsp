<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="container">
    <div class="jumbotron">
      <h1 class="display-4">Hello, world!</h1>
      <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to
        featured content or information.</p>
      <hr class="my-4">
      <p>It uses utility classes for typography and spacing to space content out within the larger container.</p>
      <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
    </div>
  </div>
  <script>
    $(() => {
      const navUserName = $('.user-name')
      navUserName.addClass('dropdown-toggle')
      const username = window.localStorage.getItem('username')
      if (username != null && username !== "") {
        navUserName.html('')
        navUserName.append($('<span>').html(username))
        navUserName.append('<span class="caret"></span>')
        navUserName.parent().append(`
<div class="dropdown-menu aria-labelledby=" navbarDropdown">
<a class="dropdown-item" href="#">Action</a>
<a class="dropdown-item" href="#">Another action</a>
<div class="dropdown-divider"></div>
<a class="dropdown-item" href="#">Something else here</a>
</div>
        `)
      }
    })
  </script>
  <jsp:include page="footer.jsp" />
