package com.github.jupittar.vmovier.core.data.entity;

public class User {

  private String auth_key;
  private String avatar;
  private String email;
  private String isadmin;
  private String uid;
  private String username;

  public String getAuth_key() {
    return auth_key;
  }

  public void setAuth_key(String auth_key) {
    this.auth_key = auth_key;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getIsadmin() {
    return isadmin;
  }

  public void setIsadmin(String isadmin) {
    this.isadmin = isadmin;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
