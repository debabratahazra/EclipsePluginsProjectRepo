package com.zealcore.se.ui.wizards;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;

public class ImportLTTng extends Wizard implements IImportWizard {

	private IWorkbench fWorkbench;

	private IStructuredSelection fSelection;

	private ImportTraceWizardPage fMainPage;

	public ImportLTTng() {
	}

	@Override
	public void addPages() {
		super.addPages();
		setWindowTitle("Import");
		fMainPage = new ImportTraceWizardPage(fWorkbench, fSelection);
		addPage(fMainPage);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		fWorkbench = workbench;
		fSelection = selection;

		List selectedResources = IDE.computeSelectedResources(selection);
		if (!selectedResources.isEmpty()) {
			fSelection = new StructuredSelection(selectedResources);
		}

		setWindowTitle("");
		setNeedsProgressMonitor(true);

	}

	@Override
	public boolean canFinish() {
		return fMainPage.isTraceSelected();
	}

	@Override
	public boolean performFinish() {
		return fMainPage.finish();
	}

}
