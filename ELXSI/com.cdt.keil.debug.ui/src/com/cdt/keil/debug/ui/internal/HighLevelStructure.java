package com.cdt.keil.debug.ui.internal;

public class HighLevelStructure {
	
	private static String[] filename;
	private static int[] lineNumber;
	private static String[] highLevelCode;
	private static String [] sAddress;
	private static String[] lAddress;
	private static int[] breakpointID;
	
	
	public static int totalLineCount=0;
	
	public HighLevelStructure(int size) {
		filename = new String[size];
		lineNumber = new int[size];
		highLevelCode = new String[size];
		sAddress = new String[size];
		lAddress= new String[size];
		breakpointID = new int[size];
	}
	
	

	public HighLevelStructure() {		
	}

	public  String getFilename(int index) {
		return filename[index];
	}

	public  void setFilename(String filename,int index) {
		HighLevelStructure.filename[index] = filename;
	}

	public  int getLineNumber(int index) {
		return lineNumber[index];
	}

	public  void setLineNumber(int lineNumber,int index) {
		HighLevelStructure.lineNumber[index] = lineNumber;
	}

	public  String getHighLevelCode(int index) {
		return highLevelCode[index];
	}

	public  void setHighLevelCode(String highLevelCode,int index) {
		HighLevelStructure.highLevelCode[index] = highLevelCode;
	}

	public  String getSAddress(int index) {
		return sAddress[index];
	}

	public  void setSAddress(String address,int index) {
		HighLevelStructure.sAddress[index] = address;
	}
	
	public String getLAddress(int index) {
		return lAddress[index];
	}

	public void setLAddress(String address, int index) {
		HighLevelStructure.lAddress[index] = address;
	}

	public  int getBreakpointID(int index) {
		return breakpointID[index];
	}

	public void setBreakpointID(int breakpointID, int index) {
		HighLevelStructure.breakpointID[index] = breakpointID;
	}
}
