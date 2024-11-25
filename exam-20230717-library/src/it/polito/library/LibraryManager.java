package it.polito.library;
import java.util.*;
import java.util.stream.Collectors;


public class LibraryManager {

	public int base = 999; //code
	public int n_lettori = 999;

	public class Book{ // singola copia
		public String titolo;
		public int n_copie;
		public Collection<String> copie = new ArrayList<>();
		public Book(String titolo, int n_copie){
			this.titolo = titolo;
			this.n_copie = n_copie;
		}
		public String getTitolo(){
			return this.titolo;
		}
		public int getN(){
			return this.n_copie;
		}
		public Collection<String> getCopie(){
			return this.copie;
		}
		public void addCopy(String code){
			this.copie.add(code);
			this.n_copie++;
		}
	}

	public class Lettore{
		public String name;
		public String surname;
		public String id;
		public Lettore(String name, String surname){
			this.name = name;
			this.surname = surname;
			n_lettori++;
			this.id = Integer.toString(n_lettori);
		}
		public String getName(){
			return this.name;
		}
		public String getSurname(){
			return this.surname;
		}
		public String getId(){
			return this.id;
		}
	}

	public class Noleggio{
		public String date;
		public String end; // data fine
		public String id;
		public String copia;
		public String titolo;
		public boolean finito;
		public Noleggio(String date, String id, String copia, String titolo){
			this.date = date;
			this.id = copia;
			this.copia = id;
			this.titolo = titolo;
			this.finito = false;
		}
		public String getDate(){
			return this.date;
		}
		public String getLettore(){
			return this.id;
		}
		public String getTitotlo(){
			return this.titolo;
		}
		public String getCopia(){
			return this.copia;
		}
		public boolean isFinito(){
			return this.finito;
		}
		public void setFinito(String end){
			this.finito = true;
			this.end = end;
		}
		public void setInizio(String date){
			this.date = date;
			this.finito = false;
		}
		public String getEnd(){
			return this.end;
		}
		@Override
		public String toString(){
			return this.id;
		}
	}

	public Collection<Lettore> lettori = new ArrayList<>();
	public List<Book> libri = new ArrayList<>();
	public Map<String, Map<String, Integer>> copieLibere = new HashMap<>();
	public Map<String, String> totaliCopie = new TreeMap<>();
	public List<Noleggio> noleggi = new ArrayList<>();

    // R1: Readers and Books 
    
    /**
	 * adds a book to the library archive
	 * The method can be invoked multiple times.
	 * If a book with the same title is already present,
	 * it increases the number of copies available for the book
	 * 
	 * @param title the title of the added book
	 * @return the ID of the book added 
	 */
    public String addBook(String title) {
		Book nuovo = new Book(title, 1);
		String code = Integer.toString(++base);
		nuovo.addCopy(code);
        if(!this.libri.stream().map(entry->entry.getTitolo()).collect(Collectors.toList()).contains(title)){
			this.libri.add(nuovo);
			this.totaliCopie.put(Integer.toString(base), title);
			Map<String, Integer> ciao = new HashMap<>();
			ciao.put(code, 1);
			this.copieLibere.put(title, ciao);
		} else{
			for(Book l: this.libri){
				if(l.getTitolo().equals(title)) nuovo = l;
			}
			code = Integer.toString(base);
			nuovo.addCopy(code);
			this.totaliCopie.put(Integer.toString(base), title);
			this.copieLibere.get(title).put(code, 1);
		}
		return code;
    }
    
    /**
	 * Returns the book titles available in the library
	 * sorted alphabetically, each one linked to the
	 * number of copies available for that title.
	 * 
	 * @return a map of the titles liked to the number of available copies
	 */
    public SortedMap<String, Integer> getTitles() {
		// ordinamento	
        SortedMap<String, Integer> finali = new TreeMap<>();
		SortedMap<Book, Integer> tmp = new TreeMap<>(Comparator.comparing(Book::getTitolo));
		for(Book b: this.libri){
			tmp.put(b, b.getN());
		}
		for(Map.Entry<Book, Integer> entry: tmp.entrySet()){
			finali.put(entry.getKey().getTitolo(), entry.getValue());
		}
		return finali;
    }
    
    /**
	 * Returns the books available in the library
	 * 
	 * @return a set of the titles liked to the number of available copies
	 */
    public Set<String> getBooks() {    	    	
       Set<String> set = this.totaliCopie.keySet();
	   return set;
    }
    
    /**
	 * Adds a new reader
	 * 
	 * @param name first name of the reader
	 * @param surname last name of the reader
	 */
    public void addReader(String name, String surname) {
		Lettore l = new Lettore(name, surname);
		this.lettori.add(l);
    }
    
    
    /**
	 * Returns the reader name associated to a unique reader ID
	 * 
	 * @param readerID the unique reader ID
	 * @return the reader name
	 * @throws LibException if the readerID is not present in the archive
	 */
    public String getReaderName(String readerID) throws LibException {
        if(!this.lettori.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(readerID)){
			throw new LibException();
		}
		String r = "";
		for(Lettore l: this.lettori){
			if(l.getId().equals(readerID)) r = l.getName()+" "+l.getSurname();
		}
		return r;
    }    
    
    
    // R2: Rentals Management
    
    
    /**
	 * Retrieves the bookID of a copy of a book if available
	 * 
	 * @param bookTitle the title of the book
	 * @return the unique book ID of a copy of the book or the message "Not available"
	 * @throws LibException  an exception if the book is not present in the archive
	 */
    public String getAvailableBook(String bookTitle) throws LibException {
        if(!this.libri.stream().map(entry->entry.getTitolo()).collect(Collectors.toList()).contains(bookTitle)){
			throw new LibException();
		}
		Map<String, Integer> ciao = this.copieLibere.get(bookTitle);
		String r = null;
		for(Map.Entry<String, Integer> entry: ciao.entrySet()){
			if(entry.getValue() == 1){
				ciao.put(entry.getKey(), 0);
				r = entry.getKey();
			}
		}
		if(r == null) r = "Not available";
		return r;
    }   

    /**
	 * Starts a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param startingDate the starting date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is already renting a book, or if the book copy is already rented
	 */
	public void startRental(String bookID, String readerID, String startingDate) throws LibException {
		if((!this.lettori.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(readerID)) || (!this.totaliCopie.keySet().stream().collect(Collectors.toList()).contains(bookID))) throw new LibException();
		for(Noleggio n: this.noleggi){
			if((n.getLettore().equals(readerID) || n.getCopia().equals(bookID)) && n.isFinito() == false){
				throw new LibException();
			}
			if(n.getLettore().equals(readerID) && n.getCopia().equals(bookID) && n.isFinito() == true){
				n.setInizio(startingDate);
				return;
			}
		}
		String titolo = this.totaliCopie.get(bookID);
		Noleggio nuovo = new Noleggio(startingDate, bookID, readerID, titolo);
		this.noleggi.add(nuovo);
		for(Noleggio n: this.noleggi){
			System.out.println(n.toString());
		}
		return;
    }
    
	/**
	 * Ends a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param endingDate the ending date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is not renting a book, or if the book copy is not rented
	 */
    public void endRental(String bookID, String readerID, String endingDate) throws LibException {
		if((!this.lettori.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(readerID)) || (!this.totaliCopie.keySet().stream().collect(Collectors.toList()).contains(bookID))) throw new LibException();
		for(Noleggio n: this.noleggi){
			if(n.getCopia().equals(bookID) && n.getLettore().equals(readerID)) n.setFinito(endingDate);
		}
		String bookTitle = null;
		for(Book b: this.libri){
			for(String s: b.getCopie()){
				if(s.equals(bookID)) bookTitle = b.getTitolo();
			}
		}
		Map<String, Integer> ciao = this.copieLibere.get(bookTitle);
		for(Map.Entry<String, Integer> entry: ciao.entrySet()){
			if(entry.getKey().equals(bookID)){
				ciao.put(entry.getKey(), 1);
			}
		}
    }
    
    
   /**
	* Retrieves the list of readers that rented a specific book.
	* It takes a unique book ID as input, and returns the readers' reader IDs and the starting and ending dates of each rental
	* 
	* @param bookID the unique book ID of the book copy
	* @return the map linking reader IDs with rentals starting and ending dates
	* @throws LibException  an exception if the book copy or the reader are not present in the archive,
	* if the reader is not renting a book, or if the book copy is not rented
	*/
    public SortedMap<String, String> getRentals(String bookID) throws LibException {
        SortedMap<String, String> finali = new TreeMap<>();
		String tmp = null;
		for(Noleggio n: this.noleggi){
			if(n.getCopia().equals(bookID)){
				if(n.isFinito() == false){
					tmp = "ONGOING";
				}
				else{
					tmp = n.getEnd();
				}
				finali.put(n.getLettore(), n.getDate()+" "+tmp);
			}
		}
		return finali;
    }
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param donatedTitles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
		for(String s: donatedTitles.split(",")){
			this.addBook(s);
		}
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
        Map<String, String> finale = new HashMap<>();
		for(Noleggio n: this.noleggi){
			if(n.isFinito() == false){
				finale.put(n.getLettore(), n.getCopia());
			}
		}
		return finale;
    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {
		for(String s: this.totaliCopie.keySet().stream().collect(Collectors.toList())){
			if(!noleggi.stream().map(entry->entry.getCopia()).collect(Collectors.toList()).contains(s)){
				this.totaliCopie.remove(s);
			}
		}
    }
    	
    // R5: Stats
    
    /**
	* Finds the reader with the highest number of rentals
	* and returns their unique ID.
	* 
	* @return the uniqueID of the reader with the highest number of rentals
	*/
    public String findBookWorm() {
		String s = "";
		int max = 0;
        for(Lettore l: this.lettori){
			int counter = 0;
			for(Noleggio n: this.noleggi){
				if(n.getLettore().equals(l.getId())){
					counter++;
				}
			}
			if(max<counter){
				max = counter;
				s = l.getId();
			}
		}
		return s;
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String,Integer> rentalCounts() {
        Map<String, Integer> finale = new HashMap<>();
		for(String s: this.libri.stream().map(entry->entry.getTitolo()).collect(Collectors.toList())){
			int counter = 0;
			for(Noleggio n: this.noleggi){
				if(n.getTitotlo().equals(s)){
					counter++;
				}
			}
			if(counter != 0) finale.put(s, counter);
		}
		System.out.println(finale);
		return finale;
    }

}
