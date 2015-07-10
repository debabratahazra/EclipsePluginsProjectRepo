package com.zealcore.se.ui.actions;

import java.util.Iterator;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.SearchCriteria;
import com.zealcore.se.core.dl.TypeRegistry;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.AbstractTaskInstance;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ReflectiveType;
import com.zealcore.se.core.model.generic.GenericTaskExecution;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.dialogs.OpenPlotDialog;
import com.zealcore.se.ui.editors.ILogSessionWrapper;
import com.zealcore.se.ui.editors.LogsetEditor;
import com.zealcore.se.ui.util.ArtifactSelection;
import com.zealcore.se.ui.views.Plot2;
import com.zealcore.se.ui.views.PlotSearchQuery;
import com.zealcore.se.ui.views.PlottableViewer;
import com.zealcore.se.ui.views.PlottableViewer.PlotType;

public class PlotTaskAction extends AbstractEditorAction {

    @SuppressWarnings("unchecked")
    @Override
    public void runSafe(final IAction action) {
        final IEditorPart editor = getEditor();
        if (!(editor instanceof LogsetEditor)) {
            throw new IllegalStateException(
                    "Action not allowed on this editor type.");
        }
        LogsetEditor ls = (LogsetEditor) editor;
        ILogSessionWrapper uiLogset = ls.getInput().getLog();
        if (!(getSelection() instanceof IStructuredSelection)) {
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) getSelection();

        ITask task;
        Object firstElement = struct.getFirstElement();
        Class searchType = GenericTaskExecution.class;
        if (firstElement instanceof ArtifactSelection) {
            ArtifactSelection arts = (ArtifactSelection) firstElement;
            IObject item = arts.getItem();
            if (!(item instanceof ITask)) {
                return;
            } else {
                task = (ITask) item;
                try {
                    TypeRegistry.getInstance().getType(
                            "com.zealcore.se.core.model.generic.TaskInstance");
                    searchType = AbstractTaskInstance.class;
                } catch (IllegalArgumentException e) {}
            }
        } else {
            return;
        }
        // IArtifact art = (IArtifact) struct.getFirstElement();
        SearchAdapter preDefSearchAdapter = SearchAdapter
                .createSearchAdapter(ReflectiveType.valueOf(searchType));

        for (Iterator<SearchCriteria> iterator = preDefSearchAdapter
                .getCritList().iterator(); iterator.hasNext();) {
            SearchCriteria type = (SearchCriteria) iterator.next();
            if (type.getAttributeName().equalsIgnoreCase("Task")) {
                String taskNameInRegExp = task.getName().replace("(", "\\(");
                taskNameInRegExp = taskNameInRegExp.replace(")", "\\)");
                type.setOperand1(taskNameInRegExp);
                type.setOperand2(taskNameInRegExp);
            }
        }

        final OpenPlotDialog dlg = new OpenPlotDialog(ls.getSite().getShell(),
                preDefSearchAdapter);
        if (dlg.open() == Window.OK) {
            Logset log = Logset.valueOf(uiLogset.getId());
            PlotSearchQuery plotSearchQuery = new PlotSearchQuery(dlg.getAdapter(), dlg.getPlotType());
			long syncKey = log.getLock();
			if (syncKey != -1) {
	            Job job = new PlotDataReader("Getting plot data", plotSearchQuery, log, syncKey);
	            try {
	                openPlotViewer(uiLogset, plotSearchQuery, dlg.getPlotType());
	            } catch (final PartInitException e) {
	                SeUiPlugin.logError(e);
	            }
	            job.schedule();
			}
        }
    }

    @SuppressWarnings("unchecked")
    private  void openPlotViewer(
            final ILogSessionWrapper uiLogset, final PlotSearchQuery plotSearchQuery,
            final PlotType type) throws PartInitException {
        IWorkbenchPage activePage = SeUiPlugin.getDefault()
                .getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IViewPart part = activePage.showView(PlottableViewer.VIEW_ID);
        if (part instanceof PlottableViewer) {
            final PlottableViewer plottViewer = (PlottableViewer) part;
            plotSearchQuery.setPlottViewer(plottViewer);
            plottViewer.setPlot(new Plot2(uiLogset, plotSearchQuery, type));
        }
    }

    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        final IEditorPart editor = getEditor();
        if (!(editor instanceof LogsetEditor)) {
            action.setEnabled(false);
        }
        if (!(selection instanceof IStructuredSelection)) {
            action.setEnabled(false);
            return;
        }
        final IStructuredSelection struct = (IStructuredSelection) selection;
        Object firstElement = struct.getFirstElement();
        action.setEnabled(false);
        if (firstElement instanceof ArtifactSelection) {
            ArtifactSelection arts = (ArtifactSelection) firstElement;
            IObject item = arts.getItem();
            if (item instanceof ITask) {
                action.setEnabled(true);
                // super.selectionChanged(action, firstElement);
            }

        }
        // if (firstElement instanceof ITask) {
        // action.setEnabled(true);
        // } else {
        // action.setEnabled(false);
        // }
        super.selectionChanged(action, selection);
    }
}
