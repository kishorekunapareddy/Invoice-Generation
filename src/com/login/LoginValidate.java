package com.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginValidate {

	public String validate(String username, String password) throws ClassNotFoundException, SQLException {
		String role="";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice","root","root");      
        Statement st = con.createStatement();    
        ResultSet rs = st.executeQuery("Select role from users where username="+"'"+username+"'"+" and password="+"'"+password+"'");
       while (rs.next()) 
    	    role = rs.getString(1);
      st.close();
      con.close();
      return role;
	}

}
