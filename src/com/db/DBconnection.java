package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.client.Client;
import com.company.Company;
import com.employee.Employee;
import com.invoice.Invoice;
import com.project.Project;
import com.project.employee.EmployeeProject;
import com.timesheet.TimeSheet;
import com.user.Users;

public class DBconnection {

	@SuppressWarnings({ "unused", "rawtypes" })
	public ArrayList execute(String sql, String name) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice","root","root");      
        Statement st = con.createStatement();   
		try{ 
	        ResultSet rs = st.executeQuery(sql);
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int count=rsmd.getColumnCount();
	        
	        if(name.equals("user")){
	        	return createUserList(rs);
	        }
	        if(name.equals("client")){
	        	return createClientList(rs);
	        }
	        if(name.equals("employee")){
	        	return createEmployeeList(rs);
	        }
	        if(name.equals("company")){
	        	return createCompanyList(rs);
	        }
	        if(name.equals("project")){
	        	return createProjectList(rs);
	        }
	        if(name.equals("employeemap")){
	        	return createProjectMapList(rs);
	        }
	        if(name.equals("timesheet")){
	        	return createTimeSheetList(rs);
	        }
	        if(name.equals("employeeProject")){
	        	return createEmpProjectList(rs);
	        }
	        if(name.equals("invoice")){
	        	return createincoice(rs);
	        }
	        
	        
		}finally{
	        st.close();
	        con.close();
		}
        return null;
	}
	

	private ArrayList createincoice(ResultSet rs) throws SQLException {
		ArrayList<Invoice> inList= new ArrayList<Invoice>();
		while(rs.next()){
			Invoice i=new Invoice();
			i.setEmpId(rs.getInt(1));
			i.setMonth(rs.getString(2));
			ArrayList<String> project=new ArrayList<String>();
			project.add(rs.getString(3));
			project.add(rs.getString(4));
			project.add(rs.getString(5));
			project.add(rs.getString(6));
			project.add(rs.getString(7));
			i.setProject(project);
			i.setBillrate(rs.getInt(8));
			i.setTotalAmount(rs.getDouble(9));
			inList.add(i);
		}
		return inList;
	}


	private ArrayList createEmpProjectList(ResultSet rs) throws SQLException {
		ArrayList<Integer> proList=new ArrayList<Integer>();
		while(rs.next()){
			proList.add(rs.getInt(1));
		}
		return proList;
	}


	private ArrayList createTimeSheetList(ResultSet rs) throws SQLException {
		ArrayList<TimeSheet> tl=new ArrayList<TimeSheet>();
		while(rs.next()){
			TimeSheet t=new TimeSheet();
			t.setTmid(rs.getInt(1));
			t.setEmpId(rs.getInt(2));
			t.setProId(rs.getInt(3));
			t.setMonth(rs.getString(4));
			t.setWeek(rs.getString(5));
			tl.add(t);
			
		}
		return tl;
	}


	private ArrayList createProjectMapList(ResultSet rs) throws SQLException {
		ArrayList<EmployeeProject> pl=new ArrayList<EmployeeProject>();
		while(rs.next()){
			EmployeeProject ep=new EmployeeProject();
			ep.setProjectId(rs.getInt(1));
			ep.setEmpId(rs.getInt(2));
			pl.add(ep);
		}
		
		return pl;
	}


	private ArrayList createProjectList(ResultSet rs) throws SQLException {
		ArrayList<Project> pl=new ArrayList<Project>();
		while(rs.next()){
			Project p=new Project();
			p.setClient(rs.getInt(1));
			p.setNumber(rs.getInt(2));
			p.setName(rs.getString(3));
			p.setStartdate(rs.getDate(4).toString());
			p.setEnddate(rs.getDate(5).toString());
			p.setStatus(rs.getString(6));
			p.setManager(rs.getString(7));
			p.setContact(rs.getString(8));
			p.setBudget(rs.getInt(9));
			pl.add(p);
		}
		return pl;
	}


	private ArrayList createEmployeeList(ResultSet rs) throws SQLException {
		ArrayList<Employee> el=new ArrayList<Employee>();
		while(rs.next()){
			Employee e=new Employee();
			e.setEmpid(rs.getInt(1));
			e.setName(rs.getString(2));
			e.setTitle(rs.getString(3));
			e.setBillrate(rs.getInt(4));
			e.setRole(rs.getString(5));
			el.add(e);
			
		}
		return el;
	}


	private ArrayList createClientList(ResultSet rs) throws SQLException {
		ArrayList<Client> cl=new ArrayList<Client>();
        while(rs.next()){
        	Client c=new Client();
        	c.setClientid(rs.getInt(1));
        	c.setName(rs.getString(2));
        	c.setAddress1(rs.getString(3));
        	c.setAddress2(rs.getString(4));
        	c.setCity(rs.getString(5));
        	c.setState(rs.getString(6));
        	c.setZip(rs.getInt(7));
        	c.setEmail(rs.getString(8));
        	c.setContact(rs.getString(9));
        	c.setInvoicefrq(rs.getString(10));
        	c.setBillingterms(rs.getString(11));
        	c.setInvoiceGroup(rs.getString(12));
        	cl.add(c);
        }

		
		return cl;
	}
	
	private ArrayList createCompanyList(ResultSet rs) throws SQLException {
		ArrayList<Company> cl=new ArrayList<Company>();
        while(rs.next()){
        	Company c=new Company();
        	c.setName(rs.getString(1));
        	c.setAddress1(rs.getString(2));
        	c.setAddress2(rs.getString(3));
        	c.setCity(rs.getString(4));
        	c.setState(rs.getString(5));
        	c.setZip(rs.getInt(6));
        	cl.add(c);
        }

		
		return cl;
	}



	public ArrayList<Users> createUserList(ResultSet rs) throws SQLException{
	  	ArrayList<Users> ul=new ArrayList<Users>();
        while(rs.next()){
        	Users u=new Users();
        	u.setUserId(rs.getString(1));
        	u.setUserName(rs.getString(2));
        	u.setPassword(rs.getString(3));
        	ul.add(u);
        }
        return ul;
	}
	
	public void insertUpdate(String sql) throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice","root","root");      
        Statement st = con.createStatement();   
		try{ 
	       st.executeUpdate(sql);
		}finally{
	        st.close();
	        con.close();
		}
		
	}
	
	

}
