package com.odcgroup.page.validation.ui.internal.markers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.validation.resolution.HtmlIdProblemResolution;

/**
 * This class provides two resolutions for validation markers that are about missing ID properties of widgets.
 * 
 * @author Kai Kreuzer
 *
 */
public class ValidationProblemResolutionGenerator implements
		IMarkerResolutionGenerator2 {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		if(hasResolutions(marker)) {
			return new IMarkerResolution[] {
				// the first resolution only generates ids for the selected model
				new IMarkerResolution2() {
					public void run(final IMarker marker) {
						try {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(true, true, new IRunnableWithProgress() {
								public void run(IProgressMonitor monitor) throws InvocationTargetException,
										InterruptedException {
									try {
										HtmlIdProblemResolution.generateMissingIds(marker.getResource(), monitor);
									} catch (CoreException e) {
										throw new InvocationTargetException(e);
									}
								}
							});
						} catch (InvocationTargetException e) {
							if(e.getCause() instanceof CoreException) {
								CoreException ce = (CoreException) e.getCause();
								ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
										"Error while generating missing ids", "An error has occurred while generating missing ids", ce.getStatus());
							} else {
								MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
										"Error while generating missing ids", e.getCause().getMessage());
							}
						} catch (InterruptedException e) {
							// end silently
						}
					}
					
					public String getLabel() {
						return "Generate missing IDs for this model";
					}
					
					@Override
					public Image getImage() {
						return null;
					}
					
					@Override
					public String getDescription() {
						return "Automatically creates required IDs for widgets of this model.";
					}
				},
				
				// the second solution generates ids for all models in the same project
				new IMarkerResolution2() {
					public void run(final IMarker marker) {
						try {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(true, true, new IRunnableWithProgress() {
								public void run(IProgressMonitor monitor) throws InvocationTargetException,
										InterruptedException {
									try {
										HtmlIdProblemResolution.generateMissingIds(marker.getResource().getProject(), monitor);
									} catch (CoreException e) {
										throw new InvocationTargetException(e);
									}
								}
							});
						} catch (InvocationTargetException e) {
							if(e.getCause() instanceof CoreException) {
								CoreException ce = (CoreException) e.getCause();
								ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
										"Error while generating missing ids", "An error has occurred while generating missing ids", ce.getStatus());
							} else {
								MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
										"Error while generating missing ids", e.getCause().getMessage());
							}
						} catch (InterruptedException e) {
							// end silently
						}
					}
					
					public String getLabel() {
						return "Generate missing IDs for all models of this project";
					}
					
					@Override
					public Image getImage() {
						return null;
					}
					
					@Override
					public String getDescription() {
						return "Automatically creates all required IDs for widgets of all models in the same project.";
					}
				} 

			};
		}
		return new IMarkerResolution2[0];
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		try {
			String msg = (String) marker.getAttribute(IMarker.MESSAGE);
			if(msg!=null && msg.equals("The property Id must be set")) {
				return true;
			}
		} catch (CoreException e) {
			// ignore if we cannot retrieve a message for this marker
		}
		return false;
	}

}
