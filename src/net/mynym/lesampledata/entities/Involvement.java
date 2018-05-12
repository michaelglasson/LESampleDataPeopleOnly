package net.mynym.lesampledata.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.neo4j.graphdb.Node;

import net.mynym.lesampledata.entities.AssociationRepo.Association;
import net.mynym.lesampledata.processing.CreateNewWorld;
import net.mynym.lesampledata.processing.GraphableInvolvable;

/*
 * An Involvement is a link between a Context and an Entity or Association
 */
public class Involvement {
	public static Integer firstId = 1000;
	public static Integer lastId = firstId;
	public Node graphNode;
	public Integer id;
	static Random r = new Random();
	public String type;
	public Context context; // The Context of this Involvement
	public Activity activity; // The Activity that discovered this Involvement
	public GraphableInvolvable entity; // Person, thing or association involved
	
	public static int countOfInvolvement() {
		return lastId-firstId;
	}
	public static String printHeader() {
		return "id\ttype\tcontextId\tactivityId\tentityType\tentityId\r\n";
	}
	 public String toLine() {
		 return id + "\t" + type + "\t" + context.id + "\t" +
				 activity.id + "\t" + entity.getType() + "\t" + entity.getId() + "\r\n";
	 }
	
	public Involvement(Context c, Activity activity) {
		id = lastId++;
		type = InvolvementTypes.getRandomInvolvementType();
		context = c;
		c.involvements.add(this);
		this.activity = activity;
		if (r.nextInt(100) > 4) {
			// Want a person
			if (r.nextInt(100) > 60) {
				// Create new person
				entity = CreateNewWorld.pRepo.addNewPerson();
			}
			else {
				// Use an existing person
				entity = CreateNewWorld.pRepo.getRandomPerson();
			}
		}
		else {
			// Want an association
			Association a;
			if (r.nextInt(100) > 89) {
				// Create new Association
				a = CreateNewWorld.aRepo.addNewAssociation();
			}
			else {
				// Use an existing Association
				a = CreateNewWorld.aRepo.getRandomAssociation();
			}
			// In either case, add some participants
			a.addSomeParticipants();
			entity = a;
		}
		entity.incrementCountOfInvolvements();
	}
	
	public static class InvolvementTypes {
		static List<String> types = Arrays.asList("Suspect", "Witness", "Offender", "Victim");
		static Random r = new Random();
		
		public static String getRandomInvolvementType() {
			return types.get(r.nextInt(types.size()));	
		}

	}

}
