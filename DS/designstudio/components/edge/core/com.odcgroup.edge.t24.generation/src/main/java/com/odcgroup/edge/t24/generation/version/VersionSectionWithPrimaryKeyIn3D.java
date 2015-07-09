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

import gen.com.acquire.intelligentforms.entities.PropertyGroupWrapper;

import com.odcgroup.edge.t24.generation.version.util.RichLayoutManager;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.t24.version.versionDSL.Version;

/**
 * TODO: Document me!
 *
 * @author sakbar
 *
 */
public class VersionSectionWithPrimaryKeyIn3D extends VersionSectionWithPrimaryKey
{
    public VersionSectionWithPrimaryKeyIn3D(AbstractBasicVersionScreen p_process, Version p_version, RichLayoutManager p_containerLayoutManager)
    {
        super( p_process, p_version, p_containerLayoutManager );
    }
    
    @Override
    protected PropertyGroupWrapper createVersionDataGroup()
    {
        PropertyGroupWrapper versionDataGroup = super.createVersionDataGroup();
        
        VersionUtility.addMDComponentModelGroup( getFormContext(), versionDataGroup );
        
        return versionDataGroup;
    }

}
