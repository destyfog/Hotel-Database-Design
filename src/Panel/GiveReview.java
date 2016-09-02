package Panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Core.*;
import Panel.*;
/*************************
 * Define strings "ratings","locations"
 * Define new variable
 * @author zhhyu
 *************************/

public class GiveReview extends JFrame {
	public static String rating;
	//private String user_name;
	//private int review_number;
	private String location;
	private JPanel contentPane;
	//private JTable table;
	private JTextField comment;

	String[] ratings={"Excellent", "Good", "Bad", "Very Bad", "Neutral"};
	String[] locations={"Atlanta","Charlotte","Savannah","Orlando","Miami"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GiveReview frame = new GiveReview();
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
	public GiveReview() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle("Give Review"); 

		//Location Menu
		final JComboBox comboBox = new JComboBox(locations);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				location=(String)comboBox.getSelectedItem();//get needed location city: "location"
			}
		});
		location=(String)comboBox.getSelectedItem();
		comboBox.setBounds(207, 54, 123, 23);
		contentPane.add(comboBox);

		JLabel label = new JLabel("Hotel Location");
		label.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		label.setBounds(63, 49, 104, 32);
		contentPane.add(label);

		//Input comment
		comment = new JTextField();
		comment.setColumns(10);
		comment.setBounds(207, 177, 264, 32);
		contentPane.add(comment);

		//Rating Menu
		final JComboBox comboBox_1 = new JComboBox(ratings);
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rating=(String)comboBox_1.getSelectedItem();//get needed: "rating"
			}
		});
		rating=(String)comboBox_1.getSelectedItem();
		comboBox_1.setBounds(207, 115, 123, 23);
		contentPane.add(comboBox_1);

		JLabel label_1 = new JLabel("Rating");
		label_1.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		label_1.setBounds(108, 110, 104, 32);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("Comment");
		label_2.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		label_2.setBounds(83, 168, 104, 32);
		contentPane.add(label_2);

		//Insert review
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				try{
					Connection connection = null;//
					connection=SQLConnection.dbConnector();//
					String query ="insert into Hotel_Review values (?,?,?,?,?)";
					PreparedStatement pst=connection.prepareStatement(query);

					//user
					pst.setString(1,Integer.toString(UserMain.review_number));
					pst.setString(2,location);  
					pst.setString(3,comment.getText());//
					pst.setString(4,rating);//
					pst.setString(5,Customer.user);
					//pst.setString(5,Customer.user);//

					pst.executeUpdate();//result will be transfer to rs

					//JOptionPane.showMessageDialog(null, "Create Success");
					connection.close();
					dispose();	
					JOptionPane.showMessageDialog(null, "submit success");

				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});
		btnNewButton.setBounds(356, 238, 93, 23);
		contentPane.add(btnNewButton);
	}
}
