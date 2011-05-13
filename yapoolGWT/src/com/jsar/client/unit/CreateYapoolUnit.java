package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.handler.VisibilityClickHandler;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.json.YapoolJson;

/**
 * this unit display a Pop Up that allow user to register
 * 
 * @author rem, hyahn
 * 
 */

public class CreateYapoolUnit {
  public static CreateYapoolUnit createYapoolUnit;

  // public static final String containerIopUpPaneld = "CreateYaPoolUDiv";

  private Label messageLabel;
  private Button createButton;
  private PopupPanel popUpPanel;

  private String currentRestaurantId;

  public CreateYapoolUnit() {
    CreateYapoolUnit.createYapoolUnit = this;

    final TextBox nameField = new TextBox();
    nameField.setText("YaPool Name");

    final TextBox addressField = new TextBox();
    addressField.setText("Address of Restaurant");
    addressField.setVisible(true);

    final TextBox telephoneNumberField = new TextBox();
    telephoneNumberField.setText("00-000-0000");

    /*final TextBox restaurantField = new TextBox();
    restaurantField.setText("restaurant name");
    restaurantField.setVisible(true);*/

    final TextBox membersField = new TextBox();
    membersField.setText("member list");
    membersField.setVisible(true);

    final TextBox descriptionField = new TextBox();
    descriptionField.setText("Description of Yapool");
    descriptionField.setVisible(true);

    final TextBox expectedOrderTimeField = new TextBox();
    expectedOrderTimeField.setText("Expected Order Time");
    expectedOrderTimeField.setVisible(true);

    final TextBox stateField = new TextBox();
    stateField.setText("State");
    stateField.setVisible(true);

    final TextBox pickUpPlaceField = new TextBox();
    pickUpPlaceField.setText("Pick up place");
    pickUpPlaceField.setVisible(true);

    final TextBox finalRatingField = new TextBox();
    finalRatingField.setText("finel rating");
    finalRatingField.setVisible(true);

    messageLabel = new Label();
    messageLabel.setVisible(false);
    popUpPanel = new PopupPanel();
    popUpPanel.setAutoHideEnabled(true);

    // RootPanel.get("displayCreateYaPoolContainer").add(displayRegisterPopUp);
    VerticalPanel verticalPannel = new VerticalPanel();
    Label registerLabel = new Label("Create YaPooL", false);
    registerLabel.setHorizontalAlignment(null);
    verticalPannel.add(registerLabel);
    registerLabel.addClickHandler(new VisibilityClickHandler("YaPooLCreationTable"));

    createButton = new Button("Create");

    verticalPannel.add(nameField);
    verticalPannel.add(addressField);
    verticalPannel.add(telephoneNumberField);
    verticalPannel.add(membersField);
    verticalPannel.add(descriptionField);
    verticalPannel.add(expectedOrderTimeField);
    verticalPannel.add(stateField);
    verticalPannel.add(pickUpPlaceField);
    verticalPannel.add(finalRatingField);
    verticalPannel.add(createButton);
    verticalPannel.add(messageLabel);

    verticalPannel.add(messageLabel);
    popUpPanel.add(verticalPannel);
    popUpPanel.setPopupPosition(450, 200);

    createButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
	createButton.setEnabled(false);
	createButton.setText("Sending...");
	YapoolJson yapoolJson = new YapoolJson();
	yapoolJson.setDescription(descriptionField.getText());
	yapoolJson.setRestaurant(currentRestaurantId);
	yapoolJson.setName(nameField.getText());
      }
    });
  }

  class RegistrationRequestCallback extends AbstractRequestCallback {

    @Override
    public void onResponseReceived(Request request, Response response) {
      String message = response.getHeader("message");
      if (message != null) {
	messageLabel.setText(message);

      } else {
	messageLabel.setText("error, no message to display");
      }
      messageLabel.setVisible(true);
      createButton.setText("Create");
      createButton.setEnabled(true);
    }
  }

  public void setVisible(boolean visibility) {
    RootPanel.get("displayCreateYaPoolContainer").setVisible(visibility);
  }

  public void createYapool(String currentRestaurantId) {
    this.currentRestaurantId=currentRestaurantId;
    popUpPanel.show();

  }

}
