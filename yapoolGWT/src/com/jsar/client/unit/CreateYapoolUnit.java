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
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.YapoolJson;
import com.jsar.client.util.CheckLoggedIn;
import com.jsar.client.util.MessageDisplayer;

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

    final Label nameLabel = new Label("name");
    final TextBox nameField = new TextBox();

    final Label descriptionLabel = new Label("description");
    final TextBox descriptionField = new TextBox();

    final Label expectedOrderTimeLabel = new Label("Expected Order Time");
    final TextBox expectedOrderTimeField = new TextBox();

    final Label pickUpPlaceLabel = new Label("Pick Up Place");
    final TextBox pickUpPlaceField = new TextBox();

    messageLabel = new Label();
    messageLabel.setVisible(false);
    popUpPanel = new PopupPanel();
    popUpPanel.setAutoHideEnabled(true);

    VerticalPanel verticalPannel = new VerticalPanel();
    Label registerLabel = new Label("Create YaPooL", false);
    registerLabel.setHorizontalAlignment(null);
    verticalPannel.add(registerLabel);
    registerLabel.addClickHandler(new VisibilityClickHandler("YaPooLCreationTable"));

    createButton = new Button("Create");

    verticalPannel.add(nameLabel);
    verticalPannel.add(nameField);
    verticalPannel.add(descriptionLabel);
    verticalPannel.add(descriptionField);
    verticalPannel.add(expectedOrderTimeLabel);
    verticalPannel.add(expectedOrderTimeField);
    verticalPannel.add(pickUpPlaceLabel);
    verticalPannel.add(pickUpPlaceField);
    verticalPannel.add(createButton);
    verticalPannel.add(messageLabel);

    verticalPannel.add(messageLabel);
    popUpPanel.add(verticalPannel);
    popUpPanel.setPopupPosition(450, 200);

    createButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
	messageLabel.setVisible(true);
	if (!CheckLoggedIn.userIsloggedIn()) {
	  return;
	}
	YapoolJson yapoolJson = new YapoolJson();
	yapoolJson.setDescription(descriptionField.getText());
	yapoolJson.setRestaurant(currentRestaurantId);
	String yapoolName = nameField.getText();
	if (yapoolName.equals("")) {
	  displayErrorMessage("you must enter a YaPooL! name");
	  return;
	}
	yapoolJson.setName(yapoolName);
	String pickUpPlace = pickUpPlaceField.getText();
	if (pickUpPlace.equals("")) {
	  displayErrorMessage("you must enter a pick up place");
	  return;
	}
	yapoolJson.setPickUpPlace(pickUpPlaceField.getText());

	createButton.setEnabled(false);
	createButton.setText("Sending...");
	HttpInterface.doPostJson("/yapooldb/", yapoolJson, new CreateYapoolCallbackCallback());
      }

      private void displayErrorMessage(String string) {
	messageLabel.setText(string);
	messageLabel.setVisible(true);

      }
    });

  }

  class CreateYapoolCallbackCallback extends AbstractRequestCallback {

    @Override
    public void onResponseReceived(Request request, Response response) {
      if (responseIsOk(response)) {
	popUpPanel.hide();
	createButton.setEnabled(true);
	createButton.setText("Create");
	MessageDisplayer.DisplayMessage("Yapool Successfully Created, you can access it from your YaPooL! page");
      } else {

      }

    }
  }

  public void setVisible(boolean visibility) {
    RootPanel.get("displayCreateYaPoolContainer").setVisible(visibility);
  }

  public void createYapool(String currentRestaurantId) {
    this.currentRestaurantId = currentRestaurantId;
    popUpPanel.show();

  }

}
