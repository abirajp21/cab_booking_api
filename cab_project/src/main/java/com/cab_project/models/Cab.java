package com.cab_project.models;

import java.sql.Time;

public class Cab {
	private int id;
	private Driver driver;
	private String route;
	private CabType cabType;
	private String timing;
	
	
	public Cab()
	{
		this.driver = new Driver();
		this.cabType = new CabType();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public CabType getCabType() {
		return cabType;
	}
	public void setCabType(CabType cabType) {
		this.cabType = cabType;
	}
	public String getTiming() {
		return timing;
	}
	public void setTiming(String timing) {
		this.timing = timing;
	}
	

}
