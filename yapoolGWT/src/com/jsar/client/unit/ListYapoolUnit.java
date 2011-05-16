package com.jsar.client.unit;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.ViewJson;
import com.jsar.client.json.YapoolJson;

public class ListYapoolUnit extends AbstractUnit {
  public static ListYapoolUnit listYapoolUnit;

  private Label allYapoolNameLabel;
  private FlexTable yapoolListTable;
  private VerticalPanel tagPanel;
  private String tagName;
  private String yapoolList;
  
  enum ListType {
    ALL, RECOMMENDED
  }

  public ListYapoolUnit() {
    listYapoolUnit = this;
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    allYapoolNameLabel = new Label("All Yapools");
    allYapoolNameLabel.getElement().setClassName("selectorLabel");
    allYapoolNameLabel.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
	listYapools(ListType.ALL);
      }
    });
    Label recommendedYapools = new Label("Recommended YapooLs");
    recommendedYapools.getElement().setClassName("selectorLabel");
    recommendedYapools.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
	listYapools(ListType.RECOMMENDED);
      }
    });
    //horizontalPanel.add(allYapoolNameLabel);
    //horizontalPanel.add(recommendedYapools);
    
    //Adding Tag Panel
    tagPanel = new VerticalPanel();
    
    
    yapoolListTable = new FlexTable();
    
    yapoolListTable.getElement().setClassName("yapoolTable");
    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(horizontalPanel);
    RootPanel.get("listYapoolTags").add(tagPanel);
    VerticalPanel vp=new VerticalPanel();
    ScrollPanel scrollPanel= new ScrollPanel();
    scrollPanel.setVisible(true);
    
    scrollPanel.add(yapoolListTable);
    scrollPanel.setHeight("400px");
    vp.add(scrollPanel);
    FlexTable table=new FlexTable();

    verticalPanel.add(table);
    verticalPanel.add(vp);
    RootPanel.get("listYapoolContainer").add(verticalPanel);
    listYapools(ListType.ALL);

  }

  public void listYapools(ListType type) {
	//Add Elements to tagPanel
	HttpInterface.doGet("/yapooldb/_design/yapoolId/_view/by_tag?group=true", new ListTagsRequestCallback());

	tagPanel.clear();
	yapoolListTable.removeAllRows();
    

    	HttpInterface.doGet("/yapooldb/_design/yapool/_view/by_id", new ListAllYapoolRequestCallback());


  }

  public class ListAllYapoolRequestCallback extends AbstractRequestCallback {

    @Override
    public void onResponseReceived(Request request, Response response) {
      // System.out.println("ListYaPool\n"+response.getText());
      JSONArray yapools = new ViewJson(response.getText()).getRows();
      displayYapoolList(yapools);
    }
  }
  
  public class ListTagsRequestCallback extends AbstractRequestCallback {

	    @Override
	    public void onResponseReceived(Request request, Response response) {
	      // System.out.println("ListYaPool\n"+response.getText());
	      JSONArray tags = new ViewJson(response.getText()).getRows();
	      int size = tags.size();
	      yapoolList = "";
	      for(int i = 0; i < size; i++){
	    	  //System.out.println("TEST TAG: "+tags.get(i).isObject().get("key").isString().stringValue());
	    	  tagName = tags.get(i).isObject().get("key").isString().stringValue();
	    	  
	    	  final Label tagLabel = new Label(tagName);
	    	  tagLabel.getElement().setClassName("listTags");
	    	  //Label divider = new Label(", ");
	    	  //tagLabel.getElement().setClassName("selectorLabel");
	    	  tagLabel.addClickHandler(new ClickHandler() {
	    	    @Override
	    	    public void onClick(ClickEvent event) {
	    	        HttpInterface.doGet("/yapooldb/_design/yapool/_view/by_tag?key=\"" + tagLabel.getText() + "\"", new AbstractRequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							JSONArray yapools = new ViewJson(response.getText()).getRows();
							displayYapoolList(yapools);
						}
	    	        });
	    	    }
	    	  });
		    	  tagPanel.add(tagLabel);
		    	  //if (i != size - 1)
		    	  //tagPanel.add(divider);
	    	    }
	      //displayYapoolList(yapools);
	    }
  }

  public void displayYapoolList(JSONArray yapools) {
    
	  yapoolListTable.removeAllRows();
	    yapoolListTable.setText(0, 0, "YaPool Name");
	    yapoolListTable.setText(0, 1, "YaPool Description");
	    yapoolListTable.setText(0, 2, "Tags");
	  int size = yapools.size();
    for (int i = 0; i < size; i++) {
      JSONObject temp = yapools.get(i).isObject().get("value").isObject();
      YapoolJson yapool = new YapoolJson(temp);
      int rowCounts = yapoolListTable.getRowCount();
      Label yapoolLabel = new Label(yapool.getName());
      yapoolLabel.addClickHandler(new DisplayYapoolClickHandler(yapool.getId()));
      yapoolListTable.setWidget(rowCounts, 0, yapoolLabel);
      yapoolListTable.setText(rowCounts, 1, yapool.getDescription());
      
      String tags_string = "";
	  JSONArray tags = yapool.getTags().isArray();
	  for (int j = 0; j < tags.size(); ++j) {
		  tags_string += tags.get(j).isString().stringValue();
		  if (j != tags.size() - 1)
			  tags_string += ", ";
	  }
	  
      yapoolListTable.setText(rowCounts, 2, tags_string);
    }
  }

  public String getContainerId() {
    return "listYapoolContainer";
  }

  public class DisplayYapoolClickHandler implements ClickHandler {

    private String yapoolId = null;

    public DisplayYapoolClickHandler(String yapoolId) {
      this.yapoolId = yapoolId;
    }

    @Override
    public void onClick(ClickEvent event) {
      DisplayYapoolUnit.displayYapoolUnit.displayYapool(yapoolId);

    }
  }

}
