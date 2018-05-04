package net.mynym.lesampledata.entities;

import java.util.HashMap;
import java.util.Map;

public class AssociationRepo {
	public Map<String, Association> associations = new HashMap<>();
	AssociationTypes types = new AssociationTypes();
	
	public Association createAssociation() {
		Association a = new Association();
		a.id = "a::" + uidGen.getKey6();
		a.type = types.getRandomAssociation();
		a.name = a.type + "-" + a.id.substring(3);
		return a;
	}
	
	public void put(Association a) {
		associations.put(a.id, a);
	}
	public Association get(String key) {
		return associations.get(key);
	}

	
}
