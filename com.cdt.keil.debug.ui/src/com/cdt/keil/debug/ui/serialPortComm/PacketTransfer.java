package com.cdt.keil.debug.ui.serialPortComm;

import java.io.IOException;

public class PacketTransfer {
	
	 static Process kill_process,process=null;	
	
	 public static Process DataTransfer(String arg)
	 {
		try {			
				process = Runtime.getRuntime().exec("java -jar " +
						"plugins\\com.cdt.keil.serialport_1.0.3.jar " + "00" + " " + arg);										
		} catch (IOException e) {}
		catch(Exception e){}
		return process;
	}	
	 
	 public static Process DataTransfer(int value, char ch){
		 		 
			String hexFile=new HexFileLocation().hexFileLocation();	
			if(hexFile==null) return null;
			hexFile = "\"" + hexFile + "\"";			
			try{
				if(hexFile!=null){
					process = Runtime.getRuntime().exec("java -jar " +
						"plugins\\com.cdt.keil.serialport_1.0.3.jar "+ hexFile + " " + value);
			}
			
	 } catch (IOException e) {}
	 catch(Exception e){}
		return process;
	 }
	 
	 
	 public static void terminateProcess()
	{
		try {
			kill_process = Runtime.getRuntime().exec("TASKKILL /F /IM JAVA.EXE");
		} catch (IOException e) {			
		}catch(Exception e){}		
	}
}
