package com.project;

import java.sql.SQLException;
import java.util.ArrayList;

import com.project.Project;
import com.db.DBconnection;

public class ProjectEvent {
	@SuppressWarnings("unchecked")
	public ArrayList<Project> getAllProject() throws ClassNotFoundException, SQLException{
		String sql="select * from project";		
		DBconnection db=new DBconnection();
		return db.execute(sql,"project");		

		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Project> getAllProjectByManager(String userId) throws ClassNotFoundException, SQLException{
		String sql="select * from project where manager='"+userId+"'";		
		DBconnection db=new DBconnection();
		return db.execute(sql,"project");		

		
	}
	
	public void addProject(Project c) throws ClassNotFoundException, SQLException{
		String sql="Insert into project values('"
				+c.getClient()+"',"
				+"NULL,'"
				+c.getName()+"','"
				+c.getStartdate()+"','"
				+c.getEnddate()+"','"
				+c.getStatus()+"','"
				+c.getManager()+"','"
				+c.getContact()+"','"
				+c.getBudget()+"')";
				
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
		String sql1="select * from project order by number desc limit 1";
		Project p=new Project();
		p=(Project) db.execute(sql1,"project").get(0);
		String sql2="insert into project_employee values('"+p.getNumber()+"','"+c.getManager()+"')";
		db.insertUpdate(sql2);
		
	}

	public Project getProject(String selected) throws ClassNotFoundException, SQLException {
		String sql="select * from project where number='"+selected+"'";		
		DBconnection db=new DBconnection();
		return (Project) db.execute(sql,"project").get(0);	
	}

	public void updateProject(Project c) throws ClassNotFoundException, SQLException {
		
		String sql= "update project set "
				+"client='"+c.getClient()+"',"
				+"startdate='"+c.getStartdate()+"',"
				+"enddate='"+c.getEnddate()+"',"
				+"status='"+c.getStatus()+"',"
				+"manager='"+c.getManager()+"',"
				+"contact='"+c.getContact()+"',"
				+"budget='"+c.getBudget()+"'";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
	}
	
	public void delete(String selected ) throws ClassNotFoundException, SQLException {
		
		String sql= "delete from project where number='"+selected+"'";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
	}


}

