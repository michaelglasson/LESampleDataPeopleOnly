public class CreateNewWorld {
	public static LocalityRepo pCodeRepo;
	public static PersonRepo pRepo;

	public static void main(String[] args) {
		/*
		 * Order of loading is postcodes, persons, associations, contexts, activities,
		 * involvements.
		 */
		pCodeRepo = new LocalityRepo(); // Loads codes
		pRepo = new PersonRepo(); // Prepares repo

		for (int i = 0; i < 100000; i++) {
			pRepo.addNewPerson();
		}

	System.out.println("Number of Persons: "+ pRepo.persons.size());

	WriteWorldToFile.writeToFlatFiles(pRepo);

}

}
