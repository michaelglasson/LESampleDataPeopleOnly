package net.mynym.lesampledata.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ActivityTypes {
	static final List<String> types = Arrays.asList("Statement", "Research", "Scientific", "Observation");
	static Random r = new Random();
	
	public static String getRandomActivity() {
		return types.get(r.nextInt(types.size()));	
	}
}
