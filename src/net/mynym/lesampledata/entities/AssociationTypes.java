package net.mynym.lesampledata.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AssociationTypes {
	static List<String> types = Arrays.asList("Club", "Family", "Institution", "PresentAt");
	static Random r = new Random();
	
	public static String getRandomAssociation() {
		return types.get(r.nextInt(types.size()));	
	}
	
	public String listTypes() {
		StringBuilder s = new StringBuilder();
		for (String t: types) {
			s.append(t + "\r\n");
		}
		return s.toString();
	}
}
