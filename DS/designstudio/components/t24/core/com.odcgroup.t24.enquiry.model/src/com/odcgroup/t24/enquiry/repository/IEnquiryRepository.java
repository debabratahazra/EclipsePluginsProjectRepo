package com.odcgroup.t24.enquiry.repository;

import org.eclipse.xtext.resource.IEObjectDescription;

import com.odcgroup.t24.common.repository.IT24LanguageRepository;

public interface IEnquiryRepository extends IT24LanguageRepository {

	static final String ENQUIRY_LANGUAGE_NAME = "enquiry";
	
	Iterable<IEObjectDescription> getAllEnquiries();
	
}
