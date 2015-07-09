package com.odcgroup.t24.enquiry.repository;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.odcgroup.t24.common.repository.T24LanguageRepository;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

public class EnquiryRepository extends T24LanguageRepository implements IEnquiryRepository {

	@Override
	public void createUserData(EObject eObject, Map<String, String> userData) {
		
		if (!(eObject instanceof Enquiry))
			return;
		
		Enquiry enquiry = (Enquiry)eObject;
		
		// T24 Name
		String t24Name = enquiry.getName();
		setT24Name(userData, t24Name);
		// T24 Component Name
		String t24Component = ""; 
		setT24ComponentName(userData, t24Component);
		// T24 Module Name
		String t24Module="";
		setT24ModuleName(userData, t24Module);
	
	}

	@Override
	public Iterable<IEObjectDescription> getAllEnquiries() {
		return getExportedObjectsByType(EnquiryPackage.Literals.ENQUIRY);
	}

	/**
	 * @param languageName
	 */
	public EnquiryRepository() {
		super(IEnquiryRepository.ENQUIRY_LANGUAGE_NAME);
	}


}
