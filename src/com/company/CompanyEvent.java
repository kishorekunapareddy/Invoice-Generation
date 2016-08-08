package com.company;

import java.sql.SQLException;
import com.db.DBconnection;

public class CompanyEvent {
	public Company getCompany(String selected) throws ClassNotFoundException, SQLException {
		String sql="select * from company where name='"+selected+"'";		
		DBconnection db=new DBconnection();
		return (Company) db.execute(sql,"company").get(0);	
	}

	public void updateCompany(Company c) throws ClassNotFoundException, SQLException {
		
		String sql= "update company set "
				+"AddressLine1='"+c.getAddress1()+"',"
				+"AddressLine2='"+c.getAddress2()+"',"
				+"city='"+c.getCity()+"',"
				+"state='"+c.getState()+"',"
				+"zip='"+c.getZip()+"'";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
	}
	

}
