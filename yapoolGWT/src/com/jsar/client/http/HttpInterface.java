package com.jsar.client.http;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Window;

public class HttpInterface {

  public static void doPost(String url, String postData) {
    RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

    try {
      HttpRequestCallback requestCallback = new HttpRequestCallback();
      Request response = builder.sendRequest(postData, requestCallback);
     
    } catch (RequestException e) {
      Window.alert("Failed to send the request: " + e.getMessage());
      e.printStackTrace();
    }
  }
  public static void doGet(String url) {
    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

    try {
      HttpRequestCallback requestCallback = new HttpRequestCallback();
      Request response = builder.sendRequest(null, requestCallback);
    } catch (RequestException e) {
      Window.alert("Failed to send the request: " + e.getMessage());
      e.printStackTrace();
    }
  }

}
