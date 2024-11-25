package it.polito.project;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ReviewServer {

	public class Group{
		private String name;
		public Group(String name){
			this.name = name;
		}
		public String getName(){
			return this.name;
		}
	}

	public class Review{
		private String titolo;
		private String topic;
		private Group gruppo;
		private String id;
		private boolean open;
		public Review(String titolo, String topic, Group gruppo){
			this.titolo = titolo;
			this.topic = topic;
			this.gruppo = gruppo;
			this.id = titolo+topic+gruppo.getName();
			this.open = false;
		}
		public String getTitolo(){
			return this.titolo;
		}
		public String getTopic(){
			return this.topic;
		}
		public Group getGroup(){
			return this.gruppo;
		}
		public String getId(){
			return this.id;
		}
		public void setOpen(){
			this.open = true;
		}
		public void setClose(){
			this.open = false;
		}
		public boolean isOpen(){
			return this.open;
		}
	}

	public class Slot{
		private Review r;
		private String date;
		private LocalTime start;
		private LocalTime end;
		public Slot(Review r, String date, LocalTime start, LocalTime end){
			this.r = r;
			this.date = date;
			this.start = start;
			this.end = end;
		}
		public Review getReview(){
			return this.r;
		}
		public String getDate(){
			return this.date;
		}
		public LocalTime getStart(){
			return this.start;
		}
		public LocalTime getEnd(){
			return this.end;
		}
		public String getInterval(){
			return this.start.toString()+"-"+this.end.toString();
		}
	}

	public class Preference{
		private Review r;
		private String email;
		private String name;
		private String surname;
		private Slot s;
		public Preference(String email, String name, String surname, Review r, Slot s){
			this.s = s;
			this.r = r;
			this.email = email;
			this.name = name;
			this.surname = surname;
		}
		public Review getReview(){
			return this.r;
		}
		public Slot getSlot(){
			return this.s;
		}
		public String getEmail(){
			return this.email;
		}
		public String getName(){
			return this.name;
		}
		public String getSurname(){
			return this.surname;
		}
		@Override
		public String toString(){
			return this.s.getDate()+"T"+this.s.getInterval()+"="+this.email;
		}
		public String toString(boolean flag){
			return this.s.getDate()+"T"+this.s.getInterval()+"=";
		}
	}

	public Collection<Group> gruppi = new ArrayList<>();
	public Collection<Review> recensioni = new ArrayList<>();
	public Collection<Slot> slots = new ArrayList<>();
	public Collection<Preference> preferenze = new ArrayList<>();

	/**
	 * adds a set of student groups to the list of groups
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param groups the project groups
	 */
	public void addGroups(String... groups) {
		for(String s: groups){
			Group nuovo = new Group(s);
			this.gruppi.add(nuovo);
		}
	}

	/**
	 * retrieves the list of available groups
	 * 
	 * @return list of groups
	 */
	public Collection<String> getGroups() {
		Collection<String> finale = this.gruppi.stream().map(entry->entry.getName()).collect(Collectors.toList());
		return finale;
	}
	
	
	/**
	 * adds a new review with a given group
	 * 
	 * @param title		title of review
	 * @param topic	subject of review
	 * @param group  group of the review
	 * @return a unique id of the review
	 * @throws ReviewException in case of non-existing group
	 */
	public String addReview(String title, String topic, String group) throws ReviewException {
		if(!this.gruppi.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(group)){
			throw new ReviewException();
		}
		Group tmp = null;
		for(Group g: this.gruppi){
			if(g.getName().equals(group)) tmp = g;
		}
		Review nuovo = new Review(title, topic, tmp);
		this.recensioni.add(nuovo);
		return nuovo.getId();
	}

	/**
	 * retrieves the list of reviews with the given group
	 * 
	 * @param group 	required group
	 * @return list of review ids
	 */
	public Collection<String> getReviews(String group) {
		Collection<String> finale = new ArrayList<>();
		for(Review r: this.recensioni){
			if(r.getGroup().getName().equals(group)) finale.add(r.getId());
		}
		return finale;
	}

	/**
	 * retrieves the title of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the title
	 */
	public String getReviewTitle(String reviewId) {
		for(Review r: this.recensioni){
			if(r.getId().equals(reviewId)) return r.getTitolo();
		}
		return null;
	}

	/**
	 * retrieves the topic of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the topic of the review
	 */
	public String getReviewTopic(String reviewId) {
		for(Review r: this.recensioni){
			if(r.getId().equals(reviewId)) return r.getTopic();
		}
		return null;
	}

	// R2
		
	/**
	 * Add a new option slot for a review as a date and a start and end time.
	 * The slot must not overlap with an existing slot for the same review.
	 * 
	 * @param reviewId	id of the review
	 * @param date		date of slot
	 * @param start		start time
	 * @param end		end time
	 * @return the length in hours of the slot
	 * @throws ReviewException in case of slot overlap or wrong review id
	 */
	public double addOption(String reviewId, String date, String start, String end) throws ReviewException {
		if(!this.recensioni.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(reviewId)){
			throw new ReviewException();
		}
		Review tmp = null;
		for(Review r: this.recensioni){
			if(r.getId().equals(reviewId)) tmp = r;
		}
		for(Slot s: this.slots){
			System.out.println(s.getReview().getId());
			if(s.getReview().getId().equals(reviewId)){
				if(s.getDate().equals(date) && ((s.getStart().isAfter(LocalTime.parse(start)) && s.getStart().isBefore(LocalTime.parse(end))) || (s.getEnd().isAfter(LocalTime.parse(start)) && s.getStart().isBefore(LocalTime.parse(end))))) throw new ReviewException();
			}
		}
		Slot nuovo = new Slot(tmp, date, LocalTime.parse(start), LocalTime.parse(end));
		this.slots.add(nuovo);
		Duration d = Duration.between(LocalTime.parse(start), LocalTime.parse(end));
		double hours = (double)d.toHours();
		return hours;
	}

	/**
	 * retrieves the time slots available for a given review.
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-15:30".
	 * 
	 * @param reviewId		id of the review
	 * @return a map date -> list of slots
	 */
	public Map<String, List<String>> showSlots(String reviewId) {
		List<Slot> selezionati = new ArrayList<>();
		for(Slot s: this.slots){
			if(s.getReview().getId().equals(reviewId)) selezionati.add(s);
		}
		return selezionati.stream().collect(Collectors.groupingBy(entry->entry.getDate(), Collectors.mapping(entry->entry.getInterval(), Collectors.toList())));
	}

	//R3
	/**
	 * Declare a review open for collecting preferences for the slots.
	 * 
	 * @param reviewId	is of the review
	 */
	public void openPoll(String reviewId) {
		for(Review r: this.recensioni){
			if(r.getId().equals(reviewId)) r.setOpen();
		}
	}


	/**
	 * Records a preference of a student for a specific slot/option of a review.
	 * Preferences can be recorded only for review for which poll has been opened.
	 * 
	 * @param email		email of participant
	 * @param name		name of the participant
	 * @param surname	surname of the participant
	 * @param reviewId	id of the review
	 * @param date		date of the selected slot
	 * @param slot		time range of the slot
	 * @return the number of preferences for the slot
	 * @throws ReviewException	in case of invalid id or slot
	 */
	public int selectPreference(String email, String name, String surname, String reviewId, String date, String slot) throws ReviewException {
		if(!this.recensioni.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(reviewId)){
			throw new ReviewException();
		}
		Collection<Slot> selezionati = new ArrayList<>();
		for(Slot s: this.slots){
			if(s.getReview().getId().equals(reviewId)) selezionati.add(s);
		}
		if(!selezionati.stream().map(entry->entry.getInterval()).collect(Collectors.toList()).contains(slot)){
			throw new ReviewException();
		}
		Review tmp = null;
		for(Review r: this.recensioni){
			if(r.getId().equals(reviewId)){
				tmp = r;
				if(r.isOpen()==false) throw new ReviewException();
			}
		}
		Slot sTmp = null;
		for(Slot s: this.slots){
			if(s.getReview().getId().equals(reviewId) && s.getDate().equals(date) && s.getInterval().equals(slot)) sTmp = s;
		}
		Preference nuovo = new Preference(email, name, surname, tmp, sTmp);
		this.preferenze.add(nuovo);
		int counter = 0;
		for(Preference p: this.preferenze){
			if(p.getSlot().getInterval().equals(slot)) counter += 1;
		}
		return counter;
	}

	/**
	 * retrieves the list of the preferences expressed for a review.
	 * Preferences are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=EMAIL", including date, time interval, and email separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId review id
	 * @return list of preferences for the review
	 */
	public Collection<String> listPreferences(String reviewId) {
		Collection<String> selezionati = new ArrayList<>();
		for(Preference p: this.preferenze){
			if(p.getReview().getId().equals(reviewId)) selezionati.add(p.toString());
		}
		return selezionati;
	}

	//R4
	/**
	 * close the poll associated to a review and returns
	 * the most preferred options, i.e. those that have receive the highest number of preferences.
	 * The options are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=##", including date, time interval, and preference count separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId	id of the review
	 */
	public Collection<String> closePoll(String reviewId) {
		Collection<Preference> selezionati = new ArrayList<>();
		for(Preference p: this.preferenze){
			if(p.getReview().getId().equals(reviewId)){
				p.getReview().setClose();
				selezionati.add(p);
			}
		}
		Map<String,Long> tmp = selezionati.stream().collect(Collectors.groupingBy(entry->entry.toString(true), Collectors.counting()));
		long max = 0;
		for(Map.Entry<String,Long> entry : tmp.entrySet()){
			if(entry.getValue()>max) max = entry.getValue();
		}
		Collection<String> finale = new ArrayList<>();
		for(Map.Entry<String,Long> entry : tmp.entrySet()){
			if(entry.getValue()==max){
				finale.add(entry.getKey()+entry.getValue());
			}
		}
		return finale;
	}

	//R5
	/**
	 * returns the preference count for each slot of a review
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots with preferences described as strings with the format 
	 * "hh:mm-hh:mm=###", including the time interval and the number of preferences 
	 * e.g. "14:00-15:30=2".
	 * 
	 * All possible dates are reported and for each date only 
	 * the slots with at least one preference are listed.
	 * 
* @param reviewId	the id of the review
	 * @return the map data -> slot preferences
	 */
	public Map<String, List<String>> reviewPreferences(String reviewId) {
		Collection<Preference> selezionati = new ArrayList<>();
		for(Preference p: this.preferenze){
			if(p.getReview().getId().equals(reviewId)) selezionati.add(p);
		}
		Map<String,Map<String,Long>> tmp = selezionati.stream().collect(Collectors.groupingBy(entry->entry.getSlot().getDate(), Collectors.groupingBy(entry->entry.getSlot().getInterval(), Collectors.counting())));
		Map<String, List<String>> finale = new HashMap<>();
		for(Map.Entry<String,Map<String,Long>> entry: tmp.entrySet()){
			List<String> lista = new ArrayList<>();
			for(Map.Entry<String,Long> value: entry.getValue().entrySet()){
				lista.add(value.getKey()+"="+value.getValue());
			}
			finale.put(entry.getKey(),lista);
		}
		return finale;
	}


	/**
	 * computes the number preferences collected for each review
	 * The result is a map that associates to each review id the relative count of preferences expressed
	 * 
	 * @return the map id : preferences -> count
	 */
	public Map<String, Integer> preferenceCount() {
		Map<String,Long> tmp = this.preferenze.stream().collect(Collectors.groupingBy(entry->entry.getReview().getId(), Collectors.counting()));
		Map<String, Integer> finale = new HashMap<>();
		for(Map.Entry<String,Long> entry: tmp.entrySet()){
			finale.put(entry.getKey(),entry.getValue().intValue());
		}
		return finale;
	}
}
