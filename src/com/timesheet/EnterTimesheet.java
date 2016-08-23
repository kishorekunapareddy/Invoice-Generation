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

import com.login.UserException;
import com.project.Project;
import com.project.ProjectEvent;
import com.project.employee.EmployeeProject;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EnterTimesheet {

	private String action="NEW";
	private JButton btnSave ;
	private JButton btnUpdate ;
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
	private JComboBox weekid; 
	private String role;

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterTimesheet window = new EnterTimesheet();
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
	public EnterTimesheet() throws ClassNotFoundException, SQLException {
		initialize();
	}
	
	public EnterTimesheet(int userId,String rl) throws ClassNotFoundException, SQLException {
		this.userId=userId;
		role=rl;
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
		
		JLabel lblEnterTimesheet = new JLabel("Enter Timesheet");
		lblEnterTimesheet.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnterTimesheet.setBounds(268, 11, 142, 14);
		panel.add(lblEnterTimesheet);
        frame.getContentPane().add(panel);
		
		w11 = new JTextField();
		w11.setBounds(248, 135, 39, 20);
		panel.add(w11);
		w11.setColumns(10);
		
		w12 = new JTextField();
		w12.setColumns(10);
		w12.setBounds(323, 135, 39, 20);
		panel.add(w12);
		
		w13 = new JTextField();
		w13.setColumns(10);
		w13.setBounds(397, 135, 39, 20);
		panel.add(w13);
		
		w14 = new JTextField();
		w14.setColumns(10);
		w14.setBounds(473, 135, 39, 20);
		panel.add(w14);
		
		w15 = new JTextField();
		w15.setColumns(10);
		w15.setBounds(542, 135, 39, 20);
		panel.add(w15);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					vaildate();
					getTimeSheet();					
				} catch (UserException | ParseException e) {
					if(e.getMessage().equals("Timesheet not found")){
						try {
							saveTimeSheet();
							dialog("TimeSheet submitted");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else
						dialog(e.getMessage());
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSave.setBounds(308, 214, 89, 23);
		panel.add(btnSave);
		
		JLabel lblSelectProject = new JLabel("Select project");
		lblSelectProject.setBounds(198, 49, 89, 14);
		panel.add(lblSelectProject);
		
		project = new JComboBox();
		project.setBounds(308, 46, 102, 20);
		panel.add(project);
		
		JLabel lblMonth = new JLabel("Month");
		lblMonth.setBounds(208, 74, 46, 14);
		panel.add(lblMonth);
		
		month = new JComboBox();
		month.setBounds(308, 77, 102, 20);
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
		
		weekid = new JComboBox();
		weekid.setBounds(119, 135, 89, 20);
		weekid.addItem("week1");
		weekid.addItem("week2");
		weekid.addItem("week3");
		weekid.addItem("week4");
		weekid.addItem("week5");
		panel.add(weekid);
		
		JLabel lblSelectWeek = new JLabel("Select Week");
		lblSelectWeek.setBounds(101, 110, 96, 14);
		panel.add(lblSelectWeek);
		
		getProject();
	}

	private void getProject() throws ClassNotFoundException, SQLException {
		TimeSheetEvent te=new TimeSheetEvent();
		ArrayList<EmployeeProject> em=new ArrayList<EmployeeProject>();
		em=te.getProject(userId);
		for(EmployeeProject e:em){
			ProjectEvent pe=new ProjectEvent();
			Project p=new Project();
			p=pe.getProject(Integer.toString(e.getProjectId()));
			project.addItem(p.getNumber()+" "+p.getName());			
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
		ts=te.getTimeSheet(userId,project.getSelectedItem().toString().split(" ")[0],month.getSelectedItem().toString(),weekid.getSelectedItem().toString());
		if(ts!=null&&action.equals("NEW"))
			throw new UserException("Timesheet already exists");
		else 
			return ts;
		}catch(IndexOutOfBoundsException e){
			throw new UserException("Timesheet not found");
		}
		
	}
	
	private void saveTimeSheet() throws ClassNotFoundException, SQLException{
		TimeSheet ts=new TimeSheet();
		ts.setEmpId(userId);
		ts.setProId(Integer.parseInt((project.getSelectedItem().toString().split(" ")[0])));
		ts.setMonth(month.getSelectedItem().toString());
		String week="";
		week=week+(w11.getText().equals("")||w11.getText().equals(null)?0:Integer.parseInt(w11.getText()));
		week=week+(w12.getText().equals("")||w12.getText().equals(null)?0:Integer.parseInt(w12.getText()));
		week=week+(w13.getText().equals("")||w13.getText().equals(null)?0:Integer.parseInt(w13.getText()));
		week=week+(w14.getText().equals("")||w14.getText().equals(null)?0:Integer.parseInt(w14.getText()));
		week=week+(w15.getText().equals("")||w15.getText().equals(null)?0:Integer.parseInt(w15.getText()));
		if(role.equalsIgnoreCase("Project Manager"))
			week=week+"A";
		else
			week=week+"S";
		
		ts.setWeek(week);
		ts.setWeekId(weekid.getSelectedItem().toString());
		
			
		TimeSheetEvent te=new TimeSheetEvent();
			te.addTimeSheet(ts);		
	}
	
	
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

	public void viewTimeSheet() {
		action="UPDATE";
		btnSave.setVisible(false);
		btnSave.updateUI();
		btnGetTimesheet = new JButton("Get TimeSheet");
		btnGetTimesheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					viewSelected();
				} catch (ClassNotFoundException | SQLException | UserException e) {
					dialog(e.getMessage());
					e.printStackTrace();
				}
			}
		});
		btnGetTimesheet.setBounds(460, 70, 142, 23);
		panel.add(btnGetTimesheet);		
		
	}
	
	public void viewSelected() throws ClassNotFoundException, SQLException, UserException{
		TimeSheet ts=new TimeSheet();
		ts=getTimeSheet();
		if(ts==null)
			throw new UserException("Timesheet not found");
		String status=ts.getWeek().split("")[5];
		if(status!="S"){
			btnUpdate = new JButton("Update");
			btnUpdate.setBounds(308, 248, 89, 23);
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						vaildate();
						saveTimeSheet();
						dialog("Timesheet updated sucessfuly");
					} catch (UserException | ParseException e) {
						dialog(e.getMessage());
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
			});
			panel.add(btnUpdate);
			btnUpdate.updateUI();
		}
		
		String[] week1=ts.getWeek().split("");
		w11.setText(week1[1]);
		w12.setText(week1[2]);
		w13.setText(week1[3]);
		w14.setText(week1[4]);
		w15.setText(week1[5]);
		
		}
}
