package net.mynym.lesampledata.entities;

import java.util.HashMap;
import java.util.Map;

public class PersonRepo {
	public Map<String, Person> people = new HashMap<>();
	
	public void put(Person p) {
		people.put(p.id, p);
	}
	
	public Person get(String key) {
		return people.get(key);
	}
}
