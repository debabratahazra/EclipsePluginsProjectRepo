/**
 */
package com.odcgroup.t24.localReferenceApplication.util;

import com.odcgroup.t24.localReferenceApplication.LocalReferenceApplicationPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class LocalReferenceApplicationXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalReferenceApplicationXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		LocalReferenceApplicationPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the LocalReferenceApplicationResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new LocalReferenceApplicationResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new LocalReferenceApplicationResourceFactoryImpl());
		}
		return registrations;
	}

} //LocalReferenceApplicationXMLProcessor
