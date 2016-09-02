package Panel;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchRoom extends JFrame {
	public static String place;//variable that need to pass
	public static Date ds;//variable that need to pass
	public static Date de;//variable that need to pass
	public static String timestart;//variable that need to pass
	public static String timeend;//variable that need to pass

	String[] places={"Atlanta","Charlotte","Savannah","Orlando","Miami"};


	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchRoom frame = new SearchRoom();
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
	public SearchRoom() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Location");
		lblNewLabel.setBounds(103, 61, 61, 16);
		contentPane.add(lblNewLabel);


		final JComboBox comboBox = new JComboBox(places);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				place=(String)comboBox.getSelectedItem();//get selected item

			}
		});
		place=(String)comboBox.getSelectedItem();//get selected item
		comboBox.setBounds(221, 57, 124, 27);
		contentPane.add(comboBox);

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(103, 108, 85, 16);
		contentPane.add(lblStartDate);

		textField = new JTextField();
		textField.setBounds(221, 96, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(221, 140, 134, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(103, 146, 61, 16);
		contentPane.add(lblEndDate);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				timestart=textField.getText();
				timeend=textField_1.getText();
				SimpleDateFormat time= new SimpleDateFormat("MM/dd/yyyy");
				try {
					Date ds=time.parse(timestart);
					Date de=time.parse(timeend);
					Date d1= time.parse("7/31/2015");
					Date d2 = time.parse("2/1/2016");
					if (de.after(ds) && ds.after(d1) && de.before(d2)){
						//						System.out.print(place);
						//						System.out.print(ds);
						//						System.out.print(de);
						dispose();
						MakeReservation makereservation=new MakeReservation();
						makereservation.setVisible(true);
					}else{
						JOptionPane.showMessageDialog(null, "Wrong date!");
					}

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Wrong date!");
				}



			}
		});
		btnNewButton.setBounds(162, 230, 117, 29);
		contentPane.add(btnNewButton);

		JLabel lblTimePeriodFrom = new JLabel("Attention: time period from 1st August 2015 to 31st January 2016");
		lblTimePeriodFrom.setBounds(27, 194, 417, 16);
		contentPane.add(lblTimePeriodFrom);
	}
}
