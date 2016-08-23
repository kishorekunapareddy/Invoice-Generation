package com.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.client.Client;
import com.client.ClientEvent;
import com.invoice.Invoice;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.Project;
import com.project.ProjectEvent;
import com.project.employee.EmployeeMapEvent;
import com.project.employee.EmployeeProject;
import com.timesheet.TimeSheet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ProjectReport {

	private JFrame frame;
	public JPanel panel; 
	private ArrayList<Project> proList;
	private ArrayList<Invoice> inList;
	private ArrayList<TimeSheet> tsList;
	private JTable table; 
	private JScrollPane scrollPane;
	private JButton btnGetReport;
	private JButton btnGeneratePdf;
	private JButton btnEmailClient;
	private JComboBox<String> month;
	private JLabel lblNewLabel;
	

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProjectReport window = new ProjectReport();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProjectReport() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 724, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 670, 421);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblProjectReport = new JLabel("Project Report");
		lblProjectReport.setBounds(284, 23, 118, 14);
		panel.add(lblProjectReport);
		
		btnGetReport = new JButton("Get Report");
		btnGetReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					viewReport();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGetReport.setBounds(203, 48, 136, 23);
		panel.add(btnGetReport);
		
		btnGeneratePdf = new JButton("Generate PDF");
		btnGeneratePdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					generatePDF();
				} catch (ClassNotFoundException | FileNotFoundException
						| SQLException | DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGeneratePdf.setBounds(349, 48, 142, 23);
		panel.add(btnGeneratePdf);
		
		btnEmailClient = new JButton("Email client");
		btnEmailClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sendEmail();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEmailClient.setBounds(501, 48, 123, 23);
		panel.add(btnEmailClient);
		
		month = new JComboBox<String>();
		month.addItem("Jan");
		month.addItem("Feb");
		month.addItem("Mar");
		month.addItem("Apr");
		month.addItem("May");
		month.addItem("Jun");
		month.addItem("Jul");
		month.addItem("Aug");
		month.addItem("Sep");
		month.addItem("Oct");
		month.addItem("Nov");
		month.addItem("Dec");
		month.setBounds(107, 49, 77, 20);
		panel.add(month);
		
		/*lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(28, 94, 617, 304);
		panel.add(lblNewLabel);*/
	}
	
	private String[][] getData() throws ClassNotFoundException, SQLException{
		ProjectEvent pro=new ProjectEvent();
		proList=new ArrayList<Project>();
		proList=pro.getAllProject();
		ReportEvent re=new ReportEvent();
		inList=new ArrayList<Invoice>();
		inList=re.EmployeeReport(month.getSelectedItem().toString());
		String[][] data=new String[50][9];
		int j=0;
		for(Project p:proList){
			data[j][0]=Integer.toString(p.getNumber());
			data[j][1]=p.getName();
			data[j][2]=Integer.toString(p.getClient());
			data[j][3]=Integer.toString(p.getBudget());
			
			EmployeeMapEvent emp=new EmployeeMapEvent();
			ArrayList<EmployeeProject> ep=new ArrayList<EmployeeProject>();
			ep=emp.getMap(p.getNumber());
			data[j][4]=Integer.toString(ep.size());			
			
			double hours=0;
			double amt=0;
			for(Invoice i:inList){
				ArrayList<String> ip=new ArrayList<String>();
				ip=i.getProject();
				for(int k=0;k<5;k++){
					if(null!=ip.get(k)) {
						int projectid=Integer.parseInt(ip.get(k).split(",")[0]);
						if(projectid==p.getNumber()){
							amt=amt+Integer.parseInt(ip.get(k).split(",")[2]);
							hours=hours+Integer.parseInt(ip.get(k).split(",")[1]);
						}
					}
				}

			}
			data[j][5]=Double.toString(hours);
			data[j][6]=month.getSelectedItem().toString();
			data[j][7]=Double.toString(amt);
			data[j][8]=Double.toString(p.getBudget()-amt);
			j++;
		}
		
		return data;
	}
	
	private void viewReport() throws ClassNotFoundException, SQLException{
		String proReport[][]=null;
		proReport=getData();
		table = new JTable();
		table.setModel(new DefaultTableModel(
				proReport,			
			new String[] {
				"Project Id", "Name","Client","Estimated Budget","Total Assigned Employee","Total Hours","Month", "BugetUsed","Remaining"
			}
		));
		
		scrollPane = new JScrollPane( table );
		scrollPane.setBounds(28, 94, 617, 304);
		panel.add(scrollPane);
	}
	
	public void generatePDF() throws ClassNotFoundException, SQLException, FileNotFoundException, DocumentException{
		String data[][]=getData();
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Project_"+month.getSelectedItem().toString()+"_Report.pdf"));
        document.open();         
        document.add(new Paragraph(month.getSelectedItem().toString()+" Invoice Report"));
        document.add(new Paragraph("ProjectId Name Client EstimatedBudget TotalAssignedEmployee TotalHours Month BudgetUsed Remaining"));
        for(int i=0;i<data.length;i++){
        	if(null!=data[i][0]){
            	String row=data[i][0]+" "+data[i][1]+" "+data[i][2]+" "+data[i][3]+" "+data[i][4]+" "+data[i][5]+" "+data[i][6]+" "+data[i][7]+" "+data[i][8];
            	document.add(new Paragraph(row));
        	}
        }
        document.close();
        writer.close();
        dialog("PDF has been generated");
	}
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
	
	public void sendEmail() throws ClassNotFoundException, SQLException{
		String data[][]=getData();
		for(int i=0;i<data.length;i++){
			if(null!=data[i][0]){
			ProjectEvent pe=new ProjectEvent();
			Project p=new Project();
			p=pe.getProject(data[i][0]);
			ClientEvent ce=new ClientEvent();
			Client c=new Client();
			c=ce.getClient(Integer.toString(p.getClient()));
			  String to = c.getEmail();
		      String from = "invoice@company.com";
		      String host = "localhost";
		      Properties properties = System.getProperties();
		      properties.setProperty("mail.smtp.host", host);
		      Session session = Session.getDefaultInstance(properties);

		      try{
		         MimeMessage message = new MimeMessage(session);
		         message.setFrom(new InternetAddress(from));
		         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		         message.setSubject("Invoice report of "+p.getName()+" for "+month.getSelectedItem().toString()+" month");
		         String msg="";
		         msg="<h1>Invoice report of "+p.getName()+" for "+month.getSelectedItem().toString()+" month </h1><br><br>";
		         msg=msg+"<h2>Project Name:</h2>"+data[i][1]+"<br>";
		         msg=msg+"<h2>Estimated Budget:</h2>"+data[i][3]+"<br>";
		         msg=msg+"<h2>Total Assigned employee:</h2>"+data[i][4]+"<br>";
		         msg=msg+"<h2>Hours worked:</h2>"+data[i][5]+"<br>";
		         msg=msg+"<h2>Budget used:</h2>"+data[i][7]+"<br>";
		         msg=msg+"<h2>Budget Remaining:</h2>"+data[i][8]+"<br>";
		         message.setContent(msg, "text/html" );
		         Transport.send(message);
		      }catch (MessagingException mex) {
		    	  dialog("Email failed to send ");
		         mex.printStackTrace();
		      }
			}	
		}
		dialog("Email send sucessfully");
	}
}
