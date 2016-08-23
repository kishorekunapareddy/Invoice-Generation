package com.timesheet;

import java.sql.SQLException;
import java.util.ArrayList;

import com.company.Company;
import com.db.DBconnection;
import com.employee.Employee;
import com.project.employee.EmployeeProject;

public class TimeSheetEvent {

	public ArrayList<EmployeeProject> getProject(int userId) throws ClassNotFoundException, SQLException {
		String sql="select * from project_employee where empid='"+userId+"'";
		DBconnection db=new DBconnection();
		return (ArrayList<EmployeeProject>) db.execute(sql,"employeemap");	
		
	}

	public void addTimeSheet(TimeSheet ts) throws ClassNotFoundException, SQLException {
		String sql="select tsId,EmpId,ProId ,Month,week1  from timesheet where empId='"+ts.getEmpId()+"' and proid='"+ts.getProId()+"' and month='"+ts.getMonth()+"'";
		DBconnection db=new DBconnection();
		
		TimeSheet t=new TimeSheet();
		try{
		t=(TimeSheet) db.execute(sql,"timesheet").get(0);
		
		String sql1="update  timesheet set "
				+ ts.getWeekId()+"='"+ts.getWeek()+"' "
				+ " where empId='"+ts.getEmpId()+"' and proid='"+ts.getProId()+"' and month='"+ts.getMonth()+"'";
				;
		db.insertUpdate(sql1);
		
		}catch(IndexOutOfBoundsException e){
			String sql1="insert into timesheet values(null,'"
					+ts.getEmpId()+"','"
					+ts.getProId()+"','"
					+ts.getMonth()+"',"
					;
			String week="";
			if(ts.getWeekId().equals("week1")){
				week="'"+ts.getWeek()+"',null,null,null,null)";
			}if(ts.getWeekId().equals("week2")){
				week="null,'"+ts.getWeek()+"',null,null,null)";
			}if(ts.getWeekId().equals("week3")){
				week="null,null,'"+ts.getWeek()+"',null,null)";
			}if(ts.getWeekId().equals("week4")){
				week="null,null,null'"+ts.getWeek()+"',null)";
			}if(ts.getWeekId().equals("week5")){
				week="null,null,null,null,'"+ts.getWeek()+"')";
			}
			
			sql1=sql1+week;
			db.insertUpdate(sql1);
		}		
	}

	public TimeSheet getTimeSheet(int userId,String project, String month,String week) throws ClassNotFoundException, SQLException {
		
		String sql="select tsId,EmpId,ProId ,Month ,"+week+" from timesheet where empId='"+userId+"' and proid='"+project+"' and month='"+month+"'";
		DBconnection db=new DBconnection();
		return (TimeSheet) db.execute(sql,"timesheet").get(0);
		
	}


	public ArrayList<EmployeeProject> getEmployee(String project) throws ClassNotFoundException, SQLException {
		
		String sql="select * from project_employee where proid='"+project+"'";
		DBconnection db=new DBconnection();
		return db.execute(sql,"employeemap");
		
		
	}

	public ArrayList<TimeSheet> getAllTimeSheet() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
