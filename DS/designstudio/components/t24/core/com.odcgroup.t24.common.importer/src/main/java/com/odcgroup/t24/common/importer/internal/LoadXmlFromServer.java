package com.odcgroup.t24.common.importer.internal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;

public class LoadXmlFromServer<T extends IModelDetail> implements IImportingStep<T > {
	
	private static final Logger logger = LoggerFactory.getLogger(LoadXmlFromServer.class);

	private IImportModelReport report;
	private IExternalLoader loader;
	private IFolder folder;
	
	private String getMessage(T model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Downloading "); //$NON-NLS-1$
		return b.toString();
	}
	
	// save the original xml in the same location
	protected void saveAsXml(T model, String xmlString) {		
		String xmlFilename = model.getXMLFilename() + ".xml"; //$NON-NLS-1$
		try {
			if (!folder.exists()) {
				folder.create(true,  true,  null);
			}
			IFile outFile = this.folder.getFile(xmlFilename);
			if (outFile.exists()) {
				outFile.delete(true,  null);
			}
			InputStream is = new ByteArrayInputStream(xmlString.getBytes(Charsets.UTF_8)); 
			outFile.create(is, true, null);
		} catch (CoreException ex) {
			logger.error("Huh, CoreException from IFile.create: " + xmlFilename.toString(), ex);
			report.error(ex);
		}	
	}
	

	@Override
	public boolean execute(T model, IProgressMonitor monitor) {
		boolean success = true;
		String xml = null;
		try {
			if (monitor != null) {
				monitor.subTask(getMessage(model));
			}
			xml = loader.getObjectAsXML(model.externalObject());
			model.setXmlString(xml);
			if (StringUtils.isNotBlank(xml)) {
				if (this.folder != null) {
					saveAsXml(model, xml);
				}
			} else {
				report.error(model, "The XML stream downloaded from the server is empty.");
				success = false;
			}
		} catch (Exception ex) {
			report.error(model, ex);
			success = false;
		}	
		return success;
	}

	public LoadXmlFromServer(IImportModelReport report, IExternalLoader loader) {
		this(report, loader, null);
	}

	public LoadXmlFromServer(IImportModelReport report, IExternalLoader loader, IFolder folder) {
		this.report = report;
		this.loader = loader;
		this.folder = folder;
	}

}
