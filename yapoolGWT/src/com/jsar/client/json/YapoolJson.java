package com.jsar.client.json;

import com.google.gwt.json.client.JSONString;

public class YapoolJson extends Json {
  
  public static final String NAME="name";
  public static final String DESCRIPTION="description";
  public static final String RESTAURANT= "restaurant";
  public static final String MEMBERS ="members";

 
  
  public void setName(String name){
    jsonObject.put(NAME, new JSONString(name));
  }

  public String getName(){
    return jsonObject.get(NAME).isString().toString();
  }
  public void setDescription(String description){
    jsonObject.put(DESCRIPTION, new JSONString(description));
  }

  public String getDescription(){
    return jsonObject.get(DESCRIPTION).isString().toString();
  }
  public void setRestaurant(String restaurantId){
    jsonObject.put(RESTAURANT, new JSONString(restaurantId));
  }

  public void getRestaurant(){
    jsonObject.get(NAME).isString().toString();
  }
  
  //TODO : add the members

  
  
  
}
