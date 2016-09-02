package Panel;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

import Core.Customer;//have to import this


public class Login {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	Connection connection = null;//******
	private JTextField textField_1;
	private JPasswordField passwordField;

	public Login() {//constructor,call
		initialize();
		connection=SQLConnection.dbConnector();
		JOptionPane.showMessageDialog(null, "WE MADE A CONNECTION!");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	//----------------------------------------------------------------in order to create a customer instance
	private Customer convertRowToCustomer(ResultSet myRs) throws SQLException {

		String Username = myRs.getString("Username");
		String Password = myRs.getString("Password");
		String email = myRs.getString("email");

		Customer logincustomer = new Customer(Username,Password, email);

		return logincustomer;
	}
	//--------------------------------------------------------------
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				if (textField_1.getText().contains("C_")){
					try{
						String query ="select * from Customer where Username=? and Password=?";
						PreparedStatement pst=connection.prepareStatement(query);

						pst.setString(1, textField_1.getText());//get text(1,2 is for the first and second ?)
						pst.setString(2, passwordField.getText());//get text

						ResultSet rs=pst.executeQuery();//result will be transfer to rs

						int count=0;
						while(rs.next()){//give the result one by one
							Customer logincustomer = convertRowToCustomer(rs);//create customer instance
							//String user=logincustomer.getUsername();//****We have to pass this Username to other methods

							logincustomer.user=logincustomer.getUsername();
							//System.out.print(logincustomer.user);
							count=count+1;


						}
						if(count==0){
							JOptionPane.showMessageDialog(null, "Username or Password is not correct!");
						}
						if(count==1){
							//JOptionPane.showMessageDialog(null, "correct!");
							frame.dispose();


							UserMain usermain=new UserMain();
							usermain.setVisible(true);


						}

						rs.close();//must close to use 
						pst.close();//another connection in other method

					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);

					}
				}else{
					try{
						String query ="select * from Management where Username=? and Password=?";
						PreparedStatement pst=connection.prepareStatement(query);

						pst.setString(1, textField_1.getText());//get text(1,2 is for the first and second ?)
						pst.setString(2, passwordField.getText());//get text

						ResultSet rs=pst.executeQuery();//result will be transfer to rs

						int count=0;
						while(rs.next()){//give the result one by one

							count=count+1;

						}
						if(count==0){
							JOptionPane.showMessageDialog(null, "Username or Password is not correct!");
						}
						if(count==1){
							//JOptionPane.showMessageDialog(null, "correct!");
							frame.dispose();

							ManagerMain managermain=new ManagerMain();
							managermain.setVisible(true);
							rs.close();//must close to use 
							pst.close();//another connection in other method


						}}catch(Exception e){
							JOptionPane.showMessageDialog(null, e);

						}


				}


			}
		});
		btnNewButton.setBounds(138, 271, 117, 29);
		frame.getContentPane().add(btnNewButton);

		textField_1 = new JTextField();
		textField_1.setBounds(186, 100, 134, 28);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(84, 106, 90, 16);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(84, 160, 61, 16);
		frame.getContentPane().add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(186, 154, 134, 28);
		frame.getContentPane().add(passwordField);

		JButton btnNewUser = new JButton("New User?");
		btnNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//go to the new user page 
				New_user newuser=new New_user();
				newuser.setVisible(true);
				//frame.setVisible(false);
			}
		});
		btnNewUser.setBounds(267, 271, 117, 29);
		frame.getContentPane().add(btnNewUser);
	}
}
