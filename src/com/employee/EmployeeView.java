package com.employee;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import com.login.UserException;

public class EmployeeView {

	private JFrame frame;
	public JPanel panel ;
	private JTextField title;
	private JTextField name;
	private JTextField billrate;
	private JComboBox role;
	public String action;

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeView window = new EmployeeView();
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
	public EmployeeView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 726, 501);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 676, 400);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblEmployeeManagement = new JLabel("Employee Management");
		lblEmployeeManagement.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmployeeManagement.setBounds(291, 11, 167, 14);
		panel.add(lblEmployeeManagement);
		
		JLabel lblTitle = new JLabel("Title ");
		lblTitle.setBounds(182, 48, 46, 14);
		panel.add(lblTitle);
		
		title = new JTextField();
		title.setBounds(280, 45, 179, 20);
		panel.add(title);
		title.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(182, 85, 46, 14);
		panel.add(lblName);
		
		name = new JTextField();
		name.setBounds(280, 82, 178, 20);
		panel.add(name);
		name.setColumns(10);
		
		JLabel lblBillRate = new JLabel("Bill Rate");
		lblBillRate.setBounds(182, 129, 46, 14);
		panel.add(lblBillRate);
		
		billrate = new JTextField();
		billrate.setBounds(280, 126, 86, 20);
		panel.add(billrate);
		billrate.setColumns(10);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setBounds(182, 172, 46, 14);
		panel.add(lblRole);
		
		role = new JComboBox();
		role.setBounds(280, 169, 178, 20);
		role.addItem("Developer");
		role.addItem("Project Manager");
		panel.add(role);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(action=="ADD"){
					try {
						addEmployee();
						dialog("Employee Added sucessfully");
						reload();
					}catch (ClassNotFoundException | SQLException e1) {
						dialog("User already exists");
						e1.printStackTrace();
					} catch (UserException e1) {
						dialog(e1.getMessage());
						e1.printStackTrace();
					}
				}else if(action=="UPDATE"){
					try {
						updateEmployee();
						dialog("Employee updated sucessfully");
					} catch (ClassNotFoundException | SQLException e1) {
						dialog("User failed to update");
						e1.printStackTrace();
					}catch (UserException e1) {
						dialog(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
		btnSave.setBounds(233, 214, 89, 23);
		panel.add(btnSave);
	}
	
	public void addEmployee() throws ClassNotFoundException, SQLException, NumberFormatException, UserException{
		if(validate()){
		Employee e=new Employee();
		e.setName(name.getText());
		e.setTitle(title.getText());
		e.setBillrate(Integer.parseInt(billrate.getText()));
		e.setRole(role.getSelectedItem().toString());
		EmployeeEvent ee =new EmployeeEvent();
		ee.addEmployee(e);
		}
		
	}

	private boolean validate() throws UserException {
		if(name.getText().equals("")||name.getText().equals(null))
			throw new UserException("Please enter name");
		if(title.getText().equals("")||title.getText().equals(null))
			throw new UserException("Please enter title");
		if(billrate.getText().equals("")||billrate.getText().equals(null))
			throw new UserException("Please enter Bill rate");
		if(Pattern.matches("[a-zA-Z]+", billrate.getText()) == true)
			throw new UserException("Billrate should be numaric ");	
		return true;
	}
	
	public void updateEmployee() throws ClassNotFoundException, SQLException, NumberFormatException, UserException{
		if(validate()){
		Employee e=new Employee();
		e.setName(name.getText());
		e.setTitle(title.getText());
		e.setBillrate(Integer.parseInt(billrate.getText()));
		e.setRole(role.getSelectedItem().toString());
		EmployeeEvent ee =new EmployeeEvent();
		ee.updateEmployee(e);
		}
		
	}

	public void viewSelected( String selected) throws ClassNotFoundException, SQLException {
		Employee e=new Employee();
		EmployeeEvent ee =new EmployeeEvent();
		e=ee.getEmployee(selected);
		name.setText(e.getName());
		name.setEditable(false);
		title.setText(e.getTitle());
		billrate.setText(Integer.toString(e.getBillrate()));
		role.setSelectedItem(e.getRole());		
		
	}
	public void reload(){
		name.setText("");
		title.setText("");
		billrate.setText("");
	}
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
}
