package net.mynym.lesampledata.entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Countries {
	public List<String> theNames = new ArrayList<>(200);
	Random r = new Random();
	
	public String getCountry() {
		if (r.nextInt(100) > 40) return "Australia";
		return theNames.get(r.nextInt(theNames.size()));
		
	}

	public void createFromFile() throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("Countries.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				theNames.add(line);
			}
		}
		System.out.println(theNames.size());
	}
}
