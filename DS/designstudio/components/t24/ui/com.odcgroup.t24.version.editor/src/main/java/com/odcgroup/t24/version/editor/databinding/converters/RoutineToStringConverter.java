package com.odcgroup.t24.version.editor.databinding.converters;

import org.eclipse.core.databinding.conversion.IConverter;

import com.odcgroup.t24.version.versionDSL.JBCRoutine;
import com.odcgroup.t24.version.versionDSL.JavaRoutine;
import com.odcgroup.t24.version.versionDSL.Routine;
/**
 * convert Routine to String.
 * @author satishnangi
 *
 */
public class RoutineToStringConverter implements IConverter {

    public RoutineToStringConverter(){
    
    }
    public Object getFromType() {
	return Routine.class;
    }

    @Override
    public Object getToType() {
	return String.class;
    }

    @Override
    public Object convert(Object fromObject) {
	if(getFromType()== Routine.class){
	    if(fromObject instanceof JavaRoutine){
		return ((JavaRoutine)fromObject).getName()+".java";
	    }
	    if(fromObject instanceof JBCRoutine){
		return ((JBCRoutine)fromObject).getName()+".b";
	    }

	}
	return null;
    }
 
}
