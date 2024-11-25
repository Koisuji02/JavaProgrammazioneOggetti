package example;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import discounts.*;

public class TestApp {

	Discounts d;

	@Before
	public void setUp(){
		d = new Discounts();
	}
	@Test
	public void testR1(){
		int cId1 = d.issueCard("Mario Rossi"); 
		int cId2 = d.issueCard("Giuseppe Verdi"); 

		assertEquals(1, cId1); 
		assertEquals(2, cId2); 
		assertEquals(2, d.nOfCards());
	}

	@Test
	public void testR2() throws DiscountsException{
		d.addProduct("c1", "p4", 10.6); 
		d.addProduct("c1", "p2", 40.8);
		d.addProduct("c2", "p3", 20.7); 
		d.addProduct("c2", "p5", 30);
		d.addProduct("c2", "p6", 50); 
		d.addProduct("c2", "p8", 70);

		double price = d.getPrice("p4");
		assertEquals(10.6, price, 0.001);

		assertThrows(DiscountsException.class,
			()->d.addProduct("c2", "p4", 3.1));

		assertThrows(DiscountsException.class,
			()->d.getPrice("p100"));

		double average = d.getAveragePrice("c1");
		assertEquals(26, average, 0.01);
		
	}

	@Test
	public void testR3() throws DiscountsException{
		d.addProduct("c1", "p4", 10.6); 
		d.addProduct("c1", "p2", 40.8);
		d.addProduct("c2", "p3", 20.7); 
		d.addProduct("c2", "p5", 30);

		d.setDiscount("c1", 20);

		int discount = 	d.getDiscount("c1");	

		assertEquals(20, discount);
	}


	@Test
	public void testR4() throws DiscountsException{
		int cId1 = d.issueCard("Mario Rossi"); 
		d.addProduct("c1", "p4", 10.6); 
		d.addProduct("c1", "p2", 40.8);
		d.addProduct("c2", "p3", 20.7); 

		d.setDiscount("c1", 20);

		int pCode1 = d.addPurchase(cId1, "p4:1","p2:3","p3:1"); //with card
		int pCode2 = d.addPurchase("p4:1","p2:3","p3:1"); //without card

		assertEquals(1, pCode1);
		assertEquals(2, pCode2);

		double purchaseAmount = d.getAmount(pCode1);
		double expectedAmount = (10.6 + 3*40.8) * 0.8 + 20.7;
		assertEquals(expectedAmount, purchaseAmount, 0.01);  //p4 and p2 discounted by 20%

		purchaseAmount = d.getAmount(pCode2); 
		expectedAmount = 10.6 + 3*40.8 + 20.7;
		assertEquals(expectedAmount, purchaseAmount, 0.01);  // no card => no discount
	
		double purchaseDiscount = d.getDiscount(pCode1);
		assertEquals(26.6, purchaseDiscount, 0.01);

		int nOfUnits = d.getNofUnits(pCode1);
		assertEquals(5, nOfUnits);
	}

	@Test
	public void testR5() throws DiscountsException{
		int cId1 = d.issueCard("Mario Rossi"); 
		int cId2 = d.issueCard("Giuseppe Verdi"); 
		d.addProduct("c1", "p4", 10.6); 
		d.addProduct("c1", "p2", 40.8);
		d.addProduct("c2", "p3", 20.7); 
		d.addProduct("c2", "p5", 30);
		d.addProduct("c2", "p6", 50); 
		d.addProduct("c2", "p8", 70);

		d.setDiscount("c1", 20);

		d.addPurchase(cId1, "p4:1","p2:3","p3:1"); //with card
		d.addPurchase("p4:1","p2:3","p3:1"); //without card
		d.addPurchase(cId2, "p5:1","p2:1","p3:1"); //with card
		d.addPurchase("p6:2"); //without card

		SortedMap<Integer, List<String>> map1 = d.productIdsPerNofUnits();
		assertEquals("{1=[p5], 2=[p4, p6], 3=[p3], 7=[p2]}", map1.toString()); //two units for p4 and p6; p8 not considered
		
		SortedMap<Integer, Double> map2 = d.totalPurchasePerCard();  //two cards
		assertEquals("{1=127.1, 2=83.34}", map2.toString());
		
		double totalPurchasesWithoutCard = d.totalPurchaseWithoutCard();
		double expectedAmount = (10.6 + 40.8*3 + 20.7) + ( 50 * 2 );
		assertEquals(expectedAmount, totalPurchasesWithoutCard, 0.01);
		
		SortedMap<Integer, Double> map3 = d.totalDiscountPerCard();  //two cards
		assertEquals("{1=26.6, 2=8.16}", map3.toString());
	}

}
