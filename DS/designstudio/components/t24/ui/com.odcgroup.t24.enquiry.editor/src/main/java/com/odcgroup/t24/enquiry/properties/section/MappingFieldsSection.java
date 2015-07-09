package com.odcgroup.t24.enquiry.properties.section;

import javax.inject.Inject;

import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.odcgroup.t24.enquiry.properties.filter.IPropertyFilter;
import com.odcgroup.t24.enquiry.properties.filter.MappingFieldsSectionFilter;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;


public class MappingFieldsSection extends AbstractSection {

	@Inject
	protected LanguageXtextEObjectSearch eObjectSearch;
	
	@Inject 
	protected GlobalDescriptionLabelProvider globalDescriptionLabelProvider;	

	
	@Override
	public IPropertyFilter getPropertyFilter() {
		return new MappingFieldsSectionFilter(eObjectSearch, globalDescriptionLabelProvider);
	}
}