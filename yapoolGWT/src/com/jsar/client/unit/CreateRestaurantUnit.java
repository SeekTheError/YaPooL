package com.jsar.client.unit;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.RestaurantJson;

/**
 * this unit display a Pop Up that allow user to register
 * 
 * @author hyahn
 * 
 */

public class CreateRestaurantUnit {
  public static CreateRestaurantUnit createRestaurantUnit;

  /*CreateRestaurantContainer*/
  public static String url = "/yapooldb/";

  private Label messageLabel; // for informing some errors
  private Button createButton;
  private FlexTable createRestaurantTable; // form table
  private PopupPanel popUpPannel;

  public String getUnitName() {
    return "createRestaurantUnit";
  }

  public void setVisible(boolean visibility) {
    if (!popUpPannel.isShowing()) {
      loadInitialForm();
      popUpPannel.show();
    }
  }

  public void loadInitialForm() {
    createButton.setText("Create");
    messageLabel.setText("");
    messageLabel.setVisible(false);
  }

  public CreateRestaurantUnit() {

    createRestaurantUnit = this;

    popUpPannel = new PopupPanel();
    popUpPannel.setAutoHideEnabled(true);

    VerticalPanel verticalPannel = new VerticalPanel();
    popUpPannel.add(verticalPannel);

    Label titleLabel = new Label("Create Restaurant", false);
    titleLabel.setHorizontalAlignment(null);
    verticalPannel.add(titleLabel);
    // titleLabel.addClickHandler(new
    // VisibilityClickHandler("RestaurantCreationTable"));

    createRestaurantTable = new FlexTable();
    verticalPannel.add(createRestaurantTable);

    final TextBox nameField = new TextBox();
    nameField.setText("");
    nameField.setVisible(true);
    createRestaurantTable.setText(0, 0, "Name");
    createRestaurantTable.setWidget(0, 1, nameField);

    final TextBox typeOfFoodField = new TextBox();
    typeOfFoodField.setText("");
    typeOfFoodField.setVisible(true);
    createRestaurantTable.setText(1, 0, "Type Of Food");
    createRestaurantTable.setWidget(1, 1, typeOfFoodField);

    // @ TODO telephone number strict form like
    // make users choose one of the following:
    // 042, 02, 062 .. ? or allowing 4 digits in the last part.
    final TextBox telephoneNumberField = new TextBox();
    telephoneNumberField.setText("");
    createRestaurantTable.setText(2, 0, "Telephone Number");
    createRestaurantTable.setWidget(2, 1, telephoneNumberField);

    final TextBox addressField = new TextBox();
    // @ TODO address feasibility? check
    addressField.setText("");
    addressField.setVisible(true);
    createRestaurantTable.setText(3, 0, "Address of Restaurant");
    createRestaurantTable.setWidget(3, 1, addressField);

    createButton = new Button("Create");
    verticalPannel.add(createButton);

    messageLabel = new Label("");
    messageLabel.setVisible(false);
    verticalPannel.add(messageLabel);

    popUpPannel.setPopupPosition(450, 200);

    createButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {

	// TODO: feasiblity check/field validation
	RestaurantJson restaurant = new RestaurantJson();
	restaurant.setName(nameField.getText());
	restaurant.setAddress(addressField.getText());
	restaurant.setTelephoneNumber(telephoneNumberField.getText());
	ArrayList<String> typeOfFoodList = new ArrayList<String>();
	typeOfFoodList.add(typeOfFoodField.getText());
	restaurant.setTypeOfFood(typeOfFoodList);

	messageLabel.setVisible(false);
	createButton.setEnabled(false);
	createButton.setText("Sending...");

	// TODO: check whether it is exist or not

	HttpInterface.doPostJson(url, restaurant, new CreateRestaurantRequestCallback());
      }
    });
  }

  class CreateRestaurantRequestCallback extends AbstractRequestCallback {
    public void onResponseReceived(Request request, Response response) {
      System.out.println(response.getText());
      if (responseIsOk(response)) {
	//TODO : clear the fields
	// messageLabel.setText("creation successful");
	popUpPannel.hide();
	// TODO display a message in the main page
      } else {
	messageLabel.setText("creation failed");
      }
      messageLabel.setVisible(true);
      createButton.setText("Create");
      createButton.setEnabled(true);
    }
  }
}
