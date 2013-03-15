package com.example.miniproject.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.miniproject.shared.Category;
import com.example.miniproject.shared.Post;
import com.example.miniproject.shared.User;

public class DbCommunication {

	public String testSQL() {
		String str = "Result: ";
		try {
			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			stat.executeUpdate("create table test(first_name varchar(20), alias varchar(20), last_name varchar(20), salary double, is_allowed boolean, id int);");
			ResultSet rs = stat.executeQuery("select * from Users;");

			while (rs.next()) {
				str += "name = " + rs.getString("User_Name");
				str += "\nemail = " + rs.getString("Email");
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			str += e.toString();
			e.printStackTrace();
		} 
		return str;
	}

	public void addUser(User newUser, String password) {
		try {
			Connection conn = getConnection();
			PreparedStatement prep = conn.prepareStatement("insert into Users values (?, ?, ?, ?, ?, ?);");
			prep.setString(1, newUser.getUserName());
			prep.setString(2, password);
			prep.setString(3, newUser.getFirstName());
			prep.setString(4, newUser.getLastName());
			prep.setString(5, newUser.getEmail());
			prep.setString(6, newUser.getPhoneNumber());
			prep.execute();
			prep.close();
			conn.close();

		}catch (Exception e) {
			e.printStackTrace();
		} 

	}

	public User login(String userName, String password) {
		User foundUser = new User();		
		Connection conn = getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from Users where User_Name = '" + userName + "' and Password = '" + password + "';");

			if(rs.next()){
				foundUser.setUserName(userName);
				foundUser.setFirstName(rs.getString(3));
				foundUser.setLastName(rs.getString(4));
				foundUser.setEmail(rs.getString(5));
				foundUser.setPhoneNumber(rs.getString(6));			
			}

			rs.close();
			stat.close();
			conn.close();
			return foundUser;

		} catch(Exception e) {
			e.printStackTrace();		
		}

		return null;		
	}

	public void addPost(Post newPost) {
		try {
			Connection conn = getConnection();
			PreparedStatement prep = conn.prepareStatement("insert into Posts values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			prep.setString(1, null);
			prep.setString(2, newPost.getHeader());
			prep.setString(3, newPost.getDescription());
			prep.setDouble(4, newPost.getPrice());
			prep.setString(5, newPost.getCity());
			prep.setTimestamp(6, newPost.getDate());
			prep.setBoolean(7, newPost.isShipable());
			prep.setInt(8, newPost.getCategoryId());
			prep.setString(9, newPost.getCreator());
			prep.execute();
			prep.close();
			conn.close();

		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public Connection getConnection() { 
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://www-und.ida.liu.se/leoja139?" + "user=leoja139&password=skrollan");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Post> getPosts(Category category) {
		ArrayList<Post> postArray = new ArrayList<Post>(); 
		Post foundPost = new Post();
		Connection conn = getConnection();
		boolean parents;

		try {
			Statement stat = conn.createStatement();
			ResultSet rs;
			if(category == null) {
				rs = stat.executeQuery("select * from Posts;");
				parents = false;
			}
			else {
				rs = stat.executeQuery("select * from Posts where Category_Id = '" + category.getId() + "';");
				parents = true;
			}

			//gets all of the posts for this category
			while(rs.next()) {
				foundPost = new Post();
				foundPost.setId(rs.getInt(1));
				foundPost.setHeader(rs.getString(2));
				foundPost.setDescription(rs.getString(3));
				foundPost.setPrice(rs.getDouble(4));
				foundPost.setCity(rs.getString(5));				
				foundPost.setDate(rs.getTimestamp(6));
				foundPost.setShipable(rs.getBoolean(7));
				foundPost.setCategoryId(rs.getInt(8));
				foundPost.setCreator(rs.getString(9));
				postArray.add(foundPost);
			}
			if (parents){
				rs = stat.executeQuery("select * from Categories where Parent ='" + category.getId() + "';");

				while(rs.next()) {
					Category foundCategory = new Category();

					while(rs.next()) {
						foundCategory = new Category();
						foundCategory.setId(rs.getInt(1));
						foundCategory.setName(rs.getString(2));
						foundCategory.setParent(rs.getInt(3));
						postArray.addAll(getPosts(foundCategory));
					}

				}
			}
			rs.close();
			stat.close();
			conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();			
		}		
		return postArray;
	}

	public ArrayList<Category> getCategories() {
		ArrayList<Category> categoryArray = new ArrayList<Category>(); 
		Category foundCategory = new Category();
		Connection conn = getConnection();

		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from Categories;");

			//gets all of the posts for this category
			while(rs.next()) {
				foundCategory = new Category();
				foundCategory.setId(rs.getInt(1));
				foundCategory.setName(rs.getString(2));
				foundCategory.setParent(rs.getInt(3));

				categoryArray.add(foundCategory);
			}

			rs.close();
			stat.close();
			conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();			
		}		
		return categoryArray;
	}
}
