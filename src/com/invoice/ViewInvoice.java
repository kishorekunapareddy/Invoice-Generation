package com.invoice;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import com.employee.Employee;
import com.employee.EmployeeEvent;
import com.project.Project;
import com.project.ProjectEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ViewInvoice {

	private JFrame frame;
	private JComboBox empId ;
	private JComboBox<String> month;
	public JPanel panel;
	private JLabel lblName;
	private JLabel lblNametx;
	private JLabel lblRole;
	private JLabel lblRletx;
	private JLabel lblBillRate;
	private JLabel lblBillre;
	private JLabel lblProject;
	private JLabel prnam;
	private JLabel lblNewLabel;
	private JLabel lblTotalAmount;
	private JLabel lblTotalAmount_1;
	private JLabel lblMonth;
	private JLabel lblMonthtx;
	private JLabel lblSta;
	private JTable table;
	private int count=0;
	private Invoice inc=new Invoice();
	private JScrollPane scrollPane ;

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewInvoice window = new ViewInvoice();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ViewInvoice() throws ClassNotFoundException, SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 718, 488);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 671, 349);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblViewInvoice = new JLabel("View Invoice");
		lblViewInvoice.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblViewInvoice.setBounds(278, 11, 126, 14);
		panel.add(lblViewInvoice);
		
		JLabel lblEmployee = new JLabel("Employee");
		lblEmployee.setBounds(105, 47, 62, 14);
		panel.add(lblEmployee);
		
		empId = new JComboBox();
		empId.setBounds(196, 44, 101, 20);
		panel.add(empId);
		getEmployee();
		
		month = new JComboBox<String>();
		month.setBounds(357, 44, 62, 20);
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
		
		panel.add(month);
		
		JButton btnGetInvoice = new JButton("Get Invoice");
		btnGetInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					getInvoice();
				} catch (NumberFormatException | ClassNotFoundException
						| SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(IndexOutOfBoundsException e){
					dialog("No Invoice found");
				}
			}
		});
		btnGetInvoice.setBounds(456, 43, 134, 23);
		
	
		
		panel.add(btnGetInvoice);
		
		
		
		
		
		}
	
	private void getEmployee() throws ClassNotFoundException, SQLException {		
		ArrayList<Employee> cl=new ArrayList<Employee>();
		EmployeeEvent ce=new EmployeeEvent();
		cl=ce.getAllEmployee();
		for(Employee c:cl){
				empId.addItem(c.getEmpid()+" "+c.getName());
		}
	}
	
	private void getInvoice() throws NumberFormatException, ClassNotFoundException, SQLException{
		InvoiceEvent ie=new InvoiceEvent();
		Invoice i=new Invoice();
		i=ie.getInvoice(Integer.parseInt(empId.getSelectedItem().toString().split(" ")[0]),month.getSelectedItem().toString());
		view(i);
		
	}
	
	@SuppressWarnings("null")
	private void view(Invoice i) throws ClassNotFoundException, SQLException{
		
		inc=i;
		JButton btnGeneratePdf = new JButton("Generate PDF");
		btnGeneratePdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generatePDF(inc);
			}
		});
		btnGeneratePdf.setBounds(105, 315, 126, 23);
		panel.add(btnGeneratePdf);
		
		JButton btnNewButton = new JButton("Send Mail");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sendMail(inc);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(472, 315, 134, 23);
		//panel.add(btnNewButton);
		
		
		EmployeeEvent ee=new EmployeeEvent();
		Employee e=new Employee();
		e=ee.getEmployee(Integer.toString(i.getEmpId()));
		
		if(count>0){
			lblNametx.setVisible(false);
			lblRletx.setVisible(false);
			lblMonthtx.setVisible(false);
			lblBillre.setVisible(false);
			table.setVisible(false);
			scrollPane.setVisible(false);
			lblTotalAmount_1.setVisible(false);
		}
		lblName = new JLabel("Name:");
		lblName.setBounds(135, 95, 46, 14);
		panel.add(lblName);
		
		lblNametx = new JLabel(e.getName());
		lblNametx.setBounds(224, 95, 137, 14);
		panel.add(lblNametx);
		
		lblRole = new JLabel("Role: ");
		lblRole.setBounds(390, 95, 46, 14);
		panel.add(lblRole);
		
		lblRletx = new JLabel(e.getRole());
		lblRletx.setBounds(455, 95, 137, 14);
		panel.add(lblRletx);
		
		lblMonth = new JLabel("Month:");
		lblMonth.setBounds(135, 122, 46, 14);
		panel.add(lblMonth);
		
		lblMonthtx = new JLabel(i.getMonth());
		lblMonthtx.setBounds(224, 122, 46, 14);
		panel.add(lblMonthtx);
		
		lblBillRate = new JLabel("Bill Rate:");
		lblBillRate.setBounds(135, 147, 76, 14);
		panel.add(lblBillRate);
		
		lblBillre = new JLabel(Integer.toString(e.getBillrate()));
		lblBillre.setBounds(224, 147, 46, 14);
		panel.add(lblBillre);
		
		lblProject = new JLabel("Project");
		lblProject.setBounds(68, 172, 186, 14);
		panel.add(lblProject);
		
		ArrayList<String> project=new ArrayList<String>();
		project=i.getProject();
		String proReport[][]=new String[5][3];
		int k=0;
		for(String s:project){
			if(null!=s){
				proReport[k][0]=s.split(",")[0];
				proReport[k][1]=s.split(",")[1];
				proReport[k][2]=s.split(",")[3];
				k++;			
			}
		}
		table = new JTable();
		table.setModel(new DefaultTableModel(
				proReport,			
			new String[] {
				"ID","Total Hours","Status"
			}
		));
		//table.setBounds(80, 196, 506, 103);
		//panel.add(table);
		scrollPane = new JScrollPane( table );
		scrollPane.setBounds(80, 196, 442, 68);
		panel.add(scrollPane);

		
		lblTotalAmount = new JLabel("Total Amount:");
		lblTotalAmount.setBounds(135, 276, 81, 14);
		panel.add(lblTotalAmount);
		
		lblTotalAmount_1 = new JLabel(Double.toString(i.getTotalAmount())+"$");
		lblTotalAmount_1.setBounds(224, 276, 101, 14);
		panel.add(lblTotalAmount_1);
		
		
		lblNametx.setVisible(true);
		lblRletx.setVisible(true);
		lblMonthtx.setVisible(true);
		lblBillre.setVisible(true);
		table.setVisible(true);
		scrollPane.setVisible(true);
		scrollPane.updateUI();
		panel.updateUI();
		
		count++;
	}
	
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
	
	private void generatePDF(Invoice i){
		GeneratePDF gp=new GeneratePDF();
		try {
			gp.generate(i);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialog("PDF has been generated");
	}
	
	
	private void sendMail(Invoice i) throws ClassNotFoundException, SQLException{
		
		EmployeeEvent ee=new EmployeeEvent();
		Employee e=new Employee();
		e=ee.getEmployee(Integer.toString(i.getEmpId()));		
	      String to = e.getName();
	      String from = "web@gmail.com";
	      String host = "localhost";
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         message.setSubject("This is the Subject Line!");
	         message.setContent("<h1>This is actual message</h1>", "text/html" );
	         Transport.send(message);
	         dialog("Email send sucessfully");
	      }catch (MessagingException mex) {
	    	  dialog("Email failed to send ");
	         mex.printStackTrace();
	      }
		
	}
}
