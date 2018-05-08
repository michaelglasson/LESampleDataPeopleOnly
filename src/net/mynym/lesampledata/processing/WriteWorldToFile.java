package net.mynym.lesampledata.processing;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import net.mynym.lesampledata.entities.Activity;
import net.mynym.lesampledata.entities.AssociationRepo;
import net.mynym.lesampledata.entities.Context;
import net.mynym.lesampledata.entities.ContextRepo;
import net.mynym.lesampledata.entities.Involvement;
import net.mynym.lesampledata.entities.Person;
import net.mynym.lesampledata.entities.PersonRepo;

public class WriteWorldToFile {
	public static void writeToFlatFiles(ContextRepo cRepo, PersonRepo pRepo, AssociationRepo aRepo) {
		try (BufferedWriter personWriter = new BufferedWriter(new FileWriter("Resources\\outfiles\\Person.txt"));
				BufferedWriter contextWriter = new BufferedWriter(new FileWriter("Resources\\outfiles\\Context.txt"));
				BufferedWriter associationWriter = new BufferedWriter(new FileWriter("Resources\\outfiles\\Association.txt"));
				BufferedWriter activityWriter = new BufferedWriter(new FileWriter("Resources\\outfiles\\Activity.txt"));
				BufferedWriter involvementWriter = new BufferedWriter(new FileWriter("Resources\\outfiles\\Involvement.txt"));
				BufferedWriter associationPersonWriter = new BufferedWriter(
						new FileWriter("Resources\\outfiles\\AssociationPerson.txt"))) {

			personWriter.append(pRepo.printHeader());
			contextWriter.append(cRepo.printHeader());
			associationWriter.append(aRepo.printHeader());
			activityWriter.append(Activity.printHeader());
			involvementWriter.append(Involvement.printHeader());
			associationPersonWriter.append("associationId\tpersonId\r\n");

			for (Person p : pRepo.persons.values()) {
				personWriter.append(p.toLine());
			}

			associationWriter.append(aRepo.listAssociations());
			associationPersonWriter.append(aRepo.listAssociationPersons());
			contextWriter.append(cRepo.listContexts());
			
			for (Context c: cRepo.contexts.values()) {
				for (Activity a: c.activities) {
					activityWriter.append(a.toLine());
				}
				for (Involvement i: c.involvements) {
					involvementWriter.append(i.toLine());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException f) {
			System.out.println(f.getMessage());
		}

	}

}
