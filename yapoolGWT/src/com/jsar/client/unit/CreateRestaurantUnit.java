package com.jsar.client.unit;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.handler.VisibilityClickHandler;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.RestaurantJson;

import com.jsar.client.unit.YapoolRegisterUnit.RegistrationRequestCallback;


/**
 * this unit display a Pop Up that allow user to register
 * @author hyahn
 *
 */
public class CreateRestaurantUnit {

	public static final String containerId = "CreateReataurantDiv";

	  private Label messageLabel;
	  private Button createButton;
	  private PopupPanel popUpPannel;

	  public CreateRestaurantUnit() {

	    final TextBox nameField = new TextBox();
	    nameField.setText("Restaurnat Name");
	   
	    final TextBox addressField = new TextBox();
	    addressField.setText("Address of Restaurant");
	    addressField.setVisible(true);
	    
	    final TextBox telephoneNumberField = new TextBox();
	    telephoneNumberField.setText("00-000-0000");
	   
	    final TextBox typeOfFoodField = new TextBox();
	    typeOfFoodField.setText("Type of Food");
	    typeOfFoodField.setVisible(true);

	    messageLabel = new Label();
	    messageLabel.setVisible(false);
	    popUpPannel = new PopupPanel();
	    popUpPannel.setAutoHideEnabled(true);

	    Label displayRegisterPopUp = new Label("Create a restaurant!");
	    displayRegisterPopUp.addClickHandler(new ClickHandler() {

	      @Override
	      public void onClick(ClickEvent event) {
		if (!popUpPannel.isShowing()) {
		  popUpPannel.show();
		}
	      }
	    });

	    RootPanel.get("displayCreateRestaurantContainer").add(displayRegisterPopUp);
	    VerticalPanel verticalPannel = new VerticalPanel();
	    Label registerLabel = new Label("Create Restaurant",false);
	    registerLabel.setHorizontalAlignment(null);
	    verticalPannel.add(registerLabel);
	    registerLabel.addClickHandler(new VisibilityClickHandler("RestaurantCreationTable"));
	    
	    createButton = new Button("Create");
	    
	    verticalPannel.add(nameField);
	    verticalPannel.add(addressField);
	    verticalPannel.add(telephoneNumberField);
	    verticalPannel.add(typeOfFoodField);
	    verticalPannel.add(createButton);
	    verticalPannel.add(messageLabel);

	    verticalPannel.add(messageLabel);
	    popUpPannel.add(verticalPannel);
	    popUpPannel.setPopupPosition(450, 200);

	    createButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    createButton.setEnabled(false);
	    createButton.setText("Sending...");
		//String params = "?registerLogin=" + loginField.getText() + "&register" + "Email=" + emailField.getText()+ "&registerPassword=" + passwordField.getText();
		//String queryUrl = "/security/register/" + params;
		//HttpInterface.doGet(queryUrl, new RegistrationRequestCallback());
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
	  RootPanel.get("displayCreateRestaurantContainer").setVisible(visibility);
	  }
}
class CreateRestaurantClickHandler implements ClickHandler {
	public RestaurantJson restaurant;
	public static String SEND_URL = "/yapooldb/";

	public void onClick(ClickEvent event) {
		HttpInterface.doPostJson(SEND_URL, restaurant,
				new RestaurantPostRequestCallback());
	}
}

class RestaurantPostRequestCallback extends AbstractRequestCallback {
	public void onResponseReceived(Request request, Response response) {
		System.out.println(response.toString());
	}
}

