package net.mynym.lesampledata.processing;

import net.mynym.lesampledata.entities.Association;

public class TestAssociation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 100; i++) {
			Association a = new Association();
			System.out.println(a.name);
		}
	}

}
