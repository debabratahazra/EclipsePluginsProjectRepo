package com.odcgroup.iris.generator;

import java.util.HashMap;

import org.eclipse.xtend.expression.Variable;
import org.eclipse.xtext.generator.IFileSystemAccess;

import com.odcgroup.t24.enquiry.util.EMEntityModel;

/**
 * @author phanikumark
 */
public interface IIRISGenerator {
	
	public void doGenerate(EMEntityModel entityModel, IFileSystemAccess fsa, HashMap<String, Variable> globalVars);

}
