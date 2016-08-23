package com.project.employee;

import java.sql.SQLException;
import java.util.ArrayList;

import com.company.Company;
import com.db.DBconnection;

public class EmployeeMapEvent {
	@SuppressWarnings("unchecked")
	public ArrayList<EmployeeProject> getMap(int projectId) throws ClassNotFoundException, SQLException{
		String sql="select * from project_employee where proid='"
				+projectId +"'";
		DBconnection db=new DBconnection();
		return (ArrayList<EmployeeProject>) db.execute(sql,"employeemap");	
		
	}

	public void saveMap(ArrayList<EmployeeProject> ep) throws ClassNotFoundException, SQLException {
		String sql="delete from project_employee where proid='"
				+ep.get(0).getProjectId() +"'";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
		for(EmployeeProject e:ep){
			String sql1="insert into project_employee values('"+e.getProjectId()+"','"+e.getEmpId()+"')";
			db.insertUpdate(sql1);
		}
		
	}

	public ArrayList<Integer> getProjects(int empid) throws ClassNotFoundException, SQLException {
		String sql="select distinct proId from project_employee where empid='"
				+empid +"'";
		DBconnection db=new DBconnection();
		return (ArrayList<Integer>) db.execute(sql,"employeeProject");	

	}

}
