package com.odcgroup.ds.t24.packager.generator;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import com.odcgroup.ds.t24.packager.basic.BasicException;
import com.odcgroup.ds.t24.packager.basic.TAFCBasicCompiler;
import com.odcgroup.ds.t24.packager.basic.external.tool.ExternalTool;
import com.odcgroup.ds.t24.packager.basic.external.tool.ExternalToolException;
import com.odcgroup.ds.t24.packager.basic.external.tool.IOutputCallback;
import com.odcgroup.ds.t24.packager.basic.validator.BasicValidationException;
import com.odcgroup.ds.t24.packager.data.Cd;
import com.odcgroup.ds.t24.packager.data.DataGenerator;
import com.odcgroup.ds.t24.packager.data.DataHelper;
import com.odcgroup.ds.t24.packager.data.validator.DataFileValidator;
import com.odcgroup.ds.t24.packager.helper.FileHelper;
import com.odcgroup.ds.t24.packager.helper.TarHelper;
import com.odcgroup.ds.t24.packager.helper.ZipHelper;
import com.odcgroup.ds.t24.packager.log.NullPackagerLogger;
import com.odcgroup.ds.t24.packager.log.PackagerLogger;
import com.odcgroup.ds.t24.packager.t24gen.T24GenProjectArchive;
import com.odcgroup.ds.t24.packager.t24gen.T24GenProjectPackager;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectArchive;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectPackager;
import com.odcgroup.ds.t24.packager.t24tafj.TafjProjectArchive;
import com.odcgroup.ds.t24.packager.t24tafj.TafjProjectPackager;

public class T24Packager {
	
	private static final String DS_GENERATED_FOLDER = "ds-generated";
	
	private static final String DETECT_OS_PROGRAM = "GET.PLATFORM.TYPE";
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String SEPARATOR = "_";
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("000");

	private PackagerLogger logger = NullPackagerLogger.INSTANCE;
	
	private T24ProjectPackager t24ProjectPackager;
	private T24GenProjectPackager t24genProjectPackager;
	private TafjProjectPackager t24TafjProjectPackager;
	private TAFCBasicCompiler tafcCompiler;
	private DataGenerator dataGenerator;
	
	private PackageTypeEnum type;
	private String release;
	private String componentName;
	private String rev;
	private String os;
	private String version;
	
	private List<Cd> cds = new ArrayList<Cd>();
	
	private ExternalTool externalTool;
	private String detectOsProgramLocation = "t24bin/eb_updates";
	
	private File outputFolder;
	
	public T24Packager(PackageTypeEnum type, String release, String componentName, String rev, String os, String version, File outputFolder) {
		this.type = type;
		this.release = release;
		this.componentName = componentName;
		this.rev = rev;
		this.os = os;
		this.version = version;
		this.outputFolder = outputFolder;
	}
	
	private String getPackageFilename() {
		return getPackageRoot() + "." + type.getExtension();
	}
	
	public String getPackageRoot() {
		String env;
		if (type == PackageTypeEnum.TAFJ) {
			env = "JAVA";
		} else {
			env = getOs();
		}

		return release + SEPARATOR + componentName + SEPARATOR + rev + SEPARATOR + env + SEPARATOR + version;
	}
	
	public File getPackageRootFolder() {
		return new File(getPackageWorkingFolder(), getPackageRoot());
	}
	
	public void addProjectArchive(T24ProjectArchive archive) {
		t24ProjectPackager.addT24Project(archive);
	}
	
	public void addGenProjectArchive(T24GenProjectArchive archive) {
		t24genProjectPackager.addT24GenProject(archive);
	}
	
	public void addTafjProjectArchive(TafjProjectArchive archive) {
		t24TafjProjectPackager.addTafjProject(archive);
	}
	
	public File getPackageWorkingFolder() {
		return new File(getOutputFolder(), "package");
	}

	public File getBasicCompilationSourceFolder() {
		return new File(getOutputFolder(), "basic-src");
	}

	public File getBasicCompilationBinariesFolder() {
		return new File(getOutputFolder(), "basic-bin");
	}
	
	public void addCd(Cd cd) {
		cds.add(cd);
	}

	public File generatePackage() throws IOException, T24PackagerException {
		try {
			if (dataGenerator == null) {
				throw new IllegalStateException("No data generator plugged in the T24Packager");
			}
			if (tafcCompiler == null) {
				throw new IllegalStateException("No TAFC compiler plugged in the T24Packager");
			}
			if (t24TafjProjectPackager == null) {
				throw new IllegalStateException("No TAFJ packager plugged in the T24Packager");
			}
			if (t24ProjectPackager == null) {
				throw new IllegalStateException("No T24Project packager plugged in the T24Packager");
			}
			if (type == PackageTypeEnum.TAFJ && StringUtils.isNotBlank(os)) {
				logger.warn("OS parameter (set to " + os + ") is ignored as the package type is TAFJ.");
			}
			if (type == PackageTypeEnum.TAFC && getOs() == null) {
				throw new IllegalStateException("No OS either set or detected");
			}
			if (!outputFolder.exists()) {
				FileUtils.forceMkdir(outputFolder);
			} else {
				FileHelper.safelyCleanFolder(outputFolder);
			}

			
			File packageFile = new File(outputFolder, getPackageFilename());
			getLogger().info("Preparing packager for " + packageFile.getAbsolutePath());
			
			getLogger().info("Processing T24 projects dependencies...");
			processT24Projects();
			getLogger().info("Processing DS T24 generation project dependencies...");
			processT24GenProjects();
			getLogger().info("Processing T24 TAFJ project dependencies...");
			processT24TafjProjects();
			
			getLogger().info("Compiling basic files...");
			compileBasicFiles();
			getLogger().info("Processing data files...");
			processDataFiles();
	
			getLogger().info("Generating package...");
			
			if (type == PackageTypeEnum.TAFC) {
				TarHelper.createTar(packageFile, getPackageWorkingFolder());
				if (TarHelper.listTarEntries(packageFile).isEmpty()) {
					throw new T24PackagerException("The package cannot be created because there is no package content.");
				}
			} else if (type == PackageTypeEnum.TAFJ) {
				ZipHelper.createZip(packageFile, getPackageWorkingFolder());
				if (ZipHelper.listZipEntries(packageFile).isEmpty()) {
					throw new T24PackagerException("The package cannot be created because there is no package content.");
				}
			} else {
				throw new IllegalArgumentException("Cannot generate a package for " + type);
			}
			getLogger().info("Package generated : "+packageFile);
			return packageFile;
		} catch (T24PackagerException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	private void processT24Projects() throws BasicValidationException, BasicException, IOException {
		t24ProjectPackager.processT24Projects();
	}
	
	private void processT24GenProjects() throws BasicValidationException, BasicException, IOException {
		t24genProjectPackager.processT24GenProjects();
	}

	private void processT24TafjProjects() throws IOException {
		t24TafjProjectPackager.processTafjProjects();
	}

	private void compileBasicFiles() throws T24PackagerException, BasicValidationException, BasicException, IOException {
		// Compile all the basic files
		tafcCompiler.compile();

		// Move the compiled files to the tar archive folder with the following
		// reorganization:
		// <folder>/* -> <folder>/<component-name>/*
		// i.e.
		// t24lib/* -> t24lib/<component-name>/*
		// t24obj/* -> t24obj/<component-name>/*
		// ...
		for (File componentFile : FileHelper.listFiles(getBasicCompilationBinariesFolder())) {
			if (componentFile.isDirectory()) {
				String component = componentFile.getName();
				getLogger().debug(" - Component found : " + componentFile.getName());
				if (!getComponentName().equals(componentFile.getName())) {
					throw new BasicValidationException("This packager is configured to package the component " + getComponentName() + ". Some sources are for component " + component + " (" + componentFile.getAbsolutePath() + ")");
				}
				// t24obj, t24lib, ...
				for (File compiledFolder : FileHelper.listFiles(componentFile)) {
					if (compiledFolder.isDirectory()) {
						File compiledComponentFolder = new File(compiledFolder, getComponentName().toLowerCase());
						FileUtils.forceMkdir(compiledComponentFolder);
						for (File content: FileHelper.listFiles(compiledFolder)) {
							if (compiledComponentFolder.equals(content))
								continue;
							FileUtils.moveToDirectory(content, compiledComponentFolder, true);
						}
						FileUtils.moveDirectoryToDirectory(compiledFolder, getPackageRootFolder(), true);
					}
				}
			}
		}
	}

	private void processDataFiles() throws IOException, T24PackagerException {
		// Create a folder for the component if not available.
		File packagedComponent = getPackageRootFolder();
		FileUtils.forceMkdir(packagedComponent);
		
		// Rename all data and generate header
		DataFileValidator dataFileValidator = new DataFileValidator(type);
		int i=0;
		for (File file : FileHelper.listFiles(packagedComponent)) {
			if (DataHelper.isDataFile(file)) {
				getLogger().debug(" - Data file found: " + file.getAbsolutePath());
				dataFileValidator.validate(file);
				dataGenerator.addDataFile(file.getName());
				String recName = "REC" + DECIMAL_FORMAT.format(++i);
				getLogger().debug(" - Renamed to : " + recName);
				file.renameTo(new File(file.getParent(), recName));
			}
		}
		getLogger().debug(" - Generating data header file");
		dataGenerator.processDataFiles(packagedComponent);
	}

	private String detectOs() {
		File osCommand = getDetectOsProgramExecutable();
		
		class OSOutputCallback implements IOutputCallback {
			String detectedOs = null;
			StringBuffer output = new StringBuffer();
			
			@Override
			public void outputLine(String line, boolean isError) {
				getLogger().debug(line);
				output.append(line);
				output.append("\n");
				if (detectedOs == null) {
					// The 2st line is the OS
					if (StringUtils.countMatches(output, "\n") == 2) {
						detectedOs = line.trim();
					}
				}
			}
		}

		try {
			OSOutputCallback callback = new OSOutputCallback();
			externalTool = new ExternalTool();
			externalTool.setPackager(this);
			getLogger().debug("Invoking OS detecting command (" + osCommand + ") ...");
			externalTool.executeCmd(osCommand.getAbsolutePath(), null,null, callback);
			getLogger().debug("Execution output");
			getLogger().debug(callback.output.toString());
			if (callback.detectedOs.contains("/") ||
					callback.detectedOs.contains("\\")) {
				throw new ExternalToolException("Invalid OS detected: " + callback.detectedOs);
			} else {
				getLogger().info("Detected OS: " + callback.detectedOs);
				return callback.detectedOs;
			}
		} catch (ExternalToolException e) {
			getLogger().error("Failed to execute the OS detection program", e);
			return null;
		}
	}

	public File getDetectOsProgramExecutable() {
		String home;
		try {
			home = ExternalTool.getTafcHome();
		} catch (T24PackagerException e) {
			logger.error("Unable to detect OS", e);
			return null;
		}
		File osDetectProgram = new File(home + FILE_SEPARATOR + getDetectOsProgramLocation() + FILE_SEPARATOR + DETECT_OS_PROGRAM);
		if (!programExists(osDetectProgram)) {
			throw new GetPlatformTypeNotFoundException("Not able to find GET.PLATFORM.TYPE in " + osDetectProgram.getParentFile().getAbsolutePath() + ". ");
		}
		return osDetectProgram;
	}

	private boolean programExists(File program) {
		if (SystemUtils.IS_OS_WINDOWS) {
			return new File(program.getAbsolutePath() + ".exe").exists();
		} else {
			return program.exists();
		}
	}
	
	public PackageTypeEnum getType() {
		return type;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getOs() {
		if (os == null) {
			os = detectOs();
		}
		return os;
	}
	
	public void setOs(String os) {
		this.os = os;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public File getOutputFolder() {
		return this.outputFolder;
	}

	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}

	public T24ProjectPackager getT24ProjectPackager() {
		return t24ProjectPackager;
	}

	public void setT24ProjectPackager(T24ProjectPackager t24ProjectPackager) {
		this.t24ProjectPackager = t24ProjectPackager;
	}

	public TAFCBasicCompiler getTafcCompiler() {
		return tafcCompiler;
	}

	public void setTafcCompiler(TAFCBasicCompiler tafcCompiler) {
		this.tafcCompiler = tafcCompiler;
	}

	public DataGenerator getDataGenerator() {
		return dataGenerator;
	}

	public void setDataGenerator(DataGenerator dataGenerator) {
		this.dataGenerator = dataGenerator;
	}

	public PackagerLogger getLogger() {
		return logger;
	}

	public void setLogger(PackagerLogger logger) {
		this.logger = logger;
	}

	public T24GenProjectPackager getT24genProjectPackager() {
		return t24genProjectPackager;
	}

	public void setT24genProjectPackager(T24GenProjectPackager t24genProjectPackager) {
		this.t24genProjectPackager = t24genProjectPackager;
	}
	
	public TafjProjectPackager getT24TafjProjectPackager() {
		return t24TafjProjectPackager;
	}
	
	public void setT24TafjProjectPackager(TafjProjectPackager t24TafjProjectPackager) {
		this.t24TafjProjectPackager = t24TafjProjectPackager;
	}

	public File getDsGeneratedFolder() {
		return new File(getPackageRootFolder(), DS_GENERATED_FOLDER);
	}

	public File getTafjFolder() {
		return getPackageRootFolder();
	}

	public String getDetectOsProgramLocation() {
		return detectOsProgramLocation;
	}

	public void setDetectOsProgramLocation(String detectOsProgramLocation) {
		this.detectOsProgramLocation = detectOsProgramLocation;
	}

}
