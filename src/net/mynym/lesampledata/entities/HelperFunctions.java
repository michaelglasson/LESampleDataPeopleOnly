package net.mynym.lesampledata.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class HelperFunctions {
	public Random r = new Random();
	
	public String getRandom(List<String> l) {
		return l.get(r.nextInt(l.size()));
	}

	public boolean flipCoin() {
		return r.nextBoolean();
	}
	
	public LocalDate getADate() {
		LocalDate d = LocalDate.of(2000, 8, 31);
		System.out.println(d);
		return d;
	}

	public LocalDate getRandomAgeAtDate(LocalDate baseDate) {
		// Set a random year before baseDate from 4 to 70
		int birthYear = baseDate.getYear() - r.nextInt(66) - 4;
		int birthMonth = r.nextInt(12) + 1;
		int birthDay = r.nextInt(28) + 1;
		return LocalDate.of(birthYear, birthMonth, birthDay);
	}
	
	public LocalDate getRandomDateInLast20Years() {
		LocalDate today = LocalDate.now();
		int year = today.getYear() - r.nextInt(20) - 1;
		int month = r.nextInt(12) + 1;
		int day = r.nextInt(28) + 1;
		return LocalDate.of(year, month, day);
		
	}

}
