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
import com.smartgwt.client.widgets.IButton;

public class MiniProject implements EntryPoint{

	private UserServiceQuery serviceQuery = new UserServiceQuery(this);
	private User activeUser;
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel categoryPanel = new VerticalPanel();
	private VerticalPanel contentPanel = new VerticalPanel();
	private HorizontalPanel topPanel = new HorizontalPanel();
	private HorizontalPanel bodyPanel = new HorizontalPanel();
	private HorizontalPanel bottomPanel = new HorizontalPanel();
	private Image logo = new Image("images/Pumba.png");
	private final Label loginLabel = new Label("Login");
	private ArrayList<Category> categories = new ArrayList<Category>();
	private ArrayList<Post> visiblePosts = new ArrayList<Post>();
	private Tree staticTree = new Tree();
	private int[] treeId;
	private TreeItem[] treeItems;
	private Button newPostButton = new Button("Add post");

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

		loginLabel.addStyleName("loginLabel");
		topPanel.add(loginLabel);


		/*
		 * Add contentTree to the categoryPanel
		 */
		newPostButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getNewPostScreen();
			}
		});
		categoryPanel.add(newPostButton);
		bodyPanel.add(categoryPanel);
		bodyPanel.add(contentPanel);
		mainPanel.add(topPanel);
		mainPanel.add(bodyPanel);
		mainPanel.add(bottomPanel);


		//hämtar div-tagg från html
		RootPanel.get("content").getElement().getStyle().setPosition(Position.RELATIVE);
		RootPanel.get("content").add(mainPanel);

		serviceQuery.getCategories();
		serviceQuery.getPosts(null);

	}
	/**
	 * Create a static tree with some data in it.
	 *
	 * @return the new tree
	 */
	private Tree createStaticTree() {

		Tree staticTree = new Tree();
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
		categoryPanel.add(staticTree);
	}

	public void setVisiblePosts(ArrayList<Post> result) {
		visiblePosts = result;
		updatePosts();

	}
	private void updatePosts() {
		Label headerLabel = new Label();
		Label descriptionLabel = new Label();
		Label priceLabel = new Label();
		Label cityLabel = new Label();
		Label dateLabel = new Label();
		Label shippableLabel = new Label();
		VerticalPanel vertPanel = new VerticalPanel();
		HorizontalPanel postPanel = new HorizontalPanel();
		HorizontalPanel horPanel = new HorizontalPanel();
		// TODO Auto-generated method stub
		contentPanel.clear();

		for(int i = 0 ; i < visiblePosts.size() ; i++){
			vertPanel = new VerticalPanel();
			horPanel = new HorizontalPanel();
			postPanel = new HorizontalPanel();
			headerLabel = new Label();
			descriptionLabel = new Label();
			priceLabel = new Label();
			cityLabel = new Label();
			dateLabel = new Label();
			shippableLabel = new Label();

			headerLabel.setText(visiblePosts.get(i).getHeader());
			headerLabel.addStyleName("postHeader");

			descriptionLabel.setText(visiblePosts.get(i).getDescription());
			descriptionLabel.addStyleName("postDescription");

			priceLabel.setText(Double.toString(visiblePosts.get(i).getPrice()));
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

			horPanel.add(priceLabel);
			horPanel.add(cityLabel);
			horPanel.add(dateLabel);
			horPanel.add(shippableLabel);

			vertPanel.add(headerLabel);
			vertPanel.add(descriptionLabel);
			vertPanel.add(horPanel);

			//			postPanel.add(PICTURE);
			postPanel.add(vertPanel);
			postPanel.addStyleName("postPanel");
			contentPanel.add(postPanel);

		}
	}
	
	private void getNewPostScreen() {
		
		
	}
	
}