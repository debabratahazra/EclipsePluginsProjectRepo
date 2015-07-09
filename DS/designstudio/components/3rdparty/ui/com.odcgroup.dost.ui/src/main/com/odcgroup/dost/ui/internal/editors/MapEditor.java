package com.odcgroup.dost.ui.internal.editors;

import java.io.IOException;

import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.odcgroup.dost.ui.internal.Activator;

public class MapEditor extends TopicEditor {

	final public static String MAP_EDITOR_ID = "com.odcgroup.dost.ui.internal.editors.MapEditor";

	@Override
	protected Templates getXSLTemplates() throws TransformerException,
			IOException {
		return Activator.getDefault().getMapPreviewTemplates();
	}
	
	@Override
	protected Browser createBrowser() {
		Browser newBrowser = super.createBrowser();
		
		// DS-1838 / PCC-13159
		newBrowser.addLocationListener(new LocationListener() {
			public void changing(LocationEvent event) {
				String location = event.location;
				
				if(event.location.equals("about:blank")) return; // this is the entry page, so display it as it is
				
				event.doit = false; // the browser should stay where it is
				if(location.startsWith("about:blank#") && (location.endsWith(".dita") || location.endsWith(".ditamap"))) {	
					location = location.substring("about:blank#".length());
					IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new Path(location).toFile().toURI());
					if(files.length>0 && files[0].exists()) {
						IFile file = files[0];
						IEditorInput input = new FileEditorInput(file);
						try {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, file.getFileExtension().equals("dita") ? EDITOR_ID : MAP_EDITOR_ID);
						} catch (PartInitException e) {
							Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
							ErrorDialog.openError(shell, "Error opening editor", e.getLocalizedMessage(), new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error opening editor for " + file.getLocation().toString(), e));
						}
					} else {
						Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						ErrorDialog.openError(shell, "Error opening editor", "Editor cannot be opened", new Status(IStatus.ERROR, Activator.PLUGIN_ID, "File does not exist: " + location, null));
					}
					return;
				}
			}
			public void changed(LocationEvent event) {
				// do nothing here
			}
		});
		return newBrowser;
	}


}
