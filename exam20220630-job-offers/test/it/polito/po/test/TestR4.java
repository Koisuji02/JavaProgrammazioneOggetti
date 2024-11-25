package it.polito.po.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import jobOffers.*;
import java.util.*;

public class TestR4 {

	JobOffers jo = new JobOffers();

	@Before
    public void setUp() {
		try {
		    jo = new JobOffers();
		    jo.addSkills ("C++", "projectMgmt", "db", "softEng", "userExperience");
		    
		    jo.addPosition("seniorProgrammer", "db:6", "C++:7", "softEng:8");
		    jo.addPosition("projectMgr", "projectMgmt:7", "softEng:7");
		    jo.addPosition("graphicDesigner", "userExperience:8", "softEng:6");
		    
		    jo.addCandidate ("Jack", "C++", "projectMgmt", "db", "softEng");
		    jo.addCandidate ("Helen", "C++", "projectMgmt", "db", "softEng");
		    jo.addCandidate ("Alice", "C++", "projectMgmt", "db", "softEng", "userExperience");
		    
		    jo.addConsultant ("Robert", "C++", "projectMgmt", "db", "softEng", "userExperience");
		    jo.addConsultant ("Emma", "C++", "projectMgmt", "db", "softEng");
		    
		} catch (JOException ex) {fail("unexpected exception during test setup");}
    }
	
	@Test
	public void testDiscardApplications1() throws JOException { //list empty, no applications discarded
		jo.addApplications ("Jack", "seniorProgrammer");
		jo.addRatings ("Robert", "Jack", "C++:8", "db:7", "projectMgmt:7", "softEng:8");
		List <String> list = jo.discardApplications();

		assertNotNull("Missing discarded candidates", list);
		assertTrue(list.isEmpty()); 
	}
	
	@Test
	public void testDiscardApplications2() throws JOException { //one application discarded
		jo.addApplications ("Jack", "seniorProgrammer"); //"seniorProgrammer", "db:6", "C++:7", "softEng:8"
		jo.addRatings ("Robert", "Jack", "C++:8", "db:7", "projectMgmt:7", "softEng:7"); //low rating for softEng
		List <String> list = jo.discardApplications();

		assertNotNull("Missing discarded candidates", list);
		assertEquals (list.toString(), ("[Jack:seniorProgrammer]"));
	}
	
	@Test
	public void testDiscardApplications3() throws JOException { //two applications discarded, no ratings for Helen
		jo.addApplications ("Jack", "seniorProgrammer");
		jo.addRatings ("Robert", "Jack", "C++:8", "db:7", "projectMgmt:7", "softEng:7");
		jo.addApplications ("Helen", "projectMgr");
		List <String> list = jo.discardApplications();

		assertNotNull("Missing discarded candidates", list);
		assertEquals (list.toString(), ("[Helen:projectMgr, Jack:seniorProgrammer]"));
	}
	
	@Test
	public void testDiscardApplications4() throws JOException { //one applications discarded
		jo.addApplications ("Jack", "seniorProgrammer");
		jo.addRatings ("Robert", "Jack", "C++:8", "db:7", "projectMgmt:7", "softEng:7");
		jo.addApplications ("Helen", "projectMgr"); //"projectMgr", "projectMgmt:7", "softEng:7"
		jo.addRatings ("Robert", "Helen", "C++:8", "db:7", "projectMgmt:7", "softEng:7");
		List <String> list = jo.discardApplications();

		assertNotNull("Missing discarded candidates", list);
		assertEquals (list.toString(), ("[Jack:seniorProgrammer]")); //Helen:projectMgr is ok
	}
	
	@Test
	public void testDiscardApplications5() throws JOException { //two applications discarded, test on numbers
		jo.addApplications ("Jack", "seniorProgrammer");
		jo.addRatings ("Robert", "Jack", "C++:8", "db:7", "projectMgmt:7", "softEng:7");
		jo.addApplications ("Helen", "projectMgr");
		List <String> list = jo.discardApplications();

		assertNotNull("Missing discarded candidates", list);
		assertEquals (2, list.size());
	}
	
	@Test
	public void testEstablishWinners1() throws JOException { 
		jo.addApplications ("Jack", "seniorProgrammer"); //jo.addPosition("seniorProgrammer", "db:6", "C++:7", "softEng:8");
		jo.addApplications ("Helen", "seniorProgrammer");
		jo.addRatings ("Robert", "Jack", "C++:8", "db:7", "projectMgmt:7", "softEng:7");
		jo.addRatings ("Robert", "Helen", "C++:8", "db:7", "projectMgmt:7", "softEng:7");
		List<String> candidateWinners = jo.getEligibleCandidates("projectMgr");
		
		assertNotNull("Missing eligible candidates", candidateWinners);
		assertTrue(candidateWinners.isEmpty()); 
	}
	
	@Test
	public void testEstablishWinners2() throws JOException { 
		jo.addApplications ("Jack", "seniorProgrammer"); //jo.addPosition("seniorProgrammer", "db:6", "C++:7", "softEng:8");
		jo.addApplications ("Helen", "seniorProgrammer");
		jo.addRatings ("Robert", "Jack", "C++:8", "db:7", "projectMgmt:7", "softEng:8");
		jo.addRatings ("Robert", "Helen", "C++:8", "db:7", "projectMgmt:7", "softEng:8");
		List<String> candidateWinners = jo.getEligibleCandidates("seniorProgrammer");
		
		assertNotNull("Missing eligible candidates", candidateWinners);
		assertEquals(("[Helen, Jack]"), candidateWinners.toString()); 
	}
	
	@Test
	public void testEstablishWinners3() throws JOException { 
		jo.addApplications ("Jack", "seniorProgrammer"); //jo.addPosition("seniorProgrammer", "db:6", "C++:7", "softEng:8");
		jo.addApplications ("Helen", "seniorProgrammer");
		jo.addRatings ("Robert", "Jack", "C++:8", "db:7", "projectMgmt:7", "softEng:7");
		jo.addRatings ("Robert", "Helen", "C++:8", "db:7", "projectMgmt:7", "softEng:8");
		List<String> candidateWinners = jo.getEligibleCandidates("seniorProgrammer");
		
		assertNotNull("Missing eligible candidates", candidateWinners);
		assertEquals("[Helen]", candidateWinners.toString()); 
	}
}
