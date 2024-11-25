package it.polito.po.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.emergency.EmergencyApp;
import it.polito.emergency.EmergencyException;
import it.polito.emergency.Professional;

public class TestR1 {

	/* Professional and Department Management */

	private EmergencyApp app;

    @Before
    public void setUp() {
        app = new EmergencyApp();
		app.addProfessional("1","John","Doe","Cardiology","2024-01-01 to 2024-12-31");	
		app.addProfessional("2","Mik","Smith","Surgery","2024-05-01 to 2024-05-28");		
		app.addProfessional("3","Lay","Jeremy","Epidemiology","2024-05-01 to 2024-06-30");		
		app.addProfessional("4","Ally","Holiday","Surgery","2024-05-01 to 2024-06-30");	
		
		app.addDepartment("General Surgery", 15);
		app.addDepartment("Cardiology", 10);
    }

	@Test
    public void testAddProfessional() throws EmergencyException {        
		// Assuming a method to retrieve a professional by ID for verification
        Professional professional = app.getProfessionalById("1");
		assertNotNull("Professional should be added", professional);
		assertEquals("Check specialization", "Cardiology", professional.getSpecialization());	
        assertEquals("Check specialization", "John", professional.getName());	
        assertEquals("Check specialization", "Doe", professional.getSurname());	
    }

    @Test
    public void testGetProfessionalWrongId() {        
		// Assuming a method to retrieve a professional by ID for verification
        assertThrows("There should be no professional with id 99",EmergencyException.class,
                    ()-> app.getProfessionalById("99"));
    }

	@Test
    public void testGetProfessionalsWitSpecialistFound() throws EmergencyException {
        List<String> pid = app.getProfessionals("Surgery");
        assertNotNull("Missing professionals by specialty", pid);
        assertEquals("Wrong number of professionals found", 2, pid.size());
        pid.containsAll(Arrays.asList(new String[]{"2","4"}));
    }

	@Test
    public void testGetProfessionalsWithNoSpecialistFound() {
		assertThrows("No professionals found with specialization: Neurology",
            EmergencyException.class, 
            () -> { app.getProfessionals("Neurology");});
    }

    @Test
    public void testGetProfessionalsInServicePeriod() throws EmergencyException {
        List<String> inService = app.getProfessionalsInService("Surgery", "2024-06-01 to 2024-06-15");
        assertNotNull("Missing perofessional in service for period", inService);
        assertEquals("Wrong number of professionals", 1, inService.size());
        assertEquals("Wrong professional found", "4", inService.get(0));
    }

    @Test
    public void testGetProfessionalsInServiceWithNoSpecialistFound() {
        assertThrows("There should be no cardiology professional",EmergencyException.class,
		            ()->app.getProfessionalsInService("Cardiology", "2023-01-01 to 2023-01-31"));
    }

    @Test
    public void testGetDepartments() throws EmergencyException {
        List<String> departments = app.getDepartments();
        assertEquals(2,departments.size());
    }

    @Test
    public void testGetDepartmentsWithNoDepartments() {
        EmergencyApp appnew = new EmergencyApp();
        assertThrows("No departments should trigger an exception", EmergencyException.class,
                     ()->appnew.getDepartments());
    }

    @Test
    public void testReadFromFileProfessionalsWithNullReader() throws IOException {
        EmergencyApp appnew = new EmergencyApp();
        assertThrows("Null reader should not be accepted",IOException.class,
                    ()->appnew.readFromFileProfessionals(null));
    }

    @Test
    public void testReadFromFileDepartmentsWithNullReader() throws IOException {
        EmergencyApp appnew = new EmergencyApp();
        assertThrows("Null reader should not be accepted",IOException.class,
                    ()->appnew.readFromFileDepartments(null));
    }

    @Test
    public void testReadFromFileProfessionals() throws IOException {
        EmergencyApp appnew = new EmergencyApp();
        StringReader reader = new StringReader("id,name,surname,specialization,period\n1,John,Doe,Cardiology,2020-01-01 to 2020-12-31");
        int count = appnew.readFromFileProfessionals(reader);
        assertEquals(1, count);
    }

    @Test
    public void testReadFromFileDepartments() throws IOException {
        EmergencyApp appnew = new EmergencyApp();
        StringReader reader = new StringReader("name,maxPatients\nCardiology,20");
        int count = appnew.readFromFileDepartments(reader);
        assertEquals(1, count);
    }

}
