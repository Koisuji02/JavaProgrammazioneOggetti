package managingProperties;

class Building {
private String id;
private Integer n;
	Building(String id, int n) {
		this.id = id; this.n = n;
	}
	public String getId() {
		return id;
	}
	public Integer getN() {
		return n;
	}
}
