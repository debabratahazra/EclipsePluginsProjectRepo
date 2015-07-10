package com.cdt.keil.debug.ui.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BreakpointMap {
	
	private static Map<Integer, String> map;
	public static int breakpointID;
	@SuppressWarnings("unchecked")
	public static  Set setKeys; 
	
	static{
		map = new TreeMap<Integer, String>();		
		breakpointID=1;
	}
	
	public BreakpointMap() {}
	
	private static void setKeys(){
		setKeys = map.keySet();
	}
	
	@SuppressWarnings("unchecked")
	public static void putMap(String address){
		breakpointID=1;
		boolean flag=false;
		setKeys();
		for(Iterator it=setKeys.iterator(); it.hasNext();){
			Integer key=(Integer)it.next();			
			if(key!=breakpointID){
				map.put(breakpointID, address);
				flag=true;
				break;
			}			
			breakpointID++;
		}
		if(!flag){
			map.put(breakpointID, address);
		}
		setKeys();
	}
		
	
	@SuppressWarnings("unchecked")
	public static int getMap(String address){
		//return key value		
		for(Iterator it=setKeys.iterator(); it.hasNext();){
			Integer key=(Integer)it.next();
			String value=(String)map.get(key);			
			if(value.equalsIgnoreCase(address)){
				return key;
			}			
		}
		return 100; //Temporary breakpoint number. 
	}
	
	@SuppressWarnings("unchecked")
	public static void removeMap(String address){		
		for(Iterator it=setKeys.iterator(); it.hasNext();){
			Integer key=(Integer)it.next();
			String value=(String)map.get(key);			
			if(value.equalsIgnoreCase(address)){
				map.remove(value);
			}			
		}
		setKeys();
	}
	
	public static void clearMap(){
		map.clear();		
		setKeys();
	}
	
}
