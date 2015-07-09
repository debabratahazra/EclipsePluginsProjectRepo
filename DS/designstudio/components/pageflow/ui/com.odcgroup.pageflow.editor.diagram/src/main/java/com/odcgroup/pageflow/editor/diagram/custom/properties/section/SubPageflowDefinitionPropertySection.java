package com.odcgroup.pageflow.editor.diagram.custom.properties.section;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.pageflow.editor.diagram.custom.dialog.PageflowSelectionDialogCreator;
import com.odcgroup.pageflow.editor.diagram.custom.dialog.TransitionMappingDefinitionDialogCreator;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.TransitionMapping;
import com.odcgroup.workbench.editors.properties.item.PropertyTableColumn;
import com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget;

public class SubPageflowDefinitionPropertySection extends AbstractPropertiesSection {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.section.AbstractPropertiesSection#configureProperties()
	 */
	@Override
	protected void configureProperties() {
		setUseThreeFourthLayout(true);
		SimpleGroupWidget group = new SimpleGroupWidget(null);	
		PageflowPackage eInstance = PageflowPackage.eINSTANCE;
		BrowsePropertyWidget pageflowBrowse = new BrowsePropertyWidget(eInstance.getSubPageflowState_SubPageflow(),
				Messages.SubPageflowGeneralPropertySectionGrpLabel);
		pageflowBrowse.setFillHorizontal(true);
		pageflowBrowse.setSelectionDialogCreator(new PageflowSelectionDialogCreator());
		group.addPropertyFeature(pageflowBrowse);
		
		EReference ref = eInstance.getSubPageflowState_TransitionMappings();
		
		TablePropertyWidget tProperty = new TablePropertyWidget(ref, Messages.SubPageflowGeneralPropertySectionMappingLabel) {

			/* (non-Javadoc)
			 * @see com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget#getPropertyTableColumnText(java.lang.Object, int)
			 */
			@Override
			public String getPropertyTableColumnText(Object element,
					int columnIndex) {
				if (element != null) {
					TransitionMapping mapping = (TransitionMapping) element;
					EcoreUtil.resolveAll(mapping);
					if (columnIndex == 0) {
						EndState endState = mapping.getEndState();
						return mapping.getEndState().getDisplayName();
					} else if (columnIndex == 1) {
						return mapping.getTransition().getDisplayName();
					}
				}
				return super.getPropertyTableColumnText(element, columnIndex);
			}
			
		};
		tProperty.addTableColumn(new PropertyTableColumn(Messages.SubPageflowDefinitionPropertySectionEndStateLabel, 
				eInstance.getTransitionMapping_EndState(), 5));
		tProperty.addTableColumn(new PropertyTableColumn(Messages.SubPageflowDefinitionPropertySectionTransitionLabel, 
				eInstance.getTransitionMapping_Transition(), 5));
		tProperty.setDialogCreator(new TransitionMappingDefinitionDialogCreator());
		group.addPropertyFeature(tProperty);
		
		this.addPropertyFeature(group);
	}


}
