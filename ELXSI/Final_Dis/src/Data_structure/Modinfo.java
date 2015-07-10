package Data_structure;
import Data_structure.Proc;
public class Modinfo {
	 
	public int mobext;
	public int ObExt;
	public int ModID;
    public int srcpath_length;
	public long highaddr;
	public long lowaddr;  

	public char Srcpath[]=new char[500];
	public char modname[]=new char[500];
	public char temp_Srcpath[]=new char[500];
	public Proc Finfo = new Proc();
	public Proc mxm_Finfo = new Proc();
	public static Proc Linfo =new Proc();
	
	public Addrline Addlinfo=new Addrline();
}
