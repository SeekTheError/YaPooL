package com.jsar.client.unit;

import com.google.gwt.user.client.ui.RootPanel;
/**
 * 
 * @author rem
 *
 */
public abstract class AbstractUnit {

  
public void SetVisible(boolean visible){
  RootPanel.get(getContainerId()).setVisible(visible);
}



public abstract String getContainerId();

}
