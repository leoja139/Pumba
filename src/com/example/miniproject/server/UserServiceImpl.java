package com.example.miniproject.server;

import java.util.ArrayList;

import com.example.miniproject.client.UserService;
import com.example.miniproject.shared.Category;
import com.example.miniproject.shared.Post;
import com.example.miniproject.shared.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements UserService{

	DbCommunication db = new DbCommunication();
	
	public String testSQL(){
		return db.testSQL();
	}

	public boolean addUser(User newUser, String password) {
		db.addUser(newUser, password); //ändra return type?
		return false;
	}
	@Override
	public User login(String userName, String password) {		
		return db.login(userName, password);
	}

	@Override
	public boolean addPost(Post newPost) {
		db.addPost(newPost); // ändra return type?
		return false;
	}

	@Override
	public ArrayList<Post> getPosts(Category category) {
		return db.getPosts(category);
	}

	@Override
	public ArrayList<Category> getCategories() {
		return db.getCategories();
	}

	@Override
	public boolean isAdmin(String userName) {
		return db.isAdmin(userName);
	}

	@Override
	public boolean removePost(int id) {
		db.removePost(id);
		return false;
	}

	@Override
	public boolean addCategory(int parentId, String categoryName) {
		db.addCategory(parentId, categoryName);
		return false;
	}

	@Override
	public ArrayList<User> getUsers() {
		return db.getUsers();
	}
}
