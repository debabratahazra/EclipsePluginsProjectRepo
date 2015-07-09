package com.odcgroup.edge.t24.generation.enquiry.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;

import com.acquire.util.AssertionUtils;
import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.composite.singleifp.GlobalContext;
import com.odcgroup.edge.t24.generation.enquiry.EdgeConnectDataType;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.enquiry.enquiry.ConstantOperation;
import com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.enquiry.impl.ApplicationFieldNameOperationImpl;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * <code>EnquiryFieldsDigest</code> is a self-populating, immutable "value object" representing the results of "digesting" the {@link Field} definitions within a specified {@link Enquiry}.<p>
 * 
 * The purpose of this class is to centralize the knowledge of how to traverse/interpret the raw <code>Field</code> information for easy consumption by any enquiry processor.
 * 
 * @author Simon Hayes
 */
public class EnquiryFieldsDigest
{
    private static final Logger LOGGER = GenLogger.getLogger(EnquiryFieldsDigest.class);
    
	private final Enquiry m_enquiry;
	private final DomainRepository m_domainRepository;

	private final String m_customNoResultsMessage;
	private final EnquiryResultsHeaderLabel[] m_enqResultsHeaderLabels;
	private final EnquiryResultsHeaderValue[] m_enqResultsHeaderValues;
	private final ResultsHeaderFooterRowSpec m_enqResultsHeaderRowSpec;
	private final EnquiryResultsTableField[] m_columnOrderedEnquiryResultsTableFields;
	private final ResultsHeaderFooterRowSpec m_enqResultsFooterRowSpec;
    private final Map<String,EnquirySelectionFieldInfo> m_enquirySelectionFieldInfoByAppFieldName = new HashMap<String,EnquirySelectionFieldInfo>();

    private final GlobalContext m_globalContext;

    public EnquiryFieldsDigest(GlobalContext p_globalContext, Enquiry p_enquiry) throws Exception
	{
		m_globalContext = AssertionUtils.requireNonNull(p_globalContext, "p_globalContext");
        m_enquiry = AssertionUtils.requireNonNull(p_enquiry, "p_enquiry");
		m_domainRepository = DomainRepository.getInstance(OfsResourceHelper.getOfsProject(p_enquiry.eResource()));
		
		final ArrayList<EnquiryResultsHeaderLabel> enqResultsHeaderLabels = new ArrayList<EnquiryResultsHeaderLabel>();
		final ArrayList<EnquiryResultsHeaderValue> enqResultsHeaderValues = new ArrayList<EnquiryResultsHeaderValue>();
		
		final TreeMap<Integer,EnquiryResultsTableField> m_enquiryResultsTableFieldByT24ColumnNumber = new TreeMap<Integer,EnquiryResultsTableField>();
		
		final MdfClass m_enquiryApplicationMdfClass = getApplicationMdfClass(p_enquiry.getFileName());
    	final EList<Field> fields = m_enquiry.getFields();
    	final int numFields = (fields == null) ? 0 : fields.size();
    	final boolean expectingCustomNoResultsMsg = Boolean.TRUE.equals(m_enquiry.getZeroRecordsDisplay());

    	ResultsHeaderFooterRowSpec
    		enqResultsHeaderRowSpec = null,
    		enqResultsFooterRowSpec = null;
    	
    	Field customNoResultsMsgField = null;
    	String customNoResultsMsg = null;
    	
    	for (int i = 0; i < numFields; ++i)
    	{
    		final Field field = fields.get(i);

    		if (field == null)
    		{
    			LOGGER.error("(Enquiry).getFields().get(" + i + ") -> null !");
    			continue;
    		}
    		
    		final String enqFieldName = field.getName();
    		
    		if ((enqFieldName == null) || (enqFieldName.length() == 0) || enqFieldName.equalsIgnoreCase("NULL"))
    		{
				LOGGER.error("(Enqiry).getFields().get(" + i + ").getName() -> " + StringUtils.quoteIfString(enqFieldName) + " (field ignored)");
    			continue;
    		}
    		
    		final FieldPosition fieldPosition = field.getPosition();
    		
    		if (fieldPosition == null)
    		{
    			LOGGER.info("[\"" + enqFieldName + "\"] field.getPosition() -> null (field ignored)");
    			continue;
    		}
    		
    		if (Boolean.TRUE.equals(fieldPosition.getPageThrow()))
    		{
    			// Don't yet know what we need to do with these.
    			// However, we *do* know that we DON'T want to generate either a heading or a table question.
    			continue;
    		}
    		
    		final Operation fieldOperation = field.getOperation();
    		
    		if (fieldOperation == null)
    		{
    			LOGGER.error("[\"" + enqFieldName + "\"] field.getOperation() -> null !");
    			continue;
    		}
    		
    		final Integer t24LineNumber = fieldPosition.getLine();
    		final int t24ColumnNumber = fieldPosition.getColumn();
    		final String ecDataItemName =  MapperUtility.processT24NameToIRISName(enqFieldName);
    		
    		if (fieldOperation instanceof ConstantOperation)
			{
    			final String constantValue = stripEnclosingQuotes(((ConstantOperation) fieldOperation).getValue());
    			
    			if (expectingCustomNoResultsMsg && (field.getDisplaySection() == DisplaySectionKind.HEADER) && "CLASS-ENQ.NORECS".equals(field.getDisplayType()))
    			{
    				// Assumption: this field defines a custom "no matching records" messaage
    				
    				if (customNoResultsMsgField != null)
    					LOGGER.error("Custom \"no matching records\" messaage field: " + enqFieldName + " ignored (custom \"no matching records\" message already specified by field: " + customNoResultsMsgField.getName());
    				
    				else
    				{
    					customNoResultsMsgField = field;
    					customNoResultsMsg = StringUtils.trimEmptyToNull(constantValue);
    				}
    				
    				continue;
    			}
    			
    			if ((t24LineNumber != null) && (fieldPosition.getRelative() == null))
    			{
	    			// Assumption: the field we're looking at defines the position and content of a static heading that is to appear somewhere above the results table.
    				enqResultsHeaderLabels.add(new EnquiryResultsHeaderLabel(t24LineNumber, t24ColumnNumber, constantValue));
	    			continue;
    			}
			}
    		
			if ((t24LineNumber != null) && fieldPosition.getRelative() != null)
			{
				// Assumption: we're looking at a results header/footer field.
				
				if (! "+".equals(fieldPosition.getRelative()))
				{
					LOGGER.warn("[\"" + enqFieldName + "\"] field.getPosition().getRelative() -> \"" + fieldPosition.getRelative() + "\" (field ignored)");
					continue;
				}
				
				ResultsHeaderFooterRowSpec headerFooterRowSpec = null;
				
				switch (t24LineNumber.intValue())
				{
					case 0:
						headerFooterRowSpec = enqResultsHeaderRowSpec = (enqResultsHeaderRowSpec == null) ? new ResultsHeaderFooterRowSpec() : enqResultsHeaderRowSpec;
						break;
						
					case 1:
						headerFooterRowSpec = enqResultsFooterRowSpec = (enqResultsFooterRowSpec == null) ? new ResultsHeaderFooterRowSpec() : enqResultsFooterRowSpec;
						break;
						
					default:
						LOGGER.warn("[\"" + enqFieldName + "\"] field.getPosition().getRelative() -> \"+\", but field.getLine() -> " + t24LineNumber + " (field ignored)");
						continue;
				}
				
				ResultsHeaderFooterCellSpec preexistingCellSpecForColumn = headerFooterRowSpec.addCellSpecFor(field, ecDataItemName);
				
				if (preexistingCellSpecForColumn != null)
					LOGGER.error("[\"" + enqFieldName + "\"] field has same (relative) position as: " + preexistingCellSpecForColumn.getT24FieldName());
				
				continue;
			}
			
    		final String appFieldName = (fieldOperation instanceof ApplicationFieldNameOperationImpl) ? ((ApplicationFieldNameOperationImpl) fieldOperation).getField() : null;
    		final MdfProperty appFieldDef = (appFieldName == null) || (m_enquiryApplicationMdfClass == null) ? null : m_enquiryApplicationMdfClass.getProperty(appFieldName.replace('.', '_'));
            final TextTranslations fieldLabel = TextTranslations.getLabelTranslations( m_globalContext.getEdgeMapper(), appFieldDef, field.getLabel(), enqFieldName );
			
			boolean isMultiValuedField = false; // tentative

    		if (appFieldDef != null)
    		{
    			EdgeConnectDataType edgeConnectDataType = findOrCreateEdgeConnectDataType(appFieldDef);
    			SearchParamDropdownInfo searchParamDropdownInfo = null;
    			
    			if (appFieldDef instanceof MdfAttribute)
    			{
    				// No special action required
    			}
    			
    			else if (appFieldDef instanceof MdfAssociation)
	    		{
	    			final MdfAssociation appFieldAssociationDef = (MdfAssociation) appFieldDef;
	    			searchParamDropdownInfo = extractSearchParamDropdownInfo(appFieldAssociationDef);
	    			isMultiValuedField = (appFieldAssociationDef.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY);
	    		}

	    		else
	    		{
	    			LOGGER.warn("Unhandled appFieldDef type: " + appFieldDef.getClass().getName() + " for appFieldName: \"" + appFieldName + "\": " + appFieldDef);
	    		}
    			
    			m_enquirySelectionFieldInfoByAppFieldName.put(appFieldName, new EnquirySelectionFieldInfo(appFieldName, enqFieldName, edgeConnectDataType, searchParamDropdownInfo, appFieldDef));
    		} // if (appFieldName != null)

			if (t24LineNumber != null)
			{
				 // Assumption: the field we're looking at defines a dynamic header value that is to appear (likely to the right of a static header label) somewhere above the results table.
				enqResultsHeaderValues.add(new EnquiryResultsHeaderValue(t24LineNumber, t24ColumnNumber, ecDataItemName, isMultiValuedField));
				continue;
			}
			
			// Assumption: the fact that we've reached this point implies we're dealing with a table column field.

			m_enquiryResultsTableFieldByT24ColumnNumber.put(t24ColumnNumber, new EnquiryResultsTableField(t24ColumnNumber, fieldLabel, ecDataItemName, isMultiValuedField));

    	} // for each enquiry field

    	m_customNoResultsMessage = customNoResultsMsg;
    	m_enqResultsHeaderLabels = EnquiryResultsHeaderLabel.toArray(enqResultsHeaderLabels);
    	m_enqResultsHeaderValues = EnquiryResultsHeaderValue.toArray(enqResultsHeaderValues);
    	m_enqResultsHeaderRowSpec = enqResultsHeaderRowSpec;
    	m_columnOrderedEnquiryResultsTableFields = EnquiryResultsTableField.toArray(m_enquiryResultsTableFieldByT24ColumnNumber.values());
    	m_enqResultsFooterRowSpec = enqResultsFooterRowSpec;
	}  
    
    public String getCustomNoResultsMsg() {
    	return m_customNoResultsMessage;
    }
    
    public EnquiryResultsHeaderLabel[] getEnqResultsHeaderLabels()
    {
    	return m_enqResultsHeaderLabels;
    }
    
    public EnquiryResultsHeaderValue[] getEnqResultsHeaderValues()
    {
    	return m_enqResultsHeaderValues;
    }
    
    public ResultsHeaderFooterRowSpec getEnqResultsHeaderRowSpec()
    {
    	return m_enqResultsHeaderRowSpec;
    }
    
    public EnquiryResultsTableField[] getColumnOrderedResultsTableFields()
    {
    	return m_columnOrderedEnquiryResultsTableFields;
    }
    
    public ResultsHeaderFooterRowSpec getEnqResultsFooterRowSpec()
    {
    	return m_enqResultsFooterRowSpec;
    }
    
    public EnquirySelectionFieldInfo getEnquirySelectionFieldInfoForAppFieldName(String p_appFieldName)
    {
    	return m_enquirySelectionFieldInfoByAppFieldName.get(p_appFieldName);
    }

	protected MdfClass getApplicationMdfClass(String p_applicationFilenameValue)
	{
		final String adaptedFullName = p_applicationFilenameValue.replace('.', '_');
		MdfName mdfName = MdfNameURIUtil.getMdfName(URI.createURI(adaptedFullName));
		final MdfClass result = (MdfClass) m_domainRepository.getEntity(mdfName);

		if (result == null)
			LOGGER.error("Referenced application: " + p_applicationFilenameValue + " not found !");
		
		return result;
	}
	
	protected static EdgeConnectDataType findOrCreateEdgeConnectDataType(MdfProperty p_mdfProperty)
	{
		AssertionUtils.requireNonNull(p_mdfProperty, "p_mdfProperty");
		
		final MdfAssociationImpl mdfAssociation = (p_mdfProperty instanceof MdfAssociationImpl) ? (MdfAssociationImpl) p_mdfProperty : null;
		
		/*
		 * Kludge City (pop: 1004)
		 * 
		 * The MdfEntity (i.e. type def) returned for an MdfProperty representing a primary or foreign key is always the MdfClass describing the entity type referred to by
		 * that key - meaning that (apparently) there is no way to discover the primitive type of such a key.
		 * 
		 * The following cop-out clause is therefore necessary to prevent infinite recursion should p_mdfProperty turn out to represent a primary or foreign key.
		 * 
		 * Fortunately we only make use of this type information for the Search screen's filter value parameters, and there we present the filter value input for foreign-key type
		 * parameters using the "Dropdown" widget, which assumes a source Question with a "Text" typed answer item.
		 */
		if ((mdfAssociation != null) && (mdfAssociation.isPrimaryKey() || isForeignKeyAssociation(mdfAssociation)))
			return EdgeConnectDataType.TEXT;

		final MdfEntity mdfEntity = p_mdfProperty.getType();

		if (mdfEntity != null)
		{
			if (mdfEntity.isPrimitiveType())
			{
				final MdfPrimitive mdfPrimitive = (mdfEntity instanceof MdfBusinessType) ? ((MdfBusinessType) mdfEntity).getType() : (MdfPrimitive) mdfEntity;
				
				if (mdfPrimitive != null)
				{
					if (mdfPrimitive.equals(PrimitivesDomain.STRING))
						return EdgeConnectDataType.TEXT;
					
					if (mdfPrimitive.equals(PrimitivesDomain.BOOLEAN) || mdfPrimitive.equals(PrimitivesDomain.BOOLEAN_OBJ))
						return EdgeConnectDataType.BOOLEAN;
					
					if (mdfPrimitive.equals(PrimitivesDomain.DATE) || mdfPrimitive.equals(PrimitivesDomain.DATE_TIME))
						return EdgeConnectDataType.DATE;

					if (
						mdfPrimitive.equals(PrimitivesDomain.SHORT) || mdfPrimitive.equals(PrimitivesDomain.SHORT_OBJ) ||
						mdfPrimitive.equals(PrimitivesDomain.INTEGER) || mdfPrimitive.equals(PrimitivesDomain.INTEGER_OBJ) ||
						mdfPrimitive.equals(PrimitivesDomain.LONG) || mdfPrimitive.equals(PrimitivesDomain.LONG_OBJ)
					) {
						return EdgeConnectDataType.NUMBER;
					}

					if (
						mdfPrimitive.equals(PrimitivesDomain.DECIMAL) || mdfPrimitive.equals(PrimitivesDomain.FLOAT_OBJ) ||
						mdfPrimitive.equals(PrimitivesDomain.FLOAT) || mdfPrimitive.equals(PrimitivesDomain.FLOAT_OBJ) ||
						mdfPrimitive.equals(PrimitivesDomain.DOUBLE) || mdfPrimitive.equals(PrimitivesDomain.DOUBLE_OBJ)
					) {
						return EdgeConnectDataType.DECIMAL;
					}
				}					
			}
			
			else if (mdfEntity instanceof MdfClass)
			{
				// NB: Given the cop-out clause at the beginning of this method, we know this must be a reference to a contained type (vs. the referent of a primary or foreign key)
				
				MdfClass mdfClass = (MdfClass) mdfEntity;

				final List<?> mdfClassProperties = mdfClass.getProperties();
				final int numMdfClassProperties = (mdfClassProperties == null) ? 0 : mdfClassProperties.size();
				
				for (int i = 0; i < numMdfClassProperties; ++i)
				{
					final Object o = mdfClassProperties.get(i);

					if (o instanceof MdfProperty)
					{
						MdfProperty mdfProperty = (MdfProperty) o;
						
						if (mdfProperty.getName().equals(p_mdfProperty.getName()))
							return findOrCreateEdgeConnectDataType(mdfProperty);
					}
				}
			}
			
			else {
				LOGGER.warn("Unhandled MdfEntity subtype: " + mdfEntity.getClass().getName() + " for property: " + p_mdfProperty.getQualifiedName());
			}
		}
		
		return EdgeConnectDataType.TEXT;
	}
	
	protected static boolean isForeignKeyAssociation(MdfAssociation p_appFieldAssociationDef)
	{
		AssertionUtils.requireNonNull(p_appFieldAssociationDef, "p_appFieldAssociationDef");
		return (p_appFieldAssociationDef.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE) && ! p_appFieldAssociationDef.isPrimaryKey(); 
	}
	
	protected  SearchParamDropdownInfo extractSearchParamDropdownInfo(MdfAssociation p_appFieldAssociationDef) throws Exception
	{
		SearchParamDropdownInfo result = null;
		
		if (isForeignKeyAssociation(p_appFieldAssociationDef))
		{
			final MdfEntity refdAppEntity = p_appFieldAssociationDef.getType();
			final String refdAppFilename = "name:/" + refdAppEntity.getQualifiedName().getQualifiedName() + "#";
			final MdfClass refdAppMdfClass = getApplicationMdfClass(refdAppFilename);
			
			if (refdAppMdfClass != null)
			{
				final List<?> refdAppPrimaryKeys = refdAppMdfClass.getPrimaryKeys();
			
				if (refdAppPrimaryKeys.size() == 1)
				{
					final Object primaryKeyObj = refdAppPrimaryKeys.get(0);
					
					if (primaryKeyObj instanceof MdfAssociation)
					{
						final String refdIrisResourceName = MapperUtility.processT24NameToIRISName(refdAppEntity.getName()) + "s";
						final String refdIrisPrimaryKeyName = MapperUtility.processT24NameToIRISName(((MdfAssociation) primaryKeyObj).getName());
						
						result = new SearchParamDropdownInfo(refdIrisResourceName, refdIrisPrimaryKeyName);
					}
				}
			}
		}
		
		return result;
	}

	protected static String stripEnclosingQuotes(String s)
	{
		String result = s;
		
		if (s != null)
		{
			final int len = s.length();
			
			if ((len >= 2) && (s.charAt(0) == '"') && (s.charAt(len - 1) == '"'))
				result = s.substring(1, len - 1);
		}
		
		return result;
	}
}
