package org.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.Utils;

class User {
  private String name;
  private Integer age;

  public User() {
  }

  public User(String name, Integer age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
}

public class Api extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    PrintWriter out = resp.getWriter();
    if (Utils.apiEndPointMatch(req, "login")) {
      String body = Utils.readReqBody(req);
      User user = new User("foo", 16);
      ObjectMapper mapper = new ObjectMapper();
      String userjson = mapper.writeValueAsString(user);
      out.println(userjson);
      out.println(body);
    } else if (Utils.apiEndPointMatch(req, "requestsTable")) {

    }
    out.close();
  }
}
