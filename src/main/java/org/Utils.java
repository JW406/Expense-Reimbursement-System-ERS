package org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

final public class Utils {
  public static Boolean apiEndPointMatch(HttpServletRequest req, String seg) {
    String[] _endPoint = req.getRequestURI().split("api/");
    String endPoint = _endPoint[_endPoint.length - 1];
    endPoint = endPoint.toLowerCase().intern();
    return endPoint.startsWith(seg);
  }

  public static String readReqBody(HttpServletRequest req) {
    String inputLine;
    String str = "";
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
      while ((inputLine = reader.readLine()) != null) {
        str += inputLine;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return str;
  }
}