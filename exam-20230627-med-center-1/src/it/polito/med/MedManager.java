package it.polito.med;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class MedManager {

	public class Speciality{
		private String specialita;
		public Speciality(String specialita){
			this.specialita = specialita;
		}
		public String getSpecialita(){
			return this.specialita;
		}
	}

	public class Doctor{
		private String id;
		private String name;
		private String surname;
		private Speciality s;
		private Doctor(String id, String name, String surname, Speciality s){
			this.id = id;
			this.name = name;
			this.surname = surname;
			this.s = s;
		}
		public String getId(){
			return this.id;
		}
		public String getName(){
			return this.name;
		}
		public String getSurname(){
			return this.surname;
		}
		public Speciality getSpeciality(){
			return this.s;
		}
	}

	public class Slot{
		private Doctor d;
		private String date;
		private LocalTime start;
		private LocalTime end;
		private int duration; // minuti
		public Slot(Doctor d, String date, LocalTime start, LocalTime end, int duration){
			this.d = d;
			this.date = date;
			this.start = start;
			this.end = end;
			this.duration = duration;
		}
		public String getDate(){
			return this.date;
		}
		public Doctor getDoctor(){
			return this.d;
		}
		public LocalTime getStart(){
			return this.start;
		}
		public LocalTime getEnd(){
			return this.end;
		}
		public int getDuration(){
			return this.duration;
		}
		@Override
		public String toString(){
			return this.start.toString()+"-"+this.end.toString();
		}
	}

	public class CurrentDate{
		private String date;
		public CurrentDate(String date){
			this.date = date;
		}
		public String getCurrentDate(){
			return this.date;
		}
	}

	public class Patient{
		private String ssn;
		private String name;
		private String surname;
		private boolean accettato;
		public Patient(String ssn, String name, String surname){
			this.ssn = ssn;
			this.name = name;
			this.surname = surname;
			this.accettato = false;
		}
		public String getSsn(){
			return this.ssn;
		}
		public String getName(){
			return this.name;
		}
		public String getSurname(){
			return this.surname;
		}
		public void setAccettato(){
			this.accettato = true;
		}
		public void setNonAccettato(){
			this.accettato = false;
		}
		public boolean getAccettato(){
			return this.accettato;
		}
	}

	public class Appointment{
		private Patient p;
		private boolean accettatoP;
		private Doctor d;
		private Slot s;
		private String appointmentId;
		private boolean completato;
		public Appointment(Patient p, Doctor d, Slot s){
			this.p = p;
			this.d = d;
			this.s = s;
			this.appointmentId = d.getId()+";"+s.toString()+"="+p.getSsn();
			this.completato = false;
			this.accettatoP = false;
		}
		public Doctor getDoctor(){
			return this.d;
		}
		public Patient getPatient(){
			return this.p;
		}
		public String getSsn(){
			return this.p.getSsn();
		}
		public String getName(){
			return this.p.getName();
		}
		public String getSurname(){
			return this.p.getSurname();
		}
		public Slot getSlot(){
			return this.s;
		}
		public String getAppointmentId(){
			return this.appointmentId;
		}
		@Override
		public String toString(){
			return this.s.getStart().toString()+"="+this.p.getSsn();
		}
		public void setCompletato(){
			this.completato = true;
		}
		public void setNonCompletato(){
			this.completato = false;
		}
		public boolean isCompletato(){
			return this.completato;
		}
		public void setAccettato(){
			this.accettatoP = true;
		}
		public void setNonAccettato(){
			this.accettatoP = false;
		}
		public boolean getAccettato(){
			return this.accettatoP;
		}
	}

	public Collection<Speciality> specialita = new ArrayList<>();
	public Collection<Doctor> medici =  new ArrayList<>();
	public Collection<Slot> slots =  new ArrayList<>();
	public Collection<Appointment> appuntamenti =  new ArrayList<>();
	public Collection<Patient> pazienti = new ArrayList<>();
	public CurrentDate d;
	

	/**
	 * add a set of medical specialities to the list of specialities
	 * offered by the med centre.
	 * Method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param specialities the specialities
	 */
	public void addSpecialities(String... specialities) {
		for(String s: specialities){
			if(!this.specialita.stream().map(entry->entry.getSpecialita()).collect(Collectors.toList()).contains(s)){
				Speciality nuovo = new Speciality(s);
				this.specialita.add(nuovo);
			}
		}
	}

	/**
	 * retrieves the list of specialities offered in the med centre
	 * 
	 * @return list of specialities
	 */
	public Collection<String> getSpecialities() {
		return this.specialita.stream().map(entry->entry.getSpecialita()).collect(Collectors.toList());
	}
	
	
	/**
	 * adds a new doctor with the list of their specialities
	 * 
	 * @param id		unique id of doctor
	 * @param name		name of doctor
	 * @param surname	surname of doctor
	 * @param speciality speciality of the doctor
	 * @throws MedException in case of duplicate id or non-existing speciality
	 */
	public void addDoctor(String id, String name, String surname, String speciality) throws MedException {
		if(!this.specialita.stream().map(entry->entry.getSpecialita()).collect(Collectors.toList()).contains(speciality)){
			throw new MedException();
		}
		if(this.medici.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(id)){
			throw new MedException();
		}
		Speciality tmp = null;
		for(Speciality s: this.specialita){
			if(s.getSpecialita().equals(speciality)) tmp = s;
		}
		Doctor nuovo = new Doctor(id, name, surname, tmp);
		this.medici.add(nuovo);

	}

	/**
	 * retrieves the list of doctors with the given speciality
	 * 
	 * @param speciality required speciality
	 * @return the list of doctor ids
	 */
	public Collection<String> getSpecialists(String speciality) {
		Collection<String> finale = new ArrayList<>();
		for(Doctor d: this.medici){
			if(d.getSpeciality().getSpecialita().equals(speciality)){
				finale.add(d.getId());
			}
		}
		return finale;
	}

	/**
	 * retrieves the name of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the name
	 */
	public String getDocName(String code) {
		for(Doctor d: this.medici){
			if(d.getId().equals(code)){
				return d.getName();
			}
		}
		return null;
	}

	/**
	 * retrieves the surname of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the surname
	 */
	public String getDocSurname(String code) {
		for(Doctor d: this.medici){
			if(d.getId().equals(code)){
				return d.getSurname();
			}
		}
		return null;
	}

	//R2
	/**
	 * Define a schedule for a doctor on a given day.
	 * Slots are created between start and end hours with a 
	 * duration expressed in minutes.
	 * 
	 * @param code	doctor id code
	 * @param date	date of schedule
	 * @param start	start time
	 * @param end	end time
	 * @param duration duration in minutes
	 * @return the number of slots defined
	 */
	public int addDailySchedule(String code, String date, String start, String end, int duration) {
		int counter = 0;
		Doctor tmp = null;
		for(Doctor d: this.medici){
			if(d.getId().equals(code)) tmp = d;
		}
		LocalTime inizio = LocalTime.parse(start);
		LocalTime fine = LocalTime.parse(end);
		LocalTime i = null;
		for(i = inizio; i.isBefore(fine); i = i.plusMinutes(duration)){
			Slot nuovo = new Slot(tmp, date, i, i.plusMinutes(duration), duration);
			this.slots.add(nuovo);
			counter += 1;
		}
		return counter;
	}

	/**
	 * retrieves the available slots available on a given date for a speciality.
	 * The returned map contains an entry for each doctor that has slots scheduled on the date.
	 * The map contains a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-14:30" describes a slot starting at 14:00 and lasting 30 minutes.
	 * 
	 * @param date			date to look for
	 * @param speciality	required speciality
	 * @return a map doc-id -> list of slots in the schedule
	 */
	public Map<String, List<String>> findSlots(String date, String speciality) {
		Collection<Slot> selezionati = new ArrayList<>();
		for(Slot s: this.slots){
			if(s.getDate().equals(date)&&s.getDoctor().getSpeciality().getSpecialita().equals(speciality)) selezionati.add(s);
		}
		return selezionati.stream().collect(Collectors.groupingBy(entry->entry.getDoctor().getId(), Collectors.mapping(entry->entry.toString(), Collectors.toList())));
	}

	//R3
	/**
	 * Define an appointment for a patient in an existing slot of a doctor's schedule
	 * 
	 * @param ssn		ssn of the patient
	 * @param name		name of the patient
	 * @param surname	surname of the patient
	 * @param code		code id of the doctor
	 * @param date		date of the appointment
	 * @param slot		slot to be booked
	 * @return a unique id for the appointment
	 * @throws MedException	in case of invalid code, date or slot
	 */
	public String setAppointment(String ssn, String name, String surname, String code, String date, String slot) throws MedException {
		if(!this.medici.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(code)){
			throw new MedException();
		}
		if(!this.slots.stream().map(entry->entry.getDate()).collect(Collectors.toList()).contains(date)){
			throw new MedException();
		}
		Collection<Slot> scelti = new ArrayList<>();
		for(Slot s: this.slots){
			if(s.getDoctor().getId().equals(code) && s.getDate().equals(date)) scelti.add(s);
		}
		if(!scelti.stream().map(entry->entry.toString()).collect(Collectors.toList()).contains(slot)){
			throw new MedException();
		}
		Doctor tmp = null;
		for(Doctor d: this.medici){
			if(d.getId().equals(code)) tmp = d;
		}
		Slot sTmp = null;
		for(Slot s: this.slots){
			if(s.getDoctor().getId().equals(code) && s.getDate().equals(date) && s.toString().equals(slot)) sTmp = s;
		}
		Patient nuovo = null;
		if(!this.pazienti.stream().map(entry->entry.getSsn()).collect(Collectors.toList()).contains(ssn)){
			nuovo = new Patient(ssn, name, surname);
			this.pazienti.add(nuovo);
		} else{
			for(Patient p:this.pazienti){
				if(p.getSsn().equals(ssn)) nuovo = p;
			}
		}
		Appointment nuovoA = new Appointment(nuovo, tmp, sTmp);
		this.appuntamenti.add(nuovoA);
		return nuovoA.getAppointmentId();
	}

	/**
	 * retrieves the doctor for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor code id
	 */
	public String getAppointmentDoctor(String idAppointment) {
		for(Appointment a: this.appuntamenti){
			if(a.getAppointmentId().equals(idAppointment)) return a.getDoctor().getId();
		}
		return null;
	}

	/**
	 * retrieves the patient for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor patient ssn
	 */
	public String getAppointmentPatient(String idAppointment) {
		for(Appointment a: this.appuntamenti){
			if(a.getAppointmentId().equals(idAppointment)) return a.getSsn();
		}
		return null;
	}

	/**
	 * retrieves the time for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return time of appointment
	 */
	public String getAppointmentTime(String idAppointment) {
		for(Appointment a: this.appuntamenti){
			if(a.getAppointmentId().equals(idAppointment)) return a.getSlot().getStart().toString();
		}
		return null;
	}

	/**
	 * retrieves the date for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return date
	 */
	public String getAppointmentDate(String idAppointment) {
		for(Appointment a: this.appuntamenti){
			if(a.getAppointmentId().equals(idAppointment)) return a.getSlot().getDate();
		}
		return null;
	}

	/**
	 * retrieves the list of a doctor appointments for a given day.
	 * Appointments are reported as string with the format
	 * "hh:mm=SSN"
	 * 
	 * @param code doctor id
	 * @param date date required
	 * @return list of appointments
	 */
	public Collection<String> listAppointments(String code, String date) {
		Collection<String> finale = new ArrayList<>();
		for(Appointment a: this.appuntamenti){
			if(a.getDoctor().getId().equals(code) && a.getSlot().getDate().equals(date)) finale.add(a.toString());
		}
		return finale;
	}

	//R4
	/**
	 * Define the current date for the medical centre
	 * The date will be used to accept patients arriving at the centre.
	 * 
	 * @param date	current date
	 * @return the number of total appointments for the day
	 */
	public int setCurrentDate(String date) {
		CurrentDate nuovo = new CurrentDate(date);
		this.d = nuovo;
		int counter = 0;
		for(Appointment a: this.appuntamenti){
			if(a.getSlot().getDate().equals(d.getCurrentDate())) counter += 1;
		}
		return counter;
	}

	/**
	 * mark the patient as accepted by the med centre reception
	 * 
	 * @param ssn SSN of the patient
	 */
	public void accept(String ssn) {
		/*for(Patient p: this.pazienti){
			if(p.getSsn().equals(ssn)){
				p.setAccettato();
			}
		}*/
		for(Appointment a: this.appuntamenti){
			if(a.getPatient().getSsn().equals(ssn)){
				a.setAccettato();
			}
		}
	}

	/**
	 * returns the next appointment of a patient that has been accepted.
	 * Returns the id of the earliest appointment whose patient has been
	 * accepted and the appointment not completed yet.
	 * Returns null if no such appointment is available.
	 * 
	 * @param code	code id of the doctor
	 * @return appointment id
	 */
	public String nextAppointment(String code) {
		Collection<Appointment> selezionati = new ArrayList<>();
		for(Appointment a: this.appuntamenti){
			if(a.getDoctor().getId().equals(code) && a.getAccettato()==true && a.isCompletato()==false) selezionati.add(a);
		}
		if(selezionati.isEmpty()) return null;
		else {
			String finale = null;
			for(Appointment k: selezionati){
				finale = k.getAppointmentId();
				break;
			}
			return finale;
		}

	}

	/**
	 * mark an appointment as complete.
	 * The appointment must be with the doctor with the given code
	 * the patient must have been accepted
	 * 
	 * @param code		doctor code id
	 * @param appId		appointment id
	 * @throws MedException in case code or appointment code not valid,
	 * 						or appointment with another doctor
	 * 						or patient not accepted
	 * 						or appointment not for the current day
	 */
	public void completeAppointment(String code, String appId)  throws MedException {
		if(!this.medici.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(code)){
			throw new MedException();
		}
		if(!this.appuntamenti.stream().map(entry->entry.getAppointmentId()).collect(Collectors.toList()).contains(appId)){
			throw new MedException();
		}
		for(Appointment a: this.appuntamenti){
			if(a.getAppointmentId().equals(appId)){
				if(!a.getDoctor().getId().equals(code)) throw new MedException();
				if(a.getAccettato() == false) throw new MedException();
				if(!a.getSlot().getDate().equals(d.getCurrentDate())) throw new MedException();
				a.setCompletato();
			}
		}
	}

	//R5
	/**
	 * computes the show rate for the appointments of a doctor on a given date.
	 * The rate is the ratio of accepted patients over the number of appointments
	 *  
	 * @param code		doctor id
	 * @param date		reference date
	 * @return	no show rate
	 */
	public double showRate(String code, String date) {
		Collection<Appointment> selezionati = new ArrayList<>();
		for(Appointment a: this.appuntamenti){
			if(a.getDoctor().getId().equals(code) && a.getSlot().getDate().equals(date)) selezionati.add(a);
		}
		double totale = 0.0;
		for(Appointment a: selezionati){
			if(a.getAccettato() == true) totale += 1;
		}
		return totale/(double) selezionati.size();
	}

	/**
	 * computes the schedule completeness for all doctors of the med centre.
	 * The completeness for a doctor is the ratio of the number of appointments
	 * over the number of slots in the schedule.
	 * The result is a map that associates to each doctor id the relative completeness
	 * 
	 * @return the map id : completeness
	 */
	public Map<String, Double> scheduleCompleteness() {
		Map<String,Long> appMedico = this.appuntamenti.stream().collect(Collectors.groupingBy(entry->entry.getDoctor().getId(), Collectors.counting()));
		Map<String,Long> slotMedico = this.slots.stream().collect(Collectors.groupingBy(entry->entry.getDoctor().getId(), Collectors.counting()));

		Map<String, Double> finale = new HashMap<>();
		for(Map.Entry<String,Long> app: appMedico.entrySet()){
			for(Map.Entry<String,Long> slot: slotMedico.entrySet()){
				if(app.getKey().equals(slot.getKey())){
					finale.put(app.getKey(), (double)app.getValue()/(double)slot.getValue());
				}
			}
		}
		return finale;
	}


	
}
