package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.jsar.client.YapoolGWT;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.RestaurantJson;

public class DisplayRestaurantUnit extends AbstractUnit {
  public static DisplayRestaurantUnit displayRestaurantUnit;

  private String currentRestaurantId;

  private Label restaurantName;
  private Label restaurantAdress;
  private Label restaurantTelephone;

  private HorizontalPanel restaurantTypeOfFood;

  public String getContainerId() {
    return "displayRestaurantContainer";
  }

  public String getUnitName() {
    return "displayRestaurantUnit";
  }

  public void displayRestaurant(String restaurantId) {
    currentRestaurantId = restaurantId;
    NavigationUnit.navigationUnit.hideAll();
    this.SetVisible(true);
    HttpInterface.doGet("/yapooldb/" + restaurantId, new AbstractRequestCallback() {

      @Override
      public void onResponseReceived(Request request, Response response) {
	RestaurantJson restaurantJson = new RestaurantJson(response.getText());
	restaurantName.setText(restaurantJson.getName());
	restaurantAdress.setText(restaurantJson.getAddress());
	restaurantTelephone.setText(restaurantJson.getTelephoneNumber());

	RootPanel pannel = RootPanel.get("restaurantTypeOfFood");
	int size = pannel.getWidgetCount();
	for (int i = 0; i < size; i++) {
	  pannel.remove(pannel.getWidget(i));
	}
	JSONArray typeOfFood = restaurantJson.getTypeOfFood();
	size = typeOfFood.size();
	for (int i = 0; i < size; i++) {
	  pannel.add(new Label(typeOfFood.get(i).isString().stringValue()));
	}
      }
    });
  }

  public DisplayRestaurantUnit() {
    DisplayRestaurantUnit.displayRestaurantUnit = this;
    restaurantName = new Label();
    restaurantAdress = new Label();
    restaurantTelephone = new Label();
    restaurantTypeOfFood = new HorizontalPanel();
    Button createYapool = new Button("Create a YaPooL!");

    RootPanel.get("restaurantNameLabel").add(restaurantName);
    RootPanel.get("restaurantTelephoneLabel").add(restaurantTelephone);
    RootPanel.get("restaurantAdress").add(restaurantAdress);
    RootPanel.get("createYapool").add(createYapool);
    RootPanel.get("restaurantTypeOfFood").add(restaurantTypeOfFood);
    this.SetVisible(false);

    createYapool.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
    	  if(YapoolGWT.currentProfile.getCurrentYapool().equals(""))
    		  CreateYapoolUnit.createYapoolUnit.createYapool(currentRestaurantId);
    	  else{
    		  Window.alert("You already have a joined YaPooL!");
    	  }

      }
    });

  }
}
