package com.odcgroup.t24.mdf.generator;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.model.translation.MdfTranslation;
import com.odcgroup.t24.application.internal.localref.NoInputChange;
import com.odcgroup.t24.application.internal.localref.VettingTable;
import com.odcgroup.t24.application.internal.localref.impl.LocalRefImpl;
import com.odcgroup.t24.application.internal.localref.impl.LocalrefFactoryImpl;
import com.odcgroup.t24.application.localref.importer.LocalRefUtils;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.localReferenceApplication.impl.LocalFieldImpl;
import com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationFactoryImpl;
import com.odcgroup.t24.localReferenceApplication.impl.LocalReferenceApplicationImpl;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslFactory;
import com.odcgroup.workbench.dsl.xml.XtextToNameURISwapper;
import com.odcgroup.workbench.dsl.xml.XtextToNameURISwapperSimpleImpl;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.xml.AbstractXmlCodeGenerator;

public class T24MdfGenerator extends AbstractXmlCodeGenerator {

	private static String LOCAL_FIELD = "LocalFields";

	private String currentProperty = null;
	private XtextToNameURISwapper nameURISwapper = new XtextToNameURISwapperSimpleImpl();
	private MdfDomainImpl enumDomain;
	private static final Logger LOGGER = LoggerFactory.getLogger(T24MdfGenerator.class);
	
	@Override
	protected String getModelsExtension() {
		return "domain";
	}

	/**
	 * Transforms the Xtext DSL EMF model resources into XML resources.
	 * 
	 * @param resource original DSL model resource (non-XML)
	 * @param xmlURI URI of the XML File to write
	 * @return XML model resource, not yet saved
	 * @throws IOException 
	 */
	@Override
	public Resource generateXMLResourceWithoutYetSavingIt(Resource resource, URI tempURI, ModelLoader loader) throws Exception {
		if(resource != null) {
			EObject root = getEObjectFromResource(resource);
			MdfDomainImpl domain = null;
			if(root instanceof MdfDomain){
				domain = (MdfDomainImpl)root;
			}
			domain = EcoreUtil.copy(domain);
			domain = nameURISwapper.cloneAndReplaceAllReferencesByNameURIProxies(domain ,resource );
			if(domain != null) {
				if(domain.getName().equals(LocalRefUtils.LOCAL_FIELD_LOCALFIELDSDEFINITION_DOMAIN)) {
					root = getMdfAttributeAsLocalRef(resource, domain, tempURI, loader);
					URI xmlURI = getModifiedURI(tempURI, LocalRefUtils.LOCAL_FIELD_PACKAGE);
					return generateXMLResource(root, xmlURI);
				} else {
					if(domain.eResource() == null){
						if(resource.getContents()!= null && resource.getContents().size() != 0){
							if(resource.getContents().get(0) instanceof MdfDomain){
								domain = (MdfDomainImpl)resource.getContents().get(0);
							}
						}
					}
					return generateLocalApplicationXML(domain, tempURI);
				}
			}
		}
		return null;
	}
	
	private Resource generateXMLResource(EObject root, URI tempURI) throws IOException {
		URI uri = tempURI.appendFileExtension("xml");
		URI xmlURI = URI.createURI(getActualURI(uri));
		
		// Create the new EObject as XML Resource
		GenericXMLResourceFactoryImpl resourceFactory = new GenericXMLResourceFactoryImpl();
		Resource xmlResource = resourceFactory.createResource(xmlURI);
		if(xmlResource != null) {
			xmlResource.getContents().add(root);
		}
		return xmlResource;
	}
	
	@SuppressWarnings("unchecked")
	private LocalFieldImpl getLocalRefApplicationAsEObject(MdfPropertyImpl prop, MdfClass baseClass, String groupName) {
		LocalFieldImpl field = (LocalFieldImpl)LocalReferenceApplicationFactoryImpl.eINSTANCE.createLocalField();
		field.setLocalRefID(T24Aspect.getT24Name(prop));
		
		if(groupName != null)
			field.setGroupName(groupName);
		//set tooltip and label translation
		LocalTranslations labels = TranslationDslFactory.eINSTANCE.createLocalTranslations();
		MdfAnnotation labelAnnotation = prop.getAnnotation(MdfTranslation.NAMESPACE_URI, MdfTranslation.TRANSLATION_LABEL);
		if(labelAnnotation != null) {
			List<MdfAnnotationProperty> annots = labelAnnotation.getProperties();
			for (MdfAnnotationProperty mdfAnnotationProperty : annots) {
				LocalTranslation label = TranslationDslFactory.eINSTANCE.createLocalTranslation();
				label.setLocale(mdfAnnotationProperty.getName());
				label.setText(mdfAnnotationProperty.getValue());
				labels.getTranslations().add(label);
			}
		}
		field.setLabel(labels);
		LocalTranslations toolTips = TranslationDslFactory.eINSTANCE.createLocalTranslations();
		MdfAnnotation toolTipAnnotation = prop.getAnnotation(MdfTranslation.NAMESPACE_URI, MdfTranslation.TRANSLATION_TOOLTIP);
		if(toolTipAnnotation != null) {
			List<MdfAnnotationProperty> annots = toolTipAnnotation.getProperties();
			for (MdfAnnotationProperty mdfAnnotationProperty : annots) {
				LocalTranslation toolTip = TranslationDslFactory.eINSTANCE.createLocalTranslation();
				toolTip.setLocale(mdfAnnotationProperty.getName());
				toolTip.setText(mdfAnnotationProperty.getValue());
				toolTips.getTranslations().add(toolTip);
			}
		}
		field.setToolTip(toolTips);
		return field;
	}

	@SuppressWarnings("unchecked")
	private Resource generateLocalApplicationXML(MdfDomainImpl domain, URI tempURI) throws Exception {
		if (domain.eResource() != null) {
			String string = domain.eResource().getURI().toString();
			List<MdfClass> klasses = domain.getClasses();
			String resourceName = string.substring(string.lastIndexOf("/") + 1);
			for (MdfClass mdfClass : klasses) {
				if (mdfClass.getBaseClass() != null) {
					URI xmlURI = null;
					LocalReferenceApplicationImpl refApp = (LocalReferenceApplicationImpl) LocalReferenceApplicationFactoryImpl.eINSTANCE
							.createLocalReferenceApplication();
					List<MdfPropertyImpl> properties = mdfClass.getProperties();
					MdfClass baseClass = replaceReferenceWithNameURI(mdfClass.getBaseClass());
					refApp.setForApplication(baseClass);
					for (MdfPropertyImpl prop : properties) {
						if (prop instanceof MdfAssociationImpl) {
							MdfAssociationImpl association = (MdfAssociationImpl) prop;
							if (association.getMultiplicity() == MdfMultiplicity.MANY
									&& association.getContainment() == MdfContainment.BY_VALUE) {
								MdfClass klass = (MdfClass) association.getType();
								List<MdfPropertyImpl> propertyList = klass.getProperties();
								String groupName = null;
								if (!klass.getProperties().isEmpty()) {
									MdfProperty firstProperty = (MdfProperty) klass.getProperties().get(0);
									groupName = firstProperty.getName();
								}
								for (MdfPropertyImpl mdfPropertyImpl : propertyList) {
									LocalFieldImpl field = getLocalRefApplicationAsEObject(mdfPropertyImpl,
											mdfClass.getBaseClass(), groupName);
									refApp.getLocalField().add(field);
								}
							} else {
								LocalFieldImpl field = getLocalRefApplicationAsEObject(association,
										mdfClass.getBaseClass(), "No");
								refApp.getLocalField().add(field);
							}
						} else {
							LocalFieldImpl field = getLocalRefApplicationAsEObject(prop, mdfClass.getBaseClass(), "No");
							refApp.getLocalField().add(field);
						}
					}
					xmlURI = URI.createURI(tempURI.toString().replace(resourceName, mdfClass.getName()));
					URI uri = getModifiedURI(xmlURI, LocalRefUtils.LOCAL_REF_APPLICATION_PACKAGE);
					return generateXMLResource(refApp, uri);
				}
			}
		}
		return null;
	}

	private MdfClass replaceReferenceWithNameURI(MdfClass mdfClass) {
		MdfClass klazz = (MdfClass) EcoreUtil.copy((EObject) mdfClass);
		URI nameURI;
		if (((InternalEObject) klazz).eIsProxy()) {
			URI initialURI = ((InternalEObject) klazz).eProxyURI();
			if (initialURI.scheme().equals("name")) {
				String name =MdfNameURIUtil.getMdfName(initialURI).getQualifiedName();
				name = name.replaceAll("\\.", ":");
				nameURI = URI.createURI("name://" + name + "#");
			} else {
				throw new IllegalStateException("EMF Proxy of another URI scheme than name:/ found for a MDF Class baseClass, aborting! (To avoid worse and more confusing DS-8144 like errors further in the processing.)"); // DS-8144
			}
		} else {
			String name = mdfClass.getParentDomain().getName() + ":" + klazz.getName();
			nameURI = URI.createURI("name://" + name + "#");
		}
		((InternalEObject) klazz).eSetProxyURI(nameURI);
		return klazz;
	}
	
	
	@SuppressWarnings("unchecked")
	private LocalRefImpl getMdfAttributeAsLocalRef(Resource resource, MdfDomainImpl clonedProxyUriDomain, URI uri, ModelLoader loader) throws Exception {
		MdfDomainImpl mdfDomain = (MdfDomainImpl)resource.getContents().get(0);

		// JUnit test case
		if(currentProperty == null){
			MdfClass localFieldsClass = (MdfClass)clonedProxyUriDomain.getClass("LocalFields");
			List<MdfPropertyImpl> propertiesList = localFieldsClass.getProperties();
			for (MdfPropertyImpl mdfProperty : propertiesList) {
				currentProperty = mdfProperty.getName();
				break;
			}
		}
		
		MdfPropertyImpl property = (MdfPropertyImpl)mdfDomain.getClass(LOCAL_FIELD).getProperty(currentProperty);
		
		LocalRefImpl ref = (LocalRefImpl) LocalrefFactoryImpl.eINSTANCE.createLocalRef();
		ref.setLocalRefID(property.getName());
		ref.setMaximumChars(T24Aspect.getMaxChars(property));
		ref.setMinimumChars(T24Aspect.getMinChars(property));
		ref.setOverridePossible(T24Aspect.getOverridePossible(property));
		ref.setDefaultPossible(T24Aspect.getDefaultPossible(property));
		ref.setNoInputChange(NoInputChange.getByName(T24Aspect.getNoInputChange(property)));
		ref.setVirtualTable(T24Aspect.getVirtualTable(property));
		ref.setMandatory(property.isRequired());

		if (property instanceof MdfAssociation) {
			if (property.getType() instanceof MdfClass) {
				MdfClass referredType = ((MdfClass) property.getType());
				if (referredType != null && (!((EObject)referredType).eIsProxy())) {
					MdfClass applicationVetClass = replaceReferenceWithNameURI(referredType);
					ref.setApplicationVET(applicationVetClass);
					// The referenced application's, 't24Name' annotation is the
					// property to be set for applicationEnrich when deploying.
					// DS-6788
					ref.setApplicationEnrich(getApplicationEnrichName(property));
				}else {
					throwErrorForUnexpectedCondition("Local Field : " + property.getName() + " had no ApplicationVET: "
							+ referredType.getQualifiedName() + ", ", clonedProxyUriDomain);
				}
				
			}
		} else {
			property = (MdfPropertyImpl)clonedProxyUriDomain.getClass(LOCAL_FIELD).getProperty(currentProperty);
			uri =((InternalEObject)property.getType()).eProxyURI();
			String  name =MdfNameURIUtil.getMdfName(uri).getQualifiedName();
			if(name.startsWith("T24BusinessTypes")) {
				name = name.substring(name.indexOf(':') + 1, name.length());
				ref.setCharType(name);
			} else if(name.startsWith("LocalFieldsEnumeration")) {
				name = name.substring(name.indexOf(':') + 1, name.length());
				final QualifiedName qualifiedName = QualifiedName.create(LocalRefUtils.LOCAL_FIELD_ENUMERATIONS_DOMAIN);
				if(loader == null){
					throw new NullPointerException("ModelLoader is null");
				}
				enumDomain = loader.getNamedEObject(mdfDomain, qualifiedName, mdfDomain.eClass());
				MdfEnumeration mdfEnum = enumDomain.getEnumeration(name);
				// Set Vetting table and Remarks
				if (mdfEnum != null) {
					String charType = mdfEnum.getType().getName();
					ref.setCharType(charType);
					List<MdfEnumValue> mdfEnumValues = mdfEnum.getValues();
					for (MdfEnumValue mdfEnumValue : mdfEnumValues) {
						VettingTable vt = LocalrefFactoryImpl.eINSTANCE.createVettingTable();
						vt.setVettingTable(mdfEnumValue.getName());
						LocalTranslations remarks = TranslationDslFactory.eINSTANCE.createLocalTranslations();
						MdfAnnotation annotation = mdfEnumValue.getAnnotation(MdfTranslation.NAMESPACE_URI,
								MdfTranslation.TRANSLATION_LABEL);
						if (annotation != null) {
							List<MdfAnnotationProperty> annots = annotation.getProperties();
							for (MdfAnnotationProperty mdfAnnotationProperty : annots) {
								LocalTranslation remark = TranslationDslFactory.eINSTANCE.createLocalTranslation();
								remark.setLocale(mdfAnnotationProperty.getName());
								remark.setText(mdfAnnotationProperty.getValue());
								remarks.getTranslations().add(remark);
							}
							vt.getRemarks().add(remarks);
						}
						ref.getVettingTable().add(vt);
					}
				}
			}
		}

		// Set Short Name
		MdfAnnotation annotation = property.getAnnotation(MdfTranslation.NAMESPACE_URI,
				MdfTranslation.TRANSLATION_LABEL);
		if (annotation != null) {
			List<MdfAnnotationProperty> annots = annotation.getProperties();
			LocalTranslations shortName = TranslationDslFactory.eINSTANCE.createLocalTranslations();
			for (MdfAnnotationProperty mdfAnnotationProperty : annots) {
				LocalTranslation translation = TranslationDslFactory.eINSTANCE.createLocalTranslation();
				translation.setLocale(mdfAnnotationProperty.getName());
				translation.setText(mdfAnnotationProperty.getValue());
				shortName.getTranslations().add(translation);
			}
			ref.getShortName().add(shortName);
		}
		// Set Description
		if (property.getDocumentation() != null) {
			ref.setDescription(property.getDocumentation());
		}
		return ref;
	}
	
	private static void throwErrorForUnexpectedCondition(String msg, MdfDomainImpl mdfDomain) {
		if (mdfDomain != null) {
			if (mdfDomain.eResource() != null) {
				msg = msg + mdfDomain.eResource().getURI();
			} else {
				try {
					msg = msg + mdfDomain.getName();
				} catch (Throwable t) {
				}
			}
		}
		LOGGER.error(msg);
		throw new IllegalArgumentException(msg); 
	}

	/**
	 * This method gives the application enrichment value, which is nothing but
	 * the referenced application's, 't24Name' annotation.
	 * 
	 * @param property
	 * @return t24Name
	 */
	private String getApplicationEnrichName(MdfPropertyImpl property) {
		MdfClass referredType = ((MdfClass) property.getType());
		// Get the application enrich value
		String appEnrich = T24Aspect.getApplicationEnrich(property);
		String t24Name = "";

		// From the application enrich value get the property in the referenced application
		MdfProperty referredTypeProperty = (MdfProperty) referredType.getProperty(appEnrich);
		
		// Get the property's "t24Name" from its annotations list
		if (referredTypeProperty != null) {
			t24Name = T24Aspect.getT24Name(referredTypeProperty);
		}

		// If t24Name is null and the referenced property is an MdfAssociation then search
		// for t24Name in the association's property list
		if (StringUtils.isBlank(t24Name)
				&& (referredTypeProperty != null && referredTypeProperty instanceof MdfAssociation)
				&& referredTypeProperty.getType() instanceof MdfClass) {
			referredType = (MdfClass) referredTypeProperty.getType();
			referredTypeProperty = (MdfProperty) referredType.getProperty(appEnrich);
			t24Name = T24Aspect.getT24Name(referredTypeProperty);
		}

		return t24Name;
	}
	
	private void generateLocalFieldsXML(List<MdfPropertyImpl> propertiesList, Resource resource, URI uri, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		MdfDomainImpl domain = (MdfDomainImpl)resource.getContents().get(0);
		for (MdfPropertyImpl mdfProperty : propertiesList) {
			URI tempURI = URI.createURI(uri.toString().replace(domain.getName() + ".domain", mdfProperty.getName()));
			currentProperty = mdfProperty.getName();
			Resource xmlResource = generateXMLResourceWithoutYetSavingIt(mdfProperty.eResource(), tempURI, loader);			
			saveResource(fsa, xmlResource);
		}	
	}
	
	@Override
	public void generateXML(Resource resource, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		String resourceName = resource.getURI().lastSegment(); 
		if(resourceName != null && !resourceName.isEmpty()) {
			MdfDomainImpl domain = (MdfDomainImpl)resource.getContents().get(0);
			
			URI uri = domain.eResource().getURI();
			
			if(T24Aspect.getLocalRefDefinition(domain)){
				if(resourceName.equals("LocalFieldsEnumeration.domain") || resourceName.equals("T24BusinessTypes.domain")) {
					// do nothing for these two domains.
					return;
				}
				MdfClass localFieldsClass = (MdfClass)domain.getClass("LocalFields");
				@SuppressWarnings("unchecked") List<MdfPropertyImpl> propertiesList = localFieldsClass.getProperties();
				generateLocalFieldsXML(propertiesList, resource, uri, loader, fsa);
			} else if(T24Aspect.getLocalRefApplications(domain)) {
				Resource xmlResource = generateLocalApplicationXML(domain, uri);
				saveResource(fsa, xmlResource);
			} else {
				// no generation available for the rest of the domains.
				// LOGGER.info("XML Generation is not required for " + domain.eResource().getURI());
			}
		}
	}
	
	private URI getModifiedURI(URI uri, String folderReplacementName) {
		return URI.createURI(uri.toString().replaceFirst(getModelsExtension() + "(?i)/" + folderReplacementName,
				getModelsExtension()));
	}
}
