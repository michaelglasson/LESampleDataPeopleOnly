package net.mynym.lesampledata.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AssociationTypes {
	List<String> types = Arrays.asList("Club", "Family", "Institution", "PresentAt");
	Random r = new Random();
	
	public String getRandomAssociation() {
		return types.get(r.nextInt(types.size()));	
	}
}
