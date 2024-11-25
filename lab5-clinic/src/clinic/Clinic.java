package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Element;


/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {

	public class Patient { // classe interna
		protected String first;
		protected String last;
		protected String ssn;
		//COSTRUTTORE
		public Patient(String first, String last, String ssn){
			this.first = first;
			this.last = last;
			this.ssn = ssn;
		}
		//GETTER
		public String getFirst(){
			return this.first;
		}
		public String getLast(){
			return this.last;
		}
		public String getSsn(){
			return this.ssn;
		}
		@Override
		public String toString(){
			return this.getLast()+" "+this.getFirst()+" ("+this.getSsn()+")";
		}
	}

	public class Doctor{ // classe interna
		protected String first;
		protected String last;
		protected String ssn;
		protected int badge;
		protected String specialization;
		//COSTRUTTORE
		public Doctor(String first, String last, String ssn, int badge, String specialization){
			this.first = first;
			this.last = last;
			this.ssn = ssn;
			this.badge = badge;
			this.specialization = specialization;
		}
		// GETTER
		public String getFirst(){
			return this.first;
		}
		public String getLast(){
			return this.last;
		}
		public String getSsn(){
			return this.ssn;
		}
		public int getBadge(){
			return this.badge;
		}
		public String getSpecialization(){
			return this.specialization;
		}
		@Override
		public String toString(){
			return this.getLast()+" "+this.getFirst()+" ("+this.getSsn()+") ["+this.getBadge()+"]: "+this.getSpecialization();
		}
	}

	protected ArrayList<Patient> pazienti = new ArrayList<>();
	protected ArrayList<Doctor> dottori = new ArrayList<>();
	// dal paziente (chiave) trovo il suo dottore (valore) - 1:1
	protected Map<String, Integer> pazientiDottori = new HashMap<>();

	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
   		Patient p = new Patient(first, last, ssn);
		this.pazienti.add(p); //! capire se gestire pazienti ripetuti
 	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
   		for(Patient p: this.pazienti){
			if(p.getSsn().equals(ssn)) return p.toString();
		}
		throw new NoSuchPatient(); // se non trovo nulla lancio l'eccezione
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		Doctor d = new Doctor(first, last, ssn, docID, specialization);
		this.dottori.add(d); //! capire se gestire dottori ripetuti
	}

	public Doctor getDoctorById(int id) throws NoSuchDoctor{
		for(Doctor d: this.dottori){
			if(d.getBadge() == id) return d;
		}
		throw new NoSuchDoctor(id);
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		for(Doctor d: this.dottori){
			if(d.getBadge() == docID) return d.toString();
		}
		throw new NoSuchDoctor(docID); // se non trovo nulla lancio l'eccezione
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		int flagP = 0, flagD = 0;
		// controllo paziente
		for(Patient p : this.pazienti){
			if(p.getSsn().equals(ssn)) flagP = 1;
		}
		if(flagP == 0) throw new NoSuchPatient();
		// controllo dottore
		for(Doctor d : this.dottori){
			if(d.getBadge() == docID) flagD = 1;
		}
		if(flagD == 0) throw new NoSuchDoctor(docID);
		// se esistono entrambi
		pazientiDottori.put(ssn, docID); // put aggiunge alla mappa la tupla se non è presente, ma se è già presente la chiave con un altro valore, allora aggiorna il valore associato alla chiave
	}

	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		int flagP = 0, flagD = 0;
		// controllo paziente
   		for(Patient p: this.pazienti){
			if(p.getSsn().equals(ssn)) flagP = 1;
		}
		if(flagP == 0) throw new NoSuchPatient();
		// controllo se paziente già assegnato
		for(String s: this.pazientiDottori.keySet()){
			if(s.equals(ssn)) flagD = 1;
		}
		if(flagD == 0) throw new NoSuchDoctor();
		// se tutto apposto, prendo il docID assegnato al paziente (ssn)
		return this.pazientiDottori.get(ssn);
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		int flag = 0;
		// controllo se esiste dottore
   		for(Doctor d: this.dottori){
			if(d.getBadge() == id) flag = 1;
		}
		if(flag == 0) throw new NoSuchDoctor(id);

		//! approccio no stream
		Collection<String> p1 = new ArrayList<>();
		// per ogni entry della mia mappa, se il valore (docID) è quello cercato, prendo la chiave (ssn) della entry e la aggiungo alla Collezione
		for(Map.Entry<String, Integer> entry: this.pazientiDottori.entrySet()){
			if(entry.getValue() == id) p1.add(entry.getKey());
		}
		return p1;

		//! approccio stream
		/* Collection<String> p2 = pazientiDottori.entrySet().stream()
			.filter(entry -> entry.getValue() == id)
			.map(Map.Entry::getKey)
			.collect(Collectors.toList()); */
	}
	
	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		// creo il buffered reader (buffered = più prestazioni, lettura in blocchi)
   		BufferedReader br = new BufferedReader(reader);
		String line; // riga
		int counter = 0; // counter righe lette
		while((line = br.readLine()) != null){ // fino a che riga != null (fine file)
			String[] parts = Arrays.stream(line.trim().split(";")) // elimino gli spazi superflui dalla riga e faccio lo split sul ";"
					.map(String::trim) // elimino gli spazi superflui circondanti ";"
					.toArray(String[]::new); // faccio l'Array per metterlo in parts
				
			if(parts.length == 0) continue; // vado alla riga dopo (err contenuti file)

			if(parts[0].equals("P")){ // paziente
				if(parts.length != 4) continue; // vado alla riga dopo (err contenuti file)
				addPatient(parts[1], parts[2], parts[3]);
			}
			else if(parts[0].equals("M")){ // dottore
				if(parts.length != 6) continue;
				addDoctor(parts[2], parts[3], parts[4], Integer.parseInt(parts[1]), parts[5]);
			}
			else continue; // ne paziente ne dottore, quindi errore
			counter++; // aumento il numero di righe buone del file
		}
		return counter;
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>
	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		//! analogo al metodo sopra con accorgimenti sul listener
   		BufferedReader br = new BufferedReader(reader);
		String line;
		int counter = 0, ordine = 0;
		while((line = br.readLine()) != null){
			String[] parts = Arrays.stream(line.trim().split(";"))
					.map(String::trim)
					.toArray(String[]::new);
				
			if(parts.length == 0){
				ordine++;
				listener.offending(ordine, line);
				continue;
			}

			if(parts[0].equals("P")){
				if(parts.length != 4){
					ordine++;
					listener.offending(ordine, line);
					continue;
				}
				addPatient(parts[1], parts[2], parts[3]);
			}
			else if(parts[0].equals("M")){
				if(parts.length != 6){
					ordine++;
					listener.offending(ordine, line);
					continue;
				}
				addDoctor(parts[2], parts[3], parts[4], Integer.parseInt(parts[1]), parts[5]);
			}
			else{
				ordine++;
				listener.offending(ordine, line);
				continue;
			}
			counter++;
			ordine++;
		}
		return counter;
	}
	
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
   		Collection<Integer> iniziali = new ArrayList<>();
		List<Doctor> ordinati = new ArrayList<>(this.dottori); // uso ordinati in modo da ordinare prima delle varie operazioni e quindi ridurre la complessità sulle operazioni di stream
		ordinati.sort(Comparator.comparing(Doctor::getLast).thenComparing(Doctor::getFirst)); // ordino prima per cognome poi per nome
		for(Doctor d: ordinati){
			iniziali.add(d.getBadge());
		}
		List<Integer> daRimuovere = new ArrayList<>(this.pazientiDottori.values());
		Collection<Integer> liberi = iniziali.stream()
			.filter(value -> !daRimuovere.contains(value)) // filtra per i valori non contenuti nella mappa
			.collect(Collectors.toList());
		return liberi;
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		Map<Integer, Integer> iniziali = this.dottori.stream()
			.collect(Collectors.toMap(Doctor::getBadge,
				d -> {
					try{
						return getAssignedPatients(d.getBadge()).size();
					} catch(NoSuchDoctor e){
						return 0; // ritorno zero se non c'è dottore con pazienti (eccezioni tirata nel metodo getAssignedPatients)
					}
				}));
		double media =  iniziali.values().stream().mapToDouble(Integer::doubleValue).average().orElse(0.0); //orElse perchè da average otteniamo OptionalDouble
		
   		Collection<Integer> finali = iniziali.entrySet().stream()
			.filter(d -> d.getValue()>media) // uso filter per prendere i dottori con valore (ovvero n° pazienti) > media
			.map(Map.Entry::getKey) // uso il map per ottenere solo le chiavi dalle mie entry
			.collect(Collectors.toList());
		return finali;
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		Map<Integer, List<Doctor>> iniziali = new TreeMap<>(Comparator.reverseOrder()); // così ordino in modo descrescente la mia mappa per numero di pazienti
		for(Doctor d: this.dottori){
			try{
				int n = getAssignedPatients(d.getBadge()).size();
				if(iniziali.containsKey(n)){ // se c'è già il numero di pazienti, aggiungo il dottore alla lista di dottori che hanno quel numero
					iniziali.get(n).add(d);
				} else{ // altrimenti creo la lista di dottori vuota per quel numero e lo aggiungo alla lista di dottori con quel numero
					iniziali.put(n, new ArrayList<>());
					iniziali.get(n).add(d);
				}
			} catch(NoSuchDoctor e){
				continue;
			}
		}
		// una volta che ho la mappa ne faccio la lista di stringhe
		Collection<String> stringhe = new ArrayList<>();
		for(Map.Entry<Integer, List<Doctor>> entry: iniziali.entrySet()){
			for(Doctor d: entry.getValue()){
				String s = String.format("%3d", entry.getKey())+" : "+d.getBadge()+" "+d.getLast()+" "+d.getFirst();
				stringhe.add(s);
			}
		}
		return stringhe;
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
   		Map<String, Long> iniziali = this.pazientiDottori.entrySet().stream()
			.collect(Collectors.groupingBy(
				entry -> {
					try{
						return getDoctorById(entry.getValue()).getSpecialization();
					} catch(NoSuchDoctor e){
						return null;
					}
				}, Collectors.counting() // valore = counter
			));
		Collection<String> finale = iniziali.entrySet().stream()
			.sorted(Map.Entry.<String,Long>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey()))
			.map(entry -> String.format("%3d - %s", entry.getValue(), entry.getKey()))
			.collect(Collectors.toList());
		return finale;
	}

}
