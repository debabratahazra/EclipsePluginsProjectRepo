
package rs232;

import java.io.IOException;

public class Send_hexfile_thread extends Transfer implements Runnable
{
	Thread t;
	String name;
	
	
	Send_hexfile_thread(String threadname) 
	{	name = threadname;		
		t = new Thread(this, name);
		t.start(); 
	}
	
	public void run() 
	{		
		for(packet_no=1;packet_no<(no_of_packet);packet_no++){				
			int len=packet_array[packet_no][0];					
					for(int i=0;i<len;i++){						
						try {
								outputStream.write(packet_array[packet_no][i]);
								} catch (IOException e) {
									e.printStackTrace();
								}								
						}
					
				r_t=new Recieve_hexfile_thread("recievethread");
				while(r_t.t.isAlive());	
	
		}
		System.out.println("\nDownload Successfull...");	
	}
}
