package com.odcgroup.workbench.ui.action.edit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.navigator.ICommonViewerSite;

import com.odcgroup.workbench.core.IOfsElement;

/**
 * Action that dispatches the <code>IAction#run()</code> and the
 * <code>ISelectionChangedListener#selectionChanged</code> according to the type
 * of the selection.
 * 
 * @author atr
 */
public abstract class SelectionDispatchAction extends Action implements ISelectionChangedListener {

	/**
	 * The empty list
	 */
	private static final List<IOfsElement> EMPTY_LIST = Arrays.asList(new IOfsElement[0]);

	/**
	 * The site owning this action
	 */
	private ICommonViewerSite site;

	/**
	 * The selection provider notifies this action whenever the selection change
	 */
	private ISelectionProvider selProvider;

	/**
	 * Indicates whether the selection has changes since <code>resources</code>
	 * and <code>nonResources</code> were computed.
	 */
	private boolean selectionDirty = true;
	
	/**
	 * The list of OFS element in the selection
	 */
	private List<IOfsElement> ofsElements;

	/**
	 * @param selection
	 */
	private void dispatchSelectionChanged(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			selectionChanged((IStructuredSelection) selection);
		} else {
			selectionChanged(selection);
		}
	}

	/**
	 * @param selection
	 */
	private void dispatchRun(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			run((IStructuredSelection) selection);
		} else {
			run(selection);
		}
	}

	/**
	 * @return the current IStructuredSelection or {@code null} if not compatible.
	 */
	protected IStructuredSelection getStructuredSelection() {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			return (IStructuredSelection)selection;
		}
		return null;
	}
	
	/**
	 * @param element the OFS element
	 * @return the parent URI of the given OFS element
	 */
	protected URI getParentURI(IOfsElement element) {
		URI uri = element.getURI();
		return uri.trimSegments(1);
	}	
	
	/**
	 * Checks if the given OFS element can be part of the selection. This
	 * default implementation returns always {@code false}
	 * 
	 * @param element
	 *            the OFS Element to check
	 * 
	 * @return {@code true} if the given OFS element can be part of the
	 *         selection
	 */
	protected abstract boolean acceptOfsElement(IOfsElement element);
	
	/**
	 * Collects from the selection all OFS elements.
	 * They must be homogeneous and must have the same parents
	 */
	@SuppressWarnings("unchecked")
	private void computeOfsElements() {
		
		IStructuredSelection selection = getStructuredSelection();
		if (selection == null) return;

		ofsElements = null;
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object next = iter.next();
			if (next instanceof IOfsElement) {
				if (acceptOfsElement((IOfsElement) next)) {
					if (ofsElements == null) {
						// assume selection contains mostly OFS resources most times
						ofsElements = new ArrayList<IOfsElement>(selection.size());
					}
					ofsElements.add((IOfsElement) next);
				} else {
					// collects here non accepted resources
				}
			}
		}
	}

	/**
	 * @return the elements in the current selection that are
	 *         <code>IOfsElement</code>s.
	 */
	protected List<IOfsElement> getSelectedOfsElements() {
		// recompute if selection has changed.
		if (selectionDirty) {
			computeOfsElements();
			selectionDirty = false;
		}

		if (ofsElements == null) {
			return EMPTY_LIST;
		}
		return ofsElements;
	}

	/**
	 * Returns the selection provided by the site owning this action.
	 * 
	 * @return the site's selection
	 */
	protected ISelection getSelection() {
		ISelection selection = null;
		ISelectionProvider selectionProvider = getSelectionProvider();
		if (selectionProvider != null) {
			selection = selectionProvider.getSelection();
		}
		return selection;
	}

	/**
	 * Executes this actions with the given structured selection. This default
	 * implementation calls <code>run(ISelection selection)</code>.
	 * 
	 * @param selection
	 *            the selection
	 */
	protected void run(IStructuredSelection selection) {
		run((ISelection) selection);
	}

	/**
	 * Executes this actions with the given selection. This default
	 * implementation does nothing.
	 * 
	 * @param selection
	 *            the selection
	 */
	protected void run(ISelection selection) {
	}

	/**
	 * Notifies this action that the given structured selection has changed.
	 * This default implementation calls
	 * <code>selectionChanged(ISelection selection)</code>.
	 * 
	 * @param selection
	 *            the new selection
	 */
	protected void selectionChanged(IStructuredSelection selection) {
		selectionChanged((ISelection) selection);
	}

	/**
	 * Notifies this action that the given selection has changed. This default
	 * implementation sets the action's enablement state to <code>false</code>.
	 * 
	 * @param selection
	 *            the new selection
	 */
	protected void selectionChanged(ISelection selection) {
		setEnabled(false);
	}

	/**
	 * Returns the site owning this action.
	 * 
	 * @return the site owning this action
	 */
	public final ICommonViewerSite getSite() {
		return site;
	}

	/**
	 * Returns the selection provider managed by the site owning this action
	 * 
	 * @return the site's selection provider
	 */
	public final ISelectionProvider getSelectionProvider() {
		ISelectionProvider provider = null;
		if (selProvider != null) {
			provider = selProvider;
		} else {
			provider = site.getSelectionProvider();
		}
		return provider;
	}

	/**
	 * Sets a special selection provider which will be used instead of the
	 * site's selection provider. This method should be used directly after
	 * constructing the action and before the action is registered as a
	 * selection listener.
	 * 
	 * @param provider
	 *            a special selection provider which is used
	 */
	public void setSelectionProvider(ISelectionProvider provider) {
		this.selProvider = provider;
	}

	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		dispatchRun(getSelection());
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		selectionDirty = true;
		dispatchSelectionChanged(event.getSelection());
	}

	/**
	 * @param site
	 */
	public SelectionDispatchAction(ICommonViewerSite site) {
		this.site = site;
	}

}
