package com.jsar.client.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MessageDisplayer {
  static Label messageLabel;

  public static void DisplayMessage(String message) {
    if (messageLabel == null) {
      messageLabel=new Label();
      RootPanel messageContainer = RootPanel.get("messageContainer");
      messageLabel.setTitle("click to hide");
      messageContainer.add(messageLabel);
    }
    messageLabel.setText(message);
    messageLabel.setVisible(true);
    // TODO add some style to the message
    messageLabel.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
	messageLabel.setVisible(false);
      }
    });

  }

}
