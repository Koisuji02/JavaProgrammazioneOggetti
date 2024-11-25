package delivery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Delivery {
	// R1
	public class Restaurant{
		private String name;
		private String category;
		private List<Integer> rating = new ArrayList<>();
		private double media;
		private List<Dish> piatti = new ArrayList<>();
		public Restaurant(String name, String category){
			this.name = name;
			this.category = category;
		}
		public String getName(){
			return this.name;
		}
		public String getCategory(){
			return this.category;
		}
		public void addDish(Dish d){
			this.piatti.add(d);
		}
		public List<Dish> getDishes(){
			return this.piatti;
		}
		public void setRating(int rating){
			this.rating.add(rating);
		}
		public List<Integer> getRating(){
			return this.rating;
		}
		public void setAverage(double average){
			this.media = average;
		}
		public double getMedia(){
			return this.media;
		}
	}

	public class Dish{
		private String name;
		private String restaurantName;
		private float price;
		public Dish(String name, String restaurantName, float price){
			this.name = name;
			this.restaurantName = restaurantName;
			this.price = price;
		}
		public String getName(){
			return this.name;
		}
		public String getRestaurant(){
			return this.restaurantName;
		}
		public float getPrice(){
			return this.price;
		}
	}

	public class Order{
		private String customerName;
		private String restaurantName;
		private int deliveryTime;
		private int deliveryDistance;
		private int n;
		private boolean risolto; // va a true se è stato già risolto l'ordine
		private Map<String, Integer> piatti = new HashMap<>(); // (nomePiatto,quantità)
		public Order(String dishName[], int quantity[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance){
			this.customerName = customerName;
			this.restaurantName = restaurantName;
			this.deliveryTime = deliveryTime;
			this.deliveryDistance = deliveryDistance;
			Map<String,Integer> tmp = new HashMap<>();
			for(int i = 0; i < dishName.length; ++i){
				tmp.put(dishName[i], quantity[i]);
			}
			this.n = n_ordine;
			this.risolto = false;
		}
		public String getCustomer(){
			return this.customerName;
		}
		public String getRestaurant(){
			return this.restaurantName;
		}
		public int getDeliveryTime(){
			return this.deliveryTime;
		}
		public int getDeliveryDistance(){
			return this.deliveryDistance;
		}
		public Map<String,Integer> getPiatti(){
			return this.piatti;
		}
		public int getN(){
			return this.n;
		}
		public boolean isRisolto(){
			return this.risolto;
		}
		public void setRisolto(){
			this.risolto = true;
		}
	}

	public List<String> categorie = new ArrayList<>();
	public List<Restaurant> ristoranti = new ArrayList<>();
	public Collection<Dish> piatti = new ArrayList<>();
	public ArrayList<Order> ordini = new ArrayList<>();
	int n_ordine = 1;
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		if(this.categorie.contains(category)) throw new DeliveryException();
		this.categorie.add(category);
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return this.categorie;
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
		if(!this.categorie.contains(category)) throw new DeliveryException();
		Restaurant nuovo = new Restaurant(name, category);
		this.ristoranti.add(nuovo);
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
		List<String> finale = new ArrayList<>();
        for(Restaurant r: this.ristoranti){
			if(r.getCategory().equals(category)){
				finale.add(r.getName());
			}
		}
		finale.sort((s1,s2)->s1.compareTo(s2));
		return finale;
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
		for(Restaurant r: this.ristoranti){
			if(r.getName().equals(restaurantName)){
				if(r.getDishes().stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(name)) throw new DeliveryException();
				Dish nuovo = new Dish(name, restaurantName, price);
				r.addDish(nuovo);
				this.piatti.add(nuovo);
			}
		}
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
		Map<String,List<String>> finale = new HashMap<>();
        for(Restaurant r: this.ristoranti){
			List<String> cibi = new ArrayList<>();
			for(Dish d: r.getDishes()){
				if(d.getPrice()>=minPrice && d.getPrice()<=maxPrice) cibi.add(d.getName());
			}
			if(!cibi.isEmpty()) finale.put(r.getName(), cibi);
		}
		return finale;
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
        List<String> finale = new ArrayList<>();
		for(Restaurant r: this.ristoranti){
			if(r.getName().equals(restaurantName)){
				for(String s: r.getDishes().stream().map(entry->entry.getName()).collect(Collectors.toList())){
					finale.add(s);
				}
			}
		}
		finale.sort((s1,s2)->s1.compareTo(s2));
		return finale;
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
        List<String> finale = new ArrayList<>();
		for(Restaurant r: this.ristoranti){
			if(r.getCategory().equals(category)){
				for(String s: r.getDishes().stream().map(entry->entry.getName()).collect(Collectors.toList())){
					finale.add(s);
				}
			}
		}
		finale.sort((s1,s2)->s1.compareTo(s2));
		return finale;
	}
	
	//R3
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
	    Order nuovo = new Order(dishNames, quantities, customerName, restaurantName, deliveryTime, deliveryDistance);
		this.ordini.add(nuovo);
		n_ordine += 1;
		return n_ordine-1;
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		List<Integer> finale = new ArrayList<>();
		if(this.ordini.isEmpty()) return finale;
        for(int i = 0; i < Math.min(maxOrders, this.ordini.size()); ++i){
			if(this.ordini.get(i).isRisolto() == false && this.ordini.get(i).getDeliveryTime() == deliveryTime && this.ordini.get(i).getDeliveryDistance()<= maxDistance){
				finale.add(this.ordini.get(i).getN());
				this.ordini.get(i).setRisolto();
			}
		}
		return finale;
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
		int counter = 0;
        for(Order o: this.ordini){
			if(o.isRisolto() == false) counter += 1;
		}
		return counter;
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		if(rating < 0 && rating > 5){
			return;
		}
		for(Restaurant r: this.ristoranti){
			if(r.getName().equals(restaurantName)) r.setRating(rating);
		}
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
        List<String> finale = new ArrayList<>();
		for(Restaurant r: this.ristoranti){
			int totale = 0;
			for(int v: r.getRating()) totale += v;
			if(r.getRating().size()>0) r.setAverage((double)totale/(double)r.getRating().size());
			else r.setAverage(0.0);
		}
		this.ristoranti.sort((s1,s2)->Double.compare(s2.getMedia(), s1.getMedia()));
		for(Restaurant r: this.ristoranti){
			if(r.getMedia() > 0) finale.add(r.getName());
		}
		return finale;
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
        Map<String,Long> finale = new HashMap<>();
		for(String s: this.categorie){
			long counter = 0;
			for(Order o: this.ordini){
				for(Restaurant r: this.ristoranti){
					if(o.getRestaurant().equals(r.getName()) && s.equals(r.getCategory())) counter += 1;
				}
			}
			finale.put(s,counter);
		}
		return finale;
	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
		for(String s: this.restaurantsAverageRating()){
			return s;
		}
		return null;
	}
}
