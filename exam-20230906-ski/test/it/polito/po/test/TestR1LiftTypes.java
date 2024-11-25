package it.polito.po.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import it.polito.ski.InvalidLiftException;
import it.polito.ski.SkiArea;


public class TestR1LiftTypes {

    public static final String OVO = "Ovo";
    public static final String CABIN = "Cabin";
    public static final String MONTEROSA_SKI = "Monterosa Ski";

    @Test
    public void testNewSkiArea() {

        SkiArea c = new SkiArea(MONTEROSA_SKI);

        assertEquals(MONTEROSA_SKI, c.getName());
    }

    @Test
    public void testCreateType() throws InvalidLiftException{
        
        SkiArea c = new SkiArea(MONTEROSA_SKI);
        
        c.liftType(OVO, CABIN, 8);
     
        
        assertEquals(CABIN,c.getCategory(OVO));
        assertEquals(8,c.getCapacity(OVO));
    }

	@Test
    public void testCreateTypeDup() throws InvalidLiftException {
        
        SkiArea c = new SkiArea(MONTEROSA_SKI);
        
        c.liftType(OVO, CABIN, 8);
        assertThrows("Cannot create duplicate type codes", InvalidLiftException.class,
        			  ()->c.liftType(OVO, CABIN, 8));
     
        
        assertEquals(CABIN,c.getCategory(OVO));
        assertEquals(8,c.getCapacity(OVO));
    }

	@Test
    public void testMissingType() {
        
        SkiArea c = new SkiArea(MONTEROSA_SKI);
        
        assertThrows("Cannot get category for non existing lift type", InvalidLiftException.class,
  			  ()->c.getCategory(OVO));
        
        assertThrows("Cannot get capacity for non existing lift type", InvalidLiftException.class,
  			  ()->c.getCapacity(OVO));
    }

	@Test
    public void testTypeList() throws InvalidLiftException {
        
        SkiArea c = new SkiArea(MONTEROSA_SKI);
        
        c.liftType("Cab", "Cable lift", 8);
        c.liftType("S4", "Chairlift", 4);
        c.liftType("S6", "Chairlift", 6);
        c.liftType("SL", "Ski-lift", 1);

        Collection<String> types = c.types();
        assertNotNull("Missing type list", types);
        assertEquals("Wrong number of types", 4, types.size());
        assertTrue("Missing type from list of types", types.contains("Cab"));
        assertTrue("Missing type from list of types", types.contains("S4"));
        assertTrue("Missing type from list of types", types.contains("S6"));
        assertTrue("Missing type from list of types", types.contains("SL"));
    }


}
