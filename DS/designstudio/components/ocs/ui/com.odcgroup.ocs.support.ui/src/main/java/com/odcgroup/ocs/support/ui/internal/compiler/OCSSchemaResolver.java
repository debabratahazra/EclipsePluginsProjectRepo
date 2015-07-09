package com.odcgroup.ocs.support.ui.internal.compiler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.ClasspathEntry;
import org.eclipse.jdt.internal.core.util.Util;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.common.uriresolver.internal.provisional.URIResolverExtension;

import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.installer.OcsBinariesExtractionFacade;
import com.odcgroup.ocs.support.ui.OCSSupportUICore;

/**
 * Resolver used to get all xsd from the following sources:
 * <ul>
 * <li>Project class path</li>
 * <li>OCS installation</li>
 * </ul>
 * @author yan
 */
@SuppressWarnings("restriction")
public class OCSSchemaResolver implements URIResolverExtension {

	private final static File ALL_OCS_SCHEMA = new File(OCSSupportUICore.getDefault().getStateLocation().toFile(), "AllOCSSchema");

	private static Map<String, URL> OCS_RUNTIME_SCHEMA;

	private static Job extractionJob;
	private static Job waitingJob;

	public OCSSchemaResolver() {
	}

	/**
	 * @see org.eclipse.wst.common.uriresolver.internal.provisional.URIResolverExtension#resolve(org.eclipse.core.resources.IFile,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public String resolve(IFile ifile, String s, String publicId,
			String systemId) {
		if (systemId != null) {
			systemId = systemId.trim();

			if (systemId.length() > 0 && systemId.endsWith(".xsd")) {
				systemId = systemId.replace('\\', '/');
				int pos = systemId.lastIndexOf('/');
				String schemaFilename = (pos == -1) ? systemId : systemId
						.substring(pos + 1);
				String name = "META-INF/schemas/" + schemaFilename;

				URL url = null;

				// First look in the project classpath. We must do that to make sure
				// that if we are actually changing the schema in the current project then the xml file will
				// be validated against the new version version of the schema and not the one in the OCS runtime
				if (ifile != null) {
					url = getResource(ifile.getProject(), name);
				}

				// Then look in the OCS runtime
				if (url == null) {
					url = getOcsRuntimeSchema(schemaFilename);
				}

				if (url != null) {
					return url.toString();
				}
			}
		}

		return null;
	}

	private URL getResource(IProject project, String path) {
		try {
			IStorage storage = lookupProject(project, path, new HashSet<IProject>());
			if (storage == null){
				return null;
			} else {
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IFile file = workspace.getRoot().getFile(storage.getFullPath());
				if (file == null){
					return null;
				} else {
					URL resolvedURL = file.getLocationURI().toURL();
					return resolvedURL;
				}
			}
		} catch (MalformedURLException e) {
			OCSSupportUICore.getDefault().logWarning(e.getMessage(), e);
			return null;
		}
	}

	private IStorage lookupProject(IProject project, String resource, Set<IProject> visitedProjects) {
		// DS-2361
		if(visitedProjects.contains(project)) return null;
		visitedProjects.add(project);

		if(project==null || !project.exists()) return null;
		try {
			if (project.hasNature(JavaCore.NATURE_ID)) {
				return lookupJavaClasspath(JavaCore.create(project), resource, visitedProjects);
			} else {
				return lookupProjectRoot(project, resource, visitedProjects);
			}
		} catch (CoreException e) {
			OCSSupportUICore.getDefault().logWarning(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * This method consider the given Java project classpath as the resource
	 * container.
	 * @param visitedProjects
	 */
	private IStorage lookupJavaClasspath(IJavaProject project, String resource, Set<IProject> visitedProjects) {
		IPath path = new Path(resource);
		IPath packagePath = path.removeLastSegments(1);
		String resourceName = path.lastSegment();
		String packageName = packagePath.toString().replace('/',
				'.');

		if (packageName.startsWith("/")){
			packageName = packageName.substring(1);
		}

		try {
			IClasspathEntry[] classpath = project.getResolvedClasspath(true);
			for (int i = 0; i < classpath.length; i++) {
				IStorage resolved = null;

				if (classpath[i].getEntryKind() == IClasspathEntry.CPE_PROJECT) {
					IWorkspaceRoot root = project.getProject().getWorkspace()
							.getRoot();
					IProject ref = root.getProject(classpath[i].getPath()
							.toString());
					resolved = lookupProject(ref, resource, visitedProjects);
				} else {
					resolved = lookupClasspathEntry(project, classpath[i],
							packageName, resourceName);
				}

				if (resolved != null)
					return resolved;
			}
		} catch (JavaModelException e) {
			OCSSupportUICore.getDefault().logWarning(e.getMessage(), e);
		}

		return null;
	}

	private IStorage lookupClasspathEntry(IJavaProject project,
			IClasspathEntry entry, String packageName, String resourceName) {
		try {
			IPackageFragmentRoot[] fragmentRoots = project
					.findPackageFragmentRoots(entry);

			for (int i = 0; i < fragmentRoots.length; i++) {
				IPackageFragment fragment = fragmentRoots[i]
						.getPackageFragment(packageName);

				if (fragment.exists()) {
					Object[] resources = fragment.getNonJavaResources();

					for (int j = 0; j < resources.length; j++) {
						if (resources[j] instanceof IStorage) {
							IStorage storage = (IStorage) resources[j];
							IPath path = storage.getFullPath();

							if (path.lastSegment().equals(resourceName)) {
								if (!Util.isExcluded(path,
										((ClasspathEntry) entry)
												.fullInclusionPatternChars(),
										((ClasspathEntry) entry)
												.fullExclusionPatternChars(),
										false)) {
									return storage;
								}
							}
						}
					}
				}
			}
		} catch (JavaModelException e) {
			OCSSupportUICore.getDefault().logWarning(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * This method consider the root of this project and that of its referenced
	 * projects as the resource containers.
	 * @param visitedProjects
	 */
	private IStorage lookupProjectRoot(IProject project, String resource, Set<IProject> visitedProjects) {
		IFile resourceFile = project.getFile(resource);
		if (resourceFile.exists()) {
			return resourceFile;
		}

		try {
			IProject[] references = project.getReferencedProjects();
			for (int i = 0; i < references.length; i++) {
				IStorage resolved = lookupProject(references[i], resource, visitedProjects);
				if (resolved != null)
					return resolved;
			}
		} catch (CoreException e) {
			OCSSupportUICore.getDefault().logWarning(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * @param schema
	 * @return the url for a specific schema or {@code null} if it doesn't exist
	 */
	private URL getOcsRuntimeSchema(String schema) {
		waitForExtractionJobCompletion();
		synchronized (OCSSchemaResolver.class) {
			if (OCS_RUNTIME_SCHEMA == null) {
				OCS_RUNTIME_SCHEMA = new HashMap<String, URL>();
				List<File> ocsSchemas = getOcsSchemas();
				for (File ocsSchema : ocsSchemas) {
					try {
						OCS_RUNTIME_SCHEMA.put(ocsSchema.getName(), ocsSchema.toURI().toURL());
					} catch (MalformedURLException e) {
						OCSPluginActivator.getDefault().logError("Unable to read " + ocsSchema, e);
						e.printStackTrace();
					}
				}
			}
			return OCS_RUNTIME_SCHEMA.get(schema);
		}
	}


	private List<File> getOcsSchemas() {
		if (!ALL_OCS_SCHEMA.exists()) {
			extractOcsSchemas();
		}

		return Arrays.asList(ALL_OCS_SCHEMA.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith(".xsd");
            }
        }));
	}

	/**
	 * @return {@code true} if the ocs schemas extraction has not been done,
	 * {@code false} otherwise
	 */
	public static boolean requiresOcsSchemasExtraction() {
		return !ALL_OCS_SCHEMA.exists();
	}

	/**
	 * Extract all xsd schemas from the OCS installation
	 */
	public static void extractOcsSchemas() {
		// Create the folder if required
		ALL_OCS_SCHEMA.mkdirs();

		// Remove all existing xsd files
		for (File fileToDelete: ALL_OCS_SCHEMA.listFiles()) {
			fileToDelete.delete();
		}

		// Extract all files
		File ocsBinariesExtractFolder = OcsBinariesExtractionFacade.instance().getOcsBinariesExtractFolder();

		final List<File> allJar = listAllJar(ocsBinariesExtractFolder);

		extractionJob = new Job("Searching for XML schemas in Triple'A Plus repository...") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Searching for schemas...", allJar.size());
				for (File file : allJar) {
					monitor.worked(1);
					monitor.setTaskName("Scanning " + file.getName() + "...");
					try {
						extractXsdFromJar(file, ALL_OCS_SCHEMA);
					} catch (IOException e) {
						OCSSupportUICore.getDefault().logError("Unable to extract schema from " + file, e);
					}
					if (monitor.isCanceled()) {
						break;
					}
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		extractionJob.schedule();
	}

	/**
	 * wait for job extraction to be finished
	 */
	private static void waitForExtractionJobCompletion() {
		// Check if the extraction job is running
		if (extractionJob != null && (extractionJob.getState() == Job.RUNNING ||
				extractionJob.getState() == Job.WAITING)) {

			// If the waiting job doesn't exist, create it
			if (waitingJob == null ||
					(waitingJob.getState()!=Job.RUNNING && waitingJob.getState()!=Job.WAITING)) {
				waitingJob = new Job("Waiting for XML schemas in Triple'A Plus repository to finish") {
					protected IStatus run(IProgressMonitor monitor) {
						try {
							extractionJob.join();
						} catch (InterruptedException e) {
						}
						return Status.OK_STATUS;
					}
				};
				waitingJob.setUser(true);
				waitingJob.schedule();
			}

			// Note: calling waitingJob.join(); freezes the UI as this code is called
			// from the UI thread
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					while (waitingJob != null &&
							(waitingJob.getState() == Job.WAITING || waitingJob.getState() == Job.RUNNING)) {
						Display.getDefault().readAndDispatch();
					}
				}
			});
			waitingJob = null;
		}
	}

	/**
	 * @param folder
	 * @return
	 */
	private static List<File> listAllJar(File folder) {
		List<File> allJars = new ArrayList<File>();
		buildList(folder, allJars);
		return allJars;
	}

	/**
	 * @param folder
	 * @param allJars
	 */
	private static void buildList(File file, List<File> allJars) {
		if (file!= null && file.isDirectory()) {
			for (File f: file.listFiles(
					new FileFilter() {
						public boolean accept(File pathname) {
							return pathname.isDirectory();
						}
					})) {
				buildList(f, allJars);
			}
			for (File f: file.listFiles(
					new FileFilter() {
						public boolean accept(File pathname) {
							return pathname.isFile() && pathname.getName().endsWith(".jar");
						}
					})) {
				allJars.add(f);
			}
		}
	}

	/**
	 * Extract all xsd under META-INF/schemas in the jar file to allOcsSchema folder
	 * @param file
	 * @param allOcsSchema
	 * @throws IOException
	 */
	private static void extractXsdFromJar(File jar, File allOcsSchema) throws IOException {
		try {
			final int BUFFER = 2048;
			final String META_INF_SCHEMAS = "META-INF/schemas";
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(jar);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				if (entry.getName().startsWith(META_INF_SCHEMAS) &&
						entry.getName().endsWith(".xsd")) {
					int count;
					byte data[] = new byte[BUFFER];
					// write the files to the disk
					FileOutputStream fos = new FileOutputStream(new File(allOcsSchema, StringUtils.removeStart(entry.getName(), META_INF_SCHEMAS)));
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				}
			}
			zis.close();
		} catch (Exception e) {
			throw new IOException("Unable to extract schema from " + jar, e);
		}

	}

}
