package net.mynym.lesampledata.processing;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

public interface Graphable {
	// Graph self and contained objects; must also create relationship to parent
	void graph(GraphDatabaseService db, Node parent, RelationshipType rel, Boolean pointsToParent);
	void graph();
	Node getNode(); // Get graph node  for self only
}
