package org;

import java.io.File;
import java.net.URL;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBUtils {
  private static SessionFactory sessionFactory;
  static {
    URL url = DBUtils.class.getClassLoader().getResource("hibernate.cfg.xml");
    File hibernateCfg = null;
    try {
      if (url == null) {
        throw new Exception();
      }
      hibernateCfg = new File(url.toURI());
    } catch (Exception e) {
      e.printStackTrace();
      // TODO: use file name
      throw new HibernateException("File not found");
    }
    Configuration config = new Configuration().configure(hibernateCfg);
    config.setProperty("hibernate.connection.url", System.getenv("rds_url"));
    config.setProperty("hibernate.connection.username", System.getenv("rds_username"));
    config.setProperty("hibernate.connection.password", System.getenv("rds_pwd"));
    sessionFactory = config.buildSessionFactory();
  }

  public static Session getSession() {
    return sessionFactory.openSession();
  }
}
