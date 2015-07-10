package com.zealcore.se.ui.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.IQueryFilter;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.ui.editors.GanttChart.TaskFilter;
import com.zealcore.se.ui.editors.ILogsetBrowser;
import com.zealcore.se.ui.editors.LogsetEditor;

public final class LogsetEditorFilterAction extends AbstractEditorAction {

    private static final String FILTER_DESCR_STRING = "Select the elements to exclude from the view:";

    @Override
    protected void runSafe(final IAction action) {
        if (!(getTargetEditor() instanceof LogsetEditor)) {
            return;
        }
        LogsetEditor editor = (LogsetEditor) getTargetEditor();

        ILogsetBrowser logsetBrowser = editor.getLogsetBrowser();
        if (logsetBrowser instanceof IQueryFilter) {
            IQueryFilter queryFilter = (IQueryFilter) logsetBrowser;

            Collection<IFilter<IObject>> dynFilter = queryFilter.getFilter();
            Object[] array = null;
            if (dynFilter.size() > 0) {
                array = dynFilter.toArray();
                Arrays.sort(array, new ObjectNameSort());
            }

			final FilteredListSelectionDialog dlg = new FilteredListSelectionDialog(
					editor.getEditorSite().getShell(), array,
					new ArrayContentProvider(), new LabelProvider(),
					FILTER_DESCR_STRING);
			dlg.setTitle("Selection Needed");
            Object[] arraySelect = null;
            Collection<IFilter<IObject>> filterSelections = queryFilter
                    .getFilterSelections();
            if (filterSelections.size() > 0) {
                arraySelect = filterSelections.toArray();
                Arrays.sort(arraySelect, new ObjectNameSort());
				for (int i = 0; i < array.length; i++) {
					for (int j = 0; j < arraySelect.length; j++) {
						if (array[i].equals(arraySelect[j])) {
							((TaskFilter)array[i]).setChecked(true);
						}
					}
				}
            }
            if (dlg.open() == Window.OK) {

                Collection<IFilter<IObject>> newFilters = new ArrayList<IFilter<IObject>>();

                for (final Object result : dlg.getResult()) {
                    for (IFilter<IObject> filter : dynFilter) {
                        if (filter.toString().equals(result.toString())) {
                            newFilters.add(filter);
                            break;
                        }
                    }
                }
                queryFilter.setFilter(newFilters);
                editor.refresh();
            }

        } else {
            // TODO rewrite filter
            // ignore extensionpoints filters, display empty list
			final FilteredListSelectionDialog dlg = new FilteredListSelectionDialog(editor
                    .getEditorSite().getShell(), null,
                    new ArrayContentProvider(), new LabelProvider(),
                    FILTER_DESCR_STRING);

            dlg.open();
        }
        
    }

    static class ObjectNameSort implements Comparator<Object> {
        public int compare(final Object o1, final Object o2) {
            return o1.toString().compareToIgnoreCase(o2.toString());
        }
    }
    
    @Override
    public void selectionChanged(final IAction action,
            final ISelection selection) {
        final IEditorPart editor = getEditor();
        if (editor instanceof LogsetEditor) {
            LogsetEditor edit = (LogsetEditor) editor;
            if (edit.getLogsetBrowser() instanceof IQueryFilter) {
                action.setEnabled(true);
            } else {
                action.setEnabled(false);
            }
        }
        super.selectionChanged(action, selection);
    }
}
