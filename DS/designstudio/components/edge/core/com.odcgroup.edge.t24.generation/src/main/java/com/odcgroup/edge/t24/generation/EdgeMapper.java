package com.odcgroup.edge.t24.generation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.slf4j.Logger;

import com.edgeipk.builder.util.ProjectUtility;
import com.odcgroup.edge.t24.generation.util.ComponentMap;
import com.odcgroup.edge.t24.generation.util.ComponentMap.ComponentType;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.connect.T24Browser.BrowserUtility;
import com.temenos.connect.utils.SlangManager;

/**
 * Mapper from introspected DS T24 model to Edge project.
 *
 * @param <T> the generic type
 * @author Michael Vorburger, for Simon File.
 */
public abstract class EdgeMapper <T>
{
    private static final Logger       LOGGER             = GenLogger.getLogger( EdgeMapper.class );

    private ConnectProject            m_connectProject;

    private final MainOutputLocation  m_mainOutputLocation;

    private final List<String>        m_mapperData       = new ArrayList<String>();

    private final IProject            m_eclipseProject;

    private final ITranslationManager m_translationManager;

    private final SlangManager        m_slangManager;
    
    /**
     * Instantiates a new edge mapper.
     * 
     * @param p_eclipseProject
     * @param p_mainOutputLocation
     * @param p_slangManager
     */
    public EdgeMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
    {
        super();
        m_eclipseProject     = p_eclipseProject;
        m_mainOutputLocation = p_mainOutputLocation;
        m_translationManager = TranslationCore.getTranslationManager(m_eclipseProject);
        m_slangManager       = p_slangManager;
    }

    public IProject getEclipseProject()
    {
        return m_eclipseProject;
    }

    public ITranslationManager getTranslationManager()
    {
        return m_translationManager;
    }

    public void setConnectProject( ConnectProject p_connectProject, String p_modelName )
    {
        m_connectProject = p_connectProject;
        BrowserUtility.setProjectBrowserAttributes(m_connectProject.getFormContext().getProject(), p_modelName );
    }

    public abstract Location getLocation(T p_object);
    
    /**
     * Perform the actual generation.
     *
     * @param p_object the object
     * @param p_componentMap the component map to add to
     * @param p_location the location details of the generated project
     * @throws Exception the exception
     */
    public abstract void map(T p_object, ComponentMap p_componentMap, ModelLoader p_modelLoader, Location p_location, String p_modelName) throws Exception;

    /**
     * Saves the project and any other resources required.
     *
     * @param p_projectFilePath the full path to save to
     * @throws Exception the exception
     */
    public void saveProject(String p_projectFilePath) throws Exception
    {
        LOGGER.info( "Saving Project To: {}", p_projectFilePath );
        
        ProjectUtility.saveProject( m_connectProject.getFormContext(), p_projectFilePath );
    }

    /**
     * @return the mapperData
     */
    public List<String> getMapperData()
    {
        return m_mapperData;
    }

    protected void storeMapperDetails(ComponentMap p_componentMap, EdgeMapper.Location p_location, ComponentType p_componentType, List<String> p_mapperData)
    {
        p_componentMap.setProperty(p_location.getResourceName(), p_location.getRelativeProjectDir(), p_location.getProjectFileName(), p_location.getComponentName(), p_componentType, p_mapperData);
    }

    /**
     * @return the slangManager
     */
    public SlangManager getSlangManager()
    {
        return m_slangManager;
    }

    public static class MainOutputLocation
    {
        private final String m_componentOutputDir;
        private final String m_componentSubOutputDir;

        public MainOutputLocation(String p_componentOutputDir, String p_componentSubOutputDir)
        {
            m_componentOutputDir = p_componentOutputDir;
            m_componentSubOutputDir = p_componentSubOutputDir;
        }
    }
    
    public class Location
    {
        private final String m_resourceName;
        private final String m_subDir;
        private final String m_projectFileName;
        private final String m_componentName;

        public Location(String p_resourceName, String p_subDir, String p_projectFileName, String p_componentName)
        {
            m_resourceName      = p_resourceName;
            m_subDir            = p_subDir;
            m_projectFileName   = p_projectFileName;
            m_componentName     = p_componentName;
        }

        public String getResourceName()
        {
            return m_resourceName;
        }

        public String getSubDir()
        {
            return m_subDir;
        }

        public String getProjectFileName()
        {
            return m_projectFileName;
        }

        public String getComponentName()
        {
            return m_componentName;
        }
       
        public String getRelativeProjectDir()
        {
            return m_mainOutputLocation.m_componentSubOutputDir + '/' + m_subDir; 
        }
        
        public String getFullProjectDir()
        {
            return m_mainOutputLocation.m_componentOutputDir + '/' + getRelativeProjectDir();    
        }
        
        public String getFullProjectPath()
        {
            return getFullProjectDir() + '/' + m_projectFileName;
        }
        
        public boolean doesProjectFileExist()
        {
            File f = new File(getFullProjectPath());
            
            return f.exists();
        }
    }
}
