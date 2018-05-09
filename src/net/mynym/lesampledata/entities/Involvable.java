package net.mynym.lesampledata.entities;

/*
 * Entities are people or things
 */

public interface Involvable {
	Integer getId();
	String getType(); // e.g. Person
	void incrementCountOfInvolvements();
	Integer countOfInvolvements();
	
}
