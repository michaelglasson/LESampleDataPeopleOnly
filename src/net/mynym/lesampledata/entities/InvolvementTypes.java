package net.mynym.lesampledata.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InvolvementTypes {
	static List<String> types = Arrays.asList("Suspect", "Witness", "Offender", "Victim");
	static Random r = new Random();
	
	public static String getRandomInvolvementType() {
		return types.get(r.nextInt(types.size()));	
	}

}
