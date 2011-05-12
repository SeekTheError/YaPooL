package com.jsar.client.unit;

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
  
  public static final String containerId="navigationUnitContainer";
  
  public NavigationUnit(){
   Label yapoolNavigatonLabel=new Label("YaPooLs");
   VerticalPanel yapoolPanel=new VerticalPanel();
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

}
