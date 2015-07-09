package com.odcgroup.workbench.migration.tests.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

public class DS3228Modelmigration extends AbstractModelMigration {

	public DS3228Modelmigration() {
	}

	@Override
	public void migrate(IOfsProject ofsProject,
			IOfsModelResource modelResource, IProgressMonitor monitor)
			throws MigrationException {
		IFile file = (IFile) modelResource.getResource();
		try {
			InputStream is = migrate(ofsProject, modelResource.getContents(), modelResource.getURI(), monitor);
			file.setContents(is, IFile.FORCE, monitor);
		} catch (CoreException e) {
			throw new MigrationException(e);
		}
	}

	@Override
	public InputStream migrate(IOfsProject ofsProject, InputStream is,
			URI modelURI, IProgressMonitor monitor)
			throws MigrationException {
		IOUtils.closeQuietly(is);
		String outDatedModelContent = "<mml:domain name=\"Domain\" namespace=\"http://www.odcgroup.com/domain\" " +
									"version=\"1.0\" metamodelVersion=\"1.0.1\" " + 
									"xsi:schemaLocation=\"http://www.odcgroup.com/mml mml.xsd http://www.odcgroup.com/aaa aaa-mml.xsd\" " +
									"xmlns:aaa=\"http://www.odcgroup.com/aaa\" " +
									"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:mml=\"http://www.odcgroup.com/mml\">" +
									"</mml:domain>";
		
		try {
			return new ByteArrayInputStream(outDatedModelContent.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
