package it.polito.po.test;
import discounts.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestR1R2 {
	Discounts d;
	
    @Before
    public void setUp() {
    	d = new Discounts();
    }
    
    @Test
    public void testCards1() {
        int cId1 = d.issueCard("Mario Rossi"); 
        int cId2 = d.issueCard("Giuseppe Verdi"); 
        int cId3 = d.issueCard("Filippo Neri"); 
    	assertEquals(1, cId1); 
    	assertEquals(2, cId2); 
    	assertEquals(3, cId3);
    }

    @Test
    public void testCardsNames() {
        int cId1 = d.issueCard("Salvo Matteini");
        String name = d.cardHolder(cId1);
        assertEquals("Wrong cardholder name", "Salvo Matteini", name);
    }

    @Test
    public void testCards2() {
    	for (int i = 1; i <= 5; i++) {
    	    d.issueCard("Renzo Mattei"+i);
    	}
    	assertEquals("Wrong number of issued cards", 5, d.nOfCards());
    }
    
    
    @Test
    public void addProduct1() throws DiscountsException  {
    	double price = 0;
    	d.addProduct("cat1", "pt5", 30.4); 
    	price = d.getPrice("pt5");
     	assertEquals(30.4, price, 0.001);
    }
    
    @Test
    public void addProduct2() throws DiscountsException {
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.addProduct("cat1", "pt6", 20.4); 
        assertThrows("Duplicate product should throw exception", DiscountsException.class,
    	            ()->d.addProduct("cat1", "pt5", 30.4));
     }

    @Test
    public void addProduct3() throws DiscountsException {
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.addProduct("cat1", "pt6", 20.4); 
        assertThrows("Unknown product should throw exception", DiscountsException.class,
		                ()->d.getPrice("pt100"));
     }
    
    @Test
    public void averagePrice1() throws DiscountsException {
    	double average;
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.addProduct("cat1", "pt6", 30.4); 
		average = d.getAveragePrice("cat1");
		assertEquals("Wrong average prices for category 'cat1'", 30, average, 0.01);
    }
    
    @Test
    public void averagePriceUndefinedCategory() throws DiscountsException {
    	d.addProduct("cat1", "pt5", 30.4); 
    	d.addProduct("cat1", "pt6", 30.4);
        assertThrows("Unknown category should throw exception", DiscountsException.class,
		             ()->d.getAveragePrice("cat2"));
    }
}
