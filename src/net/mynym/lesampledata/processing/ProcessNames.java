package net.mynym.lesampledata.processing;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import net.mynym.lesampledata.entities.Countries;
import net.mynym.lesampledata.entities.FemaleFirstNames;
import net.mynym.lesampledata.entities.HelperFunctions;
import net.mynym.lesampledata.entities.LastNames;
import net.mynym.lesampledata.entities.MaleFirstNames;
import net.mynym.lesampledata.entities.Person;
import net.mynym.lesampledata.entities.PostCodeRepo;
import net.mynym.lesampledata.entities.uidGen;

public class ProcessNames {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		LastNames l = new LastNames();
		l.createFromFile();
		FemaleFirstNames f = new FemaleFirstNames();
		f.createFromFile();
		MaleFirstNames m = new MaleFirstNames();
		m.createFromFile();
		HelperFunctions help = new HelperFunctions();
		Countries c = new Countries();
		c.createFromFile();
		Set<String> uids = new HashSet<>();
		PostCodeRepo postcodes = new PostCodeRepo();
		postcodes.loadFromFile();
		Person p = new Person();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("Person.txt"))) {
			bw.write(p.printHeader());

			for (int i = 0; i < 10000; i++) {
				while (uids.add(p.id = uidGen.getKey6()) == false)
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
				p.countryOfCitizenship = c.getCountry();
				p.SetIsAlive(help);
				if (p.countryOfCitizenship.equalsIgnoreCase("Australia")) {
					PostCodeRepo.PostcodeAndSuburb s = postcodes.new PostcodeAndSuburb();
					s = postcodes.getRandomPostcodeAndSuburb();
					p.nominalPostcode = s.pCode;
					p.nominalLocality = s.suburb;
				} else {
					p.nominalLocality = p.countryOfCitizenship;
					p.nominalPostcode = "";
				}
				bw.write(p.toLine());
			}
		}
	}
}
