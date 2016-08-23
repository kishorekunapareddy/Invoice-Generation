package com.invoice;

import java.sql.SQLException;
import java.util.ArrayList;

import com.db.DBconnection;

public class InvoiceEvent {

	public void addInvoice(Invoice inc) throws ClassNotFoundException, SQLException {
		DBconnection db=new DBconnection();
		String sql1="insert into invoice values( '"
				+inc.getEmpId()+"','"
				+inc.getMonth()+"',";
		ArrayList<String> project=inc.getProject();
		for(String p:project){
			sql1=sql1+"'"+p+"',";
		}
		if(project.size()<5){
			for(int i=0;i<5-project.size();i++){
				sql1=sql1+"null,";
			}
			
		}
		
		sql1=sql1+"'"
				+inc.getBillrate()+"','"
				+inc.getTotalAmount()+"')";
		
		db.insertUpdate(sql1);
	}
	
	public void deleteInvoice(String month) throws ClassNotFoundException, SQLException{
		String sql="delete from invoice where month='"+month+"'";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
	}

	public Invoice getInvoice(int empId, String month) throws ClassNotFoundException, SQLException {
		String sql="select * from invoice where empid='"+empId+"' and month='"+month+"'";
		DBconnection db=new DBconnection();
		return (Invoice) db.execute(sql,"invoice").get(0);
	}

}
