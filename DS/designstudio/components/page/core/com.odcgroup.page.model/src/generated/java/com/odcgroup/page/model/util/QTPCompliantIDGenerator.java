package com.odcgroup.page.model.util;

/**
 * A QTP compliant ID (5 character length) Generator
 * 
 */
public class QTPCompliantIDGenerator {	
	

	/**
	 * generates  5 random character ID which is compliant with QTP
	 * 
	 * @return id
	 */
	public static String generateID() {
		char[] id = new char[5];
		id[0] = fetchFirstCharacter();
		int specialChar = '_';
		int chr = 'A';
		int ref = 0;
		for (int i = 1; i < 5; i++) {
			ref = (int) (Math.random() * 6);
			switch (ref) {
			case 0:
				chr = '0' + (int) (Math.random() * 10);
				break;
			case 1:
				chr = 'a' + (int) (Math.random() * 26);
				break;
			case 2:
				chr = specialChar;
				break;
			case 4:
				chr = '0' + (int) (Math.random() * 10);
				break;
			case 3:
				chr = specialChar;
				break;
			case 5:
				chr = 'A' + (int) (Math.random() * 26);
				break;
			}
			id[i] = (char) chr;
		}
		return new String(id);
	}	
	
	/**
	 * the first character has to a alphabet (lower/upper)
	 * 
	 * @return char
	 */
	private static  char fetchFirstCharacter() {
		int chr = 'A';
		int ref = (int) (Math.random() * 2);
		switch (ref) {
		case 0:
			chr = 'a' + (int) (Math.random() * 26);
			break;
		case 1:
			chr = 'A' + (int) (Math.random() * 26);
			break;			
		}
		return (char) chr;
	}

}
