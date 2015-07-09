package com.odcgroup.edge.t24.generation.version;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper.EReadOnlyFormat;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.acquire.util.IDebuggable;
import com.odcgroup.edge.t24.generation.EdgeMapper;
import com.odcgroup.edge.t24.generation.IFieldMapper;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * Field mapper to handle the "*" field.
 *
 * @author sakbar
 *
 */
public class AsteriskFieldMapper implements IVersionFieldMapper
{
    public static final String ASTERISK_FIELD_NAME = "*";
    
    private final Version       m_version;
    private final Field         m_field;
    private final int           m_order;
    private IVersionFieldMapper m_previousField;
    private TextTranslations    m_labelTextTranslations;

    public AsteriskFieldMapper(Version p_version, Field p_field, int p_order)
    {
        m_version = p_version;
        m_field = p_field;
        m_order = p_order;
    }

    @Override
    public EList<String> getAttributes()
    {
    	return m_field.getAttributes();
    }
    
    @Override
    public String getName()
    {
        return ASTERISK_FIELD_NAME;
    }

    @Override
    public String getProcessedName()
    {
        return "Special Asterisk Field";
    }
    
    @Override
    public String getQualifiedName()
    {
        return getProcessedName();
    }

    @Override
    public TextTranslations getLabelText(EdgeMapper<?> p_edgeMapper)
    {
        if  ( m_labelTextTranslations == null  )
        {
            m_labelTextTranslations = TextTranslations.getLocalTranslations( p_edgeMapper, m_field.getLabel(), null );
        }
        
        return m_labelTextTranslations;
    }

    @Override
    public TextTranslations getHelpText(EdgeMapper<?> p_edgeMapper)
    {
        return null;
    }

    @Override
    public TextTranslations getHintText(EdgeMapper<?> p_edgeMapper)
    {
        return null;
    }

    @Override
    public boolean isMandatory()
    {
        return false;
    }

    @Override
    public boolean isReadOnly()
    {
        return false;
    }

    @Override
    public EReadOnlyFormat getReadOnlyFormat()
    {
        return RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.USE_DEFAULT;
    }

    @Override
    public boolean isProcessable()
    {
        return true;
    }

    @Override
    public boolean isDisplayed()
    {
        // Now  handle as part of the displayed fields, so relative positions can be used
        // as we no longer create sections around
        //
        return true;
    }

    @Override
    public int getMaxInputLength()
    {
        return 0;
    }

    @Override
    public int getMaxDisplayLength()
    {
        return 0;
    }

    @Override
    public int getOrder()
    {
        return m_order;
    }

    @Override
    public MdfProperty getMdfProperty()
    {
        return null;
    }

    @Override
    public MdfClass getForApplication()
    {
        return null;
    }

    @Override
    public String getBusinessType()
    {
        return null;
    }

    @Override
    public String getTypeModifiers()
    {
        return null;
    }
    
    @Override
    public int compareTo(IVersionFieldMapper p_field)
    {
        return VersionUtility.compare(this, p_field);
    }

    @Override
    public boolean isField()
    {
        return false;
    }

    @Override
    public boolean isGroup()
    {
        return false;
    }

    @Override
    public int getGroupMultiplicity()
    {
        return 0;
    }

    @Override
    public List<IVersionFieldMapper> getGroupInputFields()
    {
        return null;
    }

    @Override
    public IVersionFieldMapper getPreviousField()
    {
        return m_previousField;
    }

    @Override
    public void setPreviousField(IVersionFieldMapper p_previousField)
    {
        m_previousField = p_previousField;
    }

    @Override
    public IFieldMapper.EMappingType getMappingType()
    {
        return IFieldMapper.EMappingType.UNKNOWN;
    }

    @Override
    public String getFieldTypeName()
    {
        return null;
    }

    @Override
    public IFieldMapper.EFieldType getFieldType()
    {
        return EFieldType.ASTERISK;
    }

    /* (non-Javadoc)
     * @see com.acquire.util.IDebuggable#getDetails()
     */
    @Override
    public String getDetails()
    {
        return IDebuggable.Default.getDetails(this);
    }
    
    /* (non-Javadoc)
     * @see com.acquire.util.IDebuggable#getDetails(com.acquire.util.IDebuggable.DebugLevel)
     */
    @Override
    public String getDetails(DebugLevel p_debugLevel)
    {
        return IDebuggable.Default.getDetails(this, p_debugLevel);
    }
    
    @Override
    public StringBuilder addDetails(StringBuilder p_stringBuilder, DebugLevel p_debugLevel)
    {
        p_stringBuilder.append( "Asterisk Field from Version/Field Number: " ).append(m_version.getT24Name()).append("->").append(m_order);
        
        return p_stringBuilder;
    }

    @Override
    public boolean displayOnSameLine()
    {
        return false;
    }

    @Override
    public int getRow()
    {
        return m_field.getRow() != null ? m_field.getRow() : 0;
    }

    @Override
    public int getCol()
    {
        return m_field.getColumn() != null ? m_field.getColumn() : 0;
    }

    @Override
    public boolean hasAttribute(String p_attrName)
    {
        return VersionUtility.hasAttribute( m_field, p_attrName );
    }

    @Override
    public boolean hasEnrichment()
    {
        return false;
    }

    @Override
    public boolean displayDropDown()
    {
        return false;
    }

    @Override
    public boolean displayPopupDropdown()
    {
        return false;
    }
    
    @Override
    public boolean hasRecurrencePopupBehaviour()
    {
    	return false;
    }
    
    @Override
    public boolean hasCalendarPopupBehaviour()
    {
    	return false;
    }

    @Override
    public String getT24Name()
    {
        return "AS.TER.ISK";
    }

	@Override
	public boolean getHotField() 
	{
		return false;
	}

	@Override
	public boolean getHotValidate() 
	{
		return false;
	}

	@Override
	public boolean isMV()
	{
	    return false;
	}
	
	@Override
	public boolean isSV()
	{
	    return false;
	}
	
	@Override
	public MdfClass getType()
	{
	    return (MdfClass) getMdfProperty().getType();
	}

    @Override
    public boolean getRightAdjust()
    {
        return false;
    }
    
    @Override
    public String getSelectionEnquiry()
    {
        return null;
    }

    @Override
    public String getEnquiryParameter()
    {
        return null;
    }
    
    @Override
    public boolean isEnrichmentOnly()
    {
    	return false; 
    }

	@Override
	public boolean displayAsVerticalRadioButtons() 
	{		
		return false;
	}

    @Override
    public boolean displayAsComboBox()
    {
        return false;
    }

    @Override
    public boolean displayAsToggle()
    {
        return false;
    }
    
    @Override
    public boolean displayAsDropDownNoInput()
    {
        return false;
    }

    @Override
    public boolean isExpandable()
    {
        return false;
    }

	@Override
	public int getEnriLength() 
	{
		return 0;
	}
	
	@Override
	public boolean isReKeyRequired() 
	{
		return false;
	}
}
