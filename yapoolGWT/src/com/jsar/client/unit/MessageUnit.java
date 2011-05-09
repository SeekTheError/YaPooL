package com.jsar.client.unit;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.MessageJson;

public class MessageUnit {
  
  public MessageUnit(){
    final TextBox input = new TextBox();
    RootPanel.get("messageInputContainer").add(input);
  
    input.addKeyDownHandler(new KeyDownHandler() {
      
      @Override
      public void onKeyDown(KeyDownEvent event) {
       if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER){
	 MessageJson message=new MessageJson();
	 message.setMessage(input.getText());
	 input.setText("");
	 System.out.println(message);
	 HttpInterface.doPostJson("/yapooldb/", message, new MessageRequestCallback());
       }
        
      }
    });
  }
  
  class MessageRequestCallback extends AbstractRequestCallback{

    @Override
    public void onResponseReceived(Request request, Response response) {
      System.out.println(response.toString());
      
    }
    
  }

}
