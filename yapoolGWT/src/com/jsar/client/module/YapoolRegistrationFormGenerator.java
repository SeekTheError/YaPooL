package com.jsar.client.module;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jsar.client.http.EntryImpl;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.http.HttpRequestCallback;
import com.jsar.client.http.HttpDataFormatter;

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
    final TextArea messageArea=new TextArea();
    messageArea.setVisible(false);
    
    passwordField.setVisible(true);
    final Button registerButton = new Button("Register");

  
    RootPanel.get("loginFieldContainer").add(loginField);
    RootPanel.get("emailFieldContainer").add(emailField);
    RootPanel.get("passwordFieldContainer").add(passwordField);
    RootPanel.get("registerButtonContainer").add(registerButton);
    RootPanel.get("messageAreaContainer").add(messageArea);

 
    registerButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
	messageArea.setVisible(true);
	messageArea.setText("trying to create a new user");
	
	String params="?registerLogin="+loginField.getText()+"&register" +
			"Email="+emailField.getText()+"&registerPassword="+passwordField.getText();
	/*
	EntryImpl login=new EntryImpl("registerLogin",loginField.getText());
	EntryImpl email=new EntryImpl("registerEmail",emailField.getText());
	EntryImpl password=new EntryImpl("registerPassword",passwordField.getText());
	
	EntryImpl[] queryEntries= new EntryImpl[3];
	queryEntries[0]=login;
	queryEntries[1]=email;
	queryEntries[2]=password;
	
	String postData=HttpDataFormatter.buildQueryString(queryEntries);
	*/
	messageArea.setText(messageArea.getText()+"\nposting the Datas:\n"+params);
	String url="http://127.0.0.1:8888/django/security/register/"+params;
	System.out.println(url);
	HttpInterface.doGet(url);
	
      }
    });
  }

}
