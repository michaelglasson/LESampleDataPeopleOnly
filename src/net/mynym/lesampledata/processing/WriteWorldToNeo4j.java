package net.mynym.lesampledata.processing;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import net.mynym.lesampledata.entities.AssociationRepo;
import net.mynym.lesampledata.entities.ContextRepo;
import net.mynym.lesampledata.entities.PersonRepo;
import net.mynym.lesampledata.entities.LocalityRepo;


public class WriteWorldToNeo4j {

	/*
	 * person isIn association; activity isFor context; 
	 * activity involves person 
	 */

	public static void writeToNeo4j(LocalityRepo pCodeRepo, ContextRepo cRepo, PersonRepo pRepo, AssociationRepo aRepo) {
		GraphDatabaseService db = new GraphDatabaseFactory()
				.newEmbeddedDatabase(new File("C:\\Users\\mg\\Documents\\neo4j\\data\\databases\\graph.db"));
		System.out.println("Started database");
		try (Transaction tx = db.beginTx()) {
			pCodeRepo.graph(db);
			pRepo.graph(db);
			aRepo.graph(db);
			cRepo.graph(db);
			tx.success();
		}
	}
}
