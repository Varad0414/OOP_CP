package airlinemanagement;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class manageflight extends JFrame implements ActionListener {

	JLabel lblTitle, lblFlightCode, lblSource, lblDestination, lblTakeOff, lblNoOfSeats;
	JTextField txtFlightCode, txtSource, txtDestination, txtTakeOff, txtNoOfSeats;
	JButton btnInsert, btnUpdate, btnSearch, btnDelete, btnBack;
	JTable tblFlight;
	JScrollPane spTable;
	DefaultTableModel model;
	String[] columnNames = { "FlightCode", "Source", "Destination", "Take off", "NoofSeats" };
	boolean isUpdate = false;

	public manageflight() {

		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255, 204, 153));

		lblTitle = new JLabel("Manage Flight");
		lblTitle.setBounds(270, 10, 200, 30);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		c.add(lblTitle);

		lblFlightCode = new JLabel("Flight Code:");
		lblFlightCode.setBounds(20, 60, 100, 30);
		c.add(lblFlightCode);

		txtFlightCode = new JTextField();
		txtFlightCode.setBackground(new Color(255, 255, 204));
		txtFlightCode.setBounds(120, 60, 100, 30);
		c.add(txtFlightCode);

		lblSource = new JLabel("Source:");
		lblSource.setBounds(20, 100, 100, 30);
		c.add(lblSource);

		txtSource = new JTextField();
		txtSource.setBounds(120, 100, 100, 30);
		txtSource.setBackground(new Color(255, 255, 204));
		c.add(txtSource);

		lblDestination = new JLabel("Destination:");
		lblDestination.setBounds(20, 140, 100, 30);
		c.add(lblDestination);

		txtDestination = new JTextField();
		txtDestination.setBounds(120, 140, 100, 30);
		txtDestination.setBackground(new Color(255, 255, 204));
		c.add(txtDestination);

		lblTakeOff = new JLabel("Take off:");
		lblTakeOff.setBounds(20, 180, 100, 30);
		c.add(lblTakeOff);

		txtTakeOff = new JTextField();
		txtTakeOff.setBounds(120, 180, 100, 30);
		txtTakeOff.setBackground(new Color(255, 255, 204));
		c.add(txtTakeOff);

		lblNoOfSeats = new JLabel("No of Seats:");
		lblNoOfSeats.setBounds(20, 220, 100, 30);
		c.add(lblNoOfSeats);

		txtNoOfSeats = new JTextField();
		txtNoOfSeats.setBackground(new Color(255, 255, 204));
		txtNoOfSeats.setBounds(120, 220, 100, 30);
		c.add(txtNoOfSeats);

		tblFlight = new JTable();
		JTableHeader tableHeader = tblFlight.getTableHeader();
		tableHeader.setBackground(new Color(51, 255, 255));
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		tblFlight.setModel(model);
		tblFlight.setBackground(new Color(204, 255, 255));

		spTable = new JScrollPane(tblFlight);
		spTable.setBounds(250, 60, 420, 220);
		c.add(spTable);

		btnInsert = new JButton("INSERT");
		btnInsert.setBounds(60, 290, 100, 30);
		btnInsert.setBackground(new Color(204, 255, 204));
		c.add(btnInsert);
		btnInsert.addActionListener(this);

		btnUpdate = new JButton("FETCH");
		btnUpdate.setBounds(180, 290, 100, 30);
		btnUpdate.setBackground(new Color(204, 255, 204));
		c.add(btnUpdate);
		btnUpdate.addActionListener(this);

		btnSearch = new JButton("REFRESH");
		btnSearch.setBounds(300, 290, 100, 30);
		btnSearch.setBackground(new Color(204, 255, 204));
		c.add(btnSearch);
		btnSearch.addActionListener(this);

		btnDelete = new JButton("DELETE");
		btnDelete.setBounds(420, 290, 100, 30);
		btnDelete.setBackground(new Color(204, 255, 204));
		c.add(btnDelete);
		btnDelete.addActionListener(this);

		btnBack = new JButton("BACK");
		btnBack.setBounds(540, 290, 100, 30);
		btnBack.setBackground(new Color(255, 204, 204));
		c.add(btnBack);
		btnBack.addActionListener(this);
		
		loadFlightTable();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Manage Flight");
		setSize(700, 370);
		setVisible(true);
		setLocationRelativeTo(null);
		

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnInsert) {
			if(isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Please Add All Details First !");
			}
			else
				insertFlight();
		} else if (e.getSource() == btnUpdate) {
			if(isUpdate)
			{
				updateFlight();
				isUpdate = false;
				btnUpdate.setLabel("FETCH");
			}
			else
			{
				fetchDetails();
				isUpdate = true;
				btnUpdate.setLabel("UPDATE");
			}
		} else if (e.getSource() == btnSearch) {
			searchFlight();
		} else if (e.getSource() == btnDelete) {
			deleteFlight();
		} else if (e.getSource() == btnBack) {
			new dashboard().setVisible(true);
			dispose();
		}
	}
	
	public boolean isEmpty() {
		if(
		txtFlightCode.getText().toString().isEmpty() ||
		txtFlightCode.getText().toString().isEmpty() ||
		txtFlightCode.getText().toString().isEmpty() ||
		txtFlightCode.getText().toString().isEmpty() ||
		txtFlightCode.getText().toString().isEmpty() 
		) {
			return true;
		}
		
		return false;
	}

	public void insertFlight() {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlineDB", "root", "Varad_2004");
			String sql = "INSERT INTO manageFlight (flightcode, source, destination, takeoff, noOfseat) VALUES (?, ?, ?, ?, ?)";
			pst = con.prepareStatement(sql);
			pst.setString(1, txtFlightCode.getText());
			pst.setString(2, txtSource.getText());
			pst.setString(3, txtDestination.getText());
			pst.setString(4, txtTakeOff.getText());
			pst.setString(5, txtNoOfSeats.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Flight Added Successfully!");
			clearFields();
			loadFlightTable();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	public void fetchDetails() {
		int selectedRowIndex = tblFlight.getSelectedRow();
		if (selectedRowIndex < 0) {
			JOptionPane.showMessageDialog(null, "Please select a row!");
			return;
		}
		String flightCode = model.getValueAt(selectedRowIndex, 0).toString();
		Connection con = null;
		PreparedStatement pst = null;
		String flightcode = model.getValueAt(selectedRowIndex, 0).toString();
		String src = model.getValueAt(selectedRowIndex, 1).toString();
		String des = model.getValueAt(selectedRowIndex, 2).toString();
		String takeoff = model.getValueAt(selectedRowIndex, 3).toString();
		String seats = model.getValueAt(selectedRowIndex, 4).toString();
		txtFlightCode.setText(flightcode);
		txtSource.setText(src);
		txtDestination.setText(des);
		txtTakeOff.setText(takeoff);
		txtNoOfSeats.setText(seats);
	}
	
	public void updateFlight() {
		int selectedRowIndex = tblFlight.getSelectedRow();
		if (selectedRowIndex < 0) {
			JOptionPane.showMessageDialog(null, "Please select a row to update!");
			return;
		}
		String flightCode = model.getValueAt(selectedRowIndex, 0).toString();
		Connection con = null;
		PreparedStatement pst = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlineDB", "root", "Varad_2004");
			String sql = "UPDATE manageFlight SET source=? ,destination=? , takeoff=? , noOfseat=? WHERE flightcode=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, txtSource.getText());
			pst.setString(2, txtDestination.getText());
			pst.setString(3, txtTakeOff.getText());
			pst.setString(4, txtNoOfSeats.getText());
			pst.setString(5, flightCode);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Flight Updated Successfully!");
			clearFields();
			loadFlightTable();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	public void searchFlight() {
		loadFlightTable();
	}

	public void deleteFlight() {
		int selectedRowIndex = tblFlight.getSelectedRow();
		if (selectedRowIndex < 0) {
			JOptionPane.showMessageDialog(null, "Please select a row to delete!");
			return;
		}
		String flightcode = model.getValueAt(selectedRowIndex, 0).toString();
		Connection con = null;
		PreparedStatement pst = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlineDB", "root", "Varad_2004");
			String sql = "DELETE FROM manageFlight WHERE flightcode=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, flightcode);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Flight Deleted Successfully!");
			clearFields();
			loadFlightTable();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	public void clearFields() {
		txtFlightCode.setText("");
		txtSource.setText("");
		txtDestination.setText("");
		txtTakeOff.setText("");
		txtNoOfSeats.setText("");
	}

	public void loadFlightTable() {
		model.setRowCount(0);
//            Connection con = null;
//            Statement st = null;
//            ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlineDB", "root", "Varad_2004");
			Statement st = con.createStatement();
			String sql = "SELECT * from manageflight";
			PreparedStatement ptst = con.prepareStatement(sql);
			ResultSet rs = ptst.executeQuery();
			DefaultTableModel dt = (DefaultTableModel) tblFlight.getModel();
			dt.setRowCount(0);
			while (rs.next()) {
				Object o[] = { rs.getString("flightcode"), rs.getString("source"), rs.getString("destination"),
						rs.getString("takeoff"), rs.getString("noOfseat") };
				dt.addRow(o);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
	}

	public static void main(String[] args) {
		new manageflight();
	}

}
