package com.odcgroup.pageflow.compare.provider;

import org.eclipse.emf.common.notify.AdapterFactory;

import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.provider.DecisionStateItemProvider;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class DecisionStateCompareItemProvider extends DecisionStateItemProvider {

	/**
	 * @param adapterFactory
	 */
	public DecisionStateCompareItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	
	/**
	 * This returns the label text for the adapted class.
	 */
	public String getText(Object object) {
		String feature = PageflowToggleDisplayFeature.getDisplayFeature();
		String label = null;
		if (feature.equals("name")) {
			label = ((DecisionState)object).getName();
		} else {
			label = ((DecisionState)object).getDisplayName();
		}
		return label == null || label.length() == 0 ?
			getString("_UI_DecisionState_type") :
			getString("_UI_DecisionState_type") + " " + label;
	}

}
