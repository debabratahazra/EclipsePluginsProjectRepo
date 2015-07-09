package com.odcgroup.pageflow.compare.internal.engine;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.workbench.compare.gmf.engine.GMFDiffEngineExtension;

/**
 *
 * @author pkk
 *
 */
public class PageflowDiffEngineExtension extends GMFDiffEngineExtension {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.compare.engine.GMFDiffEngineExtension#getAttributesToFilter()
	 */
	@Override
	public String[] getAttributesToFilter() {
		List<String> attributes = new ArrayList<String>();
		//attributes.add(PageflowPackage.eINSTANCE.getPageflow_ModifiedBy().getName());
		//attributes.add(PageflowPackage.eINSTANCE.getPageflow_ModifiedDate().getName());
		return attributes.toArray(new String[0]);
	}

}
