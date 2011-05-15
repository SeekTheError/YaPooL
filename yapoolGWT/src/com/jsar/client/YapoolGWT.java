package com.jsar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONParser;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.ProfileJson;
import com.jsar.client.json.SessionJson;
import com.jsar.client.unit.CreateRestaurantUnit;
import com.jsar.client.unit.CreateYapoolUnit;
import com.jsar.client.unit.DisplayRestaurantUnit;
import com.jsar.client.unit.DisplayYapoolUnit;
import com.jsar.client.unit.ListRestaurantUnit;
import com.jsar.client.unit.ListYapoolUnit;
import com.jsar.client.unit.MyProfileUnit;
import com.jsar.client.unit.NavigationUnit;
import com.jsar.client.unit.YapoolRegisterUnit;
import com.jsar.client.unit.YapoolSignUnit;
import com.jsar.client.unit.EditProfileUnit;

;
/**
 * this class is responsible for loading the independant module, and also for
 * swithing from an loged in to a log out mod
 * 
 * @author rem
 * 
 */

public class YapoolGWT implements EntryPoint {

  public static SessionJson currentSession = null;
  public static ProfileJson currentProfile = null;

  YapoolRegisterUnit registerUnit;
  YapoolSignUnit signUnit;
  private boolean signState = false;
  private boolean componentLoaded = false;

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
	  HttpInterface.doGet("/yapooldb/" + currentSession.getName() + "/", new AbstractRequestCallback() {
	    @Override
	    public void onResponseReceived(Request request2, Response response2) {
	      if (JSONParser.parseStrict(response2.getText()).isObject().containsKey("error")) {
		currentProfile = new ProfileJson();
		HttpInterface.doPostJson("/yapooldb/", currentProfile, new AbstractRequestCallback() {
		  @Override
		  public void onResponseReceived(Request request, Response response) {
		    System.out.println(response.toString());
		    System.out.println("Created Successfully");
		  }
		}); // http doPostJson Ends
	      } else {
		System.out.println("Current Session Name?: " + currentSession.getName());
		System.out.println("Current Profile?: \n" + response2.getText());
		currentProfile = new ProfileJson(response2.getText());
		signUnit.displayCurrentYapoolButton();
	      }
	    }
	  });
	} else {
	  setSignState(false);
	  currentProfile = null;
	}
      }
    });

  }

  public boolean getSignState() {
    return signState;
  }

  /**
   * load the different YaPooL html Unit
   */
  private void loadComponent() {
    registerUnit = new YapoolRegisterUnit();
    signUnit = new YapoolSignUnit(this);
    new ListYapoolUnit();
    new DisplayYapoolUnit();
    new DisplayRestaurantUnit();
    new CreateYapoolUnit();
    new ListRestaurantUnit();
    new CreateRestaurantUnit();
    new NavigationUnit();
    new EditProfileUnit();
    new MyProfileUnit();
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

	  // Setting CurrentProfile
	  HttpInterface.doGet("/yapooldb/" + currentSession.getName() + "/", new AbstractRequestCallback() {
	    @Override
	    public void onResponseReceived(Request request2, Response response2) {
	      // String a = response2.getText().toString();
	      // System.out.println("_------a: " + a);
	      if (JSONParser.parseStrict(response2.getText()).isObject().containsKey("error")) {
		currentProfile = new ProfileJson();
		HttpInterface.doPostJson("/yapooldb/", currentProfile, new AbstractRequestCallback() {
		  @Override
		  public void onResponseReceived(Request request, Response response) {
		    System.out.println(response.toString());
		    System.out.println("Created Successfully");
		  }
		}); // http doPostJson Ends
	      } else {
		System.out.println("Current Session Name?: " + currentSession.getName());
		System.out.println("Current Profile?: \n" + response2.getText());
		currentProfile = new ProfileJson(response2.getText());
	      }
	    }
	  });

	} else {
	  signUnit.signOut();
	  setSignState(false);
	  currentProfile = null;
	}
      }
    });
  }
}
