package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import java.util.Comparator;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.translation.translationDsl.Translations;

public abstract class IrisResultItemSpec
{
    public static final Comparator<IrisResultItemSpec> LEFT_TO_RIGHT_WITHIN_TOP_TO_BOTTOM_COMPARATOR = new Comparator<IrisResultItemSpec>()
    {
        @Override
        public int compare(IrisResultItemSpec p_first, IrisResultItemSpec p_second) {
            AssertionUtils.requireNonNull(p_first, "p_first");
            AssertionUtils.requireNonNull(p_second, "p_second");
            
            return DisplayableT24FieldPosition.LEFT_TO_RIGHT_WITHIN_TOP_TO_BOTTOM_COMPARATOR.compare(p_first.getT24DisplayPosition(), p_second.getT24DisplayPosition());
        }
    };  
    
	private final Field m_enquiryField;
	private final String m_dataItemName;
	
	public boolean isForPrimaryKeyField()
	{
	    return "@ID".equalsIgnoreCase(getT24FieldName());
	}
	
	public Field getEnquiryField()
	{
		return m_enquiryField;
	}
	
	public String getT24FieldName()
	{
		return m_enquiryField.getName();
	}
	
	public Translations getT24FieldLabelTranslations()
	{
		return m_enquiryField.getLabel();
	}
	
	public String getT24FieldDisplayType()
	{
		return m_enquiryField.getDisplayType();
	}
	
	public DisplayableT24FieldPosition getT24DisplayPosition()
	{
	    return null;
	}

	public String getDataItemName()
	{
		return m_dataItemName;
	}
	
	protected IrisResultItemSpec(Field p_field)
	{
		AssertionUtils.requireNonNull(p_field, "p_field");
		
		final String t24FieldName = StringUtils.trimEmptyToNull(p_field.getName());
		
		AssertionUtils.requireConditionTrue(t24FieldName != null, "notNullEmptyOrBlank(p_field.getName())");
		AssertionUtils.requireConditionTrue(! "null".equalsIgnoreCase(t24FieldName), "! \"" + t24FieldName + "\".equals(p_field.getName())");
		
		m_enquiryField = p_field;
		m_dataItemName = MapperUtility.processT24NameToIRISName(t24FieldName);
	}
	
	PropertyWrapper generateDataItem(FormContext p_formContext)
	{
		final PropertyWrapper result = PropertyWrapper.create(AssertionUtils.requireNonNull(p_formContext, "p_formContext"));
		
		/*
		 * The following seems to be necessary to ensure that we don't end up with the name we specify being auto-suffixed with " - Copy" in some cases
		 * (which is not reversed when the item is added to group) due to the way edgeConnect seems to (mis)manage entity names.
		 * 
		 * This is simply crude kludge that empirically seems to work for now (and I haven't encountered any adverse consequences so far), but could do with
		 * detailed investigation & proper remedy (either within PropertyWrapper / PropertyGroupWrapper or within edgeConnect core code) when there's time.
		 * 
		 * 06/10/2014 Simon Hayes
		 */
		result.unwrap().setName(m_dataItemName, true /* p_replace */);
		
		return result;
	}
}
