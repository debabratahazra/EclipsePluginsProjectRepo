/*
 * $RCSfile$
 * $Author$
 * $Revision$
 * $Date$
 *
 * Copyright (c) 2001-2015 TEMENOS HEADQUARTERS SA. All rights reserved.
 *
 * This source code is protected by copyright laws and international copyright treaties,
 * as well as other intellectual property laws and treaties.
 *  
 * Access to, alteration, duplication or redistribution of this source code in any form 
 * is not permitted without the prior written authorisation of TEMENOS HEADQUARTERS SA.
 * 
 */

package com.odcgroup.edge.t24.generation;

import gen.com.acquire.intelligentforms.entities.ProjectWrapper;

import com.acquire.util.AssertionUtils;
import com.edgeipk.builder.pattern.container.BasicProject;

/**
 * TODO: Document me!
 *
 * @author sakbar
 *
 */
public class BasicEdgeMapperProject extends BasicProject
{
    private final EdgeMapper<?> m_edgeMapper;

    protected BasicEdgeMapperProject(ProjectWrapper p_project, EdgeMapper<?> p_edgeMapper)
    {
        super( AssertionUtils.requireNonNull( p_project, "p_project" ));
        
        m_edgeMapper = AssertionUtils.requireNonNull(p_edgeMapper, "p_edgeMapper");
    }

    public EdgeMapper<?> getEdgeMapper()
    {
        return m_edgeMapper;
    }
}
