package diet;

// GESTIONE ORARI
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	
	protected String name;
	protected Collection<NutritionalElement> products = new ArrayList<>();
	protected Collection<NutritionalElement> rawMaterials = new ArrayList<>();
	protected ArrayList<Menu> menus = new ArrayList<>();
	protected ArrayList<String> hours = new ArrayList<>();
	protected ArrayList<Order> orders = new ArrayList<>();

	public Restaurant(String name, Collection<NutritionalElement> rawMaterials, Collection<NutritionalElement> products){
		this.name = name;
		this.products = products;
		this.rawMaterials = rawMaterials;
	}

	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return this.name;
	}

	public void addOrder(Order o){
		orders.add(o);
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
	public void setHours(String ... hm) {
		// dispari non fare nulla
		if((hm.length % 2) != 0) return;
		else{
			for(String s: hm){
				this.hours.add(s);
			}
		}
	}

	/**
	 * Checks whether the restaurant is open at the given time.
	 *
	 * @param time time to check
	 * @return {@code true} is the restaurant is open at that time
	 */
	public boolean isOpenAt(String time){
		// uso un formatter per gli oggetti LocalTime in modo da essere flessibile nella gestione sia di orari HH:MM sia H:MM
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("[H][:][m]")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
		LocalTime start, end, t = LocalTime.parse(time, formatter);
		boolean flag = false;
		for(int i = 0; i+1 < this.hours.size(); i+=2){
			start = LocalTime.parse(this.hours.get(i), formatter);
			end = LocalTime.parse(this.hours.get(i+1), formatter);
			if((t.isAfter(start) || t.equals(start)) && t.isBefore(end)) flag = true;
		}
		return flag;
	}

	public String nextOpen(String time){
		// uso un formatter per gli oggetti LocalTime in modo da essere flessibile nella gestione sia di orari HH:MM sia H:MM
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("[H][:][m]")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
		LocalTime start, end, t = LocalTime.parse(time, formatter), hour;
		boolean flag = false;
		String tmp = null;
		for(int i = 0; i+1 < this.hours.size(); i+=2){
			start = LocalTime.parse(this.hours.get(i), formatter);
			end = LocalTime.parse(this.hours.get(i+1), formatter);
			if((t.isAfter(start) || t.equals(start)) && t.isBefore(end)) flag = true;
		}
		if(flag) tmp = time;
		else {
			for(int i = 0; i < this.hours.size(); i++){
				hour = LocalTime.parse(this.hours.get(i), formatter);
				if(t.isBefore(hour)){
					tmp = hour.toString();
					break;
				}
			}
		}
		return tmp;
	}

	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu	the menu
	 */
	public void addMenu(Menu menu) {
		this.menus.add(menu);
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		for(Menu m : this.menus){
			if(m.getName().equals(name)) return m;
		}
		System.out.println("Nessun menu trovato!"); // debug
		return null;
	}

	/**
	 * Retrieve all order with a given status with all the relative details in text format.
	 *
	 * @param status the status to be matched
	 * @return textual representation of orders
	 */
	public String ordersWithStatus(OrderStatus status) {
		String s = "";
		Collections.sort(this.orders, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2){
				int comparisonR = o1.getRestaurant().getName().compareTo(o2.getRestaurant().getName());
                if (comparisonR != 0) {
                    return comparisonR;
                }
                // Se i ristoranti sono uguali, confronta i clienti
                else{
					int comparisonC = o1.getClient().getFirstName().compareTo(o2.getClient().getFirstName());
					if (comparisonC != 0) {
						return comparisonC;
                	}
					// Se i clienti hanno stesso nome. confronta gli orari
					else {
						int comparisonH = o1.getHour().compareTo(o2.getHour());
						return comparisonH;
					}
				}
			}
		});
		for(Order o : this.orders){
			if(o.getStatus().equals(status)){
				s += o.toString();
			}
		}
		return s;
	}
}
