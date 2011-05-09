package com.jsar.client.json;


import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
/**
 * this class is not using the corrected policy
 * @author rem
 *
 */
public class SessionJson {

  JSONObject jsonObject=null;
  private String name=null;
  
  public SessionJson(){
    jsonObject=new JSONObject();
  }

  public SessionJson(String jsonString) {
    JSONObject object = JSONParser.parseStrict(jsonString).isObject();
    jsonObject = object.get("userCtx").isObject();
    JSONString val = jsonObject.get("name").isString();
    if (val != null) {
      setName(val.toString().replaceAll("\"", ""));
    }
  }
  
  public String toString()
  {
    return jsonObject.toString();
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
