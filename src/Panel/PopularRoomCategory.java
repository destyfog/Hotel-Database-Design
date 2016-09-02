package Panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;


public class PopularRoomCategory extends JFrame {
	Connection connection=null;

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopularRoomCategory frame = new PopularRoomCategory();
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
	public PopularRoomCategory() {
		connection=SQLConnection.dbConnector();//connect
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600,500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//1.scroll
		JScrollPane scroll= new JScrollPane();
		scroll.setBounds(52, 46, 475, 374);
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
					return String.class;//rn
				case 1:
					return String.class;////rc
				case 2:
					return String.class;//#pa
				case 3:
					return Integer.class;//cpd

				default:
					return String.class;	
				}
			}
		};

		//end model
		table.setModel(model);
		model.addColumn("Month");
		model.addColumn("Location");
		model.addColumn("top Room Category");
		model.addColumn("Total number of reservation for room category");

		try{
			//model.setRowCount(0);//clear the model!!!!!!!!!!!!!!!
			String query="select Location, RoomCategory, max(cnt) from (select Location, RoomCategory ,count(*) AS cnt from (Reservation NATURAL join ReservationHasRoom) NATURAL join Room where StartDate like '2015-08-__' group by Location, RoomCategory ) as join1 where Location='Atlanta' union all select Location, RoomCategory, max(cnt) from (select Location, RoomCategory ,count(*) AS cnt from (Reservation NATURAL join ReservationHasRoom) NATURAL join Room where StartDate like '2015-08-__' group by Location, RoomCategory ) as join1 where Location='Charlotte' union all select Location, RoomCategory, max(cnt) from (select Location, RoomCategory ,count(*) AS cnt from (Reservation NATURAL join ReservationHasRoom) NATURAL join Room where StartDate like '2015-08-__' group by Location, RoomCategory ) as join1 where Location='Orlando' union all select Location, RoomCategory, max(cnt) from (select Location, RoomCategory ,count(*) AS cnt from (Reservation NATURAL join ReservationHasRoom) NATURAL join Room where StartDate like '2015-08-__' group by Location, RoomCategory ) as join1 where Location='Miami' union all select Location, RoomCategory, max(cnt) from (select Location, RoomCategory ,count(*) AS cnt from (Reservation NATURAL join ReservationHasRoom) NATURAL join Room where StartDate like '2015-08-__' group by Location, RoomCategory ) as join1 where Location='Savannah'";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();


			while(rs.next()){
				Vector data = new Vector();
				data.add("August");
				for (int col = 0; col < rsmd.getColumnCount(); col++) {

					data.add(rs.getObject(col + 1));

				}

				model.addRow(data);
			}
		}catch(Exception e){
			e.printStackTrace();
		}



	}

}
