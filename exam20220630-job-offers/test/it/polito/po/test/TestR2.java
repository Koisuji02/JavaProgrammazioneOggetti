package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import jobOffers.*;

public class TestR2 {

	JobOffers jo;


	@Before
    public void setUp() {
		try {
		    jo = new JobOffers();
		    jo.addSkills ("C++", "projectMgmt", "db", "softEng");
		    jo.addPosition("seniorProgrammer", "db:6", "C++:7");
		    jo.addPosition("projectMgr", "projectMgmt:7", "softEng:7");
		} catch (JOException ex) {fail("unexpected exception during test setup");}
    }

	@Test
	public void testAddCandidate1() throws JOException { //ok
		int j = 0;
		j = jo.addCandidate ("Frank", "projectMgmt", "db");
		assertEquals(2, j); 
	}
	
	@Test (expected=JOException.class) // Unknown skill userEx
	public void testAddCandidate2() throws JOException {
		jo.addCandidate ("Frank", "projectMgmt", "userEx");
	}	
	
	@Test (expected=JOException.class) // duplicated candidate Brian
	public void testAddCandidate3() throws JOException {
		jo.addCandidate ("Brian", "projectMgmt");
		jo.addCandidate ("Brian", "C++");
	}
	
	
	@Test
	public void testAddApplication1() throws JOException { //skills "db", "C++", "projectMgmt", "softEng"
		jo.addCandidate ("Paul", "db", "C++", "projectMgmt", "softEng");
		List<String> list = jo.addApplications ("Paul", "seniorProgrammer", "projectMgr");

		assertEquals("[Paul:projectMgr, Paul:seniorProgrammer]", list.toString());
	}
	
	@Test (expected=JOException.class)
	public void testAddApplication2() throws JOException { //unknown candidate Brian
		jo.addCandidate ("Paul", "db", "C++", "projectMgmt", "softEng");
		jo.addApplications ("Brian", "seniorProgrammer", "projectMgr");
	}
	
	@Test (expected=JOException.class)
	public void testAddApplication3() throws JOException { //unknown position supervisor
		jo.addCandidate ("Paul", "db", "C++", "projectMgmt", "softEng");
		jo.addApplications ("Paul", "seniorProgrammer", "supervisor");
	}
	
	@Test (expected=JOException.class)
	public void testAddApplication4() throws JOException { //not enough skills, missing softEng
		jo.addCandidate ("Paul", "db", "C++", "projectMgmt");
		jo.addApplications ("Paul", "seniorProgrammer", "projectMgr");
	}
	
	@Test
	public void testGetCandidatesForPositions1() throws JOException { //ok
		jo.addCandidate ("Paul", "db", "C++", "projectMgmt", "softEng");
		jo.addApplications ("Paul", "seniorProgrammer", "projectMgr"); //db, C++; projectMgmt, softEng
		jo.addCandidate ("Emily", "projectMgmt", "softEng");
		jo.addApplications ("Emily", "projectMgr");
		SortedMap<String, List<String>> map = jo.getCandidatesForPositions();

		assertNotNull("Missong candidates map", map);
		assertEquals("{projectMgr=[Emily, Paul], seniorProgrammer=[Paul]}", map.toString());
	}
}
