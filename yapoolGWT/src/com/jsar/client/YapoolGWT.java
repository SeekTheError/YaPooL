package com.jsar.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.RestaurantJson;
import com.jsar.client.json.SessionJson;
import com.jsar.client.unit.ListRestaurantUnit;
import com.jsar.client.unit.ListYapoolUnit;
import com.jsar.client.unit.MessageUnit;
import com.jsar.client.unit.NavigationUnit;
import com.jsar.client.unit.YapoolRegisterUnit;
import com.jsar.client.unit.YapoolSignUnit;
import com.jsar.client.unit.DisplayYapoolUnit;
import com.jsar.client.unit.CreateRestaurantUnit;;
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
  private ListRestaurantUnit listRestaurantUnit;
  private DisplayYapoolUnit displayYapoolUnit;
  private NavigationUnit navigationUnit;

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
   
    
    navigationUnit=new NavigationUnit();
    listRestaurantUnit= new ListRestaurantUnit();
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
      
      RestaurantJson rs=new RestaurantJson();
      rs.setAddress("Address A");
      rs.setName("restaurant A");
      rs.setTelephoneNumber("042-350-1111");
      ArrayList typeOfFoods= new ArrayList();
      typeOfFoods.add("AAtype");
      typeOfFoods.add("ABtype");
      typeOfFoods.add("ACtype");
      rs.setTypeOfFood(typeOfFoods);
      
      HttpInterface.doPostJson("/yapooldb/", rs, new RestaurantPostRequestCallback());
      
      rs.setAddress("Address B");
      rs.setName("restaurant B");
      rs.setTelephoneNumber("042-350-2222");
      ArrayList typeOfFoods2= new ArrayList();
      typeOfFoods.add("BAtype");
      typeOfFoods.add("BBtype");
      typeOfFoods.add("BCtype");
      rs.setTypeOfFood(typeOfFoods);
      
      HttpInterface.doPostJson("/yapooldb/", rs, new RestaurantPostRequestCallback());
      
      rs.setAddress("Address A");
      rs.setName("restaurant A");
      rs.setTelephoneNumber("042-350-3333");
      ArrayList typeOfFoods3= new ArrayList();
      typeOfFoods.add("AAtype");
      typeOfFoods.add("ABtype");
      typeOfFoods.add("ACtype");
      rs.setTypeOfFood(typeOfFoods);
      
      HttpInterface.doPostJson("/yapooldb/", rs, new RestaurantPostRequestCallback());
      
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

class RestaurantPostRequestCallback extends AbstractRequestCallback {
	public void onResponseReceived(Request request, Response response) {
		System.out.println(response.toString());
	}
}
