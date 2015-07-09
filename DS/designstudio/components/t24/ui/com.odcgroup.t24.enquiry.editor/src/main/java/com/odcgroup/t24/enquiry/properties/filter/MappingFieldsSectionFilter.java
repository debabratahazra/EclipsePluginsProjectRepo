package com.odcgroup.t24.enquiry.properties.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;
import com.odcgroup.t24.enquiry.properties.descriptors.EnquiryFieldPropertyDescriptorWrapper;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;
import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

/**
 *
 * @author satishnangi
 *
 */
public class MappingFieldsSectionFilter extends AbstractPropertyFilter {

	private EnquiryPackage pkg = EnquiryPackage.eINSTANCE;

	private LanguageXtextEObjectSearch eObjectSearch;
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;

	/**
	 * @param eObjectSearch
	 * @param globalDescriptionLabelProvider
	 */
	public MappingFieldsSectionFilter(LanguageXtextEObjectSearch eObjectSearch,
			GlobalDescriptionLabelProvider globalDescriptionLabelProvider) {
		this.eObjectSearch = eObjectSearch;
		this.globalDescriptionLabelProvider = globalDescriptionLabelProvider;
	}

	@Override
	public List<EStructuralFeature> getFilterProperties(EObject eObject) {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		if (eObject instanceof TargetMapping) {
			list.add(pkg.getTargetMapping_FromField());
			list.add(pkg.getTargetMapping_ToField());
		}
		return list;
	}

	@Override
	protected IPropertyDescriptor[] customPropertyDecriptors(IPropertyDescriptor[] propertyDescriptors, EObject model) {
		List<IPropertyDescriptor> propertyList = Arrays.asList(propertyDescriptors);
		List<IPropertyDescriptor> custompropertyList = new ArrayList<IPropertyDescriptor>();
		if (model instanceof TargetMapping) {
			for (IPropertyDescriptor descriptor : propertyList) {
				if(descriptor.getId().equals(pkg.getTargetMapping_FromField().getName())){
					custompropertyList.add(new EnquiryFieldPropertyDescriptorWrapper(descriptor, model));
				}
				else if(descriptor.getId().equals(pkg.getTargetMapping_ToField().getName())){
					String applicationName = ((Target)model.eContainer()).getApplication();
					EReference ref = EcoreFactory.eINSTANCE.createEReference();
					ref.setEType(MdfPackage.Literals.MDF_CLASS);
					String fileName = EnquiryUtil.getDomainRepository().getApplicationQualifiedNameFromT24Name(model,applicationName,ref);
					custompropertyList.add(new EnquiryFieldPropertyDescriptorWrapper(descriptor, model, /*"name:/"+*/fileName));
				}
			}
		}          
		return custompropertyList.toArray(new IPropertyDescriptor[0]);
	}
}