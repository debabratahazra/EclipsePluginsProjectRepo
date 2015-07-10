/****************************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DebugArangesStructure
 * @version: 1.0
 * Date: July 2009 
 * @author Debabrata Hazra
 * Description: Handle the data structure for Structure and Union in .debug_info Section
 ****************************************************************************************/

package dwarf.internal;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dwarf.section.DebugInfoStructure;


public class StructUnionHandler {

	public static String sourceFilename;		//Source File name
	public static String[] structName;			//Structure or Union Variable type name
	public static String[] objName;				//Structure or Union Variable* name
	public static String[] memberName;			//All Data member name(s)
	public static int[] locationDetails;		//Member of the Structure or Union Location detail values
	public static int[] location;				//Variable*  location value
	public static int[] size;					//All Data member size

	public static BufferedWriter struct_writer = null;

	//Default Constructor
	public StructUnionHandler() {		
	}

	//Static Block, Initialized all static members.
	static{
		try {
			struct_writer = new BufferedWriter(new FileWriter(new File(".struct_union.txt")));
		} catch (IOException e) {			
		}
		structName = new String[1];
		objName = new String[1];
		memberName = new String[1];
		locationDetails = new int [1];
		location = new int[1];
		size = new int[1];
	}

	/**
	 * Create the Data Structure for Structure or Union in
	 * .struct_union.txt file.
	 * @param structUnionType
	 * @throws IOException
	 */
	public static void setStructUnionInit(boolean structUnionType) throws IOException{

		if(!structUnionType) return;
		else{
			DebugInfoStructure.structUnionType=false;			
		}		
		struct_writer.write(sourceFilename + " $ " + structName[0] + " $ " + DebugInfoStructure.frame_base + " $ ");
		for (int i = 0; i < memberName.length; i++) {
			byte location = 0;
			if(locationDetails.length>1){
				location =(byte)((byte)StructUnionHandler.location[0] - (byte)locationDetails[i] );
			}else{
				location =(byte)((byte)StructUnionHandler.location[0] - (byte)locationDetails[0] );
			}

			struct_writer.write(objName[0] + "." + memberName[i] + " $ ");			
			struct_writer.write(String.valueOf(location));
			struct_writer.write(" $ " + size[i] + " $ ");
		}		
		struct_writer.newLine();
		init();						//Initialized all the data members.
	}


	/**
	 * Initialized all the data member of 
	 * StructUnionHandler and index_number of DebugInfoStructure class
	 */
	private static void init() {		

		sourceFilename = new String();
		structName = new String[1];
		objName = new String[1];
		memberName = new String[1];
		locationDetails = new int [1];
		location = new int[1];
		size = new int[1];

		DebugInfoStructure.struct_index = 0;
		DebugInfoStructure.sObject_index = 0;
		DebugInfoStructure.sMember_index = 0;		
	}

}
