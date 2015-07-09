package com.odcgroup.mdf.editor.ui.viewers;

import org.eclipse.ui.views.properties.PropertySheetPage;

import com.odcgroup.mdf.editor.ui.providers.properties.MdfPropertySourceProvider;

/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfPropertySheetPage extends PropertySheetPage {

	/**
	 * Constructor for MdfPropertySheetPage
	 */
	public MdfPropertySheetPage() {
		super();
		setPropertySourceProvider(new MdfPropertySourceProvider());
	}

}
