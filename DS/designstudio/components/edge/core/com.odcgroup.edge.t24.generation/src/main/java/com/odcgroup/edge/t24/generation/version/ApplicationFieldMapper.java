package com.odcgroup.edge.t24.generation.version;

import java.util.List;

import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.EdgeMapper;
import com.odcgroup.edge.t24.generation.IFieldMapper;
import com.odcgroup.edge.t24.generation.util.ApplicationUtility;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.applicationimport.schema.InputBehaviour;

/**
 * This represents a field directly from the application.
 *
 * @author sakbar
 */
public class ApplicationFieldMapper extends AbstractVersionFieldMapper
{
    
    /** The parent field mapper. */
    private final IFieldMapper<?> m_parentFieldMapper;
    
    /** The label text translations. */
    private TextTranslations m_labelTextTranslations;
    
    /** The help text translations. */
    private TextTranslations m_helpTextTranslations;

    /** The first group field based on row/col order. */
    private IVersionFieldMapper m_firstGroupField;

    /**
     * Instantiates a new application field mapper.
     *
     * @param p_parentFieldMapper the parent field mapper
     * @param p_property the property
     * @param p_forApplication the for application
     * @param p_order the order
     */
    public ApplicationFieldMapper(IFieldMapper<?> p_parentFieldMapper, MdfProperty p_property, MdfClass p_forApplication, int p_order)
    {
        super(p_property, p_forApplication, p_order);
        
        m_parentFieldMapper = p_parentFieldMapper;
    }

    /**
     * Checks if is processable.
     *
     * @return true, if is processable
     */
    @Override
    public boolean isProcessable()
    {
        if  ( isGroup() )
        {
            List<IVersionFieldMapper> groupInputFields = getGroupInputFields();
            
            for (IVersionFieldMapper groupField : groupInputFields)
            {
                if  ( groupField.isProcessable() )
                {
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public void refresh()
    {
        m_firstGroupField = null;
        
        super.refresh();
    }
    
    /**
     * Gets the first group field based on row/col order.
     *
     * @return the first group field based on row/col order
     */
    private IVersionFieldMapper getFirstGroupField()
    {
        if  ( m_firstGroupField == null && isGroup() )
        {
            List<IVersionFieldMapper> groupInputFields = getGroupInputFields();
            
            for (IVersionFieldMapper groupField : groupInputFields)
            {
                if  (   groupField.isProcessable() 
                    &&  groupField.getRow() > 0 
                    &&  groupField.getCol() > 0 
                    )
                {
                    if  ( m_firstGroupField == null )
                    {
                        m_firstGroupField = groupField;
                    }
                    else if (   m_firstGroupField.getRow() > groupField.getRow() 
                            ||  (   m_firstGroupField.getRow() == groupField.getRow() 
                                &&  m_firstGroupField.getCol() > groupField.getCol() 
                                ) 
                            )
                    {
                        m_firstGroupField = groupField;
                    }
                }
            }
        }
        
        return m_firstGroupField;
    }
    
    /**
     * Gets the label text.
     *
     * @return the label text
     */
    @Override
    public TextTranslations getLabelText(EdgeMapper<?> p_edgeMapper)
    {
        if  ( m_labelTextTranslations == null )
        {
            m_labelTextTranslations = TextTranslations.getLabelTranslations( p_edgeMapper,
                                                                             getMdfProperty(),
                                                                             null,
                                                                             StringUtils.getWords( getMdfProperty().getName().toLowerCase(), StringUtils.DEFAULT_WORD_SEPARATORS ) );
        }
        
        return m_labelTextTranslations;
    }

    /**
     * Gets the help text.
     *
     * @return the help text
     */
    @Override
    public TextTranslations getHelpText(EdgeMapper<?> p_edgeMapper)
    {
        // FIXME: USE NEW GENERATED HELP!
        //
        if  ( m_helpTextTranslations == null && getMdfProperty().getDocumentation() != null )
        {
            m_helpTextTranslations = TextTranslations.getDefaultText( getMdfProperty().getDocumentation() );
        }
        
        return m_helpTextTranslations;
    }

    /**
     * Gets the hint text.
     *
     * @return the hint text
     */
    @Override
    public TextTranslations getHintText(EdgeMapper<?> p_edgeMapper)
    {
        return getHelpText(p_edgeMapper);
    }

    /**
     * Checks if is mandatory.
     *
     * @return true, if is mandatory
     */
    @Override
    public boolean isMandatory()
    {
        return ApplicationUtility.isMandatory(getMdfProperty());
    }

    /**
     * Checks if is read only.
     *
     * @return true, if is read only
     */
    @Override
    public boolean isReadOnly()
    {
        // FIXME: Need to check this
        
        InputBehaviour inputBehaviour = T24Aspect.getInputBehaviour( getMdfProperty() );
        
        return InputBehaviour.N == inputBehaviour;
    }
    
    /**
     * Checks if is displayed.
     *
     * @return true, if is displayed
     */
    @Override
    public boolean isDisplayed()
    {
        // Versions should only display what's been explicitly specified
        //
        return false;
    }

    /**
     * Gets the max display length.
     *
     * @return the max display length
     */
    @Override
    public int getMaxDisplayLength()
    {
        // FIXME: ??
        return getMaxInputLength();
    }

    /**
     * Display on same line.
     *
     * @return true, if successful
     */
    @Override
    public boolean displayOnSameLine()
    {
        return false;
    }

    /**
     * New sub field mapper.
     *
     * @param p_parentFieldMapper the parent field mapper
     * @param p_property the property
     * @param p_order the order
     * @return the i version field mapper
     */
    @Override
    protected IVersionFieldMapper newSubFieldMapper(IVersionFieldMapper p_parentFieldMapper, MdfProperty p_property, int p_order)
    {
        return new ApplicationFieldMapper( p_parentFieldMapper, p_property, p_parentFieldMapper.getForApplication(), p_order );
    }

    /**
     * Checks for enrichment.
     *
     * @return true, if successful
     */
    @Override
    public boolean hasEnrichment()
    {
        return ApplicationUtility.isForeignKey( getMdfProperty() );
    }
    
    /**
     * Display drop down.
     *
     * @return true, if successful
     */
    @Override
    public boolean displayDropDown()
    {
        // For now always display drop downs when we have enrichments .. expand later on with other cases
        //
        return hasEnrichment();
    }

    /**
     * Gets the row.
     *
     * @return the row
     */
    @Override
    public int getRow()
    {
        IVersionFieldMapper firstGroupField = getFirstGroupField();
        
        return (firstGroupField != null) ? firstGroupField.getRow() : 0 ;
    }

    /**
     * Gets the col.
     *
     * @return the col
     */
    @Override
    public int getCol()
    {
        IVersionFieldMapper firstGroupField = getFirstGroupField();
        
        return (firstGroupField != null) ? firstGroupField.getCol() : 0 ;
    }

    /**
     * Gets the parent field mapper.
     *
     * @return the parent field mapper
     */
    public IFieldMapper<?> getParentFieldMapper()
    {
        return m_parentFieldMapper;
    }
    
    /**
     * Gets the enrichment Length
     *
     * @return the enrichment length
     */
	@Override
	public int getEnriLength() 
	{
		// TODO Auto-generated method stub
		return 0;
	}
}