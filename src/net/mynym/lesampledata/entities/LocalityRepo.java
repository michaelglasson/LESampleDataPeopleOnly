package net.mynym.lesampledata.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import net.mynym.lesampledata.processing.Graphable;
import net.mynym.lesampledata.processing.Labels;
import net.mynym.lesampledata.processing.RelTypes;

public class LocalityRepo implements Graphable {
	static final SortedMap<String, Postcode> postcodes = new TreeMap<>();
	static final SortedMap<Integer, Locality> allLocalities = new TreeMap<>();
	static Integer cumulativePopulation = 0;
	
	static final Random r = new Random();

	@Override
	public void graph(GraphDatabaseService db) {
		for (Postcode p : postcodes.values()) {
			p.graph(db);
		}
	}

	public class Postcode implements Graphable {
		public String code;
		public List<Locality> localities = new ArrayList<>();
		Node graphNode;

		@Override
		public void graph(GraphDatabaseService db) {
			try (Transaction tx = db.beginTx()) {
				graphNode = db.createNode(Labels.postcode);
				graphNode.setProperty("Code", code);
				for (Locality s : localities) {
					s.graphNode = db.createNode(Labels.locality);
					s.graphNode.setProperty("Name", s.name);
					s.graphNode.createRelationshipTo(graphNode, RelTypes.isIn);
				}
				tx.success();
			}
		}

		@Override
		public void setGraphNode(Node graphNode) {
			// TODO Auto-generated method stub

		}

		@Override
		public Node getGraphNode() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public class Locality {
		public String name;
		public Postcode postcode;
		Node graphNode;
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
					Locality l = new Locality();
					l.name = splitArray[i];
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
		
		Postcode overseas = new Postcode();
		overseas.code = "International";
		postcodes.put(overseas.code, overseas);
		
		try (BufferedReader br = new BufferedReader(new FileReader("Resources\\Countries.txt"))) {
				while ((line = br.readLine()) != null) {
					Locality l = new Locality();
					l.name = line;
					overseas.localities.add(l);
					l.postcode = overseas;
					cumulativePopulation += 20;
					allLocalities.put(cumulativePopulation, l);
				}
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

	public static Locality getRandomLocality() {
		Integer i = allLocalities.tailMap(r.nextInt(cumulativePopulation) + 1).firstKey();
		return allLocalities.get(i);
	}
	
	public static Locality getRandomLocalityFromSamePostcode(Locality l) {
		return l.postcode.localities.get(r.nextInt(l.postcode.localities.size()));
	}
	
	
	public static Locality getRandomLocalityFromSameState(Locality l) {
		
		
		
		return l;
	}

	

	@Override
	public void setGraphNode(Node graphNode) {
		// TODO Auto-generated method stub

	}

	@Override
	public Node getGraphNode() {
		// TODO Auto-generated method stub
		return null;
	}
}
