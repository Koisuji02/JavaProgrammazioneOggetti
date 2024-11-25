package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut
 * 
 * It includes a name, optional altitude, category,
 * number of beds and location municipality.
 *  
 *
 */
public class MountainHut {
	private String name;
	private Integer altitude; //? capire se va bene
	private String category;
	private Integer bedsNumber;
	private Municipality municipality;

	public MountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		this.name = name;
		this.altitude = altitude;
		this.category = category;
		this.bedsNumber = bedsNumber;
		this.municipality = municipality;
	}

	public String getName() {
		return this.name;
	}

	public Optional<Integer> getAltitude() {
		Optional<Integer> tmp = Optional.ofNullable(this.altitude);
		return tmp;
	}

	public String getCategory() {
		return this.category;
	}

	public Integer getBedsNumber() {
		return this.bedsNumber;
	}

	public Municipality getMunicipality() {
		return this.municipality;
	}
}
