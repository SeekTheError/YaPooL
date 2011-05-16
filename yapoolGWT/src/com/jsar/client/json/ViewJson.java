package com.jsar.client.json;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;

/**
 * 
 * @author rem
 * 
 */
public class ViewJson extends AbstractJson {

  public static final String ROWS = "rows";
  public static final String VALUE = "value";

  public ViewJson(String jsonString) {
    this.jsonObject = JSONParser.parseStrict(jsonString).isObject();
  }

  public JSONArray getRows() {
    if (jsonObject.containsKey(ROWS)) {
      return jsonObject.get(ROWS).isArray();
    }else
     return new JSONArray();
  }
  
  public JSONArray getYapoolIds() {
	    if (jsonObject.containsKey(VALUE)) {
	      return jsonObject.get(VALUE).isArray();
	    }else
	     return new JSONArray();
  }

}
