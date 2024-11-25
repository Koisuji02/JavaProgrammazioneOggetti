package diet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	
	protected String name;
	protected double calories = 0;
	protected double proteins = 0;
	protected double carbs = 0;
	protected double fat = 0;
	protected boolean g100 = true;
	protected double quanto = 0;
	public Map<NutritionalElement,Double> ingredienti = new HashMap<>();
	protected ArrayList<NutritionalElement> rawMaterials = new ArrayList<>();

	public Recipe(String name, ArrayList<NutritionalElement> rawMaterials){
		this.name = name;
		this.rawMaterials = rawMaterials;
	}

	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		double calories=0, proteins=0, carbs=0, fat=0;
		NutritionalElement ingrediente = null;
		for(NutritionalElement i : this.rawMaterials){
			if(i.getName().equals(material)){
				ingrediente = i;
				calories = i.getCalories();
				proteins = i.getProteins();
				carbs = i.getCarbs();
				fat = i.getFat();
			}
		}
		if(ingrediente != null){
			this.ingredienti.put(ingrediente, quantity);
			this.calories += calories*quantity/100;
			this.carbs += carbs*quantity/100;
			this.proteins += proteins*quantity/100;
			this.fat += fat*quantity/100;
			this.quanto += quantity;
		}
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	
	@Override
	public double getCalories() {
		double x = this.calories*100/this.quanto;
		return x;
	}
	

	@Override
	public double getProteins() {
		double x = this.proteins*100/this.quanto;
		return x;
	}

	@Override
	public double getCarbs() {
		double x = this.carbs*100/this.quanto;
		return x;
	}

	@Override
	public double getFat() {
		double x = this.fat*100/this.quanto;
		return x;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return this.g100;
	}
	
}
