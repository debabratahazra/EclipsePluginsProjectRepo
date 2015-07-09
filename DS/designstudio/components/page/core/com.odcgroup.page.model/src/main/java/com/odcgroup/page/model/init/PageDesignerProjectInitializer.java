package com.odcgroup.page.model.init;

import com.odcgroup.workbench.core.init.DefaultModelProjectInitializer;

/**
 * The base class for Fragment, Module and Page project initializer.
 * 
 * @author atr
 */
public abstract class PageDesignerProjectInitializer extends DefaultModelProjectInitializer {

	/**
	 * Creates a new FragmentProjectInitializer.
	 * 
	 * @param aModelName
	 * @param anArtifactName
	 */
	public PageDesignerProjectInitializer(String aModelName) {
		super(aModelName);
	}

}
