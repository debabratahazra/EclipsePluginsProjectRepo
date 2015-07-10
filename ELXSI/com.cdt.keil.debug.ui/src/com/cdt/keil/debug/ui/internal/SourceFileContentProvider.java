package com.cdt.keil.debug.ui.internal;

import java.io.BufferedReader;
import java.io.FileReader;

import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;
import com.cdt.keil.debug.ui.debug.DownloadImageAction;

public class SourceFileContentProvider {
	
	public SourceFileContentProvider(){		
	}
	
	public static String[] sourceFilePath(){
		//Return absolute Source file path location.
				
		String srcFilePath = new SourceFileLocation(true).sourceFileLocation();
		if(srcFilePath==null){
			new DownloadImageAction();
			ConsoleDisplayMgr.getDefault().println("PROJECT BUILD ERROR, RE-BUILD THE PROJECT", 2);
			return null;
		}else{			
			try{
				FileReader fReader = new FileReader(srcFilePath);
				BufferedReader bReader = new BufferedReader(fReader);
				int i=0;
				while(bReader.readLine()!=null){
					i++;
				}				
				String [] filename=new String[i];
				i=0;
				bReader.close(); fReader.close();
				fReader = new FileReader(srcFilePath);
				bReader = new BufferedReader(fReader);
				String str= new String();
				while((str=bReader.readLine())!=null){
					filename[i]=str;
					i++;
				}
				fReader.close(); bReader.close();
				return filename;
			}catch(Exception e){}		
		}		
		return null;
	}
	
	public static String getProjectName(){
		String projectName = new SourceFileLocation(true).projectName();
		return projectName;
	}

}
