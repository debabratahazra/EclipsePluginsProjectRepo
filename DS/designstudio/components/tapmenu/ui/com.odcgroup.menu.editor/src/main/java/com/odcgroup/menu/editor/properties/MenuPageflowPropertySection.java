package com.odcgroup.menu.editor.properties;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

import com.odcgroup.menu.editor.dialog.PageflowSelectionDialogCreator;
import com.odcgroup.menu.model.MenuPackage;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.ToggleGroupWidget;

/**
 * @author pkk
 *
 */
public class MenuPageflowPropertySection extends MenuGeneralPropertySection {

	@Override
	protected void configureProperties() {
		super.configureProperties();
		
		// pageflow
		final EAttribute attribute = MenuPackage.eINSTANCE.getMenuItem_Pageflow();
		
		ToggleGroupWidget toggleWidget = new ToggleGroupWidget("Specify the location of the pageflow :", null) {
			
			
			@Override
			protected int getSelection(EObject element) {
				if (element != null) {
					String url = (String) element.eGet(attribute);
					if (url == null || url.trim().equals("")) {
						return -1;
					}
					if (url.startsWith("resource:") && (url.endsWith(".pageflow"))){
						return 0;
					} else {
						return 1;
					}
				}
				return -1;
			}

			@Override
			public String getConfirmToggleMessage(EObject element) {
				Object val =  element.eGet(attribute);
				if (val != null)
					return NLS.bind("Are you sure you want to specify different location than to {0}", val);
				else
					return null;
			}
			
		};
		
		toggleWidget.setLabel("Page Flow");
		
		GroupWidget groupOne = new GroupWidget(null, "Design Studio");
		groupOne.setFillBoth(false);
		BrowsePropertyWidget browse = new BrowsePropertyWidget(attribute, "Browse", "");
		browse.setFillHorizontal(true);
		browse.setBrowseOnly(false);
		browse.setBrowseDefault(true);
		browse.setSelectionDialogCreator(new PageflowSelectionDialogCreator());
		groupOne.addPropertyFeature(browse);
		toggleWidget.addReferenceWidget(groupOne, true);
		
		GroupWidget groupTwo = new GroupWidget(null, "Other");
		groupTwo.setFillBoth(false);
		SimpleTextWidget viewUri = new SimpleTextWidget(attribute, "URL");
		viewUri.setFillHorizontal(true);
		groupTwo.addPropertyFeature(viewUri);
		toggleWidget.addReferenceWidget(groupTwo);
		this.addPropertyFeature(toggleWidget);
	}

}
