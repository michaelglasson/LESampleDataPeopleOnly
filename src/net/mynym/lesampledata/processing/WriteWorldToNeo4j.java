package net.mynym.lesampledata.processing;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;


public class WriteWorldToNeo4j {
	public enum type implements Label {
		Person, Association, Involvement, Activity, Context
	}

	/*
	 * person isIn association; activity isFor context; 
	 * activity involves person 
	 */
	public enum link implements RelationshipType {
		isIn, involves, isFor, 
	}

	public static void main(String[] args) {
		GraphDatabaseService db = new GraphDatabaseFactory()
				.newEmbeddedDatabase(new File("C:\\Users\\mg\\Documents\\neo4j\\data\\databases\\graph.db"));
		System.out.println("Started database");
		try (Transaction tx = db.beginTx()) {
			Node n1 = db.createNode(type.Person);
			n1.setProperty("Name", "Michael");
			n1.setProperty("Age", "64");
			
			Node n2 = db.createNode(type.Association);
			n2.setProperty("Name", "VJMC");
			n1.createRelationshipTo(n2, link.isIn);
			tx.success();
		}
	}
}
