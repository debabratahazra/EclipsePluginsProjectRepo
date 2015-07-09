package com.odcgroup.page.ui.properties.table.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * TODO: Document me!
 *
 * @author pkk
 * @param <E> 
 * @param <I> 
 *
 */
public abstract class AbstractTypeListControl<E, I> implements IStructuredContentProvider {
	
	/**   */
	private ListViewer viewer;
	
	/**
	 * @param parent
	 */
	public AbstractTypeListControl(Composite parent) {
		createList(parent);
	}
	
	/**
	 * @param parent
	 */
	protected void createList(Composite parent) {
		org.eclipse.swt.widgets.List  listControl = new org.eclipse.swt.widgets.List(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 100;
		listControl.setLayoutData(gridData);
		
		viewer = new ListViewer(listControl);
		configureViewer();
	}
	
	/**
	 * 
	 */
	protected void configureViewer() {
		viewer.setContentProvider(this);
		viewer.setLabelProvider(new LabelProvider() {
			@SuppressWarnings("unchecked")
			public String getText(Object element) {
				return getDisplayText((E) element);
			}			
		});
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object input) {
		return getContents((I)input);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	}
	
	/**
	 * @param input
	 */
	public void setInput(I input) {
		viewer.setInput(input);
	}
	
	/**
	 * @return I
	 */
	@SuppressWarnings("unchecked")
	public I getInput()  {
		return (I)viewer.getInput();
	}
	
	/**
	 * @param element
	 * @return String
	 */
	public abstract String getDisplayText(E element);
	
	/**
	 * @param input
	 * @return object[]
	 */
	public abstract E[] getContents(I input);
	
	/**
	 * @return viewer
	 */
	public ListViewer getViewer() {
		return this.viewer;
	}
	
	/**
	 * @param listener
	 */
	public void addSelectionListener(SelectionListener listener) {
		viewer.getList().addSelectionListener(listener);
	}
	
	/**
	 * @param listener
	 */
	public void removeSelectionListener(SelectionListener listener) {
		viewer.getList().removeSelectionListener(listener);
	}
	
	/**
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<E> getSelection() {
		ISelection selection = viewer.getSelection();
		List<E> selectedItems = new ArrayList<E>();
		if (selection instanceof IStructuredSelection) {
			Object[] objs = ((IStructuredSelection) selection).toArray();
			for (Object object : objs) {
				selectedItems.add((E) object);
			}
		}
		return selectedItems;
	}

}
