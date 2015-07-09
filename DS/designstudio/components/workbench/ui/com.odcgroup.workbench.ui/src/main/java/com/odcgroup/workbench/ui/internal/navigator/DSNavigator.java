package com.odcgroup.workbench.ui.internal.navigator;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.navigator.CommonNavigator;

public class DSNavigator extends CommonNavigator {

	private String LINKING_ENABLED = "CommonNavigator.LINKING_ENABLED"; //$NON-NLS-1$ 

	// we override this method in order to select the "Link with Editor" feature on the very first start of DS
	@Override
	public void init(IViewSite aSite, IMemento aMemento)
			throws PartInitException {
		super.init(aSite, aMemento);
		memento = aMemento;
		if (memento != null) {
			Integer linkingEnabledInteger = memento.getInteger(LINKING_ENABLED);
			if(linkingEnabledInteger==null) {
				setLinkingEnabled(true);
			}
		} else {
			setLinkingEnabled(true);
		}
	}
}
