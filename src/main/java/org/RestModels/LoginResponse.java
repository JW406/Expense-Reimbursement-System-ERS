package org.RestModels;

public class LoginResponse extends Response {
  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
