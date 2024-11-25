package it.polito.po.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polito.emergency.EmergencyApp;
import it.polito.emergency.EmergencyException;
import it.polito.emergency.Patient;
import it.polito.emergency.EmergencyApp.*;


public class TestR2 {

    private static final String fiscalCode = "1234567890";
    private static final String name = "Wendy";
    private static final String surname = "Wonderland";
    private static final String dateOfBirth = "1990-01-01";
    private static final String reason = "Checkup";
    private static final String dateTimeAccepted = "2024-06-28";

    private EmergencyApp app;
    private Patient patient;

    @Before
    public void setUp() {
        app = new EmergencyApp(); 
        patient = app.addPatient(fiscalCode, name, surname, dateOfBirth, reason, dateTimeAccepted);
    }

    @Test
    public void testAddPatient() {
        assertNotNull("Patient should not be null", patient);
        assertEquals("Check patient fiscal code", fiscalCode, patient.getFiscalCode());
        assertEquals("Check patient status", PatientStatus.ADMITTED, patient.getStatus());
        assertEquals("Wrong patient date of birth", dateOfBirth, patient.getDateOfBirth());
        assertEquals("Wrong patient reason", reason, patient.getReason());
    }

    @Test
    public void testGetPatientByFiscalCode() throws EmergencyException {
        List<Patient> ps = app.getPatient(fiscalCode);
        assertNotNull("Patient should be found", ps.get(0));
        assertEquals("Fiscal code should match", fiscalCode, ps.get(0).getFiscalCode());
    }

    @Test
    public void testGetPatientBySurname() throws EmergencyException {
        app.addPatient("0987654321", "Bob", surname, "1992-02-02", "Emergency", dateTimeAccepted);

        List<Patient> patients;
        patients = app.getPatient(surname);
        assertNotNull("Patients list should not be null", patients);
        assertTrue("Should find multiple patients with same surname", patients.size() > 1);	
    }

    @Test
    public void testGetPatientsByDate() {
        app.addPatient("0987654321", "Bob", "Wonderland", "1992-02-02", "Emergency", "2024-06-23");

        List<String> patientCodes = app.getPatientsByDate(dateTimeAccepted);
        assertFalse("List should not be empty", patientCodes.isEmpty());
        assertTrue("Should contain the fiscal code of today's patient", patientCodes.contains("1234567890"));

		assertEquals("1234567890", patientCodes.get(0));
	}


    @Test
    public void testGetPatientsByDate2() {
        app.addPatient("0987654321", "Bob", "Brown", "1992-02-02", "Emergency", dateTimeAccepted);
        app.addPatient("0987654322", "John", "James", "1993-01-09", "Emergency", dateTimeAccepted);

        List<String> patientCodes = app.getPatientsByDate(dateTimeAccepted);
        assertNotNull("Missing codes by patient", patientCodes);
        assertEquals("List should not be empty", 3, patientCodes.size());

		assertEquals("0987654321", patientCodes.get(0));
		assertEquals(fiscalCode, patientCodes.get(2));
	}


    @Test
    public void testGetPatientNotFound() throws EmergencyException {
        assertEquals(0, app.getPatient("265t2875245").size() );
    }
}
