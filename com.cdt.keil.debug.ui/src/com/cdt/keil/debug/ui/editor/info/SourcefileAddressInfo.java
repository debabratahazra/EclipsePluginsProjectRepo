package com.cdt.keil.debug.ui.editor.info;

import com.cdt.keil.debug.ui.internal.HighLevelStructure;

public class SourcefileAddressInfo {
	
	public static int lineNumber=0;
	private static int index=0;

	public short[] packetAddressInfo(int currentCursorLineHL, String fileName) {
		/** 
		 *  @param currentCursorLineHL -> line number of current cursor position.
		 *  @param fileName -> source file name.
		 *  @return Packet value for adding breakpoint corresponding to current or nearest cursor position.
		 */
		
		String sAddress = getStartAddress(currentCursorLineHL,fileName);
		if(sAddress==null) return null;
		
		short[] Packet = packetValueCur(sAddress);		
		return Packet;
	}

	private short[] packetValueCur(String sAddress) {
		//Return short[]  Packet for adding Breakpoint.
		
		short[] Packet = new short[3];
		Packet[0]= (short)100;				
		String str = sAddress.substring(2, 4);
		Packet[1] = Short.parseShort(str, 16);
		str = sAddress.substring(4, sAddress.length());
		Packet[2] = Short.parseShort(str, 16);
		Packet[2]++;
		if(Packet[2]>255){
			Packet[2]=0;	Packet[1]++;
		}
		return Packet;
	}

	private String getStartAddress(int currentCursorLineHL, String fileName) {
		//Return corresponding starting address of current cursor position.
				
		HighLevelStructure hls = new HighLevelStructure();
		for(int i=0;i<HighLevelStructure.totalLineCount-1;i++){
			if(hls.getFilename(i).trim().equalsIgnoreCase(fileName.trim())){
				if(hls.getLineNumber(i)<=currentCursorLineHL){
					//currentCursorLineHL must be <= line number within same source file name.
					if(hls.getFilename(i+1).trim().equalsIgnoreCase(fileName.trim())){
						//Next line within same source file.
						if(hls.getLineNumber(i+1)> currentCursorLineHL){
							//Check if the next line number is > than currentCursorLineHL.
							
							return (hls.getSAddress(i)); 
						 }	
					}else{
						//Next line isn't within same source file.
						
						return (hls.getSAddress(i));
					}					 				
				}
			}
		}		
		
		if(hls.getFilename(HighLevelStructure.totalLineCount-1).trim().equalsIgnoreCase(fileName.trim())
				&&( hls.getLineNumber(HighLevelStructure.totalLineCount-1)==currentCursorLineHL 
						||  hls.getLineNumber(HighLevelStructure.totalLineCount-1)==currentCursorLineHL-1)){
					
			return (hls.getSAddress(HighLevelStructure.totalLineCount-1));
		}
		return null;
	}
	
	
	public short[] packetBreakpointInfo(int currentCursorLineHL, String fileName) {
		/** 
		 *  @param currentCursorLineHL -> line number of current cursor position.
		 *  @param fileName -> source file name.
		 *  @return Packet value for adding breakpoint corresponding to current or nearest cursor position.
		 */
		
		String sAddress = getNextStartAddress(currentCursorLineHL,fileName);
		if(sAddress==null) return null;
		
		short[] Packet = packetValue(sAddress);		
		return Packet;
	}
	
	private String getNextStartAddress(int addingBrkpntLine, String fileName) {
		/**
		 * @return corresponding starting address from current cursor position or next executable
		 * HL Code starting address. 
		 */
						
		HighLevelStructure hls = new HighLevelStructure();
		for(int i=0;i<HighLevelStructure.totalLineCount-1;i++){
			
			if(hls.getFilename(i).trim().equalsIgnoreCase(fileName.trim())){
				//Both are same source file.
				
				if(hls.getLineNumber(i)==addingBrkpntLine){
					//Adding breakpoint and executable code line is same.
					
					lineNumber = hls.getLineNumber(i);
					index=i;
					return (hls.getSAddress(i));
				}
				else if(hls.getLineNumber(i) > addingBrkpntLine){
					//Adding breakpoint number is less than the record set.
					lineNumber = hls.getLineNumber(i);
					index=i+1;
					return (hls.getSAddress(i));
				}
				else if(hls.getLineNumber(i)< addingBrkpntLine && hls.getLineNumber(i+1)> addingBrkpntLine){
					//In between given breakpoint.
					
					if(hls.getFilename(i+1).trim().equalsIgnoreCase(fileName.trim())){
						//Next record is same in the source file.
						
						lineNumber = hls.getLineNumber(i+1);
						index=i+1;
						return (hls.getSAddress(i+1));						
					}else{
						//Next record isn't same source file.
						
						lineNumber = hls.getLineNumber(1);
						index=i;
						return (hls.getSAddress(i));
					}
					
				}								
			}
		}		
		
		if(hls.getFilename(HighLevelStructure.totalLineCount-1).trim().equalsIgnoreCase(fileName.trim())){
			int index=HighLevelStructure.totalLineCount-1;
			lineNumber = hls.getLineNumber(index);
			SourcefileAddressInfo.index= index;
			return (hls.getSAddress(index));
		}
		return null;
	}

	private short[] packetValue(String sAddress) {
		//Return short[]  Packet for adding Breakpoint.
		
		short[] Packet = new short[3];
		Packet[0]= (short)new HighLevelStructure().getBreakpointID(index);
		String str = sAddress.substring(2, 4);
		Packet[1] = Short.parseShort(str, 16);
		str = sAddress.substring(4, sAddress.length());
		Packet[2] = Short.parseShort(str, 16);
		Packet[2]++;
		if(Packet[2]>255){
			Packet[2]=0;	Packet[1]++;
		}
		return Packet;
	}
	
}
