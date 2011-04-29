package com.jsar.client.json;


import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

public class SessionObject extends JSONObject {

  JSONObject context=null;
  private String name=null;

  public SessionObject(String jsonString) {
    //System.out.println(jsonString);
    JSONObject object = JSONParser.parseStrict(jsonString).isObject();
    context = object.get("userCtx").isObject();
    JSONString val = context.get("name").isString();
    if (val != null) {
      setName(val.toString().replaceAll("\"", ""));
    }
    System.out.println("user name: "+this.name);
  }
  

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
