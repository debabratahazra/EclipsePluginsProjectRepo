package com.odcgroup.edge.t24.generation.version;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;

import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.EdgeMapper;
import com.odcgroup.edge.t24.generation.util.ApplicationUtility;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.temenos.connect.T24Browser.utils.IRISOverrideMvGroup;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public class VersionFieldMapper extends AbstractVersionFieldMapper
{
    private static final Logger LOGGER = GenLogger.getLogger(VersionFieldMapper.class);
    private static final String ATTRNAME_POPUP_DROPDOWN = "POPUP.DROPDOWN";
    
    private final Version m_version;
    private final Field m_field;
    private TextTranslations m_labelTextTranslations;
    private TextTranslations m_hintTextTranslations;

    public VersionFieldMapper(Version p_version, Field p_field, MdfProperty p_property, int p_order)
    {
        super( p_property, p_version.getForApplication(), p_order );
        
        m_version = p_version;
        m_field = p_field;
        
        // Put some validation checks for known issues (although we should be skipping these now)
        //
        if  ( m_field.getRow() == null ) LOGGER.warn( "getRow() is NULL for \"{}\"", getName() );
        if  ( m_field.getColumn() == null ) LOGGER.warn( "getColumn() is NULL for \"{}\"", getName() );
    }

    
    @Override
    public EList<String> getAttributes()
    {
    	return m_field.getAttributes();
    }
    
    @Override
    public int getRow()
    {
        Integer row = m_field.getRow();
        return( row == null ? 0 : row.intValue() );
    }
    
    @Override
    public int getCol()
    {
        Integer col = m_field.getColumn();
        return( col == null ? 0 : col.intValue() );
    }
    
    @Override
    public TextTranslations getLabelText(EdgeMapper<?> p_edgeMapper)
    {
        if  ( m_labelTextTranslations == null )
        {
            m_labelTextTranslations = TextTranslations.getLabelTranslations( p_edgeMapper, getMdfProperty(), m_field.getLabel(), null );
        }
        
        return m_labelTextTranslations;
    }

    @Override
    public TextTranslations getHintText(EdgeMapper<?> p_edgeMapper)
    {
        if  ( m_hintTextTranslations == null )
        {
            m_hintTextTranslations = TextTranslations.getTooltipTranslations( p_edgeMapper, getMdfProperty(), m_field.getToolTip(), null );
        }
        
        return m_hintTextTranslations;
    }


    @Override
    public TextTranslations getHelpText(EdgeMapper<?> p_edgeMapper)
    {
        // Q. Is this needed now that we have external help? Get rid of interface method if not!
        //
        return getHintText( p_edgeMapper );
    }

    @Override
    public boolean hasAttribute(String p_attrName)
    {
        return VersionUtility.hasAttribute( m_field, p_attrName );
    }


    @Override
    public boolean hasEnrichment()
    {
        // Assuming for now that the enrichment flag on the version can only be used to switch on enrichment and otherwise
        // always do this for foreign keys  
        // FIXME:Enrichments set in the application routines(by setting OFS$ENRI directly) other than checkfile enrichments in application template which appears as foreign key enrichments to related files are not handled. Could be in domain or returned at runtime in the meta data?
        return VersionUtility.hasEnrichment( m_field ) || ApplicationUtility.isForeignKey( getMdfProperty() );  
    }
    
    @Override
    public boolean isEnrichmentOnly()
    {
    	return VersionUtility.hasEnrichment( m_field );
    }
    
    
    
    @Override
    public boolean hasCalendarPopupBehaviour()
    {
    	return VersionUtility.hasCalendarPopupBehaviour(m_field);
    }
    
    @Override
    public boolean displayAsVerticalRadioButtons()
    {
    	return VersionUtility.displayAsVerticalRadioButtons(m_field);
    }
    
    @Override
    public boolean displayAsComboBox()
    {
        return VersionUtility.displayAsComboBox(m_field);
    }
    
    @Override
    public boolean displayAsToggle()
    {
        return VersionUtility.displayAsToggle(m_field);    
    }
    
    @Override
    public boolean displayAsDropDownNoInput()
    {
        return VersionUtility.displayAsDropDownNoInput(m_field);    
    }
    
    @Override
    public boolean hasRecurrencePopupBehaviour()
    {
    	return VersionUtility.hasRecurrencePopupBehaviour(m_field);
    }

    @Override
    public boolean displayDropDown()
    {
        // For now always display a drop down when there is an enrichment (while we're currently interpreting the Enrichments flag as a 'switch on' only)
        // .. to expand later with other cases ..
        //
        return hasEnrichment() && ! hasAttribute(ATTRNAME_POPUP_DROPDOWN);
    }
    
    @Override
    public boolean displayPopupDropdown()
    {
    	return hasAttribute(ATTRNAME_POPUP_DROPDOWN) && ApplicationUtility.isForeignKey( getMdfProperty() );
    }
    
    @Override
    public boolean isMandatory()
    {
        return VersionUtility.isMandatory( m_field );
    }

    @Override
    public boolean isReadOnly()
    {
        // Assume for now that overrides are read-only
        //
        // FIXME: Change this when the correct logic is known
        //
        boolean isOverrideField = IRISOverrideMvGroup.OVERRIDE_NAME.equals(getProcessedName());
                
        return isOverrideField || VersionUtility.isReadOnly( m_field );
    }

    @Override
    public RichHTMLPresentationQuestionWrapper.EReadOnlyFormat getReadOnlyFormat()
    {
        return VersionUtility.getReadOnlyFormat( m_field );
    }
    
    @Override
    public boolean isDisplayed()
    {
        return VersionUtility.isDisplayed( m_field );
    }

    @Override
    public boolean displayOnSameLine()
    {
        // Display on the same line with previous input field if they both have exactly the same row number 
        // (excluding field order) and if this col is after the other col.
        //
        // NB: This relies on the fields having been sorted according to the compareTo() and
        // correctly presented with the previous field
        //
        return  (  getPreviousField() != null 
                && getRow() == getPreviousField().getRow() && getCol() > getPreviousField().getCol() 
                );
    }

    @Override
    public int getMaxDisplayLength()
    {
        // FIXME: For now we'll mis-use the MaxLength from the version for this so at least it can be modified
        // however, should enhance version at some point to get a display length for the field
        //
        return VersionUtility.getMaxLength( getField() );
    }
    
    @Override
    public int getEnriLength()
    {
    	//This method actually returns the enrichment length as defined in the version. Returns 0 or the enrichment length.
    	//FIXME: Method name could be changed to reflect it returns enrichment length perhaps?
        return VersionUtility.getFieldSize(getField());
    }

    @Override
    public boolean isProcessable()
    {
        String versionFieldName = m_field.getName();

        return( StringUtils.isNotBlank( versionFieldName ) );
    }
    
    @Override
    protected IVersionFieldMapper newSubFieldMapper(IVersionFieldMapper p_parentFieldMapper, MdfProperty p_property, int p_order)
    {
        return new ApplicationFieldMapper( p_parentFieldMapper, p_property, p_parentFieldMapper.getForApplication(), p_order );
    }
    
    @Override
    public StringBuilder addDetails(StringBuilder p_buff, DebugLevel p_debugLevel)
    {
        super.addDetails( p_buff, p_debugLevel );
        
        if  ( DebugLevel.HIGH.display( p_debugLevel ) )
        {
            p_buff.append( " Row:" ).append( getRow() );
            p_buff.append( " Col:" ).append( getCol() );
        }
        
        return p_buff;
    }

    /**
     * @return the field
     */
    public Field getField()
    {
        return m_field;
    }

    /**
     * @return the version
     */
    public Version getVersion()
    {
        return m_version;
    }
    
	@Override
	public boolean getHotField() 
	{
		return m_field.getHotField().getValue() == 1;
	}

	@Override
	public boolean getHotValidate() 
	{
		return m_field.getHotValidate().getValue() == 1;
	}

	@Override
	public boolean isMV()
	{
	    return m_field.getMV() != null && m_field.getMV().intValue() > 0;
	}
	
	@Override
	public boolean isSV()
	{
	    return m_field.getSV() != null && m_field.getSV().intValue() > 0;
	}

    @Override
    public boolean getRightAdjust()
    {
        return VersionUtility.getRightAdjust(m_field); 
    }

    @Override
    public String getSelectionEnquiry()
    {
        return m_field.getSelectionEnquiry();
    }

    @Override
    public String getEnquiryParameter()
    {
        return m_field.getEnquiryParameter();
    }
    
    @Override
    public boolean isExpandable()
    {
        return VersionUtility.isExpandable( m_field );
    }
    
    @Override
    public boolean isReKeyRequired() 
    {
    	return VersionUtility.isReKeyRequired( m_field );
    }
}
