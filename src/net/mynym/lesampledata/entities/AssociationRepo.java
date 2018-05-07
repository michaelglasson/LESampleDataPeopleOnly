package net.mynym.lesampledata.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AssociationRepo {
	public static final Map<Integer, Association> associations = new HashMap<>();
	public static final Random r = new Random();
	
	public Association addNewAssociation() {
		Association a = new Association();
		put(a);
		return a;
	}
	
	public String listAssociations() {
		StringBuilder s = new StringBuilder();
		for (Association a: associations.values()) {
			s.append(a.toString());
		}
		return s.toString();
	}
	
	public Association getRandomAssociation() {
		return associations.get(r.nextInt(Association.lastId-Association.firstId)+Association.firstId);
	}
	
	public void put(Association a) {
		associations.put(a.id, a);
	}
	public Association get(Integer key) {
		return associations.get(key);
	}

	
}
