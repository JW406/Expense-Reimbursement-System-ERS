<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="login-window mt-5">
    <form id="login">
      <div class="form-group">
        <label for="emailInput">Email address</label>
        <input type="email" class="form-control" id="emailInput" aria-describedby="emailHelp" autocomplete="current-password">
        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
      </div>
      <div class="form-group">
        <label for="passwordInput">Password</label>
        <input type="password" class="form-control" id="passwordInput" autocomplete="current-password">
      </div>
      <div class="form-group form-check">
        <input type="checkbox" class="form-check-input" id="exampleCheck1">
        <label class="form-check-label" for="exampleCheck1">Remember Me</label>
      </div>
      <div class="err-msg"> </div>
      <div class="w-100 d-flex justify-content-around">
        <button type="submit" class="btn btn-primary" id="loginBtn">Login</button>
        <a href="<%=request.getContextPath()%>/register" class="btn btn-primary" id="registerBtn">Register</a>
      </div>
    </form>
  </div>
  <script>
    $(() => {
      document.getElementById('login').addEventListener('submit', (ev) => {
        ev.preventDefault()
        $('.err-msg').text('')
        const email = ev.target['emailInput'].value
        const password = ev.target['passwordInput'].value
        fetch(window.__ctx + '/api/login', {
          method: 'POST',
          headers: {
            "Content-type": "application/json; charset=UTF-8",
          },
          body: JSON.stringify({ email, password }),
        }
        ).then((resp) => resp.json()).then((d) => {
          if (d['isSuccess']) {
            window.localStorage.setItem('username', d['username'])
            window.localStorage.setItem('ismanager', d['isManager'])
            window.location.href = window.__ctx + '/home';
          } else {
            $('.err-msg').text(d.msg)
          }
        })
      })
    })
  </script>
  <jsp:include page="footer.jsp" />
