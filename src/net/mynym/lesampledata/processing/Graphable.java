package net.mynym.lesampledata.processing;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

/*
 * A GraphingContainer, when asked, must create nodes for all its children and
 * then create the correct relationship with its children, e.g. child 'isIn'
 * container. The container must put the child's node into the child's graphNode
 * field. The container does not need to set the properties for the child.
 * The container must then invoke graph(db) on all its children.
 */

public interface Graphable {
	void graph(GraphDatabaseService db);
	void setGraphNode(Node graphNode);
	Node getGraphNode();
}
