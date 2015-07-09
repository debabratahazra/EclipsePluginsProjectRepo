package com.odcgroup.workbench.generation.init;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.internal.resources.ProjectDescription;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.google.common.base.Charsets;
import com.odcgroup.workbench.core.IContainerIdentifier;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.AbstractProjectInitializer;
import com.odcgroup.workbench.core.repository.MavenContainerIdentifier;
import com.odcgroup.workbench.core.repository.maven.MavenDependencyHelper;
import com.odcgroup.workbench.core.repository.maven.PomTemplatesHelper;
import com.odcgroup.workbench.core.targetplatform.TargetPlatform;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.init.classpath.OutputPathChecker;

public class CodeGenInitializer extends AbstractProjectInitializer {

    private static final Charset TEMPLATES_GEN_POM_XML__Encoding = Charsets.US_ASCII; // Doesn't contain any "special characters". If it did, watch out - it depends on the OS it's being edited with and possible the source control system mangling it..
    
	private static final String STD_PRJ_PROVIDER_EXTENSION_ID = GenerationCore.PLUGIN_ID + ".standardProjectProvider";

	private static final String PDE_PLUGIN_NATURE = "org.eclipse.pde.PluginNature";

	private static final String ODYSSEY_CLASSPATH_ID = "ODYSSEY_MAVEN_CLASSPATH";
	public static final Path ODYSSEY_CLASSPATH = new Path(ODYSSEY_CLASSPATH_ID);
	
	public static final String TARGET_CLASSES = "/target/classes";
	
	// this is public instead of package/default just so that the test which is now in a plug-in instead of a fragment can acess it..
	public static final String[] compilerOptions = new String[] {
			JavaCore.COMPILER_PB_UNUSED_IMPORT,
			JavaCore.COMPILER_PB_RAW_TYPE_REFERENCE,
			JavaCore.COMPILER_PB_UNCHECKED_TYPE_OPERATION,
			JavaCore.COMPILER_PB_UNUSED_PRIVATE_MEMBER,
			JavaCore.COMPILER_PB_MISSING_SERIAL_VERSION,
			JavaCore.COMPILER_PB_DEAD_CODE,
			JavaCore.COMPILER_PB_DEPRECATION
	};
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.init.AbstractProjectInitializer#updateConfiguration(org.eclipse.core.resources.IProject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void updateConfiguration(final IProject project, IProgressMonitor monitor)
			throws CoreException {
		
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {
				for(String sourceFolderName : GenerationCore.getJavaSourceFolderNames(project)) {
					configureJavaSourceFolder(project, sourceFolderName, monitor);
				}

				for(String sourceFolderName : GenerationCore.getClassFolderNames(project)) {
					configureClassFolder(project, sourceFolderName, monitor);
				}

				setCodeGenNature(project, monitor);	
				
				setJavaCompilerOptions(project, monitor);	
				
				for (String genProjectName : GenerationCore.getJavaProjectNames(project)) {
					IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(genProjectName);
					updateGenPomFile(project, genProject);
					IJavaProject javaProject = JavaCore.create(genProject);
					javaProject.setOutputLocation(new Path("/" + genProject.getName() + TARGET_CLASSES), monitor);
				}
			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, monitor);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.core.init.AbstractProjectInitializer#checkConfiguration(org.eclipse.core.resources.IProject)
	 */
	public IStatus checkConfiguration(IProject project, IResourceDelta delta) {
		MultiStatus status = (MultiStatus) super.checkConfiguration(project, delta);
		
		// check for other configuration
		for(String projectName : GenerationCore.getJavaProjectNames(project)) {
			
			// check that the gen project exists
			IProject outputProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if(!outputProject.exists()) {
				IStatus newError = 
					new Status(IStatus.ERROR, 
							GenerationCore.PLUGIN_ID, 
							"Project '" + projectName + "' does not exist, but is required for code generation.");
				status.add(newError);
			} else {

				// check if all required source folders exist in the gen project
				for(String sourceFolderName : GenerationCore.getJavaSourceFolderNames(project, projectName)) {
					String folderName = sourceFolderName.substring(projectName.length());
					if (GenerationCore.isJavaSourceFolder(outputProject.getFolder(folderName))) {
						IFolder folder = outputProject.getFolder(folderName);
						if(!folder.exists()) {
							try {
								OfsCore.createFolder(folder);
							} catch (CoreException e) {
								// Ignore
							}
						}
					} else {
						IStatus newError = new Status(IStatus.ERROR, 
								GenerationCore.PLUGIN_ID, 
								"Source folder '" + folderName + 
								"' in project '" + projectName +
								"' does not exist, but is required for code generation.");
						status.add(newError);
					}
				}
				
				for(String classFolderName : GenerationCore.getClassFolderNames(project)) {
					String folderName = classFolderName.substring(projectName.length());
					if (GenerationCore.isClassFolder(outputProject.getFolder(folderName))) {
						IFolder folder = outputProject.getFolder(folderName);
						if(!folder.exists()) {
							try {
								OfsCore.createFolder(folder);
							} catch (CoreException e) {
								// Ignore
							}
						}
					} else {
						IStatus newError = new Status(IStatus.ERROR, 
								GenerationCore.PLUGIN_ID, 
								"Class folder '" + folderName + 
								"' in project '" + projectName +
								"' does not exist, but is required for code generation.");
						status.add(newError);
					}
				}
				
				IStatus srcClasspathStatus = checkOutputLocations(outputProject, Arrays.asList(GenerationCore.getJavaSourceFolderNamesSlashPrefixed(project, projectName)));
				addToStatusIfNotOK(status, srcClasspathStatus);
				
				IStatus compilerStatus = checkJavaCompilerOptions(outputProject);
				addToStatusIfNotOK(status, compilerStatus);
				
				IStatus mavenStatus = checkOdysseyClasspath(outputProject);
				addToStatusIfNotOK(status, mavenStatus);
			}
		}

		return status;
	}
	
	/**
	 * @param outputProject
	 * return if the pom file exist in the ocs repo folder.
	 */
	public boolean isGenPomExistInRepoFolder(IProject outputProject) {
			MavenContainerIdentifier container = getStandardProjectContainerIdentifier(outputProject);
			if (container !=null && getStandardGenProjectPomFile(container) == null) {
				return false;
			}
		
		return true;
	}

	/**
	 * Checks that some java compiler options have the required value
	 * @param genProject the gen project
	 */
	private IStatus checkJavaCompilerOptions(IProject genProject) {
		MultiStatus status = new MultiStatus(GenerationCore.PLUGIN_ID, IStatus.OK, "ok", null);
		IJavaProject javaProject = JavaCore.create(genProject);
		for(String option : compilerOptions) {
			String value = javaProject.getOption(option, false);
		if (value == null) {
			// try to read the inherited value
				value = javaProject.getOption(option, true);
		}
			if ((value == null) || !value.equals("ignore")) {
			IStatus newError = 
				new Status(IStatus.ERROR, 
						GenerationCore.PLUGIN_ID, 
							"Java compiler options must be set to 'Ignore'.");
			status.add(newError);
				break;
		}
		}
		return status;
	}
	
	private void setJavaCompilerOptions(IProject project, IProgressMonitor monitor) throws CoreException {
		for(String projectName : GenerationCore.getJavaProjectNames(project)) {
			IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if (!checkJavaCompilerOptions(genProject).isOK()) {
				IJavaProject javaProject = JavaCore.create(genProject);
				for(String option : compilerOptions) {
					javaProject.setOption(option, "ignore");
				javaProject.save(monitor, true);
			}
		}
	}
	}

	/**
	 * @param outputProject
	 * @return
	 */
	private IStatus checkOdysseyClasspath(IProject project) {
		MultiStatus status = new MultiStatus(GenerationCore.PLUGIN_ID, IStatus.OK, "ok", null);
		
		IJavaProject javaProject = JavaCore.create(project);
		try {
			boolean odysseyContainerFound = false;
			for (IClasspathEntry cp : javaProject.getRawClasspath()) {
				if (cp.getPath().equals(ODYSSEY_CLASSPATH)) {
					odysseyContainerFound = true;
					break;
				}
			}
			if (odysseyContainerFound) {
				status.add(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 
						project.getName() + " project must not have an Design Studio Dependencies classpath."));
			}
		} catch (JavaModelException e) {
			// donothing
		}
		
		try {
			IProjectDescription description = project.getDescription();
			if (Arrays.asList(description.getNatureIds()).contains(PDE_PLUGIN_NATURE)) {
				status.add(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 
						project.getName() + " project must not have the PDE nature."));
			}
		} catch (CoreException e) {
			// donothing
		}
		
		return status;
	}

	private IStatus checkOutputLocations(IProject project, Collection<String> javaSourceFolders) {
		IJavaProject javaProject = JavaCore.create(project);
		return new OutputPathChecker().check(javaProject, javaSourceFolders);
	}

	private void addToStatusIfNotOK(MultiStatus overallStatus, IStatus status) {
		if (!status.isOK()) {
			overallStatus.addAll(status);
		}
	}

	private void setCodeGenNature(IProject project, IProgressMonitor monitor) throws CoreException {
		IProjectDescription desc = project.getDescription();
		ArrayList<String> natureIds = new ArrayList<String>(Arrays.asList(desc.getNatureIds()));
		if(!natureIds.contains(GenerationCore.TECHNICAL_NATURE_ID)) {
			natureIds.add(0, GenerationCore.TECHNICAL_NATURE_ID);
			desc.setNatureIds(natureIds.toArray(new String[0]));
			project.setDescription(desc, monitor);
		}	
	}
	
	private IProject configureJavaProject(IProject project,
			String name, String natureIds[],
			IProject referencedProjects[], IProgressMonitor monitor) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject genProject;
		genProject = root.getProject(name);
		if (!genProject.exists()) {
			// location needs to be set when creating the project
			// the rest of the project description can come later
			IProjectDescription desc = new ProjectDescription();
			desc.setName(project.getName());
			IPath location = project.getLocation().removeLastSegments(1);
			if(!location.equals(ResourcesPlugin.getWorkspace().getRoot().getLocation())) {
				desc.setLocation(location.append(genProject.getName()));
			}
			genProject.create(desc, null);
		}
		if (!genProject.isOpen())
			genProject.open(null);
		IProjectDescription desc = genProject.getDescription();
		String[] projectNatureIds = desc.getNatureIds();
		for(String natureId : natureIds) {
			if(!ArrayUtils.contains(projectNatureIds, natureId)) {
				projectNatureIds = (String[]) ArrayUtils.add(projectNatureIds, natureId);
			}
		}
		
		// For migration sake, the old pde nature is removed
		if (ArrayUtils.contains(projectNatureIds, PDE_PLUGIN_NATURE)) {
			projectNatureIds = (String[]) ArrayUtils.removeElement(projectNatureIds, PDE_PLUGIN_NATURE);
		}

		// For migration sake, the old odyssey classpath container is removed
		IJavaProject javaProject = JavaCore.create(project);
		if (javaProject.exists()) {
			IClasspathEntry[] classpathEntries = javaProject.getRawClasspath();
			try {
				IClasspathEntry odysseyContainer = null;
				for (IClasspathEntry cp : javaProject.getRawClasspath()) {
					if (cp.getPath().equals(ODYSSEY_CLASSPATH)) {
						odysseyContainer = cp;
						break;
					}
				}
				if (odysseyContainer != null) {
					classpathEntries = (IClasspathEntry[])ArrayUtils.removeElement(classpathEntries, odysseyContainer);
					javaProject.setRawClasspath(classpathEntries, monitor);
				}

			} catch (JavaModelException e) {
				// donothing
			}
		}
		
		desc.setNatureIds(projectNatureIds);
		desc.setReferencedProjects(referencedProjects);
		genProject.setDescription(desc, null);
		
		return genProject;
	}

	private IProject configureJavaSourceFolder(IProject project, String sourceFolderName, IProgressMonitor monitor) throws CoreException {
		if(sourceFolderName==null) return null;
		String projectName = sourceFolderName.split("/")[0];
		IProject genProject = 
			configureJavaProject(project,
					projectName, 
					new String[] { JavaCore.NATURE_ID }, 
					new IProject[]{}, monitor);
		IJavaProject javaProject = JavaCore.create(genProject);

		sourceFolderName = sourceFolderName.substring(projectName.length());
		String targetFolderName = sourceFolderName.contains("src")?
				sourceFolderName.replace("src", "target") : "target" + sourceFolderName;

		IFolder sourceFolder = genProject.getFolder(sourceFolderName);
		IFolder targetFolder = genProject.getFolder(targetFolderName);
		IFolder defaultTargetFolder = genProject.getFolder(TARGET_CLASSES);
		OfsCore.createFolder(sourceFolder);
		OfsCore.createFolder(targetFolder);
		OfsCore.createFolder(defaultTargetFolder);
		
		javaProject.setOutputLocation(defaultTargetFolder.getFullPath(), null);

		IClasspathEntry jreEntry = JavaCore.newContainerEntry(new Path("org.eclipse.jdt.launching.JRE_CONTAINER"), false);	
		GenerationCore.addClasspathEntry(javaProject, jreEntry);

		try {
			GenerationCore.createSourceFolder(genProject, sourceFolder);
		} catch(JavaModelException jme) {
			// source folder seems to be nested in some other source folder
			// don't do anything as the validation will add an error to the problem sheet
		}
		
		return genProject;
	}
	
	private IProject configureClassFolder(IProject project, String classFolderName, IProgressMonitor monitor) throws CoreException {
		if(classFolderName==null) return null;
		String projectName = classFolderName.split("/")[0];
		IProject genProject = 
			configureJavaProject(project,
					projectName, 
					new String[] { JavaCore.NATURE_ID }, 
					new IProject[]{}, monitor);
		IJavaProject javaProject = JavaCore.create(genProject);

		classFolderName = classFolderName.substring(projectName.length());

		IFolder classFolder = genProject.getFolder(classFolderName);
		OfsCore.createFolder(classFolder);
		
		try {
			GenerationCore.createClassFolder(genProject, classFolder);
		} catch(JavaModelException jme) {
			// source folder seems to be nested in some other source folder
			// don't do anything as the validation will add an error to the problem sheet
		}
		
		return genProject;
	}
	
	private void createPomFileFromTemplate(IProject modelProject, IProject genProject) throws CoreException {
		IFile pomFile = genProject.getFile("pom.xml");
		
		if (!pomFile.exists()) {
			String pomContent = getPomContent(modelProject, genProject);
			InputStream is = new ByteArrayInputStream(pomContent.getBytes(TEMPLATES_GEN_POM_XML__Encoding));
			pomFile.create(is, IFile.FORCE, null);
			IOUtils.closeQuietly(is);
		}
	}

	private static String getPomContent(IProject modelProject, IProject genProject)
			throws CoreException {
		String pomContent = null;

		// Generate the initial pom using a template
		pomContent = PomTemplatesHelper.getPomTemplatesProvider().getModelGenPomTemplate();

		IOfsProject ofsModelProject = OfsCore.getOfsProjectManager().getOfsProject(modelProject);
		IContainerIdentifier identifier = ofsModelProject.getIdentifier();
		pomContent = StringUtils.replace(pomContent, "${ds.artifactId}", genProject.getName());
		pomContent = StringUtils.replace(pomContent, "${ds.target.platform.version}", TargetPlatform.getTechnicalVersion());
		if (identifier instanceof MavenContainerIdentifier) {
			MavenContainerIdentifier mavenId = (MavenContainerIdentifier)identifier;
			pomContent = StringUtils.replace(pomContent, "${ds.groupId}", mavenId.getGroupId());
		} else {
			pomContent = StringUtils.replace(pomContent, "${ds.groupId}", identifier.getName());
		}

		IOfsProject ofsProject = OfsCore.getOfsProject(modelProject);
		IContainerIdentifier id = ofsProject.getIdentifier();
		pomContent = StringUtils.replace(pomContent, "${ds.models.groupId}", id.getGroupId());
		pomContent = StringUtils.replace(pomContent, "${ds.models.artifactId}", id.getName());

		// Add the model dependency to the gen project
		Set<IContainerIdentifier> modelDependencies = getDependencies(ofsModelProject);
		
		try {
			final SAXBuilder builder = new SAXBuilder();
			final InputStream is = new ByteArrayInputStream(pomContent.getBytes(TEMPLATES_GEN_POM_XML__Encoding));
			final Document pomDocument = builder.build(is);
			if (modelDependencies.size() != 0) {
				for (IContainerIdentifier modelDependency : modelDependencies) {
					String genDependencyName = modelDependency.getName() + "-gen";
					MavenDependencyHelper.addDependency(pomDocument, modelDependency.getGroupId(), genDependencyName, "${project.version}");
				}
				XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
				StringWriter pomWriter = new StringWriter();
				outputter.output(pomDocument, pomWriter);
				
				return pomWriter.toString();
			} else {
				return pomContent;
			}
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, 
					"Error updating pom.xml for project " + genProject.getName(), e);
			throw new CoreException(status);
		}

	}
	
	private static Set<IContainerIdentifier> getDependencies(IOfsProject ofsModelProject) throws CoreException {
		Set<IContainerIdentifier> deps = new HashSet<IContainerIdentifier>();
		
		IFile pomFile = ofsModelProject.getProject().getFile("pom.xml");
		try {
			SAXBuilder builder = new SAXBuilder();
			Document pomDocument = builder.build(pomFile.getLocation().toFile());

			Element dependencies = pomDocument.getRootElement().getChild("dependencies", pomDocument.getRootElement().getNamespace());
			if(dependencies!=null) {
				for(Object obj : dependencies.getChildren()) {
					if(obj instanceof Element) {
						final Element element = (Element) obj;
						final String groupId = element.getChildText("groupId", pomDocument.getRootElement().getNamespace());
						final String name = element.getChildText("artifactId", pomDocument.getRootElement().getNamespace());
						final String version = element.getChildText("version", pomDocument.getRootElement().getNamespace());
						if (element.getChild("type") == null || !element.getChildText("type").equals("ds-models"))
							continue; // Ignore non ds-models dependencies
						IContainerIdentifier id = new IContainerIdentifier() {
							public String getGroupId() {
								return groupId;
							}
							public String getVersion() {
								return version;
							}
							
							public String getName() {
								return name;
							}
						};
						deps.add(id);
					}
				}
			}
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, 
					"Error reading pom.xml for project " + ofsModelProject.getName(), e);
			throw new CoreException(status);
		}
		return deps;
	}

	@Override
	public IProject[] getProjectsToWatch(IProject project) {
		String[] depProjectNames = GenerationCore.getJavaProjectNames(project);
		IProject[] depProjects = new IProject[depProjectNames.length];
		for(int i=0; i<depProjectNames.length; i++) {
			depProjects[i] = ResourcesPlugin.getWorkspace().getRoot().getProject(depProjectNames[i]);
		}
		return depProjects;
	}
	
	private void updateGenPomFile(IProject modelProject, IProject genProject) throws CoreException {
		IFile pomFile = genProject.getFile("pom.xml");
		if (pomFile.exists()) {
			return;
		}
		
		File stdPomFile = getStandardGenProjectPomFile(getStandardProjectContainerIdentifier(modelProject));
		if(stdPomFile!=null) {
			try {
				FileInputStream is = null;
				try {
					is = new FileInputStream(stdPomFile);
					pomFile.create(is, IFile.FORCE, null);
					pomFile.refreshLocal(IResource.DEPTH_ZERO, null);
				} finally {
					IOUtils.closeQuietly(is);
				}
			} catch (IOException e) {
				GenerationCore.getDefault().logError("Cannot read the packaged standard gen project pom.xml for the project " + modelProject.getName(), e);
			}
		} else {
			createPomFileFromTemplate(modelProject, genProject);
		}
	}

	protected File getStandardGenProjectPomFile(IContainerIdentifier containerIdentifier) {
		if (containerIdentifier == null) {
			return null;
		}
		
		IConfigurationElement[] extensions = OfsCore.getExtensions(STD_PRJ_PROVIDER_EXTENSION_ID);
		for(IConfigurationElement extension : extensions) {
			File file = null;
			try {
				IStandardProjectProvider provider = (IStandardProjectProvider) extension.createExecutableExtension("class");
				file = provider.getGenPomFile(containerIdentifier);
				if(file!=null) return file;
			} catch (CoreException e) {
				GenerationCore.getDefault().logError("Cannot initialize standard project provider " + extension.getAttribute("name"), e); 
			}
		}
		return null;
	}
	
	// this is public instead of protected just so that the test which is now in a plug-in instead of a fragment can acess it..
	public MavenContainerIdentifier getStandardProjectContainerIdentifier(IProject project) {
		IConfigurationElement[] extensions = OfsCore.getExtensions(STD_PRJ_PROVIDER_EXTENSION_ID);
		for(IConfigurationElement extension : extensions) {
			File file = null;
			try {
				IStandardProjectProvider provider = (IStandardProjectProvider) extension.createExecutableExtension("class");
				for (IContainerIdentifier id : provider.getContainerIdentifiers()) {
					if (project.getName().equals(id.getName())) {
						return (MavenContainerIdentifier)id;
					}
				}
			} catch (CoreException e) {
				GenerationCore.getDefault().logError("Cannot initialize standard project provider " + extension.getAttribute("name"), e); 
			}
		}
		return null;
	}
	

}
