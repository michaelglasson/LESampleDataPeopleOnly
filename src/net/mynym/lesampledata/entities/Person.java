package net.mynym.lesampledata.entities;

public class Person {
	public String id;
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
}
