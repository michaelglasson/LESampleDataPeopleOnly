package net.mynym.lesampledata.entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextRepo {
	public Map<String, Context> contexts = new HashMap<>();
	public List<String> cTypes = new ArrayList<>();
	
	
	
	public void loadContextTypesFromFile() throws FileNotFoundException, IOException {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader("Context Types.txt"))) {
			while ((line = br.readLine()) != null) {
				cTypes.add(line);
			}
		}
	}
	
	public void put(Context c) {
		contexts.put(c.id, c);
	}

	public Context get(String key) {
		return contexts.get(key);
	}

	public void loadFromFile() throws FileNotFoundException, IOException {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader("Contexts.txt"))) {
			while ((line = br.readLine()) != null) {
				String[] splitArray = line.split("\\t", 20);
				Context c = new Context();
				c.id = splitArray[0];
				c.name = splitArray.length > 1 ? splitArray[1] : null;
				c.type = splitArray.length > 2 ? splitArray[2] : null;
				c.team = splitArray.length > 3 ? splitArray[3] : null;
				c.initiationDate = splitArray.length > 4 ? splitArray[4] : null;
				c.finalisationDate = splitArray.length > 5 ? splitArray[5] : null;
				c.finalStatus = splitArray.length > 6 ? splitArray[6] : null;
				put(c);
			}
		}
	}
}
