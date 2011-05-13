package com.jsar.client.http;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

//TODO implement a way to read
public abstract class AbstractRequestCallback implements RequestCallback {

  private static final int STATUS_CODE_OK = 200;

  // TODO : implement the request timeout, and a display somewhere of the error
  public void onError(Request request, Throwable exception) {
    System.out.println(exception.getMessage());
    /*  if (exception instanceof RequestTimeoutException) {        
      } else {
        System.out.println(exception.getMessage());
      }*/
  }

  public static boolean responseIsOk(Response response) {
    if (response.getStatusCode() == STATUS_CODE_OK || response.getStatusCode() == 201)
      return true;
    else
      return false;
  }

  public abstract void onResponseReceived(Request request, Response response);

  /*{
    System.out.println("Status Code: " + response.getStatusCode());
    System.out.println("Text: " + response.getText());
    System.out.println(response.getHeader("message"));
    if (STATUS_CODE_OK == response.getStatusCode()) {
    } else {}
  }*/
}
