package net.mynym.lesampledata.entities;

import java.util.Random;

/*
 * An Involvement is a link between a Context and an Entity or Association
 */
public class Involvement {
	public static Integer firstId = 400 * 1000 * 1000;
	public static Integer lastId = firstId;
	public Integer id;
	static Random r = new Random();
	public String type;
	public Integer contextId; // The Context of this Involvement
	public Integer activityId; // The Activity that discovered this Involvement
	public Involvable entity; // Person, thing or association involved
	
	public static int countOfInvolvement() {
		return lastId-firstId;
	}
	public static String printHeader() {
		return "id\ttype\tcontextId\tactivityId\tentityType\tentityId\r\n";
	}
	 public String toLine() {
		 return id + "\t" + type + "\t" + contextId + "\t" +
				 activityId + "\t" + entity.getType() + "\t" + entity.getId() + "\r\n";
	 }
	
	public Involvement() {
		// This is for deserialisation from file
	}

	public Involvement(Context c, Integer activityId) {
		id = lastId++;
		type = InvolvementTypes.getRandomInvolvementType();
		contextId = c.id;
		c.involvements.add(this);
		this.activityId = activityId;
		if (r.nextInt(100) > 20) {
			// Want a person
			if (r.nextInt(100) > 80) {
				// Create new person
				entity = ContextRepo.pRepo.addNewPerson();
			}
			else {
				// Use an existing person
				entity = ContextRepo.pRepo.getRandomPerson();
			}
		}
		else {
			// Want an association
			Association a;
			if (r.nextInt(100) > 90) {
				// Create new Association
				a = ContextRepo.aRepo.addNewAssociation();
			}
			else {
				// Use an existing Association
				a = ContextRepo.aRepo.getRandomAssociation();
			}
			// In any case, add some participants
			a.addSomeParticipants();
			entity = a;
		}
		entity.incrementCountOfInvolvements();
	}
	
	
}
