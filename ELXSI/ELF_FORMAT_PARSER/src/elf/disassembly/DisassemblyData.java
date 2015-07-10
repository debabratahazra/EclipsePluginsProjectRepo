/*******************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DisassemblyData
 * @version: 1.0
 * Date: June 2009 
 * @author Santanu Guchhait
 * Description: Make the data structure of high level code 
 * start address and line no.
 *******************************************************************************/


package elf.disassembly;

import java.lang.reflect.Array;
import dwarf.internal.ArrayExpend;


public class DisassemblyData {

	DisassemblyData[] disassemblyObject = new DisassemblyData[1];
	int StartAddress;
	int StartLine;
	int index = 0;

	
	/********************************************************************	 
	 * Parameter      - Startaddress and Startline 
	 * Return Type    - No return
	 * Description    - Makes the data structure by taking values of 
	 *                  starting address and starting line of high level code.
	 *********************************************************************/	
	public void setDisassemblyData(int Saddress, int Sline){
		disassemblyObject[index] = new DisassemblyData();
		disassemblyObject[index].StartAddress = Saddress;
		disassemblyObject[index].StartLine    = Sline;
		disassemblyObject = (DisassemblyData[])ArrayExpend.expend(disassemblyObject,Array.getLength(disassemblyObject)+1);
		index++;
	}

}
