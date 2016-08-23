package com.invoice;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 






import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;

import com.employee.Employee;
import com.employee.EmployeeEvent;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.Project;
import com.project.ProjectEvent;

public class GeneratePDF {

	public void generate(Invoice i) throws ClassNotFoundException, SQLException {
		Document document = new Document();
	      try
	      {
	    	  
	    	EmployeeEvent ee=new EmployeeEvent();
	  		Employee e=new Employee();
	  		e=ee.getEmployee(Integer.toString(i.getEmpId()));
	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(e.getName()+"_"+i.getMonth()+".pdf"));
	         document.open();         
	         document.add(new Paragraph("Invoice Report"));
	         document.add(new Paragraph(" "));
	         document.add(new Paragraph(" "));
	         document.add(new Paragraph("Name:"+e.getName()+"              "+"Role:"+e.getRole()));	  
	         document.add(new Paragraph("Month:"+i.getMonth()));	
	         document.add(new Paragraph("Billrate:"+e.getBillrate()));	
	         
	         document.add(new Paragraph(" "));
	         document.add(new Paragraph(" "));
	         document.add(new Paragraph("Project(Name,Total Hour,Status)"));
	         
	         
	     	ArrayList<String> project=new ArrayList<String>();
			project=i.getProject();
			for(String s:project){
				if(null!=s){
					ProjectEvent p=new ProjectEvent();
					Project pr=new Project();
					pr=p.getProject(s.split(",")[0]);
					document.add(new Paragraph(pr.getName()+"  "+s.split(",")[1]+"  "+s.split(",")[3]));
				}
			}
			document.add(new Paragraph(" "));
	        document.add(new Paragraph(" "));
	        
	        document.add(new Paragraph("Total Amount:"+i.getTotalAmount()));
	         
	         document.close();
	         writer.close();
	      } catch (DocumentException e)
	      {
	         e.printStackTrace();
	      } catch (FileNotFoundException e)
	      {
	         e.printStackTrace();
	      }
	   }
}
