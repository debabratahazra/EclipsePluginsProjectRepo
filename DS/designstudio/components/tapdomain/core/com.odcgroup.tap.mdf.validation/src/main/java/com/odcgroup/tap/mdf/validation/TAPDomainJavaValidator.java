package com.odcgroup.tap.mdf.validation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.odcgroup.domain.validation.DomainValidator;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.ecore.util.PrimitiveTypeValidator;
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
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.model.util.ModelAnnotationHelper;
import com.odcgroup.mdf.utils.ModelHelper;
import com.odcgroup.mdf.utils.PathVisitor;
import com.odcgroup.mdf.utils.PropertiesStack;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This class implements specific constraints for T24 Domain elements
 */
public class TAPDomainJavaValidator implements DomainValidator {
	
    private static final String TRIPLE_A_NAME = "TripleA";
	private static final String TRIPLEA_NAMESPACE = "http://www.odcgroup.com/mdf/aaa";
	private static final String SOURCE_LEVEL = "5.0";
    private static final String COMPLIANCE_LEVEL = "5.0";

    private class PathValidator extends PathVisitor {

        private final MdfDataset root;
        private MdfEntity type;
        private String message = null;
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
                	message = "Property '" + name
                            + "' does not exist for class "
                            + type.getQualifiedName().toString();
                    //, IStatus.ERROR);
                    return false;
                } else {
                    type = prop.getType();
                    return true;
                }
            } else if(type==null) {
                message = "Base class of dataset '"+ root.getName() + "' cannot be found."; 
                //IStatus.WARNING);
                return false;
            } else {
                message = "Property '" + name
                        + "' does not exist for type "
                        + type.getQualifiedName().toString();
                //, IStatus.ERROR);
                return false;
            }
        }

        public String getMessage() {
            return message;
        }
    }
    
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
    
    @SuppressWarnings("unchecked")
	private String getAlreadyLinkedDataset(MdfDataset dataset) {
    	MdfClass baseClass = dataset.getBaseClass();
    	String retVal = null;
    	Resource resource = ((EObject)dataset).eResource();
    	if (resource != null) {
    		baseClass = (MdfClass) MdfEcoreUtil.getMdfEntity(resource, baseClass.getQualifiedName());
	    	MdfDomain currentDomain = (MdfDomain) resource.getContents().get(0);
	    	retVal = getLinkedDataset(currentDomain.getDatasets(), dataset, baseClass);
	    	if (retVal == null) {
	        	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((MdfDatasetImpl)dataset).eResource());
	        	if (ofsProject == null) return null;
	        	DomainRepository repository = DomainRepository.getInstance(ofsProject);
	        	Collection<MdfDomain> domains = repository.getDomains();        	
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
	 * Check properties AttrDefaultDisplayLength and AttrMaxDbLength defined in
	 * the Annotation TripleA (http:://www.odcgroup.com/mdf/aaa)
	 * 
	 * @param prop
	 * @return
	 */
	private IStatus checkMaxDisplayLengthAndMaxDbLength(ValidationMessageAcceptor messageAcceptor, MdfDatasetMappedProperty prop) {
		
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
				return Activator.newStatus("Annotation TripleA/AttrDefaultDisplayLength (" +
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
				return Activator.newStatus("Annotation TripleA/MaxDbLength (" + dbLength + 
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
						return Activator.newStatus("The Annotation TripleA/AttrMaxDbLength (" + dbLength + 
								") cannot be less than the inherited annotation TripleA/AttrDefaultDisplayLength (" + inheritedDisplayLength+ ").", 
								IStatus.ERROR);
	
					}
				}
				return Status.OK_STATUS;
			}

			// the display length is supported, no check the value
			int displayLength = Integer.valueOf(strDisplayLength).intValue();
			if (displayLength > dbLength) {
				return Activator.newStatus("Annotation TripleA/AttrDefaultDisplayLength (" +
					displayLength + ") must not be greater than database length (" + dbLength + ")", 
					IStatus.ERROR);
			}
		}
		
		return Status.OK_STATUS;		
	}

	
    // DS-5624 This method used to be public helper in MMLResource (which was deleted in DS-5624) 
    @SuppressWarnings("unchecked")
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
						&& type.getQualifiedName() != null && !domain.equals(type.getParentDomain())) {
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

	private boolean isSchemaGenerationEnabled(IOfsProject ofsProject) {
		if (null == ofsProject) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
        ProjectPreferences preferences = new ProjectPreferences(ofsProject.getProject(), "com.odcgroup.workbench.generation"/*GenerationCore.PLUGIN_ID*/);
		return preferences.getBoolean("com.odcgroup.mdf.generation.XMLSchemaGenerator", false);
	}
	
    private boolean isValidValue(MdfPrimitive type, String value) {
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

    private boolean isValidDefaultValue(MdfDatasetDerivedProperty prop) {
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
    
	private void checkDatasetDerivedProperty(ValidationMessageAcceptor messageAcceptor, MdfDatasetDerivedProperty datasetDerivedProperty) {
		
		validateFieldName(messageAcceptor, datasetDerivedProperty); 
        
        MdfEntity type = datasetDerivedProperty.getType();
        if (type == null) {
        	messageAcceptor.acceptError(
        			"A property type must be specified", 
        			(EObject)datasetDerivedProperty,
        			MdfPackage.Literals.MDF_DATASET_DERIVED_PROPERTY__TYPE,
					ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
					null);
            return;
        }

        if (!(type instanceof MdfPrimitive || type instanceof MdfDataset) || MdfEntityImpl.isProxy(type)) {
        	messageAcceptor.acceptError(
        			type.getQualifiedName() + " is not a valid attribute type", 
        			(EObject)datasetDerivedProperty,
        			MdfPackage.Literals.MDF_DATASET_DERIVED_PROPERTY__TYPE,
					ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
					null);
        } else if (type instanceof MdfBusinessType) {
        	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((EObject)datasetDerivedProperty).eResource());
        	if (ofsProject!=null && isSchemaGenerationEnabled(ofsProject)) {
        		if (leadsToCyclicDependency((MdfBusinessType) type, 
        				datasetDerivedProperty.getParentDataset().getParentDomain())) {
        			messageAcceptor.acceptError(
        					"Type \'"+type.getQualifiedName() + "\' results in cyclic reference with \'" + type.getQualifiedName().getDomain() + "\' domain",
                			(EObject)datasetDerivedProperty,
                			MdfPackage.Literals.MDF_DATASET_DERIVED_PROPERTY__TYPE,
                			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
        					null);
        		}
        	}
        } else {
            String dflt = datasetDerivedProperty.getDefault();
            if (dflt != null) {
                if (!isValidDefaultValue(datasetDerivedProperty)) {
                	messageAcceptor.acceptError(
                			"The default value is not valid for the chosen type",
                			(EObject)datasetDerivedProperty,
                    		MdfPackage.Literals.MDF_DATASET_DERIVED_PROPERTY__DEFAULT,
                			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
        					null);
                }
            }
        }
	        
	}

	private void checkDatasetMappedProperty(ValidationMessageAcceptor messageAcceptor, MdfDatasetMappedProperty datasetMappedProperty) {

		validateFieldName(messageAcceptor, datasetMappedProperty); 
		
        checkMaxDisplayLengthAndMaxDbLength(messageAcceptor, datasetMappedProperty);
        
        MdfEntity type = datasetMappedProperty.getType();
        if (type instanceof MdfDataset) {
            if (MdfEntityImpl.isProxy(type)) {
            	messageAcceptor.acceptError(
            			type.getQualifiedName() + " is not a valid link dataset",
            			(EObject)datasetMappedProperty,
                		MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET,
                		ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
    					null);                		
                return;
            }

            @SuppressWarnings("rawtypes")
			Stack props = PropertiesStack.getStack(datasetMappedProperty.getParentDataset(), datasetMappedProperty.getPath());
            MdfProperty p = (MdfProperty) props.peek();
            if (p != null && !p.getType().equals(((MdfDataset) type).getBaseClass())) {
            	messageAcceptor.acceptError(
            			type.getQualifiedName() + " is not a valid link dataset", 
            			(EObject)datasetMappedProperty,
                		MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET,
                		ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
    					null);                		
                return;
            }
        }

        // TODO: Verify the path and the validity of the overriding dataset if any.
        String path = datasetMappedProperty.getPath();
        if (path == null || path.length() == 0) {
        	messageAcceptor.acceptError(
        			"A property path must be specified", 
        			(EObject)datasetMappedProperty,
        			MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__PATH,
            		ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
					null);                		
        } else {
            PathValidator validator = new PathValidator(datasetMappedProperty.getParentDataset());
            PathVisitor.visitPath(path, validator);
            String errorMessage = validator.getMessage();
            if (errorMessage != null) {
            	messageAcceptor.acceptError(
            			errorMessage,  
            			(EObject)datasetMappedProperty,
						MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__PATH,
	            		ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
						null);                		
            	return;
            }

            @SuppressWarnings("rawtypes")
			Stack props = PropertiesStack.getStack(datasetMappedProperty.getParentDataset(), path);
            MdfProperty p = (MdfProperty) props.peek();
            if (p instanceof MdfAssociation && !(datasetMappedProperty.getType() instanceof MdfDataset)) {
            	messageAcceptor.acceptError(
            			"A linked Dataset must be specified", 
            			(EObject)datasetMappedProperty,
            			MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__LINK_DATASET,
            			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
						null);              }
            if (p instanceof MdfAssociation) {
            	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((EObject)p).eResource());
            	if (ofsProject!=null && isSchemaGenerationEnabled(ofsProject)){
            		if (leadsToCyclicDependency(((MdfAssociation) p).getParentClass(), 
            				datasetMappedProperty.getParentDataset().getParentDomain())) {
            			messageAcceptor.acceptError(
            					"Path \'"+p.getQualifiedName() + "\' results in cyclic reference with \'" + p.getQualifiedName().getDomain() + "\' domain", 
                    			(EObject)datasetMappedProperty,
                    			MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__PATH,
                    			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
        						null);              		
            		}
            	}
            }
        }

        if (type instanceof MdfDataset) {
        	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((EObject)type).eResource());
        	MdfDataset linked = (MdfDataset) type;
        	if (ofsProject!=null && isSchemaGenerationEnabled(ofsProject)){
        		if (leadsToCyclicDependency(linked, datasetMappedProperty.getParentDataset().getParentDomain())) {
        			messageAcceptor.acceptError(
        					"Linked dataset \'"+linked.getQualifiedName() 
                			+ "\' results in cyclic reference with \'" 
                			+ datasetMappedProperty.getParentDataset().getParentDomain().getQualifiedName()
                			+ "\' domain", 
                			(EObject)datasetMappedProperty,
                			MdfPackage.Literals.MDF_DATASET_MAPPED_PROPERTY__PATH,
                			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
    						null);
        		}
        	}
        }
	}        

	private void checkDataset(ValidationMessageAcceptor messageAcceptor, MdfDataset dataset) {

		validateTypeName(messageAcceptor, dataset);
		
        MdfEntity baseClass;
        try {
        	baseClass = dataset.getBaseClass();
        } catch (ClassCastException e) {
        	messageAcceptor.acceptError(
        			"The base class is not class", 
        			(EObject)dataset,
        			MdfPackage.Literals.MDF_DATASET__BASE_CLASS,
        			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
					null);
        	return;
        }

        if (baseClass == null) {
        	messageAcceptor.acceptError(
        			"A base class must be specified", 
        			(EObject)dataset,
        			MdfPackage.Literals.MDF_DATASET__BASE_CLASS,
        			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
					null);
            return;
        }

        if (MdfEntityImpl.isProxy(baseClass)) {
        	messageAcceptor.acceptError(
        			baseClass.getQualifiedName() + " is not a valid class", 
        			(EObject)dataset,
        			MdfPackage.Literals.MDF_DATASET__BASE_CLASS,
        			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
					null);
        }
        
        // OCS-26566 : check primary key integrity
    	MdfClass clazz = (MdfClass) baseClass;
		if (clazz.isAbstract()) {
        	messageAcceptor.acceptError(
        			"Base class cannot be abstract", 
        			(EObject)dataset,
        			MdfPackage.Literals.MDF_DATASET__BASE_CLASS,
        			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
					null);
		}
    	if (clazz.getAttributesRef().isEmpty()) {
        	messageAcceptor.acceptError(
        			"Base class does not have a primary key (or business keys)",
        			(EObject)dataset,
               		MdfPackage.Literals.MDF_DATASET__BASE_CLASS,
           			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
					null);
    	}
    	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(((EObject)dataset.getParentDomain()).eResource());    	
    	if(ofsProject==null) {
    		return;
    	}
    	
    	if (isSchemaGenerationEnabled(ofsProject)) {
    		if (leadsToCyclicDependency(clazz, dataset.getParentDomain())) {
            	messageAcceptor.acceptError(
            			"Base-class \'"+clazz.getQualifiedName() + "\' results in cyclic reference with \'" + clazz.getParentDomain().getQualifiedName() + "\' domain", 
            			(EObject)dataset,
            			MdfPackage.Literals.MDF_DATASET__BASE_CLASS,
            			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
						null);
            	return;
    		}
    	}
        
        if (dataset.isLinked()) {
        	String linkedDs = getAlreadyLinkedDataset(dataset);
        	if (linkedDs != null) {
            	messageAcceptor.acceptError(
            			"Dataset \'" + linkedDs + "\' is already linked to the base class \'"
            					+ baseClass.getQualifiedName().toString() 
            					+ "\'. Only one default linked dataset is allowed per Class",
               			(EObject)dataset,
						MdfPackage.Literals.MDF_DATASET__LINKED,
            			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
						null);
        	}
        }
        
	}
	
	private void checkAttributeAnnotation(ValidationMessageAcceptor messageAcceptor, MdfAttribute attribute) {

		MdfEntity type = attribute.getType();	
		if (type == null) return;
	
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
           	annotation = attribute.getAnnotation(TRIPLEA_NAMESPACE, TRIPLE_A_NAME); 
        	if (annotation != null) {
        		MdfAnnotationProperty aProp = null;
        		// check value of AttrMaxDbLength
        		int maxDbLen = defaultMaxDbLen;
        		aProp = annotation.getProperty("AttrMaxDbLength");
        		if (aProp != null) {
        			String value = aProp.getValue();
        			maxDbLen = Integer.valueOf(value);
        			if (maxDbLen < 0 || maxDbLen > defaultMaxDbLen) {
        				messageAcceptor.acceptError(
        						"Annotation "+TRIPLE_A_NAME+"("+TRIPLEA_NAMESPACE+")/AttrMaxDbLength cannot be greater than "+defaultMaxDbLen,
        						(EObject)attribute,
                        		MdfPackage.Literals.MDF_MODEL_ELEMENT__ANNOTATIONS,
                    			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
        						null);
        			}
        		}
        		// check value of AttrDefaultDisplayLength
        		aProp = annotation.getProperty("AttrDefaultDisplayLength");
        		if (aProp != null) {
        			String value = aProp.getValue();
        			int displayLen = Integer.valueOf(value);
        			if (displayLen < 0 || displayLen > maxDbLen) {
        				messageAcceptor.acceptError(
        						"Annotation "+TRIPLE_A_NAME+"("+TRIPLEA_NAMESPACE+")/AttrDefaultDisplayLength cannot be greater than "+maxDbLen,
        						(EObject)attribute,
                        		MdfPackage.Literals.MDF_MODEL_ELEMENT__ANNOTATIONS,
                    			ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
        						null);
        			}
        		}
        	}
        }
	}


	@Override
	public void checkFastMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
		if (element instanceof MdfAttribute) {
			checkAttributeAnnotation(messageAcceptor, (MdfAttribute)element);
		} else if (element instanceof MdfDatasetMappedProperty) {
			checkDatasetMappedProperty(messageAcceptor, (MdfDatasetMappedProperty)element);
		} else if (element instanceof MdfDatasetDerivedProperty) {
			checkDatasetDerivedProperty(messageAcceptor, (MdfDatasetDerivedProperty)element);
		} else if (element instanceof MdfDataset) {
			checkDataset(messageAcceptor, (MdfDataset)element);
		}
	}

	@Override
	public void checkNormalMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
	}

	@Override
	public void checkExpensiveMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
	}
	
    @Override
    public void validateTypeName(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
    	String name = element.getName();
    	if (StringUtils.isNotBlank(name)) {
			if (!JavaConventions.validateJavaTypeName(name, SOURCE_LEVEL, COMPLIANCE_LEVEL).isOK()) {	
				messageAcceptor.acceptError("'" + name + "' is not a valid name",
						(EObject) element,
						MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME,
						ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
						null);
			}
    	}
    }
    
    @Override
    public void validateFieldName(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
    	String name = element.getName();
		if (StringUtils.isNotBlank(name)) {
	    	if (!JavaConventions.validateFieldName(name, SOURCE_LEVEL, COMPLIANCE_LEVEL).isOK()) {
	    		messageAcceptor.acceptError("'" + name + "' is not a valid name",  
						(EObject) element,
	    				MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME,
	    				ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
	    				null);
	    	}
        }
    }
    
	@Override
	public boolean leadsToCyclicDependency(MdfEntity entity, MdfDomain currentDomain) {
    	MdfDomain parentDomain = entity.getParentDomain();
    	if (parentDomain != null && !parentDomain.getQualifiedName().equals(currentDomain.getQualifiedName())) {
    		if (getUsedImports(parentDomain).contains(currentDomain)) {
    			return true;
    		}
    	}
    	return false;
    }
	
}
