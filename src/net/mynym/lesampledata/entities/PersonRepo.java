package net.mynym.lesampledata.entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PersonRepo {
	Map<String, Person> persons = new HashMap<>();
	LastNames l = new LastNames();
	FemaleFirstNames f = new FemaleFirstNames();
	MaleFirstNames m = new MaleFirstNames();
	HelperFunctions help = new HelperFunctions();
	Countries c = new Countries();
	Set<String> uidsUsed = new HashSet<>();
	PostCodeRepo postcodes = new PostCodeRepo();

	public Person createPerson() {
		Person p = new Person();
		while (uidsUsed.add(p.id = uidGen.getKey6()) == false)
			;
		p.lastName = help.getRandom(l.theNames);
		if (help.flipCoin()) {
			p.givenName1 = help.getRandom(f.theNames);
			p.givenName2 = help.getRandom(f.theNames);
			p.sex = "F";
		} else {
			p.givenName1 = help.getRandom(m.theNames);
			p.givenName2 = help.getRandom(m.theNames);
			p.sex = "M";
		}

		LocalDate base = help.getRandomDateInLast20Years();
		p.recordDate = base.toString();
		p.dateOfBirth = help.getRandomAgeAtDate(base).toString();
		p.country = c.getCountry();
		if (LocalDate.parse(p.dateOfBirth).getYear() < LocalDate.parse(p.recordDate).getYear() - 60) {
			p.isAlive = help.r.nextInt(100) > 50 ? "N" : "Y";
		} else {
			p.isAlive = help.r.nextInt(100) > 95 ? "N" : "Y";
		}
		if (p.country.equalsIgnoreCase("Australia")) {
			PostCodeRepo.PostcodeAndSuburb s = postcodes.new PostcodeAndSuburb();
			s = postcodes.getRandomPostcodeAndSuburb();
			p.postcode = s.pCode;
			p.locality = s.suburb;
		} else {
			p.locality = p.country;
			p.postcode = "";
		}
		put(p);
		return p;

	}

	public void writeToFile() throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("Resources\\Person.txt"))) {
			bw.write(printHeader());
			for (Person p : persons.values()) {
				bw.write(p.toLine());
			}
		}
	}

	public String printHeader() {
		StringBuilder line = new StringBuilder();
		line.append("id" + "\t");
		line.append("lastName" + "\t");
		line.append("givenName1" + "\t");
		line.append("givenName2" + "\t");
		line.append("dateOfBirth" + "\t");
		line.append("sex" + "\t");
		line.append("country" + "\t");
		line.append("isAlive" + "\t");
		line.append("locality" + "\t");
		line.append("postcode" + "\t");
		line.append("originalContext" + "\t");
		line.append("recordDate" + "\r\n");
		return line.toString();

	}

	public void put(Person p) {
		persons.put(p.id, p);
	}

	public Person get(String key) {
		return persons.get(key);
	}

}
