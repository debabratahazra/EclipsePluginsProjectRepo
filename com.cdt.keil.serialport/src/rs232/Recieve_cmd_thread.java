package rs232;

import java.io.IOException;

public	class Recieve_cmd_thread extends Transfer implements Runnable
{	Thread t; 
	String name;
	Recieve_cmd_thread (String threadname)
	{	name = threadname;		
		t = new Thread(this, name);
		t.start(); 
	}
	
   public void run()
   {
		
		int instream_avail;
		int type=0;
		
		try { instream_avail = inputStream.available();//System.out.println("<<--"+instream_avail+"  ");
			  if(instream_avail>0)
			     {
					int[] rec_packet_integer = new int[instream_avail+1];
					int[] temp_rec_packet_integer = new int[instream_avail+1];
					byte[] recieved_packet = new byte[instream_avail];
					int crc_index=0;
					inputStream.read(recieved_packet, 0, instream_avail);//System.out.println(":::");
				//	for(int llll=0;llll<instream_avail;llll++)System.out.print(recieved_packet[llll]+" ");
					for (int i = 0; i < instream_avail; i++)
						{	if((int)recieved_packet[i]<=-1)
							rec_packet_integer[i]= (256-((~recieved_packet[i])+1));
							
							else rec_packet_integer[i]=  recieved_packet[i];
								
							temp_rec_packet_integer[i]=rec_packet_integer[i];
							if(i==1)type=rec_packet_integer[i];
							if(i==(instream_avail-1))crc_index=i;
						}
					for(int z=0;z<instream_avail-2;z++)temp_rec_packet_integer[z+1]^=temp_rec_packet_integer[z];
					//System.out.println("INTEGER");
				//	for(int lll=0;lll<instream_avail;lll++)System.out.print(rec_packet_integer[lll]+" ");
					if(rec_packet_integer[crc_index]==temp_rec_packet_integer[crc_index])
						{
						   switch(type)
							{	
								case 129 : if(rec_packet_integer[0]==instream_avail)
											{chk=0;
											//System.out.print("chk::"+chk);
										//	System.out.print("program counter value ::  ");
											//System.out.print("available: "+instream_avail+" ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
				 							}
										else chk=1;//System.out.print("chk::"+chk);
										break;
										
								case 130 :if(rec_packet_integer[0]==instream_avail){chk=0;                              
										//	System.out.print("program counter value written");
											for (int i = 0; i < instream_avail-1; i++)
												System.out.print(rec_packet_integer[i] + " ");
														System.out.print('\n');
																				}
										else chk=1;
										break;
										
								case 131 : if(rec_packet_integer[0]==instream_avail)
										{chk=0;
											//System.out.print("Accumulator value ::  ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
										}
										else chk=1;
										break;
										
								case 132 :if(rec_packet_integer[0]==instream_avail){chk=0;
											//System.out.print("Accumulator value written");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');}
										else chk=1;
										break;
										
								case 133:if(rec_packet_integer[0]==instream_avail)
										{chk=0;
											//System.out.print("Scratchpad RAM value ::  ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
											}
										else chk=1;
										break;
										
								case 134:if(rec_packet_integer[0]==instream_avail){chk=0;
										//	System.out.print("Data written to scratchpad RAM");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');}
										else chk=1;
										break;
										
								case 135:if(rec_packet_integer[0]==instream_avail)
										{chk=0;
											//System.out.print("Special function register value ::  ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
										}
										else chk=1;
										break;
										
								case 136: if(rec_packet_integer[0]==instream_avail){chk=0;
										//	System.out.print("Data written to Special function register");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
								System.out.print('\n');}
										else chk=1;
										break;
										
								case 137 : if(rec_packet_integer[0]==instream_avail)
										{chk=0;
										//	System.out.print("Flash code memory value ::  ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
										}
										else chk=1;
										break;
								
								case 138:if(rec_packet_integer[0]==instream_avail){chk=0;
										//	System.out.print("Data written to Flash code memory ");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
								System.out.print('\n');}
										else chk=1;
										break;
								
								case 139: if(rec_packet_integer[0]==instream_avail)
										{chk=0;
										//	System.out.print("Flash data memory value ::  ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
										}
										else chk=1;
										break;
								
								case 140:if(rec_packet_integer[0]==instream_avail){chk=0;
										//System.out.print("Data written to Flash data memory ");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
										System.out.print('\n');}
										else chk=1;
										break;
								
								case 141: if(rec_packet_integer[0]==instream_avail)
										{chk=0;
										//	System.out.print("SRAM buffer code memory value ::  ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
										}
										else chk=1;
										break;
										
								case 142:if(rec_packet_integer[0]==instream_avail)
											{chk=0;for (int i = 0; i < instream_avail; i++)
												System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');
											}
											else chk=1;
											break;
										
//											int cr=13,lf=10;
										//	System.out.print("Data written to SRAM buffer code memory ");									
											//for (int i = 0; i < instream_avail; i++)
											//System.out.print(rec_packet_integer[i]);
											//System.out.print('\n');
											/*	int x,y;x=rec_packet_integer[i]/16;if(x>9)x=55+x;else x=x+48;fos.write(x);
											y=rec_packet_integer[i]%16;if(y>9)y=y+55;else y=y+48;fos.write(y);*/
											//fos.write(space); 
											//}//fos.write(cr);fos.write(lf);
											//	System.out.println("");
											
											
								case 143: if(rec_packet_integer[0]==instream_avail)
										{chk=0;
										//	System.out.print("SRAM buffer data memory value ::  ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
										}
										else chk=1;
										break;
								
								case 144:if(rec_packet_integer[0]==instream_avail){chk=0;
										//	System.out.print("Data written to SRAM buffer data memory ");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');}
										else chk=1;
										break;
								
								case 145:  if(rec_packet_integer[0]==instream_avail)
										{chk=0;
										//	System.out.print("SRAM data memory value ::  ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
										}
										else chk=1;
										break;
								
								case 146:if(rec_packet_integer[0]==instream_avail){chk=0;
										//	System.out.print("Data written to SRAM data memory ");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');}
										else chk=1;
										break;
								
								case 147:if(rec_packet_integer[0]==instream_avail){chk=0;
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
										//	System.out.print("Reseted..");
											System.out.print('\n');}
								
										else chk=1;
										break;
										
								case 148:
									if(rec_packet_integer[0]==instream_avail)
												{
													chk=0;//System.out.print("Running..");
													for (int i = 0; i <= instream_avail-1; i++)
														System.out.print(rec_packet_integer[i] + " ");
													System.out.print('\n');
												}
										else if (rec_packet_integer[0]==(instream_avail-3))
										{chk=0;
											for (int i = 3; i <= instream_avail-1; i++)
												System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');
										}
										else chk=1;
										break;
										
								
								case 149:
									if(rec_packet_integer[0]==instream_avail){
									chk=0;
									for (int i = 0; i <= instream_avail-1; i++)
										System.out.print(rec_packet_integer[i] + " ");
											//System.out.print("Stopped..");
											System.out.print('\n');}
										else chk=1;
										break;
										
								case 150:
									//Step Instruction Receive Packet
									
									if(rec_packet_integer[0]==instream_avail)
												{
													chk=0;		System.out.println("Step into..");
													for (int i = 0; i <= instream_avail+3; i++)
														System.out.print(rec_packet_integer[i] + " ");
													System.out.print('\n');}
										else if (rec_packet_integer[0]==(instream_avail-3))
											{	
												chk=0;
												for (int i = 3; i <= instream_avail-1; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
											}
										else if ((instream_avail-rec_packet_integer[0])>3)
										{	
											chk=0;
											for (int i = 3; i <= instream_avail-1; i++)
												System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');
										}
										else chk=1;
									System.out.println("end......");
										break;
								
								
								
								case 151:
									if(rec_packet_integer[0]==instream_avail){chk=0;
									//		System.out.print("break point set..");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');}
										else chk=1;
										break;
										
								case 152:if(rec_packet_integer[0]==instream_avail){chk=0;
										//	System.out.print("break point Removed..");
											for (int i = 0; i <= instream_avail-1; i++)
												System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');}
										else chk=1;
										break;
								
								case 153:if(rec_packet_integer[0]==instream_avail)
											{
											chk=0;//System.out.print("Free Running..");
											for (int i = 0; i <= instream_avail-1; i++)
												System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');}
										else if (rec_packet_integer[0]==(instream_avail-3))
											{
												chk=0;
												for (int i = 3; i <= instream_avail-1; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
											}
										else chk=1;
										break;
										
								case 154:if(rec_packet_integer[0]==instream_avail){chk=0;
									//		System.out.print("Data written to security memory(data memory)");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
											System.out.print('\n');}
										else chk=1;
										break;
										
								case 155:  if(rec_packet_integer[0]==instream_avail)
										{chk=0;
									//		System.out.print("Security memory(data memory)value :: ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
										}
										else chk=1;
										break;
										
								case 156:if(rec_packet_integer[0]==instream_avail)
										{chk=0;
									//		System.out.print("Data written to security memory(Program memory) ");                       
										for (int i = 0; i <= instream_avail-1; i++)
											System.out.print(rec_packet_integer[i] + " ");
										System.out.print('\n');
										}
										else chk=1;
										break;
										
								case 157:  if(rec_packet_integer[0]==instream_avail)
										{chk=0;
									//		System.out.print("Security memory(Program memory)value ::  ");
												for (int i = 0; i < instream_avail; i++)
													System.out.print(rec_packet_integer[i] + " ");
												System.out.print('\n');
										}
										else chk=1;
										break;
										
								case 163:if(rec_packet_integer[0]==instream_avail){chk=0;
									//		System.out.print("Terminal data... ");
								for (int i = 0; i <= instream_avail-1; i++)
									System.out.print(rec_packet_integer[i] + " ");
								System.out.print('\n');}
										else chk=1;
								        break;
										
								default:{	chk=1;
									//		System.out.print(" ERROR IN DATA RECIEVED :: RESENDING DATA");
										}
							}
						}
			       }
			}catch (IOException e) {//e.printStackTrace();
										}
			
		
		
		
   }
}