package it.polito.po.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import it.polito.ski.InvalidLiftException;
import it.polito.ski.SkiArea;


public class TestR5File {

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
    
    @Test
    public void testLiftTypes() throws IOException, InvalidLiftException{
        
        String f = writeFile("T ; S4P; Chairlift; 4\n"
        				   + "T;sk; Ski-lift; 1");
        
        SkiArea c = new SkiArea("Monterosa ski");
        
        c.readLifts(f);
        
        assertEquals(4,c.getCapacity("S4P"));
        assertEquals(1,c.getCapacity("sk"));
        
        
        assertEquals("Chairlift",c.getCategory("S4P"));
        assertEquals("Ski-lift",c.getCategory("sk"));
    }
    
    @Test
    public void testSkipWrongLines() throws IOException, InvalidLiftException{
        
        String f = writeFile( "T;S4P;Chairlift;4\n"
        					+ "T;sl;\n"	// wrong lines that should be skipped
        					+ "T;sk;Ski-lift;1");
        
        SkiArea c = new SkiArea("Monterosa ski");
        
        c.readLifts(f);
        
        
        assertEquals("Chairlift",c.getCategory("S4P"));
        assertEquals(4,c.getCapacity("S4P"));
        
        assertThrows(InvalidLiftException.class,
        			 ()->c.getCapacity("sl"));
        
        assertEquals(1,c.getCapacity("sk"));
        assertEquals("Ski-lift",c.getCategory("sk"));
    }
    
    @Test
    public void testLifts() throws IOException, InvalidLiftException{
        
        String f = writeFile( "T;S4P;Chairlift;4\n"
        					+ "T;tapis;Tapis Roulant;1\n" 
                            + "L;Jolanda;S4P\n"
                            + "L;Orsia;tapis");
        
        SkiArea c = new SkiArea("Monterosa ski");
        
        c.readLifts(f);
        
        Collection<String> lifts = c.getLifts();
        assertNotNull("No lifts", lifts);
        assertEquals(2,lifts.size());
        
        
        assertEquals("S4P",c.getType("Jolanda"));
        assertEquals("tapis",c.getType("Orsia"));
    }
    
    @Test
    public void testLiftsErr() throws IOException, InvalidLiftException{
        
        String f = writeFile( "T;S4P;Seggiovia;4\n"
        					+ "T;tapis;Tapis Roulant;1\n" 
                            + "L;\n"
                            + "L;Jolanda;S4P\n"
                            + "L;Orsia;tapis");
        
        SkiArea c = new SkiArea("Monterosa ski");
        
        c.readLifts(f);
        
        Collection<String> lifts = c.getLifts();
        assertNotNull("No lifts", lifts);
        assertEquals(2,lifts.size());
        
        assertEquals("S4P",c.getType("Jolanda"));
        assertEquals("tapis",c.getType("Orsia"));
    }
}
