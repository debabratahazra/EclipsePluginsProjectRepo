package com.odcgroup.edge.t24.generation.enquiry.model.ecproject;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationSpacingWrapper;
import gen.com.acquire.intelligentforms.rules.SetValueRuleWrapper;

import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.emf.common.util.EList;

import com.acquire.intelligentforms.FormContext;
import com.acquire.util.AssertionUtils;
import com.acquire.util.StringUtils;
import com.edgeipk.builder.pattern.container.BasicProject;
import com.odcgroup.edge.t24.generation.BasicEdgeMapperProject;
import com.odcgroup.edge.t24.generation.enquiry.EnquiryUtils;
import com.odcgroup.edge.t24.generation.enquiry.ecprojectelems.StaticResultsScreenHeaderControlElems;
import com.odcgroup.edge.t24.generation.enquiry.model.dsl.EnquiryAttrsDigest;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.Field;


/**
 * A <code>StaticResultsScreenHeadersModel</code> models the <u>set</u> of results-screen tables (one per distinct T24 line number} required to present any headers that
 * are defined by {@link EnquiryHeader} (<code>header: {...}</code>) elements vs. by Enquiry {@link Field}(s) having a {@link Field#getDisplaySection() display-section}
 * of {@link DisplaySectionKind#HEADER "Header"}) within the specified {@link Enquiry}.<p>
 * 
 * Given that it's the exception rather than the norm for results-screen headers to be specified in this way, rather than exposing its constructor, this class provides a static
 * {@link #createIfNeededFor(BasicProject, EnquiryAttrsDigest, PropertyGroupWrapper, PropertyWrapper) create-if-needed} method that can legally return <code>null</code> if the
 * supplied {@link Enquiry} <u>isn't</u> one containing any "static headers" of the kind modelled by this class.<p>
 *
 * In the case where a <code>StaticResultsScreenHeadersModel</code> instance <u>is</u> returned by that method however, the calling code need merely invoke it's
 * {@link #createAndAddStaticHeaderTablesTo(VisualItemContainerWrapperPair) createAndAddStaticHeaderTablesTo} to have the model:<p>
 * 
 * <ul>
 *     <li>
 *         create and add all of the necessary tables to the specified container object
 *     </li>
 *     <li>
 *         create the data store elements backing the child questions of those tables
 *     </li>
 *     <li>
 *         create all of the {@link SetValueRuleWrapper set-value rule}(s) necessary to populate those data items
 *     </li>
 * </ul><p>
 * 
 * ...and be provided with a fully-populated {@link StaticResultsScreenHeaderControlElems} instannce containing "handles" to the supporting elements created above
 * (so allowing the caller to "wire" the {@link StaticResultsScreenHeaderControlElems#initStaticHeaderValuesRule} into the generated IFP's rules only "Start" phase).<p>
 * 
 * Internally, this class is little more than an aggregate of the constructor parameters plus a t24-line-number-indexed collection of {@link StaticResultsScreenHeaderTableSpec}(s).<p>
 * 
 * @author Simon Hayes
 */
public class StaticResultsScreenHeadersModel
{
    private final BasicEdgeMapperProject m_project;
    private final EnquiryAttrsDigest m_enquiryAttrsDigest;
    private final PropertyGroupWrapper m_workingElementsGroup;
    
    private final SortedMap<Integer,StaticResultsScreenHeaderTableSpec> m_tableSpecsByT24LineNumber = new TreeMap<Integer,StaticResultsScreenHeaderTableSpec>();
    
    public static StaticResultsScreenHeadersModel createIfNeededFor(
        BasicEdgeMapperProject p_project,
        EnquiryAttrsDigest p_enquiryAttrsDigest,
        PropertyGroupWrapper p_workingElementsGroup
    ) {
        AssertionUtils.requireNonNull(p_project, "p_project");
        AssertionUtils.requireNonNull(p_enquiryAttrsDigest, "p_enquiryAttrsDigest");
        AssertionUtils.requireNonNull(p_workingElementsGroup, "p_workingElementsGroup");
        
        final EList<EnquiryHeader> enquiryHeaders = p_enquiryAttrsDigest.getEnquiry().getHeader();
        StaticResultsScreenHeadersModel result = null;
        
        if (! ((enquiryHeaders == null) || (enquiryHeaders.isEmpty())))
        {
            StaticResultsScreenHeadersModel model = null;
            
            for (final EnquiryHeader enquiryHeader: enquiryHeaders)
            {
                if (enquiryHeader == null)
                    continue;
                
                if (model == null)
                    model = new StaticResultsScreenHeadersModel(p_project, p_enquiryAttrsDigest, p_workingElementsGroup);
                
                model.addEntryFor(enquiryHeader);
            }
            
            if (! model.isEmpty())
            {
                result = model;
            }
        }
        
        return result;
    }
    
    public StaticResultsScreenHeaderControlElems createAndAddStaticHeaderTablesTo(VisualItemContainerWrapperPair p_itemContainerWrapperPair)
    {
        AssertionUtils.requireNonNull(p_itemContainerWrapperPair, "p_itemContainerWrapperPair");
        
        StaticResultsScreenHeaderControlElems staticHeaderControlElems = null;
        
        if (! isEmpty())
        {
            final FormContext formContext = m_project.getFormContext();
            
            final RichHTMLPresentationSpacingWrapper presLineSpacingBeforeFirstStaticHeaderTable = RichHTMLPresentationSpacingWrapper.create(formContext);
            presLineSpacingBeforeFirstStaticHeaderTable.setHideField(Boolean.TRUE);
            p_itemContainerWrapperPair.presWrapperObject.addChild(presLineSpacingBeforeFirstStaticHeaderTable);
            
            staticHeaderControlElems = StaticResultsScreenHeaderControlElemsImpl.create(m_project, m_workingElementsGroup, presLineSpacingBeforeFirstStaticHeaderTable);
            int nextStaticHeaderTableNumber = 1;
            
            for(StaticResultsScreenHeaderTableSpec staticHeaderTableSpec: m_tableSpecsByT24LineNumber.values())
            {
                p_itemContainerWrapperPair.addChild(
                    staticHeaderTableSpec.generateStaticHeaderTable(
                        nextStaticHeaderTableNumber++,
                        staticHeaderControlElems,
                        m_enquiryAttrsDigest,
                        formContext
                    )
                );
            }
        }
        
        return staticHeaderControlElems;
    }

    boolean isEmpty()
    {
        return m_tableSpecsByT24LineNumber.isEmpty();
    }
    
    void addEntryFor(EnquiryHeader p_enquiryHeader)
    {
        AssertionUtils.requireNonNull(p_enquiryHeader, "p_enquiryHeader");
        
        final TextTranslations headerTranslations = TextTranslations.getLocalTranslations( m_project.getEdgeMapper(), p_enquiryHeader.getLabel(), null );
        
        // FIXME: Runtime translations
        
        final String englishStaticHeaderText = EnquiryUtils.stripEnclosingQuotes(StringUtils.trimEmptyToNull(headerTranslations.getText()));
        
        if (englishStaticHeaderText == null)
            return;

        findOrCreateHeaderTableSpecForLineNumber(p_enquiryHeader).addTableColumnQuestionSpecFor(p_enquiryHeader.getColumn(), englishStaticHeaderText);
    }
    
    private StaticResultsScreenHeaderTableSpec findOrCreateHeaderTableSpecForLineNumber(EnquiryHeader p_enquiryHeader)
    {
        AssertionUtils.requireNonNull(p_enquiryHeader, "p_enquiryHeader");
        
        final int t24LineNumber = p_enquiryHeader.getLine();
        
        StaticResultsScreenHeaderTableSpec result = m_tableSpecsByT24LineNumber.get(t24LineNumber);
        
        if (result == null)
            m_tableSpecsByT24LineNumber.put(t24LineNumber, result = new StaticResultsScreenHeaderTableSpec(t24LineNumber));

        return result;
    }
    
    private StaticResultsScreenHeadersModel(BasicEdgeMapperProject p_project, EnquiryAttrsDigest p_enquiryAttrsDigest, PropertyGroupWrapper p_workingElementsGroup)
    {
        m_project = AssertionUtils.requireNonNull(p_project, "p_project");
        m_enquiryAttrsDigest = AssertionUtils.requireNonNull(p_enquiryAttrsDigest, "p_enquiryAttrsDigest");
        m_workingElementsGroup = AssertionUtils.requireNonNull(p_workingElementsGroup, "p_workingElementsGroup");
    }
}
