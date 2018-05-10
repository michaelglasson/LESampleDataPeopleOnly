package net.mynym.lesampledata.processing;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.mynym.lesampledata.entities.Activity;
import net.mynym.lesampledata.entities.Association;
import net.mynym.lesampledata.entities.AssociationRepo;
import net.mynym.lesampledata.entities.ContextRepo;
import net.mynym.lesampledata.entities.Involvement;
import net.mynym.lesampledata.entities.Person;
import net.mynym.lesampledata.entities.PersonRepo;

public class CreateWorld {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ContextRepo cRepo = new ContextRepo();
		PersonRepo pRepo = new PersonRepo();
		pRepo.seed(1000);
		AssociationRepo aRepo = new AssociationRepo();
		ContextRepo.pRepo = pRepo;
		ContextRepo.aRepo = aRepo;
		for (int i = 0 ; i < 100 ; i++) {
			cRepo.addNewContext();
		}
		
		System.out.println("Completed run. Here are some statistics");
		Runtime rt = Runtime.getRuntime();
		System.out.println("Free memory: " + rt.freeMemory() / 1000000L + 
				" MB, Total memory: " + rt.totalMemory() / 100000L + " MB\n");
		
		System.out.println("Number of contexts: " + cRepo.contexts.size());
		
		System.out.println("Number of Persons: " + pRepo.persons.size() + ", Number of Associations: " + 
				aRepo.associations.size());
		
		System.out.println("Number of Activities: " + Activity.countOfActivity() +
				", Number of Involvements: " + Involvement.countOfInvolvement());
		// Average number of involvements for an association
		int i = 0, j=0;
		for (Association a: aRepo.associations.values()) {
			i+= a.countOfInvolvements();
			if (a.countOfInvolvements > j) j = a.countOfInvolvements;
		}
		System.out.println("Average number of Involvements for an Association: " + i/aRepo.associations.size());
		System.out.println("Maximum number of Involvements for an Asssociation: " + j);
		
		i = 0; j=0;
		for (Person a: pRepo.persons.values()) {
			i+= a.countOfInvolvements();
			if (a.countOfInvolvements > j) j = a.countOfInvolvements;
		}
		System.out.println("Average number of Involvements for a Person: " + i/pRepo.persons.size());
		System.out.println("Maximum number of Involvements for a Person: " + j);
		
		WriteWorldToFile.writeToFlatFiles(cRepo, pRepo, aRepo);
		//pRepo.startGraphService();
		//pRepo.WritePersonsToGraph();

	}

}
