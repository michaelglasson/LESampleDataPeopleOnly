package net.mynym.lesampledata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LastNames {
	List<String> theNames = new ArrayList<>(25000);

	public void createFromFile() throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("LastNames10000.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				theNames.add(line);
			}
		}
		System.out.println(theNames.size());

	}

}
