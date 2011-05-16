package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.jsar.client.YapoolGWT;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.EntryImpl;
import com.jsar.client.http.HttpDataFormatter;
import com.jsar.client.http.HttpInterface;

/**
 * 
 * @author rem
 * 
 */
public class YapoolSignUnit {
  public static YapoolSignUnit yapoolSignUnit;

  private Label messageLabel;
  private Button signButton;
  private Label signLabel;
  private TextBox loginField;
  private TextBox passwordField;
  private YapoolGWT yapoolGWT;

  private SignButtonClickHandler signButtonClickHandler;
  private Label myYapoolLabel;

  public YapoolSignUnit(YapoolGWT yapoolGWT) {
    this.yapoolGWT = yapoolGWT;
    YapoolSignUnit.yapoolSignUnit=this;

    loginField = new TextBox();
    passwordField = new PasswordTextBox();
    messageLabel = new Label("");
    messageLabel.setVisible(false);
    signButton = new Button("");
    signLabel = new Label("");
    signLabel.setVisible(true);
    myYapoolLabel = new Label("My YaPooL!");
    myYapoolLabel.setVisible(false);
    RootPanel.get("signMyYapool").add(myYapoolLabel);
    RootPanel.get("signLabelContainer").add(signLabel);
    RootPanel.get("signLoginFieldContainer").add(loginField);
    RootPanel.get("signPasswordFieldContainer").add(passwordField);
    RootPanel.get("signButtonContainer").add(signButton);
    RootPanel.get("signMessageLabelContainer").add(messageLabel);

    if (yapoolGWT.getSignState() == true) {
      signIn();
    } else {
      signOut();
    }

    signButtonClickHandler = new SignButtonClickHandler();
    signButton.addClickHandler(signButtonClickHandler);

    passwordField.addKeyDownHandler(new KeyDownHandler() {

      @Override
      public void onKeyDown(KeyDownEvent event) {
	if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
	  logIn();
	}

      }
    });

    myYapoolLabel.getElement().setClassName("myYapoolLabel");
    myYapoolLabel.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
	DisplayYapoolUnit.displayYapoolUnit.displayYapool(YapoolGWT.currentProfile.getCurrentYapool());

      }
    });
  }

  class SignButtonClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      logIn();

    }
  }

  private static final String queryUrl = "/_session";

  public void logIn() {
    messageLabel.setVisible(false);
    if (yapoolGWT.getSignState() == false) {
      String login = loginField.getText();
      String password = passwordField.getText();
      passwordField.setText("");

      EntryImpl[] entries = { new EntryImpl("name", login), new EntryImpl("password", password) };
      String data = HttpDataFormatter.buildQueryString(entries);

      HttpInterface.doPost(queryUrl, data, new SignRequestCallback());

    } else {
      HttpInterface.doDelete(queryUrl, new SignRequestCallback());
    }
  }

  class SignRequestCallback extends AbstractRequestCallback {

    @Override
    public void onResponseReceived(Request request, Response response) {
      if (yapoolGWT.getSignState() == false) {
	if (SignRequestCallback.responseIsOk(response)) {
	  yapoolGWT.reloadSession();

	} else if (!SignRequestCallback.responseIsOk(response)) {
	  // TODO format this error message
	  messageLabel.setVisible(true);
	  messageLabel.setText(response.getText());
	}
      } else if (yapoolGWT.getSignState() == true) {
	yapoolGWT.reloadSession();
      }
    }
  }

  public void signOut() {
    signButton.setText("Sign in");
    signLabel.setText("Sign In");
    RootPanel.get("signLoginFieldContainer").setVisible(true);
    RootPanel.get("signPasswordFieldContainer").setVisible(true);
    myYapoolLabel.setVisible(false);

  }

  public void signIn() {
    loginField.setText("");
    signButton.setText("sign out");
    signLabel.setText(yapoolGWT.getCurrentSession().getName());
    RootPanel.get("signLoginFieldContainer").setVisible(false);
    RootPanel.get("signPasswordFieldContainer").setVisible(false);
  }

  public void displayCurrentYapoolButton() {
    if (!YapoolGWT.currentProfile.getCurrentYapool().equals("")) {
      myYapoolLabel.setVisible(true);
    } else {
      myYapoolLabel.setVisible(false);
    }
  }

}
