package net.mynym.lesampledata.entities;

import java.util.ArrayList;
import java.util.List;

/*
 * An Activity takes place within a Context and results in one or more of:
 * creating a new person, creating a new association, adding a person to an
 * Association. If the Activity creates a new Person, then the Person's
 * record will be tagged with Context id.
 */

public class Activity {
	public String id;
	public String type;
	public String date;
	public List<Association> associations = new ArrayList<>();
	public List<Person> persons = new ArrayList<>();
}
