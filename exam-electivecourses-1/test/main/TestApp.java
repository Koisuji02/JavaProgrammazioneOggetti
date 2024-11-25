package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import it.polito.oop.elective.ElectiveException;
import it.polito.oop.elective.ElectiveManager;

public class TestApp {

	@Test
    public void testAll() throws ElectiveException {
        
        ElectiveManager electiveCourses = new ElectiveManager();
        ActivityLogger l = new ActivityLogger();
        electiveCourses.addNotifier(l);

        electiveCourses.addCourse("VIQ", 3);
        electiveCourses.addCourse("AI", 1);
        electiveCourses.addCourse("OR", 1);
        
        
        Set<String> courses = electiveCourses.getCourses();
        assertNotNull(courses);
        assertEquals(3, courses.size());
        assertTrue(courses.contains("AI"));
        
        
        electiveCourses.loadStudent("S1", 25.5);
        electiveCourses.loadStudent("S2", 27);
        electiveCourses.loadStudent("S3", 25.5);
        electiveCourses.loadStudent("S4", 28);
        electiveCourses.loadStudent("S5", 22);
        
//        System.out.println("Students: " + electiveCourses.getStudents()); 
//        System.out.println("Best students: " + electiveCourses.getStudents(27.0,30.0)); // [S2,S4]

        Collection<String> students = electiveCourses.getStudents();
        assertNotNull(students);
        assertEquals(5, students.size());
        
        Collection<String> bestStudents = electiveCourses.getStudents(27.0,30.0);
        assertNotNull(bestStudents);
        assertEquals(2, bestStudents.size());
        assertTrue(bestStudents.contains("S2"));
        assertTrue(bestStudents.contains("S4"));
        
        
        electiveCourses.requestEnroll("S1",Arrays.asList("VIQ","AI"));
        electiveCourses.requestEnroll("S2",Arrays.asList("VIQ","OR","AI"));
        electiveCourses.requestEnroll("S3",Arrays.asList("AI","OR"));
        electiveCourses.requestEnroll("S4",Arrays.asList("AI","VIQ"));
        electiveCourses.requestEnroll("S5",Arrays.asList("AI","OR"));

        assertThrows(ElectiveException.class,()-> {
            electiveCourses.addCourse("XX", 1);
            electiveCourses.requestEnroll("S7",Arrays.asList("AI","VIQ","OR","XX"));
        });
        
        
        
        //System.out.println("Selections: " + electiveCourses.numberRequests() );
        Map<String,List<Long>> requests = electiveCourses.numberRequests();
        assertNotNull(requests);
        assertEquals(4, requests.size());
        Long[] ai = requests.get("AI").toArray(new Long[3]);
        Long[] ai_exp = {3L,1L,1L};
        assertTrue("Expected " + Arrays.toString(ai_exp) + " but was " + Arrays.toString(ai),
        			Arrays.equals(ai_exp, ai));
        
        long unsatisfied = electiveCourses.makeClasses();
        
        //System.out.println("Not satisfied: " + unsatisfied);  // 1
        assertEquals(1, unsatisfied);

        double success = electiveCourses.successRate(1);
        //System.out.println("Success rate for first choice: " + success*100); // 60
        //assertEquals(0.6, success, 0.001);

        //System.out.println("Classes: " + electiveCourses.getAssignments());  //  {XX=[], OR=[S3], AI=[S4], VIQ=[S2, S1]}
        Map<String,List<String>> assignments = electiveCourses.getAssignments();
        assertNotNull(assignments);
        assertEquals(4, assignments.size());
        System.out.println(assignments);
        //assertTrue(assignments.get("VIQ").contains("S2"));

        //System.out.println("Not assigned: " + electiveCourses.getNotAssigned());  // [S5]
        Collection<String> unassigned = electiveCourses.getNotAssigned();
        System.out.println(unassigned);
        assertNotNull(unassigned);
        assertEquals(1,unassigned.size());
        assertTrue(unassigned.contains("S5"));
        
        assertEquals(5, l.countRequest());
        assertEquals(4, l.countAssign());
    }    
}
