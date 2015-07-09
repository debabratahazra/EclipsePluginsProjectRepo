package com.odcgroup.pageflow.compare.provider;

import org.eclipse.emf.common.notify.AdapterFactory;

import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.provider.InitStateItemProvider;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class InitStateCompareItemProvider extends InitStateItemProvider {

	/**
	 * @param adapterFactory
	 */
	public InitStateCompareItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * This returns the label text for the adapted class.
	 */
	public String getText(Object object) {
		String feature = PageflowToggleDisplayFeature.getDisplayFeature();
		String label = null;
		if (feature.equals("name")) {
			label = ((InitState)object).getName();
		} else {
			label = ((InitState)object).getDisplayName();
		}
		return label == null || label.length() == 0 ?
			getString("_UI_InitState_type") :
			getString("_UI_InitState_type") + " " + label;
	}

}
