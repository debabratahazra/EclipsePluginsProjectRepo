package com.cdt.keil.debug.ui.editor.info;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

import com.cdt.keil.debug.ui.internal.BreakpointMap;
import com.cdt.keil.debug.ui.internal.DynamicFileLocationAL;
import com.cdt.keil.debug.ui.internal.DynamicFileLocationML;


public class EditorAddressInfo {
		
	String filePath = null;
	
	
	public EditorAddressInfo() {
	
		try {
			IEditorPart iEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
			getActivePage().getActiveEditor();
			TextEditor textEditor = (TextEditor)iEditorPart;
			String fileName = textEditor.getPartName();
			filePath=new DynamicFileLocationAL().dynFileLocInfo(fileName, 1);
		} catch (Exception e) {}
				
	}
	
	public short[] addressInfo(int lineNumber) {		
		  
		int index=0;
		int lineNo=0;
		String text=new String();
		short[] addressValue = new short[3];
		try {			
			BufferedReader reader=new BufferedReader(new FileReader(filePath));			
			 try {
				while ((text = reader.readLine()) != null){
					lineNo++;
					if(lineNo==lineNumber){
						index=text.indexOf("x");
						if(index== -1) return null;
						else{
							BreakpointMap.putMap(text.substring(index+1, index+5));
							addressValue[0]=(short)BreakpointMap.getMap(text.substring(index+1, index+5));
							addressValue[1]=(short)Integer.parseInt(text.substring(index+1, index+3),16);
							addressValue[2]=(short)Integer.parseInt(text.substring(index+3, index+5),16);
							addressValue[2]++;
							if(addressValue[2]>255){
								addressValue[2]=0; addressValue[1]++;
							}
							return addressValue;
						}
					}				
				 }
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {}	
		catch(Exception e){}
		return null;
	}
	
	
	public short[] packetAddressInfoAL(int lineNumber, String fileName) {			  
		//@param - fileName is only filename.
		
		int index=0;
		int lineNo=0;
		String text=new String();
		short[] addressValue = new short[3];
		filePath=new DynamicFileLocationAL().dynFileLocInfo(fileName, 1);
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(filePath));						
			 try {
				while ((text = reader.readLine()) != null){
					lineNo++;
					if(lineNo==lineNumber){
						index=text.indexOf("x");
						if(index== -1) return null;
						else{
							
							addressValue[0]=(short)100;
							addressValue[1]=(short)Integer.parseInt(text.substring(index+1, index+3),16);
							addressValue[2]=(short)Integer.parseInt(text.substring(index+3, index+5),16);
							addressValue[2]++;
							if(addressValue[2]>255){
								addressValue[2]=0; addressValue[1]++;
							}
							return addressValue;
						}
					}				
				 }
			} catch (IOException e) {}
		} catch (FileNotFoundException e1) {}
		catch(Exception e2){}
		return null;
	}
	
	public short[] packetAddressInfoML(int lineNumber, String fileName) {	
		//Used at Run to Cursor [ML mode Debug]		  
		//@param - fileName is only filename.
		
		int index=0;
		int lineNo=0;
		String text=new String();
		short[] addressValue = new short[3];
		filePath=new DynamicFileLocationML().dynFileLocInfo(fileName, 1);
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(filePath));						
			 try {
				while ((text = reader.readLine()) != null){
					lineNo++;
					if(lineNo==lineNumber){
						index=text.indexOf("x");
						if(index== -1) return null;
						else{
							
							addressValue[0]=(short)100;
							addressValue[1]=(short)Integer.parseInt(text.substring(index+1, index+3),16);
							addressValue[2]=(short)Integer.parseInt(text.substring(index+3, index+5),16);
							addressValue[2]++;
							if(addressValue[2]>255){
								addressValue[2]=0; addressValue[1]++;
							}
							return addressValue;
						}
					}				
				 }
			} catch (IOException e) {}
		} catch (FileNotFoundException e1) {}
		catch(Exception e2){}
		return null;
	}
	
	
	
	public String AddressInformation(int lineNumber) {		
		  
		int index=0;
		int lineNo=0;
		String text=new String();	
		try {
			BufferedReader reader=new BufferedReader(new FileReader(filePath));			
			 try {
				while ((text = reader.readLine()) != null){
					lineNo++;
					if(lineNo==lineNumber){
						index=text.indexOf("x");
						if(index== -1) return null;
						else{							
							return text.substring(index+1, index+5);						}
					}				
				 }
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {}
		catch(Exception e){}
		return "";
	}

	
	
	public short[] idInfoAL(int lineNumber) {		
		//Used at disable/remove breakpoint at AL mode of debugging.  
		
		int index=0;
		int lineNo=0;
		String text=new String();
		short[] addressValue = new short[1];
		try {
			BufferedReader reader=new BufferedReader(new FileReader(filePath));			
			 try {
				while ((text = reader.readLine()) != null){
					lineNo++;
					if(lineNo==lineNumber){
						index=text.indexOf("x");
						if(index== -1) return null;
						else{							
							addressValue[0]=(short)BreakpointMap.getMap(text.substring(index+1, index+5));							
							return addressValue;
						}
					}				
				 }
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {}
		catch(Exception e){}
		return null;
	}

	
	public short[] idInfoML(int lineNumber, String fileName) {	
		//Used at remove/disable at remove breakpoint. 
		  
		int index=0;
		int lineNo=0;
		String text=new String();
		short[] addressValue = new short[1];
		filePath=new DynamicFileLocationML().dynFileLocInfo(fileName, 1);
		try {
			BufferedReader reader=new BufferedReader(new FileReader(filePath));			
			 try {
				while ((text = reader.readLine()) != null){
					lineNo++;
					if(lineNo==lineNumber){
						index=text.indexOf("x");
						if(index== -1) return null;
						else{							
							addressValue[0]=(short)BreakpointMap.getMap(text.substring(index+1, index+5));							
							return addressValue;
						}
					}				
				 }
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {}
		catch(Exception e){}
		return null;
	}

	public short[] addressInfoML(int line, String fileName) {
		//Used for adding breakpoint (ML Debug)
		/**
		 * @param line - > editor line number
		 * @param fileName -> only .MXM or LIBRARY.DSM file name.
		 * @return Packet value for adding breakpoint
		 */
		
		int index=0;
		int lineNo=0;
		String text=new String();
		short[] addressValue = new short[3];
		filePath=new DynamicFileLocationML().dynFileLocInfo(fileName, 1);
		try {			
			BufferedReader reader=new BufferedReader(new FileReader(filePath));			
			 try {
				while ((text = reader.readLine()) != null){
					lineNo++;
					if(lineNo==line){
						index=text.indexOf("x");
						if(index== -1) return null;
						else{
							BreakpointMap.putMap(text.substring(index+1, index+5));
							addressValue[0]=(short)BreakpointMap.getMap(text.substring(index+1, index+5));
							addressValue[1]=(short)Integer.parseInt(text.substring(index+1, index+3),16);
							addressValue[2]=(short)Integer.parseInt(text.substring(index+3, index+5),16);
							addressValue[2]++;
							if(addressValue[2]>255){
								addressValue[2]=0; addressValue[1]++;
							}
							return addressValue;
						}
					}				
				 }
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {}	
		catch(Exception e){}
		return null;		
	}

}
