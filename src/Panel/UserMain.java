package Panel;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Core.Customer;

public class UserMain extends JFrame {

	private JPanel contentPane;
	public static int review_number=300;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMain frame = new UserMain();
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
	public UserMain() {
		String s = Customer.user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400,400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel label = new JLabel("Welcome,");
		label.setBounds(45, 40, 61, 16);
		contentPane.add(label);


		JLabel label_1 = new JLabel(s);
		label_1.setBounds(111, 40, 61, 16);
		contentPane.add(label_1);

		JButton button = new JButton("Make a new reservation");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchRoom searchroom=new SearchRoom();
				searchroom.setVisible(true);
			}
		});
		button.setBounds(116, 104, 184, 29);
		contentPane.add(button);

		JButton button_1 = new JButton("Update your reservation");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update updatereservation=new Update();
				updatereservation.setVisible(true);
			}
		});
		button_1.setBounds(116, 145, 184, 29);
		contentPane.add(button_1);

		JButton button_2 = new JButton("Cancel Reservation");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CancelReservation cancelreservation = new CancelReservation();
				cancelreservation.setVisible(true);
			}
		});
		button_2.setBounds(116, 187, 184, 29);
		contentPane.add(button_2);

		JButton button_3 = new JButton("Provide feedback");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//there is some thing more (number)
				GiveReview givereview=new GiveReview();
				givereview.setVisible(true);
				review_number++;
			}
		});
		button_3.setBounds(116, 228, 184, 29);
		contentPane.add(button_3);

		JButton button_4 = new JButton("View feedback");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewReview viewreview=new ViewReview();
				viewreview.setVisible(true);
			}
		});
		button_4.setBounds(116, 269, 184, 29);
		contentPane.add(button_4);
	}

}
