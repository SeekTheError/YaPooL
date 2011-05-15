package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.ViewJson;
import com.jsar.client.json.YapoolJson;

public class ListYapoolUnit extends AbstractUnit {
  public static ListYapoolUnit listYapoolUnit;

  private Label yapoolNameLabel;
  private FlexTable yapoolListTable;

  public ListYapoolUnit() {
    listYapoolUnit = this;
    yapoolNameLabel = new Label("List of yapool");
    
    yapoolListTable = new FlexTable();
    yapoolListTable.getElement().setClassName("yapoolTable");
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(yapoolNameLabel);
    verticalPanel.add(yapoolListTable);
    RootPanel.get("listYapoolContainer").add(verticalPanel);

    yapoolListTable.setText(0, 0, "YaPool Name");
    yapoolListTable.setText(0, 1, "YaPool Description");

    HttpInterface.doGet("/yapooldb/_design/yapool/_view/by_id", new ListYapoolRequestCallback());
  }

  public class ListYapoolRequestCallback extends AbstractRequestCallback {

    @Override
    public void onResponseReceived(Request request, Response response) {
      // System.out.println("ListYaPool\n"+response.getText());
      JSONArray yapools = new ViewJson(response.getText()).getRows();
      int size = yapools.size();
      for (int i = 0; i < size; i++) {
	JSONObject temp = yapools.get(i).isObject().get("value").isObject();
	YapoolJson yapool = new YapoolJson(temp);
	int rowCounts = yapoolListTable.getRowCount();
	Label yapoolLabel=new Label(yapool.getName());
	yapoolLabel.addClickHandler(new DisplayYapoolClickHandler(yapool.getId()));
	yapoolListTable.setWidget(rowCounts, 0, yapoolLabel);
	yapoolListTable.setText(rowCounts, 1, yapool.getDescription());
	// System.out.println(yapool.getId());

      }
    }
  }

  public String getContainerId() {
    return "listYapoolContainer";
  }

  
  public class DisplayYapoolClickHandler implements ClickHandler{

    private String yapoolId=null;

    public DisplayYapoolClickHandler(String yapoolId){
      this.yapoolId=yapoolId;
    }
    
    @Override
    public void onClick(ClickEvent event) {
      DisplayYapoolUnit.displayYapoolUnit.displayYapool(yapoolId);
      
    }}
  
}
