package com.example.miniproject.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable{
	private int id;
	private int parent = 0;
	private String name;

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the parent
	 */
	public int getParent() {
		return parent;
	}
	/**
	 * @param i the parent to set
	 */
	public void setParent(int i) {
		this.parent = i;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
