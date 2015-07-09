package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import com.acquire.util.AssertionUtils;
import com.acquire.util.StringUtils;
import com.odcgroup.t24.enquiry.enquiry.ConstantOperation;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.Operation;

/**
 * A <code>ResultsTableQuestionSpec</code> is a {@link #generateResultsTableQuestion(com.acquire.intelligentforms.FormContext) materializable} specification for a results-table
 * question associated with a specified display result item.
 *
 * @author	Simon Hayes
 */
public class ResultsTableQuestionSpec extends AbstractResultsTableQuestionGenerator
{
	private final DisplayResultItemSpec m_displayResultItemSpec;
	
	ResultsTableQuestionSpec(DisplayResultItemSpec p_displayResultItemSpec, VisibleIrisResultItemSpec p_visibleIrisResultItemSpec)
	{
		super(
			AssertionUtils.requireNonNull(p_visibleIrisResultItemSpec, "p_visibleIrisResultItemSpec"),
			p_visibleIrisResultItemSpec.getTargetResultsTableType()
		);

		m_displayResultItemSpec = AssertionUtils.requireNonNull(p_displayResultItemSpec, "p_displayResultItemSpec");
	}
	
    @Override
	protected String getLogicalQuestionText(IrisResultItemSpec p_definingIrisResultItemSpec)
	{
		final StringBuilder sb = new StringBuilder();
		
		sb.append('[');
		
		if (m_displayResultItemSpec.getNumReferencingIrisResultItemSpecs() > 1)
		{
			final String[] sourceT24EnqFieldNames = m_displayResultItemSpec.getSourceT24EnquiryFieldNamesInAscendingT24LineNumberOrder();
			
			for (int i = 0; i < sourceT24EnqFieldNames.length; ++i)
			{
				if (i > 0)
					sb.append(", ");
				
				sb.append(sourceT24EnqFieldNames[i]);
			}
		}
		
		else
		{
			final Field enquiryField = p_definingIrisResultItemSpec.getEnquiryField();
			final Operation fieldOperation = enquiryField.getOperation();
			
			sb.append(enquiryField.getName());
			
			if (fieldOperation instanceof ConstantOperation)
			{
				final String constantValue = StringUtils.trimEmptyToNull(((ConstantOperation) fieldOperation).getValue());
				
				if (constantValue != null)
					sb.append(" (").append(constantValue).append(')');
			}
		}
		
		return sb.append(']').toString();
	}

	@Override
	protected PropertyWrapper getQuestionDataItem()
	{
		return m_displayResultItemSpec.getGeneratedDataItem();
	}
}
