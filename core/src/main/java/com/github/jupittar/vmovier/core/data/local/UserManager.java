package com.github.jupittar.vmovier.core.data.local;

import com.github.jupittar.vmovier.core.data.entity.User;

public interface UserManager {

  boolean isLoggedIn();

  void setUser(User user);

  User getUser();

  void removeUser();

}
