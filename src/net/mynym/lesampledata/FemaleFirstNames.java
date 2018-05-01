package net.mynym.lesampledata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FemaleFirstNames {
	List<String> theNames = new ArrayList<>(2100);

	public void createFromFile() throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("FemaleFirstNames.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				theNames.add(line);
			}
		}
		System.out.println(theNames.size());

	}

}
