package com.jsar.client.unit;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;

public class ListYapoolUnit {
  
  private Label yapoolNameLabel;
  private FlexTable yapoolListTable;

  public ListYapoolUnit(){
    yapoolNameLabel=new Label("List of yapool");
    yapoolListTable=new FlexTable();
    VerticalPanel verticalPanel=new VerticalPanel();
    verticalPanel.add(yapoolNameLabel);
    verticalPanel.add(yapoolListTable);   
    RootPanel.get("displayYapoolContainer").add(verticalPanel);
    
    HttpInterface.doGet("/yapooldb/_design/yapool/_view/by_id", new ListYapoolRequestCallback());
	
    
    
  }
  
  public class ListYapoolRequestCallback extends AbstractRequestCallback{

    @Override
    public void onResponseReceived(Request request, Response response) {
      System.out.println("ListYaPool\n"+response.getText());
      
    }
    
    
    
  }
  

}
