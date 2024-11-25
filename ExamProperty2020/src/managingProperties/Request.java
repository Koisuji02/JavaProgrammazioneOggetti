package managingProperties;

class Request {
private int id;
private String state;
private String owner;
private String apartment; 
private String profession;
private String professional;
private int amount;
private String building;

	Request(int id, String owner, String apartment, String profession) {
		this.id = id; this.owner = owner; this.apartment = apartment; this.profession = profession;
		state = "pending";
		building = apartment.split(":")[0];
		//System.out.println(apartment + " " + building + " " + apartment.split(":")[1]);
	}

	public String getBuilding() {
		return building;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public String getApartment() {
		return apartment;
	}

	public String getProfession() {
		return profession;
	}

	void assign(String professional) {
		this.professional = professional;
		state = "assigned";
	}

	public void charge(int amount) {
		this.amount = amount;
		state = "completed";
	}

	public String getProfessional() {
		return professional;
	}

	public int getAmount() {
		return amount;
	}

}
