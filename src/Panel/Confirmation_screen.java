package Panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Core.Customer;

import javax.swing.JLabel;

public class Confirmation_screen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Confirmation_screen frame = new Confirmation_screen();
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
	public Confirmation_screen() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblYourReservationId = new JLabel("Your Reservation ID");
		lblYourReservationId.setBounds(92, 91, 153, 16);
		contentPane.add(lblYourReservationId);
		String r_id=String.valueOf(MakeReservation.reservationID);
		JLabel lblNewLabel = new JLabel(r_id);

		lblNewLabel.setBounds(257, 91, 124, 16);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Please save this reservation id for all further communication.");
		lblNewLabel_1.setBounds(30, 161, 401, 16);
		contentPane.add(lblNewLabel_1);
	}
}
