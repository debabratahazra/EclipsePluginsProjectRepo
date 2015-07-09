package com.temenos.maven.plugin.t24.project;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.manager.ArchiverManager;

import com.odcgroup.ds.t24.packager.t24project.T24Project;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectArchive;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectArchiver;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectException;

/**
 * Package t24 project (data and basic)
 * @goal package-t24project
 * @phase package
 */
public class T24ProjectMojo extends AbstractMojo {

	/**
	 * @component
	 */
	protected MavenProjectHelper projectHelper;
	

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
	
	/**
	 * @parameter default-value="src/Source/Public"
	 */
	private String sourcePublic;
	
	/**
	 * @parameter default-value="src/Source/Private"
	 */
	private String sourcePrivate;
	
	/**
	 * @parameter default-value="src/Data/Public"
	 */
	private String dataPublic;
	
	/**
	 * @parameter default-value="src/Data/Model"
	 */
	private String dataModel;
	
	private T24ProjectArchiver archiver = new T24ProjectArchiver();

	/**
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException {
	    
		getLog().debug("T24ProjectMojo called");
		
		getLog().debug("project.getName() : " + project.getName());
		getLog().debug("project.getVersion() : " + project.getVersion());
		getLog().debug("project.getFile().getParentFile() : " + project.getFile().getParentFile());
		getLog().debug("sourcePublic : " + sourcePublic);
		getLog().debug("sourcePrivate : " + sourcePrivate);
		getLog().debug("dataPublic : " + dataPublic);
		getLog().debug("dataModel : " + dataModel);
		getLog().debug("project.getBuild().getOutputDirectory() : " + project.getBuild().getOutputDirectory());

		T24Project t24project = new T24Project(
				project.getName(),
				project.getVersion(),
				project.getBasedir(),
				sourcePublic, 
				sourcePrivate, 
				dataPublic, 
				dataModel,
				new File(project.getBuild().getDirectory()));
		t24project.setLogger(LoggerAdapterHelper.createPackagerLoggerAdapter(getLog()));
		
		T24ProjectArchive archive;
		try {
			archive = archiver.archive(t24project);
		} catch (T24ProjectException e) {
			throw new MojoExecutionException("Unable to archive " + project.getName(), e);
		}

		List<String> dataFileNames;
		try {
			dataFileNames = archive.getDataFileNames();
		} catch (IOException e) {
			throw new MojoExecutionException("Unable to read the generated archive", e);
		}
		
		getLog().info("Archived " + dataFileNames.size() + " files.");
		if (getLog().isDebugEnabled()) {
			for (String dataFileName : dataFileNames) {
				getLog().debug(dataFileName);
			}
		}
		
		projectHelper.attachArtifact(project, "zip", archive.getArchive());
	}

}
