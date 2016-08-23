package com.user;

import java.sql.SQLException;
import java.util.ArrayList;

import com.db.DBconnection;

public class UserEvent {
	
	@SuppressWarnings("unchecked")
	public ArrayList<Users> getAllUser() throws ClassNotFoundException, SQLException{
		String sql="select * from users";		
		DBconnection db=new DBconnection();
		return db.execute(sql,"user");		

		
	}
	
	public void addUser(Users u) throws ClassNotFoundException, SQLException{
		String sql="insert into users (empid,username,password,role)  select max(empId),'"
				+u.getUserName()+"','"
				+u.getPassword()+"','"
				+u.getRole()+"' from employee";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
	}

	public Users getUser(String selected) throws ClassNotFoundException, SQLException {
		String sql="select * from users where username='"+selected+"'";		
		DBconnection db=new DBconnection();
		return (Users) db.execute(sql,"user").get(0);	
	}

	public void updateUser(Users u) throws ClassNotFoundException, SQLException {
		
		String sql= "update users set "
				+"password='"+u.getPassword()+"' "
				+"where username='"+u.getUserName()+"'";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
	}
	
	public void delete(String userName ) throws ClassNotFoundException, SQLException {
		
		String sql= "delete from users where username='"+userName+"'";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
	}

}
