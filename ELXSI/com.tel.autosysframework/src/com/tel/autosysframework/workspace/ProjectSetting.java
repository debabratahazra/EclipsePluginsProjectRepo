package com.tel.autosysframework.workspace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.tel.autosysframework.internal.Refresh;
import com.tel.autosysframework.views.GeneralConfigure;

public class ProjectSetting {
	
	private static BufferedWriter writer = null;
	private static String fileName = null;
	
	/**
	 * Constructor
	 */
	public ProjectSetting() {
	}
	
	/**
	 * Save the project settings in .setting file.
	 * @param input1
	 * @param input2
	 * @param output
	 */
	public static void saveProjectSetting(String input1, String input2, String output){
		
		fileName = new ProjectInformation().getProjectName(4) + "\\.setting";
		try {
			writer = new BufferedWriter(new FileWriter(new File(fileName)));
			
			writer.write(input1);
			writer.newLine();
			writer.write(output);
			if(GeneralConfigure.isTwoChannel()){
				writer.newLine();
				writer.write("true");
				writer.newLine();
				writer.write(input2);
			}else{
				writer.newLine();
				writer.write("false");				
			}
			writer.flush();
			writer.close();
			new Refresh().start();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Get the project settings from .setting file.
	 * @return I/O file path information
	 */
	public static String[] getProjectSetting(){
		new Refresh().start();
		String[] info = new String[3];
		fileName = new ProjectInformation().getProjectName(4) + "\\.setting";
		try {			
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			if(reader==null) return null;	//No project settings are present.
			info[0] = reader.readLine();
			info[1] = reader.readLine();			
			info[2] = reader.readLine();
			if(info[2].equalsIgnoreCase("true")){
				info[2]= reader.readLine();
			}else{
				info[2] = null;
			}
			reader.close();
			return info;
		} catch (Exception e) {
		}
		return null;
	}

}
