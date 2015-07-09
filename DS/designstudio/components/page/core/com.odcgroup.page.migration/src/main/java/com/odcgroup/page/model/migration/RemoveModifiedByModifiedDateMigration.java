package com.odcgroup.page.model.migration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractModelMigration;
import com.odcgroup.workbench.migration.MigrationException;

public class RemoveModifiedByModifiedDateMigration extends AbstractModelMigration {

	private StringBuilder dsl;
	private String NEW_LINE = System.getProperty("line.separator");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.workbench.migration.IModelMigration#migrate(com.odcgroup
	 * .workbench.core.IOfsProject, java.io.InputStream,
	 * org.eclipse.emf.common.util.URI,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream migrate(IOfsProject ofsProject, InputStream is, URI modelURI, IProgressMonitor monitor)
			throws MigrationException {
		dsl = new StringBuilder();
		// Set new metamodel version as it is difficult to do in this class.
		dsl.append("metamodelVersion = " + targetVersion.toString() + NEW_LINE);
		Scanner s = new Scanner(is);
		while (s.hasNextLine()) {
			parseLine(s.nextLine());
		}
		return new ByteArrayInputStream(dsl.toString().getBytes());
	}

	private void parseLine(String nextLine) {
		if (nextLine.startsWith("metamodelVersion") || nextLine.startsWith("modifiedDate")
				|| nextLine.startsWith("modifiedBy")) {
			return;
		}
		dsl.append(nextLine + NEW_LINE);
	}
}
