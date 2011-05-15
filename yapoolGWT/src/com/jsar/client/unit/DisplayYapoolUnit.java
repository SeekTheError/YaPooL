package com.jsar.client.unit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jsar.client.YapoolGWT;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;
import com.jsar.client.json.ProfileJson;
import com.jsar.client.json.ViewJson;
import com.jsar.client.json.PostJson;
import com.jsar.client.json.YapoolJson;

/**
 * 
 * @author janghoo, rem
 * 
 */
public class DisplayYapoolUnit extends AbstractUnit {

  public static DisplayYapoolUnit displayYapoolUnit;
  private Label yapoolNameLabel;
  private FlexTable postListTable;
  private PostJson tempMessage = null;
  private String currentYapoolId;
  private Button joinButton;
  private Button leaveButton;
  private TextBox messageInput;

  /**
   * constructor class
   */
  public DisplayYapoolUnit() {
    DisplayYapoolUnit.displayYapoolUnit = this;
    yapoolNameLabel = new Label("Wall Postings");
    postListTable = new FlexTable();
    joinButton = new Button("Join");
    leaveButton = new Button("Leave");
    messageInput = new TextBox();
    

    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(yapoolNameLabel);
    verticalPanel.add(postListTable);
    verticalPanel.add(messageInput);
    verticalPanel.add(joinButton);
    verticalPanel.add(leaveButton);

    RootPanel.get("displayYapoolContainer").add(verticalPanel);
    messageInput.addKeyDownHandler(new PostMessageKeydowndHandler());
    joinButton.addClickHandler(new JoinYapoolClickHandler());
    leaveButton.addClickHandler(new LeaveButtonClickHandler());

    this.SetVisible(false);
  }

  public void displayYapool(String yapoolId) {
    NavigationUnit.navigationUnit.hideAll();
    this.SetVisible(true);
    currentYapoolId = yapoolId;
    HttpInterface.doGet("/yapooldb/_design/post/_view/by_yapoolId?key=\"" + yapoolId + "\"",
	new DisplayYapoolPostRequestCallback());

    if (YapoolGWT.currentProfile != null) {
      if (YapoolGWT.currentProfile.getCurrentYapool().equals("")) {
	joinButton.setVisible(true);
	leaveButton.setVisible(false);
	messageInput.setVisible(false);
      } else if (YapoolGWT.currentProfile.getCurrentYapool().equals(currentYapoolId)) {
	joinButton.setVisible(false);
	leaveButton.setVisible(true);
	messageInput.setVisible(true);
      } else {
	joinButton.setVisible(false);
	leaveButton.setVisible(false);
	messageInput.setVisible(false);
      }
    }
  }

  public class PostMessageKeydowndHandler implements KeyDownHandler {
    @Override
    public void onKeyDown(KeyDownEvent event) {
      if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
	if (messageInput.getText() == "")
	  return;
	PostJson newPost = new PostJson();
	newPost.setMessage(messageInput.getText());
	newPost.setYapoolId(currentYapoolId);
	if (YapoolGWT.currentSession.getName() == null) {
	  Window.alert("You need to log in first!");
	  return;
	}
	newPost.setUser(YapoolGWT.currentSession.getName());
	messageInput.setText("");
	tempMessage = newPost;
	HttpInterface.doPostJson("/yapooldb/", newPost, new WritePostRequestCallback());
      }
    }
  }

  public class JoinYapoolClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      HttpInterface.doGet("/yapooldb/" + YapoolGWT.currentSession.getName() + "/", new AbstractRequestCallback() {
	@Override
	public void onResponseReceived(Request request, Response response) {
	  ProfileJson profile = new ProfileJson(response.getText());
	  if (profile.getCurrentYapool().equals("")) {
	    joinButton.setVisible(false);
	    // Update Profile - CurrentYapool
	    profile.setCurrentYapool(currentYapoolId);
	    HttpInterface.doPostJson("/yapooldb/", profile, new AbstractRequestCallback() {
	      @Override
	      public void onResponseReceived(Request request, Response response) {
		System.out.println(response.toString());
		System.out.println("Current Yapool is set Successfully");
	      }
	    }); // http doPostJson Ends
	    HttpInterface.doGet("/yapooldb/" + currentYapoolId, new AbstractRequestCallback() {
	      @Override
	      public void onResponseReceived(Request request, Response response) {
		YapoolJson yapool = new YapoolJson(response.getText());
		yapool.addMember(YapoolGWT.currentProfile.getId());
		HttpInterface.doPostJson("/yapooldb/", yapool, new AbstractRequestCallback() {
		  @Override
		  public void onResponseReceived(Request request, Response response) {
		    System.out.println(response.toString());
		    System.out.println("Joined Successfully");
		  }
		});
	      }
	    });
	    messageInput.setVisible(true);
	    leaveButton.setVisible(true);
	  }
	}
      });
    }
  }

  public class LeaveButtonClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      
      HttpInterface.doGet("/yapooldb/" + YapoolGWT.currentSession.getName() + "/", new AbstractRequestCallback() {
	@Override
	public void onResponseReceived(Request request, Response response) {
	  ProfileJson profile = new ProfileJson(response.getText());
	  if (profile.getCurrentYapool().equals(currentYapoolId)) {
	    leaveButton.setVisible(false);
	    profile.setCurrentYapool("");
	    HttpInterface.doPostJson("/yapooldb/", profile, new AbstractRequestCallback() {
	      @Override
	      public void onResponseReceived(Request request, Response response) {
		System.out.println(response.toString());
		System.out.println("Left Successfully");
	      }
	    }); 
	    messageInput.setVisible(false);
	    joinButton.setVisible(true);
	  } 
	}
      }); 
    } 
  }

  static int lastPostCount = -1;

  public class DisplayYapoolPostRequestCallback extends AbstractRequestCallback {
    @Override
    public void onResponseReceived(Request request, Response response) {
      JSONArray yapools = new ViewJson(response.getText()).getRows();
      int size = yapools.size();
      if (size > lastPostCount) {
	postListTable.removeAllRows();
	postListTable.setText(0, 0, "User Name");
	postListTable.setText(0, 1, "Message");
	for (int i = 0; i < size; i++) {
	  JSONObject temp = yapools.get(i).isObject().get("value").isObject();
	  PostJson post = new PostJson(temp);
	  int rowCounts = postListTable.getRowCount();
	  postListTable.setText(rowCounts, 0, post.getUser());
	  postListTable.setText(rowCounts, 1, post.getMessage());
	}
      }
      Timer t = new UpdatePostTimer();
      t.schedule(1000);
    }
  }
/**
 * update the post of a YaPooL! every second
 * @author rem
 *
 */
  public class UpdatePostTimer extends Timer {

    @Override
    public void run() {
      HttpInterface.doGet("/yapooldb/_design/post/_view/by_yapoolId?key=\"" + currentYapoolId + "\"",
	  new DisplayYapoolPostRequestCallback());
    }
  }

  public class WritePostRequestCallback extends AbstractRequestCallback {
    @Override
    public void onResponseReceived(Request request, Response response) {
      int rowCounts = postListTable.getRowCount();
      postListTable.setText(rowCounts, 0, tempMessage.getUser());
      postListTable.setText(rowCounts, 1, tempMessage.getMessage());
    }
  }

  @Override
  public String getContainerId() {
    return "displayYapoolContainer";
  }
}