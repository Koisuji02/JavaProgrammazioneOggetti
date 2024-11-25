package discounts;
import java.util.*;
import java.util.stream.Collectors;

public class Discounts {

	public class Customer{
		public String name;
		public int card;
		public Customer(String name){
			this.name = name;
			this.card = number;
		}
		public String getName(){
			return this.name;
		}
		public int getCard(){
			return this.card;
		}
	}

	public class Product{
		public String category;
		public String code;
		public double price;
		public int discount;
		public Product(String category, String code, double price){
			this.category = category;
			this.code = code;
			this.price = price;
		}
		public String getCategory(){
			return this.category;
		}
		public String getCode(){
			return this.code;
		}
		public double getPrice(){
			return this.price;
		}
		public int getDiscount(){
			return this.discount;
		}
		public void setDiscount(int discount){
			this.discount = discount;
		}
	}

	public class Purchase{
		public int card; // se carta = -1, vuol dire no acquisto con carta -> no sconto
		public int code;
		public double total;
		public double discount; // 0 senza carta
		public int units;
		public Map<String,Integer> acquistati = new HashMap<>();
		public Purchase(Map<String,Integer> carrello){
			this.code = acquisto;
			this.acquistati = carrello;
		}
		public void setCard(int card){
			this.card = card;
		}
		public int getCard(){
			return this.card;
		}
		public int getCode(){
			return this.code;
		}
		public Map<String,Integer> getAcquistati(){
			return this.acquistati;
		}
		public double getTotal(){
			return this.total;
		}
		public double getDiscount(){
			return this.discount;
		}
		public int getUnits(){
			return this.units;
		}
		public void setTotal(double total){
			this.total = total;
		}
		public void setDiscount(double discount){
			this.discount = discount;
		}
		public void setUnits(int units){
			this.units = units;
		}
	}

	int number = 1;
	int acquisto = 1;
	public Collection<Customer> clienti = new ArrayList<>();
	public Collection<Product> prodotti = new ArrayList<>();
	public Collection<Purchase> acquisti = new ArrayList<>();

	//R1
	public int issueCard(String name) {
		Customer nuovo = new Customer(name);
		this.clienti.add(nuovo);
		number += 1;
	    return number-1; // per tornare quello corrente
	}
	
    public String cardHolder(int cardN) {
        for(Customer c: this.clienti){
			if(c.getCard()==cardN) return c.getName();
		}
		return null;
    }
    

	public int nOfCards() {
	    return this.clienti.size();
	}
	
	//R2
	public void addProduct(String categoryId, String productId, double price) throws DiscountsException {
		if(this.prodotti.stream().map(entry->entry.getCode()).collect(Collectors.toList()).contains(productId)) throw new DiscountsException();
		Product nuovo = new Product(categoryId, productId, price);
		this.prodotti.add(nuovo);
	}
	
	public double getPrice(String productId) throws DiscountsException {
		if(!this.prodotti.stream().map(entry->entry.getCode()).collect(Collectors.toList()).contains(productId)) throw new DiscountsException();
		for(Product p: this.prodotti){
			if(p.getCode().equals(productId)) return p.getPrice();
		}
		return -1;
	}

	public int getAveragePrice(String categoryId) throws DiscountsException {
		if(!this.prodotti.stream().map(entry->entry.getCategory()).collect(Collectors.toList()).contains(categoryId)) throw new DiscountsException();
        Collection<Product> tmp = new ArrayList<>();
		for(Product p: this.prodotti){
			if(p.getCategory().equals(categoryId)) tmp.add(p);
		}
		double totale = 0;
		for(Product k: tmp){
			totale += k.getPrice();
		}
		int media = (int)Math.round((totale/tmp.size())); // capire come trasformare double in int
		return media;
	}
	
	//R3
	public void setDiscount(String categoryId, int percentage) throws DiscountsException {
		if(percentage < 0 || percentage > 50) throw new DiscountsException();
		if(!this.prodotti.stream().map(entry->entry.getCategory()).collect(Collectors.toList()).contains(categoryId)) throw new DiscountsException();
		for(Product p: this.prodotti){
			if(p.getCategory().equals(categoryId)) p.setDiscount(percentage);
		}
	}

	public int getDiscount(String categoryId) {
        for(Product p: this.prodotti){
			if(p.getCategory().equals(categoryId)) return p.getDiscount();
		}
		return -1;
	}

	//R4
	public int addPurchase(int cardId, String... items) throws DiscountsException {
		Map<String,Integer> tmp = new HashMap<>();
		double totale = 0.0;
		double sconto = 0.0;
		int units = 0;
		for(String stringa: items){
			String[] parti = stringa.split(":");
			if(!this.prodotti.stream().map(entry->entry.getCode()).collect(Collectors.toList()).contains(parti[0])){
				throw new DiscountsException();
			}
			int quantita = Integer.parseInt(parti[1]);
			for(Product k: this.prodotti){
				if(k.getCode().equals(parti[0])){
					units += quantita;
					if(k.getDiscount()>0){
						sconto += quantita*(k.getPrice()*k.getDiscount()/100);
						totale += quantita*(k.getPrice())*(1-k.getDiscount()/100);
					} else{
						totale += quantita*(k.getPrice());
					}
				}
			}
			tmp.put(parti[0], quantita);
		}
		Purchase p = new Purchase(tmp);
		p.setTotal(totale-sconto);
		p.setDiscount(sconto);
		p.setUnits(units);
		p.setCard(cardId);
		this.acquisti.add(p);
		acquisto += 1;
		return acquisto-1;
	}

	public int addPurchase(String... items) throws DiscountsException {
        Map<String,Integer> tmp = new HashMap<>();
		double totale = 0.0;
		int units = 0;
		for(String stringa: items){
			String[] parti = stringa.split(":");
			if(!this.prodotti.stream().map(entry->entry.getCode()).collect(Collectors.toList()).contains(parti[0])){
				throw new DiscountsException();
			}
			int quantita = Integer.parseInt(parti[1]);
			for(Product k: this.prodotti){
				if(k.getCode().equals(parti[0])){
					units += quantita;
					totale += quantita*(k.getPrice());
				}
			}
			tmp.put(parti[0], quantita);
		}
		Purchase p = new Purchase(tmp);
		p.setTotal(totale);
		p.setDiscount(0.0); // no sconto senza carta
		p.setUnits(units);
		p.setCard(-1); // se carta = -1, vuol dire no acquisto con carta -> no sconto
		this.acquisti.add(p);

		for(Purchase a: this.acquisti) System.out.println(a.getCode()+" "+a.getTotal()+" "+a.getDiscount()+" "+a.getAcquistati());
		for(Product b: this.prodotti) System.out.println(b.getCode()+" "+b.getPrice()+" "+b.getDiscount()+" "+b.getCategory());

		acquisto += 1;
		return acquisto-1;
	}
	
	public double getAmount(int purchaseCode) {
        for(Purchase p: this.acquisti){
			if(p.getCode() == purchaseCode) return p.getTotal();
		}
		return -1.0;
	}
	
	public double getDiscount(int purchaseCode)  {
        for(Purchase p: this.acquisti){
			if(p.getCode() == purchaseCode) return p.getDiscount();
		}
		return -1.0;
	}
	
	public int getNofUnits(int purchaseCode) {
        for(Purchase p: this.acquisti){
			if(p.getCode() == purchaseCode) return p.getUnits();
		}
		return -1;
	}
	
	//TODO DA QUA
	//R5
	public SortedMap<Integer, List<String>> productIdsPerNofUnits() {
		//! QUI HO SBAGLIATO
        /* Map<Integer,List<String>> tmp = this.acquisti.stream().map(entry->entry.getAcquistati()).flatMap(s->s.entrySet().stream()).collect(Collectors.groupingBy(Map.Entry::getValue, TreeMap::new, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
		SortedMap<Integer, List<String>> finale = new TreeMap<>(tmp);
		return finale; */
		
		// Create a map to collect units per product
		Map<String, Integer> unitsPerProduct = new HashMap<>();

		// Iterate over all purchases
		for (Purchase purchase : this.acquisti) {
			// Get the items purchased in this transaction
			Map<String, Integer> purchasedItems = purchase.getAcquistati();
	
			// Update unitsPerProduct with quantities from this purchase
			for (Map.Entry<String, Integer> entry : purchasedItems.entrySet()) {
				String productId = entry.getKey();
				int quantity = entry.getValue();
				unitsPerProduct.put(productId, unitsPerProduct.getOrDefault(productId, 0) + quantity);
			}
		}
	
		// Create a sorted map to store result with units as keys and lists of products as values
		SortedMap<Integer, List<String>> result = new TreeMap<>();
	
		// Populate the sorted map with products grouped by units
		for (Map.Entry<String, Integer> entry : unitsPerProduct.entrySet()) {
			String productId = entry.getKey();
			int units = entry.getValue();
			
			if (!result.containsKey(units)) {
				result.put(units, new ArrayList<>());
			}
			result.get(units).add(productId);
		}
	
		return result;
	}
	
	public SortedMap<Integer, Double> totalPurchasePerCard() {
		List<Integer> carte = new ArrayList<>();
		SortedMap<Integer,Double> finale = new TreeMap<>();
		for(Purchase p: this.acquisti){
			if((!carte.contains(p.getCard())) && p.getCard() != -1){
				carte.add(p.getCard());
			}
		}
		for(int carta: carte){
			double totale = 0.0;
			for(Purchase p: this.acquisti){
				if(p.getCard() == carta){
					totale += p.getTotal();
				}
			}
			finale.put(carta, totale);
		}
		return finale;
	}
	
	public double totalPurchaseWithoutCard() {
		double totale = 0.0;
        for(Purchase p: this.acquisti){
			if(p.getCard() == -1){ // no carta
				totale += p.getTotal();
			}
		}
		return totale;
	}
	
	public SortedMap<Integer, Double> totalDiscountPerCard() {
        List<Integer> carte = new ArrayList<>();
		SortedMap<Integer,Double> finale = new TreeMap<>();
		for(Purchase p: this.acquisti){
			if((!carte.contains(p.getCard())) && p.getCard() != -1){
				carte.add(p.getCard());
			}
		}
		for(int carta: carte){
			double sconto = 0.0;
			for(Purchase p: this.acquisti){
				if(p.getCard() == carta){
					sconto += p.getDiscount();
				}
			}
			finale.put(carta, sconto);
		}
		return finale;
	}


}
