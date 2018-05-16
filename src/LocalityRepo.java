

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class LocalityRepo {
	static final SortedMap<String, Postcode> postcodes = new TreeMap<>();
	static final SortedMap<Integer, Locality> allLocalities = new TreeMap<>();
	static Integer cumulativePopulation = 0;

	static final Random r = new Random();


	public class Postcode {
		public String code;
		public List<Locality> localities = new ArrayList<>();
		public Integer firstCumPop = 0;
		public Integer lastCumPop = 0;
	}

	public class Locality {
		public String name;
		public Postcode postcode;
	}

	public LocalityRepo() {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\Postcodes to Suburbs.txt"))) {
			while ((line = br.readLine()) != null) {
				String[] splitArray = line.split("\\t", 60);
				Postcode p = new Postcode();
				p.code = splitArray[0];
				postcodes.put(p.code, p);
				for (int i = 2; i < splitArray.length; i++) {
					cumulativePopulation += Integer.parseInt(splitArray[1]) / 1000 / (splitArray.length - 2);
					if (p.firstCumPop == 0)
						p.firstCumPop = cumulativePopulation;
					Locality l = new Locality();
					l.name = splitArray[i];
					l.postcode = p;
					p.localities.add(l);
					allLocalities.put(cumulativePopulation, l);
				}
				p.lastCumPop = cumulativePopulation;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Number of postcodes: " + postcodes.size());
		System.out.println("Cumulative population of Australia: " + cumulativePopulation);

		Postcode overseas = new Postcode();
		overseas.code = "International";
		postcodes.put(overseas.code, overseas);

		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\Countries.txt"))) {
			while ((line = br.readLine()) != null) {
				Locality l = new Locality();
				l.name = line;
				overseas.localities.add(l);
				l.postcode = overseas;
				cumulativePopulation += 5;
				allLocalities.put(cumulativePopulation, l);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Cumulative population of Australia + International: " + cumulativePopulation);

	}

	public static Locality getRandomLocality() {
		Integer i = allLocalities.tailMap(r.nextInt(cumulativePopulation) + 1).firstKey();
		return allLocalities.get(i);
	}

	public static Locality getRandomLocalityFromSamePostcode(Locality l) {
		return l.postcode.localities.get(r.nextInt(l.postcode.localities.size()));
	}
}
