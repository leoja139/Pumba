package com.example.miniproject.client;

import java.util.ArrayList;

import com.example.miniproject.shared.Category;
import com.example.miniproject.shared.Post;
import com.example.miniproject.shared.User;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class MiniProject implements EntryPoint{

	private UserServiceQuery serviceQuery = new UserServiceQuery(this);
	private User activeUser = null;
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel categoryPanel = new VerticalPanel();
	private VerticalPanel contentPanel = new VerticalPanel();
	private HorizontalPanel topPanel = new HorizontalPanel();
	private HorizontalPanel bodyPanel = new HorizontalPanel();
	private HorizontalPanel bottomPanel = new HorizontalPanel();
	private Image logo = new Image("images/Pumba.png");
	//	private final Label loginLabel = new Label("Login");
	private ArrayList<Category> categories = new ArrayList<Category>();
	private ArrayList<Post> visiblePosts = new ArrayList<Post>();
	private Tree staticTree = new Tree();
	private int[] treeId;
	private TreeItem[] treeItems;
	private Button newPostButton = new Button("Add post");
	private DynamicForm form;
	private boolean isAdmin = false;
	private int removeIndex;
	private boolean created = false;
	private User tempUser = new User();
	private ArrayList<User> users = new ArrayList<User>();

	@Override
	public void onModuleLoad() {



		mainPanel.addStyleName("mainPanel");
		categoryPanel.addStyleName("categoryPanel");
		contentPanel.addStyleName("contentPanel");
		topPanel.addStyleName("topPanel");
		bodyPanel.addStyleName("bodyPanel");
		bottomPanel.addStyleName("bottomPanel");
		/*
		 * Add content to the topPanel
		 */
		logo.addStyleName("logo");
		topPanel.add(logo);

		MultiWordSuggestOracle oracle = getOracle();
		SuggestBox searchField = new SuggestBox(oracle);
		searchField.addStyleName("searchField");
		topPanel.add(searchField);

		Button loginButton = new Button("Login");
		loginButton.addStyleName("loginButton");
		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getLoginScreen();
			}
		});
		topPanel.add(loginButton);


		/*
		 * Add contentTree to the categoryPanel
		 */
		newPostButton.addStyleName("newPostButton");
		newPostButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getNewPostScreen();
			}
		});
		staticTree.addStyleName("staticTree");
		categoryPanel.add(staticTree);
		categoryPanel.add(newPostButton);
		bodyPanel.add(categoryPanel);
		bodyPanel.add(contentPanel);
		mainPanel.add(topPanel);
		mainPanel.add(bodyPanel);
		mainPanel.add(bottomPanel);


		//hämtar div-tagg från html
		RootPanel.get("content").getElement().getStyle().setPosition(Position.RELATIVE);
		RootPanel.get("content").add(mainPanel);

		serviceQuery.getUser();
		serviceQuery.getCategories();		

	}
	/**
	 * Create a static tree with some data in it.
	 *
	 * @return the new tree
	 */
	public Tree createStaticTree() {

		staticTree.clear();
		treeItems = new TreeItem[categories.size()];
		treeId = new int[categories.size()];

		//initialize the treeItems, otherwise nullpointer exception
		for(int i = 0 ; i < treeItems.length ; i++){
			treeItems[i] = new TreeItem();
		}

		for(int i = 0 ; i < categories.size() ; i++){
			if(categories.get(i).getParent() == 0){
				treeItems[i] = staticTree.addTextItem(categories.get(i).getName());
				treeId[i] = categories.get(i).getId();
				//TODO
			}
			else{
				for(int j = 0 ; j < categories.size() ; j++){
					if(getCategory(categories.get(i).getParent()).getName().equals(treeItems[j].getText())){
						treeItems[i] = treeItems[j].addTextItem(categories.get(i).getName());
						treeId[i] = categories.get(i).getId();
					}
				}
			}
		}

		staticTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent event) {
				TreeItem item = (TreeItem) event.getSelectedItem();
				for(int i = 0 ; i < treeItems.length ; i++){
					if(treeItems[i]==item){
						serviceQuery.getPosts(getCategory(treeId[i]));
					}
				}


			}
		});

		return staticTree;
	}

	private Category getCategory(int id) {
		for(int i = 0; i < categories.size(); i++) {
			if(categories.get(i).getId() == id) {
				return categories.get(i);
			}
		}
		return null;
	}

	private MultiWordSuggestOracle getOracle() {
		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		/* 
		 * For testing purposes only, will eventually communicate with the server
		 */
		oracle.add("test1");
		oracle.add("test2");
		oracle.add("test3");
		oracle.add("Tekapsel");
		oracle.add("Tallrik");
		oracle.add("Hästkött");
		return oracle;
	}

	public void testSQL(String result) {
		Window.alert(result);
	}

	public void successfulLogin(User result) {
		if(result != null){
			this.activeUser = result;	
			serviceQuery.isAdmin(this.activeUser.getUserName());
			Window.alert("Successful login!");
		}
		else{
			Window.alert("Failed login!");
		}
	}

	public void setCategories(ArrayList<Category> result){
		categories = result;
		staticTree = createStaticTree();
		staticTree.setAnimationEnabled(true);		
	}

	public void setVisiblePosts(ArrayList<Post> result) {
		visiblePosts = result;
		updatePosts();

	}
	public void updatePosts() {
		Label headerLabel = new Label();
		Label descriptionLabel = new Label();
		Label priceLabel = new Label();
		Label cityLabel = new Label();
		Label dateLabel = new Label();
		Label shippableLabel = new Label();
		Label firstNameLabel = new Label();
		Label emailLabel = new Label();
		Label phoneNumberLabel = new Label();
		VerticalPanel itemPanel = new VerticalPanel();
		VerticalPanel userPanel = new VerticalPanel();
		HorizontalPanel postPanel = new HorizontalPanel();
		HorizontalPanel detailPanel = new HorizontalPanel();
		contentPanel.clear();

		if(isAdmin && !created){
			Button newCategoryButton = new Button("New Category");
			newCategoryButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {						
					getNewCategoryScreen();
				}
			});
			categoryPanel.add(newCategoryButton);
			created = true;
		}

		for(int i = 0 ; i < visiblePosts.size() ; i++){
			itemPanel = new VerticalPanel();
			itemPanel.addStyleName("itemPanel");
			userPanel = new VerticalPanel();
			userPanel.addStyleName("userPanel");
			detailPanel = new HorizontalPanel();
			detailPanel.addStyleName("detailPanel");
			postPanel = new HorizontalPanel();
			postPanel.addStyleName("postPanel");
			headerLabel = new Label();
			headerLabel.addStyleName("postHeader");
			descriptionLabel = new Label();
			descriptionLabel.addStyleName("postDescription");
			priceLabel = new Label();
			priceLabel.addStyleName("postPrice");
			cityLabel = new Label();
			cityLabel.addStyleName("postCity");
			dateLabel = new Label();
			dateLabel.addStyleName("postDate");
			shippableLabel = new Label();
			shippableLabel.addStyleName("postShipable");
			firstNameLabel = new Label();
			firstNameLabel.addStyleName("postFirstName");
			emailLabel = new Label();
			emailLabel.addStyleName("postEmail");
			phoneNumberLabel = new Label();
			phoneNumberLabel.addStyleName("postPhoneNumber");

			headerLabel.setText(visiblePosts.get(i).getHeader());
			headerLabel.addStyleName("postHeader");

			descriptionLabel.setText(visiblePosts.get(i).getDescription());
			descriptionLabel.addStyleName("postDescription");

			priceLabel.setText("Price: " + Double.toString(visiblePosts.get(i).getPrice()) + "kr");
			priceLabel.addStyleName("postPrice");

			cityLabel.setText(visiblePosts.get(i).getCity());
			cityLabel.addStyleName("postCity");

			dateLabel.setText(visiblePosts.get(i).getDate().toString());
			dateLabel.addStyleName("postDate");

			if(visiblePosts.get(i).isShipable()){
				shippableLabel.setText("User will ship!");
			}
			else{
				shippableLabel.setText("User will not ship!");
			}
			shippableLabel.addStyleName("postShippable");

			for(int j = 0; j < users.size(); j++) {
				if(users.get(j).getUserName().equalsIgnoreCase(visiblePosts.get(i).getCreator())) {
					tempUser = users.get(j);
					break;
				}
			}

			firstNameLabel.setText(tempUser.getFirstName());
			firstNameLabel.addStyleName("postNameLabel");

			emailLabel.setText(tempUser.getEmail());
			emailLabel.addStyleName("postEmailLabel");

			phoneNumberLabel.setText(tempUser.getPhoneNumber());
			phoneNumberLabel.addStyleName("postPhoneNumberPrice");

			userPanel.add(firstNameLabel);
			userPanel.add(emailLabel);
			userPanel.add(phoneNumberLabel);

			detailPanel.add(priceLabel);
			detailPanel.add(cityLabel);
			detailPanel.add(dateLabel);
			detailPanel.add(shippableLabel);

			itemPanel.add(headerLabel);
			itemPanel.add(descriptionLabel);
			itemPanel.add(detailPanel);
			removeIndex = i;

			if(isAdmin){
				final Button removeButton = new Button("Remove");
				removeButton.setTitle(Integer.toString(removeIndex));
				removeButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						serviceQuery.removePost(visiblePosts.get(Integer.parseInt(removeButton.getTitle())).getId());						
						visiblePosts.remove(Integer.parseInt(removeButton.getTitle())); // this assumes that the query will be successful
					}
				});
				itemPanel.add(removeButton);
			}
			//			postPanel.add(PICTURE);
			postPanel.add(itemPanel);
			postPanel.add(userPanel);
			postPanel.addStyleName("postPanel");
			contentPanel.add(postPanel);			
		}

	}

	private void getLoginScreen() {
		form = new DynamicForm(); 

		contentPanel.clear();
		form.setWidth(800);  
		TextItem userNameField = new TextItem("userName", "Enter username");   
		userNameField.setWidth(400);

		PasswordItem passwordField = new PasswordItem("password", "Enter password");
		passwordField.setWidth(400);  


		form.setFields(userNameField, passwordField);
		form.draw();
		contentPanel.add(form);

		Button sendButton = new Button("Login");
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String userName = form.getField("userName").getValue().toString();
				String password = form.getField("password").getValue().toString();

				serviceQuery.login(userName, password);



			}
		});
		contentPanel.add(sendButton);
	}

	private void getNewCategoryScreen() {
		form = new DynamicForm(); 
		contentPanel.clear();

		form.setWidth(800);  
		TextItem parentIdField = new TextItem("parentId", "Parent ID");  
		parentIdField.setMask("##");  
		parentIdField.setWidth(400);
		//firstName.setHint("<nobr>>?<??????????????<nobr>");  

		TextItem categoryNameField = new TextItem("categoryName", "Category Name");  
		categoryNameField.setMask(">?<??????????????");  
		categoryNameField.setWidth(400);  

		form.setFields(parentIdField, categoryNameField);  
		form.draw();  
		contentPanel.add(form);

		Button submitButton = new Button("Add");

		submitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int parentId = Integer.parseInt(form.getField("parentId").getValue().toString());
				String categoryName = form.getField("categoryName").getValue().toString();
				serviceQuery.addCategory(parentId, categoryName);
				form.reset();
			}
		});

		contentPanel.add(submitButton);
	}

	private void getNewPostScreen() {
		form = new DynamicForm(); 

		contentPanel.clear();

		form.setWidth(800);  


		TextItem headerField = new TextItem("header", "Header");   
		headerField.setWidth(400);

		TextAreaItem descriptionField = new TextAreaItem("description", "Description");  
		descriptionField.setWidth(400);

		TextItem priceField = new TextItem("price", "Price");  
		priceField.setMask("######## SEK");  
		priceField.setWidth(400);

		TextItem cityField = new TextItem("city", "City");  
		cityField.setMask(">?<??????????????");   
		cityField.setWidth(400);

		TextItem categoryField = new TextItem("category", "Category Id");  
		categoryField.setMask("##");  
		categoryField.setWidth(400);

		CheckboxItem shipableBox = new CheckboxItem("shipable", "Are you willing to ship this item?");
		shipableBox.setValue(false);

		if(activeUser == null){
			TextItem firstNameField = new TextItem("firstName", "First name");  
			firstNameField.setMask(">?<??????????????");  
			firstNameField.setWidth(400);
			//firstName.setHint("<nobr>>?<??????????????<nobr>");  

			TextItem lastNameField = new TextItem("lastName", "Last name");  
			lastNameField.setMask(">?<??????????????");  
			lastNameField.setWidth(400);  

			TextItem phoneNumberField = new TextItem("phoneNumber", "Phone Number");  
			phoneNumberField.setMask("###-### ## ##");  
			phoneNumberField.setWidth(400); 

			TextItem emailField = new TextItem("email", "E-mail adress");    
			emailField.setWidth(400);

			form.setFields(firstNameField, lastNameField, phoneNumberField, emailField, headerField, descriptionField, priceField, cityField, categoryField, shipableBox);  
		}else{
			form.setFields(headerField, descriptionField, priceField, cityField, categoryField, shipableBox);  
		}
		form.draw();  

		contentPanel.add(form);

		Button submitButton = new Button("Send");

		submitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String creator;
				
				String header = form.getField("header").getValue().toString();
				String description = form.getField("description").getValue().toString();
				double price = Double.parseDouble(form.getField("price").getValue().toString());
				String city = form.getField("city").getValue().toString();
				int category = Integer.parseInt(form.getField("category").getValue().toString());
				boolean shipable = Boolean.parseBoolean(form.getField("shipable").getValue().toString());

				String password = "password";
				if(activeUser == null) {

					String firstName = form.getField("firstName").getValue().toString();
					String lastName = form.getField("lastName").getValue().toString();
					String phoneNumber = form.getField("phoneNumber").getValue().toString();
					String email = form.getField("email").getValue().toString();
					creator = firstName + lastName;
					User newUser = new User();
					newUser.setFirstName(firstName);
					newUser.setLastName(lastName);
					newUser.setUserName(creator);
					newUser.setEmail(email);
					newUser.setPhoneNumber(phoneNumber);

					serviceQuery.addUser(newUser, password);
					users.add(newUser);

				}else{
					creator = activeUser.getUserName();
				}

				Post newPost = new Post();
				newPost.setHeader(header);
				newPost.setDescription(description);
				newPost.setPrice(price);
				newPost.setCity(city);
				newPost.setShipable(shipable);
				newPost.setCategoryId(category);
				newPost.setCreator(creator);

				serviceQuery.addPost(newPost);

				form.reset();
			}
		});

		contentPanel.add(submitButton);
	}

	public void setAdmin(Boolean result) {
		isAdmin = result;
		if(isAdmin)
			Window.alert("You're Admin!");
		else
			Window.alert("You're not Admin!");	
		updatePosts();
	}

	public void setUsers(ArrayList<User> result) {
		users = result;	
		serviceQuery.getPosts(null);
	}
}