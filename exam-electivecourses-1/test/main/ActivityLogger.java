package main;

import java.time.Instant;

import it.polito.oop.elective.Notifier;

/**
 * Public display mockup implementation of notifier that logs the activities on console. 
 * 
 * This is a sample implementation of the {@link it.polito.oop.elective.Notifier}
 * interface that prints the notifications to the standard output.
 *  
 */
public class ActivityLogger implements Notifier {
    private int request=0;
    private int assign=0;
    @Override
    public void assignedToCourse(String id, String course) {
        System.out.println(Instant.now() + " : student " + id + " succesfully enrolled to course " + course);
        assign++;
    }

    @Override
    public void requestReceived(String id) {
        System.out.println(Instant.now() + " : Enrollement Request received for student " + id );
        request++;
    }

    public int countRequest() {
    	return request;
    }

    public int countAssign() {
    	return assign;
    }
}
