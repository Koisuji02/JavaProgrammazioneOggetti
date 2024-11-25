package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {

	protected boolean open;

	/**
	 * Constructor
	 * @param name name of the tap element
	 */
	public Tap(String name) {
		this.name = name;
		//this.next.add(null);
	}

	@Override
	public void connect(Element elem) {
		if(this.next.size() > 0) this.next.set(0, elem);
		else this.next.add(elem);
		//this.next.get(0).flow(this.out);
	}

	@Override
	public Element getOutput(){
		if(this.next.get(0) == null) System.out.println("No element connected!");
		return this.next.get(0);
	}

	@Override
	public void flow(double portata){
		this.in = portata;
		if(this.open == true){
			setOpen(open);
			this.next.get(0).flow(this.out);
		}
	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		this.open = open;
		if(open == true) {
			this.out = this.in;
			this.next.get(0).flow(this.out);
		}
		else this.out = 0.0;
	}
	
}
