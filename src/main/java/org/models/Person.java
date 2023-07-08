package org.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public class Person {
  @Column(unique = true)
  private String email;
  @Column
  private String name;
  @Column
  private String password;
  @Column
  private String gitHubAddress;
  @Column
  private String phoneNumber;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }

  public String getGitHubAddress() {
    return gitHubAddress;
  }

  public void setGitHubAddress(String gitHubAddress) {
    this.gitHubAddress = gitHubAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "Person [email=" + email + ", gitHubAddress=" + gitHubAddress + ", name=" + name + ", password=" + password
        + ", phoneNumber=" + phoneNumber + "]";
  }
}
