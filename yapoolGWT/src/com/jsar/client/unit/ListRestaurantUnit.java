package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.RestaurantJson;
import com.jsar.client.json.ViewJson;
import com.jsar.client.json.YapoolJson;
import com.jsar.client.unit.ListYapoolUnit.DisplayYapoolClickHandler;
import com.jsar.client.unit.ListYapoolUnit.ListYapoolRequestCallback;

public class ListRestaurantUnit extends AbstractUnit {
  public static ListRestaurantUnit listRestaurantUnit;

  private Label RestaurantNameLabel;
  private FlexTable RestaurantListTable;


  public String getContainerId() {
    return "listRestaurantContainer";
  }

  public ListRestaurantUnit() {
    listRestaurantUnit = this;
    RestaurantListTable = new FlexTable();
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(RestaurantListTable);
    RootPanel.get("listRestaurantContainer").add(verticalPanel);

    RestaurantListTable.setText(0, 0, "Restaurant Name");
    RestaurantListTable.setText(0, 1, "TELEPHONE_NUMBER");
    RestaurantListTable.setText(0, 2, "ADDRESS");
    RestaurantListTable.setText(0, 3, "TYPE_OF_FOOD");

    this.SetVisible(false);
    
    HttpInterface.doGet("/yapooldb/_design/restaurant/_view/restaurant", new ListRestaurantRequestCallback());
  }

  class ListRestaurantRequestCallback extends AbstractRequestCallback {

    @Override
    public void onResponseReceived(Request request, Response response) {
      System.out.println("ListYaPool\n"+response.getText());
      JSONArray restaurants = new ViewJson(response.getText()).getRows();
      int size = restaurants.size();
      for (int i = 0; i < size; i++) {
	JSONObject temp = restaurants.get(i).isObject().get("value").isObject();
	RestaurantJson restaurant = new RestaurantJson(temp);
	int rowCounts = RestaurantListTable.getRowCount();
	Label nameLabel = new Label(restaurant.getName());
	nameLabel.addClickHandler(new DisplayRestaurantClickHandler(restaurant.getId()));
	RestaurantListTable.setWidget(rowCounts, 0, nameLabel);
	RestaurantListTable.setText(rowCounts, 1, restaurant.getTelephoneNumber());
	RestaurantListTable.setText(rowCounts, 2, restaurant.getAddress());

	String typeOfFoods_string = new String();
	JSONArray typeOfFoods = restaurant.getTypeOfFood().isArray();
	for (int j = 0; j < typeOfFoods.size(); ++j) {
	  typeOfFoods_string += typeOfFoods.get(j).isString().stringValue();
	  if (j != typeOfFoods.size() - 1)
	    typeOfFoods_string += ", ";
	}
	RestaurantListTable.setText(rowCounts, 3, typeOfFoods_string);

	// System.out.println(yapool.getId());
      }
    }
  }
}

class ListRestaurantClickHandler implements ClickHandler {

  public static String SEND_URL = "/yapooldb/";

  public void onClick(ClickEvent event) {

  }}


  class DisplayRestaurantClickHandler implements ClickHandler{

    private String restaurantId=null;

    public DisplayRestaurantClickHandler(String restaurantId){
      this.restaurantId=restaurantId;
    }
    
    @Override
    public void onClick(ClickEvent event) {
      System.out.println("Restaurant id: "+restaurantId);
      DisplayRestaurantUnit.displayRestaurantUnit.displayRestaurant(restaurantId);
      
    }
  
}


