/*******************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: MxmGeneration
 * @version: 1.0
 * Date: June 2009 
 * @author Santanu Guchhait
 * Description: Required for mixed mode generation of both ELF object file and
 * source file.
 *******************************************************************************/

package elf.disassembly;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import elf.internal.*;



public class MxmGeneration 
{   
	String Path=new String();
	int start_line,start_address,valuecheck=0;
	static int linecounter=1;
	public RandomAccessFile seeker = null;

	
	/********************************************************************
	 * @param filename	 
	 * Description    - Constructor for the class MXM_Generation.
	 *********************************************************************/
	public MxmGeneration(String filename) throws IOException 
	{
		seeker = new RandomAccessFile(filename, "r");
	}
	

	/********************************************************************	
	 * Parameter      - Filename
	 * Return Type    - No return
	 * Description    - Reads debug_line.txt file to 
	 *                  generate mixed mode of both ELF object file and
	 *                  source file. 
	 *********************************************************************/
	public void mxm_Generation (String filename) throws IOException
	{
		File file_debugline;
		FileReader input_debugline =new FileReader(".debug_line.txt");
		BufferedReader bufRead_debugline = new BufferedReader(input_debugline);
		String line_debugline = new String();
		int noSourceFiles = DsmGeneration.NumOfSourceFiles;
		while((line_debugline = bufRead_debugline.readLine()) != null )
		{
			GetStringToken mgs= new GetStringToken(line_debugline);
			mgs.myNextStringToken();
			Path = DsmGeneration.Path[DsmGeneration.NumOfSourceFiles - noSourceFiles];
			Path=Path.trim();
			int len=Path.length();
			int chrtemp = Path.lastIndexOf("\\");
			String modname = Path.substring(chrtemp+1,len);
			FileReader input_c =new FileReader(Path);
			BufferedReader bufRead_c = new BufferedReader(input_c);
			len=modname.length();
			chrtemp=modname.lastIndexOf(".");
			modname=modname.substring(0, chrtemp+1);
			String Projectpath=modname.concat("mxm");
			Projectpath=Projectpath.trim();
			file_debugline= new File(Projectpath); 
			if(!file_debugline.exists()){
				try {
					file_debugline.createNewFile();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			FileWriter fstream = null;
			try {
				fstream = new FileWriter(file_debugline);
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			BufferedWriter out = new BufferedWriter(fstream);
			DisassemblyData disdata = new DisassemblyData();
			int i;
			do
			{   
				String str = new String ();
				String str1 = new String();
				str = mgs.myNextStringToken();
				str1 = mgs.myNextStringToken();
				if (str != null || str1 != null )
				{
					start_address=Integer.parseInt((str).trim());
					start_line=Integer.parseInt((str1).trim());
					if(start_line >= valuecheck)
					{
						disdata.setDisassemblyData(start_address, start_line);
					}
				}
				valuecheck = start_line;
			}
			while(mgs.myNextStringToken() != null);
			int j = 0;
			DsmGeneration disassm_obj = new DsmGeneration(filename);
			for( i=linecounter;j < (disdata.index -1) && i<=disdata.disassemblyObject[j].StartLine; i++)
			{
				String read_temp = new String();
				read_temp = bufRead_c.readLine();
				{
					out.write(read_temp);
					out.newLine();
					out.flush();
				}

				if (i == disdata.disassemblyObject[j].StartLine)
				{
					int k, l;
					for (l = j,
							k = (disdata.disassemblyObject[l].StartAddress - DsmGeneration.difference);
					l < (disdata.index - 1) &&
					k < ((disdata.disassemblyObject[l+1].StartAddress-3)- DsmGeneration.difference);
					k += 4
					)
					{

						disassm_obj.disassembler (k, out);
					}
					j++;
				}

			}

			noSourceFiles--;
			valuecheck = 0;
			bufRead_c.close();
			out.close();
			fstream.close();
		}
		bufRead_debugline.close();
		input_debugline.close();
	}
}







