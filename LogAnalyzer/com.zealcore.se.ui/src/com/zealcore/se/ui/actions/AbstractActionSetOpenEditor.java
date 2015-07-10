package com.zealcore.se.ui.actions;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.ILogViewInput;
import com.zealcore.se.ui.editors.LogsetEditor;
import com.zealcore.se.ui.internal.LogViewInput;
import com.zealcore.se.ui.internal.SWTUtil;
import com.zealcore.se.ui.views.NavigatorLabelProvider;

public abstract class AbstractActionSetOpenEditor implements
        IWorkbenchWindowActionDelegate {

    private ILogSessionWrapper logsetWrapper;

    private Logset logset;

    static final IArtifact NO_DATA = new AbstractArtifact() {};

    /**
     */
    public void run(final IAction action) {
        openBrowser(null, getBrowserId(), null);
    }
    
    protected abstract String getBrowserId();

    protected abstract String getName();

    private static IWorkbenchPage getActivePage() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage();
    }

    protected void openBrowser(final ILogSessionWrapper logset,
            final String browserId, final IArtifactID data) {
        try {
            openSelectLogset();
            final IWorkbenchPage page = getActivePage();
            final ILogViewInput input = LogViewInput.valueOf(logsetWrapper,
                    browserId);
            IArtifactID data2 = NO_DATA;
            if (data2 == NO_DATA) {
                input.setData(null);
                page.openEditor(input, LogsetEditor.EDITOR_ID);
            } else if (data2 != null) {
                input.setData(data2);
                page.openEditor(input, LogsetEditor.EDITOR_ID);
            }

        } catch (RuntimeException e) {
            handleException(e);
        } catch (PartInitException e) {
            handleException(e);
        }
    }

    protected void handleException(final Throwable e) {
        SeUiPlugin.reportUnhandledRuntimeException(this.getClass(), e, true);
    }

    public AbstractActionSetOpenEditor() {
        super();
    }

    /**
     * Selection in the workbench has been changed. We can change the state of
     * the 'real' action here if we want, but this can only happen after the
     * delegate has been created.
     * 
     * @see IWorkbenchWindowActionDelegate#selectionChanged
     */
    public void selectionChanged(final IAction action,
            final ISelection selection) {}

    /**
     * We can use this method to dispose of any system resources we previously
     * allocated.
     * 
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    public void dispose() {}

    /**
     */
    public void init(final IWorkbenchWindow window) {}

    protected void openSelectLogset() {

        final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
                null, new NavigatorLabelProvider(),
                new BaseWorkbenchContentProvider());

        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        dialog.setInput(workspaceRoot);
        dialog.setTitle("Select Logset to show in the " + getName());
        dialog.addFilter(new ViewerFilter() {
            @Override
            public boolean select(final Viewer viewer,
                    final Object parentElement, final Object element) {
                if (CaseFileManager.isCaseFile(element)) {
                    return true;
                }
                if (element instanceof IAdaptable) {
                    return null != ((IAdaptable) element)
                            .getAdapter(ILogSessionWrapper.class);
                }
                return false;
            }
        });

        dialog.setValidator(new ISelectionStatusValidator() {

            public IStatus validate(final Object[] selection) {
                final Status error = new Status(IStatus.ERROR,
                        SeUiPlugin.PLUGIN_ID, 0, "Failed to select logset",
                        new IllegalStateException(
                                "Exactly one logset must be selected"));
                if (selection.length != 1) {
                    return error;
                }
                final Object element = selection[0];
                if (element instanceof IAdaptable) {
                    if (null != toLogset(element)) {
                        return Status.OK_STATUS;
                    }
                }
                return error;
            }
        });

        if (logset != null) {
            dialog.setInitialSelection(logset);
        }

        if (dialog.open() == Window.OK) {
            setLogset(toLogset(dialog.getFirstResult()));
            // updateErrorMessage();
        }

    }

    private void setLogset(final ILogSessionWrapper selectedLogset) {
        logset = Logset.valueOf(selectedLogset.getId());
        logsetWrapper = selectedLogset;
    }

    private ILogSessionWrapper toLogset(final Object element) {
        if (element instanceof IAdaptable) {
            final IAdaptable adapt = (IAdaptable) element;
            return (ILogSessionWrapper) adapt
                    .getAdapter(ILogSessionWrapper.class);

        }
        return null;
    }

    protected Shell getShell() {
        return SWTUtil.getActiveEditor().getSite().getShell();
    }

}
