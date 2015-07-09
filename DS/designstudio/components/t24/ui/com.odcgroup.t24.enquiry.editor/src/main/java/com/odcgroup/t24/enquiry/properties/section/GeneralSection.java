package com.odcgroup.t24.enquiry.properties.section;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.odcgroup.t24.enquiry.editor.part.DrillDownEditPart;
import com.odcgroup.t24.enquiry.editor.part.EnquiryDiagramEditPart;
import com.odcgroup.t24.enquiry.editor.part.FieldEditPart;
import com.odcgroup.t24.enquiry.editor.part.FixedSelectionEditPart;
import com.odcgroup.t24.enquiry.editor.part.RoutineEditPart;
import com.odcgroup.t24.enquiry.editor.part.SelectionCriteriaEditPart;
import com.odcgroup.t24.enquiry.editor.part.SelectionEditPart;
import com.odcgroup.t24.enquiry.editor.part.ToolEditPart;
import com.odcgroup.t24.enquiry.editor.part.WebServiceEditPart;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.properties.filter.EnquiryGeneralSectionFilter;
import com.odcgroup.t24.enquiry.properties.filter.IPropertyFilter;
import com.odcgroup.t24.enquiry.properties.sources.DrilldownPropertySourceWrapper;
import com.odcgroup.t24.enquiry.properties.sources.EnquiryPropertySourceWrapper;
import com.odcgroup.t24.enquiry.properties.sources.FieldPropertySourceWrapper;
import com.odcgroup.t24.enquiry.properties.sources.FixedSelectionPropertySourceWrapper;
import com.odcgroup.t24.enquiry.properties.sources.SelectionCriteriaPropertySourceWrapper;
import com.odcgroup.t24.enquiry.properties.sources.SelectionPropertySourceWrapper;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

/**
 * 
 * @author phanikumark
 * 
 */
public class GeneralSection extends AbstractSection {
	
	@Inject
	private LanguageXtextEObjectSearch eObjectSearch;
	
	@Inject 
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;	
    
	@Override
	public IPropertyFilter getPropertyFilter() {
		return new EnquiryGeneralSectionFilter(eObjectSearch, globalDescriptionLabelProvider);
	}
	
	@Override
	public IPropertySource getPropertySource(Object object) {
		IPropertySource source = super.getPropertySource(object);
		if( object instanceof EnquiryDiagramEditPart  ){
			Enquiry enquiry = (Enquiry)((EnquiryDiagramEditPart)object).getModel();
			source=new  EnquiryPropertySourceWrapper(source,modelPropertySourceProvider,enquiry);
			eObjectSearch.setContext(enquiry);
		} else if( object instanceof ToolEditPart  || object instanceof RoutineEditPart || object instanceof WebServiceEditPart ){
			Enquiry enquiry = (Enquiry)((EObject)((EditPart)object).getModel()).eContainer();
			source=new  EnquiryPropertySourceWrapper(source,modelPropertySourceProvider, enquiry);
		} else if( object instanceof FieldEditPart ){
			Field field = (Field)((FieldEditPart)object).getModel();
			source= new FieldPropertySourceWrapper(source,modelPropertySourceProvider,field);
		} else if( object instanceof SelectionEditPart){
			source = new SelectionPropertySourceWrapper(source);
		} else if (object instanceof DrillDownEditPart) {
			Object model = ((DrillDownEditPart)object).getModel();
			source = new DrilldownPropertySourceWrapper(source, modelPropertySourceProvider, (EObject) model);
		} else if (object instanceof SelectionCriteriaEditPart) {
			Object model = ((SelectionCriteriaEditPart)object).getModel();
			source = new SelectionCriteriaPropertySourceWrapper(source, (SelectionCriteria) model);
		} else if (object instanceof FixedSelectionEditPart) {
			Object model = ((FixedSelectionEditPart) object).getModel();
			source = new FixedSelectionPropertySourceWrapper(source, (FixedSelection) model);
		}
		return source;
	}


}
