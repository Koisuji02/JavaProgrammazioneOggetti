package it.polito.po.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import jobOffers.*;

public class TestR1 {
	JobOffers jo = new JobOffers();

	@Before
    public void setUp() {
       jo = new JobOffers();
    }

	@Test
	public void testAddSkills1() {
		int j = jo.addSkills ("C++", "projectMgmt", "db");
		assertEquals(3, j); 
	}

	@Test
	public void testAddSkills2() {
		int j = 0;
		j = jo.addSkills ("C++", "projectMgmt", "db", "C++", "db", "userEx");
		assertEquals(4, j); 
	}
	
	@Test
	public void testAddPosition1() throws JOException { //ok
		jo.addSkills ("C++", "projectMgmt", "db");
		int j = jo.addPosition("seniorProgrammer", "db:6", "C++:7");
		assertEquals(6, j); 
	}
	
	@Test  (expected=JOException.class)
	public void testAddPosition2() throws JOException {  //wrong level, the range is 4..8 inclusive
		jo.addSkills ("C++", "projectMgmt", "db");
		jo.addPosition("seniorProgrammer", "db:6", "C++:9");
	}
	
	
	@Test  (expected=JOException.class)
	public void testAddPosition3b() throws JOException { //duplicated position
			jo.addSkills ("C++", "projectMgmt", "db");
			jo.addPosition("seniorProgrammer", "db:6", "C++:7");
			jo.addPosition("seniorProgrammer", "projectMgmt:6");
	}
	
	
	@Test (expected=JOException.class) 
	public void testAddPosition4() throws JOException { //unknown skill projectMgmt
		jo.addSkills ("C++", "db");
		jo.addPosition("seniorProgrammer", "projectMgmt:6");
	}
}
