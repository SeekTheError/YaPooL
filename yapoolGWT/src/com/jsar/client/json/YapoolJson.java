package com.jsar.client.json;

import java.util.ArrayList;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class YapoolJson extends AbstractJson {

	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String RESTAURANT = "restaurant";
	private static final String MEMBERS = "members";
	private static final String EXPECTED_ORDER_DATE = "expectedOrderDate";
	private static final String PICK_UP_PLACE = "pickUpPlace";
	private static final String STATE = "state";

	public YapoolJson() {
		this.jsonObject = new JSONObject();
		this.jsonObject.put("type", new JSONString("yapool"));
		this.jsonObject.put(MEMBERS, new JSONArray());
	}

	public YapoolJson(JSONObject temp) {
		this.jsonObject = temp;
	}

	public YapoolJson(String jsonString) {
		this.jsonObject = JSONParser.parseStrict(jsonString).isObject();
	}

	public void setName(String name) {
		jsonObject.put(NAME, new JSONString(name));
	}

	public String getName() {
		return jsonObject.get(NAME).isString().stringValue();
	}

	public void setDescription(String description) {
		jsonObject.put(DESCRIPTION, new JSONString(description));
	}

	public String getDescription() {
		return jsonObject.get(DESCRIPTION).isString().stringValue();
	}

	public void setRestaurant(String restaurantId) {
		jsonObject.put(RESTAURANT, new JSONString(restaurantId));
	}

	public void getRestaurant() {
		jsonObject.get(NAME).isString().toString();
	}

	public void setExpectedOrderDate(String expectedOrderDate) {
		jsonObject.put(EXPECTED_ORDER_DATE, new JSONString(expectedOrderDate));
	}

	public String getExpectedOrderDate() {
		return jsonObject.get(EXPECTED_ORDER_DATE).isString().stringValue();
	}

	public String getPickUpPlace() {
		return jsonObject.get(PICK_UP_PLACE).isString().stringValue();
	}

	public void setPickUpPlace(String pickUpPlace) {
		this.jsonObject.put(PICK_UP_PLACE, new JSONString(pickUpPlace));
	}

	public String getState() {
		return jsonObject.get(STATE).isString().stringValue();
	}

	public void setState(String state) {
		this.jsonObject.put(STATE, new JSONString(state));
	}

	public void setOwner(String owner) {
		jsonObject.put("owner", new JSONString(owner));
	}

	public String getOwner(String owner) {
		return jsonObject.get("owner").isString().stringValue();
	}

	public void setMembers(JSONArray members) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < members.size(); ++i) {
			jsonArray.set(i, new JSONString(members.get(i).isString().stringValue()));
		}
		jsonObject.put(MEMBERS, jsonArray);
	}

	public void addMember(String member) {
		JSONArray jsonArray = getMembers();
		jsonArray.set(jsonArray.size(), new JSONString(member));
		jsonObject.put(MEMBERS, jsonArray);
	}

	public JSONArray getMembers() {
		JSONValue object = jsonObject.get(MEMBERS);
		if (object == null)
			jsonObject.put(MEMBERS, new JSONArray());
		return jsonObject.get(MEMBERS).isArray();
	}

}
