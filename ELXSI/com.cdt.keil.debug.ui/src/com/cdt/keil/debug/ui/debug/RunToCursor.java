package com.cdt.keil.debug.ui.debug;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.editor.info.EditorAddressInfo;
import com.cdt.keil.debug.ui.editor.info.EditorLineInfo;
import com.cdt.keil.debug.ui.editor.info.HighlightAssemblyMode;
import com.cdt.keil.debug.ui.editor.info.HighlightHighlevelMode;
import com.cdt.keil.debug.ui.editor.info.HighlightMixedMode;
import com.cdt.keil.debug.ui.editor.info.SourcefileAddressInfo;
import com.cdt.keil.debug.ui.internal.UpdatePC;
import com.cdt.keil.debug.ui.launch.run.LibDsmFileLocation;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.serialPortComm.IdebugInterface;

public class RunToCursor implements IWorkbenchWindowActionDelegate {
	
	private int currentCursorLineLL;
	private int currentCursorLineHL;
	EditorLineInfo info=null;
	LibDsmFileLocation dsm = null;
	
	IWorkbenchPage page=null;
	IWorkspace iWorkspace=null;
	IWorkspaceRoot iWorkspaceRoot=null;
	String filePath=null;
	IFile iFile=null;
	IEditorPart iEditorPart=null;
	TextEditor textEditor=null;
	IEditorInput iEditorInput=null;
	IDocument iDocument=null;
	IRegion iRegion=null;
	
	HighlightAssemblyMode highlight;	
	
	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow window) {		
		highlight=new HighlightAssemblyMode();
	}

	@Override
	public void run(IAction action) {
		
		switch(DebugAction.debugMode){
		
		case 1:
			//Assembly Mode
			assemblyRunToCursor();
			break;
			
		case 2:
			//High level Mode
			highlevelRunToCursor();
			break;
			
		case 3:
			//Mixed Mode
			mixedlevelRunToCursor();
			break;
			
		default:
			new DownloadImageAction();
			ConsoleDisplayMgr.getDefault().clear();
			ConsoleDisplayMgr.getDefault().println("PLEASE SELECT THE MODE OF DEBUGGING OPTION", 2);
			break;		
		}		
	}

	private void mixedlevelRunToCursor() {
		
		page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		try {
			iEditorPart = page.getActiveEditor(); 
				
			 textEditor = (TextEditor)iEditorPart;
             TextEditor editor = (TextEditor)iEditorPart;
             iEditorInput = textEditor.getEditorInput();             
             iDocument = textEditor.getDocumentProvider().getDocument(iEditorInput);   
             TextSelection sel=(TextSelection) editor.getSelectionProvider().getSelection();
             try {
				currentCursorLineLL= iDocument.getLineOfOffset(sel.getOffset());
			} catch (BadLocationException e) {}
			currentCursorLineLL++;
		} catch (Exception e) {}
				
		short[] Packet=new EditorAddressInfo().packetAddressInfoML(currentCursorLineLL, textEditor.getPartName());
		if(Packet!=null){
			IdebugInterface.addBreakpoint(Packet,false);
		}		
		else{
			//Wrong line selected.
			ConsoleDisplayMgr.getDefault().println("ERROR! HEX ADDRESS NOT FOUND.", 2);
			return;
		}
		int[] value=IdebugInterface.runInstruction2();
		if(value==null) return;
		
		if(value[1]==148){
			ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
		}else{
			ConsoleDisplayMgr.getDefault().println("RUN TO CURSOR EXECUTED.", 1);
		}			
		
		RegisterView.updateRegister(false, true);				
		new UpdatePC();
	    
	    new HighlightMixedMode().highlightLine(RegisterView.regAddressValue[0].getText(1).substring(2, 6));	
	    
		IdebugInterface.removeBreakpoint(new short[]{100}, false);
		
		
		//For Step Return Action
		if(RegisterView.spChanged){
			StepReturnAction.spRecentValue=RegisterView.regAddressValue[3].getText(1);
			String str =StepReturnAction.spRecentValue;
			int sAddress = Short.parseShort(str.substring(2, str.length()), 16) - 1;
			short[] Packet1 = new short[3];
			Packet1[0]=0;
			Packet1[1]= (short) sAddress;
			Packet1[2] = 0x02;
			StepReturnAction.stackValue = IdebugInterface.readScratchpadRAM(Packet1);			
		}	
		
		
	}

	private void highlevelRunToCursor() {
		
		page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			iEditorPart = page.getActiveEditor(); 
				
			 textEditor = (TextEditor)iEditorPart;
             TextEditor editor = (TextEditor)iEditorPart;
             iEditorInput = textEditor.getEditorInput();             
             iDocument = textEditor.getDocumentProvider().getDocument(iEditorInput);   
             TextSelection sel=(TextSelection) editor.getSelectionProvider().getSelection();
             try {
				currentCursorLineHL= iDocument.getLineOfOffset(sel.getOffset());
			} catch (BadLocationException e) {}
			currentCursorLineHL++;
		} catch (Exception e) {}
		
		StepIntoAction.firstStepInto=false;
		
		//Return Packet value corresponding to current cursor position for adding breakpoint.
		short[] Packet=new SourcefileAddressInfo().packetAddressInfo(currentCursorLineHL, 
				textEditor.getPartName());
		if(Packet==null) return;
		int[] val = IdebugInterface.addVirtualBreakpoint(Packet);		if(val==null) return;
		val=IdebugInterface.runInstruction2();							if(val==null) return;
		val = IdebugInterface.removeVirtualBreakpoint((short) 100);		if(val==null) return;
				
		RegisterView.updateRegister(false, true);				
		new UpdatePC();
		
		//Highlight next instruction (HL Code).
		new HighlightHighlevelMode().highlightLine(
				RegisterView.regAddressValue[0].getText(1));			
	}
	
	

	private void assemblyRunToCursor() {
		
		if(EditorLineInfo.text.contains("$ END") && EditorLineInfo.text.contains("$ End")){
			return;		//Program End.
		}	
		page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						
		try {
			iEditorPart = page.getActiveEditor(); 
				
			 textEditor = (TextEditor)iEditorPart;
             TextEditor editor = (TextEditor)iEditorPart;
             iEditorInput = textEditor.getEditorInput();             
             iDocument = textEditor.getDocumentProvider().getDocument(iEditorInput);   
             TextSelection sel=(TextSelection) editor.getSelectionProvider().getSelection();
             try {
				currentCursorLineLL= iDocument.getLineOfOffset(sel.getOffset());
			} catch (BadLocationException e) {}
			currentCursorLineLL++;
		} catch (Exception e) {}
				
		short[] Packet=new EditorAddressInfo().packetAddressInfoAL(currentCursorLineLL, textEditor.getPartName());
		if(Packet==null){
			ConsoleDisplayMgr.getDefault().println("ERROR! HEX ADDRESS NOT FOUND.", 2);
			return;			
		}
		if(Packet!=null){
			IdebugInterface.addBreakpoint(Packet,false);
		}		
		int[] value=IdebugInterface.runInstruction2();
		if(value==null) return;
		
		if(value[1]==148){
			ConsoleDisplayMgr.getDefault().println("TARGET BOARD IS RUNNING...", 3);
		}else{
			ConsoleDisplayMgr.getDefault().println("RUN TO CURSOR EXECUTED.", 1);
		}			
		
		RegisterView.updateRegister(false, true);				
		new UpdatePC();
	    
	    highlight.highlightLine(RegisterView.regAddressValue[0].getText(1).substring(2, 6));	
	    
		IdebugInterface.removeBreakpoint(new short[]{100}, false);
		
		//For Step Return Action
		if(RegisterView.spChanged){
			StepReturnAction.spRecentValue=RegisterView.regAddressValue[3].getText(1);
			String str =StepReturnAction.spRecentValue;
			int sAddress = Short.parseShort(str.substring(2, str.length()), 16) - 1;
			short[] Packet2 = new short[3];
			Packet2[0]=0;
			Packet2[1]= (short) sAddress;
			Packet2[2] = 0x02;
			StepReturnAction.stackValue = IdebugInterface.readScratchpadRAM(Packet2);			
		}		
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

}
