package rs232;

import java.io.IOException;
public	class Recieve_hexfile_thread extends Transfer implements Runnable
{	Thread t; 
	String name;
	Recieve_hexfile_thread (String threadname)
	{	name = threadname;		
		t = new Thread(this, name);
		t.start(); 
	}
	
   public void run()
   {		
	int instream_avail;
	int type=0;
	 
	try { instream_avail = inputStream.available();	
	
		  if(instream_avail>0)
		     {
				int[] rec_packet_integer = new int[instream_avail+1];
				int[] temp_rec_packet_integer = new int[instream_avail+1];
				byte[] recieved_packet = new byte[instream_avail];
				int crc_index=0;
				inputStream.read(recieved_packet, 0, instream_avail);
				
				
				
				for (int i = 0; i < instream_avail; i++)
					{	
					if((int)recieved_packet[i]<=-1)
						rec_packet_integer[i]= (256-((~recieved_packet[i])+1));
						
						else rec_packet_integer[i]=  recieved_packet[i];
							
						temp_rec_packet_integer[i]=rec_packet_integer[i];
						if(i==1)type=rec_packet_integer[i];
						if(i==(instream_avail-1))crc_index=i;
					}
				for(int z=0;z<instream_avail-2;z++)
					temp_rec_packet_integer[z+1]^=temp_rec_packet_integer[z];
				
				

				//Display Received Packet in Console
				for(int i=0,j=0; i<instream_avail;i=j+1) {					
					System.out.println();
					for(j=0;j<rec_packet_integer[i];j++)
						System.out.print(Integer.toHexString(rec_packet_integer[i+j]).
								toUpperCase() + " ");
				}
				
				
				
				
				if(rec_packet_integer[crc_index]==temp_rec_packet_integer[crc_index])
					{
					   switch(type)
						{
								case 0x8A:	  //Receive TYPE
									if(rec_packet_integer[0]==instream_avail)
										
										break;
					
							
							default:	if(packet_no>1)packet_no=packet_no-1;
										else packet_no=1;
										System.out.println("DATA ERROR::RESENDING DATA");
										break;
									
							
						}
					}
		       }
		}catch (IOException e) {
									}
		
	
	
	}
}

