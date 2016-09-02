package Panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class ViewReview extends JFrame {

	public static String view_place;

	private JPanel contentPane;
	private JTable table;
	Connection connection = null;//
	/**
	 * Launch the application.
	 */
	String[] places={"Atlanta","Charlotte","Savannah","Orlando","Miami"};

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewReview frame = new ViewReview();
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
	public ViewReview() {
		connection=SQLConnection.dbConnector();//

		setTitle("View Review"); 
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JComboBox comboBox = new JComboBox(places);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view_place=(String)comboBox.getSelectedItem();//get selected item
			}
		});
		view_place=(String)comboBox.getSelectedItem();
		//view_place=(String)comboBox.getSelectedItem();//get selected item
		comboBox.setBounds(177, 30, 132, 21);
		contentPane.add(comboBox);


		//********************J table*************************
		//1.scroll
		JScrollPane scroll= new JScrollPane();
		scroll.setBounds(54, 128, 365, 171);
		getContentPane().add(scroll);
		table = new JTable();
		scroll.setViewportView(table);

		//2.model
		final DefaultTableModel model;
		model=new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column){//OVERRIDE GETCOLUMNCLASS
				switch(column){
				case 0:
					return String.class;//Rating
				case 1:
					return String.class;//Comment
				default:
					return String.class;	
				}
			}
		};
		table.setModel(model);
		model.addColumn("Rating");
		model.addColumn("Comment");

		JButton btnCheckReviews = new JButton("Check Reviews");
		btnCheckReviews.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {

				try{
					model.setRowCount(0);


					String query="select Rating,Comment	from Hotel_Review where Location="+"'"+view_place+"'";
					//System.out.print(view_place);

					PreparedStatement pst=connection.prepareStatement(query);
					ResultSet rs=pst.executeQuery();

					ResultSetMetaData rs_MetaData=rs.getMetaData();

					while(rs.next()){
						Vector data=new Vector();
						for (int col = 0; col < rs_MetaData.getColumnCount(); col++) {

							data.add(rs.getObject(col + 1));

						}

						model.addRow(data);
					}
					pst.close();
					rs.close();


				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});

		btnCheckReviews.setBounds(266, 82, 132, 23);
		contentPane.add(btnCheckReviews);
	}
}
