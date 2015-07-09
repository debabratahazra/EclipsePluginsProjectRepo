package com.odcgroup.edge.t24.generation.enquiry.model.dsl.constants;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.enquiry.model.Appliable;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.temenos.connect.attributeHooks.ClassAttributeHook;


/**
 * Each constant of the <code>FieldDisplayType</code> enumeration represents one of the DSL-constant values for an Enquiry field's {@link Field#getDisplayType() display-type}, and
 * a type-safe way of determining whether it is the value {@link #isReferencedBy(Field) referenced} by a specified Enquiry {@link Field}.
 *
 * @author Simon Hayes
 */
public enum FieldDisplayType implements Appliable<RichHTMLPresentationQuestionWrapper>
{
    GRAPH_AS_BAR("BAR"),
    HEADER_DATA_LABEL("CLASS-ENQ.H.DATA.LABEL"),
    HEADER_DATA("CLASS-ENQ.H.DATA"),
    IMAGE("IMAGE"),
    CUSTOM_NO_RESULTS_MESSAGE("CLASS-ENQ.NORECS"),
    START_TREE("STARTTREE"),
    END_TREE("ENDDTREE");
    
    @Override
    public void applyTo(RichHTMLPresentationQuestionWrapper p_targetPresQuestion)
    {
        AssertionUtils.requireNonNull(p_targetPresQuestion, "p_targetPresQuestion");
        p_targetPresQuestion.setAttribute(ClassAttributeHook.EB_CUSTOM_ATTRIBUTE, m_dslName);
    }

    public boolean isReferencedBy(Field p_enquiryField)
    {
        AssertionUtils.requireNonNull(p_enquiryField, "p_enquiryField");
        return m_dslName.equals(p_enquiryField.getDisplayType());
    }
    
    public String getDslName()
    {
        return m_dslName;
    }
    
    private final String m_dslName;
    
    private FieldDisplayType(String p_dslName)
    {
        m_dslName = AssertionUtils.requireNonNullAndNonEmpty(p_dslName, "p_dslName");
    }
}
