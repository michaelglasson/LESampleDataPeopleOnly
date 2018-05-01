package net.mynym.lesampledata;

import java.time.LocalDate;

public class Person {
	String id;
	String lastName;
	String givenName1;
	String givenName2;
	String dateOfBirth;
	String sex;
	String countryOfCitizenship;
	String isAlive;
	String recordDate;
	public String printHeader() {
		StringBuilder line = new StringBuilder();
		line.append("id" + "\t");
		line.append("lastName" + "\t");
		line.append("givenName1" + "\t");
		line.append("givenName2" + "\t");
		line.append("dateOfBirth" + "\t");
		line.append("sex" + "\t");
		line.append("countryOfCitizenship" + "\t");
		line.append("isAlive" + "\t");
		line.append("recordDate" + "\r\n");
		return line.toString();
		
	}
	public String toLine() {
		StringBuilder line = new StringBuilder();
		line.append(id + "\t");
		line.append(lastName + "\t");
		line.append(givenName1 + "\t");
		line.append(givenName2 + "\t");
		line.append(dateOfBirth + "\t");
		line.append(sex + "\t");
		line.append(countryOfCitizenship + "\t");
		line.append(isAlive + "\t");
		line.append(recordDate + "\r\n");
		return line.toString();
	}
	
	public void SetIsAlive(HelperFunctions d) {
		if (LocalDate.parse(dateOfBirth).getYear() < 
				LocalDate.parse(recordDate).getYear() - 60
				) {
			isAlive = d.r.nextInt(100) > 50 ? "N" : "Y";
			return;
		}
		isAlive = d.r.nextInt(100) > 95 ? "N" : "Y";
	}

}
