package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import java.util.Comparator;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryFieldsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.temenos.connect.enquiry.IRISFieldItemCustomAttrNames;

/**
 * An <code>VisibleIrisResultItemSpec</code> is a {@link #generateDataItem(FormContext) materializable} specification for a single <u>displayable</u> enquiry result data item somewhere
 * within the IRIS response group structure (as specified by a parent {@link IrisResultGroupSpec}).<p><p>
 * 
 * The lifecycle of a <code>DisplayableIrisResultItemSpec</code> instance is as follows:<p>
 * 
 * <ul>
 *     <li>
 *         Initial {@link #VisibleIrisResultItemSpec(FieldPosition, ResultsTableType) construction} for a specified enquiry {@link Field} and target {@link ResultsTableType} (deduced by the caller
 *         based on the logical {@link Field#getDisplaySection() display section} defined for the field in the Enquiry DSL).
 *     </li>
 *     <li>
 *         {@link #setDisplayResultItemSpec(DisplayResultItemSpec) notification} of the {@link DisplayResultItemSpec} correspondent within the {@link DisplayResultGroupSpec} for the target
 *         {@link ResultsTableType} when that is created.
 *     </li>
 *     <li>
 *         Materialization of the specified iris result data item ({@link #generateDataItem(FormContext)}, which will include the necessary custom attributes (as specified by the
 *         {@link IRISFieldItemCustomAttrNames} interface) to drive {@link com.temenos.connect.enquiry.EnquiryDisplayResultsPopulatorRule EnquiryDisplayResultsPopulatorRule}.
 *     </li>
 * </ul>
 *
 * @author Simon Hayes
 */
public class VisibleIrisResultItemSpec extends IrisResultItemSpec
{
	public static final Comparator<VisibleIrisResultItemSpec> LEFT_TO_RIGHT_WITHIN_TOP_TO_BOTTOM_COMPARATOR = new Comparator<VisibleIrisResultItemSpec>()
	{
		@Override
		public int compare(VisibleIrisResultItemSpec p_first, VisibleIrisResultItemSpec p_second) {
			AssertionUtils.requireNonNull(p_first, "p_first");
			AssertionUtils.requireNonNull(p_second, "p_second");
			
			return DisplayableT24FieldPosition.LEFT_TO_RIGHT_WITHIN_TOP_TO_BOTTOM_COMPARATOR.compare(p_first.getT24DisplayPosition(), p_second.getT24DisplayPosition());
		}
	};  
	
	private final DisplayableT24FieldPosition m_t24DisplayPosition;
	private final ResultsTableType m_targetResultsTableType;
	private final boolean m_isForCollapsibleResultsTableField;

	private DisplayResultItemSpec m_displayResultItemSpec;

	public VisibleIrisResultItemSpec(Field p_field, ResultsTableType p_targetResultsTableType, EnquiryFieldsDigest p_enquiryFieldsDigest)
	{
		super(AssertionUtils.requireNonNull(p_field, "p_field"));
		
		AssertionUtils.requireNonNull(p_targetResultsTableType, "p_targetResultsTableType");
		AssertionUtils.requireNonNull(p_enquiryFieldsDigest, "p_enquiryFieldsDigest");
		
		final FieldPosition fieldPosition = p_field.getPosition();
		AssertionUtils.requireConditionTrue(fieldPosition != null, "p_field.getPosition() != null");
		
		m_t24DisplayPosition = new DisplayableT24FieldPosition(fieldPosition);
		m_targetResultsTableType = p_targetResultsTableType;
		m_isForCollapsibleResultsTableField = p_enquiryFieldsDigest.isVisibleEnquiryFieldInCollapsibleColumn(p_field);
	}

	public boolean isForCollapsibleResultsTableField()
	{
		return m_isForCollapsibleResultsTableField;
	}
	
	@Override
    public DisplayableT24FieldPosition getT24DisplayPosition()
	{
		return m_t24DisplayPosition;
	}
	
	public ResultsTableType getTargetResultsTableType()
	{
		return m_targetResultsTableType;
	}
	
	void setDisplayResultItemSpec(DisplayResultItemSpec p_displayResultItemSpec)
	{
		m_displayResultItemSpec = AssertionUtils.requireNonNull(p_displayResultItemSpec, "p_displayResultItemSpec");
	}
	
	@Override
	PropertyWrapper generateDataItem(FormContext p_formContext)
	{
		AssertionUtils.requireNonNull(p_formContext, "p_formContext");
		AssertionUtils.requireConditionTrue(m_displayResultItemSpec != null, "setDisplayResultItemSpec(DisplayResultItemSpec) has been successfully called");

		final PropertyWrapper result = super.generateDataItem(AssertionUtils.requireNonNull(p_formContext, "p_formContext"));
		
		result.setAttribute(IRISFieldItemCustomAttrNames.DISPLAY_GROUP_NAME, m_displayResultItemSpec.getDisplayGroupName());
		result.setAttribute(IRISFieldItemCustomAttrNames.DISPLAY_ITEM_NAME, m_displayResultItemSpec.getDisplayItemName());
		result.setAttribute(IRISFieldItemCustomAttrNames.SORT_ORDER_FOR_VALUE_BLOCK_WITHIN_COLUMN, String.valueOf(m_t24DisplayPosition.getT24LineNumber()));
		
		if (isForPrimaryKeyField())
		    result.setAttribute(IRISFieldItemCustomAttrNames.IS_PRIMARY_KEY_ITEM, "Y");
		
		return result;
	}
}
