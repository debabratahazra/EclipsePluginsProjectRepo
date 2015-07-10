package design_pattern.structural;

import java.util.ArrayList;

import design_pattern.internal.PrintableList;

//Adapter class between Intermediate interface and specific class
public class Adapter implements PrintableList {
	
	@Override
	public void printList(ArrayList<String> list) {

		// Converting ArrayList<String> to String so that we can pass String to
		// adaptee class
		String listString = "";

		for (String s : list) {
			listString += s + "\t";
		}

		// instantiating adaptee/specific class
		PrintString printString = new PrintString();
		printString.print(listString);
	}

}

// Specific Class type
class PrintString {

	public void print(String s) {
		System.out.println(s);
	}
}


