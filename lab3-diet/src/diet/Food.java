package diet;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food{

	protected ArrayList<NutritionalElement> rawMaterials = new ArrayList<>();
	protected ArrayList<NutritionalElement> products = new ArrayList<>();
	protected ArrayList<NutritionalElement> recipes = new ArrayList<>();
	protected ArrayList<NutritionalElement> menus = new ArrayList<>();

	public class RawMaterial implements NutritionalElement{
		protected String name;
		protected double calories;
		protected double proteins;
		protected double carbs;
		protected double fat;
		protected boolean g100 = true;

		public RawMaterial(String name, double calories, double proteins, double carbs, double fat){
			this.name = name;
			this.calories = calories;
			this.proteins = proteins;
			this.carbs = carbs;
			this.fat = fat;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public double getCalories() {
			return this.calories;
		}

		@Override
		public double getProteins() {
			return this.proteins;
		}

		@Override
		public double getCarbs() {
			return this.carbs;
		}

		@Override
		public double getFat() {
			return this.fat;
		}

		@Override
		public boolean per100g() {
			return this.g100;
		}
	}

	public class Product implements NutritionalElement{
		protected String name;
		protected double calories;
		protected double proteins;
		protected double carbs;
		protected double fat;
		protected boolean g100 = false;

		public Product(String name, double calories, double proteins, double carbs, double fat){
			this.name = name;
			this.calories = calories;
			this.proteins = proteins;
			this.carbs = carbs;
			this.fat = fat;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public double getCalories() {
			return this.calories;
		}

		@Override
		public double getProteins() {
			return this.proteins;
		}

		@Override
		public double getCarbs() {
			return this.carbs;
		}

		@Override
		public double getFat() {
			return this.fat;
		}

		@Override
		public boolean per100g() {
			return this.g100;
		}
	}

	/**
	 * Define a new raw material.
	 * The nutritional values are specified for a conventional 100g quantity
	 * @param name unique name of the raw material
	 * @param calories calories per 100g
	 * @param proteins proteins per 100g
	 * @param carbs carbs per 100g
	 * @param fat fats per 100g
	 */
	
	public void defineRawMaterial(String name, double calories, double proteins, double carbs, double fat) {
		RawMaterial r = new RawMaterial(name, calories, proteins, carbs, fat);
		this.rawMaterials.add(r);
	}

	/**
	 * Retrieves the collection of all defined raw materials
	 * @return collection of raw materials though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> rawMaterials() {
		Collections.sort(this.rawMaterials, new Comparator<NutritionalElement>() {
			@Override
			public int compare(NutritionalElement r1, NutritionalElement r2) {
				return r1.getName().compareTo(r2.getName());
			}
		});
		return this.rawMaterials;
	}

	/**
	 * Retrieves a specific raw material, given its name
	 * @param name  name of the raw material
	 * @return  a raw material though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRawMaterial(String name) {
		for(NutritionalElement r : this.rawMaterials){
			if(r.getName().equals(name)) return r;
		}
		System.out.println("Nessuna materia prima trovata!"); // debug
		return null;
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * @param name unique name of the product
	 * @param calories calories for a product unit
	 * @param proteins proteins for a product unit
	 * @param carbs carbs for a product unit
	 * @param fat fats for a product unit
	 */
	public void defineProduct(String name, double calories, double proteins, double carbs, double fat) {
		Product p = new Product(name, calories, proteins, carbs, fat);
		this.products.add(p);
	}

	/**
	 * Retrieves the collection of all defined products
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> products() {
		Collections.sort(this.products, new Comparator<NutritionalElement>() {
			@Override
			public int compare(NutritionalElement p1, NutritionalElement p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
		return this.products;
	}

	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getProduct(String name) {
		for(NutritionalElement p : this.products){
			if(p.getName().equals(name)) return p;
		}
		System.out.println("Nessuna prodotto trovato!"); // debug
		return null;
	}

	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		Recipe r = new Recipe(name, this.rawMaterials);
		recipes.add(r);
		return r;
	}
	
	/**
	 * Retrieves the collection of all defined recipes
	 * @return collection of recipes though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> recipes() {
		Collections.sort(this.recipes, new Comparator<NutritionalElement>() {
			@Override
			public int compare(NutritionalElement rp1, NutritionalElement rp2) {
				return rp1.getName().compareTo(rp2.getName());
			}
		});
		return this.recipes;
	}

	/**
	 * Retrieves a specific recipe, given its name
	 * @param name  name of the recipe
	 * @return  a recipe though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRecipe(String name) {
		for(NutritionalElement rp : this.recipes){
			if(rp.getName().equals(name)) return rp;
		}
		System.out.println("Nessuna ricetta trovata!"); // debug
		return null;
	}

	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu m = new Menu(name, this.products, this.recipes);
		menus.add(m);
		return m;
	}

	// se dovesse servire
	public Collection<NutritionalElement> menus() {
		Collections.sort(this.menus, new Comparator<NutritionalElement>() {
			@Override
			public int compare(NutritionalElement p1, NutritionalElement p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
		return this.menus;
	}
}