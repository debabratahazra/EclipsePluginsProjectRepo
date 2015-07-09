package com.odcgroup.edge.t24.generation.enquiry.ecprojectelems;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;
import gen.com.acquire.intelligentforms.rules.SetValueRuleWrapper;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.StaticResultsScreenHeadersModel;
import com.odcgroup.edge.t24.generation.enquiry.model.ecproject.VisualItemContainerWrapperPair;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;

/**
 * <code>StaticResultsScreenHeaderControlElems</code> is the return-type of <code>StaticResultsScreenHeadersModel</code>'s
 * {@link StaticResultsScreenHeadersModel#createAndAddStaticHeaderTablesTo(VisualItemContainerWrapperPair) createAndAddStaticHeaderTablesTo}
 * method.<p>
 * 
 * It is a simple aggregate of <code>public final</code> references to the data-store and rule elements created as a byprodct of the above method call. 
 *
 * @author Simon Hayes
 */
public abstract class StaticResultsScreenHeaderControlElems
{
    /**
     * <code>staticHeaderValuesGroup<code> is a <code>WorkingElements[1]</code> sub-group pre-populated with child data items for each of the static results screen header table-column
     * questions.
     */
    public final PropertyGroupWrapper staticHeaderValuesGroup;
    
    /**
     * A <code>ContainerRuleWrapper</code> pre-populated with child {@link SetValueRuleWrapper}(s) to initialize each of the data items in {@link #staticHeaderValuesGroup} with
     * English label value constant as defined by the corresponding {@link EnquiryHeader} element in the Enquiry DSL.
     */ 
    public final ContainerRuleWrapper initStaticHeaderValuesRule;
    
    /**
     * A <code>RichHTMLPresentationSpacingWrapper<code> implementing presentation spacing of 1 line that will have been created and added to the relevant <code>VisualItemContainerWrapperPair</code>
     * by <code>StaticResultsScreenHeadersModel</code>'s {@link StaticResultsScreenHeadersModel#createAndAddStaticHeaderTablesTo(VisualItemContainerWrapperPair) createAndAddStaticHeaderTablesTo}
     * method.
     * 
     * NB: As created, this will be set to be unconditionally HIDDEN. It is up to code external to both <code>StaticResultsScreenHeadersModel</code> and this class to (i) determine under what
     * conditions this presentation line-spacing needs to be shown, and - if so - (ii) to set an appropriate display {@link RichHTMLPresentationSpacingWrapper#setConditionExpression(String) condition}
     * so that this line-spacing is shown under those conditions.
     */
    public final RichHTMLPresentationSpacingWrapper lineSpacingBeforeFirstStaticResultsTable;
    
    protected StaticResultsScreenHeaderControlElems(
        PropertyGroupWrapper p_staticHeaderValuesGroup,
        ContainerRuleWrapper p_initStaticHeaderValuesRule,
        RichHTMLPresentationSpacingWrapper p_lineSpacingBeforeFirstStaticResultsTable
    ) {
        staticHeaderValuesGroup = AssertionUtils.requireNonNull(p_staticHeaderValuesGroup, "p_staticHeaderValuesGroup");
        initStaticHeaderValuesRule = AssertionUtils.requireNonNull(p_initStaticHeaderValuesRule, "p_initStaticHeaderValuesRule");
        lineSpacingBeforeFirstStaticResultsTable = AssertionUtils.requireNonNull(p_lineSpacingBeforeFirstStaticResultsTable, "p_lineSpacingBeforeFirstStaticResultsTable");
    }
}