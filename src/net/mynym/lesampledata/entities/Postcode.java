package net.mynym.lesampledata.entities;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

public class Postcode {
	public String postcode;
	List<String> suburbs = new ArrayList<>(50);

	// WriteToNeo4j creates, connects postcodes, suburbs
	// Assumes transaction is being handled by repo

	public enum l implements Label {
		postcode, suburb
	}
	
	public enum isIn implements RelationshipType {isIn}

	public void WriteToNeo4j(GraphDatabaseService db, Node country) {

		try (Transaction tx = db.beginTx()) {
			Node p = db.createNode(l.postcode);
			p.setProperty("Code", postcode);
			p.createRelationshipTo(country, isIn.isIn);
			for (String s: suburbs) {
				Node sub = db.createNode(l.suburb);
				sub.setProperty("Name", s);
				sub.createRelationshipTo(p, isIn.isIn);
			}
			tx.success();

		}
	}
}
