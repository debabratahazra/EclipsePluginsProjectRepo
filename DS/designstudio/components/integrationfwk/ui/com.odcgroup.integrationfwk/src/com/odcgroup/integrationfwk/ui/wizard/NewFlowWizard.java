package com.odcgroup.integrationfwk.ui.wizard;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.model.SourceType;

public class NewFlowWizard extends Wizard implements INewWizard {
	private FlowNewFilePage newFlowPage;
	private IStructuredSelection selection;
	private IWorkbench workbench;

	public NewFlowWizard() {
	}

	@Override
	public void addPages() {

		newFlowPage = new FlowNewFilePage(selection);
		addPage(newFlowPage);
	}

	public void init(IWorkbench aWorkbench, IStructuredSelection aSelection) {
		selection = aSelection;
		workbench = aWorkbench;
		setDefaultPageImageDescriptor(ImageDescriptor.createFromFile(
				IntegrationToolingActivator.class, "icons/flow.gif"));
		setWindowTitle(IntegrationToolingActivator
				.getEventString("_UI_WIZARD_CREATE_FLOW_MODEL_TITLE"));

	}

	public void openEditor(final IFile iFile) {
		if (iFile != null) {
			IWorkbench workbench2;
			if (workbench == null) {
				workbench2 = IntegrationToolingActivator.getDefault().getWorkbench();
			} else {
				workbench2 = workbench;
			}
			final IWorkbenchWindow workbenchWindow = workbench2
					.getActiveWorkbenchWindow();

			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					try {
						String editorId = null;

						// System.out.println("workbechn   " +
						// PlatformUI.getWorkbench().getEditorRegistry().findEditor(EventsEditor.EDITOR_ID));
						// IEditorDescriptor editor =
						// PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(iFile.getLocation().toOSString(),
						// iFile.getContentDescription().getContentType());
						// working with file name rather than content
						IEditorDescriptor editor = PlatformUI.getWorkbench()
								.getEditorRegistry()
								.getDefaultEditor(iFile.getName());

						// IEditorDescriptor editor =
						// PlatformUI.getWorkbench().getEditorRegistry().findEditor(EventsEditor.EDITOR_ID);
						if (editor != null) {
							editorId = editor.getId();
						}
						workbenchWindow.getActivePage().openEditor(
								new FileEditorInput(iFile), editorId);

					} catch (PartInitException ex) {
						TWSConsumerLog.logError(ex);
					} catch (CoreException ex) {
						TWSConsumerLog.logError(ex);
					}
				}
			});
		}
	}

	@Override
	public boolean performFinish() {

		IFile file = newFlowPage.createNewFile();

		//
		// Get the xsd schema name from the full path name
		// e.g. f:/b2b/po.xsd => schema name = po
		//

		IPath iPath = file.getFullPath().removeFileExtension();

		iPath.lastSegment();

		// Preferences preference =
		// XMLCorePlugin.getDefault().getPluginPreferences();
		// String charSet =
		// preference.getString(CommonEncodingPreferenceNames.OUTPUT_CODESET);
		// if (charSet == null || charSet.trim().equals("")) {
		// charSet = "UTF-8";
		// }

		// todo . change the shell of the file to be legitimate

		String charSet = "UTF-8";

		String newContents = "<?xml version=\"1.0\" encoding=\"" + charSet
				+ "\"?>\n";

		// defaulting the exit point type to VERSION as in line with new event
		// creation.
		String defaultExitPointType = "<ExitPointType>"
				+ SourceType.VERSION.toString() + "</ExitPointType>" + "\n";

		// newContents +="<Flow>\n<FlowName>" + fileName+
		// "</FlowName>\n<InputFields>\n<Field>\n<DisplayName></DisplayName>\n<FieldType></FieldType>\n<FieldName></FieldName>\n<Service></Service>\n<Operation></Operation>\n</Field>\n</InputFields>\n</Flow>";
		newContents += "<Flow>\n<InputFields></InputFields>\n<BaseEvent></BaseEvent>\n"
				+ defaultExitPointType + "</Flow>";
		try {
			byte[] bytes = newContents.getBytes(charSet);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

			file.setContents(inputStream, true, false, null);
			inputStream.close();
		} catch (Exception e) {

			TWSConsumerLog.logError(e);
		}

		if (file != null) {
			revealSelection(new StructuredSelection(file));
		}

		openEditor(file);

		return true;
	}

	private void revealSelection(final ISelection selection) {
		if (selection != null) {
			IWorkbench workbench2;
			if (workbench == null) {
				workbench2 = IntegrationToolingActivator.getDefault().getWorkbench();
			} else {
				workbench2 = workbench;
			}
			final IWorkbenchWindow workbenchWindow = workbench2
					.getActiveWorkbenchWindow();
			final IWorkbenchPart focusPart = workbenchWindow.getActivePage()
					.getActivePart();
			if (focusPart instanceof ISetSelectionTarget) {
				Display.getCurrent().asyncExec(new Runnable() {
					public void run() {
						((ISetSelectionTarget) focusPart)
								.selectReveal(selection);
					}
				});
			}
		}
	}

}
