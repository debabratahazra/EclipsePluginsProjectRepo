package com.odcgroup.mdf.validation.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.xtext.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.ecore.util.PrimitiveTypeValidator;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.model.util.ModelAnnotationHelper;
import com.odcgroup.mdf.utils.ModelHelper;
import com.odcgroup.mdf.utils.PathVisitor;
import com.odcgroup.mdf.utils.PropertiesStack;
import com.odcgroup.mdf.validation.MdfValidationCore;
import com.odcgroup.otf.utils.inet.MalformedUriException;
import com.odcgroup.otf.utils.inet.URI;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.GenerationCore;


/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class MdfModelValidator implements ModelValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(MdfModelValidator.class);
	
    private static final String TRIPLE_A_NAME = "TripleA";
	private static final String TRIPLEA_NAMESPACE = "http://www.odcgroup.com/mdf/aaa";
	private static final String SOURCE_LEVEL = "5.0";
    private static final String COMPLIANCE_LEVEL = "5.0";
    
    private ValidationListener listener = null;

    private boolean validateNew = false;
	
    /**
     * Constructor for MdfModelValidator
     */
    public MdfModelValidator() {
        super();
    }

    /**
     * Constructor for MdfModelValidator
     */
    public MdfModelValidator(boolean validateNew) {
        super();
        this.validateNew = validateNew;
    }

    public void setValidationListener(ValidationListener listener) {
        this.listener = listener;
    }

    /**
     * @see com.odcgroup.mdf.editor.model.ModelVisitor#accept(com.odcgroup.mdf.metamodel.MdfModelElement)
     */
    public boolean accept(MdfModelElement model) {
        IStatus status = validate(model);
        return listener.onValidation(model, status);
    }

    /**
     * @param model
     * @return
     */
    public IStatus validate(MdfModelElement model) {
    	try {
	        if (model instanceof MdfDomain) {
	            return validate((MdfDomain) model);
	        } else if (model instanceof MdfClass) {
	            return validate((MdfClass) model);
	        } else if (model instanceof MdfEnumeration) {
	            return validate((MdfEnumeration) model);
	        } else if (model instanceof MdfAttribute) {
	            return validate((MdfAttribute) model);
	        } else if (model instanceof MdfAssociation) {
	            return validate((MdfAssociation) model);
	        } else if (model instanceof MdfReverseAssociation) {
	            return validate((MdfReverseAssociation) model);
	        } else if (model instanceof MdfEnumValue) {
	            return validate((MdfEnumValue) model);
	        } else if (model instanceof MdfDataset) {
	            return validate((MdfDataset) model);
	        } else if (model instanceof MdfDatasetMappedProperty) {
	            return validate((MdfDatasetMappedProperty) model);
	        } else if (model instanceof MdfDatasetDerivedProperty) {
	            return validate((MdfDatasetDerivedProperty) model);
	        }  else if (model instanceof MdfBusinessType) {
	        	return validate((MdfBusinessType) model);
	        } else {
	            return MdfValidationCore.newStatus("Unknown model element: "
	                    + model.getClass(), IStatus.ERROR);
	        }
    	} catch(Exception e) {
        	final String msg = "Unknown error validating model element: "+model.getQualifiedName();
    		LOGGER.error(msg, e);
			return MdfValidationCore.newStatus(msg, e);
    	}
    }

    public IStatus validate(MdfDomain domain) throws ExecutionException {
        String name = domain.getName();
        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("A domain name must be specified",
                    IStatus.ERROR);
        }

        IStatus status = JavaConventions.validateJavaTypeName(name,
                SOURCE_LEVEL, COMPLIANCE_LEVEL);
        if (!status.isOK()) {
            return MdfValidationCore.newStatus("'" + name
                    + "' is not a valid domain name", IStatus.ERROR);
        }

        status = checkKeywords(domain);
        if (!status.isOK()) return status;

        if (isDuplicateName(domain)) {
            return MdfValidationCore.newStatus(
                    "A domain with this name already exists",
                    IStatus.ERROR);
        }

        String namespace = domain.getNamespace();
        if (MdfValidatorUtil.isNullOrEmpty(namespace)) {
            return MdfValidationCore.newStatus("A domain namespace must be specified",
                    IStatus.ERROR);
        }

        try {
            URI.parse(namespace);
        } catch (MalformedUriException e) {
            return MdfValidationCore.newStatus("'" + namespace
                    + "' is not a valid namespace", IStatus.ERROR);
        }

        return MdfValidationCore.STATUS_OK;

    }

    public IStatus validate(MdfEnumeration enumeration) throws ExecutionException {
        String name = enumeration.getName();

        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("An enumeration name must be specified",
                    IStatus.ERROR);
        }

        IStatus status = validateName(name, enumeration.getParentDomain());
        if (!status.isOK()) return status;
        
        status = checkKeywords(enumeration);
        if (!status.isOK()) return status;

        if (isDuplicate(enumeration)) {
            return MdfValidationCore.newStatus(
                    "An entity with the same name already exists in this domain",
                    IStatus.ERROR);
        }
        
        if (checkDuplicateInDomainsWithSamePackage(enumeration)) {
        	return MdfValidationCore.newStatus("An entity with the same name is already existing in another domain with same package. " +
        			"Please choose another name", 
        			IStatus.ERROR);
        }

        MdfEntity type = enumeration.getType();
        if (type == null) {
            return MdfValidationCore.newStatus("An enumeration type must be specified",
                    IStatus.ERROR);
        }

        if (!(type instanceof MdfPrimitive) || MdfEntityImpl.isProxy(type)) {
            return MdfValidationCore.newStatus(type.getQualifiedName()
                    + " is not a valid enumeration type", IStatus.ERROR);
        }

        return MdfValidationCore.STATUS_OK;
    }

    public IStatus validate(MdfBusinessType btype) {
        String name = btype.getName();

        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("Business type name must be specified",
                    IStatus.ERROR);
        }

        IStatus status = JavaConventions.validateJavaTypeName(name,
                SOURCE_LEVEL, COMPLIANCE_LEVEL);
        if (!status.isOK()) {
            return MdfValidationCore.newStatus("'" + name
                    + "' is not a valid business type name", IStatus.ERROR);
        }

        status = checkKeywords(btype);
        if (!status.isOK()) return status;

        if (isDuplicate(btype)) {
            return MdfValidationCore.newStatus(
                    "An entity with the same name already exists in this model",
                    IStatus.ERROR);
        }

        MdfEntity type = btype.getType();
        if (type == null) {
            return MdfValidationCore.newStatus("Base type must be specified",
                    IStatus.ERROR);
        }

        if (!(type instanceof MdfPrimitive) || MdfEntityImpl.isProxy(type)) {
            return MdfValidationCore.newStatus(type.getQualifiedName()
                    + " is not a valid base type", IStatus.ERROR);
        }

        return MdfValidationCore.STATUS_OK;
    }

    
    protected IStatus validateName(String name, MdfDomain mdfDomain) {
    	IStatus status = JavaConventions.validateFieldName(name, SOURCE_LEVEL,
                COMPLIANCE_LEVEL);
        if (!status.isOK()) {
            return MdfValidationCore.newStatus(
                    "'" + name + "' is not a valid value name", IStatus.ERROR);
        }
        return MdfValidationCore.STATUS_OK;
    }
    
    public IStatus validate(MdfEnumValue enumValue) {
        String name = enumValue.getName();

        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("A value name must be specified",
                    IStatus.ERROR);
        }

        IStatus status = validateName(name, enumValue.getParentEnumeration().getParentDomain());
        if(status != MdfValidationCore.STATUS_OK) return status;

        status = checkKeywords(enumValue);
        if (!status.isOK()) return status;

        if (isDuplicate(enumValue)) {
            return MdfValidationCore.newStatus(
                    "A value with the same name is already existing in this enumeration, please choose another name",
                    IStatus.ERROR);
        }

        String value = enumValue.getValue();
        if (MdfValidatorUtil.isNullOrEmpty(value)) {
            return MdfValidationCore.newStatus("A value must be specified", IStatus.ERROR);
        }

        MdfPrimitive type = (MdfPrimitive) enumValue.getParentEnumeration().getType();
        if (type != null) {
            if (type.equals(PrimitivesDomain.DATE)
                    || type.equals(PrimitivesDomain.DATE_TIME)) {
                if (value.equals("now()") || value.equals("today()")) {
                    return MdfValidationCore.newStatus(
                            "The value is not valid for this type of enumeration",
                            IStatus.ERROR);
                }
            }

            if (!PrimitiveTypeValidator.isValidValue(type, value)) {
                return MdfValidationCore.newStatus(
                        "The value is not valid for this type of enumeration",
                        IStatus.ERROR);
            }
            
            if ("EnumMask".equals(type.getName())) {
            	// if we are here, the value is really an integer.
            	if (Long.valueOf(value) < 0) {
                    return MdfValidationCore.newStatus("Negative value is not valid.",
                    		IStatus.ERROR);
            	}
            	
            }
        }

        if (isDuplicateValue(enumValue)) {
            return MdfValidationCore.newStatus(
                    "This value already exists in this enumeration, please specify another value",
                    IStatus.ERROR);
        }

        return MdfValidationCore.STATUS_OK;
    }

    public IStatus validate(MdfClass klass) throws ExecutionException {
        String name = klass.getName();

        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("A class name must be specified",
                    IStatus.ERROR);
        }

        IStatus status = JavaConventions.validateJavaTypeName(name,
                SOURCE_LEVEL, COMPLIANCE_LEVEL);
        if (!status.isOK()) {
            return MdfValidationCore.newStatus(
                    "'" + name + "' is not a valid class name", IStatus.ERROR);
        }

        status = checkKeywords(klass);
        if (!status.isOK()) return status;

        MdfClass baseClass = klass.getBaseClass();
        if (baseClass != null) {
            if (MdfEntityImpl.isProxy(baseClass)) {
                return MdfValidationCore.newStatus(baseClass.getQualifiedName()
                        + " is not a valid base class", IStatus.ERROR);
            } else if (!isHierarchyValid(klass)) {
                return MdfValidationCore.newStatus("Class hierarchy for class "
                        + klass.getQualifiedName() + " is invalid",
                        IStatus.ERROR);
            } else if (leadsToCyclicDependency(baseClass, klass.getParentDomain())) {
            	return MdfValidationCore.newStatus("Superclass \'"+baseClass.getQualifiedName() 
            			+ "\' results in cyclic reference with \'" 
            			+ baseClass.getParentDomain().getQualifiedName()+"\' domain", 
            			IStatus.ERROR);
            }
        }

        if (isDuplicate(klass)) {
            return MdfValidationCore.newStatus(
                    "An entity with the same name is already existing in this domain, please choose another name",
                    IStatus.ERROR);
        }
        
        if (checkDuplicateInDomainsWithSamePackage(klass)) {
        	return MdfValidationCore.newStatus("An entity with the same name is already existing in another domain with same package. " +
        			"Please choose another name", 
        			IStatus.ERROR);
        }
        
// DS-1936 disabled this rule        
//        if (isBoundToDataset(klass) && klass.getAttributesRef().isEmpty()) {
//        	return MdfValidationCore.newStatus("This class is the base class of a dataset and has no primary key (or business key), " +
//        			"Please add a primary key (or a business key)", 
//        			IStatus.ERROR);
//        }

        return MdfValidationCore.STATUS_OK;
    }
    
    protected boolean leadsToCyclicDependency(MdfEntity entity, MdfDomain currentDomain) {
    	MdfDomain parentDomain = entity.getParentDomain();
    	if (parentDomain != null && !parentDomain.getQualifiedName().equals(currentDomain.getQualifiedName())) {
    		if (getUsedImports(parentDomain).contains(currentDomain)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    // DS-5624 This method used to be public helper in MMLResource (which was deleted in DS-5624) 
    private Collection<MdfDomain> getUsedImports(MdfDomain domain) {    	
    	Map<String, MdfDomain> domainMap = new HashMap<String, MdfDomain>();
    	
    	// process all entities
    	for(MdfClass clazz : (List<MdfClass>) domain.getClasses()) {
			// check the super class
    		if (clazz.getBaseClass() != null) {
    			String domainName = clazz.getBaseClass().getQualifiedName().getDomain();
    			if(!domain.getName().equals(domainName)) {
    				if (!domainMap.containsKey(domainName)) {
        				MdfDomain usedDomain = clazz.getBaseClass().getParentDomain();
        				if(usedDomain!=null) domainMap.put(domainName, usedDomain);
    				}
    			}
    		}
			// check all non-primitive properties
			for(MdfProperty property : (List<MdfProperty>) clazz.getProperties()) {
				if(property instanceof MdfReverseAssociation) continue;				
				MdfEntity type = property.getType();				
				if(type != null && (!type.isPrimitiveType() || type instanceof MdfEnumeration))  {
					String domainName = type.getQualifiedName().getDomain();
					if (!domain.getName().equals(domainName) && !domainName.equals(PrimitivesDomain.DOMAIN.getName())) {
	    				if (!domainMap.containsKey(domainName)) {
		    				MdfDomain usedDomain = type.getParentDomain();
		    				if(usedDomain!=null) domainMap.put(domainName, usedDomain);
	    				}
					}
				}
			}    		
    	}
    	
    	// process all datasets
    	for(MdfDataset dataset : (List<MdfDataset>) domain.getDatasets()) {
    		// check-for datasets with no baseclass specified
    		if (dataset.getBaseClass() != null) {
				String domainName = dataset.getBaseClass().getQualifiedName().getDomain();
	    		if (!domain.getName().equals(domainName)){
    				if (!domainMap.containsKey(domainName)) {
						MdfDomain usedDomain = dataset.getBaseClass().getParentDomain();
						if(usedDomain!=null) domainMap.put(domainName, usedDomain);
    				}
	    		}
    		}
			// check all non-primitive properties which are not null
			for(MdfDatasetProperty property : (List<MdfDatasetProperty>) dataset.getProperties()) {
				MdfEntity type = property.getType();
				
				if(type != null && !type.isPrimitiveType()
						&& type.getQualifiedName() != null 
						&& !domain.getQualifiedName().equals(type.getParentDomain().getQualifiedName())) {
					String domainName = type.getQualifiedName().getDomain();
    				if (!domainMap.containsKey(domainName)) {
						MdfDomain usedDomain = type.getParentDomain();
						if(usedDomain!=null) domainMap.put(domainName, usedDomain);
    				}
				}
			}
    	}
    	return domainMap.values();    	
    }
    
    /**
     * @param klass the class to be checked
     * @return {@code true} if this class is bound to at least one dataset, 
     *         otherwise it returns {@code false} 
     */
    @SuppressWarnings({ "unused", "rawtypes" })
	private boolean isBoundToDataset(MdfClass klass) {
        if (klass == null) return false;
        MdfDomain domain = klass.getParentDomain();
        if (domain == null) return false;
        
        String klassName = klass.getName();
        if (StringUtils.isEmpty(klassName)) return false;
        
        List datasets = domain.getDatasets();

        Iterator it = datasets.iterator();

        while (it.hasNext()) {
            MdfDataset dataset = (MdfDataset) it.next();
            MdfClass baseClass = dataset.getBaseClass();
            if (baseClass != null && klassName.equals(baseClass.getName())) {
            	return true;
            }
        }        
        	
        return false;
    }

    private boolean isHierarchyValid(MdfClass klass) {
        return isHierarchyValid(klass, new HashSet<MdfClass>());
    }

    private boolean isHierarchyValid(MdfClass klass, Set<MdfClass> parents) {
        if (klass == null) return true;
        if (MdfEntityImpl.isProxy(klass)) return false;

        if (parents.contains(klass)) {
            return false;
        } else {
            parents.add(klass);
            return isHierarchyValid(klass.getBaseClass(), parents);
        }
    }

    public IStatus validate(MdfAssociation assoc) {
        String name = assoc.getName();

        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("An association name must be specified",
                    IStatus.ERROR);
        }

        IStatus status =  validateName(name, assoc.getParentClass().getParentDomain());
        if (!status.isOK()) return status;
        
        status = checkKeywords(assoc);
        if (!status.isOK()) return status;

        if (isDuplicate(assoc)) {
            return MdfValidationCore.newStatus(
                    "A property with the same name is already existing in this class, please choose another name",
                    IStatus.ERROR);
        }

        if (assoc.getType() == null) {
            return MdfValidationCore.newStatus("Association type must be specified for " + assoc.getName(),
                    IStatus.ERROR);
        }
        
        MdfEntity type = null;

        type = assoc.getType();
        status = validateType(type, assoc);
        return MdfValidationCore.STATUS_OK;
    }

    public IStatus validate(MdfReverseAssociation assoc) {
        String name = assoc.getName();

        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("An association name must be specified",
                    IStatus.ERROR);
        }

        IStatus status = JavaConventions.validateFieldName(name, SOURCE_LEVEL,
                COMPLIANCE_LEVEL);
        if (!status.isOK()) {
            return MdfValidationCore.newStatus("'" + name
                    + "' is not a valid association name", IStatus.ERROR);
        }

        status = checkKeywords(assoc);
        if (!status.isOK()) return status;

        if (isDuplicate(assoc)) {
            return MdfValidationCore.newStatus(
                    "A property with the same name is already existing in this class, please choose another name",
                    IStatus.ERROR);
        }

        MdfEntity type = assoc.getType();
        if (type == null) {
            return MdfValidationCore.newStatus("An association type must be specified",
                    IStatus.ERROR);
        }

        if (!(type instanceof MdfClass)) {
            return MdfValidationCore.newStatus(type.getQualifiedName()
                    + " is not a valid association type", IStatus.ERROR);
        } else if (((MdfClass) type).getAttributesRef().isEmpty()) {
            return MdfValidationCore.newStatus(type.getQualifiedName()
                    + " is not a valid type for this association. "
                    + "The type must be referenceable", IStatus.ERROR);
        }

        MdfAssociation reverse = assoc.getAssociation();
        if (reverse == null) {
            return MdfValidationCore.newStatus(
                    "The reverse association must be specified.", IStatus.ERROR);
        } else {
            if (reverse.getContainment() != MdfConstants.CONTAINMENT_BYREFERENCE) {
                return MdfValidationCore.newStatus(reverse.getQualifiedName()
                        + " is not a valid reverse association. "
                        + "The association containment must be 'By reference'",
                        IStatus.ERROR);
            }

            MdfClass klass = assoc.getParentClass();
            boolean parentClassOk = false;
            do {
                parentClassOk = klass.equals(reverse.getType());
                klass = klass.getBaseClass();
            } while (!parentClassOk && (klass != null));

            if (!parentClassOk) {
                return MdfValidationCore.newStatus(reverse.getQualifiedName()
                        + " is not a valid reverse association. "
                        + "The association type must be "
                        + klass.getQualifiedName() + " or a parent class",
                        IStatus.ERROR);
            }
        }

        return MdfValidationCore.STATUS_OK;
    }
    
    protected IStatus validateType(MdfEntity type, MdfAssociation assoc) {
    	if (type == null || !(type instanceof MdfClass) || MdfEntityImpl.isProxy(type)) {
            return MdfValidationCore.newStatus(assoc.getType().getQualifiedName()
                    + " is not a valid association type", IStatus.ERROR);
        } else if (assoc.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE) {        	
           	if (((MdfClass) type).getAttributesRef().isEmpty()) {
                return MdfValidationCore.newStatus(
                        type.getQualifiedName()
                                + " is not a valid type for this association. "
                                + "Either the type must be referenceable or the association containment must be 'By value'",
                        IStatus.ERROR);
            }
           	
           	if (leadsToCyclicDependency((MdfClass) type, assoc.getParentClass().getParentDomain())) {
                return MdfValidationCore.newStatus(
                        "Association type \'"+type.getQualifiedName()+"\' results in " 
                        + "cyclic reference with \'"
                        + ((MdfClass) type).getParentDomain().getQualifiedName()+"\' domain",
                        IStatus.ERROR);
           	}
        }
    	return MdfValidationCore.STATUS_OK;
    }

    @SuppressWarnings("rawtypes")
	public IStatus validate(MdfAttribute attr) {
        String name = attr.getName();

        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("An attribute name must be specified",
                    IStatus.ERROR);
        }

        IStatus status = validateName(name, attr.getParentClass().getParentDomain());
        if (!status.isOK()) return status;
        
        status = checkKeywords(attr);
        if (!status.isOK()) return status;

        if (isDuplicate(attr)) {
            return MdfValidationCore.newStatus(
                    "A property with the same name is already existing in this class, please choose another name",
                    IStatus.ERROR);
        }

        MdfEntity type = attr.getType();
        if (type == null) {
            return MdfValidationCore.newStatus("An attribute type must be specified",
                    IStatus.ERROR);
        }
       	type = attr.getType();

    	if (type == null || !(type instanceof MdfPrimitive) || MdfEntityImpl.isProxy(type)) {
            return MdfValidationCore.newStatus(attr.getType().getQualifiedName()
                    + " is not a valid attribute type", IStatus.ERROR);
        } else if (type != null && type instanceof MdfBusinessType) {
        	if (leadsToCyclicDependency(type, attr.getParentClass().getParentDomain())) {
                return MdfValidationCore.newStatus(
                        "Attribute type \'"+type.getQualifiedName()
                        + "\' results in cyclic reference with \'"
                        + ((MdfBusinessType) type).getParentDomain().getQualifiedName() + "\' domain",
                        IStatus.ERROR);
        	}
        } else {
            String dflt = attr.getDefault();
            if (dflt != null) {
                if (!isValidDefaultValue(attr)) {
                    return MdfValidationCore.newStatus(
                            "The default value is not valid for the chosen type",
                            IStatus.ERROR);
                }
            }
        }

        IStatus sta = validateForMultiplicity(attr, type);
        if(sta != MdfValidationCore.STATUS_OK) return sta;
        
        
        // Check multiple primary keys
        if (attr.isPrimaryKey()) {
            List pks = new ArrayList();

            Iterator it = attr.getParentClass().getPrimaryKeys(true).iterator();
            while (it.hasNext()) {
                pks.add(((MdfProperty) it.next()).getName());
            }

            if (!pks.isEmpty()) {
                if (pks.size() != 1 || !pks.contains(name))
                    return MdfValidationCore.newStatus(
                            "The containing class already has a primary-key, classes can have only one primary-key",
                            IStatus.ERROR);
            }
        }
        
        // check Annotation for AttrMaxDbLength & AttrDefaultDisplayLength 
        if ("Code".equals(type.getName()) || "Name".equals(type.getName())) {
        	MdfAnnotation annotation = null;
        	// retrieve max-db-lentgh value from the type 
        	int defaultMaxDbLen = 60; 
    		annotation = type.getAnnotation("http://www.odcgroup.com/mdf/ext/constraints", "Constraints");
    		if (annotation != null) {
    			MdfAnnotationProperty prop = annotation.getProperty("maxLength");
    			if (prop != null) {
    				String value = prop.getValue();
    				defaultMaxDbLen = Integer.valueOf(value);
    			}
    		} 
           	annotation = attr.getAnnotation(TRIPLEA_NAMESPACE, TRIPLE_A_NAME); 
        	if (annotation != null) {
        		MdfAnnotationProperty aProp = null;
        		// check value of AttrMaxDbLength
        		int maxDbLen = defaultMaxDbLen;
        		aProp = annotation.getProperty("AttrMaxDbLength");
        		if (aProp != null) {
        			String value = aProp.getValue();
        			maxDbLen = Integer.valueOf(value);
        			if (maxDbLen < 0 || maxDbLen > defaultMaxDbLen) {
                        return MdfValidationCore.newStatus(
                                "Annotation "+TRIPLE_A_NAME+"("+TRIPLEA_NAMESPACE+")/AttrMaxDbLength cannot be greater than "+defaultMaxDbLen,
                                IStatus.ERROR);
        			}
        		}
        		// check value of AttrDefaultDisplayLength
        		aProp = annotation.getProperty("AttrDefaultDisplayLength");
        		if (aProp != null) {
        			String value = aProp.getValue();
        			int displayLen = Integer.valueOf(value);
        			if (displayLen < 0 || displayLen > maxDbLen) {
                        return MdfValidationCore.newStatus(
                                "Annotation "+TRIPLE_A_NAME+"("+TRIPLEA_NAMESPACE+")/AttrDefaultDisplayLength cannot be greater than "+maxDbLen,
                                IStatus.ERROR);
        			}
        		}
        	}

        }
        
        return MdfValidationCore.STATUS_OK;
    }

    protected IStatus validateForMultiplicity(MdfAttribute attr, MdfEntity type) {
    	// DS4858: Attribute with Multiplicity MANY
	    if (attr.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
	    	if (!(type instanceof MdfEnumeration)) {
	    		return MdfValidationCore.newStatus(
	                    "Attribute with multiplicity Many must be based on an Enumeration.",
	                    IStatus.ERROR);
	    	}
    	}
        
        // DS4858: Attribute with Multiplicity MANY
	    if (attr.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
	    	if (type instanceof MdfEnumeration) {
	    		if ("EnumMask".equals(((MdfEnumeration) type).getType().getName())) {
	    			return MdfValidationCore.newStatus(
	    				"The multiplicity must be Many when the underlying business type is EnumMask.",
	                    IStatus.ERROR);
	    		}
	    	}
    	}		
	    return MdfValidationCore.STATUS_OK;
	}

	private static boolean isValidDefaultValue(MdfAttribute attr) {
        String dflt = attr.getDefault();
        MdfPrimitive type = (MdfPrimitive) attr.getType();

        if (attr.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
            return isValidValue(type, dflt);
        } else {
            StringTokenizer token = new StringTokenizer(dflt, ",");
            while (token.hasMoreTokens()) {
                if (!isValidValue(type, token.nextToken().trim())) {
                    return false;
                }
            }
            return true;
        }
    }

    private static boolean isValidDefaultValue(MdfDatasetDerivedProperty prop) {
        String dflt = prop.getDefault();
        MdfPrimitive type = (MdfPrimitive) prop.getType();

        if (prop.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
            return isValidValue(type, dflt);
        } else {
            StringTokenizer token = new StringTokenizer(dflt, ",");
            while (token.hasMoreTokens()) {
                if (!isValidValue(type, token.nextToken().trim())) {
                    return false;
                }
            }
            return true;
        }
    }
    
    private static boolean isValidValue(MdfPrimitive type, String value) {
        if (type instanceof MdfEnumeration) {
            MdfEnumeration e = (MdfEnumeration) type;
            return (e.getValue(value) != null);
        } else if (type instanceof MdfBusinessType) {
        	MdfBusinessType bType = (MdfBusinessType) type;
        	if (bType.getType() != null) {
        		return PrimitiveTypeValidator.isValidValue(bType.getType(), value);
        	} else {
            	return false;        		
        	}
        } else {
            return PrimitiveTypeValidator.isValidValue(type, value);
        }
    }

    public IStatus validate(MdfDataset dataset) throws ExecutionException {
        String name = dataset.getName();

        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("A dataset name must be specified",
                    IStatus.ERROR);
        }

        IStatus status = JavaConventions.validateJavaTypeName(name,
                SOURCE_LEVEL, COMPLIANCE_LEVEL);
        if (!status.isOK()) {
            return MdfValidationCore.newStatus("'" + name
                    + "' is not a valid dataset name", IStatus.ERROR);
        }
        
        status = checkKeywords(dataset);
        if (!status.isOK()) return status;

        if (isDuplicate(dataset)) {
            return MdfValidationCore.newStatus(
                    "An entity with the same name is already existing in this domain, please choose another name",
                    IStatus.ERROR);
        }
        
        if (isNameAlreadyTaken(dataset)) {
        	return MdfValidationCore.newStatus("A Dataset with the " +
        			"same name is already existing in the same project or dependencies." +
        			" Please choose another name", 
        			IStatus.ERROR);
        }

        MdfEntity baseClass;
        try {
        	baseClass = dataset.getBaseClass();
        } catch (ClassCastException e) {
        	return MdfValidationCore.newStatus(
        			"The base class is not class", IStatus.ERROR);
        }

        if (baseClass == null) {
            return MdfValidationCore.newStatus("A base class must be specified",
                    IStatus.ERROR);
        }

        if (MdfEntityImpl.isProxy(baseClass)) {
            return MdfValidationCore.newStatus(baseClass.getQualifiedName()
                    + " is not a valid class", IStatus.ERROR);
        }
        
        // OCS-26566 : check primary key integrity
    	MdfClass clazz = (MdfClass) baseClass;

		if (clazz.isAbstract()) {
			return MdfValidationCore.newStatus("Base class cannot be abstract", IStatus.ERROR);
		}
    	if (clazz.getAttributesRef().isEmpty()) {
                return MdfValidationCore.newStatus("Base class does not have a primary key (or business keys)",
                        IStatus.ERROR);
    	}
    	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((EObject)dataset.getParentDomain()).eResource());
    	
    	if(ofsProject==null) {
    		return MdfValidationCore.STATUS_OK;
    	}
    	
    	if (isSchemaGenerationEnabled(ofsProject)) {
    		if (leadsToCyclicDependency(clazz, dataset.getParentDomain())) {
            	return MdfValidationCore.newStatus("Base-class \'"+clazz.getQualifiedName() 
            			+ "\' results in cyclic reference with \'" 
            			+ clazz.getParentDomain().getQualifiedName()+"\' domain", 
            			IStatus.ERROR);
    		}
    	}
        
        if (dataset.isLinked()) {
        	String linkedDs = getAlreadyLinkedDataset(dataset);
        	if (linkedDs != null) {
        		 return MdfValidationCore.newStatus("Dataset \'" + linkedDs 
							+ "\' is already linked to the base class \'"
							+ baseClass.getQualifiedName().toString() 
							+ "\'. Only one default linked dataset is allowed per Class",
							IStatus.ERROR);
        	}
        }

        return MdfValidationCore.STATUS_OK;
    }

    public IStatus validate(MdfDatasetDerivedProperty prop) {
        String name = prop.getName();

        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("A property name must be specified",
                    IStatus.ERROR);
        }
        
        IStatus status = JavaConventions.validateFieldName(name, SOURCE_LEVEL,
                COMPLIANCE_LEVEL);
        if (!status.isOK()) {
            return MdfValidationCore.newStatus("'" + name
                    + "' is not a valid property name", IStatus.ERROR);
        }
        
        status = checkKeywords(prop);
        if (!status.isOK()) return status;
        
        if (isDuplicate(prop)) {
            return MdfValidationCore.newStatus(
                    "A property with the same name is already existing in this dataset, please choose another name",
                    IStatus.ERROR);
        }
        
        MdfEntity type = prop.getType();
        if (type == null) {
            return MdfValidationCore.newStatus("A property type must be specified",
                    IStatus.ERROR);
        }

        if (!(type instanceof MdfPrimitive || type instanceof MdfDataset) || MdfEntityImpl.isProxy(type)) {
            return MdfValidationCore.newStatus(type.getQualifiedName()
                    + " is not a valid attribute type", IStatus.ERROR);
        } else if (type instanceof MdfBusinessType) {
        	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((EObject)prop).eResource());
        	if (ofsProject!=null && isSchemaGenerationEnabled(ofsProject)){
        		if (leadsToCyclicDependency((MdfBusinessType) type, 
        				prop.getParentDataset().getParentDomain())) {
        			return MdfValidationCore.newStatus("Type \'"+type.getQualifiedName() 
                			+ "\' results in cyclic reference with \'" 
                			+ type.getQualifiedName().getDomain()
                			+ "\' domain", IStatus.ERROR);
        		}
        	}
        } else {
            String dflt = prop.getDefault();
            if (dflt != null) {
                if (!isValidDefaultValue(prop)) {
                    return MdfValidationCore.newStatus(
                            "The default value is not valid for the chosen type",
                            IStatus.ERROR);
                }
            }
        }
        
        
        return MdfValidationCore.STATUS_OK;
        
    }
    
    
    @SuppressWarnings("rawtypes")
	public IStatus validate(MdfDatasetMappedProperty prop) {
        String name = prop.getName();
//        if (name.equals("denom")) {
//        	int i = 0;
//        }
        IStatus status;
        if (MdfValidatorUtil.isNullOrEmpty(name)) {
            return MdfValidationCore.newStatus("A property name must be specified",
                    IStatus.ERROR);
        }

        status = checkMaxDisplayLengthAndMaxDbLength(prop);
        if (!status.isOK()) return status;
        
        status = JavaConventions.validateFieldName(name, SOURCE_LEVEL,
                COMPLIANCE_LEVEL);
        if (!status.isOK()) {
            return MdfValidationCore.newStatus("'" + name
                    + "' is not a valid property name", IStatus.ERROR);
        }
        
        status = checkKeywords(prop);
        if (!status.isOK()) return status;

        if (isDuplicate(prop)) {
            return MdfValidationCore.newStatus(
                    "A property with the same name is already existing in this dataset, please choose another name",
                    IStatus.ERROR);
        }

        MdfEntity type = prop.getType();
        if (type instanceof MdfDataset) {
            if (MdfEntityImpl.isProxy(type)) {
                return MdfValidationCore.newStatus(type.getQualifiedName()
                        + " is not a valid link dataset", IStatus.ERROR);
            }

            Stack props = PropertiesStack.getStack(prop.getParentDataset(),
                    prop.getPath());
            MdfProperty p = (MdfProperty) props.peek();
            if (p != null && !p.getType().equals(((MdfDataset) type).getBaseClass())) {
                return MdfValidationCore.newStatus(type.getQualifiedName()
                        + " is not a valid link dataset", IStatus.ERROR);
            }
        }

        // TODO: Verify the path and the validity of the overriding dataset if
        // any.
        String path = prop.getPath();
        if (path == null || path.length() == 0) {
            return MdfValidationCore.newStatus("A property path must be specified",
                    IStatus.ERROR);
        } else {
            PathValidator validator = new PathValidator(prop.getParentDataset());
            PathVisitor.visitPath(path, validator);
            status = validator.getStatus();
            if (!status.isOK()) return status;            

            Stack props = PropertiesStack.getStack(prop.getParentDataset(), path);
            MdfProperty p = (MdfProperty) props.peek();
            if (p instanceof MdfAssociation && !(prop.getType() instanceof MdfDataset)) {
            	return MdfValidationCore.newStatus("A linked Dataset must be specified", IStatus.ERROR);
            }
            if (p instanceof MdfAssociation) {
            	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((EObject)p).eResource());
            	if (ofsProject!=null && isSchemaGenerationEnabled(ofsProject)){
            		if (leadsToCyclicDependency(((MdfAssociation) p).getParentClass(), 
            				prop.getParentDataset().getParentDomain())) {
            			return MdfValidationCore.newStatus("Path \'"+p.getQualifiedName() 
                    			+ "\' results in cyclic reference with \'" 
                    			+ p.getQualifiedName().getDomain()
                    			+ "\' domain", IStatus.ERROR);
            		}
            	}
            }
        }

        if (type instanceof MdfDataset) {
        	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((EObject)type).eResource());
        	MdfDataset linked = (MdfDataset) type;
        	if (ofsProject!=null && isSchemaGenerationEnabled(ofsProject)){
        		if (leadsToCyclicDependency(linked, prop.getParentDataset().getParentDomain())) {
        			return MdfValidationCore.newStatus("Linked dataset \'"+linked.getQualifiedName() 
                			+ "\' results in cyclic reference with \'" 
                			+ prop.getParentDataset().getParentDomain().getQualifiedName()
                			+ "\' domain", IStatus.ERROR);
        		}
        	}
        }

        return MdfValidationCore.STATUS_OK;
    }

      
	/**
	 * Check properties AttrDefaultDisplayLength and AttrMaxDbLength defined in
	 * the Annotation TripleA (http:://www.odcgroup.com/mdf/aaa)
	 * 
	 * @param prop
	 * @return
	 */
	private IStatus checkMaxDisplayLengthAndMaxDbLength(MdfDatasetMappedProperty prop) {
		
		// read the value of the property "AttrMaxDbLength"
		String strDbLength = ModelAnnotationHelper.getMaxDatabaseLengthFromAnnotation(prop);
		if (strDbLength == null) {
			/*
			 * the value is not redefined on this dataset attribute.
			 * try to inherit the value.
			 */
			strDbLength = ModelAnnotationHelper.getMaxDatabaseLength(prop.getParentDataset(), prop);
			if (strDbLength == null) {
				// not supported, we can return
				return Status.OK_STATUS; 
			}
			
			// read the value of the property "AttrDefaultDisplayLength"
			String strDisplayLength = ModelAnnotationHelper.getDisplayLengthFromAnnotation(prop);
			if (strDisplayLength == null) {
				// not defined on this dataset attribute, we can return.
				return Status.OK_STATUS;
			}
			
			/*
			 * the display length is defined on this dataset attribute
			 * check the value is <= dblength
			 */
			int dbLength = Integer.valueOf(strDbLength).intValue();
			int displayLength = Integer.valueOf(strDisplayLength).intValue();
			if (displayLength > dbLength) {
				return MdfValidationCore.newStatus("Annotation TripleA/AttrDefaultDisplayLength (" +
					displayLength + ") must not be greater than database length (" + dbLength + ")", 
					IStatus.ERROR);
			}
		} else {
			/*
			 * the value of the property "AttrMaxDbLength" is redefined for this dataset attribute.
			 * try to get the value from the mapped attribute, in order to compare them
			 */
			MdfProperty mappedAttribute = ModelAnnotationHelper.getMappedAttribute(prop);
			if (mappedAttribute == null) {
				// cannot find it, stop here another rule will complain
				return Status.OK_STATUS;
			}

			String strMpdAttrDbLength = ModelAnnotationHelper.getMaxDatabaseLength(mappedAttribute);
			if (strMpdAttrDbLength == null) {
				// not supported, so nothing to compare
				return Status.OK_STATUS;
			}
			
			// the maxdblength is supported 
			int dbLength = Integer.valueOf(strDbLength).intValue();
			int mpdAttrDbLength = Integer.valueOf(strMpdAttrDbLength).intValue();
			if (dbLength > mpdAttrDbLength) {
				return MdfValidationCore.newStatus("Annotation TripleA/MaxDbLength (" + dbLength + 
					") must be less or equals to (" + mpdAttrDbLength + ")", 
					IStatus.ERROR);
			}
			
			// now verify the value of the display length
			// read the value of the property "AttrDefaultDisplayLength"
			String strDisplayLength = ModelAnnotationHelper.getDisplayLengthFromAnnotation(prop);
			if (strDisplayLength == null) {
				// not defined on this dataset attribute
				// try to retrieve the inherited value and then compare it with the dblength.
				String strInheritedDisplayLength = ModelAnnotationHelper.getDisplayLength(mappedAttribute);
				if (strInheritedDisplayLength != null) {
					int inheritedDisplayLength = Integer.valueOf(strInheritedDisplayLength).intValue();
					if (inheritedDisplayLength > dbLength) {
						return MdfValidationCore.newStatus("The Annotation TripleA/AttrMaxDbLength (" + dbLength + 
								") cannot be less than the inherited annotation TripleA/AttrDefaultDisplayLength (" + inheritedDisplayLength+ ").", 
								IStatus.ERROR);
	
					}
				}
				return Status.OK_STATUS;
			}

			// the display length is supported, no check the value
			int displayLength = Integer.valueOf(strDisplayLength).intValue();
			if (displayLength > dbLength) {
				return MdfValidationCore.newStatus("Annotation TripleA/AttrDefaultDisplayLength (" +
					displayLength + ") must not be greater than database length (" + dbLength + ")", 
					IStatus.ERROR);
			}
		}
		
		return Status.OK_STATUS;		
	}
	
    private class PathValidator extends PathVisitor {

        private final MdfDataset root;
        private MdfEntity type;
        private IStatus status = MdfValidationCore.STATUS_OK;
        private MdfProperty prop = null;

        private PathValidator(MdfDataset root) {
            this.root = root;
            this.type = root.getBaseClass();
        }

        public boolean visit(String name) {
            if (type instanceof MdfClass) {
                prop = ((MdfClass) type).getProperty(name);

                if (prop == null) {
                    prop = ModelHelper.getReverseAssociation(
                            root.getParentDomain(), (MdfClass) type, name);
                }

                if (prop == null) {
                    status = MdfValidationCore.newStatus("Property '" + name
                            + "' does not exist for class "
                            + type.getQualifiedName().toString(), IStatus.ERROR);
                    return false;
                } else {
                    type = prop.getType();
                    return true;
                }
            } else if(type==null) {
                status = MdfValidationCore.newStatus("Base class of dataset '"+ root.getName() + "' cannot be found.", IStatus.WARNING);
                return false;
            } else {
                status = MdfValidationCore.newStatus("Property '" + name
                        + "' does not exist for type "
                        + type.getQualifiedName().toString(), IStatus.ERROR);
                return false;
            }
        }

        public IStatus getStatus() {
            return status;
        }
    }

	/**
	 * isDuplicateName checks for any two or more domain's with same name .
	 * 
	 * 
	 * @param domain
	 * @return boolean
	 * @throws ExecutionException 
	 */
	private boolean isDuplicateName(MdfDomain domain) throws ExecutionException {	
		Resource resource = ((EObject) domain).eResource();
    	if (resource == null) {
			return false;
		}
    	QualifiedName name = QualifiedName.create(domain.getQualifiedName().getQualifiedName());
    	return DomainRepositoryUtil.isDuplicateDomain(name, resource);
	}
	
    private boolean isDuplicate(MdfEntity entity) {
        MdfDomain domain = entity.getParentDomain();
        if(domain==null) return false;
        
        String name = entity.getName();

        if (validateNew) {
            MdfEntity other = domain.getEntity(name);
            if (other != null) {
                return true;
            }

            other = domain.getDataset(name);
            return (other != null);
        } else {
            int count = getDuplicateCount(domain.getClasses(), entity);
            count += getDuplicateCount(domain.getDatasets(), entity);
            count += getDuplicateCount(domain.getEnumerations(), entity);
            count += getDuplicateCount(domain.getBusinessTypes(), entity);

            return (count > 1);
        }
    }
    
    /**
     * @param dataset
     * @return
     */
    private String getAlreadyLinkedDataset(MdfDataset dataset) {
    	MdfClass baseClass = dataset.getBaseClass();
    	String retVal = null;
    	Resource resource = ((EObject)dataset).eResource();
    	if (resource != null) {
    		baseClass = (MdfClass) MdfEcoreUtil.getMdfEntity(resource, baseClass.getQualifiedName());
	    	MdfDomain currentDomain = (MdfDomain) resource.getContents().get(0);
	    	retVal = getLinkedDataset(currentDomain.getDatasets(), dataset, baseClass);
	    	if (retVal == null) {
	        	Collection<MdfDomain> domains = DomainRepositoryUtil.getAllMdfDomains(resource);        	
	        	for (Object obj : domains) {
	    			if (obj instanceof MdfDomain) {
	    				MdfDomain domain = (MdfDomain) obj;
	    				if (!domain.getQualifiedName().equals(currentDomain.getQualifiedName())) {
	    					retVal = getLinkedDataset(domain.getDatasets(), dataset, baseClass);	
	    				}
	    				if (retVal != null) {
	    					return retVal;
	    				}
	    			}
	    		}
	    	}    	
    	}
    	return retVal;
    }
    
    /**
     * @param datasets
     * @param dataset
     * @param baseClass
     * @return
     */
    private String getLinkedDataset(List<MdfDataset> datasets, MdfDataset dataset, MdfClass baseClass) {
    	for (MdfDataset ds : datasets) {
			if (!dataset.getQualifiedName().equals(ds.getQualifiedName())) {
				if (ds.getBaseClass() != null && baseClass!=null && ds.getBaseClass().getQualifiedName() !=null 
						&& ds.getBaseClass().getQualifiedName().equals(baseClass.getQualifiedName()) 
						&& ds.isLinked()) {
					return ds.getQualifiedName().toString();
				}
			}
		}
    	return null;
    }
    
    /**
     * DS-1176 
     * checks for duplicate names at the package level for all domains
     * @param entity
     * @return
     * @throws ExecutionException 
     */
	private boolean checkDuplicateInDomainsWithSamePackage(MdfEntity entity) throws ExecutionException {
    	if(entity.getParentDomain()==null) return false;

    	boolean duplicate = false;
    	
        String thispack = JavaAspect.getPackage(entity.getParentDomain());
        Resource resource = ((EObject) entity).eResource();
    	if(resource == null) {
    		return false;
    	}
    	List<MdfDomain> domains = DomainRepositoryUtil.getAllMdfDomains(resource);
    	for (MdfDomain domain : domains) {
			String pckage = JavaAspect.getPackage(domain);
			if (thispack.equals(pckage)) {
				if (!entity.getParentDomain().getQualifiedName().equals(domain.getQualifiedName())) {
					int count = getDuplicateCount(domain.getClasses(), entity);
		            count += getDuplicateCount(domain.getDatasets(), entity);
		            count += getDuplicateCount(domain.getEnumerations(), entity);
		            count += getDuplicateCount(domain.getBusinessTypes(), entity);
		            duplicate = (count > 0);
					if (duplicate) {
						return true;
					}
				}
			}
		}
    	return duplicate;
    }
    
	
    /**
     * check whether the entity name is already taken in the project
     * and its dependencies
     * 
     * @param entity
     * @return
     * @throws ExecutionException 
     */
	private boolean isNameAlreadyTaken(MdfEntity entity) throws ExecutionException {
    	if(entity.getParentDomain()==null) return false;

    	boolean duplicate = false;
    	Resource resource = ((EObject) entity).eResource();
    	if(resource == null) {
    		return false;
    	}
    	List<MdfDomain> domains = DomainRepositoryUtil.getAllMdfDomains(resource);
        for (MdfDomain domain : domains) {
        	MdfName qname = domain.getQualifiedName();
        	if (!entity.getParentDomain().getQualifiedName().equals(qname)) {
				int count = getQualifiedNameDuplicateCount(domain.getClasses(), entity);
	            count += getQualifiedNameDuplicateCount(domain.getDatasets(), entity);
	            count += getQualifiedNameDuplicateCount(domain.getEnumerations(), entity);
	            count += getQualifiedNameDuplicateCount(domain.getBusinessTypes(), entity);
	            duplicate = (count > 0);
				if (duplicate) {
					return true;
				}
			}
		}
    	return duplicate;
    }

    private boolean isDuplicate(MdfProperty p) {
        MdfClass klass = p.getParentClass();
        if(klass==null) return false;
        if (validateNew) {
            return klass.getProperty(p.getName()) != null;
        } else {
            return getDuplicateCount(klass.getProperties(), p) > 1;
        }
    }

    private boolean isDuplicate(MdfEnumValue v) {
        MdfEnumeration enumeration = v.getParentEnumeration();

        if (validateNew) {
            return enumeration.getValue(v.getName()) != null;
        } else {
            return getDuplicateCount(enumeration.getValues(), v) > 1;
        }
    }

    @SuppressWarnings("rawtypes")
	private boolean isDuplicateValue(MdfEnumValue v) {
        MdfEnumeration enumeration = v.getParentEnumeration();
        Iterator it = enumeration.getValues().iterator();

        if (validateNew) {
            while (it.hasNext()) {
                MdfEnumValue val = (MdfEnumValue) it.next();
                if (val.getValue().equals(v.getValue())) {
                    return true;
                }
            }
        } else {
            int count = 0;

            while (it.hasNext()) {
                MdfEnumValue val = (MdfEnumValue) it.next();
                final String value = val.getValue();
				if (value != null && value.equals(v.getValue())) {
                    count++;
                    if (count > 1) return true;
                }
            }
        }

        return false;
    }

    private boolean isDuplicate(MdfDatasetProperty prop) {
        MdfDataset dataset = prop.getParentDataset();

        if (validateNew) {
            return dataset.getProperty(prop.getName()) != null;
        } else {
            return getDuplicateCount(dataset.getProperties(), prop) > 1;
        }
    }

    @SuppressWarnings("rawtypes")
	private int getDuplicateCount(List elements, MdfModelElement model) {
        int count = 0;
        Iterator it = elements.iterator();

        while (it.hasNext()) {
            MdfModelElement element = (MdfModelElement) it.next();
            if (element.getName().equals(model.getName())) {
                count++;
                if (count > 1) return count;
            }
        }

        return count;
    }
    
    @SuppressWarnings("rawtypes")
	private int getQualifiedNameDuplicateCount(List elements, MdfModelElement model) {
        int count = 0;
        Iterator it = elements.iterator();

        while (it.hasNext()) {
            MdfModelElement element = (MdfModelElement) it.next();
            if (element.getQualifiedName().equals(model.getQualifiedName())) {
                count++;
            }
        }

        return count;
    }

    protected IStatus checkKeywords(MdfModelElement model) {
        String name = model.getName();

        if (JavaKeywordChecker.getInstance().isKeyword(name)) {
            return MdfValidationCore.newStatus("'" + name
                    + "' is a reserved Java keyword", IStatus.ERROR);
        }

        // Limit SQL Keyword check to what will actually be in the database
//        if (model instanceof MdfAttribute || model instanceof MdfAssociation) {
//            MdfProperty p = (MdfProperty) model;
//            String tableName = SQLAspect.getSqlName(p.getParentClass());

            // XXX: Attention, if database model is fully generated this needs
            // to be changed.
            
            
            // Commented out SQL and MDF Keyword cheker for DS-4979
//            if (tableName != null) {
//                String sqlName = SQLAspect.getSqlName(model);
//                if (sqlName == null) {
//                    if (SQLKeywordChecker.getInstance().isKeyword(name)) {
//                        return MdfValidationCore.newStatus(
//                                "'"
//                                        + name
//                                        + "' is a reserved SQL keyword, "
//                                        + "you must override this element's SQL name or rename it",
//                                IStatus.ERROR);
//                    }
//                }
//            }
//        }
        
//        if (model instanceof MdfClass || model instanceof MdfDataset) {
//	        if (MDFKeywordChecker.getInstance().isKeyword(name)) {
//	            return MdfValidationCore.newStatus("'" + name
//	                    + "' is a reserved Odyssey keyword", IStatus.ERROR);
//	        }
//        }

        return MdfValidationCore.STATUS_OK;
    }

	/**
	 * @param validateNew the validateNew to set
	 */
	public void setValidateNew(boolean validateNew) {
		this.validateNew = validateNew;
	}
	
	/**
	 * @param ofsProject
	 * @return
	 */
	private boolean isSchemaGenerationEnabled(IOfsProject ofsProject) {
		if (null == ofsProject) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
        ProjectPreferences preferences = new ProjectPreferences(ofsProject.getProject(), GenerationCore.PLUGIN_ID);
		return preferences.getBoolean("com.odcgroup.mdf.generation.XMLSchemaGenerator", false);
	}
}
