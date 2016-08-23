package com.invoice;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JButton;

import com.employee.Employee;
import com.employee.EmployeeEvent;
import com.project.Project;
import com.project.ProjectEvent;
import com.project.employee.EmployeeMapEvent;
import com.project.employee.EmployeeProject;
import com.timesheet.TimeSheet;
import com.timesheet.TimeSheetEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenerateInvoice {

	private JFrame frame;
	public JPanel panel ;
	private JComboBox month;
	private ArrayList<Employee> empList;
	private ArrayList<Project> proList;
	private ArrayList<TimeSheet> tsList;
	private ArrayList<EmployeeProject> empProList;
	

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerateInvoice window = new GenerateInvoice();
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
	public GenerateInvoice() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 722, 488);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 686, 355);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblGenerateInvoice = new JLabel("Generate Invoice");
		lblGenerateInvoice.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGenerateInvoice.setBounds(268, 11, 171, 14);
		panel.add(lblGenerateInvoice);
		
		month = new JComboBox();
		month.setBounds(227, 80, 104, 20);
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
		
		JLabel lblSelectMonth = new JLabel("Select Month");
		lblSelectMonth.setBounds(115, 83, 85, 14);
		panel.add(lblSelectMonth);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					generateInvoice();
					dialog("Invoice generated");
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnGenerate.setBounds(391, 79, 89, 23);
		panel.add(btnGenerate);
	}
	
	private void generateInvoice() throws ClassNotFoundException, SQLException{
		InvoiceEvent ie=new InvoiceEvent();
		ie.deleteInvoice(month.getSelectedItem().toString());
		getAllDetails();
		for(Employee e:empList){
			ArrayList<Integer> projects = new ArrayList<Integer>();
			ArrayList<Integer> hours = new ArrayList<Integer>();
			ArrayList<Integer> amount = new ArrayList<Integer>();
			ArrayList<String> status = new ArrayList<String>();
			int i=0;
			EmployeeMapEvent ee=new EmployeeMapEvent();
			ArrayList<Integer> project=new ArrayList<Integer> ();
			project=ee.getProjects(e.getEmpid());
			
			for(int proId:project){
				int totalHour=0;
				projects.add(proId);
				TimeSheet ts=new TimeSheet();
				TimeSheetEvent tse=new TimeSheetEvent();
				String status1="";
				int amt=0;
				int len=0;
				try{
					for(int k=1;k<=5;k++){
						ts=tse.getTimeSheet(e.getEmpid(),Integer.toString(proId) , month.getSelectedItem().toString(),"week"+k);
						if(ts!=null){
							try{
							String[] week1=ts.getWeek().split("");
							String sta=ts.getWeek().split("")[6];
							len++;
							if(sta.equals("A")){
								status1=status1+"A";
								totalHour=totalHour+Integer.parseInt(week1[1])+Integer.parseInt(week1[2])+Integer.parseInt(week1[3])+Integer.parseInt(week1[4])+Integer.parseInt(week1[5]);
								amt=amt+totalHour*e.getBillrate();
							}}catch(NullPointerException n){
								
							}
						}
						
					}
					
					hours.add(totalHour);
					if(len==status1.length()){
						status.add("Appoved");
					}else if(status1.length()==0){
						status.add("Submitted for Approval");
					}else{
						status.add("Partially Appoved");
					}
					amount.add(amt);

				}
				catch(IndexOutOfBoundsException exp){
					hours.add(0);
					status.add("NotSubmitted");
					amount.add(0);
				}
			}
			int j=0;
			int totalAmount=0;
			ArrayList<String> emp=new ArrayList<String>();
			while(j<projects.size()){
				emp.add(project.get(j)+","+hours.get(j)+","+amount.get(j)+","+status.get(j));
				totalAmount=totalAmount+amount.get(j);
				j++;
			}
			Invoice inc=new Invoice();
			inc.setEmpId(e.getEmpid());
			inc.setBillrate(e.getBillrate());
			inc.setMonth(month.getSelectedItem().toString());
			inc.setTotalAmount(totalAmount);
			inc.setProject(emp);
			ie.addInvoice(inc);		
		}
		
	}
	
	private void getAllDetails() throws ClassNotFoundException, SQLException{
		EmployeeEvent ee=new EmployeeEvent();
		empList=new ArrayList<Employee>();
		empList=ee.getAllEmployee();
		
		ProjectEvent pe=new ProjectEvent();
		proList=new ArrayList<Project>();
		proList=pe.getAllProject();
	}
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
}
