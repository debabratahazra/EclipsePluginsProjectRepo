package rs232;

import java.io.IOException;
public class Send_cmd_thread extends Transfer implements Runnable
{
	Thread t;
	String name;
	
	
	
	Send_cmd_thread(String threadname) 
	{	name = threadname;		
		t = new Thread(this, name);
		t.start(); 
	}
	 
	public void run() 
		{chk=0;
			do{
				//System.out.println("inside send thread");
				int len=cmd_packet_array[0][0];
				//System.out.println("LENGTH::"+len);
				//System.out.println("PACKET     ");
				for(int i=0;i<len;i++)
					{//System.out.print("-->>>>"+cmd_packet_array[0][i]+"  ");
					try {
								outputStream.write(cmd_packet_array[0][i]);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
					}//System.out.println("");
				
				rcmd_t=new Recieve_cmd_thread("recievethread");
				while(rcmd_t.t.isAlive());//System.out.println("hey....");
				
			}while(chk!=0);
		}
		
}

