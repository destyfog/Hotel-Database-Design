package Panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class RevenueReport extends JFrame {

	Connection connection=null;//Initialize connection

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RevenueReport frame = new RevenueReport();
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
	public RevenueReport() {
		connection=SQLConnection.dbConnector();//connect
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//1.scroll
		JScrollPane scroll= new JScrollPane();
		scroll.setBounds(41, 25, 403, 547);
		getContentPane().add(scroll);
		table = new JTable();
		scroll.setViewportView(table);

		//2.model
		final DefaultTableModel model;
		model=new DefaultTableModel(){
			//private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column){//OVERRIDE GETCOLUMNCLASS
				switch(column){
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return Integer.class;

				default:
					return String.class;	
				}
			}
		};


		//end model
		table.setModel(model);
		model.addColumn("Month");
		model.addColumn("Location");
		model.addColumn("Total revenue");


		try{
			model.setRowCount(0);
			String query_revenue="(select new.sd, new.loc,SUM(new.tc) from (select distinct Reservation.StartDate as sd, Reservation.TotalCost as tc, Reservation.ReservationID, ReservationHasRoom.Location as loc from Reservation NATURAL JOIN ReservationHasRoom WHERE Reservation.StartDate LIKE '2015-08-__' ) as new group by loc) union all (select new.sd, new.loc,SUM(new.tc) from (select distinct Reservation.StartDate as sd, Reservation.TotalCost as tc, Reservation.ReservationID, ReservationHasRoom.Location as loc from Reservation NATURAL JOIN ReservationHasRoom WHERE Reservation.StartDate LIKE  '2015-09-__') as new group by loc)";
			PreparedStatement pst_revenue = connection.prepareStatement(query_revenue);
			ResultSet rs_revenue = pst_revenue.executeQuery();
			ResultSetMetaData rsmd = rs_revenue.getMetaData();

			while (rs_revenue.next()) {
				Vector data = new Vector();
				for (int col = 0; col < rsmd.getColumnCount(); col++) {
					if(col==0){
						if(rs_revenue.getObject(col + 1).toString().contains("2015-08")){
							data.add("August");

						}else{
							data.add("September");
						}
					}else{
						data.add(rs_revenue.getObject(col + 1));
					}


				}

				model.addRow(data);
			}

		}catch(Exception e){
			e.printStackTrace();
		}




	}

}
