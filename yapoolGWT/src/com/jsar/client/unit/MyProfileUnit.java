package com.jsar.client.unit;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.YapoolGWT;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.ProfileJson;
import com.jsar.client.json.YapoolJson;
import com.jsar.client.unit.EditProfileUnit.LoadProfileCallback;
import com.jsar.client.unit.ListYapoolUnit.ListAllYapoolRequestCallback;
import com.jsar.client.util.MessageDisplayer;

public class MyProfileUnit extends AbstractUnit {
  public static MyProfileUnit myProfileUnit;

  private FlexTable profileTable;
  private Label age;
  private Label major;
  private Label address;
  private Label telephone_number;
  private Label introduction;
  private ArrayList<Label> interests;
  private Label currentYaPooL;
  private ArrayList<Label> passedYaPooL;
  private HandlerRegistration currentYaPoolClick;

  
  public MyProfileUnit(){
    myProfileUnit=this;

    VerticalPanel profilePanel=new VerticalPanel();
    profileTable= new FlexTable();
    profilePanel.add(profileTable);
    RootPanel.get("myProfileContainer").add(profilePanel);
    age =new Label();
    major=new Label();
    address=new Label();
    telephone_number=new Label();
    introduction=new Label();
    interests=new ArrayList<Label>();
    currentYaPooL=new Label();
    passedYaPooL=new ArrayList<Label>();

    
    this.SetVisible(false);
           
  }
  public void displayProfile(String userId) 
  {
    DisplayProfileCallBack profileReq=new DisplayProfileCallBack();
    profileReq.userId=userId;
    HttpInterface.doGet("/yapooldb/" + userId, profileReq);  
    
  }
  public void loadProfile() {
    if(YapoolGWT.currentSession.getName()==null)
    {
      Window.alert("you need to be logged in to do this action");
      return;
    }
    displayProfile(YapoolGWT.currentSession.getName());
  }
   
  public class DisplayProfileCallBack extends AbstractRequestCallback{
        
    public String userId=new String();
    public void onResponseReceived(Request request, Response response) {
      /*
      if(! responseIsOk(response)) {
          Window.alert("The user does not exist");
          return;
      }
      */
      
 
      profileTable.removeAllRows();
      profileTable.setText(0,0,"Age:");
      profileTable.setWidget(0, 1, age);
      
      profileTable.setText(1,0,"Major:");
      profileTable.setWidget(1, 1, major);
      
      profileTable.setText(2,0,"Address:");
      profileTable.setWidget(2, 1, address);
      
      profileTable.setText(3,0,"Telephone Number:");
      profileTable.setWidget(3, 1, telephone_number);
      
      profileTable.setText(4,0,"Intoduction:");
      profileTable.setWidget(4, 1, introduction);
      
      profileTable.setText(5,0,"Interests:");
      HorizontalPanel interestsPanel=new HorizontalPanel();
      profileTable.setWidget(5, 1, interestsPanel);
      
      profileTable.setText(6,0,"Current YaPooL!:");
      profileTable.setWidget(6, 1, currentYaPooL);
      
      profileTable.setText(7, 0, "Passed YaPooL!:");
      final HorizontalPanel passedYaPooLPanel=new HorizontalPanel();
      profileTable.setWidget(7, 1, passedYaPooLPanel);
      
      ProfileJson profileJson=new ProfileJson(response.getText());
      age.setText(profileJson.getAge());
      major.setText(profileJson.getMajor());
      address.setText(profileJson.getAddress());
      telephone_number.setText(profileJson.getTelephone());
      introduction.setText(profileJson.getIntro());
      
      JSONArray interests_jsonArray = profileJson.getInterests();
      interests.clear();
      if(interests_jsonArray != null)
      {
        for (int j = 0; j < interests_jsonArray.size(); ++j) {
          interests.add(
              new Label(interests_jsonArray.get(j).isString().stringValue())
              );
          interestsPanel.add(interests.get(j));
          if(j!=interests_jsonArray.size()-1){
            interestsPanel.add(new Label(", "));
          }
         }
      }
      else {
        interestsPanel.add(new Label(" - "));
      }
      
      final String yapoolId=profileJson.getCurrentYapool();
      if(!profileJson.getCurrentYapool().trim().equals("")){
        
        HttpInterface.doGet("/yapooldb/"+yapoolId, 
            new AbstractRequestCallback() {
              public void onResponseReceived(Request request, Response response) {
                YapoolJson yapool=new YapoolJson(response.getText());
                Label yapoolNameLabel=new Label(yapool.getName());
                currentYaPooL.setText("'"+yapool.getName()+"'");
                
              }
        });
          currentYaPooL=new Label();
          profileTable.setWidget(6, 1, currentYaPooL);
          currentYaPooL.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
              if(!yapoolId.equals(""))
                DisplayYapoolUnit.displayYapoolUnit.displayYapool(yapoolId);
            }
          });
      }
      else{
        currentYaPooL=new Label();
        profileTable.setWidget(6, 1, currentYaPooL);
        currentYaPooL.setText("No Current Yapool");
        //yapoolId = "";
      }
    
      
      
      passedYaPooL.clear();
      JSONArray passedYaPooL_jsonArray = profileJson.getPassedYapools();
      if( passedYaPooL_jsonArray.size() > 0)
      {
        final int numPassed=passedYaPooL_jsonArray.size();
        for (int j = 0; j < numPassed; ++j) {
          final int idx=j;
          final Label yapoolNameLabel=new Label();
          passedYaPooLPanel.add(yapoolNameLabel);
          if( idx!=numPassed-1){
            passedYaPooLPanel.add(new Label(","));
          }
          HttpInterface.doGet("/yapooldb/"+passedYaPooL_jsonArray.get(idx).isString().stringValue(), 
              new AbstractRequestCallback() {
                public void onResponseReceived(Request request, Response response) {
                  YapoolJson yapool=new YapoolJson(response.getText());
                  if(yapool.getName()!=null) 
                  {
                    yapoolNameLabel.setText(yapool.getName());
                    passedYaPooL.add(yapoolNameLabel); 
                  }                  
                }
          });         
        }
      }
      else{
        passedYaPooLPanel.add(new Label(" - "));
      }
      
      profileTable.insertRow(0);
      profileTable.setText(0, 0, "ID: ");
      profileTable.setWidget(0, 1, new Label(profileJson.getId()));
           
    }/*onResponseReceived*/    
  }/*DisplayProfileCallBack*/
  
  @Override
  public String getContainerId() {
    return "myProfileContainer";
  }


  
}/*public class MyProfileUnit*/
