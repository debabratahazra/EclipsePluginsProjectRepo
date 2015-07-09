package com.odcgroup.t24.version.editor.databinding.converters;

import org.eclipse.core.databinding.conversion.Converter;

import com.odcgroup.t24.version.versionDSL.YesNo;
/**
 * converter to Convert YesNo type to Boolean.
 * @author satishnangi
 *
 */
public class YesNoToBooleanConverter extends Converter {

    public YesNoToBooleanConverter() {
	super(YesNo.class ,Boolean.class);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Object convert(Object fromObject) {
	if(fromObject instanceof YesNo){
	    if(((YesNo)fromObject).getValue()==YesNo.YES_VALUE){
		return Boolean.TRUE;
	    } else {
		return Boolean.FALSE;
	    }
	}
	return null;
    }

}
