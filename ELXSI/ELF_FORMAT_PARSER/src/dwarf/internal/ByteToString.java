/***********************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ByteToString
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Convert the value of Byte array to String variable.
 ***********************************************************************/

package dwarf.internal;

public class ByteToString {


	public ByteToString() {		
	}


	/*****************************************************************
	 * Convert byte array value to Hex String format from ELF File.
	 * @param header -> Header 
	 * @param startIndex -> Offset value of Header information.
	 * @param length -> Length value from Offset.
	 * @param EI_DATA -> Data Encoding format.
	 * @return strValue -> Value in Hex-String format.
	 *****************************************************************/
	public static String byte2StringFile(byte[] header, int startIndex, int length, int EI_DATA) {		

		String strValue = new String();
		byte[] headerValue = new byte[length];
		for(int i=0;i<length;i++){
			headerValue[i] = header[startIndex+i];
		}

		switch(EI_DATA){
		case 1:
			//ELFDATA2LSB
			for(int i=length-1;i>=0;i--){				
				strValue = getStringValue(strValue, headerValue, i);				
			}
			break;
		case 2:
			//ELFDATA2MSB
			for(int i=0;i<length;i++){
				strValue = getStringValue(strValue, headerValue, i);
			}
			break;
		default:
			break;
		}		
		return strValue;	
	}



	/*****************************************************************
	 * Convert byte array value to Hex String format from Array.
	 * @param header -> Header 
	 * @param startIndex -> Offset value of Header information.
	 * @param length -> Length value from Offset.
	 * @param EI_DATA -> Data Encoding format.
	 * @return strValue -> Value in Hex-String format.
	 *****************************************************************/
	public static String byte2StringArray(byte[] header, int startIndex, int length, int EI_DATA) {		

		String strValue = new String();
		byte[] headerValue = new byte[length];
		for(int i=0;i<length;i++){
			headerValue[i] = header[startIndex+i];
		}

		switch(EI_DATA){
		case 1:
			//ELFDATA2LSB
			for(int i=length-1;i>=0;i--){
				strValue = getStringValue(strValue, headerValue, i);
			}
			break;
		case 2:
			//ELFDATA2MSB
			for(int i=0;i<length;i++){
				strValue = getStringValue(strValue, headerValue, i);
			}
			break;
		default:
			break;
		}		
		return strValue;	
	}


	/******************************************
	 * Convert Byte array to String Value.
	 * @param strValue
	 * @param headerValue
	 * @param i
	 * @return
	 ********************************************/
	private static String getStringValue(String strValue, byte[] headerValue, int i) {

		int val = (int)headerValue[i];
		if(val>=0 && val<16) strValue += "0";				
		if(val < 0) {
			strValue += Integer.toHexString(val).substring(6, 8);
		}else{
			strValue += Integer.toHexString(val);
		}
		return strValue;
	}
}
