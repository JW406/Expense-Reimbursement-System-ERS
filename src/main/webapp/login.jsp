<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="login-window mt-5">
    <form id="login">
      <div class="form-group">
        <label for="exampleInputEmail1">Email address</label>
        <input type="email" class="form-control" id="emailInput" aria-describedby="emailHelp">
        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" class="form-control" id="passwordInput">
      </div>
      <div class="form-group form-check">
        <input type="checkbox" class="form-check-input" id="exampleCheck1">
        <label class="form-check-label" for="exampleCheck1">Remember Me</label>
      </div>
      <div class="w-100 d-flex justify-content-around">
        <button type="submit" class="btn btn-primary" id="loginBtn">Login</button>
        <button class="btn btn-primary" id="registerBtn">Register</button>
      </div>
    </form>
  </div>
  <script>
    document.getElementById('login').addEventListener('submit', (ev) => {
      ev.preventDefault()
      const email = $('#emailInput').val()
      const password = $('#passwordInput').val()
      fetch(window.__ctx + '/api/login', {
        method: 'POST',
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify({ email, password }),
      }
      ).then((resp) => resp.json()).then((d) => {
        console.log(d)
      })
    })

  </script>
  <jsp:include page="footer.jsp" />
