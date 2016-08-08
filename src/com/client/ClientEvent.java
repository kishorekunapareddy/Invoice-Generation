package com.client;

import java.sql.SQLException;
import java.util.ArrayList;

import com.db.DBconnection;


public class ClientEvent {
	@SuppressWarnings("unchecked")
	public ArrayList<Client> getAllClient() throws ClassNotFoundException, SQLException{
		String sql="select * from client";		
		DBconnection db=new DBconnection();
		return db.execute(sql,"client");		

		
	}
	
	public void addClient(Client c) throws ClassNotFoundException, SQLException{
		String sql="Insert into client values(null,'"
				+c.getName()+"','"
				+c.getAddress1()+"','"
				+c.getAddress2()+"','"
				+c.getCity()+"','"
				+c.getState()+"','"
				+c.getZip()+"','"
				+c.getEmail()+"','"
				+c.getContact()+"','"
				+c.getInvoicefrq()+"','"
				+c.getBillingterms()+"','"
				+c.getInvoiceGroup()+"')";
				
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
	}

	public Client getClient(String selected) throws ClassNotFoundException, SQLException {
		String sql="select * from client where clientid='"+selected+"'";		
		DBconnection db=new DBconnection();
		return (Client) db.execute(sql,"client").get(0);	
	}

	public void updateClient(Client c) throws ClassNotFoundException, SQLException {
		
		String sql= "update client set "
				+"AddressLine1='"+c.getAddress1()+"',"
				+"AddressLine2='"+c.getAddress2()+"',"
				+"city='"+c.getCity()+"',"
				+"state='"+c.getState()+"',"
				+"zip='"+c.getZip()+"',"
				+"email='"+c.getEmail()+"',"
				+"contact='"+c.getContact()+"',"
				+"invoicefrq='"+c.getInvoicefrq()+"',"
				+"billingterms='"+c.getBillingterms()+"',"
				+"invoicegrp='"+c.getInvoiceGroup()+"'"
				+"where name='"+c.getName()+"'";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
	}
	
	public void delete(String selected ) throws ClassNotFoundException, SQLException {
		
		String sql= "delete from client where clientId='"+selected+"'";
		DBconnection db=new DBconnection();
		db.insertUpdate(sql);
		
	}


}
