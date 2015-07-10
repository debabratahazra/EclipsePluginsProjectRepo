/**********************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ReadElf
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra and Santanu Guchhait
 * Description: Read complete ELF file format.
 **********************************************/

package read.elf;

import java.io.FileNotFoundException;
import java.io.IOException;

import elf.disassembly.DsmGeneration;
import elf.disassembly.ElfParser;
import elf.disassembly.MxmGeneration;

import read.dwarf.ReadDebugAbbrev;
import read.dwarf.ReadDebugAranges;
import read.dwarf.ReadDebugFrame;
import read.dwarf.ReadDebugInfo;
import read.dwarf.ReadDebugLine;
import read.dwarf.ReadDebugLoc;
import read.dwarf.ReadDebugPubnames;
import read.dwarf.ReadDebugStr;


public class ReadElf {

	/******************
	 * @param args
	 *****************/
	public static void main(String[] args) {

		//"filename" store the Source Elf file name with absolute path location from the argument.		 
		String filename = new String();
		for(int i=0; i<args.length; i++){
			filename += args[i] + " ";
		}
		filename = filename.trim();				

		/**
		 * @author Debabrata Hazra
		 */
		new ReadHeader().readElfFileHeader(filename);

		try {
			//Read Section Header from Elf file and store the information in an array
			new ReadSection(filename).readSectionHeaders();				
			//Read .debug_aranges Section from Elf file and store it in an array.
			new ReadDebugAranges().readDebugArangesSection(filename);
			//Read .debug_pubnames Section from Elf file and store it in an array.
			new ReadDebugPubnames().readDebugPubnamesSection(filename);
			//Read .debug_loc Section from Elf file and store it in an array.
			new ReadDebugLoc().readDebugLocSection(filename);			
			//Read .debug_frame Section from Elf file and store it in an array.
			new ReadDebugFrame().readDebugFrameSection(filename);
			//Read .debug_str Section from Elf file and store it in an array.
			new ReadDebugStr().readDebugStrSection(filename);
			//Read .debug_line Section from Elf file and store it in an array.
			new ReadDebugLine().readDebugLineSection(filename);
			//Read .debug_abbrev Section from Elf file and store it in an array.
			new ReadDebugAbbrev().readDebugAbbrevSection(filename);
			//Read .debug_info Section from Elf file and store it in an array.
			new ReadDebugInfo().readDebugInfoSection(filename);

		} catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}



		/**
		 * @author Santanu
		 */
		new ReadHeader().readElfFileHeader(filename);
		try {
			new ReadSection(filename).readSectionHeaders();
		} catch (FileNotFoundException e) {} 
		catch (Exception e) {}

		try {
			ElfParser r = new ElfParser(filename);
			if( r!= null)
				r.elfReading();
		} catch (IOException e) {			 
			e.printStackTrace();
		}

		try {
			DsmGeneration dsm = new DsmGeneration(filename);
			if( dsm!= null){
				dsm.executableReading(filename);
			}
		} catch (IOException e) {			 
			e.printStackTrace();
		}

		try {
			MxmGeneration mxm = new MxmGeneration(filename);
			if( mxm!= null)
				mxm.mxm_Generation(filename);
		} catch (IOException e) {			 
			e.printStackTrace();
		}
	}

}
