package net.mynym.lesampledata.entities;

import java.time.LocalDate;
import java.util.Random;

/*
 * An Activity takes place within a Context and creates or modifies involvements,
 * adding them to the Context. Activities are encapsulated fully within Contexts.
 */

public class Activity {
	static Integer lastId = 100 * 1000 * 1000;
	static Random r = new Random();
	public Integer id;
	public Context context;
	public Integer contextId;
	public String type;
	public String date;
	// public String performedBy; // Future id of person doing the activity
	
	public Activity() {
		// This is only for deserialisation from file
	}
	
	public String toString() {
		return id + "\t" + contextId + "\t" + type + "\t" + date;
	}
	
	public Activity(Context c) {
		context = c;
		contextId = c.id;
		id = lastId++;
		type = ActivityTypes.getRandomActivity();
		
		// Calculate Activity Date as some number of days after the Context Initiation Date.
		// If the Context Finalisation Date is null, or before the Activity date, then
		// update the Context Finalisation Date to a week after the Activity Date
		LocalDate d = LocalDate.parse(c.initiationDate).plusDays((long) r.nextInt(365*2));
		date = d.toString();
		if (c.finalisationDate == null || LocalDate.parse(c.finalisationDate).isBefore(d)) {
			c.finalisationDate = d.plusDays(7L).toString();
		}
		c.activities.add(this);
	}
	
	// Activity creates Involvements but Involvements add themselves to Context
	// (so we don't need to assign them here). Create some number of involvements
	public void addSomeInvolvements() {
		int numOfInvolvements = r.nextInt(5);
		for (int i = 0; i <= numOfInvolvements; i++) {
			new Involvement(context, id);
		}
	}
}
