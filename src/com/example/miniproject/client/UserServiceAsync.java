package com.example.miniproject.client;

import java.util.ArrayList;

import com.example.miniproject.shared.Category;
import com.example.miniproject.shared.Post;
import com.example.miniproject.shared.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void testSQL(AsyncCallback<String> callback);

	void addUser(User newUser, String password, AsyncCallback<Boolean> callback);

	void login(String userName, String password, AsyncCallback<User> callback);

	void addPost(Post newPost, AsyncCallback<Boolean> callback);

	void getPosts(Category category, AsyncCallback<ArrayList<Post>> callback);

	void getCategories(AsyncCallback<ArrayList<Category>> callback);

}
