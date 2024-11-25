package it.polito.po.test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import delivery.Delivery;
import delivery.DeliveryException;


public class TestR4R5 {
    private Delivery d;

    @Before
    public void setUp() throws DeliveryException {
        d = new Delivery();
        d.addCategory("Chinese");
        d.addCategory("Italian");
        d.addCategory("Pizza");
    }

    @Test
    public void testRestaurantRating1() throws DeliveryException {
        d.addRestaurant("La muraglia cinese", "Chinese");
        d.addRestaurant("Il drago d'oro", "Chinese");
        d.addRestaurant("Il re della pizza", "Pizza");
        d.addRestaurant("La vecchia trattoria", "Italian");
        d.setRatingForRestaurant("La muraglia cinese", 6);
        d.setRatingForRestaurant("La muraglia cinese", -1);
        d.setRatingForRestaurant("La muraglia cinese", -2);
        d.setRatingForRestaurant("La muraglia cinese", 5);
        d.setRatingForRestaurant("La muraglia cinese", 4);
        d.setRatingForRestaurant("La muraglia cinese", 3);

        List<String> rr = d.restaurantsAverageRating();

        assertEquals(1, rr.size());
        assertEquals("La muraglia cinese", rr.get(0));
    }

    @Test
    public void testRestaurantRating2() throws DeliveryException {
        d.addRestaurant("La muraglia cinese", "Chinese");
        d.addRestaurant("Il drago d'oro", "Chinese");
        d.addRestaurant("Il re della pizza", "Pizza");
        d.addRestaurant("La vecchia trattoria", "Italian");
        d.setRatingForRestaurant("La muraglia cinese", 5);
        d.setRatingForRestaurant("La muraglia cinese", 4);
        d.setRatingForRestaurant("La muraglia cinese", 3);
        d.setRatingForRestaurant("Il drago d'oro", 3);
        d.setRatingForRestaurant("Il drago d'oro", 2);
        d.setRatingForRestaurant("Il drago d'oro", 3);
        d.setRatingForRestaurant("Il re della pizza", 4);
        d.setRatingForRestaurant("Il re della pizza", 5);
        d.setRatingForRestaurant("Il re della pizza", 4);
        d.setRatingForRestaurant("La vecchia trattoria", 0);
        d.setRatingForRestaurant("La vecchia trattoria", 2);
        d.setRatingForRestaurant("La vecchia trattoria", 1);

        String[] expected = { "Il re della pizza" , "La muraglia cinese" , "Il drago d'oro", "La vecchia trattoria" };
        String[] actual = d.restaurantsAverageRating().stream()
                .collect(Collectors.toList()).toArray(new String[4]);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testOrdersPerCategory() throws DeliveryException {
        d.addRestaurant("La muraglia cinese", "Chinese");
        d.addRestaurant("Il drago d'oro", "Chinese");
        d.addRestaurant("Il re della pizza", "Pizza");
        d.addRestaurant("Il re della pizza 2", "Pizza");
        d.addRestaurant("La vecchia trattoria", "Italian");

        d.addDish("Pizza capricciosa", "Il re della pizza", 7);
        d.addDish("Pizza diavola", "Il re della pizza", 5);
        d.addDish("Pizza marinara", "Il re della pizza 2", 3.5f);
        d.addDish("Spaghetti aglio olio peperoncino", "La vecchia trattoria", 6);
        d.addDish("Coniglio alla cacciatora", "La vecchia trattoria", 9);

        String[] dishNames = { "Pizza capricciosa" , "Pizza marinara"};
        int[] quantities = { 2 , 3};
        d.addOrder(dishNames, quantities, "A Customer", "Il re della pizza", 18, 3);
        d.addOrder(dishNames, quantities, "B Customer", "Il re della pizza", 18, 3);

        String[] dishNames2 = { "Pizza diavola" };
        int[] quantities2 = { 1 };
        d.addOrder(dishNames2, quantities2, "C Customer", "Il re della pizza 2", 18, 3);


        String[] dishNames3 = { "Coniglio alla cacciatora" , "Spaghetti aglio olio peperoncino"};
        int[] quantities3 = { 3 , 5};
        d.addOrder(dishNames3, quantities3, "D Customer", "La vecchia trattoria", 19, 5);
        d.addOrder(dishNames3, quantities3, "E Customer", "La vecchia trattoria", 19, 5);

        Map<String, Long> map = d.ordersPerCategory();
        assertEquals(Long.valueOf(3),map.get("Pizza"));
        assertEquals(Long.valueOf(2),map.get("Italian"));
    }

    @Test
    public void testBestRestaurant() throws DeliveryException {
        d.addRestaurant("La muraglia cinese", "Chinese");
        d.addRestaurant("Il drago d'oro", "Chinese");
        d.addRestaurant("Il re della pizza", "Pizza");
        d.addRestaurant("La vecchia trattoria", "Italian");
        d.setRatingForRestaurant("La muraglia cinese", 5);
        d.setRatingForRestaurant("La muraglia cinese", 4);
        d.setRatingForRestaurant("La muraglia cinese", 3);
        d.setRatingForRestaurant("Il drago d'oro", 3);
        d.setRatingForRestaurant("Il drago d'oro", 2);
        d.setRatingForRestaurant("Il drago d'oro", 3);
        d.setRatingForRestaurant("Il re della pizza", 4);
        d.setRatingForRestaurant("Il re della pizza", 5);
        d.setRatingForRestaurant("Il re della pizza", 4);
        d.setRatingForRestaurant("La vecchia trattoria", 0);
        d.setRatingForRestaurant("La vecchia trattoria", 2);
        d.setRatingForRestaurant("La vecchia trattoria", 1);

        assertEquals("Il re della pizza",d.bestRestaurant());
    }
}
