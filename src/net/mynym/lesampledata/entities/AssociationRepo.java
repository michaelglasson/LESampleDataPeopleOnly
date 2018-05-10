package net.mynym.lesampledata.entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AssociationRepo {
	public Map<Integer, Association> associations = new HashMap<>();
	public static final Random r = new Random();
	
	public Association addNewAssociation() {
		Association a = new Association();
		put(a);
		return a;
	}
	

	
	public String printHeader() {
		StringBuilder line = new StringBuilder();
		line.append("id" + "\t");
		line.append("name" + "\t");
		line.append("type" + "\r\n");
		return line.toString();
	}

	
	public String listAssociationPersons() {
		StringBuilder s = new StringBuilder();
		for (Association a: associations.values()) {
			s.append(a.listParticipants());
		}
		return s.toString();
	}
	
	public String listAssociations() {
		StringBuilder s = new StringBuilder();
		for (Association a: associations.values()) {
			s.append(a.toLine());
		}
		return s.toString();
	}
	
	public Association getRandomAssociation() {
		Association a;
		if (associations.isEmpty()) {
			a = new Association();
			put(a);
		} else {
		a = associations.get(r.nextInt(Association.lastId-Association.firstId)+Association.firstId);
		}
		a.addSomeParticipants();
		return a;
	}
	
	public void put(Association a) {
		associations.put(a.id, a);
	}
	public Association get(Integer key) {
		return associations.get(key);
	}

	
}
