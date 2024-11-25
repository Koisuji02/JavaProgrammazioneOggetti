package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polito.emergency.EmergencyApp;
import it.polito.emergency.EmergencyException;

public class TestR5 {
    private static final String DATE = "2024-06-18";
    private EmergencyApp app;

    @Before
    public void setUp()  throws Exception {
        app = new EmergencyApp(); 

		app.addDepartment("Cardiology", 1);

		app.addProfessional("1", "Toy", "Romi", "Cardiology", "2024-06-12 to 2024-06-30");

		String surname = "Wonderland";
        app.addPatient("1234567890", "Alice", surname, "1990-01-01", "Checkup", DATE);
        app.addPatient("0987654321", "Bob", surname, "1992-02-02", "Emergency", DATE);
        app.addPatient("4287548754", "Ron", surname, "1993-01-01", "Checkup", DATE);
        app.addPatient("7426854875", "Joy", surname, "1994-02-02", "Emergency", "2024-06-20");

    }

    @Test
    public void testGetNumberOfPatients() {
		assertEquals("Should return the correct number of patients still in management",
            4, app.getNumberOfPatients());
    }

    @Test
    public void testGetNumberOfPatientsByDate() {
        assertEquals("Should return the correct number of patients for the date",
            3, app.getNumberOfPatientsByDate(DATE));
    }

    @Test
    public void testGetNumberOfPatientsHospitalizedByDepartmentWithInvalidDepartment() {
        assertThrows("Invalid department should not be accepted", EmergencyException.class,
        ()->app.getNumberOfPatientsHospitalizedByDepartment("NonexistentDepartment"));
    }

    @Test
    public void testGetNumberOfPatientsHospitalizedByDepartment() throws EmergencyException {
        String departmentName = "Cardiology";
		app.dischargeOrHospitalize("0987654321", departmentName);
        assertEquals("Should return the correct number of hospitalized patients", 1, app.getNumberOfPatientsHospitalizedByDepartment(departmentName));
		app.dischargeOrHospitalize("7426854875", departmentName);
	}

    @Test
    public void testGetNumberOfPatientsDischarged() throws EmergencyException {
		String departmentName = "Cardiology";
		app.dischargeOrHospitalize("0987654321", departmentName);
		app.dischargeOrHospitalize("1234567890", departmentName);
        assertEquals("Should return the correct number of discharged patients", 1, app.getNumberOfPatientsDischarged());
    }

    @Test
    public void testGetNumberOfPatientsAssignedToProfessionalDischarged() throws EmergencyException {
        String specialization = "Cardiology";
		app.assignPatientToProfessional("1234567890", specialization);
        app.assignPatientToProfessional("7426854875", specialization);

        assertEquals("No patients discharged by the specified specialization", 
                    0, app.getNumberOfPatientsAssignedToProfessionalDischarged(specialization));
    }
}
