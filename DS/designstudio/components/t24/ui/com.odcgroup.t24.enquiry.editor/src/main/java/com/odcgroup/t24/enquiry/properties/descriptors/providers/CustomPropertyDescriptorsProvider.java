package com.odcgroup.t24.enquiry.properties.descriptors.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.t24.enquiry.enquiry.WebService;
import com.odcgroup.t24.enquiry.properties.descriptors.DrillDownFieldPropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.DrillDownPropertyFieldDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.DrilldownParameterAutoInputPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.DrilldownParameterFunctionPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.DrilldownParameterPWActivityPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.DrilldownParameterRunPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.DrilldownParameterVariablePropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.DrilldownTypePropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.EnquiryAttributePropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.EnquiryCompaniesPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.EnquiryFieldPropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.EnquiryFileTypePropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.EnquiryFileVersionOptionPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.EnquiryPropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.FileNamePropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.MandatoryPropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.MultiValueSelectionPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.PopupDropDownPropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.SelectionCriteriaFieldPropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.properties.descriptors.SelectionOperandsPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.descriptors.TargetApplicationPropertyDescriptor;
import com.odcgroup.t24.enquiry.properties.filter.ICustomPropertyDescriptorProvider;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

/**
 *
 *
 */
public class CustomPropertyDescriptorsProvider implements ICustomPropertyDescriptorProvider {
	
	private EnquiryPackage pkg = EnquiryPackage.eINSTANCE;
	private LanguageXtextEObjectSearch eObjectSearch;
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;
	
	public CustomPropertyDescriptorsProvider(LanguageXtextEObjectSearch eObjectSearch, GlobalDescriptionLabelProvider globalDescriptionLabelProvider) {
		this.eObjectSearch = eObjectSearch;
		this.globalDescriptionLabelProvider = globalDescriptionLabelProvider;
	}

	@Override
	public List<IPropertyDescriptor> getCustomPropertyDescritpors(List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		if (model instanceof Selection) {
			custompropertyList.addAll(getSelectionCustomPropertyDescriptors(propertyDescriptors, model));
		} else if (model instanceof Enquiry) {
			custompropertyList.addAll(getEnquiryCustomPropertyDescriptors(propertyDescriptors, model));
		} else if (model instanceof FixedSelection) {
			custompropertyList.addAll(getFixedSelectionCustomPropertyDescriptors(propertyDescriptors, model));			
		} else if (model instanceof Field) {
			custompropertyList.addAll(getFieldCustomPropertyDescriptors(propertyDescriptors, model));	
		} else if (model instanceof Tool) {
			custompropertyList.addAll(getToolCustomPropertyDescriptors(propertyDescriptors, model));	
		} else if (model instanceof Routine) {
			custompropertyList.addAll(getRoutineCustomPropertyDescriptors(propertyDescriptors, model));	
		} else if (model instanceof Target) {
			custompropertyList.addAll(getTargetCustomPropertyDescriptors(propertyDescriptors, model));	
		} else if (model instanceof WebService) {
			custompropertyList.addAll(getWebServiceCustomPropertyDescriptors(propertyDescriptors, model));	
		} else if (model instanceof DrillDown) {
			custompropertyList.addAll(getDrilldownCustomPropertyDescriptors(propertyDescriptors, model));	
		} else if (model instanceof SelectionCriteria) { 
			custompropertyList.addAll(getSelectionCriteriaPropertyDescriptors(propertyDescriptors, model));
		} else {
			custompropertyList.addAll(propertyDescriptors);
		}
		return custompropertyList;
	}
	
	private Collection<? extends IPropertyDescriptor> getDrilldownCustomPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		DrillDown dd = (DrillDown) model;
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		custompropertyList.add(new DrilldownTypePropertyDescriptor(dd, eObjectSearch, globalDescriptionLabelProvider));
		for (IPropertyDescriptor descriptor : propertyDescriptors) {
			if (descriptor.getId().equals(pkg.getDrillDown_LabelField().getName())) {
				custompropertyList.add(new DrillDownFieldPropertyDescriptorWrapper(dd));
			} else {
				custompropertyList.add(descriptor);
			}
		}
		custompropertyList.add(new DrilldownParameterFunctionPropertyDescriptor());
		custompropertyList.add(new DrilldownParameterAutoInputPropertyDescriptor());
		custompropertyList.add(new DrilldownParameterVariablePropertyDescriptor(model));
		custompropertyList.add(new DrilldownParameterRunPropertyDescriptor());
		custompropertyList.add(new DrilldownParameterPWActivityPropertyDescriptor());
		custompropertyList.add(new DrillDownPropertyFieldDescriptorWrapper(model));
		return custompropertyList;
	}

	private Collection<? extends IPropertyDescriptor> getWebServiceCustomPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		for (IPropertyDescriptor descriptor : propertyDescriptors) {
			IPropertyDescriptor wrapperDescriprot = null;
			if (descriptor.getId().equals(pkg.getWebService_WebServiceActivity().getName())) {
				wrapperDescriprot = new EnquiryPropertyDescriptorWrapper(descriptor, "Activity");
				custompropertyList.add(wrapperDescriprot);
			}
			if (descriptor.getId().equals(pkg.getWebService_WebServiceDescription().getName())) {
				wrapperDescriprot = new EnquiryPropertyDescriptorWrapper(descriptor, "Description");
				custompropertyList.add(wrapperDescriprot);
			}
			if (descriptor.getId().equals(pkg.getWebService_PublishWebService().getName())) {
				wrapperDescriprot = new EnquiryPropertyDescriptorWrapper(descriptor, "Expose");
				custompropertyList.add(wrapperDescriprot);
			}
			if (descriptor.getId().equals(pkg.getWebService_WebServiceNames().getName())) {
				WebService webService = (WebService)model;
				wrapperDescriprot = new MultiValueSelectionPropertyDescriptor("Web Service Names", pkg
						.getWebService_WebServiceNames().getName(), "Service Name", webService.getWebServiceNames(),
						true);
				custompropertyList.add(wrapperDescriprot);
			}
		}
	
		return custompropertyList;
	}

	private Collection<? extends IPropertyDescriptor> getTargetCustomPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		for (IPropertyDescriptor descriptor : propertyDescriptors) {
			IPropertyDescriptor wrapperDescriptor = null;
			if (descriptor.getId().equals(pkg.getTarget_Application().getName())) {
				wrapperDescriptor = new TargetApplicationPropertyDescriptor(descriptor);
				custompropertyList.add(wrapperDescriptor);
			} else {
				custompropertyList.add(descriptor);
			}
		}	
		return custompropertyList;	
	}


	private Collection<? extends IPropertyDescriptor> getRoutineCustomPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		custompropertyList.addAll(propertyDescriptors);
		custompropertyList.add(new PropertyDescriptor(model,"Type"));
		return custompropertyList;	
	}

	private Collection<? extends IPropertyDescriptor> getToolCustomPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		for (IPropertyDescriptor descriptor : propertyDescriptors) {
			IPropertyDescriptor wrapperDescriprot = null;
			if (descriptor.getId().equals(pkg.getTool_Command().getName())) {
				Tool tool = (Tool)model;
				wrapperDescriprot = new MultiValueSelectionPropertyDescriptor("Tool Command Names", tool,
						descriptor.getDisplayName(), tool.getCommand(), false);
				custompropertyList.add(wrapperDescriprot);
			} else {
				custompropertyList.add(descriptor);
			}
		}
		return custompropertyList;	
	}


	private Collection<? extends IPropertyDescriptor> getFieldCustomPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		for (IPropertyDescriptor descriptor : propertyDescriptors) {
			IPropertyDescriptor wrapperDescriprot = null;
			if (descriptor.getId().equals(pkg.getField_Name().getName())) {
				wrapperDescriprot = new EnquiryFieldPropertyDescriptorWrapper(descriptor ,"Field Name" ,model);
				custompropertyList.add(wrapperDescriprot);
			} else {
				custompropertyList.add(descriptor);
			}
		}
		return custompropertyList;
	}

	private Collection<? extends IPropertyDescriptor> getFixedSelectionCustomPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		for (IPropertyDescriptor descriptor : propertyDescriptors) {
			IPropertyDescriptor wrapperDescriptor = null;
			if (descriptor.getId().equals(pkg.getFixedSelection_Values().getName())) {
				FixedSelection fixedSelection = (FixedSelection)model;
				wrapperDescriptor = new MultiValueSelectionPropertyDescriptor("Predefined Field Values",
						EnquiryPackage.eINSTANCE.getFixedSelection_Values().getName(), "Values",
						fixedSelection.getValues(), false);
				custompropertyList.add(wrapperDescriptor);
			} else if(descriptor.getId().equals(pkg.getFixedSelection_Field().getName())){
				wrapperDescriptor = new EnquiryFieldPropertyDescriptorWrapper(descriptor ,model);
				custompropertyList.add(wrapperDescriptor);
			} else {
				custompropertyList.add(descriptor);
			}
		}	
		return custompropertyList;
	}
	
	private Collection<? extends IPropertyDescriptor> getSelectionCriteriaPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		SelectionCriteria criteria = (SelectionCriteria) model;
		Enquiry enquiry = (Enquiry) criteria.eContainer().eContainer();
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		for (IPropertyDescriptor descriptor : propertyDescriptors) {
			IPropertyDescriptor wrapperDescriptor = null;
			if (descriptor.getId().equals(pkg.getSelectionCriteria_Field().getName())){
				wrapperDescriptor = new SelectionCriteriaFieldPropertyDescriptorWrapper(descriptor, enquiry, criteria.getField());
				custompropertyList.add(wrapperDescriptor);
			} else if(descriptor.getId().equals(pkg.getSelectionCriteria_Values().getName())) { 
				wrapperDescriptor = new MultiValueSelectionPropertyDescriptor("Selection Criteria Values",
						EnquiryPackage.eINSTANCE.getSelectionCriteria_Values().getName(), "Values",
						criteria.getValues(), true);
				custompropertyList.add(wrapperDescriptor);
			} else {
				custompropertyList.add(descriptor);
			}
		}	
		return custompropertyList;
	}

	private Collection<? extends IPropertyDescriptor> getEnquiryCustomPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		for (IPropertyDescriptor descriptor : propertyDescriptors) {
			IPropertyDescriptor wrapperDescriptor = null;
			if (descriptor.getId().equals(pkg.getEnquiry_FileName().getName())) {
				wrapperDescriptor = new FileNamePropertyDescriptorWrapper(descriptor);
				custompropertyList.add(wrapperDescriptor);
			} else if (descriptor.getId().equals(pkg.getEnquiry_Attributes().getName())) {
				wrapperDescriptor = new EnquiryAttributePropertyDescriptorWrapper(descriptor,model);
				custompropertyList.add(wrapperDescriptor);
			} else {
				custompropertyList.add(descriptor);
			}
		}
		custompropertyList.add(2,new EnquiryFileTypePropertyDescriptor(model));
		custompropertyList.add(3,new EnquiryFileVersionOptionPropertyDescriptor(model));
		custompropertyList.add(4,new EnquiryCompaniesPropertyDescriptor(model));
		custompropertyList.toArray(new IPropertyDescriptor[0]);
		return custompropertyList;
	}

	private List<IPropertyDescriptor> getSelectionCustomPropertyDescriptors(
			List<IPropertyDescriptor> propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		for (IPropertyDescriptor descriptor : propertyDescriptors) {
			IPropertyDescriptor wrapperDescriprot = null;
			if (descriptor.getId().equals(pkg.getSelection_PopupDropDown().getName())) {
				wrapperDescriprot = new PopupDropDownPropertyDescriptorWrapper(descriptor);
				custompropertyList.add(wrapperDescriprot);
			} else if (descriptor.getId().equals(pkg.getSelection_Mandatory().getName())) {
				wrapperDescriprot = new MandatoryPropertyDescriptorWrapper(descriptor);
				custompropertyList.add(wrapperDescriprot);
			} else if (descriptor.getId().equals(pkg.getSelection_Operands().getName())) {
				wrapperDescriprot = new SelectionOperandsPropertyDescriptor(model);
				custompropertyList.add(1,wrapperDescriprot);
			} else if (descriptor.getId().equals(pkg.getSelection_Field().getName())) {
				wrapperDescriprot = new EnquiryFieldPropertyDescriptorWrapper(descriptor ,model);
				custompropertyList.add(wrapperDescriprot);
			}
			else {
				custompropertyList.add(descriptor);
			}
		}
		return custompropertyList;
	}

}
