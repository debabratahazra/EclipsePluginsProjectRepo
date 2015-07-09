package com.odcgroup.integrationfwk.ui.wizard;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
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

public class NewEventWizard extends Wizard implements INewWizard {
	private EventNewFilePage newFilePage;
	private IStructuredSelection selection;
	private IWorkbench workbench;

	public NewEventWizard() {
	}

	@Override
	public void addPages() {

		newFilePage = new EventNewFilePage(selection);
		addPage(newFilePage);
	}

	public void init(IWorkbench aWorkbench, IStructuredSelection aSelection) {
		selection = aSelection;
		workbench = aWorkbench;
		setDefaultPageImageDescriptor(ImageDescriptor.createFromURL(FileLocator.find(IntegrationToolingActivator
				.getDefault().getBundle(), new Path("icons/event.gif"), null)));
		setWindowTitle(IntegrationToolingActivator
				.getEventString("_UI_WIZARD_CREATE_EVENT_MODEL_TITLE"));

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
								.getEditorRegistry().getDefaultEditor(
										iFile.getName());

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

		IFile file = newFilePage.createNewFile();

		//
		// Get the xsd schema name from the full path name
		// e.g. f:/b2b/po.xsd => schema name = po
		//

		// IPath iPath = file.getFullPath().removeFileExtension();

		// String fileName = iPath.lastSegment();
		// String schemaPrefix = "tns";
		// String prefixForSchemaNamespace = "";
		// String schemaNamespaceAttribute = "xmlns";
		// if (XSDEditorPlugin.getPlugin().isQualifyXMLSchemaLanguage()) {
		// Added this if check before disallowing blank prefixes in the
		// preferences...
		// Can take this out. See also XSDEditor
		// if (XSDEditorPlugin.getPlugin().getXMLSchemaPrefix().trim().length()
		// > 0) {
		// prefixForSchemaNamespace =
		// XSDEditorPlugin.getPlugin().getXMLSchemaPrefix() + ":";
		// += ":" + XSDEditorPlugin.getPlugin().getXMLSchemaPrefix();
		// }
		// }

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

		// String defaultTargetURI =
		// XSDEditorPlugin.getPlugin().getXMLSchemaTargetNamespace();
		// newContents += "<" + prefixForSchemaNamespace + "schema " +
		// schemaNamespaceAttribute +
		// "=\"http://www.w3.org/2001/XMLSchema\" targetNamespace=\"" +
		// defaultTargetURI + schemaName + "\" xmlns:" + schemaPrefix + "=\"" +
		// defaultTargetURI + schemaName +
		// "\" elementFormDefault=\"qualified\">\n</" + prefixForSchemaNamespace
		// + "schema>";
		newContents += "<Event>\n<ExitPointType xsi:type=\"version\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ExitPointType>\n<FlowName></FlowName>\n<Overrides>\n</Overrides>\n</Event>\n";
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
