package Panel;
import java.sql.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class New_user extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					New_user frame = new New_user();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */


	public New_user() {


		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try{

					if (passwordField.getText().length()==0&&passwordField_1.getText().length()==0){
						JOptionPane.showMessageDialog(null, "Passwords is empty!");
					}else if (passwordField.getText().equals(passwordField_1.getText())){
						Connection connection = null;//
						connection=SQLConnection.dbConnector();//
						String query ="insert into Customer values (?,?,?)";
						PreparedStatement pst=connection.prepareStatement(query);

						pst.setString(1, textField.getText());//get text(1,2 is for the first and second ?)
						pst.setString(2, passwordField.getText());//get text
						pst.setString(3, textField_1.getText());//get text

						pst.executeUpdate();//result will be transfer to rs

						JOptionPane.showMessageDialog(null, "Create Success");
						connection.close();
						dispose();



					}else{
						JOptionPane.showMessageDialog(null, "Passwords don't match!");
					}


				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Something went wrong!");
				}

			}


		});
		btnNewButton.setBounds(137, 314, 117, 29);
		contentPane.add(btnNewButton);

		JLabel label = new JLabel("Username");
		label.setBounds(45, 86, 90, 16);
		contentPane.add(label);

		JLabel label_1 = new JLabel("Password");
		label_1.setBounds(45, 133, 61, 16);
		contentPane.add(label_1);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(176, 80, 134, 28);
		contentPane.add(textField);

		passwordField = new JPasswordField();
		passwordField.setBounds(176, 127, 134, 28);
		contentPane.add(passwordField);

		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(45, 178, 117, 16);
		contentPane.add(lblConfirmPassword);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(45, 228, 61, 16);
		contentPane.add(lblEmail);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(176, 172, 134, 28);
		contentPane.add(passwordField_1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(176, 222, 134, 28);
		contentPane.add(textField_1);
	}

}
