package com.jsar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.SessionJson;
import com.jsar.client.unit.ListYapoolUnit;
import com.jsar.client.unit.MessageUnit;
import com.jsar.client.unit.NavigationUnit;
import com.jsar.client.unit.YapoolRegisterUnit;
import com.jsar.client.unit.YapoolSignUnit;
import com.jsar.client.unit.DisplayYapoolUnit;

/**
 * this class is responsible for loading the independant module, and also for
 * swithing from an loged in to a log out mod
 * 
 * @author rem
 * 
 */

public class YapoolGWT implements EntryPoint {

  public static SessionJson currentSession = null;

  YapoolRegisterUnit registerUnit;
  YapoolSignUnit signUnit;
  private boolean signState = false;

  private boolean componentLoaded = false;

  private ListYapoolUnit listYapoolUnit;
  private DisplayYapoolUnit displayYapoolUnit;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    loadSession();
  }

  public void loadSession() {
    HttpInterface.doGet("/_session", new AbstractRequestCallback() {
      @Override
      public void onResponseReceived(Request request, Response response) {
	currentSession = new SessionJson(response.getText());
	System.out.println(currentSession);
	if (!componentLoaded) {
	  loadComponent();
	  componentLoaded = true;
	}
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

  /*
   * load the different YaPooL html Unit
   */
  private void loadComponent() {
    registerUnit = new YapoolRegisterUnit();
    signUnit = new YapoolSignUnit(this);
    listYapoolUnit = new ListYapoolUnit();
    displayYapoolUnit = new DisplayYapoolUnit();
    new NavigationUnit();
  }

  public SessionJson getCurrentSession() {
    return currentSession;
  }

  public void setCurrentSession(SessionJson currentSession) {
    YapoolGWT.currentSession = currentSession;
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

  public void reloadSession() {
    HttpInterface.doGet("/_session", new AbstractRequestCallback() {
      @Override
      public void onResponseReceived(Request request, Response response) {
	currentSession = new SessionJson(response.getText());
	System.out.println(currentSession.toString());
	if (currentSession.getName() != null) {
	  signUnit.signIn();
	  setSignState(true);
	} else {
	  signUnit.signOut();
	  setSignState(false);
	}
      }
    });
  }

}