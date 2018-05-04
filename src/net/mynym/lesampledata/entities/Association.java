package net.mynym.lesampledata.entities;

import java.util.ArrayList;
import java.util.List;

/*
 * An association captures relationships between people and contexts
 */
public class Association {
	String id;
	String type;
	String name;
	List<Person> participants = new ArrayList<>();
	
	/*
	 * Add a random number of participants chosen from the existing
	 * PersonRepo
	 */
	public void createParticipants(PersonRepo repo) {
		
	}
}
