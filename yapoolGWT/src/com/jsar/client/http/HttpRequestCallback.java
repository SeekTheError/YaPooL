package com.jsar.client.http;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.http.client.Response;

//TODO implement a way to read
public class HttpRequestCallback implements RequestCallback{

  private static final int STATUS_CODE_OK = 200;
  
  

  private int lastStatusCode=0;
  private String lastResponseHeader=null;
  //TODO : implement the request timeout
  public void onError(Request request, Throwable exception) {
    lastResponseHeader=exception.getMessage();

    if (exception instanceof RequestTimeoutException) {
      System.out.println(exception.getMessage());
    } else {
      System.out.println(exception.getMessage());
    }
  }

  public void onResponseReceived(Request request, Response response) {
    System.out.println("Status Code: "+response.getStatusCode());
    System.out.println("Text: "+response.getText());
    System.out.println(response.getHeader("message"));
    if (STATUS_CODE_OK == response.getStatusCode()) {
     
    } else {
    }
  }


  public int getLastStatusCode() {
    return lastStatusCode;
  }


  public String getLastResponseHeader() {
    return lastResponseHeader;
  }
}
