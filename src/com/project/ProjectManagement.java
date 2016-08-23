package com.project;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ProjectManagement {

	private JFrame frame;
	public JPanel panel;
	private JList clList;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProjectManagement window = new ProjectManagement();
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
	public ProjectManagement() throws ClassNotFoundException, SQLException {
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
		
		JLabel lblProjectpanel = new JLabel("Project Management");
		lblProjectpanel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProjectpanel.setBounds(250, 23, 160, 14);
		panel.add(lblProjectpanel);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					addProject();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(102, 222, 97, 23);
		panel.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					updateProject();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(250, 222, 106, 23);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
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
		panel.add(btnDelete);
		
	
		viewProject();

		
	}

	@SuppressWarnings("unchecked")
	private void viewProject() throws ClassNotFoundException, SQLException {
		ArrayList<Project> clientList= new ArrayList<Project>();
		ProjectEvent ue=new ProjectEvent();
		clientList=ue.getAllProject();
		String uList[] = new String[100];
		int i=0;
		for(Project u:clientList){			
				uList[i]=u.getNumber()+"  "+u.getName()+"  "+u.getClient()+"  "+u.getManager()+"  "+u.getStatus()+"  "+u.getBudget();
				i++;			
		}
		clList = new JList(uList);		
		clList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(clList);
		scrollPane.setBounds(161, 56, 272, 119);
		panel.add(scrollPane);
	}
	

	private void delete() throws ClassNotFoundException, SQLException {
		ProjectEvent ue=new ProjectEvent();
		if(clList.getSelectedValue() != null){
			String selected=clList.getSelectedValue().toString().split("  ")[0];
			ue.delete(selected);
			ProjectManagement um=new ProjectManagement();
			panel.removeAll();
			panel.add(um.panel);
			panel.updateUI();
			panel.repaint();
			panel.revalidate();
	}else
		dialog("Please select a user");
		
	}
	
	private void addProject() throws ClassNotFoundException, SQLException {
		ProjectView uv=new ProjectView();
		uv.action="ADD";
		panel.removeAll();
		panel.add(uv.panel);
		panel.updateUI();
		
	}
	
	private void updateProject() throws ClassNotFoundException, SQLException, ParseException {
		ProjectView uv=new ProjectView();
		if(clList.getSelectedValue() != null){
			String selected=clList.getSelectedValue().toString().split(" ")[0];
			uv.viewSelected(selected);
			panel.removeAll();
			panel.add(uv.panel);
			uv.action="UPDATE";
			panel.updateUI();
	}else
		dialog("Please select a Project");
		
	}
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
}
