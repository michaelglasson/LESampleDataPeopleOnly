package net.mynym.lesampledata.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import net.mynym.lesampledata.processing.Graphable;
import net.mynym.lesampledata.processing.Labels;
import net.mynym.lesampledata.processing.RelTypes;

/*
 * A Context is the business context within which activities take place. In turn, activities
 * discover people, things and links between them. A Context is associated with a team of 
 * people who perform the activities.
 */
public class Context implements Graphable {
	public static Integer lastId = 1000;
	public static Random r = new Random();
	public static List<String> types;
	public Node graphNode;
	public Integer id = lastId++;
	public String name;
	public String type;
	public String team;
	public String initiationDate;
	public String finalisationDate;
	public String finalStatus;
	public Set<Activity> activities = new HashSet<>();
	public Set<Involvement> involvements = new HashSet<>();

	void addSomeActivities() {
		int numOfActivities = r.nextInt(8);
		for (int i = 0; i <= numOfActivities; i++) {
			new Activity(this);
		}
	}

	public Context() {
		// This for deserialisation from file
	}

	public Context(Boolean byProgram) {
		if (types == null)
			loadContextTypesFromFile();
		name = "C-" + id;
		type = types.get(r.nextInt(types.size()));
		team = "Team-" + (r.nextInt(98) + 1);
		initiationDate = HelperFunctions.getRandomDateInLast20Years().toString();
		addSomeActivities();
	}

	public void loadContextTypesFromFile() {
		types = new ArrayList<>();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\Context Types.txt"))) {
			while ((line = br.readLine()) != null) {
				types.add(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public String toLine() {
		StringBuilder line = new StringBuilder();
		line.append(id + "\t");
		line.append(name + "\t");
		line.append(type + "\t");
		line.append(team + "\t");
		line.append(initiationDate + "\t");
		line.append((finalisationDate == null ? "" : finalisationDate) + "\t");
		line.append((finalStatus == null ? "" : finalStatus) + "\r\n");
		return line.toString();
	}

	public String listActivities() {
		StringBuilder s = new StringBuilder();
		for (Activity a : activities) {
			s.append(a.toLine());
		}
		return s.toString();
	}

	public String listInvolvements() {
		StringBuilder s = new StringBuilder();
		for (Involvement i : involvements) {
			s.append(i.toLine() + "\r\n");
		}
		return s.toString();
	}

	@Override
	public void graph(GraphDatabaseService db) {
		try (Transaction tx = db.beginTx()) {
			graphNode = db.createNode(Labels.Context);
			graphNode.setProperty("id", id);
			graphNode.setProperty("name", name);
			graphNode.setProperty("type", type);
			graphNode.setProperty("team", team);
			graphNode.setProperty("initiationDate", initiationDate);
			graphNode.setProperty("finalisationDate", finalisationDate);
			for (Activity a: activities) {
				a.graphNode = db.createNode(Labels.Activity);
				a.graphNode.createRelationshipTo(graphNode, RelTypes.isFor);
				a.graph(db);
			}
			for (Involvement i: involvements) {
				Relationship r = graphNode.createRelationshipTo(i.entity.getGraphNode(), RelTypes.involves);
				r.setProperty("id", id);
				r.setProperty("role", i.type);
				r.setProperty("id", i.activity.id);
			}
			tx.success();
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
