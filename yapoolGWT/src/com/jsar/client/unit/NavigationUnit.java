package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.unit.ListYapoolUnit.ListType;
import com.jsar.client.util.CheckLoggedIn;

/**
 * 
 * This class display the left Navigation Bar, and is responsible for the window
 * management
 * 
 * @author rem
 * 
 */
public class NavigationUnit {
  public static NavigationUnit navigationUnit;

  public static final String containerId = "navigationUnitContainer";

  public NavigationUnit() {
    NavigationUnit.navigationUnit = this;

    VerticalPanel yapoolPanel = new VerticalPanel();
    Label listYapoolLabel = new Label("Existing Yapools");
    listYapoolLabel.getElement().setClassName("navigationLabel");
    listYapoolLabel.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
	hideAll();
	ListYapoolUnit.listYapoolUnit.SetVisible(true);
	ListYapoolUnit.listYapoolUnit.listYapools(ListType.ALL);
      }
    });
    yapoolPanel.add(listYapoolLabel);
    RootPanel.get("yapoolNavigationContainer").add(yapoolPanel);
    Label yapoolNavigatonLabel = new Label("YaPooLs");
    RootPanel.get("yapoolNavigationLabel").add(yapoolNavigatonLabel);

    // Label createYapool=new Label("Create a YaPooL");
    VerticalPanel restaurantPanel = new VerticalPanel();

    Label listRestaurantLabel = new Label("Browse Restaurants");
    listRestaurantLabel.getElement().setClassName("navigationLabel");
    listRestaurantLabel.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
	hideAll();
	ListRestaurantUnit.listRestaurantUnit.loadList();
	ListRestaurantUnit.listRestaurantUnit.SetVisible(true);

      }
    });
    restaurantPanel.add(listRestaurantLabel);

    Label displayCreateRestaurantPopUp = new Label("Create a restaurant!");
    displayCreateRestaurantPopUp.getElement().setClassName("navigationLabel");
    displayCreateRestaurantPopUp.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
	CreateRestaurantUnit.createRestaurantUnit.setVisible(true);
      }
    });
    restaurantPanel.add(displayCreateRestaurantPopUp);

    RootPanel.get("restaurantNavigationContainer").add(restaurantPanel);
    Label restaurantNavigationLabel = new Label("Restaurant");
    RootPanel.get("restaurantNavigationLabel").add(restaurantNavigationLabel);

    Label myPageNavigationLabel = new Label("Edit My Profile");
    myPageNavigationLabel.getElement().setClassName("navigationLabel");
    myPageNavigationLabel.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
    	if (!CheckLoggedIn.userIsloggedIn()) {
    	  return;
    	}
    	NavigationUnit.navigationUnit.hideAll();
    	EditProfileUnit.editProfileUnit.SetVisible(true);
    	EditProfileUnit.editProfileUnit.loadProfile();

      }
    });
    Label usersLabel = new Label("Users");
    RootPanel.get("myPageNavigationLabel").add(usersLabel);
    Label myProfileLabel = new Label("My Profile");
    myProfileLabel.getElement().setClassName("navigationLabel");
    RootPanel.get("myProfileNavigetion").add(myProfileLabel);
    myProfileLabel.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
	NavigationUnit.navigationUnit.hideAll();
	MyProfileUnit.myProfileUnit.SetVisible(true);
	MyProfileUnit.myProfileUnit.loadProfile();
      }
    });

    RootPanel.get("editProfileNavigation").add(myPageNavigationLabel);

  }

  public void hideAll() {
    ListYapoolUnit.listYapoolUnit.SetVisible(false);
    DisplayYapoolUnit.displayYapoolUnit.SetVisible(false);
    ListRestaurantUnit.listRestaurantUnit.SetVisible(false);
    DisplayRestaurantUnit.displayRestaurantUnit.SetVisible(false);
    EditProfileUnit.editProfileUnit.SetVisible(false);
    MyProfileUnit.myProfileUnit.SetVisible(false);
  }

}
