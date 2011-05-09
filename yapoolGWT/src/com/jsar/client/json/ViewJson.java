package com.jsar.client.json;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;

public class ViewJson extends AbstractJson {
  
  public static final String ROWS="rows";
  

  public ViewJson(String jsonString) {
    this.jsonObject = JSONParser.parseStrict(jsonString).isObject();
  }

  public JSONArray getRows(){
    return jsonObject.get(ROWS).isArray();   
  }

}
