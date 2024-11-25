package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {
		this.name = name;
		// aggiungo i primi elementi apposta da fare solo set
		this.next.add(null);
		this.next.add(null);
	}

	public Split(String name, int numOutput) {
		this.name = name;
		// aggiungo i primi elementi apposta da fare solo set
		for(int i = 0; i < numOutput; ++i){
			this.next.add(null);
		}
	}

	@Override
	public void connect(Element elem, int index){
		this.next.set(index, elem);
		//this.next.get(index).flow(this.in/2);
	}

	@Override
	public Element[] getOutputs(){
		Element[] array = new Element[this.next.size()];
		for(int i = 0; i < 2; ++i){
			if(this.next.get(i) == null) System.out.println("No element connected in exit " + i +" !");
		}
		array = this.next.toArray(array);
		return array;
	}
	
	@Override
	public void flow(double portata){
		this.in = portata;
		this.out = this.in;
		for(Element el : this.next){
			if(el != null) el.flow(this.out/2);
		}
	}

}
