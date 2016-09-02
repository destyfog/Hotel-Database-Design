package Core;

public class Customer {
	public static String user;//variable that need to pass

	private String Username;
	private String Password;
	private String email;
	public Customer(String Username,String Password,String email){
		super();
		this.email=email;
		this.Password=Password;
		this.Username=Username;

	}

	public String getUsername(){
		return Username;
	}
}
