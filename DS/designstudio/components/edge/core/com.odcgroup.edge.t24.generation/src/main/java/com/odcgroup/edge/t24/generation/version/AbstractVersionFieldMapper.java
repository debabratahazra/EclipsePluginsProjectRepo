package com.odcgroup.edge.t24.generation.version;

import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationQuestionWrapper.EReadOnlyFormat;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;

import com.odcgroup.edge.t24.generation.AbstractFieldMapper;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;

/**
 * This class implements the methods needed to generate the field for a basic version screen.<p>
 * 
 * Generally this should implement all methods in IVersionFieldMapper with default behaviour (usually "nothing", i.e. false/null etc, for methods that rely on an underlying version),
 * with VersionFieldMapper overriding with "real" implementations.<p>
 * 
 * However, all other sub-classes should be evaluated to determine whether they need some other behaviour.<p>
 *
 * @author saleem.akbar
 *
 */
public abstract class AbstractVersionFieldMapper extends AbstractFieldMapper<IVersionFieldMapper> implements IVersionFieldMapper
{
    private static final Logger LOGGER = GenLogger.getLogger(AbstractVersionFieldMapper.class);
    
    private List<IVersionFieldMapper> m_groupInputFields;

    protected AbstractVersionFieldMapper(MdfProperty p_property, MdfClass p_forApplication, int p_order)
    {
        super(p_property, p_forApplication, p_order);
    }
    
	@Override
	public boolean displayPopupDropdown() 
	{
		return false;
	}
	
	@Override
	public boolean hasAttribute(String p_attrName) 
	{
		return false;
	}
	
	@Override
	public boolean hasCalendarPopupBehaviour()
	{
		return false;
	}
	
	@Override
	public boolean hasRecurrencePopupBehaviour()
	{
		return false;
	}

	@Override
	public MdfClass getType()
	{
	    return (MdfClass) getMdfProperty().getType();
	}
	
	@Override
    public List<IVersionFieldMapper> getGroupInputFields()
    {
        if  ( m_groupInputFields == null )
        {
            // Allow class cast exception to be thrown if we don't have an MdfClass as expected
            //
            MdfClass type = getType();
            
            m_groupInputFields = new ArrayList<IVersionFieldMapper>();
            
            int order = 0;
            
            for (Object property : type.getProperties())
            {
                if  ( property instanceof MdfProperty )
                {
                    m_groupInputFields.add( newSubFieldMapper(this, (MdfProperty) property, order++ ));
                }
                else
                {
                    LOGGER.error("Expected an MdfProperty, instead have \"{}\" for property of \"{}\"", new Object[] { property, getName() });
                }
            }
            
            refresh();
        }

        return m_groupInputFields;
    }

    public void refresh()
    {
        if  ( m_groupInputFields != null )
        {
            MapperUtility.sortMappedFields( m_groupInputFields );
        }
    }

    protected abstract IVersionFieldMapper newSubFieldMapper(IVersionFieldMapper p_parentFieldMapper, MdfProperty p_property, int p_order );

    @Override
    public String getT24Name()
    {
        return T24Aspect.getT24Name( getMdfProperty() );
    }

    @Override
    public String getBusinessType() 
    {
    	MdfProperty mdfProperty = getMdfProperty();
    	MdfEntity mdfEntity = mdfProperty.getType();
    	String businessType;
    	if(mdfEntity instanceof MdfBusinessType)
    	{
    		businessType = mdfEntity.getName() ;
    	}
    	else if(mdfProperty instanceof MdfAttribute || mdfProperty instanceof MdfAssociation)
    	{	
    	 	businessType = T24Aspect.getBusinessType(mdfProperty);    		
    	}
    	else 
    	{
    		throw new IllegalArgumentException("Bug in model or code, unclear how to obtain BT from: " + mdfProperty.toString());
    	}
    	return ( businessType == null ) ? "" : businessType;
    }
    
    @Override
    public String getTypeModifiers()
    {
        String typeModifier =T24Aspect.getTypeModifiers(getMdfProperty());
        return ( typeModifier == null ) ? "" : typeModifier;
    }
    
    @Override
    public int compareTo(IVersionFieldMapper p_otherField)
    {
        return VersionUtility.compare( this, p_otherField );
    }

    @Override
    public int getGroupMultiplicity()
    {
        return  VersionUtility.<IVersionFieldMapper>isMultiLanguageGroupOrField(this) ? 25 : super.getGroupMultiplicity(); 
    }
    
    @Override
    public EReadOnlyFormat getReadOnlyFormat()
    {
        return RichHTMLPresentationQuestionWrapper.EReadOnlyFormat.USE_DEFAULT;
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
	public EList<String> getAttributes()
	{
	    return null;
	}
	
	@Override
	public boolean isExpandable()
	{
	    return true;
	}
	
	@Override
	public EFieldType getFieldType()
	{
	    EFieldType fieldType = super.getFieldType();
	    
	    // Workaround for #1132 where a list also has a selection enquiry, make the type into a string instead
	    // so at least the drop down enquiry works and any validation
	    //
	    if ( fieldType == EFieldType.LIST && getSelectionEnquiry() != null )
	    {
	        return EFieldType.STRING;
	    }
	    
        return fieldType;
	}
	
	@Override
	public boolean isReKeyRequired() 
	{
		return false;
	}
}
