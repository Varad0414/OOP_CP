package airlinemanagement;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class loginpage extends JFrame implements ActionListener {
	JLabel headingLabel, userLabel, passwordLabel, imageLabel;
	JTextField userTextField;
	JPasswordField passwordField;
	JButton submitButton;

	public loginpage() {
		getContentPane().setBackground(new Color(255, 185, 185));
		setLayout(null);
		setPreferredSize(new Dimension(400, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel headingLabel = new JLabel("Welcome to Arico Airlines!");
		headingLabel.setFont(new Font("Arial", Font.BOLD, 20));

		headingLabel.setBounds(70, 30, 300, 30);
		add(headingLabel);

		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/airlinemanagement/Arico Airlines.png"));
		imageLabel = new JLabel(imageIcon);
		imageLabel.setBounds(100, 250, 200, 100);
		add(imageLabel);

		userLabel = new JLabel("Username:");
		userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		userLabel.setBounds(50, 100, 100, 30);
		add(userLabel);

		userTextField = new JTextField();
		userTextField.setBackground(new Color(250, 229, 245));
		userTextField.setFont(new Font("Arial", Font.PLAIN, 16));
		userTextField.setBounds(160, 100, 200, 30);
		add(userTextField);

		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordLabel.setBounds(50, 150, 100, 30);
		add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(250, 229, 245));
		passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordField.setBounds(160, 150, 200, 30);
		add(passwordField);

		submitButton = new JButton("Submit");
		submitButton.setBackground(new Color(201, 245, 198));
		submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
		submitButton.setBounds(160, 200, 100, 30);
		add(submitButton);
		submitButton.addActionListener(this);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String Username = userTextField.getText();
				String Password = new String(passwordField.getPassword());
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlineDB", "root",
						"Varad_2004");
				Statement st = con.createStatement();
				String sql = "SELECT * FROM login WHERE username='" + Username + "' AND password='" + Password + "'";
				ResultSet rs = st.executeQuery(sql);
				if (rs.next()) {
					new dashboard().setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Username or Password is incorrect");
				}
				con.close();
			} catch (Exception ex) {
				System.out.println("Exception: " + ex);
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new loginpage();
	}
}
