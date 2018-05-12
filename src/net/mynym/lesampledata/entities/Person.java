package net.mynym.lesampledata.entities;

import java.time.LocalDate;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import net.mynym.lesampledata.entities.LocalityRepo.Locality;
import net.mynym.lesampledata.processing.CreateNewWorld;
import net.mynym.lesampledata.processing.GraphableInvolvable;
import net.mynym.lesampledata.processing.Labels;
import net.mynym.lesampledata.processing.RelTypes;

public class Person implements GraphableInvolvable {
	public static Integer firstId = 1000;
	public static Integer lastId = firstId;
	public Integer id = lastId++;
	public Integer countOfInvolvements = 0;
	public Node graphNode;
	public String lastName;
	public String givenName1;
	public String givenName2;
	public String dateOfBirth;
	public String sex;
	public String isAlive;
	public Locality loc;
	public String originalContext;
	public String recordDate;

	static LastNames l = new LastNames();
	static FemaleFirstNames f = new FemaleFirstNames();
	static MaleFirstNames m = new MaleFirstNames();

	public Integer countOfInvolvements() {
		return countOfInvolvements;
	}

	public void incrementCountOfInvolvements() {
		countOfInvolvements++;
	}

	public Person(Boolean byProgram) {
		lastName = HelperFunctions.getRandom(l.theNames);
		if (HelperFunctions.flipCoin()) {
			givenName1 = HelperFunctions.getRandom(f.theNames);
			givenName2 = HelperFunctions.getRandom(f.theNames);
			sex = "F";
		} else {
			givenName1 = HelperFunctions.getRandom(m.theNames);
			givenName2 = HelperFunctions.getRandom(m.theNames);
			sex = "M";
		}

		LocalDate base = HelperFunctions.getRandomDateInLast20Years();
		recordDate = base.toString();
		dateOfBirth = HelperFunctions.getRandomAgeAtDate(base).toString();
		if (LocalDate.parse(dateOfBirth).getYear() < LocalDate.parse(recordDate).getYear() - 60) {
			isAlive = HelperFunctions.r.nextInt(100) > 50 ? "N" : "Y";
		} else {
			isAlive = HelperFunctions.r.nextInt(100) > 95 ? "N" : "Y";
		}
		loc = CreateNewWorld.pCodeRepo.getRandomLocality();
	}

	public String toLine() {
		StringBuilder line = new StringBuilder();
		line.append(id + "\t");
		line.append(lastName + "\t");
		line.append(givenName1 + "\t");
		line.append(givenName2 + "\t");
		line.append(dateOfBirth + "\t");
		line.append(sex + "\t");
		line.append(isAlive + "\t");
		line.append(loc.name + "\t");
		line.append(loc.postcode.code + "\t");
		line.append(originalContext + "\t");
		line.append(recordDate + "\r\n");
		return line.toString();
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getType() {
		return "Person";
	}

	@Override
	public void graph(GraphDatabaseService db) {
		try (Transaction tx = db.beginTx()) {
			graphNode = db.createNode(Labels.Person);
			graphNode.setProperty("lastName", lastName);
			graphNode.setProperty("givenName", givenName1);
			graphNode.setProperty("DoB", dateOfBirth);
			graphNode.setProperty("sex", sex);
			graphNode.setProperty("recordDate", recordDate);
			graphNode.createRelationshipTo(loc.graphNode, RelTypes.isWasAt);
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
