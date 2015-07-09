package com.odcgroup.edge.t24.generation;

import org.eclipse.core.resources.IProject;

import com.temenos.connect.utils.SlangManager;



/**
 * Simple extension to the version mapper to create AA specific versions.
 *
 */
public class AAVersionMapper extends VersionMapper
{
    AAVersionMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
    {
        super( p_eclipseProject, p_mainOutputLocation, p_slangManager );
    }
    
//    @Override
//    protected void build(Version p_version, String p_projectName, TemplateProject p_templateProject,
//            LanguageMapHelper p_languageMapHelper) throws Exception
//    {
//        BasicVersionScreen versionScreen = new AAVersionScreen( p_projectName, p_version, this, p_templateProject, p_languageMapHelper );
//        
//        versionScreen.build();
//    }
}
