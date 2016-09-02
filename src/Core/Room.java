package Core;

public class Room {
	private Integer RoomNum;
	//private String Location;
	private Integer CostperDay;
	private String RoomCategory;
	private Integer peopleNum;
	private Integer CostofExtrabedperday;


	public Room(Integer RoomNum,String Location,Integer CostperDay,String RoomCategory,Integer peopleNum,Integer CostofExtrabedperday){
		super();
		this.RoomNum=RoomNum;
		//this.Location=Location;
		this.CostperDay=CostperDay;
		this.RoomCategory=RoomCategory;
		this.peopleNum=peopleNum;
		this.CostofExtrabedperday=CostofExtrabedperday;
	}
}
