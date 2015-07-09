package com.odcgroup.mdf.editor.ui.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.odcgroup.mdf.editor.MdfCore;


public class Mml2ECoreAction implements IObjectActionDelegate {
    private static final Logger LOGGER =
        Logger.getLogger(Mml2ECoreAction.class);
    private IWorkbenchPart targetPart;
    private StructuredSelection selection = StructuredSelection.EMPTY;

    /**
     * Constructor for Action1.
     */
    public Mml2ECoreAction() {
        super();
    }

    /**
     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.targetPart = targetPart;
    }

    /**
     * @see IActionDelegate#run(IAction)
     */
    public void run(IAction action) {
        IRunnableWithProgress op =
            new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                    try {
                        doConvert(monitor);
                    } catch (Exception e) {
                        throw new InvocationTargetException(e);
                    }
                }
            };

        IWorkbenchWindow window = targetPart.getSite().getWorkbenchWindow();

        try {
            window.run(false, true, op);
        } catch (InterruptedException e) {
            LOGGER.error(e, e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getTargetException();
            LOGGER.error(cause, cause);
            MdfCore.openError(window.getShell(), cause);
        }
    }

    /**
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = (StructuredSelection) selection;
        action.setEnabled(!this.selection.isEmpty());
    }

    private void doConvert(IProgressMonitor monitor)
        throws IOException, CoreException {
        monitor.beginTask("Loading models", selection.size());

        try {
            ResourceSet resourceSet = new ResourceSetImpl();

            Iterator it = selection.iterator();
            while (it.hasNext()) {
                IFile file = (IFile) it.next();

                URI uri = URI.createURI(file.getLocationURI().toString());
                Resource resource = resourceSet.getResource(uri, true);
                if (resource != null) {
                    IFile copy = file.getParent().getFile(new Path(file.getName() + ".domain"));
                    URI copyURI = URI.createURI(copy.getLocationURI().toString());
                    resource.setURI(copyURI);
                    resource.save(Collections.EMPTY_MAP);
                }
            }
            
        } finally {
            monitor.done();
        }
    }
}
