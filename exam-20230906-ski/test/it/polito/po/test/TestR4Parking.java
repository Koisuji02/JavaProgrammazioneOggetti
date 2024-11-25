package it.polito.po.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import it.polito.ski.InvalidLiftException;
import it.polito.ski.SkiArea;

public class TestR4Parking {

    public static final String BLACK = "nera";
    public static final String RED = "rossa";
    public static final String TRINITE_PARKING = "Trinite";
    public static final String SALATI_LIFT = "Salati";
    public static final String SEEHORN_LIFT = "Seehorn";
    SkiArea c;
    final static String OVO = "Ovo";
    final static String SG = "S4";

    final static String JOLANDA_LIFT = "Jolanda";
    final static String G3_SLOPE = "G3";
    final static String G4_SLOPE = "G4";
    
    @Before
    public void setUp() throws InvalidLiftException {
        c = new SkiArea("Monterosa Ski");
        c.liftType(OVO, "Cabinovia", 8);
        c.createLift(SALATI_LIFT, OVO);
        c.liftType(SG, "Chairlift", 4);
        c.createSlope("V3", BLACK, SALATI_LIFT);
        c.createSlope("G7", RED, SALATI_LIFT);

        c.createLift(JOLANDA_LIFT, SG);
        c.createSlope(G3_SLOPE, RED, JOLANDA_LIFT);
        c.createSlope(G4_SLOPE, BLACK, JOLANDA_LIFT);

        c.createLift(SEEHORN_LIFT, SG);
    }
    
    @Test
    public void testParkingServing(){
        
        c.createParking(TRINITE_PARKING, 200);
        
        assertEquals(200,c.getParkingSlots(TRINITE_PARKING));
     }

    
    @Test
    public void testCreateParkServing() {
        
        c.createParking(TRINITE_PARKING, 200);

        c.liftServedByParking(SALATI_LIFT, TRINITE_PARKING);
        c.liftServedByParking(JOLANDA_LIFT, TRINITE_PARKING);
        
        Collection<String> lifts = c.servedLifts(TRINITE_PARKING);
        
        assertNotNull("Missing lifts served by parking", lifts);
        assertEquals(2, lifts.size());
        assertTrue(lifts.contains(SALATI_LIFT));
        assertTrue(lifts.contains(JOLANDA_LIFT));
     }

    @Test
    public void testParkProporzionato1(){
        
        c.createParking(TRINITE_PARKING, 200);
        
        c.liftServedByParking(SEEHORN_LIFT, TRINITE_PARKING);
        
        assertFalse(c.isParkingProportionate(TRINITE_PARKING));

        c.liftServedByParking(JOLANDA_LIFT, TRINITE_PARKING);
        
        assertTrue(c.isParkingProportionate(TRINITE_PARKING));
    }
    
    @Test
    public void testParkProporzionato2(){
        
        c.createParking(TRINITE_PARKING, 100);
        
        c.liftServedByParking(SEEHORN_LIFT, TRINITE_PARKING);

        assertTrue(c.isParkingProportionate(TRINITE_PARKING));
     }
     
    @Test
    public void testParkProporzionato4() throws InvalidLiftException{
        
        c.createParking(TRINITE_PARKING, 250);
        
        c.liftServedByParking(SEEHORN_LIFT, TRINITE_PARKING);
        c.liftServedByParking(JOLANDA_LIFT, TRINITE_PARKING);
        
        assertFalse(c.isParkingProportionate(TRINITE_PARKING));
        
        c.createLift("NewOne",SG);
        c.liftServedByParking("NewOne", TRINITE_PARKING);
        
        assertTrue(c.isParkingProportionate(TRINITE_PARKING));
     }
 
}
