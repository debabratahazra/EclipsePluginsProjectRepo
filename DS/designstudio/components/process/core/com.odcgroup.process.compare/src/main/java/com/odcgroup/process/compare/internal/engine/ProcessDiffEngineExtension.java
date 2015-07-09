package com.odcgroup.process.compare.internal.engine;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.workbench.compare.gmf.engine.GMFDiffEngineExtension;

/**
 *
 * @author pkk
 *
 */
public class ProcessDiffEngineExtension extends GMFDiffEngineExtension {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.engine.GMFDiffEngineExtension#getAttributesToFilter()
	 */
	@Override
	public String[] getAttributesToFilter() {
		List<String> attributes = new ArrayList<String>();
		//attributes.add(ProcessPackage.eINSTANCE.getProcess_ModifiedBy().getName());
		//attributes.add(ProcessPackage.eINSTANCE.getProcess_ModifiedDate().getName());
		return attributes.toArray(new String[0]);
	}

}
