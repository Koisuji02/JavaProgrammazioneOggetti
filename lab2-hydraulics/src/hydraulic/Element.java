package hydraulic;

import java.util.ArrayList;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 * 
 * The class is abstract since it is not intended to be instantiated,
 * though all methods are defined to make subclass implementation easier.
 */
public abstract class Element {
	
	// ATTRIBUTI
	protected String name;
	protected double in;
	protected double out;
	protected double maxFlow;
	protected ArrayList<Element> next = new ArrayList<>();

	/**
	 * getter method for the name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return this.name;
	}

	public double getIn() {
		return this.in;
	}

	public double getOut() {
		return this.out;
	}

	public double getMax() {
		return this.maxFlow;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * In case of element with multiple outputs this method operates on the first one,
	 * it is equivalent to calling {@code connect(elem,0)}. 
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem) {
		// NULLA DI DEFAULT, FARE OVERRIDE
	}
	
	/**
	 * Connects a specific output of this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * @param elem the element that will be placed downstream
	 * @param index the output index that will be used for the connection
	 */
	public void connect(Element elem, int index){
		// NULLA DI DEFAULT, FARE OVERRIDE
	}
	
	/**
	 * Retrieves the single element connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element getOutput(){
		System.out.println("This element can't have elements connected!");
		return null;
	}

	/**
	 * Retrieves the elements connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element[] getOutputs(){
		return null;
	}
	
	
	public void flow(double portata){
		if(!(this instanceof Source)){
			this.in = portata;
		}
	}

	/**
	 * Defines the maximum input flow acceptable for this element
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		if(!(this instanceof Source)){
			this.maxFlow = maxFlow;
		}
		else this.maxFlow = Double.MAX_VALUE;
		return;
	}
}
