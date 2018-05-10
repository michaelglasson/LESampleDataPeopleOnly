package net.mynym.lesampledata.entities;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

import net.mynym.lesampledata.entities.PostcodeRepo.Locality;
import net.mynym.lesampledata.entities.PostcodeRepo.isIn;
import net.mynym.lesampledata.entities.PostcodeRepo.loc;
import net.mynym.lesampledata.processing.Graphable;

/*
 * An Activity takes place within a Context and creates or modifies involvements,
 * adding them to the Context. Activities are encapsulated fully within Contexts.
 * Activity is not a container.
 */

public class Activity implements Graphable {
	static Integer firstId = 100 * 1000 * 1000;
	static Integer lastId = firstId;
	static Random r = new Random();
	Node graphNode;
	public Integer id;
	public Context context;
	public Integer contextId;
	public String type;
	public String date;
	// public String performedBy; // Future id of person doing the activity
	
	public static int countOfActivity() {
		return lastId - firstId;
	}
	
	public static String printHeader() {
		return "id\tcontextId\ttype\tdate\r\n";
	}
	public String toLine() {
		return id + "\t" + contextId + "\t" + type + "\t" + date + "\r\n";
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
		addSomeInvolvements();
	}
	
	// Activity creates Involvements but Involvements add themselves to Context
	// (so we don't need to assign them here). Create some number of involvements
	public void addSomeInvolvements() {
		int numOfInvolvements = r.nextInt(3);
		for (int i = 0; i <= numOfInvolvements; i++) {
			new Involvement(context, id);
		}
	}
	
	public static class ActivityTypes {
		static final List<String> types = Arrays.asList("Interview", "Research", "Scientific", "Observation");
		static Random r = new Random();
		
		public static String getRandomActivity() {
			return types.get(r.nextInt(types.size()));	
		}
	}

	public enum aLabel implements Label {activity}
	
	@Override
	public void graph(GraphDatabaseService db, Node parent, RelationshipType rel, Boolean pointsToParent) {
		try (Transaction tx = db.beginTx()) {
			graphNode = db.createNode(aLabel.activity);
			graphNode.setProperty("Type", type);
			for (Locality s : localities) {
				s.graphNode = db.createNode(loc.locality);
				s.graphNode.setProperty("Name", s.name);
				s.graphNode.createRelationshipTo(graphNode, isIn.isIn);
			}
			tx.success();
		}

		
		
	}

	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return graphNode;
	}

}
