package com.tel.autosysframework.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 * @author debaratahazra
 *
 Fixed Config Parameter:
MB_SIZE 8        
FRAME_WIDTH 352
FRAME_HEIGHT 288
FRAME_WIDTHxHEIGHT 101376
Input YUV File Path
Output YUV File Path

Variable Parameter:

Key	 	Value ----- Map Interface
==========================
1 	NUM_YETA  (1- TOTAL_FRAMES )
2 	Threshold  (15 - 30)
3 	NO_OBJ_LABEL  (7 - 100)
4 	MAX_OBJ   (6 - 10)
5 	MAX_MB_OBJ   (18 - 200)
6 	MAX_FOREGR_PIX_MB   (6 - 10)    
7 	TOTAL_FRAMES (1 - 1000) 
8 	START_ROW (0 to 287)
9 	START_COL (0 to 351)
10 	END_ROW (0 to 287)
11 	END_COL (0 to 351)


12 RECT_HEIGHTxWIDTH
 */


public class VLCD_SaveConfig {
	
	@SuppressWarnings("unchecked")
	public static  Set setKeys; 
	public static boolean MonitorAreaSaveState = false;
	public static boolean VideoAnalysisSaveState = false;
	private static Map<Integer, String> map;

	static{
		map = new TreeMap<Integer, String>();		
	}

	private static void setKeys(){
		setKeys = map.keySet();
	}

	public static void putMap(Integer key, String value){
		map.put(key, value);
		setKeys();
	}
	
	/**
	 * Return value w.r.t. key
	 * @param keyVal
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMap(Integer keyVal){
		//Return value w.r.t. key
		if(setKeys==null) return null;
		for(Iterator it=setKeys.iterator(); it.hasNext();){
			Integer key=(Integer)it.next();
			String value=(String)map.get(key);			
			if(keyVal == key){
				return value;
			}			
		}
		return null;
	}

}
