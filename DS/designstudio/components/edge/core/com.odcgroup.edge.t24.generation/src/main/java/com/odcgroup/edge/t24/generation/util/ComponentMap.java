package com.odcgroup.edge.t24.generation.util;

import java.util.List;
import java.util.Properties;

import com.acquire.util.StringUtils;

/**
 * TODO: Document me!
 *
 * @author simon.file
 *
 */
public class ComponentMap extends Properties {
	
	public enum ComponentType {
		VERSION, ENQUIRY, COMPOSITE;
	}
	
	public void setProperty(String p_irisName, String p_directory, String p_ifp, String p_component, ComponentType p_type, List<String> p_data)
	{
		// Append an 's' because this is what IRIS expects 
		
		String value = p_directory + "|" + p_ifp + "|" + p_component + "|";
		
		switch(p_type) 
		{
			case VERSION:
				value += "V";
				break;
			case ENQUIRY:
				value += "E";
				break;
            case COMPOSITE:
            	value += "C";
                break;
		}
		
		value += "|"; // Add empty entry for display type .. not used at the moment?
		
		value += "|"; // Start a pipe for any data
		
        if ( p_data != null && p_data.size() > 0 )
        {
            // FIXME: Currently assume no pipes or carets or equals (although Property escapes this) .. need to handle ..
            //
        	value += StringUtils.asSeperatedString( p_data, "^" );
        }
		
        // The value must be in sync with IRISMapper.EEntryValue
        //
		//this.setProperty(p_irisName + "s", value); no need to generate a duplicate with plural
		this.setProperty(p_irisName, value);
	}

}
