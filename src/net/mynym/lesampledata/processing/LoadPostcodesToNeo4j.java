package net.mynym.lesampledata.processing;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import net.mynym.lesampledata.entities.PostCodeRepo;
import net.mynym.lesampledata.entities.Postcode;

public class LoadPostcodesToNeo4j {
	public static enum country implements Label {country}

	public static void main(String[] args) {
		PostCodeRepo pcodes = new PostCodeRepo();
		GraphDatabaseService db = new GraphDatabaseFactory()
				.newEmbeddedDatabase(new File("C:\\Users\\mg\\Documents\\neo4j\\data\\databases\\graph.db"));
		try (Transaction tx = db.beginTx()) {
			Node australia = db.createNode(country.country);
			australia.setProperty("Name", "Australia");
			for (Postcode p: pcodes.postcodes) {
				p.WriteToNeo4j(db, australia);
			}
			tx.success();
		}
	}
}
