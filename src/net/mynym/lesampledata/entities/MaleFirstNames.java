package net.mynym.lesampledata.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaleFirstNames {
	public List<String> theNames = new ArrayList<>(1300);

	public MaleFirstNames() {
		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\MaleFirstNames.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				theNames.add(line);
			}
			System.out.println("Successfully loaded " + theNames.size() + " male first names");
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
