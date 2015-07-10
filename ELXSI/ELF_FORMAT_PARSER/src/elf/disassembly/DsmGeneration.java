/*******************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: DsmGeneration
 * @version: 1.0
 * Date: June 2009 
 * @author Santanu Guchhait
 * Description: This file defines the functions and the class 
 * that will generate the disassembly level instruction for the 
 * ARM based elf file format
 *******************************************************************************/


/***********************************************************
 * File Name   - DsmGeneration.java                        *
 * Author      -                            *
 * Description - 
 ***********************************************************/
package elf.disassembly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import read.elf.ReadHeader;
import read.elf.ReadSection;
import dwarf.internal.ArrayExpend;
import elf.internal.*;



public class DsmGeneration 
{
	private static int MAX_ROW_COUNT = 1, MAX_COLUMN_COUNT = 4;
	public int character_position,sectionsize,length,Start_Address,End_Address;
	private long opcodevalue=0;
	private String Pnemonics="";
	private String flagstatus= "";
	private String registername= "";
	private int [][] Code_Array = new int [MAX_ROW_COUNT][MAX_COLUMN_COUNT];
	private RandomAccessFile seeker = null;
	String starting_address,ending_address,modulename,Projectpath=null;
	public static String[] Path =new String[1];
	public static int NumOfSourceFiles = 0;
	public static int difference=0;

	
	/********************************************************************
	 * @param filename	
	 * Description    - Constructor for the class DSM_Generation.
	 *********************************************************************/
	public DsmGeneration(String filename) throws IOException 
	{
		seeker = new RandomAccessFile(filename, "r");
	}
	

	/********************************************************************	 
	 * Parameter      - Address and BufferedWriter object
	 * Return Type    - No return
	 * Description    - Reads the executable file to parse the opcodes and 
	 *                  generate the respective assembly instructions.
	 *********************************************************************/
	public void disassembler(int addr, BufferedWriter out) throws IOException
	{
		int  j=0;
		int  i=0;
		int temp,temp1,temp2,temp3,temp4,Ps,Pd;
		String finalstring = "" ;
		String first_byte_string = new String();
		String second_byte_string = new String();
		String third_byte_string = new String();
		String fourth_byte_string,temporary_string,rd,rm,rs,rn;
		int registervalue1,registervalue2,registervalue3,registervalue4 ;
		int temp_addr=addr;
		seeker.seek(0);
		seeker.seek(temp_addr);
		Code_Array[i][j]=seeker.readByte();
		temporary_string = "";
		if(Code_Array[i][j]>= -128 && Code_Array[i][j]<= -1)
		{
			Code_Array[i][j]=Code_Array[i][j]+256;
		}
		else if(Code_Array[i][j]>=0 && Code_Array[i][j]<16)
		{
			temporary_string+="0";
		}
		first_byte_string=Long.toHexString(Code_Array[i][j]);
		first_byte_string=first_byte_string.trim();
		first_byte_string=temporary_string+first_byte_string;
		temporary_string="";
		seeker.seek(++temp_addr);
		Code_Array[i][(j+1)]=seeker.readByte();
		if(Code_Array[i][j+1]>= -128 && Code_Array[i][j+1]<= -1)
		{
			Code_Array[i][j+1]=Code_Array[i][j+1]+256;
		}
		else if(Code_Array[i][j+1]>=0 && Code_Array[i][j+1]<16)
		{
			temporary_string+="0";
		}
		second_byte_string=Long.toHexString(Code_Array[i][j+1]);
		second_byte_string=second_byte_string.trim();
		second_byte_string=temporary_string+second_byte_string;
		temporary_string="";

		seeker.seek(++temp_addr);
		Code_Array[i][j+2]=seeker.readByte();

		if(Code_Array[i][j+2]>= -128 && Code_Array[i][j+2]<= -1)
		{
			Code_Array[i][j+2]=Code_Array[i][j+2]+256;
		}
		else if(Code_Array[i][j+2]>=0 && Code_Array[i][j+2]<16)
		{
			temporary_string+="0";
		}
		third_byte_string=Long.toHexString(Code_Array[i][j+2]);
		third_byte_string=third_byte_string.trim();
		third_byte_string=temporary_string+third_byte_string;
		temporary_string="";
		seeker.seek(++temp_addr);
		Code_Array[i][j+3]=seeker.readByte();
		if(Code_Array[i][j+3]>= -128 && Code_Array[i][j+3]<= -1)
		{
			Code_Array[i][j+3]=Code_Array[i][j+3]+256;
		}
		else if(Code_Array[i][j+3]>=0 && Code_Array[i][j+3]<16)
		{
			temporary_string+="0";
		}
		fourth_byte_string=Long.toHexString(Code_Array[i][j+3]);
		fourth_byte_string=fourth_byte_string.trim();
		fourth_byte_string=temporary_string+fourth_byte_string;
		switch (ReadHeader.EI_DATA) 
		{
		case 1:
			finalstring=fourth_byte_string+third_byte_string+second_byte_string+first_byte_string;
			break;
		case 2:
			finalstring=first_byte_string+second_byte_string+third_byte_string+fourth_byte_string;
			break;
		default:
			break;
		}
		finalstring=finalstring.trim();
		opcodevalue=Long.parseLong(finalstring, 16);
		long Condition = (opcodevalue & 0xf0000000);
		long Condition2=Condition >>28;
		int Condition3=(int)Condition2;
		switch (Condition3) //Checking the Condition Code
		{
		case 0:
			flagstatus= "EQ";
			break;
		case 1:
			flagstatus= "NE";
			break;
		case 2:
			flagstatus= "CS";
			break;
		case 3:
			flagstatus= "CC";
			break;
		case 4:
			flagstatus= "MI";
			break;
		case 5:
			flagstatus= "PL";
			break;
		case 6:
			flagstatus= "VS";
			break;
		case 7:
			flagstatus= "VC";
			break;
		case 8:
			flagstatus= "HI";
			break;
		case 9:
			flagstatus= "LS";
			break;
		case 10:
			flagstatus= "GE";
			break;
		case 11:
			flagstatus= "LT";
			break;
		case 12:
			flagstatus= "GT";
			break;
		case 13:
			flagstatus= "LE";
			break;
		case 14:
			//chk_f_status= "AL";
			break;
		case 15:
			flagstatus= "NV";
			break;

		default:
			break;
		}
		temp =(int)(opcodevalue & 0x0c000000);
		temp = temp >> 26;
		Pnemonics = "";
		switch (temp) 
		{
		case 0:
			if ( (( (int) (opcodevalue & 0x0fc00000) >> 22 ) == 0) &&  (( (int) (opcodevalue & 0x000000f0) >> 4 ) == 9) )
			{
				//  MUL/MLA 
				int m_value=(int) (opcodevalue & 0x00300000); 
				m_value=m_value >>20;
				switch (m_value) {  
				case 0:
					Pnemonics="MUL"+flagstatus;
					registervalue1 = (int) (opcodevalue & 0x000f0000);
					registervalue1 = registervalue1 >>16;
					rd = registerValue( registervalue1);
					Pnemonics=  Pnemonics+" "+ rd;

					registervalue4 = (int) (opcodevalue & 0x0000000f);
					rm = registerValue( registervalue4);
					Pnemonics= Pnemonics+","+ rm;

					registervalue3 = (int) (opcodevalue & 0x00000f00);
					registervalue3 = registervalue3 >>8;
					rs = registerValue( registervalue3);
					Pnemonics= Pnemonics+","+ rs;
					break;

				case 1:
					Pnemonics="MUL"+flagstatus+"S";
					registervalue1 = (int) (opcodevalue & 0x000f0000);
					registervalue1 = registervalue1 >>16;
					rd = registerValue( registervalue1);
					Pnemonics= Pnemonics+" "+ rd;

					registervalue4 = (int) (opcodevalue & 0x0000000f);
					rm = registerValue( registervalue4);
					Pnemonics= Pnemonics+","+ rm;

					registervalue3 = (int) (opcodevalue & 0x00000f00);
					registervalue3 = registervalue3 >>8;
					rs = registerValue( registervalue3);
					Pnemonics= Pnemonics+","+ rs;
					break;

				case 2:
					Pnemonics="MLA"+flagstatus;
					registervalue1 = (int) (opcodevalue & 0x000f0000);
					registervalue1 = registervalue1 >>16;
					rd = registerValue( registervalue1);
					Pnemonics= Pnemonics+" "+ rd;

					registervalue4 = (int) (opcodevalue & 0x0000000f);
					rm = registerValue( registervalue4);
					Pnemonics= Pnemonics+","+ rm;

					registervalue3 = (int) (opcodevalue & 0x00000f00);
					registervalue3 = registervalue3 >>8;
					rs = registerValue( registervalue3);
					Pnemonics= Pnemonics+","+ rs;


					registervalue2 = (int) (opcodevalue & 0x0000f000);
					registervalue2 = registervalue2 >>12;
					rn = registerValue( registervalue2);
					Pnemonics= Pnemonics+","+ rn;
					break;

				case 3:
					Pnemonics="MLA"+flagstatus+"S";
					registervalue1 = (int) (opcodevalue & 0x000f0000);
					registervalue1 = registervalue1 >>16;
					rd = registerValue( registervalue1);
					Pnemonics= Pnemonics+" "+ rd;

					registervalue4 = (int) (opcodevalue & 0x0000000f);
					rm = registerValue( registervalue4);
					Pnemonics= Pnemonics+","+ rm;

					registervalue3 = (int) (opcodevalue & 0x00000f00);
					registervalue3 = registervalue3 >>8;
					rs = registerValue( registervalue3);
					Pnemonics= Pnemonics+","+ rs;

					registervalue2 = (int) (opcodevalue & 0x0000f000);
					registervalue2 = registervalue2 >>12;
					rn = registerValue( registervalue2);
					Pnemonics= Pnemonics+","+ rn;
					break;

				default:
					break;
				}
			}
			else
			{
				//MRS/MSR
				temp1=(int)(opcodevalue & 0x0f800000);
				temp1= temp1>>23;
				temp2=(int)(opcodevalue & 0x3f0000);
				temp2=temp2>>16;
				temp3=(int)(opcodevalue & 0xfff);
				if((temp1==2) && (temp2==15) && (temp3==0))
				{
					registervalue1 = (int) (opcodevalue & 0xf000);
					registervalue1 = registervalue1 >>12;
					rd = registerValue( registervalue1);
					Ps=(int)(opcodevalue & 0x400000);
					Ps=Ps>>22;

					if(Ps == 0)
					{
						Pnemonics="MRS"+flagstatus+"    "+rd+","+"CPSR";
					}
					else
					{
						Pnemonics="MRS"+flagstatus+"    "+rd+","+"SPSR";
					}
				}
				else
				{
					temp2=(int)(opcodevalue & 0x3ffff0);
					temp2=temp2>>4;
					if((temp1==2) && (temp2==0x29f00))
					{
						registervalue4 = (int) (opcodevalue & 0x0f);
						rm = registerValue( registervalue4);
						Pd=(int)(opcodevalue & 0x400000);
						Pd=Pd>>22;
						if(Pd == 0)
						{
							Pnemonics="MSR"+flagstatus+"    "+"CPSR"+","+rm;
						}
						else
						{
							Pnemonics="MSR"+flagstatus+"    "+"SPSR"+","+rm;
						}
					}
					else
					{
						temp2=(int) (opcodevalue & 0x3ff000);
						temp2=temp2 >> 12;
						Pd=(int)(opcodevalue & 0x01c00000);
						Pd=Pd>>22;
						if(temp2 == 0x28f)
						{
							temp =(int)(opcodevalue & 0x0e000000);
							temp = temp >> 25;
							switch (temp) 
							{                        
							case 0:
								if (Pd==4)
								{
									registervalue4 = (int) (opcodevalue & 0xf);
									rm = registerValue( registervalue4);
									Pnemonics = "MSR"+flagstatus+"   "+"CPSR_flg"+","+rotateOperation();
								}
								else if (Pd==5)
								{
									registervalue4 = (int) (opcodevalue & 0xf);
									rm = registerValue( registervalue4);
									Pnemonics = "MSR"+flagstatus+"   "+"SPSR_flg"+","+rotateOperation();
								}

								break;

							case 1:
								if(Pd==4)
								{
									temp1 = (int)(opcodevalue & 0xf00);
									temp1 = temp1 >> 8; //shift value
									temp2 = (int)(opcodevalue & 0xff); //immediate value
									String imm = ""+temp2;
									String imm1 = ""+temp1;
									Pnemonics="MSR"+flagstatus+"   "+"CPSR_flg"+","+"#"+imm+","+"ROR"+" #"+imm1; //Rotate Right
								}
								else if(Pd==5)
								{
									temp1 = (int)(opcodevalue & 0xf00);
									temp1 = temp1 >> 8; //shift value
									temp2 = (int)(opcodevalue & 0xff); //immediate value
									String imm = ""+temp2;
									String imm1 = ""+temp1;
									Pnemonics="MSR"+flagstatus+"   "+"SPSR_flg"+","+"#"+imm+","+"ROR"+" #"+imm1; //Rotate Right

								}
								break;
							}
						}
						else
						{
							//SWP
							temp=(int)(opcodevalue & 0xf800000);
							temp=temp >> 23;
							temp1=(int)(opcodevalue & 0x300000);
							temp1=temp1 >> 20;
							temp2 = (int)(opcodevalue & 0xff0);
							temp2 = temp2 >> 4;
							if ((temp == 2) && (temp1 == 0) && (temp2 == 9)  )
							{
								registervalue1 = (int) (opcodevalue & 0xf000);
								registervalue1 = registervalue1 >>12;
								rd = registerValue( registervalue1);
								registervalue4 = (int) (opcodevalue & 0x0000000f);
								rm = registerValue( registervalue4);
								registervalue3 = (int) (opcodevalue & 0xf0000);
								registervalue3 = registervalue3 >> 16;
								rn = registerValue( registervalue3);
								temp3 =(int)(opcodevalue & 0x400000);
								temp3 = temp3 >> 22;

								if(temp3 == 0)
								{
									Pnemonics = "SWP"+flagstatus+ rd +","+rm+","+"["+rn+"]"; 
								}else
								{
									Pnemonics = "SWP"+flagstatus+"B"+ rd +","+rm+","+"["+rn+"]";
								}
							}
							else
							{
								temp3 =(int)(opcodevalue & 0x02000000); //Immediate offset
								temp3 = temp3 >> 25;

								switch (temp3) {
								case 0:
									registervalue4 = (int) (opcodevalue & 0xf);
									rm = registerValue( registervalue4);
									Pnemonics = operandSelection()+","+rotateOperation();                            
									break;

								case 1:
									temp1 = (int)(opcodevalue & 0xf00);
									temp1 = temp1 >> 8; //shift value
									temp2 = (int)(opcodevalue & 0xff); //immediate value
									String imm = ""+temp2;
									String imm1 = ""+temp1;
									Pnemonics=operandSelection()+","+"#"+imm+","+"ROR"+" #"+imm1; //Rotate Right
									break;

								default:
									break;
								}
							}
						}
					}
				}
			}
			break;

		case 1: //LDR-STR
			registervalue2 = (int) (opcodevalue & 0xf0000);
			registervalue2 = registervalue2 >>16;
			rn = registerValue( registervalue2);
			temp4 =(int)(opcodevalue & 0x100000); //load/store bit
			temp4 = temp4 >> 20;
			switch (temp4) {
			case 0:
				Pnemonics="STR";
				break;

			case 1:
				Pnemonics="LDR";
				break;

			default:
				break;
			}

			temp4 =(int)(opcodevalue & 0x400000); //Byte/Word bit
			temp4 = temp4 >> 22;
			if (temp4 == 1)
				Pnemonics = Pnemonics + flagstatus + "B";
			else 
				Pnemonics = Pnemonics + flagstatus ;

			temp2 =(int)(opcodevalue & 0x1000000); //Pre/Post indexing bit
			temp2 = temp2 >> 24;
			switch (temp2) {
			case 1:

				break;
			case 0: //Post
			if (temp4 == 1)
			{
				Pnemonics = Pnemonics + "T";
			}
			break;
			default:
				break;
			}

			temp4 =(int)(opcodevalue & 0x800000); //Up/Down bit
			temp4 = temp4>>23;
			if (temp4 == 0)
				finalstring = "-";
			else
				finalstring = "+";

			temp4 =(int)(opcodevalue & 0xf000); //Rd
			temp4 = temp4 >> 12;
			Pnemonics = Pnemonics + " " + registerValue(temp4) + ",";

			switch (temp2) //Pre/Post indexing bit 
			{
			case 1:
				temp4 =(int)(opcodevalue & 0x02000000); //Immediate offset bit
				temp4 = temp4 >> 25;
				switch (temp4)
				{
				case 0:
					Pnemonics = Pnemonics + "["+rn+",#" + finalstring + (int)(opcodevalue & 0xfff) + "]";
					break;
				case 1:
					Pnemonics = Pnemonics + "["+rn+"," +  rotateOperation() + "]";
					break;

				default:
					break;
				}

				break;
			case 0:
				temp4 =(int)(opcodevalue & 0x02000000); //Immediate offset bit
				temp4 = temp4 >> 25;
				switch (temp4)
				{
				case 0:
					Pnemonics = Pnemonics + "[+"+rn+"],#" + finalstring + (int)(opcodevalue & 0xfff);
					break;
				case 1:
					Pnemonics = Pnemonics + "["+rn+"]," + rotateOperation();
					break;
				default:
					break;
				}    
			}
			temp1 =(int)(opcodevalue & 0x200000); //Write-Back bit
			temp1 = temp1 >> 21; //Write-Back bit
			if (temp1 == 1)
				Pnemonics = Pnemonics + "!";

			break;


		case 2:
			temp1 = (int)(opcodevalue & 0x2000000); //25th bit
			temp1 = temp1 >> 25;
			switch (temp1)
			{
			case 0: // LDM-STM
				int l_bit =(int)(opcodevalue & 0x100000); //Load/Store bit
				l_bit = l_bit >> 20;

				int p_bit = (int)(opcodevalue & 0x1000000); //Pre/Post indexing bit
				p_bit = p_bit >> 24;

				int u_bit = (int)(opcodevalue & 0x800000); //Up/Down bit
				u_bit = u_bit >> 23;

				switch (l_bit)
				{
				case 1:
					switch (p_bit)
					{
					case 0:
						switch (u_bit)
						{
						case 0:
							Pnemonics =  "LDM" + flagstatus; //+ "DA";
							break;
						case 1:
							Pnemonics =  "LDM" + flagstatus; //+ "IA";
							break;
						default:
							break;	
						}
						break;
					case 1:
						switch (u_bit)
						{
						case 0:
							Pnemonics =  "LDM" + flagstatus; // + "DB";
							break;
						case 1:
							Pnemonics =  "LDM" + flagstatus; // + "IB";
							break;
						default:
							break;
						}
						break;
					default:
						break;
					}
					break;
				case 0:
					switch (p_bit)
					{
					case 0:
						switch (u_bit)
						{
						case 0:
							Pnemonics =  "STM" + flagstatus; // + "DA";
							break;
						case 1:
							Pnemonics =  "STM" + flagstatus; // + "IA";
							break;
						default:
							break;
						}
						break;
					case 1:
						switch (u_bit)
						{
						case 0:
							Pnemonics =  "STM" + flagstatus; // + "DB";
							break;
						case 1:
							Pnemonics =  "STM" + flagstatus; // + "IB";
							break;
						default:
							break;
						}
						break;
					default:
					}
					break;
				default:
					break;
				}
				int Rn = (int)(opcodevalue & 0xf0000);
				Rn = Rn >> 16;
				Pnemonics = Pnemonics + " " + registerValue(Rn);
				temp1 =(int)(opcodevalue & 0x200000); //Write-Back bit
				temp1 = temp1 >> 21; //Write-Back bit
				if (temp1 == 1)
				{
					Pnemonics = Pnemonics + "!,";
				}
				else
				{
					Pnemonics = Pnemonics + ",";
				}
				String temp_str1 = "";
				int temp_count = 0x8000;
				Boolean array[] = new Boolean [20];
				temp1 =(int)(opcodevalue & 0xffff); //register list
				for (int count = 15; count >= 0; count--, temp_count/=2)
				{
					array [count] = false;
					temp2 = temp1 & temp_count;
					temp2 = temp2 >> count;
					if (temp2 == 1)
					{
						array [count] = true;
					}
				}
				int count = 0;
				temp_count = 0;
				if (temp1 != 0)
				{
					Pnemonics = Pnemonics + "{";
					while ( count <= 15)
					{
						temp_count = 0;
						while (count <= 15 && array[count] != false)
						{
							temp_count++;
							if (temp_count == 1)
							{
								temp_str1 = temp_str1 + registerValue(count);
							}
							count++;
						}
						if (temp_count > 1)
						{
							if (temp_count == 2)
							{
								temp_str1 = temp_str1 + "," + registerValue(count-1) + ",";
							}
							else
							{
								temp_str1 = temp_str1 + "-" + registerValue(count-1) + ",";
							}
						}
						else if (temp_count == 1)
						{
							temp_str1 = temp_str1 + ",";
						}
						count++;
					}
					int len = temp_str1.length();
					temp_str1 = temp_str1.substring(0, len-1);
					temp_str1 = temp_str1 + "}";
					Pnemonics = Pnemonics + temp_str1;
				}
				temp1 =(int)(opcodevalue & 0x400000); //S-bit
				temp1 = temp1 >> 22;
					if (temp1 == 1)
					{
						Pnemonics = Pnemonics + "^";
					}
					break;

					case 1: //B/BL
						int chk_b_status = (int)(opcodevalue & 0x01000000);
						chk_b_status=chk_b_status >>24; 
						switch (chk_b_status) 
						{
						case 0:
							int offset= (int)(opcodevalue & 0x00ffffff);
							String Offset="";
							Offset=Offset+offset;
							Pnemonics="B"+flagstatus+" "+Offset ;
							break;

						case 1:
							int offset1= (int)(opcodevalue & 0x00ffffff);
							String Offset1="";
							Offset1=Offset1+offset1;
							Pnemonics="BL"+flagstatus+" "+Offset1 ;
							break;

						default:
							break;
						}
						break;

					default:
						break;
			}
			break;
		case 3:
			temp1 = (int)(opcodevalue & 0x2000000); //25th bit
			temp1 = temp1 >> 25;
			switch (temp1)
			{
			case 0: // LDC-STC
				registervalue2 = (int) (opcodevalue & 0xf0000);
				registervalue2 = registervalue2 >>16;
				rn = registerValue( registervalue2);
				int l_bit =(int)(opcodevalue & 0x100000); //Load/Store bit
				l_bit = l_bit >> 20;
				int p_bit = (int)(opcodevalue & 0x1000000); //Pre/Post indexing bit
				p_bit = p_bit >> 24;
				int u_bit = (int)(opcodevalue & 0x800000); //Up/Down bit
				u_bit = u_bit >> 23;
				int n_bit = (int)(opcodevalue & 0x400000); //Transfer length bit
				n_bit = n_bit >> 22;
				int w_bit = (int)(opcodevalue & 0x200000); //Write back bit
				w_bit = w_bit >> 21;
				if (l_bit == 1)
				{
					Pnemonics = Pnemonics + "LDC" + flagstatus;
				}
				else
				{
					Pnemonics = Pnemonics + "STC" + flagstatus;
				}

				if (n_bit == 1)
				{
					Pnemonics = Pnemonics + "L";
				}

				temp1 = (int)(opcodevalue & 0xf00); //Coprocessor number
				temp1 = temp1 >> 8;
				Pnemonics = Pnemonics + " " + temp1 + ",";
				temp1 = (int)(opcodevalue & 0xf000); //Coprocessor register number
				temp1 = temp1 >> 12;
				Pnemonics = Pnemonics + temp1 + ",";
				u_bit = (int)(opcodevalue & 0x800000); //Up/Down bit
				u_bit = u_bit >> 23;
				String str_temp_sign = new String();
				if (u_bit == 0)
					str_temp_sign = "-";
				else
					str_temp_sign = "+";

				switch (p_bit) //Pre/Post indexing bit 
				{
				case 1:
					temp4 =(int)(opcodevalue & 0x02000000); //Immediate offset bit
					temp4 = temp4 >> 25;
					switch (temp4)
					{
					case 0:
						Pnemonics = Pnemonics + "["+rn+",#" + str_temp_sign + (int)(opcodevalue & 0xfff) + "]";
						break;
					case 1:
						Pnemonics = Pnemonics + "["+rn+"," + rotateOperation() + "]";
						break;

					default:
						break;
					}

					break;
				case 0:
					temp4 =(int)(opcodevalue & 0x02000000); //Immediate offset bit
					temp4 = temp4 >> 25;
					switch (temp4)
					{
					case 0:
						Pnemonics = Pnemonics + "["+rn+"],#" + str_temp_sign + (int)(opcodevalue & 0xfff);
						break;
					case 1:
						Pnemonics = Pnemonics + "["+rn+"]," + rotateOperation();
						break;
					default:
						break;
					}    
				}
				//temp_1 =(int)(value & 0x200000); //Write-Back bit
				//temp_1 = temp_1 >> 21; //Write-Back bit
				if (w_bit == 1)
				{
					Pnemonics = Pnemonics + "!";
				}
				break;

			case 1:
				temp=(int)(opcodevalue & 0x01000000);//24th bit
				temp= temp >> 24;
				switch (temp) //SWI
				{
				case 1:
					temp1 = (int)(opcodevalue & 0x00ffffff);
					finalstring="";
					finalstring=finalstring+temp1;
					Pnemonics = "SWI"+flagstatus+" "+"#"+finalstring;
					break;
				case 0:
					temp2 = (int)(opcodevalue & 0x10); //5th bit
					temp2 = temp2 >> 4;
					if (temp2 == 1) //MCR/MRC
					{
						// MRC/MCR
						l_bit =(int)(opcodevalue & 0x100000); //Load/Store bit
						l_bit = l_bit >> 20;

						if (l_bit == 1)
						{
							Pnemonics = Pnemonics + "MRC";
						}
						else
						{
							Pnemonics = Pnemonics + "MCR";
						}
						Pnemonics = Pnemonics + flagstatus + " ";
						temp1 = (int)(opcodevalue & 0xf00); //Coprocessor number
						temp1 = temp1 >> 8;
						Pnemonics = Pnemonics + " " + temp1 + ",";
						temp1 = (int)(opcodevalue & 0xe00000); //Coprocessor operation mode
						temp1 = temp1 >> 21;
						Pnemonics = Pnemonics + temp1 + ",";
						temp1 =(int)(opcodevalue & 0xf000); //Rd
						temp1 = temp1 >> 12;
						Pnemonics = Pnemonics + registerValue(temp1) + ",";
						temp1 =(int)(opcodevalue & 0xf0000); //CRn
						temp1 = temp1 >> 16;
						Pnemonics = Pnemonics + "c" + temp1 + ",";
						temp1 =(int)(opcodevalue & 0xf); //CRm
						Pnemonics = Pnemonics + "c" + temp1 + ",";
						temp1 =(int)(opcodevalue & 0xe0); //CP
						temp1 = temp1 >> 5;
						Pnemonics = Pnemonics + temp1;
					}
					else
					{
						// CDP
						Pnemonics = Pnemonics + "CDP";
						temp1 = (int)(opcodevalue & 0xf00); //Coprocessor number
						temp1 = temp1 >> 8;
						Pnemonics = Pnemonics + " " + temp1 + ",";
						temp1 = (int)(opcodevalue & 0xf00000); //Coprocessor operation mode
						temp1 = temp1 >> 20;
						Pnemonics = Pnemonics + temp1 + ",";
						temp1 =(int)(opcodevalue & 0xf000); //CRd
						temp1 = temp1 >> 12;
						Pnemonics = Pnemonics + registerValue(temp1) + ",";
						temp1 =(int)(opcodevalue & 0xf0000); //CRn
						temp1 = temp1 >> 16;
						Pnemonics = Pnemonics + "c" + temp1 + ",";
						temp1 =(int)(opcodevalue & 0xf); //CRm
						Pnemonics = Pnemonics + "c" + temp1 + ",";
						temp1 =(int)(opcodevalue & 0xe0); //CP
						temp1 = temp1 >> 5;
						Pnemonics = Pnemonics + temp1;
					}
					break;
				default:
				}
				break;
			default:
				break;
			}
			break;
		}

		out.write("0x" + Integer.toHexString(addr+DsmGeneration.difference));
		char[] temp_ch = new char [8];
		for(temp=0;temp<8;temp++)
		{
			temp_ch[temp] = '@';
		}
		temp = Pnemonics.indexOf(" ");
		finalstring = Pnemonics.substring(temp);
		Pnemonics. getChars(0, temp, temp_ch, 0);
		for (int count = temp; count<temp_ch.length && temp_ch[count] == '@' ; count++ )
		{
			temp_ch[temp++] = ' ';
		}
		Pnemonics = String.copyValueOf(temp_ch) + "  " + finalstring;
		if(out!=null)
		{
			String opcode= Long.toHexString(opcodevalue);
			int count=opcode.length();
			switch (count) {
			case 0:
				opcode="00000000"+opcode;
				break;
			case 1:
				opcode="0000000"+opcode;
				break;
			case 2:
				opcode="000000"+opcode;
				break;
			case 3:
				opcode="00000"+opcode;
				break;
			case 4:
				opcode="0000"+opcode;
				break;
			case 5:
				opcode="000"+opcode;
				break;
			case 6:
				opcode="00"+opcode;
				break;
			case 7:
				opcode="0"+opcode;
				break;
			case 8:

				break;

			default:
				break;
			}
			out.write("  " +"0x"+opcode+"  "+ Pnemonics);
			out.newLine();
			out.flush();
		}
	}

	
	/********************************************************************	 
	 * Parameter      - Filename
	 * Return Type    - No return
	 * Description    - Reads source_file.txt and make different .DSM files
	 *                  according the no.s of source files.
	 *********************************************************************/
	public void executableReading(String filename) throws IOException
	{
		File f;
		FileReader input =new FileReader(".source_file.txt");
		BufferedReader bufRead = new BufferedReader(input);
		String line;
		while((line = bufRead.readLine()) != null)
		{
			GetStringToken mgs= new GetStringToken(line);
			DsmGeneration.Path[DsmGeneration.NumOfSourceFiles]=mgs.myNextStringToken();
			DsmGeneration.Path = (String[])ArrayExpend.expend(DsmGeneration.Path,Array.getLength(DsmGeneration.Path)+1);
			DsmGeneration.Path[DsmGeneration.NumOfSourceFiles]=(DsmGeneration.Path[DsmGeneration.NumOfSourceFiles]).trim();
			// System.out.println(  DSM_Generation.Path[DSM_Generation.NumOfSourceFiles]);
			starting_address=mgs.myNextStringToken();
			starting_address=starting_address.trim();
			Start_Address =Integer.parseInt(starting_address);
			ending_address=mgs.myNextStringToken();
			ending_address=ending_address.trim();
			End_Address =Integer.parseInt(ending_address);
			length=DsmGeneration.Path[DsmGeneration.NumOfSourceFiles].length();
			character_position = DsmGeneration.Path[DsmGeneration.NumOfSourceFiles].lastIndexOf("\\");
			String modname = DsmGeneration.Path[DsmGeneration.NumOfSourceFiles].substring(character_position+1,length);
			length=modname.length();
			character_position=modname.lastIndexOf(".");
			modname=modname.substring(0, character_position+1);
			Projectpath=modname.concat("dsm");
			Projectpath=Projectpath.trim();
			f= new File(Projectpath); 
			if(!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			FileWriter fstream = null;
			try {
				fstream = new FileWriter(f);
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			BufferedWriter out = new BufferedWriter(fstream);



			for (int i1=1;i1<(ReadHeader.elf_shnum);i1++)
			{
				if(((ReadSection.sh_flags[i1])& 0x4)!=0)
				{
					sectionsize=ReadSection.sh_size[i1];
					seeker.seek(ReadSection.sh_offset[i1]);
					difference = ReadSection.sh_addr[i1] - ReadSection.sh_offset[i1];

					for(int m=(Start_Address-difference);m < (End_Address-difference);m+=4 )
					{

						disassembler(m , out);
					}

					break;
				}

			}
			DsmGeneration.NumOfSourceFiles++;
			fstream.close();
		}
		bufRead.close();
		input.close();
	}
	
	
	/********************************************************************	
	 * Parameter      - register number
	 * return Type    - register strings
	 * Description    - Returns the respective register strings depending 
	 *                  on the register number passed.
	 *********************************************************************/
	public String registerValue(int register)
	{
		switch (register) {
		case 0:
			registername= "R0";
			break;
		case 1:
			registername= "R1";
			break;
		case 2:
			registername= "R2";
			break;
		case 3:
			registername= "R3";
			break;
		case 4:
			registername= "R4";
			break;
		case 5:
			registername= "R5";
			break;
		case 6:
			registername= "R6";
			break;
		case 7:
			registername= "R7";
			break;
		case 8:
			registername= "R8";
			break;
		case 9:
			registername= "R9";
			break;
		case 10:
			registername= "R10";
			break;
		case 11:
			registername= "R11";
			break;
		case 12:
			registername= "R12";
			break;
		case 13:
			registername= "R13";
			break;
		case 14:
			registername= "R14";
			break;
		case 15:
			registername= "R15";
			break;

		default:
			break;
		}
		return registername;
	}
	
	
	/********************************************************************	
	 * Parameter      - No parameter
	 * Return type    - data processing instructions string
	 * Description    - Determines the data processing instructions based 
	 *                  on the opcodes.
	 *********************************************************************/
	public String operandSelection()
	{
		int temp_4 = 0;
		int r_value,r_value1 ;
		String rd,rn,operand;
		int temp3 = (int)(opcodevalue & 0x01e00000);
		temp3=temp3 >>21;
		switch (temp3) {
		case 0:
			operand = "AND";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd+","+rn;
			else
				Pnemonics = operand+flagstatus+" "+rd+","+rn;
			break;
		case 1:
			operand = "EOR";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S" + " "+rd+","+rn;
			else
				Pnemonics = operand+flagstatus + " "+rd+","+rn;
			break;
		case 2:
			operand = "SUB";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);

			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+ " " + rd+","+rn;
			else
				Pnemonics = operand+flagstatus+ " " + rd+","+rn;
			break;
		case 3:
			operand = "RSB";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd+","+rn;
			else
				Pnemonics = operand+flagstatus+" "+rd+","+rn;
			break;
		case 4:
			operand = "ADD";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd+","+rn;
			else
				Pnemonics = operand+flagstatus+" "+rd+","+rn;
			break;
		case 5:
			operand = "ADC";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd+","+rn;
			else
				Pnemonics = operand+flagstatus+" "+rd+","+rn;
			break;
		case 6:
			operand = "SBC";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd+","+rn;
			else
				Pnemonics = operand+flagstatus+" "+rd+","+rn;
			break;
		case 7:
			operand = "RSC";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd+","+rn;
			else
				Pnemonics = operand+flagstatus+" "+rd+","+rn;
			break;
		case 8:
			operand = "TST";
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rn;
			else
				Pnemonics = operand+flagstatus+" "+rn;
			break;
		case 9:
			operand = "TEQ";
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rn;
			else
				Pnemonics = operand+flagstatus+" "+rn;
			break;
		case 10:
			operand = "CMP";
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rn;
			else
				Pnemonics = operand+flagstatus+" "+rn;
			break;
		case 11:
			operand = "CMN";
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rn;
			else
				Pnemonics = operand+flagstatus+" "+rn;
			break;
		case 12:
			operand = "ORR";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd+","+rn;
			else
				Pnemonics = operand+flagstatus+" "+rd+","+rn;
			break;
		case 13:
			operand = "MOV";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd;
			else
				Pnemonics = operand+flagstatus+" "+rd; 
			break;
		case 14:
			operand = "BIC";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			r_value1 = (int) (opcodevalue & 0xf0000);
			r_value1 = r_value1 >>16;
			rn = registerValue( r_value1);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd+","+rn;
			else
				Pnemonics = operand+flagstatus+" "+rd+","+rn;
			break;
		case 15:
			operand = "MVN";
			r_value = (int) (opcodevalue & 0xf000);
			r_value = r_value >> 12;
			rd = registerValue( r_value);
			temp_4 = (int)(opcodevalue & 0x100000);
			temp_4= temp_4 >> 20;//checking 'S' bit
			if( temp_4==1)
				Pnemonics = operand+flagstatus+"S"+" "+rd;
			else
				Pnemonics = operand+flagstatus+" "+rd;
			break;

		default:
			break;
		}
		return Pnemonics;
	}
	
	
	/********************************************************************	
	 * Parameter      - No parameter
	 * Return Type    - string type shift operation.
	 * Description    - Determines the type of shift operation.
	 *********************************************************************/
	public String rotateOperation()
	{
		String operand =  new String();
		int temp5 = (int) (opcodevalue & 0x70);
		temp5 = temp5 >>4;
		int temp6 = (int) (opcodevalue & 0xf80);
		temp6=temp6>>7;
		operand = "" + temp6;
		switch (temp5) {
		case 0:
			operand="LSL"+" #"+operand;
			break;
		case 1:
			operand = "LSL";
			temp6 = temp6 & 0x1E; 
			temp6 = temp6 >> 1;
			operand = operand +" "+ registerValue(temp6);
			break;
		case 2:
			operand = "LSR"+" #"+operand;
			break;
		case 3:
			operand = "LSR";
			temp6 = temp6 & 0x1E;
			temp6 = temp6 >> 1;
			operand = operand + " " + registerValue(temp6);
			break;
		case 4:
			operand = "ASR"+" #"+operand;
			break;
		case 5:
			operand = "ASR";
			temp6 = temp6 & 0x1E;
			temp6 = temp6 >> 1;
			operand = operand +" "+ registerValue(temp6);
			break;
		case 6:
			operand = "ROR"+" #"+operand;
			break;
		case 7:
			operand = "ROR";
			temp6 = temp6 & 0x1E;
			temp6 = temp6 >> 1;
			operand = operand +" "+ registerValue(temp6);
			break;

		default:
			break;
		}
		return (registerValue(((int)(opcodevalue & 0xf))) + "," + operand);
	}

}
