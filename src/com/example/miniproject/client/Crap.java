//package com.example.miniproject.client;
//
//
//import com.example.miniproject.shared.FieldVerifier;
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.event.dom.client.KeyCodes;
//import com.google.gwt.event.dom.client.KeyUpEvent;
//import com.google.gwt.event.dom.client.KeyUpHandler;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.DialogBox;
//import com.google.gwt.user.client.ui.HTML;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.TextBox;
//import com.google.gwt.user.client.ui.VerticalPanel;
//
///**
// * Entry point classes define <code>onModuleLoad()</code>.
// */
//public class Crap {
//	/**
//	 * The message displayed to the user when the server cannot be reached or
//	 * returns an error.
//	 */
//	private static final String SERVER_ERROR = "An error occurred while "
//			+ "attempting to contact the server. Please check your network "
//			+ "connection and try again.";
//
//	/**
//	 * Create a remote service proxy to talk to the server-side Greeting service.
//	 */
//	private final GreetingServiceAsync greetingService = GWT
//			.create(GreetingService.class);
//
//	/**
//	 * This is the entry point method.
//	 */
//	public void onModuleLoad() {
//		final Button sendButton = new Button("Send");
//		final TextBox nameField = new TextBox();
//		nameField.setText("GWT User");
//		final Label errorLabel = new Label();
//
//		// We can add style names to widgets
//		sendButton.addStyleName("sendButton");
//
//		// Add the nameField and sendButton to the RootPanel
//		// Use RootPanel.get() to get the entire body element
//		RootPanel.get("nameFieldContainer").add(nameField);
//		RootPanel.get("sendButtonContainer").add(sendButton);
//		RootPanel.get("errorLabelContainer").add(errorLabel);
//
//		// Focus the cursor on the name field when the app loads
//		nameField.setFocus(true);
//		nameField.selectAll();
//
//		// Create the popup dialog box
//		final DialogBox dialogBox = new DialogBox();
//		dialogBox.setText("Remote Procedure Call");
//		dialogBox.setAnimationEnabled(true);
//		final Button closeButton = new Button("Close");
//		// We can set the id of a widget by accessing its Element
//		closeButton.getElement().setId("closeButton");
//		final Label textToServerLabel = new Label();
//		final HTML serverResponseLabel = new HTML();
//		VerticalPanel dialogVPanel = new VerticalPanel();
//		dialogVPanel.addStyleName("dialogVPanel");
//		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
//		dialogVPanel.add(textToServerLabel);
//		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
//		dialogVPanel.add(serverResponseLabel);
//		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
//		dialogVPanel.add(closeButton);
//		dialogBox.setWidget(dialogVPanel);
//
//		// Add a handler to close the DialogBox
//		closeButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				dialogBox.hide();
//				sendButton.setEnabled(true);
//				sendButton.setFocus(true);
//			}
//		});
//
//		// Create a handler for the sendButton and nameField
//		class MyHandler implements ClickHandler, KeyUpHandler {
//			/**
//			 * Fired when the user clicks on the sendButton.
//			 */
//			public void onClick(ClickEvent event) {
//				sendNameToServer();
//			}
//
//			/**
//			 * Fired when the user types in the nameField.
//			 */
//			public void onKeyUp(KeyUpEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					sendNameToServer();
//				}
//			}
//
//			/**
//			 * Send the name from the nameField to the server and wait for a response.
//			 */
//			private void sendNameToServer() {
//				// First, we validate the input.
//				errorLabel.setText("");
//				String textToServer = nameField.getText();
//				if (!FieldVerifier.isValidName(textToServer)) {
//					errorLabel.setText("Please enter at least four characters");
//					return;
//				}
//
//				// Then, we send the input to the server.
//				sendButton.setEnabled(false);
//				textToServerLabel.setText(textToServer);
//				serverResponseLabel.setText("");
//				greetingService.greetServer(textToServer,
//						new AsyncCallback<String>() {
//					public void onFailure(Throwable caught) {
//						// Show the RPC error message to the user
//						dialogBox
//						.setText("Remote Procedure Call - Failure");
//						serverResponseLabel
//						.addStyleName("serverResponseLabelError");
//						serverResponseLabel.setHTML(SERVER_ERROR);
//						dialogBox.center();
//						closeButton.setFocus(true);
//					}
//
//					public void onSuccess(String result) {
//						dialogBox.setText("Remote Procedure Call");
//						serverResponseLabel
//						.removeStyleName("serverResponseLabelError");
//						serverResponseLabel.setHTML(result);
//						dialogBox.center();
//						closeButton.setFocus(true);
//					}
//				});
//			}
//		}
//
//		// Add a handler to send the name to the server
//		MyHandler handler = new MyHandler();
//		sendButton.addClickHandler(handler);
//		nameField.addKeyUpHandler(handler);
//	}
//}
//

//		User newUser = new User();
//		newUser.setFirstName("Oskar");
//		newUser.setLastName("Persson");
//		newUser.setEmail("Ogge@pogge.se");
//		newUser.setUserName("Princess");
//		newUser.setPhoneNumber("03743243");
//		
//		Post newPost = new Post();
//		newPost.setHeader("Fina handskar!!!!");
//		newPost.setDescription("Jättefina är dem, jaaaa!");
//		newPost.setPrice(300.3);
//		newPost.setCity("Linköping");
//		newPost.setDate(null);//new Timestamp(System.currentTimeMillis()));
//		newPost.setShipable(true);
//		newPost.setCategoryId(1);
//		newPost.setCreator("Leoja139");
//		serviceQuery.addPost(newPost);
//		
//		
////		serviceQuery.addUser(newUser, "password"); kolla om username finns! onfailure
//		
//		serviceQuery.login("Mikol809", "micke");





		//		 Tree staticTree = new Tree();
		//		 TreeItem category1 = staticTree.addTextItem("Bostäder");
		//		 category1.addTextItem("Linköping");
		//		 category1.addTextItem("Norrköping");
		//		 TreeItem category2 = staticTree.addTextItem("Biljetter");
		//		 TreeItem category3 = staticTree.addTextItem("Litteratur");
		//		 TreeItem category4 = staticTree.addTextItem("Jobb");
		//		 category4.addTextItem("Ex-Jobb");
		//		 TreeItem category5 = staticTree.addTextItem("Blandat");
		//		 TreeItem childCategory1 = category5.addTextItem("Skor");
		//		 childCategory1.addTextItem("Finskor");


//Category[] c = new Category[6];
//
//		c[0] = new Category();
//		c[0].setId(1);
//		c[0].setName("1");
//		c[0].setParent(0);
//
//		c[1] = new Category();
//		c[1].setId(2);
//		c[1].setName("2");
//		c[1].setParent(c[0].getId());
//
//		c[2] = new Category();
//		c[2].setId(3);
//		c[2].setName("3");
//		c[2].setParent(c[0].getId());
//
//		c[3] = new Category();
//		c[3].setId(3);
//		c[3].setName("3.5");
//		c[3].setParent(c[2].getId());
//
//		c[4] = new Category();
//		c[4].setId(4);
//		c[4].setName("4");
//		c[4].setParent(0);
//
//		c[5] = new Category();
//		c[5].setId(5);
//		c[5].setName("5");
//		c[5].setParent(c[4].getId());
