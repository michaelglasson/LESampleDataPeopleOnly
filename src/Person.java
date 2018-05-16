
import java.time.LocalDate;

public class Person {
	public static Integer firstId = 1;
	public static Integer lastId = firstId;
	public Integer id = lastId++;
	public String lastName;
	public String givenName1;
	public String givenName2;
	public String dateOfBirth;
	public String sex;
	public LocalityRepo.Locality loc;

	static LastNames l = new LastNames();
	static FemaleFirstNames f = new FemaleFirstNames();
	static MaleFirstNames m = new MaleFirstNames();

	public Person() {
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
		dateOfBirth = HelperFunctions.getRandomAgeAtDate(base).toString();
		loc = LocalityRepo.getRandomLocality();
	}

	public String toLine() {
		StringBuilder line = new StringBuilder();
		line.append(id + "\t");
		line.append(lastName + "\t");
		line.append(givenName1 + "\t");
		line.append(givenName2 + "\t");
		line.append(givenName1 + " " + givenName2 + " " + lastName.toUpperCase() + "\t");
		line.append(dateOfBirth + "\t");
		line.append(sex + "\t");
		line.append(loc.name + "\t");
		line.append(loc.postcode.code + "\t");
		if (loc.postcode.code.startsWith("26")) {
			line.append("ACT");
		} else {
			switch (loc.postcode.code.charAt(0)) {
			case '0':
				line.append("NT");
				break;
			case '2':
				line.append("NSW");
				break;
			case '3':
				line.append("Vic");
				break;
			case '4':
				line.append("Qld");
				break;
			case '5':
				line.append("SA");
				break;
			case '6':
				line.append("WA");
				break;
			case '7':
				line.append("Tas");
			}
		}
		line.append("\t");

		line.append("[");
		for (int i = 0; i < 4; i++) {
			line.append(HelperFunctions.nextInt(10));
			line.append(i < 3 ? " " : "]");
		}
		line.append("\n");
		return line.toString();
	}

}
