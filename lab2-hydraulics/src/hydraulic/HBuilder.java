package hydraulic;

import java.util.ArrayList;

/**
 * Hydraulics system builder providing a fluent API
 */
public class HBuilder {

    protected HSystem sistema;
    int pos = 0;
    protected ArrayList<Integer> v = new ArrayList<>();
    protected ArrayList<Integer> rimasti = new ArrayList<>();

    public HBuilder(){
        this.sistema = new HSystem();
    }

    /**
     * Add a source element with the given name
     * 
     * @param name name of the source element to be added
     * @return the builder itself for chaining 
     */
    public HBuilder addSource(String name) {
        Source src = new Source(name);
        this.sistema.addElement(src);
        return this;
    }

    /**
     * returns the hydraulic system built with the previous operations
     * 
     * @return the hydraulic system
     */
    public HSystem complete() {
        ((Source)this.sistema.elementi.get(0)).start();
        for(Element el : this.sistema.elementi){
            if(el instanceof Multisplit){
                ((Multisplit)el).startMS();
            }
        }
        return this.sistema;
    }

    /**
     * creates a new tap and links it to the previous element
     * 
     * @param name name of the tap
     * @return the builder itself for chaining 
     */
    public HBuilder linkToTap(String name) {
        Tap tap = new Tap(name);
        this.sistema.addElement(tap);
        collega(tap);
        return this;
    }

    /**
     * creates a sink and link it to the previous element
     * @param name name of the sink
     * @return the builder itself for chaining 
     */
    public HBuilder linkToSink(String name) {
        Sink sink = new Sink(name);
        this.sistema.addElement(sink);
        collega(sink);
        return this;
    }

    public void collega(Element elemento){
        int index = 0;
        Element el = this.sistema.elementi.get(this.pos);
        if(el instanceof Source || el instanceof Tap){
            el.connect(elemento);
        }
        else if(el instanceof Sink);
        else{
            for(Element elem : el.next){
                if(elem == null) el.connect(elemento, index);
                index += 1;
            }
        }
        this.pos += 1;
    }

    /**
     * creates a split and links it to the previous element
     * @param name of the split
     * @return the builder itself for chaining 
     */
    public HBuilder linkToSplit(String name) {
        Split split = new Split(name);
        this.sistema.addElement(split);
        collega(split);
        int i = this.pos;
        this.v.add(i);
        this.rimasti.add(2);
        return this;
    }

    /**
     * creates a multisplit and links it to the previous element
     * @param name name of the multisplit
     * @param numOutput the number of output of the multisplit
     * @return the builder itself for chaining 
     */
    public HBuilder linkToMultisplit(String name, int numOutput) {
        Multisplit multi = new Multisplit(name, numOutput);
        this.sistema.addElement(multi);
        collega(multi);
        int i = this.pos;
        this.v.add(i);
        this.rimasti.add(numOutput);
        return this;
    }

    /**
     * introduces the elements connected to the first output 
     * of the latest split/multisplit.
     * The element connected to the following outputs are 
     * introduced by {@link #then()}.
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder withOutputs() {
        this.pos = this.v.get(this.v.size()-1);
        reduce(this.rimasti.get(this.rimasti.size()-1));
        return this;
    }

    public void reduce(int i){
        i += -1;
    }

    /**
     * inform the builder that the next element will be
     * linked to the successive output of the previous split or multisplit.
     * The element connected to the first output is
     * introduced by {@link #withOutputs()}.
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder then() {
        if(this.rimasti.get(this.rimasti.size()-1) > 0){
            this.pos = this.v.get(this.v.size()-1);
            reduce(this.rimasti.get(this.rimasti.size()-1));
        }
        return this;
    }

    /**
     * completes the definition of elements connected
     * to outputs of a split/multisplit. 
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder done() {
        this.rimasti.remove(this.rimasti.size()-1);
        this.v.remove(this.v.size()-1);
        return this;
    }

    /**
     * define the flow of the previous source
     * 
     * @param flow flow used in the simulation
     * @return the builder itself for chaining 
     */
    public HBuilder withFlow(double flow) {
        if(this.sistema.elementi.get(0) instanceof Source){
                if(this.sistema.elementi.get(0) instanceof Source){
                    ((Source)this.sistema.elementi.get(0)).setFlow(flow, true);
            }
        }
        return this;
    }

    /**
     * define the status of a tap as open,
     * it will be used in the simulation
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder open() {
        for(Element el : this.sistema.elementi){
            if(el instanceof Tap){
                ((Tap)el).setOpen(true);
            }
        }
        return this;
    }

    /**
     * define the status of a tap as closed,
     * it will be used in the simulation
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder closed() {
        for(Element el : this.sistema.elementi){
            if(el instanceof Tap){
                ((Tap)el).setOpen(false);
            }
        }
        return this;
    }

    /**
     * define the proportions of input flow distributed
     * to each output of the preceding a multisplit
     * 
     * @param props the proportions
     * @return the builder itself for chaining 
     */
    public HBuilder withPropotions(double[] props) {
        for(Element el : this.sistema.elementi){
            if(el instanceof Multisplit){
                ((Multisplit)el).setProportions(true,props);
            }
        }
        return this;
    }

    /**
     * define the maximum flow theshold for the previous element
     * 
     * @param max flow threshold
     * @return the builder itself for chaining 
     */
    public HBuilder maxFlow(double max) {
        for(Element el : this.sistema.elementi){
            el.setMaxFlow(max);
        }
        return this;
    }
}

