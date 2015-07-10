package com.cdt.keil.debug.ui.editor.info;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;

import com.cdt.keil.debug.ui.internal.DynamicSourceCodeFile;
import com.cdt.keil.debug.ui.internal.LineInfoContentDescriptor;
import com.cdt.keil.debug.ui.internal.MainHighlighter;


public class HighlightHighlevelMode {
				
	IWorkbenchPage page=null;
	IWorkspace iWorkspace=null;
	IWorkspaceRoot iWorkspaceRoot=null;
	String filePath=null;
	IFile iFile=null;
	IEditorPart editorPart=null;
	TextEditor textEditor=null;
	static TextEditor oldTextEditor=null;
	IEditorInput iEditorInput=null;
	IDocument iDocument=null;
	IRegion iRegion=null;
	
	public static String currentFile=null;
	public static String currentFilePath=null;
	public static int lastLine = 0;	
	public static String currentHighlightText=null;
	
	public HighlightHighlevelMode() {				
		page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		iWorkspace = ResourcesPlugin.getWorkspace();		
		iWorkspaceRoot = iWorkspace.getRoot();		
	}
	
	
	public void highlightLine(String startAddress){
		//Highlight HL Code.
		
		// Remove selected text at previous selection.
		if(oldTextEditor!=null)
		{
			oldTextEditor.resetHighlightRange();
			oldTextEditor.selectAndReveal(0, 0);
		}
		
		//Highlight main();
		if(startAddress!=null && startAddress.toLowerCase().equalsIgnoreCase("main")){
			filePath = new MainHighlighter().mainFilepath(0);	//0-> Relative file path of main() filename.
			 
			iFile = (IFile) iWorkspaceRoot.findMember(filePath);		
			
			try {			
				editorPart = IDE.openEditor(page, iFile, 
						"org.eclipse.ui.DefaultTextEditor", true);			             
	             textEditor = (TextEditor)editorPart;
	             iEditorInput = textEditor.getEditorInput();            
	             iDocument = textEditor.getDocumentProvider().getDocument(iEditorInput);                         
			}catch (Exception e1) {}
						
			int line = new MainHighlighter().mainLineNumber(); line--;
			
			try {			
				iRegion = iDocument.getLineInformation(line);		             
	         
			//highlight the region
	         textEditor.setHighlightRange(iRegion.getOffset(), iRegion.getLength(), false);             
	         textEditor.selectAndReveal(iRegion.getOffset(), iRegion.getLength());
	         oldTextEditor = textEditor;
	         currentHighlightText = iDocument.get(iRegion.getOffset(), iRegion.getLength());
	         statusUpdate(filePath,line);
	         
			} catch (BadLocationException e) {}
			catch(Exception e){}		
		}
		
		//Highlight other HL Code.
		else{
			//Return Relative file path w.r.t. sAddress.
			filePath = new DynamicSourceCodeFile().getSourceFilePath(startAddress,0);			
			iFile = (IFile) iWorkspaceRoot.findMember(filePath);				
			try {			
				editorPart = //page.getActiveEditor(); 
					IDE.openEditor(page, iFile,		"org.eclipse.ui.DefaultTextEditor", true);			             
	             textEditor = (TextEditor)editorPart;
	             iEditorInput = textEditor.getEditorInput();            
	             iDocument = textEditor.getDocumentProvider().getDocument(iEditorInput);                         
			}catch (Exception e1) {}						
			
 			int line = new LineInfoContentDescriptor().getLineNumber(startAddress);
			if(line==-1) return;	
			line--;
			
			try {			
				iRegion = iDocument.getLineInformation(line);		             
	         
			//highlight the region
	         textEditor.setHighlightRange(iRegion.getOffset(), iRegion.getLength(), false);             
	         textEditor.selectAndReveal(iRegion.getOffset(), iRegion.getLength());	         
	         oldTextEditor = textEditor;
	         
	         currentHighlightText = iDocument.get(iRegion.getOffset(), iRegion.getLength());
	         statusUpdate(filePath,line);
	         
			} catch (BadLocationException e) {}
			catch(Exception e){}
			
		}
	}
	
	private void statusUpdate(String filepath, int line){
		currentFilePath = filepath;
		int index = filepath.lastIndexOf("\\");
		currentFile = filepath.substring(index+1, filepath.length());
		lastLine = line+1;	
	}

}
