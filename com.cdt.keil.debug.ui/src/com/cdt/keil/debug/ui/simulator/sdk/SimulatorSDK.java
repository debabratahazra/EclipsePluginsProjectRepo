package com.cdt.keil.debug.ui.simulator.sdk;

import com.cdt.keil.debug.ui.memory.views.DataSecurityMemoryView;
import com.cdt.keil.debug.ui.memory.views.FlashCodeMemoryView;
import com.cdt.keil.debug.ui.memory.views.FlashDataMemoryView;
import com.cdt.keil.debug.ui.memory.views.FlashSRAMBufferCodeView;
import com.cdt.keil.debug.ui.memory.views.FlashSRAMBufferDataView;
import com.cdt.keil.debug.ui.memory.views.ProgramSecurityMemoryView;
import com.cdt.keil.debug.ui.memory.views.RegisterView;
import com.cdt.keil.debug.ui.memory.views.SFRView;
import com.cdt.keil.debug.ui.memory.views.SRAMDataMemoryView;
import com.cdt.keil.debug.ui.memory.views.ScratchpadRAMView;


public class SimulatorSDK {	
		
	private static String addressValue;	
		
	public SimulatorSDK() {		
		addressValue=new String();			
	}
	
		
	public void  DataTransfer (int[] Packet)   //Packet => Full Packet is send to SimulatorSDK.
	{   
		int Type=(int)Packet[1];
						
		switch (Type) {
		
			case 0x81:
				//Read PC				
				ReadProgramCounter(); break;
				
			case 0x82:
				//Write PC
				WriteProgramCounter(Packet); break;
				
			case 0x83:
				//Read ACC
				ReadAccumulator();	break;
				
			case 0x84:
				//Write ACC
				WriteAccumulator(Packet);	break;
				
			case 0x85:
				//Read Scratchpad RAM
				ReadScratchpadRAM(Packet);	break;
				
			case 0x86:
				//Write Scratchpad RAM
				WriteScratchpadRAM(Packet);	break;
				
			case 0x87:
				//Read SFR
				ReadSpecialFunctionRegisters(Packet);	break;
				
			case 0x88:
				//Write SFR
				WriteSpecialFunctionRegisters(Packet);	break;
				
			case 0x89:
				//Read Flash Code Memory
				ReadFlashCodeMemory(Packet);	break;
				
			case 0x8A:
				//Write Flash Code Memory
				WriteFlashCodeMemory(Packet);	break;
				
			case 0x8B:
				//Read Flash Data Memory
				ReadFlashDataMemory(Packet);	break;
				
			case 0x8C:
				//Write Flash Data Memory
				WriteFlashDataMemory(Packet);	break;
				
			case 0x8D:
				//Read Flash SRAM Buffer Code Memory
				ReadFlashSRAMBufferCodeMemory(Packet);	break;
				
			case 0x8E:
				//Write Flash SRAM Buffer Code Memory
			    WriteFlashSRAMBufferCodeMemory(Packet);	break;
				
			case 0x8F:
				//Read Flash SRAM Buffer Data Memory
				ReadFlashSRAMBufferDataMemory(Packet);	break;
				
			case 0x90:
				//Write Flash SRAM Buffer Data Memory
				WriteFlashSRAMBufferDataMemory(Packet);	break;
				
			case 0x91:
				//Read SRAM Data Memory
				ReadSRAMDataMemory(Packet);	break;
				
			case 0x92:
				//Write SRAM Data Memory
				WriteSRAMDataMemory(Packet);	break;			
				
			case 0x9A:
				//Write Data Security Memory
				WriteDataSecurityMemory(Packet);break;
				
			case 0x9B:
				//Read Data Security Memory
				ReadDataSecurityMemory(Packet);	break;
				
			case 0x9C:
				//Write Program Security Memory
				WriteProgramSecurityMemory(Packet);	break;
				
			case 0x9D:	
				//Read Program Security Memory
				ReadProgramSecurityMemory(Packet);	break;	
				
			default:	break;
		}		
	}
	
	
	
	private void ReadProgramCounter()
	{			
		//String StringValue=RegisterView.addressValue[0].getText(1);
		//ConsoleDisplayMgr.getDefault().println("ACK: PC " + StringValue, 1);					
	}
	
	private void  WriteProgramCounter(int[]  PacketData)
	{ 
		addressValue = new String(getPacketData(PacketData));
		if(!RegisterView.regAddressValue[0].getText(1).equalsIgnoreCase("0x" + addressValue)
				&& RegisterView.boolValue)
			RegisterView.regAddressValue[0].setBackground(RegisterView.yellow);
		else
			RegisterView.regAddressValue[0].setBackground(RegisterView.normal);
		RegisterView.regAddressValue[0].setText(1, "0x" + addressValue);
		
		//ConsoleDisplayMgr.getDefault().println("ACK: PC 0x" + addressValue, 1);
	}
	
	private void ReadAccumulator()
	 {
		//String StringValue=RegisterView.addressValue[1].getText(1);
		//ConsoleDisplayMgr.getDefault().println("ACK: ACC " + StringValue, 1);
	 }
	
	 private void WriteAccumulator(int[] PacketData)
	 {
		 addressValue = new String(getPacketData(PacketData));		 
		
		if(!RegisterView.regAddressValue[1].getText(1).equalsIgnoreCase("0x" + addressValue.substring(2, 4))
				&& RegisterView.boolValue)
			RegisterView.regAddressValue[1].setBackground(RegisterView.yellow);
		else
			RegisterView.regAddressValue[1].setBackground(RegisterView.normal);
		 
		 if(!RegisterView.regAddressValue[2].getText(1).equalsIgnoreCase("0x" + addressValue.substring(0, 2))
				 && RegisterView.boolValue)
			RegisterView.regAddressValue[2].setBackground(RegisterView.yellow);
		 else
			RegisterView.regAddressValue[2].setBackground(RegisterView.normal);
		 
		 RegisterView.regAddressValue[1].setText(1, "0x" + addressValue.substring(2, 4));		 
		 RegisterView.regAddressValue[2].setText(1, "0x" + addressValue.substring(0,2));
		 
		 //ConsoleDisplayMgr.getDefault().println("ACK: ACC 0x" + addressValue.substring(2, 4), 1);
	 }	 
	 
	
	private void ReadScratchpadRAM(int[] PacketData)
	 { 		 
		 int findROW = findRowValue(PacketData);		 	
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];		 
		 addressValue = new String(getPacketData(PacketData));		 
		/* ConsoleDisplayMgr.getDefault().println("Read Scratchpad RAM of 0x" + Integer.toHexString(DataLength) + 
				 " Byte from 0x" + addressValue , 1);*/
		 
		 for(; DataLength!=0 ; DataLength--,findColumn++){
			 if(findColumn==17){
				 findColumn=1;
				 findROW++;
			 }
			/* ConsoleDisplayMgr.getDefault().print("0x" + ScratchpadRAMView.
					 TableItemValue[findROW].getText(findColumn) + "  " , 1);*/
		 }	
		//ConsoleDisplayMgr.getDefault().println("",1);
	 }

	
	private void WriteScratchpadRAM(int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		/* ConsoleDisplayMgr.getDefault().println("Read Scratchpad RAM of 0x" + Integer.toHexString(DataLength) + 
				 " Byte from 0x" + addressValue , 1);	*/	 
		
		 for(int DataIndex=5; DataLength!=0 ; DataIndex++,DataLength--,findColumn++){
			 if(findColumn==17){
				String strValue = new String();
				for(int j=1;j<=16;j++){						
					int intValue=Integer.valueOf(ScratchpadRAMView.TableItemValue[findROW].getText(j),16);
					if(intValue == 0) strValue += " ";
					else strValue += new Character((char)intValue).toString();					
				}				
				ScratchpadRAMView.TableItemValue[findROW].setText(17, strValue);
				findColumn=1;
				findROW++;
			 }			 
			 String DataValue=new String();
			 if(PacketData[DataIndex]>=0 && PacketData[DataIndex]<=15) DataValue="0";
			 DataValue+=Integer.toHexString((int)PacketData[DataIndex]);			 
			 try{
				 ScratchpadRAMView.TableItemValue[findROW].setText(findColumn, DataValue.toUpperCase());
			 }catch(Exception e){}
			 finally{
				ScratchpadRAMView.table.redraw();
			}
		 }		 
		 String strValue = new String();
			for(int j=1;j<=16;j++){						
				int intValue=Integer.valueOf(ScratchpadRAMView.TableItemValue[findROW].getText(j),16);
				if(intValue == 0) strValue += " ";
				else strValue += new Character((char)intValue).toString();			
			}
			try{
				ScratchpadRAMView.TableItemValue[findROW].setText(17, strValue);
			}catch(Exception e){}
			finally{
				ScratchpadRAMView.table.redraw();
			}
	 }
	 
	 private void ReadSpecialFunctionRegisters(int[] PacketData)
	 { 
		 int findROW = findRowValue(PacketData);
		 findROW -= 8;  					 //Starting Address 0x0080;
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		/* ConsoleDisplayMgr.getDefault().println("Read SFR of 0x" + Integer.toHexString(DataLength) + 
				 " Byte from 0x" + addressValue.substring(2, addressValue.length()) , 1);*/
		 
		 for(; DataLength!=0 ; DataLength--,findColumn++){
			 if(findColumn==17){
				 findColumn=1;
				 findROW++;
			 }			 			 
			 //ConsoleDisplayMgr.getDefault().print("0x" + SFRValue + "  ",1);
		 }	
		// ConsoleDisplayMgr.getDefault().println("", 1);
	 }
	 
	 
	 private void WriteSpecialFunctionRegisters(int[] PacketData )
	 {
		 int findROW = findRowValue(PacketData);
		 findROW -= 8;   				//Starting Address 0x0080;
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		// ConsoleDisplayMgr.getDefault().println("Read SFR of 0x" + Integer.toHexString(DataLength) + 
		//		 " Byte from 0x" + addressValue.substring(2, addressValue.length()), 1);		 
		
		 for(int DataIndex=5; DataLength!=0 ; DataIndex++,DataLength--,findColumn++){
			 if(findColumn==17){
				String strValue = new String();
				for(int j=1;j<=16;j++){						
					int intValue=Integer.valueOf(SFRView.TableItemValue[findROW].getText(j),16);
					if(intValue == 0) strValue += " ";
					else strValue += new Character((char)intValue).toString();					
				}
				SFRView.TableItemValue[findROW].setText(17, strValue);
				findColumn=1;
				findROW++;
			 }			 
			 String DataValue=new String();
			 if(PacketData[DataIndex]>=0 && PacketData[DataIndex]<=15) DataValue="0";
			 DataValue+=Integer.toHexString((int)PacketData[DataIndex]);		
			 try{
				 SFRView.TableItemValue[findROW].setText(findColumn, DataValue.toUpperCase());
			 }catch(Exception e){}
			 finally{
				 SFRView.table.redraw();
			 }
		 }		
		String strValue = new String();
		for(int j=1;j<=16;j++){						
			int intValue=Integer.valueOf(SFRView.TableItemValue[findROW].getText(j),16);
			if(intValue == 0) strValue += " ";
			else strValue += new Character((char)intValue).toString();		
		}	
		try{
			SFRView.TableItemValue[findROW].setText(17, strValue);
		}catch(Exception e){}
		finally{
			 SFRView.table.redraw();
		 }
	 }	 
	

	private void ReadFlashCodeMemory(int[] PacketData)
	{
		int findROW = findRowValue(PacketData);	
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 //ConsoleDisplayMgr.getDefault().println("Read Flash Code Memory of 0x" + Integer.toHexString(DataLength) + 
			//	 " Byte from 0x" + addressValue,1);
		 
		 for(; DataLength!=0 ; DataLength--,findColumn++){
			 if(findColumn==17){
				 findColumn=1;
				 findROW++;
			 }
			// ConsoleDisplayMgr.getDefault().print("0x" + FlashCodeMemoryView.
			//		 TableItemValue[findROW].getText(findColumn) + "  ",1);
		 }	
		// ConsoleDisplayMgr.getDefault().println("",1);		    
	 }
	 
	 
	 private void WriteFlashCodeMemory(int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);	
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 //ConsoleDisplayMgr.getDefault().println("Read Flash Code Memory of 0x" + Integer.toHexString(DataLength) + 
		//	 " Byte from 0x" + addressValue,1);		 
		
		 for(int DataIndex=5; DataLength!=0 ; DataIndex++,DataLength--,findColumn++){
			 if(findColumn==17){
				 String strValue = new String();
					for(int j=1;j<=16;j++){						
						int intValue=Integer.valueOf(FlashCodeMemoryView.TableItemValue[findROW].getText(j),16);
						if(intValue == 0) strValue += " ";
						else strValue += new Character((char)intValue).toString();					
					}
					FlashCodeMemoryView.TableItemValue[findROW].setText(17, strValue);
				 findColumn=1;
				 findROW++;
			 }			 
			 String DataValue=new String();
			 if(PacketData[DataIndex]>=0 && PacketData[DataIndex]<=15) DataValue="0";
			 DataValue+=Integer.toHexString((int)PacketData[DataIndex]);		
			 try{
				 FlashCodeMemoryView.TableItemValue[findROW].setText(findColumn, DataValue.toUpperCase());
			 }catch(Exception e){}
			 finally{
				 FlashCodeMemoryView.table.redraw();
			 }
		 }	
		 String strValue = new String();
			for(int j=1;j<=16;j++){						
				int intValue=Integer.valueOf(FlashCodeMemoryView.TableItemValue[findROW].getText(j),16);
				if(intValue == 0) strValue += " ";
				else strValue += new Character((char)intValue).toString();			
			}	
			try{
				FlashCodeMemoryView.TableItemValue[findROW].setText(17, strValue);
			}catch(Exception e){}
			finally{
				 FlashCodeMemoryView.table.redraw();
			 }
	 }
	 
	 
	 private void ReadFlashDataMemory(int[] PacketData)
	 { 
		 int findROW = findRowValue(PacketData);	
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 //ConsoleDisplayMgr.getDefault().println("Read Flash Data Memory of 0x" + Integer.toHexString(DataLength) + 
		//	 " Byte from 0x" + addressValue,1);
	 
		 for(; DataLength!=0 ; DataLength--,findColumn++){
			 if(findColumn==17){
				 findColumn=1;
				 findROW++;
			 }
			// ConsoleDisplayMgr.getDefault().print("0x" + FlashDataMemoryView.
			//		 TableItemValue[findROW].getText(findColumn) + "  ",1);
		 }	
		// ConsoleDisplayMgr.getDefault().println("",1);     
	 }
	 
	 
	 private void WriteFlashDataMemory(int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);	
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		// ConsoleDisplayMgr.getDefault().println("Read Flash Data Memory of 0x" + Integer.toHexString(DataLength) + 
		//		 " Byte from 0x" + addressValue,1);		 
		
		 for(int DataIndex=5; DataLength!=0 ; DataIndex++,DataLength--,findColumn++){
			 if(findColumn==17){
				 String strValue = new String();
					for(int j=1;j<=16;j++){						
						int intValue=Integer.valueOf(FlashDataMemoryView.TableItemValue[findROW].getText(j),16);
						if(intValue == 0) strValue += " ";
						else strValue += new Character((char)intValue).toString();					
					}
					FlashDataMemoryView.TableItemValue[findROW].setText(17, strValue);
				 findColumn=1;
				 findROW++;
			 }			 
			 String DataValue=new String();
			 if(PacketData[DataIndex]>=0 && PacketData[DataIndex]<=15) DataValue="0";
			 DataValue+=Integer.toHexString((int)PacketData[DataIndex]);	
			 try{
				 FlashDataMemoryView.TableItemValue[findROW].setText(findColumn, DataValue.toUpperCase());
			 }catch(Exception e){}
			 finally{
				 FlashDataMemoryView.table.redraw();
			 }
		 }	
		 String strValue = new String();
			for(int j=1;j<=16;j++){						
				int intValue=Integer.valueOf(FlashDataMemoryView.TableItemValue[findROW].getText(j),16);
				if(intValue == 0) strValue += " ";
				else strValue += new Character((char)intValue).toString();			
			}
			try{
				FlashDataMemoryView.TableItemValue[findROW].setText(17, strValue);
			}catch(Exception e){}
			finally{
				 FlashDataMemoryView.table.redraw();
			 }
	 }
	 
	 
	 private void ReadFlashSRAMBufferCodeMemory(int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		/* ConsoleDisplayMgr.getDefault().println("Read Flash SRAM Buffer Code Memory of 0x" + Integer.toHexString(DataLength) + 
			 " Byte from 0x" + addressValue,1);*/
	 
		 for(; DataLength!=0 ; DataLength--,findColumn++){
			 if(findColumn==17){
				 findColumn=1;
				 findROW++;
			 }
			/* ConsoleDisplayMgr.getDefault().print("0x" + FlashSRAMBufferCodeView.
					 TableItemValue[findROW].getText(findColumn) + "  ",1);*/
		 }	
		 //ConsoleDisplayMgr.getDefault().println("",1);      
	 }
	 
	 
	 private void WriteFlashSRAMBufferCodeMemory(int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);	
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		/* ConsoleDisplayMgr.getDefault().println("Read Flash SRAM Buffer Code Memory of 0x" + 
				 Integer.toHexString(DataLength) + 
				 " Byte from 0x" + addressValue,1);		*/ 
		
		 for(int DataIndex=5; DataLength!=0 ; DataIndex++,DataLength--,findColumn++){
			 if(findColumn==17){
				 String strValue = new String();
					for(int j=1;j<=16;j++){						
						int intValue=Integer.valueOf(FlashSRAMBufferCodeView.TableItemValue[findROW].getText(j),16);
						if(intValue == 0) strValue += " ";
						else strValue += new Character((char)intValue).toString();						
					}
					FlashSRAMBufferCodeView.TableItemValue[findROW].setText(17, strValue);
				 findColumn=1;
				 findROW++;
			 }			 
			 String DataValue=new String();
			 if(PacketData[DataIndex]>=0 && PacketData[DataIndex]<=15) DataValue="0";
			 DataValue+=Integer.toHexString((int)PacketData[DataIndex]);
			 try{
				 FlashSRAMBufferCodeView.TableItemValue[findROW].setText(findColumn, DataValue.toUpperCase());
			 }catch(Exception e){}
			 finally{
				 FlashSRAMBufferCodeView.table.redraw();
			 }
		 }		
		 String strValue = new String();
			for(int j=1;j<=16;j++){						
				int intValue=Integer.valueOf(FlashSRAMBufferCodeView.TableItemValue[findROW].getText(j),16);
				if(intValue == 0) strValue += " ";
				else strValue += new Character((char)intValue).toString();			
			}	
			try{
				FlashSRAMBufferCodeView.TableItemValue[findROW].setText(17, strValue);
			}catch(Exception e){}
			finally{
				 FlashSRAMBufferCodeView.table.redraw();
			 }
	 }
	 
	 
	 private void ReadFlashSRAMBufferDataMemory(int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);		
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 /*ConsoleDisplayMgr.getDefault().println("Read Flash SRAM Buffer Data Memory of 0x" + Integer.toHexString(DataLength) + 
			 " Byte from 0x" + addressValue,1);*/
	 
		 for(; DataLength!=0 ; DataLength--,findColumn++){
			 if(findColumn==17){
				 findColumn=1;
				 findROW++;
			 }
			 /*ConsoleDisplayMgr.getDefault().print("0x" + FlashSRAMBufferDataView.
					 TableItemValue[findROW].getText(findColumn) + "  ",1);*/
		 }	
		 //ConsoleDisplayMgr.getDefault().println("",1);  
	 }
	 
	 
	 private void WriteFlashSRAMBufferDataMemory(int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);	
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 //ConsoleDisplayMgr.getDefault().println("Read Flash SRAM Buffer Data Memory of 0x" + Integer.toHexString(DataLength) + 
		//		 " Byte from 0x" + addressValue,1);		 
		
		 for(int DataIndex=5; DataLength!=0 ; DataIndex++,DataLength--,findColumn++){
			 if(findColumn==17){
				 String strValue = new String();
					for(int j=1;j<=16;j++){						
						int intValue=Integer.valueOf(FlashSRAMBufferDataView.TableItemValue[findROW].getText(j),16);
						if(intValue == 0) strValue += " ";
						else strValue += new Character((char)intValue).toString();					
					}
					FlashSRAMBufferDataView.TableItemValue[findROW].setText(17, strValue);
				 findColumn=1;
				 findROW++;
			 }			 
			 String DataValue=new String();
			 if(PacketData[DataIndex]>=0 && PacketData[DataIndex]<=15) DataValue="0";
			 DataValue+=Integer.toHexString((int)PacketData[DataIndex]);	
			 try{
				 FlashSRAMBufferDataView.TableItemValue[findROW].setText(findColumn, DataValue.toUpperCase());
			 }catch(Exception e){}
			 finally{
				 FlashSRAMBufferDataView.table.redraw();
			 }
		 }	
		 String strValue = new String();
			for(int j=1;j<=16;j++){						
				int intValue=Integer.valueOf(FlashSRAMBufferDataView.TableItemValue[findROW].getText(j),16);
				if(intValue == 0) strValue += " ";
				else strValue += new Character((char)intValue).toString();		
			}	
			try{
				FlashSRAMBufferDataView.TableItemValue[findROW].setText(17, strValue);
			}catch(Exception e){}
			 finally{
				 FlashSRAMBufferDataView.table.redraw();
			 }
	 }
	 
	 
	 private void ReadSRAMDataMemory(int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);	
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 /*ConsoleDisplayMgr.getDefault().println("Read SRAM Data Memory of 0x" + Integer.toHexString(DataLength) + 
			 " Byte from 0x" + addressValue,1);*/
	 
		 for(; DataLength!=0 ; DataLength--,findColumn++){
			 if(findColumn==17){
				 findColumn=1;
				 findROW++;
			 }
			/* ConsoleDisplayMgr.getDefault().print("0x" + SRAMDataMemoryView.
					 TableItemValue[findROW].getText(findColumn) + "  ",1);*/
		 }	
		 //ConsoleDisplayMgr.getDefault().println("",1); 
	 }
	 
	 
	 private void WriteSRAMDataMemory(int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 /*ConsoleDisplayMgr.getDefault().println("Read SRAM Data Memory of 0x" + Integer.toHexString(DataLength) + 
				 " Byte from 0x" + addressValue,1);	*/	 
		
		 for(int DataIndex=5; DataLength!=0 ; DataIndex++,DataLength--,findColumn++){
			 if(findColumn==17){
				 String strValue = new String();
					for(int j=1;j<=16;j++){						
						int intValue=Integer.valueOf(SRAMDataMemoryView.TableItemValue[findROW].getText(j),16);
						if(intValue == 0) strValue += " ";
						else strValue += new Character((char)intValue).toString();					
					}
					SRAMDataMemoryView.TableItemValue[findROW].setText(17, strValue);
				 findColumn=1;
				 findROW++;
			 }			 
			 String DataValue=new String();
			 if(PacketData[DataIndex]>=0 && PacketData[DataIndex]<=15) DataValue="0";
			 DataValue+=Integer.toHexString((int)PacketData[DataIndex]);	
			 try{
				 SRAMDataMemoryView.TableItemValue[findROW].setText(findColumn, DataValue.toUpperCase());
			 }catch(Exception e){}
			 finally{
				 SRAMDataMemoryView.table.redraw();
			 }
		 }	
		 String strValue = new String();
			for(int j=1;j<=16;j++){						
				int intValue=Integer.valueOf(SRAMDataMemoryView.TableItemValue[findROW].getText(j),16);
				if(intValue == 0) strValue += " ";
				else strValue += new Character((char)intValue).toString();			
			}	
			try{
				SRAMDataMemoryView.TableItemValue[findROW].setText(17, strValue);
			}catch(Exception e){}
			finally{
				 SRAMDataMemoryView.table.redraw();
			 }
	 }
	 
	 private void WriteDataSecurityMemory(int[] PacketData)
	 {
		 DataSecurityMemoryView.table.redraw();
		 int findROW = findRowValue(PacketData);		
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4] ;
		 
		 addressValue = new String(getPacketData(PacketData));
		/* ConsoleDisplayMgr.getDefault().println("Read Data Security Memory of 0x" + Integer.toHexString(DataLength) + 
				 " Byte from 0x" + addressValue,1);		 */
		
		 for(int DataIndex=5; DataLength!=0 ; DataIndex++,DataLength--,findColumn++){
			 if(findColumn==17){
				 String strValue = new String();
					for(int j=1;j<=16;j++){						
						int intValue=Integer.valueOf(DataSecurityMemoryView.TableItemValue[findROW].getText(j),16);
						if(intValue == 0) strValue += " ";
						else strValue += new Character((char)intValue).toString();					
					}
					DataSecurityMemoryView.TableItemValue[findROW].setText(17, strValue);
				 findColumn=1;
				 findROW++;
			 }			 
			 String DataValue=new String();
			 if(PacketData[DataIndex]>=0 && PacketData[DataIndex]<=15) DataValue="0";
			 DataValue+=Integer.toHexString((int)PacketData[DataIndex]);	
			 try{
				 DataSecurityMemoryView.TableItemValue[findROW].setText(findColumn, DataValue.toUpperCase());
			 }catch(Exception e){}
			 finally{
				 DataSecurityMemoryView.table.redraw();
			 }
		 }	
		 String strValue = new String();
			for(int j=1;j<=16;j++){						
				int intValue=Integer.valueOf(DataSecurityMemoryView.TableItemValue[findROW].getText(j),16);
				if(intValue == 0) strValue += " ";
				else strValue += new Character((char)intValue).toString();			
			}
			try{
				DataSecurityMemoryView.TableItemValue[findROW].setText(17, strValue);
			}catch(Exception e){}
			DataSecurityMemoryView.table.redraw();
	 }
	 
	 private void ReadDataSecurityMemory( int[] PacketData)
	 {
		 int findROW = findRowValue(PacketData);		
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 /*ConsoleDisplayMgr.getDefault().println("Read Data Security Memory of 0x" + Integer.toHexString(DataLength) + 
			 " Byte from 0x" + addressValue,1);*/
	 
		 for(; DataLength!=0 ; DataLength--,findColumn++){
			 if(findColumn==17){
				 findColumn=1;
				 findROW++;
			 }
			 /*ConsoleDisplayMgr.getDefault().print("0x" + DataSecurityMemoryView.
					 TableItemValue[findROW].getText(findColumn) + "  ",1);*/
		 }	
		 //ConsoleDisplayMgr.getDefault().println("",1);
		 
	 }
	 
	 private void WriteProgramSecurityMemory(int[] PacketData)
	 {		
		 int findROW = findRowValue(PacketData);	
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 /*ConsoleDisplayMgr.getDefault().println("Read Program Security Memory of 0x" + Integer.toHexString(DataLength) + 
				 " Byte from 0x" + addressValue,1);		 */
		
		 for(int DataIndex=5; DataLength!=0 ; DataIndex++,DataLength--,findColumn++){
			 if(findColumn==17){
				 String strValue = new String();
					for(int j=1;j<=16;j++){						
						int intValue=Integer.valueOf(ProgramSecurityMemoryView.TableItemValue[findROW].getText(j),16);
						if(intValue == 0) strValue += " ";
						else strValue += new Character((char)intValue).toString();					
					}
					ProgramSecurityMemoryView.TableItemValue[findROW].setText(17, strValue);
				 findColumn=1;
				 findROW++;
			 }			 
			 String DataValue=new String();
			 if(PacketData[DataIndex]>=0 && PacketData[DataIndex]<=15) DataValue="0";
			 DataValue+=Integer.toHexString((int)PacketData[DataIndex]);	
			 try{
				 ProgramSecurityMemoryView.TableItemValue[findROW].setText(findColumn, DataValue.toUpperCase());
			 }catch(Exception e){}
			 finally{
				 ProgramSecurityMemoryView.table.redraw();
			 }
		 }		
		 String strValue = new String();
			for(int j=1;j<=16;j++){						
				int intValue=Integer.valueOf(ProgramSecurityMemoryView.TableItemValue[findROW].getText(j),16);
				if(intValue == 0) strValue += " ";
				else strValue += new Character((char)intValue).toString();		
			}	
			try{
				ProgramSecurityMemoryView.TableItemValue[findROW].setText(17, strValue);
			}catch(Exception e){}
			finally{
				 ProgramSecurityMemoryView.table.redraw();
			 }
	 }
	 
	 private void ReadProgramSecurityMemory( int[] PacketData)
	 {		   
		 int findROW = findRowValue(PacketData);		
		 int findColumn = (PacketData[3]) & 0x000F; findColumn++;		
		 int DataLength = (int)PacketData[4];
		 
		 addressValue = new String(getPacketData(PacketData));
		 /*ConsoleDisplayMgr.getDefault().println("Read Program Security Memory of 0x" + 
				 Integer.toHexString(DataLength) + 
			 " Byte from 0x" + addressValue,1);*/
	 
		 for(; DataLength!=0 ; DataLength--,findColumn++){
			 if(findColumn==17){
				 findColumn=1;
				 findROW++;
			 }
			/*ConsoleDisplayMgr.getDefault().print("0x" + ProgramSecurityMemoryView.
					 TableItemValue[findROW].getText(findColumn) + "  ",1);*/
		 }	
		 //ConsoleDisplayMgr.getDefault().println("",1);
	 }
	 
	 private String getPacketData(int[] PacketData) {
			String strValue =new String();
			if(PacketData[2]>=0 && PacketData[2]<=15) strValue="0";
			strValue+=Integer.toHexString((int)PacketData[2]);
			if(PacketData[3]>=0 && PacketData[3]<=15) strValue+="0";
			strValue+=Integer.toHexString((int)PacketData[3]);
			strValue=strValue.toUpperCase();
			return strValue;
	}
	 
	 private int findRowValue(int[] packet) {
		 int LSB = packet[3] >>> 4;
		 int MidBit = packet[2] & 0x0F;
		 int MSB = packet[2] >>> 4 ;		 
		 return ((MSB*256)+(MidBit*16)+ LSB);		 
	}				 
}




