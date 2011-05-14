package com.jsar.client.util;

import com.google.gwt.user.client.Window;
import com.jsar.client.YapoolGWT;

public class CheckLoggedIn {

  public static boolean userIsloggedIn() {
    if (YapoolGWT.currentSession.getName() == null) {
      Window.alert("you need to be logged in to do this action");
      return false;
    } else
      return true;
  }

}
