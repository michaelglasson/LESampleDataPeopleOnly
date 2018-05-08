package net.mynym.lesampledata.processing;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.mynym.lesampledata.entities.AssociationRepo;
import net.mynym.lesampledata.entities.ContextRepo;
import net.mynym.lesampledata.entities.PersonRepo;

public class CreateContexts {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ContextRepo cRepo = new ContextRepo();
		PersonRepo pRepo = new PersonRepo();
		pRepo.seed(1000);
		AssociationRepo aRepo = new AssociationRepo();
		ContextRepo.pRepo = pRepo;
		ContextRepo.aRepo = aRepo;
		for (int i = 0 ; i < 1000 ; i++) {
			cRepo.addNewContext();
		}
		
		System.out.println("Completed run. Here are some statistics");
		Runtime rt = Runtime.getRuntime();
		System.out.println("Free memory: " + rt.freeMemory() / 1000000L + " Total memory: " + rt.totalMemory() / 100000L);
		System.out.println("Number of Persons: " + pRepo.persons.size() + ", Number of Associations: " + 
				aRepo.associations.size());
		
		WriteWorldToFile.writeToFlatFiles(cRepo, pRepo, aRepo);

	}

}
