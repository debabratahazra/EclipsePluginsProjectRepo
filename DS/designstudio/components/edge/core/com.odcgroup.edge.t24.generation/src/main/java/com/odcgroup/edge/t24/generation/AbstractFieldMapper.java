package com.odcgroup.edge.t24.generation;

import org.slf4j.Logger;

import com.acquire.util.IDebuggable;
import com.odcgroup.edge.t24.generation.util.ApplicationUtility;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public abstract class AbstractFieldMapper<T> implements IFieldMapper<T>
{
    private static final Logger LOGGER = GenLogger.getLogger(AbstractFieldMapper.class);
    
    private T                   m_previousField;
    private final MdfProperty   m_mdfProperty;
    private final int           m_order;
    private final String        m_name;
    private final String        m_processedName;
    private final MdfClass      m_forApplication;
    private EFieldType          m_fieldType;

    public AbstractFieldMapper(MdfProperty p_property, MdfClass p_forApplication, int p_order)
    {
        if  ( p_property == null ) throw new IllegalArgumentException("Application property is null");

        m_mdfProperty = p_property;
        m_order = p_order;
        m_name = p_property.getName();
        m_processedName = MapperUtility.processT24NameToIRISName( m_name );
        m_forApplication = p_forApplication;
    }

    @Override
    public void setPreviousField(T p_previousField)
    {
        m_previousField = p_previousField;
    }
    
    @Override
    public T getPreviousField()
    {
        return m_previousField;
    }

    @Override
    public String getName()
    {
        return m_name;
    }

    @Override
    public String getProcessedName()
    {
        return m_processedName;
    }

    @Override
    public int getOrder()
    {
        return m_order;
    }

    @Override
    public MdfProperty getMdfProperty()
    {
        return m_mdfProperty;
    }

    @Override
    public String getFieldTypeName()
    {
        return m_mdfProperty.getType().getName();
    }

    @Override
    public EFieldType getFieldType()
    {
        if  ( m_fieldType == null && isField() )
        {
            String fieldTypeName = getFieldTypeName();
            String businessType  = getBusinessType();
        
            // For local refs to a date, the fieldTypeName becomes "D" and businessType is blank
            //
            if  ( "date".equalsIgnoreCase( fieldTypeName ) || "D".equalsIgnoreCase( businessType ) || "DDMM".equalsIgnoreCase( businessType )|| "FOREXD".equalsIgnoreCase( businessType ) ||"MATLMM".equalsIgnoreCase( businessType ) ||"NDDATE".equalsIgnoreCase( businessType ) || "D".equalsIgnoreCase( fieldTypeName ) )
            {
                m_fieldType = EFieldType.DATE;
            }
            else if  ( "datetime".equalsIgnoreCase( fieldTypeName ) || "RELTIME".equalsIgnoreCase( businessType ) )
            {
                m_fieldType = EFieldType.DATE_TIME;
            }
            else if ( "integer".equalsIgnoreCase( fieldTypeName ) )
            {
    			// Fix for 1134 - Treat integer type as text as they could have been decimal anyway, but the introspection doesn't say so at the moment,
    			// so for practical reasons, treat as text and let T24 handle it.
            	//
            	// NB: Also in current browser, apparently you can say "1T" to indicate 1 thousand!
                //
                m_fieldType = EFieldType.STRING; // EFieldType.INTEGER;
            }
            else if ( "double".equalsIgnoreCase( fieldTypeName ) )
            {
                m_fieldType = EFieldType.DOUBLE;
            }
            else if ( ("FQU".equals( businessType ) || "FQO".equals(businessType) ) )
            {
                // Check for frequency BEFORE string as its underlying type is "string"
                //
                m_fieldType = EFieldType.FREQUENCY;
            }
            else if  ( m_mdfProperty.getType() instanceof MdfEnumeration )
            {
                // Check enumeration after date fields, as date fields seem to be "dummy" enumerations .. presumably an introspection bug?
                //
                //  e.g. CUSTOMER__DATE_OF_BIRTH : mml:string
                //         n1000=1000
                //
                m_fieldType = EFieldType.LIST;
            }
            else if ( T24Aspect.isMultiLanguage( m_mdfProperty ) )
            {
            	m_fieldType = EFieldType.MULTI_LANGUAGE_GROUP;
            }
            else if ( VersionUtility.isTextArea( m_mdfProperty ) ) 
            {
                m_fieldType = EFieldType.TEXT_AREA_GROUP;
            }
            else // Assume we're dealing with strings .. will handle exceptions as they're discovered
            {
                m_fieldType = EFieldType.STRING;
            }
        }
        
        return m_fieldType;
     }
    
    
    @Override
    public IFieldMapper.EMappingType getMappingType()
    {
        if ( getMdfProperty() instanceof MdfAttribute )
        {
            return(EMappingType.SINGLE_INPUT_FIELD);
        }
        else if ( getMdfProperty() instanceof MdfAssociation )
        {
            MdfAssociation association = (MdfAssociation) getMdfProperty();
    
            if  ( association.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE )
            {
                return(EMappingType.SINGLE_INPUT_FIELD);
            }
            else if  ( association.getContainment() == MdfConstants.CONTAINMENT_BYVALUE )
            {
                return(EMappingType.GROUP_OF_INPUT_FIELDS);
            }
            else
            {
                LOGGER.error( "Unknown containment setting of \"{}\" for \"{}\"", association.getContainment(), getName() );
                return(EMappingType.UNKNOWN);
            }
        }
        else if ( getMdfProperty() == null )
        {
            LOGGER.error( "Could not get application property for \"{}\"", getName() );
            return(EMappingType.UNKNOWN);
        }
        else
        {
            LOGGER.error( "Unknown application property type of \"{}\" for \"{}\"", getMdfProperty().getClass().getName(), getName() );
            return(EMappingType.UNKNOWN);
        }    
    }

    @Override
    public int getGroupMultiplicity()
    {
        return ( getMdfProperty().getMultiplicity() == MdfConstants.MULTIPLICITY_MANY ) ? -1 : 1; 
    }

    @Override
    public MdfClass getForApplication()
    {
        return m_forApplication;
    }

    @Override
    public boolean isField()
    {
        return getMappingType() == IFieldMapper.EMappingType.SINGLE_INPUT_FIELD;
    }

    @Override
    public boolean isGroup()
    {
        return getMappingType() == IFieldMapper.EMappingType.GROUP_OF_INPUT_FIELDS;
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
    public StringBuilder addDetails(StringBuilder p_buff, DebugLevel p_debugLevel)
    {
        p_buff.append(getName()).append(" Order: ").append(getOrder());
        
        if  ( DebugLevel.HIGH.display( p_debugLevel ) )
        {
            p_buff.append(" Prop: ").append(getMdfProperty());
        }
        
        return p_buff;
    }
    
    @Override
    public int getMaxInputLength()
    {
        return ApplicationUtility.getMaxInputLength(getMdfProperty());
    }

    @Override
    public String getQualifiedName()
    {
        return m_mdfProperty.getQualifiedName().getQualifiedName();
    }
}
