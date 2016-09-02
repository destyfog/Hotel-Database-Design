package Panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManagerMain extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerMain frame = new ManagerMain();
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
	public ManagerMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton_5 = new JButton("View reservation report");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReservationReport reservationreport=new ReservationReport();
				reservationreport.setVisible(true);
			}
		});
		btnNewButton_5.setBounds(99, 80, 176, 29);
		contentPane.add(btnNewButton_5);

		JButton btnNewButton_6 = new JButton("View category popular room categary");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopularRoomCategory popularroomcategory=new PopularRoomCategory();
				popularroomcategory.setVisible(true);
			}
		});
		btnNewButton_6.setBounds(99, 148, 258, 29);
		contentPane.add(btnNewButton_6);

		JButton btnNewButton_7 = new JButton("View revenue report");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RevenueReport revenuereport=new RevenueReport();
				revenuereport.setVisible(true);
			}
		});
		btnNewButton_7.setBounds(100, 216, 165, 29);
		contentPane.add(btnNewButton_7);
	}

}
