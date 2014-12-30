package com.cdt.keil.debug.ui.launch.run;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.cdt.keil.debug.ui.internal.SourceFileContentProvider;


public class FileOpenOperation {
		
	String dsmFile=null;
	IPath path = null;
	IFile dsmiFile = null;
			
	public FileOpenOperation() {					
	}
	
	public int openLibDsmFile(){
		
		dsmFile = new LibDsmFileLocation(true).LibDsmFileLocInfo();		
		if(dsmFile==null) return 0;		//Unsuccessful operation.
		
		path = new Path(dsmFile);
		dsmiFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		try {			
			IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
					getActivePage(), dsmiFile, "org.eclipse.ui.DefaultTextEditor", true);			
		} catch (PartInitException e) {}
		catch (Exception e) {}
		return 1;		//Successful Operation.
	}
	
	
	public void openAllDsmFile(String [] fileName){	
		
		dsmFile = new LibDsmFileLocation(true).LibDsmFileLocInfo();
		if(dsmFile==null) return;
		
		int index=dsmFile.lastIndexOf("\\");
		dsmFile=dsmFile.substring(0, index);
		
		for(int i=0;fileName[i]!=null;i++){			
			path = new Path(dsmFile +"\\"+ fileName[i]);
			dsmiFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);	
			
			try {			
				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
						getActivePage(), 
						dsmiFile, 
						"org.eclipse.ui.DefaultTextEditor");							
			} catch (PartInitException e) {}
			catch (Exception e) {}

		}		
	}
	
	public void openAllMxmFile(String[] fileName){
		
		dsmFile = new LibDsmFileLocation(true).LibDsmFileLocInfo();
		if(dsmFile==null) return;
		
		int index=dsmFile.lastIndexOf("\\");
		dsmFile=dsmFile.substring(0, index);
		
		for(int i=0;fileName[i]!=null;i++){
			if(fileName[i].toUpperCase().contains("LIBRARY.DSM")){
				continue;
			}
			index=fileName[i].lastIndexOf(".");
			fileName[i]=fileName[i].substring(0, index);
			fileName[i] = fileName[i].concat(".MXM");		
			
			try {			
				path = new Path(dsmFile +"\\"+ fileName[i]);
				dsmiFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
						getActivePage(), 
						dsmiFile, 
						"org.eclipse.ui.DefaultTextEditor");							
			} catch (PartInitException e) {}
			catch (Exception e) {}
		}	
	}
	
	public void openAllSouceFile(String[] srcFilePath){
		//srcFilePath -> absolute source file path location.
		
		String projectName = SourceFileContentProvider.getProjectName();
		for(int i=0;i<srcFilePath.length;i++){
			try{
				int index = srcFilePath[i].lastIndexOf(projectName);
				
				//Relative Source file path location.
				String srcFilePathLoc = srcFilePath[i].substring(index, srcFilePath[i].length());
				
				path = new Path(srcFilePathLoc);
				dsmiFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
						getActivePage(), 
						dsmiFile, 
						"org.eclipse.ui.DefaultTextEditor");	
			}catch(Exception e){}
		}		
	}
	
}
