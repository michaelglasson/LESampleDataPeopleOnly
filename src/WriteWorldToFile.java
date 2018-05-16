

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class WriteWorldToFile {
	public static void writeToFlatFiles(PersonRepo pRepo) {
		try (BufferedWriter personWriter = new BufferedWriter(new FileWriter("Resources\\outfiles\\Person.txt"));) {

			personWriter.append(pRepo.printHeader());

			for (Person p : pRepo.persons.values()) {
				personWriter.append(p.toLine());
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException f) {
			System.out.println(f.getMessage());
		}

	}

}
