package com.employee;

import java.sql.SQLException;
import java.util.ArrayList;

import com.db.DBconnection;
import com.user.UserEvent;
import com.user.Users;

public class EmployeeEvent {
		@SuppressWarnings("unchecked")
		public ArrayList<Employee> getAllEmployee() throws ClassNotFoundException, SQLException{
			String sql="select * from employee";		
			DBconnection db=new DBconnection();
			return db.execute(sql,"employee");		

			
		}
		
		public void addEmployee(Employee c) throws ClassNotFoundException, SQLException{
			String sql="Insert into employee values(null,'"
					+c.getName()+"','"
					+c.getTitle()+"','"
					+c.getBillrate()+"','"
					+c.getRole()+"')";					
			DBconnection db=new DBconnection();
			db.insertUpdate(sql);			
			
			
			Users u=new Users();
			u.setUserName(c.getName().split(" ")[0].toLowerCase());
			u.setPassword("password");
			u.setRole(c.getRole());
			UserEvent ue=new UserEvent();
			ue.addUser(u);
			
		}

		public Employee getEmployee(String  selected) throws ClassNotFoundException, SQLException {
			String sql="select * from employee where empid='"+selected+"'";		
			DBconnection db=new DBconnection();
			return (Employee) db.execute(sql,"employee").get(0);	
		}

		public void updateEmployee(Employee c) throws ClassNotFoundException, SQLException {
			
			String sql= "update employee set "
					+"title='"+c.getTitle()+"',"
					+"role='"+c.getRole()+"',"
					+"Bilrate='"+c.getBillrate()+"'"
					+"where empId='"+c.getEmpid()+"'";
			DBconnection db=new DBconnection();
			db.insertUpdate(sql);
			
		}
		
		public void delete(int selected ) throws ClassNotFoundException, SQLException {
			
			String sql= "delete from employee where empId='"+selected+"'";
			DBconnection db=new DBconnection();
			db.insertUpdate(sql);
			UserEvent ue=new UserEvent();
			ue.delete(Integer.toString(selected));
		}
}