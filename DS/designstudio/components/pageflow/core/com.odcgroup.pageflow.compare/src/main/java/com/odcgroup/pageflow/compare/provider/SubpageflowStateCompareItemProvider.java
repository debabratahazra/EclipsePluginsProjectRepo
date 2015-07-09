package com.odcgroup.pageflow.compare.provider;

import org.eclipse.emf.common.notify.AdapterFactory;

import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.provider.SubPageflowStateItemProvider;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class SubpageflowStateCompareItemProvider extends SubPageflowStateItemProvider {

	/**
	 * @param adapterFactory
	 */
	public SubpageflowStateCompareItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * This returns the label text for the adapted class.
	 */
	public String getText(Object object) {
		String feature = PageflowToggleDisplayFeature.getDisplayFeature();
		String label = null;
		if (feature.equals("name")) {
			label = ((SubPageflowState)object).getName();
		} else {
			label = ((SubPageflowState)object).getDisplayName();
		}
		return label == null || label.length() == 0 ?
			getString("_UI_SubPageflowState_type") :
			getString("_UI_SubPageflowState_type") + " " + label;
	}


}
