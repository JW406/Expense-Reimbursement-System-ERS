<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <jsp:include page="header.jsp" />
  <div class="login-window mt-5">
    <form>
      <div class="form-group">
        <label for="exampleInputEmail1">Email address</label>
        <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" class="form-control" id="exampleInputPassword1">
      </div>
      <div class="form-group form-check">
        <input type="checkbox" class="form-check-input" id="exampleCheck1">
        <label class="form-check-label" for="exampleCheck1">Check me out</label>
      </div>
      <div class="w-100 d-flex justify-content-between" style="padding:0 38%">
        <button type="submit" class="btn btn-primary">Login</button>
        <button type="submit" class="btn btn-primary">Register</button>
      </div>
    </form>
  </div>
  <jsp:include page="footer.jsp" />
