package com.example;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample{

    public static void main(String args[]) {
      
        List<String> threadSafeList = new CopyOnWriteArrayList<String>();
        threadSafeList.add("Java");
        threadSafeList.add("J2EE");
        threadSafeList.add("Collection");
      
        //add, remove operator is not supported by CopyOnWriteArrayList iterator
        Iterator<String> failSafeIterator = threadSafeList.iterator();
        while(failSafeIterator.hasNext()){
            System.out.printf("Read from CopyOnWriteArrayList : %s %n", failSafeIterator.next());
            threadSafeList.add("Collection_X");	// no concurrent modification error here
            //failSafeIterator.remove(); //remove() operation not supported in CopyOnWriteArrayList class. Runtime exception
        }
        
        failSafeIterator = threadSafeList.iterator();
        while(failSafeIterator.hasNext()){
            System.out.printf("Read from CopyOnWriteArrayList : %s %n", failSafeIterator.next());
            //failSafeIterator.remove(); //remove() operation not supported in CopyOnWriteArrayList class. Runtime exception
        }
    }
}