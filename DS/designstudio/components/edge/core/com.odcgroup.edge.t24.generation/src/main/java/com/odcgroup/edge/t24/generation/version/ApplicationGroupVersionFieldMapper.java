/*
 * $RCSfile$
 * $Author$
 * $Revision$
 * $Date$
 *
 * Copyright (c) 2001-2014 TEMENOS HEADQUARTERS SA. All rights reserved.
 *
 * This source code is protected by copyright laws and international copyright treaties,
 * as well as other intellectual property laws and treaties.
 *  
 * Access to, alteration, duplication or redistribution of this source code in any form 
 * is not permitted without the prior written authorisation of TEMENOS HEADQUARTERS SA.
 * 
 */

package com.odcgroup.edge.t24.generation.version;

import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.EdgeMapper;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.version.versionDSL.Field;

/**
 * This class represents a container of fields for a group.<p> 
 * 
 * This is not displayable, only its contained fields linked from the first field maybe displayed. 
 *
 * @author sakbar
 *
 */
public class ApplicationGroupVersionFieldMapper extends AbstractVersionFieldMapper
{
    private final Field m_firstMappedGroupField;
    private final MdfClass m_topClass;

    public ApplicationGroupVersionFieldMapper(MdfProperty p_firstMappedProperty, MdfClass p_topClass, MdfClass p_forApplication, Field p_firstMappedGroupField)
    {
        super( p_firstMappedProperty, p_forApplication, -1 );
        m_topClass = p_topClass;
        m_firstMappedGroupField = p_firstMappedGroupField;
    }

    @Override
    public String getT24Name()
    {
        String topClassName = m_topClass.getName();
        
        return StringUtils.getAfterFirst( topClassName, "__", false );
    }
    
    @Override
    public String getName()
    {
        return getT24Name();
    }
    
    @Override
    public String getProcessedName()
    {
        return MapperUtility.processT24NameToIRISName( getT24Name() );
    }
    
    @Override
    public MdfClass getType()
    {
        return m_topClass;
    }
    
    @Override
    public boolean displayOnSameLine()
    {
        return false;
    }

    @Override
    public int getRow()
    {
        return m_firstMappedGroupField.getRow();
    }

    @Override
    public int getCol()
    {
        return m_firstMappedGroupField.getColumn();
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
        return null;
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
        return new ApplicationFieldMapper( p_parentFieldMapper, p_property, p_parentFieldMapper.getForApplication(), p_order );
    }

    @Override
    public boolean isMV()
    {
        return true;
    }
    
    @Override
    public boolean isSV()
    {
        return getMdfProperty().getParentClass() != m_topClass;
    }
    
    @Override
    public boolean isField()
    {
        return false;
    }
    
    @Override
    public boolean isGroup()
    {
        return true;
    }

	@Override
	public int getEnriLength() 
	{
		return 0;
	}
}
