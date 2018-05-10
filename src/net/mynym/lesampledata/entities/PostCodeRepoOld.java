package net.mynym.lesampledata.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostCodeRepo {
	public List<Postcode> postcodes = new ArrayList<>();
	List<Integer> l = new ArrayList<>();
	Random r = new Random();

	public class PostcodeAndSuburb {
		public String pCode;
		public String suburb;
	}

	public PostcodeAndSuburb getRandomPostcodeAndSuburb() {
		Postcode p = postcodes.get(l.get(r.nextInt(l.size())));
		PostcodeAndSuburb pAndS = new PostcodeAndSuburb();
		pAndS.pCode = p.postcode;
		pAndS.suburb = p.suburbs.get(r.nextInt(p.suburbs.size()));
		return pAndS;
	}

	public PostCodeRepo() {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\Postcodes to Suburbs.txt"))) {
			while ((line = br.readLine()) != null) {
				String[] splitArray = line.split("\\t", 60);
				Postcode p = new Postcode();
				p.postcode = splitArray[0];
				for (int i = 2; i < splitArray.length; i++) {
					p.suburbs.add(splitArray[i]);
				}
				postcodes.add(p);
				Integer pCodePop = Integer.parseInt(splitArray[1]) / 1000;
				for (int j = 0; j < pCodePop; j++) {
					l.add(postcodes.size() - 1);
				}
			}
			System.out.println(postcodes.size());
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
