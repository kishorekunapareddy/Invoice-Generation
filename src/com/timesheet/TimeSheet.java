package com.timesheet;

public class TimeSheet {
	private int tmid;
	private int empId;
	private int proId;
	private String month;
	private String week;
	private String weekId;
	
	
	public String getWeekId() {
		return weekId;
	}
	public void setWeekId(String weekId) {
		this.weekId = weekId;
	}
	public int getTmid() {
		return tmid;
	}
	public void setTmid(int tmid) {
		this.tmid = tmid;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getProId() {
		return proId;
	}
	public void setProId(int proId) {
		this.proId = proId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
}
