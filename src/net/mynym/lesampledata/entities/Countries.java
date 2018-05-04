package net.mynym.lesampledata.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Countries {
	public List<String> theNames = new ArrayList<>(200);
	Random r = new Random();
	
	public String getCountry() {
		if (r.nextInt(100) > 20) return "Australia";
		return theNames.get(r.nextInt(theNames.size()));
		
	}

	public Countries() {
		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\Countries.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				theNames.add(line);
			}
			System.out.println(theNames.size());
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
