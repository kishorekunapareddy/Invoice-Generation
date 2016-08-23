package com.report;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.employee.Employee;
import com.employee.EmployeeEvent;
import com.invoice.Invoice;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeReport {

	private JFrame frame;
	public JPanel panel;
	private JComboBox month;
	private JTable table; 
	private JScrollPane scrollPane;
	private JButton btnGeneratePdf;

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeReport window = new EmployeeReport();
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
	public EmployeeReport() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 716, 485);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 669, 387);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		month = new JComboBox();
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
		month.setBounds(132, 30, 84, 20);
		panel.add(month);
		
		JButton btnGetInvoice = new JButton("Get Invoice");
		btnGetInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					viewReport();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGetInvoice.setBounds(290, 29, 122, 23);
		panel.add(btnGetInvoice);
		
		btnGeneratePdf = new JButton("Generate PDF");
		btnGeneratePdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					generatePDF();
				} catch (ClassNotFoundException | SQLException | FileNotFoundException | DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGeneratePdf.setBounds(473, 29, 152, 23);
		panel.add(btnGeneratePdf);
		
		
	}
	
	public String[][] getReport() throws ClassNotFoundException, SQLException{
		ReportEvent re=new ReportEvent();
		ArrayList<Invoice> inList=new ArrayList<Invoice>();
		inList=re.EmployeeReport(month.getSelectedItem().toString());
		String[][] data=new String[60][8];
		int j=0;
		if(null==inList){
			dialog("No Invoice found");
			return null;
		}else{
		for(Invoice i:inList){
			String project="";
			EmployeeEvent ee=new EmployeeEvent();
			Employee e=new Employee();
			for(String p:i.getProject()){
				if(null!=p){
					if(""==project)
						project=p.split(",")[0];
					else
						project=project+","+p.split(",")[0];
				}
				
			}
			e=ee.getEmployee(Integer.toString(i.getEmpId()));	
			data[j][0]=Integer.toString(i.getEmpId());
			data[j][1]=e.getName();
			data[j][2]=e.getRole();
			data[j][3]=i.getMonth();
			data[j][4]=project;
			data[j][5]=Double.toString(i.getTotalAmount()/e.getBillrate());
			data[j][6]=Integer.toString(e.getBillrate());
			data[j][7]=Double.toString(i.getTotalAmount());
			j++;
			
		}
		return data;
		}
	}
	
	public void viewReport() throws ClassNotFoundException, SQLException{
		String empInvoice[][]=null;
		empInvoice=getReport();
		table = new JTable();
		table.setModel(new DefaultTableModel(
				empInvoice,			
			new String[] {
				"Employee Id", "Name","Role","Month","Project Assigned","Total Hour", "Billrate","Total Amount"
			}
		));
		
		scrollPane = new JScrollPane( table );
		scrollPane.setBounds(28, 94, 617, 304);
		panel.add(scrollPane);
	}
	
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
	
	public void generatePDF() throws ClassNotFoundException, SQLException, FileNotFoundException, DocumentException{
		String data[][]=getReport();
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(month.getSelectedItem().toString()+"_Report.pdf"));
        document.open();         
        document.add(new Paragraph(month.getSelectedItem().toString()+" Invoice Report"));
        document.add(new Paragraph(" Employee Id  Name                Role  Month  Project Assigned  Total Hour   Billrate  Total Amount"));
        for(int i=0;i<data.length;i++){
        	if(null!=data[i][0]){
            	String row=data[i][0]+" "+data[i][1]+" "+data[i][2]+" "+data[i][3]+" "+data[i][4]+" "+data[i][5]+" "+data[i][6]+" "+data[i][7];
            	document.add(new Paragraph(row));
        	}
        }
        document.close();
        writer.close();
        dialog("PDF has been generated");
	}
}
