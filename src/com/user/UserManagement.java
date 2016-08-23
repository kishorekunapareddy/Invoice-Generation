package com.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.main.UserMain;

import java.awt.Font;

public class UserManagement {

	public  JFrame frame;
	public JPanel panel;
	private JLabel lblUserpanel;
	private JList usrList;
	private JScrollPane scrollPane;
	

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserManagement window = new UserManagement();
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
	public UserManagement() throws ClassNotFoundException, SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 703, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(25, 23, 636, 292);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblUserpanel = new JLabel("User Management");
		lblUserpanel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUserpanel.setBounds(250, 23, 160, 14);
		panel.add(lblUserpanel);
		
		/*JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					addUser();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(102, 222, 97, 23);
		panel.add(btnAdd);*/
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					updateUser();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(250, 222, 106, 23);
		panel.add(btnUpdate);
		
		/*JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					delete();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		btnDelete.setBounds(387, 222, 106, 23);
		panel.add(btnDelete);*/
		
	
		viewUser();

		
	}

	@SuppressWarnings("unchecked")
	private void viewUser() throws ClassNotFoundException, SQLException {
		ArrayList<Users> userList= new ArrayList<Users>();
		UserEvent ue=new UserEvent();
		userList=ue.getAllUser();
		String uList[] = new String[100];
		int i=0;
		for(Users u:userList){			
				uList[i]=u.getUserName()+"   ";
				i++;			
		}
		usrList = new JList(uList);		
		usrList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane=new JScrollPane(usrList);
		scrollPane.setBounds(161, 56, 272, 119);
		panel.add(scrollPane);
	}
	

	private void delete() throws ClassNotFoundException, SQLException {
		UserEvent ue=new UserEvent();
		if(usrList.getSelectedValue() != null){
			String selected=usrList.getSelectedValue().toString().split(" ")[0];
			ue.delete(selected);
			panel.setEnabled(false);
			UserMain um=new UserMain();
			um.panel.add(panel);
			um.panel.setVisible(true);
	}else
		dialog("Please select a user");
		
	}
	
	private void addUser() {
		UserView uv=new UserView();
		uv.action="ADD";
		panel.removeAll();
		panel.add(uv.panel);
		panel.updateUI();
		
	}
	
	private void updateUser() throws ClassNotFoundException, SQLException {
		UserView uv=new UserView();
		if(usrList.getSelectedValue() != null){
			String selected=usrList.getSelectedValue().toString().split(" ")[0];
			uv.viewSelected(selected);
			panel.removeAll();
			panel.add(uv.panel);
			uv.action="UPDATE";
			panel.updateUI();
	}else
		dialog("Please select a user");
		
	}
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
}
