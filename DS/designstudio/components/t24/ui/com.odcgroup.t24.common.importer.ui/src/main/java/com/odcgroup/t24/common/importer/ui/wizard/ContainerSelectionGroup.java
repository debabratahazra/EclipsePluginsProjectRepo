package com.odcgroup.t24.common.importer.ui.wizard;

/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Igor Fedorenko <igorfie@yahoo.com> - 
 *     		Fix for Bug 136921 [IDE] New File dialog locks for 20 seconds
 *******************************************************************************/

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.odcgroup.t24.common.importer.IContainerSelector;
import com.odcgroup.t24.common.importer.ui.Messages;

/**
 * Composite for choosing a container.
 */
public class ContainerSelectionGroup {
	
	// the message to display at the top of this dialog
	private static final String DEFAULT_MSG_SELECT_ONLY = Messages.FolderSelectionPage_message;

	private static final int SIZING_SELECTION_PANE_HEIGHT = 300;

	// sizing constants
	private static final int SIZING_SELECTION_PANE_WIDTH = 320;

	// The listener to notify of events
	private Listener listener;

	private TreeViewer treeViewer;
	
	private IContainerSelector containerSelector;
	
	/**
	 * The container selection has changed in the tree view. Update the
	 * container name field value and notify all listeners.
	 * 
	 * @param container
	 *            The container that changed
	 */
	protected void containerSelectionChanged(IContainer container) {
		setDestinationFolder(container);
	}
	
	
	protected void setDestinationFolder(IContainer container) {
		if (container.getFullPath().segmentCount() > containerSelector.minSelectedLevel()) {
			try {
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IContainer destinationContainer = workspace.getRoot().getFolder(container.getFullPath());
				containerSelector.setContainer(destinationContainer);
			} catch (Exception ex) {
				containerSelector.setContainer(null);
			}
		} else {
			if (containerSelector.minSelectedLevel() == 2) {
				// Project level selection only
				IFolder folder = getDummyFolder(container.getName());
				containerSelector.setContainer(folder);
			} else {
				containerSelector.setContainer(null);
			}
		}
		// fire an event so the parent can update its controls
		if (listener != null) {
			Event changeEvent = new Event();
			changeEvent.type = SWT.Selection;
			changeEvent.widget = null;
			listener.handleEvent(changeEvent);
		}
	}
	
	private IFolder getDummyFolder(String projectName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project != null && project.exists()) {
			IResource resource[];
			try {
				resource = project.members();
				for (IResource iResource : resource) {
					if (iResource instanceof IFolder) {
						return (IFolder) iResource;
					}
				}
			} catch (CoreException e) {
			}
		}
		return null;
	}

	protected final IContainerSelector getFolderSelector() {
		return this.containerSelector;
	}
	
	protected ITreeContentProvider createContainerContentProvider(IContainerSelector selector) {
		return new ContainerContentProvider(selector);
	}
	
	protected IBaseLabelProvider createContainerLabelProvider(IContainerSelector selector) {
		return WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider();
	}
	
	protected ViewerComparator createViewerComparator() {
		return new ViewerComparator();
	}

	/**
	 * Creates the contents of the composite.
	 * 
	 * @param message
	 * @param heightHint
	 * @param widthHint
	 */
	protected void createContents(Composite parent, String message, int heightHint, int widthHint) {

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		composite.setLayoutData(gd);
		
		Label label = new Label(composite, SWT.WRAP);
		label.setText(message);

		// filler...
		new Label(composite, SWT.NULL);

		// Create tree viewer inside drill down.
		treeViewer = new TreeViewer(composite, SWT.BORDER);
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		treeViewer.setContentProvider(createContainerContentProvider(getFolderSelector()));
		treeViewer.setLabelProvider(createContainerLabelProvider(getFolderSelector()));
		treeViewer.setComparator(createViewerComparator());
		treeViewer.setUseHashlookup(true);
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				containerSelectionChanged((IContainer) selection.getFirstElement()); // allow null
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object item = ((IStructuredSelection) selection)
							.getFirstElement();
					if (item == null) {
						return;
					}
					if (treeViewer.getExpandedState(item)) {
						treeViewer.collapseToLevel(item, 1);
					} else {
						treeViewer.expandToLevel(item, TreeViewer.ALL_LEVELS);
					}
				}
			}
		});

		// This has to be done after the viewer has been laid out
		treeViewer.setInput(ResourcesPlugin.getWorkspace());
		treeViewer.expandToLevel(2);
	}

	/**
	 * Gives focus to one of the widgets in the group, as determined by the
	 * group. This can trigger change event.
	 */
	public void setInitialFocus() {
		if (treeViewer != null) {
			treeViewer.getTree().setFocus();
		}
	}

	/**
	 * Creates a new instance of the widget.
	 * 
	 * @param parent
	 *            The parent widget of the group.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is
	 *            required.
	 * @param selector
	 *            the selector used to get the candidate folders.
	 */
	public ContainerSelectionGroup(Composite parent, Listener listener, IContainerSelector selector) {
		this(parent, listener, selector, SIZING_SELECTION_PANE_HEIGHT, SIZING_SELECTION_PANE_WIDTH);
	}

	/**
	 * Creates a new instance of the widget.
	 * 
	 * @param parent
	 *            The parent widget of the group.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is
	 *            required.
	 * @param selector
	 *            A selector used to get the candidate folders.
	 * @param heightHint
	 *            height hint for the drill down composite
	 * @param widthHint
	 *            width hint for the drill down composite
	 */
	public ContainerSelectionGroup(Composite parent, Listener listener, 
			IContainerSelector folderSelector, 
			int heightHint, int widthHint) {
		this.listener = listener;
		this.containerSelector = folderSelector;
		createContents(parent, DEFAULT_MSG_SELECT_ONLY, heightHint, widthHint);
	}
}

