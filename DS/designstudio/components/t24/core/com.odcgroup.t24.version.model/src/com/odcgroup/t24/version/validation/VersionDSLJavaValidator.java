package com.odcgroup.t24.version.validation;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Default;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.translation.TranslationDslUtill;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.Translations;

public class VersionDSLJavaValidator extends AbstractVersionDSLJavaValidator {
	
    @Inject
    private ResourceDescriptionsProvider resourceDescriptionsProvider;
    
//  @Inject
//  private IContainer.Manager containerManager;
    
    @Inject
    private IQualifiedNameProvider qualifiedNameProvider;
    
//  private IVersionRepository versionRepository;
    
    
    private boolean isUnresolvedObject(EObject eObj) {
    	if (eObj.eIsProxy()) {
    		URI proxyURI = ((InternalEObject) eObj).eProxyURI();
    		if (resolveProxy(proxyURI) == null) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * @param proxyURI
     * @return
     */
    private EObject resolveProxy(URI proxyURI) {
		EObject resolvedObject = null;
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(proxyURI
				.trimFragment().toString());
		if (ePackage != null) {
			Resource resource = ePackage.eResource();
			if (resource != null) {
				resolvedObject = resource.getEObject(proxyURI.fragment()
						.toString());
			}
		}
		return resolvedObject;
    }
    
//  public VersionDSLJavaValidator() {
//    	versionRepository = LanguageRepositoryProvider
//    			.getLanguageRepository(IVersionRepository.VERSION_LANGUAGE_NAME);
//  }

    @Check(CheckType.FAST)
	public void checkT24Name(Version version) {
		String t24Name = version.getT24Name();
		if (StringUtils.isBlank(t24Name)) {
			error("t24Name cannot be null or empty", VersionDSLPackage.Literals.VERSION__T24_NAME);
		}
	}

    /**
     * check if the description contain CR, LF characters.
     * @param version
     */
	@Check(CheckType.FAST)
	public void checkTranslationDoesNotContainCROrLF(Version version) {
		for(Translations translations: VersionUtils.getAllTransaltionForVersion(version)) {
			List<LocalTranslation> localTranslationList = ((LocalTranslations)translations).getTranslations();
			for (LocalTranslation localTranslation : localTranslationList) {
				if (!TranslationDslUtill.isValidTranslation(localTranslation)) {
					error(TranslationDslUtill.getTranslationError(localTranslation), 
						  localTranslationList.get(0).eContainer(), 
						  null, 
						  ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
				}
			}		
		}
	}
	
	
    /**
     * Check if the Field is Single value.
     * 
     * @param version
     * @return
     */
	@Check(CheckType.FAST)
    public void checkIsSingleValueField(Field field) {
		List<Default> defaults = field.getDefaults();
		if (field.getMV() != null 
				&& field.getMV().intValue() == 0 
				&& field.getSV() != null && 
				field.getSV().intValue() == 0) {
			if (defaults.size() > 1) {
				error("The field " + field.getName() + " is Single Value, only one default value is allowed",
						VersionDSLPackage.Literals.FIELD__DEFAULTS);
			}
			if (defaults.size() > 0) {
				Default deflts = defaults.get(0);
				boolean isSingleValue = (deflts.getMv() != null && deflts.getMv().intValue() == 0 
						&& deflts.getSv() != null && deflts.getSv().intValue() == 0)? true: false;
				if (!isSingleValue) {
					error("The field " + field.getName() + " is Single Value and hence Mv and Sv value should be zero",
							VersionDSLPackage.Literals.FIELD__SV);
					error("The field " + field.getName() + " is Single Value and hence Mv and Sv value should be zero",
							VersionDSLPackage.Literals.FIELD__MV);
				}
			}
		}
    }
  
    /**
     * Check if the Field is Multi value.
     * 
     * @param version
     * @return
     */
	@Check(CheckType.FAST)
    public void checkIsMultiValueField(Field field) {
		List<Default> defaults = field.getDefaults();
		if(field.getMV()!=null && field.getMV().intValue()==1 && field.getSV()!=null && field.getSV().intValue()==0){
			if(!defaults.isEmpty()){
				if(!VersionUtils.isSvValueZero(defaults)) {
					error("The field " + field.getName() + " is Multi Value and hence Sv value should be Zero",
							VersionDSLPackage.Literals.FIELD__SV);
				}
				for (Default deflts : defaults) {
					if(!VersionUtils.isUniqueMvsInDefaults(defaults, deflts)){
						error("The field " + field.getName() + " is Multi Value and hence Mv value should be unique",
								VersionDSLPackage.Literals.FIELD__MV);
					}
				}
			}
		}
    }
    
    /**
     * Check if the Field is Sub value.
     * 
     * @param version
     * @return
     */
	@Check(CheckType.FAST)
    public void checkIsSubValueField(Field field) {
		List<Default> defaults = field.getDefaults();
		if (field.getMV() != null && field.getMV().intValue() == 1 
				&& field.getSV() != null && field.getSV() == 1) {
			if (!defaults.isEmpty()) {
				if (VersionUtils.isMvValueZero(defaults)) {
					error("The field " + field.getName() + " is Sub Value and hence Mv value should be greater than zero",
							VersionDSLPackage.Literals.FIELD__MV);
				}
				if (VersionUtils.isSvValueZero(defaults)) {
					error("The field " + field.getName() + " is Sub Value and hence Sv value should be greater than zero",
							VersionDSLPackage.Literals.FIELD__SV);
				}
				for (Default deflts : defaults) {
					if (!VersionUtils.isUniqueMvSvValueInDefaults(defaults, deflts)) {
						error("The field " + field.getName() + " is Sub Value and hence [Mv,Sv] value should be unique",
								VersionDSLPackage.Literals.FIELD__SV);
						error("The field " + field.getName() + " is Sub Value and hence [Mv,Sv] value should be unique",
								VersionDSLPackage.Literals.FIELD__MV);
					}
				}
			}
		}
    }
    
	/**
     * Check if the Field exist in the ForApplication Domain class as Attribute.
     * 
     * @param version
     * @return
     */
    @Check(CheckType.FAST) 
    public void checkFieldNameInDomainClass(Version version) {
    	MdfClass mdfClass = version.getForApplication();
    	if (isUnresolvedObject((EObject) mdfClass)) {
    		// no need to report an error, it will be done by xtext itself
    		return;
    	}
    	
    	List<Field> fields = version.getFields();
    	if (fields.isEmpty()) return;
    	
    	List<MdfProperty> properties = VersionUtils.getAllProperties(mdfClass);

    	for (Field field : fields) {
	    	String fieldName = field.getName();
	
	    	// ignore special field
			if (fieldName.equals("*")) {
				continue;
			}
			
			fieldName = fieldName.replace("_", ".");
	
			boolean found = false;
			for (MdfProperty property : properties) {
				String name = T24Aspect.getT24Name(property);
				if (fieldName.equals(name)) {
					found = true;
					break;
				}
				if (name.contains("-1") && fieldName.replace(".1", "-1").equals(name)) {
					found = true;
					break;
				}
			}
			
			if (!found) {
				QualifiedName fqn = qualifiedNameProvider.getFullyQualifiedName(version);
				error("The field " + field.getName() + " cannot be found in the application " + fqn.toString(),
					  field,
					  VersionDSLPackage.Literals.FIELD__NAME,
					  ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
	    	}
    	}
    }

    @Check(CheckType.NORMAL) 
    public void checkVersionDuplicate(Version version) {
    	QualifiedName versionQN = qualifiedNameProvider.getFullyQualifiedName(version); 
        IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(version.eResource());
        
        Iterable<IEObjectDescription> objects = resourceDescriptions.getExportedObjects(VersionDSLPackage.Literals.VERSION, versionQN, true);
        if (IterableExtensions.size(objects) > 1) {
       		 error("An version with the same name already exists", VersionDSLPackage.Literals.VERSION__FOR_APPLICATION);
        }
    	
// DUPLICATE BASED ON T24 NAMES (too slow)
//    	String t24Name = version.getT24Name();
//        IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(version.eResource());
//        
//        URI eURI = version.eResource().getURI();
//
//        IResourceDescription resourceDescription = resourceDescriptions.getResourceDescription(eURI);
//        if (resourceDescription != null) {
//	        Set<String> t24Names = new HashSet<String>();
//	        for (IContainer c : containerManager.getVisibleContainers(resourceDescription, resourceDescriptions)) {
//	             for (IEObjectDescription od : c.getExportedObjectsByType(VersionDSLPackage.Literals.VERSION)) {
//	            	 String otherT24Name = versionRepository.getT24Name(od);
//	            	 if (StringUtils.equals(t24Name, otherT24Name) && !t24Names.add(otherT24Name)) {
//	            		 error("A version with the same T24-Name already exists.", VersionDSLPackage.Literals.VERSION__T24_NAME);
//	            	 }
//	             }
//	        }
//        }
    }
    
	/**
	 * Check if the physical file name of resource match the "name" in the model
	 * (of the "root" content EObject).
	 * 
	 * @param version
	 */
	@Check(CheckType.FAST)
	public void checkFileNameAndRootModelNameAreSame(Version version) {
		String versionName = version.eResource().getURI().lastSegment();
		String versionFileName = versionName.substring(0, versionName.lastIndexOf("."));
		versionFileName = versionFileName.replaceAll("_", ".");
		String versionT24Name = version.getT24Name();
		versionT24Name = versionT24Name.replace("%", ".");
		if (StringUtils.isNotBlank(versionFileName) && StringUtils.isNotBlank(versionT24Name)) {
			if (!versionFileName.equals(versionT24Name)) {
				error("Version file name and root model name are not same.", VersionDSLPackage.Literals.VERSION__FOR_APPLICATION);
			}
		}
	}
	
}
