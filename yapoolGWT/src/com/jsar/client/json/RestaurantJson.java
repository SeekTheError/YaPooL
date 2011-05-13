/**
 * 
 */
package com.jsar.client.json;

import java.util.ArrayList;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

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

  public RestaurantJson(JSONObject temp) {
    this.jsonObject = temp;
  }

  public RestaurantJson(String jsonString) {
    this.jsonObject = JSONParser.parseStrict(jsonString).isObject();
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

  public void setTypeOfFood(ArrayList<String> typeOfFood) {
    JSONArray jsonArray = new JSONArray();
    for (int i = 0; i < typeOfFood.size(); ++i) {
      jsonArray.set(i, new JSONString((String) typeOfFood.get(i)));
    }
    jsonObject.put(TYPE_OF_FOOD, jsonArray);
  }

  public JSONArray getTypeOfFood() {
    return jsonObject.get(TYPE_OF_FOOD).isArray();
  }

}
