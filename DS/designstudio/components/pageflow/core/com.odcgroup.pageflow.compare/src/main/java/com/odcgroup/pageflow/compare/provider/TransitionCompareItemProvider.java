package com.odcgroup.pageflow.compare.provider;

import org.eclipse.emf.common.notify.AdapterFactory;

import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.provider.TransitionItemProvider;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class TransitionCompareItemProvider extends TransitionItemProvider {

	/**
	 * @param adapterFactory
	 */
	public TransitionCompareItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * This returns the label text for the adapted class.
	 */
	public String getText(Object object) {
		String feature = PageflowToggleDisplayFeature.getDisplayFeature();
		String label = null;
		if (feature.equals("name")) {
			label = ((Transition)object).getName();
		} else {
			label = ((Transition)object).getDisplayName();
		}
		return label == null || label.length() == 0 ?
			getString("_UI_Transition_type") :
			getString("_UI_Transition_type") + " " + label;
	}

}
