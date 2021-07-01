<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="<%=request.getContextPath()%>/resources/libraries/js/jquery-3.5.1.slim.min.js"></script>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/libraries/css/bootstrap.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/custom.css">
  <script>window.__ctx = '<%=request.getContextPath()%>'</script>
  <script>
    $(() => {
      if (window.location.pathname === window.__ctx + '/login') {
        return
      }
      const username = window.localStorage.getItem('username')
      if (username != null && username !== "") {
        const navUserName = $('.user-name')
        navUserName.addClass('dropdown-toggle')
        navUserName.attr('data-toggle','dropdown')
        navUserName.attr('aria-haspopup', 'true')
        navUserName.attr('aria-expanded', 'false')
        navUserName.html('')
        navUserName.append($('<span>').html(username))
        navUserName.append('<span class="caret"></span>')
        navUserName.parent().append(`
<div class="dropdown-menu aria-labelledby=" navbarDropdown">
<a class="dropdown-item" href="#">Action</a>
<a class="dropdown-item" href="#">Another action</a>
<div class="dropdown-divider"></div>
<button class="dropdown-item logout-btn">Logout</button>
</div>
`)
      }
    })
  </script>
  <title>Employee Management System</title>
</head>

<body>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="<%=request.getContextPath()%>/home">Reimbursement System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse justify-content-between" id="navbarSupportedContent">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/home">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%=request.getContextPath()%>/requests">View Requests</a>
        </li>
        <li class="nav-item">
          <a class="nav-link">View All Employees</a>
        </li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item dropdown">
          <a href="<%=request.getContextPath()%>/login" class="nav-link user-name" id="navbarDropdown" role="button">
            Login
          </a>
        </li>
      </ul>
  </nav>
