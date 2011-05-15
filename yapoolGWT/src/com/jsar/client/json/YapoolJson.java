package com.jsar.client.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class YapoolJson extends AbstractJson {
  
  public static final String NAME="name";
  public static final String DESCRIPTION="description";
  public static final String RESTAURANT= "restaurant";
  public static final String MEMBERS ="members";
  private static final String EXPECTED_ORDER_DATE = "expectedOrderDate";
  private static final String PICK_UP_PLACE = "pickUpPlace";

 public YapoolJson(){
   this.jsonObject=new JSONObject();
   this.jsonObject.put("type",new JSONString("yapool"));
 }
  
  public YapoolJson(JSONObject temp) {
    this.jsonObject=temp;
  }

  public void setName(String name){
    jsonObject.put(NAME, new JSONString(name));
  }

  public String getName(){
    return jsonObject.get(NAME).isString().stringValue();
  }
  public void setDescription(String description){
    jsonObject.put(DESCRIPTION, new JSONString(description));
  }

  public String getDescription(){
    return jsonObject.get(DESCRIPTION).isString().stringValue();
  }
  public void setRestaurant(String restaurantId){
    jsonObject.put(RESTAURANT, new JSONString(restaurantId));
  }

  public void getRestaurant(){
    jsonObject.get(NAME).isString().toString();
  }
  
  public void setExpectedOrderDate(String expectedOrderDate){ 
    jsonObject.put(EXPECTED_ORDER_DATE,new JSONString(expectedOrderDate));
  }
  
  public String getExpectedOrderDate(){
    return jsonObject.get(EXPECTED_ORDER_DATE).isString().stringValue();
  }
  
  public String getPickUpPlace(){
    return jsonObject.get(PICK_UP_PLACE).isString().stringValue();
  }
  
  public void setPickUpPlace(String pickUpPlace){
    this.jsonObject.put(PICK_UP_PLACE,new JSONString(pickUpPlace));
  }
  
  public void  setOwner(String owner){
	    jsonObject.put("owner",new JSONString(owner));
  }
  public String getOwner(String owner){
	  return jsonObject.get("owner").isString().stringValue();
  }
  
  
  
  //TODO : add the members

  
  
  
}
