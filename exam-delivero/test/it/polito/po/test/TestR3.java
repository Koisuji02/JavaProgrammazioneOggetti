package it.polito.po.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import delivery.Delivery;
import delivery.DeliveryException;


public class TestR3 {
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
        d.addDish("Pizza capricciosa", "Il re della pizza", 7);
        d.addDish("Pizza diavola", "Il re della pizza", 5);
        d.addDish("Pizza marinara", "Il re della pizza", 3.5f);
        d.addDish("Spaghetti aglio olio peperoncino", "La vecchia trattoria", 6);
        d.addDish("Coniglio alla cacciatora", "La vecchia trattoria", 9);
    }

    @Test
    public void testOrderNum() {
        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        int o1 = d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);
        int o2 = d.addOrder(dishNames, quantities, "B Customer", "Il re della pizza", 18, 5);
        assertEquals(1,o1);
        assertEquals(2,o2);
    }



    @Test
    public void testScheduleDelivery1() {
        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);
        d.addOrder(dishNames, quantities, "B Customer", "Il re della pizza", 18, 5);
        assertEquals(2,d.scheduleDelivery(18, 5, 2).size());
    }

    @Test
    public void testScheduleDeliveryHour() {
        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        String[] dishNames2 = { "Coniglio alla cacciatora" , "Spaghetti aglio olio peperoncino"};
        int[] quantities2 = { 3 , 5};
        d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);
        d.addOrder(dishNames2, quantities2, "B Customer", "La vecchia trattoria", 19, 5);
        assertEquals(1,d.scheduleDelivery(18, 5, 2).size());
    }

    @Test
    public void testScheduleDeliveryMaxDistance() {
        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);//1
        d.addOrder(dishNames, quantities, "B Customer", "Il re della pizza", 18, 5);//2
        assertEquals("[1]",d.scheduleDelivery(18, 3, 3).toString());
    }

    @Test
    public void testScheduleDeliveryMaxOrders() {
        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);
        d.addOrder(dishNames, quantities, "B Customer", "Il re della pizza", 18, 5);
        assertEquals(1,d.scheduleDelivery(18, 5, 1).size());
    }

    @Test
    public void testScheduleDeliveryNoMatch() {
        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);
        d.addOrder(dishNames, quantities, "B Customer", "Il re della pizza", 18, 5);
        assertEquals(0,d.scheduleDelivery(19, 1, 3).size());
    }

    @Test
    public void testScheduleDeliveryMaxOrders2() {
        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);
        d.addOrder(dishNames, quantities, "B Customer", "Il re della pizza", 18, 5);
        assertEquals("[1, 2]",d.scheduleDelivery(18, 5, 3).toString());
    }

    @Test
    public void testGetPendingOrders1() {
        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        String[] dishNames2 = { "Coniglio alla cacciatora" , "Spaghetti aglio olio peperoncino"};
        int[] quantities2 = { 3 , 5};
        d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);
        d.addOrder(dishNames2, quantities2, "B Customer", "La vecchia trattoria", 19, 5);
        d.scheduleDelivery(18, 5, 3);
        assertEquals(1,d.getPendingOrders());
    }

    @Test
    public void testGetPendingOrders2() {
        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        String[] dishNames2 = { "Coniglio alla cacciatora" , "Spaghetti aglio olio peperoncino"};
        int[] quantities2 = { 3 , 5};
        d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);
        d.addOrder(dishNames2, quantities2, "B Customer", "La vecchia trattoria", 18, 5);
        d.scheduleDelivery(18, 5, 3);
        assertEquals(0,d.getPendingOrders());
    }
}
