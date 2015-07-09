package com.odcgroup.t24.version.editor.databinding.converters;

import org.eclipse.core.databinding.conversion.Converter;

import com.odcgroup.t24.version.versionDSL.YesNo;
/**
 * convert the boolean value to YesNo type.
 * @author satishnangi
 *
 */
public class BooleanToYesNoTypeConverter extends Converter {

    public BooleanToYesNoTypeConverter() {
	super(Boolean.class,YesNo.class);
    }

    @Override
    public Object convert(Object fromObject) {
	if(fromObject instanceof Boolean){
	    if(((Boolean)fromObject).booleanValue()){
		return YesNo.YES;
	    }
	}
	return YesNo.NO;
    }

}
