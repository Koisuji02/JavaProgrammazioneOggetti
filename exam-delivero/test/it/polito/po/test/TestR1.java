package it.polito.po.test;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import delivery.Delivery;
import delivery.DeliveryException;



public class TestR1 {
    Delivery d;
    
    @Before
    public void setUp() throws DeliveryException {
        d = new Delivery();
        d.addCategory("Chinese");
        d.addCategory("Italian");
        d.addCategory("Pizza");
        d.addRestaurant("La muraglia cinese", "Chinese");
        d.addRestaurant("Il drago d'oro", "Chinese");
        d.addRestaurant("Il re della pizza", "Pizza");
        d.addRestaurant("La vecchia trattoria", "Italian");
    }

    @Test
    public void testGetCategory() throws DeliveryException {
    	List<String> categories = d.getCategories();
    	assertEquals("There should be three categories", 3,categories.size());
    	assertTrue("Missing expected category", categories.contains("Chinese"));
    	assertTrue("Missing expected category",categories.contains("Italian"));
    	assertTrue("Missing expected category",categories.contains("Pizza"));
    }
    
    @Test(expected=DeliveryException.class)
    public void testAddDuplicateCategory() throws DeliveryException {
    	d.addCategory("Italian");
    }
    
    @Test(expected=DeliveryException.class)
    public void testAddRestaurantUndefinedCategory() throws DeliveryException {
    	d.addRestaurant("Il Re del Kebab","Kebab");
    }
    
    @Test
    public void testGetRestaurantsForCategory() {
    	List<String> list1 = d.getRestaurantsForCategory("Chinese");
    	assertEquals(2, list1.size());
    	assertTrue("Missing expected restaurant",list1.contains("La muraglia cinese"));
    	assertTrue("Missing expected restaurant",list1.contains("Il drago d'oro"));
    	List<String> list2 = d.getRestaurantsForCategory("Italian");
    	assertEquals(1, list2.size());
    	assertTrue("Missing expected restaurant",list2.contains("La vecchia trattoria"));
    }
    
    @Test
    public void testGetRestaurantsForCategoryNonExistent() {
        assertNotNull("Non existent category should return empty list",
                        d.getRestaurantsForCategory("Kebab"));
    	assertEquals("Non existent category should return empty list",
    	              0, d.getRestaurantsForCategory("Kebab").size());
    }
}
