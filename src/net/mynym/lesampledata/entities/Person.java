package net.mynym.lesampledata.entities;

import java.time.LocalDate;

public class Person {
	public String id;
	public String lastName;
	public String givenName1;
	public String givenName2;
	public String dateOfBirth;
	public String sex;
	public String countryOfCitizenship;
	public String isAlive;
	public String nominalLocality;
	public String nominalPostcode;
	public String originalContext;
	public String recordDate;
	
	
	
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
		line.append("nominalLocality" + "\t");
		line.append("nominalPostcode" + "\t");
		line.append("originalContext" + "\t");
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
		line.append(nominalLocality + "\t");
		line.append(nominalPostcode + "\t");
		line.append(originalContext + "\t");
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
