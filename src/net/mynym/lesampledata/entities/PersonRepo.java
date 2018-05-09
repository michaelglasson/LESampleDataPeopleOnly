package net.mynym.lesampledata.entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class PersonRepo {
	public Map<Integer, Person> persons = new HashMap<>();
	static Random r = new Random();
	public GraphDatabaseService db;
	
	public void startGraphService() {
		db = new GraphDatabaseFactory()
				.newEmbeddedDatabase(new File("C:\\Users\\mg\\Documents\\neo4j\\data\\databases\\graph.db"));
	}
	
	public enum person implements Label {person}
	
	public void WritePersonsToGraph() {
		try (Transaction tx = db.beginTx()) {
			for (Person p: persons.values()) {
				p.graphNode = db.createNode(person.person);
				p.graphNode.setProperty("lastName", p.lastName);
				p.graphNode.setProperty("givenName", p.givenName1);
				p.graphNode.setProperty("DoB", p.dateOfBirth);
				p.graphNode.setProperty("sex", p.sex);
				p.graphNode.setProperty("recordDate", p.recordDate);
			}
			tx.success();
		}
	}
	
	public Person addNewPerson() {
		Person p = new Person(true);
		put(p);
		return p;
	}
	
	public void seed(int numOfPersons) {
		for (int i = 0; i < numOfPersons; i++) {
			Person p = new Person(true);
			put(p);
		}
	}
	
	public Person getRandomPerson() {
		return persons.get(r.nextInt(Person.lastId-Person.firstId)+Person.firstId);
	}

	public void writeToFile() throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("Resources\\Person.txt"))) {
			bw.write(printHeader());
			for (Person p : persons.values()) {
				bw.write(p.toLine());
			}
		}
	}

	public String printHeader() {
		StringBuilder line = new StringBuilder();
		line.append("id" + "\t");
		line.append("lastName" + "\t");
		line.append("givenName1" + "\t");
		line.append("givenName2" + "\t");
		line.append("dateOfBirth" + "\t");
		line.append("sex" + "\t");
		line.append("country" + "\t");
		line.append("isAlive" + "\t");
		line.append("locality" + "\t");
		line.append("postcode" + "\t");
		line.append("originalContext" + "\t");
		line.append("recordDate" + "\r\n");
		return line.toString();

	}

	public void put(Person p) {
		persons.put(p.id, p);
	}

	public Person get(Integer key) {
		return persons.get(key);
	}

}
