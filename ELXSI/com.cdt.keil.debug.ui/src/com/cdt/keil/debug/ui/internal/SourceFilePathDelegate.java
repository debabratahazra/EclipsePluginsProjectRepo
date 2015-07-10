package com.cdt.keil.debug.ui.internal;

public class SourceFilePathDelegate {
	
	public static String absSrcFile, relativeSrcFile;
	
	static{
		absSrcFile = new String();
		relativeSrcFile= new String();
	}
	
	public SourceFilePathDelegate() {	
	}	
		
	public String getSrcFilePath(String fileName, int type){
		//Provides (type) 0-> Relative & 1-> Absolute source file path.
		
		String[] srcFilepath = SourceFileContentProvider.sourceFilePath();
		String projectname = SourceFileContentProvider.getProjectName();
		String sourceFileName= new String();
		for(int srcFileNumber=0;srcFileNumber<srcFilepath.length;srcFileNumber++){
			if(srcFilepath[srcFileNumber].toLowerCase().contains(fileName.toLowerCase())){
				sourceFileName = srcFilepath[srcFileNumber];
				break;
			}
		}
		switch(type){
		case 0:
			//Relative file path
			int index = sourceFileName.lastIndexOf(projectname);
			return sourceFileName.substring(index, sourceFileName.length());			
		case 1:
			//Absolute file path
			return sourceFileName;			
		default:
			break;
		}		
		return null;
	}

}
