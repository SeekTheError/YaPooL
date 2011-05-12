package com.jsar.client.unit;
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
import com.jsar.client.unit.ListYapoolUnit.ListYapoolRequestCallback;

public class ListRestaurantUnit {
	private Label restaurantNameLabel;
	  private FlexTable restaurantListTable;
	  
	  public String getUnitName(){return "listrestaurantUnit";}

	  public String getContainerId(){return "displayrestaurantContainer";}
	  
	  public ListRestaurantUnit(){
		restaurantNameLabel=new Label("List of restaurnat");
		restaurantListTable=new FlexTable();
	    VerticalPanel verticalPanel=new VerticalPanel();
	    verticalPanel.add(restaurantNameLabel);
	    verticalPanel.add(restaurantListTable);   
	    RootPanel.get("listRestaurnatContainer").add(verticalPanel);
	    
	    restaurantListTable.setText(0, 0, "Restaurnat Name");
	    restaurantListTable.setText(0, 1, "Restaurnat Description");
	    
	    
	    
	   // HttpInterface.doGet("/yapooldb/_design/yapool/_view/by_id", new ListYapoolRequestCallback());
		
	    
	    
	  }
	  
	  public class ListYapoolRequestCallback extends AbstractRequestCallback{

	    @Override
	    public void onResponseReceived(Request request, Response response) {
	      System.out.println("ListRestaurant\n"+response.getText());
	      JSONArray yapools=new ViewJson(response.getText()).getRows();
	      
	      int size=yapools.size();
	      for(int i=0;i<size;i++){
		JSONObject temp=yapools.get(i).isObject().get("value").isObject();
		YapoolJson yapool=new YapoolJson(temp);
		int rowCounts=restaurantListTable.getRowCount();
		restaurantListTable.setText(rowCounts, 0, yapool.getName());
		restaurantListTable.setText(rowCounts, 1, yapool.getDescription());
		System.out.println(yapool.getId());
		
		
	      }    
	    }  
	  }
}
