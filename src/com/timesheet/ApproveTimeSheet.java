package com.timesheet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import com.employee.Employee;
import com.employee.EmployeeEvent;
import com.login.UserException;
import com.project.Project;
import com.project.ProjectEvent;
import com.project.employee.EmployeeMapEvent;
import com.project.employee.EmployeeProject;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

public class ApproveTimeSheet {

	private String action="NEW";
	private JButton btnApprove ;
	private JFrame frame;
	private JTextField w11;
	private JTextField w12;
	private JTextField w13;
	private JTextField w14;
	private JTextField w15;
	private JComboBox project ;
	private JComboBox month;
	private int userId;
	public JPanel panel;
	private JButton btnGetTimesheet;
	private JLabel lblEmployee;
	private JButton btnReject;
	private JComboBox empId;
	private JButton btnGetTimeSheet;
	private JComboBox weekId;
	private JLabel lblWeek;


	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApproveTimeSheet window = new ApproveTimeSheet();
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
	public ApproveTimeSheet() throws ClassNotFoundException, SQLException {
		initialize();
	}
	public ApproveTimeSheet(int userName) throws ClassNotFoundException, SQLException {
		userId=userName;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 719, 482);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 683, 360);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblEnterTimesheet = new JLabel("Approve Timesheet");
		lblEnterTimesheet.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnterTimesheet.setBounds(268, 11, 142, 14);
		panel.add(lblEnterTimesheet);
        frame.getContentPane().add(panel);
		
		w11 = new JTextField();
		w11.setBounds(130, 119, 39, 20);
		panel.add(w11);
		w11.setColumns(10);
		
		w12 = new JTextField();
		w12.setColumns(10);
		w12.setBounds(178, 119, 39, 20);
		panel.add(w12);
		
		w13 = new JTextField();
		w13.setColumns(10);
		w13.setBounds(227, 119, 39, 20);
		panel.add(w13);
		
		w14 = new JTextField();
		w14.setColumns(10);
		w14.setBounds(276, 119, 39, 20);
		panel.add(w14);
		
		w15 = new JTextField();
		w15.setColumns(10);
		w15.setBounds(325, 119, 39, 20);
		panel.add(w15);
		
		btnApprove = new JButton("Approve");
		btnApprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
							
					try {
						vaildate();	
						saveTimeSheet("Approved");
						dialog("Timesheet has been approved");
					} catch (ClassNotFoundException | SQLException | UserException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		btnApprove.setBounds(245, 226, 89, 23);
		panel.add(btnApprove);
		
		JLabel lblSelectProject = new JLabel("Select project");
		lblSelectProject.setBounds(80, 39, 89, 14);
		panel.add(lblSelectProject);
		
		project = new JComboBox();
		project.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getEmployee();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		project.setBounds(191, 36, 102, 20);
		panel.add(project);
		
		month = new JComboBox();
		month.setBounds(325, 36, 102, 20);
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
		
		
		lblEmployee = new JLabel("Employee");
		lblEmployee.setBounds(451, 39, 65, 14);
		panel.add(lblEmployee);
		
		btnReject = new JButton("Reject");
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				
				try {
					vaildate();	
					saveTimeSheet("Rejected");
					dialog("Timesheet has been rejected");
				} catch (ClassNotFoundException | SQLException | UserException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	});
		btnReject.setBounds(420, 226, 89, 23);
		panel.add(btnReject);
		
		empId = new JComboBox();
		empId.setBounds(527, 36, 114, 20);
		panel.add(empId);
		
		btnGetTimeSheet = new JButton("Get Time Sheet");
		btnGetTimeSheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					viewSelected();
				} catch (ClassNotFoundException | SQLException  e1) {
					e1.printStackTrace();
				} catch (UserException e1) {
					dialog("Timesheet not found");
					e1.printStackTrace();
				}
			}
		});
		btnGetTimeSheet.setBounds(312, 69, 115, 23);
		panel.add(btnGetTimeSheet);
		
		weekId = new JComboBox();
		weekId.setBounds(191, 70, 102, 20);
		weekId.addItem("week1");
		weekId.addItem("week2");
		weekId.addItem("week3");
		weekId.addItem("week4");
		weekId.addItem("week5");
		panel.add(weekId);
		
		lblWeek = new JLabel("Week");
		lblWeek.setBounds(90, 64, 46, 14);
		panel.add(lblWeek);
		
		getProject();
	}

	private void getProject() throws ClassNotFoundException, SQLException {
		ProjectEvent te=new ProjectEvent();
		ArrayList<Project> em=new ArrayList<Project>();
		em=te.getAllProjectByManager(Integer.toString(userId));
		for(Project p:em){
			project.addItem(p.getNumber()+" "+p.getName());			
		}
		
	}
	
	private void getEmployee() throws ClassNotFoundException, SQLException {
		ArrayList<EmployeeProject> ep=new ArrayList<EmployeeProject>();
		EmployeeMapEvent eme=new EmployeeMapEvent();
		ep=eme.getMap (Integer.parseInt(project.getSelectedItem().toString().split(" ")[0]));
		for(EmployeeProject e:ep){
			Employee e1=new Employee();
			EmployeeEvent ee=new EmployeeEvent();
			e1=ee.getEmployee(Integer.toString(e.getEmpId()));
			empId.addItem(e1.getEmpid()+" "+e1.getName());
		}
		
		
	}
	
	private void vaildate() throws UserException, ParseException{
		boolean status=false;
		if(Pattern.matches("[a-zA-Z]+", w11.getText()) == true)
			status=true;
		if(Pattern.matches("[a-zA-Z]+", w12.getText()) == true)
			status=true;
		if(Pattern.matches("[a-zA-Z]+", w13.getText()) == true)
			status=true;
		if(Pattern.matches("[a-zA-Z]+", w14.getText()) == true)
			status=true;
		if(Pattern.matches("[a-zA-Z]+", w15.getText()) == true)
			status=true;
			if(status)
			throw new UserException("Hours should be numeric");
		
			
	}
	
	private TimeSheet getTimeSheet() throws ClassNotFoundException, SQLException, UserException{
		TimeSheetEvent te=new TimeSheetEvent();		
		TimeSheet ts=new TimeSheet();
		try{		
		ts=te.getTimeSheet(Integer.parseInt(empId.getSelectedItem().toString().split(" ")[0]),project.getSelectedItem().toString().split(" ")[0],month.getSelectedItem().toString(),weekId.getSelectedItem().toString());
		return ts;
		}catch(IndexOutOfBoundsException e){
			throw new UserException("Timesheet not found");
		}
		
	}
	
	private void saveTimeSheet(String status) throws ClassNotFoundException, SQLException{
		TimeSheet ts=new TimeSheet();
		ts.setEmpId(Integer.parseInt(empId.getSelectedItem().toString().split(" ")[0]));
		ts.setProId(Integer.parseInt((project.getSelectedItem().toString().split(" ")[0])));
		ts.setMonth(month.getSelectedItem().toString());
		String week1="";
		week1=week1+(w11.getText().equals("")||w11.getText().equals(null)?0:Integer.parseInt(w11.getText()));
		week1=week1+(w12.getText().equals("")||w12.getText().equals(null)?0:Integer.parseInt(w12.getText()));
		week1=week1+(w13.getText().equals("")||w13.getText().equals(null)?0:Integer.parseInt(w13.getText()));
		week1=week1+(w14.getText().equals("")||w14.getText().equals(null)?0:Integer.parseInt(w14.getText()));
		week1=week1+(w15.getText().equals("")||w15.getText().equals(null)?0:Integer.parseInt(w15.getText()));
		ts.setWeekId(weekId.getSelectedItem().toString());
		if(status.equals("Approved"))
			week1=week1+"A";
		else
			week1=week1+"R";
		ts.setWeek(week1);
		
		TimeSheetEvent te=new TimeSheetEvent();
			te.addTimeSheet(ts);
		
		
	}
	
	
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
	
	public void viewSelected() throws ClassNotFoundException, SQLException, UserException{
		TimeSheet ts=new TimeSheet();
		ts=getTimeSheet();
		if(ts==null)
			throw new UserException("Timesheet not found");
		

		String[] week1=ts.getWeek().split("");
		w11.setText(week1[1]);
		w12.setText(week1[2]);
		w13.setText(week1[3]);
		w14.setText(week1[4]);
		w15.setText(week1[5]);
		

		
	}

}
