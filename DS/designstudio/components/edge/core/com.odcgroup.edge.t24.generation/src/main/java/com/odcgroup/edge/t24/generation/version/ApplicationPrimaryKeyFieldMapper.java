package com.odcgroup.edge.t24.generation.version;

import com.odcgroup.edge.t24.generation.EdgeMapper;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public class ApplicationPrimaryKeyFieldMapper extends AbstractVersionFieldMapper
{
    private TextTranslations m_labelTextTranslations;
    private TextTranslations m_hintTextTranslations;

    protected ApplicationPrimaryKeyFieldMapper(MdfProperty p_property, MdfClass p_forApplication, int p_order)
    {
        super( p_property, p_forApplication, p_order );
    }

    @Override
    public boolean displayOnSameLine()
    {
        return false;
    }

    @Override
    public int getRow()
    {
        return 0;
    }

    @Override
    public int getCol()
    {
        return 0;
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
    public TextTranslations getLabelText(EdgeMapper<?> p_edgeMapper)
    {
        if  ( m_labelTextTranslations == null )
        {
            m_labelTextTranslations = TextTranslations.getLabelTranslations( p_edgeMapper, getMdfProperty(), null, getName() );
        }
        
        return m_labelTextTranslations;
    }

    @Override
    public TextTranslations getHelpText(EdgeMapper<?> p_edgeMapper)
    {
        // FIXME: get help from domain, do translations
        //
        return getHintText(p_edgeMapper);
    }

    @Override
    public TextTranslations getHintText(EdgeMapper<?> p_edgeMapper)
    {
        if  ( m_hintTextTranslations == null )
        {
            m_hintTextTranslations = TextTranslations.getTooltipTranslations( p_edgeMapper, getMdfProperty(), null, getName() );
        }
        
        return m_hintTextTranslations;
    }

    @Override
    public boolean isMandatory()
    {
        return true;
    }

    @Override
    public boolean isReadOnly()
    {
        return true;
    }

    @Override
    public boolean isProcessable()
    {
        return true;
    }

    @Override
    public boolean isDisplayed()
    {
        return false;
    }

    @Override
    public int getMaxDisplayLength()
    {
        return 0;
    }

    @Override
    protected IVersionFieldMapper newSubFieldMapper(IVersionFieldMapper p_parentFieldMapper, MdfProperty p_property, int p_order)
    {
        throw new UnsupportedOperationException("Primary key's should not have sub fields");
    }

	@Override
	public int getEnriLength() 
	{
		return 0;
	}
}
