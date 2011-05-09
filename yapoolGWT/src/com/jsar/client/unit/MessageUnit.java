package com.jsar.client.unit;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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
    input.addKeyPressHandler(new KeyPressHandler() {
      
      @Override
      public void onKeyPress(KeyPressEvent event) {
       if(event.getCharCode()==13){
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
