package com.example.miniproject.client;

import java.util.ArrayList;

import com.example.miniproject.shared.Category;
import com.example.miniproject.shared.Post;
import com.example.miniproject.shared.User;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UserServiceQuery {

	private UserServiceAsync userSvc = GWT.create(UserService.class);
	private MiniProject mp;

	public UserServiceQuery(MiniProject mp){
		this.mp =mp;
	}

	public void testSQL(){
		userSvc.testSQL(new AsyncTestSQL());
	}

	public void addUser(User newUser, String password){
		userSvc.addUser(newUser, password, new AsyncAddUser());
	}

	public void login(String userName, String password) {
		userSvc.login(userName, password, new AsyncLogin());
	}
	
	public void addPost(Post newPost) {
		userSvc.addPost(newPost, new AsyncAddPost());
	}
	
	public void getPosts(Category category) {
		userSvc.getPosts(category, new AsyncGetPosts());
	}
	
	public void getCategories() {
		userSvc.getCategories(new AsyncGetCategories());
	}	
	
	public void isAdmin(String userName) {
		userSvc.isAdmin(userName, new AsyncIsAdmin());
	}
	
	public void removePost(int id) {
		userSvc.removePost(id, new AsyncRemovePost());		
	}
	
	public void addCategory(int parentId, String categoryName) {
		userSvc.addCategory(parentId, categoryName, new AsyncAddCategory());	
	}
	
	public void getUser() {
		userSvc.getUsers(new AsyncGetUsers());		
	}
	
	private class AsyncTestSQL implements AsyncCallback<String> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void onSuccess(String result) {
			mp.testSQL(result);
		}	
	}

	private class AsyncAddUser implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Boolean result) {

		}	
	}
	
	private class AsyncLogin implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(User result) {
			mp.successfulLogin(result);
		}	
	}
	
	private class AsyncAddPost implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Boolean result) {

		}	
	}
	
	private class AsyncGetPosts implements AsyncCallback<ArrayList<Post>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Post> result) {
			mp.setVisiblePosts(result);
		}	
	}
	
	private class AsyncGetCategories implements AsyncCallback<ArrayList<Category>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Category> result) {
			mp.setCategories(result);
		}	
	}
	
	private class AsyncIsAdmin implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Boolean result) {
			mp.setAdmin(result);
		}	
	}
	
	private class AsyncRemovePost implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Boolean result) {
			mp.updatePosts();
		}	
	}
	
	private class AsyncAddCategory implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Boolean result) {
			getCategories();
		}	
	}
	
	private class AsyncGetUsers implements AsyncCallback<ArrayList<User>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<User> result) {
			mp.setUsers(result);
		}	
	}

}
