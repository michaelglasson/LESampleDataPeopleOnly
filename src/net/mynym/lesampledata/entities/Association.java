package net.mynym.lesampledata.entities;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.mynym.lesampledata.processing.CreateNewWorld;

/*
 * An association is a type of Entity that contains other Entities
 */
public class Association implements Involvable {
	static final Random r = new Random();
	public static Integer firstId = 200 * 1000 * 1000;
	public static Integer lastId = firstId;
	public Integer id = lastId++;
	public Integer countOfInvolvements = 0;
	public String type = AssociationTypes.getRandomAssociation();
	public String name = type + " " + id;
	public Set<Person> participants = new HashSet<>();
	
	public Integer countOfInvolvements() {
		return countOfInvolvements;
	}
	
	public void incrementCountOfInvolvements() {
		countOfInvolvements++;
	}
	
	public String toLine() {
		return id + "\t" + name + "\t" + type + "\r\n";
	}
	
	public String listParticipants() {
		StringBuilder s = new StringBuilder();
		for (Person p: participants) {
			s.append(id + "\t" + p.id +"\r\n");
		}
		return s.toString();
	}
	
	/*
	 * Add a random number of participants chosen from the existing
	 * PersonRepo
	 */
	public void addSomeParticipants() {
		// Add some number of Participants
		int numOfParticipants = r.nextInt(10);
		for (int i = 0; i <= numOfParticipants; i++) {
			if (r.nextInt(100) > 90) {
				// Create new Participant
				participants.add(CreateNewWorld.pRepo.addNewPerson());
			} else {
				participants.add(CreateNewWorld.pRepo.getRandomPerson());
			}
			
		}
		
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getType() {
		return "Association";
	}

}
