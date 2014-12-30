
package rs232;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

public class Transfer extends Thread implements SerialPortEventListener
{	 
	@SuppressWarnings("unchecked")
		static Enumeration portList;
		static CommPortIdentifier portId;
		static SerialPort serialPort;
		static OutputStream outputStream;
		static InputStream inputStream;
		static BufferedWriter output;
		static Recieve_hexfile_thread r_t ;
		static Recieve_cmd_thread rcmd_t ;
		static String filename, command;
		static Send_hexfile_thread s_t;
		static Send_cmd_thread scmd_t;
		static DataOutputStream dout = null;
		static DataInputStream din = null;
		static OutputStream fos = null;
		static FileInputStream fileis = null;
		static int[] read_array ,data_array ,temp_packet;
		static int[][]file_data_array,cmd_packet_array=null,packet_array =null;
		public static int ch,l,packetLen,data_array_length,eol_char,data_avail,lrc,no_of_packet,p,packet_no,cmd_packet,len2;
		static int comnd,t=0,chk=0;		
		private static int[] cmd_array=new int [50];
		
public static void main(String[] args)
	{
	
	packetLen=0;	
	filename=args[0];   //HEX Filename or "00"
	command=args[1];    //TYPE (for HEX => 0 )
	
	
	for(int i=1;i<args.length-1;i++)
		cmd_array[i]=Integer.parseInt(args[i+1]);	
	
	cmd_packet_array =new int [1][60];
	
	switch(Integer.parseInt(command)){
	

	case 0:
		//Hex file download
		//Code Added................. Downloading Hex File.
		if((!filename.equalsIgnoreCase("00")) && (filename!=null) ){
			System.out.println("Downloading Hex Filename: " + filename.toUpperCase());
			hexFileReadPacket(filename);							
				s_t=new Send_hexfile_thread("sendthread");
				try 
				{	s_t.t.join();
			} catch (InterruptedException e) {				
			}		
			closePort();
		}				
		break;
					
	 case 1:	
		 //Read PC
		 		cmd_packet_array[0][0]= 0x03;
				cmd_packet_array[0][1]= 0x01;
				cmd_packet_array[0][2]= 0x02;				
				scmd_t=new Send_cmd_thread("send_cmd");				
				 try {
							scmd_t.t.join();
						} catch (InterruptedException e) {							
						}
				closePort();
				break;				
				
	 case 2:
		 //Write PC.......No permitted at all
		 
		 	/*cmd_packet_array[0][0]=0x05;
			cmd_packet_array[0][1]=0x02;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=0;
			for(int z=0;z<cmd_packet_array[0][0]-1;z++)
				cmd_packet_array[0][4]^=cmd_packet_array[0][z];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {
				}
				*/
				
			break;
			
			
	 case 3:
		 //Read ACC
		 	 cmd_packet_array[0][0]=0x03;
			 cmd_packet_array[0][1]=0x03;
			 cmd_packet_array[0][2]=0x00;
		   	 scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {					
				}
				closePort();
			break;
			
			
	 case 4:
		 //Write ACC
		 	cmd_packet_array[0][0]=0x05;
			cmd_packet_array[0][1]=0x04;
			cmd_packet_array[0][2]=cmd_array[2];
			cmd_packet_array[0][3]=cmd_array[1];
			cmd_packet_array[0][4]=0;
			for(int z=0;z<cmd_packet_array[0][0]-1;z++)
				cmd_packet_array[0][4]^=cmd_packet_array[0][z];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
			break;
			
			
	 case 5:
		 //Read Scratchpad RAM
		 	cmd_packet_array[0][0]=0x06;
			cmd_packet_array[0][1]=0x05;         //TYPE
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int z=0;z<cmd_packet_array[0][0]-1;z++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][z];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
			break;
			
			
	 case 6:
		 //Write Scratchpad RAM
		 	
		 	packetLen = 6 + cmd_array[3];
		 	
		 	cmd_packet_array[0][0]= packetLen;     //Total LEN
			cmd_packet_array[0][1]=0x06;  //TYPE
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			for(int i=5;i<(packetLen-1);i++)
				cmd_packet_array[0][i]=cmd_array[i-1];
			cmd_packet_array[0][packetLen-1]=0;
			for(int z=0;z<(packetLen-1);z++)
				cmd_packet_array[0][packetLen-1]^=cmd_packet_array[0][z];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
			closePort();
			break;
			
			
			
	 case 7:
		 //Read SFR
		 	cmd_packet_array[0][0]=6;
			cmd_packet_array[0][1]=7;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int z=0;z<cmd_packet_array[0][0]-1;z++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][z];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 8:
		 //Write SFR.............Not Permit to all memory location.
		 	packetLen=6 + cmd_array[3];
		 	cmd_packet_array[0][0]=packetLen;
			cmd_packet_array[0][1]=8;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			for(int i=5;i<(packetLen-1);i++)
				cmd_packet_array[0][i]=cmd_array[i-1];
			cmd_packet_array[0][packetLen-1]=0;
			for(int z=0;z<(packetLen-1);z++)
				cmd_packet_array[0][packetLen-1]^=cmd_packet_array[0][z];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 9:
		 //Read Flash Code Memory
		 	cmd_packet_array[0][0]=0x06;
			cmd_packet_array[0][1]=0x09;   //TYPE
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int z=0;z<cmd_packet_array[0][0]-1;z++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][z];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 10:
		 //Write Flash Code Memory
		 	packetLen = 6 + cmd_array[3];
		 	cmd_packet_array[0][0]=packetLen;
			cmd_packet_array[0][1]=10;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			for(int i=5;i<(packetLen-1);i++)cmd_packet_array[0][i]=cmd_array[i-1];
			cmd_packet_array[0][packetLen-1]=0;
			for(int z=0;z<(packetLen-1);z++)
				cmd_packet_array[0][packetLen-1]^=cmd_packet_array[0][z];
			
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 11:
		 //Read Flash Data Memory
		 	cmd_packet_array[0][0]=0x06;
			cmd_packet_array[0][1]=0x0B;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int i=0;i<cmd_packet_array[0][0]-1;i++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 12:
		 //Write Flash Data Memory.		 	
		 	packetLen= 6 + cmd_array[3];
		 	cmd_packet_array[0][0]=packetLen;
			cmd_packet_array[0][1]=12;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			for(int i=5;i<(packetLen-1);i++)cmd_packet_array[0][i]=cmd_array[i-1];
			cmd_packet_array[0][packetLen-1]=0;
			for(int z=0;z<(packetLen-1);z++)
				cmd_packet_array[0][packetLen-1]^=cmd_packet_array[0][z];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 13:
		 //Read Flash SRAM Buffer Code Memory. 
		 	cmd_packet_array[0][0]=6;
			cmd_packet_array[0][1]=13;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int i=0;i<cmd_packet_array[0][0]-1;i++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 14:
		 //Write Flash SRAM Buffer Code Memory.
		 
		 	packetLen=6+cmd_array[3];
		 	cmd_packet_array[0][0]=packetLen;
			cmd_packet_array[0][1]=14;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			for(int i=5;i<(packetLen-1);i++)
				cmd_packet_array[0][i]=cmd_array[i-1];
			cmd_packet_array[0][packetLen-1]=0;
			for(int z=0;z<(packetLen-1);z++)
				cmd_packet_array[0][packetLen-1]^=cmd_packet_array[0][z];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 15:
		 //Read Flash SRAM Buffer Data Memory.
		 
		 	cmd_packet_array[0][0]=0x06;
			cmd_packet_array[0][1]=0x0F;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int i=0;i<cmd_packet_array[0][0]-1;i++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
				
		
	 case 16:
		 //Write Flash SRAM Buffer Data Memory.
		 
		 	packetLen= 6 + cmd_array[3];
		 	cmd_packet_array[0][0]=packetLen;
			cmd_packet_array[0][1]=0x10;          //TYPE
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			for(int i=5;i<(packetLen-1);i++)
				cmd_packet_array[0][i]=cmd_array[i-1];
			cmd_packet_array[0][packetLen-1]=0;
			for(int i=0;i<(packetLen-1);i++)
				cmd_packet_array[0][packetLen-1]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 17:
		 //Read SRAM Data Memory
		 
		 	cmd_packet_array[0][0]=6;
			cmd_packet_array[0][1]=0x11;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int i=0;i<cmd_packet_array[0][0]-1;i++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
				
		
	 case 18:
		 //Write SRAM Data Memory
		 
		 	packetLen= 6 + cmd_array[3];
		 	cmd_packet_array[0][0]=packetLen;
			cmd_packet_array[0][1]=0x12;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			for(int i=5;i<(packetLen-1);i++)
				cmd_packet_array[0][i]=cmd_array[i-1];
			cmd_packet_array[0][packetLen-1]=0;
			for(int i=0;i<(packetLen-1);i++)
				cmd_packet_array[0][packetLen-1]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 19:
		 //Reset Instruction
		 
		 	cmd_packet_array[0][0]=0x03;
		 	cmd_packet_array[0][1]=0x13;
		 	cmd_packet_array[0][2]=0x10;
		 	scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
				
		
	 case 20:
		 //Run Instruction
		 
		 	cmd_packet_array[0][0]=0x03;
		 	cmd_packet_array[0][1]=0x14;
		 	cmd_packet_array[0][2]=0x17;
		 	scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 21:
		 //Stop Instruction
		 
		 	cmd_packet_array[0][0]=0x03;
		 	cmd_packet_array[0][1]=0x15;
		 	cmd_packet_array[0][2]=0x16;
		 	scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 22:
		 //Step Into Instruction
		 
		 	cmd_packet_array[0][0]=0x3;
	 		cmd_packet_array[0][1]=0x16;
	 		cmd_packet_array[0][2]=0x15;
	 		scmd_t=new Send_cmd_thread("send_cmd");
			 
	 		try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				/*try {
				while(inputStream.available()!=0)
				{
					rcmd_t=new Recieve_cmd_thread("recievethread");
					while(rcmd_t.t.isAlive());
					
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}System.out.print('$');*/
				
	 		closePort();
			break;
		
		
	 case 23:
		 //Add Breakpoint
		 
		 	cmd_packet_array[0][0]=0x06;
			cmd_packet_array[0][1]=0x17;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int i=0;i<cmd_packet_array[0][0]-1;i++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 24:
		 //Remove Breakpoint
		 
		 	cmd_packet_array[0][0]=0x04;
			cmd_packet_array[0][1]=0x18;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=0;
			for(int i=0;i<cmd_packet_array[0][0]-1;i++)
				cmd_packet_array[0][3]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 25:
		 //Free Run
		 
		 	cmd_packet_array[0][0]=0x03;
			cmd_packet_array[0][1]=0x19;
			cmd_packet_array[0][2]=26;
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
				
		
	 case 26:
		 //Write Security Data Memory...........not Possible
		 
		 	packetLen= 6 + cmd_array[3];
		 	cmd_packet_array[0][0]=packetLen;
			cmd_packet_array[0][1]=0x1A;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			for(int i=5;i<(packetLen-1);i++)
				cmd_packet_array[0][i]=cmd_array[i-1];
			cmd_packet_array[0][packetLen-1]=0;
			for(int i=0;i<(packetLen-1);i++)
				cmd_packet_array[0][packetLen-1]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 27:
		 //Read Security Data Memory
		 
		 	cmd_packet_array[0][0]=6;
			cmd_packet_array[0][1]=27;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int i=0;i<cmd_packet_array[0][0]-1;i++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 28:
		 //Write Security Program Memory
		 
		 	packetLen= 6 + cmd_array[3];
		 	cmd_packet_array[0][0]=packetLen;
			cmd_packet_array[0][1]=0x1C;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			for(int i=5;i<(packetLen-1);i++)
				cmd_packet_array[0][i]=cmd_array[i-1];
			cmd_packet_array[0][packetLen-1]=0;
			for(int i=0;i<(packetLen-1);i++)
				cmd_packet_array[0][packetLen-1]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
		
		
	 case 29:
		 //Read Security Program Memory
		 
		 	cmd_packet_array[0][0]=0x06;
			cmd_packet_array[0][1]=0x1D;
			cmd_packet_array[0][2]=cmd_array[1];
			cmd_packet_array[0][3]=cmd_array[2];
			cmd_packet_array[0][4]=cmd_array[3];
			cmd_packet_array[0][5]=0;
			for(int i=0;i<cmd_packet_array[0][0]-1;i++)
				cmd_packet_array[0][5]^=cmd_packet_array[0][i];
			scmd_t=new Send_cmd_thread("send_cmd");
			 try {
					scmd_t.t.join();
				} catch (InterruptedException e) {}
				closePort();
				break;
				
				
	 default:
		 scmd_t=new Send_cmd_thread("send_cmd");
	 	try {
			scmd_t.t.join();
		} catch (InterruptedException e) {}	
		closePort();
	    break; 
	
	}			
}
	

public static void handleData()
	{	
		//rcmd_t=new Recieve_cmd_thread("recievethread");
		//while(rcmd_t.t.isAlive());
	}

static void closePort()
		{
		serialPort.close();
	
		}

public Transfer()
	{
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements())
			{
				portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
					{	if (portId.getName().equals("COM1"))
						{
							try
								{	serialPort = (SerialPort) portId.open("writereadApp", 2000);
								//	System.out.println("Port Opened :: " + portId.getName());
									//System.out.println("inside portopening");
									
								}catch (PortInUseException e)
									{//System.out.print(e);	
									}
						}
							try
								{
									serialPort.setSerialPortParams(115200,
									SerialPort.DATABITS_8,
									SerialPort.STOPBITS_1,
									SerialPort.PARITY_NONE);
								}catch (UnsupportedCommOperationException e)
									{System.out.println("UnsupportedCommOperationException, Could not write to the port: " + e);
									}
							try
								{	outputStream = serialPort.getOutputStream();
									inputStream = serialPort.getInputStream();
								}catch (IOException e) 
									{//System.out.print(e);
									}
							try
								{
									serialPort.addEventListener(this);
								}catch (TooManyListenersException e)
									{//System.out.print(e);
									}
							serialPort.notifyOnDataAvailable(true);
							serialPort.notifyOnCarrierDetect(true);
							serialPort.notifyOnDataAvailable(true);
							serialPort.notifyOnBreakInterrupt(true);
							serialPort.notifyOnCTS(true);
							serialPort.notifyOnDSR(true);
							serialPort.notifyOnFramingError(true);
							serialPort.notifyOnOutputEmpty(true);
							serialPort.notifyOnOverrunError(true);
							serialPort.notifyOnParityError(true);
							serialPort.notifyOnRingIndicator(true);
					}
			}
	}

public void serialEvent(SerialPortEvent event)
	{	switch(event.getEventType())
			{case SerialPortEvent.DATA_AVAILABLE:handleData();break;
			}
	}
	


//Hex File Read Completed
	private static void hexFileReadPacket(String file_name)
{
	try 
{
	fileis = new FileInputStream(file_name);
	din=new DataInputStream(fileis);
	}
	catch(FileNotFoundException fne){
		//System.out.println("File Does not exists.");
		}

	try{
		data_avail =din.available();
		
		read_array = new int[data_avail];
			do {
			 	ch=din.read();
			 	if(ch != -1&& ch !=10 && ch !=13)
			 		{	read_array[l]=ch;
			 			l++;
		
			 		}
			 	else eol_char++;
		
			   }while(ch != -1);
		 fileis.close();
																																	
		 packet_array = new int[eol_char][60];	
		 file_data_array = new int[eol_char][60];
		 
		 int temp1,temp2,temp3,j=0,s=-1;	 	 																			
		 for (int i=0;i<(data_avail-eol_char);i++)
	
		 {	 if (read_array[i]==':')
		 		{
			 s++;
			 j=0;
			 no_of_packet=s+1; 
			 }
			 else
				{	temp1=read_array[i]%48;
					temp2=read_array[i+1]%48;
					if(temp1>9)temp1=temp1-7;
					temp3=temp1<<4;
					if(temp2>9)temp2=temp2-7;
					file_data_array[s][j]= (temp3|temp2);																		
					j++;i++;																							
				}		
		 }
		
	
		 // Run Instruction in first packet
		 packet_array[0][0]=03;
		 packet_array[0][1]=20;
		 packet_array[0][2]=23;
	
	   for(p=0;p<no_of_packet;p++){
		
		 	if(file_data_array[p][3]==0)
			   	{																				
					data_array_length=file_data_array[p][0];	
					data_array = new int[data_array_length + 3];
					data_array[0]=file_data_array[p][1];
					data_array[1]=file_data_array[p][2];
					data_array[2]=file_data_array[p][0];	
						
					 for(int v=4,b=3;(b<(data_array_length+3)||v<(data_array_length+4));b++,v++)
					 {	
						 data_array[b]=file_data_array[p][v];
					 }
					temp_packet = new int [data_array_length+6];
					temp_packet[0]= (data_array_length+6);
					temp_packet[1]=0x0A;   //TYPE
					for(int k1=2,m=0;k1<(data_array_length+5)||m<(data_array_length+3);k1++,m++)
						temp_packet[k1]=data_array[m];
					int[] temp_crc = new int[data_array_length+5];
					for(int x=0;x<data_array_length+5;x++)
						temp_crc[x]=  temp_packet[x];
					for(int z=0;z<data_array_length+4;z++)
						temp_crc[z+1]^=temp_crc[z];
					temp_packet[data_array_length+5]=temp_crc[data_array_length+4];
					
					for(int d=0;d<data_array_length+6;d++)
					{
						packet_array[p+1][d]=temp_packet[d];
					}
				}
				
		 		
			
			}
	   		
	   		//Stop Instruction
	   		 packet_array[no_of_packet][0]=03;
			 packet_array[no_of_packet][1]=21;
			 packet_array[no_of_packet][2]=22;
			 
			 //Reset Instruction
			 packet_array[no_of_packet+1][0]=03;
			 packet_array[no_of_packet+1][1]=19;
			 packet_array[no_of_packet+1][2]=16;
			 
			 
			
		
	}catch(IOException ioe){
		//System.out.println("IOException" + ioe);
		}
	}
	
}





