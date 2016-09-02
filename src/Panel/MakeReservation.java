package Panel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Random;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Core.Customer;
//import Core.Room;
//import Panel.TestTableModelListener.CheckBoxModelListener;
import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

public class MakeReservation extends JFrame {
	//	static Random rand = new Random();
	//	public static int reservationID=rand.nextInt(100000) + 1;
	public static int reservationID=300;//this is soooo dumm
	public static ArrayList cardnums;

	private JPanel contentPane;
	private JTable table;




	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MakeReservation frame = new MakeReservation();
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
	Connection connection=null;
	private JScrollPane scrollPane;
	private JTable table_1;
	private JTable table_2;



	BigDecimal totalcost;
	String ds_c;
	String de_c;
	public static JComboBox comboBox_pass;
	int includeextrabed=0;
	//-------------------------------------------------not used
	//	private Room convertRowToRoom(ResultSet myRs) throws SQLException {
	//		
	//		int RoomNum = myRs.getInt("RoomNum");
	//		String Location = myRs.getString("Location");
	//		int CostperDay = myRs.getInt("CostperDay");
	//		String RoomCategory=myRs.getString("RoomCategory");
	//		int PeopleNum = myRs.getInt("PeopleNum");
	//		int CostofExtrabedperday = myRs.getInt("CostofExtrabedperday");
	//		
	//		
	//		Room searchedroom = new Room(RoomNum,Location, CostperDay,RoomCategory,PeopleNum,CostofExtrabedperday);
	//		
	//		return searchedroom;
	//	}
	//---------------------------------------------------

	public MakeReservation() {

		final DefaultTableModel model;
		//THE MODEL
		model=new DefaultTableModel(){
			/**
			 * 
			 */
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
					return Boolean.class;//

				default:
					return String.class;

				}

			}
		};
		//end model

		final DefaultTableModel model2;

		model2=new DefaultTableModel(){
			/**
			 * 
			 */

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
					return Boolean.class;//



				default:
					return String.class;

				}

			}
		};
		//end model2

		setTitle("Make Reservation");
		connection=SQLConnection.dbConnector();//connect

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//		scrollPane = new JScrollPane();
		//		scrollPane.setBounds(10, 6, 368, 194);
		//		contentPane.add(scrollPane);



		//		table = new JTable();
		//		scrollPane.setViewportView(table);
		//		table.setBackground(UIManager.getColor("CheckBox.background"));



		try{
			String s=SearchRoom.timestart;
			String e=SearchRoom.timeend;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date ds = sdf.parse(s);
			java.util.Date de = sdf.parse(e);
			sdf.applyPattern("yyyy-MM-dd");
			ds_c = sdf.format(ds);
			//ds_change = sdf2.parse(ds_c);

			de_c = sdf.format(de);
			//de_change = sdf2.parse(de_c);


			String query="select RoomNum,RoomCategory,PeopleNum,CostperDay,CostofExtrabedperday from Room where Location= "+"'"+SearchRoom.place+"'"+" and RoomNum in (select distinct RoomNum from Room where Location= "+"'"+SearchRoom.place+"'"+" and RoomNum not in(select distinct RoomNum from ReservationHasRoom where Location= "+"'"+SearchRoom.place+"'"+" and ReservationID in (select ReservationID from Reservation where IsCancelled='0' and (StartDate >= "+"'"+ds_c+"'"+" and StartDate < "+"'"+de_c+"'"+") or (EndDate> "+"'"+ds_c+"'"+" and EndDate<="+"'"+de_c+"'"+"))))";

			//System.out.println(SearchRoom.place);
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			//table.setModel(DbUtils.resultSetToTableModel(rs));



			//create scroll
			JScrollPane scroll = new JScrollPane();
			scroll.setBounds(70,36,659,200);
			getContentPane().add(scroll);
			//createscroll
			JScrollPane scroll2 = new JScrollPane();
			scroll2.setBounds(70, 325, 659, 179);
			getContentPane().add(scroll2);
			//TABLE
			final JTable table_1 = new JTable();
			scroll.setViewportView(table_1);


			final JTable table_2 = new JTable();
			scroll2.setViewportView(table_2);



			//ASSIGN THE MODEL TO TABLE
			table_1.setModel(model);

			model.addColumn("Room Number");
			model.addColumn("Room Category");
			model.addColumn("# People Allowed");
			model.addColumn("Cost per Day");
			model.addColumn("Cost of Extra Bed per Day");
			model.addColumn("Select");

			table_2.setModel(model2);

			model2.addColumn("Room Number");
			model2.addColumn("Room Category");
			model2.addColumn("# People Allowed");
			model2.addColumn("Cost per Day");
			model2.addColumn("Cost of Extra Bed per Day");
			model2.addColumn("Extra Bed?");

			//HAVE TO CREATE CLASS?

			while (rs.next()) {
				Vector data = new Vector();
				for (int col = 0; col < rsmd.getColumnCount(); col++) {
					data.add(rs.getObject(col + 1));

				}
				//System.out.print(data);
				data.add(Boolean.FALSE);//
				model.addRow(data);
			}


			//---------------get card information
			// Create an array list to be filled with group names
			cardnums = new ArrayList();
			String query_card="select CardNum from Payment_information where Username ="+"'"+Customer.user+"'";
			PreparedStatement pst_card = connection.prepareStatement(query_card);
			ResultSet rs_card = pst_card.executeQuery();

			while (rs_card.next()) { 
				int cardnum = rs_card.getInt("CardNum"); 
				// add group names to the array list
				cardnums.add(cardnum);
			} 

			comboBox_pass = new JComboBox();
			comboBox_pass.setBounds(180, 625, 192, 27);
			contentPane.add(comboBox_pass);
			// Populate the combo box
			DefaultComboBoxModel modelcombobox = new DefaultComboBoxModel(cardnums.toArray());
			comboBox_pass.setModel(modelcombobox);
			//-------------

			rs_card.close();
			pst_card.close();
			rs.close();
			pst.close();
			//connection.close();//close the connection to open another one

		}catch(Exception e){
			e.printStackTrace();

		}

		JButton btnNewButton = new JButton("Check Details");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model2.setRowCount(0);//clear the model!!!!!!!!!!!!!!!
				for (int i = 0; i < model.getRowCount(); i++) {
					boolean isChecked = (Boolean) model.getValueAt(i, 5);

					Vector data2 = new Vector();


					if (isChecked) {
						//includeextrabed=1;

						//get the values of the columns you need.
						data2.addElement(model.getValueAt(i, 0));
						data2.addElement(model.getValueAt(i, 1));
						data2.addElement(model.getValueAt(i, 2));
						data2.addElement(model.getValueAt(i, 3));
						data2.addElement(model.getValueAt(i, 4));
						data2.add(Boolean.FALSE);//

						model2.addRow(data2);
						//System.out.println(data2);
					}


				}//end for


			}
		});//button end

		btnNewButton.setBounds(612, 260, 117, 29);
		contentPane.add(btnNewButton);



		JLabel lblNewLabel = new JLabel("Start Date");
		lblNewLabel.setBounds(90, 550, 91, 16);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("End Date");
		lblNewLabel_1.setBounds(387, 550, 69, 16);
		contentPane.add(lblNewLabel_1);

		JLabel lblTotalCost = new JLabel("Total Cost");
		lblTotalCost.setBounds(90, 590, 75, 16);
		contentPane.add(lblTotalCost);

		JLabel lblUse = new JLabel("Use Card");
		lblUse.setBounds(90, 629, 61, 16);
		contentPane.add(lblUse);



		JButton btnNewButton_1 = new JButton("Add Card");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PaymentInformation paymentinformation=new PaymentInformation();
				paymentinformation.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(64, 657, 117, 29);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Submit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model2.getRowCount()==0){
					JOptionPane.showMessageDialog(null, "no room selected!");
				}
				else if (cardnums.size()==0){
					JOptionPane.showMessageDialog(null, "no card!");
				}
				else{
					//submit. put in database.

					try {
						String query_input_re="insert into Reservation values (?,?,?,?,?,?,?)";
						PreparedStatement pst_input_re = connection.prepareStatement(query_input_re);
						pst_input_re.setLong(1, reservationID);
						pst_input_re.setString(2, ds_c);
						pst_input_re.setString(3, de_c);
						pst_input_re.setBigDecimal(4, totalcost);
						pst_input_re.setInt(5, 0);//0 for not cancelled
						pst_input_re.setString(6, Customer.user);

						String num=String.valueOf(comboBox_pass.getSelectedItem());
						pst_input_re.setString(7, num);
						pst_input_re.executeUpdate();

						pst_input_re.close();

						//need to put in reservationhasroom as well
						for (int i = 0; i < model2.getRowCount(); i++) {
							boolean isChecked = (Boolean) model2.getValueAt(i, 5);

							String query_input_rehasroom="insert into ReservationHasRoom values (?,?,?,?)";
							PreparedStatement pst_input_rehasroom = connection.prepareStatement(query_input_rehasroom);
							pst_input_rehasroom.setLong(1, reservationID);

							int Roomnum=(int) model2.getValueAt(i,0);

							pst_input_rehasroom.setInt(1, reservationID);
							pst_input_rehasroom.setInt(2, Roomnum);
							pst_input_rehasroom.setString(3, SearchRoom.place);



							if (isChecked) {
								pst_input_rehasroom.setInt(4, 1);   	 
							}else{
								pst_input_rehasroom.setInt(4, 0);
							}
							pst_input_rehasroom.executeUpdate();

							pst_input_rehasroom.close();

						}//end for


						JOptionPane.showMessageDialog(null, "submit success!");

						Confirmation_screen confirm=new Confirmation_screen();
						confirm.setVisible(true);
						dispose();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1);
					}


				}


			}
		});
		btnNewButton_2.setBounds(612, 624, 117, 29);
		contentPane.add(btnNewButton_2);

		final JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(180, 590, 61, 16);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel(SearchRoom.timestart);
		lblNewLabel_3.setBounds(180, 550, 128, 16);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel(SearchRoom.timeend);
		lblNewLabel_4.setBounds(465, 550, 150, 16);
		contentPane.add(lblNewLabel_4);


		JButton btnCalculatePrice = new JButton("Calculate Price");
		btnCalculatePrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalcost=new BigDecimal(0);

				reservationID=reservationID+1;
				for (int i = 0; i < model2.getRowCount(); i++) {
					boolean isChecked = (Boolean) model2.getValueAt(i, 5);

					BigDecimal cpd=(BigDecimal) model2.getValueAt(i,3);
					totalcost=totalcost.add(cpd);

					if (isChecked) {
						BigDecimal cebpd=(BigDecimal)model2.getValueAt(i,4);			    	
						totalcost=totalcost.add(cebpd);    	 
					}

				}//end for
				lblNewLabel_2.setText(totalcost.toString());
				//textPane_2.setText(totalcost.toString());
			}
		});
		btnCalculatePrice.setBounds(612, 509, 117, 29);
		contentPane.add(btnCalculatePrice);

		JLabel lblAvailableRooms = new JLabel("Available rooms:");
		lblAvailableRooms.setBounds(70, 8, 140, 16);
		contentPane.add(lblAvailableRooms);






	}//end constructor
}
