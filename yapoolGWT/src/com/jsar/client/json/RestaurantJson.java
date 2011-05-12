/**
 * 
 */
package com.jsar.client.json;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;

/**
 * @author hyahn
 * 
 **/
public class RestaurantJson extends AbstractJson {

	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String TELEPHONE_NUMBER = "telephoneNumber";
	public static final String TYPE_OF_FOOD = "typeOfFood";
	public static final String TYPE = "TYPE";

	
	public RestaurantJson() {
		jsonObject.put(TYPE, new JSONString("restaurant"));
	}
	public RestaurantJson(JSONObject temp)
	{
		this.jsonObject=temp;
	}
	public void setName(String name) {
		jsonObject.put(NAME, new JSONString(name));
	}

	public String getName() {
		return jsonObject.get(NAME).isString().stringValue();
	}

	public void setAddress(String description) {
		jsonObject.put(ADDRESS, new JSONString(description));
	}

	public String getAddress() {
		return jsonObject.get(ADDRESS).isString().stringValue();
	}

	public void setTelephoneNumber(String telephoneNumber) {
		jsonObject.put(TELEPHONE_NUMBER, new JSONString(telephoneNumber));
		setId(telephoneNumber);
	}

	public String getTelephoneNumber() {
		return jsonObject.get(TELEPHONE_NUMBER).isString().stringValue();
	}

	public void setTypeOfFood(ArrayList typeOfFood) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < typeOfFood.size(); ++i) {
			jsonArray.set(i, new JSONString((String) typeOfFood.get(i)));
		}
		jsonObject.put(TYPE_OF_FOOD, jsonArray);
	}

	public JSONArray getTypeOfFood() {
		return jsonObject.get(TYPE_OF_FOOD).isArray();
	}
	/* yapoolDB?key=" " */
}



/*
 package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NavigationUnit {
  public static NavigationUnit navigationUnit;
  
  public static final String containerId="navigationUnitContainer";
  
  public NavigationUnit(){
    NavigationUnit.navigationUnit=this;   
        
   VerticalPanel yapoolPanel=new VerticalPanel();
    Label listYapoolLabel=new Label("list of Yapools");
   listYapoolLabel.addClickHandler(new ClickHandler() {
	    
	    @Override
	    public void onClick(ClickEvent event) {
	      hideAll();
	      ListYapoolUnit.listYapoolUnit.SetVisible(true);     
	    }
	  });
   yapoolPanel.add(listYapoolLabel);
   RootPanel.get("yapoolNavigationContainer").add(yapoolPanel);
    Label yapoolNavigatonLabel=new Label("YaPooLs");
   RootPanel.get("yapoolNavigationLabel").add(yapoolNavigatonLabel);

   VerticalPanel restaurantPanel=new VerticalPanel();
   Label listRestaurantLabel=new Label("list of Restaurants");
   listRestaurantLabel.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
   
	    }
	  });
   restaurantPanel.add(listRestaurantLabel);
   RootPanel.get("restaurantNavigationContainer").add(restaurantPanel);
   Label restaurantNavigationLabel=new Label("Restaurant");
   RootPanel.get("restaurantNavigationLabel").add(restaurantNavigationLabel);

   
   
   Label myPageNavigationLabel=new Label("My Page");
   RootPanel.get("myPageNavigationLabel").add(myPageNavigationLabel);
    
  }
  public void hideAll(){
    ListYapoolUnit.listYapoolUnit.SetVisible(false);
    DisplayYapoolUnit.displayYapoolUnit.SetVisible(false);
  }

}
*/


/* 
 * ------------------------------------------------------   
 * RestaurantJson rs=new RestaurantJson();
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
      */
