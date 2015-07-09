package com.odcgroup.edge.t24.generation.enquiry.core;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;

import com.odcgroup.edge.t24.generation.composite.singleifp.GlobalContext;
import com.odcgroup.edge.t24.generation.enquiry.EdgeConnectDataType;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperator;
import com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl;

/**
 * An <code>EnquiryFilterParamDef</code> is an immutable value object providing meta-information about a single filter field within an Enquiry's "selection" (i.e. search) critieria.
 *
 * @author Simon Hayes
 */
public class EnquiryFilterParamDef
{
	public static final EnquiryFilterParamDef[] EMPTY_ARRAY = new EnquiryFilterParamDef[0];
	
	private final EnquirySelectionFieldInfo m_selectionFieldInfo;
	private final String m_appFieldName;
	private final String m_odataFieldName;
	private final TextTranslations m_labelTranslations;
	private final boolean m_isParamMandatory;
	private final SearchParamComparisonOp[] m_paramSpecificComparisonOpList;
	private final MdfProperty m_appFieldDef;
	
	public EnquiryFilterParamDef(GlobalContext p_globalContext, SelectionImpl p_t24ParamDef, EnquiryFieldsDigest p_fieldsDigest, Logger p_logger)
	{
		m_appFieldName = p_t24ParamDef.getField();

		m_selectionFieldInfo = p_fieldsDigest.getEnquirySelectionFieldInfoForAppFieldName(m_appFieldName);
		
		MdfProperty appFieldDef = ( m_selectionFieldInfo != null ) ? m_selectionFieldInfo.appFieldDef : null;
		m_appFieldDef = appFieldDef;
        m_labelTranslations = TextTranslations.getLabelTranslations( p_globalContext.getEdgeMapper(), appFieldDef, p_t24ParamDef.getLabel(), m_appFieldName );
		
		m_isParamMandatory = Boolean.TRUE.equals(p_t24ParamDef.getMandatory());
		m_paramSpecificComparisonOpList = buildParamSpecificComparisonOpList(p_t24ParamDef.getOperands(), p_logger);

		final String enqFieldName = (m_selectionFieldInfo == null) ? null : m_selectionFieldInfo.enquiryFieldName;
		m_odataFieldName =  MapperUtility.processT24NameToIRISName((enqFieldName == null) ? m_appFieldName : enqFieldName);
	}
	
	public boolean isParamMandatory()
	{
		return m_isParamMandatory;
	}
	
	public String getAppFieldName()
	{
		return m_appFieldName;
	}
	
	public String getODataFieldName()
	{
		return m_odataFieldName;
	}
	
	public TextTranslations getLabelTranslations()
	{
		return m_labelTranslations;
	}
	
	public MdfProperty getAppFieldDefn()
    {
        return m_appFieldDef;
    }
	
	public EdgeConnectDataType getEdgeConnectDataType()
	{
		return (m_selectionFieldInfo == null) ? EdgeConnectDataType.TEXT : m_selectionFieldInfo.edgeConnectDataType;
	}
	
	public SearchParamComparisonOp[] getParamSpecificComparisonOps()
	{
		return m_paramSpecificComparisonOpList;
	}
	
	public SearchParamDropdownInfo getSearchParamDropdownInfo()
	{
		return (m_selectionFieldInfo == null) ? null : m_selectionFieldInfo.searchParamDropdownInfo;
	}
	
	private static SearchParamComparisonOp[] buildParamSpecificComparisonOpList(EList<SelectionOperator> t24ComparisonOpList, Logger p_logger)
	{
		SearchParamComparisonOp[] result = SearchParamComparisonOp.translateToUniqueInstanceList(t24ComparisonOpList, p_logger);
		
		if ((result != null) && (result.length == 0))
			result = null;
		
		return result;
	}
}
