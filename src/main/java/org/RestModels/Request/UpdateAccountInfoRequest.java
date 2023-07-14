package org.RestModels.Request;

public class UpdateAccountInfoRequest {
  private String fullName;
  private String phoneNumber;
  private String gitHubUsername;

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getGitHubUsername() {
    return gitHubUsername;
  }

  public void setGitHubUsername(String gitHubUsername) {
    this.gitHubUsername = gitHubUsername;
  }
}
