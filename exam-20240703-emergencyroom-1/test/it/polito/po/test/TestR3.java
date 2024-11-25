package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polito.emergency.EmergencyApp;
import it.polito.emergency.EmergencyException;
import it.polito.emergency.Report;

public class TestR3 {

    private EmergencyApp app;

    @Before
    public void setUp() {
        app = new EmergencyApp();
    }

    @Test(expected = EmergencyException.class)
    public void testAssignPatientToProfessionalNoPatientFound() throws EmergencyException {
        app.assignPatientToProfessional("nonexistentPatientCode", "Cardiology");
    }

    @Test
    public void testAssignPatientToProfessionalNoProfessionalFound() throws EmergencyException {
		String fiscalCode = "123456789";
        app.addProfessional("1", "John", "Doe", "Orthopedy", "2024-01-01 to 2024-12-31");
        app.addPatient(fiscalCode, "Alice", "Wonderland", "1990-01-01", "Emergency", "2024-01-01");

        assertThrows("No professional for required specialization should raise exception",
            EmergencyException.class,
                        ()->app.assignPatientToProfessional(fiscalCode, "Cardiology"));
    }


    @Test
    public void testAssignPatientToProfessional() throws EmergencyException {
        String specialization = "Cardiology";
		String fiscalCode = "123456789";
        app.addProfessional("1", "John", "Doe", specialization, "2024-01-01 to 2024-12-31");
        app.addPatient(fiscalCode, "Alice", "Wonderland", "1990-01-01", "Emergency", "2024-01-01");

        String professionalId = app.assignPatientToProfessional(fiscalCode, specialization);
        assertNotNull("Professional ID should not be null", professionalId);
    }

    @Test(expected = EmergencyException.class)
    public void testSaveReportProfessionalNotFound() throws EmergencyException {
        app.saveReport("nonexistentProfId", "123456789", "2024-01-01", "Detailed report description");
    }

    @Test
    public void testSaveReport() throws EmergencyException {
        String professionalId = "1";
        String patientCode = "123456789";
        app.addProfessional(professionalId, "John", "Doe", "Cardiology", "2024-01-01 to 2024-12-31");
        app.addPatient(patientCode, "Alice", "Wonderland", "1990-01-01", "Emergency", "2024-01-01");
        
        String reportDate = "2024-01-02";
        String description = "Patient is recovering well.";
        
        Report r = app.saveReport(professionalId, patientCode, reportDate, description);
        assertTrue("Report code should be a valid non-zero value", Integer.parseInt(r.getId()) > 0);
    }
}
