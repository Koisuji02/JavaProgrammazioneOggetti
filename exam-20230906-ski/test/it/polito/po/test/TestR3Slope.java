package it.polito.po.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import it.polito.ski.InvalidLiftException;
import it.polito.ski.SkiArea;

public class TestR3Slope {

    public static final String V3_SLOPE = "V3";
    public static final String RED = "red";
    public static final String G7_SLOPE = "G7";
    public static final String BLACK = "black";
    SkiArea c;
    final static String OVO = "Ovo";
    final static String SALATI_LIFT = "Salati";
    
    @Before
    public void setUp() throws InvalidLiftException{
        c = new SkiArea("Monterosa Ski");
        c.liftType(OVO, "Cabin", 8);
        c.createLift(SALATI_LIFT, OVO);
    }
    
    @Test
    public void testCreateSlope() throws InvalidLiftException{

        c.createSlope(V3_SLOPE, RED, SALATI_LIFT);
        
        
        assertEquals(RED,c.getDifficulty(V3_SLOPE));
        assertEquals(SALATI_LIFT,c.getStartLift(V3_SLOPE));
    }

    @Test
    public void testCreateSlopeErr() {
        assertThrows("Cannot create slope with non existing lift", InvalidLiftException.class,
        			  ()->c.createSlope(V3_SLOPE, RED, "ABCD"));
        
    }

    @Test
    public void testGetSlopes() throws InvalidLiftException{
        
        c.createSlope(V3_SLOPE, BLACK, SALATI_LIFT);
        c.createSlope(G7_SLOPE, RED, SALATI_LIFT);
        
        Collection<String> slopes = c.getSlopes();
        
        assertNotNull(slopes);
        assertEquals(2,slopes.size());
        assertTrue(slopes.contains(V3_SLOPE));
        assertTrue(slopes.contains(G7_SLOPE));
    }

    @Test
    public void testGetPistePartenza() throws InvalidLiftException{
        
        c.createSlope(V3_SLOPE, BLACK, SALATI_LIFT);
        c.createSlope(G7_SLOPE, RED, SALATI_LIFT);
                
        Collection<String> slopes = c.getSlopesFrom(SALATI_LIFT);
        
        assertNotNull(slopes);
        assertEquals(2,slopes.size());
        assertTrue(slopes.contains(V3_SLOPE));
        assertTrue(slopes.contains(G7_SLOPE));
    }
}
