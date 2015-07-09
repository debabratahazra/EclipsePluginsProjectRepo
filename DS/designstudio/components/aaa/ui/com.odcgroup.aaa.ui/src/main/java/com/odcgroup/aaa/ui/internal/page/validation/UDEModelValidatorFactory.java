package com.odcgroup.aaa.ui.internal.page.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;

import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.aaa.core.UDEHelper;
import com.odcgroup.aaa.ui.internal.model.impl.MetaDictionnaryHelper;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.validation.MdfValidationCore;
import com.odcgroup.workbench.core.IOfsProject;

@SuppressWarnings("unchecked")
public class UDEModelValidatorFactory {
    
	static final int MAX_LENGTH_SQL_NAME = 30;
	
	private static final Map<String, String> SUFFIX_RULES;
    private static final List<String> SQL_RESERVED_KEYWORDS;
    private static final String SQL_RESERVERD_KEYWORDS_FILENAME = "/com/odcgroup/aaa/ui/SQLReservedKeywords.txt";
    
    static {
    	SUFFIX_RULES = new HashMap<String, String>();
    	initSuffixRules();
    	
    	InputStream input = null;
    	List<String> sqlReserverdKeywords = null;
    	try {
    		input = UDEModelValidator.class.getResourceAsStream(SQL_RESERVERD_KEYWORDS_FILENAME);
    		sqlReserverdKeywords = Collections.<String>unmodifiableList((List<String>)IOUtils.readLines(input));
    	} catch (IOException e) {
		} finally {
    		IOUtils.closeQuietly(input);
    		SQL_RESERVED_KEYWORDS = sqlReserverdKeywords;
    	}
    }

	private static void initSuffixRules() {
		SUFFIX_RULES.put("date_t", "_d");
		SUFFIX_RULES.put("datetime_t", "_d");
		SUFFIX_RULES.put("blob_t", "_b");
		SUFFIX_RULES.put("mask_t", "_mask");
		SUFFIX_RULES.put("int_t", "_i");
		SUFFIX_RULES.put("longint_t", "_i");
		SUFFIX_RULES.put("amount_t", "_m");
		SUFFIX_RULES.put("longamount_t", "_m");
		SUFFIX_RULES.put("exchange_t", "_rate");
		SUFFIX_RULES.put("longint_t", "_li");
		SUFFIX_RULES.put("number_t", "_n");
		SUFFIX_RULES.put("timestamp_t", "_t");
		SUFFIX_RULES.put("dict_t", "_dict_id");
		SUFFIX_RULES.put("id_t", "_id");
		SUFFIX_RULES.put("percent_t", "_p");
		SUFFIX_RULES.put("period_t", "_si");
		SUFFIX_RULES.put("smallint_t", "_si");
		SUFFIX_RULES.put("year_t", "_si");
		SUFFIX_RULES.put("sysname_t", "_t");
		SUFFIX_RULES.put("text_t", "_txt");
		SUFFIX_RULES.put("url_t", "_url");
		SUFFIX_RULES.put("flag_t", "_f");
		SUFFIX_RULES.put("method_t", "_ti");
		SUFFIX_RULES.put("tinyint_t", "_ti");
	}
    
    public UDEModelValidator createUDEModelValidator() {
    	UDEModelValidator validator = new UDEModelValidator();
    	initValidator(validator);
    	return validator;
    }

	void initValidator(UDEModelValidator validator) {
		validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateSQLNameConstraints(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateUseOnlyBusinessType(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateEntityNamePrefix(originalModel, workingCopyForAAAAnnotations);
			}
    	});    	
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateSuffixUsage(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateUniqueness(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	/*
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validatePrimaryKey(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	*/
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateRequired(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateMandatory(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validatePermittedValueRank(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateBaseTypeForEnumeration(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateReferencialIntegrity(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateDefaultValue(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateNotSupportedAssociation(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateNoInheritance(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateOnlyClassAndEnumAllowed(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validatePrimaryKeyMandatory(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateNoNullValueForEnum(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateAttributeSQLUniqueWithinClass(originalModel, workingCopyForAAAAnnotations);
			}
    	});
    	validator.addValidation(new IUDEValidationRule() {
			@Override
			public IStatus validate(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
				return validateAttributeBusinessType(originalModel, workingCopyForAAAAnnotations);
			}
    	});
	}

	protected IStatus validateSQLNameConstraints(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		for (String sqlName: getSqlName(workingCopyForAAAAnnotations)) {
			if (sqlName.length() > MAX_LENGTH_SQL_NAME) {
				return MdfValidationCore.newStatus(
						"Triple'A Plus Core SQL name cannot exceed " + MAX_LENGTH_SQL_NAME + " characters.", 
						IStatus.ERROR);
			}
			if (!sqlName.matches("[a-z0-9_]*")) {
				return MdfValidationCore.newStatus(
						"Triple'A Plus Core SQL name supports only characters in lower case and digits.", 
						IStatus.ERROR);
			}
			if (SQL_RESERVED_KEYWORDS.contains(sqlName)) {
				return MdfValidationCore.newStatus(
						"This SQL name (" + sqlName + ") is a reserved word.", 
						IStatus.ERROR);
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected IStatus validateUseOnlyBusinessType(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfAttribute) {
			MdfAttribute attribute = (MdfAttribute)originalModel;
			MdfEntity type = attribute.getType();
			if (!(type instanceof MdfBusinessType) && !(type instanceof MdfEnumeration)) {
				return MdfValidationCore.newStatus(
						"Attribute must be based on business type", 
						IStatus.ERROR);
			}
		}
		return MdfValidationCore.STATUS_OK;
	}
	
	/**
	 * DS-5195
	 * 
	 * @param originalModel
	 * @param workingModel
	 * @return
	 */
	protected IStatus validateAttributeBusinessType(MdfModelElement originalModel, MdfModelElement workingModel) {
		if (originalModel instanceof MdfAttribute) {
			MdfAttribute attribute = (MdfAttribute)originalModel;
			MdfDomain domain = attribute.getParentClass().getParentDomain();
			if (AAAAspect.isTripleAUDEntities(domain)) {
				MdfEntity entity = attribute.getType();
				if (entity instanceof MdfBusinessType) {
					MdfBusinessType type = (MdfBusinessType) entity;
					if ("Enum".equals(type.getName()) || "Flag".equals(type.getName())) {
						return MdfValidationCore.newStatus(
								"No attribute of UDE class can have business type Enum or Flag", 
								IStatus.ERROR);						
					}
				}
			}
		}
		return MdfValidationCore.STATUS_OK;
	}
	
	/**
	 * DS-4811
	 * 
	 * @param originialModel
	 * @param workingCopy
	 * @return
	 */
	protected IStatus validateEntityNamePrefix(MdfModelElement originalModel, MdfModelElement workingCopy) {
		if (workingCopy instanceof MdfClass) {
			MdfClass klass = (MdfClass) workingCopy;			
			String entityName = AAAAspect.getTripleAEntitySQLName(klass);
			if (!StringUtils.isEmpty(entityName)) {
				if (!entityName.startsWith("udt_")) {
					return MdfValidationCore.newStatus(
							"Entity SQL Name must always start with \'udt_\'", 
							IStatus.ERROR);
				}
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected IStatus validateSuffixUsage(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfAttribute) {
			MdfAttribute attribute = (MdfAttribute)originalModel;
			if (attribute.isPrimaryKey()) {
				return MdfValidationCore.STATUS_OK;
			}
			String sqlName = AAAAspect.getTripleAAttributeSQLName((MdfAttribute)workingCopyForAAAAnnotations);
			if (!StringUtils.isEmpty(sqlName)) {
				sqlName = sqlName.trim();
				if (attribute.getType() instanceof MdfEnumeration) {
					MdfEnumeration mdfEnumeration = (MdfEnumeration)attribute.getType();
					MdfPrimitive type = mdfEnumeration.getType();
					if (PrimitivesDomain.BOOLEAN.equals(type) || 
							PrimitivesDomain.BOOLEAN_OBJ.equals(type)) {
						if (!sqlName.endsWith("_f")) {
							return MdfValidationCore.newStatus(
									"SQL name (" + sqlName + ") should respect suffixing naming convention (in this case \"_f\").", 
									IStatus.WARNING);
						}
					} else if (PrimitivesDomain.INTEGER.equals(type) || 
							PrimitivesDomain.INTEGER_OBJ.equals(type)) {
						if (!sqlName.endsWith("_e")) {
							return MdfValidationCore.newStatus(
									"SQL name (" + sqlName + ") should respect suffixing naming convention (in this case \"_e\").", 
									IStatus.WARNING);
						}
					}
				} else if (((MdfAttribute) originalModel).getType() instanceof MdfBusinessType) {
					MdfBusinessType bType = (MdfBusinessType)((MdfAttribute) originalModel).getType();
					String dataTypeName = AAAAspect.getTripleABusinessType(bType);
					if (SUFFIX_RULES.containsKey(dataTypeName)) {
						String expectedSuffix = SUFFIX_RULES.get(dataTypeName);
						if (!sqlName.endsWith(expectedSuffix)) {
							return MdfValidationCore.newStatus(
									"SQL name (" + sqlName + ") should respect suffixing naming convention (in this case \""+expectedSuffix+"\").", 
									IStatus.WARNING);
						}
					} /*else {
						if (PrimitivesDomain.STRING.equals(bType.getType())) {
							if (!sqlName.endsWith("_c")) {
								return MdfValidationCore.newStatus(
										"SQL name (" + sqlName + ") should respect suffixing naming convention (in this case \"_c\").", 
										IStatus.WARNING);
							}
						}
					} */ //DS-4811 - usability issues
				}
			}
		} else if (originalModel instanceof MdfAssociation) {
			MdfAssociation association = (MdfAssociation)originalModel;
			if (association.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE) {
				if (association.isPrimaryKey()) {
					return MdfValidationCore.STATUS_OK;
				}
				String sqlName = AAAAspect.getTripleAAttributeSQLName((MdfAssociation)workingCopyForAAAAnnotations);
				if (!StringUtils.isEmpty(sqlName) && !sqlName.trim().endsWith("_id")) {
					return MdfValidationCore.newStatus(
							"SQL name (" + sqlName + ") should respect suffixing naming convention (in this case \"_id\").", 
							IStatus.WARNING);
				}
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected IStatus validateUniqueness(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (workingCopyForAAAAnnotations instanceof MdfClass) {
			String sqlName = AAAAspect.getTripleAEntitySQLName((MdfClass)workingCopyForAAAAnnotations);
			if (StringUtils.isNotEmpty(sqlName)) {
				List<String> classSqlNames = getClassSqlNames((MdfClass)originalModel);
				if (classSqlNames.contains(sqlName)) {
					return MdfValidationCore.newStatus(
							"Duplicate SQL name.", 
							IStatus.ERROR);
				}
				
				List<String> aaaEntitiesSqlNames = getAaaEntitiesSqlNames();
				if (aaaEntitiesSqlNames.contains(sqlName)) {
					return MdfValidationCore.newStatus(
							"Duplicate SQL name in AAAEntities domain.", 
							IStatus.ERROR);
				}
			}
			String entityName = AAAAspect.getTripleAEntityName((MdfClass)workingCopyForAAAAnnotations);
			if (StringUtils.isNotEmpty(entityName)) {
				List<String> classEntityNames = getClassEntityNames((MdfClass)originalModel);
				if (classEntityNames.contains(entityName)) {
					return MdfValidationCore.newStatus(
							"Duplicate entity name.", 
							IStatus.ERROR);
				}
				
				List<String> aaaEntitiesNames = getAaaEntitesNames();
				if (aaaEntitiesNames.contains(entityName)) {
					return MdfValidationCore.newStatus(
							"Duplicate entity name in AAAEntities domain.", 
							IStatus.ERROR);
				}
			}
		}
		
		return MdfValidationCore.STATUS_OK;
	}

	protected List<String> getClassEntityNames(MdfClass originalClass) {
		List<String> entityNames = new ArrayList<String>();
		for (MdfClass mdfClass : (List<MdfClass>)originalClass.getParentDomain().getClasses()) {
			if (mdfClass.equals(originalClass)) {
				continue;
			}
			String entityName = AAAAspect.getTripleAEntityName(mdfClass);
			if (StringUtils.isNotEmpty(entityName)) {
				entityNames.add(entityName);
			}
		}
		return entityNames;
	}

	protected List<String> getClassSqlNames(MdfClass originalClass) {
		List<String> entitySqlNames = new ArrayList<String>();
		for (MdfClass mdfClass : (List<MdfClass>)originalClass.getParentDomain().getClasses()) {
			if (mdfClass.equals(originalClass)) {
				continue;
			}
			String entityName = AAAAspect.getTripleAEntitySQLName(mdfClass);
			if (StringUtils.isNotEmpty(entityName)) {
				entitySqlNames.add(entityName);
			}
		}
		return entitySqlNames;
	}

	protected List<String> getAaaEntitesNames() {
		List<String> entityNames = new ArrayList<String>();
		for (MdfClass mdfClass : getAAAEntitiesDomainClasses()) {
			String entityName = AAAAspect.getTripleAEntityName(mdfClass);
			if (StringUtils.isNotEmpty(entityName)) {
				entityNames.add(entityName);
			}
		}
		return entityNames;
	}

	protected List<String> getAaaEntitiesSqlNames() {
		List<String> entitySqlNames = new ArrayList<String>();
		for (MdfClass mdfClass : getAAAEntitiesDomainClasses()) {
			String entityName = AAAAspect.getTripleAEntitySQLName(mdfClass);
			if (StringUtils.isNotEmpty(entityName)) {
				entitySqlNames.add(entityName);
			}
		}
		return entitySqlNames;
	}
	
	protected List<MdfClass> getAAAEntitiesDomainClasses() {
		List<MdfClass> allClasses = new ArrayList<MdfClass>();
		IOfsProject ofsProject = AAACore.getMetaDictionaryProject();
		if (ofsProject != null) {
			MdfDomain domain = MetaDictionnaryHelper.getAAAMetadictionaryEntities(ofsProject);
			if (domain != null) {
				allClasses.addAll(domain.getClasses());
			}
		}
		return allClasses;
	}

	protected IStatus validatePrimaryKey(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (workingCopyForAAAAnnotations instanceof MdfAttribute) {
			if (((MdfAttribute)originalModel).isPrimaryKey()) {
				if (!StringUtils.equals(AAAAspect.getTripleAAttributeSQLName((MdfAttribute)workingCopyForAAAAnnotations), "id")) {
					return MdfValidationCore.newStatus(
							"Triple'A Plus Core database strongly associates the name \"id\" to identifier column (primary key)", 
							IStatus.ERROR);
				}
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected IStatus validateRequired(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfAttribute) {
			MdfAttribute attribute = (MdfAttribute)originalModel;
			if (!attribute.isRequired()) {
				if (attribute.isPrimaryKey()) {
					return MdfValidationCore.newStatus(
							"Primary key attribute must have property required selected", 
							IStatus.ERROR);
				} else if (attribute.getType() instanceof MdfEnumeration) {
					return MdfValidationCore.newStatus(
							"Attribute based on enumeration must have property required selected", 
							IStatus.ERROR);
				}
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected IStatus validateMandatory(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		IStatus mandatoryErrorStatus = MdfValidationCore.newStatus(
				"All attribute followed by (*) are mandatory", 
				IStatus.ERROR);
		if ((workingCopyForAAAAnnotations instanceof MdfClass)) {
			if (StringUtils.isEmpty(AAAAspect.getTripleAEntitySQLName((MdfClass)workingCopyForAAAAnnotations)) ||
					StringUtils.isEmpty(AAAAspect.getTripleAEntityName((MdfClass)workingCopyForAAAAnnotations))) {
				return mandatoryErrorStatus;
			}
		} else if ((workingCopyForAAAAnnotations instanceof MdfAttribute)) {
			if (StringUtils.isEmpty(AAAAspect.getTripleAAttributeSQLName((MdfAttribute)workingCopyForAAAAnnotations)) ||
					StringUtils.isEmpty(AAAAspect.getTripleAAttributeName((MdfAttribute)workingCopyForAAAAnnotations))) {
				return mandatoryErrorStatus;
			}
		} else if ((workingCopyForAAAAnnotations instanceof MdfAssociation)) {
			if (StringUtils.isEmpty(AAAAspect.getTripleAAttributeSQLName((MdfAssociation)workingCopyForAAAAnnotations)) || 
					StringUtils.isEmpty(AAAAspect.getTripleAAttributeName((MdfAssociation)workingCopyForAAAAnnotations))) {
				return mandatoryErrorStatus;
			}
		} else if ((workingCopyForAAAAnnotations instanceof MdfReverseAssociation)) {
			if (StringUtils.isEmpty(AAAAspect.getTripleAAttributeSQLName((MdfReverseAssociation)workingCopyForAAAAnnotations)) || 
					StringUtils.isEmpty(AAAAspect.getTripleAAttributeName((MdfReverseAssociation)workingCopyForAAAAnnotations))) {
				return mandatoryErrorStatus;
			}
		} else if ((workingCopyForAAAAnnotations instanceof MdfEnumeration)) {
			if (StringUtils.isEmpty(AAAAspect.getTripleAEntitySQLName((MdfEnumeration)workingCopyForAAAAnnotations)) || 
					StringUtils.isEmpty(AAAAspect.getTripleAAttributeSQLName((MdfEnumeration)workingCopyForAAAAnnotations))) {
				return mandatoryErrorStatus;
			}
		} else if ((workingCopyForAAAAnnotations instanceof MdfEnumValue)) {
			if (StringUtils.isEmpty(AAAAspect.getTripleAPermittedValueName((MdfEnumValue)workingCopyForAAAAnnotations)) ||
					StringUtils.isEmpty(AAAAspect.getTripleAPermittedValueRank((MdfEnumValue)workingCopyForAAAAnnotations))) {
				return mandatoryErrorStatus;
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected IStatus validatePermittedValueRank(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (workingCopyForAAAAnnotations instanceof MdfEnumValue) {
			String rank = AAAAspect.getTripleAPermittedValueRank((MdfEnumValue)workingCopyForAAAAnnotations);
			try {
				Integer.parseInt(rank);
			} catch (NumberFormatException e) {
				return MdfValidationCore.newStatus(
						"Triple'A Plus Core permitted value rank must be an integer.", 
						IStatus.ERROR);
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected IStatus validateBaseTypeForEnumeration(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfEnumeration) {
			MdfEnumeration mdfEnum = (MdfEnumeration)originalModel;
			MdfPrimitive type = mdfEnum.getType();
			if (type != null && 
					!PrimitivesDomain.BOOLEAN.equals(type) &&
					!PrimitivesDomain.INTEGER.equals(type) && 
					!"EnumMask".equals(type.getName()) /*DS-4763*/) {
				return MdfValidationCore.newStatus(
						"Enumeration designed for Triple'A Plus Core can only be based on integer or boolean or EnumMask data types.", 
						IStatus.ERROR);
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected IStatus validateReferencialIntegrity(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		List<String> parentTypeAttributes = new ArrayList<String>();
		parentTypeAttributes.add(AAAAspect.getTripleAParentTypeAttr(workingCopyForAAAAnnotations));
		parentTypeAttributes.add(AAAAspect.getTripleAParentTypeEntity(workingCopyForAAAAnnotations));
		for (String parentAttribute: parentTypeAttributes) {
			if (!isRefValid(originalModel, parentAttribute)) {
				return MdfValidationCore.newStatus(
						"Parent type attribute cannot be resolved (" + UDEHelper.getInstance().removeBrackets(parentAttribute) + ").", 
						IStatus.ERROR);
			}
		}
		if (originalModel instanceof MdfEnumeration) {
			List<String> relatedAttribute = new ArrayList<String>();
			relatedAttribute.add(AAAAspect.getTripleAEntitySQLName((MdfEnumeration)workingCopyForAAAAnnotations));
			relatedAttribute.add(AAAAspect.getTripleAAttributeSQLName((MdfEnumeration)workingCopyForAAAAnnotations));
			for (String ref: relatedAttribute) {
				if (!isRefValid(originalModel, ref)) {
					return MdfValidationCore.newStatus(
							"Attribute owner cannot be resolved (" + UDEHelper.getInstance().removeBrackets(ref) + ").", 
							IStatus.ERROR);
				}
			}
		}
		return MdfValidationCore.STATUS_OK;
	}
	
	private boolean isRefValid(MdfModelElement originalModel, String ref) {
		if (UDEHelper.getInstance().hasBrackets(ref)) {
			String path = UDEHelper.getInstance().removeBrackets(ref);
			if (UDEHelper.getInstance().findMdfProperty(originalModel, path) == null) {
				return false;
			}
		}
		return true;
	}

	protected IStatus validateDefaultValue(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfAttribute) {
			MdfAttribute attribute = (MdfAttribute)originalModel;
			if (attribute.getType() instanceof MdfEnumeration) {
				if (StringUtils.isEmpty(attribute.getDefault())) {
					return MdfValidationCore.newStatus(
							"Attribute based on enumeration must have a default value set.", 
							IStatus.ERROR);
				}
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected IStatus validateNotSupportedAssociation(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfAssociation) {
			MdfAssociation association = (MdfAssociation)originalModel;
			if (association.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE &&
					association.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
				return MdfValidationCore.newStatus(
						"This type of association cannot be exported to Triple'A Plus Core. Association by reference supports only multiplicity One.", 
						IStatus.ERROR);
			} else if (association.getContainment() == MdfConstants.CONTAINMENT_BYVALUE) {
				return MdfValidationCore.newStatus(
						"This type of association cannot be exported to Triple'A Plus Core. Association by value are not supported.", 
						IStatus.ERROR);
			}
		} else if (originalModel instanceof MdfReverseAssociation) {
			MdfReverseAssociation reverse = (MdfReverseAssociation)originalModel;
			if (reverse.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
				return MdfValidationCore.newStatus(
						"This type of association cannot be exported to Triple'A Plus Core. Reverse association supports only multiplicity One.", 
						IStatus.ERROR);
			}
		}
		return MdfValidationCore.STATUS_OK;
	}
	
	protected IStatus validateNoInheritance(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfClass) {
			MdfClass mdfClass = (MdfClass)originalModel;
			if (mdfClass.isAbstract() || mdfClass.getBaseClass() != null) {
				return MdfValidationCore.newStatus(
						"Triple'A Plus Core doesn't support inheritance mechanism.", 
						IStatus.ERROR);
			}
		}
		return MdfValidationCore.STATUS_OK;
	}
	
	protected IStatus validateOnlyClassAndEnumAllowed(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfDataset || originalModel instanceof MdfBusinessType) {
			return MdfValidationCore.newStatus(
					"Triple'A Plus Core supports only creation of enumerations and classes.", 
					IStatus.ERROR);
		}
		return MdfValidationCore.STATUS_OK;
	}
	
	protected IStatus validatePrimaryKeyMandatory(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfClass) {
			MdfClass mdfClass = (MdfClass)originalModel;
			if (mdfClass.getPrimaryKeys().size() == 0) {
				return MdfValidationCore.newStatus(
						"Primary key is required for user-defined entity.", 
						IStatus.ERROR);
			}
		}
		return MdfValidationCore.STATUS_OK;
	}
	
	protected IStatus validateNoNullValueForEnum(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfEnumeration) {
			MdfEnumeration mdfEnum = (MdfEnumeration)originalModel;
			if (mdfEnum.isAcceptNullValue()) {
				return MdfValidationCore.newStatus(
						"Enumeration designed for Triple'A Code cannot be NULL.", 
						IStatus.ERROR);
			}
		}
		return MdfValidationCore.STATUS_OK;
	}
	
	protected IStatus validateAttributeSQLUniqueWithinClass(MdfModelElement originalModel, MdfModelElement workingCopyForAAAAnnotations) {
		if (originalModel instanceof MdfProperty) {
			MdfProperty property = (MdfProperty)originalModel;
			String attributeSqlNameProperty = AAAAspect.getTripleAAttributeSQLName((MdfProperty)workingCopyForAAAAnnotations);
			if (!StringUtils.isBlank(attributeSqlNameProperty)) {
				for (MdfProperty siblingProperty : (List<MdfProperty>)property.getParentClass().getProperties()) {
					if (siblingProperty == property) {
						continue;
					}
					// As reverse are internally duplicated, filter them to avoid false non uniqueness error
					if (originalModel instanceof MdfReverseAssociation && 
							siblingProperty instanceof MdfReverseAssociation &&
							((MdfReverseAssociation)originalModel).getAssociation() == 
							((MdfReverseAssociation)siblingProperty).getAssociation()) {
						continue;
					}
					String siblingSqlName = AAAAspect.getTripleAAttributeSQLName(siblingProperty);
					if (attributeSqlNameProperty.trim().equals(siblingSqlName)) {
						return MdfValidationCore.newStatus(
								"Duplicate Attribute SQL name (" + attributeSqlNameProperty + ").", 
								IStatus.ERROR);
					}
				}
			}
		}
		return MdfValidationCore.STATUS_OK;
	}

	protected List<String> getSqlName(MdfModelElement model) {
		List<String> result = new LinkedList<String>();
		if (model instanceof MdfClass) {
			addIfRelevant(result, AAAAspect.getTripleAEntitySQLName((MdfClass)model));
		} else if (model instanceof MdfAttribute) {
			addIfRelevant(result, AAAAspect.getTripleAAttributeSQLName((MdfAttribute)model));
		} else if (model instanceof MdfAssociation) {
			addIfRelevant(result, AAAAspect.getTripleAAttributeSQLName((MdfAssociation)model));
			addIfRelevant(result, AAAAspect.getTripleAParentTypeAttr((MdfProperty)model));
			addIfRelevant(result, AAAAspect.getTripleAParentTypeEntity((MdfProperty)model));
		} else if (model instanceof MdfEnumeration) {
			addIfRelevant(result, AAAAspect.getTripleAEntitySQLName((MdfEnumeration)model));
			addIfRelevant(result, AAAAspect.getTripleAAttributeSQLName((MdfEnumeration)model));
		} else if (model instanceof MdfEnumValue) {
			// No sql name
		}
		return result;
	}
	
	protected void addIfRelevant(List<String> list, String item) {
		if (StringUtils.isNotEmpty(item) && !UDEHelper.getInstance().hasBrackets(item)) {
			list.add(item);
		}
	}

}
