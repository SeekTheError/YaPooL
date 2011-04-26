package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jsar.client.http.EntryImpl;
import com.jsar.client.http.HttpDataFormatter;
import com.jsar.client.http.HttpInterface;

public class YapoolRegistrationFormGenerator {

  public YapoolRegistrationFormGenerator()
  {
    
    final TextBox loginField = new TextBox();
    loginField.setText("login");
    final TextBox emailField = new TextBox();
    emailField.setText("email");
    emailField.setVisible(true);
    final TextBox passwordField = new PasswordTextBox();
    passwordField.setText("*******");
    
    passwordField.setVisible(true);
    final Button registerButton = new Button("Register");

  
    RootPanel.get("loginFieldContainer").add(loginField);
    RootPanel.get("emailFieldContainer").add(emailField);
    RootPanel.get("passwordFieldContainer").add(passwordField);
    RootPanel.get("registerButtonContainer").add(registerButton);

 
    registerButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
	String params="?registerLogin="+loginField.getText()+"&register" +
			"Email="+emailField.getText()+"&registerPassword="+passwordField.getText();
	
	
	String url="/security/register/";
	System.out.println(url);
	HttpInterface.doGet(url+params);
	
	HttpInterface.doGet("/yapooldb/");
	
      }
    });
  }

}
