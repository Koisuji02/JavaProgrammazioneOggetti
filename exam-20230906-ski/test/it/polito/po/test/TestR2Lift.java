package it.polito.po.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.ski.InvalidLiftException;
import it.polito.ski.SkiArea;

public class TestR2Lift {

    public static final String SALATI_LIFT = "Salati";
    public static final String IOLANDA_LIFT = "Iolanda";
    private SkiArea c;
    final static String OVO = "Ovo";
    final static String SG = "S4";

    @Before
    public void setUp() throws InvalidLiftException{
        c = new SkiArea("Monterosa Ski");
        c.liftType(OVO, "Cabin", 8);
        c.liftType(SG, "Chairlift", 4);
    }
    
    @Test
    public void testTipo() throws InvalidLiftException{
        c.createLift(SALATI_LIFT, OVO);
        
        String t = c.getType(SALATI_LIFT);
        
        assertEquals(t, OVO);
    }
    
    @Test
    public void testTipoWrongType(){
    	assertThrows("Cannot create lift with non existing type", InvalidLiftException.class,
  			  ()->c.createLift(SALATI_LIFT, "NoType"));
    }

    @Test
    public void testGetImpianti() throws InvalidLiftException{
        c.createLift(SALATI_LIFT, OVO);
        c.createLift(IOLANDA_LIFT, SG);
        
        Collection<String> result = c.getLifts();
        
        assertNotNull("Missing list of lifts", result);
        assertEquals(2,result.size());
        
        assertTrue(result.contains(SALATI_LIFT));
        assertTrue(result.contains(IOLANDA_LIFT));
    }

    @Test
    public void testGetImpiantiSorted() throws InvalidLiftException{
        c.createLift(SALATI_LIFT, OVO);
        c.createLift(IOLANDA_LIFT, SG);

        List<String> result = c.getLifts();

        assertNotNull("Missing list of lifts", result);
        assertEquals(2,result.size());

        assertEquals("Lifts not in order", IOLANDA_LIFT, result.get(0));
        assertEquals("Lifts not in order", SALATI_LIFT, result.get(1));
    }

}
