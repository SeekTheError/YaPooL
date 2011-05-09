package com.jsar.client.json;

import com.google.gwt.json.client.JSONString;

public class MessageJson extends AbstractJson {

  public static final String MESSAGE = "message";

  public void setMessage(String message) {
    jsonObject.put(MESSAGE, new JSONString(message));
  }

  public String getMessage() {
    return jsonObject.get(MESSAGE).isString().stringValue();
  }

}
