package Panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Core.Customer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PaymentInformation extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	Connection connection =SQLConnection.dbConnector();//

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentInformation frame = new PaymentInformation();
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
	public PaymentInformation() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);




		JLabel lblNewLabel = new JLabel("Add Card");
		lblNewLabel.setBounds(52, 25, 77, 16);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Name on Card");
		lblNewLabel_1.setBounds(16, 64, 97, 16);
		contentPane.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(116, 58, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Card Number");
		lblNewLabel_2.setBounds(16, 103, 97, 16);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Expiration Date");
		lblNewLabel_3.setBounds(16, 144, 97, 16);
		contentPane.add(lblNewLabel_3);

		JLabel lblCvv = new JLabel("CVV");
		lblCvv.setBounds(16, 188, 61, 16);
		contentPane.add(lblCvv);

		textField_1 = new JTextField();
		textField_1.setBounds(116, 97, 134, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(116, 138, 134, 28);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(116, 176, 134, 28);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Delete Card");
		lblNewLabel_4.setBounds(333, 25, 77, 16);
		contentPane.add(lblNewLabel_4);

		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(296, 99, 148, 27);
		contentPane.add(comboBox);
		DefaultComboBoxModel modelcombobox = new DefaultComboBoxModel(MakeReservation.cardnums.toArray());
		comboBox.setModel(modelcombobox);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String addcard="insert into Payment_information values (?,?,?,?,?)";
				try {
					PreparedStatement pst_addcard=connection.prepareStatement(addcard);
					pst_addcard.setString(1, textField_1.getText());//cardnum
					pst_addcard.setString(2, textField.getText());//name

					String expd=textField_2.getText();
					SimpleDateFormat time= new SimpleDateFormat("MM/dd/yyyy");
					java.util.Date expdate=time.parse(expd);
					time.applyPattern("yyyy-MM-dd");
					String expdate_str=time.format(expdate);



					pst_addcard.setString(3, expdate_str);//expdate
					pst_addcard.setString(4, textField_3.getText());//cvv
					pst_addcard.setString(5, Customer.user);
					pst_addcard.executeUpdate();
					pst_addcard.close();

					MakeReservation.cardnums.add(textField_1.getText());
					DefaultComboBoxModel modelcombobox = new DefaultComboBoxModel(MakeReservation.cardnums.toArray());
					comboBox.setModel(modelcombobox);

					MakeReservation.comboBox_pass.setModel(modelcombobox);

					JOptionPane.showMessageDialog(null, "add success");

				} catch (SQLException | ParseException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}


			}
		});

		btnNewButton.setBounds(52, 216, 117, 29);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//After ‘Submit’ from Figure 5 and Figure 6, the customer should be taken to the Payment screen. If he doesn’t have Credit Card details already stored then he should be asked to save that information first and then pay using the stored card. A resident can add multiple cards in the system. He can also delete information about cards from the system. Remember he cannot make a reservation unless he has added information about at least one card. And he can use only one card for transactions for a particular reservation. Also, he cannot delete a card if it is being used in a transaction which hasn’t ended yet (i.e. the end-date has not passed yet).
				String delete_card="delete from Payment_information where CardNum="+"'"+comboBox.getSelectedItem()+"'";
				String check="SELECT * FROM Reservation WHERE IsCancelled=0 and StartDate<curdate() and EndDate>curdate() and CardNum="+"'"+comboBox.getSelectedItem()+"'";
				try {
					PreparedStatement pst_check=connection.prepareStatement(check);
					ResultSet rs= pst_check.executeQuery();
					int count=0;
					while(rs.next()){//give the result one by one		
						count=count+1;
					}
					if(count==1){
						JOptionPane.showMessageDialog(null, "Card in use!");
					}else{
						PreparedStatement pst_delete=connection.prepareStatement(delete_card);
						pst_delete.executeUpdate();
						pst_delete.close();

						MakeReservation.cardnums.remove(comboBox.getSelectedItem());
						DefaultComboBoxModel modelcombobox = new DefaultComboBoxModel(MakeReservation.cardnums.toArray());
						comboBox.setModel(modelcombobox);

						MakeReservation.comboBox_pass.setModel(modelcombobox);
						JOptionPane.showMessageDialog(null, "delete success");
					}



				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}


			}
		});
		btnNewButton_1.setBounds(309, 216, 117, 29);
		contentPane.add(btnNewButton_1);

		JLabel lblCardNumber = new JLabel("Card Number");
		lblCardNumber.setBounds(333, 82, 97, 16);
		contentPane.add(lblCardNumber);


		//		try {
		//			connection.close();
		//		} catch (SQLException e1) {
		//			// TODO Auto-generated catch block
		//			e1.printStackTrace();
		//		}
	}
}
