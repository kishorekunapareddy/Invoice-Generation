package com.user;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import com.login.UserException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class UserView {

	private JFrame frame;
	public JPanel panel;
	private JTextField userName;
	private JPasswordField password;
	//private JComboBox role;
	public String action;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserView window = new UserView();
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
	public UserView() {
		initialize();
	}

	public UserView(int userId) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 721, 491);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(31, 21, 636, 341);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUserManagement = new JLabel("User Management");
		lblUserManagement.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUserManagement.setBounds(214, 11, 222, 14);
		panel.add(lblUserManagement);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsername.setBounds(114, 81, 112, 14);
		panel.add(lblUsername);
		
		userName = new JTextField();
		userName.setBounds(236, 79, 168, 20);
		panel.add(userName);
		userName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(114, 135, 84, 14);
		panel.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(236, 133, 168, 20);
		panel.add(password);
		password.setColumns(10);
		
		/*role = new JTextField();
		role.setBounds(236, 183, 112, 20);
		role.addItem("Accountant");
		role.addItem("Developer");
		role.addItem("Sr.Developer");
		role.addItem("Manager");
		role.setEditable(false);
		panel.add(role);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRole.setBounds(114, 186, 46, 14);
		panel.add(lblRole);*/
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(action=="ADD"){
					try {
						addUser();
						dialog("User Added sucessfully");
						reload();
					}catch (ClassNotFoundException | SQLException e) {
						dialog("User already exists");
						e.printStackTrace();
					} catch (UserException e) {
						dialog(e.getMessage());
						e.printStackTrace();
					}
				}else if(action=="UPDATE"){
					try {
						updateUser();
						dialog("User updated sucessfully");
					} catch (ClassNotFoundException | SQLException e) {
						dialog("User failed to update");
						e.printStackTrace();
					}catch (UserException e) {
						dialog(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		});
		btnSave.setBounds(202, 249, 89, 23);
		panel.add(btnSave);
	}
	
	public void addUser() throws ClassNotFoundException, SQLException, UserException{
		Users u=new Users();
		u.setUserName(userName.getText());
		u.setPassword(password.getText());
		//u.setRole(role.getSelectedItem().toString());
		UserEvent ue=new UserEvent();		
		if(validate()){
		ue.addUser(u);
		}
		
	}

	private boolean validate() throws UserException {
		if(userName.getText().equals("")||userName.getText().equals(null)){
			throw new UserException("Please Enter username");
		}
		else if(password.getText().equals("")||password.getText().equals(null)){
			throw new UserException("Please Enter username");
		}
		return true;
	}

	public void viewSelected(String selected) throws ClassNotFoundException, SQLException {
		action="UPDATE";
		Users u=new Users();
		UserEvent ue=new UserEvent();
		u=ue.getUser(selected);
		userName.setText(u.getUserName());
		userName.setEditable(false);
		password.setText(u.getPassword());
		//role.setSelectedItem(u.getRole());
		
		}
	
	public void updateUser() throws ClassNotFoundException, SQLException, UserException{
		Users u=new Users();
		u.setUserName(userName.getText());
		u.setPassword(password.getText());
		//u.setRole(role.getSelectedItem().toString());
		UserEvent ue=new UserEvent();
		if(validate()){
			ue.updateUser(u);
		}
		
	}
	
	private void reload(){
		userName.setText("");
		password.setText("");
	}
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
}
