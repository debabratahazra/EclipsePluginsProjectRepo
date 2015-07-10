package com.cdt.keil.debug.ui.internal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class HighLevelStructureDefine {
		 
	public static HighLevelStructure hlStructure ;
	
		
	public HighLevelStructureDefine() {
		totalLineNumber();
		readLineInfo();
	}
	
	
	private String getLineInfoPath(){
		return (new LineInfoLocation(true).lineInfoLocation());
	}
	
	private void totalLineNumber(){
		//Internal Function
		try{
			FileReader fReader = new FileReader(getLineInfoPath());
			BufferedReader bReader = new BufferedReader(fReader);
			int lineNo=0;
			while(bReader.readLine()!=null){
				lineNo++;
			}
			fReader.close(); bReader.close();			
			hlStructure = new HighLevelStructure(lineNo);
			HighLevelStructure.totalLineCount = lineNo;
		}catch(Exception e){}
		
	}
	
	private void readLineInfo(){
		//Internal Function
		try{
			FileReader fReader = new FileReader(getLineInfoPath());
			BufferedReader bReader = new BufferedReader(fReader);
			String str=new String();
			int objDim=0;
			while((str=bReader.readLine())!=null){
				int level=1;
				StringTokenizer st = new StringTokenizer(str, "$");
				while(st.hasMoreTokens()){
					String strTok = st.nextToken().trim();	
					setHLStructure(strTok,level,objDim);
					level++;
				}
				objDim++;
			}
			fReader.close(); bReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
		
	
	private void setHLStructure(String strValue, int fColumn, int fRow){		
		
		switch(fColumn){	
		
		case 1:
			//Source file name.
			hlStructure.setFilename(strValue,fRow);
			break;
		case 2:
			//line number of HL Code.
			hlStructure.setLineNumber(Integer.parseInt(strValue,10),fRow);
			break;
		case 3:
			//HL Code.
			hlStructure.setHighLevelCode(strValue,fRow);
			break;
		case 4:
			//Starting Address of ASM corresponding to HL Code. 
			hlStructure.setSAddress(strValue, fRow);
			break;
		case 5: 
			//Ending Address of ASM corresponding to HL Code.
			hlStructure.setLAddress(strValue, fRow);
			break;
		case 6:
			//Breakpoint ID
			hlStructure.setBreakpointID(Integer.parseInt(strValue, 10), fRow);
			break;
		default:
			break;
			
		}
			 
	}
	
}
