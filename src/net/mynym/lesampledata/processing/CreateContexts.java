package net.mynym.lesampledata.processing;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.mynym.lesampledata.entities.ContextRepo;

public class CreateContexts {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ContextRepo repo = new ContextRepo();
		repo.loadContextTypesFromFile();
		for (int i = 0 ; i < 10000 ; i++) {
			repo.createContext();
		}
		repo.writeToFile();
	}

}
