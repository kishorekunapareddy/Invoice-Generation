package com.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import com.login.UserException;
import com.main.UserMain;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientView {

	private JFrame frame;
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField city;
	private JTextField state;
	private JTextField zip;
	private JTextField email;
	private JTextField contact;
	public String action;
	public JPanel panel ;
	private JComboBox billingterms;
	private JComboBox invoiceGroup; 
	private JComboBox invoicefrq ;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientView window = new ClientView();
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
	public ClientView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 720, 486);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 0, 652, 407);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblClientManagement = new JLabel("Client Management");
		lblClientManagement.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClientManagement.setBounds(264, 0, 180, 14);
		panel.add(lblClientManagement);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(76, 28, 46, 14);
		panel.add(lblName);
		
		name = new JTextField();
		name.setBounds(175, 25, 220, 20);
		panel.add(name);
		name.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(76, 53, 66, 14);
		panel.add(lblAddress);
		
		JLabel lblLine = new JLabel("Line 1");
		lblLine.setBounds(96, 83, 46, 14);
		panel.add(lblLine);
		
		address1 = new JTextField();
		address1.setBounds(175, 80, 86, 20);
		panel.add(address1);
		address1.setColumns(10);
		
		JLabel lblLine_1 = new JLabel("Line 2");
		lblLine_1.setBounds(288, 83, 46, 14);
		panel.add(lblLine_1);
		
		address2 = new JTextField();
		address2.setBounds(344, 80, 86, 20);
		panel.add(address2);
		address2.setColumns(10);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(96, 108, 46, 14);
		panel.add(lblCity);
		
		city = new JTextField();
		city.setBounds(175, 105, 86, 20);
		panel.add(city);
		city.setColumns(10);
		
		JLabel lblState = new JLabel("State");
		lblState.setBounds(288, 108, 46, 14);
		panel.add(lblState);
		
		state = new JTextField();
		state.setBounds(344, 105, 86, 20);
		panel.add(state);
		state.setColumns(10);
		
		JLabel lblZip = new JLabel("Zip");
		lblZip.setBounds(459, 108, 46, 14);
		panel.add(lblZip);
		
		zip = new JTextField();
		zip.setBounds(515, 105, 86, 20);
		panel.add(zip);
		zip.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(76, 146, 46, 14);
		panel.add(lblEmail);
		
		email = new JTextField();
		email.setBounds(175, 136, 220, 20);
		panel.add(email);
		email.setColumns(10);
		
		JLabel lblContact = new JLabel("Contact");
		lblContact.setBounds(76, 171, 46, 14);
		panel.add(lblContact);
		
		contact = new JTextField();
		contact.setBounds(175, 167, 132, 20);
		panel.add(contact);
		contact.setColumns(10);
		
		JLabel lblInvoiceFrequency = new JLabel("Invoice Frequency");
		lblInvoiceFrequency.setBounds(76, 211, 104, 14);
		panel.add(lblInvoiceFrequency);
		
		invoicefrq = new JComboBox();
		invoicefrq.setBounds(201, 208, 86, 20);
		invoicefrq.addItem("Weekly");
		invoicefrq.addItem("BiWeekly");
		invoicefrq.addItem("Monthly");
		invoicefrq.addItem("Monthly-Cal");
		panel.add(invoicefrq);
		
		JLabel lblBillingTerms = new JLabel("Billing Terms");
		lblBillingTerms.setBounds(307, 211, 71, 14);
		panel.add(lblBillingTerms);
		
		JLabel lblGroup = new JLabel("Group");
		lblGroup.setBounds(500, 211, 46, 14);
		panel.add(lblGroup);
		
		invoiceGroup = new JComboBox();
		invoiceGroup.setBounds(541, 208, 71, 20);
		invoiceGroup.addItem("Project");
		invoiceGroup.addItem("Client");
		panel.add(invoiceGroup);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(action=="ADD"){
					try {
						addClient();
						dialog("User Added sucessfully");
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
						updateClient();
						dialog("User updated sucessfully");
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
		btnSave.setBounds(230, 249, 89, 23);
		panel.add(btnSave);
		
		billingterms = new JComboBox();
		billingterms.setBounds(388, 208, 97, 20);
		billingterms.addItem("Due on Recipt");
		billingterms.addItem("Net 10 Days");
		billingterms.addItem("Net 20 Days");
		billingterms.addItem("Net 30 Days");
		billingterms.addItem("Net 60 Days");
		panel.add(billingterms);
		
		/*JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UserMain cm=new UserMain();
					panel.removeAll();
					panel.setVisible(false);
					panel.updateUI();
					cm.clientEvent();
					cm.panel.updateUI();
					cm.panel.setVisible(true);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnBack.setBounds(355, 249, 89, 23);
		panel.add(btnBack);*/
	}
	
	public void addClient() throws ClassNotFoundException, SQLException, UserException{
		validate();
		Client c=new Client();
		c.setName(name.getText());
		c.setAddress1(address1.getText());
		c.setAddress2(address2.getText());
		c.setCity(city.getText());
		c.setState(state.getText());
		c.setZip(Integer.parseInt(zip.getText()));
		c.setEmail(email.getText());
		c.setContact(contact.getText());
		c.setInvoicefrq(invoicefrq.getSelectedItem().toString());
		c.setBillingterms(billingterms.getSelectedItem().toString());
		c.setInvoiceGroup(invoiceGroup.getSelectedItem().toString());
		ClientEvent ce=new ClientEvent();
		ce.addClient(c);
		
	}
	public void updateClient() throws ClassNotFoundException, SQLException, UserException{
		validate();
		Client c=new Client();
		c.setName(name.getText());
		c.setAddress1(address1.getText());
		c.setAddress2(address2.getText());
		c.setCity(city.getText());
		c.setState(state.getText());
		c.setZip(Integer.parseInt(zip.getText()));
		c.setEmail(email.getText());
		c.setContact(contact.getText());
		c.setInvoicefrq(invoicefrq.getSelectedItem().toString());
		c.setBillingterms(billingterms.getSelectedItem().toString());
		c.setInvoiceGroup(invoiceGroup.getSelectedItem().toString());	
		ClientEvent ce=new ClientEvent();
		ce.updateClient(c);
	}

	public void viewSelected(String selected) throws ClassNotFoundException, SQLException {
		Client c=new Client();
		ClientEvent ce=new ClientEvent();
		c=ce.getClient(selected);
		name.setText(c.getName());
		address1.setText(c.getAddress1());
		address2.setText(c.getAddress2());
		city.setText(c.getCity());
		state.setText(c.getState());
		zip.setText(Integer.toString(c.getZip()));
		email.setText(c.getEmail());
		contact.setText(c.getContact());
		invoicefrq.setSelectedItem(c.getInvoicefrq());
		billingterms.setSelectedItem(c.getBillingterms());
		invoiceGroup.setSelectedItem(c.getInvoiceGroup());
		name.setEditable(false);
	}
	
	public void reload(){
		name.setText("");
		address1.setText("");
		address2.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
		email.setText("");
		contact.setText("");
	}
	private void dialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
	public void validate() throws UserException{
		if(name.getText().equals("")||name.getText().equals(null))
			throw new UserException("Please enter name");
		if(Pattern.matches("[a-zA-Z]+", zip.getText()) == true||zip.getText().equals("")||zip.getText().equals(null))
			throw new UserException("Zip should be numaric ");		
		String regex = "^(.+)@(.+)$";		 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email.getText());
		if(!matcher.matches())
			throw new UserException("Invalid Email ");
	}
}
