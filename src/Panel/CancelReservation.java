package Panel;

import java.awt.BorderLayout;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Core.Customer;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.event.ActionEvent;

public class CancelReservation extends JFrame {
	Connection connection=null;//Initialize connection


	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	String s_date;
	BigDecimal refund_rate;
	String rid;
	String s_d;
	String e_d;
	java.util.Date sd;
	java.util.Date ed;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CancelReservation frame = new CancelReservation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//------------variables 
	BigDecimal totalcost;
	String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
	//============
	/**
	 * Create the frame.
	 */
	public CancelReservation() {
		connection=SQLConnection.dbConnector();//connect

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblReservationId = new JLabel("Reservation ID");
		lblReservationId.setBounds(76, 44, 108, 16);
		contentPane.add(lblReservationId);

		textField = new JTextField();
		textField.setBounds(196, 38, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		//-------variables
		totalcost=new BigDecimal(0);
		final JLabel lblNewLabel_3;

		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(251, 391, 142, 16);
		contentPane.add(lblNewLabel_3);

		final JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(254, 447, 123, 16);
		contentPane.add(lblNewLabel_7);


		final JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(342, 126, 117, 16);
		contentPane.add(lblNewLabel_2);

		final JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(108, 126, 117, 16);
		contentPane.add(lblNewLabel);


		//=======

		//1.scroll
		JScrollPane scroll= new JScrollPane();
		scroll.setBounds(28, 181, 439, 184);
		getContentPane().add(scroll);
		table = new JTable();

		final JButton btnNewButton;
		btnNewButton = new JButton("Go ahead and cancel");
		scroll.setViewportView(table);
		//table.setBounds(28, 181, 439, 184);
		//contentPane.add(table);

		//2.model
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
		//end model
		table.setModel(model);
		model.addColumn("Room Number");
		model.addColumn("Room Category");
		model.addColumn("# People Allowed");
		model.addColumn("Cost per Day");
		model.addColumn("Cost of Extra Bed per Day");
		model.addColumn("Select extra bed");

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//get text from reservation id field
				rid=textField.getText();
				totalcost=new BigDecimal(0);
				//search query

				try{
					model.setRowCount(0);//clear the model!!!!!!!!!!!!!!!
					String query_checkid="select StartDate, EndDate from Reservation where ReservationID="+"'"+rid+"'"+"and Username="+"'"+Customer.user+"'"+"and IsCancelled='0'";
					PreparedStatement pst_check = connection.prepareStatement(query_checkid);
					ResultSet rs_check = pst_check.executeQuery();

					int count=0;
					while(rs_check.next()){
						count=count+1;
					}
					if(count==0){
						JOptionPane.showMessageDialog(null, "Wrong Reservation ID!");
						btnNewButton.setEnabled(false);
					}else{
						btnNewButton.setEnabled(true);
						ResultSet rs_check1 = pst_check.executeQuery();
						while (rs_check1.next()){
							s_d=rs_check1.getObject(1).toString();
							e_d=rs_check1.getObject(2).toString();


						}
						rs_check1.close();
						SimpleDateFormat time2= new SimpleDateFormat("yyyy-MM-dd");

						sd=time2.parse(s_d);
						ed=time2.parse(e_d);
						time2.applyPattern("MM/dd/yyyy");
						String sd_change=time2.format(sd);
						String ed_change=time2.format(ed);

						lblNewLabel.setText(sd_change);
						lblNewLabel_2.setText(ed_change);

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
									//System.out.print(rs_search.getObject(col + 1));
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
						pst_check.close();
						rs_search.close();
						rs_check.close();
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

						try{


							SimpleDateFormat time1= new SimpleDateFormat("yyyy-MM-dd");
							long d1=time1.parse(s_d).getTime();
							long d2=time1.parse(e_d).getTime();

							long minus=(d2-d1)/(1000*60*60*24);
							BigDecimal d = new BigDecimal(minus);
							totalcost=totalcost.multiply(d);



						}catch(Exception e){
							e.printStackTrace();
						}


						lblNewLabel_3.setText(totalcost.toString());//show total cost

						String query_time="select StartDate from Reservation where ReservationID="+"'"+rid+"'";
						PreparedStatement pst_time = connection.prepareStatement(query_time);
						ResultSet rs_time = pst_time.executeQuery();

						while (rs_time.next()) { 
							s_date = rs_time.getString("StartDate"); 
						}

						//change string time to java.util.date time
						SimpleDateFormat time= new SimpleDateFormat("MM/dd/yyyy");
						java.util.Date ts=time.parse(timeStamp);
						time.applyPattern("yyyy-MM-dd");
						String timestamp=time.format(ts);


						//time minus
						//SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");

						long d1=time.parse(s_date).getTime();
						long d2=time.parse(timestamp).getTime();

						long minus=(d1-d2)/(1000*60*60*24);
						if (minus==1 || minus==0){
							btnNewButton.setEnabled(true);
							refund_rate=new BigDecimal(0);
							lblNewLabel_7.setText(totalcost.multiply(refund_rate).toString());
						}else if(minus==3||minus==2){
							btnNewButton.setEnabled(true);
							refund_rate=new BigDecimal(0.8);
							lblNewLabel_7.setText(totalcost.multiply(refund_rate).setScale(0, RoundingMode.CEILING).toString());
						}else if(minus>3){
							btnNewButton.setEnabled(true);
							refund_rate=new BigDecimal(1);
							lblNewLabel_7.setText(totalcost.multiply(refund_rate).toString());
						}else{
							JOptionPane.showMessageDialog(null, "this reservation can not be cancelled because you pass the reservation start date");
							//
							refund_rate=new BigDecimal(-1);
							lblNewLabel_7.setText("N/A");
							btnNewButton.setEnabled(false);

						}
						pst_time.close();
						rs_time.close();
					}//end else

				}catch(Exception e){
					e.printStackTrace();
				}


			}
		});
		btnSearch.setBounds(360, 39, 117, 29);
		contentPane.add(btnSearch);

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(28, 126, 96, 16);
		contentPane.add(lblStartDate);


		JLabel lblNewLabel_1 = new JLabel("End Date");
		lblNewLabel_1.setBounds(254, 126, 76, 16);
		contentPane.add(lblNewLabel_1);



		JLabel lblTotalCostOf = new JLabel("Total Cost of Reservation");
		lblTotalCostOf.setBounds(28, 391, 197, 16);
		contentPane.add(lblTotalCostOf);




		JLabel lblNewLabel_4 = new JLabel("Date of Cancellation");
		lblNewLabel_4.setBounds(28, 419, 156, 16);
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Amount to be refunded");
		lblNewLabel_5.setBounds(28, 447, 183, 16);
		contentPane.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setBounds(254, 419, 156, 16);
		contentPane.add(lblNewLabel_6);
		//date of cancellation
		lblNewLabel_6.setText(timeStamp);





		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (refund_rate.equals(-1)){
					JOptionPane.showMessageDialog(null, "this reservation can not be cancelled because you pass the reservation start date");

				}else{
					//cancel reservation and update total cost
					BigDecimal unity=new BigDecimal(1);
					try{
						//bug:can be cancelled multiple time
						String query_cancel="update Reservation set IsCancelled=1 , TotalCost="+ totalcost.multiply(unity.subtract(refund_rate)).toString() +"  where ReservationID="+"'"+rid+"'";
						PreparedStatement pst_cancel = connection.prepareStatement(query_cancel);
						pst_cancel.executeUpdate();
						JOptionPane.showMessageDialog(null, "cancel success");
						dispose();
						pst_cancel.close();
						connection.close();

						//
					}catch(Exception e){
						e.printStackTrace();
					}


				}
			}
		});
		btnNewButton.setBounds(155, 493, 203, 29);
		contentPane.add(btnNewButton);
	}
}
