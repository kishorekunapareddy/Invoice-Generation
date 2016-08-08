package com.company;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.login.UserException;
import com.main.UserMain;

public class CompanyView {

	private JFrame frame;
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField city;
	private JTextField state;
	private JTextField zip;
	private JTextField email;
	public JPanel panel ;

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompanyView window = new CompanyView();
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
	public CompanyView() {
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
		
		JLabel lblClientManagement = new JLabel("Update Company");
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
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						updateClient();
						dialog("Company details sucessfully");
						UserMain um=new UserMain();
					} catch (ClassNotFoundException | SQLException e1) {
						dialog("Company details failed to update");
						e1.printStackTrace();
					}catch (UserException e1) {
						dialog(e1.getMessage());
						e1.printStackTrace();
					}
			}
		});
		btnSave.setBounds(231, 177, 89, 23);
		panel.add(btnSave);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserMain um;
				try {
					um = new UserMain();
					panel.removeAll();
					um.viewComany();
					um.panel.updateUI();
					panel.add(um.panel);
					panel.updateUI();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnBack.setBounds(355, 177, 89, 23);
		panel.add(btnBack);
		
		
	}
	
	public void updateClient() throws ClassNotFoundException, SQLException, UserException{
		validate();
		Company c=new Company();
		c.setName(name.getText());
		c.setAddress1(address1.getText());
		c.setAddress2(address2.getText());
		c.setCity(city.getText());
		c.setState(state.getText());
		c.setZip(Integer.parseInt(zip.getText()));
		CompanyEvent ce=new CompanyEvent();
		ce.updateCompany(c);
	}

	public void viewSelected(String selected) throws ClassNotFoundException, SQLException {
		Company c=new Company();
		CompanyEvent ce=new CompanyEvent();
		c=ce.getCompany(selected);
		name.setText(c.getName());
		address1.setText(c.getAddress1());
		address2.setText(c.getAddress2());
		city.setText(c.getCity());
		state.setText(c.getState());
		zip.setText(Integer.toString(c.getZip()));
		name.setEditable(false);
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
	}
}
