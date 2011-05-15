package com.jsar.client.http;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.http.client.Response;
/**
 * 
 * @author rem
 *
 */
public abstract class AbstractRequestCallback implements RequestCallback {

  private static final int STATUS_CODE_OK = 200;

 
  public void onError(Request request, Throwable exception) {
    System.out.println(exception.getMessage());
    if (exception instanceof RequestTimeoutException) {   
      // TODO : implement the request timeout, and a display somewhere of the error;
      } else {
        System.out.println(exception.getMessage());
      }
  }

  public static boolean responseIsOk(Response response) {
    if (response.getStatusCode() == STATUS_CODE_OK || response.getStatusCode() == 201)
      return true;
    else
      return false;
  }

  public abstract void onResponseReceived(Request request, Response response);
}
