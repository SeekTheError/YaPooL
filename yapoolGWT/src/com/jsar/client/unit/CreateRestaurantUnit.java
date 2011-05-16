package com.jsar.client.unit;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.RestaurantJson;
import com.jsar.client.util.MessageDisplayer;

/**
 * this unit display a Pop Up that allow user to register
 * 
 * @author hyahn
 * 
 */

public class CreateRestaurantUnit {
  public static CreateRestaurantUnit createRestaurantUnit;

  /* CreateRestaurantContainer */
  public static String url = "/yapooldb/";

  private FlexTable createRestaurantTable; // form table
  private TextBox nameField = new TextBox();
  private int indexStartingTypesOfFood;
  // in the createRestaurantTable;
  // which row index at which the delete button clicked?
  private ArrayList<TextBox> typesOfFoodTextBoxes;
  private ListBox telephoneNumber_first;
  private TextBox telephoneNumber_middle;
  private TextBox telephoneNumber_last;
  private TextBox addressField;
  
  private Label messageLabel; // for informing some errors
  private Button createButton;
  private Button addTypeOfFoodButton;
  
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
    nameField.setText("");
    for(int i=1;i<typesOfFoodTextBoxes.size();++i)
    {
      createRestaurantTable.removeRow(indexStartingTypesOfFood+1);
      typesOfFoodTextBoxes.remove(i);
    }    
    ((TextBox)typesOfFoodTextBoxes.get(0)).setText("");
    createButton.setText("Create");
    createButton.setEnabled(true);
    
    telephoneNumber_first.
      setItemSelected(
          telephoneNumber_first.getSelectedIndex(), false);
    telephoneNumber_middle.setText("");
    telephoneNumber_last.setText("");
    addressField.setText("");
    
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

    nameField.setText("");
    nameField.setVisible(true);
    createRestaurantTable.setText(0, 0, "Name");
    createRestaurantTable.setWidget(0, 1, nameField);

    HorizontalPanel typeOfFoodPanel = new HorizontalPanel();
    typeOfFoodPanel.add(new Label("Type Of Food"));
    addTypeOfFoodButton = new Button("+");
    addTypeOfFoodButton.addClickHandler(new AddTypeOfFoodButtonClickHandler());
    typesOfFoodTextBoxes = new ArrayList<TextBox>();
    typeOfFoodPanel.add(addTypeOfFoodButton);
    indexStartingTypesOfFood = 1;
    final TextBox typeOfFoodField = new TextBox();
    typeOfFoodField.setText("");

    typesOfFoodTextBoxes.add(typeOfFoodField);
    createRestaurantTable.setWidget(indexStartingTypesOfFood, 0,
        typeOfFoodPanel);
    createRestaurantTable.setWidget(indexStartingTypesOfFood, 1,
        typeOfFoodField);

    // @ TODO telephone number strict form like
    // make users choose one of the following:
    // 042, 02, 062 .. ? or allowing 4 digits in the last part.

    final HorizontalPanel telephoneNumber_panel = new HorizontalPanel();
    telephoneNumber_first = new ListBox();
    telephoneNumber_first.addItem("041");
    telephoneNumber_first.addItem("042");
    telephoneNumber_first.addItem("043");
    telephoneNumber_first.setVisibleItemCount(1);
    telephoneNumber_middle = new TextBox();
    telephoneNumber_middle.setMaxLength(4);
    telephoneNumber_middle.setWidth("3em");
    telephoneNumber_middle.setText("0000");
    telephoneNumber_last = new TextBox();
    telephoneNumber_last.setMaxLength(4);
    telephoneNumber_last.setWidth("3em");
    telephoneNumber_last.setText("0000");
    telephoneNumber_panel.add(telephoneNumber_first);
    telephoneNumber_panel.add(new Label("-"));
    telephoneNumber_panel.add(telephoneNumber_middle);
    telephoneNumber_panel.add(new Label("-"));
    telephoneNumber_panel.add(telephoneNumber_last);
    createRestaurantTable.setText(2, 0, "Telephone Number");
    createRestaurantTable.setWidget(2, 1, telephoneNumber_panel);

    addressField = new TextBox();
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

        String telephoneNumber = new String();

        if (!telephoneNumber_first.getItemText(
            telephoneNumber_first.getSelectedIndex()).matches("[0-9]+")
            || !telephoneNumber_middle.getText().matches("[0-9]+")
            || !telephoneNumber_last.getText().matches("[0-9]+")) {
          messageLabel.setVisible(true);
          messageLabel.setText("invalid character in the telephone number");
          return;
        }

        telephoneNumber += telephoneNumber_first
            .getItemText(telephoneNumber_first.getSelectedIndex());
        telephoneNumber += "-" + telephoneNumber_middle.getText();
        telephoneNumber += "-" + telephoneNumber_last.getText();
        restaurant.setTelephoneNumber(telephoneNumber);
        ArrayList<String> typeOfFoodList = new ArrayList<String>();
        typeOfFoodList = getTypesOfFood(typesOfFoodTextBoxes);
        restaurant.setTypeOfFood(typeOfFoodList);

        messageLabel.setVisible(false);
        createButton.setEnabled(false);
        createButton.setText("Sending...");

        HttpInterface.doPostJson(url, restaurant,
            new CreateRestaurantRequestCallback());
      }
    });
  }

  class CreateRestaurantRequestCallback extends AbstractRequestCallback {
    public void onResponseReceived(Request request, Response response) {
      // TODO deal with bugs when not in sign-in state & clicking the button
      System.out.println(response.getText());
      if (responseIsOk(response)) {
        MessageDisplayer.DisplayMessage(
            "Restaurant information Successfully Added.");
        ListRestaurantUnit.listRestaurantUnit.loadList();
        popUpPannel.hide();
      } else {
        // TODO: more exactly, check whether it is exist or not
        messageLabel
            .setText("a restaurant having the same telephone number already exists.");
      }
      messageLabel.setVisible(true);
      createButton.setText("Create");
      createButton.setEnabled(true);
    }
  }

  ArrayList<String> getTypesOfFood(ArrayList<TextBox> typesOfFoodTextBoxes) {
    ArrayList<String> typesOfFoodStrings = new ArrayList<String>();
    for (int i = 0; i < typesOfFoodTextBoxes.size(); ++i){
    	if(!typesOfFoodTextBoxes.get(i).getText().equals(""))
    		typesOfFoodStrings.add(typesOfFoodTextBoxes.get(i).getText());
    }
    return typesOfFoodStrings;
  }

  class AddTypeOfFoodButtonClickHandler implements ClickHandler {
    public void onClick(ClickEvent event) {

      createRestaurantTable.insertRow(indexStartingTypesOfFood
          + typesOfFoodTextBoxes.size());
      HorizontalPanel typeOfFoodPanel = new HorizontalPanel();
      final TextBox newTypeOfFood = new TextBox();
      newTypeOfFood.setText("");
      newTypeOfFood.setVisible(true);
      typeOfFoodPanel.add(newTypeOfFood);
      Button deleteButton = new Button("x");
      deleteButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          int rowIndexTextBox=indexStartingTypesOfFood+
            typesOfFoodTextBoxes.indexOf(newTypeOfFood);
          createRestaurantTable.removeRow(rowIndexTextBox);
          
          typesOfFoodTextBoxes.remove(newTypeOfFood);
        }
      });
      typeOfFoodPanel.add(deleteButton);
      createRestaurantTable.setWidget(indexStartingTypesOfFood
          + typesOfFoodTextBoxes.size(), 1, typeOfFoodPanel);
      typesOfFoodTextBoxes.add(newTypeOfFood);
    }
  }
}
