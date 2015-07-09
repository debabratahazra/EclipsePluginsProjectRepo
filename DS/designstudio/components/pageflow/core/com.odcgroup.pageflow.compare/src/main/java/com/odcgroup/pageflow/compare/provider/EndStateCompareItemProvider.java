package com.odcgroup.pageflow.compare.provider;

import org.eclipse.emf.common.notify.AdapterFactory;

import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.provider.EndStateItemProvider;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class EndStateCompareItemProvider extends EndStateItemProvider {

	/**
	 * @param adapterFactory
	 */
	public EndStateCompareItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * This returns the label text for the adapted class.
	 */
	public String getText(Object object) {
		String feature = PageflowToggleDisplayFeature.getDisplayFeature();
		String label = null;
		if (feature.equals("name")) {
			label = ((EndState)object).getName();
		} else {
			label = ((EndState)object).getDisplayName();
		}
		return label == null || label.length() == 0 ?
			getString("_UI_EndState_type") :
			getString("_UI_EndState_type") + " " + label;
	}

}
