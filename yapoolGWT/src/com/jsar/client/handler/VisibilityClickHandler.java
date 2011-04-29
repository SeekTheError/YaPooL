package com.jsar.client.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;

public class VisibilityClickHandler implements ClickHandler {
  private String elementToManage;

  public VisibilityClickHandler(String elementToManage) {
    this.elementToManage = elementToManage;
  }

  @Override
  public void onClick(ClickEvent event) {
    boolean visible = RootPanel.get(elementToManage).isVisible();
    if (visible) {
      RootPanel.get(elementToManage).setVisible(false);
    } else {
      RootPanel.get(elementToManage).setVisible(true);
    }
  }
}
