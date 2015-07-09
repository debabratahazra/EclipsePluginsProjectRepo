package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.QuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.rules.SetValueRuleWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.ecprojectelems.StaticResultsScreenHeaderControlElems;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.constants.FieldDisplayType;
import com.odcgroup.edge.t24.generation.util.HtmlUtils;

/**
 * A <code>StaticResultsScreenHeaderTableQuestionSpec</code> is a {@link #generateTableQuestionAndDataItem "materializable"} specification for a single table [column] question
 * within an owning {@link StaticResultsScreenHeaderTableSpec}.<p>
 * 
 * Note that, in addition to knowing how "materialize" the {@link QuestionWrapperPair}, this class also asssumes responsiblity for creation of:<p>
 * 
 * <ul>
 *    <li>
 *        the {@link PropertyWrapper data item} backing the generated {@link QuestionWrapper} - this is added to the {@link StaticResultsScreenHeaderControlElems#staticHeaderValuesGroup
 *        staticHeaderValuesGroup} within the supplied {@link StaticResultsScreenHeaderControlElems} instance.
 *    </li>
 *    <li>
 *        this is added as a "true" rule to the {@link StaticResultsScreenHeaderControlElems#initStaticHeaderValuesRule initStaticHeaderValuesRule} within the supplied
 *        {@link StaticResultsScreenHeaderControlElems} instannce.
 *    </li>
 * </ul><p>
 *
 * @author Simon Hayes
 */
public class StaticResultsScreenHeaderTableQuestionSpec
{
    private final int m_t24LineNumber;
    private final int m_t24ColumnNumber;
    private final String m_englishStaticHeaderText;
    
    StaticResultsScreenHeaderTableQuestionSpec(int p_t24LineNumber, int p_t24ColumnNumber, String p_englishStaticHeaderText)
    {
        m_t24LineNumber = AssertionUtils.requireNonNegative(p_t24LineNumber, "p_t24LineNumber");
        m_t24ColumnNumber = AssertionUtils.requireNonNegative(p_t24ColumnNumber, "p_t24ColumnNumber");
        m_englishStaticHeaderText = AssertionUtils.requireNonNullAndNonEmpty(p_englishStaticHeaderText, "p_englishStaticHeaderText");
    }
    
    QuestionWrapperPair generateTableQuestionAndDataItem(StaticResultsScreenHeaderControlElems p_controlElems, FormContext p_formContext)
    {
        AssertionUtils.requireNonNull(p_controlElems, "p_controlElems");
        AssertionUtils.requireNonNull(p_formContext, "p_formContext");
     
        final String staticHeaderValueItemName = "HeaderValue_" + m_t24ColumnNumber + "_" + m_t24LineNumber;
        
        final PropertyWrapper staticHeaderValueItem = PropertyWrapper.create(p_formContext, staticHeaderValueItemName);
        p_controlElems.staticHeaderValuesGroup.addChild(staticHeaderValueItem);
        
        final SetValueRuleWrapper setStaticHeaderItemValueRule = SetValueRuleWrapper.create(p_formContext);
        setStaticHeaderItemValueRule.setName("Set " + staticHeaderValueItemName);
        setStaticHeaderItemValueRule.setType(SetValueRuleWrapper.EType.DATA_ITEM);
        setStaticHeaderItemValueRule.setPropertyName(staticHeaderValueItem);
        setStaticHeaderItemValueRule.setFromType(SetValueRuleWrapper.EFromType.VALUE);
        setStaticHeaderItemValueRule.setFromValue(HtmlUtils.convertSpacesToHtmlNonBreakingSpaces(m_englishStaticHeaderText, false, false));
        p_controlElems.initStaticHeaderValuesRule.addTrueRule(setStaticHeaderItemValueRule);
        
        final String staticHeaderCoordinatesText = genStaticHeaderCoordinatesText();
        
        final QuestionWrapper questionWrapper = QuestionWrapper.create(p_formContext);
        questionWrapper.setQuestionText(genLogicalQuestionText(staticHeaderCoordinatesText));
        questionWrapper.setPropertyKeyFromEntityKey(staticHeaderValueItem.getEntityKeyName() + ".allowMarkup()");
        questionWrapper.setMandatory(Boolean.FALSE);
        questionWrapper.setReadOnly(Boolean.TRUE);
        
        final RichHTMLPresentationQuestionWrapper presQuestionWrapper = RichHTMLPresentationQuestionWrapper.create(p_formContext, questionWrapper.unwrap());
        FieldDisplayType.HEADER_DATA_LABEL.applyTo(presQuestionWrapper);
        presQuestionWrapper.setReadOnlyFormat(RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.TEXT_ALLOW_MARKUP_);
        presQuestionWrapper.setHintText(staticHeaderCoordinatesText);
        
        return new QuestionWrapperPair(questionWrapper, presQuestionWrapper);
    }
    
    String genLogicalQuestionText(String p_staticHeaderCoordinatesText)
    {
        AssertionUtils.requireNonNullAndNonEmpty(p_staticHeaderCoordinatesText, "p_staticHeaderCoordinatesText");
        
        return new StringBuilder()
            .append('[')
            .append(p_staticHeaderCoordinatesText)
            .append(" (\"")
            .append(m_englishStaticHeaderText)
            .append("\")]")
            .toString();
    }
    
    String genStaticHeaderCoordinatesText()
    {
        return new StringBuilder()
            .append("@(")
            .append(m_t24ColumnNumber)
            .append(", ")
            .append(m_t24LineNumber)
            .append(')')
            .toString();
    }
}
