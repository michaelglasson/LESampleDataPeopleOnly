package net.mynym.lesampledata.processing;

import org.neo4j.graphdb.GraphDatabaseService;

public interface GraphingContainer {
	void graph(GraphDatabaseService db);
}
