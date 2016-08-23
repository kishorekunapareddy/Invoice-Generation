package com.project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.JXDatePicker;

import com.client.Client;
import com.client.ClientEvent;
import com.employee.Employee;
import com.employee.EmployeeEvent;
import com.login.UserException;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProjectView {

	private JFrame frame;
	public JPanel panel ;
	private JTextField name;
	private JXDatePicker startdate;
	private JXDatePicker enddate;
	private JLabel lblClient;
	private JTextField client;
	private JTextField manager;
	private JLabel lblNewLabel;
	private JTextField budget;
	private JLabel lblBudget;
	private JList<?> clientList;
	private JList<?> managerList;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JButton btnSave;
	private JButton btnAdd;
	public String action;
	private JTextField contact;
	

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Projectview window = new Projectview();
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
	public ProjectView() throws ClassNotFoundException, SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 726, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(22, 11, 678, 429);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblProjectManagement = new JLabel("Project Management");
		lblProjectManagement.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProjectManagement.setBounds(256, 11, 166, 14);
		panel.add(lblProjectManagement);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(168, 46, 46, 14);
		panel.add(lblName);
		
		name = new JTextField();
		name.setBounds(247, 43, 156, 20);
		panel.add(name);
		name.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(168, 77, 72, 14);
		panel.add(lblStartDate);
		
		
		
        startdate = new JXDatePicker();
        //startdate.setDate(Calendar.getInstance().getTime());
        startdate.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        startdate.setBounds(247, 74, 132, 20);
        panel.add(startdate);
        frame.getContentPane().add(panel);
        
        JLabel lblEndDate = new JLabel("End Date");
        lblEndDate.setBounds(389, 74, 72, 14);
        panel.add(lblEndDate);
        
        enddate = new JXDatePicker();
        enddate.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        enddate.setBounds(461, 74, 132, 20);
        panel.add(enddate);
        
        lblClient = new JLabel("Client");
        lblClient.setBounds(168, 102, 46, 14);
        panel.add(lblClient);
        
        client = new JTextField();
        client.setBounds(247, 105, 132, 20);
        panel.add(client);
        client.setColumns(10);
        
        manager = new JTextField();
        manager.setBounds(247, 162, 132, 20);
        panel.add(manager);
        manager.setColumns(10);
        
        lblNewLabel = new JLabel("Manager");
        lblNewLabel.setBounds(168, 165, 69, 14);
        panel.add(lblNewLabel);
        
        budget = new JTextField();
        budget.setBounds(247, 226, 86, 20);
        panel.add(budget);
        budget.setColumns(10);
        
        lblBudget = new JLabel("Budget");
        lblBudget.setBounds(168, 229, 46, 14);
        panel.add(lblBudget);
        
        btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(action=="ADD"){
					try {
						addProject();
						dialog("Project Added sucessfully");
						reload();
					}catch (ClassNotFoundException | SQLException e1) {
						dialog("Project already exists");
						e1.printStackTrace();
					} catch (UserException e1) {
						dialog(e1.getMessage());
						e1.printStackTrace();
					}
				}else if(action=="UPDATE"){
					try {
						updateProject();
						dialog("Project updated sucessfully");
					} catch (ClassNotFoundException | SQLException e1) {
						dialog("Project failed to update");
						e1.printStackTrace();
					}catch (UserException e1) {
						dialog(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		});
        btnSave.setBounds(286, 257, 89, 23);
        panel.add(btnSave);
        
        addClient();
        addManager();
        
        
	}

	private void addManager() throws ClassNotFoundException, SQLException {
		ArrayList<Employee> cl=new ArrayList<Employee>();
		EmployeeEvent ce=new EmployeeEvent();
		cl=ce.getAllEmployee();
		
		String cList[] = new String[1000];
		int i=0;
		for(Employee c:cl){
			if(c.getRole().equals("Project Manager")){
				cList[i]=c.getEmpid()+" "+c.getName();
				i++;
			}			
		}
		managerList = new JList<Object>(cList);		
		managerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane2 = new JScrollPane(managerList);
		scrollPane2.setBounds(481, 163, 156, 52);
		panel.add(scrollPane2);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedId=managerList.getSelectedValue().toString();
				if(!selectedId.equals(null)||!selectedId.equals(""))
					manager.setText(selectedId);
				else {
					dialog("Please select Manager");
				}
			}
		});
		btnAdd.setBounds(399, 161, 72, 23);
		panel.add(btnAdd);
		
		contact = new JTextField();
		contact.setBounds(247, 195, 86, 20);
		panel.add(contact);
		contact.setColumns(10);
		
		JLabel lblContact = new JLabel("Contact");
		lblContact.setBounds(168, 204, 46, 14);
		panel.add(lblContact);
		

	}

	private void addClient() throws ClassNotFoundException, SQLException {
		ArrayList<Client> cl=new ArrayList<Client>();
		ClientEvent ce=new ClientEvent();
		cl=ce.getAllClient();
		
		String cList[] = new String[1000];
		int i=0;
		for(Client c:cl){
			cList[i]=c.getClientid()+","+c.getName();
			i++;
		}
		clientList = new JList<Object>(cList);		
		clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane1 = new JScrollPane(clientList);
		scrollPane1.setBounds(481, 100, 156, 52);
		panel.add(scrollPane1);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedId=clientList.getSelectedValue().toString();
				if(!selectedId.equals(null)||!selectedId.equals(""))
					client.setText(selectedId.split(",")[0]);
				else {
					dialog("Please select Client");
				}
			}
		});
		btnAdd.setBounds(399, 105, 72, 23);
		panel.add(btnAdd);
		
	}
	public void addProject() throws UserException, ClassNotFoundException, SQLException{
		vaildate();
		Project p=new Project();
		p.setClient(Integer.parseInt(client.getText()));
		p.setName(name.getText());
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		p.setStartdate(formater.format(startdate.getDate()));
		p.setEnddate(formater.format(enddate.getDate()));
		Date d=new Date();
		if(enddate.getDate().after(d))
			p.setStatus("In Progress");
		else
			p.setStatus("Closed");
		p.setManager(manager.getText().split(" ")[0]);
		p.setContact(contact.getText());
		p.setBudget(Integer.parseInt(budget.getText()));
		ProjectEvent pe=new ProjectEvent();
		pe.addProject(p);
		
		
	}
	public void updateProject() throws UserException, ClassNotFoundException, SQLException{
		vaildate();
		Project p=new Project();
		p.setClient(Integer.parseInt(client.getText()));
		p.setName(name.getText());
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		p.setStartdate(formater.format(startdate.getDate()));
		p.setEnddate(formater.format(enddate.getDate()));
		Date d=new Date();
		if(enddate.getDate().after(d))
			p.setStatus("In Progress");
		else
			p.setStatus("Closed");
		p.setManager(manager.getText().split(" ")[0]);
		p.setContact(contact.getText());
		p.setBudget(Integer.parseInt(budget.getText()));
		ProjectEvent pe=new ProjectEvent();
		pe.updateProject(p);
	}
	public void viewSelected(String selected) throws ClassNotFoundException, SQLException, ParseException {
		Project p=new Project();
		ProjectEvent pe=new ProjectEvent();
		p=pe.getProject(selected);
		name.setText(p.getName());
		name.setEditable(false);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		startdate.setDate(formatter.parse(p.getStartdate()));
		enddate.setDate(formatter.parse(p.getEnddate()));
		client.setText(Integer.toString(p.getClient()));
		manager.setText(p.getManager());
		contact.setText(p.getContact());
		budget.setText(Integer.toString(p.getBudget()));
		
	}
	public void reload(){
		name.setText("");
		client.setText("");
		manager.setText("");
		contact.setText("");
		budget.setText("");	
	}
	public void vaildate() throws UserException{
		if(name.getText().equals("")||name.getText().equals(null))
			throw new UserException("Please enter name");
		if(client.getText().equals("")||client.getText().equals(null))
			throw new UserException("Please select Client");
		if(manager.getText().equals("")||manager.getText().equals(null))
			throw new UserException("Please select manager");
		if(null==startdate.getDate())
			throw new UserException("Please select startdate");
		if(null==enddate.getDate())
			throw new UserException("Please select enddate");
		if(budget.getText().equals("")||budget.getText().equals(null))
			throw new UserException("Please enter Budget");
		if(enddate.getDate().before(startdate.getDate()))
			throw new UserException("Please select valid end date");
		
		if(Pattern.matches("[a-zA-Z]+", budget.getText()) == true||budget.getText().equals(""))
			throw new UserException("Zip should be numaric ");
	}
	
	
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
}
