package com.odcgroup.edge.t24.generation.version.util;

import java.util.List;

import com.odcgroup.edge.t24.generation.version.IVersionFieldMapper;

/**
 * RichHTML version of the layout manager.
 */
public class RichFieldLayoutManager extends RichLayoutManager 
{
    
    /** The current field. */
    private IVersionFieldMapper m_currentField;
    
    /** The fields. */
    private final List<IVersionFieldMapper> m_fields;

    /**
     * Instantiates a new rich field layout manager.
     *
     * @param p_formContext the form context
     * @param p_fields the fields
     * @param p_presentationContainer the presentation container
     * @param p_languageMapHelper the language map helper
     */
    public RichFieldLayoutManager( RichLayoutManager p_containerLayout, List<IVersionFieldMapper> p_fields)
    {
        super( p_containerLayout.getFormContext(), p_containerLayout.getCurrentProcessContainer(), p_containerLayout.getCurrentPresentationContainer(), p_containerLayout.getLanguageMapHelper() );
        
        m_fields = p_fields;
    }
    
    /**
     * Sets the current field.
     *
     * @param p_field the new current field
     */
    public void setCurrentField(IVersionFieldMapper p_field)
    {
        m_currentField = p_field;
    }

    /**
     * Gets the current field.
     *
     * @return the current field
     */
    public IVersionFieldMapper getCurrentField()
    {
        return m_currentField;
    }

    /**
     * Gets the fields.
     *
     * @return the fields
     */
    public List<IVersionFieldMapper> getFields()
    {
        return m_fields;
    }
    
    /**
     * If possible, expand the space allowed for the field
     */
    public void expandFieldSpace()
    {
        // Default do nothing
    }
}
