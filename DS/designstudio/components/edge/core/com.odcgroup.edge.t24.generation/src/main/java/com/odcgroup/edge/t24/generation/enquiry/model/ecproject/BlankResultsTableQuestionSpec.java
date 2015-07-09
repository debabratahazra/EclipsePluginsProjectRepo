package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;

/**
 * A <code>BlankResultsTableQuestionSpec</code> instance is a materializable specification for a table column question acting as a "filler" column for the purpose of achieving a
 * common column set across multiple enquiry results tables.<p>
 * 
 * (Note that this class implements the {@link ResultsTableQuestionGenerator} interface through its derivation from {@link  AbstractResultsTableQuestionGenerator}).
 *
 * @author shayes
 *
 */
public class BlankResultsTableQuestionSpec extends AbstractResultsTableQuestionGenerator implements ResultsTableQuestionGenerator
{
	private final PropertyWrapper m_fillerDataItem;
	
	BlankResultsTableQuestionSpec(IrisResultItemSpec p_definingIrisResultItemSpec, PropertyWrapper p_fillerDataItem, ResultsTableType p_targetResultsTableType)
	{
		super(
			AssertionUtils.requireNonNull(p_definingIrisResultItemSpec, "p_definingIrisResultItemSpec"),
			AssertionUtils.requireNonNull(p_targetResultsTableType, "p_targetResultsTableType")
		);
		
		m_fillerDataItem = AssertionUtils.requireNonNull(p_fillerDataItem, "p_fillerDataItem");
	}
	
    @Override
    public boolean isForSingleImageTypedField() {
        return false;
    }

    @Override
    public boolean isForCollapsibleResultsTableColumnQuestion() {
        return false;
    }

	@Override
	protected String getLogicalQuestionText(IrisResultItemSpec p_definingIrisResultItemSpec)
	{
		return "[BLANK]";
	}

	@Override
	protected PropertyWrapper getQuestionDataItem()
	{
		return m_fillerDataItem;
	}
}
