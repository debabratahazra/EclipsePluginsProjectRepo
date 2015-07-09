package com.odcgroup.page.model.provider;

import org.eclipse.emf.common.notify.AdapterFactory;

import com.odcgroup.page.model.Widget;

/**
 * A Property provider used within the Page Designer. We do not touch the generated version since it might be useful in
 * other situations, for example, for editing files.
 * 
 * @author Gary Hayes
 */
public class PageWidgetItemProvider extends WidgetItemProvider {

	/**
	 * Creates a new PageWidgetItemProvider.
	 * 
	 * @param adapterFactory
	 *            The AdapterFactory
	 */
	public PageWidgetItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * Gets the text of the Widget.
	 * 
	 * @param object
	 *            The Widget
	 * @return String The display name of the Widget
	 */
	public String getText(Object object) {
		Widget w = (Widget) object;
		String label = w.getType().getDisplayName();
		return label;
	}

	/**
	 * Override the base class version. We don't want the Model displayed in the results. Therefore if the Widget is the
	 * root one we return the eResource as its parent.
	 * 
	 * @param object
	 *            The Object to get the parent for
	 * @return Object The parent Object
	 */
	public Object getParent(Object object) {
		Widget w = (Widget) object;
		if (w.getParent() == null) {
			//return w.eResource();
			return w.getModel();
		}

		return w.getParent();
	}
}
