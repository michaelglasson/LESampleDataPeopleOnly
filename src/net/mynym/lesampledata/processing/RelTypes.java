package net.mynym.lesampledata.processing;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType {
	isWasAt, isIn, involves, isFor, involved
}
