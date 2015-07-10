package com.cdt.keil.debug.ui.internal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

public class MainHighlighter {
	
	
	public MainHighlighter() {		
	}
	
	@SuppressWarnings("unchecked")
	public String getMainFilename(){
		// Return source filename of main() method.
					
		String fileName=new String();
		new FileInfoName();
		for(Iterator it=FileInfoName.setKeys.iterator(); it.hasNext();){
			Integer key = (Integer) it.next();			
			for(int i=1;i<=key;i+=4){
				String fModname = (String) FileInfoName.map.get(i+1);
				if(fModname.toLowerCase().equalsIgnoreCase("main")){
					fileName = (String) FileInfoName.map.get(i);
					int index = fileName.lastIndexOf(".");
					fileName = fileName.substring(0, index+1) + "C";
					return fileName;
				}			
			}
		}			
		return null;		
	}
	
	public int mainLineNumber(){
		//Return main(); line number
		
		String[] srcFilename = SourceFileContentProvider.sourceFilePath();
		String mainFilename = getMainFilename().toLowerCase();
		for(int i=0;i<srcFilename.length;i++){
			if(srcFilename[i].toLowerCase().contains(mainFilename)){
				mainFilename = srcFilename[i];
				break;
			}
		}
		
		try{
			FileReader fReader = new FileReader(mainFilename);
			BufferedReader bReader = new BufferedReader(fReader);
			String readLine=new String();
			int linenumber=1;
			while((readLine = bReader.readLine())!=null){
				if(readLine.toLowerCase().contains("main")){
					return linenumber;
				}
				linenumber++;
			}
			fReader.close(); bReader.close();
		}catch(Exception e){			
		}		
		return 0;	
	}
	
	public String mainFilepath(int type){
		//type->0 Return relative main() file path.
		//type->1 Return Absolute main() file path.
		
		String[] srcFilename = SourceFileContentProvider.sourceFilePath();
		String mainFilename = getMainFilename().toLowerCase();
		for(int i=0;i<srcFilename.length;i++){
			if(srcFilename[i].toLowerCase().contains(mainFilename)){
				mainFilename = srcFilename[i];
				break;
			}
		}		
		if(type==1){
			return mainFilename;		//absolute file path.
		}else{
			String projectName = SourceFileContentProvider.getProjectName();
			int index = mainFilename.lastIndexOf(projectName);
			mainFilename = mainFilename.substring(index, mainFilename.length());
			return mainFilename;		//relative file path.			
		}		
	}
	
	
	
	

}
