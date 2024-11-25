package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {

	/**
	 * constructor
	 * @param name name of the source element
	 */
	public Source(String name) {
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

	/**
	 * Define the flow of the source to be used during the simulation
	 *
	 * @param flow flow of the source (in cubic meters per hour)
	 */
	public void setFlow(double flow){
		this.out = flow;
		this.next.get(0).flow(this.out);
	}

	public void setFlow(double flow, boolean flag){
		this.out = flow;
	}

	public void start(){
		this.next.get(0).flow(this.out);
	}

}
