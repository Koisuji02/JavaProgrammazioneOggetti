package diet;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents and order issued by an {@link Customer} for a {@link Restaurant}.
 *
 * When an order is printed to a string is should look like:
 * <pre>
 *  RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
 *  	MENU_NAME_1->MENU_QUANTITY_1
 *  	...
 *  	MENU_NAME_k->MENU_QUANTITY_k
 * </pre>
 */
public class Order {

	protected OrderStatus os;
	protected Customer client;
	protected Restaurant r;
	protected String hour;
	protected PaymentMethod pm;
	// uso la TreeMap per fare in modo che gli elementi siano ordinati per nome del menu
	protected Map<NutritionalElement,Integer> menus = new TreeMap<>(Comparator.comparing(NutritionalElement::getName));


	public Order(Customer client, Restaurant r, String hour){
		this.client = client;
		this.r = r;
		this.hour = hour;
		this.os = OrderStatus.ORDERED;
		this.pm = PaymentMethod.CASH;
	}

	/**
	 * Possible order statuses
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	/**
	 * Accepted payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD
	}

	/**
	 * Set payment method
	 * @param pm the payment method
	 */
	public void setPaymentMethod(PaymentMethod pm) {
		this.pm = pm;
	}

	/**
	 * Retrieves current payment method
	 * @return the current method
	 */
	public PaymentMethod getPaymentMethod() {
		return this.pm;
	}

	/**
	 * Set the new status for the order
	 * @param os new status
	 */
	public void setStatus(OrderStatus os) {
		this.os = os;
	}

	/**
	 * Retrieves the current status of the order
	 *
	 * @return current status
	 */
	public OrderStatus getStatus() {
		return this.os;
	}

	public Customer getClient(){
		return this.client;
	}

	public String getHour(){
		return this.hour;
	}

	public Restaurant getRestaurant(){
		return this.r;
	}

	/**
	 * Add a new menu to the order with a given quantity
	 *
	 * @param menu	menu to be added
	 * @param quantity quantity
	 * @return the order itself (allows method chaining)
	 */
	public Order addMenus(String menu, int quantity) {
		Menu m = this.r.getMenu(menu);
		NutritionalElement element;
		// se è già presente il menu, sovrascrivo la quantità
		for(Map.Entry<NutritionalElement,Integer> i : this.menus.entrySet()){
			element = i.getKey();
			if(element.getName().equals(menu)){
				i.setValue(quantity);
				return this;
			}
		}
		// altrimenti lo metto nella Map
		this.menus.put(m, quantity);
		
		return this;
	}

	// ToString per l'Order
	@Override
	public String toString(){
		String s = this.r.getName()+", "+this.client.getFirstName()+" "+this.client.getLastName()+" : ("+this.hour+"):\n";
		for(Map.Entry<NutritionalElement,Integer> i : this.menus.entrySet()){
			s +="\t"+i.getKey().getName()+"->"+i.getValue()+"\n";
		}
		//System.out.println(s);
		return s;
	}
	
}
