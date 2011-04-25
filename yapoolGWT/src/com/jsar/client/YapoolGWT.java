package com.jsar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.http.EntryImpl;
import com.jsar.client.http.HttpRequestCallback;
import com.jsar.client.http.PostDataFormatter;


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

    final FormPanel form = new FormPanel();
    form.setMethod("METHOD_GET");
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
    
    // Create the popup dialog box
    
    //final DialogBox dialogBox = new DialogBox();

 
    registerButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
	messageArea.setVisible(true);
	messageArea.setText("trying to create a new user");
	
	EntryImpl login=new EntryImpl("registerLogin",loginField.getText());
	EntryImpl email=new EntryImpl("registerEmail",emailField.getText());
	EntryImpl password=new EntryImpl("registerPassword",passwordField.getText());
	
	EntryImpl[] queryEntries= new EntryImpl[3];
	queryEntries[0]=login;
	queryEntries[1]=email;
	queryEntries[2]=password;
	
	
	
	String postData=PostDataFormatter.buildQueryString(queryEntries);
	messageArea.setText(messageArea.getText()+"\nposting the Datas:\n"+postData);
	HttpRequestCallback requestCallback=HttpInterface.doPost("http://localhost:8001/security/register/", postData);
	while(requestCallback.getLastResponseHeader()==null)
	  System.out.print(".");
	messageArea.setText(messageArea.getText()+"\nResponse Received"+requestCallback.getLastResponseHeader());
	
      }
    });
  }
  
  
  
}
