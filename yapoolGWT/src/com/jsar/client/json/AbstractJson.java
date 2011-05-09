package com.jsar.client.json;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
/**
 * a wrapper class
 * @author rem
 *
 */
public abstract class AbstractJson{
  
  JSONObject jsonObject=null;
  /**
   * use to create a message directly from a json string
   * @param jsonString
   */
  public AbstractJson(String jsonString){
    this.jsonObject = JSONParser.parseStrict(jsonString).isObject();
  }
  
  public AbstractJson(JSONObject jsonObject){
    this.jsonObject=jsonObject;
  }
  
  public AbstractJson(){
    jsonObject=new JSONObject();
  }
  
  public String toString(){
    return jsonObject.toString();
  }
  
  public void  setId(String id){
    jsonObject.put("_id",new JSONString(id));
  }
  public String getId(){
    return jsonObject.get("_id").isString().stringValue();
  }
  
  
  public void  setOwner(String owner){
    jsonObject.put("owner",new JSONString(owner));
  }
  public String getOwner(String owner){
    return jsonObject.get("owner").isString().stringValue();
  }

  
}
