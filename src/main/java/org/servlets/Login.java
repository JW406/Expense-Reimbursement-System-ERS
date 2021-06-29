package org.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.DBUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.TestObject;

public class Login extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Session sess = null;
    Transaction tx = null;

    sess = DBUtils.getSession();
    tx = sess.beginTransaction();
    int n = new Random().nextInt(5555);
    sess.save(new TestObject(n, "foobar" + n));
    tx.commit();
    sess.close();

    sess = DBUtils.getSession();
    tx = sess.beginTransaction();
    TestObject to = (TestObject) sess.get(TestObject.class, n);
    System.out.println(to);

    tx.commit();
    sess.close();

    req.getRequestDispatcher("/login.jsp").forward(req, resp);
  }
}
