package com.cdt.keil.debug.ui.internal;


public class LineInfoContentDescriptor {
	
	public int getLineNumber(String startAddress) {
		//Return Highlight Line Number of the source file for HL Debug.
		
		HighLevelStructure hls = new HighLevelStructure();
		
		for(int i=0;i<HighLevelStructure.totalLineCount;i++){
			if(hls.getSAddress(i).trim().toLowerCase().equalsIgnoreCase
					(startAddress.trim().toLowerCase())){
				return (hls.getLineNumber(i));
			}
		}		
		return -1;
	}
	
	
	
	public String getNextSAddress(String currentFile, int lastLine) {
		//Return next starting address in same source file.
		
		HighLevelStructure hls = new HighLevelStructure();
		
		for(int i=0;i<HighLevelStructure.totalLineCount;i++){
			if(hls.getFilename(i).equalsIgnoreCase(currentFile)){
				int fLine = hls.getLineNumber(i); 
				if(fLine>lastLine) {					 
					return (new HighLevelStructure().getSAddress(i));
				}
			}
		}		
		return null;
	}
	
}
