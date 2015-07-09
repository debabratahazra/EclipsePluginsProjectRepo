package com.odcgroup.t24.version.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.model.translation.MdfTranslation;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.util.ApplicationFieldHelper;
import com.odcgroup.t24.version.naming.VersionNameProvider;
import com.odcgroup.t24.version.versionDSL.CaseConvention;
import com.odcgroup.t24.version.versionDSL.Default;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLPackageImpl;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslFactory;
import com.odcgroup.translation.translationDsl.Translations;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Utility class for the version model.
 * 
 * @author snn
 */
public class VersionUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(VersionUtils.class);

	private static void throwErrorForUnexpectedCondition(String msg, Version version) {
		if (version != null) {
			// This is always null.. (because Version is cloned, so it doesn't have a Resource?)
			if (version.eResource() != null) {
				msg = msg + ", URI: " + version.eResource().getURI();
			} else {
				// So we use this as a "fallback"..
				try {
					msg = msg + ", Version: " + getDisplayName(version);
				} catch (Throwable t) {
					// Do nada :(
				}
			}
		}
		LOGGER.error(msg);
		if (FeatureSwitches.t24XMLGenerationStopOnFailureEnabled.get())
			throw new IllegalArgumentException(msg); // MINOR: could use some better (checked) exception.. requires changing APIs to declare it 
	}
	
    /**
     * Transform the DS Field Names to T24 Names convention.
     */
    public static void transformFieldNameToT24Names(EObject versionObject,
	    Resource resource) {
    	if (versionObject instanceof Version) {
    		Version version = (Version) versionObject;
    		EList<Field> fieldsList = version.getFields();
    		MdfClass klass = ((Version)resource.getContents().get(0)).getForApplication();
    		if (klass != null && (!((EObject)klass).eIsProxy())) {
    			for (Field field : fieldsList) {
    				MdfProperty property = getMdfProperty(field, klass);
    				if(property == null && !"*".equals(field.getName())) {
    						throwErrorForUnexpectedCondition("Field not found in the referenced application: "+field.getName(), version);
    				}
    				if (property != null
    						&& field.getName().equals(property.getName())) {
    					String t24Name = getT24Name(property);
    					if (t24Name != null) {
    						StringBuilder sb = new StringBuilder();
    						sb.append(t24Name);
    						if (field.getMV()!=null && field.getMV().intValue() > 0) {
    							sb.append("-" + field.getMV().intValue());
    							field.setMV(Integer.valueOf(0));
    							if (field.getSV() !=null && field.getSV().intValue() > 0) {
    								sb.append("." + field.getSV().intValue());
    								field.setSV(Integer.valueOf(0));
    							}
    						}
    						field.setName(sb.toString());
    					}
    				}
    			}
    		} else {
    			throwErrorForUnexpectedCondition("Version had invalid forApplication (null, or still an EMF Proxy): ", version);
    		}
    	}
    }

    /**
     * Get the T24 name for the given MdfProperty.
     */
    public static String getT24Name(MdfProperty property) {
        return T24Aspect.getT24Name(property);
    }
    
    public static String getDisplayName(Version version) {
    	String klass = VersionNameUtils.getVersionForApplication(version).toString();
    	klass = klass.substring(klass.indexOf(":")+1);
    	String shortName = version.getShortName();
    	if (shortName == null) {
    		shortName = "";
    	}
    	return klass+","+shortName;
    }

    /**
     * Get the CaseConvention for the given name
     * 
     * @param String
     *            caseConventionName
     * @return CaseConvention
     */
    public static CaseConvention getCaseConvention(String caseConventionName) {
    	if (caseConventionName != null) {
    		for (CaseConvention caseConventionValue : CaseConvention.VALUES) {
    			if (caseConventionValue.getName().equalsIgnoreCase(
    					caseConventionName)) {
    				return caseConventionValue;
    			}
    		}
    	}
    	return null;
    }

    /**
     * Get the T24Name to MdfAttributeName map for the Given MdfClass.
     * 
     * @param clazz
     * @return
     */
    public static Map<String, String> getT24NameToAttrNameMap(MdfClass clazz) {
    	Map<String, String> t24nameToAttributeNameMap = new HashMap<String, String>();
    	List<MdfProperty> propertyList = getAllProperties(clazz);
    	for (MdfProperty property : propertyList) {
    		t24nameToAttributeNameMap.put(getT24Name(property),
    				property.getName());
    	}
    	return t24nameToAttributeNameMap;
    }

    /**
     * Get the MdfAttirbutes for the give MdfCalss.include the MdfAssociation
     * class types.
     * 
     * @param clazz
     * @return MdfAttributes list
     */
    public static List<MdfProperty> getAllProperties(MdfClass clazz) {
    	List<MdfProperty> propertyList = new ArrayList<MdfProperty>();
    	List<MdfClass> clazzList = new ArrayList<MdfClass>();
    	clazzList.add(clazz);
    	getProperties(clazz, propertyList, clazzList);
    	return propertyList;
    }

    /**
     * return true on unique Mv value
     * @param defaults
     * @param deflts
     * @return
     */
    public static boolean isUniqueMvsInDefaults(List<Default> defaults, Default deflts) {
    	int count=0;
    	for (Default dflt : defaults) {
    		Integer defltsMv=deflts.getMv();
    		Integer dfltMv=dflt.getMv();
			if (dflt != deflts && defltsMv != null && dfltMv != null
					&& defltsMv.intValue() == dfltMv.intValue()) {
				count++;
			}
    	}
    	if(count>=1){
    		return false;
    	}
    	return true;
    }
   
    /**
     * return true on unique Mv and Sv value
     * @param defaults
     * @param deflts
     * @return
     */
    public static boolean isUniqueMvSvValueInDefaults(List<Default> defaults, Default deflts) {
    	int count=0;
    	for (Default dflt : defaults) {
    		Integer defltsMv =deflts.getMv();
    		Integer defltsSv= deflts.getSv();
    		Integer dfltMv=dflt.getMv();
    		Integer dfltSv=dflt.getSv();
			if (dflt != deflts && defltsMv != null && defltsSv != null && dfltMv != null && dfltSv != null
					&& (defltsMv.intValue() == dfltMv.intValue() && defltsSv.intValue() == dfltSv.intValue())) {
    			count++; 
    		}
    	}
    	if(count>=1){
    		return false;
    	}
    	return true;
    }
    
    /**
     * return true on Sv value set to zero
     * @param defaults
     * @param deflts
     * @return
     */
    public static boolean isSvValueZero(List<Default> defaults) {
    	for (Default dflt : defaults) {
    		if(dflt.getSv()!=null&&dflt.getSv().intValue()>0){
    			return false; 
    		}
    	}
    	return true;
    }
    
    /**
     * return true on Mv value set to zero
     * @param defaults
     * @param deflts
     * @return
     */
    public static boolean isMvValueZero(List<Default> defaults) {
    	for (Default dflt : defaults) {
    		if(dflt.getMv()!=null&&dflt.getMv().intValue()>0){
    			return false; 
    		}
    	}
    	return true;
    }
    
    /**
     * Get the MdfAttributes.
     * 
     * @param clazz
     * @param attributeList
     * @param clazzList
     */
    @SuppressWarnings("unchecked")
    private static void getProperties(MdfClass clazz,
	    List<MdfProperty> propertyList, List<MdfClass> clazzList) {
    	if (clazz != null) {
    		List<Object> propertiesList = clazz.getProperties();
    		for (Object property : propertiesList) {
    			if (property instanceof MdfAttribute) {
    				propertyList.add((MdfProperty) property);
    			}
    			if (property instanceof MdfAssociation) {
    				MdfAssociation association = (MdfAssociation) property;
    				MdfClass mdfClazz = (MdfClass) association.getType();
    				if (mdfClazz == null) {
    					throwErrorForUnexpectedCondition("Association type was null: " + association.toString(), null);
    				}
    				if (!clazzList.contains(mdfClazz)
    						&& association.getContainment() == MdfContainment.BY_VALUE) {
    					clazzList.add(mdfClazz);
    					getProperties((MdfClass) association.getType(),
    							propertyList, clazzList);
    				} else if (association.getContainment() == MdfContainment.BY_REFERENCE) {
    					propertyList.add((MdfProperty) property);
    				}
    			}
    		}
    	}
    }

    /**
     * set the metamodelversion for the version model.
     * 
     * @param versionObject
     * @param maetaModelVersion
     */
    public static void setVersionMetaModelVersion(EObject versionObject) {
    	if (versionObject instanceof Version) {
    		((Version) versionObject).setMetamodelVersion(OfsCore
    				.getCurrentMetaModelVersion("version"));
    	}
    }

    /**
     * check if the give field exist in the list of properties..
     * 
     * @param field
     * @return boolean.
     */
    public static boolean isFieldExistInClazz(List<MdfProperty> propertyList,
	    Field field) {
    	boolean exists = false;
    	String fieldName = field.getName();
		if(fieldName.contains("-")){
			fieldName = fieldName.substring(0, fieldName.lastIndexOf("-"));
		}
    	for (MdfProperty property : propertyList) {
    		String propName = property.getName();
    		if (propName.equals(fieldName)) {
    	    	return exists = true;
    		}
    		//if(propName.contains("_"))
    		//	propName = propName.replaceAll("_", ".");
    	    if (propName.equals(fieldName)) {
    	    	return exists = true;
    	    }
    	}
    	return exists;
        }

    /**
     * Get the MdfProperty for the given field
     * 
     * @param field
     * @param clazz
     * @return MdfProperty
     */
    public static MdfProperty getMdfProperty(Field field, MdfClass clazz) {
    	List<MdfProperty> propertyList = getAllProperties(clazz);
    	for (MdfProperty property : propertyList) {
    		if (property.getName().equals(field.getName())) {
    			return property;
    		}
    	}
    	return null;
    }

    /**
     * Replace the version references with the T24Name of the referenced
     * Version.
     * 
     * @param versionObject
     */
    public static void replaceVersionReferenceWithT24Name(
	    EObject versionObject, Resource resource) {
    	if (versionObject != null) {
    		convertReferenceVersionScheme((Version)versionObject ,"t24");
    	}
    }    
    
    private static QualifiedName getVersionName(Version version) {
    	VersionNameProvider vnp = new VersionNameProvider();
    	return vnp.getFullyQualifiedName(version);
    }
    
    public static String getNextVersionName(Version version){
    	QualifiedName fullyQualifiedName = VersionNameUtils.getNextVersion(version);
    	String versionName = StringUtils.EMPTY;
    	if(fullyQualifiedName != null){
			versionName = fullyQualifiedName.toString();
	  	}
    	return versionName;
    }
    
    public static List<String> getAssociatedVersionNames(Version version){
    	List<QualifiedName> fullyQualifiedNames = VersionNameUtils.getAssociatedVersions(version);
    	List<String> versionsNames = new ArrayList<String>();
    	for (QualifiedName fqn : fullyQualifiedNames) {
    		String name = StringUtils.EMPTY;
    		if(fqn != null){
    			name = fqn.toString();
    		}
    		versionsNames.add(name);
	  	}
    	return versionsNames;
    }
    
    public static String getPreviewVersionName(Version version){
    	QualifiedName fullyQualifiedName = VersionNameUtils.getIBVersions(version,1);
    	String versionName = StringUtils.EMPTY;
    	if(fullyQualifiedName != null){
			versionName = fullyQualifiedName.toString();
	  	}
    	return versionName;
    }
    
    public static String getConfirmVersionName(Version version){
    	QualifiedName fullyQualifiedName = VersionNameUtils.getIBVersions(version, 0);
    	String versionName = StringUtils.EMPTY;
    	if(fullyQualifiedName != null){
			versionName = fullyQualifiedName.toString();
	  	}
    	return versionName;
    }
    
    public static String getVersionAssociationName(Version version, int refId){
    	QualifiedName fullyQualifiedName = VersionNameUtils.getVersionForAssociation(version,refId);
    	String versionName = StringUtils.EMPTY;
    	if(fullyQualifiedName != null){
			versionName = fullyQualifiedName.toString();
	  	}
    	return versionName;
    }
    
    public static String getVersionApplicationName(Version version){
    	QualifiedName fullyQualifiedName = getVersionName(version);
    	String applicationName = StringUtils.EMPTY;
    	if(fullyQualifiedName != null){
    		if(fullyQualifiedName.getSegmentCount() == 2){
    			applicationName = fullyQualifiedName.toString();
    		}else if(fullyQualifiedName.getSegmentCount() > 2){
    			applicationName = fullyQualifiedName.skipLast(1).toString();
    		} else {
    			applicationName = fullyQualifiedName.toString();
    		}
    		if(applicationName.toString().contains(".")){
    			applicationName = applicationName.toString().replace(".", ":");
    		}
    	}
    	return applicationName;
    }
    /**
     * convert the special characters in the version short name.
     * @param version
     */
    public static  void convertSpecialCharInShortName(Version version) {
    	String shortName = version.getShortName();
    	if(shortName !=null && StringUtils.isNotEmpty(shortName)){
    	   shortName = shortName.replaceAll("[$%/*!#]", "_");
    	   version.setShortName(shortName);
    	}
    }
    
    /**
     * replace All t24 scheme to Name Scheme.
     */
    public static void replaceRefVersionT24SchemeToNameScheme(EObject versionObject) {
    	 convertReferenceVersionScheme((Version)versionObject ,"name");
    }
    /**
     * replace the reference version scheme.
     * @param version
     * @param schemeName
     */
    private static void convertReferenceVersionScheme(Version version ,String schemeName){
	   Version nextVersion = version.getNextVersion();
	   if(nextVersion !=null ) {
			if ((!((EObject) nextVersion).eIsProxy())
					|| ((((EObject) nextVersion).eIsProxy()) && schemeName.equals("name"))) {
				// clone the next version and set the proxy UIR
				nextVersion = EcoreUtil.copy(nextVersion);
				String versionName = getVersionName(nextVersion, schemeName);
				if (versionName != null) {
					URI nextVersionURI = URI.createURI(schemeName + ":/" + versionName + "#");
					((InternalEObject) nextVersion).eSetProxyURI(nextVersionURI);
					version.setNextVersion(nextVersion);
				}
			}else if(schemeName.equals("t24")) {
			  throwErrorForUnexpectedCondition("Version had no next version (null, or still an EMF Proxy): ", version);
		  }
	   }
	 // associatedverisons  URI Schema change.
	   ArrayList<Version> t24AssociatedVerisonList = new ArrayList<Version>(); 
	   for (Version associatedVersion : version.getAssociatedVersions()) {
			if ((!((EObject) associatedVersion).eIsProxy() || (((EObject) associatedVersion).eIsProxy())
					&& schemeName.equals("name"))) {
				String associatedVersionName = getVersionName(associatedVersion, schemeName);
				// clone the associatedverison and set the new URI.
				Version t24NameURIAssociatedVersion = EcoreUtil.copy(associatedVersion);
				if (associatedVersionName != null) {
					// remove the old association.
					// version.getAssociatedVersions().remove(associatedVersion);
					URI associatedVersionURI = URI.createURI(schemeName + ":/" + associatedVersionName + "#");
					((InternalEObject) t24NameURIAssociatedVersion).eSetProxyURI(associatedVersionURI);
					t24AssociatedVerisonList.add(t24NameURIAssociatedVersion);
				}
			}else if(schemeName.equals("t24")) {
			  throwErrorForUnexpectedCondition("Version had no associated version (null, or still an EMF Proxy): ", version);
		  }
	   }
	   version.getAssociatedVersions().clear();
	   version.getAssociatedVersions().addAll(t24AssociatedVerisonList);
	   //confirmVersion URI Schema change. 
	   Version confirmVersion = version.getConfirmVersion();
	   if(confirmVersion != null ) {
			if ((!((EObject) confirmVersion).eIsProxy())
					|| ((((EObject) confirmVersion).eIsProxy()) && schemeName.equals("name"))) {
				// clone the confirmVersion and set the proxy UIR
				confirmVersion = EcoreUtil.copy(confirmVersion);
				String confirmVersionName = getVersionName(confirmVersion, schemeName);
				if (confirmVersionName != null) {
					URI confirmVersionURI = URI.createURI(schemeName + ":/" + confirmVersionName + "#");
					((InternalEObject) confirmVersion).eSetProxyURI(confirmVersionURI);
					version.setConfirmVersion(confirmVersion);
				}
			}else if(schemeName.equals("t24")) {
			  throwErrorForUnexpectedCondition("Version had no confirm version (null, or still an EMF Proxy): ", version);
		   }
	   }

	   //preview version URI changes. 
	   Version previewVersion = version.getPreviewVersion();
	   if(previewVersion != null) {
			if ((!((EObject) previewVersion).eIsProxy())
					|| ((((EObject) previewVersion).eIsProxy()) && schemeName.equals("name"))) {
				// clone the confirmVersion and set the proxy UIR
				previewVersion = EcoreUtil.copy(previewVersion);
				String previewVersionName = getVersionName(previewVersion, schemeName);
				if (previewVersionName != null) {
					URI previewVersionURI = URI.createURI(schemeName + ":/" + previewVersionName + "#");
					((InternalEObject) previewVersion).eSetProxyURI(previewVersionURI);
					version.setPreviewVersion(previewVersion);
				}
			}else if(schemeName.equals("t24")) {
			  throwErrorForUnexpectedCondition("Version had no preview version (null, or still an EMF Proxy): ", version);
		  }
	   }
   }
    /**
     * get the version name for the given scheme.
     * @param version
     * @param scheme
     * @return
     */
    public static String getVersionName(Version version ,String scheme){
    	if(scheme.equals("t24")){
    		return version.getT24Name();
    	}else if(version.eIsProxy()){
    		URI proxyURI = EcoreUtil.getURI(version);
    		String name = proxyURI.path().substring(1);
    		if(name !=null){
    			return name.replaceAll("[.$%/*!#]", "_");
    		}
    	}
    	return null;
    }
    /**
     * return the Tranlsation map with EStructualFeature and index.
     * @param version
     * @return
     */
    public static List<Translations> getAllTransaltionForVersion(Version version){
    	List<Translations> translationList = new ArrayList<Translations>();
    	LocalTranslations translations = (LocalTranslations) version.getDescription();
    	if(translations !=null){
    		translationList.add(translations);
		}
		//check for header translation.
		LocalTranslations headerTranlsations = (LocalTranslations) version.getHeader();
		if(headerTranlsations !=null){
			translationList.add(headerTranlsations);
		}
		//check for header1 translation
		LocalTranslations header1Tranlsations = (LocalTranslations) version.getHeader1();
		if(header1Tranlsations !=null){
			translationList.add(header1Tranlsations);
		}
		//check for header2 translation
		LocalTranslations header2Tranlsations = (LocalTranslations) version.getHeader2();
		if(header2Tranlsations !=null){
			translationList.add(header2Tranlsations);
		}
		//check for footer translation
		LocalTranslations footerTranlsations = (LocalTranslations) version.getFooter();
		if(footerTranlsations !=null){
			translationList.add(footerTranlsations);
		}
		//check for field translation
		EList<Field> fields =version.getFields();
		for(Field field:fields){
			LocalTranslations labelTranlsation =(LocalTranslations) field.getLabel();
			if(labelTranlsation !=null){
				translationList.add(labelTranlsation);
			}
			LocalTranslations tooltipTranlsation =(LocalTranslations) field.getToolTip();
			if(tooltipTranlsation !=null){
				translationList.add(tooltipTranlsation);
			}
		}
    	return translationList;
    }
    
	public static void removeT24EdgeConnectFields(EObject versionObject, Resource resource) {
    	if (versionObject instanceof Version) {
    		Version version = (Version) versionObject;
		    EStructuralFeature eFeature = versionObject.eClass().getEStructuralFeature(VersionDSLPackageImpl.VERSION__GENERATE_IFP);
		    version.eUnset(eFeature);
		    // Added for DS-6666 - Begin
			eFeature = versionObject.eClass().getEStructuralFeature(VersionDSLPackageImpl.VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN);
			version.eUnset(eFeature);
			// Added for DS-6666 - End
		    eFeature = versionObject.eClass().getEStructuralFeature(VersionDSLPackageImpl.VERSION__FIELDS_LAYOUT_PATTERN);
		    version.eUnset(eFeature);
			
    	}
	}

	/**
	 *This method adds translation to version field from application if local translation is missing.
	 *
	 * @param root
	 * @param resource
	 * @throws TranslationException
	 */
	public static void addInheritedTranslations(EObject versionObject, Resource resource) throws TranslationException {
		if (versionObject instanceof Version) {
			Version version = (Version) versionObject;
			IProject project = OfsResourceHelper.getProject(resource);
			ITranslationManager manager = TranslationCore.getTranslationManager(project);
			if (manager != null) {
				MdfClass application = version.getForApplication();
				List<MdfProperty> properties = ApplicationFieldHelper.getAllProperties(application);
				for (Field field : version.getFields()) {
					Translations translation = field.getLabel();
					if (translation == null) {
						addTranslationfromApplication(manager, properties, field);
					}
				}
			}
		}

	}

	/**
	 * @param manager
	 * @param properties
	 * @param field
	 * @throws TranslationException
	 */
	private static void addTranslationfromApplication(ITranslationManager manager, List<MdfProperty> properties,
			Field field) throws TranslationException {
		for (MdfProperty property : properties) {
			if (property.getName().equals(field.getName())) {
				MdfTranslation mdfTranslation = (MdfTranslation) manager.getTranslation(property);
				LocalTranslations trans = getLocalTranslation(manager, mdfTranslation);
				if (!trans.getTranslations().isEmpty()) {
					EStructuralFeature eFeature = field.eClass().getEStructuralFeature(
							VersionDSLPackageImpl.FIELD__LABEL);
					field.eSet(eFeature, trans);
				}
			}
		}
	}

	/**
	 * @param manager
	 * @param fieldtranslation
	 * @return
	 * @throws TranslationException
	 */
	private static LocalTranslations getLocalTranslation(ITranslationManager manager, MdfTranslation fieldtranslation) throws TranslationException {
		List<Locale> locales = manager.getPreferences().getAdditionalLocales();
		locales.add(manager.getPreferences().getDefaultLocale());
		LocalTranslations trans = TranslationDslFactory.eINSTANCE.createLocalTranslations();
		for (Locale locale : locales) {
			String text = fieldtranslation.getText(ITranslationKind.NAME, locale);
			if (text != null && !text.isEmpty()) {
				LocalTranslation localTrans = TranslationDslFactory.eINSTANCE.createLocalTranslation();
				localTrans.setLocale(locale.toString());
				localTrans.setText(text);
				trans.getTranslations().add(localTrans);
			}
		}
		return trans;
	}

}

