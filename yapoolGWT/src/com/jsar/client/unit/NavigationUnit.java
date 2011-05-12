package com.jsar.client.unit;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NavigationUnit {
  
  public static final String containerId="navigationUnitContainer";
  
  public NavigationUnit(){
   VerticalPanel yapoolPanel=new VerticalPanel();
   RootPanel.get("yapoolNavigationContainer").add(yapoolPanel);
   
   VerticalPanel restaurantPanel=new VerticalPanel();
   RootPanel.get("restaurantNavigationContainer").add(restaurantPanel);
   
   VerticalPanel myPagePanel=new VerticalPanel();
   RootPanel.get("myPageNavigationContainer").add(myPagePanel);
   
   
    
    
  }

}
