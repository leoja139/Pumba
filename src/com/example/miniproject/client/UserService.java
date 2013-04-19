package com.example.miniproject.client;

import java.util.ArrayList;

import com.example.miniproject.shared.Category;
import com.example.miniproject.shared.Post;
import com.example.miniproject.shared.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("User")
public interface UserService extends RemoteService {
	String testSQL();

	boolean addUser(User newUser, String password); 
	
	User login(String userName, String password);

	boolean addPost(Post newPost);

	ArrayList<Post> getPosts(Category category);

	ArrayList<Category> getCategories();

	boolean isAdmin(String userName);

	boolean removePost(int id);

	boolean addCategory(int parentId, String categoryName);

	ArrayList<User> getUsers();
}
