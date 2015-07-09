package com.odcgroup.workbench.ui.maven;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.project.MavenUpdateRequest;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.osgi.service.prefs.BackingStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * Contains the code to integrate with m2eclipse. Please note this is not a built
 * as a generic bridge to m2eclipse, but intends to gather all the code used
 * to integrate with m2eclipse required by DS to offers the extracted jars of 
 * OCS as an offline local repository. 
 * @author yan
 */
public class M2EclipseIntegrationFacade {

	public static final String DEFAULT_DS_MAVEN_MIRROR_PROPERTY_VALUE = "http://maven.oams.com/content/groups/all/";

	private static final String DS_MAVEN_MIRROR_PROPERTY = "ds.maven.mirror";

	private static Logger logger = LoggerFactory.getLogger(M2EclipseIntegrationFacade.class);
	
	/** plugin id of m2eclipse plugin (used to access m2eclipse properties */
	private static final String M2ECLIPSE_PLUGIN_ID = "org.eclipse.m2e.core";
	
	/** Maven xml tag for local repository */
	private static final String LOCAL_REPOSITORY_TAG = "localRepository";
	
	/** Path to the maven xml tag for mirror url */
	private static final String MIRROR_URL_PATH = "settings.mirrors.mirror.url";
	private static final String MIRRORS_TAG = "mirrors";
	private static final String MIRROR_TAG = "mirror";
	/** Mirror child Element */
	private static final String MIRROR_ID_TAG = "id";
	private static final String MIRROR_NAME_TAG = "name";
	private static final String MIRROR_URL_TAG = "url" ;
	private static final String MIRROR_OF_TAG = "mirrorOf";
	
	
	/** Maven xml tag for offline */
	private static final String OFFLINE_TAG = "offline";

	/** Settings.xml location if the DS installation */
	private static final String DS_SETTINGS_XML = File.separator + "configuration" + File.separator + "settings.xml";

	/** M2eclipse user settings property name */
	private static final String M2_ECLIPSE_USER_SETTINGS_FILE_PROPERTY = "eclipse.m2.userSettingsFile";
	
	/** Singleton */
	protected static M2EclipseIntegrationFacade INSTANCE;
	public static M2EclipseIntegrationFacade instance() {
		if (INSTANCE == null) {
			INSTANCE = new M2EclipseIntegrationFacade();
		}
		return INSTANCE;
	}

	/**
	 * Update the settings.xml's local repository.
	 * Additionally set the offline mode.
	 * @param localRepository
	 * @param offline
	 */
	public boolean updateSettingsXml(String localRepository, boolean offline) {
		File settingsXml = getSettingsXmlFile();
		
		if (!settingsXml.exists()) {
			String message = settingsXml.getAbsolutePath() + " is not found. The installation might be incomplete.";
			logger.error(message);
			displayError("Maven initialization error: " + message + " Maven won't work properly until the configuration is fixed.", null);
			return false;
		}
		
		SAXBuilder builder = new SAXBuilder();
		FileWriter fileWriter = null;
		try {
			// Read the settings.xml
			Document document = builder.build(settingsXml);
			
			// Update the settings.xml
			boolean localRepoChanged = updateLocalRepository(document, localRepository);
			boolean mirrorChanged = updateMirrorUrl(document, offline?"file://"+localRepository:System.getProperty(DS_MAVEN_MIRROR_PROPERTY, DEFAULT_DS_MAVEN_MIRROR_PROPERTY_VALUE));
			boolean offlineChanged = updateOffline(document, offline);
			
			if (localRepoChanged || mirrorChanged || offlineChanged) {
				// Save the settings.xml
				XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
				fileWriter = new FileWriter(settingsXml);
				out.output(document, fileWriter);
				fileWriter.flush();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			String message = "Unexepected error while updating " + settingsXml.getAbsolutePath() + ".\n\nCheck you installation.";
			logger.error(message, e);
			displayError(message, e);
			return false;
		} finally {
			if (fileWriter != null) {
				IOUtils.closeQuietly(fileWriter);
			}
		}
	}

	/**
	 * @return return the settings xml used by m2eclipse
	 */
	public File getSettingsXmlFile() {
		return new File(Platform.getInstallLocation().getURL().getPath() + DS_SETTINGS_XML);
	}
	
	/**
	 * Display error message in a dialog box.
	 * @param message
	 */
	protected void displayError(final String message, Exception e) {
		OfsUICore.getDefault().logError("Maven initilization error: " + message + " Maven won't work properly in Design Studion until the configuration is fixed.", e);
	}
	
	/**
	 * Update the local repository of the settings.xml
	 * @param document
	 * @param localRepository
	 * @return <code>true</code> if the document has been updated, 
	 * <code>false</code> otherwise.
	 */
	@SuppressWarnings("unchecked")
	protected boolean updateLocalRepository(Document document, String localRepository) {
		Namespace ns = document.getRootElement().getNamespace();
		Element localRepo = document.getRootElement().getChild(LOCAL_REPOSITORY_TAG, ns);
		if (localRepo == null) {
			localRepo = new Element(LOCAL_REPOSITORY_TAG, ns);
			document.getRootElement().getChildren().add(localRepo);
		} else {
			if (localRepo.getText().equals(localRepository)) {
				return false;
			}
		}
		localRepo.setText(localRepository);
		return true;
	}

	/**
	 * Update the mirror url of the settings.xml
	 * @param document
	 * @param newUrl
	 * @return <code>true</code> if the document has been updated, 
	 * <code>false</code> otherwise.
	 */
	@SuppressWarnings("unchecked")
	protected boolean updateMirrorUrl(Document document, String newUrl) {
		Namespace ns = document.getRootElement().getNamespace();
		if (newUrl == null) {
			newUrl = DEFAULT_DS_MAVEN_MIRROR_PROPERTY_VALUE;
		}
		
		if (!newUrl.equals("")) {
			StringTokenizer st = new StringTokenizer(MIRROR_URL_PATH, ".");
			st.nextToken(); // Skip root
			Element current = document.getRootElement().getChild(st.nextToken(), ns);
			if(current !=null) {
				// check for the mirror tag under mirrors.
				List mirrorTagList = current.getChildren();
				Element mirrorTag = null;
				if (mirrorTagList != null && !mirrorTagList.isEmpty()) {
					mirrorTag = (Element) mirrorTagList.get(0);// etChild("mirror");
				}
				//if mirror tag null create mirror tag with defaultvalues.
				if(mirrorTag == null){
					Element mirrorElement = createMirrorElement(document);
					current.getChildren().add(mirrorElement);
				}
			}
			
			while (st.hasMoreTokens() && current != null) {
				current = current.getChild(st.nextToken(), ns);
			}
			if (current != null) {
				String currentUrl = current.getText();
				if (!currentUrl.equals(newUrl)) {
					current.setText(newUrl);
					return true;
				}
			}
		} else {
			List mirrors = document.getRootElement().getChild(MIRRORS_TAG, ns).getChildren();
			if (!mirrors.isEmpty()) {
				mirrors.clear();
				return true;
			}
		}
		return false;
	}
    /**
     * create the mirror element if no mirror element in setting.xml file
     */
	@SuppressWarnings("unchecked")
	private Element createMirrorElement(Document document) {
		Namespace ns = document.getRootElement().getNamespace();
		Element mirrorElement = new Element(MIRROR_TAG, ns);
		String[] mirrorChildElementNames = {MIRROR_ID_TAG,MIRROR_NAME_TAG,MIRROR_URL_TAG,MIRROR_OF_TAG};
		for(String childName :Arrays.asList(mirrorChildElementNames)){
			Element  childElement = new Element(childName, ns);
			if(childName.equals(MIRROR_ID_TAG)){
				childElement.setText("Dummy");
			} 
			if(childName.equals(MIRROR_NAME_TAG)){	
				childElement.setText("Dummy repository to prevent external access");
			}
			if(childName.equals(MIRROR_OF_TAG)){
				childElement.setText("external:*");
			}
			mirrorElement.getChildren().add(childElement);
		}
		return mirrorElement;
	}

	/**
	 * Update the offline mode of the settings.xml
	 * @param document
	 * @param offline
	 * @return <code>true</code> if the document has been updated, 
	 * <code>false</code> otherwise.
	 */
	@SuppressWarnings("unchecked")
	private boolean updateOffline(Document document, boolean offline) {
		Namespace ns = document.getRootElement().getNamespace();
		Element offlineTag = document.getRootElement().getChild(OFFLINE_TAG, ns);
		if (offlineTag == null) {
			offlineTag = new Element(OFFLINE_TAG, ns);
			document.getRootElement().getChildren().add(offlineTag);
		} else {
			if (offlineTag.getText().equals(""+offline)) {
				return false;
			}
		}
		offlineTag.setText("" + offline);

		return true;
	}

	/**
	 * Forces m2eclipse to reload the settings.xml. As there is no m2eclipse API
	 * to do this, change events are triggered on all pom.xml at the root
	 * of all maven project. 
	 */
	public void reloadSettingsXml() {
		try {
			MavenPlugin.getMaven().reloadSettings();
		} catch (CoreException e) {
			logger.error("A problem occured when trying to reload the maven settings.");
		}
		
		// Force m2eclipse to recompute the classpath
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					for (final IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
						if (project.exists()
								&& project.isOpen()
								&& project.getNature(OfsCore.M2ECLIPSE_NATURE) != null) {
							final IFile pomXml = project.getFile("pom.xml");
							if (pomXml.exists()) {
								try {
									//MavenPlugin.getProjectConfigurationManager().updateProjectConfiguration(project, monitor);
									MavenUpdateRequest request = new MavenUpdateRequest(project, true, false);
									MavenPlugin.getMavenProjectRegistry().refresh(request);
								} catch (Exception e) {
									pomXml.touch(monitor);									
								}
							}
						}
					}
				}
			}, null);
		} catch (CoreException e) {
			logger.error("A problem occured when trying to touch all pom.xml file of the workspace.");
		}
	}

	/**
	 * Update the m2eclipse user setting file property according 
	 * @param synchronizeConfig if <code>true</code> the user setting file property
	 * will be set to the path of the settings.xml generated by Design Studio.
	 * If <code>false</code> it will set it to the default value if it is still
	 * referencing the settings.xml generated by Design Studio. 
	 * @param synchronizeConfig to be set to 
	 * @return <code>true</code> if the user settings file property has been changed,
	 * <code>false</code> otherwise.
	 */
	public boolean updateUserSettingsFileProperty(boolean synchronizeConfig) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(M2ECLIPSE_PLUGIN_ID);
		String currentValue = prefs.get(M2_ECLIPSE_USER_SETTINGS_FILE_PROPERTY, null);
		boolean changed = false;
		if (synchronizeConfig) {
			// Only put the value if it has changed
			if (currentValue == null || !currentValue.equals(getSettingsXmlFile().getAbsolutePath())) {
				prefs.put(M2_ECLIPSE_USER_SETTINGS_FILE_PROPERTY, getSettingsXmlFile().getAbsolutePath());
				changed = true;
			}
		} else {
			if (currentValue != null && currentValue.endsWith(DS_SETTINGS_XML)) {
				prefs.remove(M2_ECLIPSE_USER_SETTINGS_FILE_PROPERTY);
				changed = true;
			}
		}
		if (changed) {
			try {
				prefs.flush();
			} catch (BackingStoreException e) {
				// fail silently
				logger.error("A problem when saving the properties", e);
			}
		}
		return changed;
	}

	public void checkIfClasspathContainerAreUpToDate() {
		// Force m2eclipse to recompute the classpath
		for (final IProject project : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects()) {
			try {
				if (project.exists() && project.isOpen()
						&& project.getNature(JavaCore.NATURE_ID) != null
						&& project.getNature(OfsCore.M2ECLIPSE_NATURE) != null) {
					final IFile pomXml = project.getFile("pom.xml");
					if (pomXml.exists()) {
						Job job = new Job("Check Maven classpath container validity") {
							@Override
							protected IStatus run(IProgressMonitor monitor) {
								try {
									// DS-5824
									getJobManager().join(JavaUI.ID_PLUGIN, monitor);

									IJavaProject javaProject = (IJavaProject) project.getNature(JavaCore.NATURE_ID);
	
									IClasspathEntry[] expandedM2Classpath = getExpandedM2Classpath(javaProject);
	
									// skip the project if no m2eclispe
									// classpath container is found
									if (expandedM2Classpath != null) {
										boolean nonExistingJarFound = false;
										for (IClasspathEntry cp : expandedM2Classpath) {
											if (!cp.getPath().toFile().exists()) {
												nonExistingJarFound = true;
												break;
											}
										}
		
										if (nonExistingJarFound) {
											pomXml.touch(monitor);
											logger.debug("Pom touched to reload maven container: "
													+ pomXml.getLocation()
															.toPortableString());
										}
									}
									return Status.OK_STATUS;
								} catch (Exception e) {
									return new Status(Status.ERROR, OfsUICore.PLUGIN_ID, e.getMessage(), e);
								}
							}
							
						};
						job.schedule(10000);
					}
				}
			} catch (CoreException e) {
				logger.error("A problem occured when trying to touch all pom.xml file of the workspace.");
			}
		}
	}

	/**
	 * Return the m2eclipse class path expanded (i.e. all jar references resolved to a physical file)
	 * @param javaProject
	 * @return the m2eclipse class path expanded (i.e. all jar references resolved to a physical file). 
	 * return <code>null</code> if the m2eclipse classpath container cannot be found
	 * @throws JavaModelException
	 */
	public IClasspathEntry[] getExpandedM2Classpath(IJavaProject javaProject) throws JavaModelException {
		IClasspathEntry m2eClasspathCP = null;
		for (IClasspathEntry cp : javaProject.getRawClasspath()) {
			if (OfsCore.M2ECLIPSE_CLASSPATH_CONTAINER.equals(cp.getPath().toString())) {
				m2eClasspathCP = cp;
				break;
			}
		}
		
		IClasspathEntry[] expandedM2Classpath;
		if (m2eClasspathCP == null) {
			expandedM2Classpath = null;
		} else {
			expandedM2Classpath = JavaCore.getClasspathContainer(m2eClasspathCP.getPath(), javaProject).getClasspathEntries();
		}

		return expandedM2Classpath;
	}

}
