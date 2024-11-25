package example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import it.polito.project.ReviewException;
import it.polito.project.ReviewServer;

public class TestExample {

	@Test
	public void testAll() throws ReviewException {
		ReviewServer mgr = new ReviewServer();
		
		// R1 : groups and reviews
		
		mgr.addGroups("Group1","Bucaneers","Dolphins");
		
		Collection<String> groups = mgr.getGroups();
		assertNotNull(groups);
		assertEquals(3, groups.size());
		assertTrue(groups.contains("Bucaneers"));
		
		String reviewId = mgr.addReview("Project Thesis Meeting","Goal: check requierements and tests","Bucaneers");
		mgr.addReview("Final Review","Check complete project","Bucaneers");
		String arnold = mgr.addReview("Project 1 test review","Analize test suite","Group1");
		
		assertThrows(ReviewException.class,
					 ()-> mgr.addReview("Initial review","Check process","Doors"));

		Collection<String> reviews = mgr.getReviews("Bucaneers");
		assertNotNull(reviews);
		assertEquals(2,reviews.size());
		
		assertEquals("Project Thesis Meeting",mgr.getReviewTitle(reviewId));
		assertEquals("Goal: check requierements and tests",mgr.getReviewTopic(reviewId));
		
		// R2 : reviews time slots
		
		// define slot on 2023-06-28 from 10:00 to 12:00
		String date = "2023-06-28";
		double hours = mgr.addOption(reviewId, date, "10:00", "12:00");
		assertEquals(2,hours,0.001);
		
		mgr.addOption(reviewId, date, "14:00", "16:00");
		mgr.addOption(reviewId, date, "16:00", "17:30");
		mgr.addOption(reviewId, "2023-07-04", "15:00", "17:00");

		mgr.addOption(arnold, "2023-07-30", "20:00", "22:00");
		
		assertThrows("Overlapping slots should fail", ReviewException.class,
				 ()-> mgr.addOption(reviewId, date, "11:00", "13:00"));

		Map<String,List<String>> slots = mgr.showSlots(reviewId);
		assertNotNull(slots);
		assertEquals(2,slots.size());
		assertTrue(slots.containsKey(date));
		assertEquals(3, slots.get(date).size());
		assertTrue(slots.get(date).contains("10:00-12:00"));
		
		
		// R3 : students preferences
		
		mgr.openPoll(reviewId);
		
		String email = "s987654@studenti.polito.it";
		int n1  = mgr.selectPreference(email,"Giovanni","Bianchi",reviewId,date,"10:00-12:00");
		int n2 = mgr.selectPreference("rossil@ura.it","Laura","Rossi",reviewId,date,"10:00-12:00");
		mgr.selectPreference("eva@kant.eu","Eva","Kant",reviewId,"2023-07-04","15:00-17:00");
		
		assertEquals(1, n1);
		assertEquals(2, n2);
		
		assertThrows("Not enabled", ReviewException.class,
					 () -> mgr.selectPreference("sarah@sky.net","Sarah","Connor",arnold,"2023-07-30","20:00-22:00") );
		
		Collection<String> preferences = mgr.listPreferences(reviewId);
		assertNotNull(preferences);
		assertEquals(3,preferences.size());
		assertTrue("Missing slot in " + preferences, preferences.contains(date+"T10:00-12:00="+email));
		
		
		// R4 : close meeting poll
		
		Collection<String> bestOptions = mgr.closePoll(reviewId);
		
		
		assertNotNull(bestOptions);
		assertEquals(1, bestOptions.size());
		assertEquals(date+"T10:00-12:00=2", bestOptions.iterator().next());
		
		
		// R5 : stats
		
		Map<String,List<String>> prefs = mgr.reviewPreferences(reviewId);
		assertNotNull(prefs);
		assertEquals(2, prefs.size());
		assertEquals(1, prefs.get(date).size());
		assertEquals("10:00-12:00=2", prefs.get(date).get(0));
		
		
		Map<String,Integer> prefCount = mgr.preferenceCount();
		assertNotNull(prefCount);
		assertEquals(1, prefCount.size());
		assertEquals(Integer.valueOf(3), prefCount.get(reviewId));
	}
		
}
