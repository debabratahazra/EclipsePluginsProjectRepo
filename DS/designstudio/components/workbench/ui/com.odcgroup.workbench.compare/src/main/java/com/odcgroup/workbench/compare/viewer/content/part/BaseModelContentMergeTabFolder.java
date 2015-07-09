package com.odcgroup.workbench.compare.viewer.content.part;

import org.eclipse.emf.compare.ui.EMFCompareUIMessages;
import org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer;
import org.eclipse.emf.compare.ui.viewer.content.part.IModelContentMergeViewerTab;
import org.eclipse.emf.compare.ui.viewer.content.part.ModelContentMergeTabFolder;
import org.eclipse.emf.compare.ui.viewer.content.part.property.ModelContentMergePropertyTab;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scrollable;

/**
 *
 *
 */
public abstract class BaseModelContentMergeTabFolder extends ModelContentMergeTabFolder {
	
	
	/**
	 * @param viewer
	 * @param composite
	 * @param side
	 */
	public BaseModelContentMergeTabFolder(ModelContentMergeViewer viewer, Composite composite, int side) {
		super(viewer, composite, side);
	}

	/**
	 * Creates the contents of this viewer part given its parent composite.
	 * 
	 * @param composite
	 *            Parent composite of this viewer parts's widgets.
	 */
	final protected void createContents(Composite composite) {
		tabFolder = new CTabFolder(composite, SWT.BOTTOM);
		final CTabItem treeTab = new CTabItem(tabFolder, SWT.NONE);
		treeTab.setText(EMFCompareUIMessages.getString("ModelContentMergeViewerTabFolder.tab1.name")); //$NON-NLS-1$

		final CTabItem propertiesTab = new CTabItem(tabFolder, SWT.NONE);
		propertiesTab.setText(EMFCompareUIMessages.getString("ModelContentMergeViewerTabFolder.tab2.name")); //$NON-NLS-1$

		final Composite treePanel = new Composite(tabFolder, SWT.NONE);
		treePanel.setLayout(new GridLayout());
		treePanel.setLayoutData(new GridData(GridData.FILL_BOTH));
		treePanel.setFont(composite.getFont());
		tree = createViewerTab(treePanel);
		treeTab.setControl(treePanel);

		final Composite propertyPanel = new Composite(tabFolder, SWT.NONE);
		propertyPanel.setLayout(new GridLayout());
		propertyPanel.setLayoutData(new GridData(GridData.FILL_BOTH));
		propertyPanel.setFont(composite.getFont());
		properties = createPropertiesPart(propertyPanel);
		propertiesTab.setControl(propertyPanel);

		tabs.add(tree);
		tabs.add(properties);

		tabFolder.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				setSelectedTab(tabFolder.getSelectionIndex());
				fireSelectedtabChanged();
			}
		});
		tabFolder.setSelection(treeTab);
	}
	
	/**
	 * implement handling the creation of the viewer part given the parent {@link Composite} under which
	 * to create it.
	 * 
	 * @param composite
	 *            Parent {@link Composite} of the tree to create.
	 * @return The tree part displayed by this viewer part's tree tab.
	 */
	public abstract IModelContentMergeViewerTab createViewerTab(Composite composite);
	
	/**
	 * Handles the creation of the properties tab of this viewer part given the parent {@link Composite} under
	 * which to create it.
	 * 
	 * @param composite
	 *            Parent {@link Composite} of the table to create.
	 * @return The properties part displayed by this viewer part's properties tab.
	 */
	protected IModelContentMergeViewerTab createPropertiesPart(Composite composite) {
		final IModelContentMergeViewerTab propertiesPart = new ModelContentMergePropertyTab(composite,
				partSide, this);

		((Scrollable)propertiesPart.getControl()).getVerticalBar().addSelectionListener(
				new SelectionListener() {
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}

					public void widgetSelected(SelectionEvent e) {
						parentViewer.updateCenter();
					}
				});

		propertiesPart.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				fireSelectionChanged(event);
			}
		});

		return propertiesPart;
	}

}
