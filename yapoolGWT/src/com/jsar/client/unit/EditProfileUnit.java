package com.jsar.client.unit;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.YapoolGWT;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.ProfileJson;
import com.jsar.client.util.MessageDisplayer;

public class EditProfileUnit extends AbstractUnit {

  public static EditProfileUnit editProfileUnit;

  private Label ageLabel;
  private Label majorLabel;
  private Label addressLabel;
  private Label telephone_numberLabel;
  private Label introductionLabel;
  private Label interestsLabel;
  private Label idLabel;
  
  private TextBox ageInput;
  private TextBox majorInput;
  private TextBox addressInput;
  private TextBox telephone_numberInput;
  private TextArea introductionInput;
  private TextArea interestsInput;

  private ProfileJson currentProfile;

  public EditProfileUnit() {
    editProfileUnit = this;
    this.SetVisible(false);
    
    idLabel= new Label();
    ageLabel = new Label("Age : ");
    ageInput = new TextBox();

    majorLabel = new Label("Major : ");
    majorInput = new TextBox();

    addressLabel = new Label("Address : ");
    addressInput = new TextBox();

    telephone_numberLabel = new Label("Telephone number : ");
    telephone_numberInput = new TextBox();

    introductionLabel = new Label("Introduction : ");
    introductionInput = new TextArea();
    interestsLabel = new Label("Interests : ");
    interestsInput = new TextArea();
    // TODO fix this fix a proper interesting food list

    VerticalPanel verticalPanel = new VerticalPanel();

    // TODO Auto-generated method stub
    verticalPanel.add(new Label("ID : "));
    verticalPanel.add(idLabel);
    verticalPanel.add(ageLabel);
    verticalPanel.add(ageInput);
    verticalPanel.add(majorLabel);
    verticalPanel.add(majorInput);
    verticalPanel.add(addressLabel);
    verticalPanel.add(addressInput);
    verticalPanel.add(telephone_numberLabel);
    verticalPanel.add(telephone_numberInput);
    verticalPanel.add(introductionLabel);
    verticalPanel.add(introductionInput);
    verticalPanel.add(interestsLabel);
    verticalPanel.add(interestsInput);
    verticalPanel.add(new Label("* Insert interests splited by ','"));

    RootPanel.get("editProfileContainer").add(verticalPanel);

    Button save = new Button("Save Modifications");
    verticalPanel.add(save);
    save.addClickHandler(new EditProfileClickHandler());
  }

  public void loadProfile() {
    HttpInterface.doGet("/yapooldb/" + YapoolGWT.currentSession.getName(),
        new LoadProfileCallback());
  }

  public class LoadProfileCallback extends AbstractRequestCallback {

    @Override
    public void onResponseReceived(Request request, Response response) {
      ProfileJson profile = new ProfileJson(response.getText());
      currentProfile = profile;
      idLabel.setText(currentProfile.getId());
      ageInput.setText(profile.getAge());
      majorInput.setText(profile.getMajor());
      addressInput.setText(profile.getAddress());
      telephone_numberInput.setText(profile.getTelephone());
      introductionInput.setText(profile.getIntro());

      JSONArray interests_jsonArray = profile.getInterests();
      String interests=new String("");
      String item=new String();
      if(interests_jsonArray != null)
      {
        for (int j = 0; j < interests_jsonArray.size(); ++j) 
        {
          item=interests_jsonArray.get(j).isString().stringValue();
          interests+=item.replaceAll("[ ]+", " "); 
          if(j!=interests_jsonArray.size()-1){
            interests+=", ";
          }
        }
      }
      interestsInput.setText(interests);
    }
  }

  public class EditProfileClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      ProfileJson profileJson = currentProfile;
      profileJson.setAge(ageInput.getText());
      profileJson.setMajor(majorInput.getText());
      profileJson.setAddress(addressInput.getText());
      profileJson.setTelephone(telephone_numberInput.getText());
      profileJson.setIntro(introductionInput.getText());

      String interests_string=interestsInput.getText();
      String[] interests_array=interests_string.split(",");
      ArrayList<String> interests_arrayList= new ArrayList<String>();
      for(int i=0; i<interests_array.length; ++i){
        interests_arrayList.add(interests_array[i].trim());
      }
      profileJson.setInterests(interests_arrayList);
      
      HttpInterface.doPostJson("/yapooldb/", profileJson,
          new AbstractRequestCallback() {

            @Override
            public void onResponseReceived(Request request, Response response) {
              if (responseIsOk(response)) {
                MessageDisplayer.DisplayMessage("Profile Successfully Edited");
              }
              MyProfileUnit.myProfileUnit.displayProfile(currentProfile
                  .getOwner());

            }
          });
    }

  }

  @Override
  public String getContainerId() {
    return "editProfileContainer";
  }
}
