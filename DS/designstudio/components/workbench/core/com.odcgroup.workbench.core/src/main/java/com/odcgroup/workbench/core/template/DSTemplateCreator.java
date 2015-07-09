package com.odcgroup.workbench.core.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;

import com.odcgroup.template.ITemplateProvider;
import com.odcgroup.template.TemplateCreator;
import com.odcgroup.template.TemplateDescriptor;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.ProjectInitializer;
import com.odcgroup.workbench.core.targetplatform.TargetPlatform;

/**
 * @author yan
 */
public class DSTemplateCreator {

	private static final String RULE_RULES_RULES = "rule/rules/rules_";

	static final private Logger logger = org.slf4j.LoggerFactory.getLogger(DSTemplateCreator.class);

	/** Extension point */
	private static final String TEMPATE_PROVIDER_EXTENSION_POINT = "com.odcgroup.workbench.core.template.provider";

	/** Only initialize the template creator once */
	private static boolean initialized = false;
	
	private static ITemplateProvider2 templateProvider;
	
	public static List<TemplateDescriptor> getAvailableTemplates() {
		setupTemplateCreator();
		List<TemplateDescriptor> result = new ArrayList<TemplateDescriptor>();
		result.add(new SeparatorTemplateDescriptor(""));
		result.add(new SeparatorTemplateDescriptor("config -----------------------"));
		result.addAll(getAvailableStandardModelsTemplate());
		result.add(new SeparatorTemplateDescriptor("custo ------------------------"));
		result.addAll(TemplateCreator.instance().getAvailableTemplates());
		return result;
	}
	
	private static List<TemplateDescriptor> getAvailableStandardModelsTemplate() {
		List<TemplateDescriptor> standardModelsTemplate = new ArrayList<TemplateDescriptor>();
		// TODO read resource
		String[] res1 = new String[] {"com.odcgroup.ocs.cbi", "pms-models", "PMS models"};
		String[] res2 = new String[] {"com.odcgroup.ocs.cbi", "cbi-models", "CBI models"};
		String[] res3 = new String[] {"com.odcgroup.ocs.wui", "wui-admin-models", "WUI Admin models"};
		String[] res4 = new String[] {"com.odcgroup.ocs.cbi", "cdm-wui-models", "CDM wui models"};
		String[] res5 = new String[] {"com.odcgroup.ocs.mms", "mms-models", "MMS models"};
		String[] res6 = new String[] {"com.odcgroup.ocs.nbox", "nbox-models", "NBOX models"};
		String[][] resources = new String[][]{res1, res2, res3, res4, res5, res6};
		for (String[] resource: resources) {
			String groupId = resource[0];
			String modelsProjectName = resource[1];
			String genProjectName = resource[1] + "-gen";
			String description = resource[2];
			
			File standardModelsJar = resolveArtifact(groupId, modelsProjectName, TargetPlatform.getTechnicalVersion(), "standard", "jar");
			File standardModelsPom = resolveArtifact(groupId, modelsProjectName, TargetPlatform.getTechnicalVersion(), "pom", "xml");
			File standardGenProjectPom = resolveArtifact(groupId, genProjectName, TargetPlatform.getTechnicalVersion(), "pom", "xml");
			
			if (standardModelsJar != null && standardModelsPom != null && standardGenProjectPom != null) {
				StandardModelsTemplateDescriptor template = new StandardModelsTemplateDescriptor(
						modelsProjectName,
						genProjectName,
						standardModelsJar, 
						standardModelsPom, 
						standardGenProjectPom, 
						modelsProjectName, 
						description);
				
				standardModelsTemplate.add(template);
			}
		}
		
		return standardModelsTemplate;
	}

	private static File resolveArtifact(String groupId, String artifactId,
			String version, String classifier, String type) {
		setupTemplateCreator();
		
		File file = templateProvider.resolveArtifact(groupId, artifactId, version, classifier, type);
		if (file == null) {
			logger.error("Unable to find standard models/gen/pom file: (groupId="+groupId+",artifactId="+artifactId+",version="+version+"/"+version+"-SNAPSHOT,type="+type+",classifier="+classifier+")");
			logger.info("Ignoring model temlpate: (groupId="+groupId+",artifactId="+artifactId+",version="+version+"/"+version+"-SNAPSHOT,type="+type+",classifier="+classifier+")");
		}
		return file;
	}

	public static void createTemplate(TemplateDescriptor descriptor, Map<String, String> parameters, File destinationFolder) throws IOException, CoreException {
		setupTemplateCreator();
		if (descriptor instanceof StandardModelsTemplateDescriptor) {
			StandardModelsTemplateDescriptor stdDescriptor = (StandardModelsTemplateDescriptor)descriptor;
			unjarStandard(stdDescriptor, parameters, destinationFolder);
		} else {
			TemplateCreator.instance().createTemplate(descriptor, parameters, destinationFolder);
		}
	}
	
	private static void unjarStandard(StandardModelsTemplateDescriptor stdDescriptor, 
			Map<String, String> parameters, 
			File destinationFolder) throws IOException, CoreException {
		File modelProjectFolder = new File(destinationFolder, stdDescriptor.getStandardModelsName());
		createStandardTemplate(stdDescriptor, parameters, modelProjectFolder);
		
		replacePom(modelProjectFolder, stdDescriptor.getStandardModelsPom());
		
		IProject modelsProject = ResourcesPlugin.getWorkspace().getRoot().getProject(stdDescriptor.getStandardModelsName());
		modelsProject.create(null);
		modelsProject.open(null);
		for (ProjectInitializer initializer: OfsCore.getProjectInitializers(modelsProject, false)) {
			initializer.initialize(modelsProject, true, null);
		}
		
		File genProjectFolder = new File (destinationFolder, stdDescriptor.getStandardGenName());
		replacePom(genProjectFolder, stdDescriptor.getStandardGenPom());
	}

	private static void createStandardTemplate(
			StandardModelsTemplateDescriptor descriptor,
			Map<String, String> parameters, File destinationFolder) throws IOException {
		// TODO: merge with template-lib in com.odcgroup.template.TemplateCreator.createTemplate(TemplateDescriptor, Map<String, String>, File)
		destinationFolder.mkdirs();

		if (!destinationFolder.isDirectory()) {
			throw new IllegalArgumentException("The destination folder must be a directory: "
					+ destinationFolder.getAbsolutePath());
		}

		InputStream input = null;
		try {
			input = new FileInputStream(descriptor.getTemplateJar());

			JarInputStream jin = new JarInputStream(input);
			ZipEntry entry = jin.getNextEntry();
			while (entry != null) {
				try {
					String filename = entry.getName();
					// TODO: make this an option when merging to the template-lib
//					// Skip the files at the root of the archive (do not extract it)
//					if (!filename.contains("/")) {
//						continue;
//					}
					// Skip the META-INF folder
					if (filename.startsWith("META-INF/")) {
						continue;
					}
					// TODO: this is new and only applies to models projects
					if (filename.startsWith(RULE_RULES_RULES)) {
						String[] splitedPath = StringUtils.split(filename,"/");
						if (splitedPath.length >= 4) {
							if ("internal".equals(splitedPath[3])) {
								continue;
							}
						}
					}
					// Skip the .empty file (used to create empty folder)
					if (filename.endsWith(".empty")) {
						continue;
					}
					
					if (filename.charAt(filename.length() - 1) == '/') {
						filename = filename.substring(0, filename.length() - 1);
					}
					if (filename.charAt(0) == '/') {
						filename = filename.substring(1);
					}
					if (File.separatorChar != '/') {
						filename = filename.replace('/', File.separatorChar);
					}

					// Filter the file name and folders
					// TODO: correct this when merging to template-lib
					//filename = TextFilter.instance().filter(filename, parameters);
					
					File file = new File(destinationFolder, filename);
					if (entry.isDirectory()) {
						// make sure the directory exists
						file.mkdirs();
					} else {
						// make sure the directory exists
						File parent = file.getParentFile();
						if (parent != null && !parent.exists()) {
							parent.mkdirs();
						}
	
						String contents = IOUtils.toString(jin);
						boolean applyFilter = true;
						for (String filter : descriptor.getExcludedFilters()) {
							if (FilenameUtils.wildcardMatch(filename, filter)) {
								// Skip filtering for resource that match filter
								applyFilter = false;
								break;
							}
						}
						if (applyFilter) {
							// TODO: correct this when merging to template-lib
							//contents = TextFilter.instance().filter(contents, parameters);
						}

						OutputStream output = null; 
						try {
							output = new FileOutputStream(file);
							IOUtils.write(contents, output);
							output.flush();
							file.setLastModified(entry.getTime());
						} finally {
							output.close();
						}
					}
				} finally {
					jin.closeEntry();
					entry = jin.getNextEntry();
				}
			}
		} finally {
			input.close();
		}

	}

	private static void replacePom(File destinationFolder, File standardPom)
			throws FileNotFoundException, IOException {
		// Replace the pom
		FileReader input = null;
		FileWriter output = null;
		try {
			input = new FileReader(standardPom);
			output = new FileWriter(new File(destinationFolder, "pom.xml"));
			IOUtils.copy(input, output);
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	/**
	 * Setup the template creator for Design Studio.
	 * @throws CoreException
	 */
	private static void setupTemplateCreator() {
		if (initialized) {
			return;
		}
		
		// Redirect the logs to the DS logs
		com.odcgroup.template.log.LoggerFactory.setLoggerFactory(new DSLoggerFactory());
		logger.debug("LoggerFactory configured");
		
		// Retrieve the template provider
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(TEMPATE_PROVIDER_EXTENSION_POINT);
		
		if (config.length != 1) {
			logger.error("The extension point is not properly configured");
			throw new IllegalStateException("The extension point is not properly configured");
		}

		try {
			Object dsTemplateProvider = config[0].createExecutableExtension("type");
			if (!(dsTemplateProvider instanceof ITemplateProvider)) {
				logger.error("The extension point is not properly configured (wrong type)");
				throw new IllegalStateException("The extension point is not properly configured (wrong type)");
			}
			TemplateCreator.instance().setTemplateProvider((ITemplateProvider)dsTemplateProvider);
			templateProvider = (ITemplateProvider2)dsTemplateProvider;
			logger.info("The Template provider is configured");
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		
		initialized = true;
	}
}
