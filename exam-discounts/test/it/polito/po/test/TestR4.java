package it.polito.po.test;

import discounts.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestR4 {

	Discounts d;
	int cId1 = 0; 
	int cId2 = 0;
	
    @Before
    public void setUp() throws DiscountsException {
    	d = new Discounts();
        cId1 = d.issueCard("John Smith"); 
        cId2 = d.issueCard("J"); 
    	d.addProduct("cat3", "pt5", 30.4); 
    	d.addProduct("cat3", "pt3", 10); 
    	d.addProduct("cat2", "pt4", 20); 
    	d.addProduct("cat2", "pt2", 40); 
    	d.addProduct("cat7", "pt7", 50); 
    	d.setDiscount("cat2", 10);
    	d.setDiscount("cat7", 40);
    }

    @Test
    public void addPurchase1() throws DiscountsException {
    	int p1 = d.addPurchase(cId1, "pt5:1","pt3:2","pt4:2","pt2:1");
    	int p2 = d.addPurchase(cId2, "pt5:1","pt3:2","pt4:2","pt7:2");
    	assertEquals("Wrong purchase code", 1, p1);
    	assertEquals("Wrong purchase code", 2, p2);
    }

    @Test
    public void addPurchase2() {
        assertThrows("Unknown product should throw exception", DiscountsException.class,
                ()->d.addPurchase(cId1, "pt100:1"));
    }

    @Test
    public void addPurchaseWoCardWrong() {
        assertThrows("Unknown product should throw exception", DiscountsException.class,
                ()->d.addPurchase( "pt99:1"));
    }
    

    @Test
    public void getAmount() throws DiscountsException {
    	int pCode1 = 0;
    	pCode1 = d.addPurchase(cId1, "pt5:1","pt3:2","pt4:2","pt2:1","pt7:2");
        double purchaseAmount = d.getAmount(pCode1); 
        assertEquals("Wrong purchase amount", 182.4, purchaseAmount, 0.01);
    }

    @Test
    public void getDiscount() throws DiscountsException {
    	int pCode1 = 0;
    	pCode1 = d.addPurchase(cId1, "pt5:1","pt3:2","pt4:2","pt2:1");
        double purchaseDiscount = d.getDiscount(pCode1);
        assertEquals("Wrong discount amount", 8.0, purchaseDiscount, 0.01);
    }
    
    @Test
    public void getAmountNofUnits() throws DiscountsException {
    	int pCode1 = 0;
    	pCode1 = d.addPurchase(cId1, "pt5:1","pt3:2","pt4:2","pt2:1");
        int nOfUnits = d.getNofUnits(pCode1);
        assertEquals("Wrong purchase units count", 6, nOfUnits);
    }
    
    @Test
    public void getAmountNoCard() throws DiscountsException {
    	int pCode1 = 0;
    	pCode1 = d.addPurchase("pt5:1","pt3:2","pt4:2","pt2:1");
        double purchaseAmount = d.getAmount(pCode1); 
        assertEquals("Wrong purchase amount (w/o card)", 130.4, purchaseAmount, 0.01);
    }

    @Test
    public void getDiscount2() throws DiscountsException {
    	int pCode1 = 0;
    	pCode1 = d.addPurchase("pt5:1","pt3:2","pt4:2","pt2:1");
        double purchaseDiscount = d.getDiscount(pCode1);
        assertEquals("Wrong discount amount (w/o card)", 0, purchaseDiscount, 0.01);
    }

}
