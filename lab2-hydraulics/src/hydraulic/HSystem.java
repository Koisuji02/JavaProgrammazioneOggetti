package hydraulic;
import java.util.ArrayList;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {

	// ATTRIBUTI
	protected ArrayList<Element> elementi = new ArrayList<>();

// R1
	/**
	 * Adds a new element to the system
	 * 
	 * @param elem the new element to be added to the system
	 */
	public void addElement(Element elem){
		this.elementi.add(elem);
	}
	
	/**
	 * returns the element added so far to the system
	 * 
	 * @return an array of elements whose length is equal to 
	 * 							the number of added elements
	 */
	public Element[] getElements(){
		// creo nuovo array di Element con la stessa dimensione di ArrayList
		Element[] array = new Element[this.elementi.size()];
		// uso toArray per convertire l'ArrayList in Array
		array = this.elementi.toArray(array);
		return array;
	}

// R4
	/**
	 * starts the simulation of the system
	 * 
	 * The notification about the simulations are sent
	 * to an observer object
	 * 
	 * Before starting simulation the parameters of the
	 * elements of the system must be defined
	 * 
	 * @param observer the observer receiving notifications
	 */
	public void simulate(SimulationObserver observer){
		if(this.elementi.get(0) instanceof Source) {
			percorso(this.elementi.get(0), observer);
		}
		return;
	}

	/**
	 * 
	 * Calcolo di tutti i percorsi possibili con ricorsione sui next
	 * 
	 * @param elemento singolo elemento passato alla funzione
	 * @param observer
	 */

	public void percorso(Element elemento, SimulationObserver observer){

		double port_in = elemento.getIn(); if(port_in == 0.0) port_in = SimulationObserver.NO_FLOW;
		double port_out = elemento.getOut(); if(port_out == 0.0) port_out = SimulationObserver.NO_FLOW;

		observer.notifyFlow(elemento.getClass().getName(), elemento.getName(), port_in, port_out);

		for(Element el: elemento.next){
			if(el != null) percorso(el, observer);
		}
		return;
	}

// R6
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		String stampa = "", riga = "";
		if(this.elementi.get(0) instanceof Source) {
			stampa += disegna(this.elementi.get(0), stampa, riga);
		}
		System.out.println(stampa);
		return stampa;
	}

	public String disegna(Element elemento, String stampa, String riga){
		stampa += "[" + elemento.getName() + "]"+ elemento.getClass().getName() + " ";
		riga += "[" + elemento.getName() + "]"+ elemento.getClass().getName() + " ";
		int len = riga.length();
		if(elemento.next.size() > 1){
			for(Element el : elemento.next){
				if(el == null){
					stampa += "*\n";
					for(int i = 0; i < len; ++i){
						stampa += " ";
					}
					stampa += "|\n";
					riga = "";
					for(int i = 0; i < len; ++i){
						stampa += " ";
						riga += " ";
					}
				}
				else {
					stampa += "+-> ";
					riga += "+-> ";
					stampa += disegna(el, stampa, riga);
				}
			}
		}
		else if(elemento.next.size() == 1){
			if(elemento.next.get(0) == null) {
				stampa += "* ";
				return stampa;
			}
			else {
				stampa += "+-> ";
				riga += "+-> ";
				stampa += disegna(elemento.next.get(0), stampa, riga);
			}
		}
		return stampa;
	}

// R7
	/**
	 * Deletes a previously added element 
	 * with the given name from the system
	 */
	public boolean deleteElement(String name) {
		boolean flag = false;
		Element el = this.elementi.get(0), tmp;
		if(el instanceof Source) {
			// Caso di rimozione della Source
			if(el.getName().equals(name)){
				tmp = el;
				el = el.next.get(0);
				this.elementi.remove(tmp);
			}
			else flag = find(el, this.elementi.get(1), name, flag);
		}
		return flag;
	}

	public boolean find(Element previous, Element elemento, String name, boolean flag){
		int count = 0;
		if(elemento.getName().equals(name)){
			if(elemento instanceof Split || elemento instanceof Multisplit){
				for(Element el : elemento.next){
					if (el != null) count += 1;
				}
				if(count > 1) return false;
				else {
					stacca(previous, elemento);
					flag = true;
				}
			} else{
				stacca(previous, elemento);
				flag = true;
			}
		} else{
			for(Element el : elemento.next){
				if (el != null) flag = find(elemento, el, name, flag);
			}
		}
		return flag;
	}

	public void stacca(Element prev, Element el){
		prev.next = el.next;
		this.elementi.remove(el);
	}

// R8
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 * 
	 * If {@code enableMaxFlowCheck} is {@code false}  a normals simulation as
	 * the method {@link #simulate(SimulationObserver)} is performed
	 * 
	 * Before performing a checked simulation the max flows of the elements in thes
	 * system must be defined.
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		if(this.elementi.get(0) instanceof Source) {
			percorso(this.elementi.get(0), observer, enableMaxFlowCheck);
		}
		return;
	}

	public void percorso(Element elemento, SimulationObserver observer, boolean enableMaxFlowCheck){
		observer.notifyFlow(elemento.getClass().getName(), elemento.getName(), elemento.getIn(), elemento.getOut());
		if(elemento.getIn() > elemento.getMax()){
			observer.notifyFlowError(elemento.getClass().getName(), elemento.getName(), elemento.getIn(), elemento.getMax());
		}
		for(Element el: elemento.next){
			if(el != null) percorso(el, observer, enableMaxFlowCheck);
		}
		return;
	}

// R9
	/**
	 * creates a new builder that can be used to create a 
	 * hydraulic system through a fluent API 
	 * 
	 * @return the builder object
	 */
    public static HBuilder build() {
		return new HBuilder();
    }
}
