package com.main;

import javax.swing.JFrame;
import javax.swing.JButton;

import com.client.ClientManagement;
import com.company.Company;
import com.company.CompanyEvent;
import com.company.CompanyView;
import com.employee.Employee;
import com.employee.EmployeeEvent;
import com.employee.EmployeeManagement;
import com.invoice.GenerateInvoice;
import com.invoice.ViewInvoice;
import com.login.Login;
import com.project.ProjectManagement;
import com.project.employee.EmployeeMap;
import com.report.ClientReport;
import com.report.EmployeeReport;
import com.report.ProjectReport;
import com.timesheet.ApproveTimeSheet;
import com.timesheet.EnterTimesheet;
import com.user.UserEvent;
import com.user.UserManagement;
import com.user.UserView;
import com.user.Users;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

public class UserMain {

	public JFrame frame;
	public JPanel panel;
	public String role;
	public String userName;
	public String userId;
	public Employee emp;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMain window = new UserMain();
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
	public UserMain() throws ClassNotFoundException, SQLException {
		initialize();
	}

	public UserMain(String role, String userName) throws ClassNotFoundException, SQLException {
		this.role=role;
		this.userName=userName;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 721, 488);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		getEmpDetail();
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login l=new Login();
				l.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnLogout.setBounds(582, 11, 89, 23);
		frame.getContentPane().add(btnLogout);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(10, 13, 484, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnManagement = new JMenu("Management");
		mnManagement.setBounds(0, 11, 107, 22);
		frame.getContentPane().add(mnManagement);
		
		if(role.equalsIgnoreCase("Accountant")){
			
		JMenuItem mntmUser = new JMenuItem("User");
		mntmUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					userEvent();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnManagement.add(mntmUser);
		
		JMenuItem mntmClient = new JMenuItem("Client");
		mntmClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clientEvent();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnManagement.add(mntmClient);
		
		JMenuItem mntmEmployee = new JMenuItem("Employee");
		mntmEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					employeeEvent();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnManagement.add(mntmEmployee);
		
		
		JMenuItem mntmProject = new JMenuItem("Project");
		mntmProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					projectEvent();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnManagement.add(mntmProject);
		
		JMenuItem mntmCompany = new JMenuItem("Update Company");
		mntmCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					companyEvent();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnManagement.add(mntmCompany);
		
		}else{
			JMenuItem mntmCompany = new JMenuItem("Update My Account");
			mntmCompany.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						userUpdateEvent();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mnManagement.add(mntmCompany);
		}
		
		JMenu mnAssignEmployee = new JMenu("Assign Employee");
		mnManagement.setBounds(100, 100, 107, 102);
		frame.getContentPane().add(mnManagement);
		
		
		if(role.equalsIgnoreCase("Project Manager")){
		JMenuItem mntmProAssign = new JMenuItem("Assign Employee to Project");
		mntmProAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					proEmpEvent();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnAssignEmployee.add(mntmProAssign);
		}
		
		
		panel = new JPanel();
		panel.setBounds(10, 33, 685, 416);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JMenu mnTimeSheet = new JMenu("Time sheet");
		mnTimeSheet.setBounds(231, 11, 107, 22);
		frame.getContentPane().add(mnTimeSheet);
		if(role.equals("Developer")||role.equals("Project Manager")){
			JMenuItem mnEnterTimeSheet = new JMenuItem("Enter TimeSheet");
			mnEnterTimeSheet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						enterTimeSheet();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mnTimeSheet.add(mnEnterTimeSheet);
			
		}
		if(role.equals("Developer")||role.equals("Project Manager")){
			JMenuItem mnupdateTimeSheet = new JMenuItem("View/Update TimeSheet");
			mnupdateTimeSheet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						viewTimeSheet();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mnTimeSheet.add(mnupdateTimeSheet);
			
		}
		if(role.equals("Project Manager")){
			JMenuItem mnupdateTimeSheet = new JMenuItem("Approve TimeSheet");
			mnupdateTimeSheet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						approveTimeSheet();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mnTimeSheet.add(mnupdateTimeSheet);
			
		}
		
		JMenu mnInvoice = new JMenu("Invoice");
		mnInvoice.setBounds(300, 11, 107, 22);
		frame.getContentPane().add(mnInvoice);
		
		
		JMenu mnReport = new JMenu("Report");
		mnReport.setBounds(386, 12, 107, 22);
		frame.getContentPane().add(mnReport);
		
		

		if(role.equals("Accountant")){
			JMenuItem mngenerateInvoice = new JMenuItem("Generate Invoice");
			mngenerateInvoice.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						generateInvoice();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mnInvoice.add(mngenerateInvoice);
			
			JMenuItem mnviewInvoice = new JMenuItem("View Invoice");
			mnviewInvoice.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						viewInvoice();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mnInvoice.add(mnviewInvoice);
			
			
			
			
			
			JMenuItem viewEmployee = new JMenuItem("Employee Invoice");
			viewEmployee.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						empReport();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mnReport.add(viewEmployee);
			
			JMenuItem proReport = new JMenuItem("Project Report");
			proReport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						proReport();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			mnReport.add(proReport);
			
		}
		
		
		
		viewComany();

		menuBar.add(mnManagement);
		menuBar.add(mnAssignEmployee);
		menuBar.add(mnTimeSheet);
		menuBar.add(mnInvoice);
		menuBar.add(mnReport);
	}
	
	private void getEmpDetail() throws NumberFormatException, ClassNotFoundException, SQLException {
		if(!role.equals("Accountant")){
			UserEvent ue=new UserEvent();
			Users u=new Users();
			u=ue.getUser(userName);
			userId=u.getUserId();
			emp=new Employee();
			EmployeeEvent ee=new EmployeeEvent();
			emp=ee.getEmployee(u.getUserId());
			
		}
		
	}

	public void viewComany() throws ClassNotFoundException, SQLException {
		Company c=new Company();
		CompanyEvent ce=new CompanyEvent();
		c=ce.getCompany("Eagle Consluting");
		JLabel name = new JLabel("Eagle Consluting");
		name.setFont(new Font("Tahoma", Font.BOLD, 12));
		name.setBounds(241, 103, 210, 14);
		panel.add(name);
		
		JLabel lblAddress = new JLabel(c.getAddress1());
		lblAddress.setBounds(241, 132, 146, 14);
		panel.add(lblAddress);
		
		JLabel lblAddress_1 = new JLabel(c.getAddress2());
		lblAddress_1.setBounds(241, 157, 146, 14);
		panel.add(lblAddress_1);
		
		JLabel lblCity = new JLabel(c.getCity()+","+c.getState()+","+c.getZip());
		lblCity.setBounds(241, 182, 221, 14);
		panel.add(lblCity);
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblWelcome.setBounds(250, 34, 232, 14);
		panel.add(lblWelcome);
		
	}

	public void userEvent() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		UserManagement um=new UserManagement();
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void clientEvent() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		ClientManagement um=new ClientManagement();
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void employeeEvent() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		EmployeeManagement um=new EmployeeManagement();
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void companyEvent() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		CompanyView um=new CompanyView();
		um.viewSelected("Eagle Consluting");
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void projectEvent() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		ProjectManagement um=new ProjectManagement();
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void proEmpEvent() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		EmployeeMap um=new EmployeeMap(userId);
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void enterTimeSheet() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		EnterTimesheet um=new EnterTimesheet(Integer.parseInt(userId),role);
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void viewTimeSheet() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		EnterTimesheet um=new EnterTimesheet(Integer.parseInt(userId),role);
		um.viewTimeSheet();
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void approveTimeSheet() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		ApproveTimeSheet um=new ApproveTimeSheet(Integer.parseInt(userId));
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void userUpdateEvent() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		UserView um=new UserView();
		um.viewSelected(userName);;
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void generateInvoice() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		GenerateInvoice um=new GenerateInvoice();
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void viewInvoice() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		ViewInvoice um=new ViewInvoice();
		panel.add(um.panel);
		panel.updateUI();
	}
	
	public void empReport() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		EmployeeReport um=new EmployeeReport();
		panel.add(um.panel);
		panel.updateUI();
	}
	
	
	public void proReport() throws ClassNotFoundException, SQLException{
		panel.removeAll();
		ProjectReport um=new ProjectReport();
		panel.add(um.panel);
		panel.updateUI();
	}
}
