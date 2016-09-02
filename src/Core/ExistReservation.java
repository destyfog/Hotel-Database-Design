package Core;

import java.util.Date;

public class ExistReservation {
	//public static Date PStartDate;//variable that need to pass

	private int ReservationID;
	private Date StartDate;
	private Date EndDate;
	private float TotalCost; // decimal???
	private int IsCancelled; // tinyint?
	private String Username;
	private int CardNum;


	public ExistReservation(int ReservationID, Date StartDate, Date EndDate, float TotalCost, int IsCancelled, String Username,int CardNum){
		super();
		this.ReservationID=ReservationID;
		this.StartDate=StartDate;
		this.EndDate=EndDate;
		this.TotalCost=TotalCost;
		this.IsCancelled=IsCancelled;
		this.Username=Username;
		this.CardNum=CardNum;
	}

	public Date getStartDate(){
		return StartDate;
	}

	public Date getEndDate(){
		return EndDate;
	}
}
