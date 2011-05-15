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
 * @author jangho, rem
 * 
 */
public class DisplayYapoolUnit extends AbstractUnit {

	public static DisplayYapoolUnit displayYapoolUnit;
	private Label yapoolNameLabel;
	private Label postTableLabel;
	private FlexTable postListTable;
	private PostJson tempMessage = null;
	private String currentYapoolId;
	private Button joinButton;
	private Button leaveButton;
	private Button closeButton;
	private Button doneButton;

	private TextBox messageInput;
	private FlexTable memberListTable;
	private YapoolJson currentYapool;

	/**
	 * constructor class
	 */
	public DisplayYapoolUnit() {
		DisplayYapoolUnit.displayYapoolUnit = this;
		yapoolNameLabel = new Label();
		postTableLabel = new Label("Wall Postings");
		postListTable = new FlexTable();
		joinButton = new Button("Join");
		leaveButton = new Button("Leave");

		closeButton = new Button("Close");
		doneButton = new Button("Done");

		messageInput = new TextBox();
		memberListTable = new FlexTable();
		currentYapool = new YapoolJson();

		joinButton.setVisible(false);
		leaveButton.setVisible(false);
		messageInput.setVisible(false);
		closeButton.setVisible(false);
		doneButton.setVisible(false);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(yapoolNameLabel);
		verticalPanel.add(memberListTable);
		verticalPanel.add(postTableLabel);
		verticalPanel.add(postListTable);
		verticalPanel.add(messageInput);
		verticalPanel.add(joinButton);
		verticalPanel.add(leaveButton);
		verticalPanel.add(closeButton);
		verticalPanel.add(doneButton);

		RootPanel.get("displayYapoolContainer").add(verticalPanel);
		messageInput.addKeyDownHandler(new PostMessageKeydowndHandler());
		joinButton.addClickHandler(new JoinYapoolClickHandler());
		leaveButton.addClickHandler(new LeaveButtonClickHandler());
		closeButton.addClickHandler(new CloseButtonClickHandler());
		doneButton.addClickHandler(new DoneButtonClickHandler());

		this.SetVisible(false);
	}

	public void displayYapool(String yapoolId) {
		NavigationUnit.navigationUnit.hideAll();
		this.SetVisible(true);
		currentYapoolId = yapoolId;

		HttpInterface.doGet("/yapooldb/" + currentYapoolId,
				new LoadYapoolRequestCallback());

		// System.out.println("this is currentYapool: " + currentYapool);

		HttpInterface.doGet("/yapooldb/_design/post/_view/by_yapoolId?key=\""
				+ yapoolId + "\"", new DisplayYapoolPostRequestCallback());

		// System.out.println("this is currentYapool: " + currentYapool);

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
				HttpInterface.doPostJson("/yapooldb/", newPost,
						new WritePostRequestCallback());
			}
		}
	}

	public class JoinYapoolClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			HttpInterface.doGet(
					"/yapooldb/" + YapoolGWT.currentSession.getName() + "/",
					new AbstractRequestCallback() {
						@Override
						public void onResponseReceived(Request request,
								Response response) {
							ProfileJson profile = new ProfileJson(response
									.getText());
							if (profile.getCurrentYapool().equals("")) {
								joinButton.setVisible(false);

								// Update Profile - currentYapool to
								// CurrentYapool
								profile.setCurrentYapool(currentYapoolId);
								HttpInterface.doPostJson("/yapooldb/", profile,
										new AbstractRequestCallback() {
											@Override
											public void onResponseReceived(
													Request request,
													Response response) {

												System.out.println(response
														.toString());
												System.out
														.println("Current Yapool is set Successfully");
											}
										}); // http doPostJson Ends
								YapoolGWT.currentProfile = profile;

								// Update Yapool - Add this user to the Yapool
								HttpInterface.doGet("/yapooldb/"
										+ currentYapoolId,
										new AbstractRequestCallback() {
											@Override
											public void onResponseReceived(
													Request request,
													Response response) {
												YapoolJson yapool = new YapoolJson(
														response.getText());
												yapool.addMember(YapoolGWT.currentProfile
														.getId());

												HttpInterface
														.doPostJson(
																"/yapooldb/",
																yapool,
																new AbstractRequestCallback() {
																	@Override
																	public void onResponseReceived(
																			Request request,
																			Response response) {
																		System.out
																				.println(response
																						.toString());
																		System.out
																				.println("Joined Successfully");
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

			HttpInterface.doGet(
					"/yapooldb/" + YapoolGWT.currentSession.getName() + "/",
					new AbstractRequestCallback() {
						@Override
						public void onResponseReceived(Request request,
								Response response) {
							ProfileJson profile = new ProfileJson(response
									.getText());
							if (profile.getCurrentYapool().equals(
									currentYapoolId)) {
								leaveButton.setVisible(false);

								// Update Profile - currentYapool to Empty
								profile.setCurrentYapool("");
								HttpInterface.doPostJson("/yapooldb/", profile,
										new AbstractRequestCallback() {
											@Override
											public void onResponseReceived(
													Request request,
													Response response) {
												System.out.println(response
														.toString());
												System.out
														.println("Current Yapool is set to empty.");
											}
										});
								YapoolGWT.currentProfile = profile;
								// Update Yapool - Add this user to the Yapool
								HttpInterface.doGet("/yapooldb/"
										+ currentYapoolId,
										new AbstractRequestCallback() {
											@Override
											public void onResponseReceived(
													Request request,
													Response response) {
												YapoolJson yapool = new YapoolJson(
														response.getText());

												JSONArray tempArray = yapool
														.getMembers();
												JSONArray newArray = new JSONArray();
												int j = 0;
												for (int i = 0; i < tempArray
														.size(); i++) {
													if (!tempArray
															.get(i)
															.isString()
															.stringValue()
															.equals(YapoolGWT.currentSession
																	.getName())) {
														newArray.set(
																j,
																new JSONString(
																		tempArray
																				.get(i)
																				.isString()
																				.stringValue()));
														j++;
													}
												}
												yapool.setMembers(newArray);
												// yapool.getMembers(YapoolGWT.currentProfile.getId());

												HttpInterface
														.doPostJson(
																"/yapooldb/",
																yapool,
																new AbstractRequestCallback() {
																	@Override
																	public void onResponseReceived(
																			Request request,
																			Response response) {
																		System.out
																				.println(response
																						.toString());
																		System.out
																				.println("Left Successfully");
																	}
																});

											}
										});

								messageInput.setVisible(false);
								joinButton.setVisible(true);
							}
						}
					});
		}
	}

	public class CloseButtonClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			closeButton.setVisible(false);
			currentYapool.setState("closed");
			memberListTable.setText(0, 1, currentYapool.getState());
			HttpInterface.doPostJson("/yapooldb/", currentYapool,
					new AbstractRequestCallback() {
						@Override
						public void onResponseReceived(Request request,
								Response response) {

							System.out.println(response.toString());
							System.out.println("YaPooL is CLOSED");
						}
					}); // http doPostJson Ends
		}
	}

	public class DoneButtonClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			doneButton.setVisible(false);
			currentYapool.setState("done");
			memberListTable.setText(0, 1, currentYapool.getState());
			HttpInterface.doPostJson("/yapooldb/", currentYapool,
					new AbstractRequestCallback() {
						@Override
						public void onResponseReceived(Request request,
								Response response) {

							System.out.println(response.toString());
							System.out.println("YaPooL is DONE");
						}
					}); // http doPostJson Ends
		}
	}

	static int lastPostCount = -1;
	static int lastMemberCount = -1;
	static String lastState = "";

	public class LoadYapoolRequestCallback extends AbstractRequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			currentYapool = new YapoolJson(response.getText());

			yapoolNameLabel.setText(currentYapool.getName());

			// System.out.println("this is currentYapool in the callback" +
			// currentYapool);

			JSONArray members = currentYapool.getMembers();
			int size = members.size();
			String state = currentYapool.getState();
			if (size != lastMemberCount || !state.equals(lastState)) {
				lastMemberCount = size;
				lastState = state;
				memberListTable.removeAllRows();
				memberListTable.setText(0, 0, "State:");
				memberListTable.setText(0, 1, currentYapool.getState());
				memberListTable.setText(1, 0, "Members:");
				memberListTable.setText(1, 1, "*" + currentYapool.getOwner());
				for (int i = 0; i < size; i++) {
					// JSONObject temp =
					// members.get(i).isObject().get("value").isObject();
					// PostJson post = new PostJson(temp);
					int rowCounts = memberListTable.getRowCount();
					memberListTable.setText(rowCounts, 0, "");
					memberListTable.setText(rowCounts, 1, members.get(i)
							.isString().stringValue());
				}

				if (state.equals("done")) {
					// Update Profile - Add this YaPooL to the passedYapool
					HttpInterface.doGet("/yapooldb/" + YapoolGWT.currentSession.getName(),
							new AbstractRequestCallback() {
								@Override
								public void onResponseReceived(Request request,	Response response) {
									YapoolGWT.currentProfile = new ProfileJson(response.getText());
									YapoolGWT.currentProfile.archieveYapool();

									HttpInterface.doPostJson("/yapooldb/", YapoolGWT.currentProfile,
											new AbstractRequestCallback() {
												@Override
												public void onResponseReceived(Request request,	Response response) {
													System.out.println(response.toString());
													System.out.println("Archieved YaPooL Successfully");
												}
											});

								}
							});
				}
			}

			if (YapoolGWT.currentProfile != null) {
				if (YapoolGWT.currentProfile.getCurrentYapool().equals("")) {
					// NO YAPOOL
					if (currentYapool.getState().equals("open")) {
						joinButton.setVisible(true);
						leaveButton.setVisible(false);
						messageInput.setVisible(false);
						closeButton.setVisible(false);
						doneButton.setVisible(false);
					} else if (currentYapool.getState().equals("closed")) {
						joinButton.setVisible(false);
						leaveButton.setVisible(false);
						messageInput.setVisible(false);
						closeButton.setVisible(false);
						doneButton.setVisible(false);
					}
				} else if (YapoolGWT.currentProfile.getCurrentYapool().equals(
						currentYapoolId)) {
					// CURRENTYAPOOL = THIS YAPOOL
					if (currentYapool.getOwner().equals(
							YapoolGWT.currentProfile.getId())) {
						// OWNER
						if (currentYapool.getState().equals("open")) {
							joinButton.setVisible(false);
							leaveButton.setVisible(false);
							messageInput.setVisible(true);
							closeButton.setVisible(true);
							doneButton.setVisible(false);
						} else if (currentYapool.getState().equals("closed")) {
							joinButton.setVisible(false);
							leaveButton.setVisible(false);
							messageInput.setVisible(true);
							closeButton.setVisible(false);
							doneButton.setVisible(true);
						}
					} else {
						// NOT OWNER
						if (currentYapool.getState().equals("open")) {
							joinButton.setVisible(false);
							leaveButton.setVisible(true);
							messageInput.setVisible(true);
							closeButton.setVisible(false);
							doneButton.setVisible(false);
						} else if (currentYapool.getState().equals("closed")) {
							joinButton.setVisible(false);
							leaveButton.setVisible(false);
							messageInput.setVisible(true);
							closeButton.setVisible(false);
							doneButton.setVisible(false);
						}
					}
				} else {
					// IN OTHER YAPOOL
					joinButton.setVisible(false);
					leaveButton.setVisible(false);
					messageInput.setVisible(false);
					closeButton.setVisible(false);
					doneButton.setVisible(false);
				}
			}

		}

	}

	public class DisplayYapoolPostRequestCallback extends
			AbstractRequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			JSONArray yapools = new ViewJson(response.getText()).getRows();
			int size = yapools.size();
			if (size > lastPostCount) {
				lastPostCount = size;
				postListTable.removeAllRows();
				postListTable.setText(0, 0, "User Name");
				postListTable.setText(0, 1, "Message");
				for (int i = 0; i < size; i++) {
					JSONObject temp = yapools.get(i).isObject().get("value")
							.isObject();
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
	 * 
	 * @author rem
	 * 
	 */
	public class UpdatePostTimer extends Timer {

		@Override
		public void run() {
			HttpInterface.doGet(
					"/yapooldb/_design/post/_view/by_yapoolId?key=\""
							+ currentYapoolId + "\"",
					new DisplayYapoolPostRequestCallback());

			HttpInterface.doGet("/yapooldb/" + currentYapoolId,
					new LoadYapoolRequestCallback());
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