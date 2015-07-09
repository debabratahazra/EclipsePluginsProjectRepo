package com.odcgroup.iris.importer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.server.external.model.internal.IInteractionLoader;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class SaveInteractionInfo<T> implements IImportingStep<InteractionInfo> {
	
	private static final Logger logger = LoggerFactory.getLogger(SaveInteractionInfo.class);

	private static final String RIM_FILE_EXTENSION = ".rim";

	private IInteractionLoader loader;
	private IImportModelReport report;
	private String modelName;
	private IContainer container;
	
	@Override
	public boolean execute(InteractionInfo model, IProgressMonitor monitor) {

		IProject iProject = container.getProject();
		
		Map<String,String> rimMap;
		OutputStream outputStream = null;
		
		boolean success = true;
		try {
			String filePath = 
				container
					.getFullPath()
					.toPortableString()
					.replace(iProject.getName(), "");
			IFile iFile = iProject.getFile(filePath.substring(1));
	
			rimMap = loader.getRIMsMap();
			for (String  rimFile : rimMap.keySet()) {
			
				if (rimFile.endsWith(RIM_FILE_EXTENSION)) {
					outputStream = new FileOutputStream(new File(iFile.getLocation()
							.toOSString() + File.separator + rimFile));
				} else {
					outputStream = new FileOutputStream(new File(iFile.getLocation()
							.toOSString()
							+ File.separator
							+ rimFile
							+ RIM_FILE_EXTENSION));
				}
		
				String rimOutput = rimMap.get(rimFile);
				
				byte[] bytes = rimOutput.getBytes("UTF-8");
				
				try {
					outputStream.write(bytes);
					outputStream.flush();
				} catch (IndexOutOfBoundsException ex) {
					logger.error(ex.getMessage(), ex);
					report.error(model, ex);
				} finally {
					if (outputStream != null) {
						IOUtils.closeQuietly(outputStream);
					}
				}
				
			}

			// Refresh again the workspace
			container.refreshLocal(IResource.DEPTH_INFINITE, null);

		} catch (T24ServerException ex) {
			logger.error(ex.getMessage(), ex);
			report.error(model, ex);
			success = false;
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
			report.error(model, ex);
			success = false;
		} catch (CoreException ex) {
			logger.error(ex.getMessage(), ex);
			report.error(model, ex);
			success = false;
		} finally {
			if (outputStream != null) {
				IOUtils.closeQuietly(outputStream);
			}	
		}
		
		return success;

	}
	
	public SaveInteractionInfo(IImportModelReport report, IInteractionLoader loader, IContainer container, String modelName) {
		this.report = report;
		this.loader = loader;
		this.container = container;
		this.modelName = modelName;
	}

}
