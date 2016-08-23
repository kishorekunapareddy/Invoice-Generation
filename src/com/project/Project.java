package com.project;

import java.util.Date;

public class Project {
	private int client;
	private int number;
	private String name;
	private String startdate;
	private String enddate;
	private String status;
	private String manager;
	private String contact;
	private int Budget;
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public int getClient() {
		return client;
	}
	public void setClient(int client) {
		this.client = client;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String fromdate) {
		this.startdate = fromdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public int getBudget() {
		return Budget;
	}
	public void setBudget(int budget) {
		Budget = budget;
	}
	
	
	

}
