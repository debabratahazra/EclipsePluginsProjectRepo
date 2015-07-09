package com.odcgroup.mdf.migration.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

public class VersionRemovalMigration extends AbstractModelMigration {

	public VersionRemovalMigration() {
		// Migration logic for DS-3941
	}

	@Override
	public InputStream migrate(IOfsProject ofsProject, InputStream is,
			URI modelURI, IProgressMonitor monitor) throws MigrationException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			boolean versionProcessed = false;
			while((line = reader.readLine()) !=null) {
				if(versionProcessed || !line.startsWith("version")) {
					sb.append(line + "\r\n");
				} else {
					// don't add the line, i.e. remove the version attribute
					versionProcessed = true;
				}
			}
		} catch (IOException e) {
			throw new MigrationException(e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return IOUtils.toInputStream(sb.toString());
	}

}
