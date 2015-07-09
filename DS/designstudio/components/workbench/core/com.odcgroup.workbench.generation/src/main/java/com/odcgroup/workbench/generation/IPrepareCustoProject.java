package com.odcgroup.workbench.generation;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IProject;

/**
 * @author yan
 */
public interface IPrepareCustoProject {

	void copyNonCustomizedModelsFromStandard(IProject project) throws IOException;
	
}
