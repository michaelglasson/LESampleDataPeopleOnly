package net.mynym.lesampledata.entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import net.mynym.lesampledata.processing.CreateNewWorld;
import net.mynym.lesampledata.processing.Graphable;
import net.mynym.lesampledata.processing.GraphableInvolvable;
import net.mynym.lesampledata.processing.Labels;
import net.mynym.lesampledata.processing.RelTypes;

public class AssociationRepo implements Graphable {
	public Map<Integer, Association> associations = new HashMap<>();
	public static final Random r = new Random();
	public static Integer firstId = 1000;
	public static Integer lastId = firstId;
	
	public Association addNewAssociation() {
		Association a = new Association();
		put(a);
		return a;
	}
	
	public String printHeader() {
		StringBuilder line = new StringBuilder();
		line.append("id" + "\t");
		line.append("name" + "\t");
		line.append("type" + "\r\n");
		return line.toString();
	}

	public String listAssociationPersons() {
		StringBuilder s = new StringBuilder();
		for (Association a: associations.values()) {
			s.append(a.listParticipants());
		}
		return s.toString();
	}
	
	public String listAssociations() {
		StringBuilder s = new StringBuilder();
		for (Association a: associations.values()) {
			s.append(a.toLine());
		}
		return s.toString();
	}
	
	public Association getRandomAssociation() {
		Association a;
		if (associations.isEmpty()) {
			a = new Association();
			put(a);
		} else {
		a = associations.get(r.nextInt(lastId-firstId)+firstId);
		}
		a.addSomeParticipants();
		return a;
	}
	
	public void put(Association a) {
		associations.put(a.id, a);
	}
	public Association get(Integer key) {
		return associations.get(key);
	}

	public static class AssociationTypes {
		static List<String> types = Arrays.asList("Club", "Family", "Institution", "Event");
		static Random r = new Random();
		
		public static String getRandomAssociation() {
			return types.get(r.nextInt(types.size()));	
		}
		
		public String listTypes() {
			StringBuilder s = new StringBuilder();
			for (String t: types) {
				s.append(t + "\r\n");
			}
			return s.toString();
		}
	}

	
	/*
	 * An association is a type of Entity that contains other Entities
	 */
	public class Association implements GraphableInvolvable {
		public Integer id = lastId++;
		public Integer countOfInvolvements = 0;
		public Node graphNode;
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

		@Override
		public void graph(GraphDatabaseService db) {
			try (Transaction tx = db.beginTx()) {
				graphNode = db.createNode(Labels.Association);
				graphNode.setProperty("Name", name);
				graphNode.setProperty("Type", type);
				for (GraphableInvolvable e : participants) {
					e.getGraphNode().createRelationshipTo(graphNode, RelTypes.isIn);
				}
				tx.success();
			}
		}

		@Override
		public void setGraphNode(Node graphNode) {
			this.graphNode = graphNode;
		}

		@Override
		public Node getGraphNode() {
			return graphNode;
		}
	}


	@Override
	public void graph(GraphDatabaseService db) {
		for (Association a: associations.values()) {
			a.graph(db);
		}
	}

	@Override
	public void setGraphNode(Node graphNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node getGraphNode() {
		// TODO Auto-generated method stub
		return null;
	}
}
