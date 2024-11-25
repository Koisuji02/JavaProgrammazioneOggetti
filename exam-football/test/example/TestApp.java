package example;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.polito.oop.futsal.*;
import it.polito.oop.futsal.Fields.Features;

public final class TestApp {

    @Test
    public void test() throws FutsalException  {
        Fields fields = new Fields();
//R1                      
                   
        fields.defineFields( new Features(false,false,false),   // field 1
                             new Features(true,true,false),     // field 2
                             new Features(true,false,true),     // field 3
                             new Features(true,true,true)   	// field 4
                            );
        
        assertEquals(4,fields.countFields());
        assertEquals(3,fields.countIndoor());
                
        fields.setOpeningTime("14:30");
        fields.setClosingTime("23:30");
        
        assertEquals("14:30",fields.getOpeningTime());
        assertEquals("23:30",fields.getClosingTime());
        
// R2
        
        int p1 = fields.newAssociate("Genny", "Sava", "3334445566");
        int p2 = fields.newAssociate("Remo", "Williams", "3337778899");
        int p3 = fields.newAssociate("Ugo", "Ughi", "3331112233");

        
        assertTrue(p1!=p2);
        assertEquals(3,fields.countAssociates());
        
// R3
        fields.bookField(1, p1, "16:30");
        
        assertTrue(fields.isBooked(1, "16:30"));
        
        fields.bookField(4, p1, "18:30");
        fields.bookField(3, p2, "14:30");
        fields.bookField(3, p3, "15:30");
        
// R4
        List<FieldOption> options = fields
        		.findOptions("17:30",
        				new Features(true,		// indoor -> REQUIRED
        							 false,     // heating --> DON'T CARE
        							 false));   // AC --> DONT'R CARE
      
        assertNotNull(options);
        assertEquals(3,options.size()); // only three fields are indoor
        for(FieldOption f: options){
                System.out.println(f.getField() +" "+ f.getOccupation());
        }
        assertEquals(3,options.get(0).getField()); // field 2

        int p4 = fields.newAssociate("Ivo", "Uva", "3336665544");
        fields.bookField(2, p4, "18:30");
        fields.bookField(2, p1, "20:30");
        
// R5
        assertEquals(4,fields.countServedAssociates());
        assertEquals(0.16,fields.occupation(),0.01);
        
        Map<Integer,Long> ft = fields.fieldTurnover();
        assertNotNull(ft);
        assertEquals(2L,ft.get(2).longValue());
    }
}
