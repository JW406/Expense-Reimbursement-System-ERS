<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp" />

  <div class="register-window mt-5">
    <form id="register">
      <div class="form-group">
        <label for="exampleInputEmail1">Email address</label>
        <input type="email" class="form-control" id="emailInput" aria-describedby="emailHelp">
      </div>
      <div class="form-group">
        <label for="your-name">Name</label>
        <input type="text" class="form-control" id="your-name"">
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" class="form-control" id="passwordInput">
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Repeat Password</label>
        <input type="password" class="form-control" id="repeatPasswordInput">
      </div>
      <div class="err-msg"> </div>
      <div class="w-100 d-flex justify-content-around">
        <button type="submit" class="btn btn-primary" id="register">Register</button>
        <a href="<%=request.getContextPath()%>/login" class="btn btn-primary" id="registerBtn">Login with existing account</a>
      </div>
    </form>
  </div>
  <script>
    document.getElementById('register').addEventListener('submit', (ev) => {
      $('.err-msg').text('')
      ev.preventDefault()
      const email = ev.target['emailInput'].value
      const name = ev.target['your-name'].value
      const password = ev.target['passwordInput'].value
      const password2 = ev.target['repeatPasswordInput'].value
      if (password !== password2) {
        $('.err-msg').text('Passwords don\'t match')
      } else {
        fetch(window.__ctx + '/api/register', {
          method: 'POST',
          headers: {
            "Content-type": "application/json; charset=UTF-8",
          },
          body: JSON.stringify({ email, password, name }),
        }
        ).then((resp) => resp.json()).then((d) => {
          if (d['isSuccess']) {
            ev.target['passwordInput'].value = ''
            ev.target['repeatPasswordInput'].value = ''
            alert(d['msg'])
          } else {
            $('.err-msg').text(d.msg)
          }
        })
      }
    })

  </script>
<jsp:include page="footer.jsp" />
