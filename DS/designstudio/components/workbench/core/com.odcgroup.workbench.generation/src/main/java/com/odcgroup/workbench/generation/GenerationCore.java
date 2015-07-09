package com.odcgroup.workbench.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.ClasspathEntry;
import org.osgi.framework.BundleContext;

import com.odcgroup.workbench.core.AbstractActivator;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.generation.cartridge.CategoryNotFoundException;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.cartridge.CodeGenCategory;
import com.odcgroup.workbench.generation.cartridge.ModelCartridge;
import com.odcgroup.workbench.generation.cartridge.SelectionProvider;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;
import com.odcgroup.workbench.generation.internal.nature.OfsTechnicalNature;
import com.odcgroup.workbench.generation.properties.PropertyHelper;

/**
 * The activator class controls the plug-in life cycle
 */
public class GenerationCore extends AbstractActivator {

	// the messages resource bundle
	private static final String BUNDLE_NAME = "com.odcgroup.workbench.generation.messages"; //$NON-NLS-1$

	public static final String CODE_CARTRIDGE_EXTENSION_ID = "com.odcgroup.workbench.generation.m2cCartridge";
	public static final String MODEL_CARTRIDGE_EXTENSION_ID = "com.odcgroup.workbench.generation.cartridge.m2m";
	public static final String SELECTION_PROVIDER_EXTENSION_ID = "com.odcgroup.workbench.generation.selectionProvider";
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.workbench.generation";
	
	public static final String TECHNICAL_NATURE_ID = PLUGIN_ID + ".OfsTechnicalNature"; //$NON-NLS-1$
	public static final String MARKER_ID = PLUGIN_ID + ".projectProblem"; //$NON-NLS-1$

	// folder names
	public static final String PACKAGING_RESOURCE_FOLDER = "packaging";

	private static CodeCartridge[] codeCartridges = null;
	private static Set<SelectionProvider> selectionProviders = null;

	@Override
	protected String getResourceBundleName() {
		return BUNDLE_NAME;
	}

	public static GenerationCore getDefault() {
		return (GenerationCore) getDefault(GenerationCore.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	public static synchronized CodeCartridge[] getRegisteredCodeCartridges() {
		if (codeCartridges == null) {
			ArrayList<CodeCartridge> cartridges = new ArrayList<CodeCartridge>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint point = registry.getExtensionPoint(CODE_CARTRIDGE_EXTENSION_ID);
			if (point == null)
				return new CodeCartridge[0];
			IConfigurationElement[] configElements = point.getConfigurationElements();

			// iterate over all defined m2c-cartridges
			for (int j = 0; j < configElements.length; j++) {
				CodeCartridge cartridge = new CodeCartridge();
				cartridge.setId(configElements[j].getAttribute("id"));
				cartridge.setName(configElements[j].getAttribute("name"));
				cartridge.setEnabledByDefault(!"false".equals(configElements[j].getAttribute("enabledByDefault")));
				
				try {
					CodeGenCategory category = CodeGenCategory.fromString(configElements[j].getAttribute("category"));
					cartridge.setCategory(category);

					String contributorName = configElements[j].getDeclaringExtension().getContributor().getName();
					String codeGenClass = configElements[j].getAttribute("class");
					Class<?> javaClass = Platform.getBundle(contributorName).loadClass(codeGenClass);
					cartridge.setCodeGeneratorClass(javaClass);
					
					cartridges.add(cartridge);
				} catch (CategoryNotFoundException e) {
					GenerationCore.getDefault().logError(e.getMessage(), e);
				} catch (ClassNotFoundException e) {
					IStatus status = new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, "");
					GenerationCore.getDefault().logError(e.getMessage(), e);
				}
			}
			codeCartridges = cartridges.toArray(new CodeCartridge[0]);
		}
		return codeCartridges.clone();
	}

	public static CodeCartridge[] getRegisteredCodeCartridges(CodeGenCategory category) {
		ArrayList<CodeCartridge> matchingCartridges = new ArrayList<CodeCartridge>();

		CodeCartridge[] allCartridges = getRegisteredCodeCartridges();
		for (CodeCartridge cartridge : allCartridges) {
			if (cartridge.getCategory().equals(category)) {
				matchingCartridges.add(cartridge);
			}
		}
		return matchingCartridges.toArray(new CodeCartridge[0]);
	}

	public static ModelCartridge[] getRegisteredModelCartridges() {
		ArrayList<ModelCartridge> cartridges = new ArrayList<ModelCartridge>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(MODEL_CARTRIDGE_EXTENSION_ID);
		if (point == null)
			return new ModelCartridge[0];
		IConfigurationElement[] configElements = point.getConfigurationElements();

		// iterate over all defined m2m-cartridges
		for (int j = 0; j < configElements.length; j++) {
			ModelCartridge cartridge = new ModelCartridge();
			cartridge.setId(configElements[j].getAttribute("id"));
			cartridge.setWorkflow(configElements[j].getAttribute("workflow"));
			cartridge.setName(configElements[j].getAttribute("name"));
			cartridge.setPimName(configElements[j].getAttribute("pimName"));

			cartridges.add(cartridge);
		}
		return cartridges.toArray(new ModelCartridge[0]);
	}

	public static synchronized SelectionProvider[] getRegisteredSelectionProviders() {
		if (selectionProviders == null) {
			selectionProviders = new HashSet<SelectionProvider>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint point = registry.getExtensionPoint(SELECTION_PROVIDER_EXTENSION_ID);
			if (point == null)
				return new SelectionProvider[0];
			IConfigurationElement[] configElements = point.getConfigurationElements();

			// iterate over all defined selection providers
			for (int j = 0; j < configElements.length; j++) {
				try {
					SelectionProvider selectionProvider = (SelectionProvider) configElements[j].createExecutableExtension("class");
					selectionProviders.add(selectionProvider);
				} catch (CoreException e) {
					GenerationCore.getDefault().getLog().log(e.getStatus());
				}
			}
		}
		return selectionProviders.toArray(new SelectionProvider[selectionProviders.size()]);
	}
	
	/**
	 * This method returns the Java project name for a code generation category.
	 * In the configuration, the projectname may contain <code>[project]</code>
	 * as a placeholder for the Odyssey project name. This placeholder will be
	 * substituted.
	 * 
	 * @param project
	 *            the Odyssey project
	 * @param category
	 * @return
	 */
	public static String getJavaProjectName(IProject project, CodeGenCategory category) {
		ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);
		String outputSourceFolder = preferences.get(category.toString(), null);
		if (outputSourceFolder != null) {
			IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
			if (ofsProject != null) {
				String outputProject = outputSourceFolder.split("/")[0];
				outputProject = outputProject.replace("[project]", ofsProject.getName());
				return outputProject;
			}
		}
		return null;
	}

	/**
	 * This method returns the Java source folder name for a code generation
	 * category. In the configuration, the projectname may contain
	 * <code>[project]</code> as a placeholder for the Odyssey project name.
	 * This placeholder will be substituted.
	 * 
	 * @param project
	 *            the Odyssey project
	 * @param category
	 * @return
	 */
	public static IFolder getJavaSourceFolder(IProject project, CodeGenCategory category) {
		ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);
		String outputSourceFolder = preferences.get(category.toString(), null);
		if (outputSourceFolder != null) {
			if (project != null) {
				String[] outputPath = outputSourceFolder.split("/");
				String outputProjectName = outputPath[0].replace("[project]", project.getName());
				IProject outputProject = ResourcesPlugin.getWorkspace().getRoot().getProject(outputProjectName);
				if (outputPath.length > 1) {
					IFolder outputFolder = outputProject.getFolder(outputPath[1]);
					for (int i = 2; i < outputPath.length; i++) {
						outputFolder = outputFolder.getFolder(outputPath[i]);
					}
					return outputFolder;
				}
			}
		}
		return null;
	}

	/**
	 * This method returns the names of all Java projects that this project
	 * generates code into. The selected generation cartridges are evaluated, so
	 * that the returned values depend on the property settings of the project.
	 * 
	 * @param project
	 *            the Odyssey project
	 * @return array of Java project names
	 */
	public static String[] getJavaProjectNames(IProject project) {
		Set<String> projectNames = new HashSet<String>();
		ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);

		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		if (ofsProject != null) {
			for (CodeCartridge cartridge : PropertyHelper.getSelectedCodeCartridges(project)) {
				CodeGenCategory category = cartridge.getCategory();
				String[] sourceFolderPath = preferences.get(category.toString(), "").split("/");
				String outputProjectName = sourceFolderPath.length > 0 ? sourceFolderPath[0] : null;
				if (outputProjectName != null && !outputProjectName.equals("")) {
					if (outputProjectName.startsWith("[project]-gen")) {
						outputProjectName = outputProjectName.replace("[project]", ofsProject.getName());
						projectNames.add(outputProjectName);
					}
				} else {
					getDefault().logWarning("Empty preference setting for category " + cartridge.getCategory(), null);
				}
			}
		}

		return projectNames.toArray(new String[0]);
	}

	/**
	 * This method returns the names of all Java source folder paths that this
	 * project generates code into. The selected generation cartridges are
	 * evaluated, so that the returned values depend on the property settings of
	 * the project.
	 * 
	 * @param project
	 *            the Odyssey project
	 * @return array of Java project names
	 */
	public static String[] getJavaSourceFolderNames(IProject project) {
		Set<String> sourceFolderNames = new HashSet<String>();
		ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);

		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		if (ofsProject != null) {
			for (CodeCartridge cartridge : PropertyHelper.getSelectedCodeCartridges(project)) {
				if (cartridge.getCategory().isContainsJava()) {
					CodeGenCategory category = cartridge.getCategory();
					String sourceFolderName = preferences.get(category.toString(), null);
					if (sourceFolderName != null && !sourceFolderName.equals("")) {
						if (sourceFolderName.startsWith("[project]-gen/")) {
							sourceFolderName = sourceFolderName.replace("[project]", ofsProject.getName());
							sourceFolderNames.add(sourceFolderName);
						}
					} else {
						getDefault().logWarning("Empty preference setting for category " + cartridge.getCategory(), null);
					}
				}
			}
		}
		return sourceFolderNames.toArray(new String[0]);
	}

	public static String[] getClassFolderNames(IProject project) {
		Set<String> classFolderNames = new HashSet<String>();
		ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);

		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		if (ofsProject != null) {
			for (CodeCartridge cartridge : PropertyHelper.getSelectedCodeCartridges(project)) {
				if (!cartridge.getCategory().isContainsJava()) {
					CodeGenCategory category = cartridge.getCategory();
					String classFolderName = preferences.get(category.toString(), null);
					if (classFolderName != null && !classFolderName.equals("")) {
						if (classFolderName.startsWith("[project]-gen/")) {
							classFolderName = classFolderName.replace("[project]", ofsProject.getName());
							classFolderNames.add(classFolderName);
						}
					} else {
						getDefault().logWarning("Empty preference setting for category " + cartridge.getCategory(), null);
					}
				}
			}
		}
		return classFolderNames.toArray(new String[0]);
	}

	/**
	 * This method returns the names of all Java source folder paths that this
	 * project generates code into. The selected generation cartridges are
	 * evaluated, so that the returned values depend on the property settings of
	 * the project.
	 * 
	 * @param project
	 *            the Odyssey project
	 * @param outputProject
	 *            only list source folders for this output project
	 * @return array of Java project names
	 */
	public static String[] getJavaSourceFolderNames(IProject project, String outputProject) {
		Set<String> sourceFolderNames = new HashSet<String>();
		for (String sourceFolderName : getJavaSourceFolderNames(project)) {
			if (sourceFolderName.startsWith(outputProject + "/")) {
				sourceFolderNames.add(sourceFolderName);
			}
		}

		return sourceFolderNames.toArray(new String[0]);
	}
	
	public static String[] getJavaSourceFolderNamesSlashPrefixed(IProject project, String outputProject) {
		String[] sourceFolderNamesArray = getJavaSourceFolderNames(project, outputProject);
		for (int i = 0; i < sourceFolderNamesArray.length; i++) {
			String sourceFolderName = sourceFolderNamesArray[i];
			sourceFolderNamesArray[i] = "/"+sourceFolderName;
		}
		return sourceFolderNamesArray;
	}

	public static boolean isJavaSourceFolder(IFolder folder) {
		IJavaProject javaProject = JavaCore.create(folder.getProject());
		boolean found = false;
		try {
			List<IClasspathEntry> cp = getClasspathEntries(javaProject);

			for (IClasspathEntry cpEntry : cp) {
				if (cpEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE &&
						cpEntry.getPath().equals(folder.getFullPath())) {
					found = true;
				}
			}
		} catch (JavaModelException e) {
			OfsCore.getDefault().logWarning("Could not access classpath for project " + folder.getProject().getName(),
					e);
		}
		return found;
	}
	
	/**
	 * @param folder
	 * @return
	 */
	public static boolean isClassFolder(IFolder folder) {
		IJavaProject javaProject = JavaCore.create(folder.getProject());
		boolean found = false;
		try {
			List<IClasspathEntry> cp = getClasspathEntries(javaProject);

			for (IClasspathEntry cpEntry : cp) {
				if (cpEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY &&
						cpEntry.getPath().equals(folder.getFullPath())) {
					found = true;
				}
			}
		} catch (JavaModelException e) {
			OfsCore.getDefault().logWarning("Could not access classpath for project " + folder.getProject().getName(),
					e);
		}
		return found;
	}

	/**
	 * Helper method which adds a folder to the classpath of the java project
	 * 
	 * @param project
	 *            the java project to add the source folder to
	 * @param folder
	 *            the source folder
	 * 
	 */
	public static void createSourceFolder(IProject project, IFolder folder) throws CoreException {
		createSourceFolder(project, folder, null);
	}

	/**
	 * Helper method which adds a folder to the classpath of the java project
	 * 
	 * @param project
	 *            the java project to add the source folder to
	 * @param folder
	 *            the source folder
	 * @param outputPath
	 *            a dedicated output path
	 * 
	 */
	public static void createSourceFolder(IProject project, IFolder folder, IPath outputPath) throws CoreException {
		IJavaProject javaProject = JavaCore.create(project);
		OfsCore.createFolder(folder);
		IClasspathEntry newSourceEntry = JavaCore.newSourceEntry(folder.getFullPath(), ClasspathEntry.EXCLUDE_NONE,
				outputPath);
		addClasspathEntry(javaProject, newSourceEntry);
	}

	/**
	 * Helper method which adds folders to the classpath of the java project
	 * 
	 * @param project
	 *            the java project to add the source folders to
	 * @param folders
	 *            the source folders
	 * @param outputPath
	 *            a dedicated output path for all folders
	 * 
	 */
	public static void createSourceFolders(IProject project, IFolder[] folders, IPath outputPath) throws CoreException {
		IJavaProject javaProject = JavaCore.create(project);
		List<IClasspathEntry> cpEntries = new ArrayList<IClasspathEntry>();
		for (IFolder folder : folders) {
			OfsCore.createFolder(folder);
			cpEntries.add(JavaCore.newSourceEntry(folder.getFullPath(), ClasspathEntry.EXCLUDE_NONE, outputPath));
		}
		addClasspathEntries(javaProject, cpEntries.toArray(new IClasspathEntry[0]));
	}
	
	/**
	 * @param genProject
	 * @param classFolder
	 */
	public static void createClassFolder(IProject project, IFolder folder) throws CoreException {
		IJavaProject javaProject = JavaCore.create(project);
		OfsCore.createFolder(folder);
		IClasspathEntry newClassFolderEntry = JavaCore.newLibraryEntry(folder.getFullPath(), null, null, true);
		List<IClasspathEntry> cpEntries = getClasspathEntries(javaProject);
		removeEmptySourceEntry(cpEntries);
		// Avoid duplicates
		removeExistingEntriesWithSamePath(cpEntries, newClassFolderEntry);
		cpEntries.add(newClassFolderEntry);
		javaProject.setRawClasspath(cpEntries.toArray(new IClasspathEntry[cpEntries.size()]), null);
	}

	private static void removeEmptySourceEntry(List<IClasspathEntry> classpath) {
		Iterator<IClasspathEntry> iterator = classpath.iterator();
		while (iterator.hasNext()) {
			IClasspathEntry entry = iterator.next();
			if(entry.getEntryKind() == IClasspathEntry.CPE_SOURCE && entry.getPath().segmentCount() == 1) {
				iterator.remove();
			}
		}
	}

	public static void addClasspathEntries(IJavaProject javaProject, IClasspathEntry[] cpEntries)
			throws JavaModelException {
		List<IClasspathEntry> cp = getClasspathEntries(javaProject);

		
		for (IClasspathEntry cpEntry : cpEntries) {
			// if no source folder has been added yet, the project root is
			// listed as a source
			// folder in the classpath. Hence this entry must be removed before
			// adding the first
			// "real" source classpath entry.
			if (cpEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				IClasspathEntry rootSourceEntry = null;
				for (IClasspathEntry entry : cp) {
					if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE
							&& entry.getPath().equals(javaProject.getPath())) {
						// if the project root is set as a source dir, remember
						// this entry
						rootSourceEntry = entry;
					}
				}
				// remove the project root source entry, if it was found
				if (rootSourceEntry != null) {
					cp.remove(rootSourceEntry);
				}
			}
			removeExistingEntriesWithSamePath(cp, cpEntry);
			if (!cp.contains(cpEntry)) {
				cp.add(cpEntry);
			}
		}
		javaProject.setRawClasspath(cp.toArray(new IClasspathEntry[0]), null);
	}

	private static void removeExistingEntriesWithSamePath(List<IClasspathEntry> classpath, IClasspathEntry newEntry) {
		Iterator<IClasspathEntry> iterator = classpath.iterator();
		while (iterator.hasNext()) {
			IClasspathEntry entry = iterator.next();
			if(entry.getPath().equals(newEntry.getPath())) {
				iterator.remove();
			}
		}
	}

	public static List<IClasspathEntry> getClasspathEntries(IJavaProject javaProject) throws JavaModelException {
		List<IClasspathEntry> cp = new ArrayList<IClasspathEntry>(Arrays.asList(javaProject.getRawClasspath()));
		return cp;
	}

	public static void addClasspathEntry(IJavaProject javaProject, IClasspathEntry cpEntry) throws JavaModelException {
		addClasspathEntries(javaProject, new IClasspathEntry[] { cpEntry });
	}

	/**
	 * Toggles technical nature on a project
	 * 
	 * @param project
	 *            to have nature added or removed
	 */
	static public void toggleNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (int i = 0; i < natures.length; ++i) {
				if (OfsTechnicalNature.NATURE_ID.equals(natures[i])) {
					// Remove the nature
					String[] newNatures = new String[natures.length - 1];
					System.arraycopy(natures, 0, newNatures, 0, i);
					System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					return;
				}
			}

			// Add the nature
			ProjectInitializer init = new CodeGenInitializer();
			init.initialize(project, false, null);
		} catch (CoreException e) {
			GenerationCore.getDefault().logError("Error in initializing technical nature", e);
		}
	}

	/**
	 * This method tells whether an Odyssey project is of technical type or not.
	 * In general, Odyssey projects are either sketching or technical projects -
	 * from an Eclipse perspective, this translate into having the
	 * "OfsTechnicalNature" or not
	 * 
	 * @param project
	 *            the project to check
	 * @return true, if project is of technical type
	 */
	static public boolean isTechnical(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (String nature : natures) {
				if (OfsTechnicalNature.NATURE_ID.equals(nature)) {
					return true;
				}
			}
		} catch (CoreException e) {
			GenerationCore.getDefault().logError("Cannot read project description of project " + project.getName(), e);
		}
		return false;
	}

}
