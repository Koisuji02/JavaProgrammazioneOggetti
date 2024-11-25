package example;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import it.polito.ski.InvalidLiftException;
import it.polito.ski.SkiArea;


public class TestExample {

    @Test
    public void testAll() throws InvalidLiftException, IOException {
        
        SkiArea vl = new SkiArea("Via Lattea");
        
        assertEquals("Via Lattea", vl.getName());

        vl.liftType("S4P","Seggiovia",4);
        
        assertEquals(4, vl.getCapacity("S4P"));
        
        vl.createLift("Frateive", "S4P");
        
        assertEquals("S4P", vl.getType("Frateive"));

        vl.createSlope("79", "red", "Frateive");
        vl.createSlope("Rio Nero", "black", "Frateive");
        
        assertEquals("red", vl.getDifficulty("79"));
        assertEquals(2, vl.getSlopesFrom("Frateive").size());
                
        vl.createParking("Jouvenceaux", 100);
        
        assertEquals(100, vl.getParkingSlots("Jouvenceaux"));
       
        vl.liftServedByParking("Frateive", "Jouvenceaux");
        
        Collection<String> l = vl.servedLifts("Jouvenceaux");
        
        assertNotNull(l);
        assertEquals(1, l.size());
        assertTrue(l.contains("Frateive"));
        
        assertTrue(vl.isParkingProportionate("Jouvenceaux"));

        String path = writeFile(
        		"T;S2;Chairlift;2\n" +
        		"L;Cime Bianche;S2"
        		);
        
        vl.readLifts(path);
        
        assertEquals(2, vl.getCapacity("S2"));
        assertEquals("S2", vl.getType("Cime Bianche"));
    }

    
    /**
     * Create a new temporary file and write the content
     * @param content content of the file
     * @return the path of the new file.
     * @throws IOException in case of file writing error
     */
    private static String writeFile(String content) throws IOException {          
            File f = File.createTempFile("off","txt");
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(content.getBytes());
            fos.close();
            return f.getAbsolutePath();
    }

}
