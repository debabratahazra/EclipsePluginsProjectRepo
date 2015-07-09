package com.odcgroup.edge.t24.generation.util;

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;
import gen.com.acquire.intelligentforms.entities.PropertyWrapper;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.types.TextType;
import com.temenos.connect.T24Browser.BrowserUtility;
import com.temenos.connect.T24Browser.utils.IRISMvGroup;
import com.temenos.connect.T24Browser.utils.IRISMvGroup.EGroupType;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public class IRISMvPropertyGroupWrapper extends PropertyGroupWrapper
{
    private final PropertyGroupWrapper m_elementGroup;

    protected IRISMvPropertyGroupWrapper(PropertyGroupWrapper p_mvGroup, PropertyGroupWrapper p_elementGroup )
    {
        super( p_mvGroup.unwrap() );
        
        m_elementGroup = p_elementGroup;
    }

    public static IRISMvPropertyGroupWrapper create(FormContext p_formContext, String p_baseGroupName, String p_t24Name, String p_appName, EGroupType p_groupType)
    {
        String groupName = p_groupType.getGroupName( p_baseGroupName );
        
        PropertyGroupWrapper mvGroup = PropertyGroupWrapper.create( p_formContext, groupName ) ;
        
        BrowserUtility.setBasicBrowserAttributes( mvGroup.unwrap(), p_t24Name, p_appName );
        
        BrowserUtility.setIrisGroupType( mvGroup.unwrap(), p_groupType );
        
        // Add element sub group
        //
        PropertyGroupWrapper elementGroup = PropertyGroupWrapper.create( p_formContext, IRISMvGroup.ELEMENT_GROUP_NAME );

        elementGroup.setFixedSize( Boolean.FALSE );
        
        mvGroup.addChild( elementGroup );
        
        // Create and add the "valuePosition" Data Item.
        //
        PropertyWrapper valuePosition = PropertyWrapper.create( p_formContext );

        valuePosition.setName( IRISMvGroup.MV_VALUE_POSITION );

        valuePosition.setType( TextType.TYPE );

        elementGroup.addChild( valuePosition );
        
        // Create the "subValuePosition" Data Item.
        //
        PropertyWrapper subValuePosition = PropertyWrapper.create( p_formContext );

        subValuePosition.setName( IRISMvGroup.SUB_VALUE_POSITION );

        subValuePosition.setType( TextType.TYPE );
        
        elementGroup.addChild( subValuePosition );
        
        return(new IRISMvPropertyGroupWrapper(mvGroup, elementGroup));
    }

    /**
     * @return the elementGroup
     */
    public PropertyGroupWrapper getElementGroup()
    {
        return m_elementGroup;
    }
}
