package com.odcgroup.edge.t24.generation;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.slf4j.Logger;

import com.acquire.intelligentforms.entities.Project;
import com.edgeipk.builder.templates.TemplateProject;
import com.odcgroup.edge.t24.generation.languagemaps.LanguageMapHelper;
import com.odcgroup.edge.t24.generation.util.ComponentMap;
import com.odcgroup.edge.t24.generation.util.ComponentMap.ComponentType;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.connect.utils.SlangManager;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public abstract class ProjectMapper<T> extends EdgeMapper<T>
{
    private static final Logger LOGGER = GenLogger.getLogger(ProjectMapper.class);

    ProjectMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
    {
        super( p_eclipseProject, p_mainOutputLocation, p_slangManager );
    }
    
    @Override
    public Location getLocation(T p_object) 
    {
        String projectName = getProjectName(p_object);
        
        String subDir = getSubOutputDir(projectName);
        
        return new Location( projectName, subDir, projectName + Project.PROJECT_FILE_EXTENSION, getComponentName( projectName ) );
    }
    
    @Override
    public void map(T p_object, ComponentMap p_componentMap, ModelLoader p_modelLoader, Location p_location, String p_modelName) throws Exception
    {
        LOGGER.debug("Loading Template for {}", p_object );
        
        TemplateProject templateProject = createTemplateProject();

        LOGGER.info( "Mapping {}", p_location.getResourceName() );

        // Create the connect project from the template project
        //
        setConnectProject( new ConnectProject( templateProject.getFormContext() ), p_modelName );
        
        // Setup the language map helper which will create any lang maps into the library folder of the component 
        //
        final File componentDir = new File( p_location.getFullProjectDir() );
        LanguageMapHelper languageMapHelper = new LanguageMapHelper(templateProject, componentDir);

        // Now create & build the screen
        //
        build( p_object, p_location.getResourceName(), templateProject, languageMapHelper );
        
        // FIXME: Update the design properties (WILL NOT WORK YET .. until we have access to the solution)
        //
//        PresentationRoot presRoot = m_templateProject.getFormContext().getProject().getPresentationTypes();
//        for( Iterator<?> iter = presRoot.getChildrenList().iterator(); iter.hasNext(); )
//        {
//            PresentationType presType = (PresentationType)iter.next();
//            DesignUtility.setupDesignAttributes( presType );
//        }
        
        // Now add it to the component map
        // 
        storeMapperDetails(p_componentMap, p_location, getComponentType(), getMapperData());
    }


    protected abstract void build(T p_object, String p_projectName, TemplateProject p_templateProject, LanguageMapHelper p_languageMapHelper) throws Exception;

    protected abstract String getProjectName(T p_object);

    protected abstract String getSubOutputDir(String p_versionName);
    
    protected abstract ComponentType getComponentType();
    
    protected abstract String getComponentName(String p_versionName);
  
    protected abstract TemplateProject createTemplateProject();
}
