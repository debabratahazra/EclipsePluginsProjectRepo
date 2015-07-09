package com.odcgroup.workbench.editors.properties.controls;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.workbench.editors.properties.util.ListReferenceColumn;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;
import com.odcgroup.workbench.editors.ui.widgets.ReferenceUtil;

/**
 * @author pkk
 * 
 */
public class PropertyTableWidget {

	protected Table table;
	private   Font  tableFont = null;
	protected Button deleteButton;
	protected Button addButton;
	protected Button modifyButton;
	protected Button moveUpBtn;
	protected Button moveDownBtn;
	protected TableViewer tableViewer;
	protected boolean basicSection = false;
	protected Composite composite;
	protected boolean multiSelect = false;
	protected boolean readOnly = false;

	/**
	 * @param parent
	 * @param style
	 * @param reference
	 * @param columns
	 * @param booleanColumns
	 * @param basicSection
	 */
	public PropertyTableWidget(Composite parent, int style,
			ReferenceUtil reference, List<ListReferenceColumn> columns,
			boolean basicSection) {
		this.basicSection = basicSection;
		createViewComponents(parent, style, reference, columns);
	}

	/**
	 * @param parent
	 * @param style
	 * @param reference
	 * @param columns
	 * @param basicSection
	 * @param multiSelect
	 */
	public PropertyTableWidget(Composite parent, int style,
			ReferenceUtil reference, List<ListReferenceColumn> columns,
			boolean basicSection, boolean multiSelect) {
		this.basicSection = basicSection;
		this.multiSelect = multiSelect;
		createViewComponents(parent, style, reference, columns);
	}

	/**
	 * @param parent
	 * @param style
	 */
	public PropertyTableWidget(Composite parent, int style,
			ReferenceUtil reference, List<ListReferenceColumn> columns) {
		this(parent, style, reference, columns, false);
	}

	/**
	 * @param parent
	 * @param style
	 * @param reference
	 * @param columns
	 * @param booleanColumns
	 */
	protected void createViewComponents(Composite parent, int style,
			ReferenceUtil reference, List<ListReferenceColumn> columns) {
		int parentColumns = 2;
		int buttonColumns = 1;

		composite = OFSUIFactory.INSTANCE.createComposite(parent,
				parentColumns, style);
		TableLayoutComposite tableComposite = new TableLayoutComposite(
				composite, SWT.FILL);
		Composite buttonComposite = OFSUIFactory.INSTANCE.createComposite(
				composite, buttonColumns, 1040);

		createButtonComposite(buttonComposite, reference.getLabel(), reference
				.isSorter(), reference.isEditable());
		createTableComposite(tableComposite, columns);
		createTableViewer(columns, reference.isSorter(), reference.isEditable());

	}

	/**
	 * 
	 */
	public void dispose() {
		if (composite != null) {
			composite.dispose();
		}
		if (tableFont != null) {
			tableFont.dispose();
		}
		
	}

	/**
	 * @param buttonComposite
	 * @param label
	 */
	protected void createButtonComposite(Composite buttonComposite,
			String label, boolean sorter, boolean editable) {
		GridData data = new GridData();
		data.heightHint = 25;
		data.widthHint = 60;

		// add button
		addButton = new Button(buttonComposite, 0x800000);
		addButton.setToolTipText("Add " + label);
		addButton.setLayoutData(data);
		// addButton.setImage(OFSPropertyPlugIn.getBundledImageDescriptor("icons/obj16/add.gif").createImage());
		addButton.setText("Add");

		if (editable) {
			// modify button
			modifyButton = new Button(buttonComposite, 0x800000);
			modifyButton.setToolTipText("Edit selected " + label);
			modifyButton.setLayoutData(data);
			// modifyButton.setImage(OFSPropertyPlugIn.getBundledImageDescriptor("icons/obj16/modify.gif").createImage());
			modifyButton.setText("Edit");
			modifyButton.setEnabled(false);
		}

		// delete button
		deleteButton = new Button(buttonComposite, 0x800000);
		deleteButton.setToolTipText("Remove selected " + label + "(s)");
		deleteButton.setLayoutData(data);
		// deleteButton.setImage(OFSPropertyPlugIn.getBundledImageDescriptor("icons/obj16/delete.gif").createImage());
		deleteButton.setText("Remove");
		deleteButton.setEnabled(false);

		if (sorter) {
			// moveup button
			moveUpBtn = new Button(buttonComposite, 0x800000);
			moveUpBtn.setToolTipText("Move up selected " + label);
			moveUpBtn.setLayoutData(data);
			// moveUpBtn.setImage(OFSPropertyPlugIn.getBundledImageDescriptor("icons/obj16/up.gif").createImage());
			moveUpBtn.setText("   Up   ");
			moveUpBtn.setEnabled(false);

			// movedown button
			moveDownBtn = new Button(buttonComposite, 0x800000);
			moveDownBtn.setToolTipText("Move down selected " + label);
			moveDownBtn.setLayoutData(data);
			// moveDownBtn.setImage(OFSPropertyPlugIn.getBundledImageDescriptor("icons/obj16/down.gif").createImage());
			moveDownBtn.setText("  Down  ");
			moveDownBtn.setEnabled(false);
		}

	}

	/**
	 * @param tableComposite
	 * @param columns
	 */
	protected void createTableComposite(TableLayoutComposite tableComposite,
			List<ListReferenceColumn> columns) {
		// create table control
		if (isMultiSelect()) {
			table = new Table(tableComposite, SWT.BORDER | SWT.H_SCROLL
					| SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION
					| SWT.LINE_SOLID);
		} else {
			table = new Table(tableComposite, SWT.BORDER | SWT.H_SCROLL
					| SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION
					| SWT.LINE_SOLID);
		}

		tableFont = new Font(Display.getCurrent(), "Arial", 8, SWT.NORMAL);
		table.setFont(tableFont);
		if (columns.size() > 2) {
			table.setSize(600, 150);
		} else {
			table.setSize(400, 150);
		}

		TableColumn column = null;
		ListReferenceColumn columnRef = null;
		for (int i = 0; i < columns.size(); i++) {
			columnRef = columns.get(i);
			column = new TableColumn(table, columnRef.getColumnAlignment());
			column.setText(columnRef.getLabel());
			if (columnRef.getWidth() != 0 && columnRef.getWeight() != 0) {
				tableComposite.addColumnData(new ColumnWeightData(columnRef
						.getWeight(), columnRef.getWidth(), false));
			} else {
				tableComposite.addColumnData(new ColumnWeightData(100, 100,
						false));
			}
			column.setResizable(false);
			column.setMoveable(false);

		}
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
	}

	/**
	 * @param booleanColumns
	 */
	protected void createTableViewer(List<ListReferenceColumn> columns,
			boolean sorter, boolean editable) {
		tableViewer = new TableViewer(table);
		final boolean sortEnabled = sorter;
		final boolean editEnabled = editable;
		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						// enable the delete button
						IStructuredSelection selection = (IStructuredSelection) event
								.getSelection();
						if (selection != null) {
							deleteButton.setEnabled(true);
							if (editEnabled)
								modifyButton.setEnabled(true);
							if (selection.size()>1)
								modifyButton.setEnabled(false);
						}
						if (sortEnabled && selection != null
								&& selection.toList().size() == 1) {
							moveUpBtn.setEnabled(true);
							moveDownBtn.setEnabled(true);
						}
						if (readOnly) {
							deleteButton.setEnabled(false);
							moveUpBtn.setEnabled(false);
							moveDownBtn.setEnabled(false);
							addButton.setEnabled(false);
						}
					}

				});
		// TextCellEditor textEditor = null;
		// CellEditor[] editors = new CellEditor[columns.size()];
		String[] columnLabels = new String[columns.size()];
		final List<ListReferenceColumn> tableColumns = columns;
		// cell editors
		for (int i = 0; i < columns.size(); i++) {
			ListReferenceColumn column = columns.get(i);
			columnLabels[i] = columns.get(i).getLabel();
			/*
			 * if (column.isBooleanType()){ editors[i]= new
			 * CheckboxCellEditor(table); } else { textEditor = new
			 * TextCellEditor(table); ((Text)
			 * textEditor.getControl()).setTextLimit(500); editors[i] =
			 * textEditor; }
			 */
		}
		// tableViewer.setCellEditors(editors);
		tableViewer.setColumnProperties(columnLabels);
		// contentProvider
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof EList) {
					return ((EList) inputElement).toArray();
				} else {
					return null;
				}
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer1, Object obj, Object obj1) {
			}

		});

		// labelprovider
		tableViewer.setLabelProvider(new ITableLabelProvider() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
			 *      int)
			 */
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
			 *      int)
			 */
			public String getColumnText(Object element, int columnIndex) {
				EObject obj = (EObject) element;
				EList list = obj.eClass().getEAllStructuralFeatures();

				EAttribute attribute = null;
				EReference reference = null;
				for (int ii = 0; ii < list.size(); ii++) {
					Object containment = list.get(ii);
					if (containment instanceof EAttribute) {
						attribute = (EAttribute) containment;
						if (attribute.getName().toLowerCase().equals(
								tableColumns.get(columnIndex).getName()
										.toLowerCase())) {
							if (obj.eGet(attribute) == null) {
								return "";
							} else {
								return (obj.eGet(attribute)).toString();
							}
						}
					} else if (containment instanceof EReference) {
						reference = (EReference) containment;
						if (reference.getName().toLowerCase().equals(
								tableColumns.get(columnIndex).getName()
										.toLowerCase())) {
							EObject subC = (EObject) obj.eGet(reference);
							if (subC == null) {
								return "";
							} else {
								EList attList = subC.eClass()
										.getEAllAttributes();
								EAttribute att = tableColumns.get(columnIndex)
										.getAttribute();
								for (int jj = 0; jj < attList.size(); jj++) {
									EAttribute attr = (EAttribute) attList
											.get(jj);
									if (att != null
											&& att.getName().equals(
													attr.getName())) {
										if (subC.eGet(attr) != null)
											return subC.eGet(attr).toString();
									}
									if (att == null
											&& (attr.isID() || attr
													.isRequired())) {
										return subC.eGet(attr).toString();
									}
								}
							}
						}
					}

				}

				return null;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
			 */
			public void addListener(
					ILabelProviderListener ilabelproviderlistener) {
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
			 */
			public void dispose() {
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
			 *      java.lang.String)
			 */
			public boolean isLabelProperty(Object element, String property) {
				return true;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
			 */
			public void removeListener(
					ILabelProviderListener ilabelproviderlistener) {
			}

		});
	}

	/**
	 * @return
	 */
	public Button getAddButton() {
		return addButton;
	}

	/**
	 * @return
	 */
	public Button getMoveUpButton() {
		return this.moveUpBtn;
	}

	/**
	 * @return
	 */
	public Button getMoveDownButton() {
		return this.moveDownBtn;
	}

	/**
	 * @return
	 */
	public Button getDeleteButton() {
		return deleteButton;
	}

	/**
	 * @return
	 */
	public Button getModifyButton() {
		return modifyButton;
	}

	/**
	 * @return
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @return
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public void setBasicSection(boolean basicSection) {
		this.basicSection = basicSection;
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

}
