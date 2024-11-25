package it.polito.ski;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SkiArea {

	public class Type{
		public String code;
		public String category;
		public int capacity;
		public Type(String code, String category, int capacity){
			this.code = code;
			this.category = category;
			this.capacity = capacity;
		}
		public String getCategory(){
			return this.category;
		}
		public int getCapacity(){
			return this.capacity;
		}
		public String getCode(){
			return this.code;
		}
	}

	public class Lift{
		public String name;
		public String typeCode;
		public Lift(String name, String typeCode){
			this.name = name;
			this.typeCode = typeCode;
		}
		public String getName(){
			return this.name;
		}
		public String getCode(){
			return this.typeCode;
		}
	}

	public class Slope{
		public String name;
		public String difficulty;
		public String lift;
		public Slope(String name, String difficulty, String lift){
			this.name = name;
			this.difficulty = difficulty;
			this.lift = lift;
		}
		public String getName(){
			return this.name;
		}
		public String getDifficulty(){
			return this.difficulty;
		}
		public String getLift(){
			return this.lift;
		}
	}

	public class Parking{
		public String name;
		public int slots;
		public Parking(String name, int slots){
			this.name = name;
			this.slots = slots;
		}
		public String getName(){
			return this.name;
		}
		public int getSlots(){
			return this.slots;
		}
	}

	public String name;
	public Collection<Type> tipi = new ArrayList<>(); // tutti i tipi
	public Collection<Lift> lifts = new ArrayList<>(); // tutti i lifts
	public Collection<Slope> slopes = new ArrayList<>(); // tutti le slopes
	public Collection<Parking> parcheggi = new ArrayList<>(); // tutti i parcheggi
	public Map<String, ArrayList<String>> parkLifts = new HashMap<>(); // prima string = nome parcheggio; arraylist di string con nomi lifts


	/**
	 * Creates a new ski area
	 * @param name name of the new ski area
	 */
	public SkiArea(String name) {
		this.name = name;
    }

	/**
	 * Retrieves the name of the ski area
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

    /**
     * define a new lift type providing the code, the category (Cable Cabin, Chair, Ski-lift)
     * and the capacity (number of skiers carried) of a single unit
     * 
     * @param code		name of the new type
     * @param category	category of the lift
     * @param capacity	number of skiers per unit
     * @throws InvalidLiftException in case of duplicate code or if the capacity is <= 0
     */
    public void liftType(String code, String category, int capacity) throws InvalidLiftException {
		if(this.tipi.stream().map(entry->entry.getCode()).collect(Collectors.toList()).contains(code)) throw new InvalidLiftException();
		Type nuovo = new Type(code, category, capacity);
		this.tipi.add(nuovo);
    }
    
    /**
     * retrieves the category of a given lift type code
     * @param typeCode lift type code
     * @return the category of the type
     * @throws InvalidLiftException if the code has not been defined
     */
    public String getCategory(String typeCode) throws InvalidLiftException {
		if(!this.tipi.stream().map(entry->entry.getCode()).collect(Collectors.toList()).contains(typeCode)) throw new InvalidLiftException();
		for(Type t: this.tipi){
			if(t.getCode().equals(typeCode)) return t.getCategory();
		}
		return null;
    }

    /**
     * retrieves the capacity of a given lift type code
     * @param typeCode lift type code
     * @return the capacity of the type
     * @throws InvalidLiftException if the code has not been defined
     */
    public int getCapacity(String typeCode) throws InvalidLiftException {
        if(!this.tipi.stream().map(entry->entry.getCode()).collect(Collectors.toList()).contains(typeCode)) throw new InvalidLiftException();
		for(Type t: this.tipi){
			if(t.getCode().equals(typeCode)) return t.getCapacity();
		}
		return -1;
    }


    /**
     * retrieves the list of lift types
     * @return the list of codes
     */
	public Collection<String> types() {
		return this.tipi.stream().map(entry->entry.getCode()).collect(Collectors.toList());
	}
	
	/**
	 * Creates new lift with given name and type
	 * 
	 * @param name		name of the new lift
	 * @param typeCode	type of the lift
	 * @throws InvalidLiftException in case the lift type is not defined
	 */
    public void createLift(String name, String typeCode) throws InvalidLiftException{
		if(!this.tipi.stream().map(entry->entry.getCode()).collect(Collectors.toList()).contains(typeCode)) throw new InvalidLiftException();
		Lift nuovo = new Lift(name, typeCode);
		this.lifts.add(nuovo);
    }
    
	/**
	 * Retrieves the type of the given lift
	 * @param lift 	name of the lift
	 * @return type of the lift
	 */
	public String getType(String lift) {
		for(Lift l: this.lifts){
			if(l.getName().equals(lift)) return l.getCode();
		}
		return null;
	}

	/**
	 * retrieves the list of lifts defined in the ski area
	 * @return the list of names sorted alphabetically
	 */
	public List<String> getLifts(){
		return this.lifts.stream().map(entry->entry.getName()).sorted().collect(Collectors.toList());
    }

	/**
	 * create a new slope with a given name, difficulty and a starting lift
	 * @param name			name of the slope
	 * @param difficulty	difficulty
	 * @param lift			the starting lift for the slope
	 * @throws InvalidLiftException in case the lift has not been defined
	 */
    public void createSlope(String name, String difficulty, String lift) throws InvalidLiftException {
		if(!this.lifts.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(lift)) throw new InvalidLiftException();
		Slope nuovo = new Slope(name, difficulty, lift);
		this.slopes.add(nuovo);
    }
    
    /**
     * retrieves the name of the slope
     * @param slopeName name of the slope
     * @return difficulty
     */
	public String getDifficulty(String slopeName) {
		for(Slope s: this.slopes){
			if(s.getName().equals(slopeName)) return s.getDifficulty();
		}
		return null;
	}

	/**
	 * retrieves the start lift
	 * @param slopeName name of the slope
	 * @return starting lift
	 */
	public String getStartLift(String slopeName) {
		for(Slope s: this.slopes){
			if(s.getName().equals(slopeName)) return s.getLift();
		}
		return null;
	}

	/**
	 * retrieves the list of defined slopes
	 * 
	 * @return list of slopes
	 */
    public Collection<String> getSlopes(){
		return this.slopes.stream().map(entry->entry.getName()).collect(Collectors.toList());
    }

    /**
     * Retrieves the list of slopes starting from a given lift
     * 
     * @param lift the starting lift
     * @return the list of slopes
     */
    public Collection<String> getSlopesFrom(String lift){
		Collection<String> finali = new ArrayList<>();
		for(Slope s: this.slopes){
			if(s.getLift().equals(lift)) finali.add(s.getName());
		}
		return finali;
    }

    /**
     * Create a new parking with a given number of slots
     * @param name 	new parking name
     * @param slots	slots available in the parking
     */
    public void createParking(String name, int slots){
		Parking nuovo = new Parking(name, slots);
		this.parcheggi.add(nuovo);
    }

    /**
     * Retrieves the number of parking slots available in a given parking
     * @param parking	parking name
     * @return number of slots
     */
	public int getParkingSlots(String parking) {
		for(Parking p: this.parcheggi){
			if(p.getName().equals(parking)) return p.getSlots();
		}
		return -1;
	}

	/**
	 * Define a lift as served by a given parking
	 * @param lift		lift name
	 * @param parking	parking name
	 */
	public void liftServedByParking(String lift, String parking) {
		if(this.parkLifts.containsKey(parking)){
			this.parkLifts.get(parking).add(lift);
		} else{
			ArrayList<String> tmp = new ArrayList<>();
			tmp.add(lift);
			this.parkLifts.put(parking, tmp);
		}
	}

	
	/**
	 * Retrieves the list of lifts served by a parking.
	 * @param parking	parking name
	 * @return the list of lifts
	 */
	public Collection<String> servedLifts(String parking) {
		return this.parkLifts.get(parking);
	}

	/**
	 * Checks whether the parking is proportional to the capacity of the lift it is serving.
	 * A parking is considered proportionate if its size divided by the sum of the capacity of the lifts 
	 * served by the parking is less than 30.
	 * 
	 * @param parkingName name of the parking to check
	 * @return true if the parking is proportionate
	 */
	public boolean isParkingProportionate(String parkingName) {
		Parking parcheggio = null;
		for(Parking p: this.parcheggi){
			if(p.getName().equals(parkingName)) parcheggio = p;
		}
		Collection<String> nomiLifts = this.servedLifts(parkingName);
		Collection<Lift> tmp = new ArrayList<>();
		for(String s : nomiLifts){
			for(Lift l: this.lifts){
				if(l.getName().equals(s)) tmp.add(l);
			}
		}
		int capacitaTotale = 0;
		for(Lift l: tmp){
			for(Type t: this.tipi){
				if(l.getCode().equals(t.getCode())) capacitaTotale += t.getCapacity();
			}
		}
		if((parcheggio.getSlots()/capacitaTotale) < 30) return true;
		return false;
	}

	/**
	 * reads the description of lift types and lift descriptions from a text file. 
	 * The contains a description per line. 
	 * Each line starts with a letter indicating the kind of information: "T" stands for Lift Type, 
	 * while "L" stands for Lift.
	 * A lift type is described by code, category and seat number. 
	 * A lift is described by the name and the lift type.
	 * Different data on a line are separated by ";" and possible spaces surrounding the separator are ignored.
	 * If a line contains the wrong number of information it should be skipped and
	 * the method should continue reading the following lines. 
	 * 
	 * @param path 	the path of the file
	 * @throws IOException	in case IO error
	 * @throws InvalidLiftException in case of duplicate type or non-existent lift type
	 */
    public void readLifts(String path) throws IOException, InvalidLiftException {
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			String riga = null;
			while((riga = br.readLine()) != null){ // fino a che non è finito
				riga.trim(); // rimuovo il primo spazio per charAt(0)
				if(riga.split(";").length < 3) continue;
				if(riga.charAt(0) == 'T'){ // Type
					String[] tmp = new String[4];
					tmp = riga.split(";"); // prendo i singoli campi della riga
					for(int i = 0; i < tmp.length; i++) {
						tmp[i] = tmp[i].trim(); // rimuovo i singoli spazi
					}
					this.liftType(tmp[1], tmp[2], Integer.parseInt(tmp[3]));
				}
				else if(riga.charAt(0) == 'L'){ // Type
					String[] tmp = new String[3];
					tmp = riga.split(";"); // prendo i singoli campi della riga
					for(int i = 0; i < tmp.length; i++) {
						tmp[i] = tmp[i].trim(); // rimuovo i singoli spazi
					}
					this.createLift(tmp[1], tmp[2]);
				}
			}
		} catch(IOException e){
			throw new IOException();
		}
    }

}
