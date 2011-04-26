package com.jsar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.http.EntryImpl;
import com.jsar.client.http.HttpRequestCallback;
import com.jsar.client.http.HttpDataFormatter;
import com.jsar.client.unit.YapoolRegistrationFormGenerator;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class YapoolGWT implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network " + "connection and try again.";


  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    new YapoolRegistrationFormGenerator();    
    
  
  
}
}