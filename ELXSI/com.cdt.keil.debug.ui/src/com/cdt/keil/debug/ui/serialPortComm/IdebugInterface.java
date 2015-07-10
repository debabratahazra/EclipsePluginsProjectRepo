package com.cdt.keil.debug.ui.serialPortComm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.cdt.keil.debug.ui.console.ConsoleDisplayMgr;


public class IdebugInterface extends PacketTransfer 
{	
		
	//Transfer Hex File to the Target......Done
	public static  int transferHexFile() 
	{		
		Process processData = DataTransfer(0,'~');  // 0 => For Running Hex File Downloader.
		if(processData==null) return 0;
		ConsoleDisplayMgr.getDefault().println("HEX FILE DOWNLOADED SUCCESSFULLY.", 1);		
		showOutputHexFile(processData);    //To show the Received Packet on Console.		
		process.destroy();				
		return 1;
	}	

	public static int [] readProgramCounter()
	{		
		Process processData = DataTransfer("1");
		int[] value=getPacketValue(processData);		
		process.destroy();
		return value;
	}
		
	/**  Note:
	 *  Write PC Function isn't implemented.
	 */
	
	public static int [] readAccumulator()
	{
		Process processData = DataTransfer("3");
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
		
	public static int[] writeAccumulator(short[] Packet )
	{ 
		int Type=0x04;
		String arg=shortToString(Type,Packet);	
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);		
		process.destroy();
		return value;
	}	
	
	public static int [] readScratchpadRAM(short[] Packet )
	{
		int Type=0x05;
		String arg=shortToString(Type, Packet);		
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
		
		
	public static void writeScratchpadRAM(short[] Packet )
	{
		int Type=0x06;
		String arg=shortToString(Type, Packet);
		DataTransfer(arg);
		process.destroy();
	}
	
	
	public static int []  readSpecialFunctionRegisters(short[] Packet )
	{
		int Type=0x07;
		String arg=shortToString(Type, Packet);
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	
	public static int[] writeSpecialFunctionRegisters(short[] Packet )
	{
		int Type=0x08;
		String arg=shortToString(Type, Packet);
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	

	public static int [] readFlashCodeMemory(short[] Packet )
	{
		int Type=0x09;
		String arg=shortToString(Type, Packet);
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static int[] writeFlashCodeMemory (short[] Packet)
	{	
		int Type=0x0A;
		String arg=shortToString(Type,Packet);	
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;	}
	
	public static int [] readFlashDataMemory(short[] Packet )
	{
		int Type=0x0B;
		String arg=shortToString(Type, Packet);		
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static int[] writeFlashDataMemory(short[] Packet)
	{
		int Type=0x0C;
		String arg=shortToString(Type,Packet);	
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static int [] readFlashSRAMBufferCodeMemory(short[] Packet )
	{
		int Type=0x0D;
		String arg=shortToString(Type, Packet);		
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static int[] writeFlashSRAMBufferCodeMemory( short[] Packet)
	{
		int Type=0x0E;
		String arg=shortToString(Type,Packet);	
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static int [] readFlashSRAMBufferDataMemory( short[]Packet )
	{
		int Type=0x0F;
		String arg=shortToString(Type, Packet);		
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static int[] writeFlashSRAMBufferDataMemory( short[]Packet )
	{
		int Type=0x10;
		String arg=shortToString(Type,Packet);	
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	
	public static int [] readSRAMDataMemory(short[] Packet )
	{
		int Type=0x11;
		String arg=shortToString(Type, Packet);		
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	
	public static int[] writeSRAMDataMemory(short[] Packet )
	{
		int Type=0x12;
		String arg=shortToString(Type,Packet);	
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static int[] resetInstruction()
	{		
		Process processData=DataTransfer("19");
		int[] value=getPacketValue(processData);
		if(value!=null)
			ConsoleDisplayMgr.getDefault().println("TARGET RESET", 1);
		process.destroy();
		return value;
	}
	
	public static int[] runInstruction()
	{
		Process processData=DataTransfer("20");
		int[] value=getPacketValue(processData);
		if(value!=null)
			ConsoleDisplayMgr.getDefault().println("RUN INSTRUCTION EXECUTED.", 1);
		return value;
	}
	
	public static int[] runInstruction2()
	{
		Process processData=DataTransfer("20");
		int[] value=getPacketValue(processData);		
		return value;
	}
	
	public static int[] stopInstruction(boolean tValue)
	{
		Process processData=DataTransfer("21");
		int[] value=getPacketValue(processData);
		if(value!=null && tValue)
			ConsoleDisplayMgr.getDefault().println("TARGET STOP.", 1);
		process.destroy();
		return value;
	}
	
	public static int[] stepInto(){
		Process processData=DataTransfer("22");	
		int[] value=getPacketValue(processData);
		if(value!=null)
			ConsoleDisplayMgr.getDefault().println("STEP-INTO INSTRUCTION EXECUTED.", 1);
		process.destroy();
		return value;
	}
	
	public static int[] virtualStepInto(){
		Process processData=DataTransfer("22");	
		int[] value=getPacketValue(processData);		
		process.destroy();
		return value;
	}
	
	public static int[] stepOver(){
		//Step Into Instruction is sending.
		Process processData=DataTransfer("22");	
		int[] value=getPacketValue(processData);
		if(value!=null)
			ConsoleDisplayMgr.getDefault().println("STEP-OVER INSTRUCTION EXECUTED.", 1);
		process.destroy();
		return value;
	}
	
	public static int[] addBreakpoint(short[] Packet, boolean show){
		int Type=0x17;
		String arg=shortToString(Type, Packet);
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();		
		if (value[2]==100 || value[2]==99);
		else{			
			Packet[2]--;
			if(Packet[2]==-1){
				Packet[2]=255;
				Packet[1]--;
			}
			String str=new String();
			if(show){
				str = "BREAKPOINT ADDED AT 0x";
			}else{
				str = "BREAKPOINT ENABLED AT 0x";
			}						
			
			if(Packet[1]<=15) str += "0";
			str += Integer.toHexString((int)Packet[1]).toUpperCase();
			if(Packet[2]<=15)str += "0";
			str+= Integer.toHexString((int)Packet[2]).toUpperCase();
			ConsoleDisplayMgr.getDefault().println( str , 1);
		}	
		return value;
	}
	
	public static int[] addBreakpointHL(short[] Packet, boolean show, int line){
		int Type=0x17;
		String arg=shortToString(Type, Packet);
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();		
		if (value[2]==100 || value[2]==99);
		else{			
			String str=new String();
			if(show){
				str = "BREAKPOINT ADDED AT LINE NUMBER: ";
			}else{
				str = "BREAKPOINT ENABLED AT LINE NUMBER: ";
			}			
			ConsoleDisplayMgr.getDefault().println( str + line , 1);
		}	
		return value;
	}
	
	public static int[] addVirtualBreakpoint(short[] Packet){
		int Type=0x17;
		String arg=shortToString(Type, Packet);
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();	
		return value;
	}
	
	public static int[] removeBreakpoint(short[] Packet, boolean show){
		int Type=0x18;
		String arg=shortToString(Type, Packet);
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		if(value[2]==0) ConsoleDisplayMgr.getDefault().println("ALL BREAKPOINTS REMOVED.", 1);
		else if (value[2]==100 );
		else if(show){
			ConsoleDisplayMgr.getDefault().println("BREAKPOINT REMOVED." , 1);
		}else if(!show){
			ConsoleDisplayMgr.getDefault().println("BREAKPOINT DISABLED." , 1);
		}
		return value;
	}
	
	public static int[] removeBreakpointHL(short[] Packet, boolean show, int line){
		int Type=0x18;
		String arg=shortToString(Type, Packet);
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();		
		if(show){
			ConsoleDisplayMgr.getDefault().println("BREAKPOINTS REMOVED AT LINE NUMBER: " + line , 1);
		}else if(!show){
			ConsoleDisplayMgr.getDefault().println("BREAKPOINT DISABLED AT LINE NUMBER: " + line , 1);
		}
		return value;
	}
	
	public static int[] removeVirtualBreakpoint(short ID){
		int Type = 0x18;
		short[] Packet = {ID};
		String arg=shortToString(Type, Packet);
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static void freeRun(){
		DataTransfer("25");
		ConsoleDisplayMgr.getDefault().println("FREE RUN EXECUTED.", 1);
	}
	
	/**  Note:
	 *  Terminal Data Function isn't implemented. 
	 *  Write Data Security Memory function isn't implemented. 
	 */
	
	public static int[] readDataSecurityMemory(short[] Packet){
		int Type=0x1B;
		String arg=shortToString(Type, Packet);		
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static int[] writeProgramSecurityMemory(short[] Packet){
		int Type=0x1C;
		String arg=shortToString(Type,Packet);	
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}
	
	public static int[] readProgramSecurityMemory(short[] Packet){
		int Type=0x1D;
		String arg=shortToString(Type, Packet);		
		Process processData=DataTransfer(arg);
		int[] value=getPacketValue(processData);
		process.destroy();
		return value;
	}	
	
	
	
	private static void showOutputHexFile(Process processData) {
		//Show Receive Packet during download Hex File. 
		
		BufferedReader	bReader = new	BufferedReader(new	InputStreamReader
				(new BufferedInputStream(processData.getInputStream())));
		try {
			String string=new String();			
		do{
			string = bReader.readLine();
			/*if(string == null )
				break;
			ConsoleDisplayMgr.getDefault().println(string,1);
			System.out.flush();*/
		}while(!string.equalsIgnoreCase("Download Successfull..."));
		bReader.close();
		} catch (java.io.IOException e) {}		
		catch(Exception e){}
	}
	
	
	private static int[] getPacketValue(Process processData){
		BufferedInputStream buffer = new BufferedInputStream(processData.getInputStream());
		BufferedReader	commandResult = new	BufferedReader(new	InputStreamReader(buffer));
		int intValue[]=new int[10];
		try {
			String str=new String();	
			str = commandResult.readLine();	
			if(str==null){
				ConsoleDisplayMgr.getDefault().print("", 2);
				ConsoleDisplayMgr.getDefault().println("TARGET COMMUNICATION FAILED.", 2);
				return null;
			}
			str+=" ";
			int val=0;
			String[] value=new String[str.length()];
			intValue=new int[str.length()];
			for(int i=0,j=0;j<str.length()-1;i++){
				val=str.indexOf(" ",j);	
				value[i]=str.substring(j, val);
				j=val+1;			
				intValue[i]=Integer.valueOf(value[i]);				
			}						
			commandResult.close();
		} catch (Exception e) {
		}
		return intValue;		
	}
	
	
	private static String shortToString(int type, short[] packet) {
		String string=new String();
		string=Integer.toString(type);
		string+=" ";
		for(int i=0;i<packet.length;i++){
			string+=Integer.toString(packet[i]);
			string+=" ";
		}		
		return string;
	}
}
