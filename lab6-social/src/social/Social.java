package social;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Social {


	public class Person{
		public String name;
		public String surname;
		public String code;
		public Collection<Person> amici = new ArrayList<>();
		public Collection<Post> posts = new ArrayList<>();
		public Person(String name, String surname, String code){
			this.name = name;
			this.surname = surname;
			this.code = code;
		}
		public String getName(){
			return this.name;
		}
		public String getSurname(){
			return this.surname;
		}
		public String getCode(){
			return this.code;
		}
		public Collection<Person> getAmici(){
			return this.amici;
		}
		public Collection<Post> getPosts(){
			return this.posts;
		}
		public void addAmico(Person tmp){
			boolean flag = false;
			for(Person p : this.amici){
				if(tmp.getCode().equals(p.getCode())) flag = true;
			}
			if(flag == false) this.amici.add(tmp);
		}
	}

	public class Post{
		public String author;
		public String text;
		public long timestamp;
		public String id;
		public Post(String author, String text, long timestamp, String id){
			this.author = author;
			this.text = text;
			this.timestamp = timestamp;
			this.id = id;
		}
		public String getId(){
			return this.id;
		}
		public String getAuthor(){
			return this.author;
		}
		public String getText(){
			return this.text;
		}
		public long getTimestamp(){
			return this.timestamp;
		}
	}

	public Collection<Person> persone = new ArrayList<>();
	public Map<String, ArrayList<Person>> gruppi = new HashMap<>();
	public Collection<Post> posts = new ArrayList<>();
	/**
	 * Creates a new account for a person
	 * 
	 * @param code	nickname of the account
	 * @param name	first name
	 * @param surname last name
	 * @throws PersonExistsException in case of duplicate code
	 */
	public void addPerson(String code, String name, String surname) throws PersonExistsException {
		for(Person p: this.persone){
			if(p.getCode().equals(code)) throw new PersonExistsException();
		}
		Person persona = new Person(name, surname, code);
		this.persone.add(persona);
	}

	/**
	 * Retrieves information about the person given their account code.
	 * The info consists in name and surname of the person, in order, separated by blanks.
	 * 
	 * @param code account code
	 * @return the information of the person
	 * @throws NoSuchCodeException
	 */
	public String getPerson(String code) throws NoSuchCodeException {
		String s = "";
		boolean flag = false;
		for(Person p: this.persone){
			if(p.getCode().equals(code)){
				s = p.getCode()+" "+p.getName()+" "+p.getSurname();
				flag = true;
			}
		}
		if(flag == false) throw new NoSuchCodeException();
		return s;
	}

	/**
	 * Define a friendship relationship between to persons given their codes.
	 * 
	 * Friendship is bidirectional: if person A is friend of a person B, that means that person B is a friend of a person A.
	 * 
	 * @param codePerson1	first person code
	 * @param codePerson2	second person code
	 * @throws NoSuchCodeException in case either code does not exist
	 */
	public void addFriendship(String codePerson1, String codePerson2) throws NoSuchCodeException {
		boolean flag1 = false, flag2 = false;
		for(Person p: this.persone){
			if(p.getCode().equals(codePerson1)) flag1 = true;
			if(p.getCode().equals(codePerson2)) flag2 = true;
		}
		if(flag1 == false || flag2 == false) throw new NoSuchCodeException();
		// lo faccio 2 volte perchè la prima è solo per il controllo, altrimenti aggiungerei solo parzialmente l'amicizia nel caso l'altro codice non esistesse
		Person p1 = null, p2 = null;
		for(Person p : this.persone){
			if(p.getCode().equals(codePerson1)){
				p1 = p;
			}
			if(p.getCode().equals(codePerson2)){
				p2 = p;
			}
		}
		p1.addAmico(p2); p2.addAmico(p1);
	}

	/**
	 * Retrieve the collection of their friends given the code of a person.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return the list of person codes
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> listOfFriends(String codePerson) throws NoSuchCodeException {
		boolean flag = false;
		Collection<String> tmp = new ArrayList<>();
		for(Person p: this.persone){
			if(p.getCode().equals(codePerson)){
				flag = true;
				for(Person amico: p.getAmici()){
					tmp.add(amico.getCode());
				}
			}
		}
		if(flag == false) throw new NoSuchCodeException();
		return tmp;
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriends(String codePerson) throws NoSuchCodeException {
		boolean flag = false;
		Collection<String> tmp = new ArrayList<>();
		for(Person p: this.persone){
			if(p.getCode().equals(codePerson)){
				flag = true;
				for(Person amico: p.getAmici()){
					for(Person amicoAmico: amico.getAmici()){
						if(!(p.getCode().equals(amicoAmico.getCode()))) 
							tmp.add(amicoAmico.getCode());
					}
				}
			}
		}
		if(flag == false) throw new NoSuchCodeException();
		return tmp;
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * The result has no duplicates.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriendsNoRepetition(String codePerson)	throws NoSuchCodeException {
		Collection<String> tmp = this.friendsOfFriends(codePerson);
		return tmp.stream().distinct().toList();
	}

	/**
	 * Creates a new group with the given name
	 * 
	 * @param groupName name of the group
	 */
	public void addGroup(String groupName) {
		this.gruppi.put(groupName, new ArrayList<>());
	}

	/**
	 * Retrieves the list of groups.
	 * 
	 * @return the collection of group names
	 */
	public Collection<String> listOfGroups() {
		Collection<String>  nomiGruppi = this.gruppi.keySet();
		return nomiGruppi;
	}

	/**
	 * Add a person to a group
	 * 
	 * @param codePerson person code
	 * @param groupName  name of the group
	 * @throws NoSuchCodeException in case the code or group name do not exist
	 */
	public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
		boolean flagG = false, flagP = false;
		for(String s : this.gruppi.keySet()){
			if(s.equals(groupName)){
				flagG = true;
				for(Person p: this.persone){
					if(p.getCode().equals(codePerson)){
						flagP = true;
						this.gruppi.get(s).add(p);
					}
				}
			}
		}
		if(flagG == false || flagP == false) throw new NoSuchCodeException();
	}

	/**
	 * Retrieves the list of people on a group
	 * 
	 * @param groupName name of the group
	 * @return collection of person codes
	 */
	public Collection<String> listOfPeopleInGroup(String groupName) {
		Collection<Person> tmp = this.gruppi.get(groupName);
		return tmp.stream().map(entry->entry.getCode()).toList();
	}

	/**
	 * Retrieves the code of the person having the largest
	 * group of friends
	 * 
	 * @return the code of the person
	 */
	public String personWithLargestNumberOfFriends() {
		long max = 0;
		String c = null;
		for(Person p : this.persone){
			try{
				if(listOfFriends(p.getCode()).size()>max){
					max = p.getAmici().size();
					c = p.getCode();
				}
			} catch(NoSuchCodeException e){
				System.out.println("Error!");
				return null;
			}
		}
		return c;
	}

	/**
	 * Find the code of the person with largest number
	 * of second level friends
	 * 
	 * @return the code of the person
	 */
	public String personWithMostFriendsOfFriends() {
		long max = 0;
		String c = null;
		for(Person p : this.persone){
			try{
				if(friendsOfFriends(p.getCode()).size()>max){
					max = p.getAmici().size();
					c = p.getCode();
				}
			} catch(NoSuchCodeException e){
				System.out.println("Error!");
				return null;
			}
		}
		return c;
	}

	/**
	 * Find the name of group with the largest number of members
	 * 
	 * @return the name of the group
	 */
	public String largestGroup() {
		long max = 0;
		String c = null;
		for(Map.Entry<String, ArrayList<Person>> entry: this.gruppi.entrySet()){
			if(entry.getValue().size()>max){
				max = entry.getValue().size();
				c = entry.getKey();
			}
		}
		return c;
	}

	/**
	 * Find the code of the person that is member of
	 * the largest number of groups
	 * 
	 * @return the code of the person
	 */
	public String personInLargestNumberOfGroups() {
		Map<String, Integer> tmp = new HashMap<>();
		for(Person p : this.persone){
			int counter = 0;
			for(Map.Entry<String, ArrayList<Person>> entry: this.gruppi.entrySet()){
				for(Person h : entry.getValue()){
					if(h.getCode().equals(p.getCode())){
						counter++;
					}
				}
			}
			tmp.put(p.getCode(), counter);
		}
		int max = 0;
		String s = null;
		for(Map.Entry<String, Integer> entry: tmp.entrySet()){
			if(entry.getValue()>max){
				max = entry.getValue();
				s = entry.getKey();
			}
		}
		return s;
	}

	/**
	 * add a new post by a given account
	 * @param author the id of the post author
	 * @param text the content of the post
	 * @return a unique id of the post
	 */
    public String post(String author, String text) {
		String id = author+System.currentTimeMillis();
		Post p = new Post(author, text, System.currentTimeMillis(), id);
		this.posts.add(p);
		for(Person h : this.persone){
			if(h.getCode().equals(author)) h.getPosts().add(p);
		}
		return id;
    }

	/**
	 * retrieves the content of the given post
	 * @param author	the author of the post
	 * @param pid		the id of the post
	 * @return the content of the post
	 */
    public String getPostContent(String author, String pid) {
		for(Person p: this.persone){
			if(p.getCode().equals(author)){
				for(Post o : p.getPosts()){
					if(o.getId().equals(pid)) return o.getText();
				}
			}
		}
		return null;
    }

	/**
	 * retrieves the timestamp of the given post
	 * @param author	the author of the post
	 * @param pid		the id of the post
	 * @return the timestamp of the post
	 */
    public long getTimestamp(String author, String pid) {
		for(Person p: this.persone){
			if(p.getCode().equals(author)){
				for(Post o : p.getPosts()){
					if(o.getId().equals(pid)) return o.getTimestamp();
				}
			}
		}
		return -1;
    }

	/**
	 * returns the list of post of a given author paginated 
	 * 
	 * @param author	author of the post
	 * @param pageNo	page number (starting at 1)
	 * @param pageLength page length
	 * @return the list of posts id
	 */
    public List<String> getPaginatedUserPosts(String author, int pageNo, int pageLength) {
		Collection<Post> f = new ArrayList<>();
		for(Person p : this.persone){
			if(p.getCode().equals(author)) f = p.getPosts();
		}
		// ordino inverso
		List<Post> sortedPosts = f.stream()
			.sorted(Comparator.comparing(Post::getTimestamp).reversed())
			.collect(Collectors.toList());

		List<String> tmp = new ArrayList<>();
		for(int i = (pageNo-1)*pageLength; i < (pageNo)*pageLength; i++){
			tmp.add(sortedPosts.get(i).getId());
		}
		return tmp;
    }

	/**
	 * returns the paginated list of post of friends 
	 * 
	 * the returned list contains the author and the id of a post separated by ":"
	 * 
	 * @param author	author of the post
	 * @param pageNo	page number (starting at 1)
	 * @param pageLength page length
	 * @return the list of posts key elements
	 */
	public List<String> getPaginatedFriendPosts(String author, int pageNo, int pageLength) {
		Collection<Post> f = new ArrayList<>();
		for(Person p : this.persone){
			if(p.getCode().equals(author)){
				for(Person h : p.getAmici()){
					f.addAll(h.getPosts());
				}
			}
		}
		List<String> sortedPosts = f.stream()
			.sorted(Comparator.comparing(Post::getTimestamp).reversed())
			.map(entry -> entry.getAuthor()+":"+entry.getId())
			.collect(Collectors.toList());

		List<String> tmp = new ArrayList<>();
		for(int i = (pageNo-1)*pageLength; i < (pageNo)*pageLength; i++){
			tmp.add(sortedPosts.get(i));
		}
		return tmp;
	}
}