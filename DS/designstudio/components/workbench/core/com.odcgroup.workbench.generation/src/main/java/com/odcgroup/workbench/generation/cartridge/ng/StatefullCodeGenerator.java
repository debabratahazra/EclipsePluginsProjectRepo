package com.odcgroup.workbench.generation.cartridge.ng;

import org.eclipse.core.runtime.CoreException;

/**
 * @author atripod
 */
public interface StatefullCodeGenerator extends CodeGenerator2 {
	
	void beforeGeneration() throws CoreException;
	
	void afterGeneration() throws CoreException;

}
