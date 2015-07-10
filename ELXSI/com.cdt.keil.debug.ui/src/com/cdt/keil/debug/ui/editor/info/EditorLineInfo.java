package com.cdt.keil.debug.ui.editor.info;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.cdt.keil.debug.ui.internal.DynamicFileLocationAL;
import com.cdt.keil.debug.ui.launch.run.LibDsmFileLocation;

public class EditorLineInfo {
	
	public static String text;
	public static int lineNo;	
	
	LibDsmFileLocation dsm = null;
	String filePath = null;
	
	static{
		lineNo=0;
		text="";
	}
	
	
	public EditorLineInfo() {		
		dsm = new LibDsmFileLocation(true);
		filePath = dsm.dsmDetailFileLocation();
	}

	public int lineNumber(String address) {
		
		 address = address.trim();
		  try {
			  BufferedReader reader=null;
			  if(DynamicFileLocationAL.absoluteFilePath.equalsIgnoreCase("")){
				  reader=new BufferedReader(new FileReader(filePath));
			  }else{
				  reader=new BufferedReader(new FileReader(DynamicFileLocationAL.absoluteFilePath));
			  }			  
			
			int index=0;
			 try {
				while ((text = reader.readLine()) != null){
					lineNo++;
					 index=text.indexOf("x");
					 if(index<15 && index!=-1){
						 if(text.substring(index+1, index+5).compareTo(address)==0){	
						 	return lineNo;				 
						 }							
					 }					
				 }
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {}
		catch(Exception e){}
				
		return lineNo-1;
		
	}
	
	
	public int lineNumber(String address, String fileName) {
		
		 address = address.trim();
		  try {
			BufferedReader reader=new BufferedReader(new FileReader(fileName));
			int index=0;
			 try {
				while ((text = reader.readLine()) != null){
					lineNo++;
					 index=text.indexOf("x");
					 if(index<15 && index!=-1){
						 if(text.substring(index+1, index+5).compareTo(address)==0){	
						 	return lineNo;				 
						 }							
					 }					
				 }
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {}
		catch(Exception e){}
				
		return lineNo-1;
		
	}

}

