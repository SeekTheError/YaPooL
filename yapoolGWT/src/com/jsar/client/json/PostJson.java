package com.jsar.client.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class PostJson extends AbstractJson {
  
  public static final String TYPE="type";
  public static final String USER="user";
  public static final String DESCRIPTION="description";
  public static final String YAPOOL= "yapool";
  public static final String YAPOOLID= "yapool_id";
  public static final String MESSAGE="message";
  public static final String MEMBERS ="members";

 
  
  public PostJson(JSONObject temp) {
    this.jsonObject=temp;
  }

  public PostJson() {
	  jsonObject.put(TYPE, new JSONString("post"));
  }

public void setUser(String user){
    jsonObject.put(USER, new JSONString(user));
  }
  public String getUser(){
    return jsonObject.get(USER).isString().stringValue();
  }
  
  public void setDescription(String description){
    jsonObject.put(DESCRIPTION, new JSONString(description));
  }
  public String getDescription(){
    return jsonObject.get(DESCRIPTION).isString().stringValue();
  }
  
  public void setYapool(String yapool){
    jsonObject.put(YAPOOL, new JSONString(yapool));
  }
  public String getYapool(){
    return jsonObject.get(YAPOOL).isString().toString();
  }
  
  public void setYapoolId(String yapoolId){
	    jsonObject.put(YAPOOLID, new JSONString(yapoolId));
  }
  public String getYapoolId(){
	    return jsonObject.get(YAPOOLID).isString().stringValue();
  }
  
  public void setMessage(String message){
	  jsonObject.put(MESSAGE, new JSONString(message));
  }
  public String getMessage(){
	  return jsonObject.get(MESSAGE).isString().toString();
  }
  
  //TODO : add the members

  
  
  
}
