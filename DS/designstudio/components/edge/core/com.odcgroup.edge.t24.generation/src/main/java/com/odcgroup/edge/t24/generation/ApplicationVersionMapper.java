package com.odcgroup.edge.t24.generation;

import org.eclipse.core.resources.IProject;

import com.temenos.connect.utils.SlangManager;



/**
 * Simple extension to the version mapper to allow application/version specific changes.
 *
 */
public class ApplicationVersionMapper extends VersionMapper
{
    ApplicationVersionMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
    {
        super( p_eclipseProject, p_mainOutputLocation, p_slangManager );
    }
}
