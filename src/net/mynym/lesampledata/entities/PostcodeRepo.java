package net.mynym.lesampledata.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

import net.mynym.lesampledata.processing.GraphingContainer;

public class PostcodeRepo implements GraphingContainer {
	public Map<String, Postcode> postcodes = new HashMap<>();
	public SortedMap<Integer, Locality> allLocalities = new TreeMap<>();
	public Integer cumulativePopulation = 0;
	Random r = new Random();

	public enum loc implements Label {
		postcode, locality
	}

	public enum isIn implements RelationshipType {
		isIn, isWasAt
	}

	@Override
	public void graph(GraphDatabaseService db) {
		for (Postcode p: postcodes.values()) {
			p.graph(db);
		}
	}

	public class Postcode implements GraphingContainer {
		public String code;
		public Set<Locality> localities = new HashSet<>();
		Node graphNode;

		@Override
		public void graph(GraphDatabaseService db) {
			try (Transaction tx = db.beginTx()) {
				graphNode = db.createNode(loc.postcode);
				graphNode.setProperty("Code", code);

				for (Locality s : localities) {
					s.graphNode = db.createNode(loc.locality);
					s.graphNode.setProperty("Name", s.name);
					s.graphNode.createRelationshipTo(graphNode, isIn.isIn);
				}
				tx.success();
			}
		}
	}

	public class Locality {
		public String name;
		public Postcode postcode;
		Node graphNode;
	}

	public PostcodeRepo() {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\Postcodes to Suburbs.txt"))) {
			while ((line = br.readLine()) != null) {
				String[] splitArray = line.split("\\t", 60);
				Postcode p = new Postcode();
				p.code = splitArray[0];
				postcodes.put(p.code, p);
				for (int i = 2; i < splitArray.length; i++) {
					cumulativePopulation += Integer.parseInt(splitArray[1]) / 1000 / (splitArray.length - 2);
					Locality l = new Locality();
					l.name = splitArray[i];
					// System.out.println(l.name + "\t" + cumulativePopulation);
					l.postcode = p;
					p.localities.add(l);
					allLocalities.put(cumulativePopulation, l);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(postcodes.size());
		System.out.println("Cumulative population\t" + cumulativePopulation);

	}

	public Locality getRandomLocality() {
		Integer i = allLocalities.tailMap(r.nextInt(cumulativePopulation) + 1).firstKey();
		return allLocalities.get(i);
	}
}
