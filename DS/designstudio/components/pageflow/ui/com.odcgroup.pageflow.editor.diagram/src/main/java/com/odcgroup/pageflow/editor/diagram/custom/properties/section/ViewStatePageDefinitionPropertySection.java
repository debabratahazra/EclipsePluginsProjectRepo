package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.osgi.util.NLS;

import com.odcgroup.pageflow.editor.diagram.custom.pageintegration.PageSelectionDialogCreator;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.View;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.GroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.ToggleGroupWidget;

/**
 * @author pkk
 *
 */
public class ViewStatePageDefinitionPropertySection extends AbstractPropertiesSection {
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.gmf.editors.properties.AbstractPropertiesSection#configureProperties()
	 */
	protected void configureProperties() {
		setUseThreeFourthLayout(true);
		final EReference reference = PageflowPackage.eINSTANCE.getViewState_View();
		final EAttribute attribute = PageflowPackage.eINSTANCE.getView_Url();
		
		ToggleGroupWidget toggleWidget = new ToggleGroupWidget("Specify the location of the page", null) {
			@Override
			protected int getSelection(EObject element) {
				if (element != null) {
					View view = (View) element.eGet(reference);
					if (view != null) {
						String url = view.getUrl();
						if (url != null && !url.trim().equals("")) {
							if (url.startsWith("resource:")
									&& (url.endsWith(".page") 
											|| url.endsWith(".module"))){
								return 0;
							} else {
								return 1;
							}
						} 
					}
				}
				return -1;
			}

			@Override
			public String getConfirmToggleMessage(EObject element) {
				EObject obj = (EObject) element.eGet(reference);
				Object val = null;
				if (obj != null) {
					val = obj.eGet(attribute);
					return NLS.bind(Messages.ViewStateViewDefinitionConfirmDialogMsg, val);
				}
				return null;
			}
			
		};
		
		toggleWidget.setLabel("Page Flow");

		GroupWidget groupOne = new GroupWidget(reference, Messages.ViewStateViewDefinitionDSTypeLabel);
		groupOne.setFillBoth(false);
		BrowsePropertyWidget browse = new BrowsePropertyWidget(attribute, Messages.ViewStateViewDefinitionBrowse, 
				Messages.ViewStateViewDefinitionBrowseToolTip);
		browse.setFillHorizontal(true);
		browse.setSelectionDialogCreator(new PageSelectionDialogCreator());
		groupOne.addPropertyFeature(browse);
		toggleWidget.addReferenceWidget(groupOne, true);
		
		GroupWidget groupTwo = new GroupWidget(reference, Messages.ViewStateViewDefinitionOCSTypeLabel);
		groupTwo.setFillBoth(false);
		SimpleTextWidget viewUri = new SimpleTextWidget(attribute, Messages.ViewStateTechnicalPropertySectionURILabel);
		viewUri.setFillHorizontal(true);
		groupTwo.addPropertyFeature(viewUri);
		toggleWidget.addReferenceWidget(groupTwo);
		
		addPropertyFeature(toggleWidget);
	}
	
}
