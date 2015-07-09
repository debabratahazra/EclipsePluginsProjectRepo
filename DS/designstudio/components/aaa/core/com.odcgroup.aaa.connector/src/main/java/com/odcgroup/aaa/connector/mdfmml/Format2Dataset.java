package com.odcgroup.aaa.connector.mdfmml;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.aaa.connector.domainmodel.DenominableWithNameAndNotepads;
import com.odcgroup.aaa.connector.domainmodel.DenominationEntity;
import com.odcgroup.aaa.connector.domainmodel.DictDataType;
import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.domainmodel.FormatElementEntity;
import com.odcgroup.aaa.connector.domainmodel.FormatEntity;
import com.odcgroup.aaa.connector.domainmodel.NotepadEntity;
import com.odcgroup.aaa.connector.internal.util.AAAWidgetRulesUtil;
import com.odcgroup.aaa.core.util.NamingHelper;
import com.odcgroup.domain.annotations.AAAAspectDS;
import com.odcgroup.domain.annotations.SQLAspectDS;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.ext.tangij.TANGIJTranslationAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.model.translation.ModelLocaleHelper;


/**
 * Transformation of T'A Formats to MDF DataSets or MDF Entities.
 *
 * @author Michael Vorburger
 */
public class Format2Dataset {

    private final MetaDictMdfDomainHelper metaDictMdfDomainHelper;
    private final DataType2Primitives dataType2Primitives = new DataType2Primitives();

    public Format2Dataset(MetaDictMdfDomainHelper metaDictMdfDomainHelper) {
        this.metaDictMdfDomainHelper = metaDictMdfDomainHelper;
	}

    /**
     * Constructor.
     *
     * @param mdfFactory MdfModelFactory from which new Datasets and Entities should be created.
     * @param domainOfMetaDict previously created MDF Domain read from a MetaDictionary (see MetaDict2Mdf)
     * @throws TA2MMLException if something unexpected happens
     *
     * @see {@link MetaDict2Mdf}
     */
    public Format2Dataset(MdfDomain metaDictEntities, MdfDomain metaDictEnums, MdfDomain businessTypesDomain) throws TA2MMLException {
        this.metaDictMdfDomainHelper = new MetaDictMdfDomainHelper(metaDictEntities, metaDictEnums, businessTypesDomain);
    }
    
//	/**
//     * Convert a T'A Format into a MDF DataSet.
//     *
//     * @param domainForNewDataset MDF domain that the returned DataSet has to be added (this does NOT add it!)
//     * @param format T'A Format to convert
//     * @return MdfDataset built from the Format
//     *
//     * @throws MdfParsingException if something went wrong in MDF
//     * @throws TA2MMLException if something unexpected was encountered e.g. in the format
//     */
//    public MdfDataset convertFormatToDataset(MdfDomain domainForNewDataset, FormatEntity format, MdfDomain businessTypesDomain) throws MdfParsingException, TA2MMLException {
//        checkFormatElementSQLNames(format);
//
//        MdfDataset dataset = (MdfDataset) mdfFactory.createMdfDataset(domainForNewDataset);
//        dataset.setBaseClass(metaDictMdfDomainHelper.getMdfClass(format.getEntity().getSQLName()));
//        initEntityFromFormat(dataset, format);
//
//        for (FormatElementEntity element : format.getFormatElements()) {
//        	try {
//	            MdfDatasetProperty property;
//	            if (element.getAttribute() == null) {
//	                MdfDatasetDerivedProperty derrivedProperty = (MdfDatasetDerivedProperty) mdfFactory.createMdfDatasetDerivedProperty(dataset);
//	                DictDataType dictDataType = element.getDatatype();
//	                if (dictDataType.isSkipped()) {
//	                	continue;
//	                }
//	                derrivedProperty.setType(dictDataType.getBusinessType(businessTypesDomain));
//	                property = derrivedProperty;
//	            } else {
//	                MdfDatasetMappedProperty mappedProperty = (MdfDatasetMappedProperty) mdfFactory.createMdfDatasetMappedProperty(dataset);
//	                checkFormatElementWithAttribute(element);
//	                mappedProperty.setPath(metaDictMdfDomainHelper.getMdfProperty(element.getAttribute().getDictID()).getName());
//	                property = mappedProperty;
//	            }
//	            initPropertyFromFormatElement(property, element);
//	            dataset.addProperty(property);
//        	} catch(NullPointerException e) {
//        		throw new TA2MMLException("An error occurred while processing the format element '" + element.getFormat().getName() + "." + element.getSQLName() + "'", e);
//        	}
//        }
//        return dataset;
//    }

    /**
     * Convert a T'A Format into an MDF Class.
     *
     * @see #convertFormatToDataset(MdfDomain, FormatEntity)
     */
    @SuppressWarnings("unchecked")
	public MdfClass convertFormatToClass(MdfDomain domainForNewEntity, FormatEntity format, MdfDomain businessTypesDomain) throws TA2MMLException {
        checkFormatElementSQLNames(format);

        MdfClassImpl entity = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
        domainForNewEntity.getClasses().add(entity);
        initEntityFromFormat(entity, format);
        
        //boolean foundID = false;
        for (FormatElementEntity element : format.getFormatElements()) {
        	try {
        		MdfPropertyImpl property;
	            if (element.getAttribute() == null) {
	                MdfAttributeImpl attribute = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
	                DictDataType dictDataType = element.getDatatype();
	                if (dictDataType.isSkipped()) {
	                	continue;
	                }
	                attribute.setType(dataType2Primitives.getBusinessType(dictDataType, businessTypesDomain));
	                property = attribute;

	            } else if (element.getAttribute().isPermVal()) {
	                // This logic is somewhat related to what you'll find in MetaDict2Mdf.addDictAttribute(), search dictAttribute.isPermVal()
	                MdfAttributeImpl attribute = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
	                attribute.setType(metaDictMdfDomainHelper.getMdfEnum(element));
	                property = attribute;
	            } else if (element.getAttribute().getReferencedDictEntity() == null) {
	            	MdfAttributeImpl attribute = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
					DictDataType dictDataType = element.getAttribute().getDatatype();
	                if (dictDataType.isSkipped()) {
	                	continue;
	                }
	                attribute.setType(dataType2Primitives.getBusinessType(dictDataType, businessTypesDomain));
	            	AAAWidgetRulesUtil
	        			.createWidgetRulesUtil()
	        			.checkLongCodeForNameAndCodeBusinessTypes(metaDictMdfDomainHelper.getMdfBusinessTypesDomain(), element.getAttribute(), attribute);
	                property = attribute;
	            } else {
	                MdfAssociationImpl association = (MdfAssociationImpl)MdfFactory.eINSTANCE.createMdfAssociation();
	                checkFormatElementWithAttribute(element);
	                DictEntity refDictEntity = element.getAttribute().getReferencedDictEntity();
	                association.setType(metaDictMdfDomainHelper.getMdfClass(refDictEntity.getSQLName()));
	                association.setContainment(MdfConstants.CONTAINMENT_BYREFERENCE);
	                property = association;
	            }

	            //DS-3161
	            if(property!=null) {
		            if(element.getRank() != null) {
		            	AAAAspectDS.setTripleAFormatElementRank(property, element.getRank());
		            }

		            if(element.getSortingRank() != null) {
		            	AAAAspectDS.setTripleAFormatElementSortingRank(property, element.getSortingRank());
		            }
	            }

	            // DS-2181
	            if (element.getSQLName().toLowerCase().equals("seqno")) {
	                property.setPrimaryKey(true);
	                property.setRequired(true);
	                //foundID = true;
	            }
	            initPropertyFromFormatElement(property, element);
	            
	            //DS-4979
	            //property.setName(NamingHelper.getMMLJavaAttributeNameFromTADictAttributeSQLName(element.getSQLName()));
	            
	            entity.getProperties().add(property);
	    	} catch(NullPointerException e) {
	    		throw new TA2MMLException("An error occurred while processing the format element '" + element.getFormat().getName() + "." + element.getSQLName() + "'", e);
	    	}
        }

// DS-2181 obsolete
//        if (!foundID) {
//            MdfAttribute idProperty = (MdfAttribute) mdfFactory.createMdfAttribute(entity);
//            idProperty.setName("id");
//            idProperty.setPrimaryKey(true);
//            idProperty.setRequired(true);
//            idProperty.setDocumentation("Autogenerated because the T'A Format did not have a Format Element with SQL Name 'id'");
//            SQLAspect.setSQLName(idProperty, "id");
//            idProperty.setType(PrimitivesDomain.LONG);
//            entity.addProperty(idProperty);
//        }

        return entity;
    }

    private void checkFormatElementSQLNames(FormatEntity format) throws TA2MMLException {
        for (FormatElementEntity outerElement : format.getFormatElements()) {
            for (FormatElementEntity innerElement : format.getFormatElements()) {
                if (innerElement != outerElement
                 && innerElement.getSQLName().equalsIgnoreCase(outerElement.getSQLName())) {
                    throw new TA2MMLException("Format " + format.getCode()
                            + " has two Format Elements which have the same SQL Name,"
                            + "except for upper-/lowercase; this can not be imported: "
                            + innerElement.getSQLName() + " / " + outerElement.getSQLName());
                }
            }
        }
    }

    private void initEntityFromFormat(MdfEntityImpl entity, FormatEntity format) {
        entity.setName(NamingHelper.getMMLJavaClassNameFromTAFormatCode(format.getCode()));
        SQLAspectDS.setSQLName(entity, format.getCode());
        addDocumentation(entity, format);
        addTranslations(entity, format);

        // DS-2182 - Format Name
        AAAAspectDS.setTripleAFormatName(entity, format.getCode()/*format.getName()*/);
        // DS-2182 - Format Entity SQL Name
        MdfClass mdfClass = this.metaDictMdfDomainHelper.getMdfClass(format.getEntity().getSQLName());
        if (mdfClass != null) {
        	AAAAspectDS.setTripleAEntitySQLName(entity, AAAAspect.getTripleAEntitySQLName(mdfClass));
        }

        if (format.getFunction() != null) {
        	// DS-5136/OCS-40436 - do NOT add the Format Function dict-id (anymore, >= r12)
        	// Format Function Proc Name
        	AAAAspectDS.setTripleAFinFuncProcName(entity, format.getFunction().getProcName());
        	// Format Function Name
	        AAAAspectDS.setTripleAFormatFunction(entity, format.getFunction().getName());
        }

        if (format.getFilter() != null) {
        	// Format Column Filter
            AAAAspectDS.setTripleAColumnFilter(entity, format.getFilter());
        }
    }

    private void initPropertyFromFormatElement(MdfModelElementImpl property, FormatElementEntity element) {
//        property.setName(NamingHelper.getMMLJavaAttributeNameFromTAFormatElementSQLName(element.getSQLName()));
    	//property.setName(element.getSQLName());  // SAN forced this... I give up.
    	property.setName(NamingHelper.dealWithReservedJavaKeywords(element.getSQLName()));
        SQLAspectDS.setSQLName(property, element.getSQLName());
        addDocumentation(property, element);
        addTranslations(property, element);

        // DS-2182 - Format Element Name
        AAAAspectDS.setTripleAFormatElementName(property, element.getName());

        // Format - Script Definition
        String script = element.getScriptDefinition();
		if (property instanceof MdfDatasetProperty)  {
            MdfDatasetProperty mdfDatasetProperty = (MdfDatasetProperty) property;
            if (StringUtils.isNotBlank(script))
            	AAAAspectDS.setTripleAFormatElementScript(mdfDatasetProperty, script);
            if (element.getAttribute() != null) {
                // DS-2182 - Format Attribute SQL Name
            	AAAAspectDS.setTripleAAttributeSQLName(mdfDatasetProperty, element.getAttribute().getSQLName());
            }
        } else if (property instanceof MdfProperty) {
            MdfProperty mdfProperty = (MdfProperty) property;
            if (StringUtils.isNotBlank(script))
            	AAAAspectDS.setTripleAFormatElementScript(mdfProperty, script);
            if (element.getAttribute() != null) {
                // DS-2182 - Format Attribute SQL Name
            	AAAAspectDS.setTripleAAttributeSQLName(mdfProperty, element.getAttribute().getSQLName());
            }
            
            if (element.getTslMultilingual() != null &&
            		element.getTslMultilingual().equals(1)) {
            	AAAAspectDS.setTripleAAttrMultiLanguage(mdfProperty, "true");
        }
        }
        
    }

    private void addDocumentation(MdfModelElementImpl entity, DenominableWithNameAndNotepads formatOrFormatElement) {
        StringBuffer doc = new StringBuffer(formatOrFormatElement.getName());
//        if (formatOrFormatEntity.getDenom() != null && !formatOrFormatEntity.getDenom().isEmpty()) {
//            String denom = formatOrFormatEntity.getDenom();
//            // DS-2528-begin
//            if ( ! denom.contains("to remove")) {
//            	doc.append(" (" + formatOrFormatEntity.getDenom() + ")");
//            }
//        }
        List<NotepadEntity> notepads = formatOrFormatElement.getNotepads();
        // notepads are sorted by date/title
        for (NotepadEntity notepad : notepads) {
            doc.append("\n\n");
            String title = notepad.getTitle();
            if (StringUtils.isNotEmpty(title)) {
            	doc.append(title);
            	doc.append(":");
            }
            doc.append(notepad.getNote());
        }
        entity.setDocumentation(doc.toString());
    }

    private void addTranslations(MdfModelElement entity, DenominableWithNameAndNotepads formatOrFormatEntity) {
        Set<DenominationEntity> denoms = formatOrFormatEntity.getDenoms();
        for (DenominationEntity denom : denoms) {
            TANGIJTranslationAspect.addTranslation(entity, ModelLocaleHelper.getInstance().convertToJavaLocale(denom.getLanguage().getCode()), denom.getDenom());
        }
    }

   private void checkFormatElementWithAttribute(FormatElementEntity element) throws TA2MMLException {
        FormatEntity format = element.getFormat();
        if (!element.getAttribute().getDictEntity().getSQLName().equals(format.getEntity().getSQLName())) {
            // NOTE: Technically, in DS/MDF, we would of course be able to do this.. but in the T'A GUI today this is not allowed:
            throw new TA2MMLException("How can the Element '" + element.getSQLName() + "' of the Format '"
                    + format.getCode() + "' reference the attribute '" + element.getAttribute().getSQLName()
                    + "' of the Entity '" + element.getAttribute().getDictEntity().getSQLName()
                    + "' which is Entity " + element.getAttribute().getDictEntity().getSQLName() + " if the Entity of the format is '" + format.getEntity().getSQLName()
                    + "' ? I don't know what to do with this...");
        }
        if (element.getAttribute().isLogical()) {
            throw new TA2MMLException("The Element '" + element.getSQLName() + "' of the Format '" + format.getCode()
                    + "' references the LOGICAL (!) attribute '" + element.getAttribute().getSQLName()
                    + "' of the Entity '" + element.getAttribute().getDictEntity().getSQLName()
                    + "'; I don't know what to do with this " + "(because it's a logical attribute)...");
        }

//        if (element.getAttribute().getReferencedDictEntity() == null) {
//            throw new TA2MMLException("The Element '" + element.getSQLName() + "' of the Format '" + format.getCode()
//                    + "' references the attribute '" + element.getAttribute().getSQLName() + "' of the Entity '"
//                    + element.getAttribute().getDictEntity().getSQLName()
//                    + "', but that attribute is not an association "
//                    + "(ref_entity_dict_id is NULL); I don't know what to do with this...");
//        }
    }
}
