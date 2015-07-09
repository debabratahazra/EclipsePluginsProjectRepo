package com.odcgroup.edge.t24.generation;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.slf4j.Logger;

import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.ComponentMap;
import com.odcgroup.edge.t24.generation.util.ComponentMap.ComponentType;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.version.BasicVersionScreen;
import com.odcgroup.edge.t24.generation.version.BasicVersionScreenIn3D;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.t24.version.versionDSL.Version;
import com.temenos.connect.odata.utils.VersionServiceData;
import com.temenos.connect.utils.SlangManager;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public class VersionMapper extends ProjectMapper<Version>
{
    private static final Logger LOGGER = GenLogger.getLogger(VersionMapper.class);

    VersionMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
    {
        super( p_eclipseProject, p_mainOutputLocation, p_slangManager );
    }

    @Override
    protected void build(Version p_version, String p_projectName, TemplateProject p_templateProject, LanguageMapHelper p_languageMapHelper) throws Exception
    {
        BasicVersionScreen versionScreen = new BasicVersionScreenIn3D( p_projectName, p_version, this, p_templateProject, p_languageMapHelper );
        
        versionScreen.build();
    }

    @Override
    protected String getProjectName(Version p_version)
    {
        return VersionUtility.getVersionName( p_version );
    }

    @Override
    protected String getSubOutputDir(String p_versionName)
    {
        return p_versionName;
    }
    
    @Override
    protected ComponentType getComponentType()
    {
        return ComponentType.VERSION;
    }

    @Override
    protected String getComponentName(String p_versionName)
    {
        return p_versionName;
    }
    
    @Override
    protected TemplateProject createTemplateProject()
    {
        return BasicVersionScreenIn3D.getDefaultBasicVersionTemplate();
    }
    
    @Override
    protected void storeMapperDetails(ComponentMap p_componentMap, EdgeMapper.Location p_location, ComponentType p_componentType,
            List<String> p_mapperData)
    {
        super.storeMapperDetails( p_componentMap, p_location, p_componentType, p_mapperData );
        
        boolean foundKey = false;
        
        for (String data : p_mapperData)
        {
            if  ( data.startsWith( VersionServiceData.MD_PRIMARY_KEY_NAME ) )
            {
                foundKey = true;
                break;
            }
        }
        
        if  ( ! foundKey )
        {
            LOGGER.error( "Unable to locate primary key ({}) in mapper data", VersionServiceData.MD_PRIMARY_KEY_NAME );
        }
    }
}
