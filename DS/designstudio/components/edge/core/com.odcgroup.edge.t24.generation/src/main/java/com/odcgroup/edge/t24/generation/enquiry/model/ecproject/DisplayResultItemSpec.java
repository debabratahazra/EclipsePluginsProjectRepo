package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import java.util.Iterator;
import java.util.TreeMap;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryAttrsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.constants.FieldDisplayType;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableQuestionAnswerStyleRefinement;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableQuestionStyleRefinement;
import com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind;
import com.odcgroup.t24.enquiry.enquiry.Field;

/**
 * <code>DisplayResultItemSpec</code> is a {@link #generateDataItem(FormContext) materializable} specification for a single data item within a "display result group" <i>(i.e. a data group backing
 * a single enquiry results table)</i> as specified by a parent {@link DisplayResultGroupSpec}.<p><p>
 *  
 * author: Simon Hayes
 */
public class DisplayResultItemSpec implements ResultsTableQuestionGenerator
{
	private final DisplayResultGroupSpec m_parent;
	
	private String m_dataItemName;

	private final TreeMap<Integer,VisibleIrisResultItemSpec> m_referencingIrisResultItemSpecsByT24LineNumber = new TreeMap<Integer,VisibleIrisResultItemSpec>();
	
	private boolean m_isReferencedByIrisPrimaryKeyItem;
	private boolean m_isForCollapsibleResultsTableColumnQuestion;
	
	private boolean m_doTagResultsTableQuestionAsCollapsible;
	
	private PropertyWrapper m_generatedDataItem;
	
	DisplayResultItemSpec(DisplayResultGroupSpec p_parent, String p_dataItemName)
	{
		m_parent = AssertionUtils.requireNonNull(p_parent, "p_parent");
		m_dataItemName = AssertionUtils.requireNonNullAndNonEmpty(p_dataItemName, "p_dataItemName");
	}
	
	void notifyReferencedBy(VisibleIrisResultItemSpec p_referencingIrisResultItemSpec)
	{
		AssertionUtils.requireNonNull(p_referencingIrisResultItemSpec, "p_referencingIrisResultItemSpec");
		
		final ResultsTableType targetResultsTableType = m_parent.getTargetResultsTableType();
		
		m_referencingIrisResultItemSpecsByT24LineNumber.put(p_referencingIrisResultItemSpec.getT24DisplayPosition().getT24LineNumber(), p_referencingIrisResultItemSpec);
		m_isReferencedByIrisPrimaryKeyItem |= p_referencingIrisResultItemSpec.isForPrimaryKeyField();
		m_isForCollapsibleResultsTableColumnQuestion |= (targetResultsTableType.supportsCollapsibleColumns() && p_referencingIrisResultItemSpec.isForCollapsibleResultsTableField());
		m_doTagResultsTableQuestionAsCollapsible = m_isForCollapsibleResultsTableColumnQuestion && targetResultsTableType.requiresTaggingOfCollapsibleTableColumnQuestions();
	}

    @Override
    public boolean isForSingleImageTypedField()
    {
        final VisibleIrisResultItemSpec soleVisibleIrisResultItemSpec = getSoleVisibleIrisResultItemSpec();
        return (soleVisibleIrisResultItemSpec != null) && FieldDisplayType.IMAGE.isReferencedBy(soleVisibleIrisResultItemSpec.getEnquiryField());
    }

    public boolean isForSingleFooterSectionField()
    {
        final VisibleIrisResultItemSpec soleVisibleIrisResultItemSpec = getSoleVisibleIrisResultItemSpec();
        return (soleVisibleIrisResultItemSpec != null) && (soleVisibleIrisResultItemSpec.getEnquiryField().getDisplaySection() == DisplaySectionKind.FOOTER);        
    }
    
    @Override
    public boolean isForCollapsibleResultsTableColumnQuestion()
    {
        return m_isForCollapsibleResultsTableColumnQuestion;
    }

    public VisibleIrisResultItemSpec getSoleVisibleIrisResultItemSpec()
    {
        return (m_referencingIrisResultItemSpecsByT24LineNumber.size() == 1) ?
            m_referencingIrisResultItemSpecsByT24LineNumber.values().iterator().next() : null;
        
    }

	int getNumReferencingIrisResultItemSpecs()
	{
		return m_referencingIrisResultItemSpecsByT24LineNumber.size();
	}
	
	void renameDataItemAs(String p_newItemName)
	{
		assertDataItemNotYetGenerated();
		m_dataItemName = AssertionUtils.requireNonNullAndNonEmpty(p_newItemName, "p_newItemName");
	}
	
	String getDisplayGroupName()
	{
		return m_parent.getGroupName();
	}
	
	String getDisplayItemName()
	{
		return m_dataItemName;
	}
	
	ResultsTableType getTargetResultsTableType()
	{
		return m_parent.getTargetResultsTableType();
	}

	PropertyWrapper generateDataItem(FormContext p_formContext)
	{
		assertDataItemNotYetGenerated();
		AssertionUtils.requireNonNull(p_formContext, "p_formContext");

		m_generatedDataItem = PropertyWrapper.create(p_formContext);

		/*
		 * The following seems to be necessary to ensure that we don't end up with the name we specify being auto-suffixed with " - Copy" in some cases
		 * (which is not reversed when the item is added to group) due to the way edgeConnect seems to (mis)manage entity names.
		 * 
		 * This is simply crude kludge that empirically seems to work for now (and I haven't encountered any adverse consequences so far), but could do with
		 * detailed investigation & proper remedy (either within PropertyWrapper / PropertyGroupWrapper or within edgeConnect core code) when there's time.
		 * 
		 * 06/10/2014 Simon Hayes
		 */
		m_generatedDataItem.unwrap().setName(m_dataItemName, true /* p_replace */);
		
		return m_generatedDataItem;
	}
	
	@Override
	public QuestionWrapperPair generateResultsTableQuestion(FormContext p_formContext, EnquiryAttrsDigest p_enquiryAttrsDigest)
	{
		if (m_referencingIrisResultItemSpecsByT24LineNumber.isEmpty())
			throw new IllegalStateException("notifyReferencedBy(IrisResultItemSpec) has yet to be called on this instance");
		
		assertDataItemGenerated();
		
		AssertionUtils.requireNonNull(p_formContext, "p_formContext");
		AssertionUtils.requireNonNull(p_enquiryAttrsDigest, "p_enquiryAttrsDigest");
				
		final VisibleIrisResultItemSpec soleVisibleIrisResultItemSpec = getSoleVisibleIrisResultItemSpec();
		final VisibleIrisResultItemSpec irisResultItemSpecWithLowestT24LineNumber = (soleVisibleIrisResultItemSpec == null) ? m_referencingIrisResultItemSpecsByT24LineNumber.firstEntry().getValue() : soleVisibleIrisResultItemSpec;
		final QuestionWrapperPair resultsTableQuestion = new ResultsTableQuestionSpec(this, irisResultItemSpecWithLowestT24LineNumber).generateResultsTableQuestion(p_formContext, p_enquiryAttrsDigest);
		
		if (m_isReferencedByIrisPrimaryKeyItem)
		    resultsTableQuestion.presWrapperObject.setReadOnlyFormat(RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.TEXT_ALLOW_MARKUP_);
		
		else if (soleVisibleIrisResultItemSpec != null)
		{
		    final Field enqField = soleVisibleIrisResultItemSpec.getEnquiryField();
		    
		    if (FieldDisplayType.GRAPH_AS_BAR.isReferencedBy(enqField))
		        ResultsTableQuestionAnswerStyleRefinement.GRAPH_AS_BAR.addTo(resultsTableQuestion.presWrapperObject);
		}
		
		if (m_doTagResultsTableQuestionAsCollapsible)
		    ResultsTableQuestionStyleRefinement.COLLAPSIBLE_COLUMN.addTo(resultsTableQuestion.presWrapperObject);

		final StringBuilder hintTextBuffer = new StringBuilder();
		
        for (IrisResultItemSpec referencingIrisResultItemSpec: m_referencingIrisResultItemSpecsByT24LineNumber.values())
        {
            if (hintTextBuffer.length() > 0)
                hintTextBuffer.append(", ");
            
            hintTextBuffer.append(referencingIrisResultItemSpec.getT24FieldName());
        }

        resultsTableQuestion.presWrapperObject.setHintText(hintTextBuffer.toString());

        return resultsTableQuestion;
	}
	
	PropertyWrapper getGeneratedDataItem()
	{
		assertDataItemGenerated();
		return m_generatedDataItem;
	}
	
	String[] getSourceT24EnquiryFieldNamesInAscendingT24LineNumberOrder()
	{
		final String[] result = new String[m_referencingIrisResultItemSpecsByT24LineNumber.size()];
		
		final Iterator<VisibleIrisResultItemSpec> irisResultItemSpecIter = m_referencingIrisResultItemSpecsByT24LineNumber.values().iterator();
		int i = 0;
		
		while(irisResultItemSpecIter.hasNext())
		{
			result[i++] = irisResultItemSpecIter.next().getEnquiryField().getName();
		}
		
		return result;
	}
	
	private void assertDataItemGenerated()
	{
		if (m_generatedDataItem == null)
			throw new IllegalStateException("generateDataItem(FormContext) has yet to be called on this instance !");
	}
	
	private void assertDataItemNotYetGenerated()
	{
		if (m_generatedDataItem != null)
			throw new IllegalStateException("generateDataItem(FormContext) has already been called on this instance !");
	}
}