package com.cdt.managedbuilder.keil.command;



import org.eclipse.cdt.managedbuilder.core.IManagedCommandLineGenerator;
import org.eclipse.cdt.managedbuilder.core.IManagedCommandLineInfo;
import org.eclipse.cdt.managedbuilder.core.ITool;

import com.cdt.managedbuilder.keil.support.RefreshFile;;


public class KeilCommandLineGeneratorLinker implements IManagedCommandLineGenerator {

	public KeilCommandLineGeneratorLinker() {		
	}

	public IManagedCommandLineInfo generateCommandLineInfo(ITool tool,
			String commandName, String[] flags, String outputFlag,
			String outputPrefix, String outputName, String[] inputResources,
			String commandLinePattern) {
		
		ManagedBuildCommandLineInfo info = new ManagedBuildCommandLineInfo();
		
		
		try {
			KeilCommandLineGeneratorCompiler.bw.close();
			KeilCommandLineGeneratorCompiler.flag=false;
		} catch (Exception e) {}
		

	//  Passed in command name like C51
		info.commandName = new String(commandName);
		
		
		// Don't change the command line pattern
		info.commandLinePattern = new String("${COMMAND} ${INPUTS} ${FLAGS}");

		
		String myinputs = new String();
		String myflags = new String();
		
		if(inputResources!=null){		
		
	//  Get the All Inputs		
		String[] inputs = new String[inputResources.length];
		
		for (int i=0; i<inputResources.length; i++) {
			inputs[i] = inputResources[i];
		}		
		
		//  Sort
		for (int i = 0; i < inputs.length; i++) {
			for (int j = 1; j < inputs.length; j++) {
				if (inputs[j].compareTo(inputs[j-1]) < 0) {
					String temp = inputs[j-1];
					inputs[j-1] = inputs[j];
					inputs[j] = temp;
				}
			}
		}
		for (int i = 0; i < inputs.length; i++) {
			if (i > 0) myinputs += " , ";
			//myinputs += "..\\";
			
			int inputint=inputs[i].lastIndexOf("\\");
			inputs[i]=inputs[i].substring(inputint+1);			
			myinputs += inputs[i];
		}
		
		}
		
		
		
		//  Put out the flags 
		
		for (int i = flags.length - 1; i >= 0; i--) {
			if (i < flags.length - 1) myflags += " ";
			myflags += flags[i];
		}		
		
		
		
		info.commandFlags = myflags;
		
		
		
		info.commandInputs = myinputs;
		
		
		info.commandOutput = new String(outputName);
		//info.commandOutput = info.getOutput();
		
		info.commandOutputFlag = new String(outputFlag);
		
		info.commandOutputPrefix = new String(outputPrefix);
		
				
		if(info.commandOutput.length()<=1){
			//No Ops
		}
		else{
			
			String map=new String(info.commandOutput);	
			map=map.toUpperCase();
			
			
		info.commandLine = new String(info.commandName + " " + info.commandInputs + " " + 
				" TO " + info.commandOutput.substring(0, info.commandOutput.length()-4) +
				".ABS" + " " + info.commandFlags +	" " + "PRINT(" + map + ")" +
				" " + "NAME(" + map.substring(0, map.length()-4) + ")");
		}
		
		RefreshFile refresh=new RefreshFile();
		refresh.start();
		
		return info;
		
	}

	

}
