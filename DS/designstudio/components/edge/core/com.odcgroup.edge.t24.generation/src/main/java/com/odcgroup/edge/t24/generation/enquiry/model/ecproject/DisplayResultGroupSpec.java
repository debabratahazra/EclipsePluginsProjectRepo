package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.FormTableWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTableWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.BasicEnquiryTemplateIFPConstants;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryAttrsDigest;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.ResultsTableType;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.EnquiryStyleStrings;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.constants.presentation.ResultsTableStyleRefinement;

/**
 * <code>DisplayResultGroupSpec</code> is a {@link #generateDataGroup(FormContext) materializable} specification for a multi-instance data group acting as the source of displayable values for an
 * enquiry results table of a specified {@link ResultsTableType type}.<p><p>
 *
 * The lifecycle of a <code>DisplayResultGroupSpec</code> instance is as follows:<p>
 * 
 * <ul>
 *     <li>
 *         Initial {@link #DisplayResultGroupSpec(ResultsTableType) construction} for a specified {@link ResultsTableType}
 *     </li>
 *     <li>
 *         Incremental population (comprising one or more calls to {@link #findOrCreateDisplayResultItemSpecFor(IrisResultItemSpec)}
 *     </li>
 *     <li>
 *         Rationalization of the fully populated model ({@link #setInstanceNumberForNameSuffixing(int)}, {@link #renameMultiplyReferencedDisplayItems()}
 *         {@link #notifyClubbableT24ColumnNumberSet(ClubbableT24ColumnSet)})
 *     </li>
 *     <li>
 *         Materialization of the specified display result group ({@link #generateDataGroup(FormContext)})
 *     </li>
 *     <li>
 *         Materialization of a {@link DisplayResultsTableWrapperPair} defining the display table that will present the contents of the specified display result group ({@link #generateResultsTable(FormContext)})
 *     </li>
 * </ul><p><p>
 * 
 * author: Simon Hayes
 */
public class DisplayResultGroupSpec
{
	private final TreeMap<Integer,DisplayResultItemSpec> m_displayResultSpecItemByT24ColumnNumber = new TreeMap<Integer,DisplayResultItemSpec>();
	private final ResultsTableType m_resultsTableType;

	private Integer m_instanceNumber;
	private ClubbableT24ColumnSet m_clubbableT24ColumnSet;
	
	private PropertyGroupWrapper m_generatedDataGroup;
	private PropertyWrapper m_emptyColumnFillerItem;
	
	private boolean m_areAllDisplayItemsTargettedBySingleFooterSectionFields;
	
	public DisplayResultGroupSpec(ResultsTableType p_resultTableType)
	{
		m_resultsTableType = AssertionUtils.requireNonNull(p_resultTableType, "p_resultTableType");
	}
	
	public ResultsTableType getTargetResultsTableType()
	{
		return m_resultsTableType;
	}
	
	public PropertyGroupWrapper generateDataGroup(FormContext p_formContext)
	{
		assertDataGroupNotYetGenerated();
		assertClubbableT24ColumnSetNotified();
		
		AssertionUtils.requireNonNull(p_formContext, "p_formContext");
		
		m_generatedDataGroup = PropertyGroupWrapper.create(p_formContext, getGroupName());
		m_generatedDataGroup.setFixedSize(Boolean.FALSE);
		
		final Iterator<DisplayResultItemSpec> displayResultItemSpecIter = m_displayResultSpecItemByT24ColumnNumber.values().iterator();
		m_areAllDisplayItemsTargettedBySingleFooterSectionFields = true; // tentative
		
		while(displayResultItemSpecIter.hasNext())
		{
			final DisplayResultItemSpec displayResultItemSpec = displayResultItemSpecIter.next();
			final PropertyWrapper displayResultDataItem = displayResultItemSpec.generateDataItem(p_formContext);

			m_generatedDataGroup.addChild(displayResultDataItem);
			m_areAllDisplayItemsTargettedBySingleFooterSectionFields &= displayResultItemSpec.isForSingleFooterSectionField();
		}

		boolean isEmptyColumnFillerItemRequired = (
		    (
		        m_resultsTableType.isDisplayEndResults() &&
		        m_areAllDisplayItemsTargettedBySingleFooterSectionFields
		    ) ||
		    (
    			m_resultsTableType.isClubbableColumnSetMember() &&
    			(
    				(! m_resultsTableType.offersEnquiryDrilldowns()) ||
    				((m_clubbableT24ColumnSet.size() > m_displayResultSpecItemByT24ColumnNumber.size()))
    			)
    		)
		);
		
		if (isEmptyColumnFillerItemRequired)
		{
			m_emptyColumnFillerItem = PropertyWrapper.create(p_formContext, "emptyColumnFiller");
			m_generatedDataGroup.addChild(m_emptyColumnFillerItem);
		}
		
		return m_generatedDataGroup;
	}
	
	public PropertyWrapper getEmptyColumnFillerItem()
	{
		assertDataGroupGenerated();
		return m_emptyColumnFillerItem;
	}
	
	public DisplayResultsTableWrapperPair generateResultsTable(FormContext p_formContext, EnquiryAttrsDigest p_enquiryAttrsDigest)
	{
		assertDataGroupGenerated();
		assertClubbableT24ColumnSetNotified();

		AssertionUtils.requireNonNull(p_formContext, "p_formContext");
		AssertionUtils.requireNonNull(p_enquiryAttrsDigest, "p_enquiryAttrsDigest");

		final FormTableWrapper resultsTable = FormTableWrapper.create(p_formContext);
		resultsTable.setTableTitle(getTableTitle());
		resultsTable.setNotApplicable(Boolean.TRUE);
		resultsTable.setConditionExpression("$$" + m_generatedDataGroup.getEntityKeyName() + ".firstFreeInstance()$ > 1");
		
		if (m_resultsTableType.offersEnquiryDrilldowns())
		{
			resultsTable.setSelectorFromEntityKey(BasicEnquiryTemplateIFPConstants.DataStore.WorkingElementsGroup.ItemPaths.RESULTS_TABLE_SELECTOR);
			resultsTable.setAutoSelector(Boolean.TRUE);
		}
		
		final RichHTMLPresentationTableWrapper resultsPresTable = RichHTMLPresentationTableWrapper.create(p_formContext, resultsTable.unwrap());
		m_resultsTableType.applyPresentationOptionsTo(resultsPresTable, p_enquiryAttrsDigest, m_areAllDisplayItemsTargettedBySingleFooterSectionFields);
		
		final Iterator<ResultsTableQuestionGenerator> tableQuestionGeneratorIter = buildTableQuestionGeneratorList().iterator();
		
		final boolean
		    targetTableTypeSupportsCollapsibleColumns = m_resultsTableType.supportsCollapsibleColumns(),
		    isEnquiryResultsHeaderTable = m_resultsTableType.isEnquiryHeaderResults();
		
		boolean
		    includesColumnQuestionsMarkedAsCollapsible = false,
		    isLeftmostTableQuestion = true,
		    wasPrevColumnForSingleImageTypedField = false;
		
		while(tableQuestionGeneratorIter.hasNext())
		{
			final ResultsTableQuestionGenerator tableQuestionGenerator = tableQuestionGeneratorIter.next();
			final QuestionWrapperPair tableQuestionWrapperPair = tableQuestionGenerator.generateResultsTableQuestion(p_formContext, p_enquiryAttrsDigest);
			final boolean isThisColumnForSingleImageTypedField = tableQuestionGenerator.isForSingleImageTypedField();
			
			if (! isLeftmostTableQuestion)
			{
			    if (isEnquiryResultsHeaderTable)
			        tableQuestionWrapperPair.presWrapperObject.setAnswerControlStyle(EnquiryStyleStrings.HEADER_ENQUIRY_RESULTS_TABLE_QUESTION_WITH_LEFT_PADDING);
			    
			    if (isThisColumnForSingleImageTypedField && wasPrevColumnForSingleImageTypedField)
			        tableQuestionWrapperPair.presWrapperObject.setGroupWithPrevious(Boolean.TRUE);
			}
			
			resultsTable.addChild(tableQuestionWrapperPair.wrapperObject);
			resultsPresTable.addChild(tableQuestionWrapperPair.presWrapperObject);
			
			includesColumnQuestionsMarkedAsCollapsible |= (targetTableTypeSupportsCollapsibleColumns && tableQuestionGenerator.isForCollapsibleResultsTableColumnQuestion());
			isLeftmostTableQuestion = false;
			
			wasPrevColumnForSingleImageTypedField = isThisColumnForSingleImageTypedField;
		}
		
		if (includesColumnQuestionsMarkedAsCollapsible)
		    ResultsTableStyleRefinement.COLLAPSIBLE_TABLE.addTo(resultsPresTable);
		    
		return new DisplayResultsTableWrapperPair(resultsTable, resultsPresTable, m_resultsTableType, includesColumnQuestionsMarkedAsCollapsible);
	}
	
	DisplayResultItemSpec findOrCreateDisplayResultItemSpecFor(VisibleIrisResultItemSpec p_visibleIrisResultItemSpec)
	{
		assertDataGroupNotYetGenerated();
		AssertionUtils.requireNonNull(p_visibleIrisResultItemSpec, "p_visibleIrisResultItemSpec");
		
		final DisplayableT24FieldPosition t24DisplayPosition = p_visibleIrisResultItemSpec.getT24DisplayPosition();
		
		DisplayResultItemSpec displayResultItemSpec = m_displayResultSpecItemByT24ColumnNumber.get(t24DisplayPosition.getT24ColumnNumber());
		
		if (displayResultItemSpec == null)
			m_displayResultSpecItemByT24ColumnNumber.put(t24DisplayPosition.getT24ColumnNumber(), displayResultItemSpec = new DisplayResultItemSpec(this, p_visibleIrisResultItemSpec.getDataItemName()));
		
		displayResultItemSpec.notifyReferencedBy(p_visibleIrisResultItemSpec);
		
		return displayResultItemSpec;
	}
	
	boolean requiresInstanceNumberForNameSuffixing()
	{
		return m_resultsTableType.requiresSeparateTablePerT24LineNumber();
	}
	
	void setInstanceNumberForNameSuffixing(int p_instanceNumber)
	{
		AssertionUtils.requireConditionTrue(requiresInstanceNumberForNameSuffixing(), "requiresInstanceNumberForNameSuffixing()");
		AssertionUtils.requireNonNegative(p_instanceNumber, "p_instanceNumber");

		m_instanceNumber = new Integer(p_instanceNumber);
	}

	boolean requiresClubbableT24ColumnSet()
	{
		return m_resultsTableType.isClubbableColumnSetMember();
	}
	
	void notifyClubbableT24ColumnNumberSet(ClubbableT24ColumnSet p_clubbableT24ColumnNumberSet)
	{
		AssertionUtils.requireConditionTrue(requiresClubbableT24ColumnSet(), "requiresClubbableT24ColumnNumberSet()");
		AssertionUtils.requireNonNull(p_clubbableT24ColumnNumberSet, "p_t24ColumnNumberSet");
		
		m_clubbableT24ColumnSet = p_clubbableT24ColumnNumberSet;
	}
	
	void renameMultiplyReferencedDisplayItems()
	{
		final Iterator<DisplayResultItemSpec> displayItemSpecIter = m_displayResultSpecItemByT24ColumnNumber.values().iterator();
		int ordinalColumnNumber = 1;
		
		while(displayItemSpecIter.hasNext())
		{
			final DisplayResultItemSpec displayItemSpec = displayItemSpecIter.next();
			
			if (displayItemSpec.getNumReferencingIrisResultItemSpecs() > 1)
				displayItemSpec.renameDataItemAs("Column" + ordinalColumnNumber + "Value");
			
			++ordinalColumnNumber;
		}
	}
	
	String getGroupName()
	{
		String result = m_resultsTableType.getDisplayResultGroupName();
		
		if (requiresInstanceNumberForNameSuffixing())
		{
			assertInstanceNumberForNameSuffixingNotified();
			result += m_instanceNumber;
		}
		
		return result;
	}
	
	String getTableTitle()
	{
		String result = m_resultsTableType.getTableTitle();
		
		if (requiresInstanceNumberForNameSuffixing())
		{
			assertInstanceNumberForNameSuffixingNotified();
			result += " " + m_instanceNumber;
		}
		
		return result;
	}
	
	private void assertInstanceNumberForNameSuffixingNotified()
	{
		if (requiresInstanceNumberForNameSuffixing())
		{
			if (m_instanceNumber == null)
				throw new IllegalStateException("this.requiresInstanceNumberSuffixForGroupName() -> true, but this.setInstanceNumberSuffixForGroupName() hasn't been (successfully) called");
		}
	}
	
	private void assertClubbableT24ColumnSetNotified()
	{
		if (requiresClubbableT24ColumnSet())
		{
			if (m_clubbableT24ColumnSet == null)
				throw new IllegalStateException("this.requiresClubbableT24ColumnNumberSet() -> true, but this.notifyClubbableT24ColumnNumberSet() hasn't been (successfully) called");
		}
	}
	
	private void assertDataGroupGenerated()
	{
		if (m_generatedDataGroup == null)
			throw new IllegalStateException("generateDataGroup(FormContext) has yet to be called on this instance !");
	}

	private void assertDataGroupNotYetGenerated()
	{
		if (m_generatedDataGroup != null)
			throw new IllegalStateException("generateDataGroup(FormContext) has already been called on this instance !");
	}

	private List<ResultsTableQuestionGenerator> buildTableQuestionGeneratorList()
	{
		List<ResultsTableQuestionGenerator> result = new ArrayList<ResultsTableQuestionGenerator>();
		
		if (! requiresClubbableT24ColumnSet())
		{
			result.addAll(m_displayResultSpecItemByT24ColumnNumber.values());
			
			if (m_resultsTableType.isDisplayEndResults() && m_areAllDisplayItemsTargettedBySingleFooterSectionFields)
			    result.add(new InflatableColumnFillerQuestionGenerator(m_emptyColumnFillerItem));
		}
		
		else
		{
			final Iterator<Integer> clubbableT24ColumnNumberIter = m_clubbableT24ColumnSet.getDisplayOrderingT24ColumnNumberIterator();

			while(clubbableT24ColumnNumberIter.hasNext())
			{
				final Integer clubbedT24ColumnNumber = clubbableT24ColumnNumberIter.next();
				
				ResultsTableQuestionGenerator tableQuestionGenerator = m_displayResultSpecItemByT24ColumnNumber.get(clubbedT24ColumnNumber);
				
				if (tableQuestionGenerator == null)
				{
					final IrisResultItemSpec definingIrisResultItemSpec = m_clubbableT24ColumnSet.getDefiningIrisResultItemSpec(clubbedT24ColumnNumber);
					tableQuestionGenerator = new BlankResultsTableQuestionSpec(definingIrisResultItemSpec, m_emptyColumnFillerItem, m_resultsTableType);
				}
				
				result.add(tableQuestionGenerator);
			}
		}
		
		return result;
	}
}
