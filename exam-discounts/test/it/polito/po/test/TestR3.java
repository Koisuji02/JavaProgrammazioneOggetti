package it.polito.po.test;

import discounts.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TestR3 {
	Discounts d;
	
    @Before
    public void setUp() throws DiscountsException {
    	d = new Discounts();
    	d.addProduct("cat1", "pt5", 30.4);
    }
    
    
    @Test
    public void setDiscount1() throws DiscountsException {
        
    	assertEquals("Wrong default discount", 0, d.getDiscount("cat1"));
    	d.setDiscount("cat1", 10);
        assertEquals("Wrong discount", 10, d.getDiscount("cat1"));
    }

    
    @Test
    public void setDiscount2() {
        assertThrows("Unknown category should trigger exception", DiscountsException.class,
    	    ()->d.setDiscount("cat3", 10));
    }
    
    @Test
    public void setDiscount3() {
    	assertThrows("Discount > 50 should trigger exception", DiscountsException.class,
    	    ()->d.setDiscount("cat1", 70));
    }
    
    @Test
    public void setDiscount4() {
    	assertThrows("Discount < 0 should trigger exception", DiscountsException.class,
    	    ()->d.setDiscount("cat1", -10));
    }
    
    @Test
    public void getDiscount() throws DiscountsException {
    	d.setDiscount("cat1", 30);
        int discount = d.getDiscount("cat1");
        assertEquals("Wrong discount value", 30, discount);
    }
}
