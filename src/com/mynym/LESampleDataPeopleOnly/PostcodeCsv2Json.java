package com.mynym.LESampleDataPeopleOnly;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class PostcodeCsv2Json {
	public static void main(String[] args) throws IOException {
		List<Postcode> allCodes;
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(Postcode.class);
		File csvFile = new File("Resources\\Postcodes to Suburbs.txt");
		MappingIterator<Postcode> it = mapper.readerFor(Postcode.class).with(schema).readValues(csvFile);
		allCodes = it.readAll();
		for (Postcode p : allCodes) {
			if (p.code.startsWith("26")) {
				p.state = "ACT";
			} else {
				switch (p.code.charAt(0)) {
				case '0':
					p.state = "NT";
					break;
				case '2':
					p.state = "NSW";
					break;
				case '3':
					p.state = "Vic";
					break;
				case '4':
					p.state = "Qld";
					break;
				case '5':
					p.state = "SA";
					break;
				case '6':
					p.state = "WA";
					break;
				case '7':
					p.state = "Tas";
				}
			}
		}
		
		Postcode overseas = new Postcode();
		overseas.code = "International";
		overseas.pop = 2000000;
		String line;
		BufferedReader br = new BufferedReader(new FileReader("Resources\\Countries.txt"));
		while ((line = br.readLine()) != null) {
			overseas.locs.add(line);
		}
		br.close();
		allCodes.add(overseas);
		

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("Resources\\jsonout\\Postcodes.json"),
				allCodes);
	}
}
