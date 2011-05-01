package com.jsar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.SessionObject;
import com.jsar.client.unit.YapoolRegisterUnit;
import com.jsar.client.unit.YapoolSignUnit;

/**
 * this class is responsible for loading the independant module, and also to
 * manage the global layout
 * 
 * @author rem
 * 
 */

public class YapoolGWT implements EntryPoint {
  
  YapoolRegisterUnit registerUnit;
  YapoolSignUnit signUnit;
  private boolean signState = false;
  private SessionObject currentSession = null;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    HttpInterface.doGet("/_session", new AbstractRequestCallback() {
      @Override
      public void onResponseReceived(Request request, Response response) {
	currentSession = new SessionObject(response.getText());
	loadComponent();
	if (currentSession.getName() != null) {
	  setSignState(true);
	} else {
	  setSignState(false);
	}
	
      }
    });
  }

  public boolean getSignState() {
    return signState;
  }

  private void loadComponent() {
    registerUnit = new YapoolRegisterUnit();
    signUnit = new YapoolSignUnit(this);
  }

  public SessionObject getCurrentSession() {
    return currentSession;
  }

  public void setCurrentSession(SessionObject currentSession) {
    this.currentSession = currentSession;
  }

  /**
   * 
   * @param state
   *          true for logged in, false otherwise
   */
  public void setSignState(boolean state) {
    signState = state;
    if (state == true) {
      signUnit.signIn();
      registerUnit.setVisible(false);
    } else {
      currentSession.setName(null);
      signUnit.signOut();
      registerUnit.setVisible(true);
    }
  }
  public void loadSession(String jsonString){
    currentSession=new SessionObject(jsonString);
  }
  
}