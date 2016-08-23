package com.report;

import java.sql.SQLException;
import java.util.ArrayList;

import com.db.DBconnection;
import com.invoice.Invoice;

public class ReportEvent {
	
	public ArrayList<Invoice> EmployeeReport(String month) throws ClassNotFoundException, SQLException{
		String sql="select * from invoice where month='"+month+"'";	
		DBconnection db=new DBconnection();
		return (ArrayList<Invoice>) db.execute(sql,"invoice");
		
	}

}
