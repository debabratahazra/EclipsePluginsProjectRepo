package design_pattern.mainclass;

import java.util.ArrayList;

import design_pattern.internal.PrintableList;
import design_pattern.structural.Adapter;

public class MainAdapter {

	public static void main(String[] args) {
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		PrintableList pl = new Adapter();
		pl.printList(list);

	}

}
