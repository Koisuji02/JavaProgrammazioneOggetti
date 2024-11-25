package it.polito.oop.futsal;

/**
 * Represents an option for a field
 * available at a given time
 *
 */
public interface FieldOption {

     /**
     * retrieves the field number
     * @return table number
     */
    public int getField();
    
    /**
     * retrieves the field occupation.
     * 
     * 
     * @return table occupation
     */
    public int getOccupation();
    
 }
