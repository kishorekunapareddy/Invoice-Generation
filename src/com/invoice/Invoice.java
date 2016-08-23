package com.invoice;

import java.util.ArrayList;

public class Invoice {
	private int empId;
	private String month;
	private ArrayList<String> project;
	private int billrate;
	private double totalAmount;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public ArrayList<String> getProject() {
		return project;
	}
	public void setProject(ArrayList<String> project) {
		this.project = project;
	}

	public int getBillrate() {
		return billrate;
	}
	public void setBillrate(int billrate) {
		this.billrate = billrate;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	
	

}
