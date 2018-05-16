

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LastNames {
	public List<String> theNames = new ArrayList<>(25000);

	public LastNames() {
		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\LastNames10000.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				theNames.add(line);
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Number of last names: " + theNames.size());
	}

}
