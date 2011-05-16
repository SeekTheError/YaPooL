package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.jsar.client.YapoolGWT;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.PostJson;
import com.jsar.client.json.RestaurantJson;

public class DisplayRestaurantUnit extends AbstractUnit {
  public static DisplayRestaurantUnit displayRestaurantUnit;

  private String currentRestaurantId;

  private Label restaurantName;
  private Label restaurantAdress;
  private Label restaurantTelephone;

  private FlexTable restaurantTypeOfFood;

  public String getContainerId() {
    return "displayRestaurantContainer";
  }

  public String getUnitName() {
    return "displayRestaurantUnit";
  }

  public void displayRestaurant(String restaurantId) {
    currentRestaurantId = restaurantId;
    NavigationUnit.navigationUnit.hideAll();
    //this.SetVisible(true);
    HttpInterface.doGet("/yapooldb/" + restaurantId, new AbstractRequestCallback() {

      @Override
      public void onResponseReceived(Request request, Response response) {
		RestaurantJson restaurantJson = new RestaurantJson(response.getText());
		restaurantName.setText(restaurantJson.getName());
		restaurantAdress.setText(restaurantJson.getAddress());
		restaurantTelephone.setText(restaurantJson.getTelephoneNumber());
	
		restaurantTypeOfFood.removeAllRows();
		restaurantTypeOfFood.setText(0, 0, "Tags:");
		JSONArray typeOfFood = restaurantJson.getTypeOfFood();
		int size = typeOfFood.size();
		for (int i = 0; i < size; i++) {
			String temp = typeOfFood.get(i).isString().stringValue();
			int rowCounts = restaurantTypeOfFood.getRowCount();
			restaurantTypeOfFood.setText(rowCounts, 0, temp);
		}
		
		DisplayRestaurantUnit.displayRestaurantUnit.SetVisible(true);
      }
    });
  }

  public DisplayRestaurantUnit() {
    DisplayRestaurantUnit.displayRestaurantUnit = this;
    restaurantName = new Label();
    restaurantAdress = new Label();
    restaurantTelephone = new Label();
    restaurantTypeOfFood = new FlexTable();
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
    		  CreateYapoolUnit.createYapoolUnit.createYapool(currentRestaurantId, restaurantTypeOfFood);
    	  else{
    		  Window.alert("You already have a joined YaPooL!");
    	  }

      }
    });

  }
}
