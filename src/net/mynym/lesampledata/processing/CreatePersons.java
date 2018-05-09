package net.mynym.lesampledata.processing;

import java.io.IOException;

import net.mynym.lesampledata.entities.PersonRepo;

public class CreatePersons {
	public static void main(String[] args) throws IOException {
		PersonRepo repo = new PersonRepo();
		for (int i = 0; i < 10000; i++) {
			repo.addNewPerson();
		}
		repo.writeToFile();
	}

}