package Panel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Core.Customer;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;


public class Update extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	Connection connection=null;
	String rid;
	String s_d;
	String e_d;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	java.util.Date sd;
	java.util.Date ed;
	java.util.Date newsd;
	java.util.Date newed;
	SimpleDateFormat time;
	BigDecimal totalcost;
	String new_startdate;
	String new_enddate;
	String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
	java.util.Date timenow;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Update frame = new Update();
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
	public Update() {
		final JButton btnSubmit = new JButton("Submit");
		connection=SQLConnection.dbConnector();//connect
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 500,550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Reservation ID");
		lblNewLabel.setBounds(70, 38, 102, 16);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(184, 32, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		final JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(131, 98, 102, 16);
		contentPane.add(lblNewLabel_2);

		final JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(360, 98, 134, 16);
		contentPane.add(lblNewLabel_3);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				rid=textField.getText();
				try{
					String query ="select StartDate, EndDate from Reservation where ReservationID="+"'"+rid+"'"+" and IsCancelled='0' and Username="+"'"+Customer.user+"'";

					PreparedStatement pst=connection.prepareStatement(query);				
					ResultSet rs=pst.executeQuery();//result will be transfer to rs

					int count=0;
					while(rs.next()){
						count=count+1;
					}
					if (count==0){
						JOptionPane.showMessageDialog(null, "Wrong Reservation ID!");
						lblNewLabel_2.setText("");
						lblNewLabel_3.setText("");
					}else{
						ResultSet rs1=pst.executeQuery();//result will be transfer to rs
						while (rs1.next()){
							s_d=rs1.getObject(1).toString();
							e_d=rs1.getObject(2).toString();


						}
						rs1.close();

						SimpleDateFormat time= new SimpleDateFormat("yyyy-MM-dd");

						sd=time.parse(s_d);
						ed=time.parse(e_d);

						time.applyPattern("MM/dd/yyyy");
						String sd_change=time.format(sd);
						String ed_change=time.format(ed);
						timenow=time.parse(timeStamp);
						if(sd.before(timenow)){
							JOptionPane.showMessageDialog(null, "you can't update because you pass the start date!");
							btnSubmit.setEnabled(false);
						}else{
							btnSubmit.setEnabled(true);
						}


						lblNewLabel_2.setText(sd_change);
						lblNewLabel_3.setText(ed_change);


					}

					rs.close();
					pst.close();

				}catch(Exception e){
					e.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(342, 33, 117, 29);
		contentPane.add(btnNewButton);

		JScrollPane scroll= new JScrollPane();
		scroll.setBounds(24, 249, 453, 184);
		getContentPane().add(scroll);
		table = new JTable();
		scroll.setViewportView(table);

		final DefaultTableModel model;
		model=new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column){//OVERRIDE GETCOLUMNCLASS
				switch(column){
				case 0:
					return Integer.class;//rn
				case 1:
					return String.class;////rc
				case 2:
					return String.class;//#pa
				case 3:
					return Integer.class;//cpd
				case 4:
					return Integer.class;//cebpd
				case 5:
					return Boolean.class;//select

				default:
					return String.class;	
				}
			}
		};

		table.setModel(model);
		model.addColumn("Room Number");
		model.addColumn("Room Category");
		model.addColumn("# People Allowed");
		model.addColumn("Cost per Day");
		model.addColumn("Cost of Extra Bed per Day");
		model.addColumn("Select extra bed");

		JLabel lblNewLabel_1 = new JLabel("Current Start Date");
		lblNewLabel_1.setBounds(6, 98, 123, 16);
		contentPane.add(lblNewLabel_1);



		JLabel lblCurrentEndDate = new JLabel("Current End Date");
		lblCurrentEndDate.setBounds(246, 98, 107, 16);
		contentPane.add(lblCurrentEndDate);

		JLabel lblNewLabel_4 = new JLabel("New Start Date");
		lblNewLabel_4.setBounds(6, 160, 102, 16);
		contentPane.add(lblNewLabel_4);

		textField_1 = new JTextField();
		textField_1.setBounds(111, 154, 134, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewEndDate = new JLabel("New End Date");
		lblNewEndDate.setBounds(257, 160, 96, 16);
		contentPane.add(lblNewEndDate);

		textField_2 = new JTextField();
		textField_2.setBounds(360, 154, 134, 28);
		contentPane.add(textField_2);
		textField_2.setColumns(10);


		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try{
					String query_submit="update Reservation set StartDate="+"'"+new_startdate+"'"+", EndDate="+"'"+new_enddate+"'"+", TotalCost= "+"'"+totalcost.toString()+"'"+" where ReservationID="+"'"+rid+"'";
					PreparedStatement pst_submit = connection.prepareStatement(query_submit);
					pst_submit.executeUpdate();

					pst_submit.close();
					JOptionPane.showMessageDialog(null, "Update success");

				}catch(Exception e){
					e.printStackTrace();
				}


			}
		});
		btnSubmit.setBounds(360, 472, 117, 29);
		contentPane.add(btnSubmit);

		final JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setBounds(172, 477, 123, 16);
		contentPane.add(lblNewLabel_6);

		JButton btnSearchAvailability = new JButton("Search Availability");
		btnSearchAvailability.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String new_sd=textField_1.getText();
				String new_ed=textField_2.getText();
				time= new SimpleDateFormat("MM/dd/yyyy");
				totalcost=new BigDecimal(0);
				try {
					newsd=time.parse(new_sd);
					newed=time.parse(new_ed);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				time.applyPattern("yyyy-MM-dd");
				String sd_new=time.format(newsd);
				String ed_new=time.format(newed);
				String sd_old=time.format(sd);
				String ed_old=time.format(ed);

				int count=0;
				if(newsd.before(sd)){
					String query ="select distinct joinedtable.ReservationID,joinedtable.StartDate,joinedtable.EndDate from ((select ReservationID,StartDate,EndDate from Reservation  NATURAL JOIN ReservationHasRoom where ReservationHasRoom.Location in (select location from ReservationHasRoom where ReservationID="+"'"+rid+"'"+") and Reservation.ReservationID not in (select ReservationID from Reservation where (StartDate>"+"'"+sd_old+"'"+" or EndDate<"+"'"+sd_new+"'"+")) ) )as joinedtable";

					try{
						PreparedStatement pst=connection.prepareStatement(query);				
						ResultSet rs=pst.executeQuery();//result will be transfer to rs

						while(rs.next()){
							count=count+1;
						}
						rs.close();
						pst.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				if(newed.after(ed)){
					String query ="select distinct joinedtable.ReservationID,joinedtable.StartDate,joinedtable.EndDate from ((select ReservationID,StartDate,EndDate from Reservation  NATURAL JOIN ReservationHasRoom where Reservation.IsCancelled='0' and ReservationHasRoom.Location in (select location from ReservationHasRoom where ReservationID="+"'"+rid+"'"+") and Reservation.ReservationID not in (select ReservationID from Reservation where (StartDate>"+"'"+ed_new+"'"+" or EndDate<"+"'"+ed_old+"'"+")) ) )as joinedtable where joinedtable.ReservationID !="+"'"+rid+"'"+" ";

					try{
						PreparedStatement pst=connection.prepareStatement(query);				
						ResultSet rs=pst.executeQuery();//result will be transfer to rs

						while(rs.next()){
							count=count+1;
						}
						rs.close();
						pst.close();
					}catch(Exception e){
						e.printStackTrace();
					}	
				}

				if (count!=0){
					JOptionPane.showMessageDialog(null, "Room not available in chosen time! Go to the cancel page!");
					btnSubmit.setEnabled(false);
				}else{
					btnSubmit.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Rooms are available. Please confirm details below before submitting");
					model.setRowCount(0);
					try{
						String query_search="select Room.RoomNum,Room.RoomCategory, Room.PeopleNum, Room.CostperDay,Room.CostofExtrabedperday, ReservationHasRoom.IncludeExtraBed from ReservationHasRoom NATURAL JOIN Room where ReservationHasRoom.ReservationID="+"'"+rid+"'";
						PreparedStatement pst_search = connection.prepareStatement(query_search);
						ResultSet rs_search = pst_search.executeQuery();
						//3.get rs!
						ResultSetMetaData rs_searchmd = rs_search.getMetaData();

						//4. put in to jtable(actually put in to model)
						while (rs_search.next()) {
							Vector data = new Vector();
							for (int col = 0; col < rs_searchmd.getColumnCount(); col++) {//i dont know why this shoulb be meta data
								if(col==rs_searchmd.getColumnCount()-1){
									if (rs_search.getObject(col + 1).equals(false)){
										data.add(Boolean.FALSE);
									}else{
										data.add(Boolean.TRUE);
									}
								}else{
									data.add(rs_search.getObject(col + 1));
								}				        						            
							}

							model.addRow(data);
						}
						pst_search.close();
						rs_search.close();

					}catch(Exception e){
						e.printStackTrace();
					}

					//calculate totalcost
					for (int i = 0; i < model.getRowCount(); i++) {
						boolean isChecked = (Boolean) model.getValueAt(i, 5);

						BigDecimal cpd=(BigDecimal) model.getValueAt(i,3);
						totalcost=totalcost.add(cpd);

						if (isChecked) {
							BigDecimal cebpd=(BigDecimal)model.getValueAt(i,4);			    	
							totalcost=totalcost.add(cebpd);    	 
						}

					}//end for
					String nsd=textField_1.getText();
					String ned=textField_2.getText();



					try{
						SimpleDateFormat time= new SimpleDateFormat("MM/dd/yyyy");
						java.util.Date nsdnsd=time.parse(nsd);
						java.util.Date nedned=time.parse(ned);

						long d1=time.parse(nsd).getTime();
						long d2=time.parse(ned).getTime();


						time.applyPattern("yyyy-MM-dd");
						new_startdate=time.format(nsdnsd);
						new_enddate=time.format(nedned);		




						long minus=(d2-d1)/(1000*60*60*24);
						BigDecimal d = new BigDecimal(minus);
						totalcost=totalcost.multiply(d);

						lblNewLabel_6.setText(totalcost.toString());

					}catch(Exception e){
						e.printStackTrace();
					}

				}//end else






			}
		});
		btnSearchAvailability.setBounds(184, 208, 167, 29);
		contentPane.add(btnSearchAvailability);



		JLabel lblNewLabel_5 = new JLabel("Total Cost Update");
		lblNewLabel_5.setBounds(37, 477, 117, 16);
		contentPane.add(lblNewLabel_5);




		//		try {
		//			connection.close();
		//		} catch (SQLException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

	}
}
