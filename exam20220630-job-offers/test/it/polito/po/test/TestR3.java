package it.polito.po.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import jobOffers.*;


public class TestR3 {

	private JobOffers jo;

	@Before
    public void setUp() {
		try {
		    jo = new JobOffers();
		    jo.addSkills ("C++", "projectMgmt", "db", "softEng");
		    jo.addPosition("seniorProgrammer", "db:6", "C++:7");
		    jo.addPosition("projectMgr", "projectMgmt:7", "softEng:7");
		    jo.addCandidate ("Jack", "projectMgmt", "db");
		    jo.addCandidate ("Helen", "projectMgmt", "softEng");
		    jo.addCandidate ("Alice", "projectMgmt", "softEng");
		} catch (JOException ex) {fail("unexpected exception during test setup");}
    }
	
	@Test
	public void testAddConsultant1() throws JOException { //ok
		int j = 0;
		j = jo.addConsultant ("Robert", "projectMgmt", "db");

		assertEquals(2, j); 
	}
	
	@Test (expected=JOException.class) // unknown skill userEx
	public void testAddConsultant2() throws JOException {
		jo.addConsultant ("Emma", "projectMgmt", "userEx");
	}	
	
	@Test (expected=JOException.class) // duplicated consultant
	public void testAddConsultant3() throws JOException {
		jo.addConsultant ("Robert", "projectMgmt");
		jo.addConsultant ("Robert", "C++");
	}
	
	@Test 
	public void testAddRatings1() throws JOException { //ok
		int j = 0;
		j = jo.addConsultant ("Robert", "projectMgmt", "db");
		j = jo.addRatings ("Robert", "Jack", "projectMgmt:8", "db:7");
		
		assertEquals(7, j); //15:2 = 7
	}
	
	@Test (expected=Exception.class) //the consultant's skills do not include all those of the candidate
	public void testAddRatings2() throws JOException {
		jo.addConsultant ("Robert", "projectMgmt");
		jo.addRatings ("Robert", "Jack", "projectMgmt:8", "db:7");
	}
	
	@Test (expected=Exception.class) //unknown consultant Robert
	public void testAddRatings3() throws JOException {
		jo.addRatings ("Robert", "Jack", "projectMgmt:8", "db:7");
	}
	
	@Test (expected=Exception.class) //unknown candidate Frank
	public void testAddRatings4() throws JOException { 
		jo.addConsultant ("Robert", "projectMgmt", "db");
		jo.addRatings ("Robert", "Frank", "projectMgmt:8", "db:7");
	}

	
	@Test (expected=Exception.class) //rating out of range, projectMgmt
	public void testAddRatings5() throws JOException {  
		jo.addConsultant ("Robert", "projectMgmt", "db");
		jo.addRatings ("Robert", "Jack", "projectMgmt:11", "db:7"); // 11 out of range
	}
	
	
}
