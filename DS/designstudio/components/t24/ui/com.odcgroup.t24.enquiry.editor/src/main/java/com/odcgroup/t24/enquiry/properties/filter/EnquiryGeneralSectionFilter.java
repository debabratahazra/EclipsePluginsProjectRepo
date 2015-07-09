package com.odcgroup.t24.enquiry.properties.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.WebService;
import com.odcgroup.t24.enquiry.properties.descriptors.providers.CustomPropertyDescriptorsProvider;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

public class EnquiryGeneralSectionFilter extends AbstractPropertyFilter {
	
	EnquiryPackage pkg = EnquiryPackage.eINSTANCE;
	private LanguageXtextEObjectSearch eObjectSearch;
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;
	
	public EnquiryGeneralSectionFilter(LanguageXtextEObjectSearch eObjectSearch, GlobalDescriptionLabelProvider globalDescriptionLabelProvider) {
		this.eObjectSearch = eObjectSearch;
		this.globalDescriptionLabelProvider = globalDescriptionLabelProvider;
	}

	@Override
	public List<EStructuralFeature> getFilterProperties(EObject model) {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		if (model instanceof Enquiry) {
			list.add(pkg.getEnquiry_Name());
			list.add(pkg.getEnquiry_FileName());
			list.add(pkg.getEnquiry_Attributes());
			list.add(pkg.getEnquiry_GenerateIFP());
			list.add(pkg.getEnquiry_ServerMode());
			list.add(pkg.getEnquiry_EnquiryMode());
		}
		if (model instanceof FixedSelection) {
			list = getFixedSelectionFilterProperties();
		}
		if (model instanceof Field) {
			list = getFieldFilterPropertiesList();
		}
		if(model instanceof WebService){
			list.add(pkg.getWebService_WebServiceActivity());
			list.add(pkg.getWebService_WebServiceDescription());
			list.add(pkg.getWebService_PublishWebService());
			list.add(pkg.getWebService_WebServiceNames());
		}
		if (model instanceof DrillDown) {
			list.add(pkg.getDrillDown_LabelField());
			list.add(pkg.getDrillDown_Parameters());
			list.add(pkg.getDrillDown_Image());
		}
		return list;
	}

	/**
	 * @return
	 */
	private List<EStructuralFeature> getFieldFilterPropertiesList() {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		list.add(pkg.getField_Name());
		list.add(pkg.getField_Comments());
		return list;
	}

	/**
	 * get filter properties for FixedSelection.
	 * 
	 * @param fixedSelection
	 * @return
	 */
	private List<EStructuralFeature> getFixedSelectionFilterProperties() {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		list.add(pkg.getFixedSelection_Field());
		list.add(pkg.getFixedSelection_Operand());
		list.add(pkg.getFixedSelection_Values());
		return list;
	}

	@Override
	protected IPropertyDescriptor[] customPropertyDecriptors(IPropertyDescriptor[] propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> propertyList = Arrays.asList(propertyDescriptors);
		CustomPropertyDescriptorsProvider provider = new CustomPropertyDescriptorsProvider(eObjectSearch, globalDescriptionLabelProvider);
		List<IPropertyDescriptor> custompropertyList =  provider.getCustomPropertyDescritpors(propertyList, model);  
		return custompropertyList.toArray(new IPropertyDescriptor[0]);
	}
	
	
}
