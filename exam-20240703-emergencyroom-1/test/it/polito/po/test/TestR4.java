package it.polito.po.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import it.polito.emergency.EmergencyApp;
import it.polito.emergency.EmergencyException;
import it.polito.emergency.Patient;
import it.polito.emergency.EmergencyApp.*;


public class TestR4 {

    private static final String patientCode = "34765234298";
    private static final String deptName = "Cardiology";

    private EmergencyApp app;
    private Patient p;

    @Before
    public void setUp()  throws Exception {
        app = new EmergencyApp();
        app.addDepartment(deptName, 2); // Adding department with space
        p = app.addPatient(patientCode, "Bob", "Builder", "1995-05-05", "Urgent Care", "2024-01-02");
    }

	@Test(expected = EmergencyException.class)
    public void testDischargeOrHospitalize() throws EmergencyException {
        app.dischargeOrHospitalize(patientCode, "NonExistentDepartment");
    }

    @Test
    public void testDischargeOrHospitalizeNoPatient() throws EmergencyException {
        String dpt= "Surgery";
		app.addDepartment(dpt, 0);
        assertThrows("Non existing patinet ", EmergencyException.class,
		              ()->app.dischargeOrHospitalize(patientCode+"X", dpt));

    }

	@Test
    public void testDischargeOrHospitalizeNoSpace() throws EmergencyException {
        String dpt= "Surgery";
		app.addDepartment(dpt, 0);
		app.dischargeOrHospitalize(patientCode, dpt);

		assertEquals(PatientStatus.DISCHARGED, p.getStatus());
    }

    // REMOVED due to inconsistency

    // @Test
    // public void testDischargeOrHospitalizePatientHospitalizes() throws EmergencyException {
    //     String dpt= "Surgery";
	// 	app.addDepartment(dpt, 1);
    //     app.dischargeOrHospitalize(patientCode, dpt);

    //     String patient2Code = "1234567890";
    //     app.addPatient(patient2Code, "Alice", "Wonderland", "1990-01-01", "Checkup", "2024-01-01");
    //     app.dischargeOrHospitalize(patient2Code, deptName);

    //     int status = app.verifyPatient(patient2Code);
    //     assertEquals("Patient should be in a department", 0, status);
    // }

    // @Test // modified
    // public void testDischargeOrHospitalizePatientDischarged() throws EmergencyException {
    //     app.dischargeOrHospitalize(patientCode, deptName);

    //     String dpt= "Surgery";
	// 	app.addDepartment(dpt, 0);
    //     String patient2Code = "1234567890";
    //     app.addPatient(patient2Code, "Alice", "Wonderland", "1990-01-01", "Checkup", "2024-01-01");
    //     app.dischargeOrHospitalize(patient2Code, dpt);

    //     int status = app.verifyPatient(patient2Code);
    //     assertEquals("Patient should be in a department", 0, status);
    // }

    // @Test
    // public void testDischargeOrHospitalizePatientHospitalized() throws EmergencyException {
    //     app.dischargeOrHospitalize(patientCode, deptName);

    //     int status = app.verifyPatient(patientCode);
    //     assertNotEquals("Patient should be in a department", 0, status);
    // }

    @Test(expected = EmergencyException.class)
    public void testVerifyPatientNotExisting() throws EmergencyException {
        app.verifyPatient("nonexistentPatientCode");
    }

    // @Test (expected = EmergencyException.class)
    // public void testVerifyPatientNeverHospitalized() throws EmergencyException {
    //     String nonXsistentPatientCode = "3624537246234";
        
    //     app.verifyPatient(nonXsistentPatientCode);
    // }

}
