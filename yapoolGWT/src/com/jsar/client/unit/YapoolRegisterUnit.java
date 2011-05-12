package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.handler.VisibilityClickHandler;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;

public class YapoolRegisterUnit {

  public static final String containerId = "registerDiv";

  private Label messageLabel;
  private Button registerButton;
  private PopupPanel popUpPannel;

  public YapoolRegisterUnit() {

    final TextBox loginField = new TextBox();
    loginField.setText("login");
    final TextBox emailField = new TextBox();
    emailField.setText("@kaist.ac.kr");
    emailField.setVisible(true);
    final TextBox passwordField = new PasswordTextBox();
    passwordField.setText("*****");
    messageLabel = new Label();
    messageLabel.setVisible(false);
    popUpPannel = new PopupPanel();
    popUpPannel.setAutoHideEnabled(true);

    Label displayRegisterPopUp = new Label("No account yet? click HERE to register to YaPooL!");
    displayRegisterPopUp.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
	if (!popUpPannel.isShowing()) {
	  popUpPannel.show();
	}
      }
    });

    RootPanel.get("displayRegisterButtonContainer").add(displayRegisterPopUp);
    VerticalPanel verticalPannel = new VerticalPanel();
    Label registerLabel = new Label("Register",false);
    registerLabel.setHorizontalAlignment(null);
    verticalPannel.add(registerLabel);
    registerLabel.addClickHandler(new VisibilityClickHandler("registerTable"));
    
    registerButton = new Button("Register");
    
    verticalPannel.add(loginField);
    verticalPannel.add(emailField);
    verticalPannel.add(passwordField);
    verticalPannel.add(registerButton);
    verticalPannel.add(messageLabel);

    verticalPannel.add(messageLabel);
    popUpPannel.add(verticalPannel);
    popUpPannel.setPopupPosition(450, 200);

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
    RootPanel.get("displayRegisterButtonContainer").setVisible(visibility);
  }
}
