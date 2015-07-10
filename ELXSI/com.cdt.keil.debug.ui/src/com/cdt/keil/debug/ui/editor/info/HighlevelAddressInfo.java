package com.cdt.keil.debug.ui.editor.info;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

import com.cdt.keil.debug.ui.internal.HighLevelStructure;

public class HighlevelAddressInfo {
	
	String fileName= null;

	public HighlevelAddressInfo() {
		
		try {
			IEditorPart iEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
										getActivePage().getActiveEditor();
			TextEditor textEditor = (TextEditor)iEditorPart;
			fileName = textEditor.getPartName();
			
		} catch (Exception e) {}
	}
	
	
	public short[] addressInfo(int lineNumber) {
		/**
		 * @param current line number for requesting adding breakpoint.
		 * @return Packet value for adding breakpoint at the nearest executable HL Code.
		 */
		
		short[] Packet = new SourcefileAddressInfo().packetBreakpointInfo(lineNumber, fileName);		
		return Packet;
	}
	
	public static int lineInfo(){
		/**
		 * @return line number corresponding to executable HL Code during adding breakpoint.
		 */
		
		return SourcefileAddressInfo.lineNumber;
	}


	public short[] idInfo(int line) {
		/**
		 * @param line number during remove breakpoint
		 * @return Packet for removing breakpoint at the given line number.
		 */
		
		HighLevelStructure hls = new HighLevelStructure();
		for(int i=0;i<HighLevelStructure.totalLineCount;i++){
			if(hls.getFilename(i).trim().equalsIgnoreCase(fileName.trim())){
				if(hls.getLineNumber(i)==line){
					short[] pack = {(short)hls.getBreakpointID(i)};
					return (pack);
				}
			}
		}
		
		return null;
	}
	
	

}
