package com.jsar.client.json;

import java.util.ArrayList;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.jsar.client.YapoolGWT;

public class ProfileJson extends AbstractJson {

	private static final String CURRENT_YAPOOL = "currentYapool";

	private static final String AGE = "age";
	private static final String MAJOR = "major";

	private static final String ADDRESS = "address";
	private static final String TELEPHONE = "telephone_number";

	private static final String INTRO = "introduction";

	private static final String PASSED_YAPOOLS = "passedYapools";
	private static final String INTERESTS = "interests";
	private static final String FRIENDS = "friends";

	
    public ProfileJson(JSONObject temp) {
		    this.jsonObject=temp;
		  }

	  
	  
	public ProfileJson(String jsonString) {
		this.jsonObject = JSONParser.parseStrict(jsonString).isObject();
	}

	public ProfileJson() {
		this.jsonObject = new JSONObject();
		this.jsonObject.put("_id", new JSONString(YapoolGWT.currentSession.getName()));
		this.jsonObject.put("type", new JSONString("profile"));
		setCurrentYapool("");
		setAge("");
		setMajor("");
		setAddress("");
		setTelephone("");
		setIntro("");
		jsonObject.put(PASSED_YAPOOLS, new JSONArray());
		jsonObject.put(INTERESTS, new JSONArray());
		jsonObject.put(FRIENDS, new JSONArray());
		System.out.println("No Profile Exist, therefore, creating...");
	}

	public JSONArray getPassedYapools() {
		return jsonObject.get(PASSED_YAPOOLS).isArray();
	}

	public void archieveYapool() {
		String currentYapool = getCurrentYapool();
		setCurrentYapool("");
		jsonObject.put(
				PASSED_YAPOOLS,
				getPassedYapools().set(getPassedYapools().size(),
						new JSONString(currentYapool)));
	}

	public void leaveYapool() {
		setCurrentYapool("");
	}

	public void setCurrentYapool(String currentYapool) {
		jsonObject.put(CURRENT_YAPOOL, new JSONString(currentYapool));
	}

	public String getCurrentYapool() {
		return jsonObject.get(CURRENT_YAPOOL).isString().stringValue();
	}

	public void setAge(String age) {
		jsonObject.put(AGE, new JSONString(age));
	}

	public String getAge() {
		return jsonObject.get(AGE).isString().stringValue();
	}

	public void setMajor(String major) {
		jsonObject.put(MAJOR, new JSONString(major));
	}

	public String getMajor() {
		return jsonObject.get(MAJOR).isString().stringValue();
	}

	public void setAddress(String address) {
		jsonObject.put(ADDRESS, new JSONString(address));
	}

	public String getAddress() {
		return jsonObject.get(ADDRESS).isString().stringValue();
	}

	public void setTelephone(String telephone) {
		jsonObject.put(TELEPHONE, new JSONString(telephone));
	}

	public String getTelephone() {
		return jsonObject.get(TELEPHONE).isString().stringValue();
	}

	public void setIntro(String intro) {
		jsonObject.put(INTRO, new JSONString(intro));
	}

	public String getIntro() {
		return jsonObject.get(INTRO).isString().stringValue();
	}

	public void setInterests(ArrayList<String> interests) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < interests.size(); ++i) {
			jsonArray.set(i, new JSONString((String) interests.get(i)));
		}
		jsonObject.put(INTERESTS, jsonArray);
	}

	public JSONArray getInterests() {
		return jsonObject.get(INTERESTS).isArray();
	}

	public void setFriends(ArrayList<String> friends) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < friends.size(); ++i) {
			jsonArray.set(i, new JSONString((String) friends.get(i)));
		}
		jsonObject.put(FRIENDS, jsonArray);
	}

	public JSONArray getFriends() {
		return jsonObject.get(FRIENDS).isArray();
	}
}
