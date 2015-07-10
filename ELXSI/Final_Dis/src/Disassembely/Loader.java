package Disassembely;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Data_structure.*;


 
 
public class Loader {
	

	
	int File_Record[][]=new int [1000][1000];
	int  sample_array[]=new int[65534];
	int rec_length[]=new int[65534];
	int i1 = 0,i2=0,m=0,n=0,i=0,k=0,j=0,r=0;
    int l=0;
    int ln=0;
    int x=0;
    int z;
    char buffer1[][]=new char[500][500];
    char currfun[]=new char[500];
    char procname[]=new char[500];
    char lastlabel[]=new char[500];
    char temp_procname[]={' '};
    char lib_char_array[]={'L','I','B'};
    char libraryArray[]={'L','I','B','R','A','R','Y'};
    char const_char_array[]={'C','O','N','S','T'};        
    char C_extn_array[]={'.','c'};
    char asm_extn_array[]={'.','a','s','m'};
    char a51_extn_array[]={'.','a','5','1'};
    int char_position[]=new int[500];
    int actmodule=-1;
    int Validfile;
    int Code_base_Address=0;
	int reclen;
    Modinfo ptrmod[]= new Modinfo[50];
	int lastwasasmmodule;
	public static String absFilepath;
	public static String projectPath,lineInfoFilePath;	
	public long XData64B_Addr;
	char search[]=new char[500] ;
	char[] search_asm=new char[500];
	char[] search_a51=new char[500];
	char temp_char_array[]=new char[500] ;
	Modinfo pm;
	char CodeAddr[]=new char[65536];
	public String asmstr="";
	String lbl=new String();
	public  Proc funcTemp=new Proc();
	public  static Proc Libtemp=new Proc();
	boolean flag=false, flag2=false;
	FileWriter fstream2,lineInfoFW;
	BufferedWriter lineInfoBW = null;
	BufferedWriter out3 = null;
	static String HLCode=null;
	static String lastAddress=null;
	static int brkID=1;
	
	
	Proc pp=null;
	Proc libp=null;
	int mobext;
	int obext;
	int typidx;
	int ptd=0;
	public Labeladdr plba=null;
	public LabelName plbn=null;
	public Labeladdr ptra=plba;
	public int Saddr = 0;
	public int Eaddr = 0;
	
	short offset;
	
	String buff=new String();
	public int asci=0;
	public char cp[]=new char[500];
	
	Labeladdr LblAd=new Labeladdr();
	char bp[];
	char hp[];
	char sp[];
	
	
	public static  String SBit510[]={
		"P0.0","P0.1","P0.2","P0.3","P0.4","P0.5","P0.6","P0.7",
		"IT0","IE0","IT1","IE1","TR0","TF0","TR1","TF1",
		"P1.0","P1.1","P1.2","P1.3","P1.4","P1.5","P1.6","P1.7",
		"RI","TI","RB8","TB8","REN","SM2","SM1","SM0",
		"P2.0","P2.1","P2.2","P2.3","P2.4","P2.5","P2.6","P2.7",
		"EPW","ET0","EX1","ET1","ERN","ET2","A8H.6","EA",
		"RXD","TXD","INT0","INT1","T0","T1","WR","RD",
		"PX0","PT00","PX1","PT1","PS","PT0","B8H.6","B8H.7",
		"C0H.0","C0H.1","C0H.2","C0H.3","C0H.4","C0H.5","C0H.6","C0H.7",
		"C8H.0","C/T2","TR2","C8H.3","C8H.4","C8H.5","C8H.6","TF2",
		"P","F1","OV","RS0","RS1","F0","AC","CY",
		"RWT","EWT","WTRF","WDIF","PFI","EPFI","POR","D8H.7",
		"ACC.0","ACC.1","ACC.2","ACC.3","ACC.4","ACC.5","ACC.6","ACC.7",
		"P4.0","P4.1","P4.2","P4.3","P4.4","P4.5","P4.6","P4.7",
		"B.0","B.1","B.2","B.3","B.4","B.5","B.6","B.7",
		"P5.0","P5.1","P5.2","P5.3","P5.4","P5.5","P5.6","P5.7"
	};

	public static  String SReg510[]={
		"P0","SP","DPL","DPH","DPL1","DPH1","DPS","PCON",
		"TCON","TMOD","TL0","TL1","TH0","TH1","CKCON","SFLG",
		"P1","MEMCTRL","92H","93H","MEMSEL","95H","96H","97H",
		"SCON","SBUF","9AH","9BH","9CH","9DH","CRCD","CRCC",
		"P2","A1H","A2H","A3H","DCR","DOM","DSR","DDR",
		"IE","A9H","RNGD","RNGC","ACH","ADH","AEH","AFH",
		"P3","B1H","B2H","B3H","B4H","B5H","B6H","B7H",
		"IP","B9H","RSAD","RSAOP","RSAS","RSAK","BEH","BFH",
		"C0H","C1H","C2H","C3H","PMR","C5H","C6H","TA",
		"T0CON","T0MOD","RCAP2L","RCAP2H","TL0","TH0","CEH","CFH",
		"PSW","D1H","D2H","D3H","D4H","D5H","D6H","D7H",
		"WDCON","D9H","DAH","DBH","DCH","DDH","DEH","DFH",
		"ACC","E1H","E2H","E3H","E4H","E5H","E6H","E7H",
		"EIE","E9H","EAH","EBH","ECH","EDH","EEH","EFH",
		"B","F1H","F2H","F3H","F4H","F5H","F6H","F7H",
		"EIP","F9H","FAH","FBH","FLROM","FDH","FEH","FFH"
	};
	
	//@SuppressWarnings("static-access")
	public int AddLibraryModuleInfo(){
		
	Modinfo newmod=new Modinfo();
	for(int count=0;count<7;count++)
	{
		newmod.modname[count]=libraryArray[count];
	}
	
	newmod.temp_Srcpath=absFilepath.toCharArray();
	
	int c=0;
	for (int o=0;o<newmod.temp_Srcpath.length;o++)
	{
		if(newmod.temp_Srcpath[o]=='\\')
		{
			char_position[c]=o;
			c++;
					
		}
				
	}
	c--;
	
	for(int o=0;o<=char_position[c];o++)
	{
		newmod.Srcpath[o]=newmod.temp_Srcpath[o];
		temp_char_array[o]=newmod.temp_Srcpath[o];
		
	}
	
	newmod.ModID=Add(newmod);
	newmod.highaddr=0x0000;
	newmod.lowaddr=0xFFFF;
	
    newmod.Linfo = null;
	x=char_position[c]+1;
	return newmod.ModID;
	
}


	

	
	public int Addmoduleinfo()
	{
		Modinfo newmod=new Modinfo();
		
		for(int o=0;o<ln;o++)
		{
			newmod.modname[o]=buffer1[r][o];
			
		}
		
		newmod.temp_Srcpath=absFilepath.toCharArray();
		
		int c=0;
		for (int o=0;o<newmod.temp_Srcpath.length;o++)
		{
			if(newmod.temp_Srcpath[o]=='\\')
			{
			    char_position[c]=o;
				c++;
			}	
			
			
		}
		c--;
		newmod.srcpath_length=char_position[c];
		for(int o=0;o<=newmod.srcpath_length ;o++)
		{
			newmod.Srcpath[o]=newmod.temp_Srcpath[o];
			temp_char_array[o]=newmod.temp_Srcpath[o];
			
		}
		
		newmod.ModID=Add(newmod);
		newmod.highaddr=0x0000;
		newmod.lowaddr=0xFFFF;
		newmod.Finfo = null;
		return newmod.ModID;
		
	}
	 
	
	


	public int Add(Modinfo newmod) {
	   
		int x=GetModuleCount();
		if(x==0)z = 0 ;
		else z=x;
		ptrmod[z] = newmod;
		return z++ ; 
		
	}




	public int GetModuleCount() {
			
			int i = 0 ;
			while( ptrmod[i] !=null ) 
				i++ ;
			return i ;
		
		
	}
	
	//@SuppressWarnings("static-access")
	public Proc Addlibconst(char[] procname,long startaddr, long endaddr,int o) 
	{
		Modinfo pm = GetAt(0) ;
		Proc curr = pm.Linfo ;
		Proc temp = new Proc() ;
		Proc prev = new Proc();
		
		while(curr != null )
		{
			int c=0;
			for(int s=0;s<o;s++)
			{ 
				if(curr.Funname[s]==procname[s])
				{
					c++;
				}
			}
			if(c==0)
				
			{
				if(!((endaddr <= curr.StartAddr) || (startaddr >= curr.EndAddr)))
						{
				          return curr;	//return the proc addr
						}
			
			}
			curr=curr.next ;
		}

		
		temp.StartAddr = startaddr ;
		temp.EndAddr = endaddr ;
		
		temp.next = null;
		for(int s=0;s<o;s++)
		{
			temp.Funname[s]=procname[s];
		}

		curr = pm.Linfo ;
	// insert at head 
	
	if(startaddr <= pm.lowaddr )
	{
		pm.lowaddr = startaddr ;
		temp.next = pm.Linfo ;
		pm.Linfo = temp ;
		if(endaddr > pm.highaddr)
			pm.highaddr = endaddr ;
		
		
		return temp;	//return the proc addr 
	}

	curr = pm.Linfo ;		//store the head position.
	
	// insert at tail
	
	if(endaddr >= pm.highaddr)
	{
		pm.highaddr=endaddr ;
		pm.Linfo=curr ;
		if (pm.Linfo==null )
		{
			pm.Linfo=temp ;
			return temp ;
		}
		while(pm.Linfo.next !=null )
			pm.Linfo=pm.Linfo.next ;
		pm.Linfo.next = temp;
		pm.Linfo=curr ;
		//Insert at tail.
		
		
		return temp;	//return the proc addr
	}

	///////////////////////////////////////////////////////

	pm.Linfo=curr ;		//Get the Head position
	
	//InsertBefore
	
	prev=null ;

	while (pm.Linfo != null )
	{
		if(pm.Linfo.StartAddr > startaddr )
		{
			
			if (prev==null) 
			{
				temp.next = pm.Linfo ;
				pm.Linfo= temp ;
			}
			else
			{
				temp.next = pm.Linfo ;
				prev.next=temp ;
			}
			pm.Linfo=curr ;
			return temp;	//return the proc addr.
		}
		prev = pm.Linfo ;
		pm.Linfo=pm.Linfo.next ;
		
	}
	
	pm.Linfo=curr ;				//Get the  head position

	//InsertAfter

	prev =null ;

	while (pm.Linfo.next != null )
	{									
		if(startaddr > pm.Linfo.EndAddr )
		{
			temp.next = pm.Linfo.next ;
			pm.Linfo.next = temp ;
			pm.Linfo=curr ;
			return temp ;
		}
		pm.Linfo=pm.Linfo.next ;
		
	}
	
	return null ;
		
	}
    
	


	public static void main(String[] args) throws IOException {
		String absFilePath=new String();
		for(int i=0;i<args.length;i++){
			absFilePath += args[i] + " ";
		}	
		absFilepath=absFilePath;

		int k=0;
		k=absFilepath.lastIndexOf("\\");
		projectPath=absFilepath.substring(0,k+1);
	    Loader a=new Loader();
		//a.load();
		
		
		try {
			a.load();
			a.disassemblyCreation();
			a.loadMxm();
			System.out.println("GENERATING DISASSEMBLY FILE(S) SUCCESSFULLY.");
		} catch (Exception e) {
			System.out.println("GENERATING DISASSEMBLY FILE FAILED.");
			System.exit(1);
		}
		
	}
	
	
	
public void loadMxm() throws IOException {
		
		int chrtemp ;
		
		Addrline temp= null ;
		
		int Saddr,Eaddr,Naddr=0;
		Addrline prevtemp = null;

		int no ;
		String temp_str = null;
		int Slineno;

		
		Modinfo pf=new Modinfo();
		int index=0;
		index=absFilepath.lastIndexOf("\\");
		projectPath=absFilepath.substring(0,index+1);
		
		lineInfoFilePath=projectPath.concat(".LineInfo.txt");
		File lineInfoFile=new File(lineInfoFilePath);
	      try {			
			lineInfoFile.createNewFile();
		} catch (Exception e) {			
		}
		
		try {			
			lineInfoFW = new FileWriter(lineInfoFile,true);
			lineInfoBW = new BufferedWriter(lineInfoFW);
		} catch (Exception e) {}
		
		
		int NumOfModules = GetModuleCount() ;	//Gives the number of modules in the project.
		int i = 1 ;
		int cnt=1;
		long mxdlnnum;
		while ( i < NumOfModules )
		{
			pf=GetAt(i++) ;						//Actual node starts from here..
			
		 chrtemp= Loader.absFilepath.lastIndexOf("\\");
   		 Loader.projectPath=Loader.absFilepath.substring(0,chrtemp+1);
   		 int x11=0;
   		 while(pf.modname[x11] != '\0')
   		 {
   			 x11++;
   		 }
   		 String modname1=new String(pf.modname);
   		 String moduleNameOnly=modname1.substring(0,x11);
   		 
   		 Loader.projectPath=Loader.projectPath.concat(moduleNameOnly);
   		 Loader.projectPath=Loader.projectPath.concat(".MXM");
   		 
   	     File mxmFile=new File(projectPath);
         if(!mxmFile.exists()){
	         try {
				mxmFile.createNewFile();
			} catch (Exception e) {			
			}
	     }
	     FileWriter mxmFW = null;
	     BufferedWriter mxmBW=null;
		 try {
				mxmFW = new FileWriter(mxmFile);
			} catch (Exception e1) {				
			}
		    mxmBW = new BufferedWriter(mxmFW);
         		 
		 
		    		    
		/*   
		    int x_temp=0;
		 while(pf.Srcpath[x_temp] != '\0')
		 {
			 x_temp++;
		 }
		 String srcpath_temp=new String(pf.Srcpath);
		 =srcpath_temp.substring(0,x_temp-1)+"c";
		 String srcpath_temp2=srcpath_temp.substring(0,x_temp-1)+"DSM";
		// System.out.println(projectPath);
		 */
		 
		 
		 int indexing = projectPath.lastIndexOf("\\");
		 String absoluteProjectPath = projectPath.substring(0, indexing);
		 indexing = absoluteProjectPath.lastIndexOf("\\");
		 absoluteProjectPath = absoluteProjectPath.substring(0, indexing);
		 absoluteProjectPath = absoluteProjectPath.replaceAll("\\\\", "\\\\\\\\");
		 String sourceFilePath = absoluteProjectPath + "\\\\.sourceFile.txt";
		 String srcPathName=new String();
		 try{
			 FileReader srcFReader = new FileReader(sourceFilePath);
			 BufferedReader srcBReader = new BufferedReader(srcFReader);
			 String fileReadLine = new String();
			 while((fileReadLine=srcBReader.readLine())!=null){
				 if(fileReadLine.toUpperCase().contains(moduleNameOnly.toUpperCase())){
					 srcPathName=fileReadLine;
					 break;
				 }else
					 continue;
			 }
			 
		 }catch(Exception e){}	 
		 
   		FileReader srcFileReader = null;
   		BufferedReader srcBufferedReader = null;
		try {
			srcFileReader = new FileReader(srcPathName);
			srcBufferedReader = new BufferedReader(srcFileReader);
		} catch (Exception e1) {			
		}		
            
			temp = pf.Addlinfo ;
			while(temp.next != null ){
				temp = temp.next ;
				}
			try{
				temp.prev.next=null;
				temp=temp.prev;
			}catch(Exception e){}
			
			cnt=1;
			mxdlnnum=0;
				
			
			Naddr = 0;
			//Printing mxm file
			while (temp!=null)
			{
				Eaddr = 0;
				if(temp.prev !=null)
				{
					prevtemp = temp.prev;
					Eaddr = (int) prevtemp.addr;
				}
				else
				{
					if(pf.mxm_Finfo!=null)
						Eaddr =  (int) pf.mxm_Finfo.EndAddr;
					else
						Eaddr =(int) (temp.addr+1);
				}
				Saddr = (int) temp.addr;
				Slineno = (int) temp.lineno;
				while (cnt<=Slineno)
				{
					try {
						temp_str=srcBufferedReader.readLine();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					try {
						//HL Code 1
						mxmBW.write(" "+temp_str);
						
						
						if(lastAddress!=null && !flag2){
							lineInfoBW.write(lastAddress + " $ " + brkID);
							lineInfoBW.newLine();
							lineInfoBW.flush();
							brkID++;
							flag2=true;
						}						
						HLCode = temp_str;
						flag=false;
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					
						try {
							mxmBW.newLine();
							mxmBW.flush();
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						mxdlnnum++;
					    cnt++;
					
				}
				temp.m_lineNum = mxdlnnum;
				temp.eaddr = Eaddr;
				if(pf.mxm_Finfo!=null)
				{
					if(pf.mxm_Finfo.EndAddr == Saddr )
					{
						no=DisAssemble(Saddr) ;
						if(Naddr != Saddr){
							try {
								//out_mxm.write(Integer.toHexString(Saddr)+"        "+asmstr) ;
								char temp1[]=new char[10];
		    			        String p_saddr=Integer.toHexString(Saddr).toUpperCase();
		    			        int w=p_saddr.length();
								int e=0;
								while(w>0){
									temp1[e]=p_saddr.charAt(e);
									e++;
									w--;
								}
								
								
								if(temp1[1]=='\0'){
									p_saddr="0x000"+p_saddr;
								}
								else if(temp1[2]=='\0'){
									p_saddr="0x00"+p_saddr;
								}
								else if(temp1[3]=='\0'){
									p_saddr="0x0"+p_saddr;
								}
								else
								p_saddr="0x"+p_saddr;
									
								//LL Code 1
								mxmBW.write("\t"+p_saddr+"        "+asmstr) ;								
								mxmBW.newLine();
								lastAddress = p_saddr;
								
								if(!flag && !HLCode.trim().equalsIgnoreCase("")){						
									lineInfoBW.write(moduleNameOnly+".C"+" $ "+
											Slineno+ " $ "+ HLCode.trim() + " $ " + p_saddr + " $ ");
									
									lineInfoBW.flush();
									flag=true; flag2=false;
								}
							} catch (IOException e) {
								
								e.printStackTrace();
							}
						}
						Naddr = Saddr;
						Saddr+=no;
						mxmBW.flush();
                       
						pf.mxm_Finfo = pf.mxm_Finfo.next ;
						
						
						mxdlnnum++;
					}
				}
				while(Saddr < Eaddr )
				{
					no=DisAssemble(Saddr) ;
					
					if(Naddr != Saddr){
						try {
							//out_mxm.write(Integer.toHexString(Saddr)+"        "+asmstr) ;
							char temp1[]=new char[10];
	    			        String p_saddr=Integer.toHexString(Saddr).toUpperCase();
	    			        int w=p_saddr.length();
							int e=0;
							while(w>0){
								temp1[e]=p_saddr.charAt(e);
								e++;
								w--;
							}
							
							
							if(temp1[1]=='\0'){
								p_saddr="0x000"+p_saddr;
							}
							else if(temp1[2]=='\0'){
								p_saddr="0x00"+p_saddr;
							}
							else if(temp1[3]=='\0'){
								p_saddr="0x0"+p_saddr;
							}
							else
							p_saddr="0x"+p_saddr;
							
							
							//LL Code 2
							mxmBW.write("\t"+p_saddr+"        "+asmstr) ;
							mxmBW.newLine();
							mxmBW.flush();
							lastAddress = p_saddr;
							if(!flag && !HLCode.trim().equalsIgnoreCase("")){								
								lineInfoBW.write(moduleNameOnly+".C"+" $ "+Slineno+ " $ "+
										HLCode.trim() + " $ " + p_saddr + " $ ");
								
								lineInfoBW.flush();
								flag=true; flag2=false;
							}
						} catch (IOException e) {
							
							e.printStackTrace();
						}
					}
					Saddr+=no;
					mxdlnnum++;
				}
				
				temp=temp.prev;

			}
			if(srcBufferedReader!=null){
				while(srcBufferedReader.readLine()!=null)
				{
					try {
						//Unknown code (HL Code)
						mxmBW.write(srcBufferedReader.readLine()) ;
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					cnt++;
				}
			}
			
		}
		if(lastAddress!=null && !flag2){
			lineInfoBW.write(lastAddress + " $ " + brkID);
			lineInfoBW.newLine();
			lineInfoBW.flush();
			brkID++;
			flag2=true;
		}			
	}


		
	public Modinfo GetAt(int i)
	{
		return ptrmod[i];
	}
	
	
public Proc addProctolist(Modinfo pm,int r,int ln) {
	
	
	Proc curr=pm.Finfo;
	Proc temp =new Proc();
	temp.StartAddr =0xFFFF;
	temp.EndAddr = 0;
	

	for (int i = 0; i < ln; i++) {

		temp.Funname[i]=buffer1[r][i];

	}

	temp.next = null;
	
	
	pm.Finfo=curr ;
	pm.mxm_Finfo=curr;
	
	//System.out.println(temp.StartAddr);
	if (pm.Finfo==null&&pm.mxm_Finfo==null )
	{
		pm.Finfo=temp ;
		pm.mxm_Finfo=temp;
		return temp ;
	}
	while(pm.Finfo.next !=null&&pm.mxm_Finfo!=null ){
		pm.Finfo=pm.Finfo.next ;
	    pm.mxm_Finfo=pm.mxm_Finfo.next;
	}
		pm.Finfo.next = temp;
		pm.mxm_Finfo=temp;
		pm.Finfo=curr ;
		pm.mxm_Finfo=curr;
	
	return temp;
	
	
}
	
		
	public void load()
	{
		File file1=new File(absFilepath);
	   // System.out.println(file1.length());
	    int total_length=0;
	    total_length=(int) file1.length();
	    FileInputStream fis1 = null;
	      
		try 
		{
			
			fis1 = new FileInputStream(file1);
		
		} 
		catch (FileNotFoundException e)
		{
		
			e.printStackTrace();

		}
		
				while (i2 != -1) 
	          
	                 {
	        	          	        	         
	    	             try {
							i2 =  fis1.read();
						} catch (IOException e) {
						
							e.printStackTrace();
						}
	    	             
	                     sample_array[i]=i2;
	                     i++;
	                     
	                     

	                }
	            	                                     
	             i=0;
	             k=0;
	             m=0;
	          while(total_length>0)
	        	  
	          {
	        	  
	        	  
	        	    int temp_var=sample_array[i+2]<<8;
	        	  	l=temp_var|sample_array[i+1];
	        	  	total_length -=(l+3);
	        	  	
	        	    rec_length[m]=l+3;
	        	  	m++;
	        	  	for(j=0;j<l+3;j++)
	        	  	{
        	   			File_Record[k][j]=sample_array[i];
        	   		    i++;
	        	  	}
	        	  	
	        	  	k++;
	        	  
	          }
	          
	          /*m=0;
	          for(l=0;l<k;l++)
	          {
	        	 
				for(n=0;n<rec_length[m];n++)
				{
					System.out.print(File_Record[l][n]+" ");
				}
	        	  m++;
	        	  System.out.println();
	          }*/
			i1=0;
			AddLibraryModuleInfo();
	        do
	        {
	        	
	        	int rectype=File_Record[i1][0];
	        	switch(rectype)
	        	{
	        	case 06: //code
	        		
	        	    Validfile=01;
	        	    
	        	    reclen=File_Record[i1][2]<<8;
	        	    reclen=reclen|File_Record[i1][1];
	        	    reclen-=4;
	        		
	        		int offset;
	        		int temp_var;
	        		temp_var=File_Record[i1][5]<<8;
	        		offset=temp_var|File_Record[i1][4];
	        		
	        		if(actmodule!=-1 && pp!=null)
	        		{
	        			
	        				if(offset < pp.StartAddr)
	        				{
	        					pp.StartAddr=offset;
	        				}
	        				if(offset < pm.lowaddr)
	        				{
	        					pm.lowaddr=offset;
	        				}
	        				if((offset+reclen-1) > pp.EndAddr)
	        				{
	        					pp.EndAddr=(offset+reclen-1);
	        				}
	        			    if((offset+reclen-1) > pm.highaddr)
	        			    {
	        			    	pm.highaddr=(offset+reclen-1);
	        			    }
	        			    lastwasasmmodule=0;
	        			
	        			
	        		}
	        		else
	        		{
	        			if (actmodule==-1)
	        				
	        			{
	        	
							if(lastlabel[0]==temp_procname[0] && lastwasasmmodule==0)
							{
								
								 pm=GetAt(0);
								 int s=3;
	            				 libp= Addlibconst(lib_char_array,offset,offset+reclen-1,s);
							
							}
							else if(lastlabel[0]!=temp_procname[0] && lastwasasmmodule==0)
							{
								
									pm=GetAt(0);
									int ll=1;
									libp=Addlibconst(lastlabel,offset,offset+reclen-1,ll);
								
									
							}
							else
							{
								pm=GetAt(lastwasasmmodule);
								int s=3;
								Addlibconst(lib_char_array,offset,offset+reclen-1,s);
								
							}
						}
	        			
	        			else
	        			{
	        				pm=GetAt(0);
	        				if(lastlabel[0]!=temp_procname[0])
	        				{
	        					libp=Addlibconst(lastlabel, offset, offset+reclen-1,ln);
	        					lastlabel[0]=temp_procname[0];
	        					
	        				}
	        				else
	        				{
	        					int cc=5;
	        					libp=Addlibconst(const_char_array, offset,offset+reclen-1,cc);
	        					
	        				}
	        				
	        			}
	        		}
	        		int temp_array[]=new int[reclen];
	        		int z=0;
	        		int k=6;
	        		int reclen1=reclen;
	        		while(reclen1!=0)
	        		{
	        			temp_array[z]=File_Record[i1][k];
	        			z++;
	        			k++;
	        			reclen1--;
	        		}
	        		
	        		WriteMemBlock(  offset,temp_array,reclen) ;
	        		
	        	break;
	        	
	        	case 32:
	        		Validfile=01;
	        		
	        		break;
	 
	        	case 36:
	        		Validfile=01;
	        		
	        		break;
	        	
	        	case 34:
	        		Validfile=01;
	        		mobext=1;
	        		obext=1;
	        		GetAt(actmodule).ObExt=1;
	        		int ln1;
	        		int j=0;
	        		j++;
	        		ln1=File_Record[i1][j];
	        		
	        		int deftyp;
	        		j+=2;
	        		deftyp=File_Record[i1][j];
	        		j++;ln1-=2;
	        		if(deftyp==3)
	        		{
	        			while(ln1>0){
	        				
	        				int segid;
	        				segid=File_Record[i1][j];
	        				j++;
	        				long addr;
	        				int temp_var3=File_Record[i1][j+1]<<8;
	        				addr=File_Record[i1][j]|temp_var3;
	        				j+=2;
	        				temp_var3=File_Record[i1][j+1]<<8;
	        				int lineno=File_Record[i1][j]|temp_var3;
	        				Adddbginfo(addr,lineno,actmodule,currfun);
	        				j+=2;
	        				ln1=ln1-5;
	        			}
	        		}
	        		else if(deftyp<=1)
	        		{
	        			while(ln1>0){
	        			int segid;
	        			segid=File_Record[i1][j];
	        			j++;
	        			
						int syminfo=File_Record[i1][j];
						j++;
	        			int temp_var1=File_Record[i1][j+1]<<8;
						long addr=File_Record[i1][j]|temp_var1;
						
	        			j+=2;
	        			j++;
	        			int ln2;
	        			ln2=File_Record[i1][j];
	        			j++;
	        			for (int l = 0; l < ln2; l++) {
		        			buffer1[r][l]=(char) File_Record[i1][j+l];
							
						}
	        			
	        			
	        			
	        			j+=ln2;
	        			buffer1[r][ln2]=0;
	        			if(actmodule>=1)
	        			{
	        				Adddbglblinfo(addr,buffer1,deftyp,syminfo,actmodule,pp,ptd,ln2);
	        			}
	        			else if(libp!=null)
	        			{
	        				Adddbglblinfo(addr, buffer1, deftyp, syminfo, 0, libp, ptd,ln2);
	        				
	        			}
	        			
	        			ln1=ln1-6-ln2;
	        			
	        			}
	        		r++;
	        		}
	        		else if(deftyp==2)
	        		{
	        			while(ln1>0){
	        				
	        			int segid;
	        			segid=File_Record[i1][j];
	        			
	        			j++;
	        			int seginfo;
	        			seginfo=File_Record[i1][j];
	        			j++;
	        			long addr;
	        			int temp_var2=File_Record[i1][j+1];
	        			addr=File_Record[i1][j]|temp_var2;
	        			
	        			j+=2;
	        			j++;
	        			int ln3;
	        			ln3=File_Record[i1][j];
	        			
	        			j++;
	        			for (int l = 0; l < ln3; l++) 
	        			{
		        			buffer1[r][l]=(char) File_Record[i1][j+l];
							
						}
	        			 			
	        			j+=ln3;
	        			buffer1[r][ln3]=0;
	        			ln1-=ln3+6;
	        			seginfo &=0x07;
	        			if(seginfo==0)
	        			{
	        				Adddbglblinfo(addr, buffer1, deftyp,0, actmodule, null, ptd,ln3);
	        			}
	        			                      
	        			j++;
	        			ln1=ln1-6-ln3;
	        			
	        			}
	        			r++;
	        		}
	        		
	        	break;
	        		
	        		
	        		
	        	
	        	case 16:
	        		Validfile=01;
	        		switch(File_Record[i1][3])
	        		{
	        		case 0:
	        		{
	        			 ln=File_Record[i1][4];
	        			for(int o=0;o<ln;o++)
	        			{
	        				buffer1[r][o]=(char) File_Record[i1][o+5];
	        				
	        			}
	        			buffer1[r][ln]=0;
	        			if (buffer1[r][0]!='?' || buffer1[r][1]!='C') 
	        			{
							
						
	        			actmodule=Addmoduleinfo();
	        			lastwasasmmodule=actmodule;
	        			lastlabel[0]=temp_procname[0];
	        			lastlabel[1]=0;
	        			
	        			System.arraycopy(temp_char_array, 0, search, 0,GetAt(actmodule).srcpath_length+1 );
	        			System.arraycopy(temp_char_array, 0, search_asm, 0,GetAt(actmodule).srcpath_length+1 );
	        			System.arraycopy(temp_char_array, 0, search_a51, 0,GetAt(actmodule).srcpath_length+1 );
	        			
	        			x=GetAt(actmodule).srcpath_length+1;
	        			int o=0;
	        			int temp_ln;
	        			temp_ln=ln;
	        			while(temp_ln>0)
	        			{
	        				search[x]=buffer1[r][o];
	        				x++;
	        				o++;
	        				temp_ln--;
	        			}
	        			search[x]=C_extn_array[0];
	        			x++;
	        			search[x]=C_extn_array[1];
	        			int c_extn_length=x;
	        			int v=0;
	        			
	        			while(search[v]!='\0')
	        			{
	        				v++;
	        			}
	        			
	        			String Temp_filename1=new String(search);
					    String Final_Filename1=Temp_filename1.substring(0, v);
	        			File f1=new File(Final_Filename1);
	        			if(!f1.exists())
	        			{
	        				int o1=0;
	        				x=GetAt(actmodule).srcpath_length+1;
	        				temp_ln=ln;
		        			while(temp_ln>0)
		        			{
		        				search_asm[x]=buffer1[r][o1];
		        				x++;
		        				o1++;
		        				temp_ln--;
		        			}
		        			search_asm[x]=asm_extn_array[0];
		        			x++;
		        			search_asm[x]=asm_extn_array[1];
		        			x++;
		        			search_asm[x]=asm_extn_array[2];
		        			x++;
		        			search_asm[x]=asm_extn_array[3];
		        			int asm_extn_length=x;
		        			int v1=0;
		        			while(search_asm[v1]!='\0')
		        			{
		        				v1++;
		        				
		        			}
		        					        			String Temp_filename2=new String(search_asm);
						    String Final_Filename2=Temp_filename2.substring(0, v1);
		        			File f2=new File(Final_Filename2);
		        			if(!f2.exists())
		        			{
		        				int o2=0;
		        				x=GetAt(actmodule).srcpath_length+1;
		        				temp_ln=ln;
			        			while(temp_ln>0)
			        			{
			        				search_a51[x]=buffer1[r][o2];
			        				x++;
			        				o2++;
			        				temp_ln--;
			        			}
			        			search_a51[x]=asm_extn_array[0];
			        			x++;
			        			search_a51[x]=asm_extn_array[1];
			        			x++;
			        			search_a51[x]=asm_extn_array[2];
			        			
			        			int a51_extn_length=x;
			        			
			        			for (int q = 0; q < a51_extn_length; q++)
			        			{
									GetAt(actmodule).Srcpath[q]=search_a51[q];
								}
		        				
		        			}
		        			else
		        			{
		        				for (int q1 = 0; q1 < asm_extn_length; q1++) 
		        				{
		        					GetAt(actmodule).Srcpath[q1]=search_asm[q1];	
								}
		        			}
		        			
	        			}
	        			else
	        			{
	        				for (int q3 = 0; q3 <= c_extn_length; q3++)
	        				{
	        					GetAt(actmodule).Srcpath[q3]=search[q3];	
							}
		        		        			
	        			}
	        			
	        			
	        			}
	        		
	        			else
	        			{
	        				actmodule=0;
	        				for(int o1=0;o1<ln;o1++)
		        			{
		        				lastlabel[o1]= buffer1[r][o1];
		        				
		        			}
		        			
	        			}	        			
	        			
	        		    r++;
	        			
	        		}
	        		break;
	        		case 01:
	        			break;
	        			
	        		case 02:	
	        		{
	        		int ln=File_Record[i1][4];
	        		
	        		for(int o=0;o<ln;o++)
        			{
        				buffer1[r][o]=(char) File_Record[i1][o+5];
        				
        			}
	        		buffer1[r][ln]=0;
	        		pm=GetAt(actmodule);
	        		pp=addProctolist(pm,r,ln);
	        		for (int i = 0; i < ln; i++) {
	        			currfun[i]=buffer1[r][ln];
						
					}
	        		r++;
	        		}
	        		break;
	        		case 3:
	        		pp=null;
	        		mobext=0;
	        		pm=GetAt(actmodule);
	        		actmodule=-1;
	        		
	        		break;
	        		case 4:
	        			break;
	        		case 5:
	        		if (mobext==1) {
	        			
	        			procname[0]=temp_procname[0];
	        			procname[1]=0;
	        			pp=null;
						
					}
	        		break;
	        		}
	        		break;
	        	case 02:
	        		Validfile=01;
	        		break;
	        		
	        	case 04:
	        		Validfile=01;
	        		break;
	        		
	        	case 112:
	        		Validfile=01;
	        		break;
	        		
	        	default:
	        		Validfile=00;
	        	    break;
	        	}
	        	
	        	i1++;
	        	k--;
	        	
	        }while(k!=0);
		}

	public void Adddbginfo( long addr, int lineno, int actmodule,char[] currfun ) 
	{
	
	Modinfo pm = GetAt(actmodule) ;

	if(pm==null) return;

	Addrline Lindefcur=pm.Addlinfo;
	if(actmodule==-1)
    return;
	
	Addrline temp  ;

	while(Lindefcur != null)
	{
		if (Lindefcur.lineno == lineno)
		{
			if(Lindefcur.addr > addr)
				Lindefcur.addr = addr;
			return ;
		}
		Lindefcur=Lindefcur.next  ;
	}
	temp=new Addrline();
	temp.addr = addr ;
	temp.lineno= lineno ;

	//Store the function Name.
	for (int i = 0; i < ln; i++) {
	
		temp.Fname[i]=currfun[i];
	}
	temp.next = pm.Addlinfo ;
	temp.prev = null;
	if(pm.Addlinfo!=null)
		pm.Addlinfo.prev = temp;
	pm.Addlinfo = temp ;

	if(addr<pm.lowaddr)
		pm.lowaddr=addr;
	if(addr>pm.highaddr)
		pm.highaddr=addr;

	
}



	
	public void Adddbglblinfo(long addr, char[][] buffer12, int deftyp,
				int syminfo, int actmodule2, Proc pp2, int ptd2,int len) {
		

		
		char Temp_buffer[]=new char[len];
		Labeladdr  Ahead=plba;
		
		LabelName  Nhead=plbn  ;

		Labelinfo plbi = new Labelinfo();
		plbi.addr = addr ;
		plbi.Range = deftyp + 1 ;
		plbi.MemSpec = syminfo ;
		plbi.ModID = actmodule2 ;
		
		
	for (int i = 0; i < len; i++) {
			
			Temp_buffer[i]=buffer12[r][i];
			
		}
		String temp_string=new String(Temp_buffer);
		
		
		plbi.label=temp_string;
		
		
		plbi.Proc_labelinfo = pp2 ;
		plbi.Ptype = ptd ;


		//Address
		if(plba == null ) {
			
			plba=new Labeladdr();
			plba.ptrlbl= plbi ;
			plba.Addr	= addr ;
			plba.next	= null ;
		}
		else
		{	
			while(plba.next !=null)
			{
			
				plba=plba.next ;
			}
			plba.next = new Labeladdr() ;
			plba.next.ptrlbl = plbi ;
			plba.next.Addr = addr ;
			plba.next.next = null ;
			plba = Ahead;
			
		}

		//Label
		if(plbn==null) {
			plbn=new LabelName();
			plbn.ptrlbl = plbi ;
			
			plbn.label_name=temp_string;
			
			plbn.next = null ;
		}
		else
		{
			while(plbn.next != null )
			{
				
				plbn=plbn.next ;
			}
		
			plbn.next=new LabelName();
			plbn.next.ptrlbl = plbi ;
			
			plbn.next.label_name=temp_string ;
			//System.out.println(plbn.next.label_name);
			plbn.next.next= null ;
			plbn = Nhead ;
		}
			
	}	





			void WriteMemBlock(int Offset , int[] temp_array, int len )
			{
				
				int j=0;
				while( len--!=0)
				{
					
						CodeAddr[Code_base_Address + Offset] = (char) temp_array[j];
						Offset++;
						j++;
					
				}
					
					
				
			}

			public static String fileLocation() {
				
				return absFilepath;
			}
			
			
			//@SuppressWarnings("static-access")
			public void disassemblyCreation()
			
			{
			     
			     int NumOfModules = GetModuleCount() ;	
			     int l1=0;
			     int y = 0;
			     int temp1=0;
			     Modinfo pf =new Modinfo();	
			     
			     Libtemp=pf.Linfo;
			   	 	     
			    
			     projectPath=projectPath.concat(".FileInfo.txt");
				  File f1;
			      f1=new File(projectPath);
			      try {
					f1.createNewFile();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				 FileWriter fstream1;
				 BufferedWriter out1 = null;
				try {
					fstream1 = new FileWriter(f1,true);
					out1 = new BufferedWriter(fstream1);
				} catch (IOException e) {					
					e.printStackTrace();	}
			    
			     
			     
			     
			     while ( y < NumOfModules )
			     {
			    	 
			    	 pf=GetAt(y) ;
			    	 if(y>=1)
			    	 {   int g=0;
			    	     int stemp=0,stemp1=0,etemp=0,etemp1=0;
			    		 int chrtemp=Loader.absFilepath.lastIndexOf("\\");
			    		 //System.out.println(chrtemp);
			             Loader.projectPath=Loader.absFilepath.substring(0,chrtemp+1);
			    		 int x11=0;
			    		 while(pf.modname[x11] != '\0')
			    		 {
			    			 x11++;
			    		 }
			    		 String modname1=new String(pf.modname);
			    		 String modname2=modname1.substring(0,x11);
			    		 Loader.projectPath=Loader.projectPath.concat(modname2);
			    		 Loader.projectPath=Loader.projectPath.concat(".DSM");
						 //System.out.println(Loader.Filepath1);
				    	 File f;
				         f=new File(projectPath);
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
	 			         
				         
	 			       
    			     while(pf.Finfo!=null){
    			    	 int x3=0;
						   
     				    while(pf.Finfo.Funname[x3]!='\0')
 					    {
 					    	x3++;
 					    	
 					    }
 					    String Temp_Funname1=new String(pf.Finfo.Funname);
 					    String Final_Funname=Temp_Funname1.substring(0, x3);
 					    try {
     			        
     			        out.write("$FUNC \t"+ Final_Funname + " ");
     			        out.newLine();
     			       
     			     } catch (IOException e) {
     			    	
     			    	}	 
    			     
    			     
					int Saddr = (int) pf.Finfo.StartAddr;
					
					int Eaddr = (int)pf.Finfo.EndAddr ;


					try {
						out1.write(modname2+".DSM"+" "+Final_Funname+" "+Integer.toHexString(Saddr)+" "+Integer.toHexString(Eaddr));
							out1.newLine();
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}



					stemp=Saddr;
					
					if(g==0){
					stemp1=Saddr;
					g++;
					}
					etemp=Eaddr;
					if(etemp>etemp1){
						temp1=etemp;
						etemp=etemp1;
						etemp1=temp1;
					}
					if(stemp<stemp1){
						temp1=stemp;
						stemp=stemp1;
						stemp1=temp1;
					}
					 while( Saddr <= Eaddr )
					  {
						   int r=DisAssemble(Saddr) ;
						  
						   String codebytes="";
							for(int offset = 0 ; offset < r ; offset++ )
							{
								char RValue;
								RValue=CodeAddr[Saddr+offset];
								int temp_char;
								temp_char=(int)RValue;
								String b=Integer.toHexString(temp_char);
							    if(b.length()!=2){
							    	b="0"+b;
							    }
								codebytes+=b+" ";
								
							}

							
							try {
								char temp[]=new char[10];
		    			        String p_saddr=Integer.toHexString(Saddr).toUpperCase();
		    			        int w=p_saddr.length();
								int e=0;
								while(w>0){
									temp[e]=p_saddr.charAt(e);
									e++;
									w--;
								}
								
								
								if(temp[1]=='\0'){
									p_saddr="0x000"+p_saddr;
								}
								else if(temp[2]=='\0'){
									p_saddr="0x00"+p_saddr;
								}
								else if(temp[3]=='\0'){
									p_saddr="0x0"+p_saddr;
								}
								else
								p_saddr="0x"+p_saddr;
								int codebytelength=codebytes.length();
							    out.write("\t"+p_saddr+"    "+codebytes.toUpperCase());//+"        "+asmstr );
		    			        for(int i=0;i<(9-codebytelength);i++)out.write(" ");
		    			        out.write("       "+asmstr);
		    			        out.newLine(); //for debugging
		    			        		    			        	    			    
		    			     } catch (IOException e) {
		    			    	//System.out.println("exceptionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn111111111");
		    			    	}
		    			     Saddr=Saddr+r;
											
							
						}
					 try {
						out.write("$End");
						out.newLine();
						out.newLine();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					 pf.Finfo=pf.Finfo.next;
					 
    			 }
    			     try {
						out.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					/* try {
						out1.write(modname2+".DSM"+" "+ "MOD " +Integer.toHexString(stemp1)+" "+Integer.toHexString(etemp1));
						out1.newLine();
					} catch (IOException e) {
						
						e.printStackTrace();
					} */
					
			   }
			    	 else 
			    	 {   int g=0;
			    	     int stemp=0,stemp1=0,etemp=0,etemp1=0;
			    		 while(Libtemp!=null){
					    	 l1++;
					    	 Libtemp=Libtemp.next;
					     }  
			    		 
			    		 int chrtemp=Loader.absFilepath.lastIndexOf("\\");
			    		// System.out.println(chrtemp);
			    		 Loader.projectPath=Loader.absFilepath.substring(0,chrtemp+1);
			    		 
			    		String modname1=new String(pf.modname);
			    		String modname2=modname1.substring(0,7);
			    		Loader.projectPath=Loader.projectPath.concat(modname2);
						Loader.projectPath=Loader.projectPath.concat(".DSM");
						//System.out.println(Loader.Filepath1);
				    	 File f;
				         f=new File(projectPath);
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
				         
	 			       
				     
				     while(l1>0){
				    	
				    	 int x4=0;
						    while(pf.Linfo.Funname[x4]!='\0')
						    {
						    	x4++;
						    }
						    String Temp_Funname11=new String(pf.Linfo.Funname);
						    String Final_Lib_Funname=Temp_Funname11.substring(0, x4);
						    try {
					        
					        out.write("$FUNC \t"+ Final_Lib_Funname + " ");
					        out.newLine();
					        //out.close();
					     } catch (IOException e) {
					    	
					    	}
				     int Saddr = (int)pf.Linfo.StartAddr;
				     int Eaddr = (int)pf.Linfo.EndAddr ;

				     try {
							out1.write(modname2+".DSM"+" "+Final_Lib_Funname+" "+Integer.toHexString(Saddr)+" "+Integer.toHexString(Eaddr));
						out1.newLine();

						} catch (IOException e1) {
							
							e1.printStackTrace();
						}


				        stemp=Saddr;
						etemp=Eaddr;
						if(g==0){
							stemp1=Saddr;
							g++;
							}
						if(etemp>etemp1){
							temp1=etemp;
							etemp=etemp1;
							etemp1=temp1;
						}
						if(stemp<stemp1){
							temp1=stemp;
							stemp=stemp1;
							stemp1=temp1;
						}
				     while( Saddr <= Eaddr )
					  {
						   int r=DisAssemble(Saddr) ;
						  
						   String codebytes="";
							for(int offset = 0 ; offset < r ; offset++ )
							{
								
								char RValue;
								RValue=CodeAddr[Saddr+offset];
								int temp_char;
								temp_char=(int)RValue;
								String b=Integer.toHexString(temp_char);
								if(b.length()!=2){
									b="0"+b;
								}
								codebytes+=b+" ";
								
							}

							
							try {
								char temp[]=new char[10];
		    			        String p_saddr=Integer.toHexString(Saddr).toUpperCase();
		    			        int w=p_saddr.length();
								int e=0;
								while(w>0){
									temp[e]=p_saddr.charAt(e);
									e++;
									w--;
								}
								
								if(temp[1]=='\0'){
									p_saddr="0x000"+p_saddr;
								}
								else if(temp[2]=='\0'){
									p_saddr="0x00"+p_saddr;
								}
								else if(temp[3]=='\0'){
									p_saddr="0x0"+p_saddr;
								}
								else
								p_saddr="0x"+p_saddr;
								int codebytelength=codebytes.length();
							
		    			        
		    			        out.write("\t"+p_saddr+"    "+codebytes.toUpperCase());//+"        "+asmstr );
		    			        for(int i=0;i<(9-codebytelength);i++)out.write(" ");
		    			        out.write("       "+asmstr);
		    			        out.newLine(); //for debugging
		    			        
		    			        

		    			    
		    			     } catch (IOException e) {
		    			    	
		    			    	}
		    			     Saddr=Saddr+r;
							
							
							
						}	 
				     
				     try {
						out.write("$End");
						out.newLine();
						out.newLine();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				     pf.Linfo=pf.Linfo.next;
				     l1--;
			    	 }
				     try {
						out.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					/* try {
						out1.write(modname2+".DSM"+" "+ "MOD " +Integer.toHexString(stemp1)+" "+Integer.toHexString(etemp1));
						out1.newLine();
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}  */
					
			     }   
			    	 
			    y++;	 
				}
			     try {
					out1.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			    }
			
			    	 
			 public int DisAssemble(int code)
	    	 {
	    		 
	    		    String vstr=new String();
					String temp_str=new String();
					char[] temp_cp=new char[3];
					int[] cp=new int[3];
					int addr;
					

					int PcIncVal=0;
					code&=0xFFFF;
					
					
					temp_cp[0]=CodeAddr[code];
					temp_cp[1]=CodeAddr[code+1];
					temp_cp[2]=CodeAddr[code+2];
					cp[0]=(int)temp_cp[0];
					cp[1]=(int)temp_cp[1];
					cp[2]=(int)temp_cp[2];
						
					
					   
					int opcode=(int)cp[0];     //erases 1st byte in the Opcode
					 
	    			if(opcode<0x10)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=1;
	    					asmstr="nop";
	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=(short)cp[1] | (code & 0xF800);
	    					asmstr="ajmp";
	    					if(GetOffset(addr)==01)
	    					{
	    					    asmstr=asmstr.concat(lbl);
	    						
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat("(0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						
	    						asmstr.concat(lbl);
	    					}
	    					break;
	    				  case 0x02:
	    					PcIncVal=3;
	    					addr=(cp[1]<<8)+cp[2];
	    					
	    					asmstr="ljmp  ";
	    					
	    					if(GetOffset(addr)==01)
	    					{
	    						
	    						asmstr=asmstr.concat(" "+lbl);
	    						
	    						vstr=Integer.toHexString(addr);
	    						
	    						asmstr=asmstr.concat("(0x"+getCount(addr));
	    						
	    						asmstr=asmstr.concat(vstr);
	    						
	    						asmstr=asmstr.concat(")");
	    						
	    					}
	    					else
	    					{
	    						
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="rr a";
	    					break;
	    				  case 0x04:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  a";
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    				
	    					asmstr="inc  ";
	    					vstr=GetRegName(cp[1]);
	    					
	    					asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  @r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  @r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  r5";
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  r7";
	    					break;
	    				}
	    				
	    				return(PcIncVal);
	    			}
	    			if(opcode<0x20)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=3;
	    					
	    					asmstr="jbc  ";
	    					temp_str=GetBitName(cp[1]);
	    					
	    					asmstr=asmstr.concat(temp_str);
	    					if(cp[2]<0x80)
	    						addr=(int)(code+3)+(int)(cp[2]);
	    					else
	    						addr=(int)(code+3)-((-(int)cp[2]) & 0xFF);
	    					
	    					asmstr=asmstr.concat(",");
	    					if(GetOffset(addr)==01)
	    					{
	    						
	    						asmstr=asmstr.concat(lbl);
	    						
	    						vstr=Integer.toHexString(addr);
	    						
	    						asmstr=asmstr.concat(" (0x");
	    						
	    						asmstr=asmstr.concat(vstr);
	    						
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=(code & 0xF800)+cp[1];
	    					
	    					asmstr="acall ";
	    					if(GetOffset(addr)==01)
	    					{
	    						
	    						asmstr=asmstr.concat(lbl);
	    						
	    						vstr=Integer.toHexString(addr);
	    						
	    						asmstr=asmstr.concat(" (0x");
	    						
	    						asmstr=asmstr.concat(vstr);
	    						
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						
	    					asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=3;
	    					addr=(cp[1]<<8)+cp[2];
	    					
	    					asmstr="lcall ";
	    					if(GetOffset(addr)==01)
	    					{
	    						
	    						asmstr=asmstr.concat(lbl);
	    						
	    						vstr=Integer.toHexString(addr);
	    						
	    						asmstr=asmstr.concat(" (0x"+getCount(addr));
	    						
	    						asmstr=asmstr.concat(vstr);
	    						
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						
	    						asmstr=asmstr.concat(lbl);
	    					}


	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="rrc  a";
	    					break;
	    				  case 0x04:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  a";
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    					
	    					asmstr="dec  ";
	    					temp_str= GetRegName(cp[1]);
	    					
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  @r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  @r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  r5";
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="dec  r7";
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0x30)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=3;
	    					
	    					asmstr="jb   ";
	    					temp_str=GetBitName(cp[1]);
	    					
	    					asmstr=asmstr.concat(temp_str);
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)(cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asmstr=asmstr.concat(",");
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=cp[1]+0x100 +(code & 0xF800);
	    					
	    					asmstr="ajmp ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}
	    					break;
	    				  case 0x02:
	    					PcIncVal=1;
	    					
	    					asmstr="ret";
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="rl   a";
	    					break;
	    				  case 0x04:
	    					PcIncVal=2;
 	    					
	    					asmstr="add  a,#";
	    					
	    					 asci=(int)cp[1];
	    					 String b= Integer.toHexString(asci);
	    					 vstr=getCount1 (asci)+b+"H";
	    					 asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    					
	    					asmstr="add  a,";
	    					
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,@r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,@r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,r5";
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="add  a,r7";
	    					break;
	    				}
	    				
	    				return(PcIncVal);
	    			}
	    			if(opcode<0x40)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=3;
	    					
	    					asmstr="jnb  ";
	    					temp_str=GetBitName(cp[1]);
	    					
	    					asmstr=asmstr.concat(temp_str);
	    					   
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)(cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asmstr=asmstr.concat(",");
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=(code & 0xF800)+cp[1]+0x100;
	    					
	    					asmstr="acall";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=1;
	    					
	    					asmstr="reti";
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="rlc  a";
	    					break;
	    				  case 0x04:
	    					PcIncVal=2;
	    					
	    					asmstr="addc a,#";
	    					
	    					asci=(int)cp[1];
	    					String b=Integer.toHexString(asci);
	    					vstr=getCount1(asci)+b+"H";
	    					asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    					
	    					asmstr="addc a,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,@r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,@r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,r5";//actual
	    					
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="addc a,r7";
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0x50)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=2;
	    					
	    					asmstr="jc   ";
	    					temp_str=GetBitName(cp[1]);
	    					if(cp[1]<0x80)
	    					  addr=(int)(code+2)+(int)((char)cp[1]);
	    					else
	    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(temp_str);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=cp[1]+0x200 +(code & 0xF800);
	    					
	    					asmstr="ajmp ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					
	    					asmstr="orl  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",a");
	    					break;
	    				  case 0x03:
	    					PcIncVal=3;
	    					
	    					asmstr="orl  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asci=(int)cp[2];
	    					String b= Integer.toHexString(asci);
	    					vstr=", #"+getCount1(asci)+b+"H";
	    					asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x04:
	    					PcIncVal=2;
	    					
	    					asmstr="orl  a,#";
	    					
	    					asci=(int) cp[1];
	    					String b1= Integer.toHexString(asci);
	    					vstr=getCount1(asci)+b1+"H";
	    					asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    					
	    					asmstr="orl  a,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,@r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,@r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,r5";
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="orl  a,r7";
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0x60)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=2;
	    					
	    					asmstr="jnc  ";
	    					temp_str=GetBitName(cp[1]);
	    					if(cp[1]<0x80)
	    					  addr=(int)(code+2)+(int)((char)cp[1]);
	    					else
	    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(temp_str);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=(code & 0xF800)+cp[1]+0x200;
	    					
	    					asmstr="acall ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					
	    					asmstr="anl  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",A");
	    					break;
	    				  case 0x03:
	    					PcIncVal=3;
	    				
	    					asmstr="anl  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asci=(int)cp[2];
	    					String b=Integer.toHexString(asci);
	    					vstr=getCount1(asci)+b+"H";
	    					asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x04:
	    					PcIncVal=2;
	    					
	    					int asci=(int)cp[1];
	    					String b1= Integer.toHexString(asci);
	    					vstr="anl  a,#"+getCount1(asci)+b1+"H";
	    					
	    					asmstr=vstr;
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    					
	    					asmstr="anl  a,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,@r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,@r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,r5";
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="anl  a,r7";
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0x70)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=2;
	    					
	    					asmstr="jz   ";
	    					temp_str=GetBitName(cp[1]);
	    					if(cp[1]<0x80)
	    					  addr=(int)(code+2)+(int)((char)cp[1]);
	    					else
	    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(temp_str);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=cp[1]+0x300 +(code & 0xF800);
	    					
	    					asmstr="ajmp ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					
	    					asmstr="xrl  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",a");
	    					break;
	    				  case 0x03:
	    					PcIncVal=3;
	    					
	    					asmstr="xrl  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asci=(int)cp[2];
	    					String b=Integer.toHexString(asci);
	    					vstr=",#"+getCount1(asci)+b+"H";
	    					asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x04:
	    					PcIncVal=2;
	    					
	    					asmstr="xrl  a,#";
	    					
	    					asci=(int)cp[1];
	    					String b1=Integer.toHexString(asci);
	    					vstr=getCount1(asci)+b1+"H";
	    					asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    					
	    					asmstr="xrl  a,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,@r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,@r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,r5";
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="xrl  a,r7";
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0x80)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=2;
	    					
	    					asmstr="jnz  ";
	    					temp_str=GetBitName(cp[1]);
	    					if(cp[1]<0x80)
	    					  addr=(int)(code+2)+(int)((char)cp[1]);
	    					else
	    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(temp_str);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=(code & 0xF800)+cp[1]+0x300;
	    					
	    					asmstr="acall ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					
	    					asmstr="orl  C,";
	    					temp_str=GetBitName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="jmp  @a+dptr";
	    					break;
	    				  case 0x04:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b=Integer.toHexString(asci);
	    					vstr="mov  a,#"+getCount1(asci)+ b+"H";
	    					
	    					asmstr=vstr;
	    					break;
	    				  case 0x05:
	    					PcIncVal=3;
	    					
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asci=(int)cp[2];
	    					String b1=Integer.toHexString(asci);
	    					vstr=",#"+getCount1(asci)+b1+"H";
	    					asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x06:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b2=Integer.toHexString(asci);
	    					vstr="mov  @r0,#"+getCount1(asci)+b2+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x07:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b3=Integer.toHexString(asci);
	    					vstr="mov  @r1,#"+getCount1(asci)+b3+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x08:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b4=Integer.toHexString(asci);
	    					vstr="mov  r0,#"+getCount1(asci)+b4+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x09:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b5=Integer.toHexString(asci);
	    					vstr="mov  r1,#"+getCount1(asci)+b5+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x0A:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b6=Integer.toHexString(asci);
	    					vstr="mov  r2,#"+getCount1(asci)+b6+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x0B:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b7=Integer.toHexString(asci);
	    					vstr="mov  r3,#"+getCount1(asci)+b7+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x0C:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b8=Integer.toHexString(asci);
	    					vstr="mov  r4,#"+getCount1(asci)+b8+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x0D:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b9=Integer.toHexString(asci);
	    					vstr="mov  r5,#"+getCount1(asci)+b9+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x0E:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b10=Integer.toHexString(asci);
	    					vstr="mov  r6,#"+getCount1(asci)+b10+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x0F:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b11=Integer.toHexString(asci);
	    					vstr="mov  r7,#"+getCount1(asci)+b11+"H";
	    					asmstr=vstr;
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0x90)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=2;
	    					
	    					asmstr="sjmp ";
	    					if(cp[1]<0x80)
	    					  addr=(int)(code+2)+(int)((char)cp[1]);
	    					else
	    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=cp[1]+0x400 +(code & 0xF800);
	    					
	    					asmstr="ajmp ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					
	    					asmstr="anl  C,";
	    					temp_str=GetBitName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="movc a,@a+pc";
	    					break;
	    				  case 0x04:
	    					PcIncVal=1;
	    					
	    					asmstr="div  ab";
	    					break;
	    				  case 0x05:
	    					PcIncVal=3;
	    					
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[2]);
	    					asmstr=asmstr.concat(temp_str);
	    					asmstr=asmstr.concat(",");
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",@r0");
	    					break;
	    				  case 0x07:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",@r1");
	    					break;
	    				  case 0x08:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",r0");
	    					break;
	    				  case 0x09:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",r1");
	    					break;
	    				  case 0x0A:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",r2");
	    					break;
	    				  case 0x0B:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",r3");
	    					break;
	    				  case 0x0C:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",r4");
	    					break;
	    				  case 0x0D:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    				    asmstr=asmstr.concat(temp_str);
	    					
	    				    asmstr=asmstr.concat(",r5");
	    					break;
	    				  case 0x0E:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",r6");
	    					break;
	    				  case 0x0F:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetRegName(cp[1]);
	    				    asmstr=asmstr.concat(temp_str);
	    					
	    				    asmstr=asmstr.concat(",r7");
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0xA0)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=3;
	    					
	    					asci=(int)((cp[1]<<8)+cp[2]);
	    					String b=Integer.toHexString(asci);
	    					vstr="mov  dptr,#"+getCount(asci)+b+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=(code & 0xF800)+cp[1]+0x400;
	    					
	    					asmstr="acall ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					asmstr="mov  ";
	    					temp_str=GetBitName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					asmstr=asmstr.concat(",C");
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="movc a,@a+dptr";
	    					break;
	    				  case 0x04:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b1=Integer.toHexString(asci);
	    					vstr="subb  a,#"+getCount1(asci)+b1+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    					
	    					asmstr="subb a,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,@r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,@r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,r5";
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="subb a,r7";
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0xB0)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1];
	    					String b= Integer.toHexString(asci);
	    					vstr="orl  C,"+getCount1(asci)+b+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=cp[1]+0x500 +(code & 0xF800);
	    					
	    					asmstr="ajmp ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  C,";
	    					temp_str=GetBitName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="inc  dptr";
	    					break;
	    				  case 0x04:
	    					PcIncVal=1;
	    					
	    					asmstr="mul  ab";
	    					break;
	    				  case 0x05:
	    					PcIncVal=1;
	    					
	    					asmstr="DB A5H";
	    					break;
	    				  case 0x06:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  @r0,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x07:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  @r1,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x08:
	    					PcIncVal=2;
	    					asmstr="mov  r0,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x09:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  r1,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x0A:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  r2,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x0B:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  r3,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x0C:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  r4,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x0D:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  r5,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x0E:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  r6,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x0F:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  r7,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0xC0)
	    			{
	    				int asci1;
						int asci2;
						switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=2;
	    					
	    					asci=(int)cp[1]-48;
	    					String b=Integer.toHexString(asci);
	    					vstr="anl  C,/"+getCount1(asci)+b+"H";
	    					asmstr=vstr;
	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=(code & 0xF800)+cp[1]+0x500;
	    					
	    					asmstr="acall ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					
	    					asmstr="cpl  ";
	    					temp_str=GetBitName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="cpl  C";
	    					break;
	    				  case 0x04:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b1=Integer.toHexString(asci1);
	    					String b2=Integer.toHexString(asci2);
	    					vstr="cjne a,#"+getCount1(asci1)+b1+"H"+",0x"+getCount(asci2)+b2;
	    					asmstr=vstr;
	    					break;
	    				  case 0x05:
	    					PcIncVal=3;
	    					
	    					asmstr="cjne a,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci=(int)addr;
	    					String b11=Integer.toHexString(asci);
	    					vstr=",0x"+b11;
	    					asmstr=asmstr.concat(vstr);
	    					break;
	    				  case 0x06:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b21=Integer.toHexString(asci1);
	    					String b22=Integer.toHexString(asci2);
	    					vstr="cjne @r0,#"+getCount1(asci1)+b21+"H"+",0x"+getCount(asci2)+b22;
	    					asmstr=vstr;
	    					break;
	    				  case 0x07:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b31=Integer.toHexString(asci1);
	    					String b32=Integer.toHexString(asci2);
	    					vstr="cjne @r1,#"+getCount1(asci1)+b31+"H"+",0x"+getCount(asci2)+b32;
	    					asmstr=vstr;
	    					break;
	    				  case 0x08:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b41=Integer.toHexString(asci1);
	    					String b42=Integer.toHexString(asci2);
	    					vstr="cjne r0,#"+getCount1(asci1)+b41+"H"+",0x"+getCount(asci2)+b42;
	    					asmstr=vstr;
	    					break;
	    				  case 0x09:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b51=Integer.toHexString(asci1);
	    					String b52=Integer.toHexString(asci2);
	    					vstr="cjne r1,#"+getCount1(asci1)+b51+",0x"+getCount(asci2)+b52;
	    					asmstr=vstr;
	    					break;
	    				  case 0x0A:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b61=Integer.toHexString(asci1);
	    					String b62=Integer.toHexString(asci2);
	    					vstr="cjne r2,#"+getCount1(asci1)+b61+"H"+",0x"+getCount(asci2)+b62;
	    					asmstr=vstr;
	    					break;
	    				  case 0x0B:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b71=Integer.toHexString(asci1);
	    					String b72=Integer.toHexString(asci2);
	    					vstr="cjne r3,#"+getCount1(asci1)+b71+"H"+",0x"+getCount(asci2)+b72;
	    					asmstr=vstr;
	    					break;
	    				  case 0x0C:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b81=Integer.toHexString(asci1);
	    					String b82=Integer.toHexString(asci2);
	    					vstr="cjne r4,#"+getCount1(asci1)+b81+"H"+",0x"+getCount(asci2)+b82;
	    					asmstr=vstr;
	    					break;
	    				  case 0x0D:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b91=Integer.toHexString(asci1);
	    					String b92=Integer.toHexString(asci2);
	    					vstr="cjne r5,#"+getCount1(asci1)+b91+"H"+",0x"+getCount(asci2)+b92;
	    					asmstr=vstr;
	    					break;
	    				  case 0x0E:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String b10=Integer.toHexString(asci1);
	    					String b20=Integer.toHexString(asci2);
	    					vstr="cjne r6,#"+getCount1(asci1)+b10+"H"+",0x"+getCount(asci2)+b20;
	    					asmstr=vstr;
	    					break;
	    				  case 0x0F:
	    					PcIncVal=3;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					
	    					asci1=(int)cp[1];
	    					asci2=(int)addr;
	    					String bb1=Integer.toHexString(asci1);
	    					String bb2=Integer.toHexString(asci2);
	    					vstr="cjne r7,#"+getCount1(asci1)+bb1+"H"+",0x"+getCount(asci2)+bb2;
	    					asmstr=vstr;
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0xD0)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=2;
	    					
	    					asmstr="push  ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=cp[1]+0x600 +(code & 0xF800);
	    					
	    					asmstr="ajmp ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					
	    					asmstr="clr  ";
	    					temp_str=GetBitName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="clr  C";
	    					break;
	    				  case 0x04:
	    					PcIncVal=1;
	    					
	    					asmstr="swap a";
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    					
	    					asmstr="xch  a,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="xch  a,@r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="xch  a,@r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="xch  a,r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="xch  a,r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="xch  a,r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="xch  a,r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    		            asmstr="xch  a,r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="xch  a,r5";
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="xch  a,r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="xch  a,r7";
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			if(opcode<0xE0)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=2;
	    					
	    					asmstr="pop  ";
	    					
	    				    temp_str=GetRegName(cp[1]);
	    				    
	    					asmstr=asmstr.concat(temp_str);
	    					
	    					
	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=(code & 0xF800)+cp[1]+0x600;
	    					
	    					asmstr="acall ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=2;
	    					
	    					asmstr="setb ";
	    					temp_str=GetBitName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="setb C";
	    					break;
	    				  case 0x04:
	    					PcIncVal=1;
	    					
	    					asmstr="da   a";
	    					break;
	    				  case 0x05:
	    					PcIncVal=3;
	    					
	    					asmstr="djnz ";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+3)+(int)((char)cp[2]);
	    					else
	    					  addr=(int)(code+3)-((-(int)cp[2])&0xFF);
	    					asmstr=asmstr.concat(",");
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="xchd a,@r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="xchd a,@r1";
	    					break;
	    				  case 0x08:
	    					
	    					  asmstr="djnz r0,";
	    					
	    					  PcIncVal=2;
		    					if(cp[2]<0x80)
		    					  addr=(int)(code-1);//+(int)((char)cp[1]);
		    					else
		    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
		    					if(GetOffset(addr)==01)
		    					{
		    						asmstr=asmstr.concat(lbl);
		    						vstr=Integer.toHexString(addr);
		    						asmstr=asmstr.concat(" (0x");
		    						asmstr=asmstr.concat(vstr);
		    						asmstr=asmstr.concat(")");
		    					}
		    					else
		    					{
		    						asmstr=asmstr.concat(lbl);
		    					}

		    					break;
	    				  case 0x09:
	    					
	    					  asmstr="djnz r1,";
	    					
	    					  PcIncVal=2;
		    					if(cp[2]<0x80)
		    					  addr=(int)(code+2)+(int)((char)cp[1]);
		    					else
		    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
		    					if(GetOffset(addr)==01)
		    					{
		    						asmstr=asmstr.concat(lbl);
		    						vstr=Integer.toHexString(addr);
		    						asmstr=asmstr.concat(" (0x");
		    						asmstr=asmstr.concat(vstr);
		    						asmstr=asmstr.concat(")");
		    					}
		    					else
		    					{
		    						asmstr=asmstr.concat(lbl);
		    					}

		    					break;
	    				  case 0x0A:
	    					
	    					  asmstr="djnz r2,";
	    					
	    					  PcIncVal=2;
		    					if(cp[2]<0x80)
		    					  addr=(int)(code+2)+(int)((char)cp[1]);
		    					else
		    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
		    					if(GetOffset(addr)==01)
		    					{
		    						asmstr=asmstr.concat(lbl);
		    						vstr=Integer.toHexString(addr);
		    						asmstr=asmstr.concat(" (0x");
		    						asmstr=asmstr.concat(vstr);
		    						asmstr=asmstr.concat(")");
		    					}
		    					else
		    					{
		    						asmstr=asmstr.concat(lbl);
		    					}

		    					break;
	    				  case 0x0B:
	    					
	    					  asmstr="djnz r3,";
	    					
	    					  PcIncVal=2;
		    					if(cp[2]<0x80)
		    					  addr=(int)(code+2)+(int)((char)cp[1]);
		    					else
		    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
		    					if(GetOffset(addr)==01)
		    					{
		    						asmstr=asmstr.concat(lbl);
		    						vstr=Integer.toHexString(addr);
		    						asmstr=asmstr.concat(" (0x");
		    						asmstr=asmstr.concat(vstr);
		    						asmstr=asmstr.concat(")");
		    					}
		    					else
		    					{
		    						asmstr=asmstr.concat(lbl);
		    					}

		    					break;
	    				  case 0x0C:
	    					
	    					  asmstr="djnz r4,";
	    					
	    					  PcIncVal=2;
		    					if(cp[2]<0x80)
		    					  addr=(int)(code+2)+(int)((char)cp[1]);
		    					else
		    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
		    					if(GetOffset(addr)==01)
		    					{
		    						asmstr=asmstr.concat(lbl);
		    						vstr=Integer.toHexString(addr);
		    						asmstr=asmstr.concat(" (0x");
		    						asmstr=asmstr.concat(vstr);
		    						asmstr=asmstr.concat(")");
		    					}
		    					else
		    					{
		    						asmstr=asmstr.concat(lbl);
		    					}

		    					break;
	    				  case 0x0D:
	    					
	    					  asmstr="djnz r5,";
	    					
	    					  PcIncVal=2;
		    					if(cp[2]<0x80)
		    					  addr=(int)(code+2)+(int)((char)cp[1]);
		    					else
		    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
		    					if(GetOffset(addr)==01)
		    					{
		    						asmstr=asmstr.concat(lbl);
		    						vstr=Integer.toHexString(addr);
		    						asmstr=asmstr.concat(" (0x");
		    						asmstr=asmstr.concat(vstr);
		    						asmstr=asmstr.concat(")");
		    					}
		    					else
		    					{
		    						asmstr=asmstr.concat(lbl);
		    					}

		    					break;
	    				  case 0x0E:
	    					
	    					  asmstr="djnz r6,";
	    					
	    					  PcIncVal=2;
		    					if(cp[2]<0x80)
		    					  addr=(int)(code+2)+(int)((char)cp[1]);
		    					else
		    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
		    					if(GetOffset(addr)==01)
		    					{
		    						asmstr=asmstr.concat(lbl);
		    						vstr=Integer.toHexString(addr);
		    						asmstr=asmstr.concat(" (0x");
		    						asmstr=asmstr.concat(vstr);
		    						asmstr=asmstr.concat(")");
		    					}
		    					else
		    					{
		    						asmstr=asmstr.concat(lbl);
		    					}

		    					break;
	    				  case 0x0F:
	    					
	    					  asmstr="djnz r7,";
	    				
	    					PcIncVal=2;
	    					if(cp[2]<0x80)
	    					  addr=(int)(code+2)+(int)((char)cp[1]);
	    					else
	    					  addr=(int)(code+2)-((-(int)cp[1])&0xFF);
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				}

	    				return(PcIncVal);
	    			}
	    			if(opcode<0xF0)
	    			{
	    				switch(opcode & 0x0F)
	    				{
	    				  case 0x00:
	    					PcIncVal=1;
	    					
	    					asmstr="movx a,@dptr";
	    					break;
	    				  case 0x01:
	    					PcIncVal=2;
	    					addr=cp[1]+0x700 +(code & 0xF800);
	    					
	    					asmstr="ajmp ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    					break;
	    				  case 0x02:
	    					PcIncVal=1;
	    					
	    					asmstr="movx a,@r0";
	    					break;
	    				  case 0x03:
	    					PcIncVal=1;
	    					
	    					asmstr="movx a,@r1";
	    					break;
	    				  case 0x04:
	    					PcIncVal=1;
	    					
	    					asmstr="clr  a";
	    					break;
	    				  case 0x05:
	    					PcIncVal=2;
	    					
	    					asmstr="mov  a,";
	    					temp_str=GetRegName(cp[1]);
	    					asmstr=asmstr.concat(temp_str);
	    					break;
	    				  case 0x06:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,@r0";
	    					break;
	    				  case 0x07:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,@r1";
	    					break;
	    				  case 0x08:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,r0";
	    					break;
	    				  case 0x09:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,r1";
	    					break;
	    				  case 0x0A:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,r2";
	    					break;
	    				  case 0x0B:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,r3";
	    					break;
	    				  case 0x0C:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,r4";
	    					break;
	    				  case 0x0D:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,r5";
	    					break;
	    				  case 0x0E:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,r6";
	    					break;
	    				  case 0x0F:
	    					PcIncVal=1;
	    					
	    					asmstr="mov  a,r7";
	    					break;
	    				}
	    				return(PcIncVal);
	    			}
	    			switch(opcode & 0x0F)
	    			{
	    				case 0x00:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="movx @dptr,a";
	    				  break;
	    				case 0x01:
	    				  PcIncVal=2;
	    				  addr=(code & 0xF800)+cp[1]+0x700;
	    				  
	    				  asmstr="acall ";
	    					if(GetOffset(addr)==01)
	    					{
	    						asmstr=asmstr.concat(lbl);
	    						vstr=Integer.toHexString(addr);
	    						asmstr=asmstr.concat(" (0x");
	    						asmstr=asmstr.concat(vstr);
	    						asmstr=asmstr.concat(")");
	    					}
	    					else
	    					{
	    						asmstr=asmstr.concat(lbl);
	    					}

	    				  break;
	    				case 0x02:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="movx @r0,a";
	    				  break;
	    				case 0x03:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="movx @r1,a";
	    				  break;
	    				case 0x04:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="cpl  a";
	    				  break;
	    				case 0x05:
	    				  PcIncVal=2;
	    				  asmstr="mov  ";
	    				  temp_str=GetRegName(cp[1]);
	    				  asmstr=asmstr.concat(temp_str);
	    				  
	    				  asmstr=asmstr.concat(",a");
	    				  break;
	    				case 0x06:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  @r0,a";
	    				  break;
	    				case 0x07:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  @r1,a";
	    				  break;
	    				case 0x08:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  r0,a";
	    				  break;
	    				case 0x09:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  r1,a";
	    				  break;
	    				case 0x0A:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  r2,a";
	    				  break;
	    				case 0x0B:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  r3,a";
	    				  break;
	    				case 0x0C:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  r4,a";
	    				  break;
	    				case 0x0D:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  r5,a";
	    				  break;
	    				case 0x0E:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  r6,a";
	    				  break;
	    				case 0x0F:
	    				  PcIncVal=1;
	    				  
	    				  asmstr="mov  r7,a";
	    				  break;
	    			}
	    			return(PcIncVal);
	    		 }
	    		
			    	
			
			 

			
			
			    	 int GetOffset ( int  Offset)
						{
							
							LblAd = plba;
							String temp=new String();
							char[] temp1=new char[10];
							
							while (LblAd!=null)
							{
								if(LblAd.Addr == Offset)
								{
									
									lbl= LblAd.ptrlbl.label;
									return 01;
								}
								else
								{
									LblAd = LblAd.next ;
								}
							}
							
							
							temp=Integer.toHexString(Offset);
							int w=temp.length();
							int e=0;
							while(w>0)
							{
								temp1[e]=temp.charAt(e);
								
								e++;
								w--;
							}
							if(temp1[3]==0)
							{
								lbl="0x0";
								lbl+=temp;
								
							}
							else
							{
							lbl="0x";
							lbl+=temp;
							}
							
											
							return 00;
							}
				
			

			
			
			
			String GetRegName( int regno )
			{
			String grn_str=new String();
				if(regno < 128)
				{
			        
					grn_str=Integer.toHexString(regno);
					if(regno >=0 && regno <= 15){
						grn_str="0"+grn_str.toUpperCase()+"H";
					}
					else{
						grn_str=grn_str.toUpperCase()+"H";
					}
					return grn_str;
				}
			    else
			    {
			      
			    	grn_str=SReg510[regno-128];
			    	return grn_str;
			}
				
			}
				
				String GetBitName(int bitno)
				{
					int bitpos;
					String gbn_str;
					if(bitno < 0x80)
					{
					bitpos=bitno%8;
					bitno=0x20+bitno/8;
					gbn_str=Integer.toHexString(bitno)+"H."+Integer.toHexString(bitpos);
					return gbn_str;
					}
					else
					gbn_str=SBit510[bitno-0x80];
					return gbn_str;
				}
		 
				private String getCount (int a) {
					String str;
					if( a >=0 && a<=15)
					{
					str="000";
					}
					else if(a>=16 && a<=255)
					{
						str="00";
					}
					else if (a>=255 && a<=4095)
					{
						str="0";
					}
					else 
					{
						str="";
						
					}
					return str;
					
				}
				
				
				private String getCount1 (int a) {
					String str;
					if( a >=0 && a<=15)
					{
					str="0";
					}
					else 
					{
						str="";
						
					}
					return str;
					
				}
}



		

