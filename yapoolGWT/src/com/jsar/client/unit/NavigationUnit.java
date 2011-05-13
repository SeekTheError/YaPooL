package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
    Label listYapoolLabel = new Label("list of Yapools");
    listYapoolLabel.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
	hideAll();
	ListYapoolUnit.listYapoolUnit.SetVisible(true);
      }
    });
    yapoolPanel.add(listYapoolLabel);
    RootPanel.get("yapoolNavigationContainer").add(yapoolPanel);
    Label yapoolNavigatonLabel = new Label("YaPooLs");
    RootPanel.get("yapoolNavigationLabel").add(yapoolNavigatonLabel);

    // Label createYapool=new Label("Create a YaPooL");
    VerticalPanel restaurantPanel = new VerticalPanel();
    Label listRestaurantLabel = new Label("list of Restaurants");
    listRestaurantLabel.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
	hideAll();
	ListRestaurantUnit.listRestaurantUnit.SetVisible(true);
      }
    });
    restaurantPanel.add(listRestaurantLabel);
    RootPanel.get("restaurantNavigationContainer").add(restaurantPanel);
    Label restaurantNavigationLabel = new Label("Restaurant");
    RootPanel.get("restaurantNavigationLabel").add(restaurantNavigationLabel);

    Label myPageNavigationLabel = new Label("My Page");
    RootPanel.get("myPageNavigationLabel").add(myPageNavigationLabel);
    VerticalPanel myPagePanel = new VerticalPanel();
    RootPanel.get("myPageNavigationContainer").add(myPagePanel);

  }

  public void hideAll() {
    ListYapoolUnit.listYapoolUnit.SetVisible(false);
    DisplayYapoolUnit.displayYapoolUnit.SetVisible(false);
    ListRestaurantUnit.listRestaurantUnit.SetVisible(false);
    DisplayRestaurantUnit.displayRestaurantUnit.SetVisible(false);
  }

}
