package com.odcgroup.mdf.migration.internal;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

public class UTF8Migration extends AbstractModelMigration {

	public UTF8Migration() {
	}

	@Override
	public InputStream migrate(IOfsProject ofsProject, InputStream is,
			URI modelURI, IProgressMonitor monitor) throws MigrationException {
		try {
			// read the stream with the platform encoding and add a comment in front
			String content = IOUtils.toString(is);
			return IOUtils.toInputStream(content, "UTF-8");
		} catch (IOException e) {
			throw new MigrationException("Error while migrating file '" + modelURI.toString() + "' to UTF-8 encoding.");
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

}
