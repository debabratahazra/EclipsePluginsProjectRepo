package com.odcgroup.workbench.ui.action.edit;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;

import com.odcgroup.workbench.core.InvalidMetamodelVersionException;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public interface IOfsModelResourcePaster {
	
	/**
	 * Invoked just after the model has been pasted in its target container
	 * @throws CoreException
	 */
	public void paste() throws IOException, InvalidMetamodelVersionException;

}
