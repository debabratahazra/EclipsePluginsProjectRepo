package com.odcgroup.edge.t24.generation.enquiry;

import java.util.Comparator;

class LinearTableComparator implements Comparator<String>{
	 
    @Override
    public int compare(String str1, String str2) {
    	
    	int rowNoStr1 = Integer.parseInt(str1.split("_")[0]);
    	int colNoStr1 = Integer.parseInt(str1.split("_")[1]);
    	int rowNoStr2 = Integer.parseInt(str2.split("_")[0]);
    	int colNoStr2 = Integer.parseInt(str2.split("_")[1]);
    	if(rowNoStr1 > rowNoStr2 )
    	{
    		return 1;
    	}
    	else
    	{
    		if(rowNoStr1 == rowNoStr2 && colNoStr1 > colNoStr2)
    		{
    			return 1;
    		}
    		else
    		{
    			return -1;
    		}
    		
    	}
    }
     
}
