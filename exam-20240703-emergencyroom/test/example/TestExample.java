package example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import it.polito.emergency.EmergencyApp;
import it.polito.emergency.EmergencyException;
import it.polito.emergency.Patient;
import it.polito.emergency.EmergencyApp.PatientStatus;
import it.polito.emergency.Report;


public class TestExample {

	private EmergencyApp app;

	@Before
	public void setUp(){
		app = new EmergencyApp();
	}

	@Test
	public void testR1() throws EmergencyException, IOException{
		// R1 - Professional and Department Management
		app.addProfessional("1","John","Doe","Cardiology","2024-01-01 to 2024-12-31");	
		app.addProfessional("2","Mik","Smith","Surgery","2024-05-01 to 2024-05-28");		
		app.addProfessional("3","Lay","Jeremy","Epidemiology","2024-05-01 to 2024-06-30");		
		app.addProfessional("4","Ally","Holiday","Surgery","2024-05-01 to 2024-06-30");		

		List<String> professionals = app.getProfessionals("Surgery");
		assertNotNull("Missing professionals", professionals);
		assertEquals(2, professionals.size());

		assertEquals(1, app.getProfessionalsInService("Cardiology", "2024-01-01 to 2024-01-31").size());
		
		assertThrows("When no professiona il service an exception is expected",
			EmergencyException.class, 
			()->app.getProfessionalsInService("Cardiology", "2023-12-01 to 2023-12-31"));

		assertThrows("When no departments are connected an exception is expected",
			EmergencyException.class, 
			()->app.getDepartments()); 

		app.addDepartment("Cardiology", 10);
		app.addDepartment("General Surgery", 15);

		assertThrows("When no professional with required spec. an exception is expected",
						IOException.class,
						()->app.readFromFileProfessionals(null));

		StringReader reader = new StringReader("id,name,surname,specialization,period\n1,John,Doe,Surgery,2024-01-01 to 2024-12-31");
        int count;
		count = app.readFromFileProfessionals(reader);
		assertEquals(1, count);
	}

	@Test
	public void testR2() throws EmergencyException {
		app.addPatient("1234567890", "Alice", "Wonderland", "1990-01-01", "Checkup", "2024-07-01");

		List<Patient> patients = app.getPatient("Wonderland");
		assertNotNull("Patients list should not be null", patients);
        assertEquals("List should not be empty", 1, patients.size());

		List<String> patientCodes = app.getPatientsByDate("2024-07-01");
		assertNotNull(patientCodes);
        assertFalse("List should not be empty", patientCodes.isEmpty());
	}

	@Test
	public void testR3() throws EmergencyException {
		app.addProfessional("1","John","Doe","Cardiology","2024-01-01 to 2024-12-31");	
		app.addProfessional("2","Mik","Smith","Surgery","2024-05-01 to 2024-05-28");		
		app.addProfessional("3","Lay","Jeremy","Epidemiology","2024-05-01 to 2024-06-30");		
		app.addProfessional("4","Ally","Holiday","Surgery","2024-05-01 to 2024-07-30");		

		app.addPatient("1234567890", "Alice", "Wonderland", "1990-01-01", "Checkup", "2024-07-01");

		String professionalId = app.assignPatientToProfessional("1234567890", "Surgery");
		assertNotNull("Professional ID should not be null", professionalId);

		Report r = app.saveReport(professionalId, "1234567890", "2024-06-24", "Checkup OK");
        assertTrue("Report code should be a valid non-zero value", Integer.parseInt(r.getId()) > 0);
	}

	@Test
	public void testR4() throws EmergencyException {
		app.addProfessional("2","Mik","Smith","Surgery","2024-05-01 to 2024-05-28");		
		app.addProfessional("4","Ally","Holiday","Surgery","2024-05-01 to 2024-07-30");		

		Patient p = app.addPatient("1234567890", "Alice", "Wonderland", "1990-01-01", "Checkup", "2024-07-01");
		assertNotNull("Missing patient", p);

        String dpt= "Surgery";
		app.addDepartment(dpt, 0);
		app.dischargeOrHospitalize("1234567890", dpt);
		assertEquals(PatientStatus.DISCHARGED, p.getStatus());
	}

	@Test
	public void testR5() throws EmergencyException {
		app.addProfessional("2","Mik","Smith","Surgery","2024-05-01 to 2024-05-28");		
		app.addProfessional("4","Ally","Holiday","Surgery","2024-05-01 to 2024-07-30");		

		app.addPatient("1234567890", "Alice", "Wonderland", "1990-01-01", "Checkup", "2024-07-01");

        String dpt= "Surgery";
		app.addDepartment(dpt, 0);
		app.dischargeOrHospitalize("1234567890", dpt);

		assertEquals("Should return the correct number of patients still in management", 
		0, app.getNumberOfPatients());

		assertEquals("Should return the correct number of discharged patients", 
		1, app.getNumberOfPatientsDischarged());
	}
}
