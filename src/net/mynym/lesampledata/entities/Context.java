package net.mynym.lesampledata.entities;

import java.util.ArrayList;
import java.util.List;

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
	public List<Activity> activities = new ArrayList<>();
	

	String toLine() {
		StringBuilder line = new StringBuilder();
		line.append(id + "\t");
		line.append(name + "\t");
		line.append(type + "\t");
		line.append(team + "\t");
		line.append(initiationDate + "\t");
		line.append((finalisationDate == null ? "" : finalisationDate) + "\t");
		line.append((finalStatus == null ? "" : finalStatus) + "\r\n");
		return line.toString();
	}

}
