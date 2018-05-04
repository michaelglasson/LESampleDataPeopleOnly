package net.mynym.lesampledata.entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ContextRepo {
	public Map<String, Context> contexts = new HashMap<>();
	public List<String> cTypes = new ArrayList<>();
	Random r = new Random();
	int firstContextNum = 1000000;

	public Context createContext() {
		Context c = new Context();
		c.id = String.valueOf(firstContextNum++);
		c.name = "Context-" + c.id;
		c.type = cTypes.get(r.nextInt(cTypes.size()));
		c.team = "Team-" + (r.nextInt(98) + 1);
		c.initiationDate = getRandomDateInLast20Years().toString();
		put(c);
		return c;
	}

	public void writeToFile() throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("Resources\\Context.txt"))) {
			bw.write(printHeader());
			for (Context c : contexts.values()) {
				bw.write(c.toLine());
			}
		}
	}

	String printHeader() {
		StringBuilder line = new StringBuilder();
		line.append("id" + "\t");
		line.append("name" + "\t");
		line.append("type" + "\t");
		line.append("team" + "\t");
		line.append("initiationDate" + "\t");
		line.append("finalisationDate" + "\t");
		line.append("finalStatus" + "\r\n");
		return line.toString();
	}

	public void loadContextTypesFromFile() throws FileNotFoundException, IOException {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\Context Types.txt"))) {
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

	public LocalDate getRandomDateInLast20Years() {
		LocalDate today = LocalDate.now();
		int year = today.getYear() - r.nextInt(20) - 1;
		int month = r.nextInt(12) + 1;
		int day = r.nextInt(28) + 1;
		return LocalDate.of(year, month, day);

	}

}
