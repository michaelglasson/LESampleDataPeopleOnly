package net.mynym.lesampledata.entities;

/*
 * A Context is the business context within which activities take place. In turn, activities
 * discover people, things and links between them. A Context is associated with a team of 
 * people who perform the activities.
 */
public class Context {
	public String id;
	public String name;
	public String type;
	public String team;
	public String initiationDate;
	public String finalisationDate;
	public String finalStatus;
}
