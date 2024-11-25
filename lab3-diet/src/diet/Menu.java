package diet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {

	protected ArrayList<NutritionalElement> products = new ArrayList<>();
	protected ArrayList<NutritionalElement> recipes = new ArrayList<>();
	protected String name;
	protected double calories = 0;
	protected double proteins = 0;
	protected double carbs = 0;
	protected double fat = 0;
	protected boolean g100 = false;
	public Map<NutritionalElement,Double> ricette = new HashMap<>();
	public ArrayList<NutritionalElement> prodotti = new ArrayList<>();

	public Menu(String name, ArrayList<NutritionalElement> products, ArrayList<NutritionalElement> recipes){
		this.name = name;
		this.products = products;
		this.recipes = recipes;
	}

	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
    public Menu addRecipe(String recipe, double quantity) {
		double calories=0, proteins=0, carbs=0, fat=0;
		NutritionalElement ricetta = null;
		for(NutritionalElement i : this.recipes){
			if(i.getName().equals(recipe)){
				ricetta = i;
				calories = i.getCalories();
				proteins = i.getProteins();
				carbs = i.getCarbs();
				fat = i.getFat();
			}
		}
		if(ricetta != null){
			this.ricette.put(ricetta, quantity);
			this.calories += calories*quantity/100;
			this.carbs += carbs*quantity/100;
			this.proteins += proteins*quantity/100;
			this.fat += fat*quantity/100;
		}
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
    public Menu addProduct(String product) {
		double calories=0, proteins=0, carbs=0, fat=0;
		NutritionalElement prodotto = null;
		for(NutritionalElement i : this.products){
			if(i.getName().equals(product)){
				prodotto = i;
				calories = i.getCalories();
				proteins = i.getProteins();
				carbs = i.getCarbs();
				fat = i.getFat();
			}
		}
		if(prodotto != null){
			this.prodotti.add(prodotto);
			this.calories += calories;
			this.carbs += carbs;
			this.proteins += proteins;
			this.fat += fat;
		}
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return this.calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return this.proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return this.carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return this.fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return this.g100;
	}
}