package com.odcgroup.t24.version.editor.ui.providers;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;

import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;

public class CellEditingSupport extends ObservableValueEditingSupport {

	private CellEditor cellEditor;
	private VersionDesignerEditor designEditor;

	/**
	 * @param viewer
	 * @param dbc
	 */
	public CellEditingSupport(ColumnViewer viewer, DataBindingContext dbc,
			CellEditor editor, VersionDesignerEditor versionEditor) {
		super(viewer, dbc);
		this.cellEditor = editor;
		this.designEditor = versionEditor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
	 */
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport#doCreateCellEditorObservable(org.eclipse.jface.viewers.CellEditor)
	 */
	protected IObservableValue doCreateCellEditorObservable(
			final CellEditor cellEditor) {
		IObservableValue value = null;
		if (cellEditor instanceof ComboBoxCellEditor) {
			value = WidgetProperties.selection().observe(Realm.getDefault(),
					cellEditor.getControl());
		}
		if (cellEditor instanceof TextCellEditor) {
			value = WidgetProperties.text(SWT.Modify).observe(
					Realm.getDefault(), cellEditor.getControl());
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport#doCreateElementObservable(java.lang.Object, org.eclipse.jface.viewers.ViewerCell)
	 */
	protected IObservableValue doCreateElementObservable(Object element,
			ViewerCell cell) {
		return null;

	}

	/**
	 * @return
	 */
	protected VersionDesignerEditor getEditor() {
		return designEditor;
	}
}
