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
import com.google.gwt.json.client.JSONString;
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

public class DisplayYapoolUnit extends AbstractUnit {

	public static DisplayYapoolUnit displayYapoolUnit;
	private Label yapoolNameLabel;
	private FlexTable postListTable;
	private PostJson tempMessage = null;
	private String currentYapoolId;
	private Button joinButton;
	private Button leaveButton;

	public void displayYapool(String yapoolId) {
		NavigationUnit.navigationUnit.hideAll();
		this.SetVisible(true);

		currentYapoolId = yapoolId;
		postListTable.removeAllRows();
		postListTable.setText(0, 0, "User Name");
		postListTable.setText(0, 1, "Message");
		HttpInterface.doGet("/yapooldb/_design/post/_view/by_yapoolId?key=\""
				+ yapoolId + "\"", new DisplayYapoolRequestCallback());
	}

	public DisplayYapoolUnit() {
		DisplayYapoolUnit.displayYapoolUnit = this;
		yapoolNameLabel = new Label("Wall Postings");
		postListTable = new FlexTable();
		joinButton = new Button("Join");
		leaveButton = new Button("Leave");
		final TextBox messageInput = new TextBox();
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(yapoolNameLabel);
		verticalPanel.add(postListTable);
		verticalPanel.add(messageInput);
		verticalPanel.add(joinButton);
		verticalPanel.add(leaveButton);
		leaveButton.setVisible(false);
		
		RootPanel.get("displayYapoolContainer").add(verticalPanel);

		messageInput.addKeyDownHandler(new KeyDownHandler() {
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
					// System.out.println(newPost);
					tempMessage = newPost;
					HttpInterface.doPostJson("/yapooldb/", newPost,
							new MessageRequestCallback());
				}
			}
		});
		
		joinButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				//joinButton.setVisible(false);
				
				HttpInterface.doGet("/yapooldb/" + YapoolGWT.currentSession.getName() + "/", new CurrentSessionRequestCallback());
			}
		});

		this.SetVisible(false);
	}

	public class DisplayYapoolRequestCallback extends AbstractRequestCallback {

		@Override
		public void onResponseReceived(Request request, Response response) {
			System.out.println("ListYaPool\n" + response.getText());
			JSONArray yapools = new ViewJson(response.getText()).getRows();

			int size = yapools.size();
			for (int i = 0; i < size; i++) {
				JSONObject temp = yapools.get(i).isObject().get("value")
						.isObject();
				PostJson post = new PostJson(temp);
				int rowCounts = postListTable.getRowCount();
				postListTable.setText(rowCounts, 0, post.getUser());
				postListTable.setText(rowCounts, 1, post.getMessage());
				System.out.println(post.getId());
			}
		}
	}

	public class MessageRequestCallback extends AbstractRequestCallback {

		@Override
		public void onResponseReceived(Request request, Response response) {
			System.out.println(response.toString());
			System.out.println("added Successfully");

			int rowCounts = postListTable.getRowCount();
			postListTable.setText(rowCounts, 0, tempMessage.getUser());
			postListTable.setText(rowCounts, 1, tempMessage.getMessage());
		}
	}
	
	public class CurrentSessionRequestCallback extends AbstractRequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			System.out.println("Current Profile: \n" + response.getText());
			ProfileJson profile = new ProfileJson(response.getText());
			if(profile.getCurrentYapool().equals("")){
				System.out.println("No Current Yapool");
				joinButton.setVisible(false);
				profile.setCurrentYapool(currentYapoolId);
				leaveButton.setVisible(true);
				HttpInterface.doPostJson("/yapooldb/", profile, new ProfileRequestCallback());
			}
			
			
			//int size = yapools.size();
			//for (int i = 0; i < size; i++) {
			//	JSONObject temp = yapools.get(i).isObject().get("value")
			//			.isObject();
			//	PostJson post = new PostJson(temp);
			//	int rowCounts = postListTable.getRowCount();
			//	postListTable.setText(rowCounts, 0, post.getUser());
			//	postListTable.setText(rowCounts, 1, post.getMessage());
			//	System.out.println(post.getId());
			//}
		}
	}
	
	public class ProfileRequestCallback extends AbstractRequestCallback {

		@Override
		public void onResponseReceived(Request request, Response response) {
			System.out.println(response.toString());
			System.out.println("added Successfully");

			//int rowCounts = postListTable.getRowCount();
			//postListTable.setText(rowCounts, 0, tempMessage.getUser());
			//postListTable.setText(rowCounts, 1, tempMessage.getMessage());
		}
	}

	@Override
	public String getContainerId() {
		return "displayYapoolContainer";
	}
}