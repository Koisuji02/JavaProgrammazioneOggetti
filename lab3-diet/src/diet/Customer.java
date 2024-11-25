package diet;


public class Customer {

	protected String firstName;
	protected String lastName;
	protected String email;
	protected String phoneNumber;

	public Customer(String firstName, String lastName, String email, String phoneNumber){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhone() {
		return this.phoneNumber;
	}
	
	public void SetEmail(String email) {
		this.email = email;
	}
	
	public void setPhone(String phone) {
		this.phoneNumber = phone;
	}

	@Override
	public String toString(){
		return this.firstName+" "+this.lastName;
	}
	
	
}
