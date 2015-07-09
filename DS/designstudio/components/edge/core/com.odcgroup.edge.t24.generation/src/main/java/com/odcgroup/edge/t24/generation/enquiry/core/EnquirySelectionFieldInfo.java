package com.odcgroup.edge.t24.generation.enquiry.core;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.EdgeConnectDataType;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * <code>EnquirySelectionFieldInfo</code> is a simple immutable "value object" providing information about a single Enquiry selection (i.e. search filter) field.
 *
 * @author Simon Hayes
 */
public class EnquirySelectionFieldInfo
{
	public final String appFieldName;
	public final String enquiryFieldName;
	public final EdgeConnectDataType edgeConnectDataType;
	public final SearchParamDropdownInfo searchParamDropdownInfo;
    public final MdfProperty appFieldDef;
	
	public EnquirySelectionFieldInfo(String p_appFieldName, String p_enquiryFieldName, EdgeConnectDataType p_edgeConnectDataType, SearchParamDropdownInfo p_searchParamDropdownInfo, MdfProperty p_appFieldDef)
	{
        appFieldName = AssertionUtils.requireNonNullAndNonEmpty(p_appFieldName, "p_appFieldName");
		enquiryFieldName = AssertionUtils.requireNonNullAndNonEmpty(p_enquiryFieldName, "p_enquiryFieldName");
		edgeConnectDataType = AssertionUtils.requireNonNull(p_edgeConnectDataType, "p_dataTypeIdentifier");
		searchParamDropdownInfo = p_searchParamDropdownInfo;
        appFieldDef = p_appFieldDef;
	}
}
