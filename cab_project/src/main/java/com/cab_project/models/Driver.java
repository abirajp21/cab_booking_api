package com.cab_project.models;

public class Driver {
	private int id;
	private String name;
	private String phoneNo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	
	
	public String toString()
	{
		String details = this.id + " " + this.name + " " + this.phoneNo;
		return details;
	}

}
