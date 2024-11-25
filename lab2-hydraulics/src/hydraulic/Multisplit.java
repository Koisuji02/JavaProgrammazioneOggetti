package hydraulic;

import java.util.ArrayList;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	protected int numOut;
	protected ArrayList<Double> proporzioni = new ArrayList<>();

	/**
	 * Constructor
	 * @param name the name of the multi-split element
	 * @param numOutput the number of outputs
	 */
	public Multisplit(String name, int numOutput) {
		super(name, numOutput);
		this.numOut = numOutput;
	}

	@Override
	public void connect(Element elem, int index){
		this.next.set(index, elem);
		//System.out.println(this.next.get(index).getName());
	}

	@Override
	public Element[] getOutputs(){
		Element[] array = new Element[this.numOut];
		for(int i = 0; i < this.numOut; ++i){
			if(this.next.get(i) == null) System.out.println("No element connected in exit " + i +" !");
		}
		array = this.next.toArray(array);
		return array;
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		for(double proportion : proportions){
			this.proporzioni.add(proportion);
		}
		// settato dopo a causa del test
		for(int index = 0; index < this.numOut; ++index)
			this.next.get(index).flow(this.out * this.proporzioni.get(index));
	}

	public void setProportions(boolean flag,double... proportions){
		for(double proportion : proportions){
			this.proporzioni.add(proportion);
		}
	}

	public void startMS(){
		for(int index = 0; index < this.numOut; ++index)
			this.next.get(index).flow(this.out * this.proporzioni.get(index));
	}

	/*@Override
	public void flow(double portata){
		this.in = portata;
		this.out = this.in;
	}*/
	
}
