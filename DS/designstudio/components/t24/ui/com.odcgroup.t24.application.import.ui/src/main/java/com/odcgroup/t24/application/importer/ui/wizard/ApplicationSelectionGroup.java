package com.odcgroup.t24.application.importer.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.t24.application.importer.IApplicationSelector;
import com.odcgroup.t24.application.importer.ui.Messages;
import com.odcgroup.t24.server.external.model.ApplicationDetail;

public class ApplicationSelectionGroup {

	private int totalFileCount = 0;
	private int selectedFileCount = 0;
	private Label label = null;
	private Composite buttonComposite = null;

	private static class ContentProvider implements IStructuredContentProvider {
		public void dispose() {
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((List<ApplicationDetail>) inputElement).toArray();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private static class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			ApplicationDetail application = (ApplicationDetail) element;
			String result = ""; //$NON-NLS-1$
			switch (columnIndex) {
			case 0: {
				String str = application.getName();
				result = StringUtils.isBlank(str) ? Messages.ApplicationSelectionGroup_applicationDefaultName : str;
				break;
			}
			case 1:
				result = application.getComponent();
				break;
			case 2:
				result = application.getProduct();
				break;
			case 3:
				result = application.getApplicaitonDescription();
				break;
			default:
				// should not reach here
				result = ""; //$NON-NLS-1$
			}
			return result;
		}
	}

	public class ApplicationSorter extends ViewerSorter {
		private static final int ASCENDING = 0;
		private static final int DESCENDING = 1;
		private int direction = ASCENDING;
		private int column;

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			ApplicationDetail v1 = (ApplicationDetail) e1;
			ApplicationDetail v2 = (ApplicationDetail) e2;
			int rc = 0;
			switch (column) {
			case 0:
				rc = v1.getName().compareTo(v2.getName());
				break;
			case 1:
				rc = v1.getComponent().compareTo(v2.getComponent());
				break;
			case 2:
				rc = v1.getProduct().compareTo(v2.getProduct());
				break;
			case 3:
				rc = v1.getApplicaitonDescription().compareTo(v2.getApplicaitonDescription());
				break;
			default:
				rc = 0;
			}
			// If descending order, flip the direction
			if (direction == DESCENDING) {
				rc = -rc;
			}
			return rc;
		}

		public int getDirection() {
			return direction == 1 ? SWT.DOWN : SWT.UP;
		}

		public void setColumn(int column) {
			if (column == this.column) {
				// Same column as last sort; toggle the direction
				direction = 1 - direction;
			} else {
				// New column; do an ascending sort
				this.column = column;
				direction = DESCENDING;
			}
		}

		public ApplicationSorter() {
			this.column = 0;
			direction = ASCENDING;
		}

	}

	private IApplicationSelector selector;

	private Listener listener;

	private CheckboxTableViewer tableViewer;

	/**
	 * @return the tableViewer
	 */
	public CheckboxTableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * @param layout
	 * @param header
	 * @param weight
	 */
	private void createColumn(TableColumnLayout layout, final int columnIndex, String header, int weight) {
		TableColumn tc = new TableColumn(tableViewer.getTable(), SWT.NONE);
		tc.setText(header);
		tc.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((ApplicationSorter) tableViewer.getSorter()).setColumn(columnIndex);
				tableViewer.refresh();
			}
		});
		layout.setColumnData(tc, new ColumnWeightData(weight));
	}

	protected void handleAllSelectionChange(boolean state) {
		ArrayList arrayList = (ArrayList) tableViewer.getInput();
		if (!state) {
			selector.getSelectedModels().removeAll(arrayList);
		} else {
			selector.getSelectedModels().addAll(arrayList);
		}
		tableViewer.setAllChecked(state);

		// fire an event so the parent can update its controls
		if (listener != null) {
			Event changeEvent = new Event();
			changeEvent.type = SWT.Selection;
			changeEvent.widget = tableViewer.getTable();
			listener.handleEvent(changeEvent);
		}
		updateCountLabel();
	}

	/**
	 * @param parent
	 */
	public void createContents(Composite parent) {

		selectedFileCount = 0;
		totalFileCount = 0;
		Group previewGroup = new Group(parent, SWT.SHADOW_ETCHED_IN | SWT.RESIZE);
		previewGroup.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		gd.heightHint = 220;
		previewGroup.setLayoutData(gd);
		previewGroup.setText(Messages.ApplicationSelectionGroup_previewGroupLabel);

		Composite composite = new Composite(previewGroup, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		int styleTableViewer = SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER;
		tableViewer = CheckboxTableViewer.newCheckList(composite, styleTableViewer);
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setSorter(new ApplicationSorter());

		TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);
		createColumn(layout, 0, Messages.ApplicationSelectionGroup_columnApplication, 100);
		createColumn(layout, 1, Messages.ApplicationSelectionGroup_columnComponent, 100);
		createColumn(layout, 2, Messages.ApplicationSelectionGroup_columnProduct, 100);
		createColumn(layout, 3, Messages.ApplicationSelectionPage_columnDescription, 100);

		tableViewer.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object element = event.getElement();
				if (event.getChecked()) {

					selector.getSelectedModels().add((ApplicationDetail) element);

				} else {
					selector.getSelectedModels().remove(element);
				}
				// fire an event so the parent can update its controls
				if (listener != null) {
					Event changeEvent = new Event();
					changeEvent.type = SWT.Selection;
					changeEvent.widget = tableViewer.getTable();
					listener.handleEvent(changeEvent);
				}
				updateCountLabel();

			}
		});

		tableViewer.setCheckStateProvider(new ICheckStateProvider() {

			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				if (selector.getSelectedModels().contains(element))
					return true;
				return false;
			}
		});

		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		buttonComposite = new Composite(previewGroup, SWT.RESIZE);
		buttonComposite.setLayout(new GridLayout(3, false));
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		gridData.grabExcessHorizontalSpace = true;
		buttonComposite.setLayoutData(gridData);

		label = new Label(buttonComposite, SWT.RIGHT);
		GridData gd1 = new GridData();
		gd1.widthHint = 150;
		label.setLayoutData(gd1);

		Button selectButton = new Button(buttonComposite, SWT.PUSH);
		selectButton.setLayoutData(new GridData(SWT.END));
		selectButton.setText(Messages.ApplicationSelectionGroup_SelectAll);
		selectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setAllChecked(true);
			}
		});

		Button deselectButton = new Button(buttonComposite, SWT.PUSH);
		deselectButton.setLayoutData(new GridData(SWT.END));
		deselectButton.setText(Messages.ApplicationSelectionGroup_UnselectAll);
		deselectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setAllChecked(false);
			}
		});
	}

	protected void updateCountLabel() {
		if (totalFileCount == 0) {
			totalFileCount = tableViewer.getTable().getItemCount();
		}
		selectedFileCount = selector.getSelectedModels().size();
		label.setText(selectedFileCount + " selected out of " + totalFileCount);
		buttonComposite.changed(buttonComposite.getChildren());
	}

	public void setAllChecked(boolean state) {
		if (tableViewer != null) {
			handleAllSelectionChange(state);

		}
	}

	public void setInput(List<ApplicationDetail> applicationList) {
		if (tableViewer != null) {
			tableViewer.setInput(applicationList);
			updateCountLabel();
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
	 */
	public ApplicationSelectionGroup(Composite parent, Listener listener, IApplicationSelector selector, String message) {
		this.selector = selector;
		this.listener = listener;
		createContents(parent);
	}

}
