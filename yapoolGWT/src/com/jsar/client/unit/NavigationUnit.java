package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * 
 * This class display the left Navigation Bar, and is responsible for the window management
 * @author rem
 *
 */
public class NavigationUnit {
  public static NavigationUnit navigationUnit;
  
  public static final String containerId="navigationUnitContainer";
  
  public NavigationUnit(){
    NavigationUnit.navigationUnit=this;   
   Label yapoolNavigatonLabel=new Label("YaPooLs");
   Label listYapoolLabel=new Label("list of Yapools");
   listYapoolLabel.addClickHandler(new ClickHandler() {
    
    @Override
    public void onClick(ClickEvent event) {
      hideAll();
      ListYapoolUnit.listYapoolUnit.SetVisible(true);     
    }
  });
     
   VerticalPanel yapoolPanel=new VerticalPanel();
   yapoolPanel.add(listYapoolLabel);
   RootPanel.get("yapoolNavigationLabel").add(yapoolNavigatonLabel);
   RootPanel.get("yapoolNavigationContainer").add(yapoolPanel);
   //Label createYapool=new Label("Create a YaPooL");
   
   Label restaurantLabel=new Label("Restaurant");
   VerticalPanel restaurantPanel=new VerticalPanel();
   RootPanel.get("restaurantNavigationContainer").add(restaurantPanel);
   RootPanel.get("restaurantNavigationLabel").add(restaurantLabel);
   
   Label myPageLabel=new Label("My Page");
   RootPanel.get("myPageNavigationLabel").add(myPageLabel);
   //VerticalPanel myPagePanel=new VerticalPanel();
   //RootPanel.get("myPageNavigationContainer").add(myPagePanel);
   
   
   
    
    
  }
  
  public void hideAll(){
    ListYapoolUnit.listYapoolUnit.SetVisible(false);
    DisplayYapoolUnit.displayYapoolUnit.SetVisible(false);
  }

}
