package com.cdt.keil.debug.ui.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class FileInfoName {	
		
		@SuppressWarnings("unchecked")
		public static Set setKeys;
		private static File file;
		private static BufferedReader bReader;
						
		public static Map<Integer, String> map;
		
		static{
			map=new TreeMap<Integer, String>();
		}
		
		public FileInfoName()  {			
			file = new File(new FileInfoLocation(false).fileInfoLocation());
			try {
				bReader = new BufferedReader(new FileReader(file));			
			} catch (FileNotFoundException e) {}
			catch(Exception e){}
			putFileInfo();
			
		}
		
		
		private void putFileInfo() {
			int key=1;
			String lineRead = new String();
			StringTokenizer sToken ;
			try {
				while((lineRead=bReader.readLine())!=null){
					  sToken = new StringTokenizer(lineRead);
					  while (sToken.hasMoreElements()){
						  map.put(key++,(String) sToken.nextElement());
					  }
					  setKeys = map.keySet();			       
				}
				bReader.close();
			} catch (IOException e) {}
			catch(Exception e){}
		}
		
		@SuppressWarnings("unchecked")
		public String getFileInfo(int address){
			//return filename according to the address value.
			
			for(Iterator it=setKeys.iterator(); it.hasNext();){
				Integer key = (Integer) it.next();		
				
				for(int i=3;i<=key;i+=4){
					String sStr = (String) map.get(i);
					String eStr = (String) map.get(i+1);
					int sAddeessVal = Integer.parseInt(sStr, 16);
					int eAddressVal = Integer.parseInt(eStr, 16);
					if(sAddeessVal <= address && eAddressVal>= address ){
						return ((String)map.get(i-2));
					}
				}
			}		
			return null;			
		}
		
		@SuppressWarnings("unchecked")
		public String[] getAllFileName(){
			//Return all .DSM file names
			
			String fileName[]=new String[setKeys.size()/4];
			for(Iterator it=setKeys.iterator(); it.hasNext();){
				Integer key = (Integer) it.next();		
				for(int i=1,j=0;i<=key;i+=4){
					String fStr = (String) map.get(i);
					if(j==0) fileName[j++]=fStr;
					else if(fileName[j-1].equalsIgnoreCase(fStr)){
						continue;
					}
					else{
						fileName[j++]=fStr;
					}
				}
			}			
			return fileName;
		}
		
		public static void clearMap(){
			map.clear();			
			setKeys = map.keySet();
		}
		
}
