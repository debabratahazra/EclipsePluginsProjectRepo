package com.odcgroup.pageflow.compare.provider;

import org.eclipse.emf.common.notify.AdapterFactory;

import com.odcgroup.pageflow.model.ViewState;
import com.odcgroup.pageflow.model.provider.ViewStateItemProvider;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class ViewStateCompareItemProvider extends ViewStateItemProvider {

	/**
	 * @param adapterFactory
	 */
	public ViewStateCompareItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * This returns the label text for the adapted class.
	 */
	public String getText(Object object) {
		String feature = PageflowToggleDisplayFeature.getDisplayFeature();
		String label = null;
		if (feature.equals("name")) {
			label = ((ViewState)object).getName();
		} else {
			label = ((ViewState)object).getDisplayName();
		}
		return label == null || label.length() == 0 ?
			getString("_UI_ViewState_type") :
			getString("_UI_ViewState_type") + " " + label;
	}

}
