package com.odcgroup.translation.ui.editor.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.odcgroup.workbench.editors.properties.AdvancedTabbedPropertySheetPage;
import com.odcgroup.workbench.editors.properties.ISelectionAdapter;

/**
 *
 * @author pkk
 *
 */
public class TranslationPropertySheetPage extends AdvancedTabbedPropertySheetPage {

	/**
	 * @param tabbedPropertySheetPageContributor
	 */
	public TranslationPropertySheetPage(ITabbedPropertySheetPageContributor tabbedPropertySheetPageContributor) {
		super(tabbedPropertySheetPageContributor);
		setSelectionAdapter(new ISelectionAdapter() {			
			@Override
			public EObject adaptModel(Object object) {
				return null;
			}
		});
	}

}
