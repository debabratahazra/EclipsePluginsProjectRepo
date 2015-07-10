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

import com.cdt.keil.debug.ui.internal.DynamicFileLocationAL;
import com.cdt.keil.debug.ui.launch.run.LibDsmFileLocation;

public class HighlightAssemblyMode {	
	
	EditorLineInfo info=null;		
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
	
	public HighlightAssemblyMode(){
		
		info=new EditorLineInfo();		
		page=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		iWorkspace = ResourcesPlugin.getWorkspace();		
		iWorkspaceRoot = iWorkspace.getRoot();				
				
	}
	
	public void highlightLine(String address){
		
		if(oldTextEditor!=null)
		{
			oldTextEditor.resetHighlightRange();
			oldTextEditor.selectAndReveal(0, 0);
		}
		
		if(address.equalsIgnoreCase("0000")){
			filePath = new LibDsmFileLocation(true).LibDsmFileLocInfo();
			
			iFile = (IFile) iWorkspaceRoot.findMember(filePath);		
			
			try {			
				editorPart = IDE.openEditor(page, iFile, 
						"org.eclipse.ui.DefaultTextEditor", true);			             
	             textEditor = (TextEditor)editorPart;
	             iEditorInput = textEditor.getEditorInput();            
	             iDocument = textEditor.getDocumentProvider().getDocument(iEditorInput);                         
			}catch (Exception e1) {}
			
			EditorLineInfo.lineNo=0;
			String filepath = new LibDsmFileLocation(true).dsmDetailFileLocation();
			int line= info.lineNumber(address,filepath);	line--;
			try {			
				iRegion = iDocument.getLineInformation(line);		             
	         
			//highlight the region
	         textEditor.setHighlightRange(iRegion.getOffset(), iRegion.getLength(), false);             
	         textEditor.selectAndReveal(iRegion.getOffset(), iRegion.getLength());
	         oldTextEditor = textEditor;
	         
			} catch (BadLocationException e) {}
			catch(Exception e){}
			
		}else{
			int addr = Integer.parseInt(address, 16);
			filePath = new DynamicFileLocationAL().dynFileLocation(addr,0); 	//0 -> relative
			if(filePath==null){
				if(!DynamicFileLocationAL.relativeFilePath.equalsIgnoreCase("")){
					filePath = DynamicFileLocationAL.relativeFilePath;
				}else{
					filePath = new LibDsmFileLocation(true).LibDsmFileLocInfo();
				}				
			}				
			
			iFile = (IFile) iWorkspaceRoot.findMember(filePath);	//need relative path value.	
			
			try {			
				editorPart = IDE.openEditor(page, iFile, 
						"org.eclipse.ui.DefaultTextEditor", true);				
	             textEditor = (TextEditor)editorPart;	             
	             iEditorInput = textEditor.getEditorInput();            
	             iDocument = textEditor.getDocumentProvider().getDocument(iEditorInput);                         
			}catch (Exception e) {}
			finally{			
				filePath = new DynamicFileLocationAL().dynFileLocation(addr,1); //1 -> absolute
				if(filePath==null){
					if(!DynamicFileLocationAL.absoluteFilePath.equalsIgnoreCase("")){
						filePath = DynamicFileLocationAL.absoluteFilePath;
					}else{
						filePath = new LibDsmFileLocation(true).dsmDetailFileLocation();
					}				
				}		
				
				EditorLineInfo.lineNo=0;
				int line= info.lineNumber(address, filePath);		//need absolute path value.	
				line--;
				try {			
					iRegion = iDocument.getLineInformation(line);		
					
					//highlight the region
					textEditor.setHighlightRange(iRegion.getOffset(), iRegion.getLength(), false);             
					textEditor.selectAndReveal(iRegion.getOffset(), iRegion.getLength());
					oldTextEditor = textEditor;
					
				} catch (BadLocationException e) {}
				catch(Exception e){}
			}
		}
		
		
	}
}
