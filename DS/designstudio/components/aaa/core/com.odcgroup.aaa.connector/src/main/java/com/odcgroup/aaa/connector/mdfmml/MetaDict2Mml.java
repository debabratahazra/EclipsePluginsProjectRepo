package com.odcgroup.aaa.connector.mdfmml;

import static com.odcgroup.aaa.core.util.NamingHelper.getMMLJavaAttributeNameFromTADictAttributeSQLName;
import static com.odcgroup.aaa.core.util.NamingHelper.getMMLJavaClassNameFromTADictEntitySQLName;
import static com.odcgroup.aaa.core.util.NamingHelper.getMMLJavaNameFromTAEnumName;
import static com.odcgroup.aaa.core.util.NamingHelper.getMetaDictPermName;
import static com.odcgroup.aaa.core.util.NamingHelper.toFirstCharacterUpperCase;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.aaa.connector.domainmodel.DictDataType;
import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.domainmodel.DictPermValue;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributeCalculated;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributeForeignKeyPresentation;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributePermAuth;
import com.odcgroup.aaa.connector.domainmodel.enums.DictAttributeTascView;
import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictRepository;
import com.odcgroup.aaa.connector.internal.nls.Language;
import com.odcgroup.aaa.connector.internal.nls.Translateable;
import com.odcgroup.aaa.connector.internal.util.AAAWidgetRulesUtil;
import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.domain.annotations.JavaAspectDS;
import com.odcgroup.domain.annotations.SQLAspectDS;
import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ecore.MdfReverseAssociationImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.ext.sql.SQLAspect.JpaMessageEnum;
import com.odcgroup.mdf.ext.sql.SQLAspect.LevelEnum;
import com.odcgroup.mdf.ext.tangij.TANGIJTranslationAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfMultiplicity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.model.translation.ModelLocaleHelper;

/**
 * Transformation of T'A Meta Dictionary into an MDF Domain (Classes & Enums).
 *
 * @author Michael Vorburger (DS v1.40 implementation)
 * @author Camille Weber (initial early 2008 POC in ARCH)
 */
public class MetaDict2Mml {

	private static final Logger LOGGER = Logger.getLogger(MetaDict2Mml.class);

	// package visible because used in unit test also, but not outside
	Map<DictEntity, MdfClass> entity2class = new HashMap<DictEntity, MdfClass>();
	
	private final DataType2Primitives dataType2Primitives = new DataType2Primitives();
	
	/** The namespace for custom fields (package visible because used in unit test also, but not outside) */
	static final String CUSTOM_FIELD_NAMESPACE = "http://www.odcgroup.com/mdf/customization";
	 
	/** The name of the annotation containing the custom field information (package visible because used in unit test also, but not outside) */
    static final String CUSTOM_FIELD_ANNOTATION_NAME = "Custom";

    private MetaDictDomains metaDictDomains;
	// DS-2309
	private Map<DictAttribute, MdfProperty> attributes = new HashMap<DictAttribute, MdfProperty>();

	public void createAAADomainFromMetaDict(MetaDictRepository aaaMetaDict, MetaDictDomains domains) throws TA2MMLException
	{
        metaDictDomains = domains;
		// 1. Generate enumeration domain (base on T'A perm values)
		MdfDomainImpl enumDomain = (MdfDomainImpl)MdfFactory.eINSTANCE.createMdfDomain();
		enumDomain.setName(AAACore.getFindroot().toUpperCase()+"Enumerations");
		enumDomain.setNamespace("http://www.odcgroup.com/"+AAACore.getFindroot().toLowerCase()+"-enumerations");

		JavaAspectDS.setPackage(enumDomain, "com.odcgroup."+AAACore.getFindroot().toLowerCase());
		setDomainAnnotationsForJPAGeneration(enumDomain);
		Map<DictAttribute, MdfEnumeration> enums =
			addDictPermValues(
					enumDomain,
					aaaMetaDict.getPermValues(),
					domains.businessTypesDomain);
		
		// 2. Generate entities
		MdfDomainImpl entitiesDomain = (MdfDomainImpl)MdfFactory.eINSTANCE.createMdfDomain();
		entitiesDomain.setName(AAACore.getFindroot().toUpperCase()+"Entities");
		entitiesDomain.setNamespace("http://www.odcgroup.com/"+AAACore.getFindroot().toLowerCase()+"-entities");
                
		JavaAspectDS.setPackage(entitiesDomain, "com.odcgroup."+AAACore.getFindroot().toLowerCase());
		setDomainAnnotationsForJPAGeneration(entitiesDomain);
		
		addDictEntities(entitiesDomain, aaaMetaDict.getEntities(), enums, domains.businessTypesDomain);
		
		// 3. Sort the domains
		sort(enumDomain);
		sort(entitiesDomain);
		
		// 4. Set the result
		domains.enumerationsDomain = enumDomain;
		domains.entitiesDomain = entitiesDomain;
	}

	@SuppressWarnings("unchecked")
	private void sort(MdfDomainImpl domain) {
		// ordering properties according to spec, see DS-3977
		Comparator<MdfModelElement> comparator = new Comparator<MdfModelElement>() {
			public int compare(MdfModelElement e1,
					MdfModelElement e2) {
				return e1.getName().toLowerCase()
						.compareTo(e2.getName().toLowerCase());
			}
		};
		
		Iterator<EObject> it = domain.eAllContents();
		while (it.hasNext()) {
			EObject eObj = it.next();
			EList<MdfModelElement> properties = null;
			if (eObj instanceof MdfClass) {
				MdfClass clazz = (MdfClass) eObj;
				properties = (EList<MdfModelElement>) clazz.getProperties();
			}
			if (eObj instanceof MdfDataset) {
				MdfDataset dataset = (MdfDataset) eObj;
				properties = (EList<MdfModelElement>) dataset.getProperties();
			}
			if (properties != null) {
				ECollections.sort(properties, comparator);
			}
			if (eObj instanceof MdfModelElement) {
				MdfModelElement model = (MdfModelElement)eObj;
				ECollections.sort((EList<MdfAnnotation>)model.getAnnotations(), new Comparator<MdfAnnotation>() {
					public int compare(MdfAnnotation e1, MdfAnnotation e2) {
						if (StringUtils.equals(e1.getNamespace(),e2.getNamespace())) {
							return e1.getName().toLowerCase()
									.compareTo(e2.getName().toLowerCase());
						} else {
							return e1.getNamespace().toLowerCase()
									.compareTo(e2.getNamespace().toLowerCase());
						}
					}
				});
				for (MdfAnnotation annotation : (EList<MdfAnnotation>)model.getAnnotations()) {
					ECollections.sort((EList<MdfAnnotationProperty>)annotation.getProperties(), new Comparator<MdfAnnotationProperty>() {
						public int compare(MdfAnnotationProperty e1, MdfAnnotationProperty e2) {
							return e1.getName().toLowerCase()
									.compareTo(e2.getName().toLowerCase());
						}
					});
				}
			}
		}
		ECollections.sort((EList<MdfClass>)domain.getClasses(), comparator);
		ECollections.sort((EList<MdfDataset>)domain.getDatasets(), comparator);
		ECollections.sort((EList<MdfEnumeration>)domain.getEnumerations(), comparator);
		ECollections.sort((EList<MdfBusinessType>)domain.getBusinessTypes(), comparator);
	}

	/**
	 * Check if the permitted value is a custom one, and if yes add a custom
	 * annotation.
	 *
	 * @param mdfValue
	 * @param dictPermValue
	 *
	 * DS-1778
	 */
	private void addCustomAnnotations(MdfEnumeration mdfEnum, MdfEnumValue mdfValue, DictPermValue dictPermValue) {

		DictAttribute dictAttribute = dictPermValue.getAttribute();
		DictAttributePermAuth permAuth = dictAttribute.getPermAuth();

		boolean isCustom =
			DictAttributePermAuth.USER_USER_PERMITTED_VALUES_ALLOWED_STARTING_100.equals(permAuth) &&
			dictPermValue.getPermValNatE() >= 100 &&
			dictAttribute.getDatatype().getDictID() == 5;

		if (isCustom) {
			doAnnotate(mdfValue, CUSTOM_FIELD_NAMESPACE,
					CUSTOM_FIELD_ANNOTATION_NAME);
		}
	}

	/**
	 * Checks if the attribute is customized, and if yes add a custom annotation.
	 *
	 * @param dictAttribute
	 * @param aaaAttribute
	 * @param mdfFactory
	 *
	 * DS-1778
	 */
	protected void addCustomAnnotations(DictAttribute dictAttribute,
			MdfPropertyImpl aaaAttribute) {
		boolean isCustom = dictAttribute.isCustom()
				&& !"ud_id".equalsIgnoreCase(dictAttribute.getSQLName());
		if (isCustom) {
			doAnnotate(aaaAttribute, CUSTOM_FIELD_NAMESPACE,
					CUSTOM_FIELD_ANNOTATION_NAME);
		}
		if (metaDictDomains != null) { // it's normally never null - except in simple unit test like MetaDict2MmlNoDBTest
			AAAWidgetRulesUtil.createWidgetRulesUtil().checkLongCodeForNameAndCodeBusinessTypes(metaDictDomains.businessTypesDomain, dictAttribute, aaaAttribute);
		}
	}

	private boolean isCustomField(MdfModelElement model) {	 
        return model.getAnnotation(CUSTOM_FIELD_NAMESPACE, CUSTOM_FIELD_ANNOTATION_NAME) != null;	 
    }
	
	/**
	 * @param dictAttribute
	 * @param aaaAttribute
	 * @param ru
	 */
	private void addTripleAAttrMultiLang(DictAttribute dictAttribute, MdfProperty aaaAttribute) {
		if(dictAttribute.getMultiLangF() == 1) {
			AAAAspectDS.setTripleAAttrMultiLanguage(aaaAttribute, "true");
		}
	}

	@SuppressWarnings("unchecked")
	// package visible because used in unit test also, but not outside
	MdfAnnotation doAnnotate(MdfModelElement modelElem, String nameSpace, String annotationName) {
		MdfAnnotationImpl annotation = (MdfAnnotationImpl)MdfFactory.eINSTANCE.createMdfAnnotation();
		annotation.setNamespace(nameSpace);
		annotation.setName(annotationName);
		modelElem.getAnnotations().add(annotation);
		return annotation;
	}

    @SuppressWarnings("unchecked")
	private Map<DictAttribute, MdfEnumeration> addDictPermValues(MdfDomain enumDomain, Map<Long, List<DictPermValue>> permVals,
    		MdfDomain businessTypesDomain) throws TA2MMLException {
        Map<DictAttribute, MdfEnumeration> map = new HashMap<DictAttribute, MdfEnumeration>();
        Set<Entry<Long, List<DictPermValue>>> permValEntries = permVals.entrySet();
        for (Entry<Long, List<DictPermValue>> entry : permValEntries) {
            List<DictPermValue> list = entry.getValue();
            if(list.isEmpty()) throw new TA2MMLException("Attribute with DictID " + entry.getKey() + " has no permValues");
            MdfEnumerationImpl mdfEnum = (MdfEnumerationImpl)MdfFactory.eINSTANCE.createMdfEnumeration();
            DictPermValue firstPermValue = list.get(0);
            DictAttribute dictAttribute = firstPermValue.getAttribute();

        	// DS-5136 - do NOT add dict-id anymore >= r12

            DictDataType dictDataType = dictAttribute.getDatatype();
            if (dictDataType.isSkipped()) {
            	continue;
            }
            // Enums are based on primitive type rather on business type
            if (isEnumMask(dictDataType)) { // DS-6653
            	mdfEnum.setType(businessTypesDomain.getBusinessType("EnumMask"));
            } else {           
            	mdfEnum.setType(dataType2Primitives.getMDFDataType(dictAttribute.getDatatype(), true));
            }
            
            String name = getMMLJavaClassNameFromTADictEntitySQLName(dictAttribute.getDictEntity().getSQLName())
                + toFirstCharacterUpperCase(getMMLJavaAttributeNameFromTADictAttributeSQLName(dictAttribute.getSQLName()));
	        mdfEnum.setName(name);

	        // DS-2182
	        AAAAspectDS.setTripleAEntitySQLName(mdfEnum, dictAttribute.getDictEntity().getSQLName());
	        // DS-2182
	        AAAAspectDS.setTripleAAttributeSQLName(mdfEnum, dictAttribute.getSQLName());

	        if (enumDomain.getEntity(mdfEnum.getName()) == null)  {
	            enumDomain.getEnumerations().add(mdfEnum);
	        }
	        
            map.put(dictAttribute, mdfEnum);
	        for (DictPermValue dictPermValue : list) {
	        	try {
		            MdfEnumValueImpl mdfValue = (MdfEnumValueImpl) MdfFactory.eINSTANCE.createMdfEnumValue();
		            mdfValue.setName(getMetaDictPermName(getMMLJavaNameFromTAEnumName(dictPermValue.getName())));

		            AAAAspectDS.setTripleAPermittedValueName(mdfValue, dictPermValue.getName());
		            AAAAspectDS.setTripleAPermittedValueRank(mdfValue, dictPermValue.getRank()+"");

		            if (mdfEnum.getType().equals(PrimitivesDomain.BOOLEAN)
		              ||mdfEnum.getType().equals(PrimitivesDomain.BOOLEAN_OBJ)) {

	                    if (dictPermValue.getPermValNatE() == 0) {
	                        mdfValue.setValue("false");
	                    }
	                    else if (dictPermValue.getPermValNatE() == 1) {
	                        mdfValue.setValue("true");
	                    }
		            } else {
		                mdfValue.setValue(Integer.toString(dictPermValue.getPermValNatE()));
		            }
		            addCustomAnnotations(mdfEnum, mdfValue, dictPermValue);
		            addTranslations(mdfValue, dictPermValue);
		            
	                mdfEnum.getValues().add(mdfValue);
	        	} catch (NullPointerException e) {
	        		throw new TA2MMLException("An error occurred while processing the dictPermValue '" + dictPermValue.getName() + "'", e);
	        	}
            }
        }
        return map;
	}

	protected MdfClass createAAAEntityFromDictEntity(MdfDomain aaaDomain, DictEntity dictEntity) throws TA2MMLException
	{
    	try {
			MdfClassImpl aaaClass = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
			aaaClass.setName(getMMLJavaClassNameFromTADictEntitySQLName(dictEntity.getSQLName()));

			aaaClass.setDocumentation(dictEntity.getName());
			// aaaClass.setDocumentation(dictEntity.toString()); // For debug this is useful, but it blows up the MML file by 4MB!

	        // DS-2182
			AAAAspectDS.setTripleAEntityName(aaaClass, dictEntity.getName());

			setClassAnnotationsForJPAGeneration(aaaClass, dictEntity);
        	// DS-5136 - do NOT add dict-id anymore >= r12

			addTranslations(aaaClass, dictEntity);

			if(LOGGER.isDebugEnabled()) LOGGER.debug("created entity "+dictEntity.getSQLName());

			return aaaClass;
    	} catch(NullPointerException e) {
    		throw new TA2MMLException("An error occurred while processing the dictEntity '" + dictEntity.getSQLName() + "'", e);
    	}
	}

    /* default */ void addTranslations(MdfModelElement mdfEntity, Translateable translateable) {
        for (Language lang : translateable.getTranslatedLanguages()) {
        	Locale locale = new Locale(ModelLocaleHelper.getInstance().convertToJavaLocale(lang.getCode()));
            TANGIJTranslationAspect.addTranslation(mdfEntity,
                    locale.getLanguage(), translateable.getTranslatedName(lang));
        }
    }

    protected void setAAAPropertiesFromDictAttribute(MdfPropertyImpl aaaAttribute, MdfClass aaaEntity, DictAttribute dictAttribute)
            throws TA2MMLException
    {
    	aaaAttribute.setName(getMMLJavaAttributeNameFromTADictAttributeSQLName(dictAttribute.getSQLName()));

    	try {
			// For debug this is useful, but it blows up the MML file by 4MB!
	        // aaaAttribute.setDocumentation(dictAttribute.toString());
	        aaaAttribute.setDocumentation(dictAttribute.getName());

	        // DS-2182
	        AAAAspectDS.setTripleAAttributeName(aaaAttribute, dictAttribute.getName());
	        // DS-2182
	        AAAAspectDS.setTripleAAttributeSQLName(aaaAttribute, dictAttribute.getSQLName());

	        SQLAspectDS.setSQLName(aaaAttribute, dictAttribute.getSQLName());

	        // DS-2544 - begin
	        if (dictAttribute.isBusinessKey()) {
	        	aaaAttribute.setBusinessKey(true);
	        	aaaAttribute.setPrimaryKey(false);
	        } else if (dictAttribute.isPrimary()) {
	        	aaaAttribute.setBusinessKey(false);
	        	aaaAttribute.setPrimaryKey(true);
	        } else {
	        	aaaAttribute.setBusinessKey(false);
	        	aaaAttribute.setPrimaryKey(false);
	        }
	        // DS-2544 - end

	        aaaAttribute.setRequired(dictAttribute.isMandatory());
	        SQLAspectDS.setSQLRequired(aaaAttribute, dictAttribute.isMandatoryInDB());
	        aaaAttribute.setBusinessKey(dictAttribute.isBusinessKey());
	        setAAAType(aaaAttribute, dictAttribute);

        	// DS-5136 - do NOT add dict-id anymore >= r12

	        // Ignore field that are not physically in the database (like calculated fields)
	        if (dictAttribute.getCalculated().equals(DictAttributeCalculated.CALCULATED) ||
	        		dictAttribute.getCalculated().equals(DictAttributeCalculated.VIRTUAL)) {
	        	SQLAspectDS.setIgnore(aaaAttribute, true);
	        }

			// DS-2758
			if (dictAttribute.getCalculated().equals(DictAttributeCalculated.DENORM)) {
				AAAAspectDS.setMMLSpecific(aaaAttribute, true);
			}
			// DS-1778
			addCustomAnnotations(dictAttribute, aaaAttribute);
			addTripleAAttrMultiLang(dictAttribute, aaaAttribute);
			addTranslations(aaaAttribute, dictAttribute);
			
			// DS-5090
			if (dictAttribute.isLogical())
				AAAAspectDS.setLogical(aaaAttribute, dictAttribute.isLogical());
    	} catch(NullPointerException e) {
    		throw new TA2MMLException("An error occurred processing the properties of attribute '" + aaaEntity.getName() + "." + aaaAttribute.getName() + "'", e);
    	}
    }

	private void setAAAType(MdfProperty aaaAttribute, DictAttribute dictAttribute) {
        AAAAspectDS.addAAAParams(aaaAttribute, dictAttribute.getName(), dictAttribute.getDatatype().getName());
    }

    /**
     * DS-2309, see specificatio0n 8.4.5.1 - Importing specific annotations
     */
    private void importSpecificAnnotations() {
		String mappingDirection=null;
		String condAttribute = null;
		String valueType = null;
		//DS-3589 - to maintain the order of annotation values
		Map<String, String> valueMap = new TreeMap<String, String>();

    	for (DictAttribute da1 : attributes.keySet()) {

    		DictAttributeTascView tascView = da1.getTascView();
    		String da1SQLName = da1.getSQLName();
			mappingDirection = null;
			condAttribute = null;
			valueType = null;
			valueMap.clear();

			if (DictAttributeTascView.TAMD_TO_MAPP.equals(tascView)) {

				mappingDirection = "MMLtoTA";

	    		if (da1.getRefEntityAttributeDictId() == 1101) {

	    			// entity dimension
	    			valueType = "#";

	    			for (DictAttribute da2 : attributes.keySet()) {
	    				if (da2 == da1) {
	    					continue;
	    				}

	    	    		if (da1SQLName.equals(da2.getEntityAttribute()) && (da1.getDictEntity().getSQLName().equals(da2.getDictEntity().getSQLName()))) {
	    	    			if (condAttribute != null && !condAttribute.equals(da2.getEnumAttribute())) {
	    	    				//raise error;
	    	    				continue;
	    	    			}
	    	    			if (da2.getEnumAttribute() != null && da2.getEnumValue() != null && da1.getRefEntityAttributeDictId()>0 ) {
	    	    				condAttribute = da2.getEnumAttribute();
	    	    				valueMap.put(da2.getEnumValue(), da2.getRefEntityAttributeDictId()+"");
	    	    			}
	    	    		}
	    			}   // for da2

    	    	} else {

    	    		// foreign key
	    			valueType = "&";

	    			for (DictAttribute da2 : attributes.keySet()) {
	    				if (da2 == da1) {
	    					continue;
	    				}
	    	    		if (da1SQLName.equals(da2.getObjectAttribute()) && (da1.getDictEntity().getSQLName().equals(da2.getDictEntity().getSQLName()))) {
	    	    			if (condAttribute != null && !condAttribute.equals(da2.getEnumAttribute())) {
	    	    				//raise error;
	    	    				continue;
	    	    			}
	    	    			if (da2.getEnumAttribute() != null && da2.getEnumValue() != null) {
	    	    				condAttribute = da2.getEnumAttribute();
	    	    				valueMap.put(da2.getEnumValue(), getMMLJavaAttributeNameFromTADictAttributeSQLName(da2.getSQLName()));
	    	    			}
	    	    		}
	    			}   // for da2

    	    	}

    		} else if (DictAttributeTascView.MML_SPECIFIC_ASSOCIATION.equals(tascView) || DictAttributeTascView.MML_SPECIFIC_ENUMERATION.equals(tascView)) {

    			mappingDirection = "TAtoMML";

	    		if (da1.getRefEntityAttributeDictId() > 0 && da1.getObjectAttribute() != null && da1.getEntityAttribute()!= null) {
	    			// foreign key
	    			valueType = "&";
	    			condAttribute = da1.getEntityAttribute();
	    			valueMap.put(da1.getRefEntityAttributeDictId()+"", getMMLJavaAttributeNameFromTADictAttributeSQLName(da1.getObjectAttribute()));

	    		} else {

	    			// enumeration
	    			valueType = "#";
	    		}

    			for (DictAttribute da2 : attributes.keySet()) {
    	    		if (da2 == da1) {
    	    			continue;
    	    		}

    	    		if (da1SQLName.equals(da2.getEnumAttribute()) && (da1.getDictEntity().getSQLName().equals(da2.getDictEntity().getSQLName()))) {
    	    			if (condAttribute != null && ! condAttribute.equals(da2.getEntityAttribute())) {
    	    				// raise error
    	    				continue;
    	    			}
    	    			if (da2.getEntityAttribute() != null && da2.getEnumValue() != null && da2.getRefEntityAttributeDictId() > 0) {
    	    				condAttribute = da2.getEntityAttribute();
    	    				valueMap.put(da2.getRefEntityAttributeDictId()+"", da2.getEnumValue());
    	    			}
    	    		}
	    		} // for da2

    		}

    		if (mappingDirection != null && condAttribute != null && valueMap.size() > 0) {
	    		StringBuilder mappingRule = new StringBuilder();
	    		mappingRule.append("if ");
	    		if (mappingDirection.equals("MMLtoTA")) {
	    			mappingRule.append(getMMLJavaNameFromTAEnumName(condAttribute));
	    		} else {
	    			mappingRule.append(getMMLJavaAttributeNameFromTADictAttributeSQLName(condAttribute));
	    		}
	    		mappingRule.append(" = (");
	    		mappingRule.append(StringUtils.join(valueMap.keySet(), ","));
	    		mappingRule.append(") then := ");
	    		mappingRule.append(valueType);
	    		mappingRule.append("(");
	    		mappingRule.append(StringUtils.join(valueMap.values(), ","));
	    		mappingRule.append(")");

    		}
    	}
    }

    @SuppressWarnings("unchecked")
	protected void addDictEntities(MdfDomain aaaDomain, Collection<DictEntity> entities,
    		Map<DictAttribute, MdfEnumeration> enums, MdfDomain businessTypesDomain) throws TA2MMLException {
        // We need to do THREE "passes" (like a compiler) here, because a DictEntity could
        // have Attributes which reference Entities that have not yet been created as MDF Classes,
        // as well as logical (reverse) associations for which "the other end" has not been created yet.
        //

    	// So, 1. FIRST, we create all MDF Classes (initially without any attributes)
        for (DictEntity dictEntity : entities) {
        	MdfClass aaaClass = createAAAEntityFromDictEntity(aaaDomain, dictEntity);
        	aaaDomain.getClasses().add(aaaClass);
        	entity2class.put(dictEntity, aaaClass);
        }
        // and 2. SECOND we THEN add all the non-logical (reverse) attributes
        try {
	        for (DictEntity dictEntity : entities) {
	            List<DictAttribute> sortedAttributes = dictEntity.getSortedAttributes();
				for (DictAttribute aaaAttribute : sortedAttributes) {
	                if (!aaaAttribute.getSQLName().equals("ud_id")) {
	                	addDictAttribute(getEntity2class(dictEntity), aaaAttribute, enums, businessTypesDomain);
	                }
	            }
	        }
        } catch (Exception e) {
        	throw new TA2MMLException(e.getMessage(), e);
	    }
        // and 3. THIRD we fix the non-logical (reverse) attributes
        for (DictEntity dictEntity : entities) {
        	List<DictAttribute> sortedAttributes = dictEntity.getSortedAttributes();
            for (DictAttribute aaaAttribute : sortedAttributes) {
                if (aaaAttribute.isLogical()) {
                	addLogicalDictAttribute(getEntity2class(dictEntity), aaaAttribute);
                }
            }
        }
        // DS-2309
        importSpecificAnnotations();

    }

    private MdfClass getEntity2class(DictEntity dictEntity) {
    	MdfClass mdfClass = entity2class.get(dictEntity);
    	if (mdfClass == null) {
    		throw new IllegalStateException("DictEntity '" + dictEntity.getName() + "/ "+ dictEntity.getSQLName() + "' has no MML Class set (yet?)");
    	}
    	return mdfClass;
    }
    
	protected void addDictAttribute(MdfClass aaaClass, DictAttribute dictAttribute,
	        Map<DictAttribute, MdfEnumeration> enums,
	        MdfDomain businessTypeDomain) throws TA2MMLException
	{
    	try {
	    	MdfPropertyImpl aaaProperty;
			if (dictAttribute.isLogical()) {
			    return; // @see addLogicalDictAttribute()

			} else if (dictAttribute.getReferencedDictEntity() != null) {
	            MdfAssociationImpl aaaAssociation = (MdfAssociationImpl)MdfFactory.eINSTANCE.createMdfAssociation();
	            aaaAssociation.setType(getEntity2class(dictAttribute.getReferencedDictEntity()));
	            aaaAssociation.setMultiplicity(MdfConstants.MULTIPLICITY_ONE);
	            aaaAssociation.setContainment(MdfConstants.CONTAINMENT_BYREFERENCE);

//	            // DS-2316 - begin obsolete replaced by DS-2753
//	            String refDictEntityName = dictAttribute.getReferencedDictEntity().getName();
//	            boolean enabled = "currency".equalsIgnoreCase(refDictEntityName)
//	                           || "type".equalsIgnoreCase(refDictEntityName);
//	            AAAAspect.setLoadPermittedValus(aaaAssociation, enabled);
//	            // DS-2316 - end

	            if (dictAttribute.getForeignKeyPresentation() == DictAttributeForeignKeyPresentation.VALUE_1) {
	            	AAAAspectDS.setLoadPermittedValues(aaaAssociation, true);
	            }


	            aaaProperty = aaaAssociation;
			} else if (dictAttribute.isPermVal()) {
	            // This logic is somewhat related to what you'll find in Format2Dataset.convertFormatToClass(), search dictAttribute.isPermVal()
			    MdfEnumeration mdfEnumeration = null;
			    if (dictAttribute.getPermValues().size() > 0) {
			        mdfEnumeration = enums.get(dictAttribute);
			    } else {
			    	DictAttribute parent = dictAttribute.getParentAttribute();
			    	if ((parent != null) && ((parent.getPermValues().size() > 0))) {
			    		mdfEnumeration = enums.get(parent);
			    	}
			    }
			    if (mdfEnumeration == null) {
			        throw new TA2MMLException("Something is wrong; no MdfEnumeration was found for " + dictAttribute);
			    }

			    MdfAttributeImpl aaaAttribute = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
	            aaaAttribute.setType(mdfEnumeration);
	            setDefaultForEnum(dictAttribute, aaaAttribute, mdfEnumeration);
	            if (isEnumMask(dictAttribute.getDatatype())) // DS-6653
	            	aaaAttribute.setMultiplicity(MdfMultiplicity.MANY_LITERAL);
			    aaaProperty = aaaAttribute;

			} else {
			    MdfAttributeImpl aaaAttribute = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
			    DictDataType dictDataType = dictAttribute.getDatatype();
			    if (dictDataType.isSkipped()) {
			    	return;
			    }
			    aaaAttribute.setType(dataType2Primitives.getBusinessType(dictDataType, businessTypeDomain));
		        setDefault(dictAttribute, aaaAttribute);
	            aaaProperty = aaaAttribute;
			}
			this.attributes.put(dictAttribute, aaaProperty);
	        addProperty(aaaClass, aaaProperty, dictAttribute);
    	} catch(NullPointerException e) {
    		throw new TA2MMLException("An error occurred while processing the dictAttribute '" + dictAttribute.getDictEntity().getSQLName() + "." + dictAttribute.getSQLName() + "'", e);
    	}

	}
	
	private boolean isEnumMask(DictDataType dataType) {
		return "enummask_t".equals(dataType.getName());
	}

    @SuppressWarnings("unchecked")
	private void addProperty(MdfClass aaaClass, MdfPropertyImpl aaaProperty,
            DictAttribute dictAttribute) throws TA2MMLException
    {
        setAAAPropertiesFromDictAttribute(aaaProperty, aaaClass, dictAttribute);

        // if(LOGGER.isDebugEnabled()) {
        //    LOGGER.debug("Adding MML attribute '" + aaaProperty.getName() + "' (sqlName '" + dictAttribute.getSQLName() + "') to MML class '" + aaaEntity.getName() + "'");
        // }

        // Even with changed naming rules, this is still needed (see Grid.marketSegment/marketSegment1)
        int i = 0;
        String attemptedName = aaaProperty.getName();
        List<MdfProperty> properties = aaaClass.getProperties(true);
        while (propertyExists(properties, attemptedName)) {
            ++i;
            attemptedName = aaaProperty.getName() + i;
        }
        if (!attemptedName.equals(aaaProperty.getName())) {
            LOGGER.warn("Entity '" + aaaClass.getName() + "' already has an attribute named '" + aaaProperty.getName() + "', so I'm renaming it to '" + attemptedName + "'");
        }
        aaaProperty.setName(attemptedName);

        if (!(aaaProperty instanceof MdfReverseAssociation)) {
        	aaaClass.getProperties().add(aaaProperty);
        }


        // DS-2316 - begin
        String aaaClassName = aaaClass.getName();
        if ("currency".equalsIgnoreCase(aaaClassName) || "type".equalsIgnoreCase(aaaClassName)) {
    		for (MdfProperty prop : (List<MdfProperty>)aaaClass.getProperties()) {
    			if ("code".equalsIgnoreCase(prop.getName())) {
    	            AAAAspectDS.setLoadPermittedValues((MdfProperty)prop, true);
    	            break;
    			}
    		}
        }
        // DS-2316 - end


        // Ignore attribute pointing to ignored class
        if (SQLAspect.hasIgnore(aaaProperty.getType())) {
        	SQLAspectDS.setIgnore(aaaProperty, true);
        }

    }

	private boolean propertyExists(List<MdfProperty> properties, String attemptedName) {
		for (MdfProperty property: properties) {
			if (attemptedName.equals(property.getName())) {
					return true;
			}
		}
		return false;
	}
	
    @SuppressWarnings("unchecked")
    // package local, so that it can be unit tested
	void addLogicalDictAttribute(MdfClass aaaClass,
            DictAttribute logicalDictAttribute) throws TA2MMLException {

		DictEntity refDictEntity = logicalDictAttribute.getReferencedDictEntity();
		if (refDictEntity == null) {
			throw new TA2MMLException("The logical attribute [" + logicalDictAttribute.getSQLName()
					+ "] has no referenced dict entity " + logicalDictAttribute);
		}
    	try {
    		MdfClass refClass = getEntity2class(refDictEntity);

	        List<MdfAssociation> associationsPointingToMe = findAssociations(refClass, aaaClass);
	        if (associationsPointingToMe.size() == 0) {
	            boolean ok = addLogicalDictAttributeAsObjectIdBasedAssociation(aaaClass, logicalDictAttribute, refClass);
	            if (!ok) {
	                throw new TA2MMLException(logicalDictAttribute.getDictEntity().getSQLName()
	                    + "." + logicalDictAttribute.getSQLName() + " is a logical attribute (= multiple), "
	                    + "but the entity it points to (" + refClass.getName()
	                    + ") has no associations pointing back at it, and it's not one of the 'object_id' pattern?!");
	            }
	        } else if (associationsPointingToMe.size() > 1) {
	            if (aaaClass.getName().equals("DictScreen") && logicalDictAttribute.getSQLName().equals("dict_panel")) {
	                addLogicalDictAttributeAsReverse(aaaClass, logicalDictAttribute, refClass.getProperty("screen"));
	            } else if (aaaClass.getName().equals("DictEntity") && logicalDictAttribute.getSQLName().equals("dict_attribute")) {
	                addLogicalDictAttributeAsReverse(aaaClass, logicalDictAttribute, refClass.getProperty("entity"));
	            } else if (aaaClass.getName().equals("DictEntity") && logicalDictAttribute.getSQLName().equals("dict_criteria")) {
	                addLogicalDictAttributeAsReverse(aaaClass, logicalDictAttribute, refClass.getProperty("entity"));
	            } else if (aaaClass.getName().equals("Instrument") && propertyExists(refClass.getProperties(), "instr")) {
	                addLogicalDictAttributeAsReverse(aaaClass, logicalDictAttribute, refClass.getProperty("instr"));
	            } else if (aaaClass.getName().equals("XdEntity") && logicalDictAttribute.getSQLName().equals("xd_attribute")) {
	                addLogicalDictAttributeAsReverse(aaaClass, logicalDictAttribute, refClass.getProperty("xdEntity"));
	            } else if (aaaClass.getName().equals("BusinessEntity") && logicalDictAttribute.getSQLName().equals("business_orga")) {
	                addLogicalDictAttributeAsReverse(aaaClass, logicalDictAttribute, refClass.getProperty("busEntity"));
	            } else if (propertyExists(refClass.getProperties() , aaaClass.getName().toLowerCase())) {
	            	addLogicalDictAttributeAsReverse(aaaClass, logicalDictAttribute, refClass.getProperty(aaaClass.getName().toLowerCase()));
	//            } else if (aaaClass.getName().equals("Strategy") && logicalDictAttribute.getSQLName().equals("strategy_history")) {
	//                addLogicalDictAttributeAsReverse(mdfFactory, aaaClass, logicalDictAttribute, refClass.getProperty("strategy"));
	//            } else if (aaaClass.getName().equals("Currency") && logicalDictAttribute.getSQLName().equals("exch_rate")) {
	//                addLogicalDictAttributeAsReverse(mdfFactory, aaaClass, logicalDictAttribute, refClass.getProperty("currency"));
	            } else {
	                throw new TA2MMLException(logicalDictAttribute.getDictEntity().getSQLName()
	                        + "." + logicalDictAttribute.getSQLName() + " is a logical attribute (= multiple), "
	                        + "but the entity it points to (" + refClass.getName()
	                        + ") has more than one associations which are not custom (UD) fields pointing back at it, "
	                        + " and none of my hard-coded special rules matched?!");
	            }
	        } else { // associationsPointingToMe.size() == 1:
	            addLogicalDictAttributeAsReverse(aaaClass, logicalDictAttribute, associationsPointingToMe.get(0));
	        }
		} catch(NullPointerException e) {
			throw new TA2MMLException("An error occurred while processing the logical dictAttribute '" + logicalDictAttribute.getDictEntity().getSQLName() + "." + logicalDictAttribute.getSQLName() + "'. " +
					"Context : " + logicalDictAttribute, e);
		}
    }

    @SuppressWarnings("unchecked")
	private boolean addLogicalDictAttributeAsObjectIdBasedAssociation(MdfClass aaaClass, DictAttribute dictlogicalAttribute, MdfClass refClass) throws TA2MMLException
    {
		List<MdfProperty> refClassProperties = refClass.getProperties();
        if (!propertyExists(refClassProperties, "object") &&
        		!propertyExists(refClassProperties, "portObject")) {
	        if (aaaClass.getName().endsWith("Operation")
	                && refClass.getName().equals("Communication")){
	            return true;  // Skip for now, may be deal with this differently later (@see http://rd.oams.com/browse/ARCH-311)
	        } else if (aaaClass.getName().endsWith("DerivedStrategy")
	                && refClass.getName().equals("DerivedStratElt")){
	            return true;  // Skip for now, may be deal with this differently later (@see http://rd.oams.com/browse/ARCH-311)
	        } else if (/*aaaClass.getName().endsWith("StrategyElement")
	                &&*/ refClass.getName().equals("ConstraintParameter")){
	            return true;  // Skip for now, may be deal with this differently later (@see http://rd.oams.com/browse/ARCH-312)
	        } else {
	            return false; // ==> Error (Exception thrown above)
	        }
        }

        MdfAssociationImpl aaaAssociation = (MdfAssociationImpl)MdfFactory.eINSTANCE.createMdfAssociation();
        MdfClass targetClass = getEntity2class(dictlogicalAttribute.getReferencedDictEntity());
        aaaAssociation.setType(targetClass);
        aaaAssociation.setMultiplicity(MdfConstants.MULTIPLICITY_MANY);
        aaaAssociation.setContainment(MdfConstants.CONTAINMENT_BYVALUE);

        addProperty(aaaClass, aaaAssociation, dictlogicalAttribute);

        // Add SQL JPA annotations for @ElementJoinColumn/s, @see http://rd.oams.com/browse/DS-1921
        String objectId = findObjectIdWithSqlName(targetClass, new String[] {"object_id", "port_object_id", "object_dict_id"});
        String entityId = findObjectIdWithSqlName(targetClass, new String[] {"entity_dict_id", "dim_port_dict_id", "linked_entity_dict_id"});
        String attributeId = findObjectIdWithSqlName(targetClass, new String[] {"attribute_dict_id"});

        if (objectId != null) {
        	if (entityId != null || attributeId != null) {
        		if (aaaClass.getAttributesRef().size() != 1) {
	    			LOGGER.warn("WARNING: Class " + aaaClass.getQualifiedName().getQualifiedName() +
	    					" has " + aaaClass.getAttributesRef().size() + " primary keys and references a class with " +
	    					(entityId!=null?entityId:attributeId) + "." +
	  						" The reference is " + aaaAssociation.getName());
	    			SQLAspectDS.setIgnore(aaaAssociation, true);
	    			return true;
	    		}
	    		MdfProperty referencedColumnProperty = ((List<MdfProperty>)aaaClass.getAttributesRef()).get(0);
	    		String referencedColumnName = referencedColumnProperty.getName();
	    		String sqlName = SQLAspect.getSqlName(referencedColumnProperty);
	    		if (sqlName != null && !sqlName.equals("")) {
	    			referencedColumnName = sqlName;
	    		}
	            SQLAspectDS.setJoinSpecification(aaaAssociation, objectId, referencedColumnName);
	    		if (entityId != null) {
	    			SQLAspectDS.setJoinSpecification(aaaAssociation, entityId, "#entity");
	    		} else if (attributeId != null) {
	    			SQLAspectDS.setJoinSpecification(aaaAssociation, attributeId, "#attrib");
	    		}
        	}
        }

        return true;
    }

	private String findObjectIdWithSqlName(MdfClass targetClass, final String[] objectIds) {
		String objectId = null;
		for (int i=0; i<objectIds.length; i++) {
        	if (getPropertyWithSqlName(targetClass, objectIds[i]) != null) {
        		objectId = objectIds[i];
        		break;
        	}
        }
		return objectId;
	}

    @SuppressWarnings("unchecked")
	private MdfProperty getPropertyWithSqlName(MdfClass klass, String sqlName) {
    	for (MdfProperty property : (List<MdfProperty>)klass.getProperties(true)) {
    		if (SQLAspect.getSqlName(property).equals(sqlName)) {
    			return property;
    		}
    	}
    	return null;
    }

    private void addLogicalDictAttributeAsReverse(MdfClass aaaClass,
            DictAttribute logicalDictAttribute, MdfProperty parent) throws TA2MMLException
    {
        if (parent == null) {
            throw new IllegalArgumentException("parent can not be null!");
        } else if (!(parent instanceof MdfAssociation)) {
            throw new IllegalArgumentException("parent is not a MdfAssociation?!");
        } else {
            MdfAssociationImpl parentAssociation = (MdfAssociationImpl) parent;
            MdfReverseAssociationImpl aaaReverseAssociation = (MdfReverseAssociationImpl)MdfFactory.eINSTANCE.createMdfReverseAssociation();
            aaaReverseAssociation.setReversedAssociation(parentAssociation);
            aaaReverseAssociation.setReversedAssociationType(parentAssociation.getParentClass()); // DS-7332
            
            parentAssociation.setReverse(aaaReverseAssociation); // !!!!!!!!!!!!!!!!
            aaaReverseAssociation.setMultiplicity(MdfConstants.MULTIPLICITY_MANY);

            addProperty(aaaClass, aaaReverseAssociation, logicalDictAttribute);
        }
    }

    /**
     * Find all (single) associations in one MML Class which "point" to a certain entity.
     * @param aaaClass class who's assocations will be checked
     * @param refClass class which associations should point to
     * @return List of MdfAssociation.  Never null, but could be of empty, of size 1, or more.
     */
    @SuppressWarnings("unchecked")
    private List<MdfAssociation> findAssociations(MdfClass aaaClass, MdfClass refClass) {
        List<MdfAssociation> resultList = new LinkedList<MdfAssociation>();
        List<MdfProperty> properties = aaaClass.getProperties();
        for (MdfProperty property : properties) {
            if (property instanceof MdfAssociation) {
                MdfAssociation associationProperty = (MdfAssociation) property;
                if (associationProperty.getType().equals(refClass)
                		&& !isCustomField(associationProperty) // SUPPORT-3997
                	) {
                    resultList.add(associationProperty);
                }
            }
        }
        return resultList;
    }


    private void setDefault(DictAttribute dictAttribute, MdfAttributeImpl aaaAttribute) throws TA2MMLException {
        String defaultValue = dictAttribute.getDefault();
        if (defaultValue != null && !defaultValue.equals("\"NULL\"")) {
            aaaAttribute.setDefault(defaultValue);
        }
        if (defaultValue != null && aaaAttribute.getType().equals(PrimitivesDomain.BOOLEAN_OBJ))
		{
        	if (defaultValue.equals("0")) {
        		aaaAttribute.setDefault("false");
        	}
        	else if (defaultValue.equals("1")) {
        		aaaAttribute.setDefault("true");
        	}
        	else  {
                String message = "DictAttribute " + dictAttribute.getDictID() + " is a Boolean, but I don't understand what this default value means: " + defaultValue;
				LOGGER.error(message);
        		throw new TA2MMLException(message);
        	}
		}
    }

    @SuppressWarnings("unchecked")
    private void setDefaultForEnum(DictAttribute dictAttribute, MdfAttributeImpl aaaAttribute, MdfEnumeration mdfEnumeration) throws TA2MMLException {
        List<MdfEnumValue> enumValues = mdfEnumeration.getValues();
        Map<String, String> enumValuesToNames = new HashMap<String, String>(enumValues.size());
        for (MdfEnumValue mdfEnumValue : enumValues) {
            enumValuesToNames.put(mdfEnumValue.getValue(), mdfEnumValue.getName());
        }

        String defaultValue = dictAttribute.getDefault();
        if (defaultValue != null /* && !defaultValue.equals("\"NULL\"") */) {
            String enumName = enumValuesToNames.get(defaultValue);
            if (enumName == null && (defaultValue.trim().equals("0") || (defaultValue.trim().equals("1")))) {
                if (defaultValue.trim().equals("0")) {
                    enumName = enumValuesToNames.get("false");
                } else if (defaultValue.trim().equals("1")) {
                    enumName = enumValuesToNames.get("true");
                }
            }
            if (enumName == null) {
                // @see http://rd.oams.com/browse/PMSTA-7857
                String message = "Entity '" + dictAttribute.getDictEntity().getSQLName() + "' attribute " + dictAttribute.getSQLName() + " has a default_c of '" + defaultValue + "' in the T'A Meta Dictionary, but there is no MDF Enum with that value in: " + enumValuesToNames.toString();
                LOGGER.warn(message);
                // We do NOT want to throw a new TA2MMLException(message)... we ignore and life continues.
            }
            aaaAttribute.setDefault(enumName);
        }
    }

    /**
     * Adds suitable Annotations to the domain.
     *
     * The purpose of these annotations is to let the MDF-to-JPA code generator (cartridge)
     * build the correct JPA Code & Annotations so that this domain can actually be used
     * to read from a T'A DB.
     *
     * At the time of writing this (20.3.2009; MVO), this is a POC only... because the
     * only purpose of the metadict import initially was to have a domain for modeling (page designer)
     * and not for data access (which was initially thought to be going all through "classic"
     * TASC).  However these annotations don't cause harm even if this POC is not going further.
     *
     * @param aaaDomain
     */
    private void setDomainAnnotationsForJPAGeneration(MdfDomain domain) {
    	SQLAspectDS.setPrefixAssociationWithDomainIfSame(domain, false);
    	SQLAspectDS.setUseSqlNameAsItIs(domain, true);
    	SQLAspectDS.setFullyQualifiedNameAnnotationsUsed(domain, false);
    	SQLAspectDS.setMessageLevel(domain, JpaMessageEnum.JPA1, LevelEnum.WARNING);
    	SQLAspectDS.setMessageLevel(domain, JpaMessageEnum.JPA2, LevelEnum.WARNING);
    	SQLAspectDS.setMessageLevel(domain, JpaMessageEnum.JPA3, LevelEnum.WARNING);
    	SQLAspectDS.setMessageLevel(domain, JpaMessageEnum.JPA4, LevelEnum.WARNING);
        SQLAspectDS.setMessageLevel(domain, JpaMessageEnum.JPA5, LevelEnum.WARNING);
        SQLAspectDS.setMessageLevel(domain, JpaMessageEnum.JPA6, LevelEnum.WARNING);
        SQLAspectDS.setMessageLevel(domain, JpaMessageEnum.JPA7, LevelEnum.IGNORE);
        SQLAspectDS.setMessageLevel(domain, JpaMessageEnum.JPA8, LevelEnum.IGNORE);
    }

    /**
     * Adds suitable Annotations to generated classes.
     * @see #setDomainAnnotationsForJPAGeneration(MdfDomain)
     * @param klass
     * @param dictEntity
     */
    private void setClassAnnotationsForJPAGeneration(MdfClass klass, DictEntity dictEntity) {
    	SQLAspectDS.setVersionOptimisticLockingNone(klass); // @see http://rd.oams.com/browse/OCS-28166
		SQLAspectDS.setSQLName(klass, dictEntity.getSQLName() + "_vw");
		AAAAspectDS.setTripleAEntitySQLName(klass, dictEntity.getSQLName());
		if (dictEntity.isLogical()) {
			SQLAspectDS.setIgnore(klass, true);
		}
    }
    /**
     * Set the TimeStamp annotation for the enumeration domain and entities domain.
     */
    public void setTimeStampAnnotaiton(MetaDictDomains domains, String value) {
	AAAAspectDS.setTripleATimeStamp(domains.entitiesDomain, value);
	AAAAspectDS.setTripleATimeStamp(domains.enumerationsDomain, value);

    }
}
