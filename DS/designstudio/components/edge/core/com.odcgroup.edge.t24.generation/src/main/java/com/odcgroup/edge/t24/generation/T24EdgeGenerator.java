package com.odcgroup.edge.t24.generation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.swing.tree.TreePath;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.slf4j.Logger;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.IProjectVerifier;
import com.acquire.intelligentforms.LicenseInfo;
import com.acquire.intelligentforms.entities.component.ComponentManager;
import com.acquire.intelligentforms.entities.component.LibraryContext;
import com.acquire.intelligentforms.entities.component.LibraryFileDetail;
import com.acquire.intelligentforms.ide.ConnectIDE;
import com.acquire.intelligentforms.ide.util.VerifierUtility;
import com.acquire.intelligentforms.presentation.RunTimeParameters;
import com.acquire.intelligentforms.serialiser.xmlserialiser.XmlSerialiser;
import com.acquire.util.ClassUtility;
import com.acquire.util.DetailFormatter;
import com.acquire.util.FileUtility;
import com.acquire.util.IFileClassLocator;
import com.acquire.util.MemUtility;
import com.acquire.util.RegisteredThreadLocalUser;
import com.acquire.util.StringUtils;
import com.acquire.util.collection.SortedProperties;
import com.acquire.util.profile.BasicSeqMemUsed;
import com.acquire.util.profile.BasicSeqTimer;
import com.acquire.util.profile.ISeqProfiler;
import com.edgeipk.builder.util.WrapperUtility;
import com.odcgroup.edge.t24.generation.EGenOptions.EThrowUpOnError;
import com.odcgroup.edge.t24.generation.EdgeMapper.MainOutputLocation;
import com.odcgroup.edge.t24.generation.util.CachedTemplateProject;
import com.odcgroup.edge.t24.generation.util.ComponentMap;
import com.odcgroup.edge.t24.generation.util.FileSystemUtils;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.ResourcesUtil;
import com.odcgroup.edge.t24.generation.version.FullViewVersionProxy;
import com.odcgroup.edge.t24.generation.version.util.VersionUtility;
import com.odcgroup.edge.t24ui.BespokeCompositeScreen;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.YesNo;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.di.StandaloneHeadlessStatus;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.StatefullCodeGenerator;
import com.temenos.connect.utils.NameProcessor;
import com.temenos.connect.utils.SlangManager;

/**
 * Code Generation cartridge for the T24 Edge Browser Replacement POC.
 * 
 * This generates Connect *.ifp project XML files from the introspected DS T24 models.
 * 
 * @author Michael Vorburger, for Simon File.
 */

/*
 * Test comment to prove GIT 
 */

public class T24EdgeGenerator implements StatefullCodeGenerator, CodeGenerator 
{

    private static final Logger LOGGER                        = GenLogger.getLogger( T24EdgeGenerator.class );

    private static final IFileClassLocator FILE_CLASS_LOCATOR = new EclipseFileClassLocator();
    
    private static final String WEB_FRAGMENT_FOLDER_STRUCTURE = "META-INF/resources/WEB-INF/";
    private static final String WEB_FRAGMENT_DESCRIPTOR       = "META-INF/web-fragment.xml";

    private static final String EDGE_SOURCE_FOLDER            = "-gen/src/generated/edge/";

    private static final String GEN_COMPONENTS_LOCATION       = EDGE_SOURCE_FOLDER + WEB_FRAGMENT_FOLDER_STRUCTURE + "DynamicComponents";
    private static final String GEN_DATA_FOLDER_PATH          = EDGE_SOURCE_FOLDER + WEB_FRAGMENT_FOLDER_STRUCTURE + "data";

    private static final String GEN_MAP_FILENAME              = "HrefComponentMap.properties";
    private static final String HELPTEXT_MAP_FILENAME		  = "HelpTextMap.properties";
    private static final String MAP_RESOURCE_FILE             = "/data/" + GEN_MAP_FILENAME;

    private ModificationMap     m_modMap;
    private Properties			m_helpTextMap;
    private String				m_helpTextMapFile;
    private ComponentMap        m_componentMap;
    private SlangManager        m_slangManager; 
    
    private String              m_componentOutputLocation;
    private String              m_genProjectDataFolder;
    private String              m_genProjectMapFilePath;
    private String              m_genProjectSlangFolder;

    private boolean             m_isInitialised               = false;
    private boolean             m_isInterested                = false;
    private int                 m_saved                       = 0;
    private int                 m_interested                  = 0;

    private GenLoggerCallBack   m_loggerCallBack;

    private static FormContext  s_solutionContext             = null;
    private static long         s_solutionModTime             = 0;

    protected static boolean      s_forceGeneration             = EGenOptions.forceGeneration();

    private String m_modelProjectLocation;

	private String outputPath;

	// generated location
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public enum EVersionControl
    {
        STANDARD
        {
            @Override
            public VersionMapper getMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
            {
                return new VersionMapper(p_eclipseProject, p_mainOutputLocation, p_slangManager);
            }
        },
        AA
        {
            @Override
            public VersionMapper getMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
            {
                return new AAVersionMapper(p_eclipseProject, p_mainOutputLocation, p_slangManager);
            }
        }
        , 
        APPLICATION
        {
            @Override
            public VersionMapper getMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
            {
                return new ApplicationVersionMapper(p_eclipseProject, p_mainOutputLocation, p_slangManager);
            }
        }
        ;
        
        public abstract VersionMapper getMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager);
    }
    
    protected CoreException newCoreException(String message, Throwable t) throws CoreException {
        throw new CoreException(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID,
                0, message, t));
    }
    
    /** @see DS-6743 & DS-6999 */
//    public static Resource getEMFResourceWithWorkingCrossRefs(IOfsModelResource ofsModelResource) {
//    	ModelURIConverter.replaceDSResourceURIByStandardPlatformURI(ofsModelResource);
//    	return ofsModelResource.getOfsProject().getModelResourceSet().getResource(ofsModelResource.getURI(), true);
//    	// an alternative implementation (which MIGHT (re)load x2?) could be the approach used in AbstractEdgeGeneratorTest,
//    	// so reloading a Resource (using EIO helper) from the ResourceSet from the OfsProject using a platform:/ URI. 
//    }
    public static BespokeCompositeScreen getCompositeScreen(IOfsModelResource resource) throws IOException, InvalidMetamodelVersionException {
    	// temporary solution only (hack?) due to DS-6743
    	ModelURIConverter.replaceDSResourceURIByStandardPlatformURI(resource);
    	return (BespokeCompositeScreen) resource.getEMFModel().get( 1 );
    	// TODO michaelv Why does this not work??? 
    	//Resource emfResource = resource.getOfsProject().getModelResourceSet().getResource(resource.getURI(), true);
    	//BespokeCompositeScreen cos = EFactoryResource.getEFactoryEObject(emfResource, BespokeCompositeScreen.class);
	}

    //, componentOutputLocation, componentOutputMapLocation
	void doGenerate(IProject p_project, URI input, ModelLoader loader, IFileSystemAccess p_fsa) throws CoreException 
	{
        String resourceName                 = input.lastSegment();
        
		boolean runningInEclipse = !StandaloneHeadlessStatus.INSTANCE.isInStandaloneHeadless();
        
        if (runningInEclipse){
        	/*
        	 * Only if in Eclipse. Do not change anything in command line.
        	 */
	        if ((double)Runtime.getRuntime().freeMemory() / (double)Runtime.getRuntime().totalMemory() < 0.2) {  // 20%
	
	        	/*
	        	 * Give a chance to the GC to collect some memory.
	        	 */
	        	LOGGER.info("Clearing Templates to free up some memory");
		        CachedTemplateProject.clearCache();
		        ClassUtility.clearCachedMethodNames();
		        RegisteredThreadLocalUser.clearVariables();
	        }
        }
        
        try
        {
            m_loggerCallBack.setContext( resourceName );
            
            String projectPath           = p_project.getName() + '/'; // e.g. hothouse-models/
            
            String inputPath             = FileUtility.getNormalisedPath( input.path() ); // e.g. /resource/hothouse-models/version/ST_Customer/CUSTOMER,CREATE.version
            
            String componentSubOutputDir = StringUtils.getAfterFirst( inputPath, projectPath, false );
            
            componentSubOutputDir        = StringUtils.getBeforeLast( componentSubOutputDir, "/", false ); // Output = version/ST_Customer
           
            EdgeMapper.MainOutputLocation mainOutputLocation = new EdgeMapper.MainOutputLocation(m_componentOutputLocation, componentSubOutputDir);
            
            if ( "version".equals( input.fileExtension() ) )
            {
                Version version = loader.getRootEObject(input,  Version.class);
                
                EVersionControl vc = ( version.getT24Name().startsWith( "AA." ) && ! VersionUtility.isAAArrangementVersion(version) ) ? EVersionControl.AA : EVersionControl.STANDARD; 
                    
                m_saved = generateFromVersion( version, p_project, mainOutputLocation, m_componentMap, m_saved, loader, vc, m_slangManager);
            }
            else if ( "domain".equals( input.fileExtension())  && runningInEclipse)
            {
            	/*
            	 * In order to support the applications as if there where versions, 
            	 * just generate a version object with all fields, and parse it.
            	 */
            	MdfDomain domain = loader.getRootEObject(input, MdfDomain.class);
				@SuppressWarnings("unchecked")
				List<MdfClass> classes = domain.getClasses();
				for (MdfClass mdfClass : classes) {
					if (mdfClass.getName().indexOf("__") > 0){
						continue;
					}
	                Version appVersion = createDefaultVersionFromApplication(mdfClass);
	                if (appVersion != null) {
		            	LOGGER.info("Generating application resource for " + mdfClass.getName());
		            	
		            	// HelpText {
		            	// see HelpTextServlet.java it reads the properties file produced here
		            	List<MdfProperty> fields = mdfClass.getProperties();
		                String helpText, key, appName;
		                
		                // generate the helptext for the application
		                appName = mdfClass.getName();
		                helpText = mdfClass.getDocumentation();

		                if (helpText == null) helpText = "Sorry, No help available"; // FIXME: Lang map
		                m_helpTextMap.setProperty(NameProcessor.t24ToRimName(appName).toUpperCase().replace('_', '-') + " PrimaryKey", helpText);
		                		                
		                // generate the helptext for each field
		                for(MdfProperty field : fields)
		                {
		                	helpText = field.getDocumentation();
		                	key = appName.replace('_', '-') + " " + field.getName().replace('_', '-');
		                		                	
		                	if (helpText == null) helpText = "Sorry, No help available"; // FIXME: Lang map
		                	m_helpTextMap.setProperty(key, helpText);
		                }
		                
		            	// } HelpText
	                	m_saved = generateFromVersion( appVersion, p_project, mainOutputLocation, m_componentMap, m_saved, loader, EVersionControl.APPLICATION, m_slangManager);
	                }
				}

            }
            else if ( "enquiry".equals( input.fileExtension() ) )
            {
                LOGGER.info("Processing enquiry {} : {}", resourceName, new Path(inputPath) );

                Enquiry enquiry = loader.getRootEObject(input,  Enquiry.class);;
                
                m_saved = generateFromEnquiry( enquiry, p_project, mainOutputLocation, m_componentMap, m_saved, loader, m_slangManager );
            
            }
            else if ( "eson".equals( input.fileExtension() ) )
            {
                // TODO: Re-enable in isInterested()
                
                
//                LOGGER.info("Processing eson {} : {}", resource.getName(), resource.getFullPath() );
//            	BespokeCompositeScreen cos = getCompositeScreen(resource);
            	//saved = generateFromCompositeScreen( cos, p_project, mainOutputLocation, componentMap, saved );
            }
            
        }
        catch (Exception e)
        {
            final String msg = "T24EdgeGenerator failed to transform " + input.toString() + ": " + e.getMessage();
            LOGGER.error( msg, e );
            
            if  ( EGenOptions.throwupOnError() == EThrowUpOnError.AS_SOON_AS_POSSIBLE )
            {
                newCoreException( msg, e ); 
            }
        }
        finally
        {
            m_loggerCallBack.setContext(null);
            
            WrapperUtility.getWrappersCache().clear();
        }
        
	}

    private void loadSolutionProject(String p_solutionProjectFile, String p_componentsOutputLocation) throws Exception
    {
        File file = new File(p_solutionProjectFile);
        
        if  ( s_solutionContext != null )
        {
            if  ( file.lastModified() == s_solutionModTime )
            {
                return;
            }
        }
        
        LOGGER.info( "Loading Solution: {} ", p_solutionProjectFile );
        
        if  ( s_solutionContext == null )
            s_solutionContext = new FormContext();
        
        s_solutionContext.resetContext(true, true);
        s_solutionContext.resetEntityLookups();
        
        s_solutionModTime = file.lastModified();
        
        String solutionHome = FileUtility.getDirectory( p_solutionProjectFile ) ;
        
        // Read the project
        //
        XmlSerialiser projectFileSerialiser = new XmlSerialiser();

        projectFileSerialiser.readProject(s_solutionContext, p_solutionProjectFile, null, null, false);
        
        // Set up RTP as verifier requires this
        //
        RunTimeParameters rtp = new RunTimeParameters();
        rtp.setProjectHome(s_solutionContext.getProject().getProjectFolder());
        rtp.setProjectsLocation(solutionHome);
        rtp.setLibrariesLocation(p_componentsOutputLocation);
        
        s_solutionContext.setRunTimeParameters(rtp);

        LOGGER.debug( "Finished Loading Solution: {} ", p_solutionProjectFile );
    }

    protected int generateFromCompositeScreen(BespokeCompositeScreen p_bespokeCOS, IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation,
            ComponentMap p_componentMap, int p_saved, ModelLoader p_modelLoader, SlangManager p_slangManager) throws Exception, CoreException
    {
    	// if (! Boolean.FALSE.equals(p_bespokeCOS.getGenerateIFP()))
    	// {
        	final EdgeMapper<BespokeCompositeScreen> mapper = new CompositeScreenMapper(p_eclipseProject, p_mainOutputLocation, p_slangManager);
        	
        	EdgeMapper<BespokeCompositeScreen>.Location location = mapper.getLocation( p_bespokeCOS );
        	
            mapper.map(p_bespokeCOS, p_componentMap, p_modelLoader, location, p_bespokeCOS.getName());
    		
        	final String projectFilePath = location.getFullProjectPath();
        	
        	setOutputPath(projectFilePath);
            // Save project and refresh it in the eclipse workspace
            //
            mapper.saveProject( projectFilePath );
            p_saved++;
            String normalisedPath = projectFilePath.replace('\\', '/');
            refreshProjectFile( p_eclipseProject, normalisedPath);
            
            if  ( EGenOptions.verifyGeneratedProject() )
            {
                verifyGeneratedProject( normalisedPath );
            }

    	// }
    	
    	return p_saved;
    }

    /**
     * Generate from enquiry.
     *
     * @param p_enquiry the enquiry
     * @param p_project the project
     * @param p_componentsOutputLocation the components output location
     * @param p_componentMap the component map
     * @param p_saved the saved
     * @return p_saved incremented with the numbers of libraries created
     * @throws Exception the exception
     * @throws CoreException the core exception
     */
    protected int generateFromEnquiry(Enquiry p_enquiry, IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation,
            ComponentMap p_componentMap, int p_saved, ModelLoader p_loader, SlangManager p_slangManager) throws Exception, CoreException
    {
        
        final EdgeMapper<Enquiry> mapper = new EnquiryMapper( p_eclipseProject, p_mainOutputLocation, p_slangManager );

        EdgeMapper<Enquiry>.Location location = mapper.getLocation( p_enquiry );
        
        if  (   !s_forceGeneration 
            &&  !FileSystemUtils.hasEnquiryChanged( m_modMap.map(), p_enquiry )
            &&  location.doesProjectFileExist() 
            )
        {
            LOGGER.info("Not modified enquiry {}, so skipping ..", p_enquiry.getName());
            
            return p_saved;
        }
        
        if (! Boolean.FALSE.equals(p_enquiry.getGenerateIFP()))
        {
            mapper.map( p_enquiry, p_componentMap, p_loader, location, p_enquiry.getName() ); 

            // Save project and refresh it in the eclipse workspace
            //
        	String projectFilePath = location.getFullProjectPath();
        	
        	setOutputPath(projectFilePath);
        	
            mapper.saveProject( projectFilePath );
            p_saved++;
            
            String normalisedPath = projectFilePath.replace('\\', '/');
            
            refreshProjectFile( p_eclipseProject, normalisedPath );
            
            FileSystemUtils.storeLastGeneratedTimestamp(m_modMap.map(), p_enquiry);

            if  ( EGenOptions.verifyGeneratedProject() )
            {
                verifyGeneratedProject( normalisedPath );
            }
            
        }
        return p_saved;
    }

    /**
     * Generate from version.
     *
     * @param p_version the version
     * @param p_project the project
     * @param p_mainOutputLocation the components output location
     * @param p_componentMap the component map
     * @param p_saved the saved
     * @param p_versionControl 
     * @return p_saved incremented with the numbers of libraries created
     * @throws Exception the exception
     * @throws CoreException the core exception
     */
    protected int generateFromVersion(Version p_version, IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation,
            ComponentMap p_componentMap, int p_saved, ModelLoader p_modelLoader, EVersionControl p_versionControl, SlangManager p_slangManager) throws Exception, CoreException
    {
        EdgeMapper<Version> mapper = p_versionControl.getMapper( p_eclipseProject, p_mainOutputLocation, p_slangManager );

        EdgeMapper<Version>.Location location = mapper.getLocation( p_version );
        
        if  (   !s_forceGeneration 
            &&  !FileSystemUtils.hasVersionChanged( m_modMap.map(), p_version )
            &&  location.doesProjectFileExist() 
            )
        {
            LOGGER.info("Not modified version {}, so skipping ..", p_version.getT24Name());
            
            return p_saved;
        }
        
        // Avoid generating the version if explicitly told to
        //
        if ( null == p_version.getGenerateIFP() || ! YesNo.NO.equals( p_version.getGenerateIFP() ) )
        {
            LOGGER.info("Processing version {} - Type: {}", p_version.getT24Name(), p_versionControl.name() );

            if  ( "1".equals( p_version.getFieldsPerLine() ) )
            {
                // Switch to a "full view" which will generate all fields from the application into the version
                //
                p_version = new FullViewVersionProxy( p_version );
            }
            
            mapper.map( p_version, p_componentMap, p_modelLoader, location, p_version.getT24Name() );

            // Save project and refresh it in the eclipse workspace
            //
            String fullProjectPath = location.getFullProjectPath();
           
            setOutputPath(fullProjectPath);
            
            mapper.saveProject( fullProjectPath );
            
            p_saved++;
            
            refreshProjectFile( p_eclipseProject, fullProjectPath );
            
           	FileSystemUtils.storeLastGeneratedTimestamp(m_modMap.map(), p_version);               
            FileSystemUtils.storeLastGeneratedTimestamp(m_modMap.map(), (EObject) p_version.getForApplication().getParentDomain());

            if  ( EGenOptions.verifyGeneratedProject() )
            {
                verifyGeneratedProject(fullProjectPath);
            }
        }
        
        return p_saved;
    }
	
    private void loadExistingComponentMap(String p_currentMapFile, ComponentMap p_componentMap)
    {
        LOGGER.info("Loading resource map from {}", p_currentMapFile );

        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream( p_currentMapFile );
            
            p_componentMap.load( fis );
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if ( fis != null )
            {
                try
                {
                    fis.close();
                }
                catch (IOException p_ex)
                {
                }
            }
        }
        
        LOGGER.debug("Finished loading resource map from {}", p_currentMapFile );
    }

    private static void runGC(long p_gcPause)
    {
        LOGGER.info("BEFORE GC: {} ", MemUtility.getMemStats() );

        MemUtility.runGC( p_gcPause, true );

        LOGGER.info("AFTER  GC: {} ", MemUtility.getMemStats() );
    }

	private void doGenerateWebFragmentDescriptor(String projectName, String p_location) throws CoreException {
		final String webFragmentDescriptor = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<web-fragment\n" +
				"\t\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
				"\t\txmlns=\"http://java.sun.com/xml/ns/javaee\"\n" +
				"\t\txmlns:web=\"http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\"\n" +
				"\t\txmlns:webfragment=\"http://java.sun.com/xml/ns/javaee/web-fragment_3_0.xsd\"\n" +
				"\t\txsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-fragment_3_0.xsd\"\n" +
				"\t\tid=\"" + projectName + "\" version=\"3.0\">\n" + 
				"\t<display-name>DS generated web fragment for " + projectName + "</display-name>\n" + 
				"\t<name>" + projectName + "</name>\n" +
				"</web-fragment>\n";
		File webFragmentXml = new File(p_location);
		try {
			FileUtils.forceMkdir(webFragmentXml.getParentFile());
			FileUtils.writeStringToFile(webFragmentXml, webFragmentDescriptor, "UTF-8");
		} catch (IOException e) {
			newCoreException("Unable to create the web fragment descriptor (" + webFragmentXml.getAbsolutePath() + ")", e);
		}
	}

	void refreshProjectFile(IProject p_eclipseProject, String p_projectFile) throws CoreException
    {
        // Only refresh if we are outputting into the default location
        //
        if  ( EGenOptions.componentOutputLocation() == null )
        {
            LOGGER.debug("Refreshing {} ", p_projectFile );

            int index = p_projectFile.indexOf( GEN_COMPONENTS_LOCATION );
            
            String subPath = p_projectFile.substring( index );

            String projectPath = p_eclipseProject.getName() + subPath;
            
            // Edge write() API is weird and adds a ".tmp", so refresh that
            //
            refreshFile( p_eclipseProject, projectPath + ".tmp" );
            
            // As well, just to be safe (future), also *.ifp without .tmp
            //
            refreshFile( p_eclipseProject, projectPath );

            // Also refresh the theme file
            //
//            String dirPath = FileUtility.getDirectory( projectPath );
//            
//            refreshFile( p_project, dirPath + "/" + EdgeMapper.THEME_FILE );
        }
    }

    void refreshFile(IProject p_project, String p_fileName) throws CoreException
    {
        IFile genProjectIFile = p_project.getParent().getFile( new Path( p_fileName ) );
        genProjectIFile.refreshLocal( IFile.DEPTH_ZERO, null );
    }

    private static void verifyGeneratedProject(String p_libFileName)
    {
        try
        {
            LOGGER.info("Verifying Project: {}", p_libFileName);
            
            if  ( s_solutionContext == null ) throw new Exception("Need to specify solution project for verification to work");
            
            IProjectVerifier verifier = new T24EdgeGenerator.GenProjectVerifier(p_libFileName);

            String[] libraryParts = FileUtility.getParts( p_libFileName );

            String libraryName = libraryParts[1];
            
            LibraryFileDetail libDetail = new LibraryFileDetail( libraryName, libraryParts[0], p_libFileName ); 

            // TODO: May need to cache as well in the library context when we have embedded components .. arguably then the tests should be performed at the end?
            //
            LibraryContext libraryContext = ComponentManager.loadLibraryContext( null, s_solutionContext, s_solutionContext, libDetail, null, false );
            
            // Gets around template search
            //
            libraryContext.setRunTimeParameters( (RunTimeParameters) s_solutionContext.getRunTimeParameters().clone() );
            
            VerifierUtility.verifyProject( null, libraryContext, verifier, true /* p_findUnusedItems */);

            ComponentManager.getLibraryContextCache( s_solutionContext ).clear();
            
            libraryContext.resetContext( false, false );
            
            libraryContext.resetEntityLookups();

            LOGGER.debug( "Finished verifying Project: {}", p_libFileName);
        }
        catch (Exception p_ex)
        {
            LOGGER.error( "Failed to verify project", p_ex );
        }
    }

    private static final class GenLoggerCallBack implements GenLogger.ICallBack
    {
        private List<Object[]> m_errorDetails = new ArrayList<Object[]>();
        private final boolean m_profileTime;
        private final boolean m_profileMemory;
        
        private ISeqProfiler m_timer;
        private BasicSeqMemUsed m_memUsed;
        
        private String     m_context = null;
        private boolean    m_hasErrors = false;
        
        public GenLoggerCallBack(boolean p_profileTime, boolean p_profileMemory)
        {
            m_profileTime   = p_profileTime;
            m_profileMemory = p_profileMemory;
            
            if  ( m_profileTime )
            {
                m_timer = new BasicSeqTimer();
                m_timer.mark(); 
            }
            
            if  ( m_profileMemory )
            {
                m_memUsed = new BasicSeqMemUsed();
                m_memUsed.mark();
            }
        }

        public String getDiffTimeSinceStart()
        {
            if  ( m_profileTime )
            {
                return m_timer.getDiffSinceStartToLastMark().trim();
            }
            
            return null;
        }
        
        public String getDiffMemUsedSinceStart()
        {
            if  ( m_profileMemory )
            {
                return m_memUsed.getDiffSinceStartToLastMark().trim();
            }
            
            return null;
        }

        @Override
        public void callBack(final String p_methodName, final Class<?> p_loggerClass, final Object[] p_args)
        {
            if  ( p_args != null && p_args.length > 0 )
            {
                boolean isError = "error".equals( p_methodName );

                m_hasErrors |= isError;
                
                if  ( isError && EGenOptions.displayStats() )
                {
                    String[] args = new String[p_args.length];
                    
                    for (int i = 0; i < p_args.length; i++)
                    {
                        if ( p_args[i] != null )
                        {
                            args[i] = p_args[i].toString();
                        }
                    }

                    m_errorDetails.add( args );
                }

                if  (   p_args[0] instanceof String
                    &&  (  m_profileTime 
                        || m_context != null 
                        )
                    &&  (   isError 
                        ||  "warn".equals( p_methodName )
                        ||  "info".equals( p_methodName )
                        ||  "debug".equals( p_methodName )
                        )
                    )
                {
                    String message = (String) p_args[0];

                    StringBuilder newMessage = new StringBuilder(message.length() + 200);

                    if  ( m_profileTime || m_profileMemory ) 
                    {
                        if  ( m_profileTime ) m_timer.mark();
                        if  ( m_profileMemory ) m_memUsed.mark();

                        newMessage.append("Last:");
                        
                        if  ( m_profileTime )   newMessage.append(' ').append(m_timer.getDiffSinceLastMark());
                        if  ( m_profileMemory ) newMessage.append(' ').append(m_memUsed.getDiffSinceLastMark());
                        
                        newMessage.append(" Start:");

                        if  ( m_profileTime )   newMessage.append(' ').append(m_timer.getDiffSinceStartToLastMark());
                        if  ( m_profileMemory ) newMessage.append(' ').append(m_memUsed.getDiffSinceStartToLastMark());

                        newMessage.append(" - ");
                    }

                    newMessage.append(message);
                    
                    if  ( m_context != null ) 
                    {
                        newMessage.append(" (").append(m_context).append(')');
                    }
                    
                    if  ( p_loggerClass != null )
                    {
                        newMessage.append(" -> ").append(p_loggerClass.getName());
                    }
                    
                    p_args[0] = newMessage.toString();
                }
            }
        }
        
        public void appendErrors(StringBuilder p_buff)
        {
            p_buff.append("\n\nLogged ").append(getNumberOfErrors()).append(" Error(s):\n\n");

            for (Object[] errorDetails : m_errorDetails)
            {
                String errorMessage = (String) errorDetails[0];
                
                for (int i = 1; i < errorDetails.length; i++)
                {
                    try
                    {
                        errorMessage = StringUtils.replaceOnce( errorMessage, "{}", errorDetails[i].toString() );
                    }
                    catch(Exception p_ex)
                    {
                        // Ignore
                    }
                }
                
                p_buff.append(errorMessage).append('\n');
            }
        }

        public boolean hasErrors()
        {
            return m_hasErrors;
        }

        public int getNumberOfErrors()
        {
            return m_errorDetails.size();
        }
        
        public boolean profileMemUsed()
        {
            return m_profileMemory;
        }

        public boolean profileTime()
        {
            return m_profileTime;
        }

        public String getContext()
        {
            return m_context;
        }
        
        public void setContext(String p_context)
        {
            m_context = p_context;
        }
    }

    private static final class GenProjectVerifier implements IProjectVerifier
    {
        private final String m_fileName;
    
        public GenProjectVerifier(String p_fileName)
        {
            m_fileName = p_fileName;
        }
    
        @Override
        public void addSummaryStatement()
        {
            // DO NOTHING
        }
    
        @Override
        public void addStatement(String p_statement, String p_type, TreePath p_nodeTreePath, String p_editor, String p_attributeName)
        {
            String statement = p_statement.trim();
            
            if  ( p_nodeTreePath != null && p_nodeTreePath.getLastPathComponent() != null )
            {
                statement += " -> " + DetailFormatter.debug( p_nodeTreePath.getLastPathComponent() );
            }
            
            if ( VerifierUtility.ERROR.equals( p_type ) )
            {
                logError( statement );
            }
            else if ( VerifierUtility.WARNING.equals( p_type ) )
            {
                logWarning( statement );
            }
            else
            {
                logInfo( statement );
            }
        }
    
        private void logInfo(String p_statement)
        {
            p_statement = p_statement.trim();
            
            LOGGER.info( p_statement );
        }
    
        private void logWarning(String p_statement)
        {
            if  ( EGenOptions.treatVerifierWarningsAsErrors() )
            {
                logError(p_statement);
            }
            else
            {
                p_statement = p_statement.trim();
                
                if  ( !LOGGER.isInfoEnabled() )
                {
                    p_statement += " in " + m_fileName;
                }
                
                LOGGER.warn( p_statement );
            }
        }
    
        private void logError(String p_statement)
        {
            p_statement = p_statement.trim();
            
            if  ( !LOGGER.isInfoEnabled() )
            {
                p_statement += " in " + m_fileName;
            }
            
            LOGGER.error( p_statement );
        }
    }

    private IProject getProject(String projectName) {
    	if (!EMFPlugin.IS_ECLIPSE_RUNNING)
    		return null;
        IWorkspace ws = ResourcesPlugin.getWorkspace();
        if (ws == null)
        	return null;
        return ws.getRoot().getProject(projectName);
    }
    
	protected String getProjectLocation(String projectName) {
		IProject project = getProject(projectName);
		if (project == null)
			throw new IllegalStateException("getProjectLocation() needs to be @Override for the T24EdgeGenerator to work in standalone mode"); // mvorburger
		return project.getLocation().toOSString();
	}
    
    private static boolean isInterested(URI uri) {
		String ext = uri.fileExtension();
		return 	ext.equals("version") || 
				ext.equals("enquiry") ||
				ext.equals("domain"); // We are now interested since we want to generate the version for domain (application)
	}
	
	private void initialize(String projectName, IFileSystemAccess p_fsa) throws Exception {
		
		if (m_isInitialised)
			return;
		
		m_modelProjectLocation = getProjectLocation(projectName);
		
        // Check for component output override, otherwise if not set, default to the appropriate
        // *-gen project folder
        //
		m_componentOutputLocation = EGenOptions.componentOutputLocation();
		
        if ( m_componentOutputLocation == null )
        {
            m_componentOutputLocation = m_modelProjectLocation + GEN_COMPONENTS_LOCATION;
            doGenerateWebFragmentDescriptor(projectName, m_modelProjectLocation + EDGE_SOURCE_FOLDER + WEB_FRAGMENT_DESCRIPTOR);
        }
		
        m_genProjectDataFolder = m_modelProjectLocation + GEN_DATA_FOLDER_PATH;
        
        File outputDir = new File(m_genProjectDataFolder);
        
        if ( !outputDir.exists() )
            outputDir.mkdirs();
       
        m_genProjectSlangFolder = m_genProjectDataFolder + "/slang/generated";

        outputDir = new File(m_genProjectSlangFolder);
        
        if ( !outputDir.exists() )
            outputDir.mkdirs();

        m_genProjectMapFilePath = m_genProjectDataFolder + "/" + GEN_MAP_FILENAME;

        m_helpTextMapFile = m_genProjectDataFolder + "/" + HELPTEXT_MAP_FILENAME;
        
        if  ( EGenOptions.verifyGeneratedProject() )
        {
            String  solutionProject = EGenOptions.solutionProjectPath();
            
            try
            {
                if ( StringUtils.isBlank( solutionProject ) )
                {
                    solutionProject = "NONE";
                    
                    throw new Exception("No solution specified");
                }
                
                loadSolutionProject(solutionProject, m_genProjectMapFilePath);
            }
            
            catch (Exception e)
            {
                final String msg = "Failed to load solution from " + solutionProject + ": " + e.getMessage();
                LOGGER.error( msg, e );
                
                if  ( EGenOptions.throwupOnError() == EThrowUpOnError.AS_SOON_AS_POSSIBLE )
                {
                    newCoreException( msg, e ); 
                }        
            }
        }
        
		// Component MAP file
        //
		m_componentMap = new ComponentMap();

		// We need to either merge with an existing map file 
		// or start from the resource version to merge with
		//
		File mapFile = new File(m_genProjectMapFilePath);
		
        if ( !mapFile.exists() )
        {
            LOGGER.info("Copying initial resource map to {}", m_genProjectMapFilePath );
            
            try
            {
                ResourcesUtil.copyResourceToFile( MAP_RESOURCE_FILE, m_genProjectMapFilePath );
            }
            catch (Exception e)
            {
                final String msg = "Failed to copy map resource file from " + MAP_RESOURCE_FILE + " to " + m_genProjectMapFilePath + ": " + e.getMessage();
                LOGGER.error( msg, e );
                
                if  ( EGenOptions.throwupOnError() == EThrowUpOnError.AS_SOON_AS_POSSIBLE )
                {
                    newCoreException( msg, e ); 
                }            
            }
            
            LOGGER.debug("Finished copying initial resource map" );
        }
		  
        loadExistingComponentMap( m_genProjectMapFilePath, m_componentMap );
        
        loadModificationMap( projectName );
		
		loadHelpText();
		
		loadSlang();
		
        m_isInitialised = true;
	}

    /**
     * @param p_genDataFolderPath
     */
    private void loadSlang()
    {
        LOGGER.info("Loading slang from from {}", m_genProjectSlangFolder );
        
        m_slangManager = new SlangManager(false);
        
        m_slangManager.loadAllSlang(m_genProjectSlangFolder);
        
        LOGGER.debug("Finished loading slang from {}", m_genProjectSlangFolder );
    }

    private void loadHelpText() throws IOException, FileNotFoundException
    {
        LOGGER.info("Loading help text from from {}", m_helpTextMapFile );

        FileInputStream fin = null;
        try
        {
            fin = new FileInputStream( m_helpTextMapFile );
        }
        catch (Exception e)
        {
            if ( fin == null )
            {
                File file = new File( m_helpTextMapFile );
                file.createNewFile();
                fin = new FileInputStream( m_helpTextMapFile );
            }
        }
        
		m_helpTextMap = new Properties();
		m_helpTextMap.load(fin);
		fin.close();
		
		LOGGER.debug("Finished loading help text from {}", m_helpTextMapFile );
    }

    private void loadModificationMap(String p_projectName)
    {
        LOGGER.info("Loading mod map from {}", p_projectName );
        
        m_modMap = new ModificationMap(p_projectName);
		m_modMap.load();
		
		LOGGER.debug("Finished loading mod map from {}", p_projectName );
    }
	
	@Override
	public void doGenerate(URI p_input, ModelLoader p_modelLoader, IFileSystemAccess p_fsa) throws Exception {
		
		m_isInterested = isInterested(p_input);
		
		if (!m_isInterested)
			return;
		
		m_interested ++;
		
		String projectName = p_input.segment(1);
		IProject project = getProject(projectName);
		initialize(projectName, p_fsa);
		
        try
        {
            LOGGER.info("START doGenerate" );

            doGenerate(project, p_input, p_modelLoader, p_fsa);
        }
        finally
        {
            LOGGER.info( "END doGenerate" );
        }
		
	}

	@Override
	public void beforeGeneration() throws CoreException {

	    // Run GC before logger, so get baseline
	    //
        if  ( EGenOptions.runGC() > 0 )
        {
            runGC(EGenOptions.runGC());
        }

        ClassUtility.setStaticFileClassLocator( FILE_CLASS_LOCATOR );
        
	    // Create a callback routine on the logger so we can get some useful error details
        //
        m_loggerCallBack = GenLogger.setGlobalCallBack( new GenLoggerCallBack(EGenOptions.profileTime(), EGenOptions.profileMemory() ) );
        
        // HACK to allow components & widgets (used by verifier)
        //
        LicenseInfo l = ConnectIDE.getLicenseInfo();
        l.setAllowComponents(true);
        l.setAllowWidgets( true );
        
        LOGGER.info("Template Cache Option: " + EGenOptions.templateCache() );
        
	}
	
	@Override
	public void afterGeneration() throws CoreException {
		
	    try
	    {
	    	
	    	// exist gracefully if no component path is set.  This the case when
	    	// we are invoked incorrectly ie when the user gen codes on a rim
	    	if ( m_genProjectMapFilePath == null )
            {
                return;
            }
	    	
    		// save modification map
    		//
    		if (m_modMap != null) 
    			m_modMap.save();
    		
    		// save helpText map
    		if (m_helpTextMap != null) {
    			FileOutputStream outputMap = null;
    			try {
	    			outputMap = new FileOutputStream(m_helpTextMapFile);
	                m_helpTextMap.store(outputMap, "HelpText file");
	                outputMap.close();	
    			}
    			catch (Exception e){
    				final String msg = "Cannot write Edge helpText map file to: " + m_helpTextMapFile + ": " + e.getMessage();
	                LOGGER.error( msg, e );
    			}
    		}

    		// Save all slang
    		//
    		LOGGER.info("Saving all slang to {} ", m_genProjectDataFolder);
    		 
    		m_slangManager.saveAllSlang( m_genProjectSlangFolder );
    		
    		LOGGER.info("Finished Saving all slang to {} ", m_genProjectDataFolder);
    		
    		// save Component Map
    		//
            String mapFilePath = EGenOptions.componentMapFilePath();
    
            if ( mapFilePath == null )
            {
                mapFilePath = m_genProjectMapFilePath;
            }
            
            File outputMap = new File(mapFilePath);
            
            if(m_componentMap != null)
            {
	            try
	            {
	                LOGGER.info("Writing Edge component map file to: {} ", outputMap.getAbsolutePath());
	            	
	                // sort the componentMap
	                SortedProperties scm = new SortedProperties();
	                scm.putAll(m_componentMap);
	                
	                scm.store(new FileOutputStream(outputMap), 
	            	         "\n File: HrefComponentMap.properties - Dynamic Component Mapping File"
	            	       + "\n"
	            	       + "\n This file is read by custom rule: com.temenos.connect.T24Browser.IRISMapper"
	            	       + "\n"
	            	       + "\n Format: irisUrlSuffix=relativeDir|componentIfpName|componentName|[entityType]|[displayType]|[mapperData]"
	            	       + "\n "
	            	       + "\n ...where:"
	            	       + "\n"
	            	       + "\n - relativeDir is the DynamicComponents sub-directory containing the relevant component IFP"
	            	       + "\n - componentIfpName is the path-less filename of the IFP in that directory"
	            	       + "\n - componentName is the component process name (NB: For versions this should be the equivalent of the IRIS compatible version name)."
	            	       + "\n - entityType is either 'E' for an Enquiry component, or 'V' for a Version component (if left unspecified, then the default action is performed .. i.e. NOT Version)"
	            	       + "\n - displayType is either 'I' if the component is to be displayed Inside the main window, or 'P' if to be displayed in its own popup ('I' being assumed if left unspecified)"
	            	       + "\n - any mapper data required, such as a set of strings which will be persisted as caret separated values"
	            	       + "\n"
	            	       + "\n, e.g. Customer_Input=version/Customer_Input|Customer_Input.ifp|Customer_Input|V||PrimaryKeyName=CustomerCode^QuestionTextFor_en=Basic Details^QuestionTextFor_fr=Le Basic Details^QuestionTextFor_de=De Basic Details"
	            	       + "\n"
	            			);
	            	
	            	LOGGER.info("Finished Writing Edge component map file to {}", outputMap.getAbsolutePath());
	            	
	            }
	        	catch (Exception e)
	            {
	        	    final String msg = "Cannot write Edge component map file to: " + outputMap.getAbsolutePath() + ": " + e.getMessage();
	                LOGGER.error( msg, e );
	                
	                if  ( EGenOptions.throwupOnError() == EThrowUpOnError.AS_SOON_AS_POSSIBLE )
	                {
	                    newCoreException( msg, e ); 
	                }
	            }
            }
	    }
	    finally
	    {
            m_modMap = null;
            m_helpTextMap = null;
            m_componentMap = null;
            m_genProjectDataFolder = null;
            m_modelProjectLocation = null;
            m_genProjectMapFilePath = null;
            m_genProjectSlangFolder = null;
            m_slangManager = null;
            m_isInitialised = false;
	        m_isInterested = false;

            try
            {
    	        CachedTemplateProject.clearCache();
    	        ClassUtility.clearCachedMethodNames();
    	        ClassUtility.setStaticFileClassLocator( null );
    	        RegisteredThreadLocalUser.clearVariables();
	        
    	        if ( m_interested > 0 )
    	        {
                    if ( EGenOptions.displayStats() || ( m_loggerCallBack.hasErrors() && EGenOptions.throwupOnError() != EThrowUpOnError.KEEP_DOWN ) )
                    {
                        displaySummary();
                    }
    	        }
            }
            finally
            {
                m_saved = 0; // Clear here so that it can be used the in the summary display.
                m_interested = 0;
                
                GenLogger.setGlobalCallBack( null );
                
                if  ( EGenOptions.runGC() > 0 )
                {
                    runGC(EGenOptions.runGC());
                }
            }
	    }
	}

    private void displaySummary() throws CoreException
    {
        StringBuilder buff = new StringBuilder();
        
        if  ( m_saved == 0 )
        {
            buff.append("\n\nNo components were output");
        }
        else
        {
            buff.append("\n\nThere were ").append(m_saved).append(" of " ).append(m_interested).append(" libraries(s) output" );
        }
   
        if  ( m_loggerCallBack.profileTime() )
        {
            buff.append( " in " ).append(m_loggerCallBack.getDiffTimeSinceStart()).append( " seconds" );
        }
        
        if  ( m_loggerCallBack.profileMemUsed() )
        {
            buff.append( " with " ).append(m_loggerCallBack.getDiffMemUsedSinceStart()).append( " used" );
        }
   
        buff.append("\n\nCurrent Memory Used: ")
            .append( MemUtility.getUsedInMB() )
            .append("\n" );
            ;

        if  ( m_loggerCallBack.hasErrors() )
        {
            buff.append("\nThere were ").append(m_loggerCallBack.getNumberOfErrors()).append(" errors, please review the logs.");
            
            String message = buff.toString();
            
            buff.setLength( 0 );
            
            m_loggerCallBack.appendErrors(buff);
   
            // Use a new core exception as the exception details are now shown directly in the error dialog header (not just detail area),
            // this way it will be as before
            //
            newCoreException( message, new RuntimeException(buff.toString()) );
        }
        else
        {
            buff.append("\n\nThe message box is misleading .. this is OK!");
            
            // Ideally this should not be regarded as an error, but it is at the moment.
            //
            throw new CoreException(new Status(IStatus.OK, GenerationCore.PLUGIN_ID, buff.toString()));
        }
    }

	@Override
	public void run(IProgressMonitor monitor, IProject project, Collection<IOfsModelResource> modelResources,
			IFolder outputFolder, List<IStatus> nonOkStatuses) {
		throw new UnsupportedOperationException();		
	}

	private static class EclipseFileClassLocator implements IFileClassLocator
	{
        @Override
        public File getClassLoadOrigin(URL p_classUrl)
        {
            try
            {
                URL resolvedURL = FileLocator.resolve(p_classUrl);
                
                if ( ClassUtility.isClassLoadedFromArchive( resolvedURL ) )
                {
                    // Class is loaded from an archive so obtain the name of the archive file,
                    // otherwise it will end up resolving to a dummy path
                    //
                    return new File( ClassUtility.getClassArchiveFileName( resolvedURL ) );
                }
                
                File file = new File(resolvedURL.getFile());
                
                if  ( file.exists() && file.getPath().endsWith( ".class" ) )
                {
                    // Need to return the directory to iterate over
                    //
                    return file.getParentFile();
                }
            }
            catch (IOException p_ex)
            {
                // Ignore for now ..
            }

            // Let the default handling take place
            //
            return null;
        }
	    
	}
	
	/**
	 * Generate a version object with fieldsPerLine = 1 so this means "all fields"
	 * @param mdfClass
	 * @return
	 */
	private Version createDefaultVersionFromApplication(MdfClass mdfClass ){
		String sT24Name = T24Aspect.getT24Name(mdfClass);
		if (sT24Name == null){
			return null;
		}
		Version version = VersionDSLFactoryImpl.eINSTANCE.createVersion();
		version.setForApplication(mdfClass);
		version.setMetamodelVersion("1.30.6"); // SHould we get it from somewhere ?
		version.setT24Name(sT24Name);
		version.setShortName("");
		version.setNumberOfAuthorisers(1);
		version.setOtherCompanyAccess(YesNo.YES);
		version.setRecordsPerPage("1");
		version.setFieldsPerLine("1");
		return version;
	}

	
}
