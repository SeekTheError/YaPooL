package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.YapoolGWT;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.ProfileJson;
import com.jsar.client.json.RestaurantJson;
import com.jsar.client.json.ViewJson;
import com.jsar.client.json.PostJson;
import com.jsar.client.unit.ListRestaurantUnit.ListRestaurantRequestCallback;


public class ViewMyProfileUnit extends AbstractUnit{

	/*
	 * 	private static final String CURRENT_YAPOOL = "currentYapool";

	private static final String AGE = "age";
	private static final String MAJOR = "major";

	private static final String ADDRESS = "address";
	private static final String TELEPHONE = "telephone_number";

	private static final String INTRO = "introduction";

	private static final String PASSED_YAPOOLS = "passedYapools";
	private static final String INTERESTS = "interests";
	private static final String FRIENDS = "friends";
	 * */
	private String currentUserId;
	  public static ViewMyProfileUnit viewMyProfileUnit;
	  private Label nameLabel;
	  private Label ageLabel;
	  private Label majorLabel;
	  private Label addressLabel;
	  private Label telephone_numberLabel;
	  private Label introductionLabel;
	  private Label passedYapoolsLabel;
	  private Label interestsLabel;
	  private Label friendsLabel;
	  
	  private TextBox nameInput;
	  private TextBox ageInput;
	  private TextBox majorInput;
	  private TextBox addressInput;
	  private TextBox telephone_numberInput;
	  private TextBox introductionInput;
	  private TextBox passedYapoolsInput;
	  private TextBox interestsInput;
	  private TextBox friendsInput;
	  
	  
	  private FlexTable profileTable;
	  
	  public ViewMyProfileUnit()
	  {	
		    viewMyProfileUnit = this;
		    nameLabel = new Label("Name : ");
		    ageLabel = new Label("Age : ");
		    majorLabel = new Label("Major : ");
		    addressLabel = new Label("Address : ");
		    telephone_numberLabel = new Label("Telephone number : ");
		    introductionLabel = new Label("Introduction : ");
		    passedYapoolsLabel = new Label("Passed Yapools : ");
		    interestsLabel = new Label("Interests : ");
		    friendsLabel = new Label("Friends : ");
		    
		    
		    nameInput = new TextBox();
		    ageInput = new TextBox();
		    majorInput = new TextBox();
		    addressInput = new TextBox();
		    telephone_numberInput = new TextBox();
		    introductionInput = new TextBox();
		    passedYapoolsInput = new TextBox();
		    interestsInput = new TextBox();
		    friendsInput = new TextBox();
		    profileTable = new FlexTable();
		    
		    VerticalPanel verticalPanel = new VerticalPanel();
		    verticalPanel.add(profileTable);
		    RootPanel.get("displayYapoolContainer").add(verticalPanel);
		    this.SetVisible(false);
	  }

	  
	  public void viewProfile(String userId) {
		  
		  System.out.println("viewProfile****************8");
		   // currentuserId = userId;
		   	currentUserId = "hyahn";
		    NavigationUnit.navigationUnit.hideAll();
		    this.SetVisible(true);
		    HttpInterface.doGet("/yapooldb/" + currentUserId, new AbstractRequestCallback() {

		      @Override
		      public void onResponseReceived(Request request, Response response) {
			ProfileJson profileJson = new ProfileJson(response.getText());
			
			
			nameInput.setText("name");
			System.out.println("Age~~~~~~~~~ : "+profileJson.getAge());
			ageInput.setText(profileJson.getAge());
			majorInput.setText(profileJson.getMajor());
			addressInput.setText(profileJson.getAddress());
			telephone_numberInput.setText(profileJson.getTelephone());
			introductionInput.setText(profileJson.getIntro());
			JSONArray passedYapools = profileJson.getPassedYapools();
			int size = passedYapools.size();
			for (int i = 0; i < size; i++) {
				passedYapoolsInput.setText(passedYapools.get(i).isString().stringValue().toString());
		      }
			JSONArray Interests = profileJson.getInterests();
			int Interests_size = Interests.size();
			for (int i = 0; i < Interests_size; i++) {
				passedYapoolsInput.setText(Interests.get(i).isString().stringValue().toString());
		      }
			JSONArray Friends = profileJson.getFriends();
			int Friends_size = Friends.size();
			for (int i = 0; i < Friends_size; i++) {
				passedYapoolsInput.setText(Friends.get(i).isString().stringValue().toString());
			}
		      }
		    });
		 }


	  @Override
	  public String getContainerId() {
	    // TODO Auto-generated method stub
	    return null;
	  }
}
