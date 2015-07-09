package com.temenos.maven.plugin.t24.project;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.manager.ArchiverManager;

import com.odcgroup.ds.t24.packager.t24project.T24ProjectException;
import com.odcgroup.ds.t24.packager.t24tafj.TafjProject;
import com.odcgroup.ds.t24.packager.t24tafj.TafjProjectArchive;
import com.odcgroup.ds.t24.packager.t24tafj.TafjProjectArchiver;

/**
 * Package tafj project (data only)
 * @goal package-tafjproject
 * @phase package
 */
public class TafjProjectMojo extends AbstractMojo {
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
	 * @parameter default-value="src/Data/Public"
	 */
	private String dataPublic;
	
	/**
	 * @parameter default-value="src/Data/Model"
	 */
	private String dataModel;
	
	private TafjProjectArchiver archiver = new TafjProjectArchiver();

	/**
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException {
	    
		getLog().debug("TafjProjectMojo called");
		
		getLog().debug("project.getName() : " + project.getName());
		getLog().debug("project.getVersion() : " + project.getVersion());
		getLog().debug("project.getFile().getParentFile() : " + project.getFile().getParentFile());
		getLog().debug("project.getBasedir() : " + project.getBasedir());
		getLog().debug("sourcePublic : " + sourcePublic);
		getLog().debug("dataPublic : " + dataPublic);
		getLog().debug("dataModel : " + dataModel);
		getLog().debug("project.getBuild().getOutputDirectory() : " + project.getBuild().getOutputDirectory());
		
		File basedir = project.getBasedir();
		if (basedir.getName().equals("com.googlecode.addjars.mojo.AddJarsMojo")) {
			getLog().debug("appliying addjars fix...");
			basedir = basedir.getParentFile().getParentFile();
			getLog().debug("project.getBasedir() is now: " + basedir);
		}

		TafjProject tafjproject = new TafjProject(
				project.getName(),
				project.getVersion(),
				basedir,
				sourcePublic, 
				dataPublic, 
				dataModel,
				new File(project.getBuild().getDirectory()));
		tafjproject.setLogger(LoggerAdapterHelper.createPackagerLoggerAdapter(getLog()));
		
		TafjProjectArchive archive;
		try {
			archive = archiver.archive(tafjproject);
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
		
		projectHelper.attachArtifact(project, "zip", "dataPublicCode", archive.getArchive());
	}

}
