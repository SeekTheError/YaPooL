package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.jsar.client.handler.VisibilityClickHandler;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;

public class YapoolRegisterUnit {

  public static final String containerId = "registerDiv";

  private Label messageLabel;
  private Button registerButton;

  public YapoolRegisterUnit() {

    final TextBox loginField = new TextBox();
    loginField.setText("");
    final TextBox emailField = new TextBox();
    emailField.setText("@kaist.ac.kr");
    emailField.setVisible(true);
    final TextBox passwordField = new PasswordTextBox();
    passwordField.setText("");
    messageLabel = new Label();
    messageLabel.setVisible(false);

    Label registerLabel = new Label("Register");
    RootPanel.get("registerLabelContainer").add(registerLabel);
    registerLabel.addClickHandler(new VisibilityClickHandler("registerTable"));

    registerButton = new Button("Register");

    RootPanel.get("registerLoginFieldContainer").add(loginField);
    RootPanel.get("registerEmailFieldContainer").add(emailField);
    RootPanel.get("registerPasswordFieldContainer").add(passwordField);
    RootPanel.get("registerButtonContainer").add(registerButton);
    RootPanel.get("registerMessageLabelContainer").add(messageLabel);

    RootPanel.get("registerMessageLabelContainer").add(messageLabel);

    registerButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
	registerButton.setEnabled(false);
	registerButton.setText("Sending...");
	String params = "?registerLogin=" + loginField.getText() + "&register" + "Email=" + emailField.getText()
	    + "&registerPassword=" + passwordField.getText();
	String queryUrl = "/security/register/" + params;
	HttpInterface.doGet(queryUrl, new RegistrationRequestCallback());
      }
    });
  }

  class RegistrationRequestCallback extends AbstractRequestCallback {

    @Override
    public void onResponseReceived(Request request, Response response) {
      String message = response.getHeader("message");
      if (message != null) {
	messageLabel.setText(message);
      } else {
	messageLabel.setText("error, no message to display");
      }
      messageLabel.setVisible(true);
      registerButton.setText("Register");
      registerButton.setEnabled(true);
    }
  }

  public void setVisible(boolean visibility) {
    RootPanel.get(containerId).setVisible(visibility);
  }
}
