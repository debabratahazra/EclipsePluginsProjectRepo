package com.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class CollectionExample {
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		list.add("Debu");
		list.add("Soma");
		list.add("Deb");
		
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println("First Iterator : " + string);
			iterator.remove();	// Remove first element
			break;
		}
		
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println("Second Iterator: " + string);	// Display only one element "Soma"
		}
		
		ListIterator<String> listIter = list.listIterator();
		if(listIter.hasNext()) {
			System.out.println("ListIterator Next: " + listIter.next());
		}
		if(listIter.hasPrevious()){
			System.out.println("ListIterator Previous: " + listIter.previous());
		}
		
		if(listIter.hasNext()) {
			System.out.println("ListIterator Next_2: " + listIter.next());
		}
		if(listIter.hasNext()) {
			System.out.println("ListIterator Next_3: " + listIter.next());
		}
	}

}
