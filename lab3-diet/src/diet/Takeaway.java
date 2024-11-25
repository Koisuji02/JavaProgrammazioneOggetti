package diet;

import java.util.*;


/**
 * Represents a takeaway restaurant chain.
 * It allows managing restaurants, customers, and orders.
 */
public class Takeaway {

	protected Collection<NutritionalElement> rawMaterials = new ArrayList<>();
	protected Collection<NutritionalElement> products = new ArrayList<>();
	protected Collection<Restaurant> restaurants = new ArrayList<>();
	protected Collection<Customer> clients = new ArrayList<>();
	//protected Collection<NutritionalElement> recipes = new ArrayList<>();
	protected Collection<NutritionalElement> menus = new ArrayList<>();

	/**
	 * Constructor
	 * @param food the reference {@link Food} object with materials and products info.
	 */
	public Takeaway(Food food){
		this.rawMaterials = food.rawMaterials();
		this.products = food.products();
		//this.recipes = food.recipes();
		this.menus = food.menus();
	}

	/**
	 * Creates a new restaurant with a given name
	 *
	 * @param restaurantName name of the restaurant
	 * @return the new restaurant
	 */
	public Restaurant addRestaurant(String restaurantName) {
		Restaurant r = new Restaurant(restaurantName, this.rawMaterials, this.products);
		restaurants.add(r);
		return r;
	}

	/**
	 * Retrieves the names of all restaurants
	 *
	 * @return collection of restaurant names
	 */
	public Collection<String> restaurants() {
		Collection<String> nomi = new ArrayList<>();
		for(Restaurant r: this.restaurants){
			nomi.add(r.getName());
		}
		// CAPIRE SE MANTENERE ORDINAMENTO O TOGLIERE
		String[] v = nomi.toArray(new String[0]);
		Arrays.sort(v);
		nomi = Arrays.asList(v);
		return nomi;
	}

	/**
	 * Creates a new customer for the takeaway
	 * @param firstName first name of the customer
	 * @param lastName	last name of the customer
	 * @param email		email of the customer
	 * @param phoneNumber mobile phone number
	 *
	 * @return the object representing the newly created customer
	 */
	public Customer registerCustomer(String firstName, String lastName, String email, String phoneNumber) {
		Customer c = new Customer(firstName, lastName, email, phoneNumber);
		this.clients.add(c);
		return c;
	}

	/**
	 * Retrieves all registered customers
	 *
	 * @return sorted collection of customers
	 */
	public Collection<Customer> customers(){
		ArrayList<Customer> tmp = new ArrayList<>(this.clients);
		Collections.sort(tmp, new Comparator<Customer>() {
			@Override
            public int compare(Customer c1, Customer c2) {
                int cognomeComparison = c1.getLastName().compareTo(c2.getLastName());
                if (cognomeComparison != 0) {
                    return cognomeComparison;
                }
                // Se i cognomi sono uguali, confronta i nomi
                return c1.getFirstName().compareTo(c2.getFirstName());
            }
		});
		this.clients = tmp;
		return this.clients;
	}


	/**
	 * Creates a new order for the chain.
	 *
	 * @param customer		 customer issuing the order
	 * @param restaurantName name of the restaurant that will take the order
	 * @param time	time of desired delivery
	 * @return order object
	 */
	public Order createOrder(Customer customer, String restaurantName, String time) {
		Restaurant r = null;
		for(Restaurant t: this.restaurants){
			if(t.getName().equals(restaurantName)) r = t;
		}
		time = r.nextOpen(time);
		Order o = new Order(customer, r, time);
		r.addOrder(o);
		return o;
	}

	/**
	 * Find all restaurants that are open at a given time.
	 *
	 * @param time the time with format {@code "HH:MM"}
	 * @return the sorted collection of restaurants
	 */
	public Collection<Restaurant> openRestaurants(String time){
		List<Restaurant> tmp = new ArrayList<>();
		for(Restaurant r : this.restaurants){
			if(r.isOpenAt(time)){
				tmp.add(r);
			}
		}
		// sorting dei ristoranti trovati
		Collections.sort(tmp, new Comparator<Restaurant>() {
			@Override
			public int compare(Restaurant r1, Restaurant r2) {
				return r1.getName().compareTo(r2.getName());
			}
		});
		
		return tmp;
	}
}

