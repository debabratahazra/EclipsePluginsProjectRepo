package com.odcgroup.ds.t24.packager.basic;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import com.odcgroup.ds.t24.packager.basic.external.tool.ExternalTool;
import com.odcgroup.ds.t24.packager.basic.external.tool.ExternalToolException;
import com.odcgroup.ds.t24.packager.basic.external.tool.IOutputCallback;
import com.odcgroup.ds.t24.packager.basic.validator.BasicValidationException;
import com.odcgroup.ds.t24.packager.basic.validator.BasicValidator;
import com.odcgroup.ds.t24.packager.generator.T24Packager;
import com.odcgroup.ds.t24.packager.generator.T24PackagerException;
import com.odcgroup.ds.t24.packager.helper.FileHelper;
import com.odcgroup.ds.t24.packager.helper.T24EnvironmentConfiguration;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectArchive;

/**
 * TAFC Basic compiler used by the packager. 
 * The compiler has the following characteristics:
 * <ul>
 * <li>It uses an externally installed TAFC basic compiler. And fails 
 * to execute if not available.</li>
 * <li>The TAFJ compiler produces compiled artifact with fixed
 * names: t24obj/jLibDefinition, t24obj/lib.el, t24obj/lib0.so.2. 
 * (Note: if the lib0 file exceed a certain size, a lib1 file is
 * generated. And so on if the new file exceed itself the maximum size)</li>
 * <li>As it is not possible to merge compiled basic project afterward, 
 * the compilation of a component is done just before the packaging and 
 * not at the basic project level.</li>
 * <li>As if the lib0.so.2 file already exist, the compiler generates a 
 * lib0.so.3 and so on, the compilation clean the output folder before 
 * starting the compilation</li>
 * </ul>
 * This implementation uses as little as possible basic code.
 * @author yandenmatten
 */
public class TAFCBasicCompiler {
	
	protected static final FilenameFilter FILENAME_FILTER = new FilenameFilter() { 
		@Override 
		public boolean accept(File dir, String name) {
			return !name.startsWith("$") && !name.startsWith("I_"); 
		}
	};
	
	private T24Packager packager;
	private BasicValidator validator;
	private ExternalTool externalTool;
	private List<T24ProjectArchive> t24ProjectArchives = new ArrayList<T24ProjectArchive>();
	
	public void addT24ProjectArchive(T24ProjectArchive archive) {
		t24ProjectArchives.add(archive);
	}
	
	/**
	 * Compile the basic source archives added using addBasicSourceArchive.
	 * The result of the compilation is put in the outputFolder
	 */
	public void compile() throws T24PackagerException, BasicValidationException, BasicException, IOException {
		// 1. Check preconditions
		File[] componentFolder = FileHelper.listFiles(getSourceExtractOutputFolder());
		if (componentFolder.length == 0) {
			packager.getLogger().debug(" - No basic files to compile found in " + getSourceExtractOutputFolder().getAbsolutePath());
			return; // Nothing to do
		}
		
		// 2. validate the environment
		validator.validateEnvironnment();
		
		// 3. Execute command
		for (File file : componentFolder) {
			if (file.isDirectory()) {
				String component = file.getName();
				packager.getLogger().debug(" - found component folder named " + component);
				if (!packager.getComponentName().equals(component)) {
					throw new BasicValidationException("This packager is configured to package the component " + packager.getComponentName() + ". Some sources are for component " + component + " (" + file.getAbsolutePath() + ")");
				}
				File targetFolder = new File(getTAFCOutputFolder(), component);
				FileUtils.forceMkdir(targetFolder);
				packager.getLogger().debug(" - launching basic compilation...");
				compile(file, targetFolder);
			}
		}
	}
	
	public File getSourceExtractOutputFolder() {
		return packager.getBasicCompilationSourceFolder();
	}

	public File getTAFCOutputFolder() {
		return packager.getBasicCompilationBinariesFolder();
	}

	protected void compile(File sourceFolder, File outputFolder) throws T24PackagerException {
		if (!SystemUtils.IS_OS_WINDOWS && !SystemUtils.IS_OS_UNIX) {
			throw new BasicException("Unsupported T24 environment. " +
					"The maven compilation only support UNIX & Windows environments. Current environment is " + SystemUtils.OS_NAME);
		}
		
		packager.getLogger().debug("Creating t24lib & t24bin folders");
		File t24lib = new File(outputFolder, "t24lib");
		File t24bin = new File(outputFolder, "t24bin");
		for (File t24Folder : new File[]{t24lib, t24bin} ) {
			try {
				FileUtils.forceMkdir(t24Folder);
			} catch (IOException e) {
				throw new T24PackagerException("Unable to create " + t24Folder.getAbsolutePath(), e);
			}

			File libJbaseHeaderFile = new File(t24Folder, ".jbase.header");
			FileUtils.deleteQuietly(libJbaseHeaderFile);
		}
		
		String bnkRunHome = T24EnvironmentConfiguration.getT24Home();
		String tafcHome = T24EnvironmentConfiguration.getTafcHome();
		String basicIncludeFolder = bnkRunHome + File.separatorChar + ".." + File.separatorChar + "T24_BP";
		packager.getLogger().debug("HOME: " + bnkRunHome);
		packager.getLogger().debug("BASIC include folder: " + basicIncludeFolder);


		String[] catalogEnvp = addToEnv(t24lib, t24bin);

		List<Exception> failedSources = new ArrayList<Exception>();
		
		if(bnkRunHome==null || bnkRunHome.isEmpty()){
			throw new BasicException("Command is incomplete, set HOME environment variable to T24 Home location");
		}
		if (T24EnvironmentConfiguration.getJbasePath() != null) {
			packager.getLogger().info("Execution inside T24 shell ");
			String jsh = tafcHome + File.separatorChar + "bin" + File.separatorChar + "jsh -c ";
			String ebCompile;
			for (File basicFile : FileHelper.listFiles(sourceFolder, FILENAME_FILTER)) {
				File basicFileWithoutExtension = null;
				try {
					String componentName = copyFileToLocalDir(basicFile,tafcHome);
					packager.getLogger().info("Compiling using EB.COMPILE command " + basicFile.getAbsolutePath());
					ebCompile = "EB.COMPILE " + componentName + " "
								+ basicFile.getName();
					executeT24Command(jsh + "\"" + ebCompile + "\"", catalogEnvp);

				} catch (BasicException e) {
					packager.getLogger().debug("T24 command call failed.", e);
					failedSources.add(e);
				} finally {
					if (basicFileWithoutExtension != null && basicFileWithoutExtension.exists()) {
						FileUtils.deleteQuietly(basicFileWithoutExtension);
					}
				}
			}
		} 
		else{
			packager.getLogger().info("Execution outside T24 shell ");
			List<String> compileFailedSources = new ArrayList<String>();
			File[] basicFiles = FileHelper.listFiles(sourceFolder, FILENAME_FILTER);

			for (File basicFile : basicFiles ) {
				try {
					packager.getLogger().info("Compiling " + basicFile.getAbsolutePath());
					executeT24Command("BASIC -f -I" + basicIncludeFolder + " " + basicFile.getParentFile().getAbsolutePath() + " " + basicFile.getName(), null);
				} catch (BasicException e) {
					packager.getLogger().debug("T24 command call failed.", e);
					failedSources.add(e);
					compileFailedSources.add(basicFile.getAbsolutePath());
				}
			}
			for (File basicFile : basicFiles) {
				try {
					if (compileFailedSources.contains(basicFile.getAbsolutePath())) {
						continue;
					}
					packager.getLogger().info("Cataloging " + basicFile.getAbsolutePath());
					String catalogCommand = "CATALOG -f " +  basicFile.getParentFile().getAbsolutePath() + " " + basicFile.getName();
					if (SystemUtils.IS_OS_WINDOWS) {
						String jsh = "jsh -c ";
						executeT24Command(jsh + "\"" + catalogCommand + "\"", catalogEnvp);
					} else {
						executeT24Command(catalogCommand, catalogEnvp);
					}

				} catch (BasicException e) {
					packager.getLogger().debug("T24 command call failed.", e);
					failedSources.add(e);
				}
			}
		}
		
		if (failedSources.size() > 0) {
			StringBuilder sb = new StringBuilder("" + failedSources.size() + " BASIC compilation failures were detected:\n\n");
			for (int i=0; i<failedSources.size(); i++) {
				sb.append("" + (i+1) + ". " + failedSources.get(i).getMessage() + "\n\n");
			}
			throw new BasicException(sb.toString());
		}
	}
	
	private File getRootFile(File basicFile) {
		if(basicFile!=null) {
			File parentFile = basicFile.getParentFile();
			if(parentFile!=null){
				return getRootFile(parentFile);	
			}
			return basicFile;
		}
		return basicFile;
	}

	private String copyFileToLocalDir(File basicFile, String tafcHome) throws T24PackagerException  {
		File parentFile = basicFile.getParentFile();
		String copyFilePath = parentFile.getAbsolutePath();
		String targetDir = getRootFile(basicFile).getAbsolutePath(); 
		String componentName = null;
		File destFile = null;
		int index = copyFilePath.lastIndexOf(File.separatorChar);
		String retval = copyFilePath.substring(index+1, copyFilePath.length()-1);
		System.out.println(retval);
        componentName = retval + "_" + packager.getRev();
		 
		File destFolder = new File(targetDir + componentName);
		try {
			if(!destFolder.exists()) {
				FileUtils.forceMkdir(destFolder);
				packager.getLogger().info("destFolder is directory : "+destFolder.isDirectory());
			} 
		} catch (IOException e) {
			throw new T24PackagerException("Unable to create " + destFolder.getAbsolutePath(), e);
		} 
		
		try {
			destFile = new File(destFolder, basicFile.getName());
			destFile.createNewFile();
			FileUtils.copyFile(basicFile, destFile);
		} catch (IOException e) {
			throw new T24PackagerException("Unable to copy from " + basicFile + " to " + destFile, e);
		}
		
		return destFile.getParentFile().getAbsolutePath(); 
	}
	
	private String[] addToEnv(File t24lib, File t24bin) {
		Map<String, String> env = System.getenv();
		List<String> catalogEnvpList = new ArrayList<String>();
		for (Map.Entry<String, String> entry : env.entrySet()) {
			catalogEnvpList.add(entry.getKey() + "=" + entry.getValue());
		}
		catalogEnvpList.add("JBCDEV_LIB=" + t24lib.getAbsolutePath());
		catalogEnvpList.add("JBCDEV_BIN=" + t24bin.getAbsolutePath());
		String[] catalogEnvp = catalogEnvpList.toArray(new String[catalogEnvpList.size()]);
		return catalogEnvp;
	}

	private void executeT24Command(String t24command, String[] envp)
			throws BasicException {
		packager.getLogger().info("Executing command : "+t24command);
		class CompilationOutputCallback implements IOutputCallback {
			boolean compilationSuccessful = false;
			StringBuffer output = new StringBuffer();
			StringBuffer errorLines = new StringBuffer();

			@Override
			public void outputLine(String line, boolean isError) {
				packager.getLogger().debug(line);
				output.append(line);
				output.append("\n");
				if(line.contains("Permission denied")) {
					errorLines.append(line);
					errorLines.append("\n");
				} else if(line.contains("Access denied")) {
					errorLines.append(line);
					errorLines.append("\n");
				} else if(line.contains("** Error")) {
					//DS-5862
					errorLines.append(line);
					errorLines.append("\n");
				} else if (line.contains("compiled successfully") || line.contains("cataloged successfully")) {
					compilationSuccessful = true;
				}
			}
		}

		CompilationOutputCallback callback = new CompilationOutputCallback();
		try {
			externalTool.executeCmd(t24command, envp,new File(T24EnvironmentConfiguration.getT24Home()), callback);
			if (!callback.compilationSuccessful || callback.errorLines.length() != 0) {
				throw new BasicException("Failure detected during the call of " + t24command + ".\n\n" +
						(callback.errorLines.length() != 0 ?
						"The following error line(s) were issued during the processing:\n" +
						callback.errorLines.toString() + "\n" : "") +
						"Complete logs:\n" + 
						callback.output);
			}
		} catch (ExternalToolException e) {
			packager.getLogger().error("Command failed: " + callback.output);
			throw new BasicException("Unable to launch to the command: " + t24command, e);
		}
	}
	
	public void clean() throws IOException {
		FileUtils.forceMkdir(getSourceExtractOutputFolder());
		FileHelper.safelyCleanFolder(getSourceExtractOutputFolder());
		FileUtils.forceMkdir(getTAFCOutputFolder());
		FileHelper.safelyCleanFolder(getTAFCOutputFolder());
	}

	public void setValidator(BasicValidator validator) {
		this.validator = validator;
	}

	public T24Packager getPackager() {
		return packager;
	}

	public void setPackager(T24Packager packager) {
		this.packager = packager;
	}


	public ExternalTool getExternalTool() {
		return externalTool;
	}


	public void setExternalTool(ExternalTool externalTool) {
		this.externalTool = externalTool;
	}

}
