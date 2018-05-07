package net.mynym.lesampledata.entities;

import java.time.LocalDate;

public class Person  implements Involvable {
	public static Integer firstId = 500 * 1000 * 1000;
	public static Integer lastId = firstId;
	public Integer id = lastId++;
	public String lastName;
	public String givenName1;
	public String givenName2;
	public String dateOfBirth;
	public String sex;
	public String country;
	public String isAlive;
	public String locality;
	public String postcode;
	public String originalContext;
	public String recordDate;
	
	
	static LastNames l = new LastNames();
	static FemaleFirstNames f = new FemaleFirstNames();
	static MaleFirstNames m = new MaleFirstNames();
	static Countries c = new Countries();
	static PostCodeRepo postcodes = new PostCodeRepo();
	
	public Person() {
		// This is used for deserialising from file
	}
	
	public Person(Boolean byProgram) {
		lastName = HelperFunctions.getRandom(l.theNames);
		if (HelperFunctions.flipCoin()) {
			givenName1 = HelperFunctions.getRandom(f.theNames);
			givenName2 = HelperFunctions.getRandom(f.theNames);
			sex = "F";
		} else {
			givenName1 = HelperFunctions.getRandom(m.theNames);
			givenName2 = HelperFunctions.getRandom(m.theNames);
			sex = "M";
		}

		LocalDate base = HelperFunctions.getRandomDateInLast20Years();
		recordDate = base.toString();
		dateOfBirth = HelperFunctions.getRandomAgeAtDate(base).toString();
		country = c.getCountry();
		if (LocalDate.parse(dateOfBirth).getYear() < LocalDate.parse(recordDate).getYear() - 60) {
			isAlive = HelperFunctions.r.nextInt(100) > 50 ? "N" : "Y";
		} else {
			isAlive = HelperFunctions.r.nextInt(100) > 95 ? "N" : "Y";
		}
		if (country.equalsIgnoreCase("Australia")) {
			PostCodeRepo.PostcodeAndSuburb s = postcodes.new PostcodeAndSuburb();
			s = postcodes.getRandomPostcodeAndSuburb();
			postcode = s.pCode;
			locality = s.suburb;
		} else {
			locality = country;
		}
	}

	
	
	
	public String toLine() {
		StringBuilder line = new StringBuilder();
		line.append(id + "\t");
		line.append(lastName + "\t");
		line.append(givenName1 + "\t");
		line.append(givenName2 + "\t");
		line.append(dateOfBirth + "\t");
		line.append(sex + "\t");
		line.append(country + "\t");
		line.append(isAlive + "\t");
		line.append(locality + "\t");
		line.append(postcode + "\t");
		line.append(originalContext + "\t");
		line.append(recordDate + "\r\n");
		return line.toString();
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getType() {
		return "Person";
	}
}
