package com.odcgroup.t24.helptextimport.ui.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.odcgroup.t24.applicationimport.ApplicationsImportProblemsException;
import com.odcgroup.t24.helptext.ui.Messages;
import com.odcgroup.t24.helptext.ui.util.HelpTextImporterUtil;
import com.odcgroup.t24.helptext.ui.wizard.ImportTargetSelectionPage;
import com.odcgroup.workbench.ui.importer.AbstractImporter;
import com.odcgroup.workbench.ui.wizards.AbstractOcsImportWizard;

public class HelpTextImportWizard extends AbstractOcsImportWizard {

	HelpTextFolderSelectionPage folderwizardPage;
	ImportTargetSelectionPage targetPage;
	private static Logger LOGGER = Logger.getLogger("HelpTextImportWizard"); 
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.HelptextImportWizard_title); // NON-NLS-1
		setNeedsProgressMonitor(true);
		folderwizardPage = new HelpTextFolderSelectionPage(
				Messages.FolderSelectionPage_title); // NON-NLS-1
		targetPage = new ImportTargetSelectionPage(
				Messages.HelptextImportTargetDialog_title);
	}

	public void addPages() {
		addPage(folderwizardPage);
		addPage(targetPage);
	}

	protected String getModelName() {
		return "domain";
	}

	protected AbstractImporter<?> createImporter() {
		return null;
	}

	public boolean performFinish() {
		final IProject project = targetPage.getTargetProject();

		final List<File> importFiles = folderwizardPage.getSelectedFiles();

		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor progressMonitor) {
				try {
					new HelpTextImporterUtil().importHelptexts(importFiles,project, progressMonitor);
				} catch (ApplicationsImportProblemsException e) {
					LOGGER.log(Level.ERROR, e.getProblems().toString());
				} catch (InterruptedException e) {
					LOGGER.log(Level.ERROR, e.getLocalizedMessage());
				}
			}
		};
		try {
			getContainer().run(false, true, operation);
		} catch (InvocationTargetException e1) {
			LOGGER.log(Level.ERROR, e1.getLocalizedMessage());
		} catch (InterruptedException e1) {
			LOGGER.log(Level.ERROR, e1.getLocalizedMessage());
		}
		return true;
	}

}
