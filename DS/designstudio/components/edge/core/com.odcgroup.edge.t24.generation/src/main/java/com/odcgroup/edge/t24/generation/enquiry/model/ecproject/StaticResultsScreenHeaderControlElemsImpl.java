package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.rules.ContainerRuleWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.pattern.container.BasicProject;
import com.odcgroup.edge.t24.generation.enquiry.ecprojectelems.StaticResultsScreenHeaderControlElems;


/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class StaticResultsScreenHeaderControlElemsImpl extends StaticResultsScreenHeaderControlElems
{
    static StaticResultsScreenHeaderControlElemsImpl create (BasicProject p_project, PropertyGroupWrapper p_workingElementsGroup, RichHTMLPresentationSpacingWrapper p_lineSpacingBeforeFirstResultsTable)
    {
        AssertionUtils.requireNonNull(p_project, "p_project");
        AssertionUtils.requireNonNull(p_workingElementsGroup, "p_workingElementsGroup");
        AssertionUtils.requireNonNull(p_lineSpacingBeforeFirstResultsTable, "p_lineSpacingBeforeFirstResultsTable");
        
        final FormContext formContext = p_project.getProject().getFormContext();
        
        final PropertyGroupWrapper staticResultsScreenHeaderValuesGroup = PropertyGroupWrapper.create(formContext, "StaticResultsScreenHeaderValues");
        p_workingElementsGroup.addChild(staticResultsScreenHeaderValuesGroup);
        
        final ContainerRuleWrapper initStaticResultsScreenHeaderValuesRule = ContainerRuleWrapper.create(formContext, "Initialize static results header values");
        
        return new StaticResultsScreenHeaderControlElemsImpl(staticResultsScreenHeaderValuesGroup, initStaticResultsScreenHeaderValuesRule, p_lineSpacingBeforeFirstResultsTable);
    }
    
    private StaticResultsScreenHeaderControlElemsImpl(PropertyGroupWrapper p_staticHeaderValuesGroup, ContainerRuleWrapper p_initStaticHeaderValuesRule, RichHTMLPresentationSpacingWrapper p_lineSpacingBeforeFirstResultsTable)
    {
        super(p_staticHeaderValuesGroup, p_initStaticHeaderValuesRule, p_lineSpacingBeforeFirstResultsTable);
    }
}
