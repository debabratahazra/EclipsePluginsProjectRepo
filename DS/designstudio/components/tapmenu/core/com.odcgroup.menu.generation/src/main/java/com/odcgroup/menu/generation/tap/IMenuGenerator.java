package com.odcgroup.menu.generation.tap;

import java.util.HashMap;

import org.eclipse.xtend.expression.Variable;
import org.eclipse.xtext.generator.IFileSystemAccess;

import com.odcgroup.menu.model.MenuRoot;

/**
 * @author pkk
 *
 */
public interface IMenuGenerator {	
	public void doGenerate(MenuRoot root, IFileSystemAccess fsa, HashMap<String, Variable> globalVars);
}
