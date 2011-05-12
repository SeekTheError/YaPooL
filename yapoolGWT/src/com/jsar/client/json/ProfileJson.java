package com.jsar.client.json;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

public class ProfileJson extends AbstractJson {

	public static final String PASSED_YAPOOLS = "passedYapools";
	public static final String CURRENT_YAPOOL = "currentYapool";

	
	public ProfileJson(String jsonString) {
		this.jsonObject = JSONParser.parseStrict(jsonString).isObject();
	}

	public JSONArray getRows() {
		return jsonObject.get(PASSED_YAPOOLS).isArray();
	}

	public void archieveYapool() {
		String currentYapool = getCurrentYapool();
		setCurrentYapool("");
		jsonObject.put(PASSED_YAPOOLS,
				getRows().set(getRows().size(), new JSONString(currentYapool)));
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
}
