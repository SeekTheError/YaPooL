package com.jsar.client.http;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.http.client.Response;

public class HttpRequestCallback implements RequestCallback{

  private static final int STATUS_CODE_OK = 200;

  private int lastStatusCode=0;
  private String lastResponseHeader=null;
  
  public void onError(Request request, Throwable exception) {
    lastResponseHeader=exception.getMessage();
    if (exception instanceof RequestTimeoutException) {
      // handle a request timeout
    } else {
      // handle other request errors
    }
  }

  public void onResponseReceived(Request request, Response response) {
    lastStatusCode=response.getStatusCode();
    System.out.println(lastStatusCode);
    lastResponseHeader=response.getHeadersAsString();
    System.out.println(lastResponseHeader);
    if (STATUS_CODE_OK == response.getStatusCode()) {
     
    } else {
      // handle non-OK response from the server
    }
  }


  public int getLastStatusCode() {
    return lastStatusCode;
  }


  public String getLastResponseHeader() {
    return lastResponseHeader;
  }
}
