package com.jsar.client.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
/**
 * a wrapper class
 * @author rem
 *
 */
public class Json{
  
  JSONObject jsonObject=null;
  /**
   * use to create a message directly from a json string
   * @param jsonString
   */
  public Json(String jsonString){
    jsonObject = JSONParser.parseStrict(jsonString).isObject();
  }
  
  public Json(){
    jsonObject=new JSONObject();
  }
  
  public String toString(){
    return jsonObject.toString();
  }
  
  public void  setOwner(String owner){
    jsonObject.put("owner",new JSONString(owner));
  }

  
}
