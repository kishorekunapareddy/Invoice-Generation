package com.project.employee;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.employee.Employee;
import com.employee.EmployeeEvent;
import com.project.Project;
import com.project.ProjectEvent;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeeMap {

	private String userId;
	private JFrame frame;
	public JPanel panel ;
	private JComboBox project ;
	//private JList projectList;
	private JScrollPane projectPanel;
	private JList employeeList1;
	private ArrayList<JList> employeeList=new ArrayList<JList>();;
	private JScrollPane employeePanel1;
	private ArrayList<JScrollPane> employeePanel=new ArrayList<JScrollPane>();
	private ArrayList<String> assignEmployee;
	int assinCount=0;
	
	
	private JList employeeListAll;
	private JScrollPane employeePanelAll;
	int eCount=0;

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeMap window = new EmployeeMap();
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
	public EmployeeMap() throws ClassNotFoundException, SQLException {
		initialize();
	}
	

	public EmployeeMap(String username) throws ClassNotFoundException, SQLException {
		this.userId=username;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 721, 486);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 671, 369);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		project = new JComboBox();
		project.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getAssignedEmployee(project.getSelectedItem().toString().split(" ")[0]);
				} catch (NumberFormatException | ClassNotFoundException
						| SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		project.setBounds(252, 46, 150, 20);
		panel.add(project);
		

		JLabel lblAllEmployee = new JLabel("All Employees");
		lblAllEmployee.setBounds(155, 68, 98, 14);
		panel.add(lblAllEmployee);
		
		JLabel lblAssignedEmployees = new JLabel("Assigned Employees");
		lblAssignedEmployees.setBounds(464, 68, 136, 14);
		panel.add(lblAssignedEmployees);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNew();
			}
		});
		btnAdd.setBounds(311, 112, 89, 23);
		panel.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeEmp();
			}
		});
		btnRemove.setBounds(311, 146, 89, 23);
		panel.add(btnRemove);
				

		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					saveProject();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(275, 198, 89, 23);
		panel.add(btnSave);
		viewProject();
		viewAllEmployee();
		//viewAssignedEmpl();
		
	}
	
	

	private void viewAllEmployee() throws ClassNotFoundException, SQLException {
		
		ArrayList<Employee> cl=new ArrayList<Employee>();
		EmployeeEvent ce=new EmployeeEvent();
		cl=ce.getAllEmployee();
		
		String eList[] = new String[1000];
		int i=0;
		for(Employee c:cl){
			if(c.getRole().equals("Developer")){
				eList[i]=c.getEmpid()+" "+c.getName();
				i++;
			}
		}
		employeeListAll = new JList(eList);
		employeeListAll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeePanelAll=new JScrollPane(employeeListAll);
		employeePanelAll.setBounds(118, 93, 161, 88);		
		panel.add(employeePanelAll);
	}

	private void viewProject() throws ClassNotFoundException, SQLException {
		ArrayList<Project> cl=new ArrayList<Project>();
		ProjectEvent ce=new ProjectEvent();
		cl=ce.getAllProjectByManager(userId);
		for(Project c:cl){
			if(c.getStatus()!="Closed"){
				project.addItem(c.getNumber()+" "+c.getName());			
			}

		}
		JLabel lblProject = new JLabel("Assign Employee");
		lblProject.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblProject.setBounds(301, 14, 161, 14);
		panel.add(lblProject);
		
	}
	
	public void getAssignedEmployee(String selectedItem) throws NumberFormatException, ClassNotFoundException, SQLException {
		
		
		EmployeeMapEvent eme= new EmployeeMapEvent();
		ArrayList<EmployeeProject> ep=new ArrayList<EmployeeProject>();
		ep=eme.getMap(Integer.parseInt(selectedItem));
		 assignEmployee=new ArrayList<String>();
		
		int i=0;
		for(EmployeeProject c:ep){
			Employee e=new Employee();
			EmployeeEvent ee=new EmployeeEvent();
			e=ee.getEmployee(Integer.toString(c.getEmpId()));
			if(e.getRole().equals("Developer"))
			assignEmployee.add(e.getEmpid()+" "+e.getName());
			i++;
			assinCount++;
		}
		viewAssignedEmpl();
	}
	
	private void viewAssignedEmpl(){
		String cList[] = new String[assignEmployee.size()];
		for(int i=0;i<cList.length;i++)
			cList[i]=assignEmployee.get(i);
		
		/*JList employeeList1 = new JList(cList);
		employeeList.add(employeeList1);
		employeeList.get(eCount).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		JScrollPane employeePanel1=new JScrollPane(employeeList.get(eCount));
		employeePanel.add(employeePanel1);
		employeePanel.get(eCount).setBounds(425, 93, 161, 88);		
		employeeList.get(eCount).setEnabled(true);
		employeeList.get(eCount).updateUI();
		employeeList.get(eCount).setEnabled(true);
		employeePanel.get(eCount).setEnabled(true);
		employeePanel.get(eCount).updateUI();		
		panel.add(employeePanel.get(eCount));*/
		if(eCount>0){
			employeeList1.setVisible(false);
			employeePanel1.setVisible(false);
			employeeList1.updateUI();
			employeePanel1.updateUI();
		}
		
		employeeList1 = new JList(cList);
		employeeList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeePanel1=new JScrollPane(employeeList1);
		employeePanel1.setBounds(425, 93, 161, 88);		
		panel.add(employeePanel1);
		
		panel.updateUI();
		panel.repaint();
		/*if(eCount>0){
			employeePanel.get(eCount-1).setEnabled(false);
			employeePanel.get(eCount-1).updateUI();
			employeeList.get(eCount-1).setEnabled(false);
			employeeList.get(eCount-1).updateUI();
		}*/
		eCount++;
	}
	
	public void addNew(){
		String emp=employeeListAll.getSelectedValue().toString();
		boolean status =true;		
		if(!assignEmployee.contains(emp)){
			assignEmployee.add(emp);
		}
		viewAssignedEmpl();
	}
	
	public void removeEmp(){
		String emp=employeeList1.getSelectedValue().toString();
		int i=0;
		int index = 0;
		for(String s:assignEmployee){
			if(s.equals(emp))
				index=i;
			i++;
		}
		assignEmployee.remove(index);
		viewAssignedEmpl();
	}
	
	public void saveProject() throws ClassNotFoundException, SQLException{
		if(assignEmployee.size()>0){
			assignEmployee.add(userId);
			ArrayList<EmployeeProject> ep=new ArrayList<EmployeeProject>();
			for(String s:assignEmployee){
				EmployeeProject e=new EmployeeProject();
				e.setEmpId(Integer.parseInt(s.split(" ")[0]));
				e.setProjectId(Integer.parseInt(project.getSelectedItem().toString().split(" ")[0]));
				ep.add(e);
			}
			EmployeeMapEvent emp=new EmployeeMapEvent();
			emp.saveMap(ep);
			dialog("Employees assigned to the project sucessfully");
		}else
			dialog("No users assigned to the project");
		
	}
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
}
