package com.mynym.LESampleDataPeopleOnly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreatePersons {
	public static List<String> femaleFirstNames = new ArrayList<>(2100);
	public static List<String> maleFirstNames = new ArrayList<>(1300);
	public static List<String> lastNames = new ArrayList<>(12000);
	public static List<Person> persons = new ArrayList<>(12000);
	public static Random r = new Random();
	static final SortedMap<Integer, Postcode> allCodes = new TreeMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br;
		String line;
		int cumulativePopulation = 0;
		try {
			br = new BufferedReader(new FileReader("Resources\\FemaleFirstNames.txt"));
			while ((line = br.readLine()) != null) {
				femaleFirstNames.add(line);
			}
			System.out.println("Number of female first names: " + femaleFirstNames.size());

			br = new BufferedReader(new FileReader("Resources\\MaleFirstNames.txt"));
			while ((line = br.readLine()) != null) {
				maleFirstNames.add(line);
			}
			System.out.println("Number of male first names: " + maleFirstNames.size());
			
			br = new BufferedReader(new FileReader("Resources\\LastNames10000.txt"));
			while ((line = br.readLine()) != null) {
				lastNames.add(line);
			}
			System.out.println("Number of last names: " + lastNames.size());

			File postcodes = new File("Resources\\jsonout\\Postcodes.json");
			ObjectMapper mapper = new ObjectMapper();
			MappingIterator<Postcode> it = mapper.readerFor(Postcode.class).readValues(postcodes);
			while (it.hasNext()) {
				Postcode p = it.next();
				allCodes.put(cumulativePopulation += p.pop, p);
			}
			System.out.println("Total Population: " + cumulativePopulation);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		for (int i = 0; i < 10000; i++) {
			Person p = new Person();
			persons.add(p);
			p.id = i;
			p.lastName = lastNames.get(r.nextInt(lastNames.size()));
			LocalDate base = getRandomDateInLast20Years();
			p.dateOfBirth = getRandomAgeAtDate(base).toString();
			p.sex = r.nextInt(10) < 5 ? "M" : "F";
			if (p.sex.equalsIgnoreCase("M")) {
				p.givenName1 = maleFirstNames.get(r.nextInt(maleFirstNames.size()));
				p.givenName2 = maleFirstNames.get(r.nextInt(maleFirstNames.size()));

			} else {
				p.givenName1 = femaleFirstNames.get(r.nextInt(maleFirstNames.size()));
				p.givenName2 = femaleFirstNames.get(r.nextInt(maleFirstNames.size()));
			}

			Postcode c = allCodes.get(allCodes.tailMap(r.nextInt(cumulativePopulation) + 1).firstKey());
			p.postcode = c.code;
			p.state = c.state;
			p.locality = c.locs.get(r.nextInt(c.locs.size()));
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("Resources\\jsonout\\Persons.json"), persons);

	}

	public static LocalDate getRandomAgeAtDate(LocalDate baseDate) {
		// Set a random year before baseDate from 4 to 70
		int birthYear = baseDate.getYear() - r.nextInt(66) - 4;
		int birthMonth = r.nextInt(12) + 1;
		int birthDay = r.nextInt(28) + 1;
		return LocalDate.of(birthYear, birthMonth, birthDay);
	}

	public static LocalDate getRandomDateInLast20Years() {
		LocalDate today = LocalDate.now();
		int year = today.getYear() - r.nextInt(20) - 1;
		int month = r.nextInt(12) + 1;
		int day = r.nextInt(28) + 1;
		return LocalDate.of(year, month, day);

	}

}
