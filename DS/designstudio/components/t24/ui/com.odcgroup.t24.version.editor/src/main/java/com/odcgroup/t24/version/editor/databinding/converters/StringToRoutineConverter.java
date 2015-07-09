package com.odcgroup.t24.version.editor.databinding.converters;

import org.eclipse.core.databinding.conversion.IConverter;

import com.odcgroup.t24.version.versionDSL.Routine;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;

/**
 * Convert String To Routine Type.
 * 
 * @author snn
 * 
 */
public class StringToRoutineConverter implements IConverter {

	@Override
	public Object getFromType() {
		return String.class;
	}

	@Override
	public Object getToType() {
		return Routine.class;
	}

	@Override
	public Object convert(Object fromObject) {
		if (getFromType() == String.class) {
			return createRotuine((String) fromObject);
		}
		return null;
	}

	/**
	 * create the Rotuine form the String
	 * 
	 * @param routineName
	 * @return Routine
	 */
	private Routine createRotuine(String routineName) {
		String[] routineNameArray = null;
		Routine routine = null;
		if (((String) routineName).endsWith(".b")) {
			routineNameArray = ((String) routineName).split(("\\.b"));
			routine = VersionDSLFactoryImpl.eINSTANCE.createJBCRoutine();
			if (routineNameArray != null && routineNameArray.length > 0) {
				routine.setName(routineNameArray[0]);
			}
		}
		if (((String) routineName).endsWith(".java")) {
			routineNameArray = ((String) routineName).split(("(.java)"));
			routine = VersionDSLFactoryImpl.eINSTANCE.createJavaRoutine();
			if (routineNameArray != null && routineNameArray.length > 0) {
				routine.setName(routineNameArray[0]);
			}
		}
		return routine;
	}

}
