package com.temenos.maven.plugin.t24.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.manager.ArchiverManager;

import com.odcgroup.ds.t24.packager.basic.BasicException;
import com.odcgroup.ds.t24.packager.basic.TAFCBasicCompiler;
import com.odcgroup.ds.t24.packager.basic.validator.BasicValidationException;
import com.odcgroup.ds.t24.packager.basic.validator.BasicValidator;
import com.odcgroup.ds.t24.packager.generator.GetPlatformTypeNotFoundException;
import com.odcgroup.ds.t24.packager.generator.PackageTypeEnum;
import com.odcgroup.ds.t24.packager.generator.PackagerFactory;
import com.odcgroup.ds.t24.packager.generator.T24Packager;
import com.odcgroup.ds.t24.packager.generator.T24PackagerException;
import com.odcgroup.ds.t24.packager.t24gen.T24GenProjectArchive;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectArchive;
import com.odcgroup.ds.t24.packager.t24tafj.TafjProjectArchive;

/**
 * Package a T24 package (tar that contains t24 project)
 * @goal package-t24package
 * @phase package
 */
public class T24PackageMojo extends AbstractMojo {
	
	/**
	 * If the executed project is a reactor project, this will contains the full
	 * list of projects in the reactor.
	 *
	 * @parameter expression="${reactorProjects}"
	 * @required
	 * @readonly
	 */
	@SuppressWarnings("rawtypes")
	private List reactorProjects;


	/**
	 * @component
	 */
	private MavenProjectHelper projectHelper;

	/**
	 * Project instance, needed for attaching the buildinfo file. Used to add
	 * new source directory to the build.
	 * 
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;
	
	/**
	 * To look up Archiver/UnArchiver implementations
	 * 
	 * @parameter expression="${component.org.codehaus.plexus.archiver.manager.ArchiverManager}"
	 * @required
	 */
	protected ArchiverManager archiverManager;
	
	/** @component */
	protected org.apache.maven.artifact.resolver.ArtifactResolver resolver;

	/**@parameter expression="${localRepository}" */
	private org.apache.maven.artifact.repository.ArtifactRepository localRepository;

	/** @parameter expression="${project.remoteArtifactRepositories}" */
	@SuppressWarnings("rawtypes")
	private java.util.List remoteRepositories;

	/**
	 * Package type: tafj or tafc
	 * @parameter
	 */
	private String type;
	
    /**
     * T24 release version (like R13)
     * @parameter
     */
	private String release;

	/**
     * Component name including module (like FT_ModelBank)
     * @parameter
     */
	private String componentName;

	/**
     * Revision
     * @parameter
     */
	private String rev;

	/**
     * Operating system on which the package can be installed
     * @parameter
     */
	private String os;

	/**
     * 
     * @parameter
     */
	private String version;
	
	/**
	 * @parameter 
	 *   alias="ds.pack.location"
	 *   default-value="t24bin/eb_updatesrun"
	 */
	private String dsPackLocation;
	
	
	/**
     * @parameter
     */
	private boolean simulateCompilation;
	
	
	private PackagerFactory packagerFactory = new PackagerFactory();

	/**
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	@SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException {
	    
		// Check parameters
		if (StringUtils.isBlank(release)) {
			throw new MojoExecutionException("The release parameter is not specified. Please add it to the plugin configuration (i.e. <configuration><release>R14</release>...</configuration>)");
		}
		if (StringUtils.isBlank(componentName)) {
			throw new MojoExecutionException("The componentName parameter is not specified. Please add it to the plugin configuration (i.e. <configuration>...<componentName>AB_Description</componentName>...</configuration>)");
		}
		if (!componentName.contains("_")) {
			throw new MojoExecutionException("The componentName parameter specified (" + componentName + ") must contain one underscore (\"_\"). Please add it to the plugin configuration (i.e. <configuration>...<componentName>AB_Description</componentName>...</configuration>)");
		}
		if (StringUtils.isBlank(rev)) {
			throw new MojoExecutionException("The rev parameter is not specified. Please add it to the plugin configuration (i.e. <configuration>...<rev>1</rev>...</configuration>)");
		}
		
		// Launch the packager
		getLog().debug("T24PackageMojo called");
		PackageTypeEnum packageType = PackageTypeEnum.parse(type);
		String OS_T24 = System.getenv("ds.t24.packager.simulate.os");
		T24Packager packager = packagerFactory.createPackager(
				packageType,
				release, 
				componentName,
				rev,
				(OS_T24 != null && !OS_T24.isEmpty()) ? OS_T24 : os,
				StringUtils.isNotBlank(version)?version:project.getVersion(), 
				new File(project.getBuild().getDirectory()));
		packager.setDetectOsProgramLocation(dsPackLocation);
		packager.setLogger(LoggerAdapterHelper.createPackagerLoggerAdapter(getLog()));

		String t24Simulate = System.getenv("ds.t24.packager.simulate.compilation");
		if ((t24Simulate != null && t24Simulate.equals("true")) || simulateCompilation) {
			TAFCBasicCompiler fakeBasicCompiler = createFakeBasicCompiler();
			packager.setTafcCompiler(fakeBasicCompiler);
			fakeBasicCompiler.setPackager(packager);
			fakeBasicCompiler.setValidator(new BasicValidator() {
				@Override
				public void validateEnvironnment() throws BasicValidationException {
					// Ignore any validation
				}
			});
		}
		
		getLog().info("Scanning dependencies...");
		for (Artifact artifact : (Set<Artifact>)project.getDependencyArtifacts()) {
			if ("t24-project".equals(artifact.getType())) {
				getLog().debug("t24-project found : " + artifact.getArtifactId());
				File artifactFile = getArtifactAsFile(artifact);
				getLog().info("Adding t24 project archive to the packager: " + artifactFile);
				packager.addProjectArchive(new T24ProjectArchive(artifactFile));
			} else if ("ds-generated".equals(artifact.getType())) {
				getLog().debug("ds-generated found : " + artifact.getArtifactId());
				File artifactFile = getArtifactAsFile(artifact);
				getLog().info("Adding t24 generated project archive to the packager: " + artifactFile);
				packager.addGenProjectArchive(new T24GenProjectArchive(artifactFile));
			} else if ("tafj-project".equals(artifact.getType())) {
				getLog().debug("tafj-project found : " + artifact.getArtifactId());
				File artifactFile = getArtifactAsFile(artifact);
				getLog().info("Adding tafj generated project archive to the packager: " + artifactFile);
				packager.addTafjProjectArchive(new TafjProjectArchive(artifactFile));
			} else {
				getLog().debug("dependency ignored: " + artifact.getArtifactId() + " type: " + artifact.getType());
			}
		}
		
		File generatedPackage;
		try {
			getLog().info("Generating package...");
			generatedPackage = packager.generatePackage();
		} catch (IOException e) {
			throw new MojoExecutionException("Unable to generate the package", e);
		} catch (GetPlatformTypeNotFoundException e) {
			throw new MojoExecutionException((e.getMessage()!=null?e.getMessage():"") + 
					"Please set the variable dsPackLocation in the packager pom to the folder where the is the required utility. " +
					"The path is relative to the bnk.run folder. Example:\n" +
					"\t<groupId>com.odcgroup.maven.plugin</groupId>\n" +
					"\t<artifactId>t24-project-maven-plugin</artifactId>\n" +
					"\t<configuration>\n" +
					"\t\t[...]\n" +
					"\t\t<dsPackLocation>t24bin/other_location</dsPackLocation>\n", e);
		} catch (T24PackagerException e) {
			throw new MojoExecutionException("Unable to generate the package", e);
		}
		getLog().info("Generation done.");
		
		projectHelper.attachArtifact(project, packageType.getExtension(), generatedPackage);
	}

	// DS-6117 T24 Packager only work with mvn install (not mvn package)
	private File getArtifactAsFile(Artifact artifact)
			throws MojoExecutionException {
		getLog().debug("Locating artifact " + artifact.getGroupId() + ":" + artifact.getArtifactId() + ":" + artifact.getVersion() + ":" + 
				artifact.getType());

		// First try to find the artifact in the reactor
		if (reactorProjects != null) {
			for (int i=0; i<reactorProjects.size(); i++) {
				MavenProject project = (MavenProject)reactorProjects.get(i);
				if (StringUtils.equals(project.getGroupId(), artifact.getGroupId()) &&
						StringUtils.equals(project.getArtifactId(), artifact.getArtifactId()) &&
						StringUtils.equals(project.getVersion(), artifact.getVersion()) &&
						StringUtils.equals(project.getPackaging(), artifact.getType())) {
					
					ArtifactHandler artifactHandler = artifact.getArtifactHandler();
					StringBuffer filename = new StringBuffer();
					filename.append(artifact.getArtifactId()).append("-").append(artifact.getVersion());
					
					if (artifact.getClassifier() != null && artifact.getClassifier().length() > 0) {
						filename.append("-").append(artifact.getClassifier());
					}
					
			        if (artifactHandler.getExtension() != null && artifactHandler.getExtension().length() > 0) {
			            filename.append(".").append(artifactHandler.getExtension());
			        }
					File artifactFile = new File(project.getBuild().getDirectory(), filename.toString());
					getLog().debug("Artifact " + artifact.getGroupId() + ":" + artifact.getArtifactId() + ":" + artifact.getVersion() + ":" + 
							artifact.getType() + ":" + artifact.getClassifier() + " found in the reactor (located in " + artifactFile + ")");
					return artifactFile;
				}
			}
		}

		// If not found look in the local or in the remote repo
		getLog().debug("The artifact was not found in the reactor, so try to download it from repository");
		try {
			resolver.resolve(artifact, remoteRepositories, localRepository);
		} catch (ArtifactResolutionException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (ArtifactNotFoundException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
		return artifact.getFile();
	}
	
	/**
	 * @return a basic compiler with fake compilation and no basic validation
	 */
	private TAFCBasicCompiler createFakeBasicCompiler() {
		return new TAFCBasicCompiler() {
			@Override
			protected void compile(File sourceFolder, File outputFolder) throws BasicValidationException,
					BasicException {
				// fake compilation only
				File newOutputFolder = new File(outputFolder, "t24lib");
				newOutputFolder.mkdirs();
				File jLibDefinition = new File(newOutputFolder, "jLibDefinition");
				File libEl = new File(newOutputFolder, "lib.el");
				File lib0so2 = new File(newOutputFolder, "lib0.so.2");
				File[] files = new File[] {jLibDefinition, libEl, lib0so2};
				FileOutputStream outputStream = null;
				for (File file: files) {
					try {
						outputStream = new FileOutputStream(file);
						StringReader reader = new StringReader("This is fake content to avoid empty files");
						IOUtils.copy(reader, outputStream);
					} catch (IOException e) {
						throw new BasicException("Unable to (fake) compile...");
					} finally {
						IOUtils.closeQuietly(outputStream);
					}
				}
			}
		};
	}


}
