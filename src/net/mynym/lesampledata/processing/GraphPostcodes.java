package net.mynym.lesampledata.processing;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import net.mynym.lesampledata.entities.PostcodeRepo;

public class GraphPostcodes {
	public static enum country implements Label {country}

	public static void main(String[] args) {
		PostcodeRepo postcodes = new PostcodeRepo();
		GraphDatabaseService db = new GraphDatabaseFactory()
				.newEmbeddedDatabase(new File("C:\\Users\\mg\\Documents\\neo4j\\data\\databases\\graph.db"));
		postcodes.graph(db);
	}
}
