package com.cab_project.models;




public class Employee {
	
	private int id;
	private String name;
	private Desigination desigination;
	private String route;
	
	
	public 
	Employee()
	{
		desigination = new Desigination();
	}
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
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public Desigination getDesigination() {
		return desigination;
	}
	public void setDesigination(Desigination desigination) {
		this.desigination = desigination;
	}
	
}
